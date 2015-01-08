---
title: Specify file encoding for XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- encoding
---

The `file.encoding` system property defines the file encoding setting on the XL Deploy server.

## Oracle JDK

To change the file encoding for the Oracle Java Development Kit (JDK), set the following system property:

    -Dfile.encoding=<FileEncodingType>

For example:

    -Dfile.encoding=UTF-8
	
## IBM JDK

To change the file encoding for the IBM Java Development Kit (JDK), set the following system property:

    -Dclient.encoding.override=<FileEncodingType>

For example:

    -Dclient.encoding.override=UTF-8
