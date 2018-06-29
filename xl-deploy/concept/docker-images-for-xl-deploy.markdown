---
title: Docker images for XL Deploy
categories:
- xl-deploy
subject:
- Docker
tags:
- docker
- images
---

There are two types of Docker images available for XL Deploy:

* A regular image based on Debian (slim) Linux flavor of the [OpenJDK base image](https://hub.docker.com/_/openjdk/)
* An alternative image based on the Alpine Linux flavor of the [OpenJDK base image](https://hub.docker.com/_/openjdk/)

// ## Description / advantages of using the docker image

## Requirements and prerequisites

To run the Docker image for XL Deploy you must have:
* An XL Deploy valid license
// * An installed version of Docker. Version?*

## Persistent configuration setup

To use the software in optimal conditions, you must make sure that all the data (repository, configuration, plugins) is stored outside the container. This is required to maintain the container life cycle (for example: during updates and to perform proper back up).

You can do this in Docker containers by specifying volume mount points. These are links that allow a file system directory outside the container to be used inside the container.

### Set environment variables

To change the default setup procedure that runs when a fresh installation is detected, the XL Deploy image exposes the following environment variables:

* `ADMIN_PASSWORD`: sets the admin password for a new installation. If this environment variable is not set, a random admin password is generated and printed to the console of the container.
* `REPOSITORY_KEYSTORE_PASSPHRASE`: sets the passphrase of the repository encryption key for a new installation. If this environment variable is not set, a random passphrase is generated and printed to the console of the container.

### Ports exposed

The XL Deploy image exposes port `4516` over which the XL Deploy user interface and REST API are served.

### Set volumes as mount points

The XL Deploy image exposes the following directories as mount points:

* `/opt/xl-deploy-server/conf`
* `/opt/xl-deploy-server/ext`
* `/opt/xl-deploy-server/hotfix/lib`
* `/opt/xl-deploy-server/hotfix/plugins`
* `/opt/xl-deploy-server/plugins`
* `/opt/xl-deploy-server/repository`

Providing volumes for these mount points is optional and guarantees persistence across container runs. The sections below describe how the mount points are handled.

#### Configuration directory (`conf`)

The `/opt/xl-deploy-server/conf` folder on the image is empty. The first time the container is started, the content of the `/opt/xl-deploy-server/default-conf` directory is copied into the `conf` directory. The files in the `default-conf` are similar to the files in a regular XL Deploy installation ZIP. except that they have been tweaked for XL Deploy running in a container.

If a volume is provided for the `/opt/xl-deploy-server/conf` mount point, the configuration (including the admin password and the product license) is persisted across all container runs. If any files are present on the volume the first time that the container starts, they will not be overwritten by the files from the `default-conf` directory. This allows you to set configurations like the `logback.xml` ahead of time.

The image also contains the `/opt/xl-deploy-server/node-conf` directory. This directory contains an `xl-deploy.conf` file with two properties: `xl.cluster.node.id` and `xl.cluster.node.hostname`. Every time the container starts, the IP address of the container is stored in these properties. This configuration file overrides the values in the `/opt/xl-deploy-server/conf/xl-deploy.conf` configuration file and can be used to configure XL Deploy in cluster mode.

#### Plugins directory (`plugins`)

The `/opt/xl-deploy-server/plugins` folder on the image is empty. The first time that the container is started, the content of the `/opt/xl-deploy-server/default-plugins` directory is copied into the `plugins` directory.

If a plugin is already present in a volume mounted on the `plugins` mount point, it is not overwritten. This behavior also identifies different versions of the same plugin.

If you provide a volume for the `/opt/xl-deploy-server/plugins` directory, you can use it to pre-select specific plugins before startup. You must upgrade the plugins manually, see [Upgrade instructions for XL Deploy](https://docs.xebialabs.com/xl-deploy/how-to/upgrade-xl-deploy.html) for more information.

#### Data directories (`repository` and `archive`)

In the default setup, the embedded H2 and Derby databases are used to persist the repository and the archive data respectively and are stored in the `/opt/xl-deploy-server/repository` and `/opt/xl-deploy-server/archive` directories. Provide a mount point for these volumes to ensure that the repository and archive data are preserved across container runs. To set up an external database server, refer to the documentation for [XL Deploy](/xl-deploy/how-to/configure-the-xl-deploy-sql-repository.html).

#### Customizations and hotfixes directories (`ext` and `hotfix`)

The `/opt/xl-deploy-server/ext`, `/opt/xl-deploy-server/hotfix/lib` and `/opt/xl-deploy-server/hotfix/plugins` volumes are provided to allow customizations and to install hotfixes.

### Persistent configuration examples

The mount points are passed to the Docker command using the `-v` parameter.

For example, the following command starts an XL Deploy container with persistent configuration and storage:

        $ docker run -d -p 4516:4516 \
        -v ${HOME}/XebiaLabs/xl-deploy-docker/conf:/opt/xl-deploy-server/conf:rw \
        -v ${HOME}/XebiaLabs/xl-deploy-docker/repository:/opt/xl-deploy-server/repository:rw \
        -v ${HOME}/XebiaLabs/xl-deploy-docker/archive:/opt/xl-deploy-server/archive:rw \
        --name xld xebialabs/xl-deploy:8.1

With multiple mount points, it is easier to use a Docker Compose file.

In this example, all mount points are mapped to directories in the `<USER_HOME>/XebiaLabs` folder.

        xld:
          image: xebialabs/xl-deploy:8.1
          container_name: xld
          ports:
           - "4516:4516"
          links:
           - xld
          volumes:
           - ~/XebiaLabs/xl-deploy-docker/archive/lib:/opt/xl-deploy-server/archive
           - ~/XebiaLabs/xl-deploy-docker/conf:/opt/xl-deploy-server/conf
           - ~/XebiaLabs/xl-deploy-docker/ext:/opt/xl-deploy-server/ext
           - ~/XebiaLabs/xl-deploy-docker/hotfix/:/opt/xl-deploy-server/hotfix/
           - ~/XebiaLabs/xl-deploy-docker/plugins:/opt/xl-deploy-server/plugins
           - ~/XebiaLabs/xl-deploy-docker/repository:/opt/xl-deploy-server/repository

Run the Docker Compose file using the `docker-compose up -d` command and inspect the content of the folders when the servers are up and running.

**Note:** Before starting the containers, save the license file in the local `conf` directory:

           <USER_HOME>/XebiaLabs/xl-deploy-docker/conf/xl-deploy-license.lic   
