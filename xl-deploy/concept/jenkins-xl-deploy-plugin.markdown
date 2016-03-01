---
title: Introduction to the Jenkins XL Deploy plugin
categories:
- xl-deploy
subject:
- Jenkins plugin
tags:
- jenkins
- package
- deployment
---

The XL Deploy plugin for [Jenkins CI](/xl-deploy/how-to/create-a-deployment-package-using-jenkins.html) adds three post-build actions that you can use independently or together:

* Package an application
* Publish a deployment package to XL Deploy
* Deploy an application

For more information about using the plugin, refer to:

* [Create a deployment package using Jenkins](/xl-deploy/how-to/create-a-deployment-package-using-jenkins.html)
* [The XL Deploy plugin on the Jenkins wiki](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin)

## Features

* Deploy package (DAR) packaging:
    * With artifacts created by the Jenkins job
    * With other artifacts or resources
* Publish DAR packages to XL Deploy
* Trigger deployments in XL Deploy
* Auto-scale deployments to modified environments
* Execute on Microsoft Windows or Unix slave nodes
