---
title: Install XL Deploy or XL Release as a Windows service
categories:
- xl-deploy
- xl-release
tags:
- windows
- system administration
---

There are several options for installing XL Deploy and XL Release as a service.

## Install XL Deploy using Tanuki Java Service Wrapper

To install XL Deploy as a Windows service using [Tanuki Java Service Wrapper Community Edition](http://wrapper.tanukisoftware.com/doc/english/download.jsp):

1. Download Tanuki Java Service Wrapper Community Edition and extract it to a directory (referred to as `TANUKI_HOME`).
1. Download [this file](/sample-scripts/xl-deploy-as-windows-service-deployit.conf) to `TANUKI_HOME/conf` and rename it `deployit.conf`.
1. Edit `deployit.conf` and set the `JAVA_HOME`, `DEPLOYIT_HOME`, `JAVASERVICEWRAPPER_HOME` and `SERVICE_TITLE` configuration variables.

Test the configuration by running the console in `%JAVASERVICEWRAPPER_HOME%` and executing:

    bin\wrapper.exe -c ..\conf\deployit.conf

Install XL Deploy as a Windows service by running the console in `%JAVASERVICEWRAPPER_HOME%` and executing:

     bin\wrapper.exe -i..\conf\deployit.conf

To uninstall XL Deploy as a Windows service, run the console in `%JAVASERVICEWRAPPER_HOME%` and execute:

    bin\wrapper.exe -r..\conf\deployit.conf

**Note:** `stdout` and `stderr` are in `%DEPLOYIT_HOME%\wrapper.log`.

## Install XL Deploy using Yet Another Java Service Wrapper

To install XL Deploy as a Windows service using [Yet Another Java Service Wrapper (YAJSW)](http://yajsw.sourceforge.net/):

1. Download YAJSW and extract it to a directory (referred to as `YAJSW_HOME`).
1. Download [this file](/sample-scripts/xl-deploy-as-windows-service-wrapper.conf) to `YAJSW_HOME/conf` and rename it `wrapper.conf`.
1. Edit `wrapper.conf` and set the `JAVA_HOME` and `DEPLOYIT_HOME` configuration variables.
1. Follow the [YAJSW Quick Start](http://yajsw.sourceforge.net/#mozTocId212903) from step "Execute your wrapped application as console application by calling...".

The default location of the wrapper log file is `DEPLOYIT_HOME/log/wrapper.log`.

## Install XL Release using Yet Another Java Service Wrapper

To install XL Deploy as a Windows service using [YAJSW](http://yajsw.sourceforge.net/):

1. Download YAJSW and extract it to a directory (referred to as `YAJSW_HOME`).
1. Download [this file](/sample-scripts/xl-release-as-windows-service-wrapper.conf) to `YAJSW_HOME/conf` and rename it `wrapper.conf`.
1. Edit `wrapper.conf` and set the `JAVA_HOME` and `DEPLOYIT_HOME` configuration variables.
1. Follow the [YAJSW Quick Start](http://yajsw.sourceforge.net/#mozTocId212903) from step "Execute your wrapped application as console application by calling...".

The default location of the wrapper log file is `DEPLOYIT_HOME/log/wrapper.log`.
