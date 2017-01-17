---
title: Use advanced scripting in custom task types
categories:
- xl-release
subject:
- Task types
tags:
- task
- custom task
- python
- plugin
- customization
since:
- XL Release 6.1.0
---

XL Release 6.1.0 introduced a new API in the Python script context, which enables you to create [custom task types](/xl-release/how-to/create-custom-task-types.html) that concatenate script executions and allow XL Release to schedule them.

Before XL Release 6.1.0, it was not possible to execute multiple Python scripts in a single task. This meant that, if you wanted to fetch a resource by executing an `HttpRequest` and the resource was not yet available, your script would need to loop until the resource became available. This looping could cause performance issues on the XL Release server.

In XL Release 6.1.0 and later, you can provide a script that checks for the availability of a resource and another script that does something when the conditions are satisfied. XL Release will schedule the execution of the scripts and poll for the availability of the resource according to a configurable interval. If the server is stopped while the pollable script is running, XL Release will restart the script when the server is started again.

Also, as of XL Release 6.1.0, you can show custom text under the custom task name in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html).

## Configure the polling interval

By default, the polling interval is 5 seconds. You can configure it in the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file:

    xl.durations.customScriptTaskScheduleInterval = 10 seconds

Note that the polling interval must not be less than 1 second, as this will overload the system.

## Advanced Python script example

This example shows how the [Jenkins Build](/xl-release/how-to/create-a-jenkins-task.html) task is implemented.

### Definition in `synthetic.xml`

This is the definition of the Jenkins Build task in the `synthetic.xml` file:

{% highlight xml %}
...
<type type="jenkins.Build" extends="xlrelease.PythonScript">
       <property name="scriptLocation" default="jenkins/Build.py" hidden="true" />
       <property name="iconLocation" default="jenkins/jenkins.png" hidden="true" />

       <property name="jenkinsServer" category="input" label="Server" referenced-type="jenkins.Server" kind="ci" description="Jenkins server to connect to"/>
       <property name="username" category="input" required="false" description="Overrides the password used to connect to the server"/>
       <property name="password" password="true" category="input" required="false" description="Overrides the password used to connect to the server"/>
       <property name="jobName" category="input" description="Name of the job to trigger; this job must be configured on the Jenkins server. If the job is located in one or more Jenkins folders, add  a 'job' segment between each folder."/>
       <property name="jobParameters" category="input" size="large" required="false" description="If the Jenkins job expects parameters, provide them here, one parameter per line (for example,  paramName=paramValue)"/>

       <property name="buildNumber" category="output" required="false" description="Build number of the triggered job"/>
       <property name="buildStatus" category="output" required="false" description="Build status of the triggered job"/>
       <property name="location" category="script" required="false" description="Location header returned by jenkins"/>
</type>
...
{% endhighlight %}

The `location` property (of category `script`) works the same as the `output` property, but it is hidden from the UI. The `output` and `script` properties can be used to send information between Python scripts in the same task.

### Python scripts

The first Python script required for the Jenkins Build task is `jenkins/Build.py`:

{% highlight python %}
...
buildResponse = request.post(buildContext, '', contentType = 'application/json')

if buildResponse.isSuccessful():
    # query the location header which gives a queue item position

    location = None
    if 'Location' in buildResponse.getHeaders() and '/queue/item/' in buildResponse.getHeaders()['Location']:
        location = '/queue/item/' + filter(None, buildResponse.getHeaders()['Location'].split('/'))[-1] + '/'

    task.setStatusLine("Build queued")
    task.schedule("jenkins/Build.wait-for-queue.py")

else:
    print "Failed to connect at %s." % buildUrl
    buildResponse.errorDump()
    sys.exit(1)
{% endhighlight %}

The status line provided in `task.setStatusLine("Build queued")` will appear in the UI:

![Task status line](../images/task-status-line-1.png)

The `task.schedule(pollingScriptPath)` call lets XL Release know that after the current script is finished the task does not finish yet. Instead another script should be executed after a delay. You must pass the path to the script in the method argument. You can also specify an interval `task.schedule(pollingScriptPath, 2)` that will be used instead of the default one.

To fail the script, use `sys.exit(1)`.

If the response was successful, the next script triggered will be `Build.wait-for-queue.py`:

{% highlight python %}
...
if location:
    # check the response to make sure we have an item
    response = request.get(location + 'api/json', contentType='application/json')
    if response.isSuccessful():
        buildNumber = JsonPathResult(response.response, 'executable.number').get()
        if not buildNumber:
            # if there is no build number yet then we continue waiting in the queue
            task.schedule("jenkins/Build.wait-for-queue.py")
        else:
            # if we have been given a build number this item is no longer in the queue but is being built
            task.setStatusLine("Running build #%s" % jobBuildNumber)
            task.schedule("jenkins/Build.wait-for-build.py")
    else:
        print "Could not determine build number for queued build at %s." % (jenkinsURL + location + 'api/json')
        sys.exit(1)
else:
    ...
{% endhighlight %}

![Task status line](../images/task-status-line-2.png)

As before, this script configures the status line in the task and calls the next script to be executed, `jenkins/Build.wait-for-build.py`:

{% highlight python %}
...
response = request.get(jobContext + str(buildNumber) + '/api/json', contentType='application/json')
if response.isSuccessful():
    buildStatus = JsonPathResult(response.response, 'result').get()
    duration = JsonPathResult(response.response, 'duration').get()

    if buildStatus and duration != 0:
        print "\nFinished: %s" % buildStatus
        if buildStatus != 'SUCCESS':
            sys.exit(1)

    else:
        # Continue waiting for the build to be finished
        task.schedule("jenkins/Build.wait-for-build.py")

else:
    ...
{% endhighlight %}

After the script finishes without an error and no other script was scheduled, XL Release marks the task as completed.
