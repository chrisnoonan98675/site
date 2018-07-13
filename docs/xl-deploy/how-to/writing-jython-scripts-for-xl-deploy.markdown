---
title: Writing Jython scripts for XL Deploy
subject:
- Customization
categories:
- xl-deploy
tags:
- jython
- python
- script
---

Jython scripting is a very powerful way to extend or customize XL Deploy. There are several components of XL Deploy that can use such scripts and, although each of them puts different objects on the context of scripting engine, there are some general rules and good practices for writing, organizing, and packaging your scripts.

## Pointing to a Jython script from configuration files

Usually when you attach a Jython script to an XL Deploy action, event, or component, you specify a relative path to it. In this case, the only way XL Deploy can find this script is by attaching the path to the each segment of its own classpath and looking there.

If you have a configuration snippet such as `... script="myproject/run.py"...`, then XL Deploy can find the script at `ext/myproject/run.py` because the `ext` folder is on the classpath.

Also, the script can be packaged into a JAR and placed in the `plugins` folder; XL Deploy scans it at startup and adds the JARs it finds to the classpath. In this case, the JAR archive in this case should contain the `myproject` folder and `run.py` script.

## Creating a JAR

Different tools create JARs differently, so it is important to verify that the file paths in the plugin JAR do not start with `./`. You can check this with `jar tf yourfile.jar`. If you package two files and a folder, the output should look like:

    file1.xml
    file2.xml
    web/

And not like:

    ./file1.xml
    ./file2.xml
    web/

## Splitting your Jython code into modules

It is possible to split your code into modules. Note that:

* You have to create an empty `__init__.py` in each folder that becomes a module (or a segment of a package name).
* Start the package name with something unique, otherwise it can clash with other extensions or standard Jython modules. For example, `myproject.modules.repo` is a better name than `utils.repo`.

Let's imagine we have the following code in `run.py`:

{% highlight python %}
# myproject/run.py  
    infrastructureCis = repositoryService.query(None, None, "Infrastructure", None, None, None, 0, -1)
    applicationsCis = repositoryService.query(None, None, "Applications", None, None, None, 0, -1)

# do something with those cis
{% endhighlight %}

You can create a class that helps perform queries to the repository and hides unnecessary parameters.

{% highlight python %}
# myproject/modules/repo.py

class RepositoryHelper:

    def __init__(self, repositoryService):
        self._repositoryService = repositoryService

    def get_all_cis(self, parent):
        ci_ids = self._repositoryService.query(None, None, parent, None, None, None, 0, -1)
        return map(lambda ci_id: self._repositoryService.read(ci_id.id), ci_ids)
{% endhighlight %}

Then, in `run.py`, you can import and use `RepositoryHelper`:

{% highlight python %}
# myproject/run.py

from myproject.modules import repo
repository_helper = repo.RepositoryHelper(repositoryService)
infrastructureCis = repository_helper.get_all_cis("Infrastructure")
applicationsCis = repository_helper.get_all_cis("Applications")

# do something with those cis
{% endhighlight %}

The contents of the folder and JAR archive will then be:

    myproject
    myproject/__init__.py
    myproject/run.py
    myproject/modules
    myproject/modules/__init__.py
    myproject/modules/repo.py

## Using third-party libraries from scripts

In addition to your own scripts, you can use:

* Third-party Python libraries
* Third-party Java libraries
* Your own Java classes

In each of this cases make sure then they are available on the classpath in the same manner as described for your own Jython modules.

## Best practices during development

### Develop in directories, run in JARs

While developing and debugging scripts, it is convenient to keep the files open in the editor and change them after every iteration. After you have finished development, it is recommended to package them into a JAR file and place it in the `plugins` folder.

### Restarting the server

Normally there is no need to restart the server after changing a Jython script; however, modules are cached by the scripting engine after their first execution. To avoid this effect, you can use built-in `reload()` function.

{% highlight python %}
from myproject.modules import repo
reload(repo)
# ...
{% endhighlight %}

## Examples

You can find an example of scripting in the UI extension demo plugin, which is available in the `samples` folder of your XL Deploy installation.
