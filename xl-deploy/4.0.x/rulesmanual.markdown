
---
title: Rules Manual
---

## Preface

When preparing a deployment, XL Deploy must determine which actions, or steps, to take for the deployment, and in what order. This happens in three phases:

1. *Delta analysis* determines which deployables are to be deployed, resulting in a delta specification for the deployment.
2. *Orchestration* determines the order in which deployments of the deployables should happen (serially, in parallel, or interleaved).
3. *Planning* determines the specific steps that must be taken for the deployment of each deployable in the interleaved plan.

XL Deploy's *rules* system works with the planning phase. Rules allow you to use XML or Jython to specify the steps that belong in a deployment plan and how the steps are configured.

This manual describes how the rules system works and how you can influence the steps that will be included in the deployment plan. For more information about the deployment preparation process, refer to the [Customization Manual](customizationmanual.html#deployments-and-plugins).

### Orchestration

Orchestration is important in the planning of a deployment. Orchestration is not part of the planning phase itself; rather, it happens immediately before the planning phase and after the delta analysis phase, and its output is used as input for how planning is done.
 
Delta analysis first determines which deployables need to be deployed, modified, deleted, or to remain unchanged. Each of these is called a `delta`. Orchestration determines the order in which the deltas should be processed. The result of orchestration is a tree-like structure of sub-plans, each of which is:

* A serial plan that contains other plans that will be executed one after another;
* A parallel plan that contains other plans that will be executed at the same time; or
* An interleaved plan that will contain the specific deployment steps after planning is done

The leaf nodes of the full deployment plan are always interleaved plans, and it is on these that the planning phase acts.

Planning provides steps for an interleaved plan, and this is done by invoking rules. Some rules will trigger depending on the delta under planning, while others may trigger independent of any delta. When a rule is triggered, it may or may not add one or more steps to the interleaved plan under consideration.

## Rule definitions

### Where to define rules

You define and [disable](#disable-a-rule) rules in `xl-rules.xml`, which is stored in the `<XLDEPLOY_HOME>/ext` directory. XL Deploy plugin JARs can also contain `xl-rules.xml`. When the XL Deploy server starts, it scans all `xl-rules.xml` files and registers their rules.

### How to define rules

Each rule:
 
* Must have a name that is unique across the whole system
* Must have a [scope](#rule-scope)
* Must define the conditions under which it will run
* Can use the planning context to influence the resulting plan
 
The `xl-rules.xml` file has the default namespace `xmlns="http://www.xebialabs.com/deployit/xl-rules"`. The root element must be `rules`, under which `rule` and `disable-rule` elements are located.

### Rescan the rules file

You can configure XL Deploy to rescan all rules on the server whenever you change the `<XLDEPLOY_HOME>/ext/xl-rules.xml` file. To do so, edit the `file-watch` setting in the `<XLDEPLOY_HOME>/conf/planner.conf` file. For example, to check every 5 seconds if `xl-rules.xml` has been modified:
    
    xl {
        file-watch {
            interval = 5 seconds
        }
    }

By default, the interval is set to 0 seconds. This means that XL Deploy will not automatically rescan the rules when `<XLDEPLOY_HOME>/ext/xl-rules.xml` changes.

If XL Deploy is configured to automatically rescan the rules and it finds that `xl-rules.xml` has been modified, it will rescan all rules in the system. By auto-reloading the rules, it is easy to do some experimenting until you are satisfied with your set of rules.

**Note:** If you modify `planner.conf`, you must restart the XL Deploy server.

## Rules and steps

A step is a concrete action that XL Deploy performs to accomplish a task, such as *delete a file* or *execute a PowerShell script*. The plugins that are installed on the XL Deploy server define several step types and may also define rules that contribute steps to the plan. If you define your own rules, you can reuse the step types defined by the plugins.

You can also disable rules defined by the plugins. Refer to [Disable a rule](#disable-a-rule) for more information.

Each step type is identified by a name. When you create a rule, you can add a step by referring to the step type's name. 

Also every step has parameters, which are variable properties that can be determined during planning and passed to the step. The parameters that a step needs depend on the step type, but they all have at least an *order* and a *description*. The order determines when the step will run, and the description is how the step will be named when you inspect the plan.

For more information about steps, refer to the [Reference Manual](referencemanual.html#step).

## Rules and the planning context

Rules receive a reference to the XL Deploy planning context, which allows them to interact with the deployment plan. Rules use the planning context to contribute steps to the deployment plan or to add checkpoints that are needed for rollbacks.

The result of evaluating a rule is either that:

* The planning context is not affected, or 
* Steps and side effects are added to the planning context

Typically, a rule only contributes steps to the plan in a few specific situations, when all of the conditions in its `conditions` section (if present) are met. Therefore, a rule will not always produce steps.

## How rules affect one another

Rules are applied one after another, depending on the scope. Rules operate in isolation, although they can share information through the planning context. Rules can not affect one another, but you can disable rules. Every rule must have a name that is unique across the system.

## Rule scope

The input that a rule receives depends on the planning context and on the rule's scope. The scope determines when and how often the rule is applied.

For example, a rule with the `deployed` scope is applied for every delta in the interleaved plan and has access to delta information such as the current operation (`CREATE`, `MODIFY`, `DESTROY`, or `NOOP`) and the current and previous instances of the deployed. The rule can use this information to determine whether it needs to add a step to the deployment plan.

**Important:** Be aware of the plan that steps are contributed to. Deployed rules and plan rules contribute to the same plan; therefore, the order of steps is important.

### Pre-plan scope

A rule with the `pre-plan` scope is applied exactly once, at the start of the planning stage. The steps that the rule contributes are added to a single plan that XL Deploy pre-pends to the final deployment plan. A pre-plan-scoped rule is independent of deltas; however, it receives a reference to the complete delta specification of the plan, which it can use to determine whether it should add steps to the plan.

### Deployed scope

A rule with the `deployed` scope is applied for each deployed in this interleaved plan; that is, for each delta. The steps that the rule contributes are added to the interleaved plan.

You must define a `type` and an `operation` in the `conditions` for each deployed-scoped rule. If a delta matches the type and operation, XL Deploy adds the steps to the plan for the deployed.

### Plan scope

A rule with the `plan` scope is applied once for every interleaved orchestration. It is independent of any single delta; however, it receives information about the deltas that are involved in the interleaved plan, which it can use to determine whether it should add steps to the plan.

The steps that the rule contributes are added to the interleaved plan related to the orchestration, along with the steps that are contributed by the deployeds in the orchestration.

For information about orchestrators, refer to the [Reference Manual](referencemanual.html#orchestrators).

### Post-plan scope

A rule with the `post-plan` scope is applied exactly once, at the end of the planning stage. The steps that the rule contributes are added to a single plan that XL Deploy appends to the final deployment plan. A post-plan-scoped rule is independent of deltas, however, it receives a reference to the complete delta specification of the plan, which it can use to determine whether it should add steps to the plan.

## Types of rules

 There are two types of rules:

* **XML rules** allow you to define a rule using common conditions such as deployed types, operations, or the result of evaluating an expression. XML rules also allow you to define how a step must be instantiated by only writing XML.
* **Script rules** allow you to express rule logic in a Jython script. You can provide the same conditions as you can in XML rules. Depending on the scope of a script rule, it has access to the deltas or to the delta specification and the planning context.

The rule typess are comparable in functionality. XML rules are more convenient because they define frequently used concepts in a simple way. Script steps are more powerful because they can include additional logic.
 
If you do not know which type of rule to use, try an XML rule first. If the XML rule is too restrictive, try a script rule.

## XML rules

An XML rule is fully specified using XML and has the following format in `xl-rules.xml`:

 * A `rule` tag with `name` and `scope` attributes, both of which are required.
 * A `conditions` tag with:
    * One or more `type` tags that identify the UDM types that the rule is restricted to. `type` is required if the scope is `deployed`; otherwise, you must omit it. The UDM type name must refer to a *deployed* type (not a *deployable*, *container*, or other UDM type).
    * One or more `operation` tags that identify the operations that the rule is restricted to. The operation can be `CREATE`, `MODIFY`, `DESTROY`, or `NOOP`. `operation` is required if the scope is `deployed`; otherwise, you must omit it.
    * An optional `expression` tag with an expression in Jython that defines a condition upon which the rule will be triggered. This tag is optional for all scopes. If you specify an `expression`, it must evaluate to a Boolean value. 
 * A `steps` tag that contains a list of steps that will be added to the plan when this rule meets all conditions; that is, when its types and operations match and its `expression` (if present) evaluates to true. Each step to be added is represented by an XML tag specifying the step type and step parameters such as `upload` or `powershell`.
 
### Define steps in XML rules

Steps in XML rules are defined in the `steps` tag. There is no XML schema verification of the way that rules are defined, but there are guidelines that you must follow.

* The `steps` tag contains tags that must map to step names, which you can find in the [Steps Reference](referencesteps.html).
* Each step contains parameter tags that must map to the parameters of the defined step, which you can find in the [Steps Reference](referencesteps.html).    
* Each parameter tag can contain:
    * A string value that will be automatically converted to the type of the step parameter. If the conversion fails, the step will not be created and the deployment planning will fail.
    * A Jython expression that must evaluate to a value of the type of the step parameter. For example, the expression `60` will evaluate to an `Integer` value, but `"60"` will evaluate to a `String` value. If you use an expression, the surrounding parameter tag must contain the attribute `expression="true"`.
    * In the case of map-valued parameters, you can specify the map with sub-tags. Each sub-tag will result in a map entry with the tag name as key and the tag body as value. Also, you can specify `expression="true"` to place non-string values into a map.
    * In the case of list-valued parameters, you can specify the list with `value` tags. Each tag results in a list entry with the value defined by the tag body. Also, you can specify `expression="true"` to place non-string values into a list.      
* The `steps` tag may contain a `checkpoint` tag that informs XL Deploy that the action the step takes must be undone in the case of a rollback. For more information about checkpoints, refer to the [XL Deploy Java API Manual](xldeployjavaapimanual.html#checkpoints).
    
All Jython expressions are executed in same context with the same available variables as Jython scripts in [script rules](#script-rules).

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

XL Deploy uses checkpoints to build rollback plans. Checkpointing is explained more fully in the [XL Deploy Java API Manual](xldeployjavaapimanual.html#checkpoints). The rules system allows you to define checkpoints by inserting a `<checkpoint>` tag immediately after the tag for the step on which you want the checkpoint to be set. Checkpoints can be used only in the following conditions:

* The scope of the rule must be `deployed`.
* You can set one checkpoint per rule.
* If a rule specifies a single `MODIFY` operation, you can:
    * Set two checkpoints: One for the creation part and one for the deletion part of the modification (if applicable).
    * Use the attribute `completed="DESTROY"` or `completed="CREATE"` on the `checkpoint` tag to specify the operation that is actually performed for the step.

### Sample XML rule: Successfully created artifact

This is an example of a rule that is triggered for every deployed of type `udm.BaseDeployedArtifact` or `udm.BaseDeployed` and operation `CREATE`. It results in the addition of a `noop` step (a step that does nothing) with order `60` to the plan.

    <rules xmlns="http://www.xebialabs.com/xl-deploy/xl-rules">
        <rule name="SuccessBaseDeployedArtifact" scope="deployed">
            <conditions>
                <type>udm.BaseDeployedArtifact</type>
                <type>udm.BaseDeployed</type>
                <operation>CREATE</operation>
            <conditions>
            <steps>
                <noop>
                    <order>60</order>
                    <description expression="true">'Dummy step for %s' % deployed.name</description>
                </noop>
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
                <noop>
                    <order>60</order>
                    <description>Success step in Production environment</description>
                </noop>
            </steps>
        </rule>
    </rules>
     
**Note:** The `expression` tag does not need to specify `expression="true"`. Also, in this example, the description is now a literal string, so `expression="true"` is not required.

### Sample XML rule: Using a checkpoint

This is an example of an XML rule that contains a checkpoint. XL Deploy will use this checkpoint to undo the rule's action if you roll back the deployment. If the step was executed successfully, XL Deploy knows that the deployable is successfully deployed; upon rollback, the planning phase needs to add steps to undo the deployment of the deployable. 

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

This is an example of an XML rule in which the operation is `MODIFY`. This operation involves two sequential actions, which are removing the old version of a file (`DESTROY`) and then creating the new version (`CREATE`). This means that two checkpoints are needed.

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

A script rule adds steps and checkpoints to a plan by running a Jython script that calculates which steps and checkpoints to add.

**Important:** The script in a script rule only runs during the *planning* phase. The purpose of the script is to provide steps for the final plan to execute, *not* to take deployment actions. Script rules do not interact with XL Deploy's execution phase, although some of the steps executed in that phase may involve executing scripts (such as `jython` steps).

### Define steps in script rules

A script rule uses the following format in `xl-rules.xml`:

* A `rule` tag with `name` and `scope` attributes, both of which are required.
* An optional `conditions` tag with:
    * One or more `type` tags that identify the UDM types that the rule is restricted to. `type` is required if the scope is `deployed`; otherwise, you must omit it. The UDM type name must refer to a *deployed* type (not a *deployable*, *container*, or other UDM type).
    * One or more `operation` tags that identify the operations that the rule is restricted to. The operation can be `CREATE`, `MODIFY`, `DESTROY`, or `NOOP`. `operation` is required if the scope is `deployed`; otherwise, you must omit it.
    * An optional `expression` tag with an expression in Jython that defines a condition upon which the rule will be triggered. This tag is optional for all scopes. If you specify an `expression`, it must evaluate to a Boolean value. 
* A `planning-script-path` child tag that identifies a script file that is available on the class path (in the `<XLDEPLOY_HOME>/ext/` directory).
 
Every script is run in isolation; that is, you cannot pass values directly from one script to another.

### Sample script rule: Successfully created artifact

This is an example of a script that is executed for every deployed that is involved in the deployment. The step of type `noop` will only be added for new deployeds (operation is `CREATE`) that derive from the type `udm.BaseDeployedArtifact`, as defined by the `type` element. Creating a step is done through the factory object `steps`. Addition of the step is performed through `context`, which represents the planning context (*not* the execution context).

    <rules xmlns="http://www.xebialabs.com/xl-deploy/rules">
        <rule name="SuccessBaseDeployedArtifact" scope="deployed">
            <conditions>
                <type>udm.BaseDeployedArtifact</type>
                <operation>CREATE</operation>
            </conditions>
            <planning-script-path>planning/SuccessBaseDeployedArtifact.py</planning-script-path>
        </rule>    

Where `planning/SuccessBaseDeployedArtifact.py`, which is stored in the `<XLDEPLOY_HOME>/ext/` directory, has following content:

    step = steps.noop(description = "A dummy step to indicate that some new artifact was created on the target environment", order = 100)
    context.addStep(step)

## Define rule behavior

When you define an XML or script rule, you use expressions or scripts to define its behavior. These are written in Jython, a combination of Python and Java.

The data that is available for planning scripts to use depends on the scope of the rule; see the table below.

**Note:** These objects are not automatically available for *execution* scripts (such as in the `jython` or `os-script` steps). If they are needed there, the planning script must make them available explicitly, such as by adding them to the `jython-context` map parameter in the case of a `jython` step.

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
        <td>logger</td> <td><a href="http://www.slf4j.org/api/org/slf4j/Logger.html">Logger</a></td> <td>all</td> <td>Allows you to access the XL Deploy logs. Prints logs to namespace <code>com.xebialabs.platform.script.Logging</code></td>
    </tr>
</table>

### Syntax

This section describes the syntax that is available when you define rule behavior.

#### Accessing CI properties

To access configuration item (CI) properties, including synthetic properties, use the property notation. For example:

    name = deployed.container.myProperty

You can also refer to a property in the dictionary style, which is useful for dynamic access to properties. For example:

    propertyName = "myProperty"
    name = deployed.container[propertyName]

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

You can disable any rule that is registered in XL Deploy's rule registry, including rules that are:

* Predefined in XL Deploy
* Defined in the `<XLDEPLOY_HOME>/ext/xl-rules.xml` file
* Defined in `xl-rules.xml` files in plugin JARs

To disable a rule, add the `disable-rule` tag under the `rules` tag in `xl-rules.xml`. You identify the rule that you want to disable by its name (this is why rule names must be unique).

For example, to disable a rule with the name `deployArtifact`, use:

    <?xml version="1.0"?>
    <rules xmlns="http://www.xebialabs.com/xl-deploy/xl-rules">
        <disable-rule name="deployArtifact" />
    </rules>

### Predefined rule naming

You can disable the rules that are predefined by Java classes in XL Deploy. Each of the methods that used to define steps is translated into a corresponding rule. This section describes the naming convention for each type of predefined rule.

#### Deployed system rules

* All methods of deployed classes that are annotated with `@Create`, `@Modify`, `@Destroy`, `@Noop` annotations. The name of the rule is given by concatenation of the UDM type of the deployed class, the method name, and annotation name. For example

        file.DeployedArtifactOnHost.executeCreate_CREATE

#### Contributor system rules

All methods that are annotated with `@Contributor` annotations. The rule name is defined by concatenation of the full class name and method name. For example:

       com.xebialabs.deployit.plugin.generic.container.LifeCycleContributor.restartContainers

#### Pre-plan and post-plan system rules

All methods that are annotated with `@PrePlanProcessor` or `@PostPlanProcessor` annotations. The rule name is defined by concatenation of the full class name and method name. For example:

      com.xebialabs.deployit.plugins.releaseauth.planning.CheckReleaseConditionsAreMet.validate

## Use a predefined step

The plugins that are bundled with XL Deploy contain predefined steps that you can use in rules. For information about the steps that are available, refer to the [Steps Reference](referencesteps.html).

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
* If the scope is not `deployed`, the `description` cannot be calculated automatically and must be specified manually.

#### Target host

The `target-host` parameter of a step is calculated as follows:

* If the scope is `deployed` and:
    * `deployed.container` is of type [overthere.Host](remotingPluginManual.html#overthere.Host), the `target-host` is set to `deployed.container`.
    * `deployed.container` is of type [overthere.HostContainer](remotingPluginManual.html), the `target-host` is set to `deployed.container.host`.
    * `deployed.container` has a property called `host`, the value of which is of type [overthere.Host](remotingPluginManual.html#overthere.Host); then `target-host` is set to this value.
* In other cases, `target-host` cannot be calculated automatically and must be specified manually.

#### Source artifact

The `source-artifact` parameter of a step is calculated as follows:

* If the scope is `deployed` and `deployed` is of type [udm.Artifact](udmcireference.html), the `source-artifact` is set to `deployed`.
* In other cases, `source-artifact` cannot be calculated automatically and must be specified manually.

#### FreeMarker context

The `freemarker-context` parameter of a step is calculated as follows:

* If the scope is `deployed`, the `freemarker-context` is enriched with a `deployed` instance that is accessible in a FreeMarker template by name `deployed`.
* In other cases, `freemarker-context` is not calculated automatically.

#### Jython context

The `jython-context` parameter of a step is calculated as follows:

* If the scope is `deployed`, the `jython-context` is enriched with a `deployed` instance that is accessible in a python script by binding `deployed`.
* In other cases, `jython-context` is not automatically calculated.

## Define a custom step

If the predefined step types in XL Deploy do not provide the functionality that you need, you can define custom step types and create rules that refer to them. Refer to [Define a custom step](xldeployjavaapimanual.html#define-a-custom-step-for-rules) for more information.

## Troubleshooting and best practices

### Namespace

To avoid name clashes among plugins that you have created or acquired, it is recommended you use a namespace for your rules based on your company name. For example:

    <rule name="com.mycompany.xl-rules.createFooResource" scope="deployed">...</rule>

### Logging

To see more information during the planning phase, you can increase the logging output:

1. Open `<XLDEPLOY_HOME>/conf/logback.xml` for editing.
1. Add a statement such as one of the following:

        <logger name="com.xebialabs.deployit.deployment.rules" level="debug" />
        <logger name="com.xebialabs.deployit.deployment.rules" level="trace" />

1. Use the `logger` object in Jython scripts. For information on how to do so, see [Define rule behavior](#define-rule-behavior).

### Unique script names

Some step types allow you to refer to a script by name. XL Deploy will search for the script on the full classpath; this includes the `ext/` folder as well as the `conf/` folder, inside JAR files, and so on. Ensure that scripts are uniquely named across all of these locations.

**Note:** Some steps search for scripts with derived names. For example, the `os-script` step will search for `my script` as well as `myscript.sh` and `myscript.bat`.
