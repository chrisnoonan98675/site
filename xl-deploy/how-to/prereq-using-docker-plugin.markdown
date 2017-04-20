---
title: Prerequisites when using the XL Deploy Docker plugin
categories:
- xl-deploy
subject:
- Docker
tags:
- docker
- plugin
- prerequisites
since:
- XL Deploy 6.2.0
---

To use the XL Deploy Docker plugin you must first create a Docker registry and make configuration settings to XL Deploy.

## Create a Docker registry

1. Click **Explorer** in the top menu.
1. In the left pane, hover over **Configuration** and click ![Explorer action menu](/images/menu_three_dots.png)
1. Click **New** and select `docker.Registry`.
1. Fill in the required fields with the name, the registry url, username, and password.

**Note** When you deploy any container or service to an environment, XL Deploy will login to the associated registry to retrieve the images.

## Configure Xl Deploy to connect to Docker Engine

1. Click **Explorer** in the top menu.
1. In the left pane, hover over **Infrastructure** and click ![Explorer action menu](/images/menu_three_dots.png)
1. Click **New** and select `docker.Engine`.
1. Fill in the required fields with the name and the host.
1. If TLS is enabled on your host, select the **Enable TLS** option.
1. Go to the **Certificates** section. Copy the contents of `cert.pem` to **Certificate** field, `key.pem` to **Key** field, and `ca.pem` to  **Certification Authority** field.  

   **Note** If the host system is TLS enabled, the above certificates are mandatory.

1. Go to **Registries** section and associate the registry created in the above step under configuration.
{% comment %}
1. Right click the docker.Engine you created and execute **Check Connection**.
{% endcomment %}
1. Add the new `docker.Engine` infrastructure item to an environment.

## Configure XL Deploy to connect to Docker Swarm

1. Click **Explorer** in the top menu.
1. In the left pane, hover over **Infrastructure** and click ![Explorer action menu](/images/menu_three_dots.png)
1. Click **New** and select `docker.SwarmManager`.
1. Fill in the required fields with the name and the url of the Docker Swarm Leader .
1. If TLS is enabled on your Leader, select the **Enable TLS** option.
1. Go to the **Certificates** section. In the system home folder, go to `.docker` > `machine` > `certs`  and copy the contents of `cert.pem` to **Certificate** field, `key.pem` to **Key** field, and `ca.pem` to  **Certification Authority** field.   

   **Note** If the host system is TLS enabled, the above certificates are mandatory.

1. Go to **Registries** section and associate the registry created in the above step under configuration.
{% comment %}
1. Right click the docker.Engine you created and execute **Check Connection**.
{% endcomment %}
1. Add the new docker.SwarmManager infrastructure item to an environment.
