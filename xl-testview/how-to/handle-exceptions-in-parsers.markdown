---
title: Handle exceptions in test result parsers
categories:
- xl-testview
subject:
- Test results
tags:
- extension
since:
- XL TestView 1.3.0
---

When errors occur during parsing of test results, the parser is expected to throw exceptions which will be handled by XL TestView. The exceptions are Java exceptions, which have to be imported.

## `UnexpectedFormatException`

`UnexpectedFormatException` should be thrown if the parser encounters a file that is not parseable because it is another format. For example, if a JUnit parser encounters a Cucumber test result file.

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

`ImportFailedException` exception should be thrown if something goes wrong, but no other exception applies.

### Importing `ImportFailedException`

{% highlight python %}
from com.xebialabs.xlt.plugin.api.resultparser import ImportFailedException
{% endhighlight %}

### Example of `ImportFailedException` usage

{% highlight python %}
raise ImportFailedException("Something went wrong!")
{% endhighlight %}
