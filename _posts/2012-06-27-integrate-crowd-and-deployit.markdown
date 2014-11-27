---
title: Integrate Crowd and Deployit 3.7.x
categories:
- xl-deploy
tags:
- system administration
- security
---

[Atlassian Crowd](http://www.atlassian.com/software/crowd/overview) is a single sign-on and user identity tool that you can use as the authentication manager for Deployit.

To learn more about Crowd integration, refer to [Integrating Crowd with Spring Security](https://confluence.atlassian.com/display/CROWD/Integrating+Crowd+with+Spring+Security) and [Spring Security 3.0 Integration Tutorial](https://jira.atlassian.com/browse/CWD-1807).

**Note:** The method described here has only been tested with Deployit 3.7.x.

## Setup

To set up Crowd with Deployit:

1. Download the [`crowd-integration-client.jar`](https://jira.atlassian.com/secure/attachment/52826/crowd-integration-client-2.3.2-SPRING3.jar) for Spring Security 3.x.
1. In your Deployit server installation, create a Crowd directory such as `${DEPLOYT_HOME}/crowd`.
1. Copy the downloaded `crowd-integration-client.jar` file to `${DEPLOYT_HOME}/crowd`.
1. Copy the Crowd client libraries from `CROWD/client/lib` to `${DEPLOYT_HOME}/crowd`.
1. Copy `CROWD/client/conf/crowd.properties` and `CROWD/client/conf/crowd-ehcache.xml` files to `${DEPLOYT_HOME}/conf`.
1. Edit the `crowd.properties` files to match your Crowd installation.
1. Download [this file](/sample-scripts/integrate-crowd-and-deployit/deployit-security.xml), rename it `deployit-security.xml`, and replace your `${DEPLOYT_HOME}/conf/deployit-security.xml` with it.
1. Add the JAR files from `${DEPLOYIT_HOME}/crowd` in the server classpath.

**Note:** When using Deployit 3.9.4 and Crowd 2.7.1, you may encounter a `java.lang.ClassNotFoundException: org.jdom.Element` error. If this occurs, copy `CROWD/crowd-webapp/WEB-INF/lib/jdom-1.1.3.jar` to `${DEPLOYIT_HOME}/crowd`.

## Sample files

### deployit-security.xml

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:security="http://www.springframework.org/schema/security" xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.1.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-3.1.xsd">

		 <!-- crowd context -->
		 <import resource="applicationContext-CrowdClient.xml"/>

		 <bean id="rememberMeAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"/>
		 <bean id="jcrAuthenticationProvider" class="com.xebialabs.deployit.security.authentication.JcrAuthenticationProvider"/>

		 <security:authentication-manager alias="authenticationManager">
			  <security:authentication-provider ref="rememberMeAuthenticationProvider" />
			  <security:authentication-provider ref="crowdAuthenticationProvider"/>
			  <security:authentication-provider ref="jcrAuthenticationProvider"/>
		 </security:authentication-manager>

		 <bean id="crowdUserDetailsService" class="com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetailsServiceImpl">
			<property name="authenticationManager" ref="crowdAuthenticationManager" />
			<property name="groupMembershipManager" ref="crowdGroupMembershipManager" />
			<property name="userManager" ref="crowdUserManager" />
			<property name="authorityPrefix" value=""/>
			<!-- sample value <property name="authorityPrefix" value="ROLE_"/> -->
		</bean>

		<bean id="crowdAuthenticationProvider" class="com.atlassian.crowd.integration.springsecurity.RemoteCrowdAuthenticationProvider">
			<constructor-arg ref="crowdAuthenticationManager" />
			<constructor-arg ref="httpAuthenticator" />
			<constructor-arg ref="crowdUserDetailsService" />
		</bean>
	</beans>

### server.sh

    for each in `find crowd -name "*.jar"  2>/dev/null`
    do
        if [ -f $each ]; then
            DEPLOYIT_SERVER_CLASSPATH=${DEPLOYIT_SERVER_CLASSPATH}:${each}
        fi
    done
