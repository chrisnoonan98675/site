---
title: Troubleshooting with satellites
categories:
- xl-deploy
subject:
- Satellite
tags:
- remoting
- satellite
- configuration
---

## I've started a satellite, configured it in XL Deploy, but the ping does not work. 
One possible cause is the configuration of the address of the satellite. The configuration you set in the Configuration Item of the satellite in XL Deploy must **exactly** match what is printed in the log of the satellite at start time.

	13:32:27.802 INFO  Remoting - Remoting started; listening on addresses :[akka.tcp://XL-Satellite@192.168.1.7:8380]
	13:32:27.804 INFO  Remoting - Remoting now listens on addresses: [akka.tcp://XL-Satellite@192.168.1.7:8380]

In this example, the satellite starts listening to the address `192.168.1.7` on port `8380`. You must exactly put this value in XL Deploy. For example, if satellite is listening to `127.0.0.1`, don't try the address `localhost` in XL Deploy, that will **not** work.