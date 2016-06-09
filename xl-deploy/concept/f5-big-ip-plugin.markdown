---
title: Introduction to the XL Deploy F5 BIG-IP plugin
categories:
- xl-deploy
subject:
- F5 BIG-IP
tags:
- f5
- big-ip
- middleware
- plugin
- load balancer
---

The F5 BIG-IP plugin adds the ability to manage deployments to application servers and web servers with traffic that is managed by a BIG-IP load balancing device.

For information about plugin dependencies and the configuration items (CIs) that the plugin provides, refer to the [F5 BIG-IP Plugin Reference](/xl-deploy/latest/bigipPluginManual.html).

## Features

* Take servers or services out of the load balancing pool before deployment
* Put servers or services back into the load balancing pool after deployment is complete

## Installation

Download the plugin distribution ZIP file from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com). Place the plugin JAR file and all dependent plugin files in your `SERVER_HOME/plugins` directory.

Install Python 2.7.x and the additional [pycontrol](https://pypi.python.org/pypi/pycontrol) and [suds](https://pypi.python.org/pypi/suds) libraries on the host that has access to the BIG-IP load balancer device.

## Using the plugin

The plugin works in conjunction with the group based orchestrator to disable and enable containers that are part of a single deployment group at once.

The group based orchestrator divides the deployment into multiple phases, based on the `deploymentGroup` property of the containers being targeted. Each group will be disabled in BIG-IP just before they are deployed to, and will be re-enabled right after the deployment to that group. This ensures that there is no downtime during the deployment.

The plugin add the following properties to every container to control how the server is known in the BIG-IP load balancer and whether it should take part in the load balancing deployment:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| `bigIpAddress` | STRING | The address this server is registered under in the BIG-IP load balancer |
| `bigIpPool` | STRING | The BIG-IP load balancer pool this server is a member of |
| `bigIpPort` | INTEGER | The port of the service of this server that is load balanced by the BIG-IP load balancer |
| `disableInLoadBalancer` | BOOLEAN = true | Whether this server should be disabled in the load balancer when it is being deployed to |

The plugin will add two steps to the deployment of each deployment group:

1. A disable server step that will stop traffic to the servers that are managed by the load balancer.
2. An enable server step that will start traffic to the servers that were previously disabled.

Traffic management to the server is done by enabling and disabling the referenced BIG-IP pool member in the BIG-IP load balancing pool.

## Set up a load balancing configuration

To set up XL Deploy to use your BIG-IP load balancing device:

1. In the XL Deploy Repository, create a BIG-IP Local Traffic Manager (`big-ip.LocalTrafficManager`) configuration item in the Infrastructure tree under a host. Add it as a member of the environment (`udm.Environment`). The host configuration item indicates how to connect to the BIG-IP device.
2. Add all of the containers that the BIG-IP device manages to the `managedServers` collection of the BIG-IP LocalTrafficManager configuration item.
3. Populate the BIG-IP address, user name, password, and partition connection properties, as seen from the host machine.
4. Update all managed containers with the appropriate deployment group and BIG-IP member data and add them to the same environment as the BIG-IP LocalTrafficManager CI.

## Load-balance a mixed application server and web server environment

If you have an Apache `httpd` server that fronts a website backed by one or more application servers, it is possible to setup a more complex load balancing scenario, thus ensuring that the served website is not broken during the deployment. For this, the `www.ApacheHttpdServer` configuration item from the bundled [Web Server plugin](/xl-deploy/concept/webserver-plugin.html) is augmented with a property called `applicationServers`.

Whenever a deployment is done to one or more of the containers mentioned in the `applicationServers` residing in the same environment as the web server, the following happens in addition to the standard behavior:

1. Just before the first application server is deployed to, the web server is removed from the load balancing configuration.
2. After the last application server linked to the web server has been deployed to, the web server is put back into the load balancing configuration.

## Load-balance servers with custom orchestrators

If you use `*-by-deployment-*` orchestrators, you might also want to use the `sequential-by-loadbalancer-group` orchestrator. This orchestrator splits the execution plan into a sequence of three sub-plans:

1. Disable affected servers in load balancers
2. Do the deployment
3. Enable affected servers in load balancers

You can combine this orchestrator with other orchestrations to accomplish the desired deployment scenarios. For an extended example, refer to [Deploy an application using the `sequential-by-loadbalancer-group` orchestrator](/xl-deploy/how-to/deploy-using-sequential-by-loadbalancer-orchestrator.html).
