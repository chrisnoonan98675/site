---
title: Requirements for installing XL Deploy
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- installation
- license
- setup
- requirements
- system requirements
- prerequisites
---

## Server requirements

To [install](/xl-deploy/how-to/install-xl-deploy.html) the XL Deploy server, you must meet the following requirements:

* **XL Deploy license:** If you are using a paid edition of XL Deploy, you can download your [license](/xl-deploy/concept/xl-deploy-licensing.html) from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com)

* **Operating system:** Microsoft Windows (32-bit or 64-bit) or a Unix-family operating system running Java

* **Oracle, IBM, or Apple Java Development Kit (JDK):**
    * For XL Deploy 5.1.0 and earlier: JDK 7
    * For XL Deploy 5.1.1 and later: JDK 7 or 8

* **RAM:** At least 2 GB of RAM available for XL Deploy

* **Hard disk space:** Sufficient hard disk space to store the XL Deploy repository; see [Determining hard disk space requirements](#determining-hard-disk-space-requirements)

Depending on the environment, the following may also be required: 

* **Database:** XL Deploy's Jackrabbit repository supports a number of different databases; for more information, see [Configure the XL Deploy repository](/xl-deploy/how-to/configure-the-xl-deploy-repository.html)

* **LDAP:** To enable group-based security, an LDAP x.509 compliant registry is needed; for more information, see [Configure the XL Deploy security file](/xl-deploy/how-to/configure-the-xl-deploy-security-file.html)

### Networking requirements

Before installing XL Deploy, ensure that the network connection to the XL Deploy host name works. You should be able to successfully execute `ping xl_deploy_hostname`.

By default, the XL Deploy server uses port `4516`. If, during installation, you choose to enable secure communication (SSL) between the server and the XL Deploy GUI, the server uses port `4517`.

To enable secure communication and/or to change the port number during installation, choose the [manual setup option](https://docs.xebialabs.com/xl-deploy/how-to/install-xl-deploy.html#manual-setup) in the command-line server setup wizard. The wizard will take you through the setup steps.

### Determining hard disk space requirements

The XL Deploy server itself only uses about 70 MB of disk space. The main hard disk space usage comes from the repository which stores your deployment packages and deployment history. The size of the repository will vary from installation to installation but depends mainly on the:

* Size and storage mechanism used for artifacts
* Number of packages in the system
* Number of deployments performed (specifically, the amount of logging information stored)

Follow this procedure to obtain an estimate of the total required disk space:

1. Install and configure XL Deploy for your environment as described in this document. Make sure you correctly set up the database- or file-based repository.
1. Estimate the number of packages to be imported, either the total number or the number per unit of time (`NumPackages`)
1. Estimate the number of deployments to be performed, either the total number or the number per unit of time (`NumDeployments`)
1. Record the amount of disk space used by XL Deploy (`InitialSize`)
1. Import a few packages using the GUI or CLI
1. Record the amount of disk space used by XL Deploy (`SizeAfterImport`)
1. Perform a few deployments
1. Record the amount of disk space used by XL Deploy (`SizeAfterDeployments`)

The needed amount of disk space in total is equal to:

    Space Needed = ((SizeAfterImport - InitialSize) * NumPackages) +
        ((SizeAfterDeployments - SizeAfterImport) * NumDeployments)

If `NumPackages` and `NumDeployments` are expressed per time unit (that is, the number of packages to be imported per month), then the end result represents the space needed per time unit as well.

## Client requirements

### GUI clients

To use the XL Deploy GUI, you must meet the following requirements:

* **Web browser:**
	* Firefox
	* Chrome
	* Safari
	* For XL Deploy 5.0.0 or later: Internet Explorer 10 or later
	* For XL Deploy 4.5.x: Internet Explorer 8, 9, or later (Internet Explorer 10 or later if using the [Compare feature](/xl-deploy/how-to/compare-configuration-items.html))
* **Flash player:** Version 9 or later

**Note:** Internet Explorer Compatibility View is not supported.

### CLI clients

To use the XL Deploy CLI, you must meet the following requirements:

* **Operating system:** Microsoft Windows or Unix-family operating system running Java

* **Java Runtime Environment:** Java Development Kit (JDK) 7 (Oracle, IBM, or Apple)

## Middleware server requirements

### Unix middleware server requirements

Unix-based middleware servers that XL Deploy interacts with must meet the following requirements:

* **Credentials:** XL Deploy should be able to log in to the target systems using a user name and password combination that allows it to perform at least the following Unix commands: `cp`, `ls`, `mv`, `rm`, `mkdir`, and `rmdir`. If the login user cannot perform these actions, XL Deploy can also use a `sudo` user that can execute these commands.

* **SSH access:** The target systems should be accessible by SSH from the XL Deploy server; that is, they should run an SSH2 server. It is also possible to handle key-based authorization. Note that:
    * The SSH daemon on AIX is known to hang with certain types of SSH traffic.
    * For security, the SSH account that is used to access a host should have limited rights.
    * A variety of Linux distributions have made SSH require a TTY by default. This setting is incompatible with XL Deploy and is controlled by the `Defaults requiretty` setting in the `sudoers` file.

### Windows middleware server requirements

Windows-based middleware servers that XL Deploy interacts with must meet the following requirements:

* **File system access:** The target file system should be accessible via CIFS from the XL Deploy server

* **Host access:** The target host should be accessible from the XL Deploy server via WinRM or Windows Telnet server running in _stream mode_

* **Directory shares:** The account used to access a target system should have access to the host's administrative shares such as `C$`

* **Ports:**
    * For CIFS connectivity, port 445 on the target system should be accessible from the XL Deploy server
    * For Telnet connectivity, port 23 should be accessible from the XL Deploy server
    * For WinRM connectivity, port 5985 (HTTP) or port 5986 (HTTPS) should be accessible from the XL Deploy server

### Extending middleware support

It is possible to connect XL Deploy to middleware servers that do not support SSH, Telnet, or WinRM. This requires you to use the [Overthere](https://github.com/xebialabs/overthere) remote execution framework to create a custom _access method_ that connects to the server.
