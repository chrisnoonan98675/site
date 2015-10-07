---
layout: beta
title: Introduction to the XL Deploy Liberty profile plugin
categories:
- xl-deploy
subject:
- Liberty profile plugin
tags:
- liberty
- profile
- websphere
- middleware
- plugin
---

The XL Deploy WebSphere Liberty profile server (WLP) plugin adds capability for managing deployments and resources on Liberty profile server. It works out of the box for deploying/upgrading/undeploying application artifacts, features, and resources like datasources, libraries and filesets.

The plugin is implemented using XL Deploy rules and Jython scripts, so you can easily extend it to support more deployment options and management of new artifacts and resources.

For information about Liberty profile requirements and the configuration items (CIs) that the plugin supports, refer to the [Liberty Profile Plugin Reference](/xl-deploy/latest/wlpPluginManual.html).

## Features

* Applications
	* Web application (WAR)
	* Enterprise application (EAR)
	* Enterprise JavaBean (EJB)
* Security Roles
    * Users
    * Groups
    * Special subject
    * Run As User
* Resources
	* library
	* Fileset
	* Connection Manager
* Datasources
	* GenericDataSource
    * DB2DataSource
    * MicrosoftSQLServerDataSource
    * OracleDataSource
    * SybaseDataSource
* Liberty features
* Restart strategies
	* None
	* Stop Start
* Control Tasks
	* Server status
	* Start/Stop server
	* Create/Delete server
	* Generate plugin configuration
	* Reload configuration

## Setup

