---
layout: beta-noindex
title: Use provisioning outputs in templates and dictionaries
since:
- XL Deploy 5.5.0
---

In XL Deploy, a [*provisioning package*](/xl-deploy/how-to/create-a-provisioning-package.html) represents a specific version of a *blueprint*. The package contains *provisionables*, which define the settings that are needed to set up the environment. A provisionable can contain *provisioners* that define actions to take after the environment is set up. In addition to provisionables, a provisioning package can contain *templates* that define the configuration items (CIs) that XL Deploy should create based on the results of the provisioners.

When you map a provisioning package to a *provisioning environment*, XL Deploy creates a *provisioned blueprint* that contains *provisioneds*. These are the actual properties, manifests, scripts, and so on that XL Deploy will use to provision the environment.

You may want to use a provisioned property such as the IP address or host name of a provisioned server in a template or dictionary, but the property will not have a value until provisioning is done. XL Deploy allows you to use *contextual placeholders* for these types of properties. Contextual placeholders can be used for all properties of provisioneds. The format for contextual placeholders is `{% raw %}{{% ... %}}{% endraw %}`.

You can also use contextual placeholders for output properties of some CI types. XL Deploy can automatically populate output property values after provisioning is complete. For example, after you provision an [Amazon Elastic Compute Cloud (EC2)](https://aws.amazon.com/ec2/) AMI, the `aws.ec2.InstanceSpec` configuration item (CI) will contain its instance ID, public IP address, and public host name. You can see these values on the **Output** tab of the CI.

**Tip:** To see a property's name as it should be specified in a contextual placeholder, hover over the property on the **Output** tab.

## Sample provisioning output usage

Say you want to provision an Amazon EC2 AMI and then apply a [Puppet manifest](https://puppetlabs.com/) to it. The Puppet manifest requires a host, but you will not know the host's address until the AMI is provisioned, so you need to use a contextual placeholder for it. To do so:

1. Click **Repository** in the top bar.
1. Expand **Blueprints**, then expand the desired blueprint.
1. Right-click the desired provisioning package and select **New** > **aws** > **ec2.InstanceSpec** to create an `aws.ec2.InstanceSpec` provisionable. Fill in the required properties and save the CI.
1. Right-click the provisioning package and select **New** > **template** > **overethere.SshHost** to create a `template.overthere.SshHost` template. Fill in the required properties, setting the **Address** property to `{% raw %}{{%publicHostname%}}{% endraw %}`. Save the CI.

    ![Sample template with contextual placeholder](images/provisioning-create-new-template.png)

1. Right-click the `aws.ec2.InstanceSpec` CI and select **New** > **Manifest** to create a `puppet.Manifest` provisioner. Fill in the required properties, setting the **Host Template** property to the `template.overthere.SshHost` CI that you created. Save the CI.

    **Tip:** You can define Puppet modules on the manifest by right-clicking the `puppet.Manifest` CI that you created and selecting **New** > **ModuleSpec**.

1. [Provision the package to an environment](/xl-deploy/how-to/provision-an-environment.html) that contains an Amazon EC2 [provider](/xl-deploy/how-to/create-a-provider.html). During provisioning, XL Deploy will create an SSH host, using the public host name of the provisioned AMI as its address.

You could also have XL Deploy save the SSH host as an `overthere.SshHost` CI in its repository by adding the `template.overthere.SshHost` CI to the list of **Bound Templates** on the provisioning package or on a provisionable. Bound templates that are selected in a provisioning package are automatically added to the [XL Deploy environment](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html) that you specify.
