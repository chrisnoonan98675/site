---
title: Use the XL Deploy Docker images
categories:
- xl-deploy
subject:
- Docker
tags:
- docker
- images
---

The Docker image for XL Deploy has a similar behavior to the existing installation ZIP of XL Deploy. This ensures that the existing setup, upgrade, and operations procedures remain valid.  

The directory `/opt/xl-deploy-server` contains an extracted installation setup, augmented with a number of directories that are described in [Docker images for XL Deploy](/xl-deploy/concept/docker-images-for-xl-deploy.html).

To download a docker image for XL Deploy, go to [XL Deploy Docker images](https://github.com/xebialabs/xl-deploy-docker-image).

For a quick use guide of the XL Deploy image, see [Getting started with the XebiaLabs Docker containers](/xl-platform/how-to/getting-started-with-xl-docker-containers.html).

## Running the container

The following section describes the methods that are available for running a container.

### XL Deploy setup for demonstrations and simple trials

**Important:** This setup method does not include persistence and must not be used in production environments.

**Note:** When no volumes are defined, the container will maintain the state between stopping and starting.

1. Run the XL Deploy container with the following command:   

          $ docker run -d -p 5516:5516 --name xld xebialabs/xl-deploy:v8.1.0-rc.2

1. Run the logs command:

          $ docker logs -f xld

1. Search the logs to capture the generated admin password. Find the "XL Deploy has started" message the logs.

To stop the XL Deploy container:

          $ docker stop xld

To start the XL Deploy container:

          $ docker start xld

### XL Deploy setup for automated testing

By default, XL Deploy uses a randomly generated admin password. Including the XL Deploy container in an automated test or use case that depends on using a known admin password is not recommended.

To create a predefined `ADMIN_PASSWORD` environment variable, enter the following command:

          $ docker run -d -p 5516:5516 -e ADMIN_PASSWORD#secret --name xld xebialabs/xl-deploy:v8.1.0-rc.2

### XL Deploy setup for production usage - includes persistence

**Note:** In production setups, volumes must be defined and XL Deploy must be configured to store its state in an external database.

The previous two set-ups do not persist the configuration, repository, and archive data across container runs.

For more details about the XL Deploy image, persistence configuration, and examples, see [Docker images for XL Deploy](/xl-deploy/how-to/docker-images-for-xl-deploy.html).

To ensure persistence when reconfiguring, volumes must be mounted at the `conf`, `repository`, and `archive` mount points:

        $ docker run -d -p 5516:5516 \
        -v ${HOME}/xl-deploy-server/conf:/opt/xl-deploy-server/conf:rw \
        -v ${HOME}/xl-deploy-server/repository:/opt/xl-deploy-server/repository:rw \
        -v ${HOME}/xl-deploy-server/archive:/opt/xl-deploy-server/archive:rw \
        --name xld xebialabs/xl-deploy:v8.1.0-rc.2

## Building and publishing the images

### Debian-based image

To build the regular Debian slim-based image:

        $ docker build --build-arg xld_VERSION=8.1.0 --tag xebialabs/xl-deploy:8.1 --tag xebialabs/xl-deploy:8.1-debian-slim --tag xebialabs/xl-deploy:8.1.0 --tag xebialabs/xl-deploy:8.1.0-debian-slim -f debian-slim/Dockerfile

To publish the regular Debian slim-based image:

        $ docker push xebialabs/xl-deploy:8.1
        $ docker push xebialabs/xl-deploy:8.1-debian-slim
        $ docker push xebialabs/xl-deploy:8.1.0
        $ docker push xebialabs/xl-deploy:8.1.0-debian-slim

#### Non-final versions

To build non-final versions, use:

        $ docker build --build-arg xld_VERSION=8.1.0-rc.2 --tag xebialabs/xl-deploy:8.1.0-rc.2 --tag xebialabs/xl-deploy:8.1.0-rc.2-debian-slim -f debian-slim/Dockerfile

To publish non-final versions, use:

        $ docker push xebialabs/xl-deploy:8.1.0-rc.2
        $ docker push xebialabs/xl-deploy:8.1.0-rc.2-debian-slim

### Alpine-based image

To build the Alpine-based image:

        $ docker build --build-arg XLD_VERSION=8.1.0 --tag xebialabs/xl-deploy:8.1-alpine --tag xebialabs/xl-deploy:8.1.0-alpine -f alpine/Dockerfile

To publish the Alpine-based image:

        $ docker push xebialabs/xl-deploy:8.1-alpine
        $ docker push xebialabs/xl-deploy:8.1.0-alpine

#### Non-final versions

To build non-final versions, use:

        $ docker build --build-arg XLD_VERSION=8.1.0-rc.2 --tag xebialabs/xl-deploy:8.1.0-rc.2-alpine -f alpine/Dockerfile

To publish non-final versions, use:

$ docker push xebialabs/xl-deploy:8.1.0-rc.2-alpine

### Red Hat certified image

To build the Red Hat certified image:

        $ docker build --build-arg XLD_VERSION=8.0.1 --tag xebialabs/xl-deploy:8.0-rhel --tag xebialabs/xl-deploy:8.0.1-rhel -f rhel/Dockerfile buildContext
