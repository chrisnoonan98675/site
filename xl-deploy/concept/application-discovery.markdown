---
title: Application discovery
no_index: true
---

XebiaLabs supports governance in a microservices environment through two main features: application discovery and application dependencies. In this context, a microservices environment is characterized by a number of applications that are sufficiently large, and changing sufficiently rapidly, that explicitly registering each service with an ARA platform is not feasible. Also, microservices are characterized by a large number of dependencies, the management and validation of which is challenging if they are not supported by automation.

## Application discovery

Through application discovery, XebiaLabs can automatically create deployment packages based on information that is discovered from runtime platforms in the environment, whether the platform is an existing system such as [WebSphere](http://www-03.ibm.com/software/products/en/appserv-was) or a new framework such as [Kubernetes](http://kubernetes.io/). XebiaLabs provides out-of-the-box discovery logic for popular platforms as part of its plugins; also, users can cover additional frameworks by extending the discovery logic using a public API.

For more information, refer to:

* [Discover middleware using the XL Deploy GUI](/xl-deploy/how-to/discover-middleware.html)
* [Discover middleware using the XL Deploy CLI](/xl-deploy/how-to/discover-middleware-using-the-xl-deploy-cli.html)
* [Extend XL Deploy discovery](/xl-deploy/concept/discovery-in-the-xl-deploy-generic-plugin.html)
{% comment %}* link to Ravan’s application reverse engineering doc{% endcomment %}

## Application dependencies

In addition to automatically producing application packages for deployment, discovery can register dependencies among packages. The relevant dependency information is extracted from service metadata that is provided by the target framework (for example, [Docker Swarm](https://www.docker.com/products/docker-swarm) orchestration).

For more information, refer to:

* [Application dependencies in XL Deploy](/xl-deploy/concept/application-dependencies-in-xl-deploy.html)
* [XebiaLabs Docker plugin](https://xebialabs.com/plugins/docker)
* [Docker Container Delivery vs. Traditional App Delivery in XL Deploy](https://blog.xebialabs.com/2015/09/16/docker-container-delivery-vs-traditional-app-delivery-in-xl-deploy/)

## Previewing before deployment

The [Compare feature](/xl-deploy/how-to/compare-configuration-items.html) allows you to compare the current and past states of configuration items (CIs). XebiaLabs also provides a preview or "future view" of the state of an environment after a planned deployment. Users prepare deployments by creating this view in the XL Deploy Deployment Workspace.

The Deployment Workspace and Plan Analyzer provide an overview of the desired future state of the environment at a high level (that is, which components will go where) as well as at the detailed, pre-property level (that is, what will the value of this particular setting be after deployment). The Plan Analyzer also provides a detailed preview of the exact commands that XebiaLabs will execute to achieve the future state.

For more information, refer to:

* [Deploy an application](/xl-deploy/how-to/deploy-an-application.html)
* [Preview the deployment plan](/xl-deploy/how-to/preview-the-deployment-plan.html)

![Deployed properties](/xl-deploy/concept/images/deployment-workspace-deployed-properties.png)
