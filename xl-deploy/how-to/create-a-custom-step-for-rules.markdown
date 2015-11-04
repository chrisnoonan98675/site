---
title: Create a custom step for rules
categories: 
- xl-deploy
subject:
- Rules
tags:
- java
- rules
- step
- deployment
since:
- XL Deploy 4.5.0
---

XL Deploy allows you to create [rules](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html) that define which steps should be included in a deployment plan. Each rule in the `xl-rules.xml` file defines a number of steps to add to the deployment plan, and the available *step primitives* determine what kind of steps can be used. A step primitive is a definition of a piece of functionality that XL Deploy may execute as part of the deployment plan.

XL Deploy and its plugins include [predefined steps](/xl-deploy/5.0.x/referencesteps.html) such as `noop` and `os-script`. Also, you can define custom deployment step primitives in Java. To create a custom step that is available for rules, you must declare its name and parameters by providing annotations.

## Authoring a step primitive

For XL Deploy to recognize your class as a step primitive:

* It must implement the Java interface `com.xebialabs.deployit.plugin.api.flow.Step`
* It must be annotated with `@com.xebialabs.deployit.plugin.api.rules.StepMetadata(name = "step-name")`
* It must have a default constructor

The `step-name` you give in the annotation will be used verbatim as the XML tag name, so be sure to make it XML-compatible.
 
For example, the following Java code will allow you to use the `UsefulStep` class by specifying `my-nifty-step` inside your `xl-rules.xml`:

{% highlight java %}
@StepMetadata(name = "my-nifty-step")
class UsefulStep implements Step {
    ...
}
{% endhighlight %}

Your XML would then look as follows:

{% highlight xml %}
<?xml ... ?>
<rules ...>
    <rule ...>
        <conditions>...</conditions>
        <steps>
            <my-nifty-step>
                ...
            </my-nifty-step>
        </steps>
    </rule>
</rules>
{% endhighlight %}

You can make your step primitives parameterized, with parameters that are required, optional and/or auto-calculated.

XL Deploy supports all Java primitives and string classes, including `int`, `String`, and so on.

## Using the `Step` interface

XL Deploy uses the `com.xebialabs.deployit.plugin.api.flow.Step` interface to determine:

* At what order the step should be executed
* The description of the step that should appear in the deployment plan
* What action(s) to execute for the step

For this, the `Step` interface declares these methods:

{% highlight java %}
int getOrder();
String getDescription();
StepExitCode execute(ExecutionContext ctx) throws Exception;
{% endhighlight %}

The `execute` method is where you define the business logic for your step primitive. The `ExecutionContext` that is passed in allows you to access the repository using the credentials of the user executing the deployment plan. 

Your implementation should return a `StepExitCode` to indicate whether execution of the step was successful.

Refer to the [Javadoc](/xl-deploy/latest/javadoc/udm-plugin-api/index.html) for more information about `Step`.

## Defining parameters in a step primitive

XL Deploy has a dependency injection mechanism that allows values from `xl-rules.xml` to be injected into your class. This is how you can set the step description or other parameters using XML.

To receive values from a rule, define a field in your class and annotate it with the `@com.xebialabs.deployit.plugin.api.rules.StepParameter` annotation. This annotation has the following attributes:

{:.table .table-striped}
| Attribute | Description |
| --------- | ----------- |
| `name` | Defines the XML tag name of the parameter. Camel-case names (such as `myParam`) are represented with dashes in XML (`my-param`) or underscores in Jython (`my_param=...`). The content of the resulting XML tags are interpreted as Jython expressions and must result in a value of the type of the private field. |
| `required` | Controls whether XL Deploy verifies that the parameter contains a value after the post-construct logic has run. Note that setting `required=true` does not necessarily imply that the parameter must be set from within the rules XML; you can use the post-construct logic to provide a default value. |
| `calculated` | Indicates that a value can be automatically calculated in the step's post-construct logic. The setting does not influence the behavior of the step parameter or of the step itself. |
| `description` | Allows you to provide a description of the step parameter. For example, you can use this description to automatically generate documentation. It does not influence the behavior of the step parameter or of the step itself. |

For example, the `manual` step primitive has:

{% highlight java %}
@StepParameter(name = "freemarkerContext", description = "Dictionary that contains all values available in the template", required = false, calculated = true)
private Map<String, Object> vars = new HashMap<>();
{% endhighlight %}

The following XML sets the value of the `vars` field:

{% highlight xml %}
<?xml ... ?>
<rules ...>
    <rule ...>
        <conditions>...</conditions>
        <steps>
            <manual>
                ...
                <freemarker-context>...</freemarker-context>
                ...
            </manual>
        </steps>
    </rule>
