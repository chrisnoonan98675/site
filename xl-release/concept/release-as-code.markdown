---
title: Release as code using Xfile
categories:
- xl-release
subject:
- Release
tags:
- as code
- release
- xfile
- dsl
since:
- XL Release 6.1.0
---

With XL Release you can define a release from a Groovy-based DSL script, that describes all the phases, tasks, and task-groups in the release. This allows you to store release definitions as code in version control and gives you programmatic control over a release when creating it.

The Xfile format is a defined structure for a `.groovy` file that can be used to create a release.

Using the [XL Release DSL API](/xl-release/6.2.x/dsl-api) you can create a custom release template file containing phases, multiple tasks, and task-groups. This allows you to launch an Xfile Groovy script task that creates a release.

## Example of an Xfile

{% highlight groovy %}
xlr {
    release("Hello world release") {
        description "Hello world release description"
        phases {
            phase {
                title "Sample"
                tasks {
                    manual("Manual task") {
                        description "Manual task description"
                    }
                }
            }
        }
    }
}
{% endhighlight %}

## Create a release using Xfile

1. In the XL Release GUI, go to **Design** > **Templates** and open a template.
1. Add a new **Groovy Script** task to a phase. For more information about Groovy Script tasks, refer to [Create a Groovy Script task](/xl-release/how-to/create-a-groovy-script-task.html).
1. Create a release from the template and then start the release.

  When the Groovy Script task becomes active, the script is executed and a new release is created.

**Note** You can also start a release directly from a repository using an Xfile. Create a master template that creates a release from the code for every commit, and add the Xfile script to the template. For more information, refer to [Create a release from a Git repository](/xl-release/how-to/create-a-release-from-a-git-repository.html).

### Security and script tasks

When a script task becomes active, the script is executed in a sandbox environment on the XL Release server. This means that the script has very restricted permissions. By default, access to the file system and network are not allowed.

To remove these restrictions, add a `script.policy` file to the `XL_RELEASE_SERVER_HOME/conf` directory. This is a standard [Java Security Policy file](http://docs.oracle.com/javase/7/docs/technotes/guides/security/PolicyFiles.html) that contains the permissions that a script should have. You must restart the XL Release server after creating or changing the `XL_RELEASE_SERVER_HOME/conf/script.policy` file.

**Note** As of XL Release 7.1.0 the `script.policy` file was changed to allow recursive permissions for the `work` directory using `work/-`.

      permission  java.io.FilePermission "work", "read";
      permission  java.io.FilePermission "work/-", "read, write";

## Example of an Xfile containing a task that creates a release

{% highlight groovy %}
xlr {
  release("Sample release with a Create release task") {
    tags 'important', 'test'
    description "Sample template created from Groovy DSL"
    variables {
      stringVariable('createdReleaseIdVar')
    }
    phases {
      phase("Sample") {
        tasks {
          createRelease('Release it!') {
            description "Let's create a new release"
            newReleaseTitle "New release from a create release task"
            startRelease false
            template "Test/Best Template" // can be path OR Id
            templateVariables {
              stringVariable("myVariable", "myValue")
              mapVariable("myMapVariable", [
                key1: "val1",
                key2: "val2"
              ])
            }
            releaseTags "dev", "mobile", "stage"
            createdReleaseId variable('createdReleaseIdVar')
          }
        }
      }
    }
  }
}
{% endhighlight %}

## Example of an Xfile that creates a release with multiple phases

**Note** Using the `it` iterator in the array you can create multiple phases or tasks.

{% highlight groovy %}
xlr {
  release("Sample release with a Create release task") {
    tags 'important', 'test'
    description "Sample template created from Groovy DSL"
    variables {
      stringVariable('createdReleaseIdVar')
    }
    phases {
    ['dev', 'qa', 'prd'].each {
        phase(it) {
          tasks {
            manual("Manual task") {
              description "Manual task description"
            }
          }
        }
      }
    }
  }
}
{% endhighlight %}

**Note** As of XL Release 7.0.0, you can export a template in DSL format. This allows you to see the DSL code corresponding to an existing template. For more information, refer to [XL Release REST API](/xl-release/6.2.x/rest-docs). You can also see the current XFile from a template using the [XFile view](/xl-release/how-to/using-the-xfile-view.html)
