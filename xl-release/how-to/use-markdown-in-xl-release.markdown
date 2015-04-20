---
title: Use Markdown in XL Release
categories:
- xl-release
subject:
- Tasks
tags:
- task
- markdown
---

XL Release allows your to style your text using Markdown syntax. Markdown is a way to indicate headers, hyperlink, italics, and so on in your text. In XL Release, markdown is supported in task descriptions and comments.

This is a brief overview of the most common Markdown commands.

## Headers

When you start a line with one or more `#` characters, it becomes a header.

	# Header 1

	## Header 2

	###### Header 6
	
## Hyperlinks

The simplest way to create a clickable hyperlink is to enter a web site address starting with `http://` or `https://`.

    Visit our documentation at https://docs.xebialabs.com/xl-release/

You can also create a link text as follows:

    Please visit [the XebiaLabs website](http://xebialabs.com/)
    
## Bold and italic

To italicize text, surround it with a single underscore (`_`). For bold, use a double asterisk (`*`).

    Choose between **bold** and _italic_.
    
## Lists

To create a bulleted list, begin each list item with an asterisk (`*`) followed by a space.

    Bulleted list:
    
    * One item
    * Another item
    
To create a numbered list, begin each list item with a number and a period (`.`). The numbering does not have to be exact, Markdown will calculate the proper order for you.

    Numbered list:
    
    1. First item
    2. Second item
    10. Last item
    
## More information

Markdown has more options. For a full explanation, refer to [the Markdown guide by John Gruber](http://daringfireball.net/projects/markdown/syntax).
