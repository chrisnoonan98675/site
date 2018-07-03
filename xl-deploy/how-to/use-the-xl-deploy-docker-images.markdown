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

## Set up and start the Docker containers

There are multiple methods to set up and start the Docker image for XL Deploy.

For information about running the XL Deploy image using Docker Compose, see [Running the XebiaLabs Docker containers with Docker Compose](/xl-platform/how-to/getting-started-with-xl-docker-containers.html).

### Start containers with quick run commands

**Important:** This setup method does not include persistence and must not be used in production environments.

**Note:** When no volumes are defined, the container will maintain the state between stopping and starting.

1. Run the XL Deploy container with the following command:   

          $ docker run -d -p 4516:4516 --name xld xebialabs/xl-deploy:v8.1

XL Deploy will run on http://localhost:4516. You can log in with `username`: `admin` and `password`: `admin`.

You must provide a valid license before you can log in. Browse to the above URL and paste the license to the product. If you do not have a license yet, apply for an [XL Deploy trial license](https://xebialabs.com/products/xl-deploy/trial/) on the XebiaLabs web site.

### Secure container setup using a generated password

You can make the setup more secure and start with a generated admin password instead of the default password. Including the XL Deploy container in an automated test or use case that depends on using a known admin password is not recommended.

If the environment variable `ADMIN_PASSWORD` is not set and you are starting up for the first time, the container will generate a random password and print it in the logs.

To trigger this behavior, remove the `ADMIN_PASSWORD` from the Docker run command:

    $ docker run -d -p 5516:5516 --name xld xebialabs/xl-deploy:8.1

The generated password is printed when the container starts up and can be found at the top of the log file. You can view the logs using the following command:

    $ docker logs xld

Search for the following line:

    ... Generating admin password: [PASSWORD IS PRINTED HERE]

### Stopping and removing containers

To stop the containers, use the following commands:

    $ docker stop xld

The state of the application is saved in the containers. They can be started again using this command:

    $ docker start xld

You can also remove the containers completely:

    $ docker rm xld

**Note:** This will result in a blank slate. When you run the container again with the simple run command, the database will be empty, you will receive a new admin password, and the license must be entered again.

### XL Deploy setup for production using persistent volumes

**Note:** In production setups, volumes must be defined and XL Deploy must be configured to store its state in an external database.

The previous two set-ups do not persist the configuration, repository, and archive data across container runs.

For more details about the XL Deploy image, persistence configuration, and examples, see [Docker images for XL Deploy](/xl-deploy/how-to/docker-images-for-xl-deploy.html).

To ensure persistence when reconfiguring, volumes must be mounted at the `conf`, `repository`, and `archive` mount points:

        $ docker run -d -p 4516:4516 \
        -v ${HOME}/xl-deploy-server/conf:/opt/xl-deploy-server/conf:rw \
        -v ${HOME}/xl-deploy-server/repository:/opt/xl-deploy-server/repository:rw \
        --name xld xebialabs/xl-deploy:v8.1.0-rc.2
