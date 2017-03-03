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
weight: 193
---

To remove an application and its components from an environment, you need to undeploy the application. Similarly, to tear down a cloud-based environment [provisioned by XL Deploy](/xl-deploy/how-to/provision-an-environment.html), you need to deprovision it. Note that before you can deprovision an environment, you must first undeploy all applications that are deployed to it.

## Undeploying an application using the XL Deploy default GUI

To undeploy an application:

1. Expand **Environments**, and then expand the environment where the application is deployed.
1. Hover over the application, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Undeploy**. A new tab appears in the right pane.
1. You can optionally configure properties such as [orchestrators](/xl-deploy/concept/understanding-orchestrators.html) for the undeployment.
1. Click **Execute** to start executing the plan immediately.

    If the server does not have the capacity to immediately start executing the plan, it will be in a `QUEUED` state until the server has sufficient capacity.

    If a step in the undeployment fails, XL Deploy stops executing and marks the step as `FAILED`. Click the step to see information about the failure in the output log.

## Undeploying an application using the XL Deploy legacy GUI

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
