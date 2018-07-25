---
title: Understanding deployable types
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

            <div class="content" id="activeBox"><a href="understanding-deployable-types.html"><div class="box box1">Understanding deployable types</div></a></div>

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




Every deployable in a package has a *configuration item (CI) type* that:

* Describes the deployable, and
* Determines the steps that XL Deploy will add to the deployment plan when you map the item to a target *container*

The plugins that are included in your XL Deploy installation determine the CI types that are available for you to use.

### Exploring CI types

Before you create a deployment package, you should explore the CI types that are available. To do so in the XL Deploy interface, first import a sample deployment package:

1. Go to **Explorer**.
1. Hover over **Applications**, click ![Menu button](../../images/menuBtn.png), and select **Import** > **From XL Deploy server**.
1. Select the **PetClinic-ear/1.0** sample package.
1. Click **Import**. XL Deploy imports the package.
1. Click **Close**.
1. Click ![image](../images/refresh_button.png) to refresh the CI Library.
1. Expand an application, hover over a deployment package, click ![Menu button](../images/menuBtn.png), and select **New** to see the CI types that are available.

### How do I know which type to use?

In most cases, the CI types that you need to use are straightforwardly determined by the components of your application and by the target middleware. XL Deploy also includes types for common application components such as files that simply need to be moved to target servers.

For each type, you can specify properties that represent attributes of the artifact or resource to be deployed, such as the target location for a file or a JDBC connection URL for a datasource. If the value of a property is the same for all target environments, you can set the value in the deployment package itself.

If the value of a property varies across your target environments, you should use a placeholder for it. XL Deploy automatically resolves placeholders based on the environment to which you are deploying the package.
