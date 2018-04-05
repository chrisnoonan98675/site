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

In XL Deploy version 8.0.0, the package-as-code feature is used to describe the content of a deployment or provisioning package in a Deployfile that can be stored in source control with your infrastructure definitions, environment definitions, and the application source code.

For more information, see [The Deployfile](/xl-deploy/concept/environment-as-code.html#the-deployfile).

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

### Create provisioning package

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

This sample Deployfile creates a provisioning package using the `embeddedDeployable` syntax:

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
