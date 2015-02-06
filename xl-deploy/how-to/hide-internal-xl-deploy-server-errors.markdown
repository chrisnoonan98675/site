---
title: Hide internal XL Deploy server errors
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- logging
---

By default, XL Deploy will not hide any internal server errors due to incorrect user input. This allows clients to more easily determine what went wrong and report problems with the XL Deploy support team. However, this behavior can be turned off by editing the `conf/deployit.conf` file in the XL Deploy server directory and edit the following setting:

    hide.internals=true

Enabling this setting will cause the server to return a response such as the following:

	Status Code: 400
	Content-Length: 133
	Server: Jetty(6.1.11)
	Content-Type: text/plain

	An internal error has occurred, please notify your system administrator with the following code: a3bb4df3-1ea1-40c6-a94d-33a922497134

The code shown in the response can be used to track down the problem in the server logging.
