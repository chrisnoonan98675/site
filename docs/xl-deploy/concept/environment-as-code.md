---
title: Environment as code using Deployfile
categories:
xl-deploy
subject:
Environment
tags:
as code
environment
deployfile
dsl
since:
XL Deploy 7.1.0
---

XL Deploy 7.1.0 introduces the environment-as-code feature, which enables you to store information about your infrastructure and environments in a _Deployfile_, a Groovy file that you can store in source control. This feature allows you to maintain and update environment and infrastructure information outside of the XL Deploy repository, where it can be updated by multiple teams or automatically updated by infrastructure automation tools.

## The Deployfile

The Deployfile is a Groovy file that describes the desired state of infrastructure, environment and/or application configuration items (CIs) in the XL Deploy repository. When an application is included, the configuration item will be created and the application will be deployed to the target environment.
In the Deployfile, you define the CIs that should exist, their properties, and their relationships with other CIs; when you apply the Deployfile using the [REST API](/xl-deploy/7.1.x/rest-api/com.xebialabs.deployit.core.api.DslService.html) or command-line interface (CLI), XL Deploy determines whether CIs need to be created, deleted, or modified to achieve the state described in the Deployfile.

Every CI or set of CIs must be defined in the Deployfile using the `define` syntax, which creates the directory where CIs are created or modified, or from where CIs can be deleted. The `define` syntax ensures that you do not inadvertently change or delete CIs that are not related to the Deployfile. The Deployfile also supports setting or changing local permissions on the directory or directories that are defined.

The Deployfile determines whether a CI or set of CIs should be created under the **Environments** or **Infrastructure** parent node in the repository, as identified by the `forEnvironments` and `forInfrastructure` keywords.

**Note:** At this time, the Deployfile cannot be used to define CIs under **Configuration**.

### Sample Deployfile

This is a sample Deployfile that defines a `docker.SwarmManager` CI, an environment, and a dictionary.

