---
title: LDAP server binding in XL Deploy
categories:
- xl-deploy
tags:
- system administration
- ldap
- security
---

This is an example of LDAP configuration in the XL Deploy `deployit-security.xml` file:

    <bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource"> 
    <constructor-arg value="ldaps://mycompanyldap:636" /> 
    <property name="userDn" value="CN=MyUser,OU=MyCompany" /> 
    <property name="password" value="MyPassword" /> 
        <property name="baseEnvironmentProperties"> 
            <map> 
                <entry key="java.naming.referral"> 
                    <value>ignore</value> 
                </entry> 
            </map> 
        </property> 
    </bean>

It is possible to provide a "service account" to connect to LDAP, rather try to connect with the provided user credentials.

The [Spring framework](http://projects.spring.io/spring-framework/) offers a number of different ways to configure LDAP security. The one documented in the XL Deploy documentation is one that is the easiest to configure, but other options are available too. For a complete description of the possibilities, refer to the [Spring documentation](http://static.springsource.org/spring-security/site/docs/3.1.x/reference/ldap.html).

Because the standard XL Deploy LDAP configuration uses a "bind authenticator", you should be able to do away with the two lines that set the `userDn` and `password` properties. XL Deploy will then search the LDAP repository for the user's DN, using the `USER_SEARCH_FILTER` you've configured elsewhere, and then try to bind using the DN found and the password provided. The difference is that the search will be done anonymously, so this depends on anonymous users being allowed to search the LDAP tree.

If that doesn't work and DNs can be constructed from the username, you can replace the `<property name="userSearch">` section with XML that sets the `userDnPatterns` property:

    <property name="userDnPatterns"> 
        <list><value>uid={0},ou=people</value></list> 
    </property>
