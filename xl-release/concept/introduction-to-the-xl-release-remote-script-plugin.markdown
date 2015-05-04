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

The XL Release Remote Script plugin allows XL Release to execute commands on remote hosts. It does so by using the **Overthere** framework. Overthere is a Java library to manipulate files and execute processes on remote hosts, i.e. do stuff "over there". See the [Overthere Github repository](https://github.com/xebialabs/overthere) for more information.

The Remote Script plugin comes with two task types:

* **Unix Remote Script:** Execute a shell script via **SSH** on Unix hosts.
* **Windows Remote Script:** Execute a batch script via **WinRM** on Windows hosts.

## Unix

Input properties:

* `Username` is the login ID of the user on your Unix host.
* `Password` is the password of your user on your Unix host.
* `Sudo` indicates if your script will be executed using sudo.
* `Address` is the address of your remote host.
* `Remote Path` is the path on the remote host where you script will be executed.
* `Script` is the shell script to execute on your remote host.

Output properties:

* `Output` is the variable in which the standard output will be stored.
* `Err` is the variable in which the error output will be stored.

## Windows

Input properties:

* `Username` is the login ID of the user on your Windows host.
* `Password` is the password of your user on your Windows host.
* `Address` is the address of your remote host.
* `Remote Path` is the path on the remote host where you script will be executed. Note that UNC paths are not supported.
* `Script` is the batch script to execute on your remote host.
* `Timeout` is the WinRM timeout in [XML schema duration format](http://www.w3.org/TR/xmlschema-2/#isoformats). The default value is `PT60.000S`.

Output properties:

* `Output` is the variable in which the standard output will be stored.
* `Err` is the variable in which the error output will be stored.
