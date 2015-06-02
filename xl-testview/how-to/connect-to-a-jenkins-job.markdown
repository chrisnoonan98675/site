---
layout: beta
title: Connect XL TestView to a Jenkins job
categories:
- xl-testview
subject:
- Plugins
tags:
- jenkins
- test results
- import
---

If you import test results from Jenkins, XL TestView will automatically create a test specification with the same name as the Jenkins job (if it does not already exist).

To connect them so that Jenkins will send test results to XL TestView, you must install and configure the XL TestView plugin for Jenkins.

## Step 1 Install the XL TestView plugin

To use the Jenkins interface to install the plugin:

1. In a browser, go to your Jenkins host. Log in, if required.
1. Go to **Manage Jenkins** > **Manage Plugins** and click the **Available** tab.
1. Look for the XL TestView plugin.
1. Select the plugin and click **Install without restart**.

More information about installing plugins in Jenkins can be found in the [Jenkins wiki](https://wiki.jenkins-ci.org/display/JENKINS/Plugins).

## Step 2 Configure the plugin

To configure the XL TestView plugin:

1.  Go to **Manage Jenkins** > **Configure System** and locate the XL TestView section.
1.  In the **Server Url** box, enter the URL where XL TestView runs.
1.  In the **Proxy Url** box, enter the URL of a proxy server, if one is required to contact XL TestView. If no proxy server is required, leave the box empty.
1.  In the **Credentials** compartment, provide the user name and password that should be used to connect to XL TestView. For information about credentials, refer to the [Jenkins' Credentials plugin](https://wiki.jenkins-ci.org/display/JENKINS/Credentials+Plugin).

## Step 3 Add a post-build step to your job

To connect the XL TestView plugin to a build job, create a [passive test specification](/xl-testview/how-to/create-a-test-specification.html) in XL TestView. You can optionally create this specification in a [new project](/xl-testview/how-to/add-a-project.html).

Then, configure the XL TestView plugin to send the results to the test specification that you created:

1. In Jenkins, go to the job and click **Configure**.
1. In the **Post-build Actions** section, click **Add post-build action** and select **Send test results to XL TestView**.
1. Select the test specification from the **Choose test specification** list.
1. In the **Include pattern** box, optionally override the [file selection pattern](/xl-testview/concept/file-selection-patterns.html) that is used select files to send to XL TestView from the workspace. For your reference, the **Choose test specification** list shows the file selection pattern that is configured for the specification in XL TestView.

1. In the **Exclude pattern** box, optionally provide a pattern to match files that are *excluded* and will not be sent to XL TestView.

   **Note:** The include and exclude patterns depend on the tool that you selected.

1. Click **Save** to save the updated configuration.

## Step 4 Build

To start a new build of the job in Jenkins, click **Build**.

## Step 5 View in XL TestView

After the build is complete, go to the appropriate **Project** in XL TestView and locate the test specification. You can then analyze the results.
