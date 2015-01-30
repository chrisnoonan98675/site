---
title: Using XL Deploy logging
categories:
- xl-deploy
subject:
- System administration
tags:
- logging
- system administration
---

Out of the box, XL Deploy server writes informational, warning, and error log messages to standard output as well as `log/deployit.log` when running. In addition, XL Deploy writes an audit trail to the file `log/audit.log`. It is possible to change XL Deploys logging behavior (for instance to write log output to a file or to log output from a specific source). Aside from these application logs a HTTP log is written to `log/access.log`.

XL Deploy uses the [Logback](http://logback.qos.ch/) logging framework for logging. To change its behavior, edit the file `logback.xml` in the `conf` directory of the XL Deploy server installation directory.

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

## HTTP access log

The HTTP access log is configured with the `conf/logback-access.xml` configuration file. The format is slightly different from the `logback.xml` format. Per default the access log is done in the so called combined format, but you can fully customize it. The log file is rolled per day on the first log statement in the new day.

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

For additional details on the configuration and possible patterns see:

* [http://logback.qos.ch/access.html](http://logback.qos.ch/access.html)
* [http://logback.qos.ch/manual/layouts.html#AccessPatternLayout](http://logback.qos.ch/manual/layouts.html#AccessPatternLayout)

To disable the access log create a `logback-access.xml` with an empty `configuration` element:

    <configuration>
    </configuration>

## Audit log

XL Deploy writes an audit log for each human-initiated event on the server. For each event, the following information is recorded:

* the user making the request
* the event timestamp
* the component producing the event
* an informational message describing the event

For events involving CIs, the CI data submitted as part of the event is logged in XML format.

These are some of the events that are logged in the audit trail:

* the system is started or stopped
* a user logs into or out of the system
* an application is imported
* a CI is created, updated, moved or deleted
* a security role is created, updated or deleted
* a task (deployment, undeployment, control task or discovery) is started, cancelled or aborted

By default, the audit log is stored in `log/audit.log`. The audit log is rolled over daily.

## Script log

The default logging configuration file also contains a commented-out section that enables logging of all XL Deploy scripts to a separate log file.
