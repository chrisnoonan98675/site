online-docs-jekyll
==================

## Setting it up locally (subject to change)

1. Clone this repository.
1. Install [Jekyll](http://jekyllrb.com/docs/installation/) and its requirements (Ruby, etc.).
1. Install [Asciidoctor](http://asciidoctor.org/docs/install-toolchain/).
1. Execute `jekyll serve` or `jekyll serve --watch` (for watch mode)
1. Add the `jekyll` properties listed [here](https://intranet.xebia.com/confluence/display/Labs/devdoc.xebialabs.com) to your `gradle.properties` file.
1. Create a folder at `/var/lib/jenkins/jekyll` and set its permissions with `chmod o+w`.

Go to `http://localhost:4000` to see the site running locally.

If you want to pull all documentation from the product and plugin repositories:

1. Go to the location where you cloned the online-docs-jekyll repository.
1. Execute `gradle jekyllFetchSources` or `gradle jFS`.
1. Execute `jekyll serve`.
1. Go to `http://localhost:4000`.

Note that you'll have to clean up this documentation locally; don't commit it to the online-docs-jekyll repository.

## Logical structure of the site

### Manual-based approach

* Landing page (index.html)
    * Product
        * Version
            * Manual
            * Manual
        * Version
            * Manual
            * Manual
        * List of knowledge base articles related to the product
            * Article
            * Article
    * List of all knowledge base articles (organised by product and/or tag)
        * Article
        * Article
        * Article

### Topic-based approach

* Landing page (index.html)
    * Product
        * Version
            * Category
                * Topic
                * Topic
            * Category
                * Topic
                * Topic
         * Version
            * Category
                * Topic
                * Topic
            * Category
                * Topic
                * Topic
        * List of knowledge base articles related to the product
            * Article
            * Article
            * Article
    * List of all knowledge base articles (organised by product and/or tag)
        * Article
        * Article
        * Article

## How the site works

### Product documentation vs. knowledge base

In Jekyll terminology:

* Product documentation consists of *pages* that are located in `products`
* Knowledge base articles are *posts* that are located in `_posts`

[This site](http://jekyllbootstrap.com/lessons/jekyll-introduction.html#toc_9) explains more about the difference between pages and posts.

### Folders

The purpose of some folders might not be clear if you're not familiar with Jekyll, so here are some explanations:

| Folder | Purpose |
| ------ | ------- |
| `_plugins` | Contains Jekyll plugins |
| `plugins` | Makes Jekyll generate a page at `http://docs.xebialabs.com/plugins/`. This folder does not contain Jekyll plugins nor XebiaLabs plugin documentation. |
| `_posts` | Contains knowledge base articles |
| `images` | Contains images for the site itself (like product logos) *and* images for knowledge base articles (subject to change) |
| `knowledge-base` | Makes Jekyll generate a page at `http://docs.xebialabs.com/knowledge-base/`, as well as pages like `http://docs.xebialabs.com/knowledge-base/xl-deploy/`. This folder does not contain knowledge base articles (those are in `_posts`). |
| `sample-scripts` | Contains sample files for knowledge base articles. Currently, you would attach these to Tips & Tricks posts (see [here](https://support.xebialabs.com/entries/59035095-Using-rules-to-interact-with-WebSphere-cluster-members) for an example) |
| `_site` | This is where Jekyll puts generated files. Don't store things here. |

### File names

Pages can be named however you want.

Posts must be named according to the convention `YYYY-MM-DD-title.markdown`. Do not use underscores in post file names.

### File front matter

Every page and post must have YAML front matter.

#### Pages

The only required variable in the page front matter is `title`. This is the title that will appear on the page, wrapped in `h1` tags. Example:

      ---
      title: My sample XL Deploy page
      ---

#### Posts

Post front matter needs a title, categories, and tags. The title will appear on the page, wrapped in `h1` tags. You can have one or more categories and tags (but the words `categories` and `tags` should always be plural). Example:

      ---
      title: My sample XL Deploy knowledge base article
      categories:
      - xl-deploy
      tags:
      - security
      - password
      - dictionary
      ---

### Categories vs. tags

*Categories* can be used to form permalinks, while tags cannot. Tags are case-sensitive and should always be in **lowercase** (capitalisation affects the way Liquid sorts tags).

In our setup:

* Categories are product IDs (`xl-deploy`, `xl-release`)
* Tags are:
    * Concepts (`plugin`, `rules`)
    * Middleware and CI tools (`tomcat`, `maven`)

#### Tag index pages

The `tag_gen.rb` plugin generates a "tag index" for each tag under `_site/tag`. This lists all posts that are tagged with that tag. If you create a new tag, you must restart Jekyll to generate an index file for that tag.

For example, if I create a post, tag it with `thisisanewtag`, and restart Jekyll, `tag/thisisanewtag/index.html` will be created at the site root, and it will list the post I just created.

### Drafts

Save drafts of pages and posts in the `_drafts` folder.

*Post* drafts (identified by the `YYYY-MM-DD-title.markdown` file naming convention) are converted to HTML if you start Jekyll with the `--drafts` switch. *Page* drafts are never converted to HTML.

## Jekyll and Liquid

### Layout files

| Jekyll layout file   | Page(s) it applies to  | Why is this layout file needed? |
|----------------------|------------------------|---------------------------------|
| `default.html` | All | Master layout file that calls most of the `includes` that make up the page structure |
| `list-in-sidebar.html` | Landing page of a product or of the KB | Triggers Jekyll to add a link to the page in the sidebar |
| `post.html` | All posts | Includes static breadcrumbs and a "Related posts" section |
| `page.html` | All pages | Includes dynamically generated breadcrumbs | 

### Plugins

| Plugin file name | Description | Source | License |
| ---------------- | ----------- | ------ | ------- |
| `breadcrumbs.rb` | Creates dynamic breadcrumbs on pages | [Source](http://biosphere.cc/software-engineering/jekyll-breadcrumbs-navigation-plugin/) | None, apparently |
| `asciidoc_plugin.rb` | Enables Jekyll to interpret Asciidoc files | [Source](https://github.com/asciidoctor/jekyll-asciidoc) | MIT |
| `tag_gen.rb` | Generates an index page in the `_site/tag` folder for every tag on a post | [Source](http://charliepark.org/tags-in-jekyll/) | None |

### Tips

* To start Jekyll in watch mode, use `jekyll server --watch`. If you change `_config.yml`, you have to stop the server (CTRL+C) and restart it.
* Jekyll runs locally at `localhost:4000`.
* If a folder does not contain an `index.markdown` or `index.html` file, trying to access it will return a 404 error. It's a good idea to always put an index file in each folder or to use `.htaccess` or another method to redirect users to a useful page.
* If you combine conditions in a loop, the order of the conditions matters.
* In gradle, when deploying the content, we use `jekyll build --config "_config.yml,_jekyll.xebialabs.config.yml"` in order to override base URL, which is taken from the second configuration file.

### Troubleshooting

If you add a knowledge base article to the `_posts` folder but it doesn't appear on the website:

* Check the `_site` folder to see if the article was converted to HTML. 
* Ensure that the file name starts with a date in `YYYY-MM-DD` format.
* Ensure that words in the file name are separated with hyphens, *not* underscores.
* Ensure that the YAML front matter is correct.

### Useful links

* [Intro to Jekyll](http://jekyllbootstrap.com/lessons/jekyll-introduction.html#toc_0)
* [Liquid template info](https://github.com/Shopify/liquid/wiki/Liquid-for-Designers)
* [Jekyll without plugins](http://captnemo.in/blog/2014/01/20/pluginless-jekyll/)
* [Markdown help](http://stackoverflow.com/editing-help)
* [YAML sample](http://en.wikipedia.org/wiki/YAML#Sample_document)
* [Liquid cheatsheet](http://cheat.markdunkley.com/)
* [Some Jekyll plugins](https://github.com/recurser/jekyll-plugins)
* [HTML-to-Markdown converter](http://domchristie.github.io/to-markdown/): Useful for converting Tips & Tricks articles to Markdown (not perfect, but decent) 
* [Markdown-Writer for Atom](https://atom.io/packages/markdown-writer): Might be useful for tag management/consistency
* [Sort and filter in Jekyll](http://www.leveluplunch.com/blog/2014/04/03/sort-pages-by-title-filter-array-by-layout-jekyllrb/): Describes how to sort pages by weight and filter them by layout (this is the method used to generate the sidebar menu)
* [Advanced Jekyll features](http://www.divshot.com/blog/web-development/advanced-jekyll-features/)

## Workflow

* Editors can commit directly on master of the online docs repository. 
* Content can be reviewed and approved on the [jekyll.xebialabs.com](http://jekyll.xebialabs.com) site which refreshes automatically (via [Documentation/Jekyll docs](https://dexter.xebialabs.com/jenkinsng/job/Documentation/job/Jekyll%20docs/) job).
* On demand, content can be pushed out to the production site (e.g. via Jenkins) _not implemented yet_.

## Known issues

### No in-manual TOC

There is no TOC generated within each manual (such as `xl-deploy/plugins/command-plugin/4.0.x/`).

Possible solution is to copy the code that generates it on the current site. Could float it to the right, like we currently do (to avoid collision with the sidebar).

### Plugins list on product version page assumes versions match

The list of plugins on product version pages (such as `xl-deploy/4.0.x/`):

1. Cycles through every plugin for that product
2. Lists the ones that have a version that matches the product version

For example, the plugins would be listed on `xl-deploy/4.5.x/` would all be version 4.5.x. The Liquid code (in `includes/xl_deploy_plugins_list_per_version.html`) assumes that a product and a plugin are compatible if their versions match, and are *not* compatible if their versions do not match.

Some workaround ideas:

| Workaround | Pluses | Minuses |
| ---- | ---- | ---- |
| Don't list plugins on the version page at all | Easy | Not user-friendly; loss of functionality compared to the current site |
| Manually list the plugins on the product version page (basically, what we do now) | Total control of which plugin versions are listed for each product version | Manual work must be done for every product release |
| Create a compatibility matrix behind the scenes and use Liquid or Ruby to select the right plugins for each product version | Theoretically future-proof | Difficult to develop |
| Create a compatibility matrix that is visible to users and let them choose the plugin version(s) they need | Relatively easy | We would really need to test every plugin+product combination |

### Limit on articles on KB splash page is not intelligent

The number of articles in each category on `knowledge-base/index.html` is limited by the `limit` filter on the `for` loop. This limit isn't "intelligent" because Liquid doesn't know the number of articles in each category beforehand. This means that:

* The "Read more" link at the bottom of each category list will always appear, *even if* the number of articles in a category is *less than or equal to* the limit
* We can't publish an article count next to each "Read more" link

A Ruby plugin is probably required to improve this functionality (because you need to use a counter for each category).

Side note: The limit on KB articles that are listed on the product splash pages (such as `xl-deploy/index.html`) is intelligent, because Liquid knows that it should only count posts where the category matches the product ID of that index page (so, `xl-deploy`). On these pages, the "Read more" link only appears if the number of articles for the product is greater than the limit, and we can show the total number of articles for the product.

### Markdown files containing Liquid not converted correctly

When a Markdown file contains some Liquid logic (such as a `for` loop or an `if` statement), Jekyll may not convert it to HTML correctly. This is probably because it interprets indentation of lines of Liquid code as text that should be formatted as a code sample.

The solution is to change the file from Markdown to HTML. This is why most of the index files in our setup are called `index.html`, not `index.markdown`.

### Manual anchors prevent correct Markdown parsing

Jekyll does not correctly parse Markdown if an anchor is manually placed before a heading, as we have done in a few manuals (upgrade manual, Overthere manual, XLR manuals). For example, this Markdown:

      <a name="upgrade_to_450"></a>
      ### Upgrading to XL Deploy 4.5.0 ###

Results in this line appearing in the HTML file (in body text, not as a heading):

      ### Upgrading to XL Deploy 4.5.0 ###

The solution is to **not** create anchors manually. Let Bootstrap create them based on the heading text.

## To do

* Limit the list of KB articles, similar to the way they are limited on the product splash pages:
     * Per category on the KB splash page (XL Deploy, XL Release, etc.)
     * Per tag on the KB tags page (API, xl-deploy-4.0.x, etc.)
* Decide how to handle plugin compatibility (e.g. Plugin A 4.0.x is compatible with both Product B 4.0.x and Product B 4.5.x).
* See if current KB tag formatting is okay and, if not, figure out how to mask them.
