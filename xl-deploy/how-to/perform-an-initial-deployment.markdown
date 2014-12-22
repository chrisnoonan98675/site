---
title: Perform an initial deployment
categories:
- xl-deploy
subject:
- Getting started
tags:
- deployment
- application
- package
---

After you have [defined your infrastructure](connect-xl-deploy-to-your-infrastructure.html), [defined an environment](create-an-environment-in-xl-deploy.html), and added an application to XL Deploy, you can perform the initial deployment of an application to an environment.

**Tip:** Watch the *Performing an initial deployment* video [here](http://vimeo.com/97815293).

To deploy an application to an environment:

1. Click **Deployment** in the top bar.
2. Locate the application under **Packages**.
3. Expand the application name, select the deployment package, and drag it to the left side of the **Deployment Workspace**.
4. Locate the environment under **Deployed Applications**.
5. Select the environment and drag it to the right side of the **Deployment Workspace**.
6. Click ![Auto-map button](/images/button_auto-map.png) to automatically map the deployables in the application to the containers in the environment.
7. Optionally click **Analyze** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step.
8. Click **Next**. The deployment plan appears.
9. Click **Execute** to execute the plan.

If the deployment fails, click the failed step to see information about the cause of the failure.
