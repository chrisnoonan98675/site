---
title: Running the XebiaLabs Docker containers with Docker Compose
categories:
- xl-platform
- xl-deploy
- xl-release
subject:
- Docker
tags:
- docker
- containers
- docker-compose
since:
- XL Release 8.1.0
---

With the release of the XebiaLabs DevOps Platform 8.1.0, Docker images for XL Release and XL Deploy are officially supported. The Docker images are published to the Docker Hub for each release, starting with version 8.1.0.

The images can be found here:

* [XL Release](https://hub.docker.com/r/xebialabs/xl-release/)
* [XL Deploy](https://hub.docker.com/r/xebialabs/xl-deploy/)

## Run the containers using the `docker-compose.yaml` file

To start both XL Deploy and XL Release with a persistent setup:

1. Download the Docker Compose file using the following command:

        $ curl https://docs.xebialabs.com/xl-platform/docker-compose.yaml > docker-compose.yaml

1. Open the `docker-compose.yaml` file and change the ADMIN_PASSWORD for both servers to a secure password.
1. Run the file with Docker Compose:

        $ docker-compose up -d

1. Access XL Release at http://localhost:5516 and XL Deploy at http://localhost:4516.

You can now remove the passwords from the `docker-compose.yaml` file.

You must provide a valid license before you can log in. Browse to the above URLs and paste the licenses for the appropriate product. If you do not have a license yet, apply for an [XL Release trial license](https://xebialabs.com/products/xl-release/trial/ ) or an [XL Deploy trial license](https://xebialabs.com/products/xl-deploy/trial/) on the XebiaLabs web site.

## Setup XL Release and XL Deploy using Docker Compose

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

**Note:** You can run XL Deploy or XL Release containers individually by modifying the `docker-compose.yaml` file to contain only the code specific for each product.
