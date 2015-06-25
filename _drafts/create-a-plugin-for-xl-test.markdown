---
layout: beta
title: Create a plugin for XL Test
categories:
- xl-test
subject:
- Plugins
tags:
- test tool
- plugin
- import
---

XL Test has built-in support for [several test tools and output formats](supported-test-tools-and-output-formats.html). It also supports integration with other tools through plugins. A test tool plugin:

1. Searches for test results
2. Imports test results into XL Test

For example, you might want to create a plugin if:

* You would like to integrate with another test tool
* You would like to import test results data in addition to what is imported by default

**Important:** The `xl-test-server` library is required to create a new test tool plugin.

## Plugin basics

Plugins are written in Java and implement the `com.xebialabs.xltest.domain.TestTool` interface. A plugin can extend the `BaseTestTool` type.

Plugins use the [Overthere library](https://github.com/xebialabs/overthere) to interact with file systems. This library makes it possible to perform file system actions on a remote machine.

Each plugin must have:

* A name: This is the name of the test tool, as it will appear in XL Test.
* A category: This indicates the quality attribute on which the tool focuses. Examples include  *functional* and *performance*. The category relates the test specifications that use this tool to the set of reports that are available for the test results.
* A default search pattern: This is used in the import wizard and in the Jenkins plugin to identify and collect test result files.

## Identify test result files

A test tool plugin must identify the files that it will import. This is done with the `findTestResultPaths` method:

    Collection<OverthereFile> findTestResultPaths(OverthereFile searchRoot,
                                              FileMatcher fileMatcher);

The method takes a base folder, `searchRoot`, and a file match pattern. The pattern is [similar to the pattern used by Apache Ant](/xl-test/concept/xl-test-file-selection-patterns.html).

The default behavior of this method is a file search:


    public Collection<OverthereFile> findTestResultPaths(OverthereFile searchRoot,
                                                     FileMatcher fileMatcher) {
        return Globbit.find(searchRoot, fileMatcher);
    }

## Identify the results to import

Some tools keep a history of test executions. A plugin must identify which test results have already been imported, and which results to import.

The `findImportables` method provides a list of importable test results. Each importable is treated as a separate test run.

    List<Importable> findImportables(OverthereFile testResultPath,
                                 FileMatcher fileMatcher);

The `Importable` interface has one method:

    void doImport(final UUID testRunId, final EventHandler eventHandler) throws Exception;

`doImport` will perform the actual import. Individual test results will be sent to the event handler as `com.xebialabs.xltest.domain.Event`s.

An importable should send an `importStarted` event before it starts importing and an `importFinished` event after the import is complete. {% comment %}Take a look at the [reference documentation on events](#) to see which properties are expected for certain types of events.{% endcomment %}
