---
title: Create custom task types
categories:
xl-release
subject:
Extending XL Release
tags:
task
custom task
python
plugin
customization
---

XL Release allows you to add custom task types that appear in the user interface and integrate seamlessly with other tasks in the release flow. You can use custom tasks to integrate with third-party components. For example, XL Release includes JIRA integration tasks, which are a set of custom tasks.

Custom tasks are written in the Python language.

To create a custom task, you need:

* The definition of the task and its properties
* The implementation of the task in a Python script

## Defining a custom task

Custom tasks are stored in the `ext` or `plugins` directory of XL Release server:

* `ext` is used when you are developing a custom task. It contains custom task definitions in a file called `synthetic.xml`. Python scripts are placed in subdirectories of `ext`.
* `plugins/__local__` contains bundled custom tasks that are packaged in a single zip file with extension `.jar`

    **Note:** For pre 7.5 versions, the folder containing the `.jar` files is `plugins`.

This is an example of the layout of the `ext` directory:

    synthetic.xml
    jira/
       CreateIssue.py
       UpdateIssue.py

You define the custom task in XML in `synthetic.xml`. As an example, this is the definition of the "Create Jira Issue" task:

{% highlight xml %}
<synthetic xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns="http://www.xebialabs.com/deployit/synthetic"
       xsi:schemaLocation="http://www.xebialabs.com/deployit/synthetic synthetic.xsd">

  <type type="jira.CreateIssue" extends="xlrelease.PythonScript" label="Create issue">
    <property name="jiraServer"  category="input" label="Server" referenced-type="jira.Server" kind="ci"/>
    <property name="username"    category="input"/>
    <property name="password"    category="input" kind="string" password="true" />
    <property name="project"     category="input"/>
    <property name="title"       category="input"/>
    <property name="description" category="input" size="large" />
    <property name="issueType"   category="input" default="Task"/>
    <property name="issueId"     category="output"/>
  </type>

</synthetic>
{% endhighlight %}

### Synthetic element

The `<synthetic>` element is the root node. It contains the XML grammar definitions and should not be changed. For XL Deploy users: this is the same definition language that is used to extend XL Deploy.

The `<type>` element in `<synthetic>` defines the custom task. The `type` attribute defines the name of the custom task and is always of the form `prefix.TaskName`:

* The prefix is the category of the task (for example, 'jira'). It should be in lowercase.
* The task name is a descriptive name of the task (for example, "CreateIssue"). It should be in camel case.

**Important:** The `extends="xlrelease.PythonScript"` attribute defines the type as a custom task for XL Release. Do not change it.

The `label` attribute defines the name of the task that will appear while adding a new task in the release flow. By default, if is not defined, XL Release will create a label for you with the following format: `Prefix: Task Name` using the capital letters as new words (for example: "Jira: Create Issue"). You can override the `prefix` by adding a colon in the `label` attribute; for example, `label="Integration: Create Issue"`.

You can add the following properties to the `<type>` element to further customize your task:

* `scriptLocation`: Specifies a custom script location that overrides the default rules.
* `iconLocation`: Location of an icon file (PNG or GIF format) that is used in the UI for this task.
* `taskColor`: The color to use for the task in the UI, specified in HTML hexadecimal RGB format.

For example:

{% highlight xml %}
<type type="myplugin.MyTask" extends="xlrelease.PythonScript">
    <property name="scriptLocation" required="false" hidden="true" default="my/custom/dir/script.py" />
    <property name="iconLocation" required="false" hidden="true" default="my/custom/dir/icon.png" />
    <property name="taskColor" hidden="true" default="#9C00DB" />
    ...
{% endhighlight %}

### Property element

Next are the properties. They are defined as nested `<property>` elements. The following attributes can be set on each property:

