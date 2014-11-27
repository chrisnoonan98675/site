---
title: Troubleshooting "permission denied" error when starting wsadmin tool
categories:
- xl-deploy
tags:
- websphere
- connectivity
---

When XL Deploy connects to an [`overthere.SshHost`](http://docs.xebialabs.com/releases/latest/xl-deploy/remotingPluginManual.html#overtheresshhost) with the connection type set to [SUDO or INTERACTIVE_SUDO](http://docs.xebialabs.com/releases/latest/xl-deploy/remotingPluginManual.html#sudo-and-interactivesudo), the following error may appear when starting up [`wsadmin.sh`](http://publib.boulder.ibm.com/infocenter/ltscnnct/v2r0/index.jsp?topic=/com.ibm.connections.25.help/t_admin_wsadmin_starting.html):

    /opt/IBM/WebSphere/AppServer/bin/setupCmdLine.sh: line 13: cd: /home/centos: Permission denied

Despite this message, the rest of the command will complete successfully. To prevent this message from appearing, add `-i` to the `sudo` command by adding the following line in `conf/deployit-defaults.properties`:

    overthere.SshHost.suduCommandPrefix=sudo -i -u {0}
