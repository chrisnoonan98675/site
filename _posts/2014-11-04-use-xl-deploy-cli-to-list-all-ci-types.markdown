---
title: Using the XL Deploy CLI to list all CI types
categories:
- xl-deploy
tags:
- cli
- ci
---

There are several sources of configuration item (CI) types in XL Deploy. Out of the box, it comes with some types pre-defined and the plugins enabled in your installation of the tool also add new types. How do you find out which CI types are available in a running instance of XL Deploy?

To do this, you need to use some XL Deploy command-line interface (CLI) plumbing. In addition to the helper objects that are available in the CLI by default (such as `deployit` and `repository`), it is possible to enable use of the underlying proxy objects that use REST to talk to the server. For normal use of the CLI, these proxies are not needed, but they can be handy if you need to access some lower-level information.

To enable use of these proxies, start the CLI with the flag `-expose-proxies`. A new helper object called proxies is now available in the CLI. You can use the following code to list all CI types:

    for i in proxies.descriptors.list().entity.descriptors: print i.simpleName

It is also possible to get the documentation for each of the types:

    for i in proxies.descriptors.list().entity.descriptors: deployit.describe(i.simpleName)

As of Deployit 3.1, this information is available via a regular CLI call, so you don't need to access the proxies anymore.
