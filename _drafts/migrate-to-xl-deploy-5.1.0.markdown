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

With the release of XL Deploy 5.1.0, a number of features have changed. This guide will help you to adapt your configuration or customisations accordingly.

## Upgrade from Jython 2.5 to Jython 2.7
With this upgrade, we now support Python 2.7 specific language features in all our extension points where Jython can be used, including plugins, rules, UI extensions, etc.

That means that all existing Jython code needs to be compatible with 2.7 to work within XL Deploy 5.1. There is no way to use Jython 2.5 with XL Deploy 5.1.

Since 2.7 is mostly backwards compatible with 2.5, this should not have a big impact. Although we experienced a number of behavioral changes we needed to address in our own code:

* Retrieving a non existing key from a Dictionary results in a KeyError instead of a None value. We suggest checking if the key exists in the Dictionary first with the `in` operator.
* We noticed the import syntax in some cases is slightly different. 

For additional help with upgrading from Jython 2.5 and 2.7 and explanation of new features, please refer to:

* [Jython 2.7 release notes](https://hg.python.org/jython/file/412a8f9445f7/NEWS) 
* [The Jython website](http://www.jython.org)
* Since at the time of writing the Jython documentation of 2.7 is not yet published, [The Python 2.7 documentation](https://www.python.org/download/releases/2.7/)

## Spring Security 4
... placeholder as a reminder, we probably need to fill this in later

## Deprecation of Composite packages
... placeholder as a reminder, we probably need to fill this in later

