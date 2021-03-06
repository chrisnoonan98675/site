---
title: Install XL Deploy
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- installation
- license
- setup
weight: 103
---

To install XL Deploy:

1. [Ensure that you meet the system requirements.](/xl-deploy/concept/requirements-for-installing-xl-deploy.html)
1. Download the XL Deploy server archive (ZIP file) from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com) (requires customer log-in) or from the link provided when you sign up for a [free trial](https://xebialabs.com/products/xl-deploy/trial/).
1. [Extract the XL Deploy server archive.](#extract-the-xl-deploy-server-archive)
1. [Install the license.](#install-the-license)
1. [Run the server setup wizard](#run-the-server-setup-wizard) and select whether to perform a [simple setup](#simple-setup) or [manual setup](#manual-setup).
1. [Finish the setup process and log in to XL Deploy.](#finish-the-setup-process-and-log-in)
1. [Optionally install the XL Deploy command-line interface (CLI).](#install-the-xl-deploy-cli)

**Tip:** For information about installing XL Deploy as a daemon or service, refer to [Install XL Deploy as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html). If you are upgrading a previously installed version of XL Deploy, refer to [Upgrade XL Deploy](/xl-deploy/how-to/upgrade-xl-deploy.html).

## Extract the XL Deploy server archive

First, extract the XL Deploy server archive:

1. Log in to the computer where you want to install XL Deploy.
2. Create an installation directory such as `/opt/xebialabs/xl-deploy` or `C:\Program Files\XL Deploy`.
3. Copy the XL Deploy server archive to the directory.
3. Extract the archive in the directory.

**Tip:** It is recommended to install the XL Deploy server as a non-root user such as `xldeploy`.

## Install the license

If you received an XL Deploy license key by email, you will be prompted to enter it after you install and start XL Deploy.

If you do not have an XL Deploy license key, you can download a license file (`deployit-license.lic`) from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com) (requires customer log-in). Place the license file in the `conf` directory before you install XL Deploy. Be sure that you do not modify the license file in any way.

Refer to [XL Deploy licensing](/xl-deploy/concept/xl-deploy-licensing.html) for information about how the XL Deploy license works.

## Run the server setup wizard

To install the XL Deploy server, go to a command line or terminal window and execute the appropriate command to start the setup wizard:

{:.table .table-striped}
| Operating system | XL Deploy version | Command |
| ---------------- | ----------------- | ------- |
| Microsoft Windows | XL Deploy 4.5.x or earlier | `server.cmd -setup` |
| Microsoft Windows | XL Deploy 5.0.0 or later | `run.cmd -setup` |
| Unix-based systems | XL Deploy 4.5.x or earlier | `server.sh -setup` |
| Unix-based systems | XL Deploy 5.0.0 or later | `run.sh -setup ` |

To stop the setup wizard at any time, enter `exitsetup`. All changes to the configuration will be discarded.

### Select a setup method

The setup wizard shows the welcome message. You can:

* Answer `yes` (or press ENTER) to install XL Deploy with a default configuration. This method makes it easy for you to quickly get started with XL Deploy. See [Simple setup](#simple-setup) for more information.
* Answer `no` to install XL Deploy manually. This method gives you explicit control over all XL Deploy settings. See [Manual setup](#manual-setup) for more information.

**Note**: If you have installed XL Deploy in the same location before, the setup wizard will ask you whether you want to edit the existing configuration or create a new one. Answer `yes` (or press ENTER) to edit the existing configuration. The setup wizard will load all settings from the existing configuration and allow you to choose simple or manual setup. Answer `no` to start over with an empty configuration.

### Simple setup

If you choose the simple setup, XL Deploy will be installed with these settings:

* The server will run with security enabled.
* The server will not use secure communication between the XL Deploy graphical user interface (GUI) and the XL Deploy server.
* The server will listen on XL Deploy's standard HTTP port (4516).
* The server will use `/` as the context root.
* The server will use a minimum of 3 and a maximum of 24 threads.
* Applications can be imported from the `importablePackages` directory.

#### Step 1 Provide a password for the `admin` user

The setup wizard will promote you to provide a password for the `admin` user. The `admin` user has all permissions and is used to connect to XL Deploy's repository.

To use the default password of `admin`, press ENTER twice. If you plan to connect to an existing repository, enter the password that you already use to connect to that repository.

#### Step 2 Initialize the repository

The setup wizard will ask if you want to initialize the repository. Answer `yes` to create the repository, or `no` to connect to an existing repository.

For information about using an external database, refer to:

 * [Configure the XL Deploy SQL repository (XL Deploy 8.0 and later)](/xl-deploy/how-to/configure-the-xl-deploy-sql-repository.html)
 * [Configure the XL Deploy JCR repository (XL Deploy 7.6 and earlier)](/xl-release/how-to/configure-the-xl-deploy-repository.html)

**Warning**: If you choose to initialize the repository and you have installed XL Deploy in the same location before, any information stored in the repository will be lost.

#### Step 3 Generate an encryption key

If you choose to initialize the repository, the setup wizard will ask if you want to generate an encryption key to protect passwords that you store in the repository. Answer `yes` to generate a new key.

Answering `yes` will create an encryption key in `XL_DEPLOY_SERVER_HOME/conf/repository-keystore.jceks`. XL Deploy will use this key to encrypt and decrypt all password properties; it will also be used for [encrypted entries in dictionaries](/xl-deploy/how-to/create-a-dictionary.html) and passwords that are set in the `XL_DEPLOY_SERVER_HOME/conf/deployit.conf` file.

If you answer `no`, then you agree to use XL Deploy's own encryption key, or to use a key that you have previously generated.

#### Step 4 Provide a password for the encryption key

If you choose to generate an encryption key, you can also provide a password to secure the key. You will be required to enter this password when XL Deploy starts, either:

* Interactively via a prompt
* Non-interactively via a command-line parameter
* Non-interactively via a configuration file

If you do not want to provide a password, press ENTER twice.

See [Finish the setup process and log in](#finish-the-setup-process-and-log-in) to complete the setup process.

### Manual setup

Manual setup gives you control over all of XL Deploy's installation settings.

#### Step 1 Provide a password for the `admin` user

The setup wizard will promote you to provide a password for the `admin` user. The `admin` user has all permissions and is used to connect to XL Deploy's repository.

To use the default password of `admin`, press ENTER twice. If you plan to connect to an existing repository, enter the password that you already use to connect to that repository.

#### Step 2 Configure secure communication

The setup wizard will prompt you to set up secure communication (SSL) between the XL Deploy graphical user interface (GUI) and the XL Deploy server.

**Important:** If SSL is enabled, the `secure.cookie.enabled` attribute is automatically set to `true`. As an `admin`, you can manually configure the `secure.cookie.enabled` attribute in the `XL_DEPLOY_SERVER_HOME/conf/deployit.conf` file.

When `secure.cookie.enabled == true`:
1. If you are using HTTP, you cannot log in to XL Deploy and the `SESSION_XLD` cookie is marked as `secure`.
1. If you are using HTTPS, you can log in to XL Deploy and the `SESSION_XLD` cookie is marked as `secure`.
When `secure.cookie.enabled == false`:
1. If you are using HTTP, you can log in to XL Deploy and the `SESSION_XLD` cookie is marked as `not secure`.
1. If you are using HTTPS, you can log in to XL Deploy and the `SESSION_XLD` cookie is marked as `secure`.

#### Step 3 Generate a self-signed certificate

A digital certificate is required for secure communication; normally, certificates are signed by a Certificate Authority (CA). However, if you choose secure communication between the GUI and the server, the setup wizard will ask if you want XL Deploy to generate a self-signed digital certificate.

**Important:** For security reasons, a self-signed certificate is not recommended for production environments. It may trigger security warnings in some browsers and Flash players. A self-signed certificate can only be used when you access the XL Deploy GUI at `https://localhost:4516`.

#### Step 4 Use your own keystore

Instead of using a self-signed digital certificate, you can use your own keystore for secure communication between the GUI and the server. XL Deploy's built-in Jetty server requires a certificate with the name `jetty` to be present in the keystore.

The setup wizard will ask you for the keystore path (for example, `mykeystore.jks`), the keystore password, and the password of the `jetty` certificate in the keystore.

#### Step 5 Enable mutual SSL

The setup wizard will ask if you want to enable mutual SSL. If you answer yes, it will prompt you for the location and password to your truststore.

#### Step 6 Set up the HTTP configuration

The setup wizard will ask the HTTP bind address, HTTP port number, and web context root where you would like XL Deploy to run.

**Note**: If you chose to enable secure communication, the default port will be 4517 instead of 4516.

#### Step 7 Set up the thread configuration

The setup wizard will prompt you for the minimum and maximum number of threads that the XL Deploy server should use to handle incoming connections.

#### Step 8 Configure the repository

The setup wizard will ask where you want to store the repository. If the directory does not exist, XL Deploy will create it. The setup wizard will also ask if you want to initialize the repository.

For information about using an external database, refer to:
* [Configure the XL Deploy SQL repository (XL Deploy 8.0 and later)](/xl-deploy/how-to/configure-the-xl-deploy-sql-repository.html)
* [Configure the XL Deploy JCR repository (XL Deploy 7.6 and earlier)](/xl-release/how-to/configure-the-xl-deploy-repository.html)

**Warning**: If you choose to initialize the repository and you have installed XL Deploy in the same location before, any information stored in the repository will be lost.

#### Step 9 Generate an encryption key

If you choose to initialize the repository, the setup wizard will ask if you want to generate an encryption key to protect passwords that you store in the repository. Answer `yes` to generate a new key.

Answering `yes` will create an encryption key in `XL_DEPLOY_SERVER_HOME/conf/repository-keystore.jceks`. XL Deploy will use this key to encrypt and decrypt all password properties; it will also be used for [encrypted entries in dictionaries](/xl-deploy/how-to/create-a-dictionary.html) and passwords that are set in the `XL_DEPLOY_SERVER_HOME/conf/deployit.conf` file.

If you answer `no`, then you agree to use XL Deploy's own encryption key, or to use a key that you have previously generated.

#### Step 10 Provide a password for the encryption key

If you choose to generate an encryption key, you can also provide a password to secure the key. You will be required to enter this password when XL Deploy starts, either:

* Interactively via a prompt
* Non-interactively via a command-line parameter
* Non-interactively via a configuration file

If you do not want to provide a password, press ENTER twice.

See [Finish the setup process and log in](#finish-the-setup-process-and-log-in) to complete the setup process.

#### Step 11 Configure the location for importable packages

By default, you can import deployment packages from the `importablePackages` directory. If you would like to change this, enter a path when the setup wizard prompts you. If the directory does not exist, XL Deploy will create it.

## Finish the setup process and log in

After you have configured all options, the setup wizard shows a summary of the configuration that you have selected. Answer `yes` to finish the setup process. Answer `no` to exit setup.

If you answer `yes`, the setup wizard will start XL Deploy and show the URL where you can access it; for example, `http://localhost:4516`. Open this URL in a browser and log in with the username *admin* and the password that you provided during the setup process. This will open the XL Deploy default GUI.

As of version 6.2.0, the default GUI is HTML-based. To access the legacy GUI, click the gear icon menu and select **Go to legacy interface**. You can also access it by going directly to `XL_DEPLOY_URL:PORT/legacy-interface.html`.

### Configure your preferred GUI

To configure your preferred GUI in XL Deploy, you must change the value of the `http.welcome.page` parameter in the `deployit.conf` file. For example, to set the legacy GUI as your preferred GUI:

    http.welcome.page = /legacy-interface.html

**Note** You can specify any HTML page as a value for the `http.welcome.page` parameter. Make sure the specified HTML page redirects to either the default GUI or the legacy GUI log-in page.    

After you modify the `deployit.conf` file, you must restart the XL Deploy server.    

If you want to use the default GUI and the legacy GUI at the same time, you must open them in different browsers. The default GUI and the legacy GUI use different authentication methods so the same user cannot log into the XL Deploy interfaces using tabs or windows in the same browser.

**Tip:** For information about starting XL Deploy in the future, refer to [Start XL Deploy](/xl-deploy/how-to/start-xl-deploy.html).

## XL Deploy server directory structure

After you install XL Deploy, it will have the following directory structure:

* `bin`: Contains the server binaries
* `conf`: Contains server configuration files and the XL Deploy license
* `doc`: Contains the XL Deploy product documentation
* `ext`: Contains server extensions
* `hotfix`: Contains hotfixes (XL Deploy 4.5.x and earlier only)
* `hotfix/lib`: Contains hotfixes that fix issues with the server software (XL Deploy 5.0.0 and later)
* `hotfix/plugins`: Contains hotfixes that fix issues with the plugin software (XL Deploy 5.0.0 and later)
* `importablePackages`: Default location for importable deployment packages
* `lib`: Contains libraries that the server needs
* `log`: Contains server log files (this directory is only present once you have started XL Deploy server)
* `plugins`: Contains XL Deploy middleware plugins
* `recovery.dat`: Stores tasks that are in progress for recovery purposes (this file is only present after you have started XL Deploy server)
* `samples`: Contains sample plugins and configuration snippets

## Automatically install XL Deploy with default values

You can automate the installation of XL Deploy with a set of default values that you save in a file. This is useful, for example, when setting up XL Deploy using a tool such as Puppet or Ansible. To install XL Deploy, use the following command:

    bin/run.sh -setup -reinitialize -force -setup-defaults /path/to/deployit.conf

Where the `deployit.conf` file contains the installation values that you want to use.

### Sample of `deployit.conf` file

    #XL Deploy configuration file.
    #
    #Notes:
    # * If you modify this file, you must restart the XL Deploy server.
    # * Use `repository.keystore.password` property to specify the password used to open the repository keystore file,
    #   which contains the encryption key for the rest of the passwords.
    # * The other passwords specified in this file are normally encrypted, but you can change their value using plaintext
    #   and at the next start of the server the modified passwords will be re-encrypted.

    admin.password={{ xld_admin_password }}
    jcr.repository.path=repository
    threads.min=3
    maintenance.forbidden_paths=deployit/task\:POST,deployit/tasks/v2\:POST,deployit/control\:POST
    client.server_poll.delay=30000
    ssl=true
    client.session.remember.enabled=true
    http.bind.address=0.0.0.0
    http.context.root=/
    threads.max=24
    ssl.mutual=false
    http.upload.buffer=262144
    client.session.timeout.minutes=0
    http.port=4516
    client.automatically.map.all.deployables=true
    hide.internals=false
    importable.packages.path=importablePackages
    keystore.path=conf/xl-keystore.jks
    keystore.keypassword={{ keystore_keypassword }}
    keystore.password= {{ keystore_password }}

## Install the XL Deploy CLI

For information about installing the XL Deploy command-line interface (CLI), refer to [Install the XL Deploy CLI](/xl-deploy/how-to/install-the-xl-deploy-cli.html).

## Failover configuration

If you store the XL Deploy repository in a [database](/xl-deploy/how-to/configure-the-xl-deploy-repository.html#using-a-database), you can set up a failover configuration as described in [Configure failover for XL Deploy](/xl-deploy/how-to/configure-failover.html).
