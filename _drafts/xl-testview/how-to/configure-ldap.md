---
layout: beta
title: XL TestView boot properties
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

# LDAP Authentication

XL TestView supports authentication of users using LDAP. This document describes how to configure ldap authentication on XL TestView. Some general knowledge about LDAP and your LDAP server in particular is required.

## Unsecure LDAP

Update the `xl-testview.conf` property file. The following properties should be updated:

* Change property `xlt.authentication.method` to be `ldap`
* Change property `xlt.authentication.ldap.url` to be the complete url to your ldap server including port number, such as `ldap://server.domain:389`
* Change property `xlt.authentication.ldap.user-dn` to be a distinguished name template that identifies users. For example `cn={0},ou=people,dc=xebialabs,dc=com` where {0} will be replaced by the username. Identifing users by other properties is not yet supported.

Now restart XL TestView and try to log in.

## Secure LDAP

If your LDAP uses a certificate signed by a certificate authority, or a certificate that is trusted in the global java trust store, making a connection should just work by setting the secure url (for example `ldaps://server.domain:636`) in `xlt.authentication.ldap.url`.

If you use a self-signed certificate and you cannot add it to the global trust store, you need to configure a local keystore. 

**Note:** Make sure you go trough the registration process before you configure your ldap. Registration will not work afterwards.

1. Export the certificate of your ldap server. Please consult the documenation of your ldap server.
2. Go to the conf directory of your installation. Another location is also allowed.
3. Create a new trust store using the following command: `keytool -import  -alias ldap.example.com -file ldapCertificate.crt -keystore truststore.jks`
4. Type a keystore password twice
5. Confirm you trust this account
6. In the configuration file, set `xlt.truststore.location` as the absolute file location of your trust store you just made
7. Put the password in `xlt.truststore.password`.
8. Restart XL TestView and try to login.