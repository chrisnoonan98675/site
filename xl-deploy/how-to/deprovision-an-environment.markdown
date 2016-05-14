---
title: Deprovision an environment
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- cloud
since:
- XL Deploy 5.5.0
---

Before you can deprovision ("tear down") a provisioned environment, you must [undeploy](/xl-deploy/how-to/undeploy-an-application.html) all applications that are deployed to the provisioneds that the environment contains.

{% comment %} TO DO: Explain what happens to CIs in the repository when you deprovision - see DEPL-8726, DEPL-9228 {% endcomment %}

## Deprovision an environment using the GUI

To deprovision a provisioned environment:

1. Click **Provision** in the top bar.
1. Under **Provisioning Environments**, locate the desired environment, right-click it, and select **Deprovision**.
1. Click **Execute** to immediately start deprovisioning.

## Deprovision an environment using the CLI

For information about deprovisioning an environment using the XL Deploy command-line interface (CLI), refer to [Using the XL Deploy CLI provisioning extension](/xl-deploy/how-to/using-the-xl-deploy-cli-provisioning-extension.html).
