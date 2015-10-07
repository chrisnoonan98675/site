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
---

By default, XL Release server writes informational, warning, and error log messages to standard output and `log/xl-release.log` when running. In addition, XL Release writes an audit trail to the file `log/audit.log`. It is possible to change XL Releases logging behavior (for instance, to write log output to a file or to log output from a specific source).

XL Release uses the Logback logging framework for its logging. To change the behavior, edit the file `logback.xml` in the `conf` directory of the XL Release server installation directory.

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

For more information, see the [Logback website](http://logback.qos.ch/).

## Audit log

XL Release can write an audit log for each human-initiated event on the server. For each event, the following information is recorded:

* The user making the request
* The event timestamp
* The component producing the event
* An informational message describing the event

For events involving CIs, the CI data submitted as part of the event is logged in XML format.

Some of the events that are logged in the audit trail are:

* The system is started or stopped
* A user logs into or out of the system
* An application is imported
* A CI is created, updated, moved, or deleted
* A security role is created, updated, or deleted
* A task (deployment, undeployment, control task, or discovery) is started, cancelled, or aborted

By default, the audit log is stored in `log/audit.log`. The audit log is rolled over daily.
