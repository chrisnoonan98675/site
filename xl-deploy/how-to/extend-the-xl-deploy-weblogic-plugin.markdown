---
title: Extend the XL Deploy WebLogic plugin
categories:
- xl-deploy
subject:
- WebLogic plugin
tags:
- weblogic
- oracle
- middleware
- plugin
- extension
---

The [XL Deploy Oracle WebLogic (WLS) plugin](/xl-deploy/concept/weblogic-plugin.html) is designed to be extended through XL Deploy's plugin API type system and through the use of custom, user-defined WebLogic Scripting Tool (WLST) Python scripts.

The WebLogic plugin associates `CREATE`, `MODIFY`, and `DESTROY` operations received from XL Deploy with WLST Python scripts that need to be executed for the operation. The operation-specific script is given a Python object representation of the deployed that triggered the operation. The script is then executed on the target domain, using WLST.

## Sample `wls.Datasource` definition and script

This is a sample definition of `wls.Datasource` in the `synthetic.xml` file:

{% highlight xml %}
<type type="wls.DataSource" extends="wls.Resource" deployable-type="wls.DataSourceSpec">
    <generate-deployable type="wls.DataSourceSpec" extends="wls.ResourceSpec" />
	
    <property name="additionalPropertiesNotToExpose" hidden="true" default="jndiNames, url, driverName, username, password, properties"/>

    <property name="createScript"  default="wls/ds/create-datasource.py"  hidden="true" />
    <property name="destroyScript" default="wls/ds/destroy-datasource.py" hidden="true" />

    <property name="jndiNames"/>
    <property name="url"/>
    <property name="driverName"/>
    <property name="username"/>
    <property name="password" password="true"/>
</type>
{% endhighlight %}

**Tip:** For information about the configuration items (CIs) included in the plugin, refer to the [WebLogic plugin reference](/xl-deploy/latest/wlsPluginManual.html).

The script can use all information from the deployed to translate to the WLST API calls needed to configure WebLogic. The following sample Python snippet creates a datasource:

{% highlight python %}
cmo.createJDBCSystemResource(deployed.name)
datasourcePath = '/JDBCSystemResources/%s/JDBCResource/%s' % (deployed.name, deployed.name)
cd(datasourcePath)
cd('%s/JDBCDriverParams/%s' % (datasourcePath, deployed.name))
set("Url", deployed.url)
set("DriverName", deployed.driverName)
set('Password', deployed.password)
# use jmsModuleName, jmsServer and jndiName to create the queue
{% endhighlight %}

## Change the order of steps

The WebLogic plugin allows you to influence the order in which scripts are executed in relation to other deployed operations. The order allows you to chain scripts to create a logical sequence of events.

For example, the following `synthetic.xml` code indicates that the queue will be created (order = 60) before the EAR file is deployed (order = 70), and the queue will be destroyed (order = 40) after the EAR file is undeployed (order = 30).

{% highlight xml %}
<type type="wls.EarModule" extends="wls.ExtensibleDeployedArtifact" deployable-type="jee.Ear">
    <generate-deployable type="wls.Ear" extends="jee.Ear" />
	
    <property name="createScript" default="wls/application/deploy-application.py" hidden="true"/>
    <property name="createVerb" default="Deploy" hidden="true" />
    <property name="createOrder" kind="integer" default="70" hidden="true" />
	
    <property name="destroyScript" default="wls/application/undeploy-application.py" hidden="true"/>
    <property name="destroyVerb" default="Undeploy" hidden="true" />
    <property name="destroyOrder" kind="integer" default="30" hidden="true" />
	
    <property name="startScript" default="wls/application/start-application.py" hidden="true"/>
    <property name="startOrder" kind="integer" default="90" hidden="true" />
	
    <property name="stopScript" default="wls/application/stop-application.py" hidden="true"/>
    <property name="stopOrder" kind="integer" default="10" hidden="true" />
</type>
	
<type type="wls.Queue" extends="wls.AbstractQueue" deployable-type="wls.QueueSpec">
    <generate-deployable type="wls.QueueSpec" extends="wls.JmsResourceSpec"/>
	
    <property name="createScript" default="wls/jms/create-queue.py" hidden="true"/>
    <property name="createVerb" default="Create" hidden="true" />
    <property name="createOrder" kind="integer" default="60" hidden="true" />
	
    <property name="destroyScript" default="wls/jms/destroy-queue.py" hidden="true"/>
    <property name="destroyVerb" default="Destroy" hidden="true" />
    <property name="destroyOrder" kind="integer" default="40" hidden="true" />
	
    <property name="setErrorDestinationScript" default="wls/jms/set-error-queue.py" hidden="true"/>
    <property name="unsetErrorDestinationScript" default="wls/jms/unset-error-queue.py" hidden="true"/>
</type>
{% endhighlight %}

