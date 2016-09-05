---
title: Configure XL Release to trust an XL Deploy server with a self-signed certificate
---

If you configured your XL Deploy server to use a [self-signed certificate](/xl-deploy/how-to/install-xl-deploy.html#step-3-generate-a-self-signed-certificate) and then [added the server to XL Release](/xl-release/how-to/configure-xl-deploy-servers-in-xl-release.html), you will notice that testing the connection fails with the error `The XL Deploy server is not available`.

To instruct XL Release to trust the XL Deploy server's certificate, you need to configure a truststore for XL Release. Usually, you do not want to modify the JRE's global truststore for this purpose. This topic describes how to create a dedicated truststore for XL Release.

## Step 1 Export the server certificate

Export the self-signed certificate from `XL_DEPLOY_SERVER_HOME/conf`:

    keytool -export -keystore keystore.jks -alias jetty -file XLDeployServerCert.cer

For more information about the `keytool` utility, refer to the [Oracle documentation](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html).

## Step 2 Import the certificate as a trusted certificate

Import the certificate as a trusted certificate into a separate truststore for XL Release, so you don't have to modify the JRE's global truststore:

    keytool -import -alias XLDeployServerCert -file XLDeployServerCert.cer -keystore XLRTruststore.jks

## Step 3 Move the truststore to the XL Release installation

Move `XLRTruststore.jks` from `XL_DEPLOY_SERVER_HOME/conf` to `XL_RELEASE_SERVER_HOME/conf`.

## Step 4 Configure XL Release to use the truststore

Add the following lines in `XL_RELEASE_SERVER_HOME/conf/xlr-wrapper-linux.conf` (for Unix) or `XL_RELEASE_SERVER_HOME/conf/xlr-wrapper-win.conf` (for Microsoft Windows):

    wrapper.java.additional.X=-Djavax.net.ssl.trustStore=conf/myXLRTruststore.jks
    wrapper.java.additional.X+1=-Djavax.net.ssl.trustStorePassword=password

Where X is the next number in the `wrapper.java.additional` list.

## Step 5 Start XL Release

You can now [start XL Release](/xl-release/how-to/start-xl-release.html) and [add the XL Deploy server](/xl-release/how-to/configure-xl-deploy-servers-in-xl-release.html).
