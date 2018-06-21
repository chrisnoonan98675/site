---
title: Using Docker images in XL-release
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

To download a docker image for XL-Release, click [here](https://github.com/xebialabs/xl-release-docker-image) [https://github.com/xebialabs/xl-release-docker-image]. Is the right location?

### Description / advantages of using the docker image

### Requirements and prerequisites
The following are required to run the Docker image for XL-Release:
- *An installed and licensed version of XL Release 8.1.0 or later.*
- *An installed version of Docker. Version?*

### Using the Docker image for XL-Release

The Docker image for XL Release behaves similarly to the existing installation ZIP of XL Release, this ensures that the existing setup, upgrade and operations procedure remain valid.

The directory `/opt/xl-release-server` contains an extracted installation setup, augmented with a number of directories that will be discussed below.

### Running the container
The following section describes the methods that are available for running a container.

#### Demonstrations and simple trials
**Note:** This method does not include persistence and must not be used in production environments.

**Note:** When no volumes are defined, the container will keep state between stopping and starting.

1. Run the XL Release container with the following command:
    ```
    $ docker run -d -p 5516:5516 --name xlr xebialabs/xl-release:v8.1.0-rc.2
    ```
2. Run the logs command:
    ```
    $ docker logs -f xlr
    ```
1. Observe the logs to capture the generated admin password. Continue observing the logs until you see the "XL Release has started" message.

To stop the XL Release container:
    ```
    $ docker stop xlr
    ```

To start the XL Release container:
    ```
    $ docker start xlr
    ```

#### Automated testing

By default, XL Release uses a randomly generated admin password. This makes it complicated to include the XL Release container in an automated test or use case that depends using a known admin password.

To create a predefined `ADMIN_PASSWORD` environment variable, enter the following command:
```
$ docker run -d -p 5516:5516 -e ADMIN_PASSWORD#secret --name xlr xebialabs/xl-release:v8.1.0-rc.2
```

#### Production usage - includes persistence

**Note:** In production setups, volumes must be defined and XL Release must be configured to store its state to an external database.

The previous two set-ups did not persist the configuration, repository, and archive data across container runs.

To ensure persistence when reconfiguring, volumes must be mounted at the `conf`, `repository` and `archive` mount points:

```
$ docker run -d -p 5516:5516 \
-v ${HOME}/xl-release-server/conf:/opt/xl-release-server/conf:rw \
-v ${HOME}/xl-release-server/repository:/opt/xl-release-server/repository:rw \
-v ${HOME}/xl-release-server/archive:/opt/xl-release-server/archive:rw \
--name xlr xebialabs/xl-release:v8.1.0-rc.2
```

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
