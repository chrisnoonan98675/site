---
title: Configure XL Deploy to communicate with satellites
categories:
- xl-deploy
subject:
- Satellite
tags:
- satellite
- remoting
- system administration
- configuration
since:
- XL Deploy 5.0.0
---

## Enable communicate with satellites

In XL Deploy 5.0.1 and later, communication with satellites is disabled by default. To enable it, locate the `satellite` section in the `conf/system.conf` file and change `enabled` to  `yes` as follows:

	satellite {
        enabled = yes
    }

Restart XL Deploy and it will be ready to connect to satellites.

## Change default settings to communicate with satellites

By default, `conf/system.conf` has the following settings: 

	satellite {
	    hostname = ""
       port = 8180
    }

`hostname` must be set to an IP address or a host name that XL Deploy can bind to. XL Deploy will send this value to the satellites. It must be visible from the network(s) where the satellites are located. Satellites will use this value to re-establish connection with XL Deploy if the initial connection breaks. If you do not provide a value, XL Deploy will resolve it from a network interface that is available, or to a loopback address if no interfaces are available.

`port` is the port that XL Deploy binds to. It must also be accessible from the satellites. By default, it is set to `8180`.

## Configure the ping timeout

To configure the ping timeout while reaching a satellite, change the `satellite.timeout.ping` property in `conf/system.conf`:

    satellite {
      timeout {
        ping = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

## Configure the upload idle timeout

The upload idle timeout occurs when a satellite has accepted an incoming streaming connection but does not need to accept the uploaded file. This prevents unused TCP connections from remaining open. To configure the upload idle timeout, change the `satellite.timeout.upload.idle` property in the file `conf/system.conf`:

    satellite {
      timeout {
        upload.idle = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

## Configure the maximum upload threads per task

In XL Deploy 5.1.2 and later it's possible to configure the maximum threads per upload task. To change it, specify locate `satellite.streaming.max-uploads` in `conf/system.conf` and modify:

    satellite {
        streaming {
            max-uploads = 5
        }
    }