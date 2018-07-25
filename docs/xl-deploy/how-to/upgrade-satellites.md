---
title: Upgrade satellite servers
categories:
xl-deploy
subject:
Satellite
tags:
system administration
upgrade
installation
since:
XL Deploy 5.0.0
weight: 314
---

When you [upgrade](/xl-deploy/how-to/upgrade-xl-deploy.html) to a new version of XL Deploy, you must also upgrade your satellite servers to the corresponding version of the satellite distribution ZIP.

## Before you upgrade

Before you upgrade:

* Carefully read the [XL Deploy release manual](/xl-deploy/latest/releasemanual.html) and note any changes that may apply to your situation.
* Review any files that you have customized, such as `conf/satellite.conf` or `conf/logback.xml`. You will need to redo these changes in the new files.

## Upgrade the satellite

To upgrade a satellite server, [extract the satellite distribution ZIP file](/xl-deploy/how-to/install-and-configure-a-satellite-server.html) in the location on the server where you want to install the software. It is recommended that you install the software in a new directory and preserve the old installation directory in case you need to roll back.
