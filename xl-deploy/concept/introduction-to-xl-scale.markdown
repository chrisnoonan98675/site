---
title: Introduction to XL Scale
categories:
- xl-deploy
subject:
- XL Scale
tags:
- xl scale
- plugin
- virtualization
weight: 380
---

XL Scale provides functionality to create and destroy environments on virtualized infrastructure that can be used as deployment targets. It supports several different virtualization vendors. XL Scale functionality is accessible from within the XL Deploy GUI and ties in to XL Deploy's security system and reporting.

For information about the configuration items (CIs) that XL Scale supports, refer to the [XL Scale Reference](/xl-scale/latest/xlScaleManual.html).

## Concepts

XL Scale introduces two concepts:

* **Host template**: a host template is a template for creating new, virtualized hosts in XL Deploy. A host template describes the characteristics of the host that will be created (using virtualization vendor specific configuration settings) and describes which middleware will be available to XL Deploy once a new host based on the template is created. A host template is specific to a particular virtualization vendor such as Amazon EC2 or VMWare.

* **Environment template**: an environment template is a collection of one or more host templates that are instantiated or destroyed together, allowing creating or removal of an entire environment in one go. An environment template describes which middleware will be part of the environment in XL Deploy once a new environment based on the template is created. An environment template is not tied to a virtualization provider and can contain host templates from various virtualization vendors.

## Configuration

To configure the XL Scale, follow these steps:

1. [Create a _host template_ instance](/xl-deploy/how-to/create-an-xl-scale-host-template.html) for each host to be created.
2. Validate the host template descriptor using the ```validateDescriptor``` control task.
3. [Create an _environment template_ instance](/xl-deploy/how-to/create-an-xl-scale-environment-template.html) to group together a set of host templates.
4. Validate the environment template descriptor using the ```validateDescriptor``` control task.
