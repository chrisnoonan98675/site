---
title: Introduction to the XL Scale vSphere plugin
no_index: true
---

XL Scale provides functionality to create and destroy environments on virtualized infrastructure that can be used as deployment targets. The XL Scale vSphere plugin is an XL Deploy plugin that supports launching, provisioning and terminating hosts and environments on VMWare vSphere platform.

For information about the configuration items (CIs) that the XL Scale vSphere plugin supports, refer to the [XL Scale vSphere Plugin Reference](/xl-scale/latest/vspherePluginManual.html).

## Features

* Deploying vSphere templates as virtual machines from XL Deploy
* Destroying previously deployed virtual machines
* Combining vSphere virtual machines into environments which can be used for application deployment
* Registering middleware as part of a created environment

## vCenter credentials

The vSphere plugin requires access to vCenter in order to perform operations on vSphere platform. These credentials are specified under the **Configuration** root node in the repository using a `vsphere.Credentials` CI. The CI has a control task `validateCredentials` that can test that the credentials can be used to communicate with vCenter.

## Creating a single host

In its simplest form, the vSphere plugin can deploy a single virtual machine from a template and register it in XL Deploy as a CI of type `cloud.SshHost` (when connecting to the host using SSH) or `cloud.CifsHost` (when connecting using CIFS). Both CI types are available under the **Configuration** node in the repository.

The resulting host CI can contain middleware CIs that are present on the host and can be used as a normal container for deployment. The host can also be destroyed, which causes XL Deploy to terminate the vSphere instance and remove the host CI and its children from the repository.

There is a special CI type called `vsphere.HostTemplate` which is used as a template to define all information about the future virtual machine. This CI is also available under the **Configuration** node.

## Creating an environment

For information about combining cloud hosts into environments, refer to [Create an XL Scale environment template](/xl-deploy/how-to/create-an-xl-scale-environment-template.html).

## Provisioning instantiated hosts

When deploying a virtual machine, the template may already have the desired middleware installed. If this is the case, a launched host will be ready for use as soon as it has finished booting.

It is also possible to provision a host using Puppet, Chef, or a shell command after launching it. This is supported via the the notion of a _marker file_. If the host template specifies a marker file, XL Deploy will poll the launched instance for its presence. When the file is found on the instance filesystem, XL Deploy will conclude the host is up and ready for deployment. The location of the marker file can be configured in the `vsphere.HostTemplate`.

**Note**: It is the responsibility of the template to invoke the provisioning process and to create the marker file when provisioning is completed, signaling that the host and middleware are ready for deployment. Marker files are only supported when the template is based on a Unix family OS.
