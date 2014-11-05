---
title: Connecting XL Deploy to Amazon EC2 through a proxy
categories:
- xl-deploy
- xl-scale
tags:
- ec2
---

When using Deployit 3.9.x or earlier with the [Cloud Pack plugin](http://docs.xebialabs.com/releases/3.9/cloud-plugin/cloudPluginManual.html) or XL Deploy 4.0.x or later with [XL Scale](http://docs.xebialabs.com/product-version.html#/xl%20scale/4.0.x), you can configure XL Deploy to use a proxy server for [Amazon EC2](http://aws.amazon.com/ec2/). 

## Deployit 3.9.x or earlier

When using Deployit 3.9.x or earlier with the Cloud Pack plugin, edit the `server.sh` or `server.bat` file for Linux and Windows respectively (located under `<DEPLOYIT_ROOT>/bin`) and insert the following command-line parameters:

    -Dhttps.proxyHost=myproxyhost.local -Dhttps.proxyPort=8888

For example:

    $JAVACMD $DEPLOYIT_SERVER_OPTS $DEPLOYIT_SERVER_LOG_OPTS -Dhttps.proxyHost=myproxyhost.local -Dhttps.proxyPort=8888 -classpath "${DEPLOYIT_SERVER_CLASSPATH}" com.xebialabs.deployit.DeployitBootstrapper "$@"

Proper configuration can be verified by successfully validating EC2 credentials and instantiating new EC2 instances.

## XL Deploy 4.0.x or later

When using XL Deploy 4.0.x or later with XL Scale, edit the `server.sh` or `server.bat` file for Linux and Windows respectively (located under `<XLD_ROOT>/bin`) and insert the following command-line parameters:

    -Djclouds.proxy-host=myproxyhost.local -Djclouds.proxy-port=8888

For example:

    $JAVACMD ${JAVA_FLAG} $DEPLOYIT_SERVER_OPTS $DEPLOYIT_SERVER_LOG_OPTS -Djclouds.proxy-host=myproxyhost.local -Djclouds.proxy-port=8888 -classpath "${DEPLOYIT_SERVER_CLASSPATH}" com.xebialabs.deployit.DeployitBootstrapper "$@"

Again, proper configuration can be verified by successfully validating EC2 credentials and instantiating new EC2 instances.
