---
title: Introduction to the XL Release Git trigger plugin
categories:
- xl-release
subject:
- Git trigger plugin
tags:
- plugin
- git
- trigger
---

In order to use Git trigger plugin, you need to create a **Git Repository**. Git repositories could be managed from the configuration screen.

A Git Repository has the following properties:

* **Title**: The title of the repository.
* **Url**: The address where the server is reachable.
* **Username**: The login user ID on the server.
* **Password**: The login user password on the server.

The git trigger is used to poll periodically a Git repository and trigger a release if it detects a new commit. The specific fields of the plugin during its creation are:

![Git Plugin](../images/git-plugin-fields.png)

* **Git Repository**: The Git repository to poll (mandatory)
* **Branch**: The git branch you want to watch
* **Username**: The username used to connect to this repository (left blank if there is no authentication)
* **Password**: The password used to connect to this repository (left blank if there is no authentication)

This trigger exposes:

* **Commit Id**: Corresponding to the SHA1 of the new commit

## Specific permission

This plugin could require an edition of the `conf/script.policy` file. Ensure that the following line is present:

	grant {
	    ...

        permission  java.net.NetPermission "getProxySelector";

	    ...
	};

The XL Release server must be restarted after the `conf/script.policy` file is changed.
