---
title: Use provisioning outputs in templates
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- template
- cloud
since:
- XL Deploy 5.5.0
weight: 154
---

In XL Deploy, a [*provisioning package*](/xl-deploy/how-to/create-a-provisioning-package.html) is a collection of:

* Provisionables, which contain settings that are needed to provision a cloud-based environment
* Provisioners, which execute actions in the environment after it is set up
* Templates, which create configuration items (CIs) in XL Deploy during the provisioning process

When you map a provisioning package to an environment, XL Deploy creates *provisioneds*. These are the actual properties, manifests, scripts, and so on that XL Deploy will use to provision the environment.

You may want to use a provisioned property such as the IP address or host name of a provisioned server in a template, but the property will not have a value until provisioning is done. XL Deploy allows you to use *contextual placeholders* for these types of properties. Contextual placeholders can be used for all properties of provisioneds. The format for contextual placeholders is `{% raw %}{{% ... %}}{% endraw %}`.

You can also use contextual placeholders for output properties of some CI types. XL Deploy can automatically populate output property values after provisioning is complete. For example, after you provision an [Amazon Elastic Compute Cloud (EC2)](https://aws.amazon.com/ec2/) AMI, the `aws.ec2.Instance` configuration item (CI) will contain its instance ID, public IP address, and public host name. Refer to the [AWS Plugin Reference](/xl-deploy-xld-aws-plugin/latest/awsPluginManual.html) for information about properties.

## Sample provisioning output usage

Say you want to provision an Amazon EC2 AMI and then apply a [Puppet manifest](https://puppetlabs.com/) to it. The Puppet manifest requires a host, but you will not know the host's address until the AMI is provisioned, so you need to use a contextual placeholder for it. To do so:

1. Click **Repository** in the top bar.
1. In XL Deploy 5.5.x, expand **Blueprints**, then expand the desired blueprint. In XL Deploy 6.0.0 and later, expand **Applications**, then expand the desired application.
1. Right-click the desired package and select **New** > **aws** > **ec2.InstanceSpec** to create an `aws.ec2.InstanceSpec` provisionable. Fill in the required properties and save the CI.

    ![Create new provisionable (aws.ec2.InstanceSpec)](images/provisioning-create-new-provisionable-02.png)

1. Right-click the package and select **New** > **template** > **overthere.SshHost** to create a `template.overthere.SshHost` CI. Fill in the required properties, setting the **Address** property to `{% raw %}{{%publicHostname%}}{% endraw %}`. Save the CI.

    ![Sample template.overthere.SshHost with contextual placeholder](images/provisioning-create-new-template.png)

1. Double-click the package and click the **Provisioning** tab. Under **Bound Templates**, add the `template.overthere.SshHost` CI to the **Members** list. This ensures that XL Deploy will save the generated `overthere.SshHost` CI in the Repository.

    ![Sample package with bound template](images/provisioning-add-bound-template.png)

1. Right-click the `aws.ec2.InstanceSpec` CI and select **New** > **Manifest** to create a `puppet.provisioner.Manifest` provisioner. Fill in the required properties, setting the **Host Template** property to the `template.overthere.SshHost` CI that you created. Save the CI.

    ![Sample puppet.provisioner.Manifest](images/provisioning-create-puppet-manifest.png)

1. Right-click the `puppet.provisioner.Manifest` CI and select **New** > **provisioner.ModuleSpec** to add Puppet modules to the manifest.

1. Double-click an environment that contains an Amazon EC2 [provider](/xl-deploy/how-to/create-a-provider.html) to edit it. In the **Directory Path** property on the **Provisioning** tab, enter the directory where XL Deploy should save the generated `overthere.SshHost` CI.

    ![Directory path property on a sample provider](images/provisioning-directory-path-on-provider.png)

    **Note:** The directory must already exist under **Infrastructure**.

1. [Provision the package to an environment](/xl-deploy/how-to/provision-an-environment.html) that contains an Amazon EC2 [provider](/xl-deploy/how-to/create-a-provider.html). During provisioning, XL Deploy will create an SSH host, using the public host name of the provisioned AMI as its address.
