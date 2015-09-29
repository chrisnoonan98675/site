---
title: Handle exceptions in test result parsers
categories:
- xl-testview
subject:
- Extensibility
tags:
- extension
- test result parsers
- api
- import
since:
- XL TestView 1.3.0
---

If a [custom test results parser](/xl-testview/how-to/create-a-custom-test-results-parser.html) is used, XL TestView expects the parser to throw exceptions that XL TestView will then handle. These are Java exceptions that have to be imported.

## `UnexpectedFormatException`

`UnexpectedFormatException` should be thrown if the parser encounters a file that is not parseable because it is in another format. For example, if a JUnit parser encounters a Cucumber test result file.

### Importing `UnexpectedFormatException`

{% highlight python %}
from com.xebialabs.xlt.plugin.api.resultparser import UnexpectedFormatException
{% endhighlight %}

### Example of `UnexpectedFormatException` usage

{% highlight python %}
raise UnexpectedFormatException, "Canceled the import. Some files where not accepted by the test tool"
{% endhighlight %}

## `MalformedInputException`

`MalformedInputException` should be thrown if the file is of the correct type, but is malformed. For example, if there is no failure message present in a JUnit test that failed.
 
### Importing `MalformedInputException`

{% highlight python %}
from com.xebialabs.xlt.plugin.api.resultparser import MalformedInputException
{% endhighlight %}

### Example of `MalformedInputException` usage

{% highlight python %}
raise MalformedInputException("Expected to have found a failure reason!")
{% endhighlight %}

## `ImportFailedException`

`ImportFailedException` should be thrown if something goes wrong, but no other exception applies.

### Importing `ImportFailedException`

{% highlight python %}
from com.xebialabs.xlt.plugin.api.resultparser import ImportFailedException
{% endhighlight %}

### Example of `ImportFailedException` usage

{% highlight python %}
raise ImportFailedException("Something went wrong!")
{% endhighlight %}
