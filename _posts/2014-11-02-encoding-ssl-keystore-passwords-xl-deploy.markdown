---
title: Encoding SSL keystore passwords in XL Deploy 4.5.0+
categories:
- xl-deploy
- tips-and-tricks
tags:
- password
- encryption
- security
---

If you have an SSL keystore configured and you upgrade from a version of XL Deploy prior to 4.5.0 to XL Deploy 4.5.0+, you may encounter the following error when starting the server:

    2014-09-02 12:32:05.241 [main] {} INFO c.x.deployit.DeployitBootstrapper - XL Deploy version 4.5.0 (built at 14-08-28 03:12:17) 
    2014-09-02 12:32:05.257 [main] {} INFO c.x.deployit.DeployitBootstrapper - (c) 2008-2014 XebiaLabs 
    2014-09-02 12:32:05.258 [main] {} INFO c.x.deployit.DeployitBootstrapper - Starting server... 
    2014-09-02 12:32:06.105 [main] {} ERROR c.x.deployit.DeployitBootstrapper - Fatal error starting server 
    java.lang.IllegalStateException: Could not decrypt Base64 encoded password

This error occurs because of an enhanced security policy. Starting with XL Deploy 4.5.0, all passwords in `deployit.conf` are Base64-encoded and obligatorily encrypted.

To correct this error, you must replace the Base64-encoded values of the `keystore.password` and `keystore.keypassword` properties in `deployit.conf` with passwords in clear text, then restart the server. After you restart, the configuration file will be updated with encrypted and encoded values.

## Example

Before the upgrade, `deployit.conf` contained:

    keystore.password={b64}c3RvcmVzZWNyZXQ\=  # base64 version of 'storesecret'
    keystore.keypassword={b64}a2V5c2VjcmV0     # base64 version of 'keysecret'

If you upgrade and see the error, change the passwords to clear text:

    keystore.password=storesecret
    keystore.keypassword=keysecret

Then restart the server.
