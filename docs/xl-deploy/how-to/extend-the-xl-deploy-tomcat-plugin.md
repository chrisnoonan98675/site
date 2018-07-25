---
title: Extend the XL Deploy Tomcat plugin
categories:
xl-deploy
subject:
Tomcat
tags:
tomcat
middleware
plugin
extension
api
type system
---

The [Apache Tomcat plugin](/xl-deploy/concept/tomcat-plugin.html) for XL Deploy is designed to be extended through XL Deploy's plugin API type system.

## Add context attributes

Tomcat contexts have several attributes that can be set for a WAR file. By default, the Tomcat plugin only sets the `unpackWAR` and `docBase` attributes. When additional attributes are required, you can create a type modification on the corresponding `tomcat.WarModule` can be done.

For example, to add support for the `antiResourceLocking` attribute in `synthetic.xml`:

{% highlight xml %}
<type-modification type="tomcat.WarModule">
    <property name="antiResourceLocking" default="true" hidden="true"/>
    <property name="contextElementPropertyMapping" kind="map_string_string" hidden="true" default="unpackWAR:, docBase:, antiResourceLocking:"/>
</type-modification>
{% endhighlight %}

This adds a hidden property called `antiResourceLocking` to the deployed, with a default value of "true". The property is added to the `contextElementPropertyMapping` property's default value. This property is used to translate properties on the deployed to their XML equivalent in the Tomcat context. The left side represents the property name in the deployed, while the right side represents the attribute name in the XML. When the right side is blank, the property name and XML equivalent are the same.

The same technique can be used to add attributes to other Tomcat resources such as datasources, queues, and so on.

## Add new context elements

Tomcat contexts have several nested components or special features that can be defined in XML, such as context parameters, lifecycle listeners, and so on. You can model these features in the Tomcat plugin and then deploy these resources.

For example, to add support for the context parameter feature:

{% highlight xml %}
<Context>
  ...
  <Parameter name="companyName" value="My Company, Incorporated"
         override="false"/>
  ...
</Context>
{% endhighlight %}

Add the following lines to `synthetic.xml`:

{% highlight xml %}
<type type="tomcat.ContextParameter" extends="tomcat.ContextElement" deployable-type="tomcat.ContextParameterSpec">
    <generate-deployable type="tomcat.ContextParameterSpec" extends="jee.ResourceSpec"/>
    <property name="paramName" description="The name of the context parameter"/>
    <property name="value"/>
    <property name="override" kind="boolean" required="false" default="false"/>

    <!--inherited hidden -->
    <property name="targetDirectory" default="${deployed.container.contextDirectory}" hidden="true"/>
    <property name="targetFile" default="${deployed.context}.xml" hidden="true"/>
    <property name="template" default="tc/context/context-element.ftl" hidden="true"/>
    <property name="elementTag" default="Resource" hidden="true"/>
    <property name="elementPropertyMapping" kind="map_string_string" hidden="true" default="paramName:name, value:, override:"/>
</type>
{% endhighlight %}
