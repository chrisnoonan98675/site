---
title: Using XL Deploy logging
categories:
- xl-deploy
subject:
- Logging
tags:
- logging
- system administration
- script
- security
---

By default, the XL Deploy server writes informational, warning, and error log messages to standard output and `log/deployit.log` when running. In addition, XL Deploy:

* Writes an audit trail to the `log/audit.log` file
* Writes an HTTP log to the `log/access.log` file
* Can optionally log scripts in the `log/scripts.log` file

You can configure XL Deploy's logging behavior; for example, you can write log output to a file or log output from a specific source).

## Configure logging

XL Deploy uses the [Logback](http://logback.qos.ch/) logging framework for logging. To change its behavior, edit the `conf/logback.xml` file.

### Sample `logback.xml` file

The following is an example `logback.xml` file:

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

## Configure HTTP access logging

The HTTP access log is configured with the `conf/logback-access.xml` configuration file. The format is slightly different from the `logback.xml` format.

By default, the access log is done in the so-called combined format, but you can fully customize it. The log file is rolled per day on the first log statement in the new day.

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

For information about the configuration and possible patterns, refer to:

* [http://logback.qos.ch/access.html](http://logback.qos.ch/access.html)
* [http://logback.qos.ch/manual/layouts.html#AccessPatternLayout](http://logback.qos.ch/manual/layouts.html#AccessPatternLayout)

### Disable the HTTP access log

To disable the HTTP access log, create a `logback-access.xml` file with an empty `configuration` element:

    <configuration>
    </configuration>

## Audit log

XL Deploy writes an audit log for each human-initiated event on the server. For each event, the following information is recorded:

* The user making the request
* The event timestamp
* The component producing the event
* An informational message describing the event

For events involving CIs, the CI data submitted as part of the event is logged in XML format.

These are some of the events that are logged in the audit trail:

* The system is started or stopped
* A user logs into or out of the system
* An application is imported
* A CI is created, updated, moved, or deleted
* A security role is created, updated, or deleted
* A task (deployment, undeployment, control task, or discovery) is started, cancelled, or aborted

By default, the audit log is stored in `log/audit.log`. The audit log is rolled over daily.

## Enable the script log

The `logback.xml` file also contains a section that allows you to enable logging of all XL Deploy scripts to a separate log file called `log/scripts.log`. By default, this section is commented out.

**Important:** The scripts contain base64-encoded passwords. Therefore, if script logging is enabled, anyone with access to the server can read those passwords.
