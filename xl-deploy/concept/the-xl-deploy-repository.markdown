---
title: The XL Deploy Repository
categories:
- xl-deploy
subject:
- Repository
tags:
- infrastructure
- package
- application
- environment
- repository
weight: 155
---

XL Deploy's database is called the _Repository_. It stores all configuration items (CIs), binary files (such as _deployment packages_), and XL Deploy's security configuration (user accounts and rights). The Repository can be stored on disk (the default) or in a [relational database](/xl-deploy/how-to/configure-the-xl-deploy-repository.html#using-a-database).

## Repository IDs

Each CI in XL Deploy has an ID that uniquely identifies the CI. This ID is a path that determines the place of the CI in the Repository. For instance, a CI with ID "Applications/PetClinic/1.0" will appear in the **PetClinic** subdirectory under the **Applications** root directory.

## Repository directory structure

The Repository has a hierarchical layout and a version history. All CIs of all types are stored here. The top-level directories indicate the type of CI stored below it. Depending on the type of CI, the Repository stores it under a particular directory:

* Application and deployment package CIs are stored in the **Applications** directory
* Environment and dictionary CIs are stored in the **Environments** directory
* Middleware CIs (representing hosts, servers, and so on) are stored in the **Infrastructure** directory
* XL Deploy configuration CIs (such as policies and deployment pipelines) are stored in the **Configuration** directory

Also, in XL Deploy 5.5.x there are additional directories:

* Blueprint and provisioning package CIs are stored in the **Blueprints** directory
* Provisioning environments and dictionary CIs are stored in the **ProvisioningEnvironments** directory
* Provider CIs are stored in the **Providers** directory

## Version control

Everything that is stored in the Repository is fully versioned, so that any change to an item or its properties creates a new, timestamped version. Every change to every item in the Repository is logged and stored. This makes it possible to compare a history of all changes to every CI in the Repository.

XL Deploy also retains the history of all changes to _deleted_ CIs. Even if a CI is deleted, the storage it uses will not be freed up so that it is possible to retrieve the CI as it existed before the deletion.

## Containment and references

XL Deploy's Repository contains CIs that refer to other CIs. There are two ways in which CIs can refer to each other:

* **Containment**. In this case, one CI _contains_ another CI. If the parent CI is removed, so is the child CI. An example of this type of reference is an Environment CI and its deployed applications.
* **Reference**. In this case, one CI _refers_ to another CI. If the referring CI is removed, the referred CI is unchanged. Removing a CI when it is still being referred to is not allowed. An example of this type of reference is an environment CI and its middleware. The middleware exists in the **Infrastructure** directory independently of the environments the middleware is in.

## Deployed applications

A deployed application is the result of deploying a _deployment package_ to an _environment_. Deployed applications have a special structure in the Repository. While performing the deployment, package members are installed as _deployed items_ on individual environment members. In the Repository, the _deployed application_ CI is stored under the **Environment** node. Each of the deployed items are stored under the infrastructure members in the **Infrastructure** node.

So, deployed applications exist in both the **Environment** as well as **Infrastructure** folder. This has some consequences for the [security setup](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html#local-permissions).

## Searching and filtering

You can search and filter the CIs in the repository using the search box in the left pane. For example, to search for an application, type a search term in the box and press ENTER.

To clear the search results, click ![Search](/images/cancel-search.png).

**Tip:** The [`GET /repository/query`](/xl-deploy/5.5.x/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/query:GET) API call provides a more robust search.
