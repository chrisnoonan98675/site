---
title: Using the deployment pipeline view
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- pipeline
- application
- release dashboard
since:
- XL Deploy 7.1.0
---

In XL Deploy you can view the [deployment pipeline](/xl-deploy/concept/release-dashboard.html#define-a-deployment-pipeline) for an application or a deployment/provisioning package. In the deployment pipeline you can view the sequence of environments to which an application is deployed during its lifecycle. The deployment pipeline also allows you to see the data about the last deployment of an application to each environment. You must first define a deployment pipeline for each application you want to view. For more information, refer to [Create a deployment pipeline](/xl-deploy/how-to/create-a-deployment-pipeline.html).

## View deployment pipeline

To view the deployment pipeline of an application:

1. Expand **Applications** in the left pane.
1. Hover over the desired application, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Deployment pipeline**.

  **Note:** You can also expand the desired application, hover over a deployment package or provisioning package, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Deployment pipeline**.

  A new tab appears in the right pane.

  ![Deployment pipeline](images/deployment-pipeline-new.png)

**Note:** Click **Refresh** to retrieve the latest data from the server.

You can search for an environment by name in the deployment pipeline.

## View environment information

For each environment in the deployment pipeline of an application you can view valuable information:

* A drop down list of all the deployment or provisioning package versions for the selected application
* Data about the last deployment of the application to this environment
* To view the [deployment checklist items](/xl-deploy/how-to/create-a-deployment-checklist.html), click the **Deployment checklist** button

  **Note:** When you select a package form the drop down list, XL Deploy verifies if there is a deployment checklist for the selected package and environment. If you click **Deployment checklist**, the checklist items are shown and you can change the status of the items in the list. If all the checklist items are satisfied, the **Deploy** button is enabled.  

* To upgrade or downgrade the selected application, click **Deploy** and follow the instructions on the screen
