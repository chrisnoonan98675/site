online-docs-jekyll
==================

## How the site works

### Logical structure of the site

* Landing page (index.html)
    * Product
        * Product documentation for specific version
            * Category
                * Topic
                * Topic
                * Topic
            * Category
                * Topic
                * Topic
                * Topic
        * Knowledge base articles about the product
            * Article
            * Article
            * Article
    * Knowledge base (contains articles for all products)
        * Article
        * Article
        * Article

### Product documentation vs. knowledge base

In Jekyll terminology:

* Product documentation consists of *pages* that are located in `products`
* Knowledge base articles are *posts* that are located in `_posts`

[This site](http://jekyllbootstrap.com/lessons/jekyll-introduction.html#toc_9) explains more about the difference between pages and posts.

### File names

Pages can be named however you want.

Posts must be named according to the convention `YEAR-MONTH-DAY-title.markdown`. Do not use underscores in post file names.

### File header

Every page and post must have a YAML header.

#### Pages

The only required variable in a page header is `title`. This is the title that will appear on the page, wrapped in `h1` tags. Example:

      ---
      title: My sample XL Deploy page
      ---

#### Posts

Post headers need a title, categories, and tags. The title will appear on the page, wrapped in `h1` tags. You can have one or more categories and tags. Example:

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

*Categories* can be used to form permalinks, while tags cannot.

Tags are case-sensitive and cannot contain spaces.

## Jekyll and Liquid

### Layout files

| Jekyll layout file   | Page(s) it applies to  | Why is this layout file needed? |
|----------------------|------------------------|---------------------------------|
| default.html | All | Master layout file that calls most of the `includes` that make up the page structure |
| list-in-sidebar.html | Landing page of a product or of the KB | Triggers Jekyll to add a link to the page in the sidebar |
| post.html | All posts | Includes static breadcrumbs and a "Related posts" section |
| page.html | All pages | Includes dynamically generated breadcrumbs | 

### Plugins

| Plugin file name | Description | Source | License |
| ---------------- | ----------- | ------ | ------- |
| `breadcrumbs.rb` | Creates dynamic breadcrumbs on pages | [Source](http://biosphere.cc/software-engineering/jekyll-breadcrumbs-navigation-plugin/) | None, apparently |

### Tips

* To start Jekyll in watch mode, use `jekyll server --watch`. If you change `_config.yml`, you have to stop the server (CTRL+C) and restart it.
* Jekyll runs locally at `localhost:4000`.
* If a folder does not contain an `index.markdown` or `index.html` file, trying to access it will return a 404 error. It's a good idea to always put an index file in each folder or to use `.htaccess` or another method to redirect users to a useful page.
* If you combine conditions in a loop, the order of the conditions matters.

If you add a knowledge base article to the `_posts` folder but it doesn't appear on the website:

* Check the `_site/knowledge-base` folder to see if the article was converted to HTML. 
* Ensure that the file name starts with a date in `YYYY-MM-DD` format.
* Ensure that words in the file name are separated with hyphens, *not* underscores.
* Ensure that the YAML header is correct.

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

## Markdown warning!

Jekyll does not correctly parse Markdown if an anchor is manually placed before a heading, as we have done in a few manuals (upgrade manual, Overthere manual, XLR manuals). For example, this Markdown:

      <a name="upgrade_to_450"></a>
      ### Upgrading to XL Deploy 4.5.0 ###

Results in this line appearing in the HTML file (in body text, not as a heading):

      ### Upgrading to XL Deploy 4.5.0 ###

The solution is to **not** create anchors manually. Let Bootstrap create them based on the heading text.

## Workflow

Editors can commit directly on master of the online docs repository. Content can be reviewed and approved on the `devdoc` site which refreshes automatically. On demand, content can be pushed out to the production site (e.g. via Jenkins).

## To do

* In Liquid: Limit the list of KB articles per category on the KB splash page (similar to the way they are limited on the product splash pages).
* **Before going live:** The `url` property in `_config.yml` has to be changed from `http://localhost:4000` to `http://docs.xebialabs.com`. This is the value of the `{{ site.url }}` variable.