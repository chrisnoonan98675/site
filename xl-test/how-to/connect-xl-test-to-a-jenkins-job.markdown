---
layout: beta
title: Connect XL Test to a Jenkins job
categories:
- xl-test
subject:
- Plugins
tags:
- jenkins
- test results
- import
---

If you import test results from Jenkins, XL Test will automatically create a test specification with the same name as the Jenkins job (if it does not already exist).

To connect them so that Jenkins will send test results to XL Test, you must install and configure the [XL Test plugin](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin) for Jenkins.

## Step 1 Install the XL Test plugin

To use the Jenkins interface to install the plugin:

1. In a browser, go to your Jenkins host. Log in, if required.
1. Go to **Manage Jenkins** > **Manage Plugins** and click the **Available** tab.
1. Look for the XL Test plugin.
1. Select the plugin and click **Install without restart**.

For information about installing plugins in Jenkins, refer to the [Jenkins wiki](https://wiki.jenkins-ci.org/display/JENKINS/Plugins).

## Step 2 Configure the plugin

To configure the XL Test plugin:

1. Go to **Manage Jenkins** > **Configure System** and locate the **XL Test** section.
1. In the **Server Url** box, enter the URL where XL Test runs.
1. In the **Proxy Url** box, enter the URL of a proxy server, if one is required to contact XL Test. If no proxy server is required, leave the box empty.
1. Next to **Credentials**, click **Add** and provide the user name and password that Jenkins should use to connect to XL Test.

## Step 3 Add a post-build step to your job

To connect the XL Test plugin to a build job:

1. Go to the job and click **Configure**.
1. In the **Post-build Actions** section, click **Add post-build action** and select **Send test results to XL Test**.
1. Next to **Test Specifications**, click **Add**.
1. Select the test specification where the results should be sent.
2. In the **Include pattern** box, enter a [file selection pattern](/xl-test/concept/xl-test-file-selection-patterns.html) for the results to include.
3. In the **Exclude pattern** box, enter a file selection pattern for the results to exclude.
1. **Save** the updated configuration.

## Step 4 Build

To start a new build of the job, click **Build**.

## Step 5 View in XL Test

After the build is complete, open the test specification in XL Test to locate the results.
