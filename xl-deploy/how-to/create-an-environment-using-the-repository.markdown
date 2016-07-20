---
title: Create an environment using the Repository
categories:
- xl-deploy
subject:
- Environment
tags:
- middleware
- environment
since:
- XL Deploy 3.9.0
weight: 166
---

An environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

To create an environment where you can deploy an application:

1. In the top menu, click **Repository**.
1. Right-click **Environments** and select **New** > **Environment**.
2. In the **Name** box, enter a name for this environment.
3. On the **Common** tab, select one or more middleware containers from the **Containers** list and click ![Right arrow button](/images/button_add_container.png) to move it to the **Members** list.

      ![Sample environment](images/xl-deploy-trial/xl_deploy_trial_glassfish_environment.png)

4. Click **Save**. XL Deploy saves the environment in the Repository.

**Tip:** To see a sample environment being created, watch the *[Defining environments](https://www.youtube.com/watch?v=LU4NLPcCwlQ&list=PLIIv46GEoJ7ZvQd4BbzdMLaH0tc-gYyA1&index=3)* video.
