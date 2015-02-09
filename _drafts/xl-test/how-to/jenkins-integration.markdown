---
title: Hook up XL Test to an existing Jenkins job
categories:
- xl-test
subject:
- System administration
tags:
- jenkins
- test execution
---

You can import your test results easily in XL Test from Jenkins. A Test specification will automatically be created with the same name as your job, if none exists.

To make Jenkins send test results to XL Test, Jenkins needs to have the XL Test plugin installed and configured.

## Step 1 Install the XL Test plugin

To install the plugin via the Jenkins user interface:

1. Open a browser and point it to your Jenkins host, log in if required.
2. Go to **Manage Jenkins** > **Manage Plugins**, and go to the **Available** tab.
3. Look for the XL Test Plugin.
4. Select the checkbox for the plugin and click **Install without restart**.

You can find more information on installing plugins in Jenkins on the [Jenkins wiki](https://wiki.jenkins-ci.org/display/JENKINS/Plugins).

## Step 2 Configure the plugin

The XL Test Plugin requires a bit of configuration. Most notably it needs to know where XL Test is installed and how it can connect to it.

1.  Go to **Manage Jenkins** > **Manage Plugins**, scroll down to the section named XL Test.
2.  In the **Default Server Url** box, enter the URL where XL Test runs.
3.  In the **Default Proxy Url** box, enter the URL of a proxy server, if one is required to contact XL Test. If no proxy server is required, leave it empty.
4.  In the **Credentials** compartment, provide the user name and password that should be used to connect to XL Test.


**Tip**: The option **Use non-default XL Test Server** can be selected if you have multiple XL Test servers running. In the boxes **Server Url** and **Proxy Url** you can provide a different server and proxy URL, which will override the default.

## Step 3 Add a post-build step to your job

Now that the XL Test Plugin is installed and properly configured it's time to hook it up to a build job.

1. Go to your test job and select the **Configure** menu item.
2. In the **Post-build Actions** section, click **Add post-build action** and select **Send test results to XL Test**.
3. A step is added.
4. In the **Credential** box, select the right credentials.
5. In the **Tool** box, select the tool or output format.
6. In the **Pattern** box, provide the file pattern used to find the required test results. Those files will be sent to XL Test for processing. Note that the pattern is dependent on the tool you've selected.

   **Tip**: You can use [quite sophisticated file search patterns](file-name-patterns.html).

7. **Save** your updated configuration.


## Step 4 Build

Start a new build of your job, click **Build**.

## Step 5 View in XL Test

Once the build completes, a new test specification will be created in XL Test. This test specification can be added to dashboards or viewed directly. You can also make custom reports.


As an advanced feature it's also possible to let XL Test execute test specifications that builds on Jenkins.


