---
title: Configure satellite feature in XL Deploy
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

## Configure the ping timeout

To configure the ping timeout while reaching a satellite, adjust the setting `satellite.timeout.ping` in `conf/satellite.conf`:

    satellite {
      timeout {
        ping = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.

## Configure the upload idle timeout

The upload idle timeout occurs when a satellite has accepted an incoming streaming connection but does not seed to accept the upload ed file. To prevent having some useless TCP connections opened. configure the upload idle timeout, adjust the setting `satellite.timeout.upload.idle` in the file `conf/satellite.conf`:

    satellite {
      timeout {
        upload.idle = "<timeout>"
      }
    }

You can specify the ping timeout in milliseconds, seconds, or minutes. For example, `100 ms` or `10 seconds`.