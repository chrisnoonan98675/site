---
title: How do application dependencies work
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- dependency
- microservices
since:
- 5.1.0
---

## Application dependencies in XL Deploy

    ---
    title: Application dependencies in XL Deploy
    categories:
    - xl-deploy
    subject:
    - Dependencies
    tags:
    - application
    - package
    - deployment
    - dependency
    - microservices
    since:
    - 5.1.0
    ---

XL Deploy allows you to define dependencies among different versions of different applications. When you set up the deployment of an application, XL Deploy automatically includes the correct versions of other applications that it depends on and ensures that they are deployed in the correct order.

![Application with dependencies](images/deployment-package-with-dependencies.png)

Application dependencies work with other XL Deploy features such as staging, satellites, rollbacks, updates, and undeployment.

You define dependencies at the *deployment package* level.

### Versioning requirements

To define application dependencies in XL Deploy, you must use [Semantic Versioning (SemVer) 2.0.0](http://semver.org/) for deployment package names.

In the SemVer versioning scheme, a version number is expressed as `major.minor.patch`. For example, a deployment package can have the name 1.2.3, where 1 is the major version, 2 is the minor version, and 3 is the patch version.

### Version ranges

XL Deploy allows you to define ranges for version dependencies. The range formats are:

    {:.table .table-striped}

| Format | Description | Example |
| ------ | ----------- | ------- |
| `[version1,version2]` | The application depends on any version between `version1` and `version2`, including both versions | AppA depends on AppB `[1.0,2.0]`, so AppA works with AppB `1.0`, `1.5.5`, `1.9`, and `2.0` |
| `(version1,version2)` | The application depends on any version between `version1` and `version2`, excluding both versions | AppA depends on AppB `(1.0,2.0)`, so AppA works with AppB `1.5.5` and `1.9`, but does not work with AppB `1.0` or `2.0` |
| `[version1,version2)` | The application depends on any version between `version1` and `version2`, including `version1` and excluding `version2` | AppA depends on AppB `[1.0,2.0)`, so AppA works with AppB `1.0`, `1.5.5`, and `1.9`, but does not work with AppB `2.0` |
| `(version1,version2]` | The application depends on any version between `version1` and `version2`, excluding `version1` and including `version2` | AppA depends on AppB `(1.0,2.0]`, so AppA works with App B `1.5.5`, `1.9`, and `2.0`, but does not work with AppB `1.0` |

### Permissions

When you set up a deployment, XL Deploy checks the permissions of all applications that will be deployed because of dependencies. You must at least have `read` permission on all dependent applications.

For the environment, you must have one or more of the following permissions:

* `deploy#initial`: Permission to deploy a new application
* `deploy#upgrade`: Permission to upgrade a deployed application
* `deploy#undeploy`: Permission to undeploy a deployed application

## Staging applications with dependencies

    USE THIS TO OVERWRITE https://docs.xebialabs.com/xl-deploy/concept/staging-artifacts-in-xl-deploy.html

To ensure that the downtime of your application is limited, XL Deploy can stage artifacts to target hosts before deploying the application.

When staging is enabled, XL Deploy will copy all artifacts to the host before starting the deployment. If the application [depends on other applications](), XL Deploy will also copy the artifacts from the dependent applications. After the deployment completes successfully, XL Deploy will clean up the staging directory.

To enable staging on a host, enter a directory path in the host's **Advanced** > **Staging Directory Path** property.

**Note:** The plugin being used must support staging.

## Deploying applications with dependencies using satellites

    USE THIS TO UPDATE https://docs.xebialabs.com/xl-deploy/concept/getting-started-with-the-satellite-module.html#using-xl-deploy-satellites

XL Deploy satellites can be used to:

* Deploy one or more applications in a cluster that is located around the world

## Deploy an application with dependencies

    USE THIS TO UPDATE https://docs.xebialabs.com/xl-deploy/how-to/deploy-an-application.html

To deploy an application to an environment:

1. Click **Deployment** in the top bar.
1. Locate the application under **Packages** and expand it to see the versions (deployment packages).
1. Locate the environment under **Environments**.
1. Drag the version of the application that you want to deploy and drop it on the environment where you want to deploy it. The application and environment appear in the Workspace.

    If the application has [dependencies](), XL Deploy analyzes them and includes the deployables from the appropriate versions of the dependent applications. For more information, refer to [How XL Deploy checks application dependencies]().

    XL Deploy then automatically maps the deployables in the application to the appropriate containers in the environment.
    
1. Click **Execute** to immediately start the deployment.

Applications are deployed in reverse topological order, to ensure that dependent applications are deployed first.

## Deploy an application with dependencies using the CLI

    USE THIS TO UPDATE https://docs.xebialabs.com/xl-deploy/how-to/perform-a-deployment-using-the-xl-deploy-cli.html

You can use the XL Deploy command-line interface (CLI) to deploy, update, and undeploy applications, including applications with [dependencies]().

## Roll back a deployment of an application with dependencies

    USE THIS TO UPDATE https://docs.xebialabs.com/xl-deploy/how-to/deploy-an-application.html#roll-back-a-deployment

To roll back a deployment that is in a `STOPPED` or `EXECUTED` state, click **Rollback** on the deployment plan. Executing the rollback plan will revert the deployment to the previous version of the deployed application (or applications, if the deployment involved multiple applications because of [dependencies]()). It will also revert the deployeds created on execution.

## Undeploy an application with dependencies

    USE THIS TO UPDATE https://docs.xebialabs.com/xl-deploy/how-to/undeploy-an-application.html

When you undeploy an application that has dependencies, XL Deploy does not automatically undeploy the dependent applications. You must manually undeploy applications, one at a time.

If you try to undeploy an application that other applications depend on, XL Deploy will return an error and you will not be able to undeploy the application.

## Update an application with dependencies

    USE THIS TO UPDATE https://docs.xebialabs.com/xl-deploy/how-to/update-a-deployed-application.html

To update a deployed application:

1. Click **Deployment** in the top bar.
1. Locate the application under **Packages** and expand it to see the versions (deployment packages).
1. Locate the environment under **Environments**.
1. Drag the version of the application that you want to deploy and drop it on the environment where you want to deploy it. The application and environment appear in the **Workspace**.

    XL Deploy analyzes the application's [dependencies]() and the dependencies of the applications in the environment. If the new version does not satisfy the dependencies of the applications that are already deployed, then XL Deploy will not deploy it. For more information, refer to [How XL Deploy checks application dependencies]().

    XL Deploy then automatically maps the deployables in the application to the appropriate containers in the environment.

    **Note:** If the updated application is missing a deployable that was included in the previous deployment, the corresponding deployed item will appear in red.

1. Optionally view or edit the properties of a mapped deployable by double-clicking it or by selecting it and clicking ![Edit deployed](/images/button_edit_deployed.png).
1. Optionally click **Deployment Properties** to select the [orchestrators](/xl-deploy/concept/understanding-orchestrators.html) that XL Deploy should use when generating the deployment plan.
1. Optionally click **Analyze** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step.
1. Click **Next**. The deployment plan appears.
1. Click **Execute** to start the update. If the server does not have the capacity to immediately start executing the plan, the plan will be in a `QUEUED` state until the server has sufficient capacity. 

If a step in the update fails, XL Deploy stops executing the update and marks the step as `FAILED`. Click the step to see information about the failure in the output log.

## How XL Deploy checks application dependencies

    ---
    title: How XL Deploy checks application dependencies
    categories:
    - xl-deploy
    subject:
    - Dependencies
    tags:
    - package
    - deployment
    - dependency
    - microservices
    since:
    - 5.1.0
    ---

<!-- note: this content is copied from https://docs.xebialabs.com/xl-deploy/how-to/define-dependencies-between-versions.html#checking-dependencies -->

When you deploy, update, or undeploy an application, XL Deploy performs a dependency check. It selects the highest possible version in the dependency range of each application.

The dependency check may detect the following issues:

    {:.table .table-striped}

| Issue | Example |
| ----- | ------- |
| While deploying or updating an application, another application that it depends on is not present in the environment at all. | |
| While deploying or updating an application, a version of the application(s) it depends on is present in the environment, but the version is too old or too new. | The application requires application `A` version `[1.0, 2.0)`, but version `2.1` is present. |
| While deploying or updating an application, XL Deploy looks for an application in a certain range, but the version that is actually present is not in `major.minor.patch` format. | Application `Android` version `[2.0, 5.0]` is required, but version `KitKat` is present. |
| While updating an application, an application that is present in the environment depends on that application, but the version that you want to update to is out of the dependency range of that application. | You want to update application `A` to version `2.1`, but the environment contains a version of application `C` that depends on application `A` range `[1.0, 2.0)`. |
| While updating an application, an application that is present in the environment depends on that application, but the version that you want to update to is not in `major.minor.patch` format. | You want to update application `Android` to version `KitKat`, but the installed application `C` requires `Android` to be in range `[2.0, 5.0]`. |
| While undeploying, an installed application depends on the application that you want to undeploy. | |

## Advanced application dependencies example

    ---
    title: Advanced application dependencies example
    categories:
    - xl-deploy
    subject:
    - Dependencies
    tags:
    - package
    - deployment
    - dependency
    - microservices
    since:
    - 5.1.0
    ---

This is an example of an advanced scenario with multiple applications that depend on one another.

Assume that you have five applications called CustomerProfile, Inventory, PaymentOptions, ShoppingCart, and WebsiteFront-End. Their versions and dependencies are as follows:

    {:.table .table-striped}

| Application name | Version | Depends on... |
| ---------------- | ------- | ------------- |
| CustomerProfile | `1.0` | Inventory `[1.0,2.0)` |
| | | PaymentOptions `[1.0,3.0]` |
| Inventory | `1.0` | No dependencies |
| | `1.5` | ShoppingCart `[1.0,2.0]` |
| PaymentOptions | `1.1` | No dependencies |
| | `2.1` | ShoppingCart `[1.5,2.0]` |
| | `3.5` | ShoppingCart `[1.5,2.0]` |
| ShoppingCart | `2.0` | WebsiteFront-End `2.0` |
| | `2.5` | WebsiteFront-End `2.0` |
| WebsiteFront-End | `1.0` | No dependencies |
| | `2.0` | No dependencies |

You can set up a deployment of the latest version of CustomerProfile by dragging it to the Deployment Workspace. XL Deploy automatically adds the deployables from the dependent deployment packages.

![Mapped application with dependencies](images/mapping-with-dependent-apps.png)

**Tip:** Hover the mouse pointer over a deployable to see the deployment package it belongs to.

### How dependent application versions are selected

This is how XL Deploy selected the right application versions:

1. CustomerProfile 1.0: This is the latest version of CustomerProfile, so XL Deploy selected it when you dragged the application to the Deployment Workspace.

2. Inventory 1.5: CustomerProfile 1.0 depends on Inventory [1.0,2.0), so XL Deploy selects the highest version between 1.0 and 2.0, which is Inventory 1.5.

3. PaymentOptions 2.1: CustomerProfile 1.0 depends on PaymentOptions [1.0,3.0], so XL Deploy selects the highest version between 1.0 and 3.0, which is PaymentOptions 2.1.

4. ShoppingCart 2.0: Inventory 1.5 and PaymentOptions 2.1 both depend on ShoppingCart. XL Deploy selects the highest version that satisfies both of their requirements, which is ShoppingCart 2.0.

5. WebsiteFront-End 2.0: ShoppingCart 2.0 requires WebsiteFront-End 2.0, so XL Deploy selects that version.

### Updating a deployed application

You can easily update a deployed application to a new version. For example, to update the Inventory application to version 1.9:

1. Under **Environments** in the Deployment Workspace, right-click Inventory 1.5 and select **Update**.
2. Under **Packages**, locate Inventory 1.9 and drag it to the left side of the deployment workspace.
3. Click **Execute** to execute the deployment.

This deployment is possible because Inventory 1.9 satisfies the CustomerProfile dependency on Inventory `[1.0,2.0)`. Updating Inventory to a version such as 2.1 is not possible, because 2.1 does not satisfy the dependency.
