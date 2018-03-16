---
title: Using the remote completion task
categories:
- xl-release
subject:
-
tags:
- plugin
- remote completion task
since:
- XL Release 8.0.0
---

# Using the remote completion task
The remote completion task allows you to complete or fail a certain task without logging into XL Release from a remote place without access to a company’s network.

## How to use the remote completion task
A remote completion task represents a step in a template or release that must be completed by a person, remotely or from within XL Release.
Like other task types, you can assign remote completion tasks to a single user or to a release team.

**_TODO img of remote completion tasks_**

In the release flow editor, remote completion tasks have a purple border.

When a remote completion task has been started, the users that are assigned to the task will receive an email. This email contains two buttons Complete task and Fail task.

**_TODO img of email with buttons_**

Each of the two buttons will generate a new remote completion email upon click. This email can be send in order to complete or fail a task.

**_TODO img of generated email_**

## Auditing
The activity log shows everything that happens in a release. It provides an audit trail of who did what, and when. To open the activity log, select **Activity logs** from the **Show** menu. For a more in detail explanation of the activity logs see: [Release activity logs](https://docs.xebialabs.com/xl-release/concept/release-activity-logs.html).
Click **Filter categories** and select **Comments** to see who and when someone remotely completed or failed a task.

## Limitations

### Reassigning
When a remote completion task is assigned to a user or a team and the task is in progress, the only way to re-assign a user or team is to fail and retry the task after you reassigned a new user or team.

## Supported mail clients
- MacOS > Mail, Thunderbird
- Windows > Mail, Outlook, Thunderbird
- Android > Outlook, Gmail
- iOS > Mail, Gmail

There are some known issues with webmail clients. 

# Administrating the remote completion task

## Configuration

### SMTP
XL Release sends remote completion requests to users of the system by email. To configure the email server that is used to send these requests, select Settings > SMTP server from the top menu. 
The SMTP server page is only available to users who have the Admin global permission. For a more in detail explanation of how to setup an SMTP server see: [Configure SMTP server](https://docs.xebialabs.com/xl-release/how-to/configure-smtp-server.html)

### IMAP
XL Release fetches the remote completion emails send by users that want to complete or fail a remote completion task. To configure the email server that is used to receive the remote completion emails, 
select Settings > Shared configuration > Add IMAP server. The IMAP server settings is only available to users who have the Admin global permission.

**_TODO img of IMAP Server settings_**

**Host** is used to specify the internet address of the mail server, followed by the **Port** it is listening on. Consult your system administrator on the values to use and whether to use **TLS** to secure the connection.

Use the **Username** and **Password** fields to set the credentials of the user that will connect. Make sure to setup a new email account especially for receiving remote completion emails. All existing emails can be deleted when you use an existing email account. The **From Address** is used as the email address that users reply to in order to remotely complete or fail a task. 
You must enter a valid email address. The **Enable whitelisting** field can be checked to enable whitelisting, then only emails to and from whitelisted domains will be processed for remote completion. 
The domains can be added using the **Domain whitelist** field. The **secret** field is used to generate an email signature that is used to verify that a received remote completion email hasn’t been modified.

### xl-release.conf
Advanced configuration can be specified inside the xl-release.conf file located inside the conf folder from the XL Release server. The advanced configuration is used by the mail fetcher which processes incoming remote completion emails. 
The fetching of remote completion emails can be turned on or off using the **fetching-enabled** field. The **sync-interval** field specifies the interval time for the email fetcher. The **startup-delay** field specifies the initial startup delay of the mail fetcher. 

**_TODO Image of the conf file_**

## Auditing

Mailbox auditing can be turned on to log mailbox access by mailbox owners, delegates, and administrators. Inquire your mailbox provider how to setup mailbox auditing.

## Troubleshooting

### XL Release server

The XL Release server logging for the remote completion plugin can be turned on by adding the following line into the **logback.xml**: 
`<logger name="com.xebialabs.xlrelease.plugins.remotecompletion" level="info" />`
The basic flow of the remote completion process can be followed with log level **info**. The log level **debug** or even **trace** can be used for more detailed logging. 

### JavaMail
Inside the shell script to run the XL Release server, located in bin > run.sh add the following system property to the **$JAVACMD** to turn on session debugging. `-Dmail.debug=true`
That will cause debug information to be printed to the console, including a protocol trace.

## Recommendations
- Enable whitelisting, then only emails to and from whitelisted domains will be processed for remote completion.
- To prevent any data leakage, ongoing and incoming emails should be encrypted. IMAP and SMTP protocols should be encrypted with SSL/TLS.
- Set up SMTP authentication to control user access.
- To prevent spamming, use content filters, enable DNS-based black lists (DNSBL) and SURBL (Spam URI Real-time Block Lists). Also maintain local blacklists of IP addresses of spam senders. Properly configure mail relay parameter of the email server to prevent open relay.
- Activate Reverse DNS to block bogus senders.
- Activate Sender Policy Framework (SPF) to prevent spoofed sources.
- Implement proactive measures to defend DoS and DDoS attack.







