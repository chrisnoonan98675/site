---
title: Logging in XL Release
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- logging
- audit
- security
weight: 492
---

By default, XL Release server writes informational, warning, and error log messages to standard output and to `XL_RELEASE_SERVER_HOME/log/xl-release.log` when it is running.

In addition, XL Release writes an audit trail to `XL_RELEASE_SERVER_HOME/log/audit.log` and access logging to `XL_RELEASE_SERVER_HOME/log/access.log`.

## Changing logging behavior

It is possible to change the logging behavior (for example, to write log output to a file or to log output from a specific source).

XL Release uses [Logback](http://logback.qos.ch/) as logging technology. The Logback configuration is stored in `XL_RELEASE_SERVER_HOME/conf/logback.xml`.

For detailed information about the `logback.xml` file, refer to the [Logback documentation](http://logback.qos.ch/manual/).

This is a sample `logback.xml` file:

{% highlight xml %}
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!-- encoders are assigned the type
             ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
        <encoder>
              <pattern>
                %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
            </pattern>
        </encoder>
    </appender>

    <!-- Create a file appender that writes log messages to a file -->
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>%-4relative [%thread] %-5level %class - %msg%n</pattern>
        </layout>
           <File>log/my.log</File>
    </appender>

    <!-- Set logging of classes in com.xebialabs to INFO level -->
    <logger name="com.xebialabs" level="info"/>

    <!-- Set logging of class HttpClient to DEBUG level -->
    <logger name="HttpClient" level="debug"/>

    <!-- Set the logging of all other classes to INFO -->
    <root level="info">
        <!-- Write logging to STDOUT and FILE appenders -->
        <appender-ref ref="STDOUT" />
        <appender-ref ref="FILE" />
    </root>

</configuration>
{% endhighlight %}

## The audit log

<div class="alert alert-warning" style="width: 60%">
<strong>Note:</strong> The audit log is not available in XL Release 7.5.0.
</div>

XL Release keeps an audit log of each human-initiated event on the server, which complements the auditing provided by the [release activity logs](/xl-release/concept/release-activity-logs.html) (which track activity for each release at a more domain-specific level of granularity).

Some of the events that are logged in the audit trail are:

* The system is started or stopped
* An application is imported
* A CI is created, updated, moved, or deleted
* A security role is created, updated, or deleted
* A task is started, cancelled, or aborted

For each event, the following information is recorded:

* The user making the request
* The event timestamp
* The component producing the event
* An informational message describing the event

For events involving configuration items (CIs), the CI data submitted as part of the event is logged in XML format.

By default, the audit log is stored in `XL_RELEASE_SERVER_HOME/log/audit.log` and is rolled over daily.

### Enable audit logging

You can enable low-level audit logging by changing the log level of the `audit` logger in `XL_RELEASE_SERVER_HOME/conf/logback.xml`:

{% highlight xml %}
<!-- set to "info" to enable low-level audit logging -->
<logger name="audit" level="off" additivity="false">
    <appender-ref ref="AUDIT" />
</logger>
{% endhighlight %}

By default, the log stream is stored in `XL_RELEASE_SERVER_HOME/log/audit.log`. You can change this location, the file rolling policy, and so on by changing the configuration of the `AUDIT` appender. You can also pipe the log stream to additional sinks (such as [syslog](http://logback.qos.ch/manual/appenders.html#SyslogAppender)) by configuring additional appenders. Refer to the [Logback documentation](http://logback.qos.ch/manual/) for details.

This is an example of the audit stream with the level of the audit logger set to `info`:

	2014-11-22 11:24:18.764 [audit.system] system - Started
	2014-11-22 11:25:18.125 [audit.repository] admin - Created CIs [Configuration/Custom/Configuration1099418]
	2014-11-22 11:25:18.274 [audit.repository] admin - CI [Configuration/Custom/Configuration1099418]:
	<jenkins.Server id="Configuration/Custom/Configuration1099418" created-by="admin" created-at="2014-11-22T11:25:16.255-0500" last-modified-by="admin" last-modified-at="2014-11-22T11:25:16.255-0500">
	  <title>My Jenkins</title>
	  <url>http://localhost/foo</url>
	  <username>foo</username>
	  <password>{b64}C7JZetqurQo2B8x2V8qUhg==</password>
	</jenkins.Server>

## The access log

Access logging allows you to see all HTTP requests received by XL Release with the following details: URL, the time it took to process, username, the IP address where the request comes from, and the response status code. This logging data can be used to analyze the usage of XL Release and to troubleshoot.

The access log is written to `XL_RELEASE_SERVER_HOME/log/access.log`.
