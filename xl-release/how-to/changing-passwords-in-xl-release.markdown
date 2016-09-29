---
title: Changing passwords in XL Release
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- security
- repository
- password
weight: 500
---

## Configure custom passwords

XL Release provides a mechanism to automatically encrypt passwords and allow you to refer to them, so you do not need to store third-party passwords in plain text in configuration files

### Declare a new custom password

To declare a new custom password, add it in the `conf/xl-release-server.conf` file:

    third.party.password=value

**Note:** The key must end with ".password".

The next time that you start XL Release, your password will automatically be encrypted in the `xl-release-server.conf` file:

    third.party.password={b64}nbbZ2zHXozfxiz1+ooe8hg\=\=

### Refer to a custom password

After you declare a custom password, you can use it in Spring and Jackrabbit configuration files.

For example, if you have declared `mysql.xlrelease.password` in the `xl-release-server.conf` file, then you can use it in the `conf/jackrabbit-repository.xml` file:

    <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
        <param name="url" value="jdbc:mysql://localhost:3306/xlrelease" />
        <param name="user" value="xlrelease" />
        <param name="password" value="${mysql.xlrelease.password}" />
    </PersistenceManager>

## Change the encryption key password

The passwords in the XL Release repository are encrypted with a secret key. This password encryption key is stored in a keystore file called `conf/repository-keystore.jceks`, which is optionally protected with a password. If a password is set, you must enter it when the XL Release Server starts.

To change the keystore password, use the `keytool` utility that is part of the Java JDK distribution:

    keytool -storepasswd -keystore conf/repository-keystore.jceks -storetype jceks

However, there is one restriction: keytool will not to read or set passwords that are shorter than 6 characters. If you want to change a keystore with an empty or short password, download the third-party application **KeyStore Explorer**.

**Note:** `repository-keystore.jceks` is one of the two keystore concepts in XL Release. This keystore only contains the key used for encryption of passwords in the repository. If you use HTTPS, XL Release will use a second keystore file to store the (self-signed) certificate.
