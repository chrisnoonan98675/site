---
layout: beta-noindex
title: Provisioning environments with XL Deploy
since:
- XL Deploy 5.5.0
---

XL Deploy's provisioning feature allows you to provide fully automated, on-demand access to your cloud-based environments, whether they are public, private, or hybrid. With provisioning, you can:

* Create an environment in a single action
* Track and audit environments that are created through XL Deploy
* Deprovision environments created through XL Deploy
* Extend XL Deploy to create environments using technologies not supported by default

## Key provisioning concepts

To get started with XL Deploy provisioning, you create a *blueprint*, which defines the environment that you want to create and what that environment should look like.

A blueprint contains *provisioning packages*, which represent specific versions of the blueprint. A provisioning package contains *provisionables*, which are the details that are needed to set up the environment. For example, in the case of Amazon EC2, a provisionable would specify the AMI to set up and the region and security group to use.

A provisionable can contain *provisioners*; these define actions to take after the environment is set up, such as running a Puppet script to install Apache Tomcat.

In addition to provisionables, a provisioning package can contain *templates*. Templates define configuration items (CIs) that XL Deploy should create in its repository based on the provisioning results. For example, after the Puppet script installs Apache Tomcat, XL Deploy can automatically create `overthere.SshHost`, `tomcat.Server`, and `tomcat.VirtualHost` CIs with the right properties, as well as an XL Deploy environment that contains them. This allows you to immediately use those CIs in deployments.

You also need to define *ecosystems*, which are logical groupings of *providers*.

When you map a provisioning package to an ecosystem, XL Deploy creates a *provisioned blueprint* that contains *provisioneds*. These are the actual properties, manifests, scripts, and so on that XL Deploy will use to provision the environment.


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
