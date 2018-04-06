---
title: Introduction to the XL Deploy Cloud Foundry plugin
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- cloud foundry
- cloud
- plugin
since:
- XL Deploy 8.0.0
---

The XL Deploy Cloud Foundry plugin supports:

* Creating spaces
* Deploying Cloud Foundry applications
* Creating routes for a Cloud Foundry application
* Binding services with a Cloud Foundry application
* Creating Cloud Foundry services
* Deploying Cloud Foundry application using a manifest file

{% comment %} Insert link to CI reference {% endcomment %}

## Create the Cloud Foundry organization

1. Under **Infrastructure**, create a `cloudFoundry.Organization` CI.
2. Specify the following properties:
    * **Organization Name**: Organization name on the Cloud Foundry server.
    * **API Endpoint**: API endpoint of Cloud Foundry server (for example, `api.run.pivotal.io`).
    * **Username**: User name to use for authentication.
    * **Password**: Password to use for authentication.

## Provision a Cloud Foundry space using a CI

1. Under **Applications**, create an application (`udm.Application`) and deployment package (`udm.DeploymentPackage`).
2. Under the deployment package, create a `cloudFoundry.SpaceSpec` CI. Specify the following properties:
    * **Space Name**: Space name to use. If not provided, the CI name will be used as the space name.
    * Alternatively we can also use already created spaces by creating them on `cloudFoundry.Organization` by clicking on it in Infrastructure.

##  Push an application to Cloud Foundry

1. Under **Applications**, create an application (`udm.Application`) and deployment package (`udm.DeploymentPackage`).
2. Under the deployment package, create a `cloudFoundry.AppSpec` CI. Specify the following required properties:
    * **App Name**: Application name to use.
    * **Build Pack**: Build pack to use (for example, Java, Go, Binary, and so on). To specify a custom build pack, fill in the **Custom Build Pack URL** property instead.

## Configure Cloud Foundry services

1. Under **Applications**, create an application (`udm.Application`) and deployment package (`udm.DeploymentPackage`).
2. Under the deployment package, create a `cloudFoundry.Services` CI.
3. Specify the **Service Type**.

## Push an application to Cloud Foundry using a manifest

1. Under **Applications**, create an application (`udm.Application`) and deployment package (`udm.DeploymentPackage`).
2. Under the deployment package, create a `cloudFoundry.ManifestModuleSpec` CI.
3. In the **File** property, specify a ZIP file that contains the manifest file and artifacts required to create the application.
