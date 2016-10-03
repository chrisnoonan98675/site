---
title: Logging in XL Deploy
---

By default, the XL Deploy server writes informational, warning, and error log messages to standard output and to `XL_DEPLOY_SERVER_HOME/log/deployit.log` when it is running. In addition, XL Deploy:

* Writes an audit trail to the `XL_DEPLOY_SERVER_HOME/log/audit.log` file
* Writes an HTTP log to the `XL_DEPLOY_SERVER_HOME/log/access.log` file
* Can optionally log scripts in the `XL_DEPLOY_SERVER_HOME/log/scripts.log` file

## The audit log

XL Deploy writes an audit log for each human-initiated event on the server. Some of the events that are logged in the audit trail are:

* The system is started or stopped
* A user logs into or out of the system
* An application is imported
* A CI is created, updated, moved, or deleted
* A security role is created, updated, or deleted
* A task (deployment, undeployment, control task, or discovery) is started, cancelled, or aborted

For each event, the following information is recorded:

* The user making the request
* The event timestamp
* The component producing the event
* An informational message describing the event

For events involving configuration items (CIs), the CI data submitted as part of the event is logged in XML format.

By default, the audit log is stored in `XL_DEPLOY_SERVER_HOME/log/audit.log` and is rolled over daily.

### Configure audit logging

It is possible to change the logging behavior (for example, to write log output to a file or to log output from a specific source). To do so, edit the `XL_DEPLOY_SERVER_HOME/conf/logback.xml` file. This is a sample `logback.xml` file:

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

    <!-- Set logging of classes in com.xebialabs to DEBUG level -->
    <logger name="com.xebialabs" level="debug"/>

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

### Configure HTTP access logging

You can change the HTTP access logging behavior in the `XL_DEPLOY_SERVER_HOME/conf/logback-access.xml` file. The format is slightly different from the `logback.xml` format.

By default, the access log is done in the so-called combined format, but you can fully customize it. The log file is rolled per day on the first log statement in the new day.

This is a sample `logback-access.xml` file:

{% highlight xml %}
<configuration>
  <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>log/access.log</file>
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
      <fileNamePattern>log/access.%d{yyyy-MM-dd}.log.zip</fileNamePattern>
    </rollingPolicy>

    <encoder>
      <pattern>%h %l %u [%t] "%r" %s %b "%i{Referer}" "%i{User-Agent}"</pattern>
    </encoder>
  </appender>

  <appender-ref ref="FILE" />
</configuration>
{% endhighlight %}

For information about the configuration and possible patterns, refer to:

* [HTTP-access logs with logback-access, Jetty and Tomcat](http://logback.qos.ch/access.html)
* [`PatternLayout`](http://logback.qos.ch/manual/layouts.html#AccessPatternLayout)

To disable the HTTP access log, create a `logback-access.xml` file with an empty `configuration` element:

{% highlight xml %}
<configuration>
</configuration>
{% endhighlight %}

## Enable the script log

The `logback.xml` file contains a section that allows you to enable logging of all XL Deploy scripts to a separate log file called `XL_DEPLOY_SERVER_HOME/log/scripts.log`. By default, this section is commented out.

**Important:** The scripts contain base64-encoded passwords. Therefore, if script logging is enabled, anyone with access to the server can read those passwords.

Logging is configured in the `XL_DEPLOY_SERVER_HOME/conf/logback.xml` file. To enable debug mode, change the following setting:

{% highlight xml %}
<root level="debug">
	...
</root>
{% endhighlight %}

If this results in too much logging, you can tailor logging for specific packages by adding log level definitions for them. For example:

{% highlight xml %}
<logger name="com.xebialabs" level="info" />
{% endhighlight %}

You must restart the server to activate the new log settings.

See the [Logback documentation](http://logback.qos.ch/) for more information.
