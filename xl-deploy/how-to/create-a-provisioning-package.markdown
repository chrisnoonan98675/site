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
1. Expand **Blueprints**, right-click the desired blueprint, and select **New** > **Provisioning Package**.
1. In the **Name** box, enter a unique name for the provisioning package.
1. In the **Environment Id** box, enter an XL Deploy environment that should contain the CIs that XL Deploy creates based on the templates in the package.

    For example, a package could contain templates that create `overthere.SshHost`, `tomcat.Server`, and `tomcat.VirtualHost` CIs. XL Deploy can assign these CIs to an environment so you can immediately use them in a deployment.

    ![Create new provisioning package](images/provisioning-create-new-provisioning-package.png)

1. Click **Save**.

## Add a provisionable to a package

To add a provisionable to a provisioning package:

1. Click **Repository** in the top bar.
1. Expand **Blueprints**, then expand the desired blueprint to see its provisioning packages.
1. Right-click the desired provisioning package, select **New**, then select the type of provisionable that you want to add. For example, to add an Amazon Web Services EC2 AMI, select **aws** > **ec2.InstanceSpec**.
1. Fill in the configuration for the provisionable.

    ![Create new provisionable (aws.ec2.InstanceSpec)](images/provisioning-create-new-provisionable.png)

1. Click **Save**.

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
1. Right-click the desired provisioning package, select **New** > **Template**, then select the type of template that you want to add. For example, if your provisioning package will result in the installation of an Apache Tomcat server, you can automatically create a Tomcat virtual host CI by selecting **tomcat.VirtualHost**.
1. Fill in the configuration for the template.

    ![Create new template (template.overthere.SshHost)](images/provisioning-create-new-template.png)

1. Click **Save**.

## Next steps

After you create a provisioning package and add provisionables, providers, and templates, you can [provision the package to a provisioning environment](/xl-deploy/how-to/provision-an-environment.html).
