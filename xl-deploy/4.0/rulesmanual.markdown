---
title: Rules Manual
---

## Preface

Rules allow you to specify behavior during deployment planning, which is when XL Deploy decides which steps belong in the deployment plan and how the steps are configured.

The deployment planning phase happens after the delta analysis and orchestration phases. At that point, the orchestrator has divided the plan into orchestrations (parallel, serial, interleaved) that contain the delta specifications. XL Deploy knows which deployables are to be deployed, but does not yet know the steps that are needed to deploy them.

The input for the planning phase is an orchestrated plan with a delta specification. Rules determine which steps are contributed to the plan based on the delta specification.

For more information about deployment planning, refer to the [Customization Manual](customizationmanual.html#deployments-and-plugins).


### Sample XML rule: Successfully created artifact

This is an example of a rule that is triggered for every deployed of type `udm.BaseDeployedArtifact` and operation `CREATE`. It results in the addition of step `success` with order `60` to the plan.

{% highlight xml %}
    <rules xmlns="http://www.xebialabs.com/xl-deploy/xl-rules">
        <rule name="SuccessBaseDeployedArtifact" scope="deployed">
            <conditions>
                <type>udm.BaseDeployedArtifact</type>
                <operation>CREATE</operation>
            <conditions>
            <steps>
                <success>
                    <order>60</order>
                    <description expression="true">'Success step for %s' % deployed.name</description>
                </success>
            </steps>
        </rule>  
     </rules>   
{% endhighlight %}

### Sample XML rule: Successfully deployed to Production

This is an example of an XML rule that is triggered once for the whole plan, when the deployment's target environment contains the word `Production`.

    <rules xmlns="http://www.xebialabs.com/xl-deploy/xl-rules">
        <rule name="SuccessBaseDeployedArtifact" scope="post-plan">
            <conditions>
                <expression>"Production" in context.deployedApplication.environment.name</expression>
            </condition>
            <steps>
                <success>
                    <order>60</order>
                    <description>Success step in Production environment</description>
                </success>
            </steps>
        </rule>
     </rules>   


### Sample XML rule: Using a checkpoint

This is an example of an XML rule that contains a checkpoint. XL Deploy will use this checkpoint to undo the rule's action if you roll back the deployment.

    <rule name="CreateBaseDeployedArtifact" scope="deployed">
        <conditions>
            <type>udm.BaseDeployedArtifact</type>
            <operation>CREATE</operation>
        </conditions>        
        <steps>
            <copy-artifact>
                <....>
            </copy-artifact>
            <checkpoint/>
        </steps>
    </rule>  

### Sample XML rule: Using checkpoints when operation is `MODIFY`

This is an example of an XML rule in which the operation is `MODIFY`. This operation involves two sequential actions—removing the old version of a file (`DESTROY`), then creating the new version (`CREATE`)—so two checkpoints are needed.

    <rule name="ModifyBaseDeployedArtifact" scope="deployed">
        <conditions>
            <type>udm.BaseDeployedArtifact</type>
            <operation>MODIFY</operation>
        </conditions>
        <steps>
            <delete>
                <....>
            </delete>
            <checkpoint completed="DESTROY"/>
    
            <upload>
                <....>
            </upload>
            <checkpoint completed="CREATE"/>
        </steps>
    </rule>  

## Script rules

A script rule is fully specified using a Jython script with XL Deploy internals and planning details.

**Note:** Script rules only run during the planning phase. They provide steps for the final plan to execute. They do not interact with XL Deploy's execution phase.

### Define steps in script rules

A script rule uses the following format in `xl-rules.xml`:

* A `rule` tag with required `name` and `scope` attributes, holding:
* Optionally a `conditions` tag containing the following:
    * A sequence of `type` tags that restrict the rule to a specific collection of UDM types. This tag must be specified if the scope is `deployed`.
    * A sequence of `operation` tags that restrict the rule to a specific collection of operations. Possible values are `CREATE`, `MODIFY`, `DESTROY`, and `NOOP`. This tag must be specified if the scope is `deployed`.
    * An `expression` tag, with an expression in Jython, that specifies a condition upon which rule will be triggered. This tag is optional, but when specified, it must evaluate to a Boolean value.
* Either:
    * `planning-script` child tag pointing to a script file that is available on the class path (in the `<XLDEPLOY_HOME>/ext/` directory)
 
Every script is run in isolation; that is, you cannot pass values directly from one script to another.


### Sample script rule: Successfully created artifact

This is an example of a script that is executed for every deployed that is involved in the deployment. The step with the name `success` will only be added for new deployeds (operation is `CREATE`) that derive from the type `udm.BaseDeployedArtifact`, as defined by the `type` element. Creating a step is done through the factory object `steps`. Addition of the step is performed through `context`.

    <rules xmlns="http://www.xebialabs.com/xl-deploy/rules">
        <rule name="SuccessBaseDeployedArtifact" scope="deployed">
            <conditions>
                <type>udm.BaseDeployedArtifact</type>
                <operation>CREATE</operation>
            </conditions>
            <planning-script>planning/SuccessBaseDeployedArtifact.py</planning-script>
        </rule>    

Where `planning/SuccessBaseDeployedArtifact.py`, which is stored in the `<XLDEPLOY_HOME>/ext/` directory, has following content:

    step = steps.success(description = "Example 1", order = 100)
    context.addStep(step)

## Define rule behavior

When you define an XML or script rule, you use expressions or scripts to define its behavior. These are written in Jython, a combination of Python and Java.

The data that is available for scripts to use depends on the scope of the rule:

<table class="deployed-matrix">
    <tr>
        <th>Object name</th> <th>Type</th> <th>Scope</th> <th>Description</th>
    </tr>
    <tr>
        <td>context</td> <td><a href="javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/planning/DeploymentPlanningContext.html">DeploymentPlanningContext</a></td> <td>all</td> <td>Use this to add steps and checkpoints to the plan</td>
    </tr>
    <tr>
        <td>deployedApplication</td> <td><a href="javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/udm/DeployedApplication.html">DeployedApplication</a></td> <td>all</td> <td>Specifies which application version will be deployed to which environment</td>
    </tr>
    <tr>
        <td>steps</td> <td> </td> <td>all</td> <td>Allows you to create steps from the <a href="#use-a-predefined-step">step registry</a></td>
    <tr>
        <td>specification</td> <td><a href="javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/DeltaSpecification.html">DeltaSpecification</a></td> <td>pre-plan<br/>post-plan</td> <td>Contains the delta specification for the current deployment; see the <a href="customizationmanual.html#the-planning-stage">Customization Manual</a> for more information</td>
    </tr>
    <tr>
        <td>delta</td> <td><a href="javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Delta.html">Delta</a></td> <td>deployed</td> <td>Whether the deployed should be created, modified, destroyed, or left unchanged (noop); see the <a href="customizationmanual.html#@Create,-@Modify,-@Destroy,-@Noop">Customization Manual</a> </td>
    </tr>
    <tr>
        <td>deployed</td> <td><a href="javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/udm/Deployed.html">Deployed</a></td> <td>deployed</td> <td>In the case of create, modify, or noop, this is the "current" deployed that the <code>delta</code> variable refers to; in the case of destroy, this is the "old" deployed</td>
    </tr>
    <tr>
        <td>deltas</td> <td><a href="javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Deltas.html">Deltas</a></td> <td>plan</td> <td>Collection of all <code>Delta</code>s in the current <code>InterleavedPlan</code></td>
    </tr>
    <tr>
        <td>controlService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.ControlService">ControlService</a></td> <td>all</td> <td>Gives you access to the ControlService</td>
    </tr>
    <tr>
        <td>deploymentService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.DeploymentService">DeploymentService</a></td> <td>all</td> <td>Gives you access to the DeploymentService</td>
    </tr>
    <tr>
        <td>inspectionService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.InspectionService">InspectionService</a></td> <td>all</td> <td>Gives you access to the InspectionService</td>
    </tr>
    <tr>
        <td>metadataService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.MetadataService">MetadataService</a></td> <td>all</td> <td>Gives you access to the MetadataService</td>
    </tr>
    <tr>
        <td>packageService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.PackageService">PackageService</a></td> <td>all</td> <td>Gives you access to the PackageService</td>
    </tr>
    <tr>
        <td>permissionService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.PermissionService">PermissionService</a></td> <td>all</td> <td>Gives you access to the PermissionService</td>
    </tr>
    <tr>
        <td>repositoryService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.RepositoryService">RepositoryService</a></td> <td>all</td> <td>Gives you access to the RepositoryService</td>
    </tr>
    <tr>
        <td>roleService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.RoleService">RoleService</a></td> <td>all</td> <td>Gives you access to the RoleService</td>
    </tr>
    <tr>
        <td>serverService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.ServerService">ServerService</a></td> <td>all</td> <td>Gives you access to the ServerService</td>
    </tr>
    <tr>
        <td>taskService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.TaskService">TaskService</a></td> <td>all</td> <td>Gives you access to the TaskService</td>
    </tr>
    <tr>
        <td>taskBlockService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.TaskBlockService">TaskBlockService</a></td> <td>all</td> <td>Gives you access to the TaskBlockService</td>
    </tr>
    <tr>
        <td>userService</td> <td><a class="api-doc-url" href="service/com.xebialabs.deployit.engine.api.UserService">UserService</a></td> <td>all</td> <td>Gives you access to the UserService</td>
    </tr>
    </tr>
        <tr>
        <td>logger</td> <td><a href="http://www.slf4j.org/api/org/slf4j/Logger.html">Logger</a></td> <td>all</td> <td>Allows you to access the XL Deploy logs. Prints logs to namespace `com.xebialabs.platform.script.Logging`</td>
    </tr>
</table>

### Syntax

This section describes the syntax that is available when you define rule behavior.

#### Accessing CI properties

To access configuration item (CI) properties, including synthetic properties, use the property notation. For example:

    name = deployed.container.myProperty

You can also refer to a property in the dictionary style, which is useful for dynamic access to properties. For example:

    name = deployed.container["myProperty"]

For full, dynamic read-write access to properties, you can access properties through the `values` object. For example:

    deployed.container.values["myProperty"] = "test"

#### Accessing deployeds

The delta and delta specification expose the previous and current deployed. To access the deployed that is going to be updated, use the `deployedOrPrevious` property:

    depl = delta.deployedOrPrevious
    app = specification.deployedOrPreviousApplication

#### Comparing delta operations and types

You can compare a delta operation to the constants `"CREATE"`, `"DESTROY"`, `"MODIFY"` or `"NOOP"` as follows:

    if delta.operation == "CREATE":
        pass

You can compare the CI `type` property to the string representation of the fully qualified type:

    if deployed.type == "udm.Environment":
        pass

## Disable a rule

You can disable any rule that is registered in XL Deploy's step registry, including rules that are predefined in XL Deploy, rules that are defined in the `<XLDEPLOY_HOME>/ext/xl-rules.xml` file, and rules that are defined in `xl-rules.xml` files in plugin JARs. 

To disable a rule, add the `disable-rule` tag under the `rules` tag in `xl-rules.xml`. You identify the rule that you want to disable by its name (this is why rule names must be unique).

For example, to disable a rule with name `deployArtifact`, use:

    <?xml version="1.0"?>
    <rules xmlns="http://www.xebialabs.com/xl-deploy/xl-rules">
        <disable-rule name="deployArtifact" />
    </rules>

### Predefined rule naming

You can disable the rules that are predefined in XL Deploy. This section describes the naming convention for each type of predefined rule.

#### Deployed system rules

* All methods of deployed classes that are annotated with `@Create`, `@Modify`, `@Destroy`, `@Noop` annotations.
* Name is defined by concatenation of the `udm` type of the deployed class, method name, and annotation name.
* Example: `file.DeployedArtifactOnHost.executeCreate_CREATE`

#### Contributor system rules

* All methods that are annotated with `@Contributor` annotations.
* Name is defined by concatenation of the full class name and method name.
* Example: `com.xebialabs.deployit.plugin.generic.container.LifeCycleContributor.restartContainers`

#### Pre-plan and post-plan system rules

* All methods that are annotated with `@PrePlanProcessor` or `@PostPlanProcessor` annotations.
* Name is defined by concatenation of the full class name and method name.
* Example: `com.xebialabs.deployit.plugins.releaseauth.planning.CheckReleaseConditionsAreMet.validate`

## Use a predefined step

The plugins that are bundled with XL Deploy contain predefined steps that you can use in rules. For information about the steps that are available, refer to the:

* [Step Reference](referencesteps.html)

### Calculated step parameters

For some predefined steps, XL Deploy calculates the values of parameters so you do not have to specify them (even for parameters that are required).

#### Order of a step

The `order` parameter of a step is calculated as follows:

* If the scope is `pre-plan`, `post-plan`, or `plan`, the `order` is 50
* If the scope is `deployed` and:
    * The operation is `CREATE`, `MODIFY`, or `NOOP` and:
        * The deployed is an [udm.Artifact](udmcireference.html), the `order` is 70
        * The deployed is **not** an [udm.Artifact](udmcireference.html), the `order` is 60
    * The operation is `DESTROY` and:
        * The deployed is an [udm.Artifact](udmcireference.html), the `order` is 30
        * The deployed is **not** an [udm.Artifact](udmcireference.html), the `order` is 40

The XL Deploy order convention is described in the [Customization Manual](customizationmanual.html#deployed-ci-processing).

#### Description of a step

The `description` parameter of a step is calculated as follows:

* If the scope is `deployed`, the `description` is calculated based on the `operation`, the name of the `deployed`, and the name of the `container`.
* If the scope is **not** `deployed`, the `description` cannot be automatically calculated and must be specified manually.

#### Target host

The `target-host` parameter of a step is calculated as follows:

* If the scope is `deployed` and:
    * `deployed.container` is of type [overthere.Host](remotingPluginManual.html#overthere.Host), the `target-host` is set to `deployed.container`.
    * `deployed.container` is of type [overthere.HostContainer](remotingPluginManual.html), the `target-host` is set to `deployed.container.host`.
* In other cases, `target-host` cannot be automatically calculated and must be specified manually.

#### Source artifact

The `source-artifact` parameter of a step is calculated as follows:

* If the scope is `deployed` and `deployed` is of type [udm.Artifact](udmcireference.html), the `source-artifact` is set to `deployed`.
* In other cases, `source-artifact` cannot be automatically calculated and must be specified manually.

#### Freemarker Context

The `freemarker-context` parameter of a step is calculated as follows:

* If the scope is `deployed`, the `freemarker-context` is enriched with a `deployed` instance that is accessible in a freemarker template by name `deployed`.
* In other cases, `freemarker-context` is not automatically calculated.

#### Python Context

The `python-context` parameter of a step is calculated as follows:

* If the scope is `deployed`, the `python-context` is enriched with a `deployed` instance that is accessible in a python script by binding `deployed`.
* In other cases, `python-context` is not automatically calculated.

## Define a custom step

If the predefined steps in XL Deploy do not provide the functionality that you need, you can define custom steps and create rules that refer to them. 
Please refer to [Define a custom step](xldeployjavaapimanual.html#define-a-custom-step-for-rules).  

## Troubleshooting and best practices

### Namespace

To avoid name clashes between different plugins you have created or acquired, it is recommended you use a namespace for your rules based on your company name For example:

    <rule name="com.mycompany.xl-rules.createFooResource" scope="deployed">...</rule>

### Logging

To see more information during the planning phase, you can increase the logging output:

1. Open `<XLDEPLOY_HOME>/conf/logback.xml` for editing.
1. Add a statement such as `<logger name="com.xebialabs.deployit.deployment.rules" level="debug" />` or `<logger name="com.xebialabs.deployit.deployment.rules" level="trace" />`.
1. Start using logging in Jython scripts by means of `logger` object. See [Define rule behavior](#define-rule-behavior)
