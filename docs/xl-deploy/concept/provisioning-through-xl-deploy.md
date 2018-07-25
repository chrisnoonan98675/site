---
title: Provisioning through XL Deploy
categories:
xl-deploy
subject:
Provisioning
tags:
provisioning
cloud
since:
XL Deploy 6.0.0
weight: 150
---

XL Deploy's provisioning feature allows you to provide fully automated, on-demand access to your public, private, or hybrid cloud-based environments. With provisioning, you can:

* Create an environment in a single action by provisioning infrastructure, installing middleware, and configuring infrastructure and middleware components
* Track and audit environments that are created through XL Deploy
* Deprovision environments created through XL Deploy
* Extend XL Deploy to create environments using technologies not supported by default

**Note:** A version of this topic is available for [XL Deploy 5.5.x](/xl-deploy/5.5.x/provisioning-through-xl-deploy-5.5.html).

## Provisioning packages

A provisioning package is a collection of:

* _Provisionables_ that contain settings that are needed to provision the environment
* _Provisioners_ that execute actions in the environment after it is set up
* _Templates_ that create configuration items (CIs) in XL Deploy during the provisioning process

For example, a provisioning package could contain:

* A provisionable that creates an [Amazon Web Services EC2](https://aws.amazon.com/ec2/) instance (`aws.ec2.InstanceSpec`)
* A [Puppet](https://puppet.com/) provisioner that installs [Apache HTTP Server](https://httpd.apache.org/) on the instance (`puppet.provisioner.Manifest`)
* Templates that create an SSH host CI (`template.overthere.SshHost`), a Tomcat server CI (`template.tomcat.Server`), and a Tomcat virtual host CI (`template.tomcat.VirtualHost`)

The process of provisioning a cloud-based environment through XL Deploy is very similar to the process of deploying an application. You start by creating an _application_ (`udm.Application`) that defines the environment that you want to provision. You then create _provisioning packages_ (`udm.ProvisioningPackage`) that represent specific versions of the environment definition.

## Providers

You can also define _providers_, which are cloud technologies such as [Amazon Web Services EC2](https://aws.amazon.com/ec2/) (`aws.ec2.Cloud`). A provider CI contains required connection information, such as an access key ID and a secret access key. You define provider CIs under **Infrastructure** in the XL Deploy Repository. After you define a provider, you add it to an _environment_ (`udm.Environment`).

### Provisioneds

After you have created packages and added providers to an environment, you start provisioning the same way you would [start a deployment](/xl-deploy/how-to/deploy-an-application.html). When you map a provisioning package to an environment, XL Deploy creates *provisioneds* based on the provisionables in the package. These are the actual properties, manifests, scripts, and so on that XL Deploy will use to provision the environment.

## Supported provisioning technologies

Support for provisioning technologies is provided through plugins. To see the provisioning plugins that are available, refer to the version of XL Deploy that you are using (for example, [XL Deploy 5.5.x](/xl-deploy/5.5.x)).

## Get started with provisioning

To get started with XL Deploy provisioning:

1. [Upgrade to XL Deploy 5.5.0 or later](/xl-deploy/5.5.x/releasemanual.html).
1. [Create a provisioning package](/xl-deploy/how-to/create-a-provisioning-package.html).
1. [Create a provider](/xl-deploy/how-to/create-a-provider.html) and [add it to an environment](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html).
1. [Provision the environment](/xl-deploy/how-to/provision-an-environment.html).
1. [Deploy to the environment](/xl-deploy/how-to/deploy-an-application.html).
1. [Deprovision the environment](/xl-deploy/how-to/undeploy-an-application.html).

## Limitations and known issues

* It may take one minute or longer to generate a provisioning plan preview if the plan includes many provisioneds.
* When creating an `aws.ec2.InstanceSpec` CI, you can only enter an AWS security group that already exists. To use a new security group, you must first create it manually in AWS.
* In [reports](/xl-deploy/how-to/using-xl-deploy-reports.html):
    * Provisioning and unprovisioning actions appear on the Deployments tab
    * Provisioning environments are listed on the Deployed Applications tab
