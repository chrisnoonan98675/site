---
title: Connect to your LDAP or Active Directory to XL Deploy or XL Release
subject:
- System administration
categories:
- xl-deploy
- xl-release
tags:
- ldap
- active directory
- security
---

This tutorial describes how to connect XL Deploy or XL Release to your LDAP or Active Directory.

## Step 1 Get your LDAP credentials

Check with your system administrator for your LDAP credentials:

<table class="table table-striped">
	<thead>
    <tr>
        <th>Placeholder</th> <th>Description</th> <th>Example</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>LDAP_SERVER_URL</td> <td>LDAP URL to connect to</td> <td><code>ldap://localhost:389/</code></td>
    </tr>
        <tr>
        <td>MANAGER_DN</td> <td>Principal to perform the initial bind to the LDAP server</td> <td><code>cn=admin,dc=example,dc=com</code></td>
    </tr>
            <tr>
        <td>MANAGER_PASSWORD</td> <td>Credentials to perform the initial bind to the LDAP server</td> <td><code>secret</code></td>
    </tr>
    </tbody>
</table>

## Step 2 Verify your credentials

Use an LDAP browser such as [JXplorer](http://jxplorer.org/) to verify that the credentials are correct.

## Step 3 Update security

Add the following code to `deployit-security.xml` if you are using XL Deploy or to `xl-release-security.xml` if you are using XL Release. Replace the placeholders with your credentials. Note that credentials are case-sensitive.

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:security="http://www.springframework.org/schema/security" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd ">
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
	 
	 <bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/> 
	
	 <bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider"/> 
	
	 <security:authentication-manager alias="authenticationManager"> 
	   <security:authentication-provider ref="rememberMeAuthenticationProvider" /> 
	   <security:authentication-provider ref="jcrAuthenticationProvider"/> 
	 </security:authentication-manager>
	</beans>

Restart XL Deploy or XL Release. Ensure that the server starts without any exceptions.

## Step 4 Determine user properties

Using an LDAP browser, search for a user who has permission to log in to XL Deploy or XL Release. Use this user to determine these items:

<table class="table table-striped">
	<thead>
    <tr>
        <th>Placeholder</th> <th>Description</th> <th>Example</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>USER_SEARCH_FILTER</td> <td>LDAP filter to determine the LDAP <code>dn</code> for the user who is logging in; <code>{0}</code> will be replaced with the username</td> <td><code>((uid={0})(objectClass=inetOrgPerson))</code></td>
    </tr>
        <tr>
        <td>USER_SEARCH_BASE</td> <td>LDAP filter to use as a basis for searching for users</td> <td><code>dc=example,dc=com</code></td>
    </tr>
    </tbody>
</table>

## Step 5 Update security

Update `deployit-security.xml` or `xl-release-xecurity.xml` as follows. Replace the placeholders with your credentials. Note that credentials are case-sensitive.

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:security="http://www.springframework.org/schema/security" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd ">
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
	 </bean> 
	
	 <bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/> 
	 <bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider"/> 
	
	 <security:authentication-manager alias="authenticationManager"> 
	   <security:authentication-provider ref="rememberMeAuthenticationProvider" /> 
	   <security:authentication-provider ref="ldapProvider" />
	   <security:authentication-provider ref="jcrAuthenticationProvider"/> 
	 </security:authentication-manager>
	
	</beans>

Restart XL Deploy or XL Release. Ensure that the server starts without any exceptions.

## Step 6 Add the user in the GUI

Add the user who you used in [step 4](#step-4-determine-user-properties) as a principal in the XL Deploy or XL Release interface and assign the principal permission to log in.

## Step 7 Verify the user log-in

Verify that you can log in with the user you used in [step 4](#step-4-determine-user-properties).

## Step 8 Determine group properties

Using an LDAP browser, search for a group that should be a principal in to XL Deploy or XL Release. Use this group to determine these items:

<table class="table table-striped">
	<thead>
    <tr>
        <th>Placeholder</th> <th>Description</th> <th>Example</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>GROUP_SEARCH_FILTER</td> <td>LDAP filter to determine group memberships of the user; <code>{0}</code> will be replaced with the <code>dn</code> of the user</td> <td><code>(memberUid={0})</code></td>
    </tr>
        <tr>
        <td>GROUP_SEARCH_BASE</td> <td>LDAP filter to use as a basis for searching for groups</td> <td><code>ou=groups,dc=example,dc=com</code></td>
    </tr>
    </tbody>
</table>

## Step 9 Update security

Update `deployit-security.xml` or `xl-release-xecurity.xml` as follows. Replace the placeholders with your credentials. Note that credentials are case-sensitive.

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:security="http://www.springframework.org/schema/security" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd ">
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

Restart XL Deploy or XL Release. Ensure that the server starts without any exceptions.

## Step 10 Add the group in the GUI

Add the group that you used in [step 8](#step-8-determine-group-properties) as a principal in the XL Deploy or XL Release interface and assign the principal permission to log in.

## Step 11 Verify the group log-in

Verify that you can log in with the group you used in [step 8](#step-8-determine-group-properties).

## Conclusion

You have now set up your LDAP integration. For more information about security, refer to the:

* [System Administration Manual](http://docs.xebialabs.com/releases/latest/deployit/systemadminmanual.html#configuring-security)
* [Security Manual](http://docs.xebialabs.com/releases/latest/deployit/securitymanual.html)
