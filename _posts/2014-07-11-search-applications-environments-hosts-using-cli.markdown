---
title: Search applications, environments, and hosts using the XL Deploy CLI
categories:
- xl-deploy
tags:
- cli
- application
- environment
- host
---

[This script](/sample-scripts/search-applications-environments-hosts-using-cli/searchRepo.py) enables you to use the XL Deploy [command-line interface](http://docs.xebialabs.com/releases/latest/xl-deploy/climanual.html) (CLI) to search for applications, environments, and hosts that are stored in your XL Deploy repository.

To use it:

1. Save it in the `ext` directory of your XL Deploy home directory.
2. Start the XL Deploy CLI with the `-expose-proxies` option.

To search for applications with the command `findApp("testApp")`. Replace `testApp` with the ID of the application that you want to find.

To search for environments with the command `findEnvironment("testEnv")`. Replace `testEnv` with the ID of the environment that you want to find.

To search for hosts with the command `findHost("testCifsHost", "overthere.CifsHost")`. Replace `testCifsHost` with the name of the host that you want to find, and `overthere.CifsHost` with its type.
