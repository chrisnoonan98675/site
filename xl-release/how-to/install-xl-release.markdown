---
title: Install XL Release
categories:
- xl-release
subject:
- Installation
tags:
- system administration
- installation
- setup
weight: 402
---

Before installing XL Release, [ensure that you meet the system requirements](/xl-release/concept/requirements-for-installing-xl-release.html). Then, choose whether to use a [GUI installer](#using-the-gui-installer) or the [command-line installer](#using-the-command-line-installer).

**Tip:** If you are upgrading a previously installed version of XL Release, refer to [Upgrade XL Release](/xl-release/how-to/upgrade-xl-release.html).

## Using a GUI installer

GUI installers are available for Microsoft Windows (EXE file) and OS X (DMG file). These will install XL Release as a service.

To use a GUI installer, double-click the file and follow the steps. After installation is complete, the installer will show the URL where you can access XL Release; for example, `http://localhost:5516`. When you open this URL in a browser, you will be prompted to enter your license key. You can then log in with the user name *admin* and the password that you provided during the setup process.

After installation is complete, you can configure XL Release startup options in the `XL_RELEASE_SERVER_HOME/bin/xl-release-server.vmoptions` file.

## Using the command-line installer

The command-line installer is available in the XL Release server ZIP file (for example, `xl-release-5.0.0-server.zip`). To install XL Release as a service, you can use this installer and then follow the steps in [Install XL Release as a service](/xl-release/how-to/install-xl-release-as-a-service.html).

### Extract the XL Release server ZIP file

First, extract the XL Release server ZIP file:

1. Log in to the computer where you want to install XL Release.
2. Create an installation directory such as `/opt/xebialabs/xl-release` or `C:\Program Files\XL Release` (referred to as `XL_RELEASE_SERVER_HOME`).
3. Copy the XL Release server ZIP file to the directory.
4. Extract the ZIP file in the directory.

**Tip:** It is recommended that you install the XL Release server as a non-root user such as `xlrelease`.

### Install the license

If you received an XL Release license key by email, you will be prompted to enter it after you install and start XL Release.

If you do not have an XL Release license key, you can download a license file from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com) (requires customer log-in). Place the license file (`xl-release-license.lic`) in the `conf` directory before you install XL Release. Be sure that you do not modify the license file in any way.

Refer to [XL Release licensing](/xl-release/concept/xl-release-licensing.html) for information about how the XL Release license works.

### Run the server setup wizard

To install the XL Release server, go to a command line prompt or terminal window and execute the appropriate command to start the server setup wizard:

{:.table .table-striped}
| Operating system | Command |
| ---------------- | ------- |
| Microsoft Windows | `run.cmd -setup` |
| Unix-based systems | `run.sh -setup` |

To stop the setup wizard at any time, enter `exitsetup`. All changes to the configuration will be discarded.

#### Select a setup method

The setup wizard shows the welcome message. You can:

