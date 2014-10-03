---
title: Connecting to Windows target machines using a winrs proxy with XL Deploy
categories:
- xl-deploy
tags:
- windows
- connectivity
---

[Windows Remote Management](http://msdn.microsoft.com/en-us/library/aa384426%28v=vs.85%29.aspx), or WinRM, is Microsoft's implementation of the open [DMTF WS-Management standard](https://en.wikipedia.org/wiki/WS-Management) and is the standard way that [XL Deploy](http://xebialabs.com/products/xl-deploy) runs commands on Windows target systems. WinRM is a SOAP-based protocol, and XL Deploy can communicate directly with the target system by sending the appropriate messages over HTTP or HTTPS. This is XL Deploy's [WINRM_INTERNAL](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#cifs-connection-types-includes-winrm-and-telnet) connection mode.

XL Deploy can also communicate with the target system by calling Microsoft's own WinRM client, the command-line [`winrs`](http://technet.microsoft.com/en-us/library/hh875630.aspx) utility, and using it to send the necessary messages. This is the WINRM_NATIVE connection mode. It is often useful because it allows you to run the same commands that XL Deploy uses to communicate with the target system by invoking `winrs` yourself from the command line of the machine on which the XL Deploy server runs. This makes connection verification and troubleshooting much easier and is a nice benefit of an agentless automation tool.

Because `winrs` has some [advanced options](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#overtherecifshost) that are not yet available using WINRM_INTERNAL, we generally recommend using WINRM_NATIVE when possible.

Where's the catch? Well, `winrs` is Microsoft's own command-line WinRM client, so it's not that surprising to find that it's only available on Windows operating systems. There are two ways this can be set up with XL Deploy.

## Running `winrs` locally

The easiest way to allow XL Deploy to use WINRM_NATIVE is to simply run the XL Deploy server on a Windows operating system. This is the default setup assumed when you select WINRM_NATIVE.

![Running winrs locally]({{ site.url }}/images/connecting-with-winrs-winrm-native.png)

## Using a `winrs` proxy

But what if you can't run your XL Deploy server on a Windows machine? In that case, you can use a `winrs` proxy, which is a Windows server that has the `winrs` command-line utility installed and from which a network path exists to the "real" target system. XL Deploy will first connect to the `winrs` proxy, and then run `winrs` from there to connect to the target system. It is still easy to emulate what XL Deploy is doing: you simply need to invoke `winrs` on the `winrs` proxy machine.

![Running winrs via a proxy]({{ site.url }}/images/connecting-with-winrs-via-proxy.png)

Of course, we're not quite done yet, because you still need to connect from the XL Deploy server to the `winrs` proxy. And because your XL Deploy server is most likely not running on Windows in this scenario (otherwise you could simply use the default setup!), you will probably need to use WINRM_INTERNAL or one of the other supported protocols for that. But you only need to set up that connection for one server, which can be much quicker than setting up WINRM_INTERNAL for *all* servers in a large, multi-domain environment.

### How do I configure a `winrs` proxy?

Configuring a `winrs` proxy is easy. First, create a host entry for the `winrs` proxy [host](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#host-types), specifying the connection information XL Deploy will use to log in to that machine. Then, select that `winrs` proxy host as the [`winrsProxy`](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#overtherecifshost) on your target Windows system. The connection type for the target system needs to be WINRM_NATIVE.

![winrs proxy configuration]({{ site.url }}/images/connecting-with-winrs-via-proxy-how-to-configure.png)

### Multiple `winrs` proxies

If you have a partitioned network setup in which some Windows machines that you want to target from XL Deploy are not visible from one `winrs` proxy, you can create multiple hosts to act as `winrs` proxies. In that case, select the appropriate host when setting the `winrsProxy` value for the target machine.

![Multiple winrs proxies]({{ site.url }}/images/connecting-with-winrs-via-proxy-multiple-proxies.png)
