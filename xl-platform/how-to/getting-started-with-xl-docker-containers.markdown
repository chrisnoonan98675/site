---
title: Getting started with the XebiaLabs Docker containers
categories:
- xl-platform
- xl-deploy
- xl-release
subject:
- Docker
tags:
- docker
- containers
since:
- XL Release 8.1.0
---

With the release of the XebiaLabs DevOps Platform 8.1.0, Docker images for XL Release and XL Deploy are officially supported. The Docker images are published to the Docker Hub for each release, starting with version 8.1.0.

The images can be found here:

* [XL Release](https://hub.docker.com/r/xebialabs/xl-release/)
* [XL Deploy](https://hub.docker.com/r/xebialabs/xl-deploy/)

## Set up and start the Docker containers

There are multiple methods to set up and start the Docket images for XL Release and XL Deploy.

### Fast setup using the `docker-compose.yaml` file

To start both XL Deploy and XL Release with a persistent setup:

1. Download the Docker Compose file using the following command:

        $ curl https://docs.xebialabs.com/xl-platform/docker-compose.yaml > docker-compose.yaml

1. Open the `docker-compose.yaml` file and change the ADMIN_PASSWORD for both servers to a secure password.
1. Run the file with Docker Compose:

        $ docker-compose up -d

1. You can access XL Release at http://localhost:5516 and XL Deploy at http://localhost:4516.

You can now remove the passwords from the `docker-compose.yaml` file.

You must provide a valid license before you can log in. Browse to the above URLs and paste the licenses for the appropriate product. If you do not have a license yet, apply for an [XL Release trial license](https://xebialabs.com/products/xl-release/trial/ ) or an [XL Deploy trial license](https://xebialabs.com/products/xl-deploy/trial/) on the XebiaLabs web site.

## Start containers with quick run commands

The quick way to start the containers is using these commands:

* XL Release:

        $ docker run -d -p 5516:5516 -e ADMIN_PASSWORD=admin --name xlr xebialabs/xl-release:8.1

* XL Deploy:

        $ docker run -d -p 4516:4516 -e ADMIN_PASSWORD=admin --name xld xebialabs/xl-deploy:8.1

XL Release will run on http://localhost:5516 and XL Deploy on http://localhost:5516. For both products, you can log in with `username`: `admin` and `password`: `admin`.

You must provide a valid license before you can log in. Browse to the above URLs and paste the licenses for the appropriate product. If you do not have a license yet, apply for an [XL Release trial license](https://xebialabs.com/products/xl-release/trial/ ) or an [XL Deploy trial license](https://xebialabs.com/products/xl-deploy/trial/) on the XebiaLabs web site.

## Secure container setup using a generated password

You can make the setup more secure and start with a generated admin password instead of the default password.

If the environment variable ADMIN_PASSWORD is not set and you are starting up for the first time, the container will generate a random password and print it in the logs.

To trigger this behavior, remove the ADMIN_PASSWORD from the Docker run command:

    $ docker run -d -p 5516:5516 --name xlr xebialabs/xl-release:8.1

The generated password is printed when the container starts up and can be found at the top of the log file. You can view the logs using the following command:

    $ docker logs xlr

Search for the following line:

    ... Generating admin password: [PASSWORD IS PRINTED HERE]

This applies for both XL Release and XL Deploy containers.

## Stopping and removing containers

To stop the containers, use the following commands:

    $ docker stop xlr
    $ docker stop xld

The state of the application is saved in the containers. They can be started again using this command:

    $ docker start xlr xld

You can also remove the containers completely:

    $ docker rm xlr xld

**Note:** This will result in a blank slate. When you run the containers again with the simple run command, the database will be empty, you will receive a new admin password, and the license must be entered again. For a permanent setup using persistent volumes, see [Setting up a persistent configuration](/xl-platform/how-to/getting-started-with-xl-docker-containers.html#setting-up-a-persistent-configuration).

## Linking XL Release and XL Deploy using Docker Compose

If you want to use XL Deploy and XL Release together, both servers need to be able identify each other. When you start a single instance of a Docker container, the network is not configured to allow the two servers to identify each other. To link the instances of the two servers, you can use Docker Compose. This also allows you to start both containers at the same time and move all command line options into a file.

1. Create a file called `docker-compose.yaml` and copy the following contents inside:

        xld:
          image: xebialabs/xl-deploy:8.1
          container_name: xld
          ports:
           - "4516:4516"

        xlr:
          image: xebialabs/xl-release:8.1
          container_name: xlr
          ports:
           - "5516:5516"
          links:
           - xld

1. Start both containers with this single command:

           $ docker-compose up -d

1. Both containers will start and the password can be viewed using these commands:

           $ docker logs xlr
           $ docker logs xld

1. Log in to XL Release with the admin password. You can configure the XL Deploy server under **Settings** > **Shared Configuration**. Use the following settings:

![image](/images/xld-shared-configuration-docker-compose.png)

## Setting up a persistent configuration

To use the software in optimal conditions, you must make sure that all the data (repository, configuration, plugins) is stored outside the container. This is required to maintain the container life cycle (for example: during updates and to perform proper back up).

You can do this in Docker containers by specifying volume mount points. These are links that allow a file system directory outside the container to be used inside the container.

### Use XL Deploy and XL Release directories as mount points

The XL Release and XL Deploy containers share a similar setup and provide the following mount points:

#### Configuration directory

The `conf` directory contains the server configuration.

The first time that the container is started, this directory is populated with default files that are configured for running in a container.

If any files are present on the volume on the initial start of the container, they will not be overwritten by the container. This allows you to set configurations such as the license file ahead of time.

#### Plugins directory

Product plugins are stored in the `plugins` directory.

The first time that the container starts, it is populated with the default plugins.

#### Data directories

The default setup of XL Release and XL Deploy uses embedded databases and the data is stored in `repository` and `archive` (XL Release only) directories. To set up an external database server, refer to the documentation for [XL Release](/xl-release/how-to/configure-the-xl-release-sql-repository-in-a-database.html) or [XL Deploy](/xl-deploy/how-to/configure-the-xl-deploy-sql-repository.html).

#### Customizations and hotfixes directories

* `ext`
* `hotfix` (XL Release)
* `hotfix/lib` (XL Deploy)
* `hotfix/plugins` (XL Deploy)

### Persistent configuration examples

The mount points are passed to the Docker command using the `-v` parameter.

For example, the following command starts an XL Release container with persistent configuration and storage:

        $ docker run -d -p 5516:5516 \
        -v ${HOME}/XebiaLabs/xl-release-docker/conf:/opt/xl-release-server/conf:rw \
        -v ${HOME}/XebiaLabs/xl-release-docker/repository:/opt/xl-release-server/repository:rw \
        -v ${HOME}/XebiaLabs/xl-release-docker/archive:/opt/xl-release-server/archive:rw \
        --name xlr xebialabs/xl-release:8.1

With multiple mount points, it is easier to use a Docker Compose file.

In this example, all mount points are mapped to directories in the `<USER_HOME>/XebiaLabs` folder.

        xld:
          image: xebialabs/xl-deploy:8.1
          container_name: xld
          ports:
           - "4516:4516"
          volumes:
           - ~/XebiaLabs/xl-deploy-docker/conf:/opt/xl-deploy-server/conf
           - ~/XebiaLabs/xl-deploy-docker/ext:/opt/xl-deploy-server/ext
           - ~/XebiaLabs/xl-deploy-docker/hotfix/lib:/opt/xl-deploy-server/hotfix/lib
           - ~/XebiaLabs/xl-deploy-docker/hotfix/plugins:/opt/xl-deploy-server/hotfix/plugins
           - ~/XebiaLabs/xl-deploy-docker/plugins:/opt/xl-deploy-server/plugins
           - ~/XebiaLabs/xl-deploy-docker/repository:/opt/xl-deploy-server/repository

        xlr:
          image: xebialabs/xl-release:8.1
          container_name: xlr
          ports:
           - "5516:5516"
          links:
           - xld
          volumes:
           - ~/XebiaLabs/xl-release-docker/archive/lib:/opt/xl-release-server/archive
           - ~/XebiaLabs/xl-release-docker/conf:/opt/xl-release-server/conf
           - ~/XebiaLabs/xl-release-docker/ext:/opt/xl-release-server/ext
           - ~/XebiaLabs/xl-release-docker/hotfix/:/opt/xl-release-server/hotfix/
           - ~/XebiaLabs/xl-release-docker/plugins:/opt/xl-release-server/plugins
           - ~/XebiaLabs/xl-release-docker/repository:/opt/xl-release-server/repository

Run the Docker Compose file using the `docker-compose up -d` command and inspect the contents of the folders when the servers are up and running.

**Note:** Before starting the containers, save the license file in the local `conf` directory:

           <USER_HOME>/XebiaLabs/xl-deploy-docker/conf/deployit-license.lic
           <USER_HOME>/XebiaLabs/xl-release-docker/conf/xl-release-license.lic           
