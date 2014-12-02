---
title: Installing and configuring an XL Satellite plugin on XL Deploy
subject:
- Satellite
categories:
- xl-satellite
tags:
---

## Configuring the hostname of the XL Satellite plugin

In order to be able to start an XL Satellite server and connect to it from the outside world, you need to add the hostname to the `conf/satellite.conf` file. If you do not, it will try to get the first hostname, which typically resolves to `127.0.0.1` and the XL Satellite will not be reachable from the outside. The following setting needs to be added to `conf/satellite.conf`

    akka {
      remote {
        netty.tcp {
          hostname = <external ip / hostname>
        }
      }
    }

## Configuring the port of the XL Satellite plugin

It is very easy to change the ports of XL Satellite plugin. You can change the following settings in the `conf/satellite.conf` file:

    akka {
      remote {
        netty.tcp {
          port = <command port>
        }
      }
    }

## Configuring ping timeout on XL Satellite

You can configure the ping timeout on XL Satellite with the following settings in the `conf/satellite.conf` file:

    satellite {
      timeout {
        ping = "<timeout>"
      }
    }

You can fill the ping timeout with ms, seconds, minutes units (ex. 100 ms, 10 seconds)

## Configuring upload timeout on XL Satellite

You can configure the upload idle timeout with the following settings in the `conf/satellite.conf` file:

    satellite {
      timeout {
        upload.idle = "<timeout>"
      }
    }

You can fill the upload timeout with ms, seconds, minutes units (ex. 100 ms, 10 seconds). This timeout occurs when
XL-Satellite plugin didn't succeed in having a connection for streaming to XL-Satellite server.