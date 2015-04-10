---
layout: beta
title: Configure the satellite feature in XL Deploy
categories:
- xl-deploy
subject:
- Satellite
tags:
- plugin
- configuration
- remoting
- satellite
since:
- 5.0.x
---

## Configure the ping timeout

To configure the ping timeout while reaching a satellite, change the `deployit.satellite.timeout.ping` property in `conf/system.conf`:

    deployit {
        satellite {
          timeout {
            ping = "<timeout>"
          }
        }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

## Configure the upload idle timeout

The upload idle timeout occurs when a satellite has accepted an incoming streaming connection but does not seed to accept the uploaded file. This prevents unused TCP connections from remaining open. To configure the upload idle timeout, change the `satellite.timeout.upload.idle` property in the file `conf/satellite.conf`:

    deployit {
        satellite {
          timeout {
            upload.idle = "<timeout>"
          }
        }
    }
You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.
