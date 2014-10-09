---
title: Getting started with XebiaLabs community development
categories:
- xl-platform
tags:
- community
- plugin
---

We're always happy when users are interested in and able to contribute back to the community. Many popular plugins and features have been created and shared in this way, and we're excited to hear you're thinking about contributing too!

In order to help get you started, here's a short guide to how contributing to the [community repository](https://github.com/xebialabs/community-plugins) generally works:

1. The usual starting point/prerequisite for contributing plugins is the XL Platform training, which introduces the relevant concepts of the APIs and includes various training exercises. The basic information is largely also described in the documentation (including a [plugin sample](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#sample-java-plugin)), but is presented there more in a "reference format".

      The training, on the other hand, is specifically geared to the kind of "how to" scenarios that will help you get started.

1. Any build framework could be used (we use [Gradle](http://www.gradle.org/) internally), but the community repository uses [Maven](https://maven.apache.org/) by default as that's still the most common build system we're seeing. An XL Platform plugin project is developed using a local checkout of the community repository, which is set up to reference a local server installation of, for example, [XL Deploy](https://github.com/xebialabs/community-plugins/blob/master/project/pom.xml#L10). This provides the correct versions of the library components.

      If you're concerned about licensing issues: no worries, since the XL Platform license model doesn’t count the number of server installations.
 
1. The easiest way to start a new plugin project, in our experience, is to make a copy of a similar existing project and modify it as required. Projects can be built individually, so you typically only compile your current project. We recommend using [rpm-plugin](https://github.com/xebialabs/community-plugins/tree/master/deployit-udm-plugins/utility-plugins/rpm-plugin) as a starting point for simple, XML-only plugins, and [restrict-placeholders-to-dicts-plugin](https://github.com/xebialabs/community-plugins/tree/master/deployit-udm-plugins/utility-plugins/restrict-placeholders-to-dicts-plugin) for more adventurous stuff.

      Obviously, the XebiaLabs team is more than happy to suggest a good starting point if you have a specific plan and would like a hint!
 
1. If you are able to contribute the new plugin back to the community (which is obviously always very welcome!), we go through the standard [GitHub pull request process](https://help.github.com/articles/using-pull-requests), during we which we collaboratively review and, if possible, improve the submission before merging it into the repository. See [this pull request](https://github.com/xebialabs/community-plugins/pull/21) against [liquibase-plugin](https://github.com/xebialabs/community-plugins/tree/master/deployit-udm-plugins/middleware-plugins/liquibase-plugin) for an example.
 
1. We typically make a first release of the new plugin as part of the merge, and updated releases based on subsequent pull requests and discussion with the "champions" of the plugin.
