---
title: Using the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- script
- python
- jython
---

XL Deploy includes a command-line interface (CLI) that you can use to control and administer many features, such as discovering middleware topology, setting up environments, importing packages, and performing deployments. It connects to the XL Deploy server using the standard HTTP/HTTPS protocol, so it can be used remotely without firewall issues.

You can program the CLI with [Python](http://www.python.org/).

## Passing arguments to CLI commands or script

You can pass arguments from the command line to the CLI. You are not required to specify any options to pass arguments. 

This is an example of passing arguments, without specifying options:

    ./cli.sh these are four arguments

And with options:

    ./cli.sh -username User -port 8443 -secure again with four arguments

You can start an argument with the `-` character. To instruct the CLI to interpret it as an argument instead of an option, use the `--` separator between the option list and the argument list:

    ./cli.sh -username User -- -some-argument there are six arguments -one

This separator only needs to be used if one or more of the arguments begin with `-`.

You can use the arguments in commands given on the CLI or in a script passed with the `-f` option, by using the `sys.argv[index]` method, whereby the index runs from 0 to the number of arguments. Index 0 of the array contains the name of the passed script, or is empty when the CLI was started in interactive mode. The first argument has index 1, the second argument index 2, and so forth. Given the command line in the first example presented above, the following commands:

    import sys
    print sys.argv

Would yield as a result:

    ['', '-some-argument', 'there', 'are', 'six', 'arguments', '-one']

## The CLI and special (Unicode) characters

By default the CLI uses the platform encoding, this may cause issues in a setting were no uniform encoding is used between systems. Or where multibyte encodings may appear. Of special note is Jython's default behavior regarding strings and different encodings.

When strings need to support Unicode, it is recommended to make proper unicode strings in your scripts. That is, `u` prefix for a string and/or use the `unicode` type. For example, to create a dictionary with special characters in it:

    dic = factory.configurationItem('Environments/UnicodeExampleDict', 'udm.Dictionary')
    dic.entries = { 'micro' : u'µ', 'paragraph' : u'§', 'eaccute' : u'é' }
    repository.create(dic)
    print dic.entries

If values come from a database or other web service, you should be aware of the encoding and take care of properly converting the values to Unicode.
