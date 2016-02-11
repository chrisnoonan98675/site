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
since:
- XL TestView 1.3.0
---

XL TestView supports authentication of users using LDAP. This topic describes how to configure LDAP authentication on XL TestView. Some general knowledge about LDAP and your LDAP server in particular is required.

## Configure unsecure LDAP

To configure LDAP, update the following properties in the `<XLTESTVIEW_HOME>/conf/xl-testview.conf` file:

{:.table .table-striped}

| Property | Description | Example |
| -------- | ----------- | ------- |
| `xlt.authentication.method` | Set to `ldap`. |
| `xlt.authentication.ldap.url` | The complete URL of your LDAP server, including the port number. | `ldap://server.domain:389` |
| **Bind authentication** |
| `xlt.authentication.ldap.user-dn` | A [distinguished name (DN)](http://www.ietf.org/rfc/rfc2253.txt) template that identifies users; `{0}` will be replaced with the user name.<br /><br />If this property is set, `xlt.authentication.ldap.user-search-base` and `xlt.authentication.ldap.user-search-filter` will be ignored. | `cn={0},ou=developers,ou=persons,dc=nodomain` |
| **Search filter**|
| `xlt.authentication.ldap.user-search-base` | Base DN to use to search for users.<br /><br />If this property is used, `xlt.authentication.ldap.user-dn` should be commented out.  | `ou=persons,dc=nodomain` |
| `xlt.authentication.ldap.user-search-filter` | Filter to use when searching for users.<br /><br />If this property is used, `xlt.authentication.ldap.user-dn` should be commented out. | `(&(uid={0})(objectClass=inetOrgPerson))` |
| `xlt.authentication.ldap.admin-dn` | To use search filters, some LDAP instances (such as Active Directory) require authentication prior to the search. Define a full admin DN. | `CN=xltv-admin,CN=Users,DC=nodomain` |
| `xlt.authentication.ldap.admin-password` | Password for the `admin-dn` account. The password is in plain text. | `secret` |

**Note:** If `xlt.authentication.ldap.user-dn`, `xlt.authentication.ldap.user-search-base`, and `xlt.authentication.ldap.user-search-filter` are all uncommented, then `xlt.authentication.ldap.user-dn` will take precedence over the other settings.

After saving the `xl-testview.conf` file, [restart XL TestView](/xl-testview/how-to/start.html) and log in.

### Bind authentication

With LDAP bind authentication, the user is verified by logging on to the LDAP server. If the log on succeeds, the user/password combination is valid and the user is authenticated by XL TestView.

    ldap {
      url = "ldap://ldap.nodomain:389"

      user-dn = "cn={0},ou=developers,ou=persons,dc=nodomain"
    }

The placeholder `{0}` is substituted by the user name.

Identifying users by properties other than the user name is currently not supported.

### Search filter

LDAP authentication can also be done by doing a search for a user before performing authentication. For this setup you need to define a `user-search-base` and a `user-search-filter`. A search should result in one unique object to which will be authenticated.

    ldap {
      url = "ldap://ldap.nodomain:389"

      user-search-base = "CN=Users,DC=example,DC=com"

      user-search-filter = "(&(uid={0})(objectClass=inetOrgPerson))"
    }

The placeholder `{0}` is substituted by the user name.

It might be possible that XL TestView first needs to authenticate itself to the LDAP server before a search can be performed. To achieve this, you can add `admin-dn` and `admin-password`. For an example, have a look at the Active Directory example below.

## Connecting to Active Directory

Active Directory authentication is supported as of XL TestView 1.4.1.

To authenticate with Active Directory, configure LDAP as shown below.

    ldap {
      admin-dn = "CN=xltv-admin,CN=Users,DC=nodomain"
     
      admin-password = "secret"
	 
      url = "ldap://ad-master.nodomain:389"

      user-search-base = "CN=Users,DC=example,DC=com"

      user-search-filter = "(&(SamAccountName={0})(objectClass=user))"
    }
    
With Active Directory you need to configure the search filter option to find a user. The `SamAccountName` property usually contains the user name. To perform the search XL TestView needs to authenticate itself. The `admin-dn` and `admin-password` properties provide for that. 

## Configure secure LDAP

If your LDAP configuration uses a certificate signed by a certificate authority or a certificate that is trusted in the global Java truststore, you should be able to connect by setting the `xlt.authentication.ldap.url` property in `<XLTESTVIEW_HOME>/conf/xl-testview.conf` to the secure URL (for example, `ldaps://server.domain:636`). After saving the `xl-testview.conf` file, [restart XL TestView](/xl-testview/how-to/start.html) and log in.

If you use a self-signed certificate and you cannot add it to the global truststore, you need to configure a local keystore. You can do so using the [`keytool`](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html) utility (part of the Java JDK distribution).

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
