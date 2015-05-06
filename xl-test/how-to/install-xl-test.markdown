---
layout: beta
title: Install XL Test
categories:
- xl-test
subject:
- Installation
tags:
- system administration
- installation
---

## Prerequisites

### Server requirements

The XL Test server requires:

* Microsoft Windows or Unix-family operating system running Java 7
* Java Runtime Environment (JRE) 1.7 (Oracle or IBM); note that Java Development Kit (JDK) 7 is required to execute the example tests included with XL Test
* At least 1 GB of RAM available for XL Test
* At least 256 MB of hard disk space available to store the XL Test repository (the exact amount of space required depends on your usage of XL Test)

### Client requirements

The XL Test user interface requires one of the following browsers:

* Internet Explorer 10.0 or later
* Firefox
* Chrome
* Safari

## Installation procedure

To install XL Test:

1. Download XL Test from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com).
2. Extract `xl-test-server-<version>.zip` to the directory where you want to install XL Test; for example, `/opt/xebialabs/xl-test` or `C:\Program Files\XL Test`.
3. Open a command prompt and navigate to `XLTEST_HOME/bin`.
4. Execute one of the following commands to start the setup wizard:
      * On Unix: `server.sh`
      * On Windows: `server.cmd`
5. After XL Test has started, the message `Starting XL Test ... done.` the URL to open XL Test appear.

### Run XL Test on a non-default port

If you want to run XL Test on a different port, then perform the following procedure after step 3: 

* Edit `server.sh` or `server.cmd` and look for the definition of `XLTEST_SERVER_OPTS`.
* Add the parameter `xltest.host` to the definition of `XLTEST_SERVER_OPTS` as follows:

    XLTEST_SERVER_OPTS="-Xmx1024m -XX:MaxPermSize=128m -Dxltest.port=<port number>"

To access the XL Test user interface, open the provided URL in a browser.

## Directory structure

After you install the XL Test server, this directory structure exists in the installation directory (`XLTEST_HOME`):

* `bin`: Contains server startup scripts
* `conf`: Contains server configuration files 
* `ext`: Contains server Java extensions
* `plugins`: Contains modules for extending XL Test's functionality
* `lib`: Contains libraries that the server needs
* `log`: Contains server log files
