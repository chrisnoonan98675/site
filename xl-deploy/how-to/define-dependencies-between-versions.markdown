---
title: Define dependencies between versions
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

You can define dependencies between application versions in XL Deploy. This is done by setting the _Application Dependencies_ (`applicationDependencies`) property of `udm.DeploymentPackage`. Composite packages cannot define dependencies, but you can depend on a composite package; [see below](#Composite-packages).
 
The property is of type `map_string_string`, where the key specifies a name of the application to depend on, and the value specifies the required version range. The version range is specified in [OSGI SemVer format](http://www.osgi.org/wiki/uploads/Links/SemanticVersioning.pdf).

For example:

![Application dependencies](images/application-dependencies.png)

In this case, `App2/2.0` requires that `App1` is present on the same environment with version number from 1.0 up to 2.0 (excluding 2.0).

## Dependency checking

When you deploy, update, or undeploy an application, a dependency check will take place. This may detect the following problems:

* While installing or updating, some application it depends on is not installed at all.
* While installing or updating, a version of the application(s) it depends on is installed, but it is too old or too new. That is, XL Deploy requires application `A` version `[1.0, 2.0)`, but version `2.1` is installed.
* While installing or updating, XL Deploy depends on an application in a certain range, but the actually installed version is not in X.X.X format. That is, application `Android` version `[2.0, 5.0]` is required, but the installed version is `KitKat`.
* While updating, some installed application depends on us, but the version we update to is out of the dependency range of that application. E.g. we want to update application `A` to version `2.1`, but a version of `C` is installed that depends on application `A` range `[1.0, 2.0)`.
* While updating, some installed application depends on us, but the version we update to is not in X.X.X format. E.g. we want to update application `Android` to version `KitKat` but the installed version application `C` requires `Android` to be in range `[2.0, 5.0]`.
* While undeploying, some installed application depends on us.

Note that it is possible to declare a dependency on an application that is not (yet) in the XL Deploy repository, or to specify a version range that cannot be met by any versions currently in the repository. This allows you to import applications even before all dependencies can be met; that is, you can import, but not deploy, the `frontend` package before its required `backend` package is ready. However, this means that you must be careful to enter the correct versions.

It is also possible, although not advisable, to modify the declared dependencies of a deployment package even after it has been deployed. No validation will be performed in that case.

## Composite packages

Composite packages cannot declare dependencies on other applications. However, a deployment package can declare a dependency on a composite package. In this case the actual composite package is required to be installed, not just its constituents. 

That is, suppose the following situation. We want to deploy a deployment package that declares a dependency on composite package `C` version `[1.0, 1.0]`. Suppose also that `C` version `1.0` consists of deployment packages `D` version `3.1` and `E` version `5.2.2`. Then it is not enough that just `D` `3.1` and `E` `5.2.2` are deployed on the environment but not `C` `1.0` itself.

When deploying a composite package, the dependency check is skipped completely. This means that if its constituents declare any dependencies, these will not be checked either. That is, in the above situation, if `D` version `3.1` declares any dependencies, the composite package can still be deployed to an empty environment.