{:.table .table-striped}
| Property | Description |
| -------| ----------|
| `name` | Name of the property. This is also the name of the variable by which it is referred in the Python script. |
| `category` | XL Release supports two categories.<br />`input` appear in the task in the XL Release UI and must be specified before the task starts. They are then passed to the Python script. If you add an `input` type and do not specify the value for the `required` field, it will be set to the default value `true`. <br />`output` can be set in the Python script. When the script completes, they can be copied into release variables in XL Release. |
| `label` | Group and label used in the XL Release UI. If you do not specify a group and label, XL Release will attempt to make a readable version. For example, `myCompany.myTask` will appear as a `My Task` task type in the `My Company` group.<br/><br/>In XL Release 6.1.0 and later, you can group task types in your preferred groups by adding the group before a colon in the label; for example, `Other Items: My Task`. |
| `description` | Help text explaining the property in more detail. This will appear in the UI. |
| `kind` | The property type, which is `string`, `integer`, `boolean`, or `ci`. In XL Release 4.8.0 and later, the `list_of_string`, `set_of_string`, and `map_string_string` property types are also available. In XL Release 6.1.0 and later, the `enum` type is also available.<br /><br />If omitted, this attribute defaults to `string`. |
| `password` | Set this attribute to `true` to instruct XL Release to treat the property as a password. The content of password fields are obscured in the UI and encrypted in network traffic and storage. |
| `size` | Indicates how much space the UI gives to the property. Supported levels are `default`, `small`, `medium`, and `large`. |
| `default` | The default value of the property. |
| `referenced-type` | Indicates the type of CI this property can reference (only apply if `kind` is set to `ci`). |

#### Output properties size limit

To prevent performance issues, output properties of type `string` are limited to 32 Kb. If your script adds content that exceeds the limit, XL Release will truncate the property. You can still print the property inside the script and XL Release will attach the content to the task.

You can change this limit for each task type in the `XL_RELEASE_SERVER_HOME/conf/deployit-defaults.properties` file. For example:

    #webhook.JsonWebhook.maxOutputPropertySize=18000

To change the limit, delete the number sign (`#`) at the beginning of the relevant line, change the limit as desired, then save the file and restart the XL Release server.

### Add custom tasks

After you save `synthetic.xml` and restart the XL Release server, the custom task appears in the UI and you can add it to the release flow editor like any other task.

![Select custom task](../images/select-custom-task.png)

This is how the above task definition looks like in the task details window:

![Jira task](../images/jira-task-details.png)

### Python scripts

When the custom task becomes active, it triggers the Python script that is associated with it. For information about the script, refer to [API and scripting overview](/xl-release/how-to/api-and-scripting-overview.html).

Store scripts in a directory that has the same name as the prefix of the task type definition. The script file name has the same name as the name of the task, followed by the `.py` extension. For example, the Python script for the `jira.CreateIssue` task must be stored in `jira/CreateIssue.py`.

Input properties are available as variables in the Python script. You can set output values by assigning values to their corresponding variables in the script. After execution, the script variables are copied to the release variables that were specified on the task in the UI.

**Tip:** To concatenate multiple Python scripts and have XL Release schedule them, refer to [Using scheduling in scripts to connect to long running jobs](/xl-release/how-to/using-scheduling-in-scripts.html).

For example, this is a possible implementation of the `jira.CreateIssue` task in Python:

{% highlight python %}
import sys, string
import com.xhaus.jyson.JysonCodec as json

ISSUE_CREATED_STATUS = 201

content = """
{
    "fields": {
       "project":
       {
          "key": "%s"
       },
       "summary": "%s",
       "description": "%s",
       "issuetype": {
          "name": "%s"
       }
   }
}
""" % (project, title, description, string.capwords(issueType))

if jiraServer is None:
    print "No server provided."
    sys.exit(1)

jiraURL = jiraServer['url']
if jiraURL.endswith('/'):
    jiraURL = jiraURL[:len(jiraURL)-1]

# You can pass custom headers to the request
headers = {'myCustomHeaders': 'myValue'}

# jiraServer object may contains proxy information (field proxyHost and proxyPort)
request = HttpRequest(jiraServer, username, password)
response = request.post('/rest/api/2/issue', content, contentType = 'application/json', headers = headers)

