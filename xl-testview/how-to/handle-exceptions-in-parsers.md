---
title: Handle exceptions in parsers
categories:
- xl-testview
subject:
- TODO
tags:
---

When errors occur during parsing of test results, the parser is expected to throw exceptions which will be handled by XL Testview. The exceptions are java exceptions, which have to be imported.

### UnexpectedFormatException
This exception should be thrown if the parser encounters a file that is not parsable, because it is another format. For example, if a JUnit parser encounters a cucumber test result file.

#### Importing
{% highlight python %}
from com.xebialabs.xlt.plugin.api.resultparser import UnexpectedFormatException
{% endhighlight %}

#### Example of usage
{% highlight python %}
raise UnexpectedFormatException, "Canceled the import. Some files where not accepted by the test tool"
{% endhighlight %}

### MalformedInputException
This exception should be thrown if the file is of the correct type, but is malformed. For example, if in a JUnit test which failed, there is no failure message present.
 
#### Importing
{% highlight python %}
from com.xebialabs.xlt.plugin.api.resultparser import MalformedInputException
{% endhighlight %}

#### Example of usage
{% highlight python %}
raise MalformedInputException("Expected to have found a failure reason!")
{% endhighlight %}

### ImportFailedException
This exception should be thrown if something goes wrong, but no other exception is applicable.

#### Importing
{% highlight python %}
from com.xebialabs.xlt.plugin.api.resultparser import ImportFailedException
{% endhighlight %}

#### Example of usage
{% highlight python %}
raise ImportFailedException("Something went wrong!")
{% endhighlight %}