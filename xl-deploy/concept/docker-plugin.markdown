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

The `docker.Registry` CI registers a Docker registry with the Docker host.

The `docker.ServicePort` CI binds the Docker container port to the host port.

The `docker.ServiceSpec` CI creates a Docker service deployable.

The `docker.Port` CI creates a Docker service deployable.

The `docker.MountedVolume` CI configures a new Volume.

The `docker.ContainerSpec` CI creates a deployable for a Docker container.

The `docker.Network` CI configures a Docker network.

The `docker.NetworkSpec` CI creates a deployable for a Docker network.

The `docker.DeployedFolder` CI deploys a folder to the Docker host.

The `docker.Service` CI is similar to the docker.SwarmServiceSpec from the XL Deploy Docker community plugin.

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
| `docker.EnvironmentVariable` | A new property of type map_string_string added to docker.Container CI |
| `docker.DataFolderVolume` | docker.Folder |
| `docker.DataFileVolume` | Not present |
| `docker.ComposedContainer` | Not present |
| `sql.DockerMySqlClient` | Not present |
| `sql.DockerizedExecutedSqlScripts` | Not present |
| `docker.DeployedSwarmMachine` | docker.SwarmManager |
| `docker.DockerMachineDictionary` | Not present |
| `docker.DeployedDockerMachine` (for provisioning of Docker machine) | Not present |
| `Not present` | docker.Registry |
| `Not present` | docker.Service |
| `Not present` | docker.ServicePort |
| `Not present` | docker.ServiceSpec |
| `Not present` | docker.Port |
| `Not present` | docker.MountedVolume |
| `Not present` | docker.ContainerSpec |
| `Not present` | docker.Network |
| `Not present` | docker.NetworkSpec |
| `Not present` | docker.DeployedFolder |

Differences in behavior:

1. Differences in configuring a Docker host:

   In the community supported plugin, you can connect to a Docker host using an `overthere.Connection` and creating an instance of `docker.Machine` while in the officially supported plugin, the infrastructure items of the type `docker.Engine` and `docker.SwarmManager` are available to establish a connection with the Docker host.

2. Difference in configuring the Docker registry:

   In the community supported plugin, you can use the Registry Host and Registry Port properties of `docker.RunContainer` to integrate the Docker registry while in the officially supported plugin, you must create the docker registry configuration in the **Configuration** section and then add the registry to the Docker host configuration in the **Registries** section.

3. Docker compose is not supported in the XL Deploy official docker plugin while it is supported in the community plugin.
