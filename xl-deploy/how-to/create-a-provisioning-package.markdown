---
layout: beta-noindex
title: Create a provisioning package
since:
- XL Deploy 5.5.0
---

In XL Deploy, a provisioning package represents a specific version of a blueprint. It contains provisionables, which are the details that are needed to set up the environment. A provisionable can contain provisioners that define actions to take after the environment is set up.

In addition to provisionables, a provisioning package can contain templates that define configuration items (CIs) that XL Deploy should create based on the results of the provisioners.

To create a provisioning package:

1. Click **Repository** in the top bar.
1. If the blueprint does not exist yet:
    1. Right-click **Blueprints** and select **New** > **Blueprint**.
    1. In the **Name** box, enter a unique name for the blueprint.
    1. Click **Save**.
1. Right-click the blueprint that you just created and select **New** > **Provisioning Package**.
1. In the **Name** box, enter a unique name for the provisioning package.
1. In the **Environment Id** box, enter the 



## Create a provisioning package in other ways

