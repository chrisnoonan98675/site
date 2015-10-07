---
title: Introduction to the release dashboard
subject:
- Release dashboard
categories:
- xl-deploy
tags:
- gui
- checklist
- security
- dashboard
---

Most organizations have a process around releasing software to their environments. Typically, application versions are promoted to a number of environments before being released to production. In each environment, the application version is integrated or tested before being allowed to progress to the next stage. XL Deploy offers the Release Dashboard as a way to get insight into this process.

## Preparing the Release Dashboard

Before using XL Deploy's Release Dashboard, you need to define the deployment pipeline, deployment checklists and checklist items.

### Defining the deployment pipeline

The deployment pipeline is the sequence of environments that an application is deployed to during it's lifecycle. Each application can be configured with it's own deployment pipeline. An application version starts in the first environment and is promoted to each of the following environments in turn. It is also possible for a particular version to be deployed to multiple environments at once. 

A common example of such a pipeline is:  _Development_, _Testing_, _Acceptance_ and _Production_. If this deployment pipeline was associated with the PetClinic application, then version 1.0 of PetClinic would be deployed to the Development environment first, then the Testing environment, etc.

You need to define the deployment pipeline for every application that you want to see in the Release Dashboard.

### Defining deployment checklists and checklist items

To ensure the quality of the deployment pipeline, each environment in the pipeline can be associated with a checklist that a deployment package must satisfy before being deployed to the environment. It is also possible to include an environment in a deployment pipeline that does not have a checklist. 

XL Deploy supports checkboxes and text fields as checklist items. A checkbox condition is met if it is checked. A text field condition is met if it is not empty.

Some examples of checklist items are:

* Have release notes been included in the deployment package?
* Has the application version been performance tested?
* Is there a change ticket number associated with the deployment?
* Has the business owner signed off on the deployment?

You need to define the deployment checklist for every environment that you want to use in a deployment pipeline. 

## Release Dashboard and Security

The XL Deploy security system determines who can do what in XL Deploy and this also applies to the Release Dashboard. The following items describe how security affects the functioning of the Release Dashboard:

* The values for deployment checklist items are stored _on the deployment package CI_. This means that whoever has `repo#edit` permission on the deployment package is allowed to set or unset these items. In addition, it's possible to restrict the set of users that can set a checklist item to a certain role.
* When viewing the deployment pipeline, you will only see the environments that the logged in user has access to. That is, if user 'developer' has access to the Development and Testing environments, he will only see these environments, even if the complete deployment pipeline includes additional environments.
* Regular deployment permissions apply when a deployment is initiated from the Release Dashboard.
