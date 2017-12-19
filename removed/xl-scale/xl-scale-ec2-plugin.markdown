---
title: Introduction to the XL Scale EC2 plugin
no_index: true
---

XL Scale provides functionality to create and destroy environments on virtualized infrastructure that can be used as deployment targets. The XL Deploy EC2 plugin is a XL Deploy plugin that supports launching, provisioning and terminating hosts and environments on the Amazon EC2 platform.

For information about the configuration items (CIs) that the XL Scale EC2 plugin supports, refer to the [XL Scale EC2 Plugin Reference](/xl-scale/latest/ec2PluginManual.html).

## Features

* Launching and terminating EC2 instances from XL Deploy
* Optional launching within Amazon VPC (Virtual Private Cloud)
* Combining EC2 instances into environments which can be used for application deployment
* Registering middleware on EC2 instances as part of a created environment

## EC2 credentials

The EC2 plugin requires access to the AWS key and secret to access the EC2 API. These credentials are specified under the **Configuration** root node in the repository using a `ec2.Credentials` CI. The CI has a control task `validateCredentials` that can test that the credentials can be used to communicate with EC2.

## Creating a single host

In its simplest form, the EC2 plugin can launch a single instance on EC2 and register it in XL Deploy as a CI of type `cloud.SshHost` (when connecting to the host using SSH) or `cloud.CifsHost` (when connecting using CIFS). Both CI types are available under the **Configuration** node in the repository.

The resulting host CI can contain middleware CIs that are present on the host and can be used as normal containers for deployment. The host can also be destroyed, which causes XL Deploy to terminate the EC2 instance and remove the host CI and its children from the repository.

There is a special CI type `ec2.HostTemplate` which is used as a template to define all information about the future host. This CI is also available under the **Configuration** node. For information about creating a `ec2.HostTemplate` CI, refer to [Create an XL Scale EC2 host template](/xl-deploy/how-to/create-an-xl-scale-ec2-host-template.html).

## Creating an environment

For information about combining cloud hosts into environments, refer to [Create an XL Scale environment template](/xl-deploy/how-to/create-an-xl-scale-environment-template.html).

## Provisioning instantiated hosts

When launching an instance on EC2, the AMI it is based on may already have the desired middleware installed. If this is the case, a launched host will be ready for use as soon as it has finished booting.

It is also possible to provision a host using Puppet, Chef or a shell command after launching it. This is supported via the the notion of _marker file_. If the host template specifies a marker file, XL Deploy will poll the launched instance for its presence. When the file is found on the instance filesystem, XL Deploy will conclude the host is up and ready for deployment. The location of the marker file can be configured in the `ec2.HostTemplate`.

**Note:** It is the responsibility of the AMI to invoke the provisioning process and to create the marker file when provisioning is completed, signaling that the host and middleware is ready for deployment. Marker files are only supported on Unix-based AMIs.
