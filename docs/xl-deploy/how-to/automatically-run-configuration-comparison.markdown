---
no_index: true
title: Automatically run configuration comparison at the start of a deployment
---

To automatically run [configuration comparison](/xl-deploy/how-to/compare-configuration-items.html) at the start of a deployment:

1. Add a property called, for example, _Run compare before deployment_ to the deployment properties.
1. If the property is selected, XL Deploy adds a step to the beginning of the deployment plan that calls the API to trigger configuration comparison on the target environment.
1. The step fails if configuration drift is detected. This causes the deployment to fail and triggers [email notifications](/xl-deploy/concept/trigger-plugin.html) for the deployment.
