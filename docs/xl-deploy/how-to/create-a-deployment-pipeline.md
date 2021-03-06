---
title: Create a deployment pipeline
subject:
Release dashboard
categories:
xl-deploy
tags:
pipeline
release dashboard
weight: 195
---

A deployment pipeline defines the sequence of environments to which an application is deployed during its lifecycle. Deployment pipelines appear on the XL Deploy [release dashboard](/xl-deploy/concept/release-dashboard.html).

### To create a deployment pipeline for an application:

1. From the top navigation bar, click **Explorer**.
1. In the left pane, click **Configuration**.
1. Click ![Explorer action menu](/images/menu_three_dots.png), and select **New** > **Release** > **DeploymentPipeline**.
1. In the **Name** field, enter a unique name for the pipeline.
1. In the **Pipeline** field, enter the environments to add them to deployment pipeline.  
  **Note:** The order of the environments in the list is the order that they will appear in the pipeline. You can reorder the list by dragging and dropping items. 
1. Click **Save**.
1. Expand **Applications**.
1. Click an application, click ![Explorer action menu](/images/menu_three_dots.png), and then click **Edit properties**.
1. In the **Common** tab, select the deployment pipeline from the **Pipeline** list.
1. Click **Save**.   



  ![Adding environments to the pipeline](images/adding-environments-to-the-pipeline.png)



#### To view a deployment pipeline:
 Hover over the application, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Deployment pipeline**.

    Or    
 Double-click the application to see the read-only deployment pipeline in the summary screen.    


### To create a deployment pipeline for an application using the legacy interface:

1. Log in to XL Deploy.
1. Click **Repository** in the top bar.
1. Right-click **Configuration** and select **New** > **release** > **DeploymentPipeline**.
1. In the **Name** box, enter a unique name for the pipeline.
1. Under **Pipeline**, select each environment that should be in the application's pipeline and click ![Right arrow button](/images/button_add_container.png) to add it to the **Members** list.

    The order of environments in the **Members** list is the order that they will appear in the pipeline. You can reorder the list by dragging and dropping members.

1. Click **Save**.
1. Expand **Applications** and double-click the desired application.
1. On the **Common** tab, select the deployment pipeline from the **Pipeline** list.
1. Click **Save**.
1. Click **Release Dashboard** in the top bar.
1. Double-click the application to see its deployment pipeline.