{% highlight groovy %}
xld {
  define(forInfrastructure: directory('Infrastructure/MyInfra') {
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
  define(forEnvironments: 'Environments/Production') {
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
| ------------| --------------------|
| Integer | `integerProp = 123` |
| String | `stringProp = 'value1'` |
| Strings | <code>stringsProp = [<br />&nbsp;&nbsp;'value1',<br />&nbsp;&nbsp;'value2'<br />]</code> |
| Encrypted strings | <code>stringsEncryptedProp = [<br />&nbsp;&nbsp;encrypted('value1'),<br />&nbsp;&nbsp;encrypted('value2')<br />]</code> |
| Map of strings | <code>stringsMap = [<br />&nbsp;&nbsp;'key1': 'value1',<br />&nbsp;&nbsp;'key2': 'value2'<br />] |
| Boolean | `booleanProp = true` |
| Date | `date('2017-06-28T18:40:35')` |
| Enum | `FRIDAY` or `java.time.DayOfWeek.FRIDAY` |

### Use `ref` and `read` syntaxes

The `ref` syntax represents any CI property of the CI kind or individual elements of `SET_OF_CI` or `LIST_OF_CI`. Example:

        members = [ref('Infrastructure/CICD/test_server_1.0.0')]

When a CI property of the `STRING` type contains security keys like in `.pem` or `.cert` files, use the `read` syntax to wrap your file path instead of copying the content of those files in your Deployfile.

        read('CA.cert')

You can also use the absolute path:

        read('/var/.ssh/apiserver.pem')        

The XL deploy server handles the rest of the process.

#### Example how to create a docker engine using a Deployfile:

          xld {
            define(
              forInfrastructure: 'Infrastructure/dir'
            ) {
              infrastructure('dockerEngine', 'docker.Engine') {
                dockerHost = 'tcp://127.0.0.1:2336'
                enableTLS = true
                certPem = read('/Users/admin/.docker/machine/machines/node1/cert.pem')
                keyPem = read('certs/key.pem')
                caPem = read('../ca.pem')
              }
            }
          }

### Set permissions on directories

You can set local permissions on the directories that will be created or modified when a Deployfile is applied. For example, to create an empty directory with permissions set on it:

{% highlight groovy %}
xld {
  define(forInfrastructure: directory('Infrastructure/Empty') {
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

You can refer to a CI that is defined in another part of the Deployfile or to a CI that already exists in the XL Deploy repository. For example:

{% highlight groovy %}
xld {
  define(forEnvironments: 'Environments/Internal/Testing') {
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

## Generate a Deployfile

An easy way to get started with the Deployfile format is to generate Deployfiles from the directories that are already defined in your XL Deploy repository. You can use the XL Deploy REST API, the command-line interface (CLI), or the default graphical user interface to generate Deployfiles. To generate a Deployfile, you must have the *generate#dsl* [local permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html).

Note that:

* You can only generate a Deployfile from a directory or directories, not from the **Environments** or **Infrastructure** parent node.
* Nested directories are defined as separate structures within the Deployfile.
* When you generate a Deployfile that contains an `enum` CI property, the property value will contain the full Java name. For example, you can specify the value `unix` for the `os` property on an `overthere.SshHost` CI, but when the Deployfile is generated, the value will be `com.xebialabs.overthere.OperatingSystemFamily.UNIX`. You can also use the full Java name when applying a Deployfile.

### Generate a Deployfile using the REST API

This is an example of generating a Deployfile using cURL:

{% highlight curl %}
curl --user "matt:secret01" --request GET 'http://localhost:4516/deployit/deployfile/generate?folder=Environments/MyEnvs'
{% endhighlight %}

### Generate a Deployfile using the CLI

To generate a Deployfile from multiple directories and print the Groovy code in the console, execute:

{% highlight python %}
repository.generateDeployfile(["Infrastructure/MyInfra","/Infrastructure/Experimental"])
{% endhighlight %}

To generate a Deployfile from multiple directories and print the Groovy code to a file, execute:

{% highlight python %}
from java.io import File
repository.generateDeployfile(["Infrastructure/MyInfra","Infrastructure/Experimental"],File("/path/to/file.groovy"))
{% endhighlight %}

### Generate a Deployfile using the user interface

To generate a Deployfile using the default XL Deploy user interface:

1. Expand **Environments** or **Infrastructure** from the left pane.
1. Hover over a folder, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Generate Example Deployfile**. A `.groovy` file is generated and saved on your local machine.

## Apply a Deployfile

You can use the XL Deploy REST API or the command-line interface (CLI) to apply Deployfiles to the XL Deploy repository. To apply a Deployfile, you must have the *repo#edit* [local permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html) in the parts of the repository affected by the Deployfile.

**Note:** For security reasons, Deployfiles are applied in a sandbox that does not have access to XL Deploy APIs.

### Apply a Deployfile using the Lightweight CLI

To apply a Deployfile, open a command terminal and execute this command:

        xld --url http://xl-deploy.mycompany.com:4516 --username john --password secret01 apply /Users/john/Deployfile

### Apply a Deployfile using the REST API

This is an example of applying a Deployfile using cURL:

{% highlight curl %}
curl -v --user "matt:secret01" --request POST -H "Content-Type: text/plain" --data 'xld {scope(forEnvironments: "Environments/MyEnvs"){}}' 'http://localhost:4516/deployit/deployfile/apply'
{% endhighlight %}

### Apply a Deployfile using the CLI

To apply a Deployfile, execute:

{% highlight python %}
from java.io import File
repository.applyDeployfile(File("/path/to/file.groovy"))
{% endhighlight %}

## Security recommendations when using the Deployfile

Use [property placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html#property-placeholders) instead of storing sensitive data directly in CIs.

### Generate encrypted values

To generate encrypted values using the Lightweight CLI, execute this command:

        xld --url http://localhost:4516 --username admin --password admin encrypt

You will prompted with this message: `What is plain text to encrypt?`. Enter the value to be encrypted, for example: `mypassword`.
To encrypt a value using the CLI, execute this command:

        repository.encrypt("My password")

Both examples of commands return an encrypted value such as `{b64}jnlt/MPFYnteuHr5rbCvzw==`. You can use this value as `encryptedString` wrapped in `encrypted('{b64}jnlt/MPFYnteuHr5rbCvzw==')`.        
