---
title: Get started with XL Deploy
---

XL Deploy is the only application release automation solution that is agentless across all target platforms. Connect to Microsoft Windows and Unix target systems using proven, industry-standard remote protocols; no agent installation and maintenance, no overhead on the target systems, no firewall ports to be opened, and no security reviews.

## Step 1 Download XL Deploy

If you're new to XL Deploy, you can [try it for free](https://xebialabs.com/products/xl-deploy/trial/)! After signing up for a free trial, you will receive a license key by email.

If you've already purchased XL Deploy, you can download the software, XL Deploy plugins, and your license at the [XebiaLabs Software Distribution site](https://dist.xebialabs.com).

For more information about licenses, refer to [XL Deploy licensing](/xl-deploy/concept/xl-deploy-licensing.html).

## Step 2 Install XL Deploy

To install the XL Deploy server, ensure that you meet the [system requirements](/xl-deploy/concept/requirements-for-installing-xl-deploy.html), then follow the instructions at [Install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html). If you want to run XL Deploy as a service or daemon, follow the instructions at [Install XL Deploy as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html).

XL Deploy also includes a command-line interface (CLI) that you can use to automate tasks. To install the CLI, refer to [Install the XL Deploy CLI](/xl-deploy/how-to/install-xl-deploy.html#install-the-xl-deploy-cli) and [Connect to XL Deploy from the CLI](/xl-deploy/how-to/connect-to-xl-deploy-from-the-cli.html).

## Step 3 Learn the basics

To learn the basics of XL Deploy, check out:

* [Visibility, Automation, and Control with XL Release and XL Deploy](https://www.youtube.com/watch?v=vyAGFcFjdxI)
* [Understanding XL Deploy's architecture](/xl-deploy/concept/xl-deploy-architecture.html)
* [Key XL Deploy concepts](/xl-deploy/concept/key-xl-deploy-concepts.html)
* [Deployment overview and the Unified Deployment Model (UDM)](/xl-deploy/concept/deployment-overview-and-unified-deployment-model.html)
* [Our video series about getting started with XL Deploy](/videos/index.html#xl-deploy)

Application developers should read:

* [XL Deploy for developers](/xl-deploy/concept/xl-deploy-for-developers.html)
* [Preparing your application for XL Deploy](/xl-deploy/concept/preparing-your-application-for-xl-deploy.html)

After you're familiar with XL Deploy's key concepts, you can dive deeper into advanced topics:

* [Understanding deployables and deployeds](/xl-deploy/concept/understanding-deployables-and-deployeds.html)
* [Understanding the XL Deploy planning phase](/xl-deploy/concept/understanding-the-xl-deploy-planning-phase.html)
* [Understanding tasks in XL Deploy](/xl-deploy/concept/understanding-tasks-in-xl-deploy.html)
* [Understanding archives and folders in XL Deploy](/xl-deploy/concept/understanding-archives-and-folders-xl-deploy.html)

## Step 4 Connect to your infrastructure

Before XL Deploy can deploy your applications, it needs to connect to the hosts and middleware in your infrastructure. For information about connecting to Microsoft Windows and Unix hosts, refer to [Connect XL Deploy to your infrastructure](/xl-deploy/how-to/connect-xl-deploy-to-your-infrastructure.html).

For a walkthrough of the process of connecting to middleware, refer to:

* [Deploy your first application on IBM WebSphere Application Server](/xl-deploy/how-to/deploy-your-first-application-on-websphere.html) ([video version](https://www.youtube.com/watch?v=XEh5QGcpn5U))
* [Deploy your first application on Apache Tomcat](/xl-deploy/how-to/deploy-your-first-application-on-tomcat.html) ([video version](https://www.youtube.com/watch?v=rNVkeHeVB-Q))
* [Deploy your first application on JBoss EAP 6 or JBoss AS/WildFly 7.1+](/xl-deploy/how-to/deploy-your-first-application-on-wildfly.html) ([video version](https://www.youtube.com/watch?v=o_J3ov9jAzI))
* [Deploy your first application on Oracle WebLogic](/xl-deploy/how-to/deploy-your-first-application-on-weblogic.html)
* [Deploy your first application on Microsoft IIS](/xl-deploy/how-to/deploy-your-first-application-on-iis.html)
* [Deploy your first application on GlassFish](/xl-deploy/how-to/deploy-your-first-application-on-glassfish.html)

## Step 5 Define environments

In XL Deploy, an environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

To define the environments that you need, follow the instructions in [Create an environment in XL Deploy](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html).

## Step 6 Import or create an application

To deploy an application with XL Deploy, you supply a deployment package that represents a version of the application. The package contains the files (artifacts) and middleware resources that XL Deploy can deploy to a target environment. For detailed information about what a deployment package contains, refer to [Preparing your application for XL Deploy](/xl-deploy/concept/preparing-your-application-for-xl-deploy.html).

You can add a deployment package to XL Deploy by creating it in the XL Deploy interface or by importing a Deployment Archive (DAR) file. To create or import a package, follow the instructions in [Add a package to XL Deploy](/xl-deploy/how-to/add-a-package-to-xl-deploy.html).

## Step 7 Deploy the application

After you have defined your infrastructure, defined an environment, and imported or created an application, you can perform the initial deployment of the application to an environment. [Deploy an application](/xl-deploy/how-to/deploy-an-application.html) describes the process.
