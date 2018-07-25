---
title: Requirements for installing XL Deploy
categories:
xl-deploy
subject:
Installation
tags:
system administration
installation
license
setup
system requirements
weight: 102
---

## Server requirements

To [install](/xl-deploy/how-to/install-xl-deploy.html) the XL Deploy server, you must meet the following requirements:

* **XL Deploy [license](/xl-deploy/concept/xl-deploy-licensing.html):**
    * If you are using a paid edition of XL Deploy, you can download your license from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com)
    * If you are using a free trial edition of XL Deploy, your license will be sent to you by email

* **Operating system:** Microsoft Windows (32-bit or 64-bit) or a Unix-family operating system running Java

* **Oracle, IBM, or Apple Java Development Kit (JDK):**
    * For XL Deploy 6.0.0 and later: JDK 1.8.0_25 or later

    **Important:** XL Deploy is not compatible with Java Development Kit 9 (JDK 9).

* **RAM:** At least 2 GB of RAM available for XL Deploy

* **Hard disk space:** Sufficient hard disk space to store the XL Deploy repository; see [Hard disk space requirements](#hard-disk-space-requirements)

Depending on the environment, the following may also be required:

* **Database:** The default XL Deploy setting is to use the internal database that stores the data on the file system. This is intended for temporary use and is not recommended for production use. For production use, it is strongly recommended to use an industrial-grade external database server such as PostgreSQL, MySQL, Oracle, Microsoft SQL Server or DB2. See [Configure the XL Deploy SQL repository (XL Deploy 8.0 and later)](/xl-deploy/how-to/configure-the-xl-deploy-sql-repository.html) for details.
If you are using XL Deploy version 7.6 or earlier, see [Configure the XL Deploy JCR repository (XL Deploy 7.6 and earlier)](/xl-deploy/how-to/configure-the-xl-deploy-repository.html) for details.

* **LDAP:** To enable group-based security, an LDAP x.509 compliant registry is needed; for more information, see [Configure the XL Deploy security file](/xl-deploy/how-to/configure-the-xl-deploy-security-file.html)

### Networking requirements

Before installing XL Deploy, ensure that the network connection to the XL Deploy host name works. You should be able to successfully execute `ping xl_deploy_hostname`.

By default, the XL Deploy server uses port `4516`. If, during installation, you choose to enable secure communication (SSL) between the server and the XL Deploy GUI, the server uses port `4517`.

To enable secure communication and/or to change the port number during installation, choose the [manual setup option](https://docs.xebialabs.com/xl-deploy/how-to/install-xl-deploy.html#manual-setup) in the command-line server setup wizard. The wizard will take you through the setup steps.

### Hard disk space requirements

The XL Deploy server itself only uses about 70 MB of disk space. The main hard disk space usage comes from the repository which stores your deployment packages and deployment history. The size of the repository will vary from installation to installation, but depends mainly on the:

* Size and storage mechanism used for artifacts
* Number of packages in the system
* Number of deployments performed (specifically, the amount of logging information stored)

While it is possible to store the repository in an [external database](/xl-deploy/how-to/configure-the-xl-deploy-repository.html), the underlying technology requires that the repository itself must not be stored on NFS as this is not supported.

XL Deploy always requires that the disk space for the server be persistent. This is important for several reasons:

* Lucene indexes are stored in the repository directory; if the disk space is not persistent, these indexes will be rebuilt each time the server starts, which is very time-consuming
* Configuration files such as `deployit.conf` and `deployit-defaults.properties` are updated by the running system
* Log files are also updated by the running system (unless configured otherwise)

#### Estimating required disk space

Follow this procedure to obtain an estimate of the total required disk space:

1. Install and configure XL Deploy for your environment as described in this document. Make sure you correctly set up the databaseor file-based repository.
1. Estimate the number of packages to be imported, either the total number or the number per unit of time (`NumPackages`)
1. Estimate the number of deployments to be performed, either the total number or the number per unit of time (`NumDeployments`)
1. Record the amount of disk space used by XL Deploy (`InitialSize`)
1. Import a few packages using the GUI or CLI
1. Record the amount of disk space used by XL Deploy (`SizeAfterImport`)
1. Perform a few deployments
1. Record the amount of disk space used by XL Deploy (`SizeAfterDeployments`)

The needed amount of disk space in total is equal to:

    Space Needed = ((SizeAfterImport InitialSize) * NumPackages) +
        ((SizeAfterDeployments SizeAfterImport) * NumDeployments)

If `NumPackages` and `NumDeployments` are expressed per time unit (that is, the number of packages to be imported per month), then the end result represents the space needed per time unit as well.

## Client requirements

### GUI clients

To use the XL Deploy GUI, you must meet the following requirements:

* **Web browser:**
	* Firefox: the 2 latest versions
	* Chrome: the 2 latest versions
	* Internet Explorer 11 or later
* **Flash player:** Version 9 or later

**Note:** Internet Explorer Compatibility View is not supported.

### CLI clients

To use the XL Deploy CLI, you must meet the following requirements:

* **Operating system:** Microsoft Windows or Unix-family operating system running Java

* **Java Runtime Environment:** The same Java Development Kit (JDK) version as your version of XL Deploy

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
