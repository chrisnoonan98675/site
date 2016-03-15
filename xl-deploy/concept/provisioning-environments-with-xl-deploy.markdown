---
layout: beta-noindex
title: Provisioning environments with XL Deploy
since:
- XL Deploy 5.5.0
---

XL Deploy's provisioning feature allows you to provide fully automated, on-demand access to your public, private, or hybrid cloud-based environments. With provisioning, you can:

* Create an environment in a single action
* Track and audit environments that are created through XL Deploy
* Deprovision environments created through XL Deploy
* Extend XL Deploy to create environments using technologies not supported by default

## Key provisioning concepts

Provisioning starts with a *blueprint* that defines the environment that you want to create and what that environment should look like.

Each blueprint contains *provisioning packages* that represent specific versions of the blueprint. A provisioning package contains *provisionables*, which are the details that are needed to set up the environment. For example, in the case of Amazon EC2, a provisionable would specify the AMI to set up and the region and security group to use.

A provisionable can contain *provisioners* that define actions to take after the environment is set up, such as running a Puppet script to install Apache Tomcat.

In addition to provisionables, a provisioning package can contain *templates*. Templates define configuration items (CIs) that XL Deploy should create based on the results of the provisioners. For example, after a Puppet script installs Apache Tomcat, XL Deploy can automatically create `overthere.SshHost`, `tomcat.Server`, and `tomcat.VirtualHost` CIs with the right properties, as well as an XL Deploy environment that contains them. This allows you to immediately use these CIs in deployments.

In addition to blueprints and provisioning packages, you need to define *ecosystems*, which are logical groupings of *providers*. A provider is a set of credentials needed to connect to a cloud technology such as Amazon EC2.

When you map a provisioning package to an ecosystem, XL Deploy creates a *provisioned blueprint* that contains *provisioneds*. These are the actual properties, manifests, scripts, and so on that XL Deploy will use to provision the environment.

## Provisioning example

***Add an example with screenshots of the repository and a mapping***

## Get started with provisioning

To get started with XL Deploy provisioning:

1. [Upgrade to XL Deploy 5.5.0 or later](/xl-deploy/5.5.x/releasemanual.html).
1. [Create a blueprint and a provisioning package](/xl-deploy/how-to/create-a-provisioning-package.html).
1. [Create an ecosystem and a provider](/xl-deploy/how-to/create-an-ecosystem.html).
1. [Provision the environment and deploy to it](/xl-deploy/how-to/provision-an-environment.html).
1. [Deprovision the environment]().

## Limitations

When creating an Amazon EC2 AMI (`aws.ec2.AMI`) configuration item, you can only enter an AWS security group that already exists. To use a new security group, you must first create it manually in AWS.

## Checkist

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

* Troubleshooting cloud functionality
* What about:
    * Cloud + staging
    * Cloud + satellites
    * Cloud + plugins (w/ examples)
    * Cloud + rules (w/ examples)

There are demo plugins for the rules and UI extension features in the `XLDEPLOY_HOME/samples` directory. It would be nice to provide something here for cloud.
