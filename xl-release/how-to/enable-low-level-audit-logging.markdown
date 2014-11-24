---
title: Enabling granular audit logging in XL Release
---

XL Release, like our app deployment tool [XL Deploy](http://xebialabs.com/products/xl-deploy), provides a low-level audit stream that tracks all activity in the system: login events, creating, modifying and deleting configuration items etc. This information complements the audit stream already provided by XL Release's [Activity Log](http://docs.xebialabs.com/releases/4.0/xl-release/reference_manual.html#activity-logs) (which tracks activity for each release, at a more domain-specific level of granularity) and is made available as a log stream.

You can enable low-level audit logging by changing the log level of the `audit` logger in `SERVER_HOME/conf/logback.xml`:

```
<!-- set to "info" to enable low-level audit logging -->
<logger name="audit" level="off" additivity="false">
    <appender-ref ref="AUDIT" />
</logger>
```

By default, the log stream is stored in `SERVER_HOME/log/audit.log`. You can change this location, the file rolling policy etc. by changing the configuration of the `AUDIT` appender, and/or pipe the log stream to additional sinks (e.g. [syslog](http://logback.qos.ch/manual/appenders.html#SyslogAppender)) by configuring additional appenders. See the [Logback documentation](http://logback.qos.ch/manual/) for details.

Here's a sample of the audit stream with the level of the audit logger set to info:

```
2014-11-22 11:24:18.764 [audit.system] system - Started
2014-11-22 11:25:18.125 [audit.repository] admin - Created CIs [Configuration/Custom/Configuration1099418] 
2014-11-22 11:25:18.274 [audit.repository] admin -     CI [Configuration/Custom/Configuration1099418]:
<jenkins.Server id="Configuration/Custom/Configuration1099418" created-by="admin" created-at="2014-11-22T11:25:16.255-0500" last-modified-by="admin" last-modified-at="2014-11-22T11:25:16.255-0500">
  <title>My Jenkins</title>
  <url>http://localhost/foo</url>
  <username>foo</username>
  <password>{b64}C7JZetqurQo2B8x2V8qUhg==</password>
</jenkins.Server>
```