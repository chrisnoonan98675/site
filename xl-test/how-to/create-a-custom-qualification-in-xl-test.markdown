---
layout: beta
title: Create a custom qualification in XL Test
categories:
- xl-test
subject:
- Reports
tags:
- report
- script
- python
- json
- freemarker
- qualification
---

XL Test can evaluate test results and qualify them to determine whether a test run as a whole passed or failed. You can use this qualification to make an automated go/no-go decision in a continuous integration tool such as Jenkins or in a pipeline orchestrator such as Go or XL Release.

XL Test also supports custom qualifications. To create a custom qualification, you:

1. Configure the qualification in `synthetic.xml`. This file defines [configuration item (CI) types](/xl-deploy/concept/key-xl-deploy-concepts.html#type-system) such as qualifiers and reports. 
1. Write a script for the qualification. This is done in Python.

The easiest way to start creating a custom qualifier is to copy a default qualifier. This example shows how to create a custom qualifier based on the default functional qualifier.

For more information about features such as qualification, refer to [Key XL Test concepts](/xl-test/concept/key-xl-test-concepts.html).

## Configure the qualifier in `synthetic.xml`

First, add a custom qualifier type to `<XLTEST_HOME>/ext/synthetic.xml`:

1. Copy a `type` element with attribute `type="xltest.DefaultFunctionalTestsQualifier"` from `<XLTEST_HOME>/plugins/demo/synthetic.xml` and add it to `<XLTEST_HOME>/ext/synthetic.xml`. For example:

        <type type="xltest.DefaultFunctionalTestsQualifier" extends="generic.Qualification">
            <property name="scriptLocation" default="functional/qualification.py"/>
        </type> 

1. Change the `type` attribute to your desired prefix and name; for example, `type="myCompany.myFunctionalQualifier"`.
1. Change the `scriptLocation` to the report script that you will create; for example, `myFunctionalQualifier.py`.

    The result will look like:

        <type type="myCompany.myFunctionalQualifier" extends="generic.Qualification">
            <property name="scriptLocation" default="functional/myFunctionalQualifier.py"/>
        </type>

1. Save `synthetic.xml` and restart XL Test. (All changes made to `synthetic.xml` require you to restart XL Test.)
1. To verify that your changes took effect:
    1. Click **Test specifications** in the navigation bar.
    1. Locate a functional test specification and click **Edit**.
    1. Look at the options for the **Qualification Type**. Your custom qualifier should appear here.

        ![Qualification Type with new Qualification](images/sample-qualification-dropdown.png)

## Write the script

Next, copy `<XLTEST_HOME>/plugins/demo/functional/qualification.py` to `ext/functional/myFunctionalQualifier.py`. You can implement the logic that you want to use for qualification in this script. (You do not have to restart XL Test after changing scripts.)

The default functional qualifier requires all tests to pass for the test run to qualify as passed. This example changes the logic so that only tests with a name that includes the word "Critical" must pass for the test run to qualify as passed. If a test name does not contain the word "Critical", then a failure of that test will not cause the test run to qualify as failed.

This requires changing the `result` dictionary:

    result = {}

The `success` key must be generated in your qualifier. It can have a value of `True` (for passing/successful qualifications) or `False` (for failing qualifications). You can optionally add a `reason` with text that describes why the test run qualified as passed or failed.

The sample qualifier below:

1. Sets `success` to `True`
1. Loops over events
    1. If an event is a functional result
        1. Check if it is a failure
            1. If it is a failure, set the qualification to `False`
            1. Set the reason
1. Return the result

First, to check if the test name includes "Critical":

    if 'CRITICAL' in p.get('name').upper():

This code retrieves the `name` property of the test, `p.get('name')`. It then uses the `String` method to make the name uppercase (`.upper()`). After this is done, it checks if the text `'CRITICAL'` is in the name. 

The complete `myFunctionalQualifier.py` script is now:

	result = {}

	def successCounters(p):
	return p.get('result') is None and p.get('wrong') == 0 and p.get('exceptions') == 0

	if events:
		result = {'success': True}
		success = True
		for ev in events:
			if ev.type == 'functionalResult':
			p = ev.getProperties()
			# print 'looking at functionalResult event with these props:', p
				if 'CRITICAL' in p.get('name').upper():
					if not (successCounters(p) or (p.get('result') == 'PASSED')):
						result['success'] = False
						result['reason'] = 'There is at least one failure with a CRITICAL test'
						break

	resultHolder.setResult(result)

This example also includes a new value for the `reason` field.
