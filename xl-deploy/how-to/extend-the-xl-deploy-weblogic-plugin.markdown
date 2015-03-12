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

The Oracle WebLogic (WLS) plugin is designed to be extended through XL Deploy's plugin API type system and through the use of custom user defined WLST Python scripts.

The WLS plugin associates `CREATE`, `MODIFY`, and `DESTROY` operations received from XL Deploy with WLST Python scripts that need to be executed for the operation. The operation specific script is given a Python object representation of the deployed that triggered the operation. The script is then executed using WLST on the target domain. Below, for example is the definition of `wls.Datasource` in synthetic.xml:

	<type type="wls.DataSource" extends="wls.Resource" deployable-type="wls.DataSourceSpec">
        <generate-deployable type="wls.DataSourceSpec" extends="wls.ResourceSpec" />
		
		<property name="additionalPropertiesNotToExpose" hidden="true" 
		  default="jndiNames, url, driverName, username, password, properties"/>

		<property name="createScript"  default="wls/ds/create-datasource.py"  hidden="true" />
		<property name="destroyScript" default="wls/ds/destroy-datasource.py" hidden="true" />

		<property name="jndiNames"/>
		<property name="url"/>
		<property name="driverName"/>
		<property name="username"/>
		<property name="password" password="true"/>
	</type>

The script has all the information from the Deployed at its disposal to translate into the WLST API calls needed to configure WebLogic. The following sample Python snippet is using deployed to create a datasource:
	
	cmo.createJDBCSystemResource(deployed.name)
	datasourcePath = '/JDBCSystemResources/%s/JDBCResource/%s' % (deployed.name, deployed.name)
	cd(datasourcePath)
	cd('%s/JDBCDriverParams/%s' % (datasourcePath, deployed.name))
	set("Url", deployed.url)
	set("DriverName", deployed.driverName)
	set('Password', deployed.password)
	# use jmsModuleName, jmsServer and jndiName to create the queue

The WLS plugin also offers the ability to influence the order in which scripts are executed in relation to other Deployed operations. The order allows for the chaining of scripts to create a logical sequence of events. For example, the following synthetic.xml snippet says that creation of the queue (order = 60) will happen before deployment of the Ear (order = 70), and the destruction of the queue (order = 40) will take place the after undeployment of the Ear (order = 30).

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

## Hide an existing property from a deployed or deployable

The following `synthetic.xml` snippet shows how the `JDBCConnectionPoolParams_CapacityIncrement` property in `wls.Datasource` can be hidden, giving it a default value of 2.

	<type-modification type="wls.DataSource">
        <!-- Makes the property hidden from the UI -->
	    <property name="JDBCConnectionPoolParams_CapacityIncrement" category="Connection Pool" 
	              label="Capacity Increment" kind="integer" hidden="true" default="2"/>
	</type-modification>

## Add a new property to a deployed or deployable

The following `synthetic.xml` snippet shows how a new property `inactiveConnectionTimeoutSeconds` can be added to `wls.Datasource`.

	<type-modification type="wls.DataSource">
        <!-- Adding new property -->
        <property name="JDBCConnectionPoolParams_InactiveConnectionTimeoutSeconds"  category="Connection Pool" 
                  label="Inactive Connection Timeout (sec)" kind="integer" 
                  description="inactive Connection Timeout in Seconds" />
	</type-modification>	

**Important:** When you add a new property in WLS plugin, the property name should correspond to the relative path of the property (file) from the configuration item in WLST (minus the type name).

For example, because  the relative path of property `InactiveConnectionTimeoutSeconds` in WLST is `{datasource-name}/JDBCConnectionPoolParams/{datasource-name}/InactiveConnectionTimeoutSeconds`, the property name to use while adding a new property is `JDBCConnectionPoolParams_InactiveConnectionTimeoutSeconds`.

## Add a new type

The following `synthetic.xml` snippet shows the definition of a new CI type `wls.WorkManager`. Because it is a resource and it can be targeted to a cluster or a server, it has been made to extend `wls.Resource`.

    <type type="wls.WorkManager" extends="wls.Resource" deployable-type="wls.WorkManagerSpec">
		<generate-deployable type="wls.WorkManagerSpec" extends="wls.ResourceSpec"/>
		
        <property name="createScript"  default="wls/env/create-work-manager.py"  hidden="true" />
		<property name="destroyScript" default="wls/env/destroy-work-manager.py" hidden="true" />
    </type>

The name property is automatically added to all CIs so it has not been defined explicitly as a property. Additional properties can be added in the definition as per the need.

Next step involves adding the Python scripts for the steps. For the `wls.WorkManager` example, two Python scripts needs to be created: `create-work-manager.py` and `destroy-work-manager.py`.

### `wls/env/create-work-manager.py`

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

### `wls/env/destroy-work-manager.py`

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

**Note:** In the above example Python files, functions `connectAndEdit()`, `saveAndExit()` are utility functions defined in the `base.py` file in WLS plugin. Have a look at the `base.py` file to see other utility functions.
