---
title: XL Deploy for developers
subject:
- Packaging
categories:
- xl-deploy
tags:
- application
- package
- deployment
---

XL Deploy is an application release automation (ARA) tool that can deploy applications to development, test, QA, and production environments, all while managing configuration values that are specific to each environment. It is designed to make the process of deploying applications faster, easier, and more reliable. You provide the components that make up your application, and XL Deploy does the rest.

## Key XL Deploy concepts

XL Deploy is based on these key concepts:

* Application—The software that will be deployed in a target system
* Deployable—An artifact such as a file, a folder, or a resource specification that you can add to a deployment package and that contains placeholders for environment-specific values
* Deployment package—The collection of deployables that makes up a specific version of your application
* Environment—A collection of containers (target servers, databases, and so on) to which elements of your packages can be deployed
* Mapping—The task of identifying where each deployment package should be deployed
* Deployment—The task of mapping a specific deployment package to the containers in a target environment and running the resulting deployment plan
* Deployment plan—The steps that are needed to deploy a package to a target environment
* Deployed item—A deployable that has been deployed to a container and contains environment-specific values

## Deployment packages

To deploy an application with XL Deploy, you supply a file called a *deployment package*, or a DAR package. A deployment package contains *deployables*, which are the physical files (artifacts) and resource specifications (datasources, topics, queues, and so on) that make up a specific version of your application.

Unlike formats such as RPM or MSI, DAR packages do not contain deployment commands or scripts. Instead, XL Deploy automatically generates a *deployment plan* that contains all of the deployment steps that are needed.

DAR packages are intended to be environment-independent so that artifacts can be used all the way from development to production. Artifacts and resources in the package can contain customization points (such as placeholders in configuration files or resource attributes) that XL Deploy will replace with environment-specific values during deployment. The values are defined in *dictionaries*.

A DAR package is a ZIP file that contains application files and a manifest file that describes the package content and any resource specifications that are needed. You can create DAR packages in the XL Deploy interface, or you can use a plugin to automatically build packages as part of your delivery pipeline. XL Deploy offers a variety of plugins for tools such as Maven, Jenkins, Team Foundation Server (TFS), and others.

Alternatively, you can use a command line tool such as `zip`, the Java `jar` utility, the Maven `jar` plugin, or the Ant `jar` task to prepare DAR packages.

## Automated deployment plans

With XL Deploy, there is no need for you to create deployment scripts or workflows. When a deployment is created in XL Deploy, a *deployment plan* is created automatically. This plan contains all of the steps that are needed to deploy a specific version of an application to a target environment.

XL Deploy also generates deployment plans when a deployed application needs to be upgraded to a new version, downgraded to an old version, or removed from an environment (called undeploying). 

When it is time to carry out a deployment, XL Deploy executes the deployment plan steps in the required order. XL Deploy compares the deployed application to the one that you want to deploy and generates a plan that only contains the steps that are required, improving the efficiency of application updates.

XL Deploy includes several features that allow you to configure the deployment plan, so you can be sure your application is deployed the way it should be. [Preparing your application for XL Deploy](preparing_your_application_for_XL_Deploy.html) describes how you can take advantage of these features.

Finally, XL Deploy offers automated rollback functionality at every stage of the deployment.
