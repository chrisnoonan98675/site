---
title: Install XL Deploy
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- installation
---

Before you install XL Deploy, ensure that you meet the [prerequisites](/xl-deploy/concept/requirements-for-installing-xl-deploy.html).

**Tip:** If you want to install XL Deploy as a daemon or service, refer to [Install XL Deploy as a service](install-xl-deploy-as-a-service.html). If you are upgrading a previously installed version of XL Deploy, refer to [Upgrade XL Deploy](upgrade-xl-deploy.html).

## Extract the XL Deploy server archive

First, extract the XL Deploy server archive:

1. Log in to the computer where you want to install XL Deploy.
2. Create an installation directory such as `/opt/xebialabs/xl-deploy` or `C:\Program Files\XL Deploy`.
3. Copy the XL Deploy server archive to the directory.
3. Extract the archive in the directory.

**Tip:** It is recommended to install the XL Deploy server as a non-root user such as `xldeploy`.

## Install the license

To install the XL Deploy license:

1. Download license file (`deployit-license.lic`) from [https://dist.xebialabs.com/licenses/download/](https://dist.xebialabs.com/licenses/download/).
1. Copy the downloaded file to the `conf` directory. Be sure that you do not modify the license file in any way.

Refer to [Licensing in XL Deploy](/xl-deploy/concept/licensing-in-xl-deploy.html) for information about how the XL Deploy license works.

## Run the server setup wizard

To install the XL Deploy server, go to a command line or terminal window and execute the appropriate command to start the setup wizard:

{:.table .table-striped}
| Operating system | XL Deploy version | Command |
| ---------------- | ----------------- | ------- |
| Microsoft Windows | XL Deploy 4.5.x and earlier | `server.cmd -setup` |
| Microsoft Windows | XL Deploy 5.0.0 and later <span class="label label-danger">beta</span> | `run.cmd -setup` |
| Unix-based systems | XL Deploy 4.5.x and earlier | `server.sh -setup` |
| Unix-based systems | XL Deploy 5.0.0 and later <span class="label label-danger">beta</span> | `run.sh -setup ` |

**Tip:** To stop the setup wizard at any time, enter `exitsetup`. All changes to the configuration will be discarded.

The setup wizard shows the welcome message:

* Answer `yes` (or press ENTER) to use the simple setup. This makes it easy to quickly get started with XL Deploy and to use the default configuration. See [Simple setup](#simple-setup) for more information.
* Answer `no` to use the manual setup. This provides explicit control over all XL Deploy settings. See [Manual setup](#manual-setup) for more information.

**Note**: If you installed XL Deploy in the same location before, the setup wizard will ask you whether you want to edit the existing configuration or create a new one. Answer `yes` (or press ENTER) to edit the existing configuration. The setup wizard will load all settings from the existing configuration and allow you to choose simple or manual setup. Answer `no` to start over with an empty configuration.

### Simple setup

If you choose the simple setup, XL Deploy will be installed with these settings:

* The server will run with security enabled.
* The server will not use secure communication between the XL Deploy graphical user interface (GUI) and the XL Deploy server.
* The server will listen on XL Deploy's standard HTTP port (4516).
* The server will use `/` as the context root.
* The server will use a minimum of 3 and a maximum of 24 threads.
* Applications can be imported from the `importablePackages` directory.

#### Provide a password for the `admin` user

The setup wizard will promote you to provide a password for the `admin` user. The `admin` user has all permissions and is used to connect to XL Deploy's JCR repository.

To use the default password of `admin`, press ENTER twice. If you plan to connect to an existing repository, enter the password that you already use to connect to that repository.

#### Initialize the repository

The setup wizard will ask if you want to initialize the repository. Answer `yes` to create the repository, or `no` to connect to an existing repository.

**Warning**: If you choose to initialize the repository and you have installed XL Deploy in the same location before, any information stored in the repository will be lost.

#### Generate an encryption key

If you choose to initialise the repository, the setup wizard will ask if you want to generate an encryption key to protect passwords that you store in the repository. Answer `yes` to generate a new key.

If you answer `no`, then you agree to use XL Deploy's own encryption key, or to use a key that you have previously generated.

#### Provide a password for the encryption key

If you choose to generate an encryption key, you can also provide a password to secure the key. You will be required to enter this password when XL Deploy starts, either:

* Interactively via a prompt
* Non-interactively via a command-line parameter
* Non-interactively via a configuration file

If you do not want to provide a password, press ENTER twice.

See [Finishing the setup process](#finishing-the-setup-process) to complete the setup process.

### Manual setup

Manual setup gives you control over all of XL Deploy's installation settings. 

#### Provide a password for the `admin` user

The setup wizard will promote you to provide a password for the `admin` user. The `admin` user has all permissions and is used to connect to XL Deploy's JCR repository.

To use the default password of `admin`, press ENTER twice. If you plan to connect to an existing repository, enter the password that you already use to connect to that repository.

#### Configure secure communication

The setup wizard will prompt you to set up secure communication (SSL) between the XL Deploy graphical user interface (GUI) and the XL Deploy server.

#### Generate a self-signed certificate

A digital certificate is required for secure communication; normally, certificates are signed by a Certificate Authority (CA). However, if you choose secure communication between the GUI and the server, the setup wizard will ask if you want XL Deploy to generate a self-signed digital certificate. 

**Important:** For security reasons, a self-signed certificate is not recommended for production environments. It may trigger security warnings in some browsers and Flash players. A self-signed certificate can only be used when you access the XL Deploy GUI at `https://localhost:4516`.

#### Use your own keystore

Instead of using a self-signed digital certificate, you can use your own keystore for secure communication between the GUI and the server. XL Deploy's built-in Jetty server requires a certificate with the name `jetty` to be present in the keystore.

The setup wizard will ask you for the keystore path (for example, `mykeystore.jks`), the keystore password, and the password of the `jetty` certificate in the keystore.

#### Enable mutual SSL

The setup wizard will ask if you want to enable mutual SSL. If you answer yes, it will prompt you for the location and password to your truststore.

#### Set up the HTTP configuration

The setup wizard will ask the HTTP bind address, HTTP port number, and web context root where you would like XL Deploy to run. 

**Note**: If you chose to enable secure communication, the default port will be 4517 instead of 4516.

#### Set up the thread configuration

The setup wizard will prompt you for the minimum and maximum number of threads that the XL Deploy server should use to handle incoming connections. 

#### Configure the repository

The setup wizard will ask where you want to store the JCR repository. If the directory does not exist, XL Deploy will create it. The setup wizard will also ask if you want to initialize the repository.

**Warning**: If you choose to initialize the repository and you have installed XL Deploy in the same location before, any information stored in the repository will be lost.

#### Generate an encryption key

If you choose to initialise the repository, the setup wizard will ask if you want to generate an encryption key to protect passwords that you store in the repository. Answer `yes` to generate a new key.

If you answer `no`, then you agree to use XL Deploy's own encryption key, or to use a key that you have previously generated.

#### Provide a password for the encryption key

If you choose to generate an encryption key, you can also provide a password to secure the key. You will be required to enter this password when XL Deploy starts, either:

* Interactively via a prompt
* Non-interactively via a command-line parameter
* Non-interactively via a configuration file

If you do not want to provide a password, press ENTER twice.

See [Finishing the setup process](#finishing-the-setup-process) to complete the setup process.

#### Configure the location for importable packages

By default, you can import deployment packages from the `importablePackages` directory. If you would like to change this, enter a path when the setup wizard prompts you. If the directory does not exist, XL Deploy will create it.

## Finishing the setup process

After you have configured all options, the setup wizard shows a summary of the configuration that you have selected. Answer `yes` to finish the setup process. Answer `no` to exit setup.

## Start XL Deploy

To start the XL Deploy server, execute the appropriate command:

{:.table .table-striped}
| Operating system | XL Deploy version | Command |
| ---------------- | ----------------- | ------- |
| Microsoft Windows | XL Deploy 4.5.x and earlier | `server.cmd` |
| Microsoft Windows | XL Deploy 5.0.0 and later <span class="label label-danger">beta</span> | `run.cmd` |
| Unix-based systems | XL Deploy 4.5.x and earlier | `server.sh` |
| Unix-based systems | XL Deploy 5.0.0 and later <span class="label label-danger">beta</span> | `run.sh` |

## High availability setup

XL Deploy can be configured to ensure maximum uptime of the application. In such a high availability setup, two instances of XL Deploy are running in an active/passive configuration. At any one time, only one XL Deploy instance is active but as soon as a failure is detected, the passive XL Deploy instance is activated and the failed instance is taken down for repair.

To configure XL Deploy for high availability, the XL Deploy repository must be used in clustering mode. This means that each XL Deploy node writes changes to a shared journal, in addition to applying the change to its own repository. See [Configure the XL Deploy repository](/xl-deploy/how-to/configure-the-xl-deploy-repository.html) for more information on setting up clustering.
