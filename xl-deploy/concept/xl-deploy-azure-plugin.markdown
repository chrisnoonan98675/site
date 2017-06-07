---
title: Introduction to the XL Deploy Azure plugin
categories:
- xl-deploy
subject:
- Azure
tags:
- azure
- cloud
- plugin
since:
- XL Deploy 7.0.0
---

The XL Deploy Azure plugin enables XL Deploy to work with the Microsoft Azure cloud computing service. It allows you to provision Virtual Machines and define security groups, public IP, and configure network settings for them.

For information about requirements and the configuration items (CIs) that the Azure plugin provides, refer to the [Azure Plugin Reference](/xl-deploy-xld-azure-plugin/latest/azurePluginManual.html).

## Features ##

* Create Resource Groups and Storage Accounts
* Define Security Groups and Security Rules associate with them
* Setup Public IP addresses
* Define Virtual Networks (VNet) and their Route Tables
* Provision Virtual Machines associated to their Network Interface (NIC)
* Automatically destroy machines during undeployment

This is a diagram representing all the resource types XL Deploy Azure plugin supports and their dependencies:

![Azure types diagram](images/xl-deploy-azure-diagram.png)
