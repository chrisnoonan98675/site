---
title: Configure the CLI to trust an XL Deploy server's certificate
categories:
xl-deploy
subject:
Command-line interface
tags:
system administration
security
cli
weight: 242
---

If you configured your XL Deploy server to use a [self-signed certificate](/xl-deploy/how-to/install-xl-deploy.html#step-3-generate-a-self-signed-certificate), you will notice that trying to connect with a normal command-line interface (CLI) configuration will fail:

    C:\...\xl-deploy-5.5.0-cli>bin\cli.cmd -secure
    Username: admin
    Password:
    Exception in thread "main" java.lang.IllegalStateException: Could not contact the server at https://127.0.0.1:4517/deployit
        ...
    Caused by: javax.net.ssl.SSLHandshakeException: sun.security.validator.Validator
    Exception: PKIX path building failed: sun.security.provider.certpath.SunCertPath
    BuilderException: unable to find valid certification path to requested target

To instruct the CLI to trust the server's certificate, you need to configure a truststore for the CLI. Usually, you do not want to modify the JRE's global truststore for this purpose. This topic describes how to create a dedicated truststore for your CLI.

## Step 1 Export the server certificate

Export the self-signed certificate from `XL_DEPLOY_SERVER_HOME/conf`:

    keytool -export -keystore keystore.jks -alias jetty -file XLDeployServerCert.cer

For more information about the `keytool` utility, refer to the [Oracle documentation](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html).

## Step 2 Import the certificate as a trusted certificate

Import the certificate as a trusted certificate into a separate truststore for the CLI, so you don't have to modify the JRE's global truststore:

    keytool -import -alias XLDeployServerCert -file XLDeployServerCert.cer -keystore myCliTruststore.jks

## Step 3 Move the truststore to the CLI installation

Move `myCliTruststore.jks` from `XL_DEPLOY_SERVER_HOME/conf` to `XL_DEPLOY_CLI_HOME/conf`.

## Step 4 Configure the CLI to use the truststore

Set the [CLI options](/xl-deploy/how-to/install-the-xl-deploy-cli.html#set-environment-variables) (or, equivalently, change `XL_DEPLOY_CLI_HOME/bin/cli.sh` or `cli.cmd`) to use the truststore. Use the password specified when creating the truststore in the step above:

    export DEPLOYIT_CLI_OPTS="-Xmx512m -XX:MaxPermSize=256m -Djavax.net.ssl.trustStore=conf/myCliTruststore.jks -Djavax.net.ssl.trustStorePassword=secret"

## Step 5 Start the CLI

You can now start the CLI. Ensure that the hostname you use is the hostname that is listed in the certificate:

    C:\...\xl-deploy-5.5.0-cli>bin\cli.cmd -secure -host localhost
    Username: admin
    Password:
    Welcome to the XL Deploy Jython CLI!
    Type 'help' to learn about the objects you can use to interact with XL Deploy.

**Note:** If you are creating a new self-signed certificate with a hostname other than `localhost`, use the certificate alias `jetty` when importing it into the keystore, as described in [Update the XL Deploy digital certificate](/xl-deploy/how-to/update-the-xl-deploy-digital-certificate.html).
