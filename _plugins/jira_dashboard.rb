require "json"
require "net/http"
require "net/https"
require "yaml"

def log(message)
  puts "jira-dashboard :: #{message}"
end

module Jira
  class Base
    def to_liquid
      content = Hash[instance_variables.map { |name|
        attribute = name[1..name.size]
        value = instance_variable_get(name)
        [attribute, value]
      }]
      return content
    end
  end

  class Products
    attr_reader :name, :title, :jira_id, :jira_project

    def initialize(name, title, jira_id, jira_project)
      @name = name
      @title = title
      @jira_id = jira_id
      @jira_project = jira_project
    end

    XL_DEPLOY = Products.new('xl-deploy', 'XL Deploy', '10010', 'DEPL')
    XL_RELEASE = Products.new('xl-release', 'XL Release', '10030', 'REL')
    XL_TEST = Products.new('xl-testview', 'XL TestView', '10430', 'TES')

    ALL = [XL_DEPLOY, XL_RELEASE, XL_TEST]
  end

  class Product < Base
    attr_accessor :name, :title, :upcoming_releases, :recent_releases_by_month

    def initialize
      @upcoming_releases = []
      @recent_releases_by_month = {}
    end
  end

  class Release < Base
    attr_accessor :id, :name, :description, :archived, :released, :start_date, :release_date, :issues

    def initialize
      @issues = []
    end

    def self.from_json(json)
      release = Release.new
      release.id = json['id'].to_s
      release.name = json['name'].to_s
      release.description = (json['description'] || '').to_s
      release.archived = json['archived']
      release.released = json['released']
      release.start_date = json['startDate'] ? Date.strptime(json['startDate'].to_s, '%Y-%m-%d') : nil
      release.release_date = json['releaseDate'] ? Date.strptime(json['releaseDate'].to_s, '%Y-%m-%d') : nil
      return release
    end

    def release_type
      @name =~ /[0-9]+\.[0-9]+\.0/ ? 'Major' : 'Maintenance'
    end

    def release_target
      @name.include?('plugin') ? 'Plugins' : 'Product'
    end

    def title
      @description.nil? || @description.empty? ? @name.split(/[^a-z0-9\.]/i).map(&:capitalize).join(" ") : @description.to_s
    end

    def precise_date?
      (@release_date.mjd - DateTime.now.mjd).abs < 60
    end

    def get_issues_by_type(type, *types)
      return @issues.select { |x| x.issue_type == type or types.include?(x.issue_type) }
    end

    def component
      parts = parse_version
      if parts.size > 1
        return parts[0]
      end
      return ''
    end

    def version
      parts = parse_version
      if parts.size > 1
        return parts[1..parts.size].join("-")
      end
      return ''
    end

    def parse_version
      @name.split(/\-(?=[\d])/)
    end

    def to_liquid
      hash = super
      hash['id'] = id
      hash['release_type'] = release_type
      hash['release_target'] = release_target
      hash['title'] = title
      hash['precise_date'] = precise_date?
      hash['stories'] = get_issues_by_type 'Story', 'Epic'
      hash['improvements'] = get_issues_by_type 'Improvement'
      hash['bugs'] = get_issues_by_type 'Bug'
      hash['component'] = component
      hash['version'] = version
      return hash
    end

  end

  class Issue < Base
    attr_accessor :key, :summary, :issue_type, :fix_versions, :description

    def self.from_json(json)
      issue = Issue.new
      issue.key = json['key'].to_s
      issue.summary = json['fields']['summary'].to_s
      issue.description = (json['renderedFields']['description'] || '').to_s
      issue.issue_type = json['fields']['issuetype']['name'].to_s
      issue.fix_versions = json['fields']['fixVersions'].map { |x| x['id'] }
      return issue
    end
  end

  class Server
    VERSIONS_REQ_URL = '%{base_url}/project/%{jira_id}/versions'
    ISSUES_SUMMARY_REQ_URL = '%{base_url}/search/?fields=key,summary,issuetype,fixVersions&maxResults=9999&expand=renderedFields&jql=project=%{jira_project}+and+fixVersion+in+(%{versions})+and+"Public+Issue"="Yes - summary only"'
    ISSUES_DESCRIPTION_REQ_URL = '%{base_url}/search/?fields=key,summary,issuetype,fixVersions,description&maxResults=9999&expand=renderedFields&jql=project=%{jira_project}+and+fixVersion+in+(%{versions})+and+"Public+Issue"="Yes - summary and description"'

    def initialize(base_url, username, password)
      @base_url = base_url
      @username = username
      @password = password
    end

    def get_products_with_releases
      products = Products::ALL.map { |productType|
        product = Product.new
        product.name = productType.name
        product.title = productType.title
        product.upcoming_releases = get_upcoming_product_releases(productType)
        product.recent_releases_by_month = get_recent_product_releases(productType).group_by { |release | release.release_date.strftime("%B %Y") }
        product
      }
      return products
    end

    def get_upcoming_product_releases(product)
      log "Gathering upcoming releases for product #{product.title}"

      upcoming_releases = gel_all_product_releases(product).select { |release|
        !release.archived and !release.released and release.release_date and !release.description.include?('(internal)')
      }

      return upcoming_releases unless upcoming_releases.any?

      log "Gathering issues for releases #{upcoming_releases.map(&:name)}"
      all_issues = get_all_issues_for_releases(product, upcoming_releases)
      upcoming_releases.each { |release|
        release.issues = all_issues[release.id] || []
        release.issues = release.issues.sort_by(&:key)
      }
      upcoming_releases = upcoming_releases.select{|x| x.issues.any?}.sort_by(&:release_date)
      return upcoming_releases
    end

    def get_recent_product_releases(product)
      log "Gathering recent releases for product #{product.title}"

      recent_releases = gel_all_product_releases(product).select { |release|
        !release.archived and release.released and release.release_date and release.release_date <= DateTime.now and release.release_date >= (DateTime.now - 180)
      }

      return recent_releases unless recent_releases.any?

      log "Gathering issues for releases #{recent_releases.map(&:name)}"
      all_issues = get_all_issues_for_releases(product, recent_releases)
      recent_releases.each { |release|
        release.issues = all_issues[release.id] || []
        release.issues = release.issues.sort_by(&:key)
      }
      recent_releases = recent_releases.select{|x| x.issues.any?}.sort_by(&:release_date).reverse!
      return recent_releases
    end

    def gel_all_product_releases(product)
      return get_json(VERSIONS_REQ_URL % {:base_url => @base_url, :jira_id => product.jira_id}).map { |x|
        Release.from_json(x)
      }
    end

    def get_all_issues_for_releases(product, releases)
      all_issues = get_issues_for_releases(product, releases, ISSUES_SUMMARY_REQ_URL) | get_issues_for_releases(product, releases, ISSUES_DESCRIPTION_REQ_URL)
      grouped_issues = {}
      all_issues.each { |issue|
        issue.fix_versions.each { |fv|
          grouped_issues[fv] = (grouped_issues[fv] || []) << issue
        }
      }
      return grouped_issues
    end

    def get_issues_for_releases(product, releases, query_url)
      return get_json(query_url % {:base_url => @base_url, :jira_project => product.jira_project, :versions => releases.map(&:id).join(',')})['issues'].map { |x|
        Issue.from_json(x)
      }
    end

    def get_json(url)
      uri = URI(URI.escape(url))
      #puts "GET #{url}"
      Net::HTTP.start(uri.host, uri.port,
                      :use_ssl => uri.scheme == 'https',
                      :verify_mode => OpenSSL::SSL::VERIFY_NONE) do |http|

        request = Net::HTTP::Get.new uri.request_uri
        request.basic_auth @username, @password

        response = http.request request
        error_value = response.value # will raise exception if not 200 OK
        return JSON.parse(response.body)
      end
    end
  end

