---
title: Using Python modules in XL Deploy CLI scripts
categories:
- xl-deploy
tags:
- cli
- jython
---

Python has a lot of useful libraries that are sometimes handy to use from a CLI script. Most of the libraries, though, are not packaged with the XL Deploy command-line interface (CLI) out of the box. Here's how to add them.

Let's say we want to use the `urllib` module. In the Python script, we want to import the module, but we first need to make it available to the CLI. Follow these steps:

1. Download the Jython release from [www.jython.org](http://www.jython.org/).
1. Install the release on your system.
1. Edit the CLI startup script (`cli.sh` or `cli.cmd`).
1. Include the directory `<JYTHON_HOME>/lib` in the classpath used when starting the CLI.

Now, the urllib module can be imported.

**Note:** The XL Deploy CLI actually uses Jython 2.5.1 to allow writing Python scripts against the product. Not all Python libraries are available in Jython. Take a look at the [Jython documentation](http://www.jython.org/docs/library/indexprogress.html) for details.
