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
- XL Deploy 5.5.0
---

The XL Deploy Docker plugin allows you to deploy Docker images to create containers and connect networks and volumes to them.

For information about requirements and the configuration items (CIs) that the Docker plugin provides, refer to the [Docker Plugin Reference](/xl-deploy-xld-docker-plugin/latest/dockerPluginManual.html).

## Features

* Deploy Docker images
* Create Docker containers
* Connect networks and volumes to Docker containers

## Using the plugin configuration items

The `docker.Container` CI creates and starts a Docker container by retrieving a specified image from Docker Hub.

The `docker.Network` CI creates a Docker network for a specified driver and connects Docker containers with networks.

The `docker.Volume` CI creates a Docker volume and connects containers to specified data volumes.

## Plugin compatibility

The XL Deploy Docker plugin is not compatible with the [XL Deploy Docker community plugin](https://github.com/xebialabs-community/xld-docker-plugin).

The community plugin is based on the [Docker command-line interface (CLI)](https://docs.docker.com/engine/reference/commandline/cli/) and uses the `docker.Machine` configuration item (CI) type to connect to Docker, while this plugin uses the `docker-py` library to connect to the Docker daemon through the `docker.Engine` CI type. This plugin does not support the following properties of the `docker.Machine` type: `dynamicParameters`, `provider`, `swarmMaster`, and `swarmPort`.

The `docker.RunContainer` type in the community plugin is similar to the `docker.Container` type in this plugin. However, this plugin does not support the following properties of the `docker.RunContainer` type: `entryPoint`, `args`, `volumesFrom`, `variables`, `extendedPrivileges`, `memory`, `pidNamespace`, `workDirectory`, `removeOnExit`, `dumpLogsAfterStartup`, `checkContainerIsRunning`, `restartAlways`, `registryHost`, and `registryPort`.

The `docker.Network` CI type is an incompatible type that exists in both plugins.

Other differences between the plugins are listed below:

{:.table table-striped}
| Community supported plugin | Officially supported plugin |
| -------------------------- | --------------------------- |
| `docker.Volume` is present as an embedded CI | `docker.Volume` is present as a type |
| `docker.Link` | links (only as a property in `docker.Container`) |
| `docker.EnvironmentVariable` | Not present |
| `docker.DataFolderVolume` | Not present |
| `docker.DataFileVolume` | Not present |
| `docker.ComposedContainer` | Not present |
| `sql.DockerMySqlClient` | Not present |
| `sql.DockerizedExecutedSqlScripts` | Not present |
| `docker.DeployedSwarmMachine` | Not present |
| `docker.DockerMachineDictionary` | Not present |
| `docker.DeployedDockerMachine` (for provisioning of Docker machine) | Not present |
