---
title: The XL Deploy repository
categories:
- xl-deploy
subject:
- Repository
tags:
- infrastructure
- package
- application
- environment
---

XL Deploy's database is called the _repository_. It stores all configuration items (CIs), binary files (such as _deployment packages_) and XL Deploy's security configuration (user accounts and rights). The repository can be stored on disk (default) or in a relational database (see the [System Administration Manual](https://docs.xebialabs.com/xl-deploy/4.5.x/systemadminmanual.html)).

Each CI in XL Deploy has an ID that uniquely identifies the CI. This ID is a path that determines the place of the CI in the repository. For instance, a CI with ID "Applications/PetClinic/1.0" will appear in the **PetClinic** subfolder under the **Applications** root folder.

The repository has a hierarchical layout and a version history. All CIs of all types are stored here. The top-level folders indicate the type of CI stored below it. Depending on the type of CI, the repository stores it under a particular folder:

* Application CIs are stored under the **Applications** folder
* Environment and Dictionary CIs are stored under the **Environments** folder
* Middleware CIs (representing hosts, servers, and so on) are stored under the **Infrastructure** folder

The repository acts as a version control system, that is, every change to every object in the repository is logged and stored. This makes it possible to compare a history of all changes to every CI in the repository. XL Deploy also retains the history of all changes to _deleted_ CIs. Even if a CI is deleted, the storage it uses will not be freed up so that it is possible to retrieve the CI as it existed before the deletion. See the [System Administration Manual](https://docs.xebialabs.com/xl-deploy/4.5.x/systemadminmanual.html) for more information about managing the repository.

## Containment and references

XL Deploy's repository contains CIs that refer to other CIs. There are two ways in which CIs can refer to each other:

* **Containment**. In this case, one CI _contains_ another CI. If the parent CI is removed, so is the child CI. An example of this type of reference is an Environment CI and its deployed applications.
* **Reference**. In this case, one CI _refers_ to another CI. If the referring CI is removed, the referred CI is unchanged. Removing a CI when it is still being referred to is not allowed. An example of this type of reference is an Environment CI and its middleware. The middleware exists in the **/Infrastructure** folder independently of the environments the middleware is in.

## Deployed applications

A deployed application is the result of deploying a _deployment package_ to an _environment_. Deployed applications have a special structure in the repository. While performing the deployment, package members are installed as _deployed items_ on individual environment members. In the repository, the _deployed application_ CI is stored under the **Environment** node. Each of the deployed items are stored under the infrastructure members in the **Infrastructure** node.

So, deployed applications exist in both the **Environment** as well as **Infrastructure** folder. This has some consequences for the security setup. See the [System Administration Manual](https://docs.xebialabs.com/xl-deploy/4.5.x/systemadminmanual.html) for details.
