---
layout: beta-noindex
title: Using the XL Deploy CLI provisioning extension
since:
- XL Deploy 5.5.0
---

XL Deploy 5.5.0 introduced support for provisioning environments, which means that you can use the XL Deploy GUI and CLI to provision environments and to deploy applications to them. This topic describes how to use the provisioning extension for the XL Deploy CLI.

## Prerequisites

To use the XL Deploy CLI for provisioning:

1. [Upgrade](/xl-deploy/how-to/upgrade-xl-deploy.html) to XL Deploy 5.5.0 or later.
1. Ensure that the [XL Deploy CLI](/xl-deploy/how-to/install-the-xl-deploy-cli.html) is installed.
1. Download the XL Deploy Provisioning plugin and EC2 plugin from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com) and copy them to the `XLDEPLOY_HOME/plugins` directory (where `XLDEPLOY_HOME` is the directory where XL Deploy is installed).

    **Tip:** XL Deploy supports several cloud providers; this topic shows how to use the Amazon EC2 plugin, but you can use the same approach for other providers.

## Step 1 Install the XL Deploy CLI provisioning extension

Download the XL Deploy CLI provisioning extension (`xld-provision-cli.jar`) from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com) and copy it to `XLDEPLOY_CLI_HOME/plugins`.

## Step 2 Launch the XL Deploy CLI

Open a terminal window or command prompt and go to the `XLDEPLOY_CLI_HOME/bin` directory (where `XLDEPLOY_CLI_HOME` is the directory where the CLI is installed). Execute the start command:

* Unix-based operating systems: `./cli.sh`
* Microsoft Windows: `cli.cmd`

For example:

{% highlight bash %}
$ sh bin/cli.sh
{% endhighlight %}

You will be asked to provide a user name and password to use when connecting to the XL Deploy instance.

For more information about using the CLI, refer to:

* [Connect to XL Deploy from the CLI](/xl-deploy/how-to/connect-to-xl-deploy-from-the-cli.html)
* [Using the XL Deploy CLI](/xl-deploy/how-to/using-the-xl-deploy-cli.html)

## Step 3 Import the provisioning module

After you log in to the XL Deploy CLI, import the provisioning module. This will give you access to the provisioning API.

This shows how to import the `Provisioner` class from the `provisioner` module and then create the `provisioner` object:

{% highlight python %}
from provision import Provisioner
provisioner = Provisioner()
{% endhighlight %}

You can then perform following operations using the `provisioner` object:

{% highlight python %}
provisioner.initialProvisioning()
provisioner.validate()
provisioner.createProvisioningTask()
provisioner.mapAllProvisionables()
provisioner.mapSelectedProvisionables()
provisioner.preview()
provisioner.deprovision()
{% endhighlight %}

## Step 4 Create a provisioning package

Before you can provision an environment, you need to import a provisioning package. The examples below show how to create configuration items (CIs) that are required for provisioning.

First, create a blueprint. This is a logical grouping of provisioning packages.

{% highlight python %}
bp = repository.create(factory.configurationItem("Blueprints/EC2AMIs", "upm.Blueprint"))
{% endhighlight %}

After the blueprint is created, use it to create a `ProvisioningPackage`.

{% highlight python %}
pp = repository.create(factory.configurationItem(bp.id + "/1.0", "upm.ProvisioningPackage", {"blueprint":bp.id }))
{% endhighlight %}

A `ProvisioningPackage` consists of a `Provisionable` and a `Template`.

{% highlight python %}
template = repository.create(factory.configurationItem(pp.id + "/ubuntu-host", "template.overthere.SshHost", {
  "instanceId":"Infrastructure/ubuntu",
  "address":"{{%publicHostname%}}",
  "username":"ubuntu",
  "sudoUsername":"root",
  "connectionType":"SUDO",
  "os":"UNIX",
  "privateKeyFile":"PATH_TO_SSH_KEY_FILE"
}))
{% endhighlight %}

Next, create a `upm.Provisionable` for EC2 AMI.

{% highlight python %}
params = {"provisioners" :[],"cardinality": "1","boundTemplates" : [template.id],"amiId":"amiId"}
ami = repository.create(factory.configurationItem(pp.id + "/ubuntu", "aws.ec2.InstanceSpec",params))
{% endhighlight %}

## Step 5 Create a provisioning environment

A provisioning environment groups providers and dictionaries and serves as a target for provisioning. Start by creating a provider.

{% highlight python %}
params = {"accesskey":"EC2_ACCESS_KEY","accessSecret":"EC2_ACCESS_SECRET"}
provider = repository.create(factory.configurationItem("Providers/EC2Provider", "aws.ec2.Cloud", params))
{% endhighlight %}

Next, create a provisioning environment using the provider.

{% highlight python %}
params = {"providers":[provider.id]}
provisioningEnvironment = repository.create(factory.configurationItem("ProvisioningEnvironments/test-environment", "upm.ProvisioningEnvironment", params))
{% endhighlight %}

## Step 6 Perform the initial provisioning

Now that you have a provisioning package and an environment, you can perform the initial provisioning. To do so, use the `initial_provisioning` method. It requires a provisioning package ID and a provisioning environment ID.

{% highlight python %}
provisioner = Provisioner()
provisioning = provisioner.initialProvisioning(packageId="Blueprints/EC2AMIs/1.0", provisioningEnvironmentId="ProvisioningEnvironments/test-environment")
provisioning.provisionedBlueprint.environmentName = "env"
{% endhighlight %}

