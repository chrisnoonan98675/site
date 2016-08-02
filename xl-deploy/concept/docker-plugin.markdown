---
title: Introduction to the XL Deploy Docker plugin
categories:
- xl-deploy
subject:
- Docker
tags:
- docker
- plugin
since:
- XL Deploy 5.0.0
---

The XL Deploy Docker plugin allows you to deploy Docker images to create containers and connect networks and volumes to them.

For information about the configuration items (CIs) that the Docker plugin provides, refer to the [Docker Plugin Reference](/xl-deploy-xld-docker-plugin/latest/dockerPluginManual.html).

## Features

* Deploy Docker images
* Create Docker containers
* Connect networks and volumes to Docker containers

## Using the plugin configuration items

The `docker.Container` CI creates and starts a Docker container by retrieving a specified image from Docker Hub.

The `docker.Network` CI creates a Docker network for a specified driver and connects Docker containers with networks.

The `docker.Volume` CI creates a Docker volume and connects containers to specified data volumes.
