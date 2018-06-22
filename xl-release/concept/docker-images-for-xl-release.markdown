---
title: Docker images for XL Release
categories:
- xl-release
subject:
- Docker
tags:
- docker
- images
---

Two flavors of Docker images are available for XL Release:

* The regular image, which is based on the Alpine Linux flavor of the https://hub.docker.com/_/openjdk/[OpenJDK base image]
* The Red Hat certified image, which is based on the rhel7-atomic base image

### Description / advantages of using the docker image

### Requirements and prerequisites
The following are required to run the Docker image for XL-Release:
- *An installed and licensed version of XL Release 8.1.0 or later.*
- *An installed version of Docker. Version?*

### Environment variables

The `xebialabs/xl-release` image exposes the following environment variables to change the default setup procedure that runs when a fresh installation is detected:

* `ADMIN_PASSWORD` sets the admin password for a new installation. If this environment variable is not set, a random admin password is generated and printed to the console of the container.
* `REPOSITORY_KEYSTORE_PASSPHRASE` sets the passphrase of the repository encryption key for a new installation. If this environment variable is not set, a random passphrase is generated and printed to the console of the container.

### Ports

The `xebialabs/xl-release` image exposes port 5516 over which the XL Release user interface and REST API are served.

### Volumes

The `xebialabs/xl-release` image exposes the following directories as mount points:

* `/opt/xl-release-server/archive`
* `/opt/xl-release-server/conf`
* `/opt/xl-release-server/ext`
* `/opt/xl-release-server/hotfix`
* `/opt/xl-release-server/plugins`
* `/opt/xl-release-server/repository`

Providing volumes for these mount points is optional, but does guarantee persistence across container runs. The sections below will describe how the mountpoints are handled.

#### Configuration (`conf`)

The `/opt/xl-release-server/conf` on the image is empty. The first time that the container is started, the contents of the `/opt/xl-release-server/default-conf` directory are copied into the `conf` directory. The files in the `default-conf` are similar to those in a regular XL Release installation ZIP, except that they have been tweaked for XL Release running in a container.

If a volume is provided for the `/opt/xl-release-server/conf` mount point, the configuration (including the admin password, the product license) is persisted across container runs. Also, if any files are present on the volume the first time that the container is started, they will not be overwritten by the files from the `default-conf` directory. This allows one to set configurations like the `logback.xml` ahead of time.

In addition to the `conf` directory, the image also contains `/opt/xl-release-server/node-conf` directory. This directory contains a `xl-release.conf` file that contains two properties: `xl.cluster.node.id` and `xl.cluster.node.hostname`. Every time the container is started, the IP address of the container is stored in these properties. This configuration file overrides the values in the `/opt/xl-release-server/conf/xl-release.conf` configuration file and can be used to configure XL Release in cluster mode.

#### Plugins (`plugins`)

Just like the `conf` directory, the `/opt/xl-release-server/plugins` directory on the image is empty. The first time that the container is started, the contents of the `/opt/xl-release-server/default-plugins` directory are copied into the `plugins` directory.

And just like with the `conf` directory, if a plugin is already present in a volume mounted on the `plugins` mount point, it is not overwritten. This behavior also recognizes different versions of the same plugin.

If a volume is provided for the `/opt/xl-release-server/plugins` directory, it can be used to pre-select certain plugins before startup. It also means that you'll have to upgrade the plugins manually, as per the https://docs.xebialabs.com/xl-release/how-to/upgrade-xl-release.html[Upgrade instructions for XL Release].

#### Data (`repository` and `archive`)

In a default setup, where the embedded H2 and Derby databases are used to persist the repository and the archive data respectively, those database are stored in the `/opt/xl-release-server/repository` and `/opt/xl-release-server/archive` directories. Provide a mount point for these volumes to ensure that the repository and archive data are preserved across container runs.

#### Customizations and hotfixes (`ext` and `hotfix`)

The `/opt/xl-release-server/ext` and `/opt/xl-release-server/hotfix` volumes are provided to allow for customizations such as https://docs.xebialabs.com/xl-release/how-to/create-custom-task-types.html[custom tasks] and to install hotfixes.