{% highlight python %}
admin > print provisioning
{u'provisionedBlueprint': {u'provisionedEnvironment': {u'type': u'udm.Environment', u'id': u'Environments/env-wdt6cP'}, u'optimizePlan': True, u'provisioningId': u'wdt6cP', u'type': u'upm.ProvisionedBlueprint', u'orchestrator': [u'provisioning'], u'provisioningPackage': {u'type': u'upm.ProvisioningPackage', u'id': u'Blueprints/EC2AMIs/1.0'}, u'environmentName': u'env', u'provisioningEnvironment': {u'type': u'upm.ProvisioningEnvironment', u'id': u'ProvisioningEnvironments/test-environment'}, u'id': u'ProvisioningEnvironments/test-environment/wdt6cP-EC2AMIs'}, u'provisioneds': [{u'type': u'aws.ec2.Instance', u'provisionable': {u'type': u'aws.ec2.InstanceSpec', u'id': u'Blueprints/EC2AMIs/1.0/ubuntu'}, u'provider': {u'type': u'aws.ec2.Cloud', u'id': u'Providers/EC2Provider'}, u'id': u'Providers/EC2Provider/wdt6cP-ubuntu', u'instanceBootRetryCount': 5, u'instanceType': u'm1.small', u'keyName': u'shekhar-xl', u'securityGroup': u'default', u'amiId': u'ami-d91be1ae', u'region': u'eu-west-1', u'ordinal': 1}]}
{% endhighlight %}

You can access specific fields using the dot notation as shown below.

{% highlight python %}
admin > provisioning.provisionedBlueprint.provisionedEnvironment.id
Environments/env-wdt6cP
{% endhighlight %}

### Perform the initial provisioning with unresolved placeholders

If you are using placeholders such as `{% raw %}{{PLACEHOLDER_NAME}}{% endraw %}` in your provisioning package and these placeholders are not specified in a `upm.Dictionary` that is attached to the `upm.ProvisioningEnvironment`, the list of unresolved placeholders is reported in `provisioning` object by path `provisioning.provisionedBlueprint.unresolvedPlaceholders`.

To provide values for unresolved placeholders, execute `initialProvisioning` again.

{% highlight python %}
provisioning = provisioner.initialProvisioning(packageId="Blueprints/EC2AMIs/1.0", provisioningEnvironmentId="ProvisioningEnvironments/test-environment", placeholders={'PLACEHOLDER_NAME': 'VALUE'})
{% endhighlight %}

## Step 7 Validate provisioning

After the initial `provisioning` object is created, it is validated. Validation errors are reported in `provisioning.validationErrors` as an array. If an array is not set, there are no validation errors. To revalidate a modified `provisioning` object, call the `validate` method.

{% highlight python %}
admin > del provisioning.provisionedBlueprint['environmentName'] #delete required property
admin > provisioning = provisioner.validate(provisioning)
admin > print provisioning.validationErrors
[{u'message': u'Value is required', u'reference': u'ProvisioningEnvironments/test-environment/wdt6cP-EC2AMIs', u'propertyName': u'environmentName'}]
{% endhighlight %}

## Step 8 Map provisionables

After the `provisioning` object has been validated, you must map the provisionables specified in the `upm.ProvisioningPackage` to providers specified in the `upm.ProvisioningEnvironment`.

To automatically map all provisionables, call:

{% highlight python %}
provisioning = provisioner.mapAllProvisionables(provisioning)
{% endhighlight %}

To specify the provisionables to map, call:

{% highlight python %}
provisioning = provisioner.mapSelectedProvisionables(['Blueprints/EC2AMIs/1.0/ubuntu'], provisioning)
{% endhighlight %}

## Step 9 Preview provisioning task

The CLI provisioning extension allows you to preview the provisioning plan that XL Deploy generated based on the provisioning configuration. To view the plan, you can use the `preview` method of `provisioner` object as shown below.

{% highlight python %}
task_preview = provisioner.preview(provisioning)
{% endhighlight %}

You can also preview a step by passing it a blockId and step number as shown below.

{% highlight python %}
step_preview = provisioner.preview(provisioning, "0_1_1_1", "1")
{% endhighlight %}

## Step 10 Invoke the provisioning task

After you perform the initial provisioning, you can request that XL Deploy create a provisioning task.

{% highlight python %}
task = provisioner.createProvisioningTask(p)
{% endhighlight %}

This will create a task. You can view the task ID as shown below.

{% highlight python %}
admin > task.id
577c51e2-1225-41cb-9a6d-8f44829d407a
{% endhighlight %}

## Step 9 Start and wait for provisioning to finish

After the task is created, you can use the `deployit` object to start it and wait for it to finish.

{% highlight python %}
deployit.startTaskAndWait(task.id)
{% endhighlight %}

After the task finishes successfully, you will have a new environment provisioned.

## Step 10 Deprovision the environment

To deprovision the created environment, you can use the `deprovision` method, passing it the `id` of the `upm.ProvisionedBlueprint` you want to deprovision. This will destroy the environment and all related configuration items.

{% highlight python %}
task = provisioner.deprovision(provisionedBlueprintId=provisioning.provisionedBlueprint.id) # where provisioning is object created by initialProvisioning
deployit.startTaskAndWait(task.id)
{% endhighlight %}
