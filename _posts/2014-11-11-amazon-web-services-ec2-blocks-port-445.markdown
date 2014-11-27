---
title: Amazon Web Services blocks port 445
author: mark_ravech
categories:
- xl-deploy
tags:
- security
- ec2
- windows
---

If you connect XL Deploy to an instance running Microsoft Windows Server on [Amazon Web Services (AWS) EC2](http://aws.amazon.com/ec2/), note that AWS blocks port 445. This is the case even if you enable port 445 in your firewall and security group.

To copy files and execute them, install an SSH server such as [WinSSHD](http://www.bitvise.com/ssh-server) on the Windows Server.
