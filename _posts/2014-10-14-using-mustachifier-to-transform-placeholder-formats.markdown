---
title: Using Mustachifier to transform placeholder formats
categories:
- xl-deploy
tags:
- extension
- package
- application
- dictionary
---

The [Mustachifier importer](https://github.com/xebialabs/community-plugins/tree/master/deployit-server-plugins/importers/mustachifier-importer) is an XL Deploy server extension that transforms archive files to XL Deploy-compatible [Deployment ARchives (DARs)](http://docs.xebialabs.com/releases/latest/xl-deploy/packagingmanual.html#dar-format) during the import phase, using configurable transforms. 

{% comment %} The 'raw' tags below are required because Jekyll interprets text in double curly brackets as Liquid code {% endcomment %}

The importer is especially useful for converting placeholders in configuration files to XL Deploy's {% raw %}`{{...}}`{% endraw%} format.

This example will show how the importer works.

## Structure of the DAR file

This is the structure of the DAR file in the example. `TestFolder5` is the path to the configuration item (CI), and `TestFolder5-CI-Name` is the CI.

	DAR STRUCTURE
		├── META-INF
		│   └── MANIFEST.MF
		├── TestFolder5
		│   └── TestFolder5-CI-Name
		│       ├── dir1
		│       │   └── file1.txt
		│       ├── dir2
		│       │   └── file2.txt
		│       └── dir3
		│           └── file3.txt
		├── TestFolder6
		│   └── TestFolder6-CI-Name
		│       ├── dir4
		│       │   └── file4.txt
		│       ├── dir5
		│       │   └── file5.txt
		│       └── dir6
		│           └── file6.txt
		└── deployit-manifest.xml

## File contents

These are the contents of the files in the DAR file.

`file1.txt`

    foo=[bar]
    baz=quux

`file2.txt`

    foo=[bar]
    baz=quux

`file3.txt`

    foo=[bar]
    baz=quux


`file4.txt`

    baz=((quux))


`file5.txt`

    foo=bar
    baz=((quux))


`file6.txt`

    foo=bar
    baz=((quux))

## Manifest

This is the DAR file [manifest](http://docs.xebialabs.com/releases/latest/xl-deploy/packagingmanual.html#old-style-manifest-format).

	Manifest-Version: 1.0
	Deployit-Package-Format-Version: 1.3
	CI-Application: TestMustachifier
	CI-Version: 1.6

	Name: TestFolder5
	CI-scanPlaceholders: true
	CI-Type: file.Folder
	CI-Name: TestFolder5-CI-Name
	CI-targetPath: /Users/droberts/MyTargetDir

	Name: TestFolder6
	CI-scanPlaceholders: true
	CI-Type: file.Folder
	CI-Name: TestFolder6-CI-Name
	CI-targetPath: /Users/droberts/MyTargetDir

## String transform options

The string transform options are:

* `transform.N.type`: String replace
* `transform.N.ci.type`: String replace
* `transform.N.ci.path.pattern`: String replace (optional)
* `transform.N.encoding`: Encoding of the source file(s) matched; for example, `ISO-8859-1`
* `transform.N.find`: The string to find; for example, `foo`
* `transform.N.replacement`: The string to use as a replacement; for example, `bar`
* `transform.N.textFileNamesRegex`: Regular expression that matches file names of text files; default is `.+\.(cfg | conf | config | ini | properties | props | txt | xml )`

## Regular expression transform options

The regular expression transform options are:

* `transform.N.type`: Regular expression replace
* `transform.N.ci.type`: Regular expression replace
* `transform.N.ci.path.pattern`: Regular expression replace (optional)
* `transform.N.encoding`: Encoding of the source file(s) matched; for example, `ISO-8859-1`
* `transform.N.pattern`: The pattern to find; for example, `'\\$(\\{[^\\}]+\\})'`. Uses the Java regular expression syntax, so special characters should be escaped using *double* backslashes; for example, `'\\$'` for a dollar sign. Parts of the pattern may be captured as matching groups.
* `transform.N.replacement`: The string to use as a replacement; for example, `'\\{$1\\}'`. Matching groups from the pattern match may be used. Again, uses the Java regular expression syntax.
* `transform.N.textFileNamesRegex`: Regular expression that matches file names of text files; default is `.+\.(cfg | conf | config | ini | properties | props | txt | xml )`

## `mustachifier.properties` file

Place the `mustachifier.properties` file in the `<XLDEPLOY_HOME>/conf` directory and restart the XL Deploy server. The file contains:

{% comment %} The 'raw' tags below are required because Jekyll interprets text in double curly brackets as Liquid code {% endcomment %}

	transform.1.type=string-replace
	transform.1.ci.type=file.Folder
	transform.1.ci.path.pattern=TestFolder5
	transform.1.encoding=UTF-8
	transform.1.find=[bar]
	transform.1.replacement={% raw %}{{bar-transform-1}}{% endraw %}

	transform.2.type=string-replace
	transform.2.ci.type=file.Folder
	transform.2.ci.path.pattern=TestFolder6
	transform.2.encoding=UTF-8
	transform.2.find=((quux))
	transform.2.replacement={% raw %}{{quux-transform-2}}{% endraw %}

## Required JAR files

These files are required for the importer:

* `<XLDEPLOY_HOME>/lib/mustachifier-3.8.0.jar`
* `<XLDEPLOY_HOME>/plugins/mustachifier-importer-3.8.0.jar`

# Transform results

These are the results of the transform.

{% comment %} The 'raw' tags below are required because Jekyll interprets text in double curly brackets as Liquid code {% endcomment %}

`file1.txt`

    foo={% raw %}{{bar-transform-1}}{% endraw %}
    baz=quux

`file2.txt`

    foo={% raw %}{{bar-transform-1}}{% endraw %}
    baz=quux

`file3.txt`

    foo={% raw %}{{bar-transform-1}}{% endraw %}
    baz=quux

`file4.txt`

    baz={% raw %}{{quux-transform-2}}{% endraw %}

`file5.txt`

    foo=bar
    baz={% raw %}{{quux-transform-2}}{% endraw %}

`file6.txt`

    foo=bar
    baz={% raw %}{{quux-transform-2}}{% endraw %}

## Debugging

To debug the importer, add the following line to `<XLDEPLOY_HOME>/conf/logback.xml`:

    <logger name="ext.deployit.community.cli.mustachify" level="DEBUG" />

### Server log messages

These are examples of server log messages you can use for debugging:

	2014-06-10 10:36:29.725 [qtp190128751-68] {username=admin} DEBUG e.d.c.cli.mustachify.Mustachifier - Copied source archive /Users/droberts/training/xl-deploy-4.0.0-server/importablePackages/TestMustachifier-1.8.dar to new target /var/folders/gj/wmb0ggy10h98c1gymjgcq3vw0000gn/T/TestMustachifier-1.82431732514824169860-dar.dar
	
	2014-06-10 10:36:29.729 [qtp190128751-68] {username=admin} DEBUG e.d.c.cli.mustachify.Mustachifier - Matched 'RegexReplaceTransformer{patternToFind=\Qfoo\E, replacement='foo-transform-1'} DarTextEntryTransformer{encoding=UTF-8} DarEntryTransformer{entryPathPatternToMatch=TestFolder5, pathIndependentMatch=false, typeToMatch='file.Folder'}' to DAR entry 'DarManifestEntry [type=file.Folder, name=TestFolder5-CI-Name, jarEntryPath=TestFolder5]'
	
	2014-06-10 10:36:29.730 [qtp190128751-68] {username=admin} DEBUG e.d.c.cli.mustachify.Mustachifier - Matched 'RegexReplaceTransformer{patternToFind=\Qbaz\E, replacement='baz-transform-2'} DarTextEntryTransformer{encoding=UTF-8} DarEntryTransformer{entryPathPatternToMatch=TestFolder6, pathIndependentMatch=false, typeToMatch='file.Folder'}' to DAR entry 'DarManifestEntry [type=file.Folder, name=TestFolder6-CI-Name, jarEntryPath=TestFolder6]'
	
	2014-06-10 10:36:29.733 [qtp190128751-68] {username=admin} DEBUG e.d.c.cli.mustachify.Mustachifier - About to transform entries [DarManifestEntry [type=file.Folder, name=TestFolder6-CI-Name, jarEntryPath=TestFolder6], DarManifestEntry [type=file.Folder, name=TestFolder5-CI-Name, jarEntryPath=TestFolder5]]
