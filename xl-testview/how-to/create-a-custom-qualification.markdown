---
title: Create a custom qualification in XL TestView
categories:
- xl-testview
subject:
- Qualifications
tags:
- qualification
---

XL TestView can evaluate test results and qualify them to determine whether a test run as a whole passed or failed. You can use this qualification to make an automated go/no-go decision in a continuous integration tool such as Jenkins or in a pipeline orchestrator such as Go or XL Release.

XL TestView also supports custom qualifications. To create a custom qualification, you:

1. Configure the qualification in `synthetic.xml`. This file defines [configuration item (CI) types](/xl-deploy/concept/key-xl-deploy-concepts.html#type-system) such as qualifiers and reports. 
1. Write a script for the qualification. This is done in Python.

The easiest way to start creating a custom qualifier is to copy a default qualifier. This example shows how to create a custom qualifier based on the default functional qualifier.

For more information about features such as qualification, refer to [Key XL TestView concepts](/xl-testview/concept/key-concepts.html).

## Configure the qualifier in `synthetic.xml`

First, add a custom qualifier type to `<XLTESTVIEW_HOME>/ext/synthetic.xml`:

1. Copy a `type` element with attribute `type="xlt.DefaultFunctionalTestsQualifier"` from `<XLTESTVIEW_HOME>/plugins/demo/synthetic.xml` and add it to `<XLTESTVIEW_HOME>/ext/synthetic.xml`. For example:

        <type type="xlt.DefaultFunctionalTestsQualifier" extends="generic.Qualification">
            <property name="scriptLocation" default="qualification/functional.py"/>
        </type> 

1. Change the `type` attribute to your desired prefix and name; for example, `type="myCompany.myFunctionalQualifier"`.
1. Change the `scriptLocation` to the report script that you will create; for example, `myFunctionalQualifier.py`.

    The result will look like:

        <type type="myCompany.myFunctionalQualifier" extends="generic.Qualification">
            <property name="scriptLocation" default="mycompany/myFunctionalQualifier.py"/>
        </type>

1. Save `synthetic.xml` and restart XL TestView.
1. All changes made to `synthetic.xml` require you to restart XL TestView.
1. To verify that your changes took effect:
    1. Click **Test specifications** in the navigation bar.
    1. Locate a functional test specification and click **Edit**.
    1. Look at the options for the **Qualification Type**. Your custom qualifier should appear here.

        ![Qualification Type with new Qualification](images/sample-qualification-dropdown.png)

## Write the script

Next, copy `<XLTESTVIEW_HOME>/plugins/demo/functional/qualification.py` to `ext/mycompany/myFunctionalQualifier.py`. You can implement the logic that you want to use for qualification in this script. (You do not have to restart XL TestView after changing scripts.)

The default functional qualifier requires all tests to pass for the test run to qualify as passed. This example changes the logic so that only tests with a name that includes the word "Critical" must pass for the test run to qualify as passed. If a test name does not contain the word "Critical", then a failure of that test will not cause the test run to qualify as failed.

This requires changing the `result` dictionary:

{% highlight python %}

    result = {}
{% endhighlight %}

The `success` key must be generated in your qualifier. It can have a value of `True` (for passing/successful qualifications) or `False` (for failing qualifications). You can optionally add a `reason` with text that describes why the test run qualified as passed or failed. Once this is populated we will call `result_holder.result = my_result` at the end of the script to submit the qualification result.

The sample qualifier below:

1. Sets `success` to `True`
1. Loops over events
    1. If an event is a functional result
        1. Check if it is a failure
            1. If it is a failure, set the qualification to `False`
            1. Set the reason
1. Return the result

As an example, let's create a qualification that only checks critical tests. Critical tests can be identified, because they live in a package (suite) named "Critical".

First, to check if the test is in a "Critical" package (suite):

{% highlight python %}
    if ... and 'Critical' in event.hierarchy:
{% endhighlight %}

This code retrieves the `hierarchy` property of the test, `p.hierarchy`. It checks if the hierarchy (a list) contains a field named "Critical".

The complete `myFunctionalQualifier.py` script is now:

{% highlight python %}
    result = {}

    if events:
        result = {'success': True}
        failures = filter(lambda ev: ev.type == 'functionalResult' and ev.result != 'PASSED' and 'Critical' in ev.hierarchy, events)
        if failures:
            result['success'] = False
            result['reason'] = '%d test%s failed' % (len(failures), len(failures) > 1 and 's' or '')

    result_holder.result = result
{% endhighlight %}

This example also includes a new value for the `reason` field.
