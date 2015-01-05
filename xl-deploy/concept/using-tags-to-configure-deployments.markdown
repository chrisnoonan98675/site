---
title: Using tags to configure deployments
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- tag
---

Tags make it easier to configure deployments by marking which deployables should be mapped to which containers.

To perform a deployment using tags, specify tags on the deployables and containers. Tags can be specified either in the imported package or by using the repository browser. When deploying a package to an environment, XL Deploy will match the deployables and containers based on the following rules:

* Both of them have **no** tags.
* Either of them has a tag called '\*'.
* Either of them has a tag called '+' and the other has at least one tag.
* Both have at least one tag in common.

If none of these rules apply, XL Deploy will not generate a deployed for the deployable-container combination.

The following diagram shows which tags match when:

![Tag matching](images/tag-matching.png)

An example scenario is deploying a front-end and back-end application to two application servers. By tagging the front-end EAR and front-end application server both with 'FE' and the back-end EAR and back-end server with 'BE', XL Deploy will automatically create the correct deployeds.
