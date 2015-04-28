---
title: Deploy the XL Deploy demo
subject:
- Getting started
categories:
- xl-deploy
tags:
- tomcat
- middleware
- deployment
since:
- 5.0.0
---

The [XL Deploy installer](/xl-deploy/how-to/install-xl-deploy.html#install-xl-deploy-using-the-installer) for Microsoft Windows and Mac OS X allows you to install demo content that includes:

* A sample application with two versions
* A sample environment
* An [Apache Tomcat 8](http://tomcat.apache.org/download-80.cgi) server that runs at [http://localhost:4520/](http://localhost:4520/)

To deploy the application:

1. Click **Deployment** in the top navigation bar to go to the Deployment Workspace.
2. Under **Packages**, expand the application to see the versions (these are *deployment packages*).
3. Drag version 1.0 to the left side of the Workspace.
3. Under **Environments**, click the environment and drag it to the right side of the Workspace.

    XL Deploy automatically maps the items in the package to the middleware containers in the environment.

4. Click **Execute** to deploy the application.
5. After the deployment is complete, go to [http://localhost:4520/](http://localhost:4520/) in a browser to see the deployed application.

To see a deployment in action, watch the [Performing an initial deployment](https://www.youtube.com/watch?v=pw17C9j60xY) video.

**Tip:** After you deploy the sample application, you can update it. Follow the instructions above, but select version 2.0 instead of 1.0.
