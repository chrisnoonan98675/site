---
title: Introduction to the XL Deploy AWS plugin
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- aws
- cloud
- plugin
since:
- XL Deploy 5.5.0
weight: 160
---

The Amazon Web Services (AWS) plugin enables XL Deploy to work with Amazon Web Services such as Elastic Compute Cloud (EC2) and Simple Storage Service (S3).

**Note:** Prior to version 6.2.0, the plugin was called the XL Deploy EC2 plugin.

For information about AWS requirements and the configuration items (CIs) that the plugin supports, refer to the [EC2 Plugin Reference](/xl-deploy-xld-aws-ec2-plugin/latest/ec2PluginManual.html).

**Note:** The EC2 plugin is written in Python. To extend the plugin, knowledge of the Python programming language is required.

## Features

* Create virtual machines on Elastic Compute Cloud (EC2) with a specified Amazon Machine Image (AMI)
* Automatically destroy EC2 instances during undeployment
* Provision a Simple Storage Service (S3) bucket
