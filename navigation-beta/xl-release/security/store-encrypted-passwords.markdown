---
title: Store encrypted passwords in XL Release
categories:
- xl-release
subject:
- Security
tags:
- system administration
- security
- repository
- password
---

XL Release provides a mechanism to automatically encrypt passwords and allow you to refer to them, so you do not need to store third-party passwords in plain text in configuration files. To declare a new third-party password:

1. Add the password to the `XL_RELEASE_SERVER_HOME/conf/xl-release-server.conf` file:

        third.party.password=value

    **Note:** The key must end with `.password`.

2. Restart XL Release. The password will automatically be encrypted in the `xl-release-server.conf` file:

        third.party.password={b64}nbbZ2zHXozfxiz1+ooe8hg\=\=

3. Use the password in Spring and Jackrabbit configuration files.

For example, if you declare `mysql.xlrelease.password` in the `xl-release-server.conf` file, then you can use it in the `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml` file:

    <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
        <param name="url" value="jdbc:mysql://localhost:3306/xlrelease" />
        <param name="user" value="xlrelease" />
        <param name="password" value="${mysql.xlrelease.password}" />
    </PersistenceManager>
