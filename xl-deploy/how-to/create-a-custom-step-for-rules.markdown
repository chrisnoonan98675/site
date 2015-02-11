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
---

This topic describes how to create a custom step that can be used in rules. For more information on rules, please refer to [Understanding XL Deploy rules](/xl-deploy/concept/understanding-xl-deploy-rules.html).

Custom steps are defined in Java. To create a custom step that is available for rules, you must declare its name and parameters by providing annotations.

## Step metadata annotation

The [`@StepMetadata`](/xl-deploy/5.0.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/rules/StepMetadata.html) annotation that is targeted to the Java type (that is, the Java class) specifies the name of the step.

## Step parameter annotation

The [`@StepParameter`](/xl-deploy/5.0.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/rules/StepParameter.html) annotation that is targeted to fields specifies the parameters. The name of the parameter is the Java field name of the parameter. The annotation allows you to specify metadata such as a label and description.

## Supported parameter types

XL Deploy supports all Java primitives and string classes, including `int`, `String`, and so on.

## Post-construct method

You can add additional logic to your step that will be executed when step is created within a rule. This can include defining default parameters of your step, applying complex validations and etc.
  
You have to annotate your method with [`@RulePostConstruct`](/xl-deploy/5.0.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/rules/RulePostConstruct.html) and make sure that this method accepts [`StepPostConstructContext`](/xl-deploy/5.0.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/rules/StepPostConstructContext.html) as its argument. 

## Custom step example

This is an example of the implementation of a new type of step:
 
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

In `xl-rules.xml`, you refer to this rule as follows:
   
    <rule ...>
        ...
        <steps>
            <my-step>
                <order>42</order>
                <my-param expression="true">deployed.foo.bar</myParam>
            </my-step>
        </steps>
    </rule>
   
The script variant is as follows (note the underscores):
 
    <rule ...>
        <steps>
            <script><![CDATA[
                context.addStep(steps.my_step(order=42, my_param=deployed.foo.bar))
            ]]></script>
        </steps>
    </rule>
    
A step type is represented by a Java class with a default constructor implementing the 
[`Step`](/xl-deploy/5.0.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/flow/Step.html) interface. The resulting class file must be placed in the standard XL Deploy classpath.
   
The `order` represents the execution order of the step and the `description` is the description of this step, which will appear in the Plan Analyzer and the deployment execution plan. The `execute` method is executed when the step runs. The [`ExecutionContext`](/xl-deploy/5.0.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/flow/ExecutionContext.html) interface that is passed to the `execute` method allows you to access the Repository and the step logs and allows you to set and get attributes, so steps can communicate data.
 
The step class must be annotated with the [`StepMetadata`](/xl-deploy/5.0.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/rules/StepMetadata.html) annotation, which has only a `name` String member. This name translates directly to a tag inside the `steps` section of `xl-rules.xml`, so the name must be XML-compliant. In this example, `@StepMetadata(name="my-step")` corresponds to the `my-step` tag.
 
Passing data to the step class is done using dependency injection. You annotate the private fields that you want to receive data with the [`StepParameter`](/xl-deploy/5.0.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/rules/StepParameter.html) annotation.

In `xl-rules.xml`, you fill these fields by adding tags based on the field name. Camel-case names (such as `myParam`) are represented with dashes in XML (`my-param`) or underscores in Jython (`my_param=...`). The content of the resulting XML tags are interpreted as Jython expressions and must result in a value of the type of the private field.
