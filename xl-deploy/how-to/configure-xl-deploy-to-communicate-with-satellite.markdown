---
title: Configure XL Deploy to communicate with XL Satellite
categories:
- xl-deploy
subject:
- Satellite
tags:
- satellite
- system administration
- configuration
since:
- 5.1.0
---

## Enable satellite communication 

Satellite communication is switched off by default on XL Deploy. To enable it, find `satellite` section in configuration file `conf\system.conf`. And change `enabled` to  `yes` as follows

	satellite {
		enabled = yes
	}

Restart XL Deploy and it will be ready to connect to any instance of XL Satellite. 


## Change default settings to communicate with XL Satellite

The following setting are provided by default 

	satellite {
  		hostname = ""
		port = 8180
	}

* `hostname` must be set to an IP address or a host name XL Deploy can bind to. Note that this name must be visible from the network where XL Satellite is located. It's used by XL Satellite to reestablish connection with XL Deploy if initial connection broke. If no configuration value is provided, XL Deploy will resolve it from a network interface avalibale or to a loopback address if no interfaces are available.
* `port` where XL Deploy binds to. It has to be accessable from XL Satellite network too. 