</rules>
{% endhighlight %}

Refer to the [Javadoc](/xl-deploy/latest/javadoc/udm-plugin-api/index.html) for more information about `StepParameter`.

## Implementing post-construct logic

You can add additional logic to your step that will be executed after all field values have been injected into your step. This logic may include defining or calculating default parameters of your step, applying complex validations, and so on.

To define post-construct logic:

* Define a method with signature `void myMethod(com.xebialabs.deployit.plugin.api.rules.StepPostConstructContext ctx)`
* Annotate your method with `@com.xebialabs.deployit.plugin.api.rules.RulePostConstruct`

There can be multiple post-construct methods in your class chain; each of these will be invoked in alphabetical order by name.

The `StepPostConstructContext` contains references to the `DeployedApplication`, the `Scope`, the scoped object (`Delta`, `Deltas`, or `Specification`), and the repository.

For example, the following step will try to find a value for `defaultUrl` in the repository if it is not specified in the rules XML, and the planning will fail if it is not found:

{% highlight java %}
@StepParameter(name="defaultHostURL", description="The URL to contact first", required=true, calculated=true)
private String defaultUrl;

@RulePostConstruct
private void lookupDefaultUrl(StepPostConstructContext ctx) {
    if (defaultUrl==null || defaultUrl.equals("")) {
        Repository repo = ctx.getRepository();
        Delta delta = ctx.getDelta();
        defaultUrl = findDefaultUrl(delta, repo);      // to be implemented yourself
    }
}
{% endhighlight %}

Refer to the [Javadoc](/xl-deploy/latest/javadoc/udm-plugin-api/index.html) for more information about `StepPostConstructContext`.

## Compiling step primitives

To compile your own step primitives, you depend on the following plugins, located in `<XLDEPLOY_HOME>/lib`:

* `base-plugin-x.y.z.jar`
* `udm-plugin-api-x.y.z.jar`

## Making step primitives available to XL Deploy

After writing the code for your step primitive, you make it available to XL Deploy by compiling it into a JAR file and putting the file in `<XLDEPLOY_HOME>/plugins`.

## Custom step example

This is an example of the implementation of a new type of step:

{% highlight java %}
import com.xebialabs.deployit.plugin.api.flow.Step;
import com.xebialabs.deployit.plugin.api.rules.StepMetadata;
import com.xebialabs.deployit.plugin.api.rules.StepParameter;

@StepMetadata(name = "my-step")
public class MyStep implements Step {
   
    @StepParameter(label = "My parameter", description = "The foo's bar to baz the quuxes", required=false)
    private FooBarImpl myParam;
    @StepParameter(label = "Order", description = "The execution order of this step")
    private int order;
   
    public int getOrder() { return order; }
    public String getDescription() { return "Performing MyStep..."; }
    public StepExitCode execute(ExecutionContext ctx) throws Exception {
        /* ...perform deployment operations, using e.g. myParam...*/
    }
}
{% endhighlight %}

In `xl-rules.xml`, you refer to this rule as follows:

{% highlight xml %}
<rule ...>
    ...
    <steps>
        <my-step>
            <order>42</order>
            <my-param expression="true">deployed.foo.bar</myParam>
        </my-step>
    </steps>
</rule>
{% endhighlight %}

The script variant is as follows (note the underscores):

{% highlight xml %}
<rule ...>
    <steps>
        <script><![CDATA[
            context.addStep(steps.my_step(order=42, my_param=deployed.foo.bar))
        ]]></script>
    </steps>
</rule>
{% endhighlight %}

A step type is represented by a Java class with a default constructor implementing 
the `Step` interface. The resulting class file must be placed in the standard XL Deploy classpath.
   
The `order` represents the execution order of the step and the `description` is the description of this step, which will appear in the Plan Analyzer and the deployment execution plan. The `execute` method is executed when the step runs. The `ExecutionContext` interface that is passed to the `execute` method allows you to access the repository and the step logs and allows you to set and get attributes, so steps can communicate data.
 
The step class must be annotated with the `StepMetadata` annotation, which has only a `name` String member. This name translates directly to a tag inside the `steps` section of `xl-rules.xml`, so the name must be XML-compliant. In this example, `@StepMetadata(name="my-step")` corresponds to the `my-step` tag.
 
Passing data to the step class is done using dependency injection. You annotate the private fields that you want to receive data with the `StepParameter` annotation.

In `xl-rules.xml`, you fill these fields by adding tags based on the field name.

Refer to the [Javadoc](/xl-deploy/latest/javadoc/udm-plugin-api/index.html) for more information about interfaces and annotations.
