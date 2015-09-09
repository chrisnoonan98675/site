online-docs-jekyll
==================

# Setting it up locally

1. Clone this repository.
1. Install Jekyll 2.x and its requirements:
    * Linux and OS X users, follow [the official instructions](http://jekyllrb.com/docs/installation/)
    * Windows users, follow [these instructions](http://jekyll-windows.juthilo.com/) (you can skip step #3 there)
1. Install [Asciidoctor](http://asciidoctor.org/docs/install-toolchain/).
1. In the directory where you cloned the repository, execute `jekyll serve` or `jekyll serve --watch` (for [watch mode](http://jekyllrb.com/docs/usage/)). Go to `http://localhost:4000` to see the site running locally.

Tips:

* We are using Jekyll 2.x. Jekyll 3.x will not work yet because of changes in the Liquid templating system.
* It's a known issue that generating the site (even in watch mode) is quite slow. Hopefully Jekyll 3.x will fix this.
* To disable updating of the Development Dashboard while you run Jekyll in watch mode, change the `jira_dashboard` `generate` setting in `_config.yml` to `false`. **Do not commit this change to the repository!**
* If you use [Homebrew](http://brew.sh/) to install Jekyll on OS X, you may encounter [this issue](https://github.com/Homebrew/homebrew/issues/11448). [Here](http://davidensinger.com/2013/03/installing-jekyll/) is more information about fixing it.
* You may want to download a Markdown editor such as [MacDown](http://macdown.uranusjr.com/) for OS X or [MarkdownPad](http://markdownpad.com/) for Windows.

**Tip:** In MacDown, go to **Preferences** > **Rendering** and select **Detect Jekyll front-matter** to have MacDown nicely format the YAML frontmatter in Markdown files.

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

The pull request should be merged into the master branch, a release branch, or a feature branch, depending on what changes it implements.

# Versioning content

You can add version information to a topic as a whole or to a section of a topic.

To add version information to a topic as a whole, use the `since`, `deprecated`, and `removed` front matter keys. Refer to the `_drafts/documentation-template.markdown` file for information about setting values for these keys.

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

To mark an entire topic as "beta", add the following line to its front matter:

    layout: beta

To mark a section of a topic as "beta", add the following line just after the section's heading:

    <div class="alert alert-danger" role="alert">The information in this section is in beta and is subject to change.</div>

To mark a paragraph, sentence, or table cell as "beta", add the following inline label to it:

    <span class="label label-danger">beta</span>

# Drafts

Although draft versions can be put in a `_drafts` folder, it is prefered to use branching instead.

The `_drafts` folder does contain the *documentation template*.

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

Table styles are defined by Bootstrap CSS; see [this documentation](http://getbootstrap.com/css/#tables) for class names and examples. Use the following notation immediately before a table:

    {:.class}

For a [normal](http://getbootstrap.com/css/#tables-example) table:

    {:.table}
    | Column 1 | Column 2 | Column 3 |
    | -------- | -------- | -------- |
    | Content  | Content  | Content  |

For a [zebra-striped](http://getbootstrap.com/css/#tables-striped) table:

    {:.table .table-striped}
    | Column 1 | Column 2 | Column 3 |
    | -------- | -------- | -------- |
    | Content  | Content  | Content  |

## Code blocks in Markdown files

To format a block of code, indent each line by at least four spaces.

Jekyll does not support formatting a block of code by surrounding it with [three backticks](https://help.github.com/articles/github-flavored-markdown/#fenced-code-blocks).

## Syntax highlighting and line numbers

[Syntax highlighting](http://jekyllrb.com/docs/templates/#code-snippet-highlighting) is provided by Pygments. To highlight a block of code, surround it with Liquid `{% highlight %}` tags and specify the [language](http://pygments.org/languages/). To include line numbers, add `linenos`.

    {% highlight python linenos %}
    code goes here
    {% endhighlight %}

## Manual HTML anchors

Do not manually insert HTML anchors directly above headings, like this:

      <a name="upgrade_to_450"></a>
      ### Upgrading to XL Deploy 4.5.0 ###

This prevents "Upgrading to XL Deploy 4.5.0" from being rendered as a heading.

HTML anchors are automatically created for headings (h1, h2, h3, etc.).

## AsciiDoc

You can use [AsciiDoc](http://asciidoctor.org/docs/asciidoc-syntax-quick-reference/) instead of Markdown to format files. AsciiDoc support is provided by [a Jekyll plugin](https://github.com/asciidoctor/jekyll-asciidoc). If you use it, please carefully review the way that Jekyll renders the HTML file.

# Tags and subjects

To see the tags and subjects that are already in use, visit [https://docs.xebialabs.com/tags-and-subjects.html](https://docs.xebialabs.com/tags-and-subjects.html).

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

**Note also:** If you use absolute URL's, you can check the links from the comfort of your local work environment.

# Development Dashboard

The [Development Dashboard](https://docs.xebialabs.com/development-dashboard/index.html) is generated by a Jekyll plugin that pulls information from JIRA.

To make a release appear on the Development Dashboard, go to the [project versions screen](https://confluence.atlassian.com/display/JIRA/Managing+Versions) and assign a **Release date** to the desired version.

**Tip:** By default, the project name appears in sentence case; for example, *XL TestView* will appear on the Dashboard as *Xl Testview*. To override this, enter the desired project name in the version's **Description** field on the [project versions screen](https://confluence.atlassian.com/display/JIRA/Managing+Versions).

To make a story, improvement, or bug fix to appear on the Dashboard, assign it a **Fix Version** and set its **Public Issue** property must be set to one of the following options:

* Yes - summary only: Only show the issue's title
* Yes - summary and description: Show the issue's title and description

To refresh the Dashboard so it shows the latest information from JIRA, log in to [Jenkins](https://xebialabs.atlassian.net/wiki/display/Labs/Jenkins) and execute the [Jekyll docs job](https://dexter.xebialabs.com/jenkinsng/job/Documentation/job/app1/job/Jekyll%20docs/).

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

# Jekyll and Liquid

In Jekyll terminology, topics are *pages* that are located in directories such as `xl-deploy`, `xl-release`, and so on.

## File names

Refer to the template in `_drafts` for file naming guidelines.

## File front matter

Every page must have YAML front matter. Refer to the template in `_drafts` for information about the front matter.

## Drafts

Save drafts of pages in `_drafts`. Drafts are never converted to HTML.

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
| `remove_whitespace.rb` | Adds the `{% strip %}` tag, which you can use to remove the empty lines that Liquid loops produce | [Source](https://github.com/aucor/jekyll-plugins/blob/master/strip.rb) | MIT |

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

The old way to do it:

1. Add the `jekyll` properties listed [here](https://xebialabs.atlassian.net/wiki/display/Labs/devdoc.xebialabs.com) to your `gradle.properties` file.
1. Create a folder at `/var/lib/jenkins/jekyll` and set its permissions with `chmod o+w`.
1. Go to the location where you cloned the online-docs-jekyll repository.
1. Execute `gradle jekyllFetchSources` or `gradle jFS`.
1. Execute `jekyll serve`.
1. Go to `http://localhost:4000`.
