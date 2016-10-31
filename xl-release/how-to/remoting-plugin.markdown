---
title: Using the XL Release Remoting plugin
categories:
- xl-release
subject:
- Remoting plugin
tags:
- plugin
- connectivity
- remoting
- ssh
since: XL Release 5.0.0
---

The XL Release Remoting plugin allows you to configure Unix and Windows hosts in XL Release, so that other plugins can execute commands on and transfer files to those hosts. For example, the XL Release [Ansible](/xl-release/how-to/ansible-plugin.html), [Kubernetes](/xl-release/how-to/kubernetes-plugin.html), and [Docker Compose](/xl-release/how-to/docker-compose-plugin.html) plugins all leverage the Remoting plugin.

## Features

* Supports SSH for connectivity to Unix and Microsoft Windows hosts
* Supports CIFS, Telnet, and WinRM for connectivity to Windows hosts
* Allows [SSH jumpstations](/xl-release/how-to/configure-a-jumpstation.html) to be used to access hosts to which a direct network connection is not possible

## Set up a host

To set up a host in XL Release:

1. Go to **Settings** > **Configuration** and click the **Add** link under **Unix Host** or **Windows Host**.
1. In the **Title** box, enter a name for the host.
1. In the **Address** and **Port** boxes, enter the IP address and port of the host.
1. In the **Username** and **Password** boxes, enter the server log-in user ID and password.
1. In the **Private Key File** and **Passphrase** boxes, enter the private key and passphrase to use for authentication (supported for SSH hosts only).
1. In the **SUDO Username** box, enter the user name to use for SUDO operations.
1. In the **SU Username** and **SU Password** boxes, enter the user name and password to use for SU operations.
1. From the **Connection Type** list, select the type of connection to create.
1. Click **Save** to save the host.
