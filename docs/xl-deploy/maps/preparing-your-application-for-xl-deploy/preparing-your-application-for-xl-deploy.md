---
title: Preparing your application for XL Deploy
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

            <div class="content" id="activeBox" ><a href="preparing-your-application-for-xl-deploy.html"><div class="box box1">Preparing your application for XL Deploy</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="understanding-deployable-types.html"><div class="box box1">Understanding deployable types</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="whats-in-a-deployment-package.html"><div class="box box2">What's in an application deployment package?</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="create-a-deployment-package.html"><div class="box box3">Create a deployment package</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="create-and-verify-the-deployment-plan.html"><div class="box box3">Create and verify the deployment plan</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="troubleshoot-the-deployment-plan.html"><div class="box box5">Troubleshoot the deployment plan</div></a></div>

<div class="clearfix"></div>
</div>
</html>


XL Deploy uses the Unified Deployment Model (UDM) to structure deployments. In this model, deployment packages are containers for complete application distribution. They include application artifacts (EAR files, static content) as well as resource specifications (datasources, topics, queues, and so on) that the application needs to run.

A Deployment ARchive, or DAR file, is a ZIP file that contains application files and a manifest file that describes the package content. In addition to packages in a compressed archive format, XL Deploy can also import _exploded DARs_ or archives that have been extracted.

Packages should be independent of the target environment and contain customization points (for example, placeholders in configuration files) that supply environment-specific values to the deployed application. This enables a single artifact to make the entire journey from development to production.