## Hide a property of a deployed or deployable

You can hide existing deployed and deployable properties in the XL Deploy user interface and give them default values. For example, the following `synthetic.xml` code shows how the `JDBCConnectionPoolParams_CapacityIncrement` property in `wls.Datasource` can be hidden and given a default value of 2.

{% highlight xml %}
<type-modification type="wls.DataSource">
    <!-- Makes the property hidden from the UI -->
    <property name="JDBCConnectionPoolParams_CapacityIncrement" category="Connection Pool" label="Capacity Increment" kind="integer" hidden="true" default="2"/>
</type-modification>
{% endhighlight %}

## Add a new property to a deployed or deployable

You can add new properties to deployeds and deployables. For example, the following `synthetic.xml` code shows how a new property called `inactiveConnectionTimeoutSeconds` can be added to `wls.Datasource`.

{% highlight xml %}
<type-modification type="wls.DataSource">
    <!-- Adding new property -->
    <property name="JDBCConnectionPoolParams_InactiveConnectionTimeoutSeconds"  category="Connection Pool" label="Inactive Connection Timeout (sec)" kind="integer" description="inactive Connection Timeout in Seconds" />
</type-modification>
{% endhighlight %}	

**Important:** When you add a new property in the WebLogic plugin, the property name should correspond to the relative path of the property (file) from the configuration item in WLST (without the type name). For example, because  the relative path of property `InactiveConnectionTimeoutSeconds` in WLST is `{datasource-name}/JDBCConnectionPoolParams/{datasource-name}/InactiveConnectionTimeoutSeconds`, the property name to use is `JDBCConnectionPoolParams_InactiveConnectionTimeoutSeconds`.

## Add a new CI type

You can add a new configuration item (CI) type to the WebLogic plugin. For example, the following `synthetic.xml` code shows the definition of a new CI type called `wls.WorkManager`. Because it is a resource and it can be targeted to a cluster or a server, it extends `wls.Resource`.

{% highlight xml %}
<type type="wls.WorkManager" extends="wls.Resource" deployable-type="wls.WorkManagerSpec">
    <generate-deployable type="wls.WorkManagerSpec" extends="wls.ResourceSpec"/>
	
    <property name="createScript"  default="wls/env/create-work-manager.py"  hidden="true" />
    <property name="destroyScript" default="wls/env/destroy-work-manager.py" hidden="true" />
</type>
{% endhighlight %}

The name property is automatically added to all CIs, so it has not been defined explicitly as a property. Additional properties can be added in the definition as needed.

## Add Python scripts for steps

The next step involves adding Python scripts for steps. For the `wls.WorkManager` example, two Python scripts need to be created: `create-work-manager.py` and `destroy-work-manager.py`.

### Sample create script

This is a sample `wls/env/create-work-manager.py` script:

{% highlight python %}
workManagerPath='/SelfTuning/%s/WorkManagers/%s' %(deployed.container.domain.name, deployed.name)
connectAndEdit()

if exists(workManagerPath):
    print 'Modifying work manager %s for target %s' % (deployed.name, deployed.container.name)
    setOrOverride = overrideWithWarning
else:
    print 'Creating work manager %s for target %s' % (deployed.name, deployed.container.name)
    cd('/SelfTuning/' + deployed.container.domain.name + '/WorkManagers')
    cmo.createWorkManager(deployed.name)
    setOrOverride = set

cd(workManagerPath)
newTargets = []
for t in get('Targets'):
    newTargets.append(t)

newTargets.append(ObjectName(deployed.container.objectName))
set('Targets', jarray.array(newTargets, ObjectName))

saveAndExit()
{% endhighlight %}

### Sample destroy script

This is a sample `wls/env/destroy-work-manager.py` script:

{% highlight python %}
workManagerPath='/SelfTuning/%s/WorkManagers/%s' %(deployed.container.domain.name, deployed.name)
connectAndEdit()

if not exists(workManagerPath):
    print "Work manager with name %s does not exist." %(deployed.name)
    sys.exit(1)

cd(workManagerPath)
currentTargets = get('Targets')
print 'oldTargets: %s' %(currentTargets)
containerTarget = ObjectName(deployed.container.objectName)
newTargets = []
for t in currentTargets:
    if t != containerTarget:
    newTargets.append(t)

print 'new targets: %s' %(newTargets)
if len(newTargets) > 0:
    print 'Modifying work manager %s' %(deployed.name)
    set('Targets', jarray.array(newTargets, ObjectName))
else:
    print 'Deleting workmanager %s' % (deployed.name)
    cd('../')
    delete(deployed.name, 'WorkManagers')

saveAndExit()
{% endhighlight %}

**Note:** In the above sample Python files, `connectAndEdit()`, `saveAndExit()` are utility functions defined in the `base.py` file in the WebLogic plugin. To see other utility functions, refer to the `base.py` file.