* Answer `yes` (or press ENTER) to install XL Release with a default configuration. This method makes it easy for you to quickly get started with XL Release. See [Simple setup](#simple-setup) for more information.
* Answer `no` to install XL Release manually. This method gives you explicit control over all XL Release settings. See [Manual setup](#manual-setup) for more information.

#### Edit an existing configuration

If you have installed XL Release in the same location before, the setup wizard will ask whether you want to edit the existing configuration or create a new one:

* Answer `yes` (or press ENTER) to edit the existing configuration.
* Answer `no` to continue setup with an empty configuration.

#### Simple setup

If you choose the simple setup, XL Release will be installed with these settings:

* The server will run with security enabled.
* The server will not use secure communication between the XL Release graphical user interface (GUI) and the XL Release server.
* The server will listen on XL Release's standard HTTP port (5516).
* The server will use `/` as the context root.
* The server will use a minimum of 3 and a maximum of 24 threads.

##### Step 1 Provide a password for the *admin* user

The setup wizard will ask you to provide a password for the *admin* user. The *admin* user has all permissions and is used to connect to XL Release's JCR repository.

To use the default password of *admin*, press ENTER twice. If you plan to connect to an existing repository, enter the password that you already use to connect to that repository.

##### Step 2 Initialize the repository

**Note**: Only XL Release 7.2 or earlier. The setup wizard will ask if you want to initialize the repository. Answer `yes` to create the repository, or `no` to connect to an existing repository.

**Warning:** If you choose to initialize the repository and you have installed XL Release in the same location before, any information stored in the repository will be lost.

By default, the repository is stored in an embedded Derby database at `XL_RELEASE_SERVER_HOME/repository`. For information about using an external database, refer to [Configure the XL Release JCR repository in a database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html) or [Configure the XL Release SQL repository](/xl-release/how-to/configure-the-xl-release-sql-repository-in-a-database.html).

##### Step 3 Generate an encryption key

If you choose to initialize the repository, the setup wizard will ask if you want to generate an encryption key to protect passwords that you store in the repository. Answer `yes` to generate a new key.

If you answer `no`, then you agree to use XL Release's own encryption key, or to use a key that you have previously generated.

##### Step 4 Provide a password for the encryption key

If you choose to generate an encryption key, you can also provide a password to secure the key. You will be required to enter this password when XL Release starts, either:

* Interactively via a prompt
* Non-interactively via a command-line parameter
* Non-interactively via a configuration file

If you do not want to provide a password, press ENTER twice.

See [Finish the setup process and log in](#finish-the-setup-process-and-log-in) to complete the setup process.

#### Manual setup

Manual setup gives you control over all of XL Release's installation settings.

##### Step 1 Provide a password for the *admin* user

The setup wizard will ask you to provide a password for the *admin* user. The *admin* user has all permissions and is used to connect to XL Release's JCR repository.

To use the default password of *admin*, press ENTER twice. If you plan to connect to an existing repository, enter the password that you already use to connect to that repository.

##### Step 2 Configure secure communication

The setup wizard will prompt you to set up secure communication (SLL) between the XL Release graphical user interface (GUI) and the XL Release server.

##### Step 3 Generate a self-signed certificate

A digital certificate is required for secure communication; normally, certificates are signed by a Certificate Authority (CA). However, if you choose secure communication between the GUI and the server, the setup wizard will ask if you want XL Release to generate a self-signed digital certificate.

**Important:** For security reasons, a self-signed certificate is not recommended for production environments. It may trigger security warnings in some browsers and Flash players. A self-signed certificate can only be used when you access the XL Release GUI at https://localhost:4516.

##### Step 4 Use your own keystore

Instead of using a self-signed digital certificate, you can use your own keystore for secure communication between the GUI and the server. XL Release's built-in Jetty server requires a certificate with the name `jetty` to be present in the keystore.

The setup wizard will ask you for the keystore path (for example, `mykeystore.jks`), the keystore password, and the password of the `jetty` certificate in the keystore.

##### Step 5 Enable mutual SSL

The setup wizard will ask if you want to enable mutual SSL. If you answer yes, it will prompt you for the location and password to your truststore (for example, `cacerts.jks`).

##### Step 6 Set up the HTTP configuration

The setup wizard will ask the HTTP bind address, HTTP port number, and web context root where you would like XL Release to run.

This URL is used in the email notifications that the XL Release server sends.

##### Step 7 Set up the thread configuration

The setup wizard will prompt you for the minimum and maximum number of threads that the XL Release server should use to handle incoming connections.

**Note:** After setup is complete, you can see the thread configuration in the `threads.min` and `threads.max` settings in the `XL_RELEASE_SERVER_HOME/conf/xl-release-server.conf` file.

##### Step 8 Configure the repository

**Note**: Only XL Release 7.2 or earlier. The setup wizard will ask where you want to store the JCR repository. If the directory does not exist, XL Release will create it. The setup wizard will also ask if you want to initialize the repository.

**Warning:** If you choose to initialize the repository and you have installed XL Release in the same location before, any information stored in the repository will be lost.

By default, the repository is stored in an embedded Derby database at `XL_RELEASE_SERVER_HOME/repository`. For information about using an external database, refer to [Configure the XL Release JCR repository in a database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html) or [Configure the XL Release SQL repository](/xl-release/how-to/configure-the-xl-release-sql-repository-in-a-database.html).

##### Step 9 Generate an encryption key

If you choose to initialize the repository, the setup wizard will ask if you want to generate an encryption key to protect passwords that you store in the repository. Answer `yes` to generate a new key.

If you answer `no`, then you agree to use XL Release's own encryption key, or to use a key that you have previously generated.

##### Step 10 Provide a password for the encryption key

If you choose to generate an encryption key, you can also provide a password to secure the key. You will be required to enter this password when XL Release starts, either:

* Interactively via a prompt
* Non-interactively via a command-line parameter
* Non-interactively via a configuration file

If you do not want to provide a password, press ENTER twice.

See [Finish the setup process and log in](#finish-the-setup-process-and-log-in) to complete the setup process.

### Finish the setup process and log in

After you have configured all options, the setup wizard shows a summary of the configuration that you have selected. Answer `yes` to finish the setup process. Answer `no` to exit setup.

If you answer `yes`, the setup wizard will start XL Release and show the URL where you can access it; for example, `http://localhost:5516`. When you open this URL in a browser, you will be prompted to enter a license key if you did not already install a license file. You can then log in with the user name *admin* and the password that you provided during the setup process.

## XL Release server directory structure

After the XL Release installation file is extracted, the following directory structure exists in the installation directory:

* `bin`: Contains the server binaries
* `conf`: Contains server configuration files
* `ext`: Contains server Java extensions
* `hotfix`: Contains hotfixes that correct issues with the server software
* `lib`: Contains libraries that the server needs
* `log`: contains server log files

The installation directory is referred to as `XL_RELEASE_SERVER_HOME`.

## Failover configuration

If you store the XL Release repository in a [database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html), you can set up a failover configuration as described in:

* [Configure failover for XL Release](/xl-release/how-to/configure-failover.html)
* [Configure active/hot-standby mode](/xl-release/how-to/configure-active-hot-standby.html) (supported in XL Release 6.0.0 and later)
* [Configure active/active mode](/xl-release/how-to/configure-active-active.html) (supported in XL Release 7.5.0 and later)
