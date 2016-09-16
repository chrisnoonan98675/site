---
title: Provisioning through XL Deploy (XL Deploy 5.5.x)
since:
- XL Deploy 5.5.0
removed:
- XL Deploy 6.0.0
---

XL Deploy's provisioning feature allows you to provide fully automated, on-demand access to your public, private, or hybrid cloud-based environments. With provisioning, you can:

* Create an environment in a single action
* Track and audit environments that are created through XL Deploy
* Deprovision environments created through XL Deploy
* Extend XL Deploy to create environments using technologies not supported by default

**Note:** A version of this topic is available for [XL Deploy 6.0.0 and later](/xl-deploy/concept/provisioning-through-xl-deploy.html).

## Key provisioning concepts

### Blueprints and provisioning packages

Provisioning starts with a *blueprint*, which defines what you want to provision. *Provisioning packages* represent specific versions of a blueprint.

A provisioning package contains *provisionables*, which are the details that are needed to set up the environment. For example, in the case of [Amazon Elastic Compute Cloud (EC2)](https://aws.amazon.com/ec2/), a provisionable would specify the Amazon Machine Image (AMI) to set up and the region and security group to use for it.

A provisionable can contain *provisioners* that define actions to take after the environment is set up. For example, a [Puppet](https://puppet.com/) provisioner can apply a Puppet manifest that installs [Apache HTTP Server](https://httpd.apache.org/) on the AMI.

In addition to provisionables, a provisioning package can contain *templates* that define items to use in the provisioned environment. For example, an `overthere.SshHost` template creates an SSH host that can be used for the Puppet manifest. Also, you can use templates as *bound templates*, which means that XL Deploy will automatically create configuration items (CIs) based on them and assign those CIs to an [environment](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html). This allows you to immediately use those CIs in deployments.

### Provisioning environments and providers

You provision packages to *provisioning environments*, which are logical groupings of *providers*. A provider is a cloud technology such as Amazon EC2; it contains required connection information such as an access key ID and secret access key.

### Provisioned blueprints and provisioneds

When you map a provisioning package to a provisioning environment, XL Deploy creates a *provisioned blueprint* that contains *provisioneds*. These are the actual properties, manifests, scripts, and so on that XL Deploy will use to provision the environment.

## Supported provisioning technologies

Support for provisioning technologies is provided through plugins. To see the provisioning plugins that are available, refer to the version of XL Deploy that you are using (for example, [XL Deploy 5.5.x](/xl-deploy/5.5.x)).

## Get started with provisioning

To get started with XL Deploy provisioning:

1. [Upgrade to XL Deploy 5.5.0 or later](/xl-deploy/5.5.x/releasemanual.html).
1. [Create a blueprint and a provisioning package](/xl-deploy/how-to/create-a-provisioning-package.html).
1. [Create a provisioning environment](/xl-deploy/how-to/create-a-provisioning-environment.html) and a [provider](/xl-deploy/how-to/create-a-provider.html).
1. [Provision the environment](/xl-deploy/how-to/provision-an-environment.html).
1. [Deploy to the environment](/xl-deploy/how-to/deploy-to-a-provisioned-environment.html).
1. [Deprovision the environment](/xl-deploy/how-to/deprovision-an-environment.html).

## Limitations and known issues

* The provisioning feature currently uses an internal API. A public API will be available in a future release.
* It may take one minute or longer to generate a provisioning plan preview if the plan includes many provisioneds.
* When creating an `aws.ec2.InstanceSpec` configuration item, you can only enter an AWS security group that already exists. To use a new security group, you must first create it manually in AWS.
* It is currently not possible to automatically purge provisioning packages according to a [retention policy](/xl-deploy/how-to/automatically-purge-packages-according-to-a-user-defined-policy.html).
* The [CLI provisioning extension](/xl-deploy/how-to/using-the-xl-deploy-cli-provisioning-extension.html) does not currently include help.
* In the Provisioning Workspace:
    * Provisioning is limited to a single tab.
    * Occasionally, the Provisioning Environments list may be empty. To correct this issue, clear your browser cache and refresh the screen, or use a different browser.
* In the Repository:
    * When creating an XL Deploy environment (`udm.Environment`), providers erroneously appear in the Containers list
    * When adding deployables to a deployment package, the `aws.ec2.InstanceSpec` CI erroneously appears as an option
* In [reports](/xl-deploy/how-to/using-xl-deploy-reports.html):
    * Provisioning and unprovisioning actions appear on the Deployments tab
    * Provisioning environments are listed on the Deployed Applications tab
