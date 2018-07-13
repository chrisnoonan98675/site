---
title: Discovery in the XL Deploy Generic plugin
categories:
- xl-deploy
subject:
- Bundled plugins
tags:
- generic
- plugin
- discovery
---

The Generic plugin supports discovery in any subtype of `generic.Container`, `generic.NestedContainer`, or `generic.AbstractDeployed`. To implement custom discovery tasks, you provide shell scripts that interact with the discovery mechanism, via the standard out, with specially formatted output representing the inspected property or discovered configuration item.

To extend the Generic plugin for custom discovery tasks, you must set attributes in `synthetic.xml` as follows:

* The `inspectable` attribute must be set to `true` on the container
* You must define one or more properties with the `inspectionProperty` attribute set to `true`

This is a sample extension for Tomcat:

    <!-- Sample of extending Generic Mode plugin -->
    <type type="sample.TomcatServer" extends="generic.Container" inspectable="true">
        ...
        <property name="inspectScript" default="inspect/inspect-server" hidden="true"/>
        <property name="example" inspectionProperty="true"/>
    </type>

    <type type="sample.VirtualHost" extends="sample.NestedContainer">
        <property name="server" kind="ci" as-containment="true" referenced-type="sample.TomcatServer"/>
        ...
        <property name="inspectScript" default="inspect/inspect-virtualhost" hidden="true"/>
    </type>

    <type type="sample.DataSource" extends="generic.ProcessedTemplate" deployable-type="sample.DataSourceSpec"
          container-type="sample.Server">
        <generate-deployable type="sample.DataSourceSpec" extends="generic.Resource"/>
        <property name="inspectScript" default="inspect/inspect-ds" hidden="true"/>
        ...
    </type>

## Encoding

The discovery mechanism uses URL encoding as described in [RFC3986](http://tools.ietf.org/html/rfc3986) to interpret the value of an inspected property. It is the responsibility of the plugin extender to perform said encoding in the inspect shell scripts.

Sample of encoding in a BASH shell script:

    function encode()
    {
        local  myresult=$(printf "%b" "$1" | perl -pe's/([^-_.~A-Za-z0-9])/sprintf("%%%02X", ord($1))/seg')
        echo "$myresult"
    }
    myString='This is a string spanning many lines and with funky characters like !@#$%^&*() and \|'"'"'";:<>,.[]{}'
    myEncodedString = $(encode "$myString")
    echo $myEncodedString

## Property inspection

The discovery mechanism identifies an inspected property when output with the following format is sent to the standard out.

    INSPECTED:propertyName=value

The output must be prefixed with `INSPECTED:` followed by the name of the inspected property, an `=` sign and then the encoded value of
the property.

Sample:

    echo INSPECTED:stringField=A,value,with,commas
    echo INSPECTED:intField=1999
    echo INSPECTED:boolField=true

### Inspecting set properties

When an inspected property is a set of strings, the value must be comma-separated.

    INSPECTED:propertyName=value1,value2,value3

Sample:

    echo INSPECTED:stringSetField=$(encode 'Jac,q,ues'),de,Molay
    # will result in the following output
    # INSPECTED:stringSetField=Jac%2Cq%2Cues,de,Molay

### Inspecting map properties

When an inspected property is a map of strings, entries must be comma separated and key values must be colon separated

    INSPECTED:propertyName=key1:value1,key2:value2,key3:value3

Sample:

    echo INSPECTED:mapField=first:$(encode 'Jac,q,ues:'),second:2
    # will result in the following output
    # INSPECTED:mapField=first:Jac%2Cq%2Cues,second:2

## Configuration item discovery

The discovery mechanism identifies a discovered configuration item when output with the following format is sent to the standard out:

    DISCOVERED:configurationItemId=type

The output must be prefixed with `DISCOVERED:` followed by the id of the configuration item as stored in the XL Deploy repository, an `=` sign, and the type of the configuration item

Sample:

    echo DISCOVERED:Infrastructure/tomcat/defaultContext=sample.VirtualHost
