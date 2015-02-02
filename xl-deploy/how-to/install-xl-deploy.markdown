---
title: Install XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- getting started
---

Before you install XL Deploy, ensure that you meet the [prerequisites](/xl-deploy/concept/requirements-for-installing-xl-deploy.html).

**Tip:** If you are upgrading a previously installed version of XL Deploy, refer to [Upgrade XL Deploy](upgrade-xl-deploy.html).

To install the XL Deploy server application:

1. Log in to the server where the XL Deploy Server will be installed. It is recommended to install XL Deploy Server as a non-root user such as `xldeploy`.
2. Create an installation directory such as `/opt/xebialabs/xl-deploy`.
3. Copy the XL Deploy Server archive to the directory.
3. Extract the archive into the directory.

## Install the license

The license for XL Deploy must be installed manually:

1. Download license from [https://dist.xebialabs.com/licenses/download/](https://dist.xebialabs.com/licenses/download/).
1. Copy the downloaded file to the `conf` directory. It must be installed as `deployit-license.lic`. Be sure not to modify the license file (some mail clients change the file unless it is packed in a ZIP).

### License validation

XL Deploy checks the validity of your license when you start the XL Deploy server and at certain times while the server is running.

The server will not start if a valid license is not installed.

If XL Deploy finds a license violation while the server is running, the server will not stop; however, some requests will be denied. For example:

* If the license usage time has expired, the server will deny all requests.
* If the license limits the number of configuration items (CIs) that you can create and that limit has been reached for a CI type, the server will not allow you to create new CIs of that type; see [Licensed number of configuration items](#licensed-number-of-configuration-items) for more information.

**Note:** After the server is stopped, it might not start again (for example, if the license expiry date has been reached).

### Licensed number of configuration items

If your license limits the number of configuration items (CIs) that you can create, XL Deploy validates it as follows:

* You cannot create more instances of a CI type than your license allows. Note that if you delete instances of a CI type, you can create new instances of that type.
* If a CI is a subtype of another type (its *supertype*), the instances of the subtype CI count toward the limit on the supertype. 
* You can always create instances of CI types that are not limited by your license.

### Checking license usage

Your license may pose restrictions one or more CI types, disallowing you to create more than the given number of CIs of that type. You can find out how many you have created already by opening the About box in the GUI, and going to the License tab. The About box opens by clicking the arrow next to the Help dropdown in the top right corner of the XL Deploy GUI.

## Install the CLI

Follow these steps to install the XL Deploy CLI application:

1. Login to the server where the XL Deploy CLI will be installed.
2. Create an installation directory.
3. Copy the XL Deploy CLI archive to the directory.
3. Extract the archive into the directory.

## Run the server Setup Wizard

Run the XL Deploy Setup Wizard to start the XL Deploy server and prepare it for use. The command `run.sh -setup` starts the wizard. If you want to stop the Setup Wizard at any time, enter `exitsetup`. All changes to the configuration will be discarded.

The Setup Wizard displays the welcome message. Answer `yes` (or press ENTER) to use the simple setup. Simple setup makes it easy to quickly get started with XL Deploy and to use the product's default configuration.

Answer `no` to use the manual setup. Manual setup provides explicit control over all XL Deploy settings. See [Manual setup](#manual-setup) for more information.

**Note**: If you installed XL Deploy in the same location before, the Setup Wizard will ask you whether you want to edit the existing configuration or create a new one. Answer `yes` (or press ENTER) to edit the existing configuration. The Setup Wizard will load all settings from the existing configuration and allow you to choose simple or manual setup. Answer `no` to start over with an empty configuration.

### Simple setup

Using simple setup, the Setup Wizard will assume default values for all configuration parameters. Specifically, the following defaults will be used:

* The server will run with security enabled.
* The server will not use secure communication between the XL Deploy GUI and the XL Deploy server.
* The server will listen on XL Deploy's standard HTTP port (4516).
* The server will use `/` as the context root.
* The server will use a minimum of 3 and a maximum of 24 threads.
* Applications can be imported from the `importablePackages` directory.

First, the Setup Wizard will ask you to provide a password for the built-in `admin` user.

The admin user has by definition all permissions (like 'root' on a unix operating system). The admin user is also used to connect to the JCR repository. If you connect to an existing repository, make sure you enter the password that has been used before to connect to the repository, otherwise the XL Deploy Server will not be able to connect. To change the admin password of an initialized repository, use the method described below in [Changing the admin password](#changing-the-admin-password).

If you give an empty password (pressing ENTER twice), the default password `admin` will be used.

Then, the Setup Wizard will ask if you want XL Deploy to initialize the JCR repository.

Answer `yes` (or press ENTER) if you want the XL Deploy repository to be recreated. The Setup Wizard must have write access to the repository directory. Answer `no` to leave the repository intact. This option is useful if you already have an existing repository that you want to reuse.

If you answer `yes`, the Setup Wizard will ask the following questions to help you configure your repository:

    The password encryption key protects the passwords stored in the repository.
    Do you want to generate a new password encryption key?
    Options are yes or no.
    [yes]:

XL Deploy ships with a default encryption key that matches the encryption key used in earlier versions of XL Deploy. By answering `no`, you agree to use either the XL Deploy-provided key or any key you previously generated. Answer `yes` to generate a new key. When you do this, you have the option of locking the keystore with a password as well:

    The password encryption key is optionally secured by a password.
    Please enter the password you wish to use. (Use an empty password to avoid a password prompt when starting XL Deploy.)
    New password:
    Re-type password:

If you want to secure the keystore with a password, enter the password here. You will need to provide this password to XL Deploy when it starts. There are three ways you can provide the password: interactively via a prompt, non-interactively via a command line parameter, and non-interactively via the configuration file. If you don't want to use a password for the keystore, press enter.

See [Finishing the setup process](#finishing-the-setup-process) to complete the setup process.

**Warning**: If you choose to recreate the XL Deploy repository and you have installed XL Deploy in the same location before, any information stored in the repository will be lost.

### Manual setup

The manual setup procedure contains the following steps:

#### Admin password

First, the Setup Wizard will ask you to provide a password for the built-in `admin` user.

The admin user has by definition all permissions (like `root` on a Unix operating system). The admin user is also used to connect to the JCR repository. If you connect to an existing repository, make sure you enter the password that has been used before to connect to the repository, otherwise the XL Deploy Server will not be able to connect. To change the admin password of an initialized repository, use the method described in [Changing the admin password](#changing-the-admin-password).

If you give an empty password (pressing ENTER twice), the default password `admin` will be used.

#### Secure communication configuration

The Setup Wizard will show the following message:

    Would you like to enable SSL?
    Options are yes or no.
    [yes]:

Answer `no` to use unsecured communication between the GUI and the server. Continue with the [HTTP configuration](#http-configuration) section.

Answer `yes` (or press ENTER) if you want to use a secure connection from the GUI to the server.

If you answer `yes`, the Setup Wizard will ask the following question to help you configure secure communication:

    Would you like XL Deploy to generate a keystore with a self-signed
    certificate for you?
    N.B.: Self-signed certificates do not work correctly with some versions
    of the Flash Player and some browsers!
    Options are yes or no.
    [yes]:

Answer `yes` (or press ENTER) if you want the Setup Wizard to generate a digital certificate automatically. The digital certificate is required to secure communication and is normally signed by a Certificate Authority (CA). The Setup Wizard can generate a _self-signed_ certificate if there is no official certificate available. Continue with the [HTTP configuration](#http-configuration) section.

**Note:** For security reasons, using a self-signed certificate is not recommended in a production environment. A self-signed certificate may trigger security warnings in some Flash players and browsers. This certificate can only be used when you access the XL Deploy server at `https://localhost:4516`.

Answer `no` if you want to use your own keystore. XL Deploy uses the built-in Jetty web server to communicate with the GUI. Jetty requires a certificate with the name `jetty` to be present in the keystore.

The Setup Wizard prompts you for the following keystore information:

    What is the path to the keystore?
    []:

    What is the password to the keystore?
    []:

    What is the password to the key in the keystore?
    []:

Enter the filesystem location of the keystore (for example, `mykeystore.jks`), the password to unlock the keystore and the password for the `Jetty` certificate in the keystore.

### HTTP configuration

The Setup Wizard shows the following questions:

    What http bind address would you like the server to listen on?
    [localhost]:

    What http port number would you like the server to listen on?
    [4516]:

    Enter the web context root where XL Deploy will run
    [/]:

**Note**: If you chose to enable secure communication, the default port will be 4517 instead of 4516.

Enter the host name, port number and context root that the XL Deploy server listens on for connections.

### Thread configuration

The Setup Wizard shows the following questions:

    Enter the minimum number of threads the HTTP server should use (recommended:
        3 per client, so 3 for single user usage)
    [3]:

Enter the minimum number of threads that the XL Deploy server uses to handle incoming connections. The recommended minimum number of threads is 3 per XL Deploy application client.

    Enter the maximum number of threads the HTTP server should use (recommended :
        3 per client, so 24 for 8 concurrent users)
    [24]:

Enter the maximum number of threads that the XL Deploy server uses to handle incoming connections. The recommended maximum number of threads is 3 per XL Deploy application client.

### Repository configuration

The Setup Wizard shows the following questions:

    Where would you like XL Deploy to store the JCR repository?
    [repository]:

Enter the filesystem path to a directory where XL Deploy will create the repository. If the directory does not exist, the Setup Wizard will create it.

    Do you want XL Deploy to initialize the JCR repository?
    Options are yes or no.
    [yes]:

Answer `no` to leave the repository intact.

Answer `yes` (or press ENTER) if you want the XL Deploy repository to be recreated. The Setup Wizard must have write access to the repository directory. The Setup Wizard will ask the following questions to help you configure your repository:

    The password encryption key protects the passwords stored in the repository.
    Do you want to generate a new password encryption key?
    Options are yes or no.
    [yes]:

XL Deploy ships with a default encryption key that matches the encryption key used in earlier versions of XL Deploy. By answering `no`, you agree to use either the XL Deploy-provided key or any key you previously generated. Answer `yes` to generate a new key. When you do this, you have the option of locking the keystore with a password as well:

    The password encryption key is optionally secured by a password.
    Please enter the password you wish to use. (Use an empty password to avoid a password prompt when starting XL Deploy.)
    New password:
    Re-type password:

If you want to secure the keystore with a password, enter the password here. You will need to provide this password to XL Deploy when it starts, either interactively via a prompt or non-interactively via a command line parameter or using the configuration file. If you don't want to use a password for the keystore, press ENTER.

**Warning**: If you choose to recreate the XL Deploy repository and you have installed XL Deploy in the same location before, any information stored in the repository will be lost.

### Importable packages configuration

The Setup Wizard shows the following question:

    Where would you like XL Deploy to import packages from?
    [importablePackages]:

Enter the filesystem path to a directory from which XL Deploy will import packages. The Setup Wizard assumes that this directory exists once the XL Deploy server starts and will not create it.

### Finishing the setup process

Once you have completed configuration of the setup process, the Setup Wizard displays an overview of all selected options. The following text is an example:

    Do you agree with the following settings for XL Deploy and would you like
        to save them?
    Changes will be saved in deployit.conf
        SSL will be disabled
        HTTP bind address is localhost
        HTTP port is 4516
        HTTP server will use a minimum of 3 and a maximum of 24 threads
        JCR repository home is at repository
        JCR repository will be initialized.
        Task recovery file will deleted
        Application import location is importablePackages
    [yes]:

Answer `yes` or press ENTER to store the configuration settings and end the Setup Wizard. If you selected the option to initialize the repository, this will be done now.

Answer `no` to abort the Setup Wizard.

If the Setup Wizard is successfully completed, it will display the following message:

    You can now start your XL Deploy server by executing the command run.cmd
        on Windows or run.sh on Unix.
    Note: If your XL Deploy server is running please restart it.
    Finished setup.

## Install the server as a daemon or service

<div class="alert alert-danger" role="alert">The information in this section is in beta and is subject to change.</div>

Before installing the server as a service, first follow the installation procedure documented above. This includes as a step executing `bin/run.sh` or `bin/run.cmd` script in order to configure and initialize the server. On Linux, you should do this as the user under which you want XL Deploy to run. Also, please make sure the server is configured so that it can start without input from the user (e.g. if a repository keystore password is required then it should be provided in `deployit.conf`).

The next step is to execute `bin/install.sh` on Linux or `bin/install.cmd` on Windows. These scripts will do the actual installation of the service. They require the current user to be root on Linux or an Administrator on Windows.
On Linux you will be asked for the user name under which XL Deploy server was previously installed using `run.sh`.

To remove the installed service from the system please use `bin/uninstall.sh` or `bin/uninstall.cmd` scripts.

## Changing the admin password

XL Deploy has a special user with administrative permissions, the `admin` user. The admin password can be set in the setup wizard when installing XL Deploy.

To change the admin password, the following procedure has to be followed. Make sure the XL Deploy Server is running. Then start the CLI as the admin user (or another user with admin rights) and issue the following commands:

    adminUser = security.readUser('admin')
    adminUser.password = 'newpassword'
    security.modifyUser(adminUser)

At this point, the XL Deploy Server must be stopped. Once this has happened, set the new admin password in the `deployit.conf` file in the `conf` directory of the server installation directory.

Restart XL Deploy and test the new credentials in the CLI using the following command:

    security.login('admin', 'newpassword')

## Changing the encryption key password

The passwords in the repository are encrypted with a secret key. This password encryption key is stored in a keystore file called `conf/repository-keystore.jceks`. This keystore file is optionally protected with a password. If a password is set, you need to enter the password when the XL Deploy Server starts up.

To change the keystore password, you can use the `keytool` utility that is part of the Java JDK distribution. Usage:

    keytool -storepasswd -keystore conf/repository-keystore.jceks -storetype jceks

There is one restriction: keytool refuses to read or set passwords that are shorter than 6 characters. If you want to change a keystore with an empty or short password, download the third-party application **KeyStore Explorer**.

**Note:** `repository-keystore.jceks` is one of the two keystore concepts in XL Deploy. This keystore only contains the key used for encryption of passwords in the repository. If you use HTTPS, XL Deploy will use a second keystore file to store the (self-signed) certificate.

## High availability setup

XL Deploy can be configured to ensure maximum uptime of the application. In such a high availability setup, two instances of XL Deploy are running in an active/passive configuration. At any one time, only one XL Deploy instance is active but as soon as a failure is detected, the passive XL Deploy instance is activated and the failed instance is taken down for repair.

To configure XL Deploy for high availability, the XL Deploy repository must be used in clustering mode. This means that each XL Deploy node writes changes to a shared journal, in addition to applying the change to its own repository. See [Configure the XL Deploy repository](/xl-deploy/how-to/configure-the-xl-deploy-repository.html) for more information on setting up clustering.

## XL Deploy server directory structure

After the XL Deploy installation file is extracted, the following directory structure exists in the installation directory (`XLDEPLOY_SERVER_HOME`):

* `bin`: Contains the server binaries
* `conf`: Contains server configuration files and the XL Deploy license
* `doc`: Contains the XL Deploy product documentation
* `ext`: Contains server extensions
* `hotfix`: Contains hotfixes that fix issues with the server software
* `hotfix/plugins`: Contains hotfixes that fix issues with the plugin software
* `importablePackages`: Default location for importable deployment packages
* `lib`: Contains libraries that the Server needs
* `log`: Contains Server log files (this directory is only present once you have started XL Deploy Server)
* `plugins`: Contains the XL Deploy middleware plugins
* `recovery.dat`: Stores tasks that are in progress for recovery purposes (this file is only present after you have started XL Deploy server)
* `samples`: Contains sample plugins and configuration snippets

## XL Deploy CLI directory structure

After the XL Deploy installation file is extracted, the following directory structure exists in the installation directory:

* `bin`: Contains the CLI binaries
* `ext`: Contains CLI Python extension scripts
* `hotfix`: Contains hotfixes that fix issues with the CLI software
* `lib`: Contains necessary libraries
* `plugins`: Contains the CLI plugins
