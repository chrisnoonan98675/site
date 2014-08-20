online-docs-jekyll
==================

# Logical structure of the site:

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

Jekyll layouts:

| Page | File name | Jekyll layout file | Why? |
| --- | --- | --- | --- |
| Default template for pages | any page |  default.html | This is defined in `_config.yml`. It means that you don't have to set `layout: default` in the `yaml` header of every page. |
| Landing page | index.markdown | landing_page.html | Among other things, the landing page doesn't need the `breadcrumbs.html` include. |

# Workflow

Editors can commit directly on master of the online docs repository. Content can be reviewed and approved on the `devdoc` site which refreshes automatically. On demand, content can be pushed out to the production site (e.g. via Jenkins).

# Useful links

* https://github.com/Shopify/liquid/wiki/Liquid-for-Designers
* http://stackoverflow.com/editing-help
* http://en.wikipedia.org/wiki/YAML#Sample_document

# To do

* Investigate this page about [advanced Jekyll features](http://www.divshot.com/blog/web-development/advanced-jekyll-features/) -- it looks like it tackles a lot of the things we want to do.
