---
title: Cloud
categories:
- xl-deploy
subject:
- Cloud
tags:
- cloud
since:
- XL Deploy 5.5.0
---

This is a placeholder topic for information related to the XL Deploy 5.5.0 cloud functionality.

## Checkist

What does the documentation need to cover? Brainstorming:

* Cloud concepts
    * Terminology
    * Step-by-step process; what happens when I deploy to cloud? What steps does XLD take?
* How to get started with cloud
    * Do I need something special in my license? (like with satellite)
    * Do I need to install a plugin?
    * Maybe provide a sample package that they can deploy to a cloud environment, so they can quickly see it in action (after they set up the infra CIs)
* Set up cloud infrastructure
    * What CIs you need to create
    * What data you need to get from your cloud host (AMI IDs, etc.)
    * Do you still need to create an environment the normal way? Which CIs go in the environment? Can an environment contain a mix of cloud and non-cloud CIs?
* Create a provisioning package
* Deploy an application to a cloud environment
* Undeploy an application from a cloud environment?
    * How is the environment taken down?
* Limitations on cloud functionality
* Troubleshooting cloud functionality
* What about:
    * Cloud + staging
    * Cloud + satellites
    * Cloud + plugins (w/ examples)
    * Cloud + rules (w/ examples)

## Generated documentation

The XL Deploy (and maybe XL Platform) repositories need to be set up to generate reference docs for cloud:

* CI reference
* REST API reference
* Jython API reference
* Javadoc?

## Other ideas and reminders

* There are demo plugins for the rules and UI extension features in the `XLDEPLOY_HOME/samples` directory. It would be nice to provide something here for cloud.
* Important limitation to document: An already existing security group (for AMIs) needs to be selected. You need to create one manually if you want a new one. That is: we don't support the "create a new environment using AMIs you haven't use before" use case.
