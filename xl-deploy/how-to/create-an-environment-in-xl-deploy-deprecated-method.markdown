---
title: Create an environment in XL Deploy (deprecated method)
subject:
- Environment
categories:
- xl-deploy
tags:
- middleware
- environment
- container
since:
- 3.9.0
deprecated:
- 5.0.0
---

An environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

**Note:** For information about creating an environment in XL Deploy 5.0.0 and later, refer to [Create an environment in XL Deploy](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html).

To create an environment where you can deploy an application:

1. Right-click **Environments** and select **New** > **Environment**.
2. In the **Name** box, enter a name for this environment.
3. On the **Common** tab, select one or more middleware containers from the **Containers** list and click ![Right arrow button](/images/button_add_container.png) to move it to the **Members** list.

      ![Sample environment](images/xl-deploy-trial/xl_deploy_trial_glassfish_environment.png)

4. Click **Save**. XL Deploy saves the environment in the Repository.

**Tip:** To see a sample environment being created, watch the *[Defining environments](https://www.youtube.com/watch?v=LU4NLPcCwlQ&list=PLIIv46GEoJ7ZvQd4BbzdMLaH0tc-gYyA1&index=3)* video.
