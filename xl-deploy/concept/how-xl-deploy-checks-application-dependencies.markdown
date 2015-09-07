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
