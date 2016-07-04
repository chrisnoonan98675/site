---
title: Undeploy an application or deprovision an environment
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- undeploy
- provisioning
---

To remove an application and its components from an environment, you need to undeploy the application. Similarly, to tear down a cloud-based environment [provisioned by XL Deploy](/xl-deploy/how-to/provision-an-environment.html), you need to deprovision it. Note that before you can deprovision an environment, you must first undeploy all applications that are deployed to it.

To undeploy an application or deprovision an environment:

1. Click **Deployment** in the top bar.
1. Locate the environment under **Environments**.
1. Expand the environment, right-click the deployed application or provisioned environment, and select **Undeploy**.
1. Optionally select one or more orchestrators to use when executing.
1. Click **Next**. The plan appears.
1. Click **Execute** to execute the plan.

## Undeploying an application with dependencies

When you undeploy an application that has [dependencies](/xl-deploy/concept/application-dependencies-in-xl-deploy.html), XL Deploy does not automatically undeploy the dependent applications. You must manually undeploy applications, one at a time.

If you try to undeploy an application that other applications depend on, XL Deploy will return an error and you will not be able to undeploy the application.
