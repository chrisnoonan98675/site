---
title: Use the XL Deploy CLI to build and publish a deployment package
categories:
- xl-deploy
tags:
- cli
- application
- package
---

XL Deploy users often want to automate the process of building a deployment package (DAR file) and publishing it to an XL Deploy server. If you're using [Maven](http://tech.xebialabs.com/deployit-maven-plugin), [Jenkins](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin), [Team Foundation Server](http://docs.xebialabs.com/releases/latest/tfs-plugin/tfsPluginManual.html), or one of the other build and ci tools for which we have plugins, there's not too much you should have to do here. But it's useful to see how easy it is to put together similar functionality using the XL Deploy [command-line interface](http://docs.xebialabs.com/releases/latest/xl-deploy/climanual.html) (CLI).

[This CLI script](/sample-scripts/build-deployment-package-and-publish/create-and-import-package.py):

1. Takes an XL Deploy manifest and a path to a directory containing the artifacts to be included in the deployment package as arguments
2. Builds a DAR file based on the manifest
3. Publishes the DAR file to an XL Deploy server

**Tip:** If you're using Microsoft Windows, you can use the [Manifest Editor](http://docs.xebialabs.com/releases/latest/tfs-plugin/manifestEditorManual.html) or compose the package in the XL Deploy interface, export it, and extract the manifest to use as a template.

In the manifest, paths to artifacts should be relative to the "artifact base directory", which is the second argument to the script. If, for example, the artifact is `/path/to/artifact/basedir/app/my.exe` and the second argument to the script is `/path/to/artifact/basedir`, the path in the manifest should be `app/my.exe`.

The manifest can contain a token, `@@VERSION@@`, which will be replaced by a generated version number based on a timestamp. Alternatively, if you prefer to pass the version number as an argument to the script, you can change these lines:

    # version = sys.argv[3]
    version = time.strftime('%Y%m%d.%H%M%S', time.localtime())

To:

    version = sys.argv[3]
    # version = time.strftime('%Y%m%d.%H%M%S', time.localtime())

In that case, the third argument to the script will be used as the version number.

## Examples

Using the unmodified script (with auto-generated versions):

    CLI_HOME/bin/cli.sh [cli-arguments-if-needed] -f /path/to/create-and-import-package.py -- /path/to/deployit-manifest.xml /path/to/artifact/basedir

Using the modified script (that is, the version is passed as the third argument):
    
    CLI_HOME/bin/cli.sh [cli-arguments-if-needed] -f /path/to/create-and-import-package.py -- /path/to/deployit-manifest.xml /path/to/artifact/basedir packageversion

For a related sample script that will create packages without requiring a manifest, refer to [*Build a package in XL Deploy from properties in files, dictionaries, and command arguments*](/build-deployment-package-from-properties).
