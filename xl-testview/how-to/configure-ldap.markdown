---
title: Configure LDAP authentication for XL TestView
categories:
- xl-testview
subject:
- Configuration
tags:
- system administration
- installation
- ldap
- configuration
---

XL TestView supports authentication of users using LDAP. This topic describes how to configure LDAP authentication on XL TestView. Some general knowledge about LDAP and your LDAP server in particular is required.

## Configure unsecure LDAP

To configure LDAP, update the following properties in the `<XLTESTVIEW_HOME>/conf/xl-testview.conf` file:

{:.table .table-striped}
| Property | Description | Example |
| -------- | ----------- | ------- |
| `xlt.authentication.method` | Set to `ldap`. |
| `xlt.authentication.ldap.url` | The complete URL of your LDAP server, including the port number. | `ldap://server.domain:389` |
| `xlt.authentication.ldap.user-dn` | A [distinguished name (DN)](http://www.ietf.org/rfc/rfc2253.txt) template that identifies users; `{0}` will be replaced with the user name.<br /><br />If this property is set, `xlt.authentication.ldap.user-search-base` and `xlt.authentication.ldap.user-search-filter` should be commented out. | `cn={0},ou=developers,ou=persons,dc=nodomain` |
| `xlt.authentication.ldap.user-search-base` | Base DN to use to search for users.<br /><br />If this property is used, `xlt.authentication.ldap.user-dn` should be commented out.  | `ou=persons,dc=nodomain` |
| `xlt.authentication.ldap.user-search-filter` | Filter to use when searching for users.<br /><br />If this property is used, `xlt.authentication.ldap.user-dn` should be commented out. | `(&(uid={0})(objectClass=inetOrgPerson))` |

**Note:** If `xlt.authentication.ldap.user-dn`, `xlt.authentication.ldap.user-search-base`, and `xlt.authentication.ldap.user-search-filter` are all uncommented, then `xlt.authentication.ldap.user-dn` will take precedence over the other settings.

Identifying users by properties other than the user name is not currently supported.

After saving the `xl-testview.conf` file, [restart XL TestView](/xl-testview/how-to/start.html) and log in.

## Configure secure LDAP

If your LDAP configuration uses a certificate signed by a certificate authority or a certificate that is trusted in the global Java truststore, you should be able to connect by setting the `xlt.authentication.ldap.url` property in `<XLTESTVIEW_HOME>/conf/xl-testview.conf` to the secure URL (for example, `ldaps://server.domain:636`). After saving the `xl-testview.conf` file, [restart XL TestView](/xl-testview/how-to/start.html) and log in.

If you use a self-signed certificate and you cannot add it to the global truststore, you need to configure a local keystore. You can do so using the [`keytool`](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html) utility (part of the Java JDK distribution).

**Note:** Ensure you complete the XL TestView registration process before you configure LDAP. Registration will not work afterward.

To configure a local keystore:

1. Export the certificate of your LDAP server. Please consult the documentation of your LDAP server for instructions.
2. Go to the `conf` directory of your installation. Another location is also allowed.
3. Create a new truststore using the following command:

        keytool -import  -alias ldap.example.com -file ldapCertificate.crt -keystore truststore.jks

4. Type a keystore password twice.
5. Confirm you trust this account.
6. In `<XLTESTVIEW_HOME>/conf/xl-testview.conf`, set `xlt.truststore.location` to the absolute file location of the truststore you just created.
7. Set `xlt.truststore.password` to the password.
8. After saving the `xl-testview.conf` file, [restart XL TestView](/xl-testview/how-to/start.html) and log in.

## Disable authentication

If you do not need XL TestView to authenticate users because it is running in a trusted environment, you can disable authentication. To do so, set `xlt.authentication.method` to `none` in the `<XLTESTVIEW_HOME/conf/xl-testview.conf` file.
