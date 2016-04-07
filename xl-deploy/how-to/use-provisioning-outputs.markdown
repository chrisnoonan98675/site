---
layout: beta-noindex
title: Use provisioning outputs in templates and dictionaries
since:
- XL Deploy 5.5.0
---

In XL Deploy, a [*provisioning package*](/xl-deploy/how-to/create-a-provisioning-package.html) represents a specific version of a *blueprint*. The package contains *provisionables*, which define the settings that are needed to set up the environment. A provisionable can contain *provisioners* that define actions to take after the environment is set up.

In addition to provisionables, a provisioning package can contain *templates* that define the configuration items (CIs) that XL Deploy should create based on the results of the provisioners.

Some provisionables have *output properties* that XL Deploy will populate after provisioning is complete. For example, after you provision an Amazon EC2 AMI, the `aws.ec2.InstanceSpec` configuration item (CI) will contain its instance ID, public IP address, and public host name. You can see these values on the **Output** tab of the CI.

To refer to these output properties in templates and dictionaries, you use output property placeholders. The format for these placeholders is `{% raw %}{{% ... %}}{% endraw %}`.

**Tip:** To see a property's name as it should be specified in a placeholder, hover over the property on the **Output** tab.

## Sample provisioning output usage

Assume that you want to provision an Amazon EC2 AMI and then apply a [Puppet manifest](https://puppetlabs.com/) to it. The Puppet manifest requires a host, but you will not know the host's address until the AMI is provisioned.

To use a placeholder for the host address:

1. Click **Repository** in the top bar.
1. Expand **Blueprints**, then expand the desired blueprint.
1. Right-click the desired provisioning package and select **New** > **aws** > **ec2.InstanceSpec** to create an `aws.ec2.InstanceSpec` provisionable. Fill in the required properties and save the CI.
1. Right-click the provisioning package and select **New** > **template** > **overethere.SshHost** to create a `template.overthere.SshHost` template. Fill in the required properties, setting the **Address** property to `{% raw %}{{%publicHostname%}}{% raw %}`. Save the CI.
1. Right-click the `aws.ec2.InstanceSpec` CI and select **New** > **Manifest** to create a `puppet.Manifest` provisioner. Fill in the required properties, setting the **Host Template** property to the `template.overthere.SshHost` CI that you created. Save the CI.

    **Tip:** You can define Puppet modules on the Puppet manifest by right-clicking the `puppet.Manifest` CI that you created and selecting **New** > **ModuleSpec**.

1. [Provision the provisioning package to an ecosystem](/xl-deploy/how-to/provision-a-package-to-an-ecosystem.html) that contains an Amazon EC2 [provider](/xl-deploy/how-to/create-a-provider.html). XL Deploy will create an `overthere.SshHost` CI based on the template that you created. Its address will be the public host name of the provisioned AMI.
