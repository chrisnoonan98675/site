---
title: Introduction to the XL Deploy execution engine
categories:
xl-deploy
subject:
Rules
tags:
rules
planning
step
since:
XL Deploy 4.5.0
---

XL Deploy is a model-driven deployment solution. Users declaratively define the artifacts and resources that they need to deploy in a package (a ZIP file with a `deployit-manifest.xml` file) and XL Deploy figures out how to install the components in a target environment via [rules](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html).

Rules provide an easy way for you to teach the XL Deploy execution engine how to generate your desired deployment steps in a scalable, reusable, and maintainable way.

You define rules once and XL Deploy applies them intelligently, based on what you want to deploy and where you want to deploy it. From the user's perspective, there is no distinction between deploying an application to a single server environment, or to a clustered, load-balanced, datacenter-aware environment. XL Deploy will apply the rules accordingly.

## Rules

In general, the nature of a deployment entails the creation, destruction, or modification of artifacts on middleware servers. These changes have side effects such as execution order, restart strategies, error handling, retrying from a failed step, deployment orchestration (such as parallel, load-balanced, canary, and blue-green deployments), rollback, access control, logging, and so on.

The XL Deploy execution engine captures the generic nature as well as the side effects of the deployment. Rules take advantage of these states and side effects to contribute steps to the deployment.

You can think of rules as a way to create intelligent, self-generating workflows. They allow you to model your required deployment steps without requiring you to scaffold the generic nature of the deployment, which is usually the case with workflows created by hand.

## Steps

In general, when deploying to or configuring systems, you need to perform actions such as uploading a file, deleting a file, executing commands, or performing API calls. The actions have a generic nature that can be captured in a few step types.

XL Deploy provides a collection of [predefined step types](/xl-deploy/6.0.x/referencesteps.html) that you can [use](/xl-deploy/how-to/use-a-predefined-step-in-a-rule.html) in your rules. Once a rule is fired, the rule will contribute steps to the deployment plan.

## Putting it all together

For example, if you had to configure a Microsoft Windows service, you could use the predefined [Powershell](/xl-deploy/6.0.x/referencesteps.html#powershell) step to execute your desired script. XL Deploy will automatically pass the script all of the deployment parameters it may need and execute it on the target Windows host. You only need to concentrate on the business goal of having to configure the service.

{% highlight powershell %}
$serviceName = $deployed.serviceName
$displayName = $deployed.serviceDisplayName
$description = $deployed.serviceDescription
Write-Host "Installing service [$serviceName]"
New-Service -Name $serviceName -BinaryPathName $deployed.binaryPathName -DependsOn $deployed.dependsOn -Description $description -DisplayName $displayName -StartupType $deployed.startupType | Out-Null
{% endhighlight %}

The above script will create or update the service. Its associated rule definition would be:

{% highlight xml %}
<rule name="sample.InstallService" scope="deployed">
    <conditions>
        <type>demo.WindowsService</type>
        <operation>CREATE</operation>
        <operation>MODIFY</operation>
    </conditions>
    <steps>
        <powershell>
            <description expression="true">"Install $deployed.name on $deployed.container.name"</description>
            <script>sample/windows/install_service.ps1</script>
        </powershell>
    </steps>
</rule>
{% endhighlight %}

The same pattern can be used for other type of integrations. For example:

* If you need to run a batch or bash script to encapsulate your deployment logic, you could use the [OS-Script step](/xl-deploy/6.0.x/referencesteps.html#os-script).

* If you have complex logic that requires the power of a language, you could use the [Jython step](/xl-deploy/6.0.x/referencesteps.html#jython) to code Python to handle the step logic.

For a more comprehensive examples, please refer to the [rules tutorial](https://docs.xebialabs.com/xl-deploy/how-to/xl-deploy-rules-tutorial.html).

## Packaging your rules

You can package a group of related rules in a JAR file that you [install in XL Deploy](/xl-deploy/how-to/install-or-remove-xl-deploy-plugins.html#install-a-plugin). These are sometimes referred to as plugins. XL Deploy includes predefined rule sets for technologies such as WebSphere, WebLogic, JBoss, IIS, and so on. Other reusable rule sets can be found in the [XebiaLabs community](https://www.github.com/xebialabs-community). You can reuse these rules, or refer to them as examples when creating your own rules.
