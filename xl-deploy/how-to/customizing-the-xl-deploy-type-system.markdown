---
title: Customizing the XL Deploy type system
categories:
- xl-deploy
subject:
- Customization
tags:
- ci
- synthetic
---

Today's middleware products are complicated and support lots of configuration options. XL Deploy plugins represent this middleware to the XL Deploy system. If a plugin wants to be a direct representation of the options in the middleware, it will quickly grow very large and unwieldy. XL Deploy provides a better way.

Customizing XL Deploy is possible by providing new _state_ (modifying existing CIs or defining new CIs) and by providing new _behavior_ (by modifying existing scripts or adding new scripts). This section describes how to modify XL Deploy state. To learn how to add new behavior to the product, take a look at the manual for the plugin to be modified or the [Generic Model Plugin Manual](genericPluginManual.html) to define a new plugin.

## Synthetic Properties

XL Deploy's type system allows an extender to customize any CI by changing its definition. Properties can be added, hidden or changed. These new properties are called _synthetic properties_ since they are not defined in a Java class. The properties and changes are defined in an XML file called `synthetic.xml` which is added to the XL Deploy classpath. Changes to the types are loaded when the XL Deploy server starts and can be used to perform deployments.

## Modifying Existing CIs

Types existing in XL Deploy can be modified to contain additional synthetic properties. These properties become a part of the CI type and can be specified in the deployment package and shown in the XL Deploy GUI.

There are several reasons to modify a CI:

* A CI property is always given the same value in your environment. Using synthetic properties, the property can be given a default value and hidden from the user in the GUI.
* There are additional properties of an existing CI that you want to specify. For example, suppose there is a CI representing a deployed datasource for a specific middleware platform. The middleware platform allows the user to specify a connection pool size and connection timeout and XL Deploy supports the connection pool size out of the box. In this case, modifying the CI to add a synthetic property allows the user to specify the connection timeout.

**Note**: to use the newly defined property in a deployment, XL Deploy's behavior must be modified. To learn how to add new behavior to the product, take a look at the manual for the plugin to be modified or the [Generic Model Plugin Manual](genericPluginManual.html) to define a new plugin.

Additionally, any property that is modified is listed as a nested `property` element. For each property, the following information can be specified:

{:.table .table-striped}
| Property | Required | Description |
| -------- | -------- | ----------- |
| `name` | Yes | The name of the property to modify. |
| `kind` | No | The type of the property to modify. Possible values are: `enum`, `boolean`, `integer`, `string`, `ci`, `set_of_ci`, `set_of_string`, `map_string_string`, `list_of_ci`, `list_of_string`, and `date` (internal use only). |
| `description` | No | Describes the property. |
| `category` | No | Categorizes the property. Each category is shown in a separate tab in the XL Deploy GUI. |
| `label` | No | Sets the property's label. If set, the label is shown in the XL Deploy GUI instead of the name. |
| `required` | No | Indicates whether the property is required or not. Note that you cannot modify the required attribute of existing CIs. |
| `size` | No | Specifies the property size. Possible values are: `default`, `small`, `medium`, and `large`. Large text fields will be shown as a text area in the XL Deploy GUI. *Only relevant for properties of kind `string`.* |
| `default` | No | Specifies the default value of the property. |
| `enum-class` | No | The enumeration class that contains the possible values for this property. *Only relevant for properties of kind `enum`.* |
| `referenced-type` | No | The type of the referenced CI. *Only relevant for properties of kind `ci`, `set_of_ci`, or `list_of_ci`.* |
| `as-containment` | No | Indicates whether the property is modeled as containment in the repository. If true, the referenced CI or CIs are stored under the parent CI. *Only relevant for properties of kind `ci`, `set_of_ci`, or `list_of_ci`.* |
| `hidden` | No | Indicates whether the property is hidden. Hidden properties do not appear in the XL Deploy GUI. Note that a hidden property must have a default value. |
| `transient` | No | Indicates whether the property is persisted in the repository or not. |
| `inspectionProperty` | No | Indicates that this property is used for inspection (discovery). |

**Note:** For security reasons, the `password` property of a CI cannot be modified.

### Hiding a CI property

The following XML snippet hides the `connectionTimeoutMillis` property for `Hosts` from the UI and gives it a default value:

    <type-modification type="base.Host">
        <property name="connectionTimeoutMillis" kind="integer" default="1200000" hidden="true" />
    </type-modification>

### Extending a CI

For example, you could add a notes field to a CI to record notes:

    <type-modification type="overthere.Host">
        <property name="notes" kind="string"/>
    </type-modification>

## Defining new CIs

It is also possible to define new CIs using this mechanism. By specifying a new type, its base (either a concrete Java class or another synthetic type) and namespace, a new type will become available in XL Deploy. This means the CI type can be a part of deployment packages and created in the Repository browser. Each of the three categories of CIs (deployables, deployeds and containers) can be defined this way.

The following information can be specified when defining a new type:

{:.table .table-striped}
| Property | Required | Description |
| -------- | -------- | ----------- |
| `type` | Yes | The CI type name. |
| `extends` | Yes | The parent CI type that this CI type inherits from. |
| `description` | No | Describes the new CI. |
| `virtual` | No | Indicates whether the CI is virtual (used to group together common properties) or not. Virtual CIs can not be used in a deployment package.
| `deployable-type` | No | The type of deployable CI type that this CI type deploys. *Only relevant for deployed CIs.* |
| `container-type` | No | The type of CI container that this CI type is deployed to. *Only relevant for deployed CIs.* |
| `generate-deployable` | No | The type of deployable CI to be generated. This property is specified as a nested element. *Only relevant for deployed CIs.* |

