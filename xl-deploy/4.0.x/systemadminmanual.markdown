---
title: System Administration Manual
---

## Preface

This manual describes how to install and configure XL Deploy.

## Installing XL Deploy

This section contains information on the installation of the XL Deploy server.

### Prerequisites

#### Server requirements

To install the XL Deploy server, the following prerequisites must be met:

* **XL Deploy license**: You can download your license from [https://tech.xebialabs.com/download/license](https://tech.xebialabs.com/download/license). 
* **Operating system**: Windows or Unix-family operating system running Java.
* **Java Runtime Environment**: JDK 7 (Oracle, IBM or Apple)
* **RAM**: At least 2GB of RAM available for XL Deploy.
* **Hard disk space**: Sufficient hard disk space to store the XL Deploy repository. This depends on your usage of XL Deploy. See [Determining hard disk space requirements](#determining-hard-disk-space-requirements).

Depending on the environment, the following may also be required: 

* **Database**: XL Deploy's Jackrabbit repository supports a number of different databases. For more information, see [Configuring the repository](#configuring-the-repository).
* **LDAP**: To enable group-based security, an LDAP x.509 compliant registry is needed. For more information, see [Configuring LDAP security](#configuring-ldap-security).

#### Determining hard disk space requirements

The XL Deploy server itself only uses about 70MB of disk space. The main hard disk space usage comes from the repository which stores your deployment packages and deployment history. The size of the repository will vary from installation to installation but depends mainly on the:

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

#### Unix middleware server requirements

Unix-based middleware servers that XL Deploy interacts with must meet the following requirements:

* **SSH access**: The target systems should be accessible by SSH from the XL Deploy server, i.e. they should run an SSH2 server. It is also possible to handle key-based authorization. Notes:
    * The SSH daemon on AIX is known to hang with certain types of SSH traffic.
    * For security, the SSH account that is used to access a host should have limited rights.
    * A variety of Linux distributions have made SSH require a TTY by default. This setting is incompatible with XL Deploy and is controlled by the `Defaults requiretty` setting in the `sudoers` file.
* **Credentials**: XL Deploy should be able to log in to the target systems using a login/password combination that allows it to perform at least the following Unix commands:
    * `cp`
    * `ls`
    * `mv`
    * `rm`
    * `mkdir`
    * `rmdir`

    If the login user cannot perform these actions, XL Deploy can also use a `sudo` user that can execute these commands.

#### Windows middleware server requirements

Windows-based middleware servers that XL Deploy interacts with must meet the following requirements:

* **File system access**: The target file system should be accessible via CIFS from the XL Deploy server.
* **Host access**: The target host should be accessible from the XL Deploy server via WinRM or Windows Telnet server running in _stream mode_.
* **Directory shares**: The account used to access a target system should have access to the host's administrative shares such as **C$**.
* **Ports**: For CIFS connectivity, port 445 on the target system should be accessible from the XL Deploy server. For Telnet connectivity, port 23 should be accessible from the XL Deploy server. For WinRM connectivity, port 5985 (HTTP) or port 5986 (HTTPS) should be accessible from the XL Deploy server.

#### Extending middleware support

It is possible to connect XL Deploy to middleware servers that do not support SSH, Telnet or WinRM. Using the Overthere remote execution framework, a custom _access method_ can be created that connects to the server. See the [Customization Manual](customizationmanual.html) for more information.

#### Client requirements

##### GUI clients

To use the XL Deploy GUI, you must meet the following requirements:

* **Web browser**: The following web browsers are supported:
	* Internet Explorer 8.0 or later
	* Firefox
	* Chrome
	* Safari
* **Flash player**: A Flash player is required, versions 9.0 and up are supported.

##### CLI clients

To use the XL Deploy CLI, you must meet the following requirements:

* **Operating system**: Windows or Unix-family operating system running Java.
* **Java Runtime Environment**: JDK 7 (Oracle, IBM or Apple)

### Installation Procedure

To begin installing XL Deploy, first unpack the distribution archive. The distribution archive contains the following:

* Release notes describing the changes made in this version of XL Deploy
* A server archive
* A CLI archive

**Note:** See the [Upgrade Manual](upgrademanual.html) for instructions on how to upgrade XL Deploy from a previous version.

#### Installing the server

Follow these steps to install the XL Deploy server application:

1. Log in to the server where the XL Deploy Server will be installed. It is recommended to install XL Deploy Server as a non-root user such as `xldeploy`.
2. Create an installation directory such as `/opt/xebialabs/xl-deploy`.
3. Copy the XL Deploy Server archive to the directory.
3. Extract the archive into the directory.

**XL Deploy server directory structure**

After the XL Deploy installation file is extracted, the following directory structure exists in the installation directory (in the remainder of the document this directory will be referred to as `XLDEPLOY_SERVER_HOME`):

* `bin`: Contains the server binaries
* `conf`: Contains server configuration files and the XL Deploy license
* `doc`: Contains the XL Deploy product documentation
* `ext`: Contains server Java extensions. See the **Customization Manual** for more information
* `hotfix`: Contains hotfixes that fix issues with the Server software
* `importablePackages`: default location for importable packages
* `lib`: Contains libraries that the Server needs
* `log`: Contains Server log files (this directory is only present once you have started XL Deploy Server)
* `plugins`: Contains the XL Deploy middleware plugins
* `recovery.dat`: Stores tasks that are in progress for recovery purposes (this file is only present after you have started XL Deploy server)
* `samples`: Contains sample plugins and configuration snippets

#### Installing the license

The license for XL Deploy must be installed manually:

1. Download license from [https://tech.xebialabs.com/download/license](https://tech.xebialabs.com/download/license).
1. Copy the downloaded file to the `conf` directory. It must be installed as `deployit-license.lic`. Be sure not to modify the license file. (Some mail clients change the file unless it is packed in a ZIP)

**Note:** The license can be installed while server is running. It will be picked up automatically. 

When the license expires XL Deploy will finish running tasks and not accept any new requests. Also during boot up information about the license status will be printed in the logging. Once a new license is obtained it can be copied into the running server which will start functioning again.

#### Installing the CLI

Follow these steps to install the XL Deploy CLI application:

1. Login to the server where the XL Deploy CLI will be installed.
2. Create an installation directory.
3. Copy the XL Deploy CLI archive to the directory.
3. Extract the archive into the directory.

**XL Deploy CLI directory structure**

After the XL Deploy installation file is extracted, the following directory structure exists in the installation directory:

* `bin`: Contains the CLI binaries
* `ext`: Contains CLI Python extension scripts
* `hotfix`: Contains hotfixes that fix issues with the CLI software
* `lib`: Contains necessary libraries
* `plugins`: Contains the CLI plugins

#### Running the server Setup Wizard

Run the XL Deploy Setup Wizard to start the XL Deploy server and prepare it for use. The command `server.sh -setup` starts the wizard. If you want to stop the Setup Wizard at any time, enter `exitsetup`. All changes to the configuration will be discarded.

The Setup Wizard displays the following welcome message:

    Welcome to the XL Deploy setup.
    You can always exit by typing 'exitsetup'.
    To re-run this setup and make changes to the XL Deploy server configuration
    you can run server.cmd -setup on Windows or server.sh -setup on Unix.

    Do you want to use the simple setup?
    Default values are used for all properties. To make changes to the default
    properties, please answer no.
    Options are yes or no.
    [yes]:

Answer **yes** (or press Enter) to use the simple setup. Simple setup makes it easy to quickly get started with XL Deploy and to use the product's default configuration. See **Simple setup** for more information.

Answer **no** to use the manual setup. Manual setup provides explicit control over all XL Deploy settings. See **Manual setup** for more information.

**Note**: If you installed XL Deploy in the same location before, the Setup Wizard will ask you whether you want to edit the existing configuration or create a new one. Answer **yes** (or press ENTER) to edit the existing configuration. The Setup Wizard will load all settings from the existing configuration and allow you to choose simple or manual setup. Answer **no** to start over with an empty configuration.

##### Simple setup

Using simple setup, the Setup Wizard will assume default values for all configuration parameters. Specifically, the following defaults will be used:

* The server will run with security enabled.
* The server will **not** use secure communication between the XL Deploy GUI and the XL Deploy server.
* The server will listen on XL Deploy's standard HTTP port (4516).
* The server will use '/' as the context root.
* The server will use a minimum of 3 and a maximum of 24 threads.
* Applications can be imported from the `importablePackages` directory.

First, the Setup Wizard will ask you to provide a password for the built-in 'admin' user.

    Please enter the admin password you wish to use for XL Deploy Server
    New password:
    Re-type password:

The admin user has by definition all permissions (like 'root' on a unix operating system). The admin user is also used to connect to the JCR repository. If you connect to an existing repository, make sure you enter the password that has been used before to connect to the repository, otherwise the XL Deploy Server will not be able to connect. To change the admin password of an initialized repository, use the method described below in [Changing the admin password](#changing-the-admin-password).

If you give an empty password (hitting enter twice), the default password 'admin' will be used.

Then, the Setup Wizard will ask:

    Do you want XL Deploy to initialize the JCR repository?
    Options are yes or no.
    [yes]:

Answer **yes** (or press Enter) if you want the XL Deploy repository to be recreated. The Setup Wizard must have write access to the repository directory. Answer **no** to leave the repository intact. This option is useful if you already have an existing repository that you want to reuse.

If you answer **yes**, the Setup Wizard will ask the following questions to help you configure your repository:

    The password encryption key protects the passwords stored in the repository.
    Do you want to generate a new password encryption key?
    Options are yes or no.
    [yes]:

XL Deploy ships with a default encryption key that matches the encryption key used in earlier versions of XL Deploy. By answering **no**, you agree to use either the XL Deploy-provided key or any key you previously generated. Answer **yes** to generate a new key. When you do this, you have the option of locking the keystore with a password as well:

    The password encryption key is optionally secured by a password.
    Please enter the password you wish to use. (Use an empty password to avoid a password prompt when starting XL Deploy.)
    New password:
    Re-type password:

If you want to secure the keystore with a password, enter the password here. You will need to provide this password to XL Deploy when it starts. There are three ways you can provide the password: interactively via a prompt, non-interactively via a command line parameter, and non-interactively via the configuration file. If you don't want to use a password for the keystore, press enter.

See **Finishing the setup process** to complete the setup process.

**Warning**: if you choose to recreate the XL Deploy repository and you have installed XL Deploy in the same location before, any information stored in the repository will be lost.

##### Manual setup

The manual setup procedure contains the following steps:

**Admin password**

First, the Setup Wizard will ask you to provide a password for the built-in 'admin' user.

    Please enter the admin password you wish to use for XL Deploy Server
    New password:
    Re-type password:

The admin user has by definition all permissions (like 'root' on a unix operating system). The admin user is also used to connect to the JCR repository. If you connect to an existing repository, make sure you enter the password that has been used before to connect to the repository, otherwise the XL Deploy Server will not be able to connect. To change the admin password of an initialized repository, use the method described in [Changing the admin password](#changing-the-admin-password).

If you give an empty password (pressing ENTER twice), the default password 'admin' will be used.

**Secure communication configuration**

The Setup Wizard will show the following message:

    Would you like to enable SSL?
    Options are yes or no.
    [yes]:

Answer **no** to use unsecured communication between the GUI and the server. Continue with the **Http configuration** section.

Answer **yes** (or press ENTER) if you want to use a secure connection from the GUI to the server.

If you answer **yes**, the Setup Wizard will ask the following question to help you configure secure communication:

    Would you like XL Deploy to generate a keystore with a self-signed
    certificate for you?
    N.B.: Self-signed certificates do not work correctly with some versions
    of the Flash Player and some browsers!
    Options are yes or no.
    [yes]:

Answer **yes** (or press ENTER) if you want the Setup Wizard to generate a digital certificate automatically. The digital certificate is required to secure communication and is normally signed by a Certificate Authority (CA). The Setup Wizard can generate a _self-signed_ certificate if there is no official certificate available. Continue with the **Http configuration** section.

**Note:** For security reasons, using a self-signed certificate is not recommended in a production environment. A self-signed certificate may trigger security warnings in some Flash players and browsers. This certificate can only be used when you access the XL Deploy server at `https://localhost:4516`.

Answer **no** if you want to use your own keystore. XL Deploy uses the built-in Jetty webserver to communicate with the GUI. Jetty requires a certificate with the name `jetty` to be present in the keystore.

The Setup Wizard prompts you for the following keystore information:

    What is the path to the keystore?
    []:

    What is the password to the keystore?
    []:

    What is the password to the key in the keystore?
    []:

Enter the filesystem location of the keystore (for example, `mykeystore.jks`), the password to unlock the keystore and the password for the `Jetty` certificate in the keystore.

**HTTP configuration**

The Setup Wizard shows the following questions:

    What http bind address would you like the server to listen on?
    [localhost]:

    What http port number would you like the server to listen on?
    [4516]:

    Enter the web context root where XL Deploy will run
    [/]:

**Note**: if you chose to enable secure communication, the default port will be _4517_ instead of _4516_.

Enter the host name, port number and context root that the XL Deploy server listens on for connections.

**Thread configuration**

The Setup Wizard shows the following questions:

    Enter the minimum number of threads the HTTP server should use (recommended:
        3 per client, so 3 for single user usage)
    [3]:

Enter the minimum number of threads that the XL Deploy server uses to handle incoming connections. The recommended minimum number of threads is 3 per XL Deploy application client.

    Enter the maximum number of threads the HTTP server should use (recommended :
        3 per client, so 24 for 8 concurrent users)
    [24]:

Enter the maximum number of threads that the XL Deploy server uses to handle incoming connections. The recommended maximum number of threads is 3 per XL Deploy application client.

**Repository configuration**

The Setup Wizard shows the following questions:

    Where would you like XL Deploy to store the JCR repository?
    [repository]:

Enter the filesystem path to a directory where XL Deploy will create the repository. If the directory does not exist, the Setup Wizard will create it.

    Do you want XL Deploy to initialize the JCR repository?
    Options are yes or no.
    [yes]:

Answer **no** to leave the repository intact.

Answer **yes** (or press ENTER) if you want the XL Deploy repository to be recreated. The Setup Wizard must have write access to the repository directory. The Setup Wizard will ask the following questions to help you configure your repository:

    The password encryption key protects the passwords stored in the repository.
    Do you want to generate a new password encryption key?
    Options are yes or no.
    [yes]:

XL Deploy ships with a default encryption key that matches the encryption key used in earlier versions of XL Deploy. By answering **no**, you agree to use either the XL Deploy-provided key or any key you previously generated. Answer **yes** to generate a new key. When you do this, you have the option of locking the keystore with a password as well:

    The password encryption key is optionally secured by a password.
    Please enter the password you wish to use. (Use an empty password to avoid a password prompt when starting XL Deploy.)
    New password:
    Re-type password:

If you want to secure the keystore with a password, enter the password here. You will need to provide this password to XL Deploy when it starts, either interactively via a prompt or non-interactively via a command line parameter or using the configuration file. If you don't want to use a password for the keystore, press ENTER.

**Warning**: if you choose to recreate the XL Deploy repository and you have installed XL Deploy in the same location before, any information stored in the repository will be lost.

**Importable packages configuration**

The Setup Wizard shows the following question:

    Where would you like XL Deploy to import packages from?
    [importablePackages]:

Enter the filesystem path to a directory from which XL Deploy will import packages. The Setup Wizard assumes that this directory exists once the XL Deploy server starts and will not create it.

##### Finishing the setup process

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

Answer **yes** or press ENTER to store the configuration settings and end the Setup Wizard. If you selected the option to initialize the repository, this will be done now.

Answer **no** to abort the Setup Wizard.

If the Setup Wizard is successfully completed, it will display the following message:

    You can now start your XL Deploy server by executing the command server.cmd
        on Windows or server.sh on Unix.
    Note: If your XL Deploy server is running please restart it.
    Finished setup.

#### Changing the admin password

XL Deploy has a special user with administrative permissions, the `admin` user.
The admin password can be set in the setup wizard when installing XL Deploy.

To change the admin password, the following procedure has to be followed. Make sure the XL Deploy Server is running. Then start the CLI as the admin user (or another user with admin rights) and issue the following commands:

    adminUser = security.readUser('admin')
    adminUser.password = 'newpassword'
    security.modifyUser(adminUser)

At this point, the XL Deploy Server must be stopped. Once this has happened, set the new admin password in the `deployit.conf` file in the `conf` directory of the server installation directory.

Restart XL Deploy and test the new credentials in the CLI using the following command:

    security.login('admin', 'newpassword')

#### Changing the encryption key password

The passwords in the repository are encrypted with a secret key. This password encryption key is stored in a keystore file called `conf/repository-keystore.jceks`. This keystore file is optionally protected with a password. If a password is set, you need to enter the password when the XL Deploy Server starts up.

To change the keystore password, you can use the `keytool` utility that is part of the Java JDK distribution. Usage:

    keytool -storepasswd -keystore conf/repository-keystore.jceks -storetype jceks

There is one restriction: keytool refuses to read or set passwords that are shorter than 6 characters. If you want to change a keystore with an empty or short password, download the third-party application **KeyStore Explorer**.

**Note:** `repository-keystore.jceks` is one of the two keystore concepts in XL Deploy. This keystore only contains the key used for encryption of passwords in the repository. If you use HTTPS, XL Deploy will use a second keystore file to store the (self-signed) certificate.

### High availability setup

XL Deploy can be configured to ensure maximum uptime of the application. In such a high availability setup, two instances of XL Deploy are running in an _active/passive_ configuration. At any one time, only one XL Deploy instance is active but as soon as a failure is detected, the passive XL Deploy instance is activated and the failed instance is taken down for repair.

To configure XL Deploy for high availability, the XL Deploy repository must be used in _clustering_ mode. This means that each XL Deploy node writes changes to a shared journal in addition to applying the change to it's own repository. See the section **Configuring the Repository** below for more information on setting up clustering.

## Configuring XL Deploy

This section contains information on the configuration of the XL Deploy server.

**Note:** If you modify any file in the `conf` directory, `ext/synthetic.xml` or `ext/xl-rules`, then you must restart the
XL Deploy server in order for the changes to take effect. For `ext/xl-rules` you can change the default behaviour by
enabling automatic reloading in `conf/planner.conf` file.

### Configuring security

See the [Security Manual](securitymanual.html) for more information about how to configure security.

#### Configuring LDAP security

By default, XL Deploy authenticates users and retrieves authorization information from its repository. XL Deploy can also be configured to use an LDAP repository to authenticate users and to retrieve role (group) membership. In this scenario, the LDAP users and groups are used as principals in XL Deploy and can be mapped to XL Deploy roles. Role membership and rights assigned to roles are always stored in the JCR repository.

XL Deploy treats the LDAP repository as **read-only**. This means that XL Deploy will use the information from the LDAP repository, but can not make changes to that information.

To configure XL Deploy to use an LDAP repository, the security configuration file `deployit-security.xml` must be modified. For a step-by-step procedure, refer to [How to connect to your LDAP or Active Directory](http://docs.xebialabs.com/general/connect_ldap_or_active_directory.html).

##### Sample XL Deploy security file

This is an example of a working `deployit-security.xml` file that uses LDAP:

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:security="http://www.springframework.org/schema/security"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
            http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-3.1.xsd
        ">

        <bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource">
            <constructor-arg value="LDAP_SERVER_URL" />
            <property name="userDn" value="MANAGER_DN" />
            <property name="password" value="MANAGER_PASSWORD" />
            <property name="baseEnvironmentProperties">
                <map>
                    <entry key="java.naming.referral">
                        <value>ignore</value>
                    </entry>
                </map>
            </property>
        </bean>

        <bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider">
            <constructor-arg>
                <bean class="org.springframework.security.ldap.authentication.BindAuthenticator">
                    <constructor-arg ref="ldapServer" />
                    <property name="userSearch">
                        <bean id="userSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch">
                            <constructor-arg index="0" value="USER_SEARCH_BASE" />
                            <constructor-arg index="1" value="USER_SEARCH_FILTER" />
                            <constructor-arg index="2" ref="ldapServer" />
                        </bean>
                    </property>
                </bean>
            </constructor-arg>
            <constructor-arg>
                <bean class="org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator">
                    <constructor-arg ref="ldapServer" />
                    <constructor-arg value="GROUP_SEARCH_BASE" />
                    <property name="groupSearchFilter" value="GROUP_SEARCH_FILTER" />
                    <property name="rolePrefix" value="" />
                    <property name="searchSubtree" value="true" />
                    <property name="convertToUpperCase" value="false" />
                </bean>
            </constructor-arg>
        </bean>

        <bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/>

        <bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider"/>

        <security:authentication-manager alias="authenticationManager">
            <security:authentication-provider ref="rememberMeAuthenticationProvider" />
            <security:authentication-provider ref="ldapProvider" />
            <security:authentication-provider ref="jcrAuthenticationProvider"/>
        </security:authentication-manager>

    </beans>

The XML fragment above contains placeholders for the following values:

* `LDAP_SERVER_URL`: The LDAP url to connect to (example: `"ldap://localhost:389/"`)
* `MANAGER_DN`: The principal to perform the initial bind to the LDAP server (example: `"cn=admin,dc=example,dc=com"`)
* `MANAGER_PASSWORD`: The credentials to perform the initial bind to the LDAP server. (example: "secret")
* `USER_SEARCH_FILTER`: The LDAP filter to determine the LDAP dn for the user that's logging in, `{0}` will be replaced with the username that is logging in (example: `"(&(uid={0})(objectClass=inetOrgPerson))"`)
* `USER_SEARCH_BASE`: The LDAP filter that is the base for searching for users (example: `"dc=example,dc=com"`)
* `GROUP_SEARCH_FILTER`: The LDAP filter to determine the group memberships for the user, `{0}` will be replaced with the DN of the user (example: `"(memberUid={0})"`)
* `GROUP_SEARCH_BASE`: The LDAP filter that is the base for searching for groups (example: `"ou=groups,dc=example,dc=com"`)

#### Assigning a default role to all authenticated users

When your LDAP is not setup to contain a group that all XL Deploy users are assigned to, or you want to use such a group in the default `JcrAuthenticationProvider`, it is possible to configure this in your `deployit-security.xml` file. The following snippet will setup an 'everyone' group (The name is arbitrary, choose a different one if you wish) that is assigned to each user who is authenticated. You could then link this group up to an XL Deploy role, and assign 'login' privileges to it for instance.

    <beans>
        ...

        <bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider">
            <constructor-arg>
                ...
            </constructor-arg>

            <property name="authoritiesMapper" ref="additionalAuthoritiesMapper" />
        </bean>

        <bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider">
            <property name="authoritiesMapper" ref="additionalAuthoritiesMapper" />
        </bean>

        <bean id="additionalAuthoritiesMapper" class="com.xebialabs.deployit.security.AdditionalAuthoritiesMapper">
            <property name="additionalAuthorities">
                <list>
                    <value>everyone</value>
                </list>
            </property>
        </bean>

    </beans>

#### Hiding internal server errors

By default XL Deploy will not hide any internal server errors due to incorrect user input. This allows clients to more easily determine what went wrong and report problems with the XL Deploy Support team. However this behaviour can be turned off by editing the `conf/deployit.conf` file in the XL Deploy server directory and edit the following setting:

    hide.internals=true

Enabling this setting will cause the server to return a response such as the following:

	Status Code: 400
	Content-Length: 133
	Server: Jetty(6.1.11)
	Content-Type: text/plain

	An internal error has occurred, please notify your system administrator with the following code: a3bb4df3-1ea1-40c6-a94d-33a922497134

The code shown in the response can be used to track down the problem in the server logging.

### Configuring the repository

XL Deploy uses a repository to store all of its data such as CIs, deployment packages, logging, etc. XL Deploy can use the filesystem or a database for binary artifacts (deployment packages) and CIs and CI history.

Out of the box, XL Deploy uses the filesystem to store all data in the repository.

#### Location of the repository

By default, the repository is located in `XLDEPLOY_SERVER_HOME/repository`. To change the location, change the value of `jcr.repository.path` in `XLDEPLOY_SERVER_HOME/conf/deployit.conf`. For example:

      jcr.repository.path=file://opt/xldeploy/repository

#### Location of the work directory

By default, the XL Deploy work directory is located at `XLDEPLOY_SERVER_HOME/work`. To change the location, change the value of `recovery-dir` in `XLDEPLOY_SERVER_HOME/conf/tasker.conf`.

#### Using a database

XL Deploy can also use a database to store its repository. The built-in Jackrabbit JCR implementation must be configured to make this possible.

There are several configuration options when setting up a database repository:

* Store **only binary artifacts** in a database. This requires configuring the `DataStore` property.
* Store **only CIs and CI history** in a database. This requires configuring the `PersistenceManager` and `FileSystem` properties.
* Store **all data** (binary artifacts and CIs and CI history) in a database. This requires configuring the `DataStore`, `PersistenceManager` and `FileSystem` must be configured.

Here are some examples of configuring XL Deploy to use a database for various database vendors. The XML snippets below must be put into the `conf/jackrabbit-repository.xml` file.

**Note:** XL Deploy **must** initialize the repository before it can be used. Run XL Deploy's setup wizard and initialize the repository after making any changes to the repository configuration.

For more information about using a database with Jackrabbit, see the [PersistenceManager FAQ](http://wiki.apache.org/jackrabbit/PersistenceManagerFAQ) and [DataStore FAQ](http://wiki.apache.org/jackrabbit/DataStore).

##### Using XL Deploy with [MySQL](http://www.mysql.com/)

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
        <param name="driver" value="com.mysql.jdbc.Driver"/>
        <param name="url" value="jdbc:mysql://localhost:3306/deployit"/>
        <param name="databaseType" value="mysql"/>
        <param name="user" value="deployit" />
        <param name="password" value="deployit" />
    </DataStore>

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://localhost:3306/deployit"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
            <param name="schema" value="mysql" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://localhost:3306/deployit" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
        </PersistenceManager>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${wsp.home}/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>

    </Workspace>

    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://localhost:3306/deployit"/>
            <param name="schemaObjectPrefix" value="version_" />
            <param name="schema" value="mysql" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
            <param name="url" value="jdbc:mysql://localhost:3306/deployit" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

Note: The MySQL database is not suited for storage of large binary objects. See [the MySQL bug tracker](http://bugs.mysql.com/bug.php?id=10859).

##### Using XL Deploy with [DB2](http://www-01.ibm.com/software/data/db2/)

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/deployit"/>
            <param name="databaseType" value="db2"/>
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
    </DataStore>

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
                <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
                <param name="url" value="jdbc:db2://localhost:50002/deployit"/>
                <param name="schemaObjectPrefix" value="${wsp.name}_" />
                <param name="schema" value="db2" />
                <param name="user" value="deployit" />
                <param name="password" value="deployit" />
        </FileSystem>

         <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.BundleDbPersistenceManager">
                <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
                <param name="url" value="jdbc:db2://localhost:50002/deployit" />
                <param name="user" value="deployit" />
                <param name="password" value="deployit" />
                <param name="databaseType" value="db2" />
                <param name="schemaObjectPrefix" value="${wsp.name}_" />
             </PersistenceManager>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${wsp.home}/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>
    </Workspace>

    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/deployit"/>
            <param name="schemaObjectPrefix" value="version_" />
            <param name="schema" value="db2" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.BundleDbPersistenceManager">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/deployit" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="databaseType" value="db2" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

##### Using XL Deploy with [Oracle](http://www.oracle.com/us/products/database/index.html)

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
        <param name="driver" value="oracle.jdbc.OracleDriver"/>
        <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
        <param name="databaseType" value="oracle"/>
        <param name="user" value="deployit" />
        <param name="password" value="deployit" />
    </DataStore>

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.OracleFileSystem">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_"/>
            <param name="schema" value="oracle" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager
            class="org.apache.jackrabbit.core.persistence.bundle.OraclePersistenceManager">
            <param name="driver" value="oracle.jdbc.driver.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="databaseType" value="oracle" />
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
        </PersistenceManager>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${wsp.home}/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>
    </Workspace>

    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.OracleFileSystem">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="schemaObjectPrefix" value="version_"/>
            <param name="schema" value="oracle" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager
            class="org.apache.jackrabbit.core.persistence.bundle.OraclePersistenceManager">
            <param name="driver" value="oracle.jdbc.driver.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="databaseType" value="oracle" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

If you use the TNSNames Alias syntax to connect to Oracle, you may need to inform the driver where to find the TNSNAMES file. See the Oracle documentation for more information.

#### Clustering

It is also possible to run XL Deploy server with its repository shared with other XL Deploy server instances. For this to happen, the jackrabbit JCR must be configured to run in a [clustered mode](http://wiki.apache.org/jackrabbit/Clustering#Overview). This needs a cluster configuration to be present in the `jackrabbit-repository.xml` file.

##### File-based repository

Add the following snippet to the `jackrabbit-repository.xml` to enable clustering:

    <Cluster id="node1">
      <Journal class="org.apache.jackrabbit.core.journal.FileJournal">
        <param name="revision" value="${rep.home}/revision.log" />
        <param name="directory" value="/nfs/myserver/myjournal" />
      </Journal>
    </Cluster>

In the above example, the `directory` property refers to the shared journal. Both XL Deploy instances must be able to write to the same journal.

##### Database repository

The following XML snippet shows a sample clustering configuration for a JCR using Oracle as its repository.

    <Cluster id="101" syncDelay="2000">
        <Journal class="org.apache.jackrabbit.core.journal.OracleDatabaseJournal">
            <param name="revision" value="${rep.home}/revision" />
            <param name="driver" value="oracle.jdbc.driver.OracleDriver" />
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="schemaObjectPrefix" value="JOURNAL_" />
        </Journal>
    </Cluster>

Note that each Jackrabbit cluster node should have a unique value for `id`. For more information on JCR clustering, or ways to configure clustering using other databases, please refer to the Jackrabbit [clustering documentation](http://wiki.apache.org/jackrabbit/Clustering#Overview).

### Installing and uninstalling plugins

XL Deploy Server supports various plugins that add functionality to the system. When it starts, the XL Deploy server scans the `plugins` directory and loads all plugins it finds. The additional functionality they provide is immediately available. Any plugins added or removed when XL Deploy server is running will not take effect until the server is restarted.

To install a new plugin, stop the XL Deploy server and copy the plugin JAR archive into the `plugins` directory, then restart the XL Deploy server.

To uninstall a plugin, stop the XL Deploy server and remove the plugin JAR archive from the `plugins` directory, then restart the XL Deploy server.

### Editing XL Deploy properties

XL Deploy automatically generates the `<XLDEPLOY_HOME>/conf/deployit-defaults.properties` file and populates it with the default values provided by all installed plugins.

Lines in the file that start with a number sign (`#`) are provided for reference; XL Deploy ignores these lines. To change the value of a property, remove the number sign from that line and set the property to the desired value.

### Advanced configuration

A number of advanced configuration options are available by editing the `deployit.conf` file in the `conf` directory. Restart the server after making the necessary changes.

* **taskThreadPool.corePoolSize:** The minimum number of threads allocated to execute tasks. Default value: 10.
* **taskThreadPool.maxPoolSize:** The maximum number of threads allocated to execute tasks. Default value: 2147483647.
* **taskThreadPool.keepAliveSeconds:** The number of seconds a time is kept alive before destroying it if the number of allocated threads exceeds.
* **taskThreadPool.queueCapacity:** The capacity of the queue that holds tasks to be executed if no threads are available. Default value: 2147483647.

### Client security configuration

A number of client security configuration options are available by editing the `deployit.conf` file in the `conf` directory. Restart the server after making the necessary changes.

* **client.session.timeout.minutes:** The number of minutes it takes before the session of the user is locked when he is not using the XL Deploy GUI. The default value of '0' means that no time-out is configured.
* **client.session.remember.enabled:** Setting this value to false disables the "Keep me logged in" option on the login screen. Default value: true.

## Logging

### Configuring logging

Out of the box, XL Deploy server writes informational, warning and error log messages to standard output as well as `log/deployit.log` when running. In addition, XL Deploy writes an audit trail to the file `log/audit.log`. It is possible to change XL Deploys logging behavior (for instance to write log output to a file or to log output from a specific source). Aside from these application logs a HTTP log is written to `log/access.log`.

XL Deploy uses the Logback logging framework for logging. To change its behavior, edit the file `logback.xml` in the `conf` directory of the XL Deploy server installation directory.

The following is an example `logback.xml` file:

    <configuration>
        <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <!-- encoders are assigned the type
                 ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
            <encoder>
                  <pattern>
                    %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
                </pattern>
            </encoder>
        </appender>

        <!-- Create a file appender that writes log messages to a file -->
        <appender name="FILE" class="ch.qos.logback.core.FileAppender">
            <layout class="ch.qos.logback.classic.PatternLayout">
                <pattern>%-4relative [%thread] %-5level %class - %msg%n</pattern>
            </layout>
               <File>log/my.log</File>
        </appender>

        <!-- Set logging of classes in com.xebialabs to DEBUG level -->
        <logger name="com.xebialabs" level="debug"/>

        <!-- Set logging of class HttpClient to DEBUG level -->
        <logger name="HttpClient" level="debug"/>

        <!-- Set the logging of all other classes to INFO -->
        <root level="info">
            <!-- Write logging to STDOUT and FILE appenders -->
            <appender-ref ref="STDOUT" />
            <appender-ref ref="FILE" />
        </root>

    </configuration>

For more information see the [Logback website](http://logback.qos.ch/).

### HTTP access log ###

The HTTP access log is configured with the `conf/logback-access.xml` configuration file. The format is slightly different from the `logback.xml` format. Per default the access log is done in the so called combined format, but you can fully customize it. The log file is rolled per day on the first log statement in the new day.

    <configuration>
      <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>log/access.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
          <fileNamePattern>access.%d{yyyy-MM-dd}.log.zip</fileNamePattern>
        </rollingPolicy>

        <encoder>
          <pattern>%h %l %u [%t] "%r" %s %b "%i{Referer}" "%i{User-Agent}"</pattern>
        </encoder>
      </appender>

      <appender-ref ref="FILE" />
    </configuration>

For additional details on the configuration and possible patterns see:

* [http://logback.qos.ch/access.html](http://logback.qos.ch/access.html)
* [http://logback.qos.ch/manual/layouts.html#AccessPatternLayout](http://logback.qos.ch/manual/layouts.html#AccessPatternLayout)

To disable the access log create a `logback-access.xml` with an empty `configuration` element:

    <configuration>
    </configuration>

### Audit log ###

XL Deploy writes an audit log for each human-initiated event on the server. For each event, the following information is recorded:

* the user making the request
* the event timestamp
* the component producing the event
* an informational message describing the event

For events involving CIs, the CI data submitted as part of the event is logged in XML format.

These are some of the events that are logged in the audit trail:

* the system is started or stopped
* a user logs into or out of the system
* an application is imported
* a CI is created, updated, moved or deleted
* a security role is created, updated or deleted
* a task (deployment, undeployment, control task or discovery) is started, cancelled or aborted

By default, the audit log is stored in `log/audit.log`. The audit log is rolled over daily.

### Script log ###

The default logging configuration file also contains a (commented out) section that enables logging of all XL Deploy scripts to a separate log file.

## Starting XL Deploy

Open a terminal window and change to the XLDEPLOY_SERVER_HOME directory. Start the XL Deploy server with the command:

* On Unix: `bin/server.sh`
* On Windows: `bin/server.cmd`

Start the server with the `-h` flag to see the options it supports:

        java -cp deployit-server-<version>.jar [options...] com.xebialabs.deployit.XL Deploy arguments...
         -help                  : Prints this usage message
         -repository-keystore-password VAL : The password to open the repository keystore file, if not given,
                                  XL Deploy will prompt you.
         -reinitialize          : Reinitialize the repository, only useful with -setup
         -setup                 : (Re-)run the setup for XL Deploy
         -setup-defaults VAL    : Use the given file for defaults during setup server.sh arguments...

The command line options are:

* `-repository-keystore-password VAL` tells XL Deploy which password to use to access the repository keystore. As an alternative, this password can be specified in the `deployit.conf` configuration file using `repository.keystore.password` as a key. If not specified and the repository keystore does require a password, XL Deploy will prompt you for it.
* `-reinitialize` tells XL Deploy to reinitialize the repository. Used only in conjunction with `-setup`. **Note:** This flag only works if XL Deploy is running on the filesystem repository, not when you've configured XL Deploy to run against a database.
* `-setup` runs the XL Deploy Setup Wizard.
* `-setup-defaults VAL` specifies a file that contains default values for configuration properties set in the Setup Wizard.

### Server options

Any options you want to give the XL Deploy server when it starts can be specified in the `XLDEPLOY\_SERVER\_OPTS` environment variable.

### Starting XL Deploy in the background

By running the `server.sh` or `server.cmd` command, the XL Deploy server is started in the foreground. To run the server as a background process:

* On Unix, use `nohup bin/server.sh &`
* On Windows, run XL Deploy as a [service](http://support.xebialabs.com/entries/20022143-install-deployit-as-a-windows-service)

## Shutting down XL Deploy

You can stop the XL Deploy server using a REST API call. This is an example of a command to generate such a call (replace `admin:admin` with your own credentials):

    curl -X POST --basic -u admin:admin
        http://admin:admin@localhost:4516/deployit/server/shutdown

This requires the external `curl` command, available for both Unix and Windows.

If you have administrative permissions, you can shut down the server using the command-line interface (CLI) command:

    deployit.shutdown()

### Lock files left by unclean shutdown

If the server is not shut down cleanly, the following lock files may be left on the server:

* `XLDEPLOY_HOME/repository/.lock` (ensure that XL Deploy is not running before removing this file)
* `XLDEPLOY_HOME/repository/index/write.lock`
* `XLDEPLOY_HOME/repository/workspaces/default/write.lock`
* `XLDEPLOY_HOME/repository/workspaces/security/write.lock` (server start-up will be slower after this file is removed because the indexes must be rebuilt)
* `XLDEPLOY_HOME/repository/version/db/db.lck`
* `XLDEPLOY_HOME/repository/version/db/dbex.lck`
* `XLDEPLOY_HOME/repository/workspaces/default/db/db.lck`
* `XLDEPLOY_HOME/repository/workspaces/default/db/dbex.lck`
* `XLDEPLOY_HOME/repository/workspaces/security/db/db.lck`
* `XLDEPLOY_HOME/repository/workspaces/security/db/dbex.lck`

## Maintaining XL Deploy

This section describes how to maintain the XL Deploy server in your environment.

### Creating backups

To create a backup of XL Deploy, several components may need to be backed up depending on your configuration:

* **Repository**.
    * Built-in repository: Create a backup of the built-in JCR repository by backing up the files in the `repository` directory.
    * Database repository: Create a backup of both the files in the `repository` directory, as well as the database (using the tools provided by your database vendor).
* **Configuration**. Create a backup of the XL Deploy configuration by backing up the files in the `conf` directory in the installation directory.
* **Customization**. Create a backup of the XL Deploy customizations by backing up the files in the `ext` and `plugins` directory in the installation directory.

**Note:** XL Deploy **must not** be running when you are making a backup. Schedule backups outside planned deployment hours to ensure the server is not being used.

### Restoring backups

To restore a backup of XL Deploy, restore one of the following components:

* **JCR repository**.
    * Built-in repository: Remove the `repository` directory and replace it with the backup.
    * Database repository: Remove the `repository` directory and replace it with the backup. Next, restore a backup of the database using the tools provided by your vendor.
* **Configuration**. Remove the `conf` directory in the XLDEPLOY_SERVER_HOME directory and replace it with the backup.
* **Customization**. Remove the `ext` and `plugins` directories in the XLDEPLOY_SERVER_HOME directory and replace them with the backups.

**Note:** XL Deploy **must not** be running when you are restoring a backup.

### Freeing up disk space

The repository is the place where XL Deploy stores all of its data. If you deal with lots of large
binary artifacts, this can be problematic since the disk space they consume is not reclaimed when the are deleted. To reclaim the disk space after
deleting a CI, use the following CLI snippet:

        deployit.runGarbageCollector()

### Updating the digital certificate

The XL Deploy [Setup Wizard](#running-the-server-setup-wizard) can generate a self-signed digital certificate for secured communications. This can cause issues in situations where XL Deploy needs to be accessed via a URL other than `https://localhost:4516`, because the Common Name in the certificate is `localhost`.

To view the certificate, use the `keytool` utility (part of the Java JDK distribution) on the XL Deploy server:

      keytool -list -keystore conf/keystore.jks -v

Sample output:

      *****************  WARNING WARNING WARNING  *****************
      * The integrity of the information stored in your keystore  *
      * has NOT been verified!  In order to verify its integrity, *
      * you must provide your keystore password.                  *
      *****************  WARNING WARNING WARNING  *****************

      Keystore type: JKS
      Keystore provider: SUN

      Your keystore contains 1 entry

      Alias name: jetty
      Creation date: Jun 3, 2014
      Entry type: PrivateKeyEntry
      Certificate chain length: 1
      Certificate[1]:
      Owner: CN=localhost, O=XL Deploy Server, C=NL
      Issuer: CN=localhost, O=XL Deploy Server, C=NL
      Serial number: 38e4ab60
      Valid from: Tue Jun 03 11:24:19 CEST 2014 until: Thu Jun 04 11:24:19 CEST 2015
      Certificate fingerprints:
           MD5:  04:C1:91:34:70:FA:CD:16:DA:FA:F0:E3:1B:AC:81:9B
           SHA1: AA:D2:54:0E:04:8A:56:51:80:74:6B:9C:B9:F1:6D:7F:2F:F9:88:0F
           SHA256: 5E:80:50:86:B8:C3:73:66:44:36:E2:AA:54:25:B4:F3:2B:DF:CC:78:31:0D:24:E5:8A:64:C9:10:A2:17:BB:AB
           Signature algorithm name: SHA256withRSA
           Version: 3

      Extensions:

      #1: ObjectId: 2.5.29.14 Criticality=false
      SubjectKeyIdentifier [
      KeyIdentifier [
      0000: 5D 37 E4 76 6E 59 C9 59   28 A3 DF FF 01 92 70 3E  ]7.vnY.Y(.....p>
      0010: 0B 04 B0 5F                                        ..._
      ]
      ]



      *******************************************
      *******************************************

Note that the alias name is `jetty`. XL Deploy looks up the certificate using this key.

If you need to update the digital certificate:

1. Move the current `conf/keystore.jks` file to a different location.
2. Use `keytool` in the XLDEPLOY_HOME directory:

        keytool -genkey -keyalg RSA -alias jetty -keystore conf/keystore.jks -validity 360 -keysize 2048

3. Choose a keystore password.
4. For the first and last name, enter the host name that you want to use to access XL Deploy. This is a sample of the output:

        Enter keystore password:
        Re-enter new password:
        What is your first and last name?

        What is the name of your organizational unit?
          [Unknown]:
        What is the name of your organization?
          [Unknown]:
        What is the name of your City or Locality?
          [Unknown]:
        What is the name of your State or Province?
          [Unknown]:
        What is the two-letter country code for this unit?
          [Unknown]:
        Is CN=yourservername.yourdomain.com, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown correct?


        Enter key password for <jetty>
            (RETURN if same as keystore password):

5. Update the following settings in `conf/deployit.conf`:

        keystore.password=yourpassword
        keystore.keypassword=yourpassword

**Tip:** If you require a more complex digital certificate, generate it with [OpenSSL](https://www.openssl.org/) and import it using `keytool` with the alias `jetty`.
