---
title: Versioning deployment packages with Bamboo and Deployit
categories:
- xl-deploy
tags:
- package
- application
- bamboo
- maven
---

Many users use [Bamboo](https://www.atlassian.com/software/bamboo) in combination with build tools such as [Ant](https://ant.apache.org/) or [Maven](https://maven.apache.org/), producing a [deployment package](http://docs.xebialabs.com/releases/latest/deployit/packagingmanual.html) as part of the build job. The package is then published to a Deployit server using the *Publish a Deployment Archive to a Deployit Server* task, and then deployed to the first development or testing environment using the *Deploy an application with Deployit* task.

Two questions that frequently arise are:

* How to generate a new version of the deployment package for each build run, to avoid duplicate versions?
* How to reference this version in the Deploy task to ensure the correct application version is deployed?

**Note:** This method has been tested with [Deployit 3.9.0](http://docs.xebialabs.com/product-version.html#/deployit/3.9.x) and the [Bamboo Deployit plugin 3.9.0](https://marketplace.atlassian.com/plugins/com.xebialabs.deployit.plugin.bamboo-deployit-plugin).

## Bamboo variables

Bamboo's [build-specific variables](https://confluence.atlassian.com/display/BAMBOO/Bamboo+variables#Bamboovariables-Build-specificvariables) are an easy and convenient way to address both issues. Like most CI servers, Bamboo injects a number of "magic" variables into the execution context of your build, which you can reference from your Ant build script, Maven POM, and so on. `bamboo.buildNumber` and `bamboo.buildKey` are common choices, but you can use any value that uniquely identifies the build, such as a commit revision.

Your Maven POM can look like this, for example:

    <?xml version="1.0"?>
    <project>
        ...
        <version>3.0-${bamboo.buildNumber}</version>

You can then use the same reference to specify the package version to be deployed:

![Deployment configuration](/images/versioning-deployment-packages-with-bamboo/package-version.png)

Of course, you can still run your build outside Bamboo by specifying the appropriate variable; for example, by using `mvn ... -Dbamboo.buildKey=42` if using Maven. See the [Bamboo documentation](https://confluence.atlassian.com/display/BAMBOO/Bamboo+variables#Bamboovariables-Antexamples) for an Ant example.

By saving the package version to a file, [sharing that artifact](https://confluence.atlassian.com/display/BAMBOO/Configuring+artifact+sharing+between+jobs) with downstream stages and jobs and [injecting the version information](https://marketplace.atlassian.com/plugins/com.atlassian.bamboo.plugins.bamboo-variable-inject-plugin) into the job (Bamboo 5 and up), you can use the Deploy task to promote the package to downstream test, QA etc. environments.