if response.status == ISSUE_CREATED_STATUS:
    # In order to debug your script, you can print the status, the body and the headers
    print response.status
    print response.response
    print response.headers

    # you can access the http headers of the response
    if response.headers['Content-Type'].startswith('application/json'):
        # Parsing the response body as JSON
        data = json.loads(response.response)
        issueId = data.get('key')
        print "Created %s in JIRA at %s." % (issueId, jiraURL)
else:
    print "Failed to create issue in JIRA at %s." % jiraURL
    response.errorDump()
    sys.exit(1)
{% endhighlight %}

**Note:** Since XL Release 4.7.0, Jython scripts of custom task types are not run in a sandboxed environment and do not have any restrictions (in contrast to [Jython Script tasks](/xl-release/how-to/create-a-jython-script-task.html)). You do not have to update the `script.policy` file of your XL Release installation if you need additional access from your custom task type (such as to the filesystem or network). You still need to do this for versions prior to 4.7.0.

### HttpRequest

`HttpRequest` is a class provided by XL Release that is used to perform HTTP calls. Refer to the [Jython API](/jython-docs/#!/xl-release/4.7.x//service/HttpRequest.HttpRequest) for more information.

## Examples

### Posting JSON

{% highlight python %}
request = HttpRequest({'url': 'http://site'})
response = request.post(
    '/api/addObject',
    '{"json": "content"}',
    contentType = 'application/json')
content = response.getResponse()
status = response.getStatus()
{% endhighlight %}

### Using connection credentials

{% highlight python %}
request = HttpRequest({'url': 'http://site'}, "username", "password")
response = request.get('/api/tasks')
content = response.getResponse()
status = response.getStatus()
{% endhighlight %}

### Using a proxy

{% highlight python %}
request = HttpRequest({
    'url': 'http://site',
    'proxyHost': 'proxy.company',
    'proxyPort': '8080'})
response = request.get('/api/tasks')
content = response.getResponse()
status = response.getStatus()
{% endhighlight %}

### Getting results using a configuration object

If your task definition has a [configuration object](/xl-release/how-to/create-custom-configuration-types-in-xl-release.html#reference-a-configuration-instance-from-a-custom-task) defined using:

{% highlight xml %}
<property name="jiraServer" category="input" label="Server" referenced-type="jira.Server" kind="ci"/>
{% endhighlight %}

You can use the `jiraServer` instance like that:

{% highlight python %}
request = HttpRequest(jiraServer)
response = request.get('/api/tasks', contentType = 'application/json')
content = response.getResponse()
status = response.getStatus()
{% endhighlight %}

`HttpRequest` will then use the settings of the HttpConnection.

## XL Release API

The [XL Release API](/xl-release/latest/rest-api/) is available for scripts. Refer to the [Jython API reference](/xl-release/latest/jython-api/index.html) to find a complete description of the methods you can call.

For an extended example, refer to [Creating XL Release tasks dynamically using Jython API](http://blog.xebialabs.com/2015/08/11/creating-xl-release-tasks-dynamically-using-jython-api/).

## Changing or removing customizations

Before changing or removing a custom task type or one of the properties of a custom task type, ensure that the type is not used in any releases or templates. Changing or removing a type that is in use may result in errors.

## Packaging

You can compress the contents of the `ext` folder into a single file with a `.jar` extension and place this file in the `plugins` directory. XL Release reads custom tasks from this location.

When compressing, do not include the `ext` folder as part of the path. The `synthetic.xml` file should be in the root of the `.jar` file. On Unix systems, use these commands:

    cd ext
    zip -r ../myplugin.jar .

On Microsoft Windows systems, use Windows Explorer to compress the individual files in the `ext` folder.

**Note:** Do not use `.zip` as the extension of the compressed file.

You can have multiple plugins and define contents in the `ext` folder at the same time. The `ext` directory takes precedence over the `plugins` directory.

If you change `ext/synthetic.xml` or the contents of the `plugins` folder, you must restart the XL Release server. You do not need to restart the server if you change the contents of a Python script.
