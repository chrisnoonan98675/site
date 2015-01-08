---
title: Enable additional logging for the XL Deploy server
subject:
- System administration
category:
- xl-deploy
tags:
- system administration
- logging
- troubleshooting
- placeholder
- dictionary
---

Logging is configured in the `<XLDEPLOY_HOME>/conf/logback.xml` file. To enable debug mode, change the following setting:

	<root level="debug">
		...
	</root>
	
If this results in too much logging, you can tailor logging for specific packages by adding log level definitions for them. For example:
	
	<logger name="com.xebialabs" level="info" />

Note that the server must be restarted to activate the new log settings.

See the [Logback site](http://logback.qos.ch/) for more information.

## How do I debug placeholder scanning in XL Deploy?

To debug placeholder scanning, edit the `<XLDEPLOY_HOME>/conf/logback.xml` file and add the following line:

    <logger name="com.xebialabs.deployit.engine.replacer.Placeholders" level="debug" />

When importing a deployment package (DAR file), you will see debug statements in the `deployit.log` file as follows:

     ...
     DEBUG c.x.d.engine.replacer.Placeholders - Determined New deploymentprofile.deployment to be a binary file
     ...
