---
layout: beta-noindex
title: Create a provider
since:
- XL Deploy 5.5.0
---

In XL Deploy, a *provider* is a set of credentials needed to connect to a cloud technology. You can group providers logically in *ecosystems*, and then provision packages to them.

To create a provider:

1. Click **Repository** in the top bar.
1. Right-click **Infrastructure** and select **New**, then select the provider. For example, for Amazon Web Services, select **aws** > **ec2.Cloud**.
1. In the **Name** box, enter a unique name for the provider.
1. Enter the required information for the provider. For example, for EC2, enter the access key ID and secret access key
1. Click **Save**.

## Next steps

After you create a provider, you can [add it to an ecosystem](/xl-deploy/how-to/create-an-ecosystem.html).
