---
title: How XL Deploy checks application dependencies
categories:
xl-deploy
subject:
Dependencies
tags:
package
deployment
dependency
microservices
since:
XL Deploy 5.1.0
weight: 121
---

When you deploy, update, or undeploy an application, XL Deploy performs a [dependency check](/xl-deploy/concept/application-dependencies-in-xl-deploy.html), which may detect the following issues:

<table class="table table-striped table-bordered">
    <thead>
        <tr>
            <th>Message</th>
            <th>Possible cause</th>
            <th>Example</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Error while trying to resolve the dependencies of application &lt;name&gt;. Cannot find an application with the name &lt;name&gt;.</td>
            <td>While deploying or updating an application, another application that it depends on is not present in the environment at all.</td>
            <td>The application requires application AppA version [1.0.0, 2.0.0), but AppA is not present.</td>
        </tr>
        <tr>
            <td rowspan="3">Error while trying to resolve the dependencies of application &lt;name&gt;. Cannot find matching version of application &lt;name&gt; for version range &lt;range&gt;.</td>
            <td>While deploying or updating an application, a version of the application(s) it depends on is present in the environment, but the version is too old or too new.</td>
            <td>The application requires application AppA version [1.0.0, 2.0.0), but version 2.1.0 is present.</td>
        </tr>
        <tr>
            <td>While deploying or updating an application, XL Deploy looks for an application in a certain range, but the version that is actually present is not in major.minor.patch format.</td>
            <td>Application AppAndroid version [2.0.0, 5.0.0] is required, but version KitKat is present.</td>
        </tr>
        <tr>
            <td>While updating an application, an application that is present in the environment depends on that application, but the version that you want to update to is not in major.minor.patch format.</td>
            <td>You want to update application AppAndroid to version KitKat, but the installed application AppC requires AppAndroid to be in range [2.0.0, 5.0.0].</td>
        </tr>
        <tr>
            <td>Application &lt;name&gt; cannot be upgraded, because the deployed application &lt;name&gt; depends on its current version. The required version range is &lt;range&gt;.</td>
            <td>While updating an application, an application that is present in the environment depends on that application, but the version that you want to update to is out of the dependency range of that application.</td>
            <td>You want to update application AppA to version 2.1.0, but the environment contains a version of application AppC that depends on AppA range [1.0.0, 2.0.0).</td>
        </tr>
        <tr>
            <td>Application &lt;name&gt; cannot be undeployed, because the deployed application &lt;name&gt; depends on its current version. The required version range is &lt;range&gt;.</td>
            <td>While undeploying, an installed application depends on the application that you want to undeploy.</td>
            <td>You want to undeploy application AppA 1.5.0, but the environment contains a version of application AppC that depends on AppA range [1.0.0, 2.0.0).</td>
        </tr>
    </tbody>
</table>

 In XL Deploy 5.1.x and 5.5.x, XL Deploy always selects the highest possible version in the dependency range of each application that will be deployed because of dependencies. In XL Deploy 6.0.0 and later, XL Deploy uses the **Dependency Resolution** property of the deployment package that you choose when setting up the deployment to select the other application versions. For more information, refer to [Application dependencies in XL Deploy](/xl-deploy/concept/application-dependencies-in-xl-deploy.html#how-does-xl-deploy-select-the-versions-to-deploy).
