---
title: Configuration drift correction in XL Deploy
no_index: true
---

A configuration drift can occur when changes are made to the infrastructure of a target system outside of the defined deployment environment. This results in state of the deployment system being out of sync with the actual state of the target system.

XL Deploy can detect state changes in the infrastructure of the target system and repair the configuration drift using the desired state defined in XL Deploy.

## Configuration drift detection model

XL Deploy has a built-in mechanism that allows you to discover the infrastructure and configuration of middleware running on a target platform.
For example, in [WebSphere](http://www-03.ibm.com/software/products/en/appserv-was), XL Deploy can discover all the clusters, nodes, servers, datasources, or queues. For cloud platforms like [Azure](https://azure.microsoft.com/), XL Deploy can discover resource groups, app services, virtual machines, and others.

The discovery mechanism in XL Deploy is extensible through plugins allowing you to adapt to various discovery scenarios. For more information on how the discovery mechanism works, refer to [Discover middleware](/xl-deploy/how-to/discover-middleware.html)

## Repairing system states using configuration drift

The XL Deploy model based deployment engine can generate deployment steps required to apply configuration changes to a target system.

Target environments, environment configuration settings (dictionaries), application components, application configuration, and application middleware configuration are modeled in a declarative manner in XL Deploy using the Unified Deployment Model (UDM).  

For more information on how the XL Deploy deployment model works, refer to:

[Deployment overview and Unified Deployment Model](/xl-deploy/concept/deployment-overview-and-unified-deployment-model.html)

[Deployments and plugins](/xl-deploy/concept/xl-deploy-architecture.html#deployments-and-plugins)

[Introduction to the XL Deploy execution engine](/xl-deploy/concept/introduction-to-the-execution-engine.html)

When configuration drift is detected, XL Deploy uses the deployment engine to redeploy the desired state to the target system.

To see how you can create a job schedule for detecting configuration drifts, refer to [Define a job schedule](/xl-deploy/how-to/define-a-job-schedule.html)

To view and correct a configuration drift, refer to [View and correct a detected drift](/xl-deploy/how-to/view-and-correct-drift.html)
