---
title: Using the XL Deploy Docker plugin
categories:
xl-deploy
subject:
Docker
tags:
docker
plugin
since:
XL Deploy 6.2.0
---

## Deploy a Docker Container

1. Create an [application](/xl-deploy/how-to/create-a-deployment-package-using-the-ui.html#creating-an-application) and a [deployment package](/xl-deploy/how-to/create-a-deployment-package-using-the-ui.html#creating-a-deployment-package).
1. Hover over the deployment package, click ![Explorer action menu](/images/menu_three_dots.png), click **New**, and then select `docker.ContainerSpec`.
1. Hover over the deployment package containing the new `docker.ContainerSpec`, click ![Explorer action menu](/images/menu_three_dots.png), click **Deploy** and select the target environment.
1. Click **Continue** and then click **Deploy** to execute the plan.

Sample Manifest:

      <?xml version="1.0" encoding="UTF-8"?>
      <udm.DeploymentPackage version="demo_docker_host" application="demo_create_container_app">
          <application />
          <orchestrator />
          <deployables>
            <docker.ContainerSpec name="/nginx_container">
                <tags />
                <containerName>demo_nginx</containerName>
                <image>nginx</image>
                <labels />
                <environment />
                <restartPolicyMaximumRetryCount>40</restartPolicyMaximumRetryCount>
                <networks />
                <dnsOptions />
                <links />
                <portBindings />
                <volumeBindings />
            </docker.ContainerSpec>
          </deployables>
          <applicationDependencies />
          <dependencyResolution>LATEST</dependencyResolution>
          <undeployDependencies>false</undeployDependencies>
      </udm.DeploymentPackage>

## Deploy a Docker Service

1. Create an [application](/xl-deploy/how-to/create-a-deployment-package-using-the-ui.html#creating-an-application) and a [deployment package](/xl-deploy/how-to/create-a-deployment-package-using-the-ui.html#creating-a-deployment-package).
1. Hover over the deployment package, click ![Explorer action menu](/images/menu_three_dots.png), click **New**, and then select `docker.Service`.
1. Hover over the deployment package containing the new `docker.Service`, click ![Explorer action menu](/images/menu_three_dots.png), click **Deploy** and select the target environment containing the `docker.SwarmManager`.
1. Click **Continue** and then click **Deploy** to execute the plan.

Sample Manifest:

      <?xml version="1.0" encoding="UTF-8"?>
      <udm.DeploymentPackage version="docker_swarm" application="docker_swarm_demo_app">
          <application />
          <orchestrator />
          <deployables>
            <docker.ServiceSpec name="/tomcat_service">
                <tags />
                <serviceName>tomcat-service</serviceName>
                <image>tomcat</image>
                <labels />
                <containerLabels />
                <constraints />
                <waitForReplicasMaxRetries>30</waitForReplicasMaxRetries>
                <networks />
                <environment />
                <restartPolicyMaximumRetryCount>30</restartPolicyMaximumRetryCount>
                <portBindings />
            </docker.ServiceSpec>
          </deployables>
          <applicationDependencies />
          <dependencyResolution>LATEST</dependencyResolution>
          <undeployDependencies>false</undeployDependencies>
      </udm.DeploymentPackage>

 **Note** You can only deploy a docker service to a docker swarm.

## Deploy a Docker Volume

### Create an independent volume

1. Create an [application](/xl-deploy/how-to/create-a-deployment-package-using-the-ui.html#creating-an-application) and a [deployment package](/xl-deploy/how-to/create-a-deployment-package-using-the-ui.html#creating-a-deployment-package).
1. Hover over the deployment package, click ![Explorer action menu](/images/menu_three_dots.png), click **New**, and then select `docker.VolumeSpec`.
1. Enter a name for the service in the **Name** field and specify a **Volume Name** for the volume.
1. Hover over the deployment package containing the new `docker.VolumeSpec`, click ![Explorer action menu](/images/menu_three_dots.png), click **Deploy** and select the target environment.
1. Click **Continue** and then click **Deploy** to execute the plan.
1. Run this command `docker-machine ssh <node_name>` to connect to your Swarm Manager host and run this command `docker ps -a` to check the service created.

### Attach a volume to a Docker container

1. Hover over the created container, click ![Explorer action menu](/images/menu_three_dots.png), click **New**, and then select MountedVolumeSpec.
1. Enter a name for the application version, specify a name for the volume, enter the directory of the docker container where the volume will be attached in the **Mountpoint** field, and the default value *false* in the **Read Only** field.
1. [Deploy](/xl-deploy/how-to/deploy-an-application.html) the created package to the target environment.

The Docker container is created with the mounted volume attached at the mount point.

 Sample Manifest:

      <?xml version="1.0" encoding="UTF-8"?>
      <udm.DeploymentPackage version="docker_volume" application="docker_volume_demo">
          <application />
          <orchestrator />
          <deployables>
            <docker.VolumeSpec name="/test_volume">
                <tags />
                <volumeName>testvolume</volumeName>
                <driverOptions />
                <labels />
            </docker.VolumeSpec>
            <docker.ContainerSpec name="/nginx_container">
                <tags />
                <containerName>nginx-container</containerName>
                <image>nginx</image>
                <labels />
                <environment />
                <networks />
                <dnsOptions />
                <links />
                <portBindings />
                <volumeBindings>
            <docker.MountedVolumeSpec name="/nginx_container/testvolume">
                <volumeName>testvolume</volumeName>
                <mountpoint>/tmp</mountpoint>
            </docker.MountedVolumeSpec>
            </volumeBindings>
            </docker.ContainerSpec>
          </deployables>
          <applicationDependencies />
          <dependencyResolution>LATEST</dependencyResolution>
          <undeployDependencies>false</undeployDependencies>
        </udm.DeploymentPackage>

## Create a Docker Network

1. Create an [application](/xl-deploy/how-to/create-a-deployment-package-using-the-ui.html#creating-an-application) and a [deployment package](/xl-deploy/how-to/create-a-deployment-package-using-the-ui.html#creating-a-deployment-package).
1. Hover over the deployment package, click ![Explorer action menu](/images/menu_three_dots.png), click **New**, and then select `docker.NetworkSpec`. Perform the same action again to create a `docker.ContainerSpec`.
1. In the **Network Name** field, specify the name of the private network that will be created.
1. Click **Save** to create the network.
1. For the created container, go to the network tab and add the name of the network which will bind the    
containers.
1. [Deploy the application](/xl-deploy/how-to/deploy-an-application.html) to the target environment.
1. Log in to your Docker host and run this command `docker network inspect <network_name>`.
1. Run this command `docker network ls` to verify if the network is created with the docker host.

## Map a Docker container port to a Docker host

Port mapping is used to map the host port with the container port.

To create a port mapper:

1. Create a container inside an application with a deployment package.
1. Hover over the created container, click ![Explorer action menu](/images/menu_three_dots.png), click **New**, and then select PortSpec.
1. Enter a name for the application version, in the **Host Port** field, enter the port of the Docker host that will be mapped to the container, the container port, and specify the protocol over which the connection will be established.
1. [Deploy the application](/xl-deploy/how-to/deploy-an-application.html) to the target environment.
1. Log in to your Docker host and run this command `docker network inspect <network_name>`.
1. Run this command `docker network ls` to verify if the network is created with the docker host.

 Sample Manifest:

        <?xml version="1.0" encoding="UTF-8"?>
        <udm.DeploymentPackage version="network_package" application="docker_demo_network">
            <application />
            <orchestrator />
            <deployables>
            <docker.NetworkSpec name="/custom_network">
              <tags />
            <networkName>custom_network</networkName>
            <networkOptions />
            </docker.NetworkSpec>
            <docker.ContainerSpec name="/mysql-container">
              <tags />
            <containerName>mysql-container</containerName>
            <image>mysql</image>
            <labels />
            <environment />
            <networks>
            <value>custom_network</value>
            </networks>
            <dnsOptions />
            <links />
            <portBindings>
            <docker.PortSpec name="/mysql-container/port_map">
              <hostPort>92</hostPort>
            <containerPort>80</containerPort>
            <protocol>tcp</protocol>
            </docker.PortSpec>
            </portBindings>
            <volumeBindings />
            </docker.ContainerSpec>
            </deployables>
            <applicationDependencies />
            <dependencyResolution>LATEST</dependencyResolution>
        <undeployDependencies>false</undeployDependencies>
        </udm.DeploymentPackage>
