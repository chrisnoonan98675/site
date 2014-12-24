---
title: Configure satellite in XL Deploy
categories:
- xl-deploy
subject:
- Satellite
tags:
- plugin
- configuration
- remoting
- satellite
---

## Configure the host name of a satellite

To enable XL Deploy to start a satellite server and connect to it, you must add its host name to the `conf/satellite.conf` as follows:

    akka {
      remote {
        netty.tcp {
          hostname = <external ip / hostname>
        }
      }
    }

If you do not add the host name to the configuration file, XL Deploy will try to get the first host name, which typically resolves to `127.0.0.1`.

## Configure the port of a satellite

To configure the port of a satellite server, adjust the following settings in `conf/satellite.conf`:

    akka {
      remote {
        netty.tcp {
          port = <command port>
        }
      }
    }

## Configure the ping timeout on a satellite

To configure the ping timeout of a satellite server, adjust the following settings in `conf/satellite.conf`:

    satellite {
      timeout {
        ping = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

## Configure the upload timeout on a satellite

The upload timeout occurs when the XL Deploy satellite plugin cannot successfully connect to a satellite server for streaming. To configure the upload idle timeout, adjust the following settings in `conf/satellite.conf`:

    satellite {
      timeout {
        upload.idle = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.
