---
layout: beta-noindex
title: Create a provisioning package
since:
- XL Deploy 5.5.0
---

In XL Deploy, a *provisioning package* represents a specific version of a *blueprint*. The package contains *provisionables*, which define the settings that are needed to set up the environment. A provisionable can contain *provisioners* that define actions to take after the environment is set up.

In addition to provisionables, a provisioning package can contain *templates* that define the configuration items (CIs) that XL Deploy should create based on the results of the provisioners.

## Create a blueprint

To create a blueprint:

1. Click **Repository** in the top bar.
1. Right-click **Blueprints** and select **New** > **upm** > **Blueprint**.
1. In the **Name** box, enter a unique name for the blueprint.

    ![Create new blueprint](images/provisioning-create-new-blueprint.png)

1. Click **Save**.

## Create a provisioning package

To create a provisioning package:

1. Click **Repository** in the top bar.
1. Expand **Blueprints**, right-click the desired blueprint, and select **New** > **ProvisioningPackage**.
1. In the **Name** box, enter a unique name for the provisioning package.
1. In the **Environment Name** box, enter an XL Deploy environment that should contain the CIs that are generated during provisioning. This field cannot contain forward slashes (`/`).

    ![Create new provisioning package](images/provisioning-create-new-provisioning-package.png)

1. In the **Directory Path** box, enter the location in the repository where the environment and the CIs that are created should be saved. Omit root nodes such as `Environments`.
1. Click **Save**.

### Specifying an environment name and path

During provisioning, XL Deploy can automatically create an environment (`udm.Environment`) that will contain the CIs that are generated based on [bound templates](/xl-deploy/concept/provisioning-and-ci-templates.html). You specify the environment name on the provisioning package or in the [provisioning properties](/xl-deploy/how-to/provision-an-environment.html).

You can also specify a directory path where the environment and infrastructure CIs should be saved.

**Important:** The directory structure must already exist under the **Environments** and **Infrastructure** root nodes.

During provisioning, XL Deploy creates the environment. A unique ID will automatically be added to the name that you specified, as described in [Provision an environment](/xl-deploy/how-to/provision-an-environment.html#the-unique-provisioning-id). The CIs that are generated during provisioning are assigned to the environment.

You can see the environment and CIs that will be created by previewing the provisioning plan:

![Preview provisioned CIs that will be created](images/provisioning-create-provisioned-cis.png)

## Add a provisionable to a package

To add a provisionable to a provisioning package:

1. Click **Repository** in the top bar.
1. Expand **Blueprints**, then expand the desired blueprint to see its provisioning packages.
1. Right-click the desired provisioning package, select **New**, then select the type of provisionable that you want to add. For example, to add an Amazon Web Services EC2 AMI, select **aws** > **ec2.InstanceSpec**.
1. In the **Cardinality** box, enter the number of provisioneds that should be created based on this provisionable (default is 1).

    ![Create new provisionable (aws.ec2.InstanceSpec)](images/provisioning-create-new-provisionable-01.png)

1. Fill in the rest of the provisionable properties. For example, for an `ec2.instanceSpec`:

    ![Create new provisionable (aws.ec2.InstanceSpec)](images/provisioning-create-new-provisionable-02.png)

1. Click **Save**.

#### Cardinality in provisionables

Cardinality allows you to create multiple provisioneds based on a single provisionable. For example, an `aws.ec2.InstanceSpec` with a cardinality of 5 will result in five Amazon EC2 instances, all based on the same instance specification. When each provisioned is created, its ordinal will be added to its name, as described in [Provision an environment](/xl-deploy/how-to/provision-an-environment.html#the-unique-provisioning-id).

It is recommended that you use a [placeholder](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) such as `{% raw %}NUMBER_OF_TOMCAT_INSTANCES{% endraw %}` for the cardinality property. You can then enter the number of instances in the provisioning properties when setting up the provisioning.

### Add a provisioner to a provisionable

To add a provisioner to a provisionable:

1. Click **Repository** in the top bar.
1. Expand **Blueprints**, then expand the desired blueprint to see its provisioning packages.
1. Expand the desired provisioning package to see its provisionables.
1. Right-click the desired provisionable, select **New**, then select the type of provisioner that you want to add. For example, to add a Puppet manifest, select **Manifest**.
1. Fill in the configuration for the provisioner.

    ![Create new provisioner (puppet.Manifest)](images/provisioning-create-new-provisioner.png)

1. Click **Save**.

## Add a template to a package

To add a template to a provisioning package:

1. Click **Repository** in the top bar.
1. Expand **Blueprints**, then expand the desired blueprint to see its provisioning packages.
1. Right-click the desired provisioning package, select **New** > **Template**, then select the type of template that you want to add.

    For example, if the provisioning package includes a `puppet.Manifest` provisioner, you will need a host template such as **overthere.SshHost** for the manifest to use. Also, if the provisioning will install an Apache Tomcat server, you can select templates such as **tomcat.Server** and **tomcat.VirtualHost**.

1. Fill in the configuration for the template.

    ![Create new template (template.overthere.SshHost)](images/provisioning-create-new-template.png)

1. Click **Save**.

## Next steps

After you create a provisioning package and add provisionables, providers, and templates, you can [provision the package to a provisioning environment](/xl-deploy/how-to/provision-an-environment.html).
