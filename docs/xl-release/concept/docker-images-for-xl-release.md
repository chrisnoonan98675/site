---
title: Docker images for XL Release
categories:
xl-release
subject:
Docker
tags:
docker
images
---

The available Docker images for XL Release:

* A regular image based on Debian (slim) Linux flavor of the [OpenJDK base image](https://hub.docker.com/_/openjdk/)
* The Red Hat certified image based on the `rhel7-atomic` base image

## Requirements and prerequisites

To run the Docker image for XL Release you must have:
* An XL Release valid license
* Docker version 18.03.1-ce installed

## Persistent configuration setup

To use the software in optimal conditions, you must make sure that all the data (repository, configuration, plugins) is stored outside the container. This is required to maintain the container life cycle (for example: during updates and to perform proper back up).

You can do this in Docker containers by specifying volume mount points. These are links that allow a file system directory outside the container to be used inside the container.

### Set environment variables

To change the default setup procedure that runs when a fresh installation is detected, the XL Release image exposes the following environment variables:

* `ADMIN_PASSWORD`: sets the admin password for a new installation. If this environment variable is not set, a random admin password is generated and printed to the console of the container.
* `REPOSITORY_KEYSTORE_PASSPHRASE`: sets the passphrase of the repository encryption key for a new installation. If this environment variable is not set, a random passphrase is generated and printed to the console of the container.

### Ports exposed

The XL Release image exposes port `5516` over which the XL Release user interface and REST API are served.

### Set volumes as mount points

The XL Release image exposes the following directories as mount points:

* `/opt/xl-release-server/archive`
* `/opt/xl-release-server/conf`
* `/opt/xl-release-server/ext`
* `/opt/xl-release-server/hotfix`
* `/opt/xl-release-server/plugins`
* `/opt/xl-release-server/repository`

Providing volumes for these mount points is optional and guarantees persistence across container runs. The sections below describe how the mount points are handled.

#### Configuration directory (`conf`)

The `/opt/xl-release-server/conf` folder on the image is empty. The first time the container is started, the content of the `/opt/xl-release-server/default-conf` directory is copied into the `conf` directory. The files in the `default-conf` are similar to the files in a regular XL Release installation ZIP. except that they have been tweaked for XL Release running in a container.

If a volume is provided for the `/opt/xl-release-server/conf` mount point, the configuration (including the admin password and the product license) is persisted across all container runs. If any files are present on the volume the first time that the container starts, they will not be overwritten by the files from the `default-conf` directory. This allows you to set configurations like the `logback.xml` ahead of time.

The image also contains the `/opt/xl-release-server/node-conf` directory. This directory contains an `xl-release.conf` file with two properties: `xl.cluster.node.id` and `xl.cluster.node.hostname`. Every time the container starts, the IP address of the container is stored in these properties. This configuration file overrides the values in the `/opt/xl-release-server/conf/xl-release.conf` configuration file and can be used to configure XL Release in cluster mode.

#### Plugins directory (`plugins`)

The `/opt/xl-release-server/plugins` folder on the image is empty. The first time that the container is started, the content of the `/opt/xl-release-server/default-plugins` directory is copied into the `plugins` directory.

If a plugin is already present in a volume mounted on the `plugins` mount point, it is not overwritten. This behavior also identifies different versions of the same plugin.

If you provide a volume for the `/opt/xl-release-server/plugins` directory, you can use it to pre-select specific plugins before startup. You must upgrade the plugins manually, see [Upgrade instructions for XL Release](https://docs.xebialabs.com/xl-release/how-to/upgrade-xl-release.html) for more information.

#### Data directories (`repository` and `archive`)

In the default setup, the embedded H2 and Derby databases are used to persist the repository and the archive data respectively, and are stored in the `/opt/xl-release-server/repository` and `/opt/xl-release-server/archive` directories. Provide a mount point for these volumes to ensure that the repository and archive data are preserved across container runs. To set up an external database server, refer to the documentation for [XL Release](/xl-release/how-to/configure-the-xl-release-sql-repository-in-a-database.html).

#### Customizations and hotfixes directories (`ext` and `hotfix`)

The `/opt/xl-release-server/ext` and `/opt/xl-release-server/hotfix` volumes are provided to allow customizations for [custom tasks](https://docs.xebialabs.com/xl-release/how-to/create-custom-task-types.html) and to install hotfixes.

### Persistent configuration example

The mount points are passed to the Docker command using the `-v` parameter.

For example, the following command starts an XL Release container with persistent configuration and storage:

        $ docker run -d -p 5516:5516 \
        -v ${HOME}/XebiaLabs/xl-release-docker/conf:/opt/xl-release-server/conf:rw \
        -v ${HOME}/XebiaLabs/xl-release-docker/repository:/opt/xl-release-server/repository:rw \
        -v ${HOME}/XebiaLabs/xl-release-docker/archive:/opt/xl-release-server/archive:rw \
        --name xlr xebialabs/xl-release:8.1

**Note:** Before starting the containers, save the license file in the local `conf` directory:

           <USER_HOME>/XebiaLabs/xl-release-docker/conf/xl-release-license.lic   
