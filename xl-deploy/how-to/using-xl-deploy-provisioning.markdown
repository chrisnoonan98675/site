---
title: Using XL Deploy provisioning
categories:
- xl-deploy
subject:
- Provisioning
tags:
- deployment
- provisioning
- environment
- infrastructure
---


This document will help you get started with XLD provisioning support. We will cover all the steps you need to perform to provision a tomcat environment and then deploy `petclinic` application to it.

## Prerequisite

1. Download the latest XLD distribution from distribution website.
2. Make sure you have JDK 8 installed on your machine.

> **This document assume you are using *nix system.**

Now that we have all the prerequisite covered let's go through all the steps one by one.

## Step 1: Start XLD

Extract XLD to a convenient location on your file system. **Make sure to also copy the `deployit-license.lic` in the `conf` directory.**

Now, start the XLD server using the shell executable. We are running this command from the `XLDEPLOY_HOME` location.

```bash
$ sh bin/run.sh
```

Once server boots up, you can view the XLD running at [http://localhost:4516/](http://localhost:4516/).

## Step 2: Create Blueprint

A blueprint is a logical grouping of provisioning packages. A provisioning package describes what all infrastructure items should be created and details of the environment that infrastructure items will be associated with.

Let's create a Blueprint. Go to `Repository` screen, then right click to create a new one as shown below.

![](images/provisioning/create-new-blueprint.png)

This will open a web form and you will be asked to provide name of the `Blueprint`. You can give it any valid name. For this document, we will name it `PetclinicEnvBlueprint`. Press the **Save** button.

## Step 3: Create a Provisioning Package

As mentioned above, a Provisioning Package is a specification of your provisioned environment. To create a new provisioning package, right click on the `PetclinicEnvBlueprint`, then create a new Provisioning package. Give it a name, we will use `1.0` as the name of provisioning package to denote that it is version 1.0 of our specification.

![](images/provisioning/create-new-provisioning-package.png)

## Step 4: Create provisionables and templates

A Provisioning package consists of `templates` and `provisionables`. `Provisionable` has one-on-one correspondence to `deployable`. For provisioning, a Provisionable is a virtual machine instance spec. Right click on the provisioning package to create a new Provisionable. We will use `aws.ec2.InstanceSpec` provisionable.

Enter the following details in the form.

| Form Field     | Value     |Description|
| :------------- | :------------- |:------------- |
| Name       | tomcat-instance-spec       |Any valid name|
| Cardinality       | 1       |how many instance you want to create default is 1|
| AWS AMI ID       | ami-d91be1ae       |This ami id with puppet installed.|
| Region       | eu-west-1       |The EC2 region. AMI are associated with Region so make sure both are valid.|
| AWS Security Group       | default       |Default value|
| Instance Type       | m1.small       |Default instance size|
| AWS key pair name       | Your AWS key name|Name of your EC2 SSH key pair. Please create one.|


If you don't have AWS key name, then please login to Amazon EC2 console and create a new key. You will have to download the key on your local machine.

Now, we will create three templates required for Tomcat. Right click on the package and create a template for `template.overthere.SshHost`. Please enter following values.

| Form Field     | Value     | Description|
| :------------- | :------------- |:------------- |
| Name       | tomcat-host       |Any valid name|
| Operating System       | UNIX       |Operating system of your VM|
| Connection Type       | SUDO       |We need SUDO type as Puppet needs it|
| Address       | {{%publicHostname%}}       |These placeholders are resolved from the deployed.|
|Username| ubuntu|EC2 machine username|
|Private Key File| SSH_DIRECTORY/{{%keyName%}}.pem|This is the location of the SSH key on your local machine that will be used to connect to EC2 instance. SSH_DIRECTORY is the directory where you store your SSH keys.For me it is `/Users/shekhargulati/.ssh`|
|SUDO username|root|This is under `Advanced` tab.|

![](images/provisioning/create-tomcat-host.png)


Now create another template of type `template.tomcat.Server`. Create it right clicking it on `tomcat-host` so that relationship is maintained. Following are the values.


| Form Field     | Value     | Description|
| :------------- | :------------- |:------------- |
| Name       | tomcat-server       |Any valid name|
| Home       | /opt/apache-tomcat       |Puppet will install tomcat inside this directory.|
| Start Command       | sh bin/startup.sh       |The command that will start tomcat.|
| Stop Command       | sh bin/shutdown.sh       |The command that will stop tomcat.|


Finally, create another template of type `template.tomcat.VirtualHost`. Give it a name `tomcat-vh` and choose the default values.

You will see following template hierarchy in the repository screen.

![](images/provisioning/template-hierachy.png)

## Step 5: Bound the `tomcat-host` template with `tomcat-instance-spec`

Open the tomcat-instance-spec by double clicking and select `Blueprints/PetclinicEnvBlueprint/1.0/tomcat-host` and move it to members as shown below. Make changes persistent by pressing the **Save** button.

![](images/provisioning/attach-bound-template.png)

## Step 6: Add puppet provisioner

We will use Puppet for provisioning. Right click on the `tomcat-instance-spec` to create a new CI of type `puppet.Manifest`. Give it a name `install-tomcat` and upload your puppet manifest file to install Tomcat.

Puppet uses modules to install software. We will need couple of puppet modules -- `puppetlabs-tomcat` and `puppetlabs-java`. Right click on `install-tomcat` CI and create two CI of type `puppet.ModuleSpec`. The form has two fields. For tomcat puppet module, use `puppetlabs-tomcat` for both field and for java module use `puppetlabs-java` for both fields.

Blueprint will look like as shown below.

![](images/provisioning/blueprint.png)


## Step 7: Create AWS provider

Create a new provider inside the Provider root. To create AWS provider, you will need access to AWS keys.

![](images/provisioning/create-aws-provider.png)

## Step 8: Create Ecosystem

Create a new ecosystem under the `Ecosystems` root. Select the `xl-aws-provider`.

## Step 9: Perform Provisioning

Now, we have all the required CI items created we can do the actual provisioning. Go to the `Provisioning` screen by clicking the `Provisioning` menu item on the top. You will see `PetclinicEnvBlueprint` on the left and `environment-creator` Ecosystem on the right.

Drag both of them to Provisioning workspace. You will be greeted with one error. This error is because we have not specified environment name.

![](images/provisioning/provisioning-error.png)

Give name to environment. We are using `petclinic`.

![](images/provisioning/provide-environment-name.png)

Now press the `Execute` button to perform provisioning.

![](images/provisioning/provisioning-in-action.png)

You have to wait couple of minutes for provisioning to finish. Once finished, you will have `petclinic` environment created.
