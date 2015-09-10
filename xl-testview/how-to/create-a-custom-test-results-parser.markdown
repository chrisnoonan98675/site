---
title: Create a custom test results parser
categories:
- xl-testview
subject:
- Test results
tags:
- system administration
- test result parsers
- extension
since:
- 1.3.0
---

By default, XL TestView supports [a number of test tools](/xl-testview/concept/supported-test-tools-and-test-result-formats.html). XL TestView also allows you to write custom test result parsers so you can integrate with tools that are not supported by default. A test results parser is a program that parses several test result files and produces test results in a format that XL TestView can store in its database. You can write parsers in Python or Java.

This topic explains the steps required to create your own test result parser. The examples in this topic assume a Python-based parser, which is considered the prefered way of creating new test result parsers. Some knowlegde on XL TestView's [key concepts](/xl-testview/concept/key-concepts.html) and [architecture](/xl-testview/concept/understanding-the-architecture.html) are required to understand this topic.

{% comment %} TODO: Insert plug here for possible cooperation with XebiaLabs to sell support? (like, we can help you with building a plugin for X money?) {% endcomment %}

## General approach

The general approach to creating a custom test parser is:

1. Determine the type of results produced by the test tool. XL TestView supports functional and performance test results.
2. Define a new test tool configuration in the `synthetic.xml` file. This defines the type of test results and where XL TestView can find the parser.
3. Write an implementation of the test result parser.
4. Test and repeat from step 2, if needed.

## Using a default test result parser

You can base your custom test results parser on one of the default test result parsers. The following parsers are useful starting points:

{:.table .table-striped}
| Script | Tool | Type | Input format |
| ------ | ---- | ---- | ------------ |
| `junit.py` | JUnit | Functional | XML |
| `cucumber.py` | Cucumber | Functional | JSON |
| `gatling.py` | Gatling | Performance | CSV and JSON |
| `jmeter_csv.py` | JMeter | Performance | CSV |
| `jmeter_xml.py` | JMeter | Performance | XML |

The implementations can be found in the test tools plugin: `plugins/testtools-plugin-x.y.z.jar`.

## Determine test result type

XL TestView supports functional and performance test results.

Functional test results have a *result*, such as *passed*, *failed*, or any other result you want to support. They refer to a single *test case* or, in the case of feature-based test tools such as Cucumber, a *scenario*.

Performance test results are summaries of the performance results from tests.

If the tool you wish to support is not performance-related, you should choose the functional test tool.

## Define a new test tool configuration in `synthetic.xml`

First, you must define a new test tool configuration in the `ext/synthetic.xml` file. For example:

{% highlight xml linenos=table %}
<type type="custom.MyTestToolConfiguration"
      extends="xlt.TestToolConfiguration"
      label="My custom tool">
    <property name="category" default="functional"/>

    <property name="defaultSearchPattern" default=""/>

    <!-- optional, default is python -->
    <property name="language" default="python"/>

    <!-- optional, only used when language is python -->
    <property name="scriptLocation" default="custom-script.py"/>

    <!-- optional, only used when language is java -->
    <property name="className" default="com.mycompany.xltestview.testtools.mytesttool.MyTestToolParser"/>
</type>
{% endhighlight %}

The properties that are available are:

