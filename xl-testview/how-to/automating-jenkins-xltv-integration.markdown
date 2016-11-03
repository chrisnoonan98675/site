---
title: Automating the Jenkins and XL TestView configuration
categories:
- xl-testview
subject:
- Jenkins
tags:
- jenkins
- import
- job dsl
---

Many companies automate the creation of their pipelines in Jenkins using the [Jenkins Job DSL plugin](https://github.com/jenkinsci/job-dsl-plugin/wiki). This topic describes one way to configure the [Jenkins XL TestView plugin](https://wiki.jenkins-ci.org/display/JENKINS/XL+TestView+Plugin) post-build step with the Jenkins Job DSL.

This topic demonstrates:

* How to set up the Jenkins Job DSL
* How to use the XL TestView REST API to create test specifications on demand

## Prerequisites

To replicate this example, install a recent version of Jenkins and an instance of XL TestView on the local system with default ports.

This example shows a setup in which job definitions are loaded from GitHub and dependencies for executing REST calls to XL TestView are downloaded with Gradle.

## Step 1 Install plugins

Install the following plugins in your Jenkins instance:

* The [XL TestView Jenkins plugin](https://wiki.jenkins-ci.org/display/JENKINS/XL+TestView+Plugin)
* The [Jenkins Job DSL plugin](https://wiki.jenkins-ci.org/display/JENKINS/Job+DSL+Plugin)
* The [Gradle plugin](https://wiki.jenkins-ci.org/display/JENKINS/Gradle+Plugin)
* The [GitHub plugin](https://wiki.jenkins-ci.org/display/JENKINS/GitHub+Plugin)
* The [Mask Passwords plugin](https://wiki.jenkins-ci.org/display/JENKINS/Mask+Passwords+Plugin)

## Step 2 Configure a job

Next, configure the job that executes the Job DSL:

1. Go to **Manage Jenkins** > **Configure System** > **Mask Passwords - Global name/password pairs** and add two entries:
    * `XLTV_USER` with value `admin`
    * `XLTV_PASSWORD` with value `admin` (adapt to your setup if necessary)
2. Configure the XL TestView server and ensure the connection test works.
2. Create a new **Freestyle job** named "Job Creator".
3. Select `git` in **Source code Management**.
4. Use `https://github.com/xebialabs/xl-testview-jenkins-job-dsl-sample.git` as the repository URL.
5. Enable **Mask passwords (and enable global passwords)** in **Build Environment**.
6. Add a **Invoke gradle step**, select **Use gradle wrapper** and specify `libs` for tasks.
7. Add a **Process Job DSLs** step to it. Select the **Look on Filesystem** option and add `jobs/*_jobs.groovy` as DSL script pattern.
8. Click **Save**.

## Step 3 Execute the job

Finally, execute the Job Creator job. This will result in a new Jenkins job that builds the Jenkins XL TestView plugin. The job will be configured to upload its test results to the XL TestView instance.

## How it works

In [`xltestview_plugin_jobs.groovy`](https://github.com/xebialabs/xl-testview-jenkins-job-dsl-sample/blob/master/jobs/xltestview_plugin_jobs.groovy), you will see that a `XLTestViewClient` is created and used to connect to XL TestView on the local machine. Using this client, a "Demo" project is created in XL TestView, and a test specification is created in the project. This is done with the public REST API. If the project and the test specification already exist, then only the ID of the test specification is looked up. The low-level ID is needed to tell Jenkins where to upload the test data.

Also, there is a section with actual Jenkins Job DSL. It configures a job that checks out the XL TestView plugin from GitHub, builds it, and then uploads the test data to XL TestView.

Because XL TestView does not have a native Job DSL construct, the [configure](https://jenkinsci.github.io/job-dsl-plugin/#method/javaposse.jobdsl.dsl.jobs.FreeStyleJob.configure) construct is used to build the necessary configuration. The important thing to note is that the previously obtained test specification ID is inserted in the final Jenkins configuration XML. (The configure construct uses a [Groovy DSL for building XML](http://groovy-lang.org/processing-xml.html#_creating_xml).) The `includes` element corresponds to the **Include pattern** in the Jenkins interface.

The [`XLTestViewClient`](https://github.com/xebialabs/xl-testview-jenkins-job-dsl-sample/blob/master/jobs/XLTestViewClient.groovy) is a small wrapper around the REST API that will create a project and test specification with a certain test tool on demand.

The client uses some additional libraries that must be on the classpath for the Job DSL run. These dependencies are downloaded by the *Invoke gradle step* in the `Job Creator` job. This will download the dependencies to a `lib` directory before the actual *Process Job DSLs* step executes.
