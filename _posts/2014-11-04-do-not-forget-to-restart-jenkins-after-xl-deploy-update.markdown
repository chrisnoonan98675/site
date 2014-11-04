---
title: Do not forget to restart Jenkins after an XL Deploy update
categories:
- xl-deploy
tags:
- integration
- jenkins
---

If you use the [XL Deploy Jenkins plugin](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin), you may run into issues when starting deployments from Jenkins. The plugin caches the types from XL Deploy when Jenkis starts up. So if you upgrade XL Deploy and do not restart Jenkins, you may see errors during deployments.
