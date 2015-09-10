---
title: Writing XML rules
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- orchestrator
- planning
- step
since:
- XL Deploy 4.5.0
---

An XML rule is fully specified using XML and has the following format in `xl-rules.xml`:

 * A `rule` tag with `name` and `scope` attributes, both of which are required.
 * A `conditions` tag with:
    * One or more `type` tags that identify the UDM types that the rule is restricted to. `type` is required if the scope is `deployed`; otherwise, you must omit it. The UDM type name must refer to a *deployed* type (not a *deployable*, *container*, or other UDM type).
    * One or more `operation` tags that identify the operations that the rule is restricted to. The operation can be `CREATE`, `MODIFY`, `DESTROY`, or `NOOP`. `operation` is required if the scope is `deployed`; otherwise, you must omit it.
    * An optional `expression` tag with an expression in Jython that defines a condition upon which the rule will be triggered. This tag is optional for all scopes. If you specify an `expression`, it must evaluate to a Boolean value. 
 * A `steps` tag that contains a list of steps that will be added to the plan when this rule meets all conditions; that is, when its types and operations match and its `expression` (if present) evaluates to true. Each step to be added is represented by an XML tag specifying the step type and step parameters such as `upload` or `powershell`.
 
## Define steps in XML rules

Steps in XML rules are defined in the `steps` tag. There is no XML schema verification of the way that rules are defined, but there are guidelines that you must follow.

* The `steps` tag contains tags that must map to step names.
* Each step contains parameter tags that must map to the parameters of the defined step.    
* Each parameter tag can contain:
    * A string value that will be automatically converted to the type of the step parameter. If the conversion fails, the step will not be created and the deployment planning will fail.
    * A Jython expression that must evaluate to a value of the type of the step parameter. For example, the expression `60` will evaluate to an `Integer` value, but `"60"` will evaluate to a `String` value. If you use an expression, the surrounding parameter tag must contain the attribute `expression="true"`.
    * In the case of map-valued parameters, you can specify the map with sub-tags. Each sub-tag will result in a map entry with the tag name as key and the tag body as value. Also, you can specify `expression="true"` to place non-string values into a map.
    * In the case of list-valued parameters, you can specify the list with `value` tags. Each tag results in a list entry with the value defined by the tag body. Also, you can specify `expression="true"` to place non-string values into a list.      
* The `steps` tag may contain a `checkpoint` tag that informs XL Deploy that the action the step takes must be undone in the case of a rollback.
    
All Jython expressions are executed in same context with the same available variables as Jython scripts in script rules.

### Using dynamic data

You can use dynamic data in steps. For example, to incorporate a file name in a step description, use:

{% highlight xml %}
<description expression="true">"Copy file " + deployed.file.name</description>
{% endhighlight %}

**Note:** Do not forget to set `expression` to `true` to enable dynamic data.

### Escaping special characters

Because `xl-rules.xml` is an XML file, some expressions must be escaped. For example, you must use `myParam &lt; 0` instead of `myParam < 0`. Alternatively, you can wrap expressions in a `CDATA` section.

### Using special characters in strings

You can set a step property to a string that contains a special character (such as a letter with an umlaut).

If the parameter is an expression, enclose the string with single or double quotation marks (`'` or `"`) and prepend it with the letter `u`. For example:

{% highlight xml %}
<parameter-string expression="true">u'pingüino'</parameter-string>
{% endhighlight %}

If the parameter is not evaluated as an expression, no additional prefix is required. You can simply assign the value. For example: 

{% highlight xml %}
<parameter-string>pingüino</parameter-string>
{% endhighlight %}

### Using checkpoints

XL Deploy uses checkpoints to build rollback plans. The rules system allows you to define checkpoints by inserting a `<checkpoint>` tag immediately after the tag for the step on which you want the checkpoint to be set. Checkpoints can be used only in the following conditions:

* The scope of the rule must be `deployed`.
* You can set one checkpoint per rule.
* If a rule specifies a single `MODIFY` operation, you can:
    * Set two checkpoints: One for the creation part and one for the deletion part of the modification (if applicable).
    * Use the attribute `completed="DESTROY"` or `completed="CREATE"` on the `checkpoint` tag to specify the operation that is actually performed for the step.

## Sample XML rule: Successfully created artifact

This is an example of a rule that is triggered for every deployed of type `udm.BaseDeployedArtifact` or `udm.BaseDeployed` and operation `CREATE`. It results in the addition of a `noop` step (a step that does nothing) with order `60` to the plan.

{% highlight xml %}
<rules xmlns="http://www.xebialabs.com/xl-deploy/xl-rules">
    <rule name="SuccessBaseDeployedArtifact" scope="deployed">
        <conditions>
            <type>udm.BaseDeployedArtifact</type>
            <type>udm.BaseDeployed</type>
            <operation>CREATE</operation>
        </conditions>
        <steps>
            <noop>
                <order>60</order>
                <description expression="true">'Dummy step for %s' % deployed.name</description>
            </noop>
        </steps>
    </rule>  
 </rules>
{% endhighlight %}

## Sample XML rule: Successfully deployed to Production

This is an example of an XML rule that is triggered once for the whole plan, when the deployment's target environment contains the word `Production`.

{% highlight xml %}
<rules xmlns="http://www.xebialabs.com/xl-deploy/xl-rules">
    <rule name="SuccessBaseDeployedArtifact" scope="post-plan">
        <conditions>
            <expression>"Production" in context.deployedApplication.environment.name</expression>
        </conditions>
        <steps>
            <noop>
                <order>60</order>
                <description>Success step in Production environment</description>
            </noop>
        </steps>
    </rule>
</rules>
{% endhighlight %}
     
**Note:** The `expression` tag does not need to specify `expression="true"`. Also, in this example, the description is now a literal string, so `expression="true"` is not required.

## Sample XML rule: Using a checkpoint

This is an example of an XML rule that contains a checkpoint. XL Deploy will use this checkpoint to undo the rule's action if you roll back the deployment. If the step was executed successfully, XL Deploy knows that the deployable is successfully deployed; upon rollback, the planning phase needs to add steps to undo the deployment of the deployable. 

{% highlight xml %}
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
{% endhighlight %}

## Sample XML rule: Using checkpoints when operation is `MODIFY`

This is an example of an XML rule in which the operation is `MODIFY`. This operation involves two sequential actions, which are removing the old version of a file (`DESTROY`) and then creating the new version (`CREATE`). This means that two checkpoints are needed.

{% highlight xml %}
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
{% endhighlight %}
