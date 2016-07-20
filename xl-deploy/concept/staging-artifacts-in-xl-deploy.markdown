---
title: Staging artifacts in XL Deploy
subject:
- Deployment
categories:
- xl-deploy
tags:
- staging
- deployment
- artifacts
weight: 187
---

To ensure that the downtime of your application is limited, XL Deploy can stage artifacts (files) to target hosts before deploying the application. Staging is based on the artifact's **Checksum** property, and requires that the plugin being used to deploy the artifact supports staging.

When staging is enabled, XL Deploy will copy all artifacts to the host before starting the deployment. After the deployment completes successfully, XL Deploy will clean up the staging directory.

If the application [depends on other applications](/xl-deploy/concept/application-dependencies-in-xl-deploy.html), XL Deploy will also stage the artifacts from the dependent applications (supported in XL Deploy 5.1.0 and later).

To enable staging on a host:

1. Click **Repository** in the top bar.
1. Expand **Infrastructure** and double-click the host that you want to modify.
1. Go to the **Advanced** tab and enter a directory path in the **Staging Directory Path** box.
1. Click **Save**.

**Tip:** If you set a staging directory on a host but you do not see staging steps in the deployment plan, verify that the `file.DeployedFile.copyDirectlyToTargetPath` and `file.DeployedFile.DeployedFolder` properties in the `XLDEPLOY_HOME/conf/deployit-default.properties` file are set to "false" (their default setting).
