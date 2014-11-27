---
title: Clean up application versions in XL Deploy's repository
categories:
- xl-deploy
tags:
- package
- application
- cli
---

While working with Deployit in a continuous integration setup, you might end up with many versions of an application in Deployit's repository; daily developer builds lead to a lot of application revisions before a stable version is chosen and promoted to Test or QA.

Every once in a while, you may want to delete a few of these previous revisions to reduce the number of versions kept in Deployit's repository. You can use a script for XL Deploy's [command-line interface](http://docs.xebialabs.com/releases/latest/xl-deploy/climanual.html) (CLI) to do this.

**Note:** This script has been tested with [Deployit 3.9.x](http://docs.xebialabs.com/product-version.html#/deployit/3.9.x).

## Usage

[This CLI script](/sample-scripts/clean-up-application-versions-xl-deploy-repository/cleanRevisions.py) can be used to clean up all revisions of an application older than a specified version. 

Save this file in the `scripts` directory of your XL Deploy CLI installation (if this directory does not exist, create it first). Then, you can run it from the CLI:

    bin/cli.sh -expose-proxies -username <user> -password <pass> -f scripts/cleanRevisions.py "Applications/MyApp/1.0.39"

 The output will look like:

    2013-10-02 14:26:26.086 [main] INFO c.x.d.b.remote.DeployitCommunicator - Connecting to the Deployit server at http://127.0.0.1:4516/deployit...
    2013-10-02 14:26:26.181 [main] INFO c.x.d.b.remote.DeployitCommunicator - Successfully connected. Exposing Proxies!
    Deleting all revisions before this Revision : Applications/MyApp/1.0.39

    This package : Applications/MyApp/2.0.37 is being deleted

    This package : Applications/MyApp/2.0.38 is being deleted
