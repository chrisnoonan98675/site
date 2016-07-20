---
title: Undeploy an application
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- deployed
- application
- package
- undeploy
weight: 193
---

To remove an application and its components from an environment, you need to _undeploy_ the application:

1. Click **Deployment** in the top bar.
1. Locate the environment under **Environments**.

    **Note:** In XL Deploy 4.5.x and earlier, this section is called **Deployed Applications**.

1. Expand the environment, right-click the deployed application, and select **Undeploy**.
1. Optionally select one or more orchestrators to use when executing the undeployment.
1. Click **Next**. The undeployment plan appears.
1. Click **Execute** to execute the plan.

## Undeploying an application with dependencies

When you undeploy an application that has [dependencies](/xl-deploy/concept/application-dependencies-in-xl-deploy.html), XL Deploy does not automatically undeploy the dependent applications. You must manually undeploy applications, one at a time.

If you try to undeploy an application that other applications depend on, XL Deploy will return an error and you will not be able to undeploy the application.
