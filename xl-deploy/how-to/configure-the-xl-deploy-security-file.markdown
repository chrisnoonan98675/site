---
title: Configure the XL Deploy security file
categories:
- xl-deploy
subject:
- Security
tags:
- system administration
- security
- ldap
---

By default, XL Deploy authenticates users and retrieves authorization information from its repository. XL Deploy can also be configured to use an LDAP repository to authenticate users and to retrieve role (group) membership. In this scenario, the LDAP users and groups are used as principals in XL Deploy and can be mapped to XL Deploy roles. Role membership and rights assigned to roles are always stored in the JCR repository.

XL Deploy treats the LDAP repository as **read-only**. This means that XL Deploy will use the information from the LDAP repository, but can not make changes to that information.

To configure XL Deploy to use an LDAP repository, the security configuration file `deployit-security.xml` must be modified. For a step-by-step procedure, refer to [How to connect to your LDAP or Active Directory](/xl-deploy/how-to/connect-ldap-or-active-directory.html).

### Sample XL Deploy security file

This is an example of a working `deployit-security.xml` file that uses LDAP:

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

        <bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider">
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

* `LDAP_SERVER_URL`: The LDAP url to connect to (example: `"ldap://localhost:389/"`)
* `MANAGER_DN`: The principal to perform the initial bind to the LDAP server (example: `"cn=admin,dc=example,dc=com"`)
* `MANAGER_PASSWORD`: The credentials to perform the initial bind to the LDAP server. (example: "secret")
* `USER_SEARCH_FILTER`: The LDAP filter to determine the LDAP dn for the user that's logging in, `{0}` will be replaced with the username that is logging in (example: `"(&(uid={0})(objectClass=inetOrgPerson))"`)
* `USER_SEARCH_BASE`: The LDAP filter that is the base for searching for users (example: `"dc=example,dc=com"`)
* `GROUP_SEARCH_FILTER`: The LDAP filter to determine the group memberships for the user, `{0}` will be replaced with the DN of the user (example: `"(memberUid={0})"`)
* `GROUP_SEARCH_BASE`: The LDAP filter that is the base for searching for groups (example: `"ou=groups,dc=example,dc=com"`)

## Assign a default role to all authenticated users

When your LDAP is not setup to contain a group that all XL Deploy users are assigned to, or you want to use such a group in the default `JcrAuthenticationProvider`, it is possible to configure this in your `deployit-security.xml` file. The following snippet will setup an 'everyone' group (The name is arbitrary, choose a different one if you wish) that is assigned to each user who is authenticated. You could then link this group up to an XL Deploy role, and assign 'login' privileges to it for instance.

    <beans>
        ...

        <bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider">
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
