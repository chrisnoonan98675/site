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

## System requirements

### Server requirements

The XL TestView server requires:

* Microsoft Windows or Unix-family operating system
* Java Development Kit (JDK):
    * For XL TestView 1.4.0 and later: JDK 8 (Oracle and IBM)
    * For XL TestView 1.3.x and earlier: JDK 7 or 8 for Oracle and IBM, and JDK 7 for OpenJDK
* At least 1 GB of RAM available for XL TestView.
* At least 1 GB of hard disk space available for XL TestView and the database (the exact amount of space required depends on your usage of XL TestView)

Note that:

* If you do not want or need XL TestView to generate sample data, then you can install the Java Runtime Environment (JRE) instead of the JDK
* Generation of sample jMeter data is not supported on JDK 8

### Client requirements

The XL TestView user interface requires one of the following browsers:

* Firefox
* Chrome
* Safari
* Internet Explorer 10 or later

**Note:** Internet Explorer Compatibility View is not supported.

## Installation procedure

To install XL TestView:

1. Download the XL TestView ZIP file from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com).
2. Extract the ZIP file to the directory where you want to install XL TestView; for example, `/opt/xebialabs/xl-testview` or `C:\Program Files\XL TestView`. This is referred to as `<XLTESTVIEW_HOME>`.
3. If you have a license file, copy it to `<XLTESTVIEW_HOME>/conf/xl-testview-license.lic`. If you do not have a license, you can request one after XL TestView starts.
4. Open a command prompt and navigate to `<XLTESTVIEW_HOME>/bin`.
5. Execute one of the following commands to start the setup wizard:
      * On Unix: `<XLTESTVIEW_HOME>/bin/server.sh`
      * On Windows: `<XLTESTVIEW_HOME>/bin/server.cmd`
6. After XL TestView has started, you will see the message `Starting XL TestView ... done.` and the URL where you can access XL TestView.

**Note:** Because XL TestView can perform file system operations such as scanning the file system, it is strongly advised that you execute XL TestView as a specialized user with limited permissions and minimal access rights.

The default username and password are both `admin`. You can change these values in `<XLTESTVIEW_HOME>/conf/users.conf`.

After installation, various aspects of the server can be configured. See [Configuring XL TestView](/xl-testview/how-to/configure-xl-testview.html).

## Directory structure

After you install the XL TestView server, this directory structure exists in `<XLTESTVIEW_HOME>`:

* `bin`: Contains server startup scripts
* `conf`: Contains server configuration files
* `data`: Contains the databases in which XL TestView stores configuration and test data.
* `ext`: Contains server Java extensions
* `lib`: Contains libraries that the server needs
* `log`: Contains server log files
* `plugins`: Contains modules for extending XL TestView's functionality
* `service`: Contains service scripts (since XL TestView 1.4.1)

Additional information:

* [Start XL TestView](/xl-testview/how-to/start.html)
* [Upgrade XL TestView](/xl-testview/how-to/upgrade-xl-testview.html)
* [Install XL TestView as a service](/xl-testview/how-to/install-xl-testview-as-a-service.html)
