---
title: Create a provisioning environment
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- provisioning environment
- cloud
since:
- XL Deploy 5.5.0
weight: 326
---

In XL Deploy, a *provisioning environment* is a collection of *providers*, which are sets of credentials needed to connect to cloud technologies.

To create a provisioning environment:

1. Click **Repository** in the top bar.
1. Right-click **ProvisioningEnvironments** and select **New** > **upm** > **ProvisioningEnvironment**.
1. In the **Name** box, enter a unique name for the environment.
1. Under **Providers**, select one or more providers and click ![Right arrow button](/images/button_add_container.png) to move them to the **Members** list.
1. Under **Dictionaries**, select one or more dictionaries and click ![Right arrow button](/images/button_add_container.png) to move them to the **Members** list.

   ![Create a provisioning environment](images/provisioning-create-new-environment.png)

1. Click **Save**.

## Next steps

After you create a provisioning environment, you can [provision packages to it](/xl-deploy/how-to/provision-an-environment.html) to it.
