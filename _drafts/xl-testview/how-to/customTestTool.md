---
layout: beta
title: Write custom test tools
categories:
- xl-testview
subject:
- System administration
tags:
- system administration
- testtool
- extension
---

XL TestView supports a number of test tools out of the box, but there are many other tools available. Some are commercial, self build or proprietary. To support all these tools, it is possible to write custom test tools.

A test tool is essentially a program that parses several test files, and produces events that XL TestView can store in its database. XL TestView offers several interfaces, and users can write implementations in Python or Java that will provide the logic that is specific for that test tool. The following interfaces are available:

1. FunctionalTestToolApi
2. PerformanceTestToolApi
3. GreatPowerTestToolApi

## Writing a new Test Tool
The following steps are required for a new test tool:

1. Select the interface you require to implement.
2. Write the implementation
3. Add an entry to a synthetic.xml file.

The easiest way to build a test tool is to copy and expand an existing test tool. The following table can help you select a good starting point:

|Script|Tool|Type|Input format|
|------|----|----|------------|
|cucumber.py|Cucumber|Functional|json|
|gatling.py|Gatling|Performance|csv & json|
|jmeter_csv.py|JMeter|Performance|csv|
|jmeter_xml.py|JMeter|Performance|xml|
|junit.py|JUnit|Functional|xml|

## Writing a functional test tool
This is the interface that needs to be implemented:

    public interface FunctionalTestTool<T> extends TestTool {
    
        /**
         * Filters blob list of files in list which contains fails who are *all* interpretable for the test tool
         */
        List<OverthereFile> filterReadable(List<OverthereFile> blob);
    
        /**
         * Splits a file into 'Subjects', which can be thought of as 'test suites' or 'scenarios'
         *
         * Returns a list of subjects, which are of type T
         */
        List<T> splitIntoSubjects(OverthereFile file);
    
        List<T> splitIntoCases(final T subject);
    
        /**
         * Returns the time that the last test was executed, or the most recent modification of the file if no such information is available.
         *
         * @param files The test result
         * @return the number of milliseconds since 1 January 1970
         */
        long getLastModified(OverthereFile files);
    
        /**
         * Returns the test result. One of the following:
         * PASSED
         * FAILED
         * SKIPPED
         * ERROR
         *
         * @param testCase
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        String getResult(T testCase, Map<String, Object> eventMap);
    
        /**
         * Gets the duration of a testCase in milliseconds
         * @param testCase
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        int getDuration(final T testCase, final Map<String, Object> eventMap);
    
        /**
         * Gets the reason of failure of a test case
         * @param testCase
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        String failureReason(T testCase, final Map<String, Object> eventMap);
    
        /**
         * Returns the hierarchy of this test. This is a list of Strings that, combined with the test name, uniquely identifies this test case.
         * This map will be used to calculate the drill down features of events.
         *
         * For example:
         *
         * ['com','xebialabs','xltestview','MyTestClass']
         *
         * @param testCase The test case to parse.
         * @param subject
         * @param file
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        List<String> getSuite(T testCase, final T subject, OverthereFile file, Map<String, Object> eventMap);
    
        /**
         * Add additional properties to events
         * @param testCase
         * @param subject
         * @param file
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        Map<String, Object> getOtherProperties(T testCase, final T subject, OverthereFile file, Map<String, Object> eventMap);
    }

This is a template of the python file that needs to be implemented:

    from com.xebialabs.xltest.domain import TestToolCategory
    from com.xebialabs.xltest.domain import FunctionalTestTool
    from com.xebialabs.xltest.domain import TestToolUtils
    
    class CustomTestTool(FunctionalTestTool):
    
        def filterReadable(self, blob):
            pass
    
        def splitIntoSubjects(self, file):
            pass
    
        def splitIntoCases(self, subject):
            pass
    
        def getDuration(self, testCase, eventMap):
            pass
    
        def getResult(self, testCase, eventMap):
            pass
    
        def failureReason(self, testCase, eventMap):
            pass
    
        def getSuite(self, testCase, subject, file, eventMap):
            pass
    
        def getOtherProperties(self, testCase, subject, file, eventMap):
            pass
    
        def getCategory(self):
            pass
    
        def getLastModified(self, file):
            pass
    
    
    resultHolder.result = CustomTestTool()
    
You also need an addition to the synthetic.xml:

    <type type="" extends="xlt.FunctionalTestToolFactory">
        <property name="testToolName" default=""/>
        <property name="category" default="functional"/>
        <property name="defaultSearchPattern" default=""/>
        <property name="scriptLocation" default=""/>
    </type>

In the system only this type definition is used, no instances are created. Set the following values:

* type - A unique typename for this test Tool
* testToolName - A unique, user friendly name for this test tool
* category - `functional` or `performance`
* defaultSearchPattern - A Ant style pattern to select relevant test result files. For example: `**/test-results/TEST*.xml` selects all files starting with `TEST` and ending with `.xml` that are in a directory `test-results`, which can be at any depth in the file tree.
* scriptLocation - The name of the script.

For more information about the type system, please look at <link>
##Writing a performanceTestTool
...

## Writing a GreatPowerTestTool
The GreatPowerTestTool gives complete control over the events produced. It is done by implementing this interface:

    /**
     * This is the most generic test tool, where *all* responsibility is delegated to the tool. (old style)
     */
    public interface GreatPowerTestTool extends TestTool {
    
        List<Event> generateEventsFromFiles(UUID testRunId, List<OverthereFile> blob) throws ImportException;
    
    }

There are hardly any restrictions on the list of events produced. The test tool is responsible to generate sensible events.

    <type type="" extends="xlt.GreatPowerTestToolFactory">
        <property name="testToolName" default=""/>
        <property name="category" default=""/>
        <property name="defaultSearchPattern" default=""/>
        <property name="scriptLocation" default=""/>
    </type>

The synthetic is identical to the synthetic entries of the functional and performance test tools, except for the category. It should be set to `functional` or `performance`. 