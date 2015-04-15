---
layout: beta
title: Create a custom qualifier in XL Test
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

XL Test generates qualifications using Python scripts using the same basic framework as graphical report creation. 

## Create a new custom qualifier

Creating a custom qualifier involves two steps:

1. Configuring the report in `synthetic.xml`
1. Writing a script for the report

The easiest way to start creating a custom qualifier is to copy a built-in qualifier. This example shows how to create a custom qualifier based on the built-in Default Functional Qualifier.

### Configure the report in `synthetic.xml`

First, add a custom qualification type to `<XLTEST_HOME>/ext/synthetic.xml`:

1. Copy a `type` element with attribute `type="xltest.DefaultFunctionalTestsQualifier"` from `<XLTEST_HOME>/plugins/demo/synthetic.xml`:

        <type type="xltest.DefaultFunctionalTestsQualifier" extends="generic.Qualification">
            <property name="scriptLocation" default="functional/qualification.py"/>
        </type> 

1. Change the `type` attribute to use your desired prefix and name; for example, `type="myCompany.myFunctionalQualifier"`.
1. Change the `scriptLocation` to the report script that you will create; for example, `myFunctionalQualifier.py`.

    The result will look like:

        <type type="myCompany.myFunctionalQualifier" extends="generic.Qualification">
            <property name="scriptLocation" default="functional/myFunctionalQualifier.py"/>
        </type>

1. Save `synthetic.xml` and restart XL Test. All changes made to `synthetic.xml` require a restart.
1. To verify that your changes took effect, click `Test specifications` in the top menu, then click `edit` for a Functional Test Specification. It should show your qualification in the `Qualification Type` dropdown:

    ![Qualification Type with new Qualification](images/sample-qualification-dropdown.png)

## Write the script

Next, copy `<XLTEST_HOME>\plugins\demo\functional/qualification.py` to `functional/myFunctionalQualifier.py` in `ext`. Changes to the script do not require you to restart XL Test.

We can now begin to implement the logic we want in the qalification. The Default Functional Qualifier currently requires all test to pass in order for qualification to pass. In this example we will change this so that only tests which have the word "Critical" in their test name have to pass. If it does not have the word "Critical" in the test name then a failure of that test will not cause qualification to fail.

The output we are going to adjust here is the `result` dictionary, we can see where this is instanciated in line 2:

        result = {}

There is a mandatory entry we need to generate in our qualification, that is the `success` key, which can have a value of `True` or `False`. We can also optionally add a `reason` which we can set to text to describe why the qualification is true or false.

Before we start to edit this qualifier, let's take a look at what it does.

1. It sets the `success` to True
1. Loops over events
    1. If an event is a functional result
        1. Check if it's a failure
            1. If it is set the qualification to false
            1. Set the reason
1. Return the result.

We are going to check the name of the test as part of this flow, right before we check if the test is a failure. The line to do this is as follows:

        if 'CRITICAL' in p.get('name').upper():

This new if statement retrieves the `name` property of the test, `p.get('name')`. Then uses the String method to make this upper case `.upper()`. Once this is done we check to see if the text `'CRITICAL'` is in the name. Our whole file now looks as follows, notice the new if statement:

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

I also changed the value of the `reason` field. This is a basic example but you can perform more complex logic in here to fit your requirements.
