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
---

Before you install XL Release, ensure that you meet the [prerequisites](/xl-release/concept/requirements-for-installing-xl-release.html).

<!--**Note:** See **The upgrade process** for instructions on how to upgrade XL Release from a previous version.
-->
To install the XL Release server application:

1. Log in to the server where the XL Release Server will be installed. It is recommended to install XL Release server as a non-root user such as `xl-release`.
2. Create an installation directory such as `/opt/xebialabs/xl-release`.
3. Copy the XL Release server archive to the directory.
3. Extract the archive into the directory.

The installation directory is referred to as `XL_RELEASE_SERVER_HOME`.

## Installing the license

XL Release requires a valid license to operate. Your XebiaLabs representative will provide a license.

To activate the license, save the `xl-release-license.lic` file in the `XL_RELEASE_SERVER_HOME/conf` directory.

## Running the server Setup Wizard

Run the XL Release Setup Wizard to start the XL Release server and prepare it for use. The command `server.sh -setup` starts the wizard. If you want to stop the Setup Wizard at any time, enter `exitsetup`. All changes to the configuration will be discarded.

The Setup Wizard displays the following welcome message:

    Welcome to the XL Release setup.
    You can always exit by typing 'exitsetup'.
    To re-run this setup and make changes to the XL Release server configuration
    you can run server.cmd -setup on Windows or server.sh -setup on Unix.

    Do you want to use the simple setup?
    Default values are used for all properties. To make changes to the default
    properties, please answer no.
    Options are yes or no.
    [yes]:

Answer **yes** or press ENTER to use the simple setup. Simple setup makes it easy to quickly get started with XL Release and to use the product's default configuration. See **Simple setup** for more information.

Answer **no** to use the manual setup. Manual setup provides explicit control over all XL Release settings. See **Manual setup** for more information.

### Edit an existing configuration

If you have installed XL Release in the same location before, the Setup Wizard will ask you whether you want to edit the existing configuration or create a new one. Answer **yes** or press ENTER to edit the existing configuration. The Setup Wizard will load all settings from the existing configuration and allow you to choose simple or manual setup. Answer **no** to start over with an empty configuration.

## Simple setup

Using simple setup, the Setup Wizard uses the default values for all configuration parameters:

* The server will run with security enabled.
* The server will **not** use secure communication between the XL Release GUI and the XL Release server.
* The server will listen on XL Release's standard HTTP port (5516).
* The server will use `/` as the context root.
* The server will use a minimum of 3 and a maximum of 24 threads.

First, the Setup Wizard will ask you to provide a password for the built-in `admin` user.

    Please enter the admin password you wish to use for XL Release
    New password:
    Re-type password:

The admin user has all permissions (like `root` on a Unix operating system). The admin user is also used to connect to the JCR repository. If you connect to an existing repository, ensure that enter the password that has been used before to connect to the repository; otherwise, the XL Release server will not be able to connect.

If you provide an empty password by pressing ENTER twice, the default password `admin` is used.

Then, the Setup Wizard will ask:

    Do you want XL Release to initialize the JCR repository?
    Options are yes or no.
    [yes]:

Answer **yes** or press ENTER if you want the XL Release repository to be recreated. The Setup Wizard must have write access to the repository directory. Answer **no** to leave the repository intact. This option is useful if you have an existing repository that you want to reuse.

**Warning**: If you choose to recreate the XL Release repository and you have installed XL Release in the same location before, any information stored in the repository will be lost.

If you answer **yes**, the Setup Wizard will ask the following questions to help you configure your repository:

    The password encryption key protects the passwords stored in the repository.
    Do you want to generate a new password encryption key?
    Options are yes or no.
    [yes]:

XL Release includes a default encryption key. By answering **no**, you agree to use either the XL Release-provided key or any key you previously generated. Answer **yes** to generate a new key. When you do this, you have the option of locking the keystore with a password as well:

    The password encryption key is optionally secured by a password.
    Please enter the password you wish to use. (Use an empty password to avoid a password prompt when starting XL Release.)
    New password:
    Re-type password:

If you want to secure the keystore with a password, enter the password here. You must provide this password to XL Release when it starts, either interactively via a prompt or via a command line parameter. If you do not want to use a password for the keystore, press ENTER.

See **Finishing the setup process** to complete the setup process.

## Manual setup

The manual setup procedure contains the following steps.

### Setting the admin password

First, the Setup Wizard asks you to provide a password for the built-in `admin` user.

    Please enter the admin password you wish to use for XL Release Server
    New password:
    Re-type password:

The admin user has all permissions (like `root` on a Unix operating system). The admin user is also used to connect to the JCR repository. If you connect to an existing repository, ensure that enter the password that has been used before to connect to the repository; otherwise, the XL Release server will not be able to connect.

### Secure communication configuration**

The Setup Wizard shows the following message:

    Would you like to enable SSL?
    Options are yes or no.
    [yes]:

Answer **no** to use regular unsecured communication between the GUI and the server. Continue with the **HTTP configuration** section.

Answer **yes** or press ENTER to use a secure connection between the GUI and the server. If you answer **yes**, the Setup Wizard asks the following question to help you configure secure communication:

    Would you like XL Release to generate a keystore with a self-signed
    certificate for you?
    N.B.: Self-signed certificates do not work correctly with some versions
    of the Flash Player and some browsers!
    Options are yes or no.
    [yes]:

Answer **yes** or press ENTER if you want the Setup Wizard to generate a digital certificate automatically. The digital certificate is required to secure communication and is normally signed by a Certificate Authority (CA).

