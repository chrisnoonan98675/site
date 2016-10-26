---
title: Using the XL Release Docker Compose plugin
categories:
- xl-release
subject:
- Task types
tags:
- plugin
- docker
- task
since:
- XL Release 5.0.0
---

The XL Release [Docker Compose](https://docs.docker.com/compose/) plugin allows XL Release to create and destroy multi-container Docker applications on a Docker host. It includes the following task types:

* **Docker Compose: Up**
* **Docker Compose: Start**
* **Docker Compose: Stop**
* **Docker Compose: Down**
* **Docker Compose: Command**

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Docker Compose tasks have a blue border.

## Features

* Create and start containers and services on a Docker host
* Stop containers and services on a Docker host
* Remove containers, networks, images, and volumes on a Docker host

## Requirements

The Docker Compose plugin requires the XL Release [Remoting plugin](/xl-release/how-to/remoting-plugin.html) to be installed.

## Set up a Docker Compose server

To set up a connection to a Unix server running Docker Compose:

1. In XL Release, go to **Settings** > **Task configurations** and click **Add Unix Host**.

    **Note:** Prior to XL Release 6.0.0, go to **Settings** > **Configuration**.

2. In the **Address** box, enter the IP address or host name of the remote machine running Docker Compose.
3. In the **Port** box, enter the SSH port of the remote machine.
4. In the **Username** and **Password** boxes, specify the user name and password of the SSH user that XL Release should use when connecting to the remote machine.
5. In the **Sudo Username** box, enter the user name of the `sudo` user on the remote machine (for example, `root`).

    ![Create Unix host](../images/xlr-docker-compose-plugin/docker-compose-unix-host.png)

## Docker Compose: Up

The **Docker Compose: Up** task type creates a container on a Docker host. It requires you to specify the configuration in YAML format. You can specify the configuration:

* By providing YAML in the task
* By providing a URL to a YAML file

If the URL is secure, you must also provide credentials in the **Username** and **Password** boxes.

![Docker Compose Up task with inline configuration](../images/xlr-docker-compose-plugin/docker-compose-up-task-inline.png)

## Docker Compose: Start

The **Docker Compose: Start** task type starts a container or service on a Docker host. It requires you to specify the configuration in YAML format. You can specify the configuration:

* By providing YAML in the task
* By providing a URL to a YAML file

If the URL is secure, you must also provide credentials in the **Username** and **Password** boxes.

## Docker Compose: Stop

The **Docker Compose: Stop** task type stops a container or service on a Docker host. It requires you to specify the configuration in YAML format. You can specify the configuration:

* By providing YAML in the task
* By providing a URL to a YAML file

If the URL is secure, you must also provide credentials in the **Username** and **Password** boxes.

## Docker Compose: Down

The **Docker Compose: Down** task type removes a container, network, image, or volume on a Docker host. It requires you to specify the configuration in YAML format. You can specify the configuration:

* By providing YAML in the task
* By providing a URL to a YAML file

If the URL is secure, you must also provide credentials in the **Username** and **Password** boxes.

## Docker Compose: Command

The **Docker Compose: Command** task type executes any `docker-compose` command. For example, the following command willl force running containers to stop:

    docker-compose kill

For a complete list of the supported commands, refer to the [Docker Compose command-line reference](https://docs.docker.com/compose/reference/).
