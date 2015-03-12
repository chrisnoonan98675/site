---
title: Update the XL Deploy digital certificate
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- cli
- repository
- security
---

The XL Deploy setup wizard can generate a self-signed digital certificate for secured communications. This can cause issues in situations where XL Deploy needs to be accessed via a URL other than `https://localhost:4516`, because the Common Name in the certificate is `localhost`.

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
2. Use `keytool` in the `XLDEPLOY_HOME` directory:

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

For more information about SSL and Jetty, refer to the [Jetty documentation](http://www.eclipse.org/jetty/documentation/current/configuring-ssl.html).
