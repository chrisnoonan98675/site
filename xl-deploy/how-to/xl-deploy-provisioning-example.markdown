---
title: XL Deploy provisioning example
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- cloud
since:
- XL Deploy 7.2.0
weight: 158
---

This topic provides a step-by-step example that will help you get started with the XL Deploy [provisioning feature](/xl-deploy/concept/provisioning-through-xl-deploy.html). To follow this example, you need:

* An [Amazon Web Services EC2](https://aws.amazon.com/ec2/) AMI where [Puppet](https://puppet.com/) is installed
* A Puppet manifest that will install [Apache Tomcat](http://tomcat.apache.org/) in `/opt/apache-tomcat`
* The sample PetClinic-war application provided with XL Deploy (optional)

The instructions in this topic assume that you are using a Unix-based operating system.

**Note:** A version of this topic is available for [XL Deploy 5.5.x](/xl-deploy/5.5.x/xl-deploy-provisioning-example-5.5.html).

## Step 1 Start XL Deploy

Follow [the instructions](/xl-deploy/how-to/install-xl-deploy.html) to install XL Deploy 6.0.0 or later. Note that Java Development Kit (JDK) 8 is required.

If XL Deploy is already installed, [start the XL Deploy server](/xl-deploy/how-to/start-xl-deploy.html).

## Step 2 Create an application

XL Deploy uses the same model for deployment packages and provisioning packages, so you must first create an _application_ that will serve as a logical grouping of provisioning packages. A provisioning package describes the infrastructure items that should be created and the details of the environment to which infrastructure items will be associated. To create a new application:

1. Click **Repository** in the top bar.
1. Right-click **Applications** and select **New** > **Application**.
1. In the **Name** box, enter `PetClinicEnv`.
1. Click **Save**.

## Step 3 Create a provisioning package

To create a new provisioning package:

1. Right-click **PetClinicEnv** and select **New** > **Provisioning Package**.
1. In the **Name** box, enter `1.0.0`.
1. Click **Save**.

## Step 4 Create provisionables and templates

A provisioning package consists of provisionables, which are virtual machine specifications, and templates for configuration items (CIs).

### Create an instance specification

To create a new provisionable, right-click the **1.0.0** package and select **New** > **aws** > **ec2.InstanceSpec**. Enter the following properties:

{:.table .table-striped}
| Property | Value | Description |
| -------- | ----- | ----------- |
| Name | `tomcat-instance-spec` | The name of the CI |
| AWS AMI ID | Your AWS AMI ID (for example, `ami-d91be1ae`) | The ID of an AMI where Puppet is installed |
| Region | The EC2 region of the AMI (for example, `eu-west-1`) | The EC2 region, which must be valid for the AMI that you selected |
| AWS Security Group | `default` | The security group of the AMI |
| Instance Type | `m1.small` | The size of the instance |
| AWS key pair name | Your AWS key name | Name of your EC2 SSH key pair. If you do not have an AWS key name, log in to the Amazon EC2 console, create a new key, and download it to your local machine. |

Click **Save** to save the CI.

![Sample aws.ec2.InstanceSpec CI](images/provisioning-create-new-provisionable-02.png)

### Create an SSH host template

Right-click the **1.0.0** package and select **template** > **overthere.SshHost**. Enter the following properties:

{:.table .table-striped}
| Property | Value | Description |
| -------- | ----- | ----------- |
| Name | `tomcat-host` | The name of the CI |
| Operating System | `UNIX` | Operating system of the virtual machine |
| Connection Type | `SUDO` | Puppet requires a SUDO connection |
| Address | `{% raw %}{{%publicHostname%}}{% endraw %}` | This is a placeholder that will be resolved from the provisioned |
| Username | `ubuntu` | User name for the EC2 machine |
| Private Key File | `SSH_DIRECTORY/{% raw %}{{%keyName%}}{% endraw %}.pem` | The location of the SSH key on your local machine to use when connecting to the EC2 instance. `SSH_DIRECTORY` is the directory where you store your SSH keys; for example, `Users/yourusername/.ssh` |
| SUDO username | `root` | The user name to use for SUDO operations (this property is located on the **Advanced** tab |

Click **Save** to save the CI.

![Sample template.overthere.SshHost CI](images/provisioning-template-overthere-sshhost.png)

### Create a Tomcat server template

Next, right-click the **tomcat-host** CI and select **New** > **tomcat.Server**. Enter the following properties:

{:.table .table-striped}
| Property | Value | Description |
| -------- | ----- | ----------- |
| Name | `tomcat-server` | The name of the CI |
| Home | `/opt/apache-tomcat` | Puppet will install Tomcat in this directory |
| Start Command | `sh bin/startup.sh` | The command that will start Tomcat |
| Stop Command | `sh bin/shutdown.sh` | The command that will stop Tomcat |

Click **Save** to save the CI.

![Sample template.tomcat.Server CI](images/provisioning-template-tomcat-server.png)

### Create a Tomcat virtual host template

Next, right-click the **tomcat-server** CI and select **New** > **tomcat.VirtualHost**. Enter the following properties:

{:.table .table-striped}
| Property | Value | Description |
| -------- | ----- | ----------- |
| Name | `tomcat-vh` | The name of the CI |

Click **Save** to save the CI.

![Sample template.tomcat.VirtualHost CI](images/provisioning-template-tomcat-virtualhost.png)

### Create a directory to store generated CIs

Finally, create a directory to store the CIs that XL Deploy will generated from the templates:

1. Right-click **Infrastructure** and select **Directory**.
1. In the **Name** box, enter `Tomcat`.
1. Click **Save**.

## Step 5 Bind the SSH host template to the instance spec

To bind the *tomcat-host* template to the *tomcat-instance-spec* provisionable:

1. Double-click **tomcat-instance-spec** to open it.
1. Go to the **Provisioning** tab.
1. Under **Bound Templates**, select `Applications/PetClinicEnv/1.0.0/tomcat-host` and click ![Right arrow button](/images/button_add_container.png) to move it to the **Members** list.
1. Click **Save**.

![Sample aws.ec2.InstanceSpec with bound template](images/provisioning-aws-ec2-instancespec-bound-template.png)

## Step 6 Add a Puppet provisioner

To add Puppet as a provisioner:

1. Right-click **tomcat-instance-spec** and select **provisioner.Manifest** to create a new Puppet manifest.
1. In the **Name** box, enter `install-tomcat`.
1. Select `tomcat-host` from the **Host** list.
1. Under **Artifact**, upload a Puppet manifest file that will install Tomcat.
1. Click **Save**.

![Sample puppet.provisioner.Manifest CI](images/provisioning-puppet-provisioner.png)

### Add modules to the provisioner

Add Puppet modules to the provisioner:

1. Right-click the **install-tomcat** CI and select **New** > **provisioner.ModuleSpec**.
1. In the **Name** box and the **Module Name** box, enter `puppetlabs-tomcat`.
1. Click **Save**.
1. Repeat this process to create a module for `puppetlabs-java`.

Now, if you open the *install-tomcat* CI, you will see the modules:

![Sample puppet.provisioner.Manifest CI with module specs](images/provisioning-puppet-provisioner-with-modules.png)

## Step 7 Create the AWS provider

Create a new provider for Amazon Web Services (AWS):

1. Right-click **Infrastructure** and select **New** > **aws** > **ec2.Cloud**.
1. In the **Name** box, enter `AWS-EC2`.
1. Enter your Amazon Web Services credentials in the **Access Key ID** and **Secret Access Key** boxes.
1. Click **Save**.

![Sample aws.ec2.Cloud CI](images/provisioning-aws-ec2-cloud.png)

## Step 8 Create an environment

Create an environment where the package will be provisioned:

1. Right-click **Environments** and select **New** > **Environment**.
1. In the **Name** box, enter `Cloud`.
1. Under **Providers**, select `Infrastructure/AWS-EC2` and click ![Right arrow button](/images/button_add_container.png) to move it to the **Members** list.
1. Go to the **Provisioning** tab.
1. In the **Directory Path** box, enter `Tomcat`.
1. Click **Save**.

![Environment with aws.ec2.Cloud added](images/provisioning-environment.png)

## Step 9 Perform provisioning

To provision the environment:

1. Click **Deployment** in the top bar.
1. Under **Packages**, select **PetClinicEnv** and drag it to the left side of the Deployment Workspace.
1. Under **Environments**, select **Cloud** and drag it to the right side of the Deployment Workspace.
1. Optionally, click **Preview** to preview the provisioning plan.

    ![Provisioning plan preview](images/provisioning-with-preview.png)

1. Optionally, click **Deployment Properties** and go to the **Provisioning** tab. The value in the **Provisioning Id** field is automatically generated by XL Deploy. To prevent name collisions, this ID will be prepended to CIs that are generated from bound templates.
1. Click **Execute** to perform the provisioning.

## Conclusion

You can see the generated CIs in the Repository:

![Infrastructure CIs generated from bound templates](images/provisioning-generated-infrastructure-cis.png)

In this case, the unique provisioning ID was `695hnTMa`.

You can also see that the CIs were added to the *Cloud* environment:

![CIs generated from bound templates added to environment](images/provisioning-generated-cis-in-environment.png)

Now, you can [import](/xl-deploy/how-to/add-a-package-to-xl-deploy.html#import-a-package) the sample package *PetClinic-war/1.0* from the XL Deploy server and [deploy it](/xl-deploy/how-to/deploy-an-application.html) to the *Cloud* environment. After the deployment succeeds, you can see the application running at `http://<instance public IP address>:8080/petclinic`. You can find the public IP address and other properties in the instance CI under the provider.
