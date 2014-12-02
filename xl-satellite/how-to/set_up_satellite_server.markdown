---
title: Installing and configuring an XL Satellite server
subject:
- Satellite
categories:
- xl-satellite
tags:
- remoting
---

## Installing the XL Satellite server

In order to install the XL Satellite server, you need to have JDK7 already installed. Unzip the ZIP file containing the XL Satellite server into a directory of your choosing.

After unzipping the XL Satellite, please have a look at the next sections before starting it.

## Configuring the hostname of the XL Satellite server

In order to be able to start an XL Satellite server and connect to it from the outside world, you need to add the hostname to the `conf/application.conf` file. If you do not, it will try to get the first hostname, which typically resolves to `127.0.0.1` and the XL Satellite will not be reachable from the outside. The following setting needs to be added to the XL Satellite's `conf/application.conf`

    akka {
      remote {
        netty.tcp {
          hostname = <external ip / hostname>
        }
      }
    }

Please note that when connecting to the XL Satellite, you need to specify the exact hostname or IP address you've entered here.

## Configuring the ports the XL Satellite server listens on

It is very easy to change the ports the XL Satellite server process listens on. You can change the following settings in the `conf/application.conf` file:

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

The remote program will only need to know the command port, the XL Satellite server will upon connecting to it notify the connecting process of which port is configured for streaming.

## Configuring upload timeout on XL Satellite

You can configure the upload idle timeout on XL Satellite with the following settings in the `conf/application.conf` file:

    satellite {
      timeout {
        upload.idle = "<timeout>"
      }
    }

You can fill the upload timeout with ms, seconds, minutes units (ex. 100 ms, 10 seconds). Time timeout corresponds to an idle TCP connection on the streaming server without any associated command. This will prevent some TCP connection to keep opened unnecessary connections 
on XL-Satellite.

## Configuring the streaming chunk size

You can modify the chunk size of the files which will be exchange between XL Deploy and XL Satellite.
You can change the following settings in the `conf/application.conf` file:

    satellite {
      streaming {
        chunk-size = <chunk size>
      }
    }

Note that the chunk size is in octet.

## Activating the compression

You can enable compression for streaming files between XL-Deploy and XL_Satellite.
You can change the following settings in the `conf/application.conf` file:

    satellite {
      streaming {
        compression = on // off
      }
    }

Note that you can also use values 'true/false' instead of 'on/off'.

## Configuring files location on satellite

You can change the directory where the files will be transfered on XL Satellite server.
You have to change the following settings in the `conf/application.conf` file:

    satellite {
      workdir = "<your directory>"
    }

In that directory, XL Satellite server will look for files to execute deployments.

## Launch XL Satellite server

You can launch the server with a command line script in the bin folder.
There are scripts for Unix `xl-satellite-server` and Windows systems `xl-satellite-server.cmd`