The plugin manages the Liberty profile server through the secure Java Management Extensions (JMX) REST connector. To enable the REST connector, include the `restConnector-1.0` feature in the `server.xml` file on the Liberty server. The instructions for configuring REST connector are available at [Configuring secure JMX connection to the Liberty profile](https://www-01.ibm.com/support/knowledgecenter/was_beta_liberty/com.ibm.websphere.wlp.nd.multiplatform.doc/ae/twlp_admin_restconnector.html?cp=was_beta_liberty%2F1-6-1-16-1).

To keep communication confidential, configure SSL certificates on the Liberty profile and on the XL Deploy instance connecting to the server. For information about configuring certificates on the Liberty profile server, refer to [Enabling SSL communication for the Liberty profile](https://www-01.ibm.com/support/knowledgecenter/was_beta_liberty/com.ibm.websphere.wlp.nd.multiplatform.doc/ae/twlp_sec_ssl.html?cp=was_beta_liberty).

You can use the following sample `server.xml` configuration to set up the Liberty profile server with the plugin on an `overthere.LocalHost`. The Liberty profile server location is `/opt/IBM/wlp/usr/servers/defaultServer` and the XL Deploy installation directory is `/opt/xl-deploy-server`.

    <server description="new server">
      <featureManager>
        <feature>restConnector-1.0</feature>
      </featureManager>
        <httpEndpoint host="*" httpPort="9080" httpsPort="9443" id="defaultHttpEndpoint"/>
      <quickStartSecurity userName="wlpadmin" userPassword="wlpadmin" />
      <keyStore id="defaultKeyStore" password="mypass" />
    </server>

### Security

Based on the configuration defined in `server.xml`, the Liberty profile server will automatically generate `key.jks` in the `/opt/IBM/wlp/usr/servers/defaultServer/resources/security` directory. The following commands can be executed to generate trust store which is configured in XL Deploy. The generated `truststore.ts` is copied to `/opt/xl-deploy-server/certs` directory.

    keytool -export -alias default -file mycert.crt -keystore key.jks

    keytool -import -trustcacerts -alias default -file mycert.crt -keystore truststore.ts -storepass mypass -noprompt

### Plugin configuration

The basic plugin configuration is:

![Basic plugin configuration](images/wlp-basic-properties.png)

Sample connector properties:

![Connector Properties](images/wlp-connector-properties.png)

The value of the **Password** property is `wlpadmin`, and the **Trust store password** is `mypass`.

### Defaults

The server can be configured to accept all hosts and certificates by setting the hidden attributes `trustAllHostnames` and `trustAllCertificates` to `true` in `<XLDEPLOY_HOME>/conf/deployit-defaults.properties`:

    # Ignores certificate verification checks, use in development environments only.
    wlp.Server.trustAllCertificates=false
    # Ignores host verification checks, use in development environments only.
    wlp.Server.trustAllHostnames=false

**Note:** These settings should only be used in development environment.

`deployit-defaults.properties` can also be used to define values for `connectTimeout` and `readTimeout` to resolve connection issues.

## Use in deployment packages

The plugin works with the standard deployment package (DAR) format. The following is a sample `deployit-manifest.xml` file that can be used to create a Liberty profile-specific deployment package. It contains declarations for a WAR file (`wlp.WebApplicationSpec`) and a datasource (`wlp.GenericDataSourceSpec`) with the related driver, fileset, library, and connection manager.

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="app">
        <deployables>
            <wlp.WebApplicationSpec name="sampleWeb" file="sampleWeb/sampleWeb-1.0.war">
                <scanPlaceholders>false</scanPlaceholders>
                <location>sampleweb.war</location>
                <contextRoot>sample</contextRoot>
            </wlp.WebApplicationSpec>
            <wlp.GenericDataSourceSpec name="dbDatasource">
                <jndiName>jdbc/test</jndiName>
                <jdbcDriverRef>dbDriver</jdbcDriverRef>
                <connectionManagerRef>dbConnectionManager</connectionManagerRef>
            </wlp.GenericDataSourceSpec>
            <wlp.JdbcDriverSpec name="dbDriver">
                <libraryRef>dbLibrary</libraryRef>
                <xaDataSource>com.postgres.xa.datasource</xaDataSource>
            </wlp.JdbcDriverSpec>
            <wlp.FilesetSpec name="dbFileset">
                <dir>/tmp/postgres.jar</dir>
            </wlp.FilesetSpec>
            <wlp.LibrarySpec name="dbLibrary">
                <filesetRef>dbFileset</filesetRef>
            </wlp.LibrarySpec>
            <wlp.ConnectionManagerSpec name="dbConnectionManager">
                <maxPoolSize>20</maxPoolSize>
                <minPoolSize>10</minPoolSize>
            </wlp.ConnectionManagerSpec>
        </deployables>
    </udm.DeploymentPackage>

## Server container

The `wlp.Server` CI defines an instance of a stand-alone Liberty profile server. Use the **Connector** tab on the server configuration to configure the client for the JMX REST connector, the SSL truststore path, and password. Use the **Reloading** attribute to enable reloading of the server configuration when the `server.xml` file is changed (enabled by default).

Control options such as status, start, and stop are available on the server instance. You can also create or delete a server instance using control tasks.

## Deploying applications

The way an application is deployed to a container can be influenced by modifying properties of the corresponding deployed. The following deployed properties determine how the application is deployed to the container:

* `location`: The location of the WAR file can be absolute or relative. A relative path is resolved with a reference to the apps directory in the server; for example, for the `defaultServer` location, `tmp/sample.war` will be copied to `<installation directory>/usr/servers/defaultServer/apps/tmp/sample.war`.

* `Restart strategy`: This attribute can be set to `NONE` or `STOP_START`. When the `STOP_START` strategy is used, an existing application will be stopped before undeployment or upgrading, and a new application version will be started after an initial deployment or an upgrade.

* `wlp.ApplicationBndSpec`: This is available as an embedded configuration item on an application. It is used to bind general deployment information included in the application to security roles. There are security role types for users, groups, "special subject", and "run as user".

	The following sample `deployit-manifest.xml` file creates a XL Deploy deployment package which deploys a Web application with role bindings to the WebSphere Liberty Profile server instance:
	
	    <?xml version="1.0" encoding="UTF-8"?>
	    <udm.DeploymentPackage version="1.0" application="secure">
	        <deployables>
	            <wlp.WebApplicationSpec name="auth" file="auth/auth-war-1.0.war">
	                <location>secure.war</location>
	                <contextRoot>secure</contextRoot>
	                <applicationBindings>
	                    <wlp.ApplicationBndSpec name="auth/bnd">
	                        <securityRoles>
	                            <wlp.SecurityRoleSpec name="auth/bnd/samplerole">
	                                <roleName>SampleRole</roleName>
	                                <users>
	                                    <wlp.UserRoleSpec name="auth/bnd/samplerole/sampleuser">
	                                        <userName>sampleuser</userName>
	                                        <accessId>sampleuser</accessId>
	                                    </wlp.UserRoleSpec>
	                                </users>
	                                <groups/>
	                                <specialSubjects/>
	                                <runAsUsers/>
	                            </wlp.SecurityRoleSpec>
	                        </securityRoles>
	                    </wlp.ApplicationBndSpec>
	                </applicationBindings>
	            </wlp.WebApplicationSpec>
	        </deployables>
	    </udm.DeploymentPackage>

* `wlp.ClassloaderSpec`: This configuration is used to configure references of shared libraries required by an application.

## Resources

The plugin supports the deployment and undeployment of resources such as fileset, library, connection managers, and datasources.

## Liberty features

Liberty features that enable loading of units of functionality in Liberty profile server runtime can also be installed and uninstalled using the plugin.
