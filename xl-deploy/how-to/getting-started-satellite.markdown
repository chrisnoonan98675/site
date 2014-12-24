---
title: Getting started with satellite
categories:
- xl-deploy
subject:
- Satellite
tags:
- remoting
- satellite
---

## Use case
You want to deploy your application in your cluster all around the world? For some security reasons, XL-Deploy must not have access to production servers in a dedicated subnet? You have an heterogeneous infrastructure with both Windows and Unix hosts and authentication in between is painful?

![image](images/XLSatellitegraphic_art.fw.png) 

XL-Deploy fulfills the following requests with satellites. Satellites are standalone processes. They are located inside your infrastructure, close to your hosts. XL Deploy orchestrates your deployments in various situations by driving satellites and uploading files and commands to it. It can be your entry point in a secure subnet, cache the artifact files and can be automatically updated.
You can find more information about what are the features around XL Deploy satellite [What is a satellite](/xl-deploy/concept/what-is-xl-deploy-satellite.html)?

## The notorious Hello, World!
If you want to do your deployments using satellites inside your infrastructure, these are the following steps to follow:

* You first need to [install from the binaries and run a satellite in your infrastructure](/xl-deploy/how-to/install-and-configure-a-satellite-server.html)

* XL-Deploy communicates with satellites throught a protocol over TCP. Two ports are needed to be opened on a satellite. One for the artifacts uploading, the other one for command handling. You may need to configure satellite communication ports and insure that firewalls are opened for outgoing traffic from XL-Deploy to satellites. Please check our documentation on [satellite configuration](/xl-deploy/how-to/install-and-configure-a-satellite-server.html) for the complete configuration of a satellite process.

* From a running instance of XL-Deploy, you now need to create a [Configuration Item (CI) representing your satellite](/xl-deploy/how-to/add-a-satellite-to-xl-deploy.html) in the **Repository** view of XL Deploy application. A satellite CI can be created as an **Infrastructure CI**. You basically need to give it a name, an IP address and the port used for command handling.

* You can now check your configuration by [pinging the satellite from a Control Task](/xl-deploy/how-to/ping-satellite-from-xl-deploy.html) on your satellite CI.

* Once a satellite can be reached from XL-Deploy, you need [to synchronise plugins version to this instance](/xl-deploy/how-to/synchronize-plugins-with-xl-satellite-server.html) throught a Control Task on the Satellite CI.

* The last step is to tell to XL-Deploy which satellite can be used when deploying an application to a given host. This is done by [attaching a satellite to a host](/xl-deploy/how-to/attach-a-satellite-server-to-a-ci.html).

* Create a deployment plan to your favorite middleware on your infrastructure and let's run it. You'll see some dedicated steps in your plan for the satellite process. All the detail of this are detailled in [how satellites impact a deployment plan](/xl-deploy/concept/impact-of-satellite-on-deployment-plan.html).


##Advanced reading

* Read [Configure satellite feature in XL Deploy](/xl-deploy/how-to/configure-satellite-in-xl-deploy.html)
* Read [Configure a secure communication with satellite](/xl-deploy/how-to/configure-secure-communication-with-a-satellite.html)
* Read [How to display satellite metrics](/xl-deploy/how-to/configure-xl-deploy-satellite-metrics.html)
* Learn [How to develop a custom step to run on a satellite](/xl-deploy/how-to/create-custom-step-running-with-satellite.html)
* Checkout the [Troubleshooting](/xl-deploy/how-to/troubleshoot-with-satellite.html) secution for more help.