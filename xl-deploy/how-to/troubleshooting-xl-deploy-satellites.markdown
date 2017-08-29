---
title: Troubleshooting XL Deploy satellites
categories:
- xl-deploy
subject:
- Satellite
tags:
- remoting
- satellite
- configuration
- troubleshooting
since:
- XL Deploy 5.0.0
weight: 313
---

## I configured a satellite in XL Deploy and started it, but I cannot ping it

If you are using XL Deploy 5.0.1 or later, ensure that the `satellite` `enabled` setting in `conf/system.conf` is set to `yes`, as described in [Configure XL Deploy to communicate with satellites](/xl-deploy/how-to/configure-xl-deploy-to-communicate-with-satellites.html).

Also, this issue may be caused by the configuration of the satellite address. The IP address and port number that you specify when you create the [satellite configuration item (CI)](/xl-deploy/how-to/add-a-satellite-server-to-xl-deploy.html) in XL Deploy's repository must *exactly* match the values that appear in the satellite's log when it is started.

For example, in this log:

	13:32:27.802 INFO  Remoting - Remoting started; listening on addresses :[akka.tcp://XL-Satellite@192.168.1.7:8380]
	13:32:27.804 INFO  Remoting - Remoting now listens on addresses: [akka.tcp://XL-Satellite@192.168.1.7:8380]

The satellite starts listening to the address `192.168.1.7` on port `8380`. These are the values that you must use for the satellite in XL Deploy.

**Note:** If the satellite is listening to `127.0.0.1`, you must use this value when adding the satellite to XL Deploy. Using `localhost` will not work.

## Deploying to a satellite returns an `OversizedPayloadException`

When deploying an application to a satellite, if you see an error such as:

    akka.remote.OversizedPayloadException: Discarding oversized payload sent to Actor[akka.tcp://XL-Satellite@mycompany.local:8380/]: max allowed size 128000 bytes, actual size of encoded class akka.actor.ActorSelectionMessage was 162392 bytes.

Add the `akka.remote.netty.tcp.maximum-frame-size` property to the `XL_DEPLOY_SERVER_HOME/conf/system.conf` and `XL_DEPLOY_SATELLITE_HOME/conf/satellite.conf` files. It is recommended that you start by setting this property to `150000`; if you continue to encounter the error, increase the setting by increments of `50000` until the error does not occur. Ensure that the property's value is the same in the configuration files on both XL Deploy and the satellite.

## XL Satellite does not require a restart after communication failure

As of XL Deploy version 7.2.0, the `satellite.conf` file is divided into two sections: for configuring the `akka` communication system and for configuring the `akka` task system. If a communication failure occurs between the satellite and the XL Deploy server, the `akka` task system continues to function and only the `akka` communication system will require a restart.

If you added configuration options in `satellite.conf` under the `akka` key and you upgrade to XL Deploy version 7.2.0, the satellite will not start. The satellite log will show an error and you will have to move the configuration options to `satellite.communication.akka` or `satellite.tasks.akka`.
