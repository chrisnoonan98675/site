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
        * Knowledge base for product
            * Article
            * Article
            * Article
    * Information that applies to multiple products
        * Knowledge base for all products
            * Article
            * Article
            * Article

### Product documentation vs. knowledge base

In Jekyll terminology:

* Product documentation consists of *pages* that are located in `products`
* Knowledge base articles are *posts* that are located in `_posts`

### Categories and tags

*Categories* can be used in permalinks. *Tags* cannot. For example, to get a URL like `/knowledge-base/xl-deploy/foo/`, `xl-deploy` needs to be a category, not a tag.

Categories and tags are more important for *posts* than for *pages* because the category appears in the post's permalink. For example, in the case of a *page* with the URL `/products/xl-deploy/4.0/foobar.html`, `products`, `xl-deploy`, and `4.0` are all actually folders. But in the case of a *post* with the URL `/knowledge-base/xl-deploy/foobar/`, `knowledge-base` and `xl-deploy` are the categories defined in the header of `foobar.markdown`.

### Order of categories

The order of categories in the YAML header matters! For example, if the post `foo.markdown` has the header `categories: xl-deploy knowledge-base`, it will be published at `/xl-deploy/knowledge-base/foo/`. If it has the header `categories: knowledge-base xl-deploy`, it will be published at `/knowledge-base/xl-deploy/foo/`.

## Jekyll and Liquid

### Layout files

| Jekyll layout file   | Page(s) it applies to                               | Why is this layout file needed?                                                                                                                                                              |
|----------------------|-----------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| landing_page.html    | Landing page (index.markdown) of docs.xebialabs.com | This page shouldn't show breadcrumbs.                                                                                                                                                        |
| list-in-sidebar.html | Landing page (index.markdown) of a product          | This layout file triggers Jekyll to add a link to the page in the sidebar. The layout is actually the same as the default layout for all pages (in appearance).                              |
| default.html         | All other pages                                     | This is defined as the default layout for all pages (in _config.yml). This means that you don't have to put layout: default in the header of every page. (Maybe replace this with page.html) |
| post.html            | All posts                                           | This actually isn't in use yet, but it does include a loop for related posts, which could be handy in the future.                                                                            |

### Lessons learned

* If a folder does not contain an `index.markdown` or `index.html` file, trying to access it will return a 404 error. It's a good idea to always put an index file in each folder or to use `.htaccess` or another method to redirect users to a useful page.
* The order of conditions in a loop matters. For example, `{% for post in site.categories.xl-deploy and site.categories.knowledge-base%}` returns different results from `{% for post in site.categories.knowledge-base and site.categories.xl-deploy%}`.

### Useful links

* Intro to Jekyll: http://jekyllbootstrap.com/lessons/jekyll-introduction.html#toc_0
* Liquid template info: https://github.com/Shopify/liquid/wiki/Liquid-for-Designers
* Markdown help: http://stackoverflow.com/editing-help
* YAML sample: http://en.wikipedia.org/wiki/YAML#Sample_document
* Liquid cheatsheet: http://cheat.markdunkley.com/
* Some Jekyll plugins: https://github.com/recurser/jekyll-plugins
* HTML-to-Markdown converter: http://domchristie.github.io/to-markdown/

## Workflow

Editors can commit directly on master of the online docs repository. Content can be reviewed and approved on the `devdoc` site which refreshes automatically. On demand, content can be pushed out to the production site (e.g. via Jenkins).

## To do

* Figure out how to exclude the `knowledge-base` category list on the main knowledge base page (`http://localhost:4000/products/knowledge-base/index.html`).
* Investigate this page about [advanced Jekyll features](http://www.divshot.com/blog/web-development/advanced-jekyll-features/) -- it looks like it tackles a lot of the things we want to do.
* **Before going live:** The `url` property in `_config.yml` has to be changed from `http://localhost:4000` to `http://docs.xebialabs.com`. This is the value of the `{{ site.url }}` variable.