---
title: What's in an deployment package
subject:
Packaging
categories:
xl-deploy
tags:
application
package
deployment
weight: 200
no_index: true
---

<html>
<div id="userMap">

            <div class="content"><a href="preparing-your-application-for-xl-deploy.html"><div class="box box1">Preparing your application for XL Deploy</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="understanding-deployable-types.html"><div class="box box1">Understanding deployable types</div></a></div>

            <div class="arrow">→</div>

            <div class="content" id="activeBox"><a href="whats-in-a-deployment-package.html"><div class="box box2">What's in an application deployment package?</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="create-a-deployment-package.html"><div class="box box3">Create a deployment package</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="create-and-verify-the-deployment-plan.html"><div class="box box3">Create and verify the deployment plan</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="troubleshoot-the-deployment-plan.html"><div class="box box5">Troubleshoot the deployment plan</div></a></div>

<div class="clearfix"></div>
</div>
</html>


    

**Note:** This section covers some essential reading on how XL-Deploy works, if you are already familiar with this content, skip to the next page.  

An application deployment package contains *deployables*, which are:

* The physical files (artifacts) that make up a specific version of the application; for example, an application binary, configuration files, or web content
* The middleware resource specifications that are required for the application; for example, a datasource, queue, or timer configuration

The deployment package should contain everything that your application needs to run and that should be removed if your application is undeployed (that is, not resources that are shared among multiple applications).

### Deployment commands and scripts

Generally, the deployment package for an application should not contain deployment commands or scripts. When you prepare a deployment in XL Deploy, a *deployment plan* is automatically generated. This plan contains all of the steps that are needed to deploy your application to a target environment.

### Environment-specific values

An *environment* is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

A deployment package should be independent of the environment in which it will be deployed. This means that the deployables in the package should not contain environment-specific values. You can think of the deployment package as a template for the deployed application. XL Deploy supports placeholders for environment-specific values; these are discussed later in this article.

### Deploying shared resources

You may have resources that are shared by more than one application. You should package these resources so that XL Deploy can deploy them; however, you should not include them in the deployment package for an individual application that uses them. Instead, you should create a deployment package that contains shared resources and use placeholders to refer to these shared resources from your application packages.
