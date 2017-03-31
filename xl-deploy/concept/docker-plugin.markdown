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

The 'docker.Registry' CI type added in the new plugin. Is is used to register a docker registry with the docker host.

The 'docker.Service' CI is a new added CI to the standard docker plugin. It is similar to the docker.SwarmServiceSpec of community plugin.

The 'docker.ServicePort' CI is added in the standard plugin and is used to bind the docker container port to the host port.

The 'docker.ServiceSpec' CI is a new CI added to the standard docker plugin and is used to create a docker service deployable.

* The 'docker.Port' CI is a new CI added to the standard docker plugin and is used to create a docker service deployable.

The 'docker.MountedVolume' is a new CI added to the standard docker plugin and is used to configure a new Volume.

The 'dockerContainerSpec' is a new CI added to the standard docker plugin and is used to create a deployable for a docker container.

The 'docker.Network' is a new CI added to the standard plugin and is used to configure a docker network.

The 'docker.NetworkSpec' is a new CI added to the standard plugin and is used to create a deployable for a docker network.

The 'docker.DeployedFolder' is a new CI added which is a new CI added to the standard plugin to deploy a folder to the docker host.

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
| `                            ` | docker.Registry |
| `                            ` | docker.Service |
| `                            ` | docker.ServicePort |
| `                            ` | docker.ServiceSpec |
| `                            ` | docker.Port |
| `                            ` | docker.MountedVolume |
| `                            ` | docker.ContainerSpec |
| `                            ` | docker.Network |
| `                            ` | docker.NetworkSpec |
| `                            ` | docker.DeployedFolder |

Differences in behaviour (XLD standard docker plugin vs XLD community docker plugin):

1. Differences in way to configure a docker host:
   In Community plugin we connect to a docker host using an overthere.Connection and creating an instance of docker.Machine while in the standard plugin a new infrastructure item
   of type docker.Engine and docker.SwarmManager is available establish a connection with the docker connection.

2. Difference in the way to configure the docker registry:
   In the community plugin we use the properties - Registry Host and Registry Port of docker.RunContainer to integrate the docker registry
   while in the standard plugin we need to create the docker registry config in the Configuration section and then add the registry to the
   docker host configuration in the registries section.

3. Docker compose is not supported in the XLD standard docker plugin while its present in the community version.

