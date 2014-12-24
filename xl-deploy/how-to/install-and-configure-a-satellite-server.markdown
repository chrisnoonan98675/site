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

## Install the satellite server

Before you install the XL Deploy satellite software, Java SE Development Kit 7 (JDK 7) must be installed.

To install the satellite software, extract the ZIP file in the desired installation location.

## Configure the server host name

To enable XL Deploy to start a satellite server and connect to it, you must add the satellite's host name to the `conf/satellite.conf` as follows:

    akka {
      remote {
        netty.tcp {
          hostname = <external ip / hostname>
        }
      }
    }

If you do not add the host name to the configuration file, XL Deploy will try to get the first host name, which typically resolves to `127.0.0.1`.

When connecting to the satellite, you must specify the exact host name or IP address specified here.

## Configure the server ports

To configure the ports where the satellite server process listens, modify the following settings in `conf/application.conf`:

    akka {
      remote {
        netty.tcp {
          port = <command port>
        }
      }
    }

    satellite {
      streaming {
        port = <streaming port>
      }
    }

The remote program only requires the command port. Upon connecting to it, the satellite will notify the connecting process of the port that is configured for streaming.

## Configure the upload timeout

You can configure a file upload idle timeout on a satellite server. The timeout corresponds to an idle TCP connection on the streaming server without an associated command. This will prevent some TCP connections from keeping necessary connections open on the satellite.

To configure the file upload idle timeout on a satellite server, modify the following settings in `conf/application.conf`:

    satellite {
      timeout {
        upload.idle = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

## Configure the streaming chunk size

You can modify the chunk size of the files that will be exchanged between XL Deploy and a satellite server. To enable this feature, modify the following settings in `conf/application.conf`:

    satellite {
      streaming {
        chunk-size = <chunk size>
      }
    }

**Note:** The chunk size is in octets.

## Enable file compression

You can enable compression for files that are streamed between XL Deploy and a satellite server. To enable this feature, modify the following settings in `conf/application.conf` file:

    satellite {
      streaming {
        compression = on // off
      }
    }

**Tip:** You can use the values `true` and `false` instead of `on` and `off`.

## Enable streaming bandwidth limitation

You can limit the bandwidth used by the streaming connection between XL Deploy and a satellite server. The limit is intended for each connection to a satellite server. To enable this feature, modify the following settings in `conf/application.conf`:

    satellite{
      streaming {
        throttle = on // off
        throttle-speed = 10000 // given in kB/sec. Must be at least 100kB/sec
      }
    }

## Configure file location

To change the directory where files are transferred on the satellite server, modify the following settings in `conf/application.conf`:

    satellite {
      workdir = "<your directory>"
    }

In that directory, XL Satellite server will look for files to execute deployments.

## Launch satellite server

To launch the satellite server, execute the appropriate script in the `bin` directory:

* Unix: `xl-satellite-server`
* Windows: `xl-satellite-server.cmd`
