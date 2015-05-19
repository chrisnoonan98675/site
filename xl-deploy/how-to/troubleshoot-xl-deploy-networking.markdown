---
title: Troubleshooting XL Deploy networking issues
categories:
- xl-deploy
subject:
- Networking
tags:
- networking
- configuration
- troubleshooting
- system administration
since:
- 5.0.0
---

## Server seems to be stuck on startup

This issue may occur if networking is not configured on the XL Deploy server.

If you use the [`jstack` tool](http://docs.oracle.com/javase/7/docs/technotes/tools/share/jstack.html) to create a thread dump of the JVM process that appears to be stuck and you see lines similar to these in the generated file:

	locked <0x00000007eb7cde38> (a java.lang.Object)
	at ch.qos.logback.core.util.ContextUtil.getLocalHostName(ContextUtil.java:38)
	at ch.qos.logback.core.util.ContextUtil.addHostNameAsProperty(ContextUtil.java:74)
	at ch.qos.logback.classic.joran.action.ConfigurationAction.begin(ConfigurationAction.java:57)
	
Then it means that `logback` library is stuck trying to resolve the host name.
Please ensure that you can ping the host name and configure networking.

## XL Deploy appears to start, but `java.net.UnknownHostException` appears in the log file 

This issue may occur if networking is not configured on the XL Deploy server.

For example, in this log:

	2015-05-04 17:50:49.248 [main] {} INFO com.xebialabs.deployit.Server - XL Deploy Server has started.
	2015-05-04 17:50:49.250 [main] {} ERROR n.j.t.e.s.LoggingEventHandlerStrategy - Could not dispatch event: com.xebialabs.deployit.engine.spi.event.SystemStartedEvent@5d2b3c32 to handler com.xebialabs.deployit.Server@7fc75485[startListener]
	java.lang.IllegalStateException: java.net.UnknownHostException: MBP-de-Benoit: MBP-de-Benoit: nodename nor servname provided, or not known
	at com.xebialabs.deployit.ServerConfiguration.getDerivedServerUrl(ServerConfiguration.java:593) ~[appserver-core-2015.2.1.jar:na]
	at com.xebialabs.deployit.ServerConfiguration.getServerUrl(ServerConfiguration.java:569) ~[appserver-core-2015.2.1.jar:na]
	at com.xebialabs.deployit.Server.startListener(Server.java:322) [server-5.0.0.jar:na]

You can see that the XL Deploy server cannot determine server URL.

You can manually specify the `server.url` property in `conf/deployit.conf`.

A similar error can occur in a different place:

	Caused by: java.net.UnknownHostException: packer-freebsd-10.0-amd64: packer-freebsd-10.0-amd64: hostname nor servname provided, or not known
	at java.net.InetAddress.getLocal	Host(InetAddress.java:1473) ~[na:1.7.0_71]
	at akka.remote.transport.ne	tty.NettyTransportSettings.<init>(NettyTransport.scala:123) ~[akka-remote_2.10-2.3.5.jar:na]
	at akka.remote.transport.netty.NettyTransport.<init>(Nett	yTransport.scala:240) ~[akka-remote_2.10-2.3.5.jar:na]

Here, Akka `NettyTransport` cannot find the default host name because networking is not configured.

You can manually specify the host name property in `conf/server.conf`.

To proceed, you should configure networking on the server. Ensure that you can ping the host name.
