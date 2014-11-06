---
title: Creating a self-signed SSL certificate for XL Deploy
categories:
- xl-deploy
tags:
- system administration
- security
---

For your convenience, XL Deploy can help you with generating a simple certificate for you during installation. If you need a more advanced certificate, you can create it yourself and configure XL Deploy to use it. You can use the [keytool command](http://docs.oracle.com/javase/6/docs/technotes/tools/windows/keytool.html) from the Java SDK to do this. This is an example command:

    keytool -genkey -keyalg RSA -keystore keystore.jks -storepass CHANGEME1 -alias jetty -keypass CHANGEME2 -validity 366 -dname "CN=localhost,O=Deployit,C=NL"

This will generate a new `keystore.jks` file. Configure `deployit.conf` to use it like this:

    keystore.path=conf/keystore.jks
    keystore.password=CHANGEME1
    keystore.keypassword=CHANGEME2

Just put the passwords in plain text, when the server starts up it will save them back encrypted.

If you use the Java 7 `keytool` you can also add Subject Alternative Names for other IPs or DNS names. Use `-ext san=dns:www.example.com` or `-ext san=ip:10.0.0.1`.

Note that a self-signed certificate is not suitable for production use.
