---
title: Create a test tool plugin
categories:
- xl-test
subject:
- Plugin
tags:
- test tool
---

**Disclaimer:** As of now the xl-test-server library is required to create a new test tool!

XL Test comes with support for a couple of test tools: a.o. FitNesse, xUnit, Gatling, Cucumber. There may be reasons to create a custom test tool:

- Your tool is not supported
- The test results need extra information not collected by the default importers

A test tool purpose is to:

1. Search for test results
2. Import test results into XL Test



## The basics

Test tools are created in Java, the language XL Test is also written in. A test tool implements the `com.xebialabs.xltest.domain.TestTool` interface. Test tools can extend `BaseTestTool` for convenience. (TODO: link javadoc?)

Test tools use the [Overthere library](https://github.com/xebialabs/overthere) to interact with file systems. This library makes it possible to perform file system actions on a remote machine, just as if you're doing it on you local machine.

A Test tool has a set of static properties:

1. A name
2. A category
3. A default search pattern

The **name** is the name of the test tool. This name will be visible in XL Test. The **category** denotes the type of test tool. The values used in XL Test are *functional* and *performance*. The category will relate the test specifications built on a tool to the set of possible reports that may be used to visualize the test results.

A **default search pattern** is used in the import wizard and Jenkins plugin to define the default pattern used for collecting test result files.

## Find test result files

The first task of a test tool is to tell which files it will import. This is done via the method `findTestResultPaths`:

```
Collection<OverthereFile> findTestResultPaths(OverthereFile searchRoot,
                                              FileMatcher fileMatcher);
```

The method takes a base folder, **searchRoot**, and a file match pattern. The pattern is a Ant-like search pattern. The default behavior of this method is a file search:

```
public Collection<OverthereFile> findTestResultPaths(OverthereFile searchRoot,
                                                     FileMatcher fileMatcher) {
    return Globbit.find(searchRoot, fileMatcher);
}
```

## Find what should be imported

As a next step in creating a test tool it's important to know *what* has to be imported. Some tools keep a history of test executions. XL Test should know which test results to import and which test results have been imported already.

**TODO:** This feature is currently work in progress.

The `findImportables` method will produce a list of importable test results. Each *importable* will be treated as a separate test run.


```
List<Importable> findImportables(OverthereFile testResultPath,
                                 FileMatcher fileMatcher);
```

The `Importable` interface has one method:

```
void doImport(final UUID testRunId, final EventHandler eventHandler) throws Exception;
```

`doImport` will perform the actual import. Individual test results will be sent as `com.xebialabs.xltest.domain.Event`'s to the event handler.

A Importable should send an `importStarted` event before it starts importing and an `importFinished` event after the import is done. Take a look at the [reference documentation on events](#) to see which properties are expected for certain types of events.

