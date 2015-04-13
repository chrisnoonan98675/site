---
title: Use the release dashboard
subject:
- Release dashboard
categories:
- xl-deploy
tags:
- package
- application
- gui
- checklist
---

After you have configured a deployment pipeline in XL Deploy, you can use the Release Dashboard.

## Opening the Release Dashboard

Log into the XL Deploy GUI and select the **Release Dashboard** tab. This will bring up the empty Release Dashboard screen.

On the left side of the screen, an Application Browser is shown. Here, you will see all applications that you can access in XL Deploy. The application node can be expanded to bring up all versions of the application that are available. You can also traverse through all directories to locate the application you need.

## Displaying an application's deployment pipeline

Click on an application in the Application Browser to view it's deployment pipeline. The following screenshot shows an example of a deployment pipeline for the PetPortal sample application:

![Deployment Pipeline](images/deployment-pipeline.png)

In the above example, the environments DEV, TEST, ACC and PROD are configured to be the deployment pipeline for the PetPortal application. This screen shows you which versions of the PetPortal application are currently deployed to each environment in the pipeline.

Click on another application to display multiple deployment pipelines side-by-side. Click on the cross in the top-left corner to close the deployment pipeline.

## Displaying an application version's deployment pipeline

To display the deployment pipeline for a specific application version, click on the application version in the Application Browser or, alternatively, click on the application version number in the application deployment pipeline. The Release Dashboard shows a deployment pipeline for the selected version:

![Deployment Pipeline Package Detail](images/deployment-pipeline-package-detail.png)

Here, you see all environments in the deployment pipeline and which version is deployed where. The focus here is on the selected application version, _PetPortal_ version _1.0_. Each environment for which the deployment checklist has been completed are outlined in green. A deploy button is shown in the environment. Environments for which the checklist has not yet been completed are outlined in red.

## Filling out deployment checklists

Before promoting an application version to a new environment, the deployment checklist for this package and environment must be met. Click on the environment name to show the deployment checklist for the application version and environment:

![Deployment Checklist](images/deployment-checklist.png)

Conditions that are not met are marked with a stop sign. Check a checkbox or fill out a string field and click the Save button to satisfy the condition. If all items are satisfied, the environment will turn green, indicating that the application version can be promoted to the target environment.

## Starting a deployment

When the checklist for a particular version and environment has been filled out, a **Deploy** button appears on the environment. Clicking this button will start a new deployment on the Deployment tab. The deployment can be configured and executed in the regular way.

## Verifying the deployment checklist

Deployment checklists are verified at two points during a deployment:

* When a deployment is configured
* When a deployment is executed

When configuring a deployment, XL Deploy validates that all checks for the environment have been met for the deployment package you selected. This validation happens when XL Deploy calculates the steps necessary for the deployment. If this is not the case, an error message is shown. Open the Release Dashboard to view the checklist and fill out the necessary items.

Any deployment of a package to an environment with a checklist contains an additional step at the start of the deployment. Not only does this step validate that the necessary checklist items have been satisfied, but it also writes confirmation of this fact to the deployment log. This allows an administrator to verify these later if necessary.
