---
title: Sending XL Deploy auditing and logging to syslog
categories:
- xl-deploy
tags:
- system administration
---

You can use this configuration to integrate XL Deploy audit logging with a [syslog](https://en.wikipedia.org/wiki/Syslog) server. From the XL Deploy [logging configuration](http://logback.qos.ch/documentation.html) file (`DEPLOYIT_HOME/conf/logback.xml`):

        ...
        <!-- syslog appender -->
        <appender name="SYSLOG" class="ch.qos.logback.classic.net.SyslogAppender">
            <syslogHost>0.1.2.3</syslogHost>
            <facility>LOCAL1</facility>
            <suffixPattern>[%thread] %logger %msg</suffixPattern>
        </appender>

        <logger name="audit" level="info" additivity="false">
            <appender-ref ref="AUDIT" />
            <appender-ref ref="SYSLOG" />
        </logger>
    </configuration>

See the documentation about [`SyslogAppender`](http://logback.qos.ch/manual/appenders.html#SyslogAppender) for more information.
