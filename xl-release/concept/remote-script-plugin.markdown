---
title: Introduction to the XL Release Remote Script plugin
categories:
- xl-release
subject:
- Task types
tags:
- plugin
- script
- task
---

The XL Release Remote Script plugin allows XL Release to execute commands on remote hosts. It does so by using the [Overthere](https://github.com/xebialabs/overthere) framework, Java library for manipulating files and executing processes on remote hosts.

The Remote Script plugin includes two task types:

* **Remote Script: Unix**: Execute a shell script on a Unix host via SSH
* **Remote Script: Windows**: Execute a batch script on a Microsoft Windows host via WinRM

## Features

* Execute shell scripts on Unix hosts
* Execute batch scripts on Microsoft Windows hosts

## Unix remote script

The following properties are available in the **Remote Script: Unix** task type:

* **Username** and **Password**: The Unix host user log-in ID and password
* **Sudo**: Indicates whether the script will be executed using `sudo`
* **Address**: Address of the remote host
* **Remote Path**: The path on the remote host where the script should be executed
* **Script**: The shell script to execute on the remote host

The following output properties are available:

* **Output**: The variable in which the standard output will be stored
* **Err**: The variable in which the error output will be stored

## Windows remote script

The following properties are available in the **Remote Script: Windows** task type:

* **Username** and **Password**: The Windows host user log-in ID and password
* **Address**: Address of the remote host
* **Remote Path**: Path on the remote host where the script should be executed; note that UNC paths are not supported
* **Script**: The batch script to execute on the remote host
* **Timeout**: The WinRM timeout in [XML schema duration format](http://www.w3.org/TR/xmlschema-2/#isoformats); the default value is `PT60.000S`

The following output properties are available:

* **Output**: The variable in which the standard output will be stored
* **Err**: The variable in which the error output will be stored
