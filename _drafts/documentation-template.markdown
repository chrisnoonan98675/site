---
title: Documentation template
categories:
- 
subject:
- 
tags:
-
since:
-   
---

## File name

The file name format is `your-file-name-here.markdown`. Do not use underscores, spaces, or uppercase letters in the file name.

## Front matter

The front matter consists of keys and values. You can specify them in any order. Do not change the keys.

| Key | Required? | More than one value allowed? | Description | Capitalization | Spaces allowed? | Example |
| --- | --------- | ---------------------------- | ----------- | -------------- | --------------- | ------- |
| title | Yes | No | The title of the page. | Sentence case | Yes | Create a new role |
| categories | Yes | Yes | The product(s) that the page applies to. | Lowercase | No | xl-deploy, xl-release |
| subject | Yes | No | The subject is like a high-level tag. It helps users who are used to reading documentation in manuals. | Sentence case | Yes | Security |
| tags | Yes | Yes | Tags help users browse posts and improve search results. Try to use tags that are already in use. You can add new tags, but do so with caution. | Lowercase | Yes | role, permissions, user management |
| layout | No | No | If this is beta documentation, set the layout to <code>beta</code>. If the topic concerns pre-rules customization (that is, via Java-based plugins), set the layout to <code>pre-rules</code>. | Lowercase | No | beta |
| since | No | No | This is the version in which the functionality described in the page was introduced. It isn't required, but it is recommended. | Not applicable | Not applicable | XL Deploy 3.9.x |
| deprecated | No | No | This is the version in which the functionality described in the page was deprecated. | Not applicable | Not applicable | XL Release 4.6.0 |
| removed | No | No | This is the version in which the functionality described in the page was removed. | Not applicable | Not applicable | XL TestView 1.2.0 |

**Tip:** To see the subjects that are in use, go to [https://docs.xebialabs.com/tags-and-subjects.html](https://docs.xebialabs.com/tags-and-subjects.html).

## Formatting the file

Refer to the `readme` of the `online-docs-jekyll` repository to see guidelines for formatting within the file.
