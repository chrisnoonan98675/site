---
layout: beta
title: Create a custom test result parser
categories:
- xl-testview
subject:
- Test tool
tags:
- system administration
- test tool
- extension
---

XL TestView supports a number of test tools out of the box. Many more tools are available. Some are open source, self build or proprietary. To support these tools, XL-TestView allows writing a custom test result parser so that you can support tools that are not (yet) supported out of the box. 

TODO: Insert plug here for possible cooperation with XebiaLabs to sell support? (like, we can help you with building a plugin for X money?)

A test result parser is a program that parses several test result files, and produces test results in a format that XL TestView can store in its database. 

XL TestView offers a flexible scripting interface. Parsers can be written in Python or Java.

In this how-to we will explain the steps required to create your own test result parser.

## General approach
In general the following steps need to be followed.

1. Determine the type of results produced by the test tool. XL TestView supports functional and performance test results
2. Define a new `test tool configuration` in the `synthetic.xml`. Here we have to decide wether the tool will produce functional or performance results and we define where we can find the actual `parser`.
3. Write an implementation of the test results parser
4. Test (and repeat from #2 if needed)

XL TestView contains a set of default tools. Those can be used as a basis for new test tools. The following table can help you select a good starting point:

{:.table .table-striped}
|Script|Tool|Type|Input format|
|------|----|----|------------|
|junit.py|JUnit|Functional|xml|
|cucumber.py|Cucumber|Functional|json|
|gatling.py|Gatling|Performance|csv & json|
|jmeter_csv.py|JMeter|Performance|csv|
|jmeter_xml.py|JMeter|Performance|xml|

### Determine type of results
XL-TestView currently supports `functional` and `performance` test results. More information about these can be found <a href="#">HERE THAT DOES NOT WORK YET</a>

Functional results have a `RESULT` (ie `PASSED` or `FAILED`, or any other you might want to support). They refer to a single `test case` or `scenario` (in case of feature based test tools, like for example Cucumber).

Performance results are summaries of the performance results from tests. 

If the tool you wish to support is not performance related, you would choose the functional test tool.

### Define a new `test tool configuration` in the `synthetic.xml`
Add an entry to the `synthetic.xml` file. This file is located in your `ext` directory.

Refer to the following snippet as inspiration:

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

The properties are explained below:

<table class="table table-striped">
	<thead>
		<tr>
			<th>Property name</th>
			<th>Required</th>
			<th>Default</th>
			<th>Description</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>type</td>
			<td>yes</td>
			<td>n/a</td>
			<td><p>A unique value that identifies your tool.
			
			It is recommended to prefix the value. XL-TestView prefixes tools with <strong>xlt</strong>. For example: <pre>xlt.SurefireJUnit</pre></p>
			<p>
			Be aware that there is a distinction between the <strong>format</strong> that a test tool should produce and the <strong>report generator</strong> of the build tool you're using. For example: if you're generating JUnit test results it depends on your build tool (maven, gradle, ant) and its respective <strong>generator</strong> (ie surefire) how the result file will look like. Reflecting these distinctions in the type name is recommended.</p>
			</td>
		</tr>
		<tr>
			<td>category</td>
			<td>yes</td>
			<td>n/a</td>
			<td><p>Specificies what kind of test results are generated.</p>
			Valid values are:
			<pre>functional</pre>
			functional results are results are related to test cases, scenario's, etc.
			<pre>performance</pre>
			performance results are related to performance tests, derived from summarized data.
			</td>
		</tr>
		<tr>
			<td>label</td>
			<td>yes</td>
			<td>n/a</td>
			<td><p>A unique, user friendly name for this test tool</p>
			</td>
		</tr>
		<tr>
			<td>defaultSearchPattern</td>
			<td>yes</td>
			<td>n/a</td>
			<td><p>An Ant style pattern to select relevant test result files. For example: <pre>**/test-results/TEST*.xml</pre> selects all files starting with <em>TEST</em> and ending with <em>.xml</em> that are in a directory <em>test-results</em>, which can be at any depth in the file tree.</p>
			</td>
		</tr>
		<tr>
			<td>scriptLocation</td>
			<td>Location of python script for parsing test results</td>
			<td>yes - if <pre>language</pre> is <pre>python</pre> or not specified</td>
			<td>n/a</td>
		</tr>
		<tr>
			<td>language</td>
			<td>yes</td>
			<td><pre>python</pre></td>
			<td><p>Tells XL-TestView in what language the parser is written.</p>
				<p>Possible values are:
					<pre>python</pre>
				</p>
			</td>
		</tr>
		<tr>
			<td>className</td>
			<td>yes - if <pre>language</pre> property is <pre>java</pre></td>
			<td>n/a</td>
			<td><p>If language is <strong>java</strong> it tells XL-TestView where the parser is located.</p>
			</td>
		</tr>
	</tbody>
</table>

XL-TestView uses the same type system as XL-Deploy. However, only the above fields are used for test tools. To acquire a better understanding about the type system in general please look at [Understanding XL Deploy rules](/xl-deploy/how-to/customize-an-existing-ci-type.html).

### Write an implementation of the test results parser

<blockquote>Depending on your language property you have to provide a python or java implementation. This chapter assumes a python implementation.</blockquote>

Using the search pattern from the test tool configuration the files to be imported will be assembled into one list and feeded into the test results parser.

The output of the test result parser is a collection of test runs. Each test run is itself a collection of test results. 

In pseudo-code this looks like:

    TestResultParser(files) -> [ [ test result1, test result2, ... ], ... ]

In many cases all test results that can be found belong to one test run. This is the case with for example JUnit and Cucumber. One 'run' of the test tool generates results. All these results belong to a `test run`.

Some tools build up a *history* of test runs, such as Gatling and FitNesse. This means that every *run* of the tool *adds* test result data (instead of replacing it with new data). This implies that the test result parser will be feeded with the latest and all previous test run data. It is up to the test result parser to deal with this case and distinguish already imported runs.

Apart from a list of `files`, the script is primed with the following information:

{:.table .table-striped}
|Attribute|Description|
|---------|-----------|
| `testRunId` | An ID identifying a test run. |
| `files` | The files found in the `workingDirectory` based on a search pattern provided in the test tool configuration. |
| `workingDirectory` | Directory there the test results have been stored. |
| `testSpecification` | The test specification that will own the test run. |
| `testRunHistorian` | This service can tell the script if results have been imported already. |
| `LOG` | A SLF4J logger you can use to log information at greater detail |


### The `python` functional test result parser
<blockquote>This is focussed on functional test tools.</blockquote>

For functional test tools, the Python module `parser.xunit` contains useful functions for processing test results and making sure the test results are structured in a way accepted by XL TestView.

Results returned by the python script are done via this obligated line:


	resultHolder.result = events


Where `events` at the right should be the list of events generated by the test result parser. `resultHolder.result` is basically the return value of the script.

As explained in the previous section the `files` param provides all the files that are to be imported and processed. Your script should roughly look like this:

	events = do_something_with_files(files)
	resultHolder.result = events

As said, the `parser.xunit` module contains methods you can use to do various things. For example `parse_last_modified` takes a list of files and extracts the timestamp from the `xunit` test result files. 

If you ever need to deviate from the default behavior you can provide functions as parameters. This differs for each function you want to use, look into the `xunit.py`, for example:


	def parse_last_modified(files, extract_last_modified=extract_last_modified):


This indicates that we can override the behavior to extract the last modified date (from a file). If we look for that function in `xunit.py` then we see:

	def extract_last_modified(file):
	    root = ET.parse(file.getPath()).getroot()
	    timestamp = root.attrib["timestamp"]
	    return int(((datetime.strptime(timestamp, "%Y-%m-%dT%H:%M:%S") - datetime(1970,1,1)).total_seconds() * 1000))


This gives us a clue how the *method signature* and the *return value* should be. 

### Example: Changing default behavior for `xunit` importer
Lets say we have test result files from an `xunit` tool where the date-time format is different then usual. In order to write a test result parser for this we have to override the default `extract_last_modified` function. 

We start by copying the `junit.py` and add the function that extracts the date-time properly. The script will look as following:

	
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
	
	resultHolder.result = [events] if events else []



## Writing a performance test result parser

Writing a performance test result parser is much alike writing a functional test result parser. In this case the useful functions are located in module `performance.parser`.

## Logging

When a script uses the python 'print' statement the output will be logged in the `<XLTESTVIEW_HOME>/log/xl-testview.log` using a logger with the name of the test tool at `INFO` level. You can also use the SLF4J logger `LOG` that is availble in the script e.g.:

```
   LOG.error('Test results improperly formatted')
   LOG.warn('Test result parser did not find any usefull information')
   LOG.info('Parsing the results took {} seconds', time)
```

This logger will log to a logger with the name of the test result parser.
