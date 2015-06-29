---
layout: beta
title: Configure LDAP authentication for XL TestView
categories:
- xl-testview
subject:
- System administration
tags:
- system administration
- installation
- ldap
- authentication
---

XL TestView supports authentication of users using LDAP. This topic describes how to configure LDAP authentication on XL TestView. Some general knowledge about LDAP and your LDAP server in particular is required.

## Unsecure LDAP

Update the following properties in the `xl-testview.conf` file:

* Set the `xlt.authentication.method` property to `ldap`.
* Set the `xlt.authentication.ldap.url` property to the complete URL of your LDAP server including the port number; for example, `ldap://server.domain:389`
* Set the `xlt.authentication.ldap.user-dn` property to a distinguished name template that identifies users; for example, `cn={0},ou=people,dc=xebialabs,dc=com`, where `{0}` will be replaced by the user name. Identifing users by other properties is not yet supported.

Then restart XL TestView and try to log in.

## Secure LDAP

If your LDAP uses a certificate signed by a certificate authority or a certificate that is trusted in the global Java trust store, making a connection should work by setting the secure URL (for example, `ldaps://server.domain:636`) in `xlt.authentication.ldap.url`.

If you use a self-signed certificate and you cannot add it to the global trust store, you need to configure a local keystore. You can do so using the [`keytool`](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html) utility (part of the Java JDK distribution).

**Note:** Ensure you complete the registration process before you configure LDAP. Registration will not work afterward.

To configure a local keystore:

1. Export the certificate of your LDAP server. Please consult the documentation of your LDAP server for instructions.
2. Go to the `conf` directory of your installation. Another location is also allowed.
3. Create a new trust store using the following command: `keytool -import  -alias ldap.example.com -file ldapCertificate.crt -keystore truststore.jks`.
4. Type a keystore password twice.
5. Confirm you trust this account.
6. In the configuration file, set `xlt.truststore.location` as the absolute file location of your trust store you just made.
7. Put the password in `xlt.truststore.password`.
8. Restart XL TestView and try to log in.
