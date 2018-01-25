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
- XL Deploy 7.6.0
---

As of XL Deploy version 7.6.0, you can use the package-as-code feature to describe the content of a deployment or provisioning package in a Deployfile that can be stored in source control with your infrastructure definitions, environment definitions, and the application source code.

For more information about the Deployfile, refer to [The Deployfile](/xl-deploy/concept/environment-as-code.html#the-deployfile).

### Sample of a Deployfile

{% highlight groovy %}
xld {
    define(
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
    define(
      forEnvironments: 'Environments/Production'
      ) {
       environment('PROD') {
        members = [
           ref('Infrastructure/Docker/dkr-prd-mgr')
         ]
         dictionaries = [dictionary('Production Settings', ['logFilePath': '/tmp', 'HOST_PORT': '8181', 'title': 'Dockerized SampleApp', 'timeout': '8', 'logLevel': 'WARN'])]
      }
    }
    deploy('app', '1.0') {
      orchestrator = ['one', 'two', 'three']
      deployable('classic', 'aws.elb.ClassicELBSpec') {
        loadBalancerName = 'sss'
        listeners = [
          embeddedDeployable('ci', 'aws.elb.ClassicListenerSpec') {
            instanceProtocol = 'http'
            instancePort = '1111'
          }
        ]
      }
    }
    provision('provisioningApp', '1.0') {
      deployable('ec2Instance', 'aws.ec2.InstanceSpec') {
        boundTemplates = [
          ref('Applications/provisioningApp/1.0/ami'),
          ref('Applications/provisioningApp/1.0/ami (1)')
        ]
        instanceName = 'aws instance'
      }
      template('ami', 'template.aws.ec2.Instance_createAmi') {
        amiName = 'aws-ami'
      }
      template('ami (1)', 'template.aws.ec2.Instance_createAmi') {
        amiName = 'aws-ami'
      }
    }
}
{% endhighlight %}
