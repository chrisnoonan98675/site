---
title: Create custom task types in XL Release
categories:
- xl-release
subject:
- Tasks
tags:
- task
- custom task
- python
- plugin
---

XL Release allows you to add custom task types that appear in the user interface and integrate seamlessly with other tasks in the release flow. You can use custom tasks to integrate with third-party components. For example, XL Release includes JIRA integration tasks, which are a set of custom tasks.

Custom tasks are written in the Python language.

To create a custom task, you need:

* The definition of the task and its properties
* The implementation of the task in a Python script

## Task definition

Custom tasks are stored in the `ext` or `plugins` directory of XL Release server:

* `ext` is used when you are developing a custom task. It contains custom task definitions in a file called `synthetic.xml`. Python scripts are placed in subdirectories of `ext`.
* `plugins` contains bundled custom tasks that are packaged in a single zip file with extension `.jar`

This is an example of the layout of the `ext` directory:

    synthetic.xml
    jira/
       CreateIssue.py
       UpdateIssue.py

### Sample custom task

You define the custom task in XML in `synthetic.xml`. As an example, this is the definition of the "Create Jira Issue" task:

    <synthetic xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns="http://www.xebialabs.com/deployit/synthetic"
           xsi:schemaLocation="http://www.xebialabs.com/deployit/synthetic synthetic.xsd">

      <type type="jira.CreateIssue" extends="xlrelease.PythonScript">
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

### Synthetic element

The `<synthetic>` element is the root node. It contains the XML grammar definitions and should not be changed. For XL Deploy users: this is the same definition language that is used to extend XL Deploy.

The `<type` element in `<synthetic>` defines the custom task. The `type` attribute defines the name of the custom task and is always of the form `prefix.TaskName`.

The prefix is the category of the task (for example, 'jira'). It should be in lowercase.

The task name is a descriptive name of the task (for example, "CreateIssue"). It should be in camel case.

The attribute `extends="xlrelease.PythonScript"` defines this type as a custom task for XL Release. Do not change it.

### Property element

Next are the properties. They are defined as nested `<property>` elements. The following attributes can be set on each property:

* `name`: Name of the property. This is also the name of the variable by which it is referred in the Python script.

* `category`: XL Release supports two categories:
	* `input`: Appear in the task in the XL Release UI and must be specified before the task starts. They are then passed to the Python script.
	* `output`: Can be set in the Python script. When the script completes, they can be copied into release variables in XL Release.

* `label`: Label used in the XL Release UI. If you do not specify a label, XL Release will attempt to make a readable version of the property name. For example, the "issueType" property will appear as "Issue Type" in the UI.

* `description`: Help text explaining the property in more detail. This will appear in the UI.

* `kind`: The property type, which is `string`, `int`, `boolean`, or `ci`. If omitted, this attribute defaults to `string`.

* `password`: Set this attribute to `true` to instruct XL Release to treat the property as a password. The content of password fields are obscured in the UI and encrypted in network traffic and storage.

* `size`: Indicates how much space the UI gives to the property. Supported levels are `default`, `small`, `medium`, and `large`.

* `default`: The default value of the property.

* `referenced-type`: Indicates the type of CI this property can reference (only apply if `kind` is set to `ci`).

### Add custom tasks

After you save `synthetic.xml` and restart the XL Release server, the custom task appears in the UI and you can add it to the release flow editor like any other task.

![Select custom task](../images/select-custom-task.png)

This is how the above task definition looks like in the task details window:

![Jira task](../images/jira-task-details.png)

## Python scripts

When the custom task becomes active, it triggers the Python script that is associated with it. Scripts must be written in the Jython dialect of Python, which is version 2.6 of Python running in the Java VM, with full access to the Java 7 API. 

Store scripts in a directory that has the same name as the prefix of the task type definition. The script file name has the same name as the name of the task, followed by the `.py` extension. For example, the Python script for the `jira.CreateIssue` task must be stored in `jira/CreatePython.py`.

Input properties are available as variables in the Python script. You can set output values by assigning values to their corresponding variables in the script. After execution, the script variables are copied to the release variables that were specified on the task in the UI.

For example, this is a possible implementation of the `jira.CreateIssue` task in Python:

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


#### HttpRequest