{:.table .table-striped}
| Property | Required? | Default | Description |
| -------- | --------- | ------- | ----------- |
| `type` | Yes | N/A | A unique value that identifies the tool. It is recommended that you prefix the value; for example, XL TestView prefixes tools with `xlt`, as in `xlt.SurefireJUnit`. The prefixes `xlt`, `udm` and `overthere` are used by XL TestView and should not be used by custom tools. <br /><br />Be aware that there is a distinction between the *format* that a test tool should produce and the *report generator* of the build tool that you are using. For example, if you are generating JUnit test results, the way the test results file looks depends on your build tool (Maven, Gradle, Ant) and its *generator* (such as Surefire). It is recommended that you indicate these distinctions when creating the type name. |
| `category` | Yes | N/A | Specifies the type of test results that are generated. Valid values are `functional` (results related to test cases, scenarios, and so on) and `performance` (results related to performance tests, derived from summarized data) |
| `label` | Yes | N/A | A unique, user-friendly name for the test tool. |
| `defaultSearchPattern` | Yes | N/A | An [Ant](http://ant.apache.org/)-style pattern to select relevant test result files. For example: `**/test-results/TEST*.xml` selects all files starting with `TEST` and ending with `.xml` that are in a `test-results` directory, which can be at any depth in the file tree. Multiple patterns may be specified separated with a `','`. This implies that you cannot match on `','` in files.|
| `language` | Yes | `python` | Defines the language in which the parser is written. Valid values are `python` and `java`. |
| `scriptLocation` | Yes, if `language` is `python` or the language is not specified | N/A | Location of the Python script that parses test results. |
| `className` | Yes, if `language` is `java` | N/A | Defines the location of the parser (only applies if `language` is `java`). |

XL TestView uses the same type system as XL Deploy, although only the properties above are used for test tools. For more information about the type system, refer to [Understanding XL Deploy rules](/xl-deploy/how-to/customize-an-existing-ci-type.html).

## Write an implementation of the test results parser

Using the search pattern from the test tool configuration, the test results files to be imported will be assembled into one list and fed to the test results parser. The output of the parser is a collection of test runs. Each test run is a collection of [test results](/xl-testview/concept/events.html). Test results are instances of the Java class [`com.xebialabs.xlt.plugin.api.testrun.Event`](/xl-testview/latest/javadoc/Event.html).

In pseudocode, this looks like:

    TestResultParser(files) -> [ [ test result 1, test result 2, ... ], ... ]

In many cases, all test results that can be found belong to one test run. For example, this is the case for JUnit and Cucumber. One *run* of the test tool generates results, and all of these results belong to the same test run.

Other tools, such as Gatling and FitNesse, build up a *history* of test runs. This means that every *run* of the tool adds test result data, instead of replacing it with new data. This implies that the test results parser will receive the latest and all previous test run data. The parser must deal with this case by identifying runs that have already been imported. See [detecting duplicate imports](/xl-testview/how-to/detect-duplicate-imports.html) for details on how to deal with this.

In addition to `files`, the following variables are used to communicate with the rest of the system:

{:.table .table-striped}
| Attribute | Description |
| --------- | ----------- |
| `files` | The files found in the `working_directory` based on a search pattern provided in the test tool configuration. |
| `working_directory` | Directory there the test results are stored. |
| `test_specification` | The test specification that will own the test run. |
| `test_run_historian` | This service can inform the script if results have already been imported. |
| `logger` | An SLF4J logger you can use to log information in greater detail. |
| `result_holder` | Callback object to return the calculated results|

For usage of the `test_run_historian`, see [detecting duplicate imports](/xl-testview/how-to/detect-duplicate-imports.html).

### Writing a functional test results parser

The result of a functional test tool is a list of test runs. A test run is a list of [test result events](/xl-testview/concept/events.html#functionalresult-event-properties), accompanied by some meta information. This is the simplest example of a test run:

{% highlight json linenos=table %}
[
  {
    "@testedAt": 1440759717000,
    "@type": "importStarted",
    "@runKey": "1440759717000"
  },
  {
    "@duration": 11,
    "@hierarchy": [
      "com",
      "xebialabs",
      "xltest",
      "Test1",
      "method1"
    ],
    "@result": "FAILED",
    "@type": "functionalResult",
    "fileName": "TEST-com.xebialabs.xltest.reference.p1.Junit.xml",
    "firstError": "java.lang.AssertionError: \nExpected: is <true>\n     but: was <false>"
  },
  {
    "@duration": 43,
    "@type": "importFinished"
  }
]
{% endhighlight %}

A test run has at least three events: an `importStarted` event, an `importFinished` event and one or more `functionalResult` or `performanceResult` events. Events are simple python dictionaries, and stored in the database as json objects.

* In line 3 is the `@testedAt` property. This is the moment the test was executed, in milliseconds after 1970-01-01 00:00:00 UTC.
* In line 4, 17 and 23 are the event types. There is always exactly one `importStarted` event, one `importFinished` event and one or more `functionalResult` or `performanceResult` events.
* Line 8 is the duration of this specific test
* Lines 9 to 15 is the hierarchy of the test. Tests are usually structured in some kind of tree, for example grouping suites, scenarios or test cases. In JUnit the tests are typically structured by package, class name and method name, but this can vary between test tools. The `@hierarchy` property is a list of strings, uniquely identifying a test and its place in relation to other tests. It is used to drill down on tests in reports, and identifying tests when comparing to previous runs of the same test.
* Line 16 is the test result. Typically this is either `PASSED` or `FAILED`, but more values are allowed.
* Line 19 is the error message of this failed test.
* Line 22 is the duration of the complete run

Additional properties can be added by tools if required, but the property names cannot start with either `@` or `_`. These are reserved for XL TestView use.

Results returned by the Python script in this line:

{% highlight python %}
result_holder.result = events
{% endhighlight %}

Where `events` is the list of events generated by the test result parser. `result_holder.result` is the return value of the script.

As explained in the previous section, the `files` parameter provides the files that will be imported and processed. The script should look similar to this:

{% highlight python %}
events = do_something_with_files(files)
result_holder.result = events
{% endhighlight %}

#### JUnit variants
JUnit has a loose format for test results used by a lot of tools. XL TestView offers several utilities to help parsing these variants.

The `parser.xunit` Python module contains useful functions for processing functional test tool results and ensuring that test results are structured in a way that XL TestView accepts. For example, `parse_last_modified` takes a list of files and extracts the timestamp from `xunit` test result files.

If you need to deviate from the default `parser.xunit` behavior, you can provide functions as parameters. This differs for each function. For example, in `xunit.py`:

{% highlight python %}
def parse_last_modified(files, extract_last_modified=extract_last_modified):
{% endhighlight %}

This indicates that you can override the behavior to extract the last modified date (from a file). If you look for that function in `xunit.py`, then you see:

{% highlight python %}
def extract_last_modified(file):
    root = ET.parse(file.getPath()).getroot()
    timestamp = root.attrib["timestamp"]
    return int(((datetime.strptime(timestamp, "%Y-%m-%dT%H:%M:%S") -
        datetime(1970,1,1)).total_seconds() * 1000))
{% endhighlight %}

This helps you see what the *method signature* and the *return value* should be.

#### Example: Changing default behavior for `xunit` importer

Assume you have test result files from an `xunit` tool in which the date/time format is different from usual. To write a test results parser for this, you need to override the default `extract_last_modified` function.

Start by copying `junit.py` and adding the function that extracts the date/time properly. The script will look like:

{% highlight python %}
from parser.xunit import validate_files, parse_last_modified, parse_junit_test_results

# our own version to extract date
def extract_last_modified_date_only(file):
    root = ET.parse(file.getPath()).getroot()
    timestamp = root.attrib["timestamp"]
    # my file format only has dates and no time!
    return int(((datetime.strptime(timestamp, "%Y-%m-%dT") - datetime(1970,1,1)).total_seconds() * 1000))

validate_files(files)

# here we pass it to the parse_last_modified function
last_modified = parse_last_modified(files, extract_last_modified=extract_last_modified_date_only)

if not test_run_historian.isKnownKey(str(last_modified)):
    events = parse_junit_test_results(testRunId, files, last_modified)
else:
    events = None

# Result holder should contain a list of test runs. A test run is a list of events

result_holder.result = [events] if events else []
{% endhighlight %}

### Writing a performance test results parser

**Note:** Performance result parsers will be changed in the upcoming releases, we will not guarantee backwards compatability at this time.

Writing a performance test results parser is much alike writing a functional test result parser. In this case, the useful functions are located in the `performance.parser` module.

## Logging

When a script uses the Python `print` statement, the output will be logged in `<XLTESTVIEW_HOME>/log/xl-testview.log` using a logger with the name of the test tool at `INFO` level. You can also use the SLF4J logger `logger` that is available in the script. For example:

{% highlight python %}
logger.error('Test results improperly formatted')
logger.warn('Test results parser did not find any useful information')
logger.info('Parsing the results took {} seconds', time)
{% endhighlight %}

This will log to a logger with the name of the test results parser.

## Dates
All dates in events are the number milliseconds from 1970-01-01 00:00:00 UTC. The test parser is responsible for any conversions of timezones. Many test tools do not report time zones in the test result files, but keep in mind that it is dangerous to assume that the test system and the XL TestView system are in the same time zone.

## Exceptions
Several things might go wrong while parsing test results. See how to [handle exceptions in parsers](handle-exceptions-in-parsers.html) for an explanation of the available exceptions and their use.