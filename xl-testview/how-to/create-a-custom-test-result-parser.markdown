---
layout: beta
title: Create a custom test result parser
categories:
- xl-testview
subject:
- System administration
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
			<td>defaultSearchPattern</td>
			<td>yes</td>
			<td>n/a</td>
			<td><p>Ant pattern used for retrieving the applicable test result files for a given directory. Propagated in the GUI and Jenkins Plugin.</p>
			<p>
			Example, for JUnit test results the following pattern is used:
			<pre>**/*.xml	</pre>
			</p></td>
		</tr>
		<tr>
			<td>scriptLocation</td>
			<td>Location of python script for parsing test results</td>
			<td>yes - if <pre>language</pre> is <pre>python</pre> or not specified</td>
			<td>n/a</td>
		</tr>
		<tr>
			<td>language</td>
			<td>no</td>
			<td><pre>python</pre></td>
			<td><p>Tells XL-TestView in what language the parser is written.</p>
				<p>Possible values are:
					<pre>python</pre>
					<pre>java</pre>
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

## Writing a new Test Tool

1. Determine the type of results produced by the test tool. XL TestView supports functional and performance test results
2. Write the implementation
3. Add an entry to a synthetic.xml file.


## Writing a test result parser

A (Python) script is required to do the actual parsing.

As input XL TestView provides the files that contain test results, the output is a collection of test runs. Each test run is itself a collection of test results. In pseudo-code:

    TestResultParser(files) -> [ [ test result1, test result2, ... ], ... ]
   
In many cases all test results that can be found belong to one test run. This is the case with for example JUnit and Cucumber. Some tools build up a history of test runs, such as Gatling and FitNesse. It is up to the test result parser implementor to be aware of this distinction.

Apart from a list of `files`, the script is primes with the following information:

{:.table .table-striped}
|Attribute|Description|
|---------|-----------|
| `testRunId` | An ID identifying a test run. |
| `files` | The files found in the `workingDirectory` based on a search pattern provided in the test tool configuration. |
| `workingDirectory` | Directory there the test results have been stored. |
| `testSpecification` | The test specification that will own the test run. |
| `testRunHistorian` | This service can tell the script if results have been imported already. |

You also need an addition to the `synthetic.xml`:

    <type type="custom.MyTestToolConfiguration"
          extends="xlt.TestToolConfiguration"
          label="My custom tool">
        <property name="category" default="functional"/>
        <property name="defaultSearchPattern" default=""/>
        <property name="scriptLocation" default="custom-script.py"/>
    </type>

TODO: make remark on type namespace prefix and class path namespacing.

The following values need to be specified:

{:.table .table-striped}
|Attribute|Description|
|---------|-----------|
| `type` | A unique typename for this test Tool. |
| `label` | A unique, user friendly name for this test tool. |
| `category` | `functional` or `performance`. |
| `defaultSearchPattern` | A Ant style pattern to select relevant test result files. For example: `**/test-results/TEST*.xml` selects all files starting with `TEST` and ending with `.xml` that are in a directory `test-results`, which can be at any depth in the file tree. |
| `scriptLocation` | The name of the script. |

XL-TestView uses the same type system as XL-Deploy. However, only the above fields are used for test tools. To acquire a better understanding about the type system in general please look at [Understanding XL Deploy rules](/xl-deploy/how-to/customize-an-existing-ci-type.html).



TODO: example parse flow


## Writing a functional test result parser

For functional test tools, the Python module `functional.parser` contains useful functions for processing test results and making sure the test results are structured in a way accepted by XL TestView.

   
## Writing a performance test result parser

Writing a performance test result parser is much alike writing a functional test result parser. In this case the useful functions are located in module `performance.parser`.



