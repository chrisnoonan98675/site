---
title: Create a provisioning package
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- template
- cloud
since:
- XL Deploy 6.0.0
weight: 152
---

In XL Deploy, a _provisioning package_ is a collection of:

* Provisionables, which contain settings that are needed to provision a cloud-based environment
* Provisioners, which execute actions in the environment after it is set up
* Templates, which create configuration items (CIs) in XL Deploy during the provisioning process

For example, a provisioning package could contain:

* A provisionable that creates an Amazon Web Services EC2 instance (`aws.ec2.InstanceSpec`)
* A Puppet provisioner that installs Apache Tomcat on the instance (`puppet.provisioner.Manifest`)
* Templates that create an SSH host CI (`template.overthere.SshHost`), a Tomcat server CI (`template.tomcat.Server`), and a Tomcat virtual host CI (`template.tomcat.VirtualHost`)

The process of creating a provisioning package is the same as the process of [creating a deployment package](/xl-deploy/how-to/add-a-package-to-xl-deploy.html); you will first create an application (`udm.Application`) CI and then create packages (`udm.deploymentPackage`) that represent versions of the provisioning configuration.

**Note:** A version of this topic is available for [XL Deploy 5.5.x](/xl-deploy/5.5.x/create-a-provisioning-package-5.5.html).

## Step 1 Create an application

To create an application:

1. Click **Repository** in the top bar.
1. Right-click **Applications** and select **New** > **Application**.
1. In the **Name** box, enter a unique name for the application.
1. Click **Save**.

## Step 2 Create a provisioning package

To create a provisioning package:

1. In the Repository, right-click the application, and select **New** > **Deployment Package**.
1. In the **Name** box, enter a unique name for the provisioning package.
1. Go to the **Provisioning** tab.
1. In the **Environment Name** box, enter the XL Deploy environment that should contain the CIs that are generated during provisioning. This field cannot contain forward slashes (`/`).
1. In the **Directory Path** box, enter the location in the repository where the environment and the CIs that are created should be saved. Omit root nodes such as `Environments`.
1. Click **Save**.

### Specifying an environment name and path

During provisioning, XL Deploy automatically creates an environment that will contain the CIs that are generated based on [bound templates](/xl-deploy/concept/provisioning-and-ci-templates.html). You specify the environment name on the provisioning package or in the [deployment properties](/xl-deploy/how-to/provision-an-environment.html) when you set up a provisioning. You can also specify a directory path where the environment and infrastructure CIs should be saved.

**Important:** The directory structure must already exist under the **Environments** and **Infrastructure** root nodes.

During provisioning, XL Deploy creates the environment. To prevent name collisions, the [unique provisioning ID](/xl-deploy/how-to/provision-an-environment.html#the-unique-provisioning-id) will be added to the environment name that you specify. The CIs that are generated during provisioning are assigned to the environment.

You can see the environment and CIs that will be created by previewing the provisioning plan.

## Step 3 Add a provisionable to a package

To add a provisionable to a provisioning package:

1. In the Repository, right-click the desired provisioning package, select **New**, then select the type of provisionable that you want to add. For example, to add an Amazon Web Services EC2 AMI, select **aws** > **ec2.InstanceSpec**.
1. Fill in the provisionable properties. For example, for an `ec2.instanceSpec`:

    ![Create new provisionable (aws.ec2.InstanceSpec)](images/provisioning-create-new-provisionable-02.png)

1. If you want to generate more than one provisioned based on this provisionable, go to the **Provisioning** tab and enter the number of provisioneds in the **Cardinality** box (default is 1).
1. Click **Save**.

#### Cardinality in provisionables

Cardinality allows you to create multiple provisioneds based on a single provisionable. For example, an `aws.ec2.InstanceSpec` with a cardinality of 5 will result in five AWS EC2 instances, all based on the same instance specification. When each provisioned is created, its ordinal will be added to its name, as described in [Provision an environment](/xl-deploy/how-to/provision-an-environment.html#the-unique-provisioning-id).

It is recommended that you use a [placeholder](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) such as `{% raw %}NUMBER_OF_TOMCAT_INSTANCES{% endraw %}` for the cardinality property. You can then enter the desired number of instances when you set up the provisioning.

### Add a provisioner to a provisionable

You can optionally add a provisioner (such as Puppet) to a provisionable:

1. In the Repository, right-click the desired provisionable, select **New**, then select the type of provisioner that you want to add. For example, to add a Puppet manifest, select **Manifest**.
1. Fill in the configuration for the provisioner.
1. Click **Save**.

## Step 4 Add a template to a package

To add a template to a provisioning package:

1. In the Repository, right-click the desired provisioning package, select **New** > **Template**, then select the type of template that you want to add.

    For example, if the provisioning package includes a `puppet.Manifest` provisioner, you will need a host template such as `template.overthere.SshHost` for the manifest to use. Also, if the provisioning will install an Apache Tomcat server, you can select templates such as `template.tomcat.Server` and `template.tomcat.VirtualHost`.

1. Fill in the configuration for the template.
1. Click **Save**.
