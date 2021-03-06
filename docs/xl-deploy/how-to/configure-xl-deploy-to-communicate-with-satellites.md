---
title: Configure XL Deploy to communicate with satellites
categories:
xl-deploy
subject:
Satellite
tags:
satellite
remoting
system administration
configuration
since:
XL Deploy 5.0.0
weight: 301
---

## Enable communicate with satellites

In XL Deploy 5.0.1 and later, communication with satellites is disabled by default. To enable it, locate the `satellite` section in the `XL_DEPLOY_SERVER_HOME/conf/system.conf` file and change `enabled` to  `yes` as follows:

	satellite {
        enabled = yes
    }

Restart XL Deploy and it will be ready to connect to satellites.

## Configure hostname and port

By default, `XL_DEPLOY_SERVER_HOME/conf/system.conf` has the following settings:

	satellite {
       hostname = ""
			 bind-hostname = "0.0.0.0"
       port = 8180
    }

Set `hostname` to an IP address or a host name to which XL Deploy can bind. XL Deploy will send this value to the satellites. It must be visible from the network(s) where the satellites are located.

Satellites will use this value to re-establish connection with XL Deploy if the initial connection breaks. If you do not provide a value, XL Deploy will resolve it from a network interface that is available, or to a loopback address if no interfaces are available.

Set `bind-hostname` to an IP address or a host name to listen on. The default setting listens on all network interfaces. 

`port` is the port that XL Deploy binds to. It must also be accessible from the satellites. By default, it is set to `8180`.

**Note:** You cannot connect multiple XL Deploy server instances to the same satellite. XL Deploy allows you to assign the same satellite to multiple hosts from the same XL Deploy instance.

## Configure the ping timeout

To configure the ping timeout while reaching a satellite, change the `satellite.timeout.ping` property in `XL_DEPLOY_SERVER_HOME/conf/system.conf`:

    satellite {
      timeout {
        ping = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

## Configure the upload idle timeout

The upload idle timeout occurs when a satellite has accepted an incoming streaming connection but does not need to accept the uploaded file. This prevents unused TCP connections from remaining open. To configure the upload idle timeout, change the `satellite.timeout.upload.idle` property in `XL_DEPLOY_SERVER_HOME/conf/system.conf`:

    satellite {
      timeout {
        upload.idle = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

## Configure the file streaming timeout

In XL Deploy 5.1.2 and later, you can configure the file streaming timeout. This timeout occurs when XL Deploy attempts to connect to satellites for file transfer. To configure this value, change the `satellite.timeout.streaming` property in `XL_DEPLOY_SERVER_HOME/conf/system.conf`:

    satellite {
      timeout {
        streaming = "<timeout>"
      }
    }

The default timeout is 10 seconds.

## Configure the maximum upload threads per task

In XL Deploy 5.1.2 and later, you can configure the maximum threads per upload task. To configure this value, change the `satellite.streaming.max-uploads` property in `XL_DEPLOY_SERVER_HOME/conf/system.conf`:

    satellite {
        streaming {
            max-uploads = 5
        }
    }

## Configure secure communication

XL Deploy communicates with satellite servers over a secure communication channel using [TLS/SSL technology](http://en.wikipedia.org/wiki/Transport_Layer_Security) to encrypt data. For information about configuring SSL, refer to [Configure secure communication between XL Deploy and satellites](/xl-deploy/how-to/configure-secure-communication-with-a-satellite.html).
