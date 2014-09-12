---
title: Writing Jython scripts for XL Deploy
categories:
- xl-deploy
tags:
- Jython
- scripting
- customization
- extension
- xl-deploy-3.9.x
- xl-deploy-4.0.x
- xl-deploy-4.5.x
---

Jython scripting is a very powerful way to extend or customise XL Deploy. There are several components of XL Deploy which can make use of such scripts and although each of them puts different objects on the context of scripting engine, there are some general rules and good practices of writing, organising and packaging your scripts.

## Pointing to a Jython script from configuration files

Usually when you attach a Jython script to some action/event/component of XL Deploy, you specify a relative path to it. In this case the only way how XL Deploy can find this script is by attaching the path to the each segment of own classpath and looking there.

If we have a configuration snippet like: `... script="myproject/run.py"...` then XL Deploy can find the script at `ext/myproject/run.py` because `ext` folder is on the classpath. Besides that the script can be packaged into a **jar** and placed into `plugins` folder: XL Deploy scans it at startup and adds all found jars to the classpath. The jar archive in this case should contain `myproject` folder and `run.py` script inside it.

## Creating a jar

Different tools create jars differently and one important thing to verify that the file paths in the plugin JAR do not start with `./`. You can check this with `jar tf yourfile.jar`. If you package two files and a folder the output should look like:

      file1.xml 
      file2.xml 
      web/ 

and not like:

      ./file1.xml 
      ./file2.xml 
      web/ 

## Splitting your Jython code into modules

It is possible to split your code into modules. There are 2 important things about that:

*   Yo have to create an empty `__init__.py` in each folder which becomes a module (or a segment of a package name).
*   Make sure to begin the package name with something unique, otherwise it can clash with other extensions or standard Jython modules. Good example: `myproject.modules.repo`. Bad example: `utils.repo`.

Let's imagine we have the following code in `run.py`

      # myproject/run.py  
      infrastructureCis = repositoryService.query(None, None, "Infrastructure", None, None, None, 0, -1)
      applicationsCis = repositoryService.query(None, None, "Applications", None, None, None, 0, -1)

      # do something with those CIs

You can create a class which helps to perform queries to the repository and hides unnecessary parameters.

      # myproject/modules/repo.py

      class RepositoryHelper:

            def __init__(self, repositoryService):
                  self._repositoryService = repositoryService

            def get_all_cis(self, parent):
                  ci_ids = self._repositoryService.query(None, None, parent, None, None, None, 0, -1)
                   return map(lambda ci_id: self._repositoryService.read(ci_id.id), ci_ids)

Then in `run.py` you can import and use `RepositoryHelper`

      # myproject/run.py

      from myproject.modules import repo
      repository_helper = repo.RepositoryHelper(repositoryService)
      infrastructureCis = repository_helper.get_all_cis("Infrastructure")
      applicationsCis = repository_helper.get_all_cis("Applications")

      # do something with those CIs</pre>

The contents of the folder/jar archive will then be:

      myproject
      myproject/__init__.py
      myproject/run.py
      myproject/modules
      myproject/modules/__init__.py
      myproject/modules/repo.py

## Using 3rd party libraries from scripts

Besides your own scripts it is possible to use:

*   3rd party Python libraries
*   3rd party Java libraries
*   Your own Java classes

In each of this cases make sure then they are available on the classpath in the same manner as described for own python modules.

## Best practices during development

#### Develop in directories, run in JARs

While developing and debugging scripts it is convenient to keep the files open in the editor and change them after every iteration. Once done with development it is recommended to package them into a jar file and put it into `plugins` folder.

#### Restarting the server

Normally there is no need to restart the server after changing a Jython script, however if you use modules, those are cached by the scripting engine after first execution. To avoid this effect you can use built-in `reload()` function.

      from myproject.modules import repo
      reload(repo)
      # ...

## Examples

An example of scripting can be found in [UI extension demo plugin](https://github.com/xebialabs/xl-deploy-samples/blob/master/ui-extension-demo-plugin).
