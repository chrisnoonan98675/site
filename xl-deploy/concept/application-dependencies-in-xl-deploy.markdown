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
- XL Deploy 5.0.0
---

XL Deploy allows you to define dependencies among different versions of different applications. When you set up the deployment of an application, XL Deploy automatically includes the correct versions of other applications that it depends on and ensures that they are deployed in the correct order.

Application dependencies work with other XL Deploy features such as staging, satellites, rollbacks, updates, and undeployment.

You define dependencies at the *deployment package* level.

![Application with dependencies](images/deployment-package-with-dependencies.png)

You can define a dependency on an application that does not yet exist in the XL Deploy repository. You can also specify a version range that cannot be met by any versions that are currently in the repository.

This allows you to import applications even before all dependencies can be met; that is, you can import, but not deploy, the `frontend` package before its required `backend` package is ready. However, this means that you must be careful to enter the correct versions.

It is also possible to modify the declared dependencies of a deployment package even after it has been deployed. In that case, XL Deploy will not perform any validation. It is not recommended to modify dependencies after deployment.

## Versioning requirements

To define application dependencies in XL Deploy, you must use [Semantic Versioning (SemVer) 2.0.0](http://semver.org/) for deployment package names.

In the SemVer versioning scheme, a version number is expressed as `major.minor.patch`. For example, a deployment package can have the name 1.2.3, where 1 is the major version, 2 is the minor version, and 3 is the patch version.

## Version ranges

XL Deploy allows you to define ranges for version dependencies. The range formats are:

{:.table .table-striped}
| Format | Description | Example |
| ------ | ----------- | ------- |
| `[version1,version2]` | The application depends on any version between `version1` and `version2`, including both versions | AppA depends on AppB `[1.0,2.0]`, so AppA works with AppB `1.0`, `1.5.5`, `1.9`, and `2.0` |
| `(version1,version2)` | The application depends on any version between `version1` and `version2`, excluding both versions | AppA depends on AppB `(1.0,2.0)`, so AppA works with AppB `1.5.5` and `1.9`, but does not work with AppB `1.0` or `2.0` |
| `[version1,version2)` | The application depends on any version between `version1` and `version2`, including `version1` and excluding `version2` | AppA depends on AppB `[1.0,2.0)`, so AppA works with AppB `1.0`, `1.5.5`, and `1.9`, but does not work with AppB `2.0` |
| `(version1,version2]` | The application depends on any version between `version1` and `version2`, excluding `version1` and including `version2` | AppA depends on AppB `(1.0,2.0]`, so AppA works with App B `1.5.5`, `1.9`, and `2.0`, but does not work with AppB `1.0` |

## Permissions

When you set up a deployment, XL Deploy checks the permissions of all applications that will be deployed because of dependencies. You must at least have `read` permission on all dependent applications.

For the environment, you must have one or more of the following permissions:

* `deploy#initial`: Permission to deploy a new application
* `deploy#upgrade`: Permission to upgrade a deployed application
* `deploy#undeploy`: Permission to undeploy a deployed application

## Dependencies and composite packages

Composite packages cannot declare dependencies on other applications. However, a deployment package can declare a dependency on a composite package. In this case, the actual composite package must to be installed, not just its constituents. 

For example, you want to deploy a deployment package that declares a dependency on composite package `C` version `[1.0, 1.0]`. `C` version `1.0` consists of deployment packages `D` version `3.1` and `E` version `5.2.2`. If `D` `3.1` and `E` `5.2.2` are deployed on the environment but `C` `1.0` is not, then you will not be able to deploy the package.

When you deploy a composite package, the dependency check is skipped. This means that if its constituents declare any dependencies, these will not be checked. That is, in the above situation, if `D` version `3.1` declares any dependencies, the composite package can still be deployed to an empty environment.
