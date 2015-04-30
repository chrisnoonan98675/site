---
title: Define dependencies between application versions
categories:
- xl-deploy
subject:
- Packaging
tags:
- package
- application
- microservices
- dependency
since:
- 5.0.0
---

In XL Deploy 5.0.0 and later, you can define dependencies between application versions. This is done by setting the _Application Dependencies_ (`applicationDependencies`) property of a deployment package (`udm.DeploymentPackage`).
 
The property allows you to define the name of the application to depend on and the required version range. The version range is specified in [OSGI SemVer format](http://www.osgi.org/wiki/uploads/Links/SemanticVersioning.pdf).

For example:

![Application dependencies](images/application-dependencies.png)

In this case, `App2/2.0` requires that `App1` is present in the same environment with a version number from 1.0 up to 2.0 (excluding 2.0).

## Defining dependencies

You can declare a dependency on an application that does not yet exist in the XL Deploy repository. You can also specify a version range that cannot be met by any versions that are currently in the repository.

This allows you to import applications even before all dependencies can be met; that is, you can import, but not deploy, the `frontend` package before its required `backend` package is ready. However, this means that you must be careful to enter the correct versions.

It is also possible to modify the declared dependencies of a deployment package even after it has been deployed. In that case, XL Deploy will not perform any validation. It is not recommended to modify dependencies after deployment.

## Checking dependencies

When you deploy, update, or undeploy an application, XL Deploy performs a dependency check. This may detect the following issues:

{:.table .table-striped}
| Issue | Example |
| ----- | ------- |
| While installing or updating an application, another application that it depends on is not installed at all. | |
| While installing or updating an application, a version of the application(s) it depends on is installed, but it is too old or too new. | The application requires application `A` version `[1.0, 2.0)`, but version `2.1` is installed. |
| While installing or updating an application, XL Deploy depends on an application in a certain range, but the version that is actually installed is not in X.X.X format. | Application `Android` version `[2.0, 5.0]` is required, but the installed version is `KitKat`. |
| While updating an application, some installed application depends on that application, but the version that you want to update to is out of the dependency range of that application. | You want to update application `A` to version `2.1`, but a version of `C` is installed that depends on application `A` range `[1.0, 2.0)`. |
| While updating an application, an installed application depends on that application, but the version that you want to update to is not in X.X.X format. | You want to update application `Android` to version `KitKat`, but the installed version application `C` requires `Android` to be in range `[2.0, 5.0]`. |
| While undeploying, an installed application depends on the application that you want to undeploy. |

## Dependencies and composite packages

Composite packages cannot declare dependencies on other applications. However, a deployment package can declare a dependency on a composite package. In this case, the actual composite package must to be installed, not just its constituents. 

For example, you want to deploy a deployment package that declares a dependency on composite package `C` version `[1.0, 1.0]`. `C` version `1.0` consists of deployment packages `D` version `3.1` and `E` version `5.2.2`. If `D` `3.1` and `E` `5.2.2` are deployed on the environment but `C` `1.0` is not, then you will not be able to deploy the package.

When you deploy a composite package, the dependency check is skipped. This means that if its constituents declare any dependencies, these will not be checked. That is, in the above situation, if `D` version `3.1` declares any dependencies, the composite package can still be deployed to an empty environment.
