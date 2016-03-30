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
provisioner.initial_provisioning()
provisioner.validate()
provisioner.provisioning_task()
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
pp = repository.create(factory.configurationItem(bp.id + "/1.0", "upm.ProvisioningPackage", {"environmentId":"dev","blueprint":bp.id }))
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
ami = repository.create(factory.configurationItem(pp.id + "/ubuntu", "aws.ec2.AMI",params))
{% endhighlight %}

## Step 5 Create an ecosystem

An ecosystem groups providers and dictionaries and serves as a target for provisioning. Start by creating a provider.

{% highlight python %}
params = {"accesskey":"EC2_ACCESS_KEY","accessSecret":"EC2_ACCESS_SECRET"}
provider = repository.create(factory.configurationItem("Providers/EC2Provider", "aws.ec2.Cloud", params))
{% endhighlight %}

Next, create an ecosystem using the provider.

{% highlight python %}
params = {"providers":[provider.id]}
ecosystem = repository.create(factory.configurationItem("Ecosystems/test-ecosystem", "upm.Ecosystem", params))
{% endhighlight %}

## Step 6 Perform the initial provisioning

Now that you have a provisioning package and an ecosystem, you can perform the initial provisioning. To do so, use the `initial_provisioning` method. It requires a provisioning package ID and an ecosystem ID.

{% highlight python %}
provisioner = Provisioner()
p = provisioner.initial_provisioning(package_id="Blueprints/EC2AMIs/1.0", ecosystem_id="Ecosystems/test-ecosystem")
{% endhighlight %}

If there are any validation errors, you will see them in the `validationErrors` field in the response. As you can see below, `validationErrors` is empty so no there is no validation error.

{% highlight python %}
admin > print(p)
{u'provisionedBlueprint': {u'provsioningPackage': {u'type': u'upm.ProvisioningPackage', u'id': u'Blueprints/EC2AMIs/1.0'}, u'provisionedEnvironment': {u'type': u'udm.Environment', u'id': u'dev'}, u'ecosystem': {u'type': u'upm.Ecosystem', u'id': u'Ecosystems/test-ecosystem'}, u'optimizePlan': True, u'type': u'upm.ProvisionedBlueprint', u'id': u'Environments/test-ecosystem/EC2AMIs-4bb0470f-76ef-4436-8705-c5de05327391'}, u'provisioneds': [{u'instanceType': u'm1.small', u'keyName': u'default', u'securityGroup': u'default', u'type': u'aws.ec2.Instance', u'provisionable': {u'type': u'aws.ec2.AMI', u'id': u'Blueprints/EC2AMIs/1.0/ubuntu'}, u'amiId': u'ami-id', u'provider': {u'type': u'aws.ec2.Cloud', u'id': u'Providers/EC2Provider'}, u'id': u'Providers/EC2Provider/ubuntu-efa096aa-394c-4c49-ac19-ec4738e31e62', u'region': u'eu-west-1', u'ordinal': 1}], u'validationErrors': []}
{% endhighlight %}

You can access specific fields using the dot notation as shown below.

{% highlight python %}
admin > p.validationErrors
PyList: []
{% endhighlight %}

## Step 7 Preview provisioning task

The CLI provisioning extension allows you to preview the provisioning plan that XL Deploy generated based on the provisioning configuration. To view the plan, you can use the `preview` method of `provisioner` object as shown below.

{% highlight python %}
task_preview = provisioner.preview(p)
{% endhighlight %}

You can also preview a step by passing it a blockId and step number as shown below.

{% highlight python %}
step_preview = provisioner.preview(p,"0_1_1_1","1")
{% endhighlight %}

## Step 8 Invoke the provisioning task

After you perform the initial provisioning, you can request that XL Deploy create a provisioning task.

{% highlight python %}
task = provisioner.provisioning_task(p)
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

To deprovision the created environment, you can use the `deprovision` method, passing it the ID of the environment you want to deprovision. Thsi will destroy the environment and all related configuration items.

{% highlight python %}
provisioner.deprovision(provisioned_environment_id="Environments/env/id")
{% endhighlight %}
