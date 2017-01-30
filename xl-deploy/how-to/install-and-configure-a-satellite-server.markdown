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
- system administration
- installation
since:
- XL Deploy 5.0.0
weight: 304
---

## Requirements for a satellite server

An [XL Deploy satellite server](/xl-deploy/concept/getting-started-with-the-satellite-module.html) must meet the following requirements:

* **Operating system:** Microsoft Windows (32-bit or 64-bit) or a Unix-family operating system running Java

* **Java Runtime Environment:**
    * For XL Deploy 5.1.0 and earlier: Java Development Kit (JDK) 7 (Oracle, IBM, or Apple)
    * For XL Deploy 5.1.1: Java Development Kit (JDK) 7 or 8 (Oracle, IBM, or Apple)

* **RAM:** At least 2 GB of RAM available for the XL Deploy satellite module

* **Hard disk space:** Sufficient hard disk space to store the artifacts that will be deployed to this specific satellite server

* **Version:** XL Deploy and the satellite server must run the same version

## Install the satellite

To install the XL Deploy satellite software:

1. Ensure that the satellite server meets the requirements.
2. Extract the satellite distribution ZIP file in the location on the server where you want to install the software.

## Configure the satellite

Satellite servers communicate with XL Deploy through TCP connections. The satellite side of the connection is considered to be the server side. The satellite must open two ports and wait for XL Deploy to connect. One port is required for command handling, and the other is required for file (artifact) upload. You may need to configure satellite communication ports and ensure that firewalls are opened for outgoing traffic from XL Deploy to satellites.

**Note:** If you change the satellite configuration, you must restart the satellite process. You do not have to restart XL Deploy.

### Configure the command handling channel

The `satellite.hostname` property in the `SATELLITE_HOME/conf/satellite.conf` file defines the command handling channel. If it is blank, the satellite will bind to the first host name that is resolved.

You can override the property as follows:

    satellite {
        hostname = <external IP / hostname>
    }

You can then configure the port that is opened in the `satellite.port` property. By default, it is `8380`.

**Important:** The `satellite.hostname` and `satellite.port` values must *exactly* match the values that you set when you [add the satellite configuration item (CI) to XL Deploy](/xl-deploy/how-to/add-a-satellite-server-to-xl-deploy.html).

### Configure the file streaming port

In addition to command handling, a satellite needs a port to act as a streaming server for incoming files that are needed for a deployment. This port is automatically exchanged between XL Deploy and the satellite. You do not need to configure it manually. By default, port `8480` is opened on the satellite for file streaming.

The port is defined by the `satellite.streaming.port` property in `SATELLITE_HOME/conf/satellite.conf`:

      satellite {
        streaming {
          port = <streaming port>
        }
      }

### Configure the upload timeout

You can configure a file upload idle timeout on a satellite server. The timeout corresponds to an idle TCP connection on the streaming server without an associated command. This will prevent unnecessary connections from being kept open on the satellite.

To configure the file upload idle timeout on a satellite server, change `satellite.timeout.upload.idle` in `SATELLITE_HOME/conf/satellite.conf`:

    satellite {
      timeout {
        upload.idle = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

### Configure the streaming chunk size

To ensure efficient file transfer without saturating the network, files are uploaded by chunk. You can adjust the chunk size to adapt it to the topology of your infrastructure. Set the chunk size (in bytes) with `satellite.streaming.chunk-size` in `SATELLITE_HOME/conf/satellite.conf`:

    satellite {
      streaming {
        chunk-size = <chunk size>
      }
    }

### Enable a streaming bandwidth limitation

To ensure efficient file transfer without saturating the network, you might need to limit the bandwidth that the streaming connection between XL Deploy and a satellite uses. The limit is intended per connection.

To enable this feature, change the following properties in `SATELLITE_HOME/conf/satellite.conf`:

    satellite{
      streaming {
        throttle = on // off
        throttle-speed = 10000 // given in kB/sec. Must be at least 100kB/sec
      }
    }

### Configure file transfer location

To change the directory where XL Deploy transfers files to the satellite server, change the `satellite.directory.work`property in `SATELLITE_HOME/conf/satellite.conf`:

    satellite {
        directory {
            work = "<your directory>"
        }
    }

Files are stored in this directory by task.

### Configure task recovery location

To change the directory where XL Satellite stores task recovery files, change the `satellite.directory.recovery`property in `SATELLITE_HOME/conf/satellite.conf`:

    satellite {
        directory {
            recovery = "<your directory>"
        }
    }

## Configure the satellite to use Kerberos

If you are going to use Microsoft Windows domain accounts to access remote hosts with the WINRM_INTERNAL connection type, you must configure Kerberos on the satellite. For information about this, refer to [Set up Kerberos for WINRM_INTERNAL](/xl-deploy/how-to/set-up-kerberos-for-a-winrm-connection.html#configure-kerberos-for-the-xl-deploy-satellite-module).

## Start the satellite

To start the satellite software, execute the appropriate script in the `bin` directory of the installation:

* Unix: `run.sh`
* Microsoft Windows: `run.cmd`
