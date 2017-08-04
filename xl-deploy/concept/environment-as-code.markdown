---
title: Environment as code using Xfile
categories:
- xl-deploy
subject:
- Environment
tags:
- as code
- environment
- xfile
- dsl
since:
- XL Deploy 7.1.0
---

XL Deploy 7.1.0 introduces the environment-as-code feature, which enables you to store information about your infrastructure and environments in an _Xfile_, a Groovy file that you can store in source control. This feature allows you to maintain and update environment and infrastructure information outside of the XL Deploy repository, where it can be updated by multiple teams or automatically updated by infrastructure automation tools.

## The Xfile

The Xfile is a Groovy file that describes the desired state of infrastructure and/or environment configuration items (CIs) in the XL Deploy repository. In the Xfile, you define the CIs that should exist, their properties, and their relationships with other CIs; when you apply the Xfile using the REST API or command-line interface (CLI), XL Deploy determines whether CIs need to be created, deleted, or modified to achieve the state described in the Xfile.

Every CI or set of CIs defined in the Xfile must have a `scope`, which is the directory in which CIs should be created or modified, or from which CIs should be deleted. The scope ensures that you do not inadvertently change or delete CIs that are not related to the Xfile. The Xfile also supports setting or changing local permissions on the directory or directories that are in scope.

The Xfile determines whether a CI or set of CIs should be created under the **Environments** or **Infrastructure** parent node in the repository, as identified by the `forEnvironments` and `forInfrastructure` keywords.

Note that:

* The scope must be a directory under the **Environments** or **Infrastructure** parent node. The scope cannot be a parent node itself.
* At this time, the Xfile cannot be used to define CIs under **Applications** or **Configuration**.

### Sample Xfile

This is a sample Xfile that defines a `docker.SwarmManager` CI, an environment, and a dictionary.

{% highlight groovy %}
xld {
    scope(
      forInfrastructure: directory('Infrastructure/MyInfra') {
        permissions = [
          'admins': ['read', 'controltask#execute', 'generate#dsl', 'repo#edit'],
          'deployer': ['read', 'controltask#execute', 'repo#edit']
        ]
      }
    ) {
      infrastructure('dkr-prd-mgr', 'docker.SwarmManager') {
        dockerHost = 'tcp://192.168.99.103:2376'
        registries = [
          ref('Configuration/Docker/Docker Hub')
        ]
      }
    }
    scope(
      forEnvironments: 'Environments/Production'
      ) {
       environment('PROD') {
        members = [
           ref('Infrastructure/Docker/dkr-prd-mgr')
         ]
         dictionaries = [dictionary('Production Settings', ['logFilePath': '/tmp', 'HOST_PORT': '8181', 'title': 'Dockerized SampleApp', 'timeout': '8', 'logLevel': 'WARN'])]
      }
    }
}
{% endhighlight %}

### Define CI properties

These are examples of the Groovy format for CI property kinds:

{:.table .table-bordered}
| Property kind | Groovy format example |
| ------------- | --------------------- |
| Integer | `integerProp = 123` |
| String | `stringProp = 'value1'` |
| Strings | <code>stringsProp = [<br />&nbsp;&nbsp;'value1',<br />&nbsp;&nbsp;'value2'<br />]</code> |
| Encrypted strings | <code>stringsEncryptedProp = [<br />&nbsp;&nbsp;encrypted('value1'),<br />&nbsp;&nbsp;encrypted('value2')<br />]</code> |
| Map of strings | <code>stringsMap = [<br />&nbsp;&nbsp;'key1': 'value1',<br />&nbsp;&nbsp;'key2': 'value2'<br />] |
| Boolean | `booleanProp = true` |
| Date | `date('2017-06-28T18:40:35')` |
| Enum | `FRIDAY` or `java.time.DayOfWeek.FRIDAY` |

### Set permissions on directories

You can set local permissions on the directories that will be created or modified when an Xfile is applied. For example, to create an empty directory with permissions set on it:

