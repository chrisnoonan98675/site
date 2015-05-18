---
title: Configure LDAP security for XL Release
categories:
- xl-release
subject:
- Security
tags:
- security
- ldap
- active directory
- system administration
- user management
---

XL Release cookies store security information that is provided by the [Spring Security](http://projects.spring.io/spring-security/) framework. XL Release does not store any additional information in cookies.

XL Release can be configured to use an LDAP repository to authenticate users and to retrieve role (group) membership. The LDAP users and groups are used as principals in XL Release and can be mapped to XL Release roles. Role membership and rights assigned to roles are always stored in the JCR repository.

XL Release treats the LDAP repository as **read-only**. This means that XL Release will use the information from the LDAP repository, but it cannot make changes to that information.

To configure XL Release to use an LDAP repository, modify the `xl-release-security.xml` security configuration file. For a step-by-step procedure, refer to [How to connect to your LDAP or Active Directory](/xl-deploy/how-to/connect-ldap-or-active-directory.html).

**Note:** You must restart the XL Release server after you configure LDAP access.

## Sample XL Release security file

This is an example of a working `xl-release-security.xml` file that uses LDAP:

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:security="http://www.springframework.org/schema/security"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd
        ">

        <bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource">
            <constructor-arg value="LDAP_SERVER_URL" />
            <property name="userDn" value="MANAGER_DN" />
            <property name="password" value="MANAGER_PASSWORD" />
            <property name="baseEnvironmentProperties">
                <map>
                    <entry key="java.naming.referral">
                        <value>ignore</value>
                    </entry>
                </map>
            </property>
        </bean>

        <bean id="ldapProvider" class="com.xebialabs.xlrelease.security.authentication.LdapAuthenticationProvider">
            <constructor-arg>
                <bean class="org.springframework.security.ldap.authentication.BindAuthenticator">
                    <constructor-arg ref="ldapServer" />
                    <property name="userSearch">
                        <bean id="userSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch">
                            <constructor-arg index="0" value="USER_SEARCH_BASE" />
                            <constructor-arg index="1" value="USER_SEARCH_FILTER" />
                            <constructor-arg index="2" ref="ldapServer" />
                        </bean>
                    </property>
                </bean>
            </constructor-arg>
            <constructor-arg>
                <bean class="org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator">
                    <constructor-arg ref="ldapServer" />
                    <constructor-arg value="GROUP_SEARCH_BASE" />
                    <property name="groupSearchFilter" value="GROUP_SEARCH_FILTER" />
                    <property name="rolePrefix" value="" />
                    <property name="searchSubtree" value="true" />
                    <property name="convertToUpperCase" value="false" />
                </bean>
            </constructor-arg>
        </bean>

        <bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/>

        <bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider"/>

        <security:authentication-manager alias="authenticationManager">
            <security:authentication-provider ref="rememberMeAuthenticationProvider" />
            <security:authentication-provider ref="ldapProvider" />
            <security:authentication-provider ref="jcrAuthenticationProvider"/>
        </security:authentication-manager>

    </beans>

The XML fragment above contains placeholders for the following values:

{:.table .table-striped}
| Placeholder | Description | Example |
| ----------- | ----------- | ------- |
| `LDAP_SERVER_URL` | The LDAP URL to connect to | `ldap://localhost:1389/` |
| `MANAGER_DN` | The principal to perform the initial bind to the LDAP server | `"cn=admin,dc=example,dc=com"` |
| `MANAGER_PASSWORD` | The credentials to perform the initial bind to the LDAP server; see [Configure custom passwords](/xl-release/how-to/changing-passwords-in-xl-release.html#configure-custom-passwords) for information about encrypting this password | `"secret"` |
| `USER_SEARCH_FILTER` | The LDAP filter to determine the LDAP DN for the user who is logging in; `{0}` will be replaced with the user name | `"(&(uid={0})(objectClass=inetOrgPerson))"` |
| `USER_SEARCH_BASE` | The LDAP filter that is the base for searching for users | `"dc=example,dc=com"` |
| `GROUP_SEARCH_FILTER` | The LDAP filter to determine the group memberships for the user; `{0}` will be replaced with the DN of the user | `"(memberUid={0})"` |
| `GROUP_SEARCH_BASE` | The LDAP filter that is the base for searching for groups | `"ou=groups,dc=example,dc=com"` |

## Assign a default role to all authenticated users

In the `xl-release-security.xml` file, you can configure:

* An LDAP setup in which there is not a group that contains all XL Release users
* You want to use such a group in the default `JcrAuthenticationProvider`

The code below sets up an group called `everyone` that is assigned to each user who is authenticated (the group name can be anything you want). You can then link this group to a XL Release role and assign 'login' privileges to it.

    <beans>
        ...

        <bean id="ldapProvider" class="com.xebialabs.xlrelease.security.authentication.LdapAuthenticationProvider">
            <constructor-arg>
                ...
            </constructor-arg>

            <property name="authoritiesMapper" ref="additionalAuthoritiesMapper" />
        </bean>

        <bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider">
            <property name="authoritiesMapper" ref="additionalAuthoritiesMapper" />
        </bean>

        <bean id="additionalAuthoritiesMapper" class="com.xebialabs.deployit.security.AdditionalAuthoritiesMapper">
            <property name="additionalAuthorities">
                <list>
                    <value>everyone</value>
                </list>
            </property>
        </bean>

    </beans>
