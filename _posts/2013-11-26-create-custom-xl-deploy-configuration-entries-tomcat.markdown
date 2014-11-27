---
title: Create custom XL Deploy CI types for Apache Tomcat
categories:
- xl-deploy
tags:
- tomcat
- middleware
- deployment
---

Using XL Deploy's extensible [configuration item (CI) type system](http://docs.xebialabs.com/releases/latest/xl-deploy/referencemanual.html#type-system) and the [plugin for Apache Tomcat](http://docs.xebialabs.com/releases/latest/tomcat-plugin/tomcatPluginManual.html), you can add configuration entries in Tomcat's `context.xml` file for webservices and environments. 

This example extends two Tomcat CI types to create new resource types called `tomcat.ContextResourceSpec` and `tomcat.ContextEnvironmentSpec`. They map directly to entries in the Tomcat `context.xml` file.

Add the following entries to the `synthetic.xml` file, then restart the XL Deploy server:

    <type type="tomcat.ContextResource" extends="tomcat.JndiContextElement" deployable-type="tomcat.ContextResourceSpec"> 
        <generate-deployable type="tomcat.ContextResourceSpec" extends="jee.JndiResourceSpec" copy-default-values="true"/> 
        <property name="auth" default="Container" required="true"/> 
        <property name="description"/> 
        <property name="resourceType" kind="boolean" required="true"/>
        <!--inherited hidden --> 
        <property name="elementTag" default="Resource" hidden="true"/> 
        <property name="elementPropertyMapping" kind="map_string_string" hidden="true" default="jndiName:name, auth:, description:, resourceType:type"/> 
    </type>

    <type type="tomcat.ContextEnvironment" extends="tomcat.JndiContextElement" deployable-type="tomcat.ContextEnvironmentSpec"> 
        <generate-deployable type="tomcat.ContextEnvironmentSpec" extends="jee.JndiResourceSpec" copy-default-values="true"/>
        <property name="jndiName" label="name" required="true"/> 
        <property name="value" required="true"/> 
        <property name="override" kind="boolean" default="false"/> 
        <property name="resourceType" required="true"/>
        <!--inherited hidden --> 
        <property name="elementTag" default="Environment" hidden="true"/> <property name="elementPropertyMapping" kind="map_string_string" hidden="true" default="jndiName:name, override:, value:, resourceType:type"/>
    </type> 

After you deploy the resource, it will appear in the `context.xml` file as: 

    <?xml version="1.0" encoding="UTF-8"?>
    <Context>
        <!-- Default set of monitored resources -->
        <WatchedResource>WEB-INF/web.xml</WatchedResource>
        <Environment name="myname" override="true" type="java.lang.Integer" value="asd" />
        <Resource description="sample" name="jdbc/asd" type="false" auth="Container" />
    </Context>

For more information about the Tomcat plugin, see the [Tomcat Manual](http://docs.xebialabs.com/releases/latest/tomcat-plugin/tomcatPluginManual.html).