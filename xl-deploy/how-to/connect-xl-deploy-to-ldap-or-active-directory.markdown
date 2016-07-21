---
title: Connect XL Deploy to your LDAP or Active Directory
subject:
- Security
categories:
- xl-deploy
tags:
- ldap
- active directory
- security
- user management
weight: 263
---

This tutorial describes how to connect XL Deploy to your LDAP or Active Directory.

## Step 1 Get your LDAP credentials

First, check with your system administrator for your LDAP credentials and the search filters that should be used to find users and group members in LDAP. The administrator should also provide the distinguished names (DNs) to use as starting points for the search.

**Tip:** Use an LDAP browser such as [JXplorer](http://jxplorer.org/) to verify that the credentials are correct. You can also use an LDAP browser to locate a user that has permission to log in to XL Deploy and a group that should be a principal in Xl Deploy; you can then use these to determine the filter and DN.

You need the following information to update the `<XLDEPLOY_HOME>/conf/deployit-security.xml` file:

{:.table .table-striped}
| Placeholder | Description | Example |
| ----------- | ----------- | ------- |
| `LDAP_SERVER_URL` | LDAP URL to connect to | `ldap://localhost:389/` |
| `MANAGER_DN` | Principal to perform the initial bind to the LDAP server | `cn=admin,dc=example,dc=com` |
| `MANAGER_PASSWORD` | Credentials to perform the initial bind to the LDAP server | `secret` |
| `USER_SEARCH_BASE` | LDAP DN to use as the basis for searching for users | `dc=example,dc=com` |
| `USER_SEARCH_FILTER` | LDAP filter to determine the LDAP DN for the user who is logging in; `{0}` will be replaced with the username | `(&amp;(uid={0})(objectClass=inetOrgPerson))` |
| `GROUP_SEARCH_BASE` | LDAP filter to use as a basis for searching for groups | `ou=groups,dc=example,dc=com` |
| `GROUP_SEARCH_FILTER` | LDAP filter to determine group memberships of the user; `{0}` will be replaced with the DN of the user | `(memberUid={0})` |

### Escaping special characters

Because `deployit-security.xml` is an XML file, you must escape certain characters in the values that will replace placeholders.

{:.table .table-striped}
| Character | Escape with |
| --------- | ----------- |
| `&` | `&amp;` |
| `"` | `&quot;` |
| `'` | `&apos;` |
| `<` | `&lt;` |
| `>` | `&gt;` |

## Step 2 Add the LDAP server definition

Add the following code to `deployit-security.xml`. Replace the placeholders with your credentials.
<pre>
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
</pre>

**Important:** Credentials are case-sensitive.

Restart XL Deploy and ensure that the server starts without any exceptions.

## Step 3 Add LDAP user authentication

Add the following code to `deployit-security.xml`. Replace the placeholders with your credentials.
<pre>
&lt;bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider"&gt;
  &lt;constructor-arg&gt;
    &lt;bean class="org.springframework.security.ldap.authentication.BindAuthenticator"&gt;
      &lt;constructor-arg ref="ldapServer" /&gt;
        &lt;property name="userSearch"&gt;
        &lt;bean id="userSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch"&gt;
          &lt;constructor-arg index="0" value="<mark>USER_SEARCH_BASE</mark>" /&gt;
          &lt;constructor-arg index="1" value="<mark>USER_SEARCH_FILTER</mark>" /&gt;
          &lt;constructor-arg index="2" ref="ldapServer</mark>" /&gt;
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
</pre>

**Important:** Credentials are case-sensitive.

Also, locate the following section and add `ldapProvider` as an authentication provider:
<pre>
&lt;security:authentication-manager alias="authenticationManager"&gt;
  &lt;security:authentication-provider ref="rememberMeAuthenticationProvider" /&gt;
  &lt;security:authentication-provider ref="jcrAuthenticationProvider" /&gt;
  <mark>&lt;security:authentication-provider ref="ldapProvider" /&gt;</mark>
&lt;/security:authentication-manager&gt;
</pre>

**Note:** `ldapProvider` should come after `jcrAuthenticationProvider`. This ensures that, if there is a problem with LDAP, you can still log in to XL Deploy as a local user.

Restart XL Deploy and ensure that the server starts without any exceptions.

## Step 4 Add the user in XL Deploy

Add the user as a [principal](/xl-deploy/concept/overview-of-security-in-xl-deploy.html#principals) in the XL Deploy GUI and assign the principal permission to log in.

Log out, then verify that you can log in with the user.

## Step 5 Add the group in XL Deploy

Add the group as a principal in the XL Deploy GUI and assign the principal permission to log in.

Log out, then verify that you can log in with the group.

## Sample `deployit-security.xml` file

This sample `deployit-security.xml` file shows the required LDAP configuration in context. Note that other parts of your `deployit-security.xml` file may differ from this example, depending on the version of XL Deploy that you are using.

<pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:security="http://www.springframework.org/schema/security" xmlns:p="http://www.springframework.org/schema/p" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd "&gt;
<mark> &lt;bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource"&gt;
   &lt;constructor-arg value="ldap://localhost:389/" /&gt;
	 &lt;property name="userDn" value="cn=admin,dc=example,dc=com" /&gt;
	 &lt;property name="password" value="secret" /&gt;
	 &lt;property name="baseEnvironmentProperties"&gt;
	   &lt;map&gt;
		 &lt;entry key="java.naming.referral"&gt;
		   &lt;value&gt;ignore&lt;/value&gt;
		 &lt;/entry&gt;
	   &lt;/map&gt;
	 &lt;/property&gt;
 &lt;/bean&gt;</mark>

 <mark>&lt;bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider"&gt;
  &lt;constructor-arg&gt;
   &lt;bean class="org.springframework.security.ldap.authentication.BindAuthenticator"&gt;
	 &lt;constructor-arg ref="ldapServer" /&gt;
	 &lt;property name="userSearch"&gt;
		&lt;bean id="userSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch"&gt;
		  &lt;constructor-arg index="0" value="<mark>dc=example,dc=com</mark>" /&gt;
		  &lt;constructor-arg index="1" value="<mark>(&amp;(uid={0})(objectClass=inetOrgPerson))</mark>" /&gt;
		  &lt;constructor-arg index="2" ref="ldapServer" /&gt;
		&lt;/bean&gt;
	 &lt;/property&gt;
   &lt;/bean&gt;
  &lt;/constructor-arg&gt;
  &lt;constructor-arg&gt;
   &lt;bean class="org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator"&gt;
	 &lt;constructor-arg ref="ldapServer" /&gt;
	 &lt;constructor-arg value="ou=groups,dc=example,dc=com" /&gt;
	 &lt;property name="groupSearchFilter" value="(memberUid={0})" /&gt;
	 &lt;property name="rolePrefix" value="" /&gt;
	 &lt;property name="searchSubtree" value="true" /&gt;
	 &lt;property name="convertToUpperCase" value="false" /&gt;
   &lt;/bean&gt;
  &lt;/constructor-arg&gt;
 &lt;/bean&gt;</mark>

 &lt;bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/&gt;
 &lt;bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider"/&gt;

 &lt;security:authentication-manager alias="authenticationManager"&gt;
   &lt;security:authentication-provider ref="rememberMeAuthenticationProvider" /&gt;
   &lt;security:authentication-provider ref="jcrAuthenticationProvider" /&gt;
   <mark>&lt;security:authentication-provider ref="ldapProvider" /&gt;</mark>
 &lt;/security:authentication-manager&gt;

 &lt;bean id="unanimousBased" class="org.springframework.security.access.vote.UnanimousBased"&gt;
   &lt;constructor-arg&gt;
	 &lt;list&gt;
	   &lt;bean class="org.springframework.security.access.vote.AuthenticatedVoter"/&gt;
	   &lt;bean class="com.xebialabs.deployit.security.LoginPermissionVoter"/&gt;
	 &lt;/list&gt;
   &lt;/constructor-arg&gt;
 &lt;/bean&gt;

 &lt;bean id="basicAuthenticationFilter" class="com.xebialabs.deployit.security.authentication.BasicAuthWithRememberMeFilter"&gt;
   &lt;constructor-arg ref="authenticationManager"/&gt;
   &lt;constructor-arg ref="basicAuthenticationEntryPoint"/&gt;
 &lt;/bean&gt;

 &lt;bean id="basicAuthenticationEntryPoint"
   class="com.xebialabs.deployit.security.authentication.BasicAuthenticationEntryPoint"
   p:realmName="Deployit"/&gt;

 &lt;security:http security="none" pattern="/deployit/internal/download/**" create-session="never"/&gt;
 &lt;security:http security="none" pattern="/deployit/internal/configuration/**" create-session="never"/&gt;

 &lt;security:http realm="Deployit" access-decision-manager-ref="unanimousBased" entry-point-ref="basicAuthenticationEntryPoint" create-session="never"&gt;
   &lt;security:csrf disabled="true"/&gt;
   &lt;!-- The download url has no security access set --&gt;
   &lt;security:intercept-url pattern="/deployit/**" access="IS_AUTHENTICATED_FULLY"/&gt;
   &lt;security:intercept-url pattern="/api/**" access="IS_AUTHENTICATED_FULLY"/&gt;
   &lt;security:custom-filter position="BASIC_AUTH_FILTER" ref="basicAuthenticationFilter"/&gt;
   &lt;security:session-management session-fixation-protection="none"/&gt;
 &lt;/security:http&gt;

&lt;/beans&gt;
</pre>
