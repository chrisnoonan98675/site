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

* Product
    * Documentation in legacy format
    * Documentation in topic-based format
    * Auto-generated documentation
    * Plugin
        * Documentation in legacy format
        * Documentation in topic-based format
* Tips & Tricks blog
    * Blog posts

## How the site works

### Product documentation vs. blog

In Jekyll terminology:

* Product documentation consists of *pages* that are located in `xl-deploy`, `xl-release`, etc.
* Blog posts are *posts* that are located in `_posts`

[This site](http://jekyllbootstrap.com/lessons/jekyll-introduction.html#toc_9) explains the difference between pages and posts.

### Folders

The purpose of some folders might not be clear if you're not familiar with Jekyll, so here are some explanations:

| Folder | Purpose |
| ------ | ------- |
| `_plugins` | Contains Jekyll plugins |
| `plugins` | Makes Jekyll generate a page at `http://docs.xebialabs.com/plugins/`. This folder does not contain Jekyll plugins nor XebiaLabs plugin documentation. |
| `_posts` | Contains blog posts |
| `images` | Contains images for the site itself (like product logos) *and* images for blog posts (subject to change) |
| `tips-and-tricks` | Makes Jekyll generate a page at `http://docs.xebialabs.com/tips-and-tricks/`. This folder does not contain blog posts (those are in `_posts`). |
| `sample-scripts` | Contains sample files for blog posts. Currently, you would attach these to Tips & Tricks posts (see [here](https://support.xebialabs.com/entries/59035095-Using-rules-to-interact-with-WebSphere-cluster-members) for an example) |
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

### Gravatars

If an author is identified in the front matter of a post *and* there is an email address for that person defined in `_data/authors.yml`, Jekyll will automatically look for a [Gravatar](https://en.gravatar.com/) based on that email address.

If there is not a Gravatar for that email address, the default Gravatar image will appear next to the author's name.

If an author is not defined for a post, that part of the post's metadata won't appear.

## Jekyll and Liquid

### Layout files

| Jekyll layout file   | Page(s) it applies to  | Why is this layout file needed? |
|----------------------|------------------------|---------------------------------|
| `default.html` | All | Master layout file that calls most of the `includes` that make up the page structure |
| `list-in-sidebar.html` | Landing page of a product or of the KB | Triggers Jekyll to add a link to the page in the sidebar |
| `post.html` | All posts | Includes author, post date, and a "Related posts" section |
| `page.html` | All pages | Includes dynamically generated breadcrumbs | 

### Plugins and customizations

| Plugin file name | Description | Source | License |
| ---------------- | ----------- | ------ | ------- |
| `breadcrumbs.rb` | Creates dynamic breadcrumbs on pages | [Source](http://biosphere.cc/software-engineering/jekyll-breadcrumbs-navigation-plugin/) | None |
| `asciidoc_plugin.rb` | Enables Jekyll to interpret Asciidoc files | [Source](https://github.com/asciidoctor/jekyll-asciidoc) | MIT |
| `tag_gen.rb` | Generates an index page in the `_site/tag` folder for every tag on a post | [Source](http://charliepark.org/tags-in-jekyll/) | None |
| `to_gravatar.rb` | Gets a Gravatar based on the email address of the post's author | Sources: [1](http://blog.sorryapp.com/blogging-with-jekyll/2014/02/06/adding-authors-to-your-jekyll-site.html), [2](http://blog.sorryapp.com/blogging-with-jekyll/2014/02/13/add-author-gravatars-to-your-jekyll-site.html) | None |

### Tips

* To start Jekyll in watch mode, use `jekyll server --watch`. If you change `_config.yml`, you have to stop the server (CTRL+C) and restart it.
* Jekyll runs locally at `localhost:4000`.
* If a folder does not contain an `index.markdown` or `index.html` file, trying to access it will return a 404 error. It's a good idea to always put an index file in each folder or to use `.htaccess` or another method to redirect users to a useful page.
* If you combine conditions in a loop, the order of the conditions matters.
* In gradle, when deploying the content, we use `jekyll build --config "_config.yml,_jekyll.xebialabs.config.yml"` in order to override base URL, which is taken from the second configuration file.

### Troubleshooting

If you add a post to the `_posts` folder but it doesn't appear on the website:

* Check the `_site` folder to see if it was converted to HTML. 
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

## Writing blog posts in Jekyll (WIP)

This section is a work in progress. Please don't add blog posts to Jekyll yet!

### Add yourself as an author

If you have never written a blog post in Jekyll before, you must first add an entry for yourself in `_data/authors.yml`. Follow the same format that the other entries use; spacing, punctuation, and capitalization are important!

Jekyll will use the email address that you enter to look up your [Gravatar](https://en.gravatar.com/). The email address will not be visible to website visitors.

### Write a blog post

To write a blog post:

1. Copy `_drafts/2014-12-01-blog-post-template.markdown` to `_posts` and rename it with the current date and the title of the post.
2. Open the Markdown file and update:

    * The title
    * The author
    * The category(ies)
    * The tag(s)

3. Write the content of the post.

    * If you need to add images, store them in `images`.
    * If you need to add files that people can download (such as a sample Python script), store them in `sample-scripts`.

4. Start Jekyll locally, go to `http://localhost:4000/tips-and-tricks`, and review your post.
5. If you want to:

    * Publish the post immediately, commit the change to the repository
    * Save the post without publishing it yet, move it to the `_drafts` folder and commit the change to the repository

### Publish a blog post

To publish a blog post, move the Markdown file from `_drafts` to `_posts` and commit the change to the repository.

### Remove a blog post

To remove a blog post, delete the Markdown file from `_posts` or move it to `_drafts` and commit the change to the repository.
