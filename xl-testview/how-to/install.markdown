---
layout: beta
title: Install XL TestView
categories:
- xl-testview
subject:
- Installation
tags:
- system administration
- installation
---

## Prerequisites

### Server requirements

The XL TestView server requires:

* Microsoft Windows or Unix-family operating system
* Java Runtime Environment (JRE) 1.7 (Oracle or IBM)
* At least 1 GB of RAM available for XL TestView
* At least 1 GB of hard disk space available for XL TestView and the database (the exact amount of space required depends on your usage of XL TestView)

### Client requirements

The XL TestView user interface requires one of the following browsers:

* Internet Explorer 10.0 or later
* Firefox
* Chrome
* Safari

## Installation procedure

To install XL TestView:

1. Download the XL TestView ZIP file from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com).
2. Extract the ZIP file to the directory where you want to install XL TestView; for example, `/opt/xebialabs/xl-testview` or `C:\Program Files\XL TestView`. This is referred to as `<XLTESTVIEW_HOME>`.
3. Open a command prompt and navigate to `<XLTESTVIEW_HOME>/bin`.
4. Execute one of the following commands to start the setup wizard:
      * On Unix: `server.sh`
      * On Windows: `server.cmd`
5. After XL TestView has started, you will see the message `Starting XL TestView ... done.` and the URL where you can access XL TestView.

If a license has not been provided to you, fill out the in-product registration form that is provided as soon as you start XL TestView to obtain a license. This license allows you to create [one project](/xl-testview/how-to/add-a-project.html) in addition to the demo project.

**Note:** Because XL TestView can perform file system operations such as scanning the file system, it is strongly advised that you execute XL TestView as a specialized user with limited permissions and minimal access rights.

The default username is `admin` (password: `admin`). This can be changed in the file `<XLTESTVIEW_HOME>/conf/users.conf`.

### Run XL TestView on a non-default port

If you want to run XL TestView on a different port, then perform the following procedure after step 3:

* Edit `server.sh` or `server.cmd` and look for the definition of `XLTEST_SERVER_OPTS`.
* Add the parameter `xltest.port` to the definition of `XLTEST_SERVER_OPTS` as follows:

        XLTEST_SERVER_OPTS="-Xmx1024m -XX:MaxPermSize=128m -Dxltest.port=<port number>"

To access the XL TestView user interface, open the provided URL in a browser.

## Directory structure

After you install the XL TestView server, this directory structure exists in `<XLTESTVIEW_HOME>`:

* `bin`: Contains server startup scripts
* `conf`: Contains server configuration files
* `data`: Contains the databases in which XL TestView stores configuration and test data.
* `ext`: Contains server Java extensions
* `lib`: Contains libraries that the server needs
* `log`: Contains server log files
* `plugins`: Contains modules for extending XL TestView's functionality
