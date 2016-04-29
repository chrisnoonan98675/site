---
layout: beta-noindex
title: XL Deploy provisioning example
since:
- XL Deploy 5.5.0
---

{% comment %}
categories:
- xl-deploy
subject:
- Provisioning
tags:
- deployment
- provisioning
- environment
- infrastructure
{% endcomment %}

This topic provides a step-by-step example that will help you get started with the XL Deploy [provisioning feature](/xl-deploy/concept/provisioning-through-xl-deploy.html). This example shows how to provision a cloud-based environment running [Apache Tomcat](http://tomcat.apache.org/).

**Note:** This example assumes you are using a Unix-based operating system.

## Step 1 Start XL Deploy

Follow [the instructions](/xl-deploy/how-to/install-xl-deploy.html) to install XL Deploy 5.5.0 or later. Note that Java Development Kit (JDK) 8 is required.

If XL Deploy is already installed, [start the XL Deploy server](/xl-deploy/how-to/start-xl-deploy.html).

## Step 2 Create a blueprint

A blueprint is a logical grouping of provisioning packages. A provisioning package describes the infrastructure items that should be created and the details of the environment to which infrastructure items will be associated. To create a new blueprint:

1. Click **Repository** in the top bar.
1. Right-click **Blueprints** and select **New** > **upm** > **Blueprint**.
1. In the **Name** box, enter `PetclinicEnvBlueprint`.
1. Click **Save**.

## Step 3 Create a provisioning package

To create a new provisioning package:

1. Right-click **PetclinicEnvBlueprint** and select **New** > **ProvisioningPackage**.
1. In the **Name** box, enter `1.0`.
1. Click **Save**.

## Step 4 Create provisionables and templates

A provisioning package consists of *provisionables*, which are virtual machine specifications, and *templates* for configuration items (CIs).

### Create an instance specification

To create a new provisionable, right-click the **1.0** provisioning package and select **New** > **aws** > **ec2.InstanceSpec**. This will create a specification for an [Amazon EC2](https://aws.amazon.com/ec2/) instance. Enter the following properties:

{:.table .table-striped}
| Property | Value | Description |
| -------- | ----- | ----------- |
| Name | `tomcat-instance-spec` | The name of the CI |
| Cardinality | `1` | The number of instances to create (default is 1) |
| AWS AMI ID | Your AWS AMI ID (for example, `ami-d91be1ae`) | The ID of the AMI where Puppet is installed |
| Region | The EC2 region of the AMI (for example, `eu-west-1`) | The EC2 region, which must be valid for the AMI that you selected |
| AWS Security Group | `default` | The security group of the AMI |
| Instance Type | `m1.small` | The size of the instance |
| AWS key pair name | Your AWS key name | Name of your EC2 SSH key pair. If you do not have an AWS key name, log in to the Amazon EC2 console, create a new key, and download it to your local machine. |

![Sample aws.ec2.InstanceSpec CI](images/provisioning/provisioning-example-aws-ec2-instancespec.png)

### Create an SSH host template

Right-click the **1.0** provisioning package and select **template** > **overthere.SshHost**. Enter the following properties:

{:.table .table-striped}
| Property | Value | Description |
| -------- | ----- | ----------- |
| Name | `tomcat-host` | The name of the CI |
| Operating System | `UNIX` | Operating system of the virtual machine |
| Connection Type | `SUDO` | Puppet requires a SUDO connection |
| Address | `{% raw %}{{%publicHostname%}}{% endraw %}` | This is a placeholder that will be resolved from the provisioned |
| Username | `ubuntu` | User name for the EC2 machine |
| Private Key File| `SSH_DIRECTORY/{{%keyName%}}.pem` | The location of the SSH key on your local machine to use when connecting to the EC2 instance. `SSH_DIRECTORY` is the directory where you store your SSH keys; for example, `Users/yourusername/.ssh` |
| SUDO username | `root` | The user name to use for SUDO operations (this property is located on the **Advanced** tab |

![Sample template.overthere.SshHost CI](images/provisioning/provisioning-example-template-overthere-sshhost.png)

### Create a Tomcat server template

Next, right-click the **tomcat-host** CI that you just created and select **New** > **tomcat.Server**. Enter the following properties:

{:.table .table-striped}
| Property | Value | Description |
| -------- | ----- | ----------- |
| Name | `tomcat-server` | The name of the CI |
| Home | `/opt/apache-tomcat` | Puppet will install Tomcat in this directory |
| Start Command | `sh bin/startup.sh` | The command that will start Tomcat |
| Stop Command | `sh bin/shutdown.sh` | The command that will stop Tomcat |

![Sample template.tomcat.Server CI](images/provisioning/provisioning-example-tomcat-server.png)

### Create a Tomcat virtual host template

Finally, right-click the **tomcat-server** CI that you just created and select **New** > **tomcat.VirtualHost**. Enter the following properties:

{:.table .table-striped}
| Property | Value | Description |
| -------- | ----- | ----------- |
| Name | `tomcat-vh` | The name of the CI |

Now you will have the following CIs under **Blueprints**:

![Blueprint CIs](images/provisioning/provisioning-example-blueprint-cis-01.png)

## Step 5 Bind the SSH host template to the instance spec

To bind the *tomcat-host* template to the *tomcat-instance-spec* provisionable:

1. Double-click **tomcat-instance-spec** to open it.
1. Under **Bound Templates**, select `Blueprints/PetclinicEnvBlueprint/1.0/tomcat-host` and click ![Right arrow button](/images/button_add_container.png) to move it to the **Members** list.
1. Click **Save**.

![Add SSH host template to instance spec](images/provisioning/provisioning-example-bound-template.png)

## Step 6 Add the Puppet provisioner

To add Puppet as a provisioner:

1. Right-click **tomcat-instance-spec** and select **provisioner.Manifest** to create a new Puppet manifest.
1. In the **Name** box, enter `install-tomcat`.
1. Select `tomcat-host` from the **Host** list.
1. Under **Artifact**, upload a Puppet manifest file that will install Tomcat.
1. Click **Save**.

![Sample provisioner.Manifest](images/provisioning/provisioning-example-provisioner-manifest.png)

### Add modules to the provisioner

Add Puppet modules to the provisioner:

1. Right-click the **install-tomcat** CI that you just created and select **New** > **provisioner.ModuleSpec**.
1. In the **Name** box and the **Module Name** box, enter `puppetlabs-tomcat`.
1. Click **Save**.
1. Repeat this process to create a `provisioner.ModuleSpec` CI for `puppetlabs-java`.

Now you will have the following CIs under **Blueprints**:

![Blueprint CIs](images/provisioning/provisioning-example-blueprint-cis-02.png)

## Step 7 Create the AWS provider

Create a new provider for Amazon Web Services (AWS):

1. Right-click **Providers** and select **New** > **aws** > **ec2.Cloud**.
1. In the **Name** box, enter `xl-aws-provider`.
1. Enter your AWS credentials in the **Access Key ID** and **Secret Access Key** boxes.
1. Click **Save**.

![Sample aws.ec2.Cloud](images/provisioning/provisioning-example-aws-ec2-cloud.png)

## Step 8 Create a provisioning environment

Create an environment where the provisioning package will be provisioned:

1. Right-click **ProvisioningEnvironments** and select **New** > **upm** > **ProvisioningEnvironment**.
1. In the **Name** box, enter `environment-creator`.
1. Under **Providers**, select `Providers/xl-aws-provider` and click ![Right arrow button](/images/button_add_container.png) to move it to the **Members** list.
1. Click **Save**.

![Sample upm.ProvisioningEnvironment](images/provisioning/provisioning-example-provisioningenvironment.png)

## Step 9 Perform provisioning

To provision the environment:

1. Click **Provisioning** in the top bar.
1. Under **Blueprints**, select **PetclinicEnvBlueprint** and drag it to the left side of the Provisioning Workspace.
1. Under **Provisioning Environments**, select **environment-creator** and drag it to the right side of the Provisioning Workspace.

    ![Initial mapping](images/provisioning/provisioning-example-initial-mapping-with-error.png)

1. There will be one error, because you did not specify the XL Deploy environment where the `overthere.SshHost` CI based on the bound template should be assigned. To fix this error, click **Provisioning Properties**.
1. In the **Environment Name** box, enter `petclinic`.

    ![Provisioning properties](images/provisioning/provisioning-example-provisioning-properties.png)

1. Click **OK**.
1. Click **Execute** to perform the provisioning.

    ![Provisioning in progress](images/provisioning/provisioning-example-provisioning-in-action.png)
