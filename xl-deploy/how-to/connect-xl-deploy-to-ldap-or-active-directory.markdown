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
---

This tutorial describes how to connect XL Deploy to your LDAP or Active Directory.

## Step 1 Get your LDAP credentials

Check with your system administrator for your LDAP credentials:

{:.table .table-striped}
| Placeholder | Description | Example |
| ----------- | ----------- | ------- |
| `LDAP_SERVER_URL` | LDAP URL to connect to | `ldap://localhost:389/` |
| `MANAGER_DN` | Principal to perform the initial bind to the LDAP server | `cn=admin,dc=example,dc=com` |
| `MANAGER_PASSWORD` | Credentials to perform the initial bind to the LDAP server | `secret` |

## Step 2 Verify your credentials

Use an LDAP browser such as [JXplorer](http://jxplorer.org/) to verify that the credentials are correct.

## Step 3 Update security - LDAP Server Definition

Add the highlighted code to `deployit-security.xml`. Replace the placeholders with your credentials. Note that credentials are case-sensitive.

<pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:security="http://www.springframework.org/schema/security" xmlns:p="http://www.springframework.org/schema/p" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd "&gt;
<mark>
 &lt;bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource"&gt;
    &lt;constructor-arg value="LDAP_SERVER_URL" /&gt;
	 &lt;property name="userDn" value="MANAGER_DN" /&gt;
	 &lt;property name="password" value="MANAGER_PASSWORD" /&gt;
	 &lt;property name="baseEnvironmentProperties"&gt;
	   &lt;map&gt;
		 &lt;entry key="java.naming.referral"&gt;
		   &lt;value&gt;ignore&lt;/value&gt;
		 &lt;/entry&gt;
	   &lt;/map&gt;
	 &lt;/property&gt;
 &lt;/bean&gt;</mark>
 &lt;bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/&gt; 

 &lt;bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider"/&gt; 

 &lt;security:authentication-manager alias="authenticationManager"&gt; 
   &lt;security:authentication-provider ref="rememberMeAuthenticationProvider" /&gt; 
   &lt;security:authentication-provider ref="jcrAuthenticationProvider"/&gt; 
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
   &lt;!-- The download url has no security access set --&gt;
   &lt;security:intercept-url pattern="/deployit/**" access="IS_AUTHENTICATED_FULLY"/&gt;
   &lt;security:intercept-url pattern="/api/**" access="IS_AUTHENTICATED_FULLY"/&gt;
   &lt;security:custom-filter position="BASIC_AUTH_FILTER" ref="basicAuthenticationFilter"/&gt;
   &lt;security:session-management session-fixation-protection="none"/&gt;
 &lt;/security:http&gt;

&lt;/beans&gt;
</pre>

Restart XL Deploy. Ensure that the server starts without any exceptions.

## Step 4 Determine user properties

Check with your LDAP administrator for the search filter that should be used for finding users in LDAP. The administrator should also provide the distinguished name to use as a starting point for the search.

Alternatively, using an LDAP browser, search for a user who has permission to log in to XL Deploy. Use this user to determine the filter and DN.

{:.table .table-striped}
| Placeholder | Description | Example |
| ----------- | ----------- | ------- |
| `USER_SEARCH_FILTER` | LDAP filter to determine the LDAP `dn` for the user who is logging in; `{0}` will be replaced with the username. Please note that any `&` used in the search filter must be written as `&amp;` | `(&amp;(uid={0})(objectClass=inetOrgPerson))` |
| `USER_SEARCH_BASE` | LDAP `dn` to use as the basis for searching for users | `dc=example,dc=com` |

## Step 5 Update security - LDAP User Authentication

Update the highlighted lines in `deployit-security.xml` as follows. Replace the placeholders with your credentials. Note that credentials are case-sensitive.

<pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:security="http://www.springframework.org/schema/security" xmlns:p="http://www.springframework.org/schema/p" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd "&gt;
 &lt;bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource"&gt;
   &lt;constructor-arg value="LDAP_SERVER_URL" /&gt;
	 &lt;property name="userDn" value="MANAGER_DN" /&gt;
	 &lt;property name="password" value="MANAGER_PASSWORD" /&gt;
	 &lt;property name="baseEnvironmentProperties"&gt;
	   &lt;map&gt;
		 &lt;entry key="java.naming.referral"&gt;
		   &lt;value&gt;ignore&lt;/value&gt;
		 &lt;/entry&gt;
	   &lt;/map&gt;
	 &lt;/property&gt;
 &lt;/bean&gt; 

 <mark>&lt;bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider"&gt; 
  &lt;constructor-arg&gt; 
   &lt;bean class="org.springframework.security.ldap.authentication.BindAuthenticator"&gt;
	 &lt;constructor-arg ref="ldapServer" /&gt;
	 &lt;property name="userSearch"&gt;
		&lt;bean id="userSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch"&gt;
		  &lt;constructor-arg index="0" value="USER_SEARCH_BASE" /&gt;
		  &lt;constructor-arg index="1" value="USER_SEARCH_FILTER" /&gt;
		  &lt;constructor-arg index="2" ref="ldapServer" /&gt;
		&lt;/bean&gt;
	 &lt;/property&gt;
   &lt;/bean&gt;
  &lt;/constructor-arg&gt;
 &lt;/bean&gt;</mark> 

 &lt;bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/&gt; 
 &lt;bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider"/&gt; 

 &lt;security:authentication-manager alias="authenticationManager"&gt; 
   &lt;security:authentication-provider ref="rememberMeAuthenticationProvider" /&gt; 
   &lt;security:authentication-provider ref="jcrAuthenticationProvider"/&gt;
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
   &lt;!-- The download url has no security access set --&gt;
   &lt;security:intercept-url pattern="/deployit/**" access="IS_AUTHENTICATED_FULLY"/&gt;
   &lt;security:intercept-url pattern="/api/**" access="IS_AUTHENTICATED_FULLY"/&gt;
   &lt;security:custom-filter position="BASIC_AUTH_FILTER" ref="basicAuthenticationFilter"/&gt;
   &lt;security:session-management session-fixation-protection="none"/&gt;
 &lt;/security:http&gt;

&lt;/beans&gt;
</pre>

Restart XL Deploy. Ensure that the server starts without any exceptions.

## Step 6 Add the user in the GUI

Add the user who you used in [step 4](#step-4-determine-user-properties) as a principal in the XL Deploy interface and assign the principal permission to log in.

## Step 7 Verify the user log-in

Verify that you can log in with the user you used in [step 4](#step-4-determine-user-properties).

## Step 8 Determine group properties

Check with your LDAP administrator for the search filter that should be used for finding group members in LDAP. The administrator should also provide the distinguished name to use as a starting point for the search.

Alternatively, using an LDAP browser, search for a group that should be a principal in XL Deploy. Use this group to determine the filter and DN.


{:.table .table-striped}
| Placeholder | Description | Example |
| ----------- | ----------- | ------- |
| `GROUP_SEARCH_FILTER` | LDAP filter to determine group memberships of the user; `{0}` will be replaced with the `dn` of the user | `(memberUid={0})` |
| `GROUP_SEARCH_BASE` | LDAP filter to use as a basis for searching for groups | `ou=groups,dc=example,dc=com` |

## Step 9 Update security - LDAP Group Authentication

Update the highlighted lines in `deployit-security.xml` as follows. Replace the placeholders with your credentials. Note that credentials are case-sensitive.

<pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:security="http://www.springframework.org/schema/security" xmlns:p="http://www.springframework.org/schema/p" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd "&gt;
 &lt;bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource"&gt;
   &lt;constructor-arg value="LDAP_SERVER_URL" /&gt;
	 &lt;property name="userDn" value="MANAGER_DN" /&gt;
	 &lt;property name="password" value="MANAGER_PASSWORD" /&gt;
	 &lt;property name="baseEnvironmentProperties"&gt;
	   &lt;map&gt;
		 &lt;entry key="java.naming.referral"&gt;
		   &lt;value&gt;ignore&lt;/value&gt;
		 &lt;/entry&gt;
	   &lt;/map&gt;
	 &lt;/property&gt;
 &lt;/bean&gt; 

 &lt;bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider"&gt; 
  &lt;constructor-arg&gt; 
   &lt;bean class="org.springframework.security.ldap.authentication.BindAuthenticator"&gt;
	 &lt;constructor-arg ref="ldapServer" /&gt;
	 &lt;property name="userSearch"&gt;
		&lt;bean id="userSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch"&gt;
		  &lt;constructor-arg index="0" value="USER_SEARCH_BASE" /&gt;
		  &lt;constructor-arg index="1" value="USER_SEARCH_FILTER" /&gt;
		  &lt;constructor-arg index="2" ref="ldapServer" /&gt;
		&lt;/bean&gt;
	 &lt;/property&gt;
   &lt;/bean&gt;
  &lt;/constructor-arg&gt; 
  <mark>&lt;constructor-arg&gt;
   &lt;bean class="org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator"&gt;
	 &lt;constructor-arg ref="ldapServer" /&gt;
	 &lt;constructor-arg value="GROUP_SEARCH_BASE" /&gt;
	 &lt;property name="groupSearchFilter" value="GROUP_SEARCH_FILTER" /&gt;
	 &lt;property name="rolePrefix" value="" /&gt;
	 &lt;property name="searchSubtree" value="true" /&gt;
	 &lt;property name="convertToUpperCase" value="false" /&gt;
   &lt;/bean&gt;
  &lt;/constructor-arg&gt;</mark>
 &lt;/bean&gt; 

 &lt;bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/&gt; 
 &lt;bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider"/&gt; 

 &lt;security:authentication-manager alias="authenticationManager"&gt; 
   &lt;security:authentication-provider ref="rememberMeAuthenticationProvider" /&gt; 
   &lt;security:authentication-provider ref="jcrAuthenticationProvider"/&gt; 
   &lt;security:authentication-provider ref="ldapProvider" /&gt;
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
   &lt;!-- The download url has no security access set --&gt;
   &lt;security:intercept-url pattern="/deployit/**" access="IS_AUTHENTICATED_FULLY"/&gt;
   &lt;security:intercept-url pattern="/api/**" access="IS_AUTHENTICATED_FULLY"/&gt;
   &lt;security:custom-filter position="BASIC_AUTH_FILTER" ref="basicAuthenticationFilter"/&gt;
   &lt;security:session-management session-fixation-protection="none"/&gt;
 &lt;/security:http&gt;

&lt;/beans&gt;
</pre>

Restart XL Deploy. Ensure that the server starts without any exceptions.

## Step 10 Add the group in the GUI

Add the group that you used in [step 8](#step-8-determine-group-properties) as a principal in the XL Deploy interface and assign the principal permission to log in.

## Step 11 Verify the group log-in

Verify that you can log in with the group you used in [step 8](#step-8-determine-group-properties).

