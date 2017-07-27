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

As of XL Deploy version 7.1.0, you can store information about your environments in a Groovy-based DSL script that you can keep in version control. This allows you to maintain and update environment information outside of the XL Deploy repository. The environment information can be updated by multiple users or teams or automatically updated by infrastructure automation tools.

Environment as code provides the option to manage changes in your dynamic infrastructure data.

The Xfile format is a defined structure for a `.groovy` file that can be used to store your environment information.

## Example of an Xfile

{% highlight groovy %}
xld {
  scope(forInfrastructure: 'Infrastructure/MyInfra', forEnvironments: 'Environments/MyEnvs') {
    def myHost = infrastructure('MyHost', 'overthere.LocalHost') {
      os = 'unix'
    }
    environment('MyEnv') {
      members = [myHost]
    }
  }
}
{% endhighlight %}

## Define CI properties in an Xfile

You can define properties for your infrastructure using the `forInfrastructure` syntax or properties for your environment using the `forEnvironments` syntax.

Here is an example for defining containers in an infrastructure and dictionaries for your environment:

{% highlight groovy %}
xld {
  scope(forInfrastructure: 'Infrastructure/MyInfra') {
    infrastructure('LocalHost', 'overthere.LocalHost') {
      os = com.xebialabs.overthere.OperatingSystemFamily.UNIX
    }
    infrastructure('container3', 'dummyCis-test.DummyTestContainer') {
      integerProp = 123
      stringProp = 'name'
      booleanProp = true
      dateProp = date('2017-06-28T18:40:35')
      enumProp = java.time.DayOfWeek.FRIDAY
      stringsProp = [
        'bla',
        'test'
      ]
      stringsEncryptedProp = [
        encrypted('bla2'),
        encrypted('test2')
      ]
      stringsMap = [
        'a': 'b'
      ]
    }
  }
  scope(forEnvironments: directory('Environments/MyEnvs') {
    permissions = [
        'Dev': ['deploy#initial'],
        'QA' : ['deploy#upgrade']
    ]
  }) {
    environment('DEV') {
      members = [
        ref('Infrastructure/MyInfra/LocalHost')
      ]
      dictionaries = [
        ref('Environments/MyEnvs/dictionary1')
      ]
    }
    dictionary('dictionary1', [
      'key1': 'value1',
      'key2': 'value2',
      'key3': encrypted('value3')
    ])
    dictionary('dictionary3', [
      'key': 'value'
    ]) {
      restrictToContainers = [
        ref('Infrastructure/MyInfra/LocalHost')
      ]
    }
  }
}
{% endhighlight %}

## Set permissions on directories using Xfile

To set local permissions on directories, create and Xfile with the following content:

{% highlight groovy %}
xld {
    scope(forInfrastructure: 'Infrastructure/MyInfra', forEnvironments: directory('Environments/MyEnvs') {
        permissions = [
            'Dev': ['deploy#initial'],
            'QA' : ['deploy#upgrade']
        ]
    }) {}
}
{% endhighlight %}

## Refer to a CI defined in another part of a Groovy file

To add a reference to a CI defined in another part of an Xfile, add the following code:

{% highlight groovy %}
xld {
    scope(forInfrastructure: 'Infrastructure/MyInfra', forEnvironments: 'Environments/MyEnvs') {

        def myHost = infrastructure('MyHost', 'overthere.LocalHost') {
            os = 'unix'
        }
        environment('MyEnv') {
            members = [ref("Infrastructure/MyInfra/LocalHost"), myHost]
        }
    }
    scope(forInfrastructure: 'Infrastructure/MyInfra') {
         infrastructure('LocalHost', 'overthere.LocalHost') {
            os = 'unix'
        }

    }

}
{% endhighlight %}

## Generate an environment as an Xfile

To generate an environment as an Xfile, you must have the *export#dsl* [local permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html).

There are multiple options to generate an environment from XL Deploy.

### Using the Command Line (CLI)

To generate multiple directories and display the Groovy code in the console:

    repository.generateDsl(["Infrastructure/MyInfra","/Infrastructure/Experimental"])

To generate multiple directories and print the Groovy code to an Xfile:

    from java.io import File
    repository.generateDsl(["Infrastructure/MyInfra","Infrastructure/Experimental"],File("/path/to/file.groovy"))

### Using the REST API

### Using the XL Deploy default GUI

1. Expand **Environments** or **Infrastructure** from the left pane.
1. Hover over a dictionary, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Generate**. A `.groovy` file is generated and saved on your local machine.

## Apply an environment from an Xfile

To apply an environment as an Xfile, you must have the *repo#edit* [local permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html).

There are multiple options to apply an environment to XL Deploy.

### Using the Command Line (CLI)

To apply an Xfile via the CLI:

    from java.io import File
    repository.applyDsl(File("/path/to/file.groovy"))

### Using the REST API

## Security recommendations when using environment as code

* Use the *export#dsl* local permission to limit the number of users that can export CIs containing sensitive data (encrypted property values such as password properties).
* Use [property placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html#property-placeholders) instead of storing sensitive data directly in CIs.