For each defined CI, zero or more properties can be specified. See the section above for more information about how to specify a property.

Here is an example for each of the CI categories.

### Defining a deployable CI

Usually, deployable CIs are generated by XL Deploy (see the `generate-deployable` element above). The following XML snippet defines a `tomcat.DataSource` CI and lets XL Deploy generate the deployable (`tomcat.DataSourceSpec`) for it:

    <type type="tomcat.DataSource" extends="tomcat.JndiContextElement" deployable-type="jee.DataSourceSpec"
              description="DataSource installed to a Tomcat Virtual Host or the Common Context">
            <generate-deployable type="tomcat.DataSourceSpec" extends="jee.DataSourceSpec"/>
            <property name="driverClassName"
                      description="The fully qualified Java class name of the JDBC driver to be used."/>
            <property name="url"
                      description="The connection URL to be passed to our JDBC driver to establish a connection."/>
    </type>

It is also possible to copy default values from the deployed type definition to the generated deployable type. Here is an example:

    <type type="tomcat.DataSource" extends="tomcat.JndiContextElement" deployable-type="jee.DataSourceSpec"
              description="DataSource installed to a Tomcat Virtual Host or the Common Context">
            <generate-deployable type="tomcat.DataSourceSpec" extends="jee.DataSourceSpec" copy-default-values="true"/>
            <property name="driverClassName"
                      description="The fully qualified Java class name of the JDBC driver to be used."
                      default="{% raw %}{{DATASOURCE_DRIVER}}{% endraw %}/">
            <property name="url"
                      description="The connection URL to be passed to our JDBC driver to establish a connection."
                      default="{% raw %}{{DATASOURCE_URL}}{% endraw %}/">
    </type>

**Note:** Properties that are either hidden, or of kind `ci`, `list_of_ci`, or `set_of_ci`, will not be copied to the deployable.

The following snippet shows an example of defining a deployable manually:

    <type type="acme.CustomWar" extends="jee.War">
        <property name="startApplication" kind="boolean" required="true"/>
    </type>

### Defining a container CI

This XML snippet shows how to define a new container CI:

    <type type="tc.Server" extends="generic.Container">
        <property name="home" default="/tmp/tomcat"/>
    </type>

### Defining a deployed CI

This XML snippet shows how to define a new deployed CI:

    <type type="tc.WarModule" extends="udm.BaseDeployedArtifact" deployable-type="jee.War"
          container-type="tc.Server">
        <generate-deployable type="tc.War" extends="jee.War"/>
        <property name="changeTicketNumber" required="true"/>
        <property name="startWeight" default="1" hidden="true"/>
    </type>

The `tc.WarModule` CI (a deployed) is generated when a `tc.War` (a deployable) is deployed to a `tc.Server` (a container). The new CI inherits all properties from the `udm.BaseDeployedArtifact` CI and adds the required property `changeTicketNumber`. The `startWeight` property is hidden from the user with a default value of 1.

### Defining an embedded CI

An embedded CI is a CI that is embedded (part of) another CI. The following XML snippet shows how to define an embedded CI that represents a portlet contained in a WAR file. The `tc.Portlet` embedded CI can be embedded in the `tc.WarModule` deployed CI, also shown:

    <type type="tc.Server" extends="udm.BaseContainer">
        <property name="host" kind="ci" referenced-type="overthere.Host" as-containment="true" />
    </type>

    <type type="tc.WarModule" extends="udm.BaseDeployedArtifact" deployable-type="jee.War"
          container-type="tc.Server">
        <property name="changeTicketNumber" required="true"/>
        <property name="startWeight" default="1" hidden="true"/>
        <property name="portlets" kind="set_of_ci" referenced-type="tc.Portlet" as-containment="true"/>
    </type>

    <type type="tc.War" extends="jee.War">
        <property name="changeTicketNumber" required="true"/>
        <property name="startWeight" default="1" hidden="true"/>
        <property name="portlets" kind="set_of_ci" referenced-type="tc.PortletSpec" as-containment="true"/>
    </type>

    <type type="tc.Portlet" extends="udm.BaseEmbeddedDeployed" deployable-type="tc.PortletSpec" container-type="tc.WarModule">
        <generate-deployable type="tc.PortletSpec" extends="udm.BaseEmbeddedDeployable" />
    </type>

The `tc.WarModule` has a `portlets` property that contains a set of `tc.Portlet` embedded CIs.

In a deployment package, a `tc.War` CI and its `tc.PortletSpec` CIs can be specified. When a deployment is configured, a `tc.WarModule` deployed is generated, complete with all its `tc.Portlet` portlets deployeds.

## Defining synthetic methods

In addition to defining CIs, it is also possible to define _methods_ on CIs. Each method can be executed on an instance of a CI via the GUI or CLI. Methods are used to implement _control tasks_, actions on CIs to control the middleware. An example is starting or stopping of a server. The CI itself is responsible for implementing the specified method, either in Java (see the section "Writing a Plugin" below) or synthetically when extending an existing plugin such as the Generic Model Plugin.

This XML snippet shows how to define a control task:

    <type type="tc.DeployedDataSource" extends="generic.ProcessedTemplate" deployable-type="tc.DataSource"
          container-type="tc.Server">
        <generate-deployable type="tc.DataSource" extends="generic.Resource"/>
        ...
        <method name="ping" description="Test whether the datasource is available"/>
    </type>

The _ping_ method defined above can be invoked on an instance of the `tc.DeployedDataSource` CI through the server REST interface, GUI or CLI. The implementation of the _ping_ method is part of the `tc.DeployedDataSource` CI.