{% highlight groovy %}
xld {
  scope(
    forInfrastructure: directory('Infrastructure/Empty') {
      permissions = [
        'admins': ['read', 'controltask#execute', 'generate#dsl', 'repo#edit'],
        'deployer': ['read', 'controltask#execute', 'generate#dsl', 'repo#edit'],
        'developer': ['read', 'generate#dsl', 'repo#edit']
      ]
    }
  ) { }
}
{% endhighlight %}

If you do not specify the `permissions` property, the directory's local permissions will not be modified.

### Refer to another CI

You can refer to a CI that is defined in another part of the Xfile or to a CI that already exists in the XL Deploy repository. For example:

{% highlight groovy %}
xld {
  scope(
    forEnvironments: 'Environments/Internal/Testing'
  ) {
    environment('ACC01') {
      members = [
        ref('Infrastructure/Other middleware/apache-22/webserver1'),
        ref('Infrastructure/JBoss/jboss-51/jboss-8080-1'),
        ref('Infrastructure/Other middleware/localhost/Final smoke test station'),
        ref('Infrastructure/Other middleware/ora-10g-express-unix/10gXE')
      ]
      dictionaries = [
        ref('Environments/Dictionaries/Portal settings'),
        ref('Environments/Master dictionaries/JBoss config'),
        ref('Environments/Internal/Testing/Custom settings')
      ]
    }
    dictionary('Custom settings', [
      'LOGO': '/web/images/custom-logo.png',
      'TITLE': 'Custom User Portal Test'
    ])
  }
}
{% endhighlight %}

## Generate an Xfile

An easy way to get started with the Xfile format is to generate Xfiles from the directories that are already defined in your XL Deploy repository. You can use the XL Deploy REST API, the command-line interface (CLI), or the default graphical user interface to generate Xfiles. To generate an Xfile, you must have the *export#dsl* [local permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html).

Note that:

* You can only generate an Xfile from a directory or directories, not from the **Environments** or **Infrastructure** parent node.
* Nested directories are defined as separate scopes within the Xfile.
* When you generate an Xfile that contains an `enum` CI property, the property value will contain the full Java name. For example, you can specify the value `unix` for the `os` property on an `overthere.SshHost` CI, but when the Xfile is generated, the value will be `com.xebialabs.overthere.OperatingSystemFamily.UNIX`. You can also use the full Java name when applying an Xfile.

{% comment %}### Generate an Xfile using the REST API {% endcomment %}

### Generate an Xfile using the CLI

To generate an Xfile from multiple directories and print the Groovy code in the console, execute:

{% highlight python %}
repository.generateDsl(["Infrastructure/MyInfra","/Infrastructure/Experimental"])
{% endhighlight %}

To generate an Xfile from multiple directories and print the Groovy code to a file, execute:

{% highlight python %}
from java.io import File
repository.generateDsl(["Infrastructure/MyInfra","Infrastructure/Experimental"],File("/path/to/file.groovy"))
{% endhighlight %}

### Generate an Xfile using the user interface

To generate an Xfile using the default XL Deploy user interface:

1. Expand **Environments** or **Infrastructure** from the left pane.
1. Hover over a folder, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Generate DSL**. A `.groovy` file is generated and saved on your local machine.

## Apply an Xfile

You can use the XL Deploy REST API or the command-line interface (CLI) to apply Xfiles to the XL Deploy repository. To apply an Xfile, you must have the *repo#edit* [local permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html) in the parts of the repository affected by the Xfile.

**Note:** For security reasons, Xfiles are applied in a sandbox that does not have access to XL Deploy APIs.

{% comment %}### Apply an Xfile using the REST API {% endcomment %}

### Apply an Xfile using the CLI

To apply an Xfile, execute:

{% highlight python %}
from java.io import File
repository.applyDsl(File("/path/to/file.groovy"))
{% endhighlight %}

## Security recommendations when using environment as code

* Use the *export#dsl* local permission to limit the users who can export CIs containing sensitive data (encrypted property values such as password properties).
* Use [property placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html#property-placeholders) instead of storing sensitive data directly in CIs.
