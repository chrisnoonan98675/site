---
title: Install and configure a satellite server
categories:
- xl-deploy
subject:
- Satellite
tags:
- remoting
- satellite
- configuration
---

## Install it

Before you install the XL Deploy satellite software, Java SE Development Kit 7 (JDK 7) must be installed.

To install the satellite software, extract the ZIP file of the Xebialabs distribution in the desired installation location.

## Run it

To launch the satellite server, execute the appropriate script in the `bin` directory:

* Unix: `xl-satellite-server`
* Windows: `xl-satellite-server.cmd`

## Configure it

**NOTE**: any change in the configuration requires a restart of the satellite process. There is no live-reloading targeted yet. However, there is not impact on XL-Deploy side.

A satellite communicates with XL-Deploy throught TCP connections. The satellite side is considered as the server side. It will open two ports and wait for xl-deploy to connect. 

### Configuration of the command handling channel

A blank value is this property will make satellite to bind to the first hostname resolved. You can override this property in this configuration `akka.remote.netty.tcp.hostname` in the file `conf/application.conf`.

    akka {
      remote {
        netty.tcp {
          hostname = <external ip / hostname>
        }
      }
    }

You can then configure the port opened throught the property `akka.remote.netty.tcp.port` with is by default `8380`. 

**NOTE**: This is the value that needs to be present in the Satellite CI in XL-Deploy.

### Configuration of the file streaming

In addition to the command handling, a satellite needs a port to act as a streaming server for files needed by the deployment and incoming from XL-Deploy.

You can modify the following settings `satellite.streaming.port` in `conf/application.conf`:

      satellite {
        streaming {
          port = <streaming port>
        }
      }

**NOTE**: This port is automatically exchanged between XL-Deploy and XL-Satellite. There is no need to configure this manually.

### Configure the upload timeout

You can configure a file upload idle timeout on a satellite server. The timeout corresponds to an idle TCP connection on the streaming server without an associated command. This will prevent from keeping unnecessary connections opened on the satellite.

To configure the file upload idle timeout on a satellite server, modify the following settings `satellite.timeout.upload.idle` in `conf/application.conf`:

    satellite {
      timeout {
        upload.idle = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

### Configure the streaming chunk size

To ensure a good file transfer with saturating the network, files are uploaded by chunk. You can tune this value to adapt it to the topology of your infrastructure. Just set the size of chunk in bytes on the property `satellite.streaming.chunk-size` in `conf/application.conf`:

    satellite {
      streaming {
        chunk-size = <chunk size>
      }
    }

### Enable streaming bandwidth limitation

To ensure a good file transfer with saturating the network, you might need to limit the bandwidth used by the streaming connection between XL Deploy and a satellite. The limit is intended per connection. To enable this feature, modify the following settings in `conf/application.conf`:

    satellite{
      streaming {
        throttle = on // off
        throttle-speed = 10000 // given in kB/sec. Must be at least 100kB/sec
      }
    }

### Configure file location

To change the directory where files are transferred on the satellite server, modify the following settings `satellite.workdir`in `conf/application.conf`:

    satellite {
      workdir = "<your directory>"
    }

In that directory, XL Satellite will store by task all the files meant to be deployed on hosts.