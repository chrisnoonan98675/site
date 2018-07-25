---
title: Extend the XL Release Plugin for XL Deploy using python SDK
categories:
xl-release
subject:
Bundled plugins
tags:
jython
xldeploy-py
xlr-xld-plugin
since:
XL Release 8.1.0
---

You can extend the functionality of the official XL Release plugin for XL Deploy by using [xldeploy-py](https://pypi.org/project/xldeploy-py/), the python SDK for XL Deploy.
The SDK allows interaction with XL Deploy over the REST services and supports the following:

* Deployfile
* Deployment
* Metadata
* Package
* Repository
* Tasks

Each of the above services has specific methods that correspond to the [REST services offered by XL Deploy](/xl-deploy/8.0.x/rest-api/index.html).

**Note:** Not all methods and services available in the REST API are currently supported by the SDK. The SDK is customizable depending on the requirements of the clients using it.

In XL Release, you can create custom tasks. A custom task contains an XML section that becomes part of the `synthetic.xml` in the `ext` folder of the XL Release server, and a python file stored to a location adjacent to this `synthetic.xml`.
For more information, see [How to Create Custom Task Types](/xl-release/how-to/create-custom-task-types.html).

To extend the XL Release plugin for XL Deploy, you can create custom tasks that can retrieve items from XL Deploy or perform actions in XL Deploy using the SDK. These custom tasks can extend the `xldeploy.XldTask` to reuse properties required for any task related to XL Deploy.

## Example of defining a custom task

Create a custom task to check if a CI exists on XL Deploy Server. The new type to be included in `synthetic.xml` contains one new `scriptLocation` parameter representing the location of the python script within the `ext` directory.
The other parameters are inherited from `xldeploy.XldTask`.

### Modify the `synthetic.xml`

{% highlight xml %}
<type type="xld.CheckCIExist" extends="xldeploy.XldTask" label="XL-Deploy: Check CI exists" description="Custom Task to check if a CI exists">
    <property name="scriptLocation" default="CheckExists.py" hidden="true"/>
    <property name="ci_path" category="input" label="CI Path" required="true"/>
</type>
{% endhighlight %}

The `CheckExists.py` python script referred above can perform the required actions using the python SDK. The official plugin already contains methods to create the `xldeploy-py` client.
You must pass the `server` property of the task as an argument to method `get_api_client()`. The client returned can be used to call methods on any of the services mentioned above.

### The `CheckExists.py` python script

{% highlight python %}
from xlrxldeploy import *

client = get_api_client(task.getPythonScript().getProperty("server"))
path="some/path/to/a/ci"
print (client.repository.exists(path))
{% endhighlight %}
