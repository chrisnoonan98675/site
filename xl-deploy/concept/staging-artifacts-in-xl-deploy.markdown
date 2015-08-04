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
---

XL Deploy can stage artifacts to target hosts before deploying them. This ensures that the downtime of your application is minimized. After deployment completes successfully, XL Deploy will clean up the staging directories.

**Note:** The plugin being used to perform the deployment must support staging.

To enable staging, set the **Staging Directory Path** property on the host(s).

**Tip:** If you set a staging directory on the host(s) but you do not see staging steps in the deployment plan, verify that the `file.DeployedFile.copyDirectlyToTargetPath` and `file.DeployedFile.DeployedFolder` in the `conf/deployit-default.properties` file are set to "false" (their default setting).
