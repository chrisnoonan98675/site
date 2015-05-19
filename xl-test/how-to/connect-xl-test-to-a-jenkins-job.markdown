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

To connect the XL Test plugin to a build job, first create the test specification in XL Test to which the results will be sent. Optionally, [add a new project](/xl-test/how-to/add-a-project-to-xl-test.html) to which this test specification is added. Please ensure to create a [Passive Test Specification](/xl-test/how-to/create-a-test-specification.html), since the test data will be provided by Jenkins.

Next, configure the XL Test plugin to send the results to the created test specification:

1. Go to the job and click **Configure**.
1. In the **Post-build Actions** section, click **Add post-build action** and select **Send test results to XL Test**.
<<<<<<< HEAD
1. In the **Choose test specification** list, select the appropriate test specification to which the results will be added.
1. Select the tool or output format from the **Tool** list.
1. In the **Include pattern** box, optionally override the [file selection pattern](/xl-test/concept/xl-test-file-selection-patterns.html) that is used select files from the workspace that are sent to XL Test. As a reference, the file selection pattern from the test specification as configured in XL Test is shown in the **Choose test specification** list.
1. Optionally, provide an pattern to match files that are *excluded* and will not be sent to XL Test.
1. The matching files will be sent to XL Test for processing. Note that the pattern depends on the tool that you selected.
=======
1. Next to **Test Specifications**, click **Add**.
1. Select the test specification where the results should be sent.
2. In the **Include pattern** box, enter a [file selection pattern](/xl-test/concept/xl-test-file-selection-patterns.html) for the results to include.
3. In the **Exclude pattern** box, enter a file selection pattern for the results to exclude.
>>>>>>> d6ab7d660f65e59287770e8cfc5d38bd5d975625
1. **Save** the updated configuration.

## Step 4 Build

To start a new build of the job, click **Build**.

## Step 5 View in XL Test

<<<<<<< HEAD
After the build is complete, navigate to the appropriate **Project** and its **Test specification** in  XL Test to view and analyze the test results.

You can also configure XL Test to execute test specifications that build on Jenkins.
=======
After the build is complete, open the test specification in XL Test to locate the results.
>>>>>>> d6ab7d660f65e59287770e8cfc5d38bd5d975625
