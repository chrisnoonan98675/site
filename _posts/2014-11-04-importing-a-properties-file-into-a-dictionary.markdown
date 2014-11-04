---
title: Importing a properties file into a dictionary
categories:
- xl-deploy
tags:
- dictionary
- cli
---

If you find yourself with a lot of properties files that you would like to load into dictionaries, you can use [this XL Deploy command-line interface (CLI) script](/sample-scripts/import-export-dictionaries.cli). Place it in the `ext` directory of your XL Deploy CLI installation. You can invoke the commands below.

Import the contents of `DEPLOYIT_CLI_HOME/myprops.properties` into existing dictionary `Environments/myDict`, preserving existing values in the dictionary if there are entries with the same key in the properties file:

    importIntoDict("myprops.properties", "myDict")

Import the contents of `/my/directory/myprops.properties` into existing dictionary `Environments/Dev/myDict`, overwriting existing values in the dictionary if there are entries with the same key in the properties file:

    importIntoDict("/my/directory/myprops.properties", "Dev/myDict", True)

Export the contents of existing dictionary `Environments/myDict` into properties file `DEPLOYIT_CLI_HOME/myprops.properties`:

    exportFromDict("myDict", "myprops.properties")

Export the contents of existing dictionary `Environments/Dev/myDict` into properties file `/my/directory/myprops.properties`:

    exportFromDict("Dev/myDict", "/my/directory/myprops.properties")

Note that the dictionary referenced, `Environments/myDict`, must exist in the XL Deploy repository.
