---
title: Configure the CLI to trust an XL Deploy server with a self-signed certificate
---

If you configured your [XL Deploy](http://xebialabs.com/products/xl-deploy) server to use a [self-signed certificate](http://docs.xebialabs.com/releases/latest/xl-deploy/systemadminmanual.html#running-the-server-setup-wizard) (this is fine for development and testing environments, but for production use a properly signed certificate is recommended!), you will notice that trying to connect with a "vanilla" [command-line interface (CLI)](http://docs.xebialabs.com/releases/latest/xl-deploy/climanual.html) configuration will fail:

    C:\...\xl-deploy-4.5.1-cli>bin\cli.cmd -secure
    Username: admin
    Password:
    Exception in thread "main" java.lang.IllegalStateException: Could not contact the server at https://127.0.0.1:4517/deployit
        ...
    Caused by: javax.net.ssl.SSLHandshakeException: sun.security.validator.Validator
    Exception: PKIX path building failed: sun.security.provider.certpath.SunCertPath
    BuilderException: unable to find valid certification path to requested target

In order to get the CLI to trust the server's certificate, you will need to configure a truststore for the CLI. Usually, you don't want to modify the JRE's global truststore for this purpose, so we'll describe here how to create a dedicated truststore for your CLI.

## Exporting the server certificate

Export the server's self-signed certificate from `SERVER_HOME/conf`:

    keytool -export -keystore keystore.jks -alias jetty -file XLDeployServerCert.cer

See [here](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html) for more information on the `keytool` utility.

## Importing the certificate as a trusted cert

Import the certificate as a trusted certificate into a separate truststore for the CLI, so you don't have to mess with the JRE's global truststore:

    keytool -import -alias XLDeployServerCert -file XLDeployServerCert.cer -keystore myCliTruststore.jks

## Moving the truststore to the CLI installation

Move `myCliTruststore.jks` from `SERVER_HOME/conf` to `CLI_HOME/conf`.

## Configuring the CLI to use the truststore

Set the [CLI options](http://docs.xebialabs.com/releases/latest/xl-deploy/climanual.html#environment-variables) (or, equivalently, change `CLI_HOME/bin/cli.sh` or `cli.cmd`) to use the truststore. Use the password specified when creating the truststore in the step above:

    set DEPLOYIT_CLI_OPTS=-Xmx512m -XX:MaxPermSize=256m -Djavax.net.ssl.trustStore=conf/myCliTruststore.jks -Djavax.net.ssl.trustStorePassword=secret

## Starting the CLI

You can now start the CLI. Ensure that the hostname you use is the hostname that is listed in the certificate:

    C:\...\xl-deploy-4.5.1-cli>bin\cli.cmd -secure -host localhost
    Username: admin
    Password:
    Welcome to the XL Deploy Jython CLI!
    Type 'help' to learn about the objects you can use to interact with XL Deploy.

**Note:** If you are creating a new self-signed certificate with a hostname other than `localhost`, use the certificate alias `jetty` when importing it into the keystore, as described [here](http://docs.xebialabs.com/releases/latest/xl-deploy/systemadminmanual.html#updating-the-digital-certificate).
