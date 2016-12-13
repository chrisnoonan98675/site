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
weight: 120
---

XL Deploy allows you to define dependencies among different versions of different applications. When you set up the deployment of an application, XL Deploy automatically includes the correct versions of other applications that it depends on. Application dependencies work with other XL Deploy features such as staging, satellites, rollbacks, updates, and undeployment. You define dependencies at the *deployment package* level.

## Versioning requirements

To define application dependencies in XL Deploy:

* You must use [Semantic Versioning (SemVer) 2.0.0](http://semver.org/) for deployment package names
* Deployment package names can contain numbers, letters, periods (`.`), and hyphens (`-`)

In the SemVer scheme, a version number is expressed as `major.minor.patch`; for example, 1.2.3. All three parts of the version number are required.

You can also append a hyphen to the version number, followed by numbers, letters, or periods; for example, 1.2.3-beta. In the SemVer scheme, this notation indicates a pre-release version.

### Examples of deployment package names

Some examples of deployment package names that use the SemVer scheme are:

* 1.0.0
* 1.0.0-alpha
* 1.0.0-alpha.1
* 1.0.0-0.3.7
* 1.0.0-x.7.z.92

## Version ranges

You can use parentheses and square brackets to version dependency ranges. The range formats are:

{:.table .table-striped}

| Format | Description | Example |
| ------ | ----------- | ------- |
| `[version1,version2]` | The application depends on any version between `version1` and `version2`, including both versions (note that `version1` and `version2` can be the same value) | AppA depends on AppB [1.0.0,2.0.0], so AppA works with AppB 1.0.0, 1.5.5, 1.9.3, and 2.0.0 |
| `(version1,version2)` | The application depends on any version between `version1` and `version2`, excluding both versions | AppA depends on AppB (1.0.0,2.0.0), so AppA works with AppB 1.5.5 and 1.9.3, but does not work with AppB 1.0.0 or 2.0.0 |
| `[version1,version2)` | The application depends on any version between `version1` and `version2`, including `version1` and excluding `version2` | AppA depends on AppB [1.0.0,2.0.0), so AppA works with AppB 1.0.0, 1.5.5, and 1.9.3, but does not work with AppB 2.0.0 |
| `(version1,version2]` | The application depends on any version between `version1` and `version2`, excluding `version1` and including `version2` | AppA depends on AppB (1.0.0,2.0.0], so AppA works with App B 1.5.5, 1.9.3, and 2.0.0, but does not work with AppB 1.0.0 |
| `version1` | The application depends on any version above `version1`, including `version1` | AppA depends on AppB 1.0.0, so AppA works with AppB 1.0.0, 1.0.1, 1.1.0, and so on |

## Simple dependency example

Assume that you have two applications called WebsiteFrontEnd and WebsiteBackEnd. WebsiteFrontEnd version 1.0.0 requires WebsiteBackEnd version 2.0.0. To define this dependency in the XL Deploy interface, you would:

1. Go to the Repository.
1. Expand **Applications** > **WebsiteFrontEnd** and double-click the 1.0.0 deployment package.
1. In the **Application Dependencies** section, add the key WebsiteBackEnd and the value [2.0.0,2.0.0]. This is the [Semantic Versioning (SemVer)](http://semver.org/) format that indicates that WebsiteFrontEnd 1.0.0 depends on WebsiteBackEnd 2.0.0, and only 2.0.0 (not any older or newer version).

    ![Application with dependencies](images/app-dependencies-example-01.png)

When you set up a deployment of WebsiteFrontEnd 1.0.0, XL Deploy will automatically include WebsiteBackEnd 2.0.0.

For an extended example of dependencies, refer to [Advanced application dependencies example](/xl-deploy/concept/advanced-application-dependencies-example.html).

## When can you set dependencies?

You can define a dependency on an application that does not yet exist in the XL Deploy Repository. You can also specify a version range that cannot be met by any versions that are currently in the Repository.

This allows you to import applications even before all dependencies can be met; that is, you can import, but not deploy, a front-end package before its required back-end package is ready. However, this means that you must be careful to enter the correct versions.

It is also possible to modify the declared dependencies of a deployment package even after it has been deployed. In that case, XL Deploy will not perform any validation. It is not recommended to modify dependencies after deployment.

## How does XL Deploy check dependencies?

For detailed information on the way XL Deploy verifies dependencies, refer to [How XL Deploy checks application dependencies](/xl-deploy/concept/how-xl-deploy-checks-application-dependencies.html).

## How does XL Deploy select the versions to deploy?

In XL Deploy 5.1.x and 5.5.x, XL Deploy always selects the highest possible version in the dependency range of each application that will be deployed because of dependencies.

In XL Deploy 6.0.0 and later, XL Deploy uses the **Dependency Resolution** property of the deployment package that you choose when setting up the deployment to select the other application versions. The dependency resolution property can be set to:

* `LATEST`: Select the highest possible version in the dependency range of each application that will be deployed (this is the default)
* `EXISTING`: If the version of an application that is currently deployed to the environment satisfies the dependency range, do not select a new version

The `LATEST` option ensures that you always deploy the latest version of each application, while the `EXISTING` option ensures that you only update applications when they no longer satisfy your dependencies (so you will have the smallest deployment plan possible).

**Tip:** You can use a [placeholder](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) in the **Dependency Resolution** property to set a different dependency resolution value per environment.

### Dependency resolution example

You have the following applications and dependencies:

{:.table .table-striped}
| Application | Versions | Dependencies |
| ----------- | -------- | ------------ |
| AppA | 1.0.0 | AppB [3.0.0,4.0.0] |
| AppA | 2.0.0 | AppB [3.0.0,4.0.0] |
| AppB | 3.0.0 | None |
| AppB | 4.0.0 | None |

Your environment already contains AppA 1.0.0 and AppB 3.0.0. You want to update AppA to version 2.0.0. If the dependency resolution for AppA 2.0.0 is set to:

* `LATEST`, then you will deploy AppA 2.0.0 and AppB 4.0.0.
* `EXISTING`, then you will deploy AppA 2.0.0 _only_. This is because the existing deployed application (AppB 3.0.0) satisfies AppA's dependency range.

Note that the dependency resolution set on the AppB deployment packages is ignored, because XL Deploy uses the value from the deployment package that you choose when you set up the deployment.

## Deploying dependencies in the right order

When deploying applications with dependencies, the order in which the applications will be deployed might be important. For example, if application A depends on application B, you want to deploy application B before A. You can achieve this by using the [`sequential-by-dependency`](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html) orchestrator (available in XL Deploy 6.0.0 and later). This orchestrator will deploy all applications in reverse topological order to ensure that dependent applications are deployed first. By default, all steps for all applications will be interleaved.

You can combine the `sequential-by-dependency` orchestrator with other orchestrators such as the `sequential-by-deployment-group` orchestrator to support more advanced use cases.

## Dependencies and permissions

When you set up a deployment, XL Deploy checks the permissions of all applications that will be deployed because of dependencies. You must at least have `read` permission on all dependent applications.

For the environment, you must have one or more of the following permissions:

* `deploy#initial`: Permission to deploy a new application
* `deploy#upgrade`: Permission to upgrade a deployed application
* `deploy#undeploy`: Permission to undeploy a deployed application

## Dependencies and composite packages

Composite packages cannot declare dependencies on other applications. However, a deployment package can declare a dependency on a composite package. In this case, the actual composite package must to be installed, not just its constituents.

For example, you want to deploy a deployment package that declares a dependency on composite package AppC version `[1.0.0,1.0.0]`. AppC version 1.0.0 consists of deployment packages AppD version 3.1.0 and AppE version 5.2.2. If AppD 3.1.0 and AppE 5.2.2 are deployed on the environment but AppC 1.0.0 is not, then you will not be able to deploy the package.

When you deploy a composite package, the dependency check is skipped. This means that if its constituents declare any dependencies, these will not be checked. That is, in the above situation, if AppD version 3.1.0 declares any dependencies, the composite package can still be deployed to an empty environment.

## Undeploying application with dependencies

Prior to XL Deploy 6.0.0, XL Deploy does not automatically undeploy an application's dependencies when you undeploy the application.

When undeploying an application in XL Deploy 6.0.0 and later, you can automatically undeploy all of its direct or transient dependencies by setting the **Undeploy Dependencies** property to `TRUE` on the deployment package or in the deployment properties. If this property is set to:

* `TRUE`, dependent applications will be undeployed even if they were originally deployed manually
* `FALSE`, the application will be undeployed, but its dependencies will remain deployed

**Tip:** You can use a [placeholder](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) in the **Undeploy Dependencies** property to set a different value per environment.
