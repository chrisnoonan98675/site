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

XL Deploy allows you to define dependencies among different versions of different applications. When you set up the deployment of an application, XL Deploy automatically includes the correct versions of other applications that it depends on.

Application dependencies work with other XL Deploy features such as staging, satellites, rollbacks, updates, and undeployment.

You define dependencies at the *deployment package* level.

## Versioning requirements

To define application dependencies in XL Deploy:

* You must use [Semantic Versioning (SemVer) 2.0.0](http://semver.org/) for deployment package names
* Deployment package names can contain numbers, letters, periods (`.`), and hyphens (`-`)

In the SemVer scheme, a version number is expressed as `major.minor.patch`; for example, `1.2.3`. All three parts of the version number are required.

You can also append a hyphen to the version number, followed by numbers, letters, or periods; for example, `1.2.3-beta`. In the SemVer scheme, this notation indicates a pre-release version.

### Examples of deployment package names

Some examples of deployment package names that use the SemVer scheme are:

* `1.0.0`
* `1.0.0-alpha`
* `1.0.0-alpha.1`
* `1.0.0-0.3.7`
* `1.0.0-x.7.z.92`

## Version ranges

You can use parentheses and square brackets to version dependency ranges. The range formats are:

{:.table .table-striped}
| Format | Description | Example |
| ------ | ----------- | ------- |
| `[version1,version2]` | The application depends on any version between `version1` and `version2`, including both versions (note that `version1` and `version2` can be the same value) | AppA depends on AppB `[1.0.0,2.0.0]`, so AppA works with AppB `1.0.0`, `1.5.5`, `1.9.3`, and `2.0.0` |
| `(version1,version2)` | The application depends on any version between `version1` and `version2`, excluding both versions | AppA depends on AppB `(1.0.0,2.0.0)`, so AppA works with AppB `1.5.5` and `1.9.3`, but does not work with AppB `1.0.0` or `2.0.0` |
| `[version1,version2)` | The application depends on any version between `version1` and `version2`, including `version1` and excluding `version2` | AppA depends on AppB `[1.0.0,2.0.0)`, so AppA works with AppB `1.0.0`, `1.5.5`, and `1.9.3`, but does not work with AppB `2.0.0` |
| `(version1,version2]` | The application depends on any version between `version1` and `version2`, excluding `version1` and including `version2` | AppA depends on AppB `(1.0.0,2.0.0]`, so AppA works with App B `1.5.5`, `1.9.3`, and `2.0.0`, but does not work with AppB `1.0.0` |

## Simple dependency example

Assume that you have two applications called WebsiteFrontEnd and WebsiteBackEnd. WebsiteFrontEnd version 1.0.0 requires WebsiteBackEnd version 2.0.0. To define this dependency in the XL Deploy interface, you would:

1. Go to the Repository.
1. Expand **Applications** > **WebsiteFrontEnd** and double-click the 1.0.0 deployment package.
1. In the **Application Dependencies** section, add the key `WebsiteBackEnd` and the value `[2.0.0,2.0.0]`. This is the [Semantic Versioning (SemVer)](http://semver.org/) format that indicates that WebsiteFrontEnd 1.0.0 depends on WebsiteBackEnd 2.0.0, and only 2.0.0 (not any older or newer version).

    ![Application with dependencies](images/app-dependencies-example-01.png)

When you set up a deployment of WebsiteFrontEnd 1.0.0, XL Deploy will automatically include WebsiteBackEnd 2.0.0.

For an extended example of dependencies, refer to [Advanced application dependencies example](/xl-deploy/concept/advanced-application-dependencies-example.html).

## When can you set dependencies?

You can define a dependency on an application that does not yet exist in the XL Deploy repository. You can also specify a version range that cannot be met by any versions that are currently in the repository.

This allows you to import applications even before all dependencies can be met; that is, you can import, but not deploy, the `frontend` package before its required `backend` package is ready. However, this means that you must be careful to enter the correct versions.

It is also possible to modify the declared dependencies of a deployment package even after it has been deployed. In that case, XL Deploy will not perform any validation. It is not recommended to modify dependencies after deployment.

## How does XL Deploy check dependencies?

For detailed information on the way XL Deploy verifies dependencies, refer to [How XL Deploy checks application dependencies](/xl-deploy/concept/how-xl-deploy-checks-application-dependencies.html).

## Deploying dependencies in the right order

When deploying applications with dependencies, the order in which the applications will be deployed might be important. For example, if application A depends on application B, you want to deploy application B before A. You can achieve this by using the [`sequential-by-dependency`](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html) orchestrator. This orchestrator will deploy all applications in reverse topological order to ensure that dependent applications are deployed first. By default, all steps for all applications will be interleaved.

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

When undeploying an application, you can also automatically undeploy all of its direct or transient dependencies. To do so, enable the **Undeploy Dependencies** option in the deployment properties. If this option is not enabled, the application will be undeployed, but its dependencies will remain deployed.

**Note:** Dependent applications will be undeployed even if they were originally deployed manually.
