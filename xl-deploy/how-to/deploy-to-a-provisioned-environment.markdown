---
layout: beta-noindex
title: Deploy to a provisioned environment
since:
- XL Deploy 5.5.0
---

After you use XL Deploy's [provisioning feature](/xl-deploy/concept/provisioning-environments-with-xl-deploy.html) to provision an [environment](/xl-deploy/how-to/provision-an-environment.html), you can deploy applications to it.

To do so, you need to add the middleware containers that result from the provisioning to an [XL Deploy environment](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html). XL Deploy will automatically do this for you if you set the **Environment Name** property on the [provisioning package](/xl-deploy/how-to/create-a-provisioning-package.html).

After the environment is set up as desired, follow the instructions in [Deploy an application](/xl-deploy/how-to/deploy-an-application.html) to deploy an application to it.

## Next steps

After you deploy an application to a provisioned environment, you can [update the application to another version](/xl-deploy/how-to/update-a-deployed-application.html) or [undeploy it](/xl-deploy/how-to/undeploy-an-application.html).
