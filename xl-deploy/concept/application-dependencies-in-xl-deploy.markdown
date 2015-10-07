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
- XL Deploy 5.1.0
---

XL Deploy allows you to define dependencies among different versions of different applications. When you set up the deployment of an application, XL Deploy automatically includes the correct versions of other applications that it depends on and ensures that they are deployed in the correct order.

Application dependencies work with other XL Deploy features such as staging, satellites, rollbacks, updates, and undeployment.

You define dependencies at the *deployment package* level.

## Simple deployment examples

Assume that you have three applications called WebsiteFrontEnd, Inventory, and PaymentOptions. WebsiteFrontEnd version 1.0 requires Inventory version 2.0.1. To define this dependency in the XL Deploy interface, you would:

1. Go to the Repository.
2. Expand **Applications** > **WebsiteFrontEnd** and double-click the 1.0 deployment package.
3. In the **Application Dependencies** section, add the key *Inventory* and the value *[2.0.1,2.0.1]*. This is the [Semantic Versioning (SemVer)](http://semver.org/) format that indicates that WebsiteFrontEnd 1.0 depends on Inventory 2.0.1, and only 2.0.1 (not any older or newer version).

    ![Application with dependencies](images/app-dependencies-example-01.png)

Inventory 2.0.1, in turn, requires PaymentOptions version 3.5. To define this dependency, you would:

1. Go to the Repository.
2. Expand **Applications** > **Inventory** and double-click the 2.0.1 deployment package.
3. In the **Application Dependencies** section, add the key *PaymentOptions* and the value *[3.5,3.5]*.

    ![Application with dependencies](images/app-dependencies-example-02.png)

When you set up a deployment of WebsiteFrontEnd 1.0, XL Deploy will automatically include Inventory 2.0.1 and PaymentOptions 3.5.

For an extended example of dependencies, refer to [Advanced application dependencies example](/xl-deploy/concept/advanced-application-dependencies-example.html).

## When can you set dependencies?

You can define a dependency on an application that does not yet exist in the XL Deploy repository. You can also specify a version range that cannot be met by any versions that are currently in the repository.

This allows you to import applications even before all dependencies can be met; that is, you can import, but not deploy, the *frontend* package before its required *backend* package is ready. However, this means that you must be careful to enter the correct versions.

It is also possible to modify the declared dependencies of a deployment package even after it has been deployed. In that case, XL Deploy will not perform any validation. It is not recommended to modify dependencies after deployment.

## Versioning requirements

To define application dependencies in XL Deploy:

* You must use [Semantic Versioning (SemVer) 2.0.0](http://semver.org/) for deployment package names
* Deployment package names can only contain numbers and periods (`.`); letters and hyphens (`-`) are not currently supported

In the SemVer versioning scheme, a version number is expressed as `major.minor.patch`. For example, a deployment package can have the name 1.2.3, where 1 is the major version, 2 is the minor version, and 3 is the patch version.

## Version ranges

XL Deploy allows you to define ranges for version dependencies. The range formats are:

{:.table .table-striped}
| Format | Description | Example |
| ------ | ----------- | ------- |
| `[version1,version2]` | The application depends on any version between `version1` and `version2`, including both versions (note that `version1` and `version2` can be the same value) | AppA depends on AppB `[1.0,2.0]`, so AppA works with AppB `1.0`, `1.5.5`, `1.9`, and `2.0` |
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