The Setup Wizard can generate a _self-signed_ certificate if there is no official certificate available. Note that using a self-signed certificate may trigger security warnings in some Flash players and browsers. Continue with the **HTTP configuration** section.

Answer **no** if you want to use your own keystore. XL Release uses the built-in Jetty web server to communicate with the GUI. Jetty requires a certificate with the name `jetty` to be present in the keystore.

The Setup Wizard prompts you for the following keystore information:

    What is the path to the keystore?
    []:

    What is the password to the keystore?
    []:

    What is the password to the key in the keystore?
    []:

Enter the filesystem location of the keystore (for example, _mykeystore.jks_), the password to unlock the keystore, and the password for the `Jetty` certificate in the keystore.

The Setup Wizard shows the following message:

    Would you like to enable mutual SSL?
    Options are yes or no.
    [yes]:

Answer **yes** or press ENTER if you want the Setup Wizard to configure mutual SSL.

Answer **no** if you do not want mutual SSL. Continue with the **HTTP configuration** section.

The Setup Wizard prompts you for the following mutual SSL information:

    What is the path to the truststore?
    []:

    What is the password to the truststore?
    []:

Enter the filesystem location of the truststore (for example, _cacerts.jks_) and the password to unlock the truststore.

### HTTP configuration

The Setup Wizard shows the following questions:

    What http bind address would you like the server to listen on?
    [localhost]:

    What http port number would you like the server to listen on?
    [5516]:

    Enter the web context root where XL Release will run
    [/]:

Enter the host name, port number, and context root that the XL Release server listens on for connections.

XL Release derives its URL from this information. This URL is used in the emails that the XL Release server sends. In the next question, you can override this value. For example, if XL Release runs behind a proxy server and must be accessed using a different URL:

    Enter the public URL to access XL Release
    [http://xlrelease-server:5516/]:

### Thread configuration

The Setup Wizard shows the following questions:

    Enter the minimum number of threads the HTTP server should use (recommended:
        3 per client, so 3 for single user usage)
    [3]:

Enter the minimum number of threads that the XL Release server uses to handle incoming connections. The recommended minimum number of threads is 3 per XL Release application client.

    Enter the maximum number of threads the HTTP server should use (recommended :
        3 per client, so 24 for 8 concurrent users)
    [24]:

Enter the maximum number of threads that the XL Release server uses to handle incoming connections. The recommended maximum number of threads is 3 per XL Release application client.

**Note:** After setup is complete0, you can see the thread configuration in the `threads.min` and `threads.max` settings in the `conf/xl-release-server.conf` file.

### Repository configuration

The Setup Wizard shows the following questions:

    Where would you like XL Release to store the JCR repository?
    [repository]:

Enter the filesystem path to a directory where XL Release will create the repository. If the directory does not exist, the Setup Wizard will create it.

    Do you want XL Release to initialize the JCR repository?
    Options are yes or no.
    [yes]:

Answer **no** to leave the repository intact.

Answer **yes** or press ENTER if you want the XL Release repository to be recreated. The Setup Wizard must have write access to the repository directory. The Setup Wizard will ask the following questions to help you configure your repository:

    The password encryption key protects the passwords stored in the repository.
    Do you want to generate a new password encryption key?
    Options are yes or no.
    [yes]:
    
**Warning**: If you choose to recreate the XL Release repository and you have installed XL Release in the same location before, any information stored in the repository will be lost.

XL Release ships with a default encryption key that matches the encryption key used in earlier versions of XL Release. By answering **no**, you agree to use either the XL Release-provided key or any key you previously generated. Answer **yes** to generate a new key. When you do this, you have the option of locking the keystore with a password as well:

    The password encryption key is optionally secured by a password.
    Please enter the password you wish to use. (Use an empty password to avoid a password prompt when starting XL Release.)
    New password:
    Re-type password:

If you want to secure the keystore with a password, enter the password here. You must to provide this password to XL Release when it starts, either interactively via a prompt or via a command line parameter. If you do not want to use a password for the keystore, press enter.

## Finishing the setup process

Afer you have completed configuration of the setup process, the Setup Wizard displays an overview of all selected options. For example:

    Do you agree with the following settings for XL Release and would you like
        to save them?
    Changes will be saved in xl-release-server.conf
        SSL will be disabled
        HTTP bind address is localhost
        HTTP port is 5516
        HTTP server will use a minimum of 3 and a maximum of 24 threads
        JCR repository home is at repository
        JCR repository will be initialized.
        Task recovery file will deleted
    [yes]:

Answer **yes** or press ENTER to store the configuration settings and end the Setup Wizard. If you selected the option to initialize the repository, this will be done now.

Answer **no** to abort the Setup Wizard.

If the Setup Wizard completes successfully, it shows the following message:

    You can now start your XL Release server by executing the command server.cmd
        on Windows or server.sh on Unix.
    Note: If your XL Release server is running please restart it.
    Finished setup.

### High availability setup

XL Release can be configured to ensure maximum uptime of the application. In such a high availability setup, two instances of XL Release run in an _active/passive_ configuration. At any one time, only one XL Release instance is active; but as soon as a failure is detected, the passive XL Release instance is activated and the failed instance is taken down for repair.

To configure XL Release for high availability, the XL Release repository must be used in _clustering_ mode. This means that each XL Release node writes changes to a shared journal in addition to applying the change to its own repository. See [Configure the XL Release repository in a database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html) for information about setting up clustering.
