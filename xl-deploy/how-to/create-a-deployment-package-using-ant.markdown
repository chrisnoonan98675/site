---
title: Create a deployment package using Ant
subject:
- Ant
categories:
- xl-deploy
tags:
- package
- application
---

Creating a XL Deploy package via Ant is possible using the `jar` task.

* Create a manifest file conforming to the XL Deploy manifest standard
* Create a directory structure containing the files as they should appear in the package

In the Ant build file, include a `jar` task invocation as follows:

    <jar destfile="package.jar"
        basedir="."
        includes="**/*"/>
