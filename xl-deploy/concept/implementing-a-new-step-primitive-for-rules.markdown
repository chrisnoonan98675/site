---
title: Authoring a new step primitive for rules
subject:
- Rules
categories:
- xl-deploy
tags:
- rules
- steps
- deployment
---

XL Deploy allows you to deploy your applications using a rule DSL, where the final deployment plan gets composed via a number of pre-packaged or user-defined rules that define which steps should be included in the plan. However, the kinds of steps that you can use may not always provide all the functionality you need. In those cases you may want to write your own step primitives that perform the actions you need.


## What is a step primitive?

A step primitive is a definition of a piece of functionality that XL Deploy may execute as part of the deployment plan. Each rule in `xl-rules.xml` defines a number of steps to add to the plan, and the available step primitives determine what kind of steps can be used. In this document, whenever we mention XML rules or the `xl-rules.xml`, Jython rules are also implied.

The following example shows the use of two step primitives: the 'noop' step primitive and the 'os-script' step primitive.

    <?xml version="1.0" encoding="UTF-8"?>
    <rules xmlns="http://www.xebialabs.com/xl-deploy/xl-rules">
        <rule name="Example rule" scope="deployed">
            <conditions>
                <type>example.Resource</type>
                <operation>CREATE</operation>
            </conditions>
            <steps>
                <noop>
                    <description>A step of type 'noop' is a simple step type with only the basic parameters 'order' and 'description'</description>
                    <order>50</order>
                </noop>
                <os-script>
                    <description>Step parameters may be auto-calculatable, optional, or both, as e.g. for os-script steps</description>
                    <!-- <order> is auto-calculated -->
                    <target-host expression="true">deployed.container.host</target-host>
                    <script>os-script-example/scripts/deploy</script>
                    <freemarker-context>
                        <deployedApplicationName expression="true">deployedApplication.name</deployedApplicationName>
                    </freemarker-context>
                    <classpath-resources>
                        <value>os-script-example/images/logo.jpg</value>
                        <value>os-script-example/html/index.html</value>
                    </classpath-resources>
                    <!-- <upload-artifacts> is optional -->
                </os-script>
            </steps>
        </rule>
    </rules>

Refer to the [Step Reference](stepreference.html) for all predefined step primitives. You can define your own step primitives by writing Java classes of your own, using the proper annotations and interfaces.


## Referencing step library code

In order to compile your own step primitives, you depend on the following jars, to be found in XL Deploy's `lib/` folder:

* base-plugin-x.y.z.jar
* udm-plugin-api-x.y.z.jar


## Making step primitives available to XL Deploy

After writing the code for your step primitive(s), you make them available to XL Deploy by compiling them into a jar file and putting the jar in XL Deploy's `plugins/` folder.


## Authoring a step primitive

For XL Deploy to recognize your class as a step primitive:

* It must implement the Java interface `com.xebialabs.deployit.plugin.api.flow.Step`
* It must be annotated with `@com.xebialabs.deployit.plugin.api.rules.StepMetadata(name = "step-name")`
* It must have a default constructor

The `step-name` you give in the annotation will be used verbatim as the XML tag name, so be sure to make it XML-compatible.
 
For example, the following Java code will allow you to use the `UsefulStep` class by specifying `my-nifty-step` inside your `xl-rules.xml`:

    @StepMetadata(name = "my-nifty-step")
    class UsefulStep implements Step {
        ...
    }

Your XML would then look as follows:

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

You can make your step primitives parameterized, with parameters that are required, optional and/or auto-calculated. [See below](#Defining-step-parameters-in-a-step-primitive) for details.
    

## Using the `Step` interface

XL Deploy uses the `com.xebialabs.deployit.plugin.api.flow.Step` interface to find out

* at what order the step should be executed,
* what description it should have in the deployment plan, and
* what action(s) to execute for this step.

For this, the `Step` interface declares these methods:

    int getOrder();
    String getDescription();
    StepExitCode execute(ExecutionContext ctx) throws Exception;

The `execute` method is where you define the business logic for your step primitive. The `ExecutionContext` that gets passed in allows you to access the Repository using the credentials of the user executing the deployment plan. [See the javadocs](javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/flow/ExecutionContext.html) for full details. Your implementation should return a [`StepExitCode`](javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/flow/StepExitCode.html) to indicate whether execution of the step was successful.

The Javadocs have the full details on [the `Step` interface](javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/flow/Step.html).


## Defining step parameters in a step primitive

XL Deploy has a dependency injection mechanism that allows values from the `xl-rules.xml` to be injected into your class. This is how you can set e.g. the step description or other parameters using XML. To receive values from a rule, define a field in your class and annotate it with the [`@com.xebialabs.deployit.plugin.api.rules.StepParameter`](javadocs.html) annotation. This annotation has the following attributes:

* `name` defines the XML tag name of the parameter. By default, the field name will be used, but capitals will be replaced with a dash and a lowercase character. E.g. `targetPath` will become `target-path`.
* `required` controls whether XL Deploy verifies that the parameter contains a value after the post-construct logic has run.
* `calculated` *documents* the fact that a value can be automatically calculated in the Step's post-construct logic; [see below](#Implementing-post-construct-logic). The setting does not in any way influence the behavior of the step parameter or the step itself.
* `description` allows you to provide a description of the step parameter, to be used e.g. to auto-generate documentation. It does not in any way influence the behavior of the step parameter or the step itself.

Note that setting `required=true` does not necessarily imply that the parameter must be set from within the rule XML: you can use the post-construct logic to provide a default value.

For example, the `manual` step primitive has:

    @StepParameter(name = "freemarkerContext", description = "Dictionary that contains all values available in the template", required = false, calculated = true)
    private Map<String, Object> vars = new HashMap<>();

The following XML then sets the value of the `vars` field:

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


## Implementing Post-Construct logic

You can add additional logic to your step that will be executed after all field values have been injected into your step. This logic may include defining or calculating default parameters of your step, applying complex validations, etc. 

To define such post-construct logic:

* Define a method with signature `void myMethod(com.xebialabs.deployit.plugin.api.rules.StepPostConstructContext ctx)`
* Annotate your method with [`@com.xebialabs.deployit.plugin.api.rules.RulePostConstruct`](javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/rules/RulePostConstruct.html)

There can be multiple post-construct methods in your class chain; each of these will be invoked in alphabetical order by name.

The `StepPostConstructContext` contains references to the `DeployedApplication`, the `Scope`, the scoped object (`Delta`, `Deltas`, or `Specification`), and the repository; see the [Rules manual](rulesmanual.html) and the [Javadocs for StepPostConstructContext](javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/flow/StepPostConstructContext.html) for details.

For example, the following step will try to find a value for `defaultUrl` in the repository if it is not specified in the rules XML, and the planning will fail if it is not found.

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
    