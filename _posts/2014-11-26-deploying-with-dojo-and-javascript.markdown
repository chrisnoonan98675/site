---
title: Deploying with Dojo and JavaScript
author: dave_roberts
categories:
- xl-deploy
tags:
- middleware
- javascript
---

After seeing [how to call the REST API for a deployment](/tips-and-tricks/xl-deploy/20140807-prepare-validate-start-deployment-rest-api/) using a series of curl commands, you may want to embed the deployment process into a web application. JavaScript with Dojo provides a nice medium for this. See [www.dojotoolkit.org](http://www.dojotoolkit.org/) for more information on Dojo.

The logic flow is exactly the same whether working from the GUI, the command line, or JavaScript. First determine whether your deployment is an initial deployment or an update deployment. and make the appropriate REST API call to obtain a deployment spec. Next, pass the spec to the prepare-deployed, validate-deployment and deploy steps. The first two of these will make updates to the spec to be passed along to subsequent steps, and the final result is a task in a pending state. Make a call to start the task, and your deployment is off and running.

Here is this logic expressed in a JavaScript function that drives the process:

	function performDeployment) {
	  error = false;
	  deploymentType = checkDeploymentExists();
	  deploymentSpec = null;
	  taskId="";
	  if (deploymentType == "update") {
		deploymentSpec = prepareUpdate();
	  } else if (deploymentType == "initial") {
		deploymentSpec = prepareInitial();
	  } else {
		error = true;
		dojo.publish("xhrError", [{message: "Application or Environment not found", type: "error", duration: 2000}]);
	  }
	  if (!error) {
		deploymentSpec = prepareDeployeds(deploymentSpec);
	  }
	  if (!error) {
		deploymentSpec = validateDeployment(deploymentSpec);
	  }
	  if (!error) {
		taskId = deploy(deploymentSpec);
	  }
	  if (!error && taskId.length > 0) {
		startTask(taskId);
	  }
	}

Each call is made from a subfunction, so let's look at those in sequence. 

The first one, `checkDeploymentExists()`, determines whether the deployment should be initial or update.  It uses the Dojo `xhrGet` method and passes the required parameters in a JavaScript object.

	function checkDeploymentExists() {
	  deploymentType = "notfound";
	  dojo.xhrGet({
		url: "http://localhost:4516/deployit/deployment/exists",
		user: "admin",
		password: "admin",
		headers: { "Content-Type": "application/xml"},
		content: { 
		  environment: dojo.byId("environment").value,
		  application: dojo.byId("application").value,
		},
		sync: true,
		//timeout: 10000,
		handleAs: "xml",
		load: function(result, ioargs) {
		  if (result.getElementsByTagName("boolean")[0].childNodes[0].nodeValue == "true") {
			deploymentType = "update";
		  } else {
			deploymentType = "initial";
		  }
		  dojo.publish("xldStatus", [{message: deploymentType + " HTTP status code " + ioargs.xhr.status, 
			type: "message", duration: 2000}]);
		  },
		error: commonError
	  });
	  return deploymentType;
	}

`url`, `user`, `password` and headers match what you saw in the curl commands.  The items under content are appended to the URL as a query string. You have a choice between sync (wait until the query returns before continuing), and timeout (run asynchronously but fail if the given number of milliseconds elapses without a return). 

The remaining parameters refer to how to handle what comes back:  handleAs refers to the format of the returned data, load is a callback function for a good result, and error is the `errback` for a bad one.  In this case, the latter will be `commonError`; we'll handle all error conditions with the same logic to make the code a little simpler.

When we get a good result, we parse the XML for the value between the `<boolean>...</boolean>` tags, and expect either true or false.  Otherwise we return not-found and exit out of the driver.

The next two functions are similar, differing in the REST API calls used and the parameters passed to them.

`prepareInitial`:

	function prepareInitial() {
	  deploymentSpec = "";
	  dojo.xhrGet({
		url: "http://localhost:4516/deployit/deployment/prepare/initial",
		user: "admin",
		password: "admin",
		headers: { "Content-Type": "application/xml"},
		content: { 
		  environment: dojo.byId("environment").value,
		  version: dojo.byId("version").value
		},
		sync: true,
		//timeout: 10000,
		handleAs: "xml",
		load: function(result, ioargs) {
		  deploymentSpec = result; 
		  dojo.publish("xldStatus", [{message: "InitDeplSpec HTTP status code " + ioargs.xhr.status, type: "message", duration: 2000}]);
		},
		error: commonError
	  });
	  return deploymentSpec;
	}

`prepareUpdate`:

	function prepareUpdate() {
	  deploymentSpec = "";
	  dojo.xhrGet({
		url: "http://localhost:4516/deployit/deployment/prepare/update",
		user: "admin",
		password: "admin",
		headers: { "Content-Type": "application/xml"},
		content: { 
		  version: dojo.byId("version").value,
		  deployedApplication: dojo.byId("deployedApplication").value,
		},
		sync: true,
		//timeout: 10000,
		handleAs: "xml",
		load: function(result, ioargs) {
		  deploymentSpec = result; 
		  dojo.publish("xldStatus", [{message: "UpdtDeplSpec HTTP status code " + ioargs.xhr.status, type: "message", duration: 2000}]);
		},
		error: commonError
	  });
	  return deploymentSpec;
	}

They both use `xhrGet` calls similar to the one for `checkDeploymentExists()`, and both return the entire XML object obtained, which is the deployment spec in XML format. In this example, they get their parameters from input fields using `dojo.byId`.

The next two functions for prepareDeployeds and `validDeployment()` are similar to each other as well.  They both use `xhrPost` to make a post request. The deployment spec already obtained is passed in as the value of the `postData` item after being serialized to a string by a handy method of the `XMLSerializer` object. Both return a deployment spec, potentially with new data added since we started.

`prepareDeployeds`:

	function prepareDeployeds(deploymentSpec) {
	  dojo.xhrPost({
		url: "http://localhost:4516/deployit/deployment/prepare/deployeds",
		user: "admin",
		password: "admin",
		headers: { "Content-Type": "application/xml"},
		sync: true,
		postData: (new XMLSerializer()).serializeToString(deploymentSpec),
		//timeout: 10000,
		handleAs: "xml",
		load: function(result, ioargs) {
		  deploymentSpec = result; 
		  dojo.publish("xldStatus", [{message: "PrepareDeployeds HTTP status code " + ioargs.xhr.status, type: "message", duration: 2000}]);
		  },
		error: commonError
	  });
	  return deploymentSpec;
	}

`validateDeployment`:

	function validateDeployment(deploymentSpec) {
	  dojo.xhrPost({
		url: "http://localhost:4516/deployit/deployment/validate",
		user: "admin",
		password: "admin",
		headers: { "Content-Type": "application/xml"},
		sync: true,
		postData: (new XMLSerializer()).serializeToString(deploymentSpec),
		//timeout: 10000,
		handleAs: "xml",
		load: function(result, ioargs) {
		  deploymentSpec = result; 
		  dojo.publish("xldStatus", [{message: "ValidateDeployeds HTTP status code " + ioargs.xhr.status, type: "message", duration: 2000}]);
		  },
		error: commonError
	  });
	  return deploymentSpec;
	}

Finally, we pass the deployment spec to the deploy function, which creates a task in pending status and returns its ID to us as text.  Note the `handeAs` item reflects this format in this `xhrPost` call.

	function deploy(deploymentSpec) {
	  taskId = "";
	  dojo.xhrPost({
		url: "http://localhost:4516/deployit/deployment",
		user: "admin",
		password: "admin",
		headers: { "Content-Type": "application/xml"},
		sync: true,
		postData: (new XMLSerializer()).serializeToString(deploymentSpec),
		//timeout: 10000,
		handleAs: "text",
		load: function(result, ioargs) {
		  taskId = result; 
		  dojo.publish("xldStatus", [{message: "Deploy HTTP status code " + ioargs.xhr.status, type: "message", duration: 2000}]);
		  },
		error: commonError
	  });
	  return taskId;
	}

We can start the task with this function, whose `xhrPost` call embeds the taskId in the url and omits the `postData` item: 

	function startTask(taskId) {
	  dojo.xhrPost({
	  url: "http://localhost:4516/deployit/task/" + taskId + "/start",
	  user: "admin",
	  password: "admin",
	  headers: { "Content-Type": "application/xml"},
	  sync: true,
	  handleAs: "xml",
	  load: function(result, ioargs) {
		dojo.publish("xldStatus", [{message: "StartTask HTTP status code " + ioargs.xhr.status, type: "message", duration: 2000}]);
		},
	  error: commonError
	  });
	}

Finally, the common error function is:

	// Global error variable accessed by multiple functions
	error = false;
	function commonError(text, ioargs) { 
	 error = true;
	 dojo.publish("xhrError", [{message: "HTTP status code " + ioargs.xhr.status, type: "error", duration: 2000}]);
	 return text;
	}

If you've found this article useful, please comment.  And let us know what other creative and interesting things you are doing with Dojo, JavaScript, and XL Deploy!
