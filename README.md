online-docs-jekyll
==================

# Setting it up locally

1. Clone this repository.
1. Install Jekyll and its requirements:
    * Linux and OS X users, follow [the official instructions](http://jekyllrb.com/docs/installation/)
    * Windows users, follow [these instructions](http://jekyll-windows.juthilo.com/) (you can skip step #3 there)
1. Install [Asciidoctor](http://asciidoctor.org/docs/install-toolchain/).
1. In the directory where you cloned the repository, execute `jekyll serve` or `jekyll serve --watch` (for [watch mode](http://jekyllrb.com/docs/usage/)).

Go to `http://localhost:4000` to see the site running locally.

**Note:** If you use [Homebrew](http://brew.sh/) to install Jekyll on OS X, you may encounter [this issue](https://github.com/Homebrew/homebrew/issues/11448). [Here](http://davidensinger.com/2013/03/installing-jekyll/) is more information about fixing it.

# Contributing

*Coming soon!*

# Things to know about formatting

## Headings

Use heading 2 (`##` in Markdown) and lower. Don't use heading 1.

## Placeholders

In [Liquid](https://github.com/Shopify/liquid/wiki) (the template language that Jekyll uses), variables are identified by double curly brackets, just like XebiaLabs-style placeholders. If you want to show a XebiaLabs-style placeholder, you must surround it with [`{% raw %}` tags](http://docs.shopify.com/themes/liquid-documentation/tags/theme-tags#raw).

**Example #1**

You can handle a secure property by setting the property on the deployable to a placeholder such as `{% raw %}{{my.datasource.password}}{% endraw %}`.

**Example #2**

The deployable contains `username = {% raw %}{{my.password}}{% endraw %}`.

**Example #3**

	transform.2.find=((quux))
	transform.2.replacement={% raw %}{{quux-transform-2}}{% endraw %}

## Code blocks in Markdown files

To format a block of code, indent each line by at least four spaces.

Jekyll does not support formatting a block of code by surrounding it with [three backticks](https://help.github.com/articles/github-flavored-markdown/#fenced-code-blocks).

## Manual HTML anchors

Do not manually insert HTML anchors directly above headings, like this:

      <a name="upgrade_to_450"></a>
      ### Upgrading to XL Deploy 4.5.0 ###

This prevents "Upgrading to XL Deploy 4.5.0" from being rendered as a heading.

HTML anchors are automatically created for headings (h1, h2, h3, etc.).

## AsciiDoc

You can use [AsciiDoc](http://asciidoctor.org/docs/asciidoc-syntax-quick-reference/) instead of Markdown to format files. AsciiDoc support is provided by [a Jekyll plugin](https://github.com/asciidoctor/jekyll-asciidoc). If you use it, please carefully review the way that Jekyll renders the HTML file.

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

# Logical structure of the site

* Product
    * Documentation in topic-based format
        * Concepts
        * How-to
    * Documentation in legacy format (product manuals)
    * Auto-generated documentation
    * Plugin
        * Documentation in legacy format (plugin manual)
        * Documentation in topic-based format
            * Concept
            * How-to

# How the site works

In Jekyll terminology, topics are *pages* that are located in directories such as `xl-deploy`, `xl-release`, and so on.

## Folders

The purpose of some folders might not be clear if you're not familiar with Jekyll, so here are some explanations:

| Folder | Purpose |
| ------ | ------- |
| `_plugins` | Contains Jekyll plugins |
| `images` | Contains images for the site itself (like product logos) *and* images for topics |
| `sample-scripts` | Contains sample files for topics (such as downloadable Python scripts) |
| `_site` | This is where Jekyll puts generated files |

## File names

Refer to the template in `_drafts` for file naming guidelines.

## File front matter

Every page must have YAML front matter. Refer to the template in `_drafts` for information about the front matter.

## Drafts

Save drafts of pages in `_drafts`. Drafts are never converted to HTML.

# Jekyll and Liquid

## Layout files

| Jekyll layout file   | Page(s) it applies to  | Why is this layout file needed? |
|----------------------|------------------------|---------------------------------|
| `default.html` | All | Master layout file that calls most of the `includes` that make up the page structure |
| `list-in-sidebar.html` | Landing page of a product | Triggers Jekyll to add a link to the page in the sidebar |
| `page.html` | All pages | Includes dynamically generated breadcrumbs | 

## Plugins

| Plugin file name | Description | Source | License |
| ---------------- | ----------- | ------ | ------- |
| `breadcrumbs.rb` | Creates dynamic breadcrumbs on pages | [Source](http://biosphere.cc/software-engineering/jekyll-breadcrumbs-navigation-plugin/) | None |
| `asciidoc_plugin.rb` | Enables Jekyll to interpret Asciidoc files | [Source](https://github.com/asciidoctor/jekyll-asciidoc) | MIT |
| `pageless_redirects.rb` | Allows you to create redirects in `_redirects.yml` | [Source](https://github.com/nquinlan/jekyll-pageless-redirects/pull/7) | MIT |
| `sitemap_generator.rb` | Generates `sitemap.xml` | [Source](https://github.com/kinnetica/jekyll-plugins) | Creative Commons |

# Tips

* To start Jekyll in watch mode, use `jekyll serve --watch`. If you change `_config.yml`, you have to stop Jekyll (CTRL+C) and restart it.
* Jekyll runs locally at `localhost:4000`.
* If a folder does not contain an `index.markdown` or `index.html` file, trying to access it will return a 404 error.
* If you combine conditions in a loop, the order of the conditions matters.
* In gradle, when deploying the content, we use `jekyll build --config "_config.yml,_jekyll.xebialabs.config.yml"` in order to override base URL, which is taken from the second configuration file. [DEPRECATED]

# Useful links

* [Intro to Jekyll](http://jekyllbootstrap.com/lessons/jekyll-introduction.html#toc_0)
* [Liquid template info](https://github.com/Shopify/liquid/wiki/Liquid-for-Designers)
* [Jekyll without plugins](http://captnemo.in/blog/2014/01/20/pluginless-jekyll/)
* [Markdown help](http://stackoverflow.com/editing-help)
* [YAML sample](http://en.wikipedia.org/wiki/YAML#Sample_document)
* [Liquid cheatsheet](http://cheat.markdunkley.com/)
* [Some Jekyll plugins](https://github.com/recurser/jekyll-plugins)
* [HTML-to-Markdown converter](http://domchristie.github.io/to-markdown/)
* [Markdown-Writer for Atom](https://atom.io/packages/markdown-writer): Might be useful for tag management/consistency
* [Sort and filter in Jekyll](http://www.leveluplunch.com/blog/2014/04/03/sort-pages-by-title-filter-array-by-layout-jekyllrb/): Describes how to sort pages by weight and filter them by layout (this is the method used to generate the sidebar menu)
* [Advanced Jekyll features](http://www.divshot.com/blog/web-development/advanced-jekyll-features/)

# Workflow

* Editors can commit directly on master of the online docs repository. 
* Content can be reviewed and approved on the [testdocs.xebialabs.com](http://testdocs.xebialabs.com) site which refreshes automatically (via [Documentation/Jekyll docs](https://dexter.xebialabs.com/jenkinsng/job/Documentation/job/Jekyll%20docs/) job).
* On demand, content can be pushed out to the production site (e.g. via Jenkins) _not implemented yet_.

# Pulling doc from other repositories

Follow these instructions to pull all documentation from the product and plugin repositories.

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

The old way to do it:

1. Add the `jekyll` properties listed [here](https://xebialabs.atlassian.net/wiki/display/Labs/devdoc.xebialabs.com) to your `gradle.properties` file.
1. Create a folder at `/var/lib/jenkins/jekyll` and set its permissions with `chmod o+w`.
1. Go to the location where you cloned the online-docs-jekyll repository.
1. Execute `gradle jekyllFetchSources` or `gradle jFS`.
1. Execute `jekyll serve`.
1. Go to `http://localhost:4000`.

Note that you'll have to clean up this documentation locally; don't commit it to the online-docs-jekyll repository.
