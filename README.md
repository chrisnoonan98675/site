online-docs-jekyll
==================

# Introduction

This repository contains:

* XebiaLabs product and plugin documentation in topics
* Code used to generate and style the Jekyll-based part of the documentation site

For general information about XebiaLabs documentation, refer to [Technical documentation basics](https://xebialabs.atlassian.net/wiki/display/Labs/Technical+documentation+basics).

# Generate the documentation site locally

To generate the Jekyll-based part of the documentation site locally:

1. Clone this repository.
1. Install Jekyll 3.0.1 and its requirements:
    * Linux and OS X users, follow [the official instructions](http://jekyllrb.com/docs/installation/). **Important:** If you're using OS X 10.11 (El Capitan) or later, [read this important info](http://jekyllrb.com/docs/troubleshooting/#jekyll-amp-mac-os-x-1011)!
    * Windows users, follow [these instructions](http://jekyll-windows.juthilo.com/)
1. In the directory where you cloned the repository, execute `jekyll serve`. Go to `http://localhost:4000` to see the site running locally.

## Installation tips

You may also need to install:

* [Magnific Popup](https://github.com/dimsemenov/Magnific-Popup) for video pop-ups
* [Rouge](https://rubygems.org/gems/rouge/versions/1.10.1) for code syntax highlighting

If you use [Homebrew](http://brew.sh/) to install Jekyll on OS X, you may encounter [this issue](https://github.com/Homebrew/homebrew/issues/11448). [Here](http://davidensinger.com/2013/03/installing-jekyll/) is more information about fixing it.

## Usage tips

If you run into problems, first check that you have the right version of Jekyll with `jekyll --version`.

### Jekyll environments

***NEW!*** There are three Jekyll environments that you can use.

* **Development**: Default environment when you execute `jekyll serve` or `jekyll build`
* **Lightweight**: Used to generate the site locally as quickly as possible
* **Production**: Used to generate pages for the live documentation site

#### Development environment

***NEW!*** By default, `jekyll serve` and `jekyll build` both run Jekyll in a "development environment". This means that:

* Some navigation elements (such as the Google search box, the sidebar menu, and breadcrumbs) are disabled to reduce the time it takes to generate the site
* An *Edit this page* link is available at the bottom of each topic

Initial generation of the site in development mode takes about 25 seconds.

Keep in mind that a site generated in development mode will not look exactly like the live documentation site because of the disabled elements.

#### Lightweight environment

***NEW!*** The "lightweight environment" has the same features as the development environment, but with even more elements disabled.

Initial generation of the site in lightweight mode takes about 7 seconds.

Keep in mind that a site generated in lightweight mode will look very different form the live documentation site. It is recommended that you use this mode if you only plan to work with a few specific topics.

To use the lightweight environment, start Jekyll with:

    JEKYLL_ENV=lightweight jekyll serve

You can combine this with the `--incremental` or `--no-watch` options (see below).

#### Production environment

***NEW!*** The "production environment" generates the site including all elements that are needed for the live documentation site (menus, lists, breadcrumbs, etc.). The [Jenkins job](https://dexter.xebialabs.com/jenkinsng/job/Documentation/job/xldoc/job/Jekyll%20docs/) for the documentation site uses the production environment.

Initial generation of the site in production mode takes 80 to 90 seconds.

To use the production environment, start Jekyll with:

    JEKYLL_ENV=production jekyll serve

You can combine this with the `--incremental` or `--no-watch` options (see below).

## Speed up site regeneration

***NEW!*** If you execute `jekyll serve`, the site is regenerated every time you make a change (except in `_config.yml`). You can prevent this by using the `--incremental` option. This means Jekyll will only regenerate the files that you change, which usually takes less than 1 second.

Note that this is an experimental feature; incremental builds don't always pick up changes in Liquid code or changes that affect the various lists of topics that Jekyll generates. If you're using incremental builds and you don't see the output you expected, stop Jekyll (with CTRL+C) and run `jekyll serve` or `jekyll build`. This will regenerate the whole site and clean up the `_site` directory; you can then try using incremental builds again.

Alternatively, you can disable regeneration completely by executing `jekyll serve --no-watch`.

## Disable the Fix Tracker

Another way to speed up site generation is to disable the Fix Tracker (formerly known as the Development Dashboard) plugin. It is recommended that you do this if you want to run the site locally without internet access, because the plugin accesses the JIRA API.

Change the `jira_dashboard` `generate` setting in `_config.yml` to `false`, then start Jekyll. *Do not commit this change to the repository!*

## Writing tips

* You may want to use a Markdown editor such as [MacDown](http://macdown.uranusjr.com/) for OS X or [MarkdownPad](http://markdownpad.com/) for Windows.
* In MacDown, go to **Preferences** > **Rendering** and select **Detect Jekyll front-matter** to see the [YAML front matter](http://jekyllrb.com/docs/frontmatter/) in a nice table.
* Be sure to spell check your work! Set your spell checker to *U.S. English*.

# Publishing changes to the site

When you commit a change to the master branch of this repository, a [Jenkins job](https://dexter.xebialabs.com/jenkinsng/job/Documentation/job/app1/job/Jekyll%20docs/) is triggered. This job generates the HTML and immediately publishes it to [docs.xebialabs.com](https://docs.xebialabs.com).

If you want to make a documentation change that should *not* be published immediately, create a branch.

# Branches

## Release branches

A branch should be created for each product or plugin release; for example, `xl-release-4.6.0` or `xl-deploy-5.0.0-beta-2`. The release branch contains all changes that should be published for that release. The branch must be merged into the master branch when it's time to release that product/plugin version.

## Feature branches

You can create branches for a feature—for example, `DEPL-1234` or `REL-5678`—but feature branches are only for short-term use. They should eventually be merged into the appropriate release branch when the feature is ready.

# Pull requests

If you want to submit changes for review without immediately publishing them, [create a branch](https://help.github.com/articles/creating-and-deleting-branches-within-your-repository/#creating-a-branch) and then [create a pull request](https://help.github.com/articles/creating-a-pull-request/).

After review, the pull request should be merged into the master branch, a release branch, or a feature branch, depending on what changes it implements.

# File naming convention

The naming convention for documentation site files is `your-file-name-here.markdown`. Do not use underscores, spaces, or uppercase letters in the file name.

# Front matter options

[Front matter](http://jekyllrb.com/docs/frontmatter/) consists of keys and values located between dashes at the top of every Markdown or HTML file. Jekyll ignores files that do not have front matter.

You can specify front matter options in any order. The keys *must* match the keys listed below.

| Key | Required? | Multiple values allowed? | Capitalization | Spaces allowed? | Description |
| --- | --------- | ------------------------ | -------------- | --------------- | ----------- |
| `title` | Yes | No | Sentence case | Yes | Title of the page. |
| `categories` | See below | Yes | Lowercase | No | Product(s) that the page applies to. |
| `subject` | See below | No | Sentence case | Yes | Subject of the page; this is like a tag, but only one subject is allowed. Try to use [subjects that are already in use](https://docs.xebialabs.com/tags-and-subjects.html). |
| `tags` | See below | Yes | Lowercase | Yes | Tags to help users browse posts. Try to use [tags that are already in use](https://docs.xebialabs.com/tags-and-subjects.html). |
| `since` | No | No | Not applicable | Yes | Version in which the functionality described in the page was introduced, in `Product Name X.Y.Z` format (for example, `XL Deploy 5.0.0`). |
| `deprecated` | No | No | Not applicable | Yes | Version in which the functionality described in the page was deprecated, in the same format as `since`. |
| `removed` | No | No | Not applicable | Yes | Version in which the functionality described in the page was removed, in the same format as `since`. |
| `weight` | No | No | Not applicable | No | Determines the page's position in various lists. |
| `beta` ***NEW!*** | No | No | Lowercase | No | Set to `true` to insert a warning indicating that the page's information is in beta. |
| `pre_rules` ***NEW!*** | No | No | Lowercase | No | Set to `true` to insert a note indicating that the page's information applies to Java-based plugins instead of XL Deploy rules. |
| `no_index` ***NEW!*** | No | No | Lowercase | No | Set to `true` to prevent search engines from indexing the page. |
| `no_mini_toc` ***NEW!*** | No | No | Lowercase | No | Set to `true` to prevent Jekyll from generating a mini table of contents in the topic. |
| `no_breadcrumbs` ***NEW!*** | No | No | Lowercase | No | Set to `true` to prevent Jekyll from generating breadcrumbs in the topic. |
| `list_in_sidebar` ***NEW!*** | No | No | Lowercase | No | Set to `true` to list the page in the site sidebar. |
| `sidebar_weight` ***NEW!*** | No | No | Not applicable | No | Determines the page's position in the site sidebar. |
| `placeholder` ***NEW!*** | No | No | Lowercase | No | Set to `true` if the page is a placeholder that should be redirected to versioned documentation. |
| `destination` ***NEW!*** | No | No | Lowercase | No | URL that the placeholder page should redirect to (ignored unless `placeholder` is `true` |

**Tip:** To see the tags and subjects that are in use, go to [https://docs.xebialabs.com/tags-and-subjects.html](https://docs.xebialabs.com/tags-and-subjects.html).

## Categories, subject, and tags

The `categories`, `subject`, and `tags` front matter options are almost always required for topics. The exception is a topic that you don't want to appear in lists like [this](https://docs.xebialabs.com/xl-release/#browse-documentation-by-subject) and [this](https://docs.xebialabs.com/xl-testview/concept/). We occasionally do this for beta documentation that we want to make available to a limited number of customers.

If a topic doesn't appear in lists where you expect it, double-check the `categories`, `subject`, and `tags`.

## Single values vs. multiple values

If a front matter option only allows a single value, then specify the value on the same line as the key:

    title: Sample topic title
    no_mini_toc: true

If a front matter option allows multiple values, then specify the values in a list:

    categories:
    - xl-deploy
    - xl-release
    tags:
    - tag 1
    - tag 2

If a topic doesn't appear in lists where you expect it, double-check the formatting of the front matter options.

## How page weights work

***NEW!*** Lists of topics are ordered by the `weight` value in their front matter. Topics without a weight are ordered alphabetically (ascending) by file name.

To help prevent weight clashes, these ranges are used for topics:

| Product | Weight range allowed |
| ------- | -------------------- |
| XL Deploy | 100 - 399 |
| XL Release | 400 - 699 |
| XL TestView | 700 - 999 |

**Note:** Weights over 999 do not work.

**Tip:** To see the weights of all topics, go to [https://docs.xebialabs.com/topic-weights.html](https://docs.xebialabs.com/topic-weights.html).

# Things to know about formatting

## Headings

Use heading 2 (`##` in Markdown) and lower. Don't use heading 1.

## Placeholders and double curly brackets

In [Liquid](https://github.com/Shopify/liquid/wiki) (the template language that Jekyll uses), variables are identified by double curly brackets, just like XebiaLabs-style placeholders. If you want to show two curly brackets or show a XebiaLabs-style placeholder, you must surround it with [`{% raw %}` tags](http://docs.shopify.com/themes/liquid-documentation/tags/theme-tags#raw).

**Example #1**

You can handle a secure property by setting the property on the deployable to a placeholder such as `{% raw %}{{my.datasource.password}}{% endraw %}`.

**Example #2**

The deployable contains `username = {% raw %}{{my.password}}{% endraw %}`.

**Example #3**

	transform.2.find=((quux))
	transform.2.replacement={% raw %}{{quux-transform-2}}{% endraw %}

**Example #4**

Placeholders are surrounded by {% raw %}`{{`{% endraw %} and {% raw %}`}}`{% endraw %}.

## Table styles in Markdown files

Table styles are defined by Bootstrap CSS; see [this documentation](http://getbootstrap.com/css/#tables) for class names and examples. Use the following notation immediately before each table (without a line break between them):

    {:.class}

We normally use the [zebra-striped table style](http://getbootstrap.com/css/#tables-striped):

    {:.table .table-striped}
    | Column 1 | Column 2 | Column 3 |
    | -------- | -------- | -------- |
    | Content  | Content  | Content  |

You can also use the [normal table style](http://getbootstrap.com/css/#tables-example):

    {:.table}
    | Column 1 | Column 2 | Column 3 |
    | -------- | -------- | -------- |
    | Content  | Content  | Content  |

### Line breaks in table cells

***NEW!*** In Markdown files, you must use `<br />` HTML tags to insert line breaks and blank lines in content that is inside a table cell. For example:

    This is the first line of text.<br /><br />This is the second line of text.

## Code blocks in Markdown files

To format a block of code, indent each line by at least four spaces. Formatting a block of code by [fencing it with backticks](https://help.github.com/articles/github-flavored-markdown/#fenced-code-blocks) produces inconsistent results.

## Syntax highlighting and line numbers

***NEW!*** [Syntax highlighting](http://jekyllrb.com/docs/templates/#code-snippet-highlighting) is provided by [Rouge](https://github.com/jneen/rouge). To highlight a block of code, surround it with Liquid `{% highlight %}` tags and specify the [language](https://github.com/jneen/rouge/wiki/List-of-supported-languages-and-lexers). To include line numbers, add `linenos`.

    {% highlight python linenos %}
    code goes here
    {% endhighlight %}

## Manual HTML anchors

Do not manually insert HTML anchors directly above headings in Markdown files, like this:

      <a name="upgrade_to_450"></a>
      ### Upgrading to XL Deploy 4.5.0 ###

This prevents "Upgrading to XL Deploy 4.5.0" from being rendered as a heading.

HTML anchors are automatically created for headings (h1, h2, h3, etc.).

# "Latest" links

"Latest" links point to the latest version of the *versioned* documentation for each product. There are two formats; the new format is preferred, but the legacy format is also supported.

**New format:** `/<product-id>/latest/<doc-file-name.html>`

**Legacy formats:**

| Documentation | Pattern |
| --------------| ------- |
| XL Deploy and bundled plugins | `/releases/latest/deployit/<doc-file-name.html>` |
| XL Deploy non-bundled plugins | `/releases/latest/<plugin-name>/<doc-file-name.html>` |
| XL Release | `/releases/latest/xl-release/<doc-file-name.html>` |
| XL Release plugins | `/releases/latest/<plugin-name>/<doc-file-name.html>` |
| XL Scale | `/releases/latest/xl-scale-plugin/<doc-file-name.html>` |
| XL Scale plugins | `/releases/latest/<plugin-name>/<doc-file-name.html>` |

See `_redirects.yml` for a complete list of the redirects that are available.

**Note:** At this time, there is no "latest" link for the Jython API documentation.

# Update the Fix Tracker

The [Fix Tracker](https://docs.xebialabs.com/tracker/index.html) (formerly known as the Development Dashboard) is generated by a Jekyll plugin that pulls information from JIRA.

To make a release appear on the Fix Tracker, go to the [project versions screen](https://confluence.atlassian.com/display/JIRA/Managing+Versions) and assign a **Release date** to the desired version.

**Tip:** By default, the project name appears in sentence case; for example, *XL TestView* will appear on the Fix Tracker as *Xl Testview*. To override this, enter the desired project name in the version's **Description** field on the [project versions screen](https://confluence.atlassian.com/display/JIRA/Managing+Versions).

To make a story, improvement, or bug fix to appear on the Fix Tracker, assign it a **Fix Version** and set its **Public Issue** property to one of the following options:

* Yes - summary only: Only show the issue's title
* Yes - summary and description: Show the issue's title and description

To refresh the Fix Tracker so it shows the latest information from JIRA, log in to [Jenkins](https://xebialabs.atlassian.net/wiki/display/Labs/Jenkins) and run the [Jekyll docs job](https://dexter.xebialabs.com/jenkinsng/job/Documentation/job/app1/job/Jekyll%20docs/).

# Versioning content

You can add version information to a topic as a whole or to a section of a topic.

To add version information to a topic as a whole, use the `since`, `deprecated`, and `removed` front matter options.

When adding version information to a section of a topic, include the major, minor, and patch version (`4.5.3`) or use an `x` for the patch number (`4.5.x`). Use the words *earlier* and *later* to refer to other versions. For example:

* In XL Deploy 5.0.0 and later...
* In XL Deploy 4.5.2 and earlier...
* In XL Deploy 4.5.3, XL Deploy 5.0.0, and later...

## Major new feature

If a release introduces a major new feature:

1. Write a new topic
2. Use the `since` front matter key to identify a version

## Minor new feature

If a release introduces a new feature that isn't big enough to warrant its own topic:

1. Add a new section to an existing topic
2. Use a sentence and, optionally, the section heading to identify a version

For example:

> **Using the foo feature**
> 
> In XL Deploy 5.0.0 and later, you use the foo feature to...

Or:

> **Using the foo feature in XL Deploy 5.0.0 and later**
> 
> In XL Deploy 5.0.0 and later, you use the foo feature to...

## Major change to an existing feature

If a release introduces a major change to an existing feature:

1. Copy the relevant existing topic and change its title and file name to include the version
2. Update the original topic to reflect the new release
3. Use the `since` front matter key to identify a version in the original topic

For example, if XL Deploy 5.0.0 introduced a major change to the "foo" feature:

* **Using the foo feature** (`using-the-foo-feature.html`): This would contain the XL Deploy 5.0.0 version of the foo feature
* **Using the foo feature in XL Deploy 4.5.x and earlier** (`using-the-foo-feature-in-xl-deploy-45x-and-earlier.html`)

## Minor change to an existing feature

If a release introduces a minor change to an existing feature:

1. Add a new section to an existing topic
2. Use a sentence and the section heading to identify a version

For example:

> **Using the foo feature in XL Deploy 4.5.x and earlier**
> 
> In XL Deploy 4.5.x and earlier, you use the foo feature to...
>
> **Using the foo feature in XL Deploy 5.0.0 and later**
> 
> In XL Deploy 5.0.0 and later, you use the foo feature to...

## Deprecated feature

If a release deprecates a feature, either:

* Use the `deprecated` front matter key to mark the relevant topic(s) as deprecated
* Add a note that the relevant part of a topic(s) is deprecated

For example:

> **Note:** The foo feature is deprecated as of XL Deploy 5.0.0.

## Removed feature

If a release removes a feature that was previously marked as deprecated, either:

* Use the `removed` front matter key to mark the relevant topic(s) as removed
* Add a note that the relevant part of a topic(s) has been removed

For example:

> **Note:** The foo feature was removed in XL Deploy 5.0.0.

## Beta documentation

To mark an entire topic as "beta", use the `beta` front matter option.

To mark a section of a topic as "beta", add the following line just after the section's heading:

    <div class="alert alert-danger" role="alert">The information in this section is in beta and is subject to change.</div>

To mark a paragraph, sentence, or table cell as "beta", add the following inline label to it:

    <span class="label label-danger">beta</span>

# Jekyll plugins we use

| Plugin file name | Description | Source | License |
| ---------------- | ----------- | ------ | ------- |
| `pageless_redirects.rb` | Allows you to create redirects in `_redirects.yml` | [Source](https://github.com/nquinlan/jekyll-pageless-redirects) | MIT |
| `sitemap_generator.rb` | Generates `sitemap.xml` | [Source](https://github.com/kinnetica/jekyll-plugins) | Creative Commons |
| `weighted_pages.rb` | Allows you to sort pages by the `weight` property with `site.weighted_pages` instead of `site.pages` | [Source](https://github.com/aucor/jekyll-plugins) | MIT |

# Pulling doc from other repositories

Follow these instructions to pull all documentation from the product and plugin repositories.

**Important:** Note that you'll have to clean up this documentation locally; don't commit it to the `online-docs-jekyll` repository.

Set up key-based authentication with tech ssh server:

1. Create private/public key (execute `ssh-keygen`, do not create password, name it `tech_id_rsa`)
1. Copy your public key to the tech server: `ssh-copy-id -i ~/.ssh/tech_id_rsa.pub tech@tech.xebialabs.com`
1. Make sure key based auth works (just execute `ssh tech@tech.xebialabs.com`, it should work without asking you for password)

WARNING: Sometimes ssh authentication via public key may fail. The reason for
this can usually be found in `/var/log/auth.log`. For example, if rsync executes
and in process changes mode of `/var/www/docs.xebialabs.com` directory ssh server
may detect it and refuse authentication via public key.

Now you should be able to sync documentation to local folder:

1. Execute `./_sync.sh`.
1. Execute `jekyll serve`.
1. Go to `http://localhost:4000`.
