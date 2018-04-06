---
title: Package as code using Deployfile
categories:
- xl-deploy
subject:
- Packaging
tags:
- as code
- package
- deployfile
- dsl
since:
- XL Deploy 8.0.0
---

In XL Deploy version 8.0.0, the package-as-code feature is used to describe the content of a deployment or provisioning package in a Deployfile that can be stored in source control with your infrastructure definitions, environment definitions, and the application source code. With this feature you can deploy any deployment package or provisioning package to an environment.

For more information, see [The Deployfile](/xl-deploy/concept/environment-as-code.html#the-deployfile).

### Deployfile syntaxes

Define the `deploy` action:

        xld {
          deploy('app', '1.0') {
            deployable('listDirectory', 'cmd.Command') {
              commandLine = 'ls'
            }
          }
        }

This example creates an application 'app', if it does not exists on your XL Deploy instance. If the application exists and it does not contain the package 1.0, this creates the package 1.0 under 'app'. The Deployfile also create one deployable `listDirectory` inside package 1.0 and deploys this package to the default environment, which is the first environment defined in the Deployfile.

Syntaxes to define an application:

         deploy('app', '1.0'){}
         deploy('directory/app', '1.0'){}
         deploy('Applications/directory/app', '1.0'){}

You can also specify the target environment to deploy your package:

        xld {
          deploy('app', '1.0','Environments/env') {
            deployable('listDirectory', 'cmd.Command') {
              commandLine = 'ls'
            }
          }
        }

This deploys your package to `Environments/env` if it exists in your XL Deploy server or is defined in your Deployfile.

Use the `upload` syntax when a local `sourceArtifact` must wrapped within:

        upload('path')

The path can be relative to the Deployfile:

      upload('PetClinic-1.0.ear')
      upload('../Petclinic-1.0.ear')

The path can also be absolute:

      upload('/var/folders/PetClinic-1.0.ear')

Deployfile example using the `upload` syntax:

         xld {
          deploy('myApp', '1.0','Environments/env') {
            deployable('fileCI', 'file.File') {
              preScannedPlaceholders = true
              fileUri = upload('fileToUpload.sh')
              targetPath = '/tmp'
            }
          }
        }

### Sample of a basic Deployfile

{% highlight groovy %}
xld {
  define(forInfrastructure: 'Infrastructure/CICD') {
    infrastructure('test_server_1.0.0', 'overthere.LocalHost') {
      os = com.xebialabs.overthere.OperatingSystemFamily.UNIX
    }
    infrastructure('test_server_2.0.0', 'overthere.LocalHost') {
      os = com.xebialabs.overthere.OperatingSystemFamily.UNIX
    }
  }
  define(forEnvironments: 'Environments/XLD') {
    environment('ACC') {
      members = [
        ref('Infrastructure/CICD/test_server_1.0.0')
      ]
    }
    environment('Staging') {
      members = [
       ref('Infrastructure/CICD/test_server_2.0.0')
      ]
    }
    dictionary('PetDictionaryDev', [
      'username': 'admin',
      'securePassword': 'secret'
    ])
  }
  deploy('PetPortal', '1.1.0') {      
    deployable('PetClinic-ear', 'jee.Ear') {
      fileUri = upload("PetClinic-1.0.ear")
    }
  }
}
{% endhighlight %}

### Deploy to a target environment

There are three options to deploy using the Deployfile:

1. Deploy to the default environment (the first environment defined in the Deployfile).
1. Deploy to a target environment defined inside the Deployfile

                deploy('PetPortal', '1.1.0', 'Environments/XLD/Staging') {      
                  deployable('PetClinic-ear', 'jee.Ear') {
                    fileUri = upload("PetClinic-1.0.ear")
                  }

1. Deploy to target environment existing on your XL Deploy server (not defined in the Deployfile)

                deploy('PetPortal', '1.1.0', '<FullEnvironmentID>') {      
                  deployable('PetClinic-ear', 'jee.Ear') {
                    fileUri = upload("PetClinic-1.0.ear")
                  }

### Create a deployment or provisioning package

This sample Deployfile creates a provisioning package using the `template` syntax:

{% highlight groovy %}
xld {
  provision('provision', '1.0') {
    deployable('MySpec', 'dummyCis-test.DummyProvisionable') {
        boundTemplates = [
                relRef('createAmi')
        ]
    }
    template('createAmi', 'template.dummyCis-test.DummyTestCi') {
        stringProperty = "testString"
    }
  }
}
{% endhighlight %}    

This sample Deployfile creates a deployment package using the `embeddedDeployable` syntax:

{% highlight groovy %}
xld {
 deploy('MyApp', '1.0') {
  deployable('dummyci', 'dummyCis-test.DummyTestDeployable') {
   embeddedlistOfcis = [
           embeddedDeployable('embeddedDeployable', 'dummyCis-test.DummyTestEmbeddedDeployable') {
            stringProp = "someProp"
           }
   ]
  }
 }
}
{% endhighlight %}   

### Deploy an application using the Lightweight CLI

1. Create a Deployfile as described in one of the samples above or [generate the Deployfile](/xl-deploy/concept/xl-deploy-lightweight-cli.html#generate-a-deployfile).
1. If you have the [lightweight CLI installed](/xl-deploy/concept/xl-deploy-lightweight-cli.html), upgrade to the latest version of [xld-py-cli](https://pypi.python.org/pypi/xld-py-cli/).
1. Open a command terminal  
1. Execute this command:

          xld --url http://localhost:4516 --username john --password secret01 apply Deployfile

**Note:** Make sure the Deployfile is located in the directory where you execute the command.    

### Apply a DeployFile with artifacts using REST

When a Deployfile contains local artifacts wrapped in `upload` or file paths wrapped in `read`, those files will be uploaded to the server through multipart form data request.

        curl -v --user "admin:admin" --request POST -F   "deployFile=xld {
            deploy('myApp1121', '1.0', 'Environments/env') {
                deployable('fileCI1211', 'file.File') {
                    fileUri = upload('fileToUpload.txt')
                    targetPath= '/tmp'
                }
            }
        }
        "  -F 'artifacts=@/Users/admin/deployfiles/fileToUpload.txt' http://xl-deploy.mycompany.com:4516/deployit/deployfile/apply

You can upload as many files as you want by adding this to your request:

        -F 'artifacts=@/Users/admin/deployfiles/otherfile.txt'    
