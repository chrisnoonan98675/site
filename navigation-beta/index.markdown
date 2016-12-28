---
no_index: true
beta: true
title: Navigation beta
---

This page exists to gather feedback on a new way of organizing and navigating the technical documentation. Dive in and try it, or scroll down to read some background information.

## Try it out

To get started, expand items in the menu on the left!

### General questions

* How inconvenient would it be for you — and for the users you know — if some existing URLs started returning 404 errors?
* If you followed an old link that returned a 404 error, would this type of navigation would help you find the information that you were looking for?

### Navigation questions

While looking at the menu, please consider:

* Does the organization of menu and submenu items make sense to you?
    * Are menu items too granular? Not granular enough?
    * Does the order of menu items seem logical?
* Would a menu like this help you navigate the documentation site?

### Page questions

Locate the following pages:

* For XL Release: **Working with phases and tasks**
* For XL Deploy: **Using placeholders and dictionaries**
* For XL TestView: **Create custom reports**

Please consider:

* Could you easily find these pages in the sidebar menu?
* Can you navigate within the page itself?
* Do you have a sense of where you are within the structure of the page?
* Is the table of contents (at the top right of the page) useful? Is it too granular?
* Does the URL make sense?

### Where to send your feedback

Send your comments to Amy at [**ajohnston@xebialabs.com**](mailto:ajohnston@xebialabs.com)!

## Background information

In December 2014, we launched a new version of the documentation site, powered by [Jekyll](https://jekyllrb.com) and based on [topics](http://techwhirl.com/getting-started-with-topic-based-writing/) instead of manuals. In the subsequent two years, we've learned that while this implementation provides many benefits, there's still room to improve.

For inspiration, I've been looking at the documentation of some technologies that are familiar to many of our users. If you take a close look at the docs for [Docker](https://docs.docker.com/), [Puppet](https://docs.puppet.com/pe/latest/overview_about_pe.html), and [Chef](https://docs.chef.io/), you'll notice some commonalities:

* In the sidebar, the top-level menu items are usually products or components, along with some "high profile" items such as release notes.
* When you drill down into the sidebar submenus, you see a mix of conceptual and how-to documentation. There is usually some type of introductory, overview, or getting-started page near the top of the submenu.
* When you visit a specific page of the documentation, the page's table of contents appears on the right side.
* Generally, pages are fairly long and cover multiple concepts or tasks (for example, see [Organizations and Teams in Docker Cloud](https://docs.docker.com/docker-cloud/orgs/)). However, some specialized pages are short (for example, see [Leveraging multi-CPU architecture support](https://docs.docker.com/docker-for-mac/multi-arch/)).

Note: The documentation for [Serena Deployment Automation](http://help.serena.com/doc_center/sra/ver6_1_3/sda_help/help.html) has a similar setup, but to me, the look and feel is outdated compared to the documentation for Docker, Puppet, and Chef.

In contrast, check out the [documentation for UrbanCode](https://developer.ibm.com/urbancode/documents/). There are many categories and tags to explore, there are some options to sort the documentation, and there is a search box at the top of the index page. These are great tools if you're a knowledgeable user and/or if you're searching for a unique term (think "unified deployment model" vs. "deploy"). However, they're less useful for new users; the sheer number of topics can be overwhelming when you don't know where to begin.

Unfortunately, I think our current doc site is more similar to UrbanCode's site than to Docker/Puppet/Chef. So I spent a few Tech Rallies working on the way our technical documentation is organized. I combined many topics into larger pages — not rewriting them, but combining things that logically go together. I built a sidebar menu based on this organization, which you can see on this page. **It isn't 100% complete**; this is just a prototype to get your feedback. Most links don't contain any documentation yet.

Please take a look, think about the questions above, and drop me an email with your feedback at [ajohnston@xebialabs.com](mailto:ajohnston@xebialabs.com).

— Amy