`HttpRequest` is a class provided by XL Release that is used to perform HTTP calls. It has the following API:

        class HttpRequest:
            def __init__(self, params, username = None, password = None):
                """
                Builds an HttpRequest

                :param params: an HttpConnection
                :param username: the username
                    (optional, it will override the credentials defined on the HttpConnection object)
                :param password: an password
                    (optional, it will override the credentials defined on the HttpConnection object)
                """

            def doRequest(self, **options):
                """
                Performs an HTTP Request

                :param options: A keyword arguments object with the following properties :
                    method: the HTTP method : 'GET', 'PUT', 'POST', 'DELETE'
                        (optional: GET will be used if empty)
                    context: the context url
                        (optional: the url on HttpConnection will be used if empty)
                    body: the body of the HTTP request for PUT & POST calls
                        (optional: an empty body will be used if empty)
                    contentType: the content type to use
                        (optional, no content type will be used if empty)
                    headers: a dictionary of headers key/values
                        (optional, no headers will be used if empty)
                :return: an HttpResponse instance
                """

            def get(self, context, **options):
                """
                Performs an Http GET Request

                :param context: the context url
                :param options: the options keyword argument described in doRequest()
                :return: an HttpResponse instance
                """

            def post(self, context, body, **options):
                """
                Performs an Http POST Request

                :param context: the context url
                :param body: the body of the HTTP request
                :param options: the options keyword argument described in doRequest()
                :return: an HttpResponse instance
                """

            def put(self, context, body, **options):
                """
                Performs an Http PUT Request

                :param context: the context url
                :param body: the body of the HTTP request
                :param options: the options keyword argument described in doRequest()
                :return: an HttpResponse instance
                """

            def delete(self, context, **options):
                """
                Performs an Http DELETE Request

                :param context: the context url
                :param options: the options keyword argument described in doRequest()
                :return: an HttpResponse instance
                """


        class HttpResponse:
            def getStatus(self):
                """
                Gets the status code
                :return: the http status code
                """

            def getResponse(self):
                """
                Gets the response content
                :return: the reponse as text
                """

            def isSuccessful(self):
                """
                Checks if request successful
                :return: true if successful, false otherwise
                """

            def getHeaders(self):
                """
                Returns the response headers
                :return: a dictionary of all response headers
                """

            def errorDump(self):
                """
                Dumps the whole response
                """

### Examples

#### Posting JSON

        request = HttpRequest({'url': 'http://site'})
        response = request.post(
            '/api/addObject',
            '{"json": "content"}',
            contentType = 'application/json')
        content = response.getResponse()
        status = response.getStatus()

#### Using connection credentials

        request = HttpRequest({'url': 'http://site'}, "username", "password")
        response = request.get('/api/tasks')
        content = response.getResponse()
        status = response.getStatus()

#### Using a proxy

        request = HttpRequest({
            'url': 'http://site',
            'proxyHost': 'proxy.company',
            'proxyPort': '8080'})
        response = request.get('/api/tasks')
        content = response.getResponse()
        status = response.getStatus()

#### Getting results using a configuration object

If your task definition has a [configuration object](/xl-release/how-to/create-custom-configuration-types-in-xl-release.html#reference-a-configuration-instance-from-a-custom-task) defined using:

        <property name="jiraServer" category="input" label="Server" referenced-type="jira.Server" kind="ci"/>

You can use the jiraServer instance like that:

        request = HttpRequest(jiraServer)
        response = request.get('/api/tasks', contentType = 'application/json')
        content = response.getResponse()
        status = response.getStatus()

`HttpRequest` will then use the settings of the HttpConnection

### XL Release API

The [XL Release API](/xl-release/latest/rest-api/) is available in scripts : You can view the [Jython API Manual](/jython-docs/#!/xl-release/4.6.x/) to find a complete description of the methods you can call.

## Further customization

You can add the following properties to the `<type>` element to further customize your task:

    <type type="myplugin.MyTask" extends="xlrelease.PythonScript">
        <property name="scriptLocation" required="false" hidden="true" default="my/custom/dir/script.py" />
        <property name="iconLocation" required="false" hidden="true" default="my/custom/dir/icon.png" />
        <property name="taskColor" hidden="true" default="#9C00DB" />
        ...

* `scriptLocation`: Specifies a custom script location that overrides the default rules.
* `iconLocation`: Location of an icon file (PNG or GIF format) that is used in the UI for this task.
* `taskColor`: The color to use for the task in the UI, specified in HTML hexadecimal RGB format.

## Packaging

You can compress the contents of the `ext` folder into a single file with a `.jar` extension and place this file in the `plugins` directory. XL Release reads custom tasks from this location.

When compressing, do not include the `ext` folder as part of the path. The `synthetic.xml` file should be in the root of the `.jar` file. On Unix systems, use these commands:

    cd ext
    zip -r ../myplugin.jar .

On Microsoft Windows systems, use Windows Explorer to compress the individual files in the `ext` folder.

**Note:** Do not use `.zip` as the extension of the compressed file.

You can have multiple plugins and define contents in the `ext` folder at the same time. The `ext` directory takes precedence over the `plugins` directory.

If you change `ext/synthetic.xml` or the contents of the `plugins` folder, you must restart the XL Release server. You do not need to restart the server if you change the contents of a Python script.
