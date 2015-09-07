---
layout: beta
title: Configure LDAP authentication for XL TestView
categories:
- xl-testview
subject:
- Configuration
tags:
- system administration
- installation
- ldap
- authentication
---

XL TestView supports authentication of users using LDAP. This topic describes how to configure LDAP authentication on XL TestView. Some general knowledge about LDAP and your LDAP server in particular is required.

## Configure unsecure LDAP

To configure LDAP, update the following properties in the `xl-testview.conf` file:

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| `xlt.authentication.method` | Set to `ldap` |
| `xlt.authentication.ldap.url` | Set to the complete URL of your LDAP server including the port number; for example, `ldap://server.domain:389` |
| `xlt.authentication.ldap.user-dn` | Set to a distinguished name template that identifies users; for example, `cn={0},ou=people,dc=xebialabs,dc=com`, where `{0}` will be replaced by the user name |

**Note:** Identifying users by properties other than the user name is not currently supported.

After saving the `xl-testview.conf` file, [restart XL TestView](/xl-testview/how-to/start.html) and log in.

## Configure secure LDAP

If your LDAP configuration uses a certificate signed by a certificate authority or a certificate that is trusted in the global Java truststore, you should be able to connect by setting the secure URL (for example, `ldaps://server.domain:636`) in `xlt.authentication.ldap.url`.

If you use a self-signed certificate and you cannot add it to the global truststore, you need to configure a local keystore. You can do so using the [`keytool`](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html) utility (part of the Java JDK distribution).

**Note:** Ensure you complete the registration process before you configure LDAP. Registration will not work afterward.

To configure a local keystore:

1. Export the certificate of your LDAP server. Please consult the documentation of your LDAP server for instructions.
2. Go to the `conf` directory of your installation. Another location is also allowed.
3. Create a new truststore using the following command:

        keytool -import  -alias ldap.example.com -file ldapCertificate.crt -keystore truststore.jks

4. Type a keystore password twice.
5. Confirm you trust this account.
6. In the configuration file, set `xlt.truststore.location` to the absolute file location of the truststore you just created.
7. Set `xlt.truststore.password` to the password.
8. After saving the file, [restart XL TestView](/xl-testview/how-to/start.html) and log in.
