---
title: Support legacy use of XLRequest using HttpRequest
categories:
- xl-release
subject: Connectivity
tags:
- webhook
- script
- custom task
- http
---

Prior to XL Release 4.0.10, HTTP requests from XL Release to target systems—whether via webhooks, scripts or custom tasks—were executed via Jython's [`httplib`](http://www.jython.org/docs/library/httplib.html) implementation and thus ultimately by the standard Java networking stack. This was fine in most situations, but made it hard or impossible to handle certain advanced use cases such as complex proxy setups in a user-accessible way: the configuration that would have to be changed was part of Jython's internals.

To allow users to handle such cases and generally have as much control as possible over the HTTP stack for calls to target systems, XL Release 4.0.10 moved to [Apache HTTP Components](https://hc.apache.org/) and added convenience wrappers called `HttpRequest` and `HttpResponse`. These will replace the previous convenience classes `XLRequest` and `XLResponse` (basically thin wrappers around [`httplib.HTTPConnection`](http://www.jython.org/docs/library/httplib.html#httpconnection-objects) and [`httplib.HttpResponse`](http://www.jython.org/docs/library/httplib.html#httpconnection-objects)), which are now deprecated.

The translation from using `XLRequest`/`XLResponse` to `HttpRequest`/`HttpResponse` is described in the documentation [here](/xl-release/latest/upgrademanual.html#updating-calls-to-xlrequest). Still, if you are using `XLRequest` in many places in your script and custom tasks, updating all of these may not be something you want to tackle right now.

`XLRequest` and `XLResponse` are still included in XL Release, but will be removed in a future release. In that case, you can give yourself and your users a bit more time by "simulating" `XLRequest` and `XLResponse` using the new `HttpRequest` and `HttpResponse` classes. That way, you initially only have to take care of these two simple "adapters", which you can remove when you no longer need `XLRequest` and `XLResponse`.

This is an example of creating adapters in an XL Release 4.0.10 installation.

## Extract `XLRequest` and `XLResponse` to the extensions directory

First, create the directory `SERVER_HOME/ext/pythonutil` if it does not already exist. Then, extract the `XLRequest.py` and `XLResponse.py` classes, which are in `SERVER_HOME/lib/xl-release-server-<version>.jar`, into that directory. An easy way to do this is to copy the JAR into a temporary directory, open it using a ZIP file browser (change the file extension to `.zip` if necessary), and extract the two files from the `pythonutil` folder. Copy the extracted files to `SERVER_HOME/ext/pythonutil`, where they will override the provided `XLRequest.py` and `XLResponse.py` classes in the JAR file.

![Extract XLRequest.py and XLResponse.py](../images/extract-xlreq-xlresp.png)

Obviously, you can only extract `XLRequest.py` and `XLResponse.py` if you have access to an XL Release version (such as 4.0.10) that still includes these. If you are running a version that no longer provides these, please download an older version from the XebiaLabs download site or [contact our support team](http://support.xebialabs.com/).

## Modify `XLRequest` and `XLResponse` to use `HttpRequest` and `HttpResponse`

Now, you can update `XLRequest` and `XLResponse` so they delegate to `HttpRequest` and `HttpResponse`, respectively.

### Modifying `XLReponse` to use `HttpResponse`

Because the HTTP calls made through the `XLRequest` adapter will now actually return `HttpResponse` objects, you need to modify XLResponse to handle those instead of the standard Jython `httplib` response objects it works with by default. Because `XLResponse` actually does not do very much, and `HttpResponse` provides all the information you need, the required change is easy.

    class XLResponse:

    def __init__(self, httpResponse):
        self.httpResponse = httpResponse
        self.status = self.httpResponse.getStatus()

    def isSuccessful(self):
        return self.status >= 200 and self.status < 400

    def errorDump(self):
        # this is not *exactly* the same as the original XLResponse.errorDump()
        # since "reason" is not available...but the raw error is more detailed
        print 'Status: %s\n' % self.status
        print 'Reason: <unavailable>\n'
        print 'Raw error:\n'
        print self.httpResponse.errorDump()

    def read(self):
        return self.httpResponse.getResponse()

**Note:** This assumes that you are not creating XLResponse objects yourself in your scripts or custom tasks, but are only using objects returned by `XLRequest(...).send()` calls. If you are creating `XLResponse` objects somewhere, you could:

1. Extract the provided `XLResponse.py` class into `SERVER_HOME/ext/pythonutil` and leave it unchanged, so your code can create XLResponse objects as before
1. Create a class `XLResponse2` with the code example for `XLResponse` above
1. Use `XLResponse2` instead of `XLResponse` in the code example for `XLRequest` below

### Modifying `XLRequest` to use `HttpRequest`

With `XLRequest`, the basic pattern is:

    response = XLRequest(url, method, content, username, password, content_type).send()

In other words, all arguments are passed to the XLRequest constructor. `HttpRequest` works a differently: only the URL and credentials are passed to the constructor, while the remaining arguments are passed to the doRequest method that is `HttpRequest`'s version of the `XLRequest.send()` method.

    class XLRequest:

    def __init__(self, url, method, body, username, password, content_type):
        self.httpRequest = HttpRequest({'url': url}, username, password)
        # need these later to invoke HttpRequest.doRequest
        self.method = method
        self.body = body
        self.content_type = content_type
        # print 'DEBUG: Created HttpRequest', self.httpRequest, '\n'

    def send(self):
        # using the URL set in self.httpRequest
        httpResponse = self.httpRequest.doRequest(
            method=self.method,
            context='',
            body=self.body,
            contentType=self.content_type)
        # print 'DEBUG: Received HttpResponse', httpResponse, '\n'
        return XLResponse(httpResponse)

## Verify that the adapter classes are being called correctly

Now you can verify that, when you use `XLRequest` and `XLResponse` in a script or custom task, you are actually calling the adapters you just created. A quick way to do this is to uncomment the "DEBUG" print statements in the new `XLRequest` and `XLResponse` classes, create a simple script task (or an instance of any of your custom task types that uses `XLRequest` and `XLResponse`), and run it.

![Test script](../images/test-xlrequest-script-task.png)

![Test script output](../images/test-xlrequest-output.png)

[This sample template](sample-scripts/Test_XLRequest_adapter-template.xlr) does exactly that. Also, see [Test an automated task during configuration](test-an-automated-task-during-configuration.html) for more tips on tweaking and debugging tasks.

**Note:** `HttpRequest` and `HttpResponse`, and `XLRequest` and `XLResponse` before them, are intended as convenient helper classes, nothing more. XL Release certainly does not require that you use them in your scripts or custom tasks: if you'd rather work directly with [`httplib`](http://www.jython.org/docs/library/httplib.html) or [`java.net`](http://docs.oracle.com/javase/tutorial/networking/), you can.

**Important:** The code here is sample code only that is not officially supported by XebiaLabs. If you have questions, please [contact our support team](http://support.xebialabs.com/).
