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

TODO: make this a more tutorial like.

XL TestView supports a number of test tools out of the box, but there are many other tools available. Some are open source, self build or proprietary. To support these tools, it is possible to define new test tool configurations. The processing of test results is done by a test result parser.

A test result parser is essentially a program that parses several test files, and produces test results in a format that XL TestView can store in its database. XL TestView offers a flexibale scripting interface. Users can write implementations in Python or Java that will provide the logic that is specific for that test tool.

## Writing a new Test Tool
The following steps are required for a new test tool:

1. Determine the type of results produced by the test tool. XL TestView supports functional and performance test results
2. Write the implementation
3. Add an entry to a synthetic.xml file.

XL TestView contains a set of default tools. Those can be used as a basis for new test tools. The following table can help you select a good starting point:

{:.table .table-striped}
|Script|Tool|Type|Input format|
|------|----|----|------------|
|junit.py|JUnit|Functional|xml|
|cucumber.py|Cucumber|Functional|json|
|gatling.py|Gatling|Performance|csv & json|
|jmeter_csv.py|JMeter|Performance|csv|
|jmeter_xml.py|JMeter|Performance|xml|

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



