---
title: How do application dependencies work
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- dependencies
- microservices
---

XL Deploy allows users to deploy application along with its dependencies together. XL Deploy figures out correct deployment order of application dependencies and makes sure your dependencies are not broken. In situations, it can't figure out  correct deployment order manual intervention will be required and IT administrator has to deploy applications manually.

## How to define application dependencies?

To use application dependencies, you have to use [Semver Versioning 2.0](http://semver.org/) for package names as shown below. In Semver versioning scheme, a version number is expressed as **MAJOR.MINOR.PATH**. For example, a deployment package with name 1.2.3, where 1 is the **MAJOR** version, 2 is the **MINOR** version, and 3 is the **PATCH** version.

![Deployment Package Semver Version](images/application_dependencies/deployment_package_semver_versioning.png)

**Application dependencies functionality only work when your package names uses Semver versioning scheme.**

Lets suppose we have two applications `app1` and `app2` where `app1` has dependency on `app2`. Application dependencies are specified at deployment package level. To define dependency of `app2` version 1.0 on `app1` version 1.2.3, you have to specify the dependency on deployment package screen as shown below.

![Define application dependency](images/application_dependencies/application_dependency.png)

Application dependencies also support version ranges using the formats shown below:

1. [version1, version2) : This version string means application is dependent on any version starting from version1 to version2 excluded. For example, if app1 version 1.0 is dependent on app2 version [1.0,2.0) then app1 works with any version starting from 1.0 like 1.0, 1.1,1.9 but not 2.0.

2. [version1, version2] : This version string means application is dependent on any version starting from version1 to version2 included. For example, if app1 version 1.0 is dependent on app2 version [1.0,2.0] then app1 works with any version starting from 1.0 like 1.0, 1.1,1.9 including 2.0 but not versions above 2.0 like 2.1.

3. (version1, version2) : This version string means application is dependent on any version between version1 to version2. For example, if app1 version 1.0 is dependent on app2 version (1.0,2.0) then app1 works with any version between 1.0 and 2.0 like 1.1, 1.2.1, 1.9.9, etc.

4. (version1, version2] : This version string means application is dependent on any version between version1 to version2 included. For example, if app1 version 1.0 is dependent on app2 version (1.0,2.0] then app1 works with any version between 1.0(excluded) to 2.0(included) like 1.1,1.9, .. 2.0.

## How to deploy/upgrade/undeploy an application with its dependencies?

Lets suppose we have five applications app1, app2, app3, app4, and app5. The app1 has dependencies on app2 and app3 as shown below.

![App1 Application Dependencies](images/application_dependencies/application_dependencies_app1.png)

The app2 has two deployment packages with names 1.0, 1.5. The 1.0 version of app2 has no dependency whereas version 1.5 of app2 has dependency on app 4 [1.0,2.0]. This is shown below.

![App2 Application Dependencies](images/application_dependencies/application_dependencies_app2.png)

The app3 has three deployment packages with names 1.1, 2.1, 3.5. The version 1.1 has no dependencies whereas version 2.1 and 3.5 have dependency on app4 version [1.5,2.0]

![App3 Application Dependencies](images/application_dependencies/application_dependencies_app3.png)

The app4 has two deployment packages with name 2.0 and 2.5. Both deployment packages have dependency on app5 version 2.0 as shown below.

![App4 Application Dependencies](images/application_dependencies/application_dependencies_app4.png)

The app5 has two deployment packages 1.0 and 2.0 with no dependencies.

![App5 Application Dependencies](images/application_dependencies/application_dependencies_app5.png)

Now that we have setup application dependencies, we can deploy multiple applications together i.e. if we deploy app1 then all its dependencies along with dependencies of dependencies will also get deployed. Go to the **Deployment** screen and you will see all the applications listed on the left and environment listed on the right.

![Application Deployment Screen](images/application_dependencies/application_depedencies_deployment_screen.png)

Now drag the app1 from left to the deployment workspace and drag `dev` environment from right to the deployment workspace and all the deployables from all the application dependencies will be be resolved as shown below.

![Deployment workspace with Dependencies](images/application_dependencies/deployment_workspace_with_dependencies.png)

Click on the Preview button at the bottom to view the deployment plan

![Deployment workspace with Dependencies](images/application_dependencies/deployment_workspace_preview.png)

It will show you the preview of the deployment plan. As you can see on the preview screen, the deployment order for the applications is app5, app4, app2, app3, and finally app1.

![Preview Screen](images/application_dependencies/preview_screen.png)

Now that you have previewed the plan generated by XL Deploy, you can execute the deployment by pressing the **Execute** button. Once deployed, you will see all tasks ticked green as shown below.

![Execution Screen](images/application_dependencies/execute_deployment_task.png)

## How XL Deploy decide which deployment package version to deploy?

You would have noticed that XL Deploy picked specific deployment package versions for deployment. Lets discuss why XL Deploy did what it did one by one:

1. app1 version 1.0: As we dragged version 1.0 of the app1 and there are no multiple versions of app1 so version 1.0 of app1 is selected.

2. app2 version 1.5: app1 version 1.0 has a dependency on app 2 [1.0,2.0]. XL Deploy will pick the maximum version of app2 within the [1.0,2.0]. app2 has two deployment packages 1.0 and 2.0. XL Deploy selects version 1.5 as it is the maximum version within the [1.0,2.0] range.

3. app3 version 2.1: app1 version 1.0 has a dependency on app 3 [1.0,3.0]. XL Deploy will pick the maximum version of app3 within the [1.0,3.0]. app3 has three deployment packages with names 1.1, 2.1, and 3.5. XL Deploy selects version 2.1 as it is the maximum version within the [1.0,3.0] range.

4. app4 version 2: XL Deploy selects version 2.0 of app4 as it is the maximum version that satisfies dependency of app2 and app3.

5. app5 version 2: app4 has a hard dependency on app5 version 2. As mentioned above, app5 has two versions 1.0 and 2.0. Because app5 has version 2 so XL Deploy picked deployment package with version 2 for deployment.

## Undeploying dependent applications

XL Deploy currently does not support automated undeployment of dependencies. Users of XL Deploy have to undeploy applications manually one by one. If you try to undeploy an application which is a dependency of one or more applications then XL Deploy will not allow you to undeploy the application and will greet you with an error message.


## Managing application dependencies updates##

As mentioned above, `app2` has two versions 1.0 and 1.5 and XL Deploy selected 1.5 version for deployment as that is the maximum version with in the `app1` dependency range [1.0,2.). Now lets suppose a new version 1.9 of `app2` is available and you want to update `app2` to latest version i.e. 1.9. To do that using XL Deploy UI, right click on the deployed `app2/1.9` deployment package in the deployment workspace **Environments** section and click update as shown below.

<img src="images/application_dependencies/update_application.png" height="300" width="300">

Then drag the version 1.9 of the application from the left on the deployment workspace and press automapping button as shown below.

<img
src="images/application_dependencies/automapping_update_app.png" height="300" width="600">

Now to update `app2` to 1.9 version, press the **Execute** button.

> If you try to update an application to a version that does not satisfy the depepency of another application that depends on the first application then XL Deploy will not allow you to update the application. For example, in above scenario if we try to update `app2` to version 2.1 then update will not work as that would break dependency of `app1`.

## Rolling back

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

## xsacefv


    * gui
        * using cli
        * Upgrade - no difference

    * Undeployment -

    * Permissions


* How does XL Deploy deploy dependent applications?
    * How does it decide which version of an application to deploy
    * How does it decide on upgrading currently deployed applications
    * Order of applications in the plan
        * how does this work with orchestration
    * Validation if the desired deployment is allowed
        * will it break other dependencies
        * Circular dependencies
    * How does it behave in combination with Staging/Satellites
    * Rollbacks


* How to go from Composite packages to Application Dependencies
* Todo: Fix the current API documentation because the api changed - migration manual?
