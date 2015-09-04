---
title: Configure XL Deploy to communicate with satellites
categories:
- xl-deploy
subject:
- Satellite
tags:
- satellite
- system administration
- configuration
since:
- XL Deploy 5.0.1
---

By default, communication with satellites is disabled in XL Deploy. To enable it, locate the `satellite` section in the `conf/system.conf` file and change `enabled` to  `yes` as follows:

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

* `hostname` must be set to an IP address or a host name that XL Deploy can bind to. XL Deploy will send this value to the satellites. It must be visible from the network(s) where the satellites are located.

    Satellites will use this value to re-establish connection with XL Deploy if the initial connection breaks.
    
    If you do not provide a value, XL Deploy will resolve it from a network interface that is available, or to a loopback address if no interfaces are available.

* `port` is the port that XL Deploy binds to. It must also be accessible from the satellites.
