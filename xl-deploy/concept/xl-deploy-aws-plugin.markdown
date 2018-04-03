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

The Amazon Web Services (AWS) plugin for XL Deploy supports:

* Launching and terminating AWS Elastic Compute Cloud (EC2) and Virtual Private Cloud (VPC) instances
* Deploying applications to AWS cloud-based instances
* Using Amazon's Elastic Load Balancing feature for EC2 instances
* Creating and using Simple Storage Service (S3) buckets for file storage
* Provisioning EC2 Container Service (ECS) clusters, tasks, and services
* Working with EC2 Container Registry (ECR) repositories
* Using the Relational Database Service (RDS) for databases
* Using the Elastic Block Store (EBS) for persistent block storage
* Provisioning AWS API Gateway to invoke Lambda functions

**Note:** Prior to version 6.2.0, the plugin was called the XL Deploy EC2 plugin.

For information about AWS requirements and the configuration items (CIs) that the plugin supports, refer to the [AWS Plugin Reference](/xl-deploy-xld-aws-plugin/latest/awsPluginManual.html).

## Features

* Create virtual machines on Elastic Compute Cloud (EC2) with a specified Amazon Machine Image (AMI)
* Automatically destroy EC2 instances during undeployment
* Provision a Simple Storage Service (S3) bucket

### Attach an elastic IP address with a non-VPC EC2 instance

To create and attach an elastic IP address with a non-Virtual Private Cloud (VPC) EC2 instance:

1. Go to the **Elastic IP** tab and set **Attach Elastic IP** to `true` and **Elastic IP Domain** to `standard`.
1. A new elastic IP is created and attached to the non-VPC EC2 instance.
1. If the EC2 instance in stopped, the elastic IP is detached and is reattached by the plugin when you restart the EC2 instance.
1. You can set the **Elastic IP** property to `false` during a MODIFY operation. This will detach the elastic IP and will release it.
1. If you perform an undeployment, the elastic IP is released.

### Attach an elastic IP address with VPC EC2 instance

To create and attach an elastic IP with a Virtual Private Cloud (VPC) EC2 instance:

1. Go to the **Elastic IP** tab and set **Attach Elastic IP** to `true` and **Elastic IP Domain** to `standard`.
1. A new elastic IP is created and attached to the default network interface connected to the EC2 instance at `eth0`.
1. If the EC2 instance in restarted, the elastic IP is still attached to the default network interface and does not require to be reattached.
1. You can set the **Elastic IP** property to `false` during a MODIFY operation. This will detach the elastic IP and will release it.
1. If you perform an undeployment, the elastic IP is released.
