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

For information about AWS requirements and the configuration items (CIs) that the plugin supports, refer to the [AWS Plugin Reference](/xl-deploy-xld-aws-plugin/latest/awsPluginManual.html).

**Note:** The AWS plugin is written in Python. To extend the plugin, knowledge of the Python programming language is required.

## Features

* Create virtual machines on Elastic Compute Cloud (EC2) with a specified Amazon Machine Image (AMI)
* Automatically destroy EC2 instances during undeployment
* Provision a Simple Storage Service (S3) bucket
* Create/Destroy Network Elements like VPC, Subnets, Routing Tables etc.
* Create Clusters and deploy service on Elastic Container Service 
* Elastic Container Registry
* Lambda
* API Gateway
* Relational Database Service (RDS)
* Auto Scaling groups
* Save a running instance as AMI (Fits into CD pipeline)


### Attach an elastic IP address with a non-Virtual Private Cloud (VPC) EC2 instance

To create and attach an elastic IP:

1. Go to the **Elastic IP** tab and set **Attach Elastic IP** to `true` and **Elastic IP Domain** to `standard`.
1. A new elastic IP is created and attached to the non-VPC EC2 instance.
1. If the EC2 instance in stopped, the elastic IP is detached and is reattached by the plugin when you restart the EC2 instance.
1. You can set the **Elastic IP** property to `false` during a MODIFY operation. This will detach the elastic IP and will release it.
1. If you perform and undeployment, the elastic IP is released.

### Attach an elastic IP address with a Virtual Private Cloud (VPC) EC2 instance

To create and attach an elastic IP:

1. Go to the **Elastic IP** tab and set **Attach Elastic IP** to `true` and **Elastic IP Domain** to `standard`.
1. A new elastic IP is created and attached to the default network interface connected to the EC2 instance at `eth0`.
1. If the EC2 instance in restarted, the elastic IP is still attached to the default network interface and does not require to be reattached.
1. You can set the **Elastic IP** property to `false` during a MODIFY operation. This will detach the elastic IP and will release it.
1. If you perform and undeployment, the elastic IP is released.
