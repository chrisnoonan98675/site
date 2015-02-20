---
title: Extend the XL Deploy WebSphere Application Server plugin
categories:
- xl-deploy
subject:
- WebSphere Application Server plugin
tags:
- websphere
- middleware
- plugin
- extension
---

The WAS plugin is designed to be extended through XL Deploy's plugin API type system and through the use of custom, user-defined WAS
Python scripts.

The WAS plugin associates **Create**, **Modify**, **Destroy** and **Inspect** operations received from XL Deploy with WAS Python scripts that need to be executed for the specific operation to be performed. The operation specific script is given a Python object representation of the `Deployed` that triggered the operation. The script is then executed using `wsadmin`.

There also exists an advanced method to extend the WAS plugin, but the implementation of this form of extension needs to be written in the Java programming language and consists of writing so-called `Deployed contributors`, `PlanPreProcessors` and `Contributors`.

## Extending using XML and Python scripts

The easiest way to extend the WAS plugin is by using XML and Python scripts. This method does not require you to write Java code. Extending the behavior of the plugin is done by simply defining the necessary deployables and deployeds for the specific environment. When XL Deploy starts up, it reads a file called `synthetic.xml` from the server class-path (that is, the `ext` directory of the server).

This file contains the type definitions of the deployables and the deployeds, i.e. the information about the types and their properties. In addition to that, it defines which Python scripts should be executed for a particular operation, as mentioned above. The scripts have all the information from the `Deployed` at their disposal to perform their work.

As an example, this is the type definition of a virtual host as it appears in `synthetic.xml`:

    <type type="was.VirtualHost" extends="was.Resource" deployable-type="was.VirtualHostSpec" container-type="was.Cell">
        <generate-deployable type="was.VirtualHostSpec" extends="was.Deployable" />
        <property name="createScript" default="was/virtualhost/create-virtual-host.py" hidden="true" />
        <property name="destroyScript" default="was/virtualhost/destroy-virtual-host.py" hidden="true" />
        <property name="inspectScript" default="was/virtualhost/inspect-virtual-host.py" hidden="true" />
        <property name="aliases" kind="set_of_string" description="Virtual host aliases - enter alias as: hostname:port" />
    </type>

Looking at this type definition more closely, it indicates that a virtual host, the type of which is `was.VirtualHost` will be created on its target infrastructure, called container, with a `container-type` of `was.Cell`. On WAS a virtual host may be created on a cell and that is exactly what this definition means.

The attribute `extends` tells the plugin what resource is extended by this definition. In this case it is a simple basic resource so it extends the type `was.Resource`. Since multiple virtual hosts may be created this way, each with its own set of properties, a specification of what will be deployed is created, a so-called `deployable-type`, and is itself of type `was.VirtualHostSpec`.

Within the type definition, there is the possibility of specifying properties of exactly how the virtual host is to be created. The first property is called `createScript` and specifies the script to be executed by `wsadmin` for the creation of the virtual host. An extension of this plugin could specify a different creation script. The plugin comes with a default creation script (`createScript`):

### `create-virtual-host.py`

    import re

    pattern = re.compile('^[^:]+:\d{,5}')
    virtualHostParent = AdminConfig.getid('/Cell:%s/' % (deployed.container.cellName))
    attributes = [['name', deployed.name]]
    attributes.append(['aliases', [[['hostname', alias.split(':')[0]], ['port', alias.split(':')[1]]]
                        for alias in deployed.aliases if pattern.match(alias) != None]])
    re.purge()

    print "Creating virtual host %s on target scope %s with attribute(s) %s" % (deployed.name, virtualHostParent, attributes)
    AdminConfig.create('VirtualHost', virtualHostParent, attributes)

This script also shows that `aliases` are also created for the specified virtual host. Aliases may be specified using the property `aliases` and it takes a set of strings. An example of this property is:

    <property name='aliases' kind='set_of_string' value='www.my-domain.com:80,www.proxy-domain.com:8443'/>

When the script executes a predefined variable `deployed` has a reference to the deployed that is being deployed.

The script executed on the host is created by appending a number of library scripts and adding the script from the type last. Per default you will get the following runtime scripts added:

* From the Python plugin: `python/runtime/base.py`
* From the WAS plugin: `was/runtime/base.py`
* From the deployed itself scripts defined in the `libraryScripts` hidden property

In addition to a creation script, a destruction script (`destroyScript`) must also be specified:

### `destroy-virtual-host.py`

    virtualHostContainmentPath = '/Cell:%s/VirtualHost:%s' % (deployed.container.cellName, deployed.name)
    virtualHostId = validateNotEmpty(AdminConfig.getid(virtualHostContainmentPath),
    "Cannot find virtual host with id: %s" % (virtualHostContainmentPath))

    print "Destroying virtual host %s" % (deployed.name)
    AdminConfig.remove(virtualHostId)

Also for the destroy operation a predefined variable `deployed` is available with the deployed being destroyed.

Finally, a modification script (`modifyScript`) may also be specified. If that is not present, the destruction script is invoked to remove the resource with the old settings and then the creation script is invoked to create the resource with the new settings.

The modify script also has access to the `deployed` variable.

### Inspection

The WAS plugin includes a property called `inspectScript`. When the Domain manager of WAS is known, it is possible for XL Deploy to  discover on its own most of the WAS topology automatically. This specific script is called upon by XL Deploy when it is trying, in our example, to  discover a virtual host on a WAS cell. This is a simplified script implementation shipped with the WAS plugin:

#### `inspect-virtual-host.py`

    virtualhosts = AdminConfig.list('VirtualHost')
    if virtualhosts != "":
        for virtualhost in virtualhosts.splitlines():
            if virtualhost.find('/nodes/') == -1:
                virtualHostName = AdminConfig.showAttribute(virtualhost, 'name')
                virtualHostId = '%s/%s' % (container.id, virtualHostName)
                discovered(virtualHostId, 'was.VirtualHost')
                virtualHostContainmentPath = '/Cell:%s/VirtualHost:%s' % (container.cellName, virtualHostName)
                virtualHostAliases = AdminConfig.getid(virtualHostContainmentPath + '/HostAlias:/').split()
                inspectedProperty(virtualHostId, 'aliases', [
                    AdminConfig.showAttribute(a, 'hostname') + ':' + AdminConfig.showAttribute(a, 'port')
                    for a in virtualHostAliases if a != ""])
                inspectedItem(virtualHostId)

An inspection script generally consists of a number of steps:

* Collect information from WebSphere.
* Indicate to XL Deploy that an object with a certain configuration item ID and a certain type was detected. This is achieved by calling `discovered` with the configuration item ID that the object has to get inside XL Deploy and the type.
* Set the properties on the object. This is done by repeatedly calling `inspectedProperty`. `inspectedProperty` takes three parameters, the ID of the configuration item being discovered, the name of the property and the value. The value should be representable as either a string, or a list of strings.
* When the complete object has been processed this is indicated to XL Deploy by calling `inspectedItem` with the configuration item ID.

The `discovered`, `inspectedProperty` and `inspectedItem` functions are defined in the Python plugin's `python/runtime/base.py`. This file also contains some additional helper functions.

Discovery starts at the Cell level. If you need to inspect other levels of the hierarchy you need to traverse the children of the Cell yourself. For convenience a function `findAllContainers` is defined in the runtime script `runtime/base.py`.

When the discovery script executes two predefined variables are available:

* `prototype` contains a 'prototype' deployed which can be used to get the type of the CI being discovered
* `container` contains the Cell

### Adding a property

The architecture of the WAS plugin enables the transfer of properties from the deployed (`was.VirtualHost` in this example) to the accompanying Python scripts defined with the properties `createScript` and `destroyScript`. In order for this to be possible, the properties are bound to an object called `deployed` and can be accessed as `deployed.<property-name>`. This can be seen in the creation script, where the property `aliases` is available as  `deployed.aliases` and the name of the virtual host as `deployed.name`. Using this convention as many properties as needed by the scripts can be  bound to the type definition. Care should be taken that the scripts use the same type as specified in the definition, so if a property defines a `kind=integer`, the script should also treat the value of this property as being of type `integer`.

