---
title: Connect XL Deploy to your LDAP or Active Directory
subject:
Security
categories:
xl-deploy
tags:
ldap
active directory
security
user management
weight: 263
---

By default, XL Deploy authenticates users and retrieves authorization information from its repository. XL Deploy can also be configured to use an LDAP repository to authenticate users and to retrieve role (group) membership. In this scenario, the LDAP users and groups are used as principals in XL Deploy and can be mapped to XL Deploy roles. Role membership and rights assigned to roles are always stored in the JCR repository.

XL Deploy treats the LDAP repository as **read-only**. This means that XL Deploy will use the information from the LDAP repository, but can not make changes to that information.

To configure XL Deploy to use an LDAP repository, you must change the security configuration file (`deployit-security.xml`).

This is a step-by-step procedure describing how to connect XL Deploy to your LDAP or Active Directory.

## Step 1 Get your LDAP credentials

First, check with your system administrator for your LDAP credentials and the search filters that should be used to find users and group members in LDAP. The administrator should also provide the distinguished names (DNs) to use as starting points for the search.

**Tip:** Use an LDAP browser such as [JXplorer](http://jxplorer.org/) to verify that the credentials are correct. You can also use an LDAP browser to locate a user that has permission to log in to XL Deploy and a group that should be a principal in XL Deploy; you can then use these to determine the filter and DN.

You need the following information to update the `XL_DEPLOY_SERVER_HOME/conf/deployit-security.xml` file:

{:.table .table-striped}
| Placeholder | Description | Example |
| ----------| ----------| ------|
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
| --------| ----------|
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
<pre class="highlight">
&lt;bean id="userSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch"&gt;
  &lt;constructor-arg index="0" value="<mark>USER_SEARCH_BASE</mark>" /&gt;
  &lt;constructor-arg index="1" value="<mark>USER_SEARCH_FILTER</mark>" /&gt;
  &lt;constructor-arg index="2" ref="ldapServer" /&gt;
&lt;/bean&gt;
&lt;bean id="authoritiesPopulator" class="org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator"&gt;
  &lt;constructor-arg ref="ldapServer" /&gt;
  &lt;constructor-arg value="<mark>GROUP_SEARCH_BASE</mark>" /&gt;
  &lt;property name="groupSearchFilter" value="<mark>GROUP_SEARCH_FILTER</mark>" /&gt;
  &lt;property name="rolePrefix" value="" /&gt;
  &lt;property name="searchSubtree" value="true" /&gt;
  &lt;property name="convertToUpperCase" value="false" /&gt;
&lt;/bean&gt;
&lt;bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider"&gt;
  &lt;constructor-arg&gt;
    &lt;bean class="org.springframework.security.ldap.authentication.BindAuthenticator"&gt;
      &lt;constructor-arg ref="ldapServer" /&gt;
        &lt;property name="userSearch" ref="userSearch"&gt;
      &lt;/property&gt;
    &lt;/bean&gt;
  &lt;/constructor-arg&gt;
  &lt;constructor-arg ref="authoritiesPopulator" /&gt;
&lt;/bean&gt;  
</pre>

**Important:** Credentials are case-sensitive.

Also, locate the following section and add `ldapProvider` as an authentication provider:
<pre class="highlight">
&lt;security:authentication-manager alias="authenticationManager"&gt;
  &lt;security:authentication-provider ref="rememberMeAuthenticationProvider" /&gt;
  &lt;security:authentication-provider ref="XlAuthenticationProvider" /&gt;
  <mark>&lt;security:authentication-provider ref="ldapProvider" /&gt;</mark>
&lt;/security:authentication-manager&gt;
</pre>

**Note:** `ldapProvider` should come after `XlAuthenticationProvider`. This ensures that, if there is a problem with LDAP, you can still log in to XL Deploy as a local user.

Restart XL Deploy and ensure that the server starts without any exceptions.

## Step 4 Add the user in XL Deploy

Add the user as a [principal](/xl-deploy/concept/overview-of-security-in-xl-deploy.html#principals) in the XL Deploy GUI and assign the principal permission to log in.

Log out, then verify that you can log in with the user.

## Step 5 Add the group in XL Deploy

Add the group as a principal in the XL Deploy GUI and assign the principal permission to log in.

Log out, then verify that you can log in with the group.

## Sample `deployit-security.xml` file

This sample `deployit-security.xml` file shows the required LDAP configuration in context. Note that other parts of your `deployit-security.xml` file may differ from this example, depending on the version of XL Deploy that you are using.

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:security="http://www.springframework.org/schema/security" xmlns:p="http://www.springframework.org/schema/p" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd ">
     <bean id="ldapServer" class="org.springframework.security.ldap.DefaultSpringSecurityContextSource">
       <constructor-arg value="ldap://localhost:389/" />
    	 <property name="userDn" value="cn=admin,dc=example,dc=com" />
    	 <property name="password" value="secret" />
    	 <property name="baseEnvironmentProperties">
    	   <map>
    		 <entry key="java.naming.referral">
    		   <value>ignore</value>
    		 </entry>
    	   </map>
    	 </property>
     </bean>

     <bean id="userSearch" class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch">
         <constructor-arg index="0" value="dc=springframework,dc=org" />
         <constructor-arg index="1" value="(&amp;(uid={0})(objectClass=inetOrgPerson))" />
         <constructor-arg index="2" ref="ldapServer" />
     </bean>
     <bean id="authoritiesPopulator" class="org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator">
         <constructor-arg ref="ldapServer" />
         <constructor-arg value="ou=groups,dc=springframework,dc=org" />
         <property name="groupSearchFilter" value="(member={0})" />
         <property name="rolePrefix" value="" />
         <property name="searchSubtree" value="true" />
         <property name="convertToUpperCase" value="false" />
     </bean>

     <bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider">
       <constructor-arg>
            <bean class="org.springframework.security.ldap.authentication.BindAuthenticator">
                <constructor-arg ref="ldapServer" />
                <property name="userSearch" ref="userSearch"/>
            </bean>
       </constructor-arg>
       <constructor-arg ref="authoritiesPopulator"/>
     </bean>

     <bean id="userDetailsService" class="org.springframework.security.ldap.userdetails.LdapUserDetailsService">
        <constructor-arg index="0" ref="userSearch"/>
        <constructor-arg index="1" ref="authoritiesPopulator"/>
     </bean>

     <bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/>
     <bean id="XlAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.XlAuthenticationProvider"/>

     <security:authentication-manager alias="authenticationManager">
       <security:authentication-provider ref="rememberMeAuthenticationProvider" />
       <security:authentication-provider ref="XlAuthenticationProvider" />
       <security:authentication-provider ref="ldapProvider" />
     </security:authentication-manager>

    </beans>

## Assign a default role to all authenticated users

If your LDAP is not set up with a group to which all XL Deploy users are assigned, or if you want to use such a group in the default `XlAuthenticationProvider`, you can configure this in the `deployit-security.xml` file.

The following example shows how to set up a group called `everyone`, which is assigned to each user who is authenticated. You could then link this group to an XL Deploy role and, for example, assign it the `login` permission.

{% highlight xml %}
<beans>
    ...

    <bean id="ldapProvider" class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider">
        <constructor-arg>
            ...
        </constructor-arg>

        <property name="authoritiesMapper" ref="additionalAuthoritiesMapper" />
    </bean>

    <bean id="userDetailsService" class="org.springframework.security.ldap.userdetails.LdapUserDetailsService">
        <constructor-arg index="0" ref="userSearch"/>
        <constructor-arg index="1" ref="authoritiesPopulator"/>
    </bean>

    <bean id="XlAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.XlAuthenticationProvider">
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
{% endhighlight %}
