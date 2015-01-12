---
title: Add a control task to an existing type
subject:
- Generic plugin
categories:
- xl-deploy
tags:
- control task
- configuration
---

To add a control task to an existing type (such as `Host`) in XL Deploy, you can extend the Generic plugin as follows:

1. Define a custom container that extends the generic container. The custom container should define the control task and the associated script to run. The script is a FreeMarker template that is rendered, copied to the target host, and executed. For example, in `synthetic.xml`:

		<type type="mycompany.ConnectionTest" extends="generic.Container"> 
			<!-- inherited hidden --> 
			<property name="startProcessScript" default="mycompany/connectiontest/start" hidden="true"/> 
			<property name="stopProcessScript" default="mycompany/connectiontest/stop" hidden="true"/> 
			<!-- control tasks --> 
			<method name="start" description="Start some process"/> 
			<method name="stop" description="Stop some process"/> 
		</type>

2. In the XL Deploy repository, create the container under the host that you want to test.
3. Execute the control task.