An example of adding another property is:

    <property name='index-range' kind='integer' value='999' description='maximum index of an array of 1000 items'/>

### Fixing a property

In the case where it is necessary that a specific property should always contain a fixed predefined value, the attribute `hidden=true` should be used on the definition of the property. This way an end user, who is performing the deployment using the XL Deploy user interface, will not be able to see this property and therefore not have the possibility of modifying it. Usually this is done for the Python scripts which once written are not allowed to be changed by an end-user.

Using the example of the previous section and wanting to fix the maximum range, the example becomes:

    <property name='index-range' kind='integer' value='999' hidden='true' description='maximum index of an array of 1000 items'/>

### Excluding a property

If you do not want certain properties of a deployable to be exposed to Python scripts, you can use the hidden property `additionalPropertiesNotToExpose` to exclude them. For example:

      <type-modification type="was.WmqQueue">
            <property name="additionalPropertiesNotToExpose" default="jmsProvider,wasType,customProperties" hidden="true" />
      </type-modification>

### Execution order

A deployment process consists of a series of steps that are executed sequentially. Plugins offer the ability to influence the order of execution of the steps contributed to the deployment process in relation to other contributed steps and operations, not necessarily contributed by the same plugin(s), that are part of the deployment process.

The order of execution allows for the chaining of scripts or operations to create a logical sequence of events. In order to specify the order, properties with the  name of `createOrder` and `destroyOrder` may be used with an attribute called `default` to specify the order ordinal. For example, the following `synthetic.xml` snippet says that creation of the virtual host, `default=60`, will happen before any step with a higher order, for example `default=70`, but after any step with a lower order, i.e. lower than `default=60`.

    <type type="was.VirtualHost" extends="was.Resource" deployable-type="was.VirtualHostSpec" container-type="was.Cell">
        <generate-deployable type="was.VirtualHostSpec" extends="was.Deployable" />

        <property name="createScript" default="was/virtualhost/create-virtual-host.py" hidden="true" />
        <property name="createVerb" default="Deploy" hidden="true" />
        <property name="createOrder" kind="integer" default="60" hidden="true" />

        <property name="destroyScript" default="was/virtualhost/destroy-virtual-host.py" hidden="true" />
        <property name="destroyVerb" default="Undeploy" hidden="true" />
        <property name="destroyOrder" kind="integer" default="30" hidden="true" />

        <property name="inspectScript" default="was/virtualhost/inspect-virtual-host.py" hidden="true" />
        <property name="aliases" kind="set_of_string" description="Virtual host aliases - enter alias as: hostname:port" />
    </type>

Notice that the `destroyOrder` has a low order, because when executing a deployment the virtual host should be destroyed before it can be created again.

## Extending the plugin with custom control task

The plugin has the capability to add control tasks to `was.ExtensibleDeployed` or `python.PythonManagedContainer`.  The control task can be specified as a Python script that will be executed using `wsadmin` on the target host or as an OS shell script that will be run on the target host. The OS shell script is first processed with FreeMarker before being executed.

### Creating a Python script control task to test datasources

 `synthetic.xml` snippet:

    <type-modification type="was.Datasource">
      <method name="testDatasource" script="was/resources/ds/test-ds.py" language="python"/>
    </type>

 `test-ds.py` snippet:

    datasource = AdminConfig.getid("%s/JDBCProvider:%s/DataSource:%s/" %
     (deployed.container.containmentPath, deployed.jdbcProvider, deployed.name))
    if datasource == '':
      print "WARN: No JDBC DataSource '%s' found. Nothing to do" % (deployed.name)
    else:
      print "Testing JDBC DataSource '%s' (config ID '%s')" % (deployed.name, datasource)
      AdminControl.testConnection(datasource)

### Creating an OS script control task to start the `DeploymentManager`

 `synthetic.xml` snippet:

    <type-modification type="was.DeploymentManager">
      <method name="start" script="was/container/start-dm" language="os"/>
    </type-modification>

 `start-dm.sh snippet` for Unix:

    ${container.wasHome}/bin/startManager.sh

 `start-dm.bat` snippet for Windows:

    ${container.wasHome}\bin\startManager.bat
