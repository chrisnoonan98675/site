---
title: Install XL Test
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

1. Go to [http://xebialabs.com/download/xl-test](http://xebialabs.com/download/xl-test) and download XL Test.
2. Extract `xl-test-server-<version>.zip` to the directory where you want to install XL Test; for example, `/opt/xebialabs/xl-test` or `C:\Program Files\XL Test`.
3. Open a command prompt and navigate to `xl-test-server-<version>/bin`.
4. Execute one of the following commands to start the setup wizard:
      * On Unix: `server.sh`
      * On Windows: `server.bat`
5. After XL Test has started, the message `Starting XL Test ... done.` appears.

To access the XL Test user interface, open [http://localhost:6516](http://localhost:6516) in a browser.
