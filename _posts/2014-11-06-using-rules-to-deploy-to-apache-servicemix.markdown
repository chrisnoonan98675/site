---
title: Using rules to deploy to Apache ServiceMix
categories:
- xl-deploy
tags:
- rules
- middlware
---

The rules feature, introduced in [XL Deploy 4.5.0](http://docs.xebialabs.com/product-version.html#/xl%20deploy/4.5.x), makes it easy to extend XL Deploy to deploy an [Apache ServiceMix](http://servicemix.apache.org/) feature. Generally, the steps for deploying a ServiceMix feature are:

1. Copy the artifact to a directory
1. Add the feature in ServiceMix
1. Load the feature

This is an example of a plan that will deploy a ServiceMix feature:

![Deployment to ServiceMix](/images/xl_deploy_rules_apache_servicemix.png)

To generate the plan, we need these rules in the [`xl-rules.xml`](http://docs.xebialabs.com/releases/4.5/xl-deploy/rulesmanual.html#where-to-define-rules) file on the XL Deploy server:

	<rule name="servicemix.DeployedFeature.CREATE_MODIFY" scope="deployed">
		 <conditions>
			  <type>servicemix.DeployedFeature</type>
			  <operation>CREATE</operation>
			  <operation>MODIFY</operation>
		 </conditions>
		 <steps>
			  <upload>
				   <order>70</order>
				   <description expression="true">"upload feature %s to %s" % (deployed.name, deployed.container.repository)</description>
				   <target-path expression="true">"%s/%s" % (deployed.container.repository,deployed.name)</target-path>
			  </upload>
			  <os-script>
				   <order>70</order>
				   <description expression="true">"add feature %s" % (deployed.name)</description>
				   <script>servicemix/add-feature</script>
				   <freemarker-context>
						<targetDeployed expression="true">delta.deployed</targetDeployed>
				   </freemarker-context>
			  </os-script>
			  <os-script>
				   <order>70</order>
				   <description expression="true">"deploy feature %s" % (deployed.name)</description>
				   <script>servicemix/deploy-feature</script>
				   <freemarker-context>
						<targetDeployed expression="true">delta.deployed</targetDeployed>
				   </freemarker-context>
			  </os-script>
			  <wait>
				   <order>70</order>
				   <description>Wait for bundle to start</description>
				   <seconds>10</seconds>
			  </wait>
			  <os-script>
				   <order>70</order>
				   <description expression="true">"test feature is deployed %s" % (deployed.name)</description>
				   <script>servicemix/test-feature</script>
				   <freemarker-context>
						<targetDeployed expression="true">delta.deployed</targetDeployed>
				   </freemarker-context>
			  </os-script>
		 </steps>
	</rule>

For more information about using rules, see the [Rules Manual](http://docs.xebialabs.com/releases/latest/deployit/rulesmanual.html) and the [Rules Tutorial](http://docs.xebialabs.com/releases/latest/deployit/rulestutorial.html).
