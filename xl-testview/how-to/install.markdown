---
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
* Java Development Kit (JDK). Version 1.7 and 1.8 are supported for Oracle and IBM. Version 1.7 is supported for OpenJDK.
* At least 1 GB of RAM available for XL TestView for version 1.2.x, at least 2GB of RAM for XL TestView 1.3.x.
* At least 1 GB of hard disk space available for XL TestView and the database (the exact amount of space required depends on your usage of XL TestView)
* A Java Development Kit is required for generating the demo data. If you do not need demo data, the Java Runtime Environment is sufficient.
* Generation of jMeter demodata is not supported on version 1.8

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
4. If you already have a license file, copy it to `<XLTESTVIEW_HOME>/conf/xl-testview-license.lic`. If you do not have a license file, you can request one after XL TestView starts.
5. Execute one of the following commands to start the setup wizard:
      * On Unix: `<XLTESTVIEW_HOME>/bin/server.sh`
      * On Windows: `<XLTESTVIEW_HOME>/bin/server.cmd`
6. After XL TestView has started, you will see the message `Starting XL TestView ... done.` and the URL where you can access XL TestView.

If you do not have a license, fill out the registration form that appears when you go to the URL. This license allows you to create [one project](/xl-testview/how-to/add-a-project.html) in addition to the demo project.

**Note:** Because XL TestView can perform file system operations such as scanning the file system, it is strongly advised that you execute XL TestView as a specialized user with limited permissions and minimal access rights.

The default username is `admin` and default password is `admin`. You can change this in `<XLTESTVIEW_HOME>/conf/users.conf`.

After installation various aspects of the server can be configured. See [Configuring XL TestView](/xl-testview/how-to/configuration.html).

## Directory structure

After you install the XL TestView server, this directory structure exists in `<XLTESTVIEW_HOME>`:

* `bin`: Contains server startup scripts
* `conf`: Contains server configuration files
* `data`: Contains the databases in which XL TestView stores configuration and test data.
* `ext`: Contains server Java extensions
* `lib`: Contains libraries that the server needs
* `log`: Contains server log files
* `plugins`: Contains modules for extending XL TestView's functionality
