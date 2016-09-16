---
title: Create a provider
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- cloud
since:
- XL Deploy 5.5.0
weight: 151
---

In XL Deploy, a *provider* is a set of credentials needed to connect to a cloud technology. You can group providers logically in [environments](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html), and then provision packages to them.

To create a provider:

1. Click **Repository** in the top bar.
1. In XL Deploy 5.5.x, right-click **Providers** and select **New**, then select the provider type.

    In XL Deploy 6.0.0 and later, right-click **Infrastructure**, then select the provider type.

    For example, if you are using Amazon Elastic Compute Cloud (Amazon EC2), select **aws** > **ec2.Cloud**.

1. In the **Name** box, enter a unique name for the provider.
1. Enter the required information for the provider.

    For example, if you are using Amazon EC2, you must enter your access key ID and secret access key.

1. Click **Save**.

## Next steps

After you create a provider, you can add it to an [environment](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html).
