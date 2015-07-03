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
    - package
    - deployment
    - dependency
    - microservices
    since:
    - 5.1.0
    ---

XL Deploy allows you to define dependencies among different versions of different applications. When you set up the deployment of an application, XL Deploy automatically includes the correct versions of other applications that it depends on and ensures that they are deployed in the correct order.

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

### Staging applications with dependencies [WIP]

Staging functionality can be used with dependencies as well. In case of application with dependencies, the system will first analyze all the artifacts that must be staged and upload them to staging directory before first deployment started. This will reduce the delays between dependencies deployment and overall downtime of the deployed applications.

To enable staging, you need to specify **"Staging Directory Path"** on **"Advanced"** tab of the host:

<img
src="images/application_dependencies/application_dependencies_staging1.png">

After that, on the next deployment, you will see an additional staging and cleanup steps at the beginning and the end of the plan:

<img
src="images/application_dependencies/application_dependencies_staging2.png">

### Deploying applications with dependencies using satellites [WIP]

Applications with dependencies can be deployed with the help of satellite.
The process is the same as deploying single application.
Just enable satellite support on your host:

<img
src="images/application_dependencies/application_dependencies_satellite1.png">

As a result, during deployment you will see an additional phases in your deployment plan:

<img
src="images/application_dependencies/application_dependencies_satellite2.png">

The first phase is actually preparing deployment task on satellite, checking plugins and doing other heavy lifting that may take significant time to complete.

For each particular application deployment XL-Deploy will test satellite connectivity and finally clean up satellite at the end.

## Deploy an application with dependencies [WIP]

    ADD THIS TO https://docs.xebialabs.com/xl-deploy/how-to/deploy-an-application.html

For more information, refer to [How XL Deploy checks application dependencies]().

## Roll back a deployment of an application with dependencies [WIP]

    UPDATE SECTION https://docs.xebialabs.com/xl-deploy/how-to/deploy-an-application.html#roll-back-a-deployment

It is possible to rollback application with dependencies deployment operations.

Let's look at the simple example. Suppose that we have 3 applications:

`app1` of version 1.0, `app2` of version 2.0 and `app3` or version 1.0

The dependency chain can be represented like this:
`app1 (1.0) -> app2 (2.0) -> app3 (1.0)`

Now, let's execute the deployment of `app1 (1.0)` and it's dependencies and then click **"Rollback"** button:

<img src="images/application_dependencies/application_dependencies_rollback_1.png">

As a result, you will see a rollback plan for application and it's dependencies. Please notice, that deployment order is reverted. This done explicitly not to break the dependencies during un-deployment:

<img src="images/application_dependencies/application_dependencies_rollback_2.png">

It's also possible to rollback partial deployments. Let's assume, that during deployment of application `app1` and it's dependencies, we put **"pause"** step somewhere in the middle of dependent `app2` and decided to roll-back:

<img src="images/application_dependencies/application_dependencies_rollback_3.png">

After clicking **"Rollback"** button, you will see the plan that consists of only an artifacts and applications that **where already deployed**. Please notice, that part of `app2` artifacts and the whole `app1` is not inclided in the plan:

<img src="images/application_dependencies/application_dependencies_rollback_4.png">

## Undeploy an application with dependencies

    ADD THIS TO https://docs.xebialabs.com/xl-deploy/how-to/undeploy-an-application.html

When you undeploy an application that has dependencies, XL Deploy does not automatically undeploy the dependent applications. You must manually undeploy applications, one at a time.

If you try to undeploy an application that other applications depend on, XL Deploy will return an error and you will not be able to undeploy the application.

## Update an application with dependencies [WIP]

    ADD THIS TO https://docs.xebialabs.com/xl-deploy/how-to/update-a-deployed-application.html

As mentioned above, `app2` has two versions 1.0 and 1.5 and XL Deploy selected 1.5 version for deployment as that is the maximum version with in the `app1` dependency range [1.0,2.). Now lets suppose a new version 1.9 of `app2` is available and you want to update `app2` to latest version i.e. 1.9. To do that using XL Deploy UI, right click on the deployed `app2/1.9` deployment package in the deployment workspace **Environments** section and click update as shown below.

<img src="images/application_dependencies/update_application.png" height="300" width="300">

Then drag the version 1.9 of the application from the left on the deployment workspace and press automapping button as shown below.

<img
src="images/application_dependencies/automapping_update_app.png" height="300" width="600">

Now to update `app2` to 1.9 version, press the **Execute** button.

> If you try to update an application to a version that does not satisfy the dependency of another application that depends on the first application then XL Deploy will not allow you to update the application. For example, in above scenario if we try to update `app2` to version 2.1 then update will not work as that would break dependency of `app1`.

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

<!-- note: this content is copied from https://docs.xebialabs.com/xl-deploy/how-to/define-dependencies-between-versions.html#checking-dependencies 
question: should we use the term 'deployed' instead of 'installed' here? -->

When you deploy, update, or undeploy an application, XL Deploy performs a dependency check. This may detect the following issues:

{:.table .table-striped}
| Issue | Example |
| ----- | ------- |
| While installing or updating an application, another application that it depends on is not installed at all. | |
| While installing or updating an application, a version of the application(s) it depends on is installed, but it is too old or too new. | The application requires application `A` version `[1.0, 2.0)`, but version `2.1` is installed. |
| While installing or updating an application, XL Deploy looks for an application in a certain range, but the version that is actually installed is not in X.X.X format. | Application `Android` version `[2.0, 5.0]` is required, but the installed version is `KitKat`. |
| While updating an application, an installed application depends on that application, but the version that you want to update to is out of the dependency range of that application. | You want to update application `A` to version `2.1`, but a version of `C` is installed that depends on application `A` range `[1.0, 2.0)`. |
| While updating an application, an installed application depends on that application, but the version that you want to update to is not in X.X.X format. | You want to update application `Android` to version `KitKat`, but the installed version application `C` requires `Android` to be in range `[2.0, 5.0]`. |
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

    ADD SCREENSHOT OF MAPPING HERE

**Tip:** Hover the mouse pointer over a deployable to see the deployment package it belongs to.

### Selecting dependent application versions

This is how XL Deploy selected the right application versions:

1. CustomerProfile 1.0: This is the latest version of CustomerProfile, so XL Deploy selected it when you dragged the application to the Deployment Workspace.

2. Inventory 1.5: CustomerProfile 1.0 depends on Inventory [1.0,2.0), so XL Deploy selects the highest version between 1.0 and 2.0, which is Inventory 1.5.

3. PaymentOptions 2.1: CustomerProfile 1.0 depends on PaymentOptions [1.0,3.0], so XL Deploy selects the highest version between 1.0 and 3.0, which is PaymentOptions 2.1.

4. ShoppingCart 2.0: Inventory 1.5 and PaymentOptions 2.1 both depend on ShoppingCart. XL Deploy selects the highest version that satisfies both of their requirements, which is ShoppingCart 2.0.

5. WebsiteFront-End 2.0: ShoppingCart 2.0 requires WebsiteFront-End 2.0, so XL Deploy selects that version.

## Remaining items

* How does XL Deploy calculate dependencies during mapping (algorithm)
* Deployment via the CLI
* Permissions
* How does XL Deploy deploy dependent applications?
    * Order of applications in the plan
        * how does this work with orchestration
    * Validation if the desired deployment is allowed
        * will it break other dependencies
* Todo: Fix the current API documentation because the api changed - migration manual?
