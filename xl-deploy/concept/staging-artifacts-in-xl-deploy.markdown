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

To ensure that the downtime of your application is limited, XL Deploy can stage artifacts to target hosts before deploying the application.

When staging is enabled, XL Deploy will copy all artifacts to the host before starting the deployment.

In XL Deploy 5.1.0 and later, if the application [depends on other applications](/xl-deploy/concept/application-dependencies-in-xl-deploy.html), XL Deploy will also copy the artifacts from the dependent applications.

After the deployment completes successfully, XL Deploy will clean up the staging directory.

To enable staging on a host, enter a directory path in the host's **Advanced** > **Staging Directory Path** property.

**Note:** The plugin being used must support staging.
