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
---

## I configured a satellite in XL Deploy and started it, but I cannot ping it

If you are using XL Deploy 5.0.1 or later, ensure that the `satellite` `enabled` setting in `conf/system.conf` is set to `yes`, as described in [Configure XL Deploy to communicate with satellites](/xl-deploy/how-to/configure-xl-deploy-to-communicate-with-satellites.html).

Also, this issue may be caused by the configuration of the satellite address. The IP address and port number that you specify when you create the [satellite configuration item (CI)](/xl-deploy/how-to/add-a-satellite-server-to-xl-deploy.html) in XL Deploy's repository must *exactly* match the values that appear in the satellite's log when it is started.

For example, in this log:

	13:32:27.802 INFO  Remoting - Remoting started; listening on addresses :[akka.tcp://XL-Satellite@192.168.1.7:8380]
	13:32:27.804 INFO  Remoting - Remoting now listens on addresses: [akka.tcp://XL-Satellite@192.168.1.7:8380]

The satellite starts listening to the address `192.168.1.7` on port `8380`. These are the values that you must use for the satellite in XL Deploy.

**Note:** If the satellite is listening to `127.0.0.1`, you must use this value when adding the satellite to XL Deploy. Using `localhost` will not work.
