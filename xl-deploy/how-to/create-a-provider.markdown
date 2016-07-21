---
title: Create a provider
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- provider
- cloud
since:
- XL Deploy 5.5.0
weight: 325
---

In XL Deploy, a *provider* is a set of credentials needed to connect to a cloud technology. You can group providers logically in [*provisioning environments*](/xl-deploy/how-to/create-a-provisioning-environment.html), and then provision packages to them.

To create a provider:

1. Click **Repository** in the top bar.
1. Right-click **Providers** and select **New**, then select the provider type. For example, for Amazon Elastic Compute Cloud (Amazon EC2), select **aws** > **ec2.Cloud**.
1. In the **Name** box, enter a unique name for the provider.
1. Enter the required information for the provider. For example, for Amazon EC2, enter your access key ID and secret access key.

    ![Create new provider](images/provisioning-create-new-provider.png)

1. Click **Save**.

## Next steps

After you create a provider, you can [add it to a provisioning environment](/xl-deploy/how-to/create-a-provisioning-environment.html).
