---
title: Use the XL Release Docker images
categories:
- xl-release
subject:
- Docker
tags:
- docker
- images
---

The Docker image for XL Release has a similar behavior to the existing installation ZIP of XL Release. This ensures that the existing setup, upgrade, and operations procedures remain valid.  

The directory `/opt/xl-release-server` contains an extracted installation setup, augmented with a number of directories that are described in [Docker images for XL Release](/xl-release/concept/docker-images-for-xl-release.html).

To download a docker image for XL-Release, go to [XL Release Docker images](https://github.com/xebialabs/xl-release-docker-image).

## Set up and start the Docker containers

There are multiple methods to set up and start the Docker image for XL Release.

For information about running the XL Release image using Docker Compose, see [Running the XebiaLabs Docker containers with Docker Compose](/xl-platform/how-to/getting-started-with-xl-docker-containers.html).

### Start containers with quick run commands

**Important:** This setup method does not include persistence and must not be used in production environments.

**Note:** When no volumes are defined, the container will maintain the state between stopping and starting.

1. Run the XL Release container with the following command:   

          $ docker run -d -p 5516:5516 --name xlr xebialabs/xl-release:v8.1.0-rc.2

XL Release  will run on http://localhost:5516. You can log in with `username`: `admin` and `password`: `admin`.

You must provide a valid license before you can log in. Browse to the above URL and paste the license to the product. If you do not have a license yet, apply for an [XL Release trial license](https://xebialabs.com/products/xl-release/trial/ ).

### Secure container setup using a generated password

You can make the setup more secure and start with a generated admin password instead of the default password. Including the XL Release container in an automated test or use case that depends on using a known admin password is not recommended.

If the environment variable `ADMIN_PASSWORD` is not set and you are starting up for the first time, the container will generate a random password and print it in the logs.

To trigger this behavior, remove the `ADMIN_PASSWORD` from the Docker run command:

      $ docker run -d -p 5516:5516 -e ADMIN_PASSWORD#secret --name xlr xebialabs/xl-release:v8.1.0-rc.2

The generated password is printed when the container starts up and can be found at the top of the log file. You can view the logs using the following command:

    $ docker logs xlr

Search for the following line:

    ... Generating admin password: [PASSWORD IS PRINTED HERE]

### Stopping and removing containers

To stop the containers, use the following commands:

    $ docker stop xlr

The state of the application is saved in the containers. They can be started again using this command:

    $ docker start xlr

You can also remove the containers completely:

    $ docker rm xlr

**Note:** This will result in a blank slate. When you run the container again with the simple run command, the database will be empty, you will receive a new admin password, and the license must be entered again.

### XL Release setup for production using persistent volumes

**Note:** In production setups, volumes must be defined and XL Release must be configured to store its state in an external database.

The previous two set-ups do not persist the configuration, repository, and archive data across container runs.

For more details about the XL Release image, persistence configuration, and examples, see [Docker images for XL Release](/xl-release/how-to/docker-images-for-xl-release.html).

To ensure persistence when reconfiguring, volumes must be mounted at the `conf`, `repository`, and `archive` mount points:

        $ docker run -d -p 5516:5516 \
        -v ${HOME}/xl-release-server/conf:/opt/xl-release-server/conf:rw \
        -v ${HOME}/xl-release-server/repository:/opt/xl-release-server/repository:rw \
        -v ${HOME}/xl-release-server/archive:/opt/xl-release-server/archive:rw \
        --name xlr xebialabs/xl-release:v8.1.0-rc.2
