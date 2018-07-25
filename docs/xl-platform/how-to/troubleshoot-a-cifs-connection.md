---
title: Troubleshoot a CIFS connection
categories:
xl-platform
xl-deploy
xl-release
subject:
Remoting
tags:
connectivity
remoting
cifs
smb
overthere
weight: 347
---

The [remoting functionality](/xl-platform/concept/remoting-plugin.html) for XL Deploy and XL Release uses the [Overthere framework](https://github.com/xebialabs/overthere) to manipulate files and execute commands on remote hosts. CIFS, Telnet, and WinRM are supported for connectivity to Microsoft Windows hosts. These are configuration errors that can occur when using XL Deploy or XL Release with the CIFS protocol.

#### CIFS connections are very slow to set up

The [JCIFS library](http://jcifs.samba.org), which the Remoting plugin uses to connect to CIFS shares, will try to query the Windows domain controller to resolve the hostname in SMB URLs. JCIFS will send packets over port 139 (one of the [NetBIOS over TCP/IP] ports) to query the <a href="http://en.wikipedia.org/wiki/Distributed_File_System_(Microsoft)">DFS</a>. If that port is blocked by a firewall, JCIFS will only fall back to using regular hostname resolution after a timeout has occurred.

Set the following Java system property to prevent JCIFS from sending DFS query packets: `-Djcifs.smb.client.dfs.disabled=true`.

See [this article on the JCIFS mailing list](http://lists.samba.org/archive/jcifs/2009-December/009029.html) for a more detailed explanation.

#### CIFS connections time out

If the problem cannot be solved by changing the network topology, try increasing the JCIFS timeout values documented in the [JCIFS documentation](http://jcifs.samba.org/src/docs/api/overview-summary.html#scp). Another system property not mentioned there but only on the [JCIFS homepage](http://jcifs.samba.org/) is `jcifs.smb.client.connTimeout`.

To get more debug information from JCIFS, set the system property `jcifs.util.loglevel` to 3.

#### Connection fails with "A duplicate name exists on the network"

This error can occur when connecting to a host with an IP address that resolves to more than one name. For information about resolving this error, refer to [Microsoft Knowledge Base article #281308](https://support.microsoft.com/en-us/kb/281308).
