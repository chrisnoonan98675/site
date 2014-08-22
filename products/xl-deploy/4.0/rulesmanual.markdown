---
layout: product_manual
title: Rules Manual
---

## Preface

Rules allow you to specify behavior during deployment planning, which is when XL Deploy decides which steps belong in the deployment plan and how the steps are configured.

The deployment planning phase happens after the delta analysis and orchestration phases. At that point, the orchestrator has divided the plan into orchestrations (parallel, serial, interleaved) that contain the delta specifications. XL Deploy knows which deployables are to be deployed, but does not yet know the steps that are needed to deploy them.

The input for the planning phase is an orchestrated plan with a delta specification. Rules determine which steps are contributed to the plan based on the delta specification.

For more information about deployment planning, refer to the [Customization Manual](customizationmanual.html#deployments-and-plugins).

### Steps

A step is a concrete action that XL Deploy performs to accomplish a task. The plugins that are installed on the XL Deploy server contribute steps, which XL Deploy registers in its step registry.

Each step is identified by a name. When you create a rule, you refer to the step by its name. And every step has parameters, which are variable properties that can be determined during planning and passed to the step.

For more information about steps, refer to the [Reference Manual](referencemanual.html#step).

### Rules and the planning context

Rules receive input from the XL Deploy planning context, which allows them to interact with the deployment plan. Rules use the planning context to contribute steps to the deployment plan or to add checkpoints that are needed for rollbacks.

The result of evaluating a rule is either that:

* The planning context is not affected, or 
* Steps and side effects are added to the planning context

Typically, a rule only contributes steps to the plan in a few specific situations, when all of the conditions in its `<conditions>` section (if present) are met. Therefore, a rule will not always produce steps.

### Rules and scope

In addition to the planning context, the input that a rule receives depends on its [scope](#rules-scope), which determines when and how often is is applied. Some scopes mean that the rule will only be applied once, while others mean that it will be applied several times.

For example, a rule with the `deployed` scope is applied for every deployed; that is, for every delta. Therefore, it has access to delta information like the current operation (`CREATE`, `MODIFY`, `DESTROY`, or `NOOP`) and the current and previous instances of the deployed. The rule can use this information to determine whether XL Deploy needs to add a step to the deployment plan.

### How rules affect one another

Rules are applied one after another, depending on the scope. Rules operate in isolation, although they can share information through the planning context. Rules can not affect one another, but you can disable rules. Every rule must have a unique name.

### Where to define rules

You define rules in `xl-rules.xml`, which is stored in the `<XLDEPLOY_HOME>/ext` directory. XL Deploy plugin JARs can also contain `xl-rules.xml`. When the XL Deploy server starts, it scans all `xl-rules.xml` files and registers their rules.

### How to define rules

The `xl-rules.xml` file has the default namespace `xmlns="http://www.xebialabs.com/deployit/xl-rules"`. The root element must be `<rules>`. The `<rule>` and `<disable-rule>` elements are located under `<rules>`.

* Every rule must have a name that is unique across the whole system.
* Every rule must have a [scope](#rules-scope).

### Rescan the rules file

You can configure XL Deploy to rescan all rules on the server whenever you change the `ext/xl-rules.xml` file. To do so, edit the `file-watch` setting in the `<XLDEPLOY_HOME>/conf/planner.conf` file. For example, to check every 5 seconds if `xl-rules.xml` has been modified:
    
    xl {
        file-watch {
            interval = 5 seconds
        }
    }

By default, the interval is set to 0 seconds. This means that XL Deploy will not automatically reload the rule definitions.

If XL Deploy finds that `xl-rules.xml` has been modified, it will rescan all rules in the system.

**Note:** If you modify `planner.conf`, you must restart the XL Deploy server.
 
## Rule scope

When and how often a rule is applied depends on its scope: `pre-plan`, `deployed`, `plan`, or `post-plan`.

Scopes are ordered in the same order as the rule types that will be applied. The `pre-plan` and `post-plan` scopes run once, while the planning of the `deployed` and `plan`-scoped rules is iterated for all orchestrations.

**Important:** Be aware of the plan that steps are contributed to. Deployed rules and plan rules contribute to the same plan; therefore, the order of steps is important.

### Pre-plan scope

A rule with the `pre-plan` scope is applied exactly once, at the start of the planning stage. The steps that the rule contributes are added to a single plan that XL Deploy pre-pends to the final deployment plan.

### Deployed scope

A rule with the `deployed` scope is applied for deployed; that is, for every delta. Deltas are organized by orchestrations. The steps that the rule contributes are added to the plan related to the orchestration.

You must define a `<type>` and an `<operation>` in the `<conditions>` for each `deployed` rule. If a deployed matches the type and operation, XL Deploy adds the steps to the plan for the deployed.

### Plan scope

A rule with the `plan` scope is applied for every interleaved orchestration. The steps that the rule contributes are added to the plan related to the orchestration, along with the steps that are contributed by the deployeds in the orchestration.

For information about orchestrators, refer to the [Reference Manual](referencemanual.html#orchestrators).

### Post-plan scope

A rule with the `post-plan` scope is applied exactly once, at the end of the planning stage. The steps that the rule contributes are added to a single plan which XL Deploy appends to the final deployment plan.

## Types of rules

Rules define the way XL Deploy will plan your deployment. There are two types of rules:

* **XML rules** help you easily define a rule using common conditions such as deployed types, operations, or the result of evaluating an expression. XML rules also allow you to define how a step must be instantiated in XML.
* **Script rules** allow you to express rule logic in a Jython script. You can provide the same conditions as you can in XML rules. Depending on the scope of a script rule, it has access to the deltas or to the delta specification and the planning context.

The rules are comparable in functionality. Script rules are more powerful, but XML rules are more convenient because they define frequently used concepts that you most likely need. If you do not know which type of rule to use, try the XML rule first. If the XML rule is too restrictive, you can try a script rule.

## XML rules

An XML rule is fully specified using XML with XL Deploy internals and planning details. An XML rule uses the following format in `xl-rules.xml`:

 * A `rule` tag with required `name` and `scope` attributes, containing:
 * Optionally a `conditions` tag, holding the following:
    * A sequence of `type` tags that hold UDM type. These restrict the rule to a specified collection of types. This tag must be specified if the scope is `deployed`.   
    * A sequence of `operation` tags that hold operations. These restrict the rule to a specified collection of operations. Possible values are `CREATE`, `MODIFY`, `DESTROY`, and `NOOP`. This tag must be specified if the scope is `deployed`.   
    * An `expression` tag with a expression in Jython. A condition on which rule will be triggered. This tag is optional, but when specified, it must evaluate to a Boolean value. 
 * A `steps` tag containing a list of steps that will be added to the plan when this rule meets all conditions; that is, when its types and operation match and its `expression` evaluates to true (if present).
 
### Define steps in XML rules

Steps in XML rules are defined in the `<steps>` tag. There is no XML schema verification of the way that rules are defined, but there are guidelines that you must follow.

* The `<steps>` tag contains tags that must map to actual step names. For the list of names, refer to [Step Reference](referencesteps.html).
* Each step contains a list of parameter tags. The parameter tags must map to actual parameters of the defined step. For the list of step parameters, refer to [Step Reference](referencesteps.html).    
* Each parameter tag can contains: 
    * An expression that must evaluate to a value of the type of parameter; for example, `60` will evaluate to an `Integer`, but `"60"` will evaluate to a `String`. Each expression parameter must have the attribute `expression` set to `true`.
    * A string value that will be converted to the type of the step parameter. If the conversion fails, the step will not be created and the deployment planning will fail.
    * A collection of tags that represent a map with tag name as key and tag body as value.      
    * A collection of tags with name `<value>` that represent a list of values defined by each tag body.      
* The `<steps>` tag may contain a `<checkpoint>` tag that informs XL Deploy that the action the step takes must be undone in case of a rollback. For more information about checkpoints, refer to the [XL Deploy Java API Manual](xldeployjavaapimanual.html#checkpoints). 
    
All Jython expressions are executed in same context with the same available variables as Jython scripts in script rules.

Every step must have a `<description>` and an `<order>`. Literal strings such as the description must be enclosed in quotation marks (`"`).

#### Using dynamic data

You can use dynamic data in steps. For example, to incorporate a file name in a step description, use:

	<description expression="true">"Copy file " + deployed.file.name</description>

**Note:** Do not forget to set `expression` to `true` to enable dynamic data.

#### Escaping special characters

Because `xl-rules.xml` is an XML file, some expressions must be escaped. For example, you must use `myParam &lt; 0` instead of `myParam < 0`. Alternatively, you can wrap expressions in a `CDATA` section.

#### Using special characters in strings

You can set a step property to a string that contains a special character (such as a letter with an umlaut).

If the parameter is an expression, enclose the string with single or double quotation marks (`'` or `"`) and prepend it with the letter `u`. For example:

    <parameter-string expression="true">u'pingüino'</parameter-string>

If the parameter is not evaluated as an expression, no additional prefix is required. You can simply assign the value. For example: 
    
    <parameter-string>pingüino</parameter-string>
    
#### Using checkpoints

XL Deploy uses checkpoints to build rollback plans. 

Checkpoints are important for a rollback plan. XL Deploy does not know how to rollback a plan without checkpoints. Checkpoints can be used only in the following conditions:

* The scope of the rule must be `deployed`.
* You can set one checkpoint per rule.
* If a rule specifies a single `MODIFY` operation, you can:
    * Set two checkpoints for the rule.   
    * Use the attribute `completed` on the `checkpoint` tag to specify the operation that is actually performed for the step.

### Sample XML rule: Successfully created artifact

This is an example of a rule that is triggered for every deployed of type `udm.BaseDeployedArtifact` and operation `CREATE`. It results in the addition of step `success` with order `60` to the plan.

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
