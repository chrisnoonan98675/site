---
title: Introduction to the XL Deploy NetScaler plugin
categories:
- xl-deploy
subject:
- NetScaler
tags:
- netscaler
- plugin
- load balancer
---

The Citrix NetScaler Application Delivery Controller plugin allows XL Deploy to manage deployments to application and web servers whose traffic is managed by a NetScaler load-balancing device.

For information about plugin dependencies and the configuration items (CIs) that the plugin provides, refer to the [NetScaler Plugin Reference](/xl-deploy-netscaler-plugin/latest/netscalerPluginManual.html).

## Features

* Take servers or services out of the load balancing pool before deployment
* Put servers or services back into the load balancing pool after deployment is complete

## Functionality

The plugin supports two modes of working:

1. Service group-based
2. Server/Service-based

Furthermore the plugin works in conjunction with the "group-based" orchestrator to disable and enable containers which are part of a single deployment group in one go.

The group-based orchestrator will divvy up the deployment into multiple phases, based on the 'deploymentGroup' property of the containers that are being targeted. Each of these group will be disabled in the NetScaler just before they're deployed to, and will be re-enabled right after the deployment to that group. This will ensure that there is no downtime during the deployment.

### Service group-based

The plugin will add the following 4 properties to every deployable and deployed to control which service in which service group this deployed affects.

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| `netscalerServiceGroup` | STRING | The name of the service group that the service running on the targeted container is registered under. (default: {% raw %}`{{NETSCALER_SERVICE_GROUP}}`{% endraw %}) |
| `netscalerServiceGroupName` | STRING | The name of the service in the service group. (default: {% raw %}`{{NETSCALER_SERVICE_GROUP_NAME}}`{% endraw %}) |
| `netscalerServiceGroupPort` | STRING | The port the service in the service group runs on. Note: This is a string on the deployable to support placeholder replacement. (default: {% raw %}`{{NETSCALER_SERVICE_GROUP_PORT}}`{% endraw %}) |

### Server/Service-based

The plugin will add the following properties to every container to control how the server is known in the NetScaler ADC, and how long it should take to do a graceful disable of the server:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| `netscalerAddress` | STRING | The IP address or name this server is registered under in the NetScaler load balancer |
| `netscalerType` | STRING | Whether this is a 'server' or a 'service' in the NetScaler load balancer (default: server) |
| `netscalerShutdownDelay` | INTEGER | The amount of seconds before the server is disabled in the NetScaler load balancer. A value of -1 triggers use of the defaultShutdownDelay of the NetScaler device. (default: -1) |

### Behavior

The plugin will add three steps to the deployment of each deployment group.

1. A disable server step, this will stop the traffic to the servers that are managed by the load balancer.
2. A wait step, this will wait the maximum shutdown delay
3. An enable server step, this will start the traffic to the servers that were previously disabled.

## Setting up a load-balancing configuration

In order to setup XL Deploy to use your NetScaler ADC device, you will need to take the following steps.

1. Create a NetScaler (`netscaler.NetScaler`) configuration item in the 'Infrastructure' tree under a host, and add it as a `member` to the `udm.Environment`. The host configuration item indicates how to connect to the NetScaler device.
2. Add all the containers that the NetScaler device manages to the `managedServers` collection of the created NetScaler configuration item.

### Service group-based

For the service group based setup, you can create dictionaries restricted to containers in the environment. Each of these dictionaries will need to contain the following keys:

- `NETSCALER_SERVICE_GROUP`
- `NETSCALER_SERVICE_GROUP_NAME`
- `NETSCALER_SERVICE_GROUP_PORT`

As a second option, you could immediately do an initial deployment and set the values correctly on all the deployeds. During an upgrade deployment these values will be copied from the previous deployment.

### Server/Service-based

Configure the `netscalerAddress` property of each of the containers so that the NetScaler configuration item knows how the container is known within the NetScaler ADC device.

Once that is done, during any deployment to the environment, the NetScaler plugin will ensure that the load-balancing logic is implemented.

### Load-balancing a mixed application server and web server environment

If you have an Apache `httpd` server which fronts a website backed by one or more application servers, it is possible to setup a more complex loadbalancing scenario, thus ensuring that the served website is not broken during the deployment. For this, the `www.ApacheHttpdServer` configuration item from the standard web server plugin is augmented with a property called `applicationServers`.

Whenever a deployment is done to one or more of the containers mentioned in the `applicationServers` residing in the same Environment as the web server, the following happens in addition to the standard behavior:

1. Just before the first application server will be deployed to, the web server will be removed from the load-balancing configuration.
2. After the last application server linked to the web server has been deployed to, the web server is put back into the load-balancing configuration

## Customization

By default, the disable and enable server scripts are called:

- `netscaler/disable-server.cli.ftl`
- `netscaler/enable-server.cli.ftl`

They contain the NetScaler CLI commands to influence the load balancing. They are FreeMarker templates which have access to the following variables during resolution:

- `servers`: A list of `NetScalerItem` (`ServiceGroup` or `ServerOrService`) that are to be enabled/disabled.
- `loadBalancer`: The `netscaler.NetScaler` load balancer that manages the servers.
