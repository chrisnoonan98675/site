---
title: Extend the Tomcat plugin
categories:
- xl-deploy
subject:
- Tomcat plugin
tags:
- tomcat
- middleware
- plugin
- extension
---

The Tomcat plugin is designed to be extended through XL Deploy's plugin API type system.

## Adding additional context attributes

Tomcat contexts have several attributes that can be set for a WAR. By default, the Tomcat plugin only sets the `unpackWAR` and `docBase` attributes. When additional attributes are required, performing a type modification on the corresponding `tomcat.WarModule` can be done.

For example, adding support for the `antiResourceLocking` attribute in `synthetic.xml`:

	<type-modification type="tomcat.WarModule">
	    <property name="antiResourceLocking" default="true" hidden="true"/>
        <property name="contextElementPropertyMapping" kind="map_string_string" hidden="true"
                  default="unpackWAR:, docBase:, antiResourceLocking:"
	</type-modification>

An additional hidden property `antiResourceLocking` with a default value of "true" is added to the deployed. The property is added to the `contextElementPropertyMapping` property's default value. This property is used to translate properties on the deployed to their XML equivalent in the Tomcat context. The left side represents the property name in the deployed, while the right hand side represents the attribute name in the XML. When the right side is blank, the property name and XML equivalent are the same.

The same technique can be used to add attributes to other Tomcat resources like datasources, queues, and so on.

## Adding new context elements

Tomcat contexts have several nested components or special features (Context Parameters, Lifecycle Listeners, and so on), that can be defined in the XML.

It is possible to model these features in the Tomcat plugin and perform subsequence deployments of these resources.

For example, adding support for the Context Parameter feature:

    <Context>
      ...
      <Parameter name="companyName" value="My Company, Incorporated"
             override="false"/>
      ...
    </Context>

Add to `synthetic.xml`:

	<type type="tomcat.ContextParameter" extends="tomcat.ContextElement"
	            deployable-type="tomcat.ContextParameterSpec">
        <generate-deployable type="tomcat.ContextParameterSpec" extends="jee.ResourceSpec"/>
        <property name="paramName" description="The name of the context parameter"/>
        <property name="value"/>
        <property name="override" kind="boolean" required="false" default="false"/>

        <!--inherited hidden -->
        <property name="elementTag" default="Parameter" hidden="true"/>
        <property name="elementName" default="paramName" hidden="true"/>
        <property name="elementPropertyMapping" kind="map_string_string" hidden="true"
                  default="paramName:name, value:, override:"/>
    </type>

For more information about the Tomcat plugin, refer to [Introduction to the XL Deploy Tomcat plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-tomcat-plugin.html).
