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

In XL Deploy, a provisioning package is a collection of:

* _Provisionables_ that contain settings that are needed to provision the environment
* _Provisioners_ that execute actions in the environment after it is set up
* _Templates_ that create configuration items (CIs) during the provisioning process

For example, a provisioning package could contain:

* A provisionable that creates an [Amazon Web Services EC2](https://aws.amazon.com/ec2/) instance (`aws.ec2.InstanceSpec`)
* A [Puppet](https://puppet.com/) provisioner that installs [Apache HTTP Server](https://httpd.apache.org/) on the instance (`puppet.provisioner.Manifest`)
* Templates that create an SSH host CI (`template.overthere.SshHost`), a Tomcat server CI (`template.tomcat.Server`), and a Tomcat virtual host CI (`template.tomcat.VirtualHost`)

The process of provisioning a cloud-based environment through XL Deploy is very similar to the process of deploying an application. You start by creating an _application_ (`udm.Application`) that defines the environment that you want to provision. You then create _provisioning packages_ (`udm.ProvisioningPackage`) that represent specific versions of the environment definition.

**Note:** A version of this topic is available for [XL Deploy 5.5.x](/xl-deploy/5.5.x/create-a-provisioning-package-5.5.html).

## Step 1 Create an application

To create an application:

1. Click **Repository** in the top bar.
1. Right-click **Applications** and select **New** > **Application**.
1. In the **Name** box, enter a unique name for the application.
1. Click **Save**.

## Step 2 Create a provisioning package

To create a provisioning package:

1. Right-click the application and select **New** > **Provisioning Package**.
1. In the **Name** box, enter a unique name for the provisioning package.
1. Click **Save**.

## Step 3 Add a provisionable to a package

To add a provisionable to a provisioning package:

1. Right-click the provisioning package, select **New**, then select the type of provisionable that you want to add. For example, to add an Amazon Web Services EC2 AMI, select **aws** > **ec2.InstanceSpec**.
1. Fill in the provisionable properties. For example, for an `ec2.instanceSpec`:

    ![Create new provisionable (aws.ec2.InstanceSpec)](images/provisioning-create-new-provisionable-02.png)

1. Click **Save**.

{% comment %}
#### Cardinality in provisionables

Cardinality allows you to create multiple provisioneds based on a single provisionable. For example, an `aws.ec2.InstanceSpec` with a cardinality of 5 will result in five AWS EC2 instances, all based on the same instance specification. When each provisioned is created, its ordinal will be added to its name, as described in [Provision an environment](/xl-deploy/how-to/provision-an-environment.html#the-unique-provisioning-id).

It is recommended that you use a [placeholder](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) such as `{% raw %}NUMBER_OF_TOMCAT_INSTANCES{% endraw %}` for the cardinality property. You can then enter the desired number of instances when you set up the provisioning.
{% endcomment %}

### Add a provisioner to a provisionable

You can optionally add a provisioner (such as Puppet) to a provisionable:

1. Right-click the provisionable, select **New**, then select the type of provisioner that you want to add. For example, to add a Puppet manifest, select **provisioner.Manifest**.
1. Fill in the configuration for the provisioner.
1. Click **Save**.

## Step 4 Add a template to a package

To add a template to a provisioning package:

1. Right-click the provisioning package, select **New** > **Template**, then select the type of template that you want to add.

    For example, if the provisioning package includes a `puppet.Manifest` provisioner, you will need a host template such as `template.overthere.SshHost` for the manifest to use. Also, if the provisioning will install an Apache Tomcat server, you can select templates such as `template.tomcat.Server` and `template.tomcat.VirtualHost`.

1. Fill in the configuration for the template.
1. Click **Save**.

### Add a child template

To add a template as a _child_ of another template, right-click the _parent_ template CI and select **New** > **Template**.

For example, if you have a `puppet.Manifest` provisioner that will install an [Apache Tomcat](/xl-deploy/concept/tomcat-plugin.html#tomcat-topology) server on an `overthere.SshHost`, you would:

* Add a `template.tomcat.Server` CI as a child of the `template.overthere.SshHost` CI
* Add a `template.tomcat.VirtualHost` CI as a child of the `template.tomcat.Server` CI

This will give you a structure like:

![Hierarchy of CI templates](images/provisioning-template-hierarchy.png)

### Adding a template as a bound template

XL Deploy will create a CI based on this template in the provisioned environment. If you want to use the CI after provisioning is complete, you must add it to the provisionable as a _bound template_. This means it will be saved in the Repository and added to the environment. To add a bound template:

1. Double-click the provisionable to open it.
2. Under **Bound Templates**, select the template and click ![Add template](/images/button_add_container.png) to add it to the **Members** list.
3. Click **Save**.

### Specifying an environment name and path

When you provision a package to an environment, XL Deploy can generate CIs based on [bound templates](/xl-deploy/concept/provisioning-and-ci-templates.html) and add them to the environment. XL Deploy stores the generated CIs in the location you specify in the environment's **Directory Path** property (located on the **Provisioning** tab).

For example,

You specify the directory where XL Deploy should store the generated CIs on the environment

During provisioning, XL Deploy automatically creates an environment that will contain the CIs that are generated based on [bound templates](/xl-deploy/concept/provisioning-and-ci-templates.html). You specify the environment name on the provisioning package or in the [deployment properties](/xl-deploy/how-to/provision-an-environment.html) when you set up a provisioning. You can also specify a directory path where the environment and infrastructure CIs should be saved.

**Important:** The directory structure must already exist under the **Environments** and **Infrastructure** root nodes.

During provisioning, XL Deploy creates the environment. To prevent name collisions, the [unique provisioning ID](/xl-deploy/how-to/provision-an-environment.html#the-unique-provisioning-id) will be added to the environment name that you specify. The CIs that are generated during provisioning are assigned to the environment.

You can see the environment and CIs that will be created by previewing the provisioning plan.
