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

XL Deploy can stage artifacts to target hosts before a deployment commences. For it to do so, two requirements have to be met:

* The host(s) must have the `stagingDirectory` set
* The plugin being used must support staging

If these conditions are met, XL Deploy will copy all artifacts that are being deployed to this host before executing the real deployment. This will ensure that the downtime of your application is minimized. After the deployment has completed successfully, XL Deploy will clean up the staging directories.
