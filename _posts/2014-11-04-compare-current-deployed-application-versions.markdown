---
title: Compare current deployed application versions in a pipeline
categories:
- xl-deploy
tags:
- cli
- application
---

[This sample XL Deploy command-line interface (CLI) script](/sample-scripts/compareAppAcrossEnvironments-0.9.cli) produces a component-by-component overview of deployed versions across an application's pipeline. The overview can be printed to the CLI console or saved as a CSV file.

In [the sample output](/sample-scripts/compareAppAcrossEnvironments-sample-result.csv), the pipeline for `sampleApp` consists of `MyEnv2` and `MyEnv`. The same version of `sampleApp`—1.0-20130302-220317—is deployed to both environments, although with different mappings and environment-specific settings.

To install, copy [this file](/sample-scripts/compareAppAcrossEnvironments-0.9.cli) to your `CLI_HOME/ext` directory. You can then call the following to print the overview to the CLI console:

    compareAppOnEnvironments(appName) // e.g. compareAppOnEnvironments("sampleApp")

And call the following to save the overview to a file:

    compareAppOnEnvironmentsF(targetFile, appName) // e.g. compareAppOnEnvironmentsF("~/result.csv", "sampleApp")

This sample script has been tested against Deployit 3.8.4 and may require minor modifications to run against earlier versions.
