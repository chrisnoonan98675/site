---
title: Migrate to XL Deploy 5.1.0
categories:
- xl-deploy
subject:
- Upgrade
tags:
- migration
- upgrade
- installation
- system administration
since:
- 5.1.0
---

With the release of XL Deploy 5.1.0, a number of features have changed. This guide will help you to adapt your configuration or customizations accordingly.

## Upgrade from Jython 2.5 to Jython 2.7

XL Deploy 5.1.0 supports features that area specific to Python 2.7 in all extension points where Jython can be used, including plugins, rules, user interface extensions, and so on.

That means that all existing Jython code must be compatible with Jython 2.7 to work within XL Deploy 5.1.0. Use of Jython 2.5 with XL Deploy 5.1.0 is not supported.

Because Jython 2.7 is mostly backward-compatible with 2.5, this should not have a big impact. However, there are some areas that may need to be addressed:

* Retrieving a nonexistent key from a dictionary results in `KeyError` instead of a `None` value. It is recommended that you use the `in` operator to first check the key exists in the dictionary.
* In some cases, the import syntax has changed slightly.

For more information about upgrading from Jython 2.5 and 2.7 and an explanation of new features, please refer to:

* [Jython 2.7 release notes](https://hg.python.org/jython/file/412a8f9445f7/NEWS) 
* [Jython website](http://www.jython.org)
* [Python 2.7 documentation](https://www.python.org/download/releases/2.7/)

## Upgrade from Spring Security 3.x to 4.x

Spring Security was upgraded from version 3.2.5 to 4.0.1. Some deprecated API was removed in the 4.0 version, so please check the [Spring Security 3.x to 4.x](http://docs.spring.io/spring-security/site/migrate/current/3-to-4/html5/migrate-3-to-4-xml.html) migration guide to see if you need to update `conf/deployit-security.xml` file.

## Deprecation of composite packages

... placeholder as a reminder, we probably need to fill this in later

