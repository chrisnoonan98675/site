---
title: Create an environment in XL Deploy (deprecated method)
subject:
- Getting started
categories:
- xl-deploy
tags:
- middleware
- environment
---

An environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

**Tip:** To see a sample environment being created, watch the *[Defining environments](http://vimeo.com/97815292)* video.

To create an environment where you can deploy an application:

1. Right-click **Environments** and select **New** > **Environment**.
2. In the **Name** box, enter a name for this environment.
3. On the **Common** tab, select one or more middleware containers from the **Containers** list and click ![Right arrow button](/images/button_add_container.png) to move it to the **Members** list.

      ![Sample environment](images/xl-deploy-trial/xl_deploy_trial_glassfish_environment.png)

4. Click **Save**. XL Deploy saves the environment in the Repository.
