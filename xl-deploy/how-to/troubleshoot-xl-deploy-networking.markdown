---
title: Troubleshooting XL Deploy networking issues
categories:
- xl-deploy
subject:
- networking
tags:
- networking
- configuration
- troubleshooting
since:
- 5.0.0
---

## Server seems to be stuck on startup

This issue may be caused by non configured networking on server.
If you make thread dump of JVM process that appears to be stuck (by using jstack tool) and see something like the following snippet in the generated file:

	locked <0x00000007eb7cde38> (a java.lang.Object)
	at ch.qos.logback.core.util.ContextUtil.getLocalHostName(ContextUtil.java:38)
	at ch.qos.logback.core.util.ContextUtil.addHostNameAsProperty(ContextUtil.java:74)
	at ch.qos.logback.classic.joran.action.ConfigurationAction.begin(ConfigurationAction.java:57)
	
then it means that logback library is stuck trying to resolve hostname.
Please make sure that you can ping your hostname and configure your networking.


## XLD seems to start but in the log file there is a `java.net.UnknownHostException`

This issue may be caused by non-configured networking on the host server.

For example, in this log:

	2015-05-04 17:50:49.248 [main] {} INFO com.xebialabs.deployit.Server - XL Deploy Server has started.
	2015-05-04 17:50:49.250 [main] {} ERROR n.j.t.e.s.LoggingEventHandlerStrategy - Could not dispatch event: com.xebialabs.deployit.engine.spi.event.SystemStartedEvent@5d2b3c32 to handler com.xebialabs.deployit.Server@7fc75485[startListener]
	java.lang.IllegalStateException: java.net.UnknownHostException: MBP-de-Benoit: MBP-de-Benoit: nodename nor servname provided, or not known
	at com.xebialabs.deployit.ServerConfiguration.getDerivedServerUrl(ServerConfiguration.java:593) ~[appserver-core-2015.2.1.jar:na]
	at com.xebialabs.deployit.ServerConfiguration.getServerUrl(ServerConfiguration.java:569) ~[appserver-core-2015.2.1.jar:na]
	at com.xebialabs.deployit.Server.startListener(Server.java:322) [server-5.0.0.jar:na]

we can see that XL Deploy server is unable to determine server url.
You can manually specify server.url property in conf/deployit.conf file.

Similar error can happen in a different place:

	Caused by: java.net.UnknownHostException: packer-freebsd-10.0-amd64: packer-freebsd-10.0-amd64: hostname nor servname provided, or not known
	at java.net.InetAddress.getLocal	Host(InetAddress.java:1473) ~[na:1.7.0_71]
	at akka.remote.transport.ne	tty.NettyTransportSettings.<init>(NettyTransport.scala:123) ~[akka-remote_2.10-2.3.5.jar:na]
	at akka.remote.transport.netty.NettyTransport.<init>(Nett	yTransport.scala:240) ~[akka-remote_2.10-2.3.5.jar:na]

Here, akka netty transport is unable to find default hostname because networking is not configured.
You can manually specify hostname property in conf/server.conf file.

In order to proceed you should configure networking on a server. Make sure you can ping host name.

