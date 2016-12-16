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
1. In the **Name** box, enter the provisioning package version.
1. Click **Save**.

## Step 3 Add a provisionable to a package

To add a provisionable to a provisioning package:

1. Right-click the provisioning package, select **New**, then select the type of provisionable that you want to add. For example, to add an Amazon Web Services EC2 AMI, select **aws** > **ec2.InstanceSpec**.
1. Fill in the provisionable properties. For example, some of the properties for an `aws.ec2.instanceSpec` are:

    ![Create new provisionable (aws.ec2.InstanceSpec)](images/provisioning-create-new-provisionable-02.png)

1. Click **Save**.

## Step 4 Add a template to a package

To add a template to a provisioning package:

1. Right-click the provisioning package, select **New** > **Template**, then select the type of template that you want to add.

    A template's type is the same as the type of CI it represents, with a `template.` prefix. For example, the template type that will create an `overthere.SshHost` CI is called `template.overthere.SshHost`.

1. Fill in the configuration for the template.

    Template properties are inherited from the original CI type, but simple property kinds are mapped to the `STRING` kind. This allows you to specify [placeholders](/xl-deploy/how-to/using-placeholders-with-provisioning.html) in template properties. XL Deploy resolves the placeholders when it instantiates a CI based on the template.

1. Click **Save**.

## Step 5 Add a template as a bound template

For XL Deploy to resolve a template and create a CI based on it, you must add the template as a *bound template* on a provisioning package (`udm.ProvisioningPackage`). You can use [*contextual placeholders*](/xl-deploy/how-to/use-provisioning-outputs.html) in the properties of templates.

### Storing generated CIs

CIs that are generated from bound templates are saved in the directory that you specify in the **Directory Path** property of the target environment; for example, `Cloud/EC2/Testing`.

**Important:** The directory that you specify must already exist under **Infrastructure** and/or **Environments** (for `udm.Dictionary` CIs).

### Naming generated CIs

The names of CIs that are generated based on templates follow this pattern:

    /Infrastructure/$DirectoryPath$/$ProvisioningId$-$rootTemplateName$/$templateName$

Where:

* The root (in this example, `/Infrastructure`) is based on the CI type. It can be any repository root name.
* `$DirectoryPath$` is the value specified in the **Directory Path** property of the target environment.
* `$ProvisioningId$` is the [unique provisioning ID](/xl-deploy/how-to/provision-an-environment.html#the-unique-provisioning-id) that XL Deploy generates.
* `$rootTemplateName$` is the name of the root template, if the template has a root template or is a root template.
* `$templateName$` is the name of the template when it is nested under a root template.

You can change this rule by specifying the optional **Instance Name** property on the template. The resulting ID would look like:

    /Infrastructure/$DirectoryPath$/$rootInstanceName$/$templateInstanceName$

### Creating a hierarchy of templates

Like other CIs, you can create a hierarchy of templates that have a parent-child relationship. You do so by right-clicking the parent CI and selecting **New** > **Template**. For example, this is a hierarchy of `template.overthere.SshHost`, `template.tomcat.Server`, and `template.tomcat.VirtualHost` CIs:

![Hierarchy of CI templates](images/provisioning-template-hierarchy.png)

In this case, you only need to specify the root (parent) of the hierarchy as a bound template. XL Deploy will automatically also create CIs based on the child templates.

## Step 6 Add a provisioner to a provisionable

You can optionally add a provisioner such as Puppet to a provisionable:

1. Right-click the provisionable, select **New**, then select the type of provisioner that you want to add. For example, to add a Puppet manifest, select **provisioner.Manifest**.
1. Fill in the configuration for the provisioner.
1. Click **Save**.

**Tip:** A provisioner must run on a host, so you should first create a host template (for example, `template.overthere.SshHost`), and then assign it to the provisioner.
