---
title: Getting started
---

## Installing XL Test

### Prerequisites

#### Server requirements

To install the XL Test server, the following prerequisites must be met:

* **Operating system**: Microsoft Windows or Unix-family operating system running Java 7
* **Java runtime environment**: JRE 1.7 (Oracle or IBM), JDK 7 is required to execute the example tests included in the application package
* **RAM**: At least 1GB of RAM available for XL Test
* **Hard disk space**: At least 256MB of hard disk space to store the XL Test repository (this depends on your usage of XL Test)

#### Client requirements

The following web browsers are supported for the XL Test GUI:

* Internet Explorer 10.0 or higher
* Firefox
* Chrome
* Safari

### Installation procedure

To install the XL Test server application:

1. Go to http://xebialabs.com/products/xl-test/ and follow the Download link.
2. Extract `xl-test-server-<version>.zip`.
3. Go to the folder `xl-test-server-<version>/bin`.
4. Launch `server.sh` or `server.bat` depending on your operating system.

#### XL Test server directory structure

After the XL Test installation file is extracted, the following directory structure exists in the installation directory:

* **bin**: Contains the server startup scripts
* **conf**: Contains server configuration files 
* **ext**: Contains server Java extensions
* **plugins**: Contains modules for extending XL Test's functionality
* **lib**: Contains libraries that the server needs
* **log**: contains server log files

Throughout this document, the installation directory is referred to as `XL_TEST_SERVER_HOME`.

## A first look

After launching the XL Test server, the application generates data for demonstration purposes.
This message will indicate XL Test is ready for use:

    Starting XL Test ... done.

    Please open http://localhost:6516 with a modern browser.

Following the advice given, the browser will display a Welcome screen allows you to view a prepopulated dashboard showing
data generated on initial startup or load your own test results in XL Test.

### Loading test results

In order to get started with your own test results you need to have a software project set up on
your computer, a remote computer, or a Jenkins server. Launch the import wizard by clicking **Get started** on the welcome screen
or by clicking **Import** in the top navigation.

XL Test provides support for the following tooling out of the box:

* **FitNesse**: The importer reads the test results. FitNesse suites must be executed by FitNesse itself, not through its jUnit runner.
* **Cucumber**: Cucumber javascript reports.
* **Gatling**: Performance test results.
* **xUnit**: Many test tools produce some sort of jUnit-like output. Note that this is a sparse output format.

If none of the tools listed above suites your needs, please drop us a mail via the **I can't find my tool** option.

