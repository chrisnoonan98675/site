---
title: Extend the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- script
- python
- jython
- extension
---

You can extend the XL Deploy command-line interface (CLI) by installing extensions that are loaded during CLI startup. Extensions are Python scripts, for example with Python class definitions, that will be available in commands or scripts run from the CLI. This feature can be combined with arguments given on the command line when starting up the CLI.

To install CLI extensions follow these steps:

1. Create a directory called `ext`. This directory should be created in the same directory from which you will start the CLI; during startup the current directory will be searched for the existence of the `ext` directory.
2. Copy Python scripts into the `ext` directory.
3. Restart the CLI. During startup, the CLI will search for, load and execute all scripts with the `py` or `cli` suffix found in the extension directory.

Please note that the order in which scripts from the `ext` directory are executed is not guaranteed.
