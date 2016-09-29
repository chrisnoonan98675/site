---
title: Getting started with the satellite module
categories:
- xl-deploy
subject:
- Satellite
tags:
- remoting
- satellite
since:
- XL Deploy 5.0.0
weight: 300
---

The XL Deploy satellite module lets you to execute deployments on remote data centers. It includes:

* An efficient system for transferring files
* Executing deployment tasks transparently on satellite servers
* Light process execution on data centers
* Secure communication between XL Deploy and satellite servers based on Transport Layer Security (TLS)


## Using XL Deploy satellites

XL Deploy satellites can be used to:

* Deploy an application in a cluster that is located around the world
* Deploy to production servers in a dedicated subnet that XL Deploy would normally not be allowed access for security reasons
* Deploy to an infrastructure that contains both Unix and Microsoft Windows hosts, with easier authentication between them

![image](images/xl-deploy-satellite-feature.png)

Satellites are stand-alone processes that are located in your infrastructure, close to your hosts. XL Deploy orchestrates your deployments by driving the satellites and uploading files (artifacts) and commands to them. You can also use a satellite as an entry point into a secure subnet.

## Getting started with a satellite

To get started deploying applications using a satellite:

1. [Configure XL Deploy](/xl-deploy/how-to/configure-xl-deploy-to-communicate-with-satellites.html) to communicate with satellites
1. [Install and configure](/xl-deploy/how-to/install-and-configure-a-satellite-server.html) the software on the satellite server
1. [Open the necessary ports](/xl-deploy/how-to/install-and-configure-a-satellite-server.html) on the satellite server
1. [Create a configuration item (CI)](/xl-deploy/how-to/add-a-satellite-server-to-xl-deploy.html) for the satellite in XL Deploy
1. [Verify the connection](/xl-deploy/how-to/ping-a-satellite-from-xl-deploy.html) by pinging the satellite from XL Deploy
1. [Synchronize plugins](/xl-deploy/how-to/synchronize-plugins-with-a-satellite-server.html) between XL Deploy and the satellite
1. [Define which satellite XL Deploy should use](/xl-deploy/how-to/assign-a-satellite-to-a-host.html) when deploying an application to a given host

You can now create a deployment plan and [see the deployment steps that are added for the satellite](/xl-deploy/concept/how-satellites-affect-the-deployment-plan.html).