end

module Jekyll
  class ProductDashboardPage < Page
    def initialize(site, base, dir, product)
      @site = site
      @base = base
      @dir = dir
      @name = "#{product.name}-tracker.html"
      self.process(@name)
      self.read_yaml(File.join(base, '_layouts'), 'release_dashboard.html')
      self.data['product'] = product
      self.data['title'] = "#{product.title} Fix Tracker"
    end
  end

  class ProductDashboardGenerator < Generator
    def generate(site)
      dashboard_config = site.config['jira_dashboard'] || {}
      generate = dashboard_config['generate'] || false
      if not generate
        log 'Skipping development dashboard generation'
        return
      end

      log 'Gathering data for development dashboard site'

      dir = dashboard_config['dir']
      jira_url = dashboard_config['jira_url']
      jira_username = dashboard_config['jira_username']
      jira_password = dashboard_config['jira_password']
      server = Jira::Server.new(jira_url, jira_username, jira_password)

      server.get_products_with_releases.each { |product|
        log "Generating development dashboard site for product #{product.title}"
        dashboard_page = ProductDashboardPage.new(site, site.source, dir, product)
        dashboard_page.render(site.layouts, site.site_payload)
        dashboard_page.write(site.dest)
        site.pages << dashboard_page
      }

      log 'Finished development dashboard generation, yay!'
    end
  end
end