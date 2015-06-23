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

* Todo: Fix the current API documentation because the api changed - migration manual?


* How to define application dependencies?

  * To use application dependencies, you have to use [Semver Versioning 2.0](http://semver.org/) for package names as shown below. In Semver versioning scheme, a version number is expressed as **MAJOR.MINOR.PATH**. For example, a deployment package with name 1.2.3, where 1 is the **MAJOR** version, 2 is the **MINOR** version, and 3 is the **PATCH** version.
  ![Deployment Package Semver Version](images/application_dependencies/deployment_package_semver_versioning.png)

  **Application dependencies functionality only work when your package names uses Semver versioning scheme.**

  Lets suppose we have two applications `app1` and `app2` where `app2` is dependent on `app1`. Application dependencies are specified at deployment package level. To define dependency of `app2` version 1.0 on `app1` version 1.0, you have to specify the dependency on deployment package screen as shown below.
  ![Define application dependency](images/application_dependencies/application_dependency.png)

  Application dependencies also support version ranges using the formats shown below:

  1. [version1, version2) : This version string means application is dependent on any version starting from version1 to version2 excluded. For example, if app1 version 1.0 is dependent on app2 version [1.0,2.0) then app1 works with any version starting from 1.0 like 1.0, 1.1,1.9 but not 2.0.

  2. [version1, version2] : This version string means application is dependent on any version starting from version1 to version2 included. For example, if app1 version 1.0 is dependent on app2 version [1.0,2.0] then app1 works with any version starting from 1.0 like 1.0, 1.1,1.9 including 2.0 but not versions above 2.0 like 2.1.

  3. (version1, version2) : This version string means application is dependent on any version between version1 to version2. For example, if app1 version 1.0 is dependent on app2 version (1.0,2.0) then app1 works with any version between 1.0 and 2.0 like 1.1, 1.2.1, 1.9.9, etc.

  4. (version1, version2] : This version string means application is dependent on any version between version1 to version2 included. For example, if app1 version 1.0 is dependent on app2 version (1.0,2.0] then app1 works with any version between 1.0(excluded) to 2.0(included) like 1.1,1.9, .. 2.0.


* How to deploy/upgrade/undeploy an application with its dependencies?
    * How
    * gui
        * using cli
        * Upgrade - no difference
    * Undeployment - not possible (automatically) - it will prevent you from breaking it
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
