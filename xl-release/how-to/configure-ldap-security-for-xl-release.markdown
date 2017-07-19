---
title: Configure LDAP security for XL Release
categories:
- xl-release
subject:
- System administration
tags:
- security
- ldap
- active directory
- system administration
- user management
---

XL Release has a role-based security system with two types of users:

* [_Internal users_](/xl-release/how-to/configure-user-settings.html) that are managed by XL Release
* _External users_ that are maintained in an LDAP repository such as Active Directory

This topic describes how to configure XL Release to use an LDAP repository to authenticate users and retrieve role (group) membership. In XL Release, LDAP users and groups become *principals* that you can assign to roles. [Global permissions](/xl-release/how-to/configure-permissions.html) are assigned at the role level.

While role memberships and permissions assigned to roles are stored in XL Release's JCR repository, XL Release treats the LDAP repository as read-only. This means that XL Release will use information from the LDAP repository, but it cannot make changes to that information.

**Note:** XL Release cookies store security information that is provided by the [Spring Security](http://projects.spring.io/spring-security/) framework. XL Release does not store any additional information in cookies.

## Sample XL Release security file

To configure XL Release to use an LDAP repository, modify the `xl-release-security.xml` security configuration file. This is an example of a `xl-release-security.xml` file that uses LDAP. Values that you must provide are highlighted.

<pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:security="http://www.springframework.org/schema/security"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd"&gt;

    &lt;bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource"&gt;
        &lt;constructor-arg value="<mark>LDAP_SERVER_URL</mark>" /&gt;
        &lt;property name="userDn" value="<mark>MANAGER_DN</mark>" /&gt;
        &lt;property name="password" value="<mark>MANAGER_PASSWORD</mark>" /&gt;
        &lt;property name="baseEnvironmentProperties"&gt;
            &lt;map&gt;
                &lt;entry key="java.naming.referral"&gt;
                    &lt;value&gt;ignore&lt;/value&gt;
                &lt;/entry&gt;
            &lt;/map&gt;
        &lt;/property&gt;
    &lt;/bean&gt;

    &lt;bean id="ldapProvider" class="com.xebialabs.xlrelease.security.authentication.LdapAuthenticationProvider"&gt;
        &lt;constructor-arg&gt;
            &lt;bean class="org.springframework.security.ldap.authentication.BindAuthenticator"&gt;
                &lt;constructor-arg ref="ldapServer" /&gt;
                &lt;property name="userSearch"&gt;
                    &lt;bean id="userSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch"&gt;
                        &lt;constructor-arg index="0" value="<mark>USER_SEARCH_BASE</mark>" /&gt;
                        &lt;constructor-arg index="1" value="<mark>USER_SEARCH_FILTER</mark>" /&gt;
                        &lt;constructor-arg index="2" ref="ldapServer" /&gt;
                    &lt;/bean&gt;
                &lt;/property&gt;
            &lt;/bean&gt;
        &lt;/constructor-arg&gt;
        &lt;constructor-arg&gt;
            &lt;bean class="org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator"&gt;
                &lt;constructor-arg ref="ldapServer" /&gt;
                &lt;constructor-arg value="<mark>GROUP_SEARCH_BASE</mark>" /&gt;
                &lt;property name="groupSearchFilter" value="<mark>GROUP_SEARCH_FILTER</mark>" /&gt;
                &lt;property name="rolePrefix" value="" /&gt;
                &lt;property name="searchSubtree" value="true" /&gt;
                &lt;property name="convertToUpperCase" value="false" /&gt;
            &lt;/bean&gt;
        &lt;/constructor-arg&gt;
    &lt;/bean&gt;

    &lt;bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/&gt;

    &lt;bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.XlAuthenticationProvider"/&gt;

    &lt;security:authentication-manager alias="authenticationManager"&gt;
        &lt;security:authentication-provider ref="rememberMeAuthenticationProvider" /&gt;
        &lt;security:authentication-provider ref="ldapProvider" /&gt;
        &lt;security:authentication-provider ref="jcrAuthenticationProvider"/&gt;
    &lt;/security:authentication-manager&gt;

&lt;/beans&gt;
</pre>

**Note:** This sample may differ from your `xl-release-security.xml` file, depending on your version of XL Release and any other customizations that have been done. It is recommended that you only add the highlighted items, instead of copying the entire sample.

## Required LDAP information

Update `xl-release-security.xml` with information about your LDAP setup:

{:.table .table-striped}
| Placeholder | Description | Example |
| ----------- | ----------- | ------- |
| `LDAP_SERVER_URL` | LDAP URL to connect to | `ldap://localhost:1389/` |
| `MANAGER_DN` | Principal to perform the initial bind to the LDAP server | `cn=admin,dc=example,dc=com` |
| `MANAGER_PASSWORD` | Credentials to perform the initial bind to the LDAP server; see [Configure custom passwords](/xl-release/how-to/changing-passwords-in-xl-release.html#configure-custom-passwords) for information about encrypting this password | `secret` |
| `USER_SEARCH_FILTER` | LDAP filter to determine the LDAP `dn` for the user who is logging in; `{0}` will be replaced with the user name | `(&amp;(uid={0})(objectClass=inetOrgPerson))` |
| `USER_SEARCH_BASE` | LDAP filter to use as a basis for searching for users | `dc=example,dc=com` |
| `GROUP_SEARCH_FILTER` | LDAP filter to determine the group memberships of the user; `{0}` will be replaced with the `dn` of the user | `(memberUid={0})` |
| `GROUP_SEARCH_BASE` | LDAP filter to use as a basis for searching for groups | `ou=groups,dc=example,dc=com` |

After you enter your values, restart the XL Release server. Add the user and group as principals in the XL Release interface and assign them permission to log in.

**Tip:** Below we provide a concrete example how a possible LDAP configuration could look like.
**Tip:** Use an LDAP browser such as [JXplorer](http://jxplorer.org/) to verify that the credentials are correct. 

## Escaping special characters

Because `xl-release-security.xml` is an XML file, you must escape certain characters in the values that will replace placeholders:

{:.table .table-striped}
| Character | Escape with |
| --------- | ----------- |
| `&` | `&amp;` |
| `"` | `&quot;` |
| `'` | `&apos;` |
| `<` | `&lt;` |
| `>` | `&gt;` |

## Example: Allow users of a certain group to login only
For convenience we provide a filled in security.xml to show you how this could work. 

Like the template description above, the interesting bits have been marked.

Do note that this is based on an [openldap](http://www.openldap.org/) implementation. Filter queries may differ based on your ldap setup and vendor.

<pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:security="http://www.springframework.org/schema/security"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd
    "&gt;
    &lt;bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/&gt;
    &lt;security:authentication-manager alias="authenticationManager"&gt;
        &lt;security:authentication-provider ref="rememberMeAuthenticationProvider" /&gt;
        &lt;!--     &lt;security:authentication-provider ref="xlAuthenticationProvider"/&gt;--&gt;
        &lt;security:authentication-provider ref="ldapProvider" /&gt;
    &lt;/security:authentication-manager&gt;
    &lt;bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource"&gt;
        &lt;constructor-arg value="<mark>ldap://192.168.0.1:389</mark>" /&gt;
        &lt;property name="userDn" value="<mark>cn=admin,dc=test,dc=com</mark>" /&gt;
        &lt;property name="password" value="<mark>myPassword</mark>" /&gt;
        &lt;property name="baseEnvironmentProperties"&gt;
            &lt;map&gt;
                &lt;entry key="java.naming.referral" value="ignore"/&gt;
            &lt;/map&gt;
        &lt;/property&gt;
    &lt;/bean&gt;
    &lt;bean id="ldapUserSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch"&gt;
        &lt;constructor-arg index="0" value="dc=test,dc=com"/&gt;
        <mark>&lt;!-- Use this LDAP filter query to allow only users from a specific Organisational Unit --&gt;</mark>
        &lt;constructor-arg index="1" value="<mark>(&amp;(objectclass=posixAccount)(entryDN=cn={0},cn=MY_AD_GROUP,ou=Security,ou=Groups,ou=France,ou=Regions,dc=test,dc=com)</mark>"/&gt;
        &lt;constructor-arg index="2" ref="ldapServer"/&gt;
    &lt;/bean&gt;
    &lt;bean id="ldapProvider" class="com.xebialabs.xlrelease.security.authentication.LdapAuthenticationProvider"&gt;
        &lt;constructor-arg&gt;
            &lt;bean class="org.springframework.security.ldap.authentication.BindAuthenticator"&gt;
                &lt;constructor-arg ref="ldapServer" /&gt;
                &lt;property name="userSearch" ref="ldapUserSearch"/&gt;
            &lt;/bean&gt;
        &lt;/constructor-arg&gt;
        &lt;constructor-arg&gt;
            &lt;bean class="org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator"&gt;
                &lt;constructor-arg ref="ldapServer" /&gt;
                <mark>&lt;!-- Do note that this filter is used for authorities, not logging in --&gt;</mark>
                &lt;constructor-arg value="<mark>dc=test,dc=com</mark>" /&gt;
                &lt;property name="groupSearchFilter" value="<mark>(&amp;(objectclass=group)(member={0}))</mark>" /&gt;
                &lt;property name="rolePrefix" value="" /&gt;
                &lt;property name="searchSubtree" value="true" /&gt;
                &lt;property name="convertToUpperCase" value="false" /&gt;
            &lt;/bean&gt;
        &lt;/constructor-arg&gt;
    &lt;/bean&gt;
&lt;/beans&gt;
</pre>

## Assign a default role to all authenticated users

If you have an LDAP setup in which there is not a group that contains all XL Release users, and you want to use such a group in the default `XlAuthenticationProvider` (`JcrAuthenticationProvider` in XL Release 4.7.x and earlier), you can configure this in the `xl-release-security.xml` file.

This example creates a group called `everyone` that is assigned to each user who is authenticated (the group name can be anything you want). You can then link this group to a XL Release role and assign a [global permission](/xl-release/how-to/configure-permissions.html) to it.

    <beans>
        ...

        <bean id="ldapProvider" class="com.xebialabs.xlrelease.security.authentication.LdapAuthenticationProvider">
            <constructor-arg>
                ...
            </constructor-arg>

            <property name="authoritiesMapper" ref="additionalAuthoritiesMapper" />
        </bean>

        <bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.XlAuthenticationProvider">
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
