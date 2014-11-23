---
title: Blog post template
author: your_name
categories:
- 
tags:
- 
---

## File name

Name your file in this format: `YYYY-MM-DD-your-post-title-here.markdown`

* The date is the date that you will publish the post
* The text must be all lowercase
* The text will be the HTML file name (without the date)
* Do not use underscores in the file name

## Title

Set `title` to the title of the blog post. Don't retype the title in the body of the post.

## Author

Set `author` to your author ID, as defined in `_data/authors.yml`. See the readme file for instructions on adding yourself to `_data/authors.yml`.

## Categories

Categories correspond to products. For example, a blog post about XL Deploy should have the category `xl-deploy`. For a complete list of the categories that are available, start Jekyll locally and go to `http://localhost:4000/utilities/`.

Note: Do not change the word `categories` to `category`, even if you only enter one category.

## Tags

Tags help users browse posts and improves search results. For a complete list of the tags that are available, start Jekyll locally and go to `http://localhost:4000/utilities/`.

Note: Do not change the word `tags` to `tag`, even if you only enter one tag.

## Using XL-style placeholders

Liquid uses double curly brackets for variables, like we do for placeholders. That means that Jekyll will **not** render XL-style placeholders unless you surround them with the Liquid `{% raw %}` tag. For example:

    Use the {% raw %}{{ foo }}{% endraw %} placeholder...
