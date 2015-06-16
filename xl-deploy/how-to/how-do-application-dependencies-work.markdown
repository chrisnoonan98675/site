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
    * SemVer


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

