---
title: Administrating the remote completion task
categories:
- xl-release
subject:
- 
tags:
- plugin
- relationships
- sub-releases
- gates
since:
- XL Release 7.5.0
---

# Administrating the remote completion task

## Configuration

### SMTP
XL Release sends remote completion requests to users of the system by email. To configure the email server that is used to send these requests, select Settings > SMTP server from the top menu. 
The SMTP server page is only available to users who have the  _Admin_ [global permission](https://docs.xebialabs.com/xl-release/how-to/configure-permissions.html). For a more detailed explanation of how to set up an SMTP server see: [Configure SMTP server](https://docs.xebialabs.com/xl-release/how-to/configure-smtp-server.html)

### IMAP
XL Release fetches the remote completion emails sent by users that want to complete or fail a remote completion task. To configure the email server that is used to receive the remote completion emails, 
select Settings > Shared configuration > Add IMAP server. The IMAP server settings are only available to users who have the _Admin_ [global permission](https://docs.xebialabs.com/xl-release/how-to/configure-permissions.html).

- IMAP server host (used to specify the internet address of the mail server)
- IMAP server port (port where the server is listening on)
- Use TLS (used to secure the connection)
- IMAP from address (this is the email address belonging to the IMAP server account; requests to remotely complete or fail a task gets fetched from this email account)
- IMAP server login ID
- IMAP server login password
- Enable whitelisting (can be checked to enable whitelisting, then only emails to and from whitelisted domains are processed for remote completion)
- Domain whitelist (used for adding whitelisted domains)
- Secret for generating email signatures (used to generate an email signature that verifies the integrity of a received remote completion email)

Make sure to set up a new email account specifically for receiving remote completion emails. All emails are deleted from INBOX once processed by XL Release, including unrecognized and existing emails.

### xl-release.conf
Advanced configuration can be specified inside the **xl-release.conf** file located inside the conf folder of the XL Release server. The advanced configuration is used by the mail fetcher which processes incoming remote completion emails. 

```
xl {
  remote-completion {
    sync-interval = 30 seconds
    startup-delay = 30 seconds
  }
}
```

- sync-interval (specifies the interval time for the email fetcher, default 30 seconds)
- startup-delay (specifies the initial startup delay of the mail fetcher, default 30 seconds)

## Auditing

Mailbox auditing can be turned on to log mailbox access by mailbox owners, delegates, and administrators. Inquire your mailbox provider how to set up mailbox auditing.

## Troubleshooting

### XL Release server
The XL Release server provides a mechanism for logging the application. As for default the basic remote completion process is being logged.
In order to enable more detailed logging, you can add the following line into the **logback.xml**: 
`<logger name="com.xebialabs.xlrelease.plugins.remotecompletion" level="debug" />`
The log level **trace** can be used for even more detailed logging.

### JavaMail
Inside the shell script to run the XL Release server, located in bin > run.sh add the following system property to the **$JAVACMD** to turn on session debugging: `-Dmail.debug=true`.
That will cause debug information to be printed to the console, including a protocol trace.

## Security Recommendations
- Enable whitelisting, then only emails to and from whitelisted domains will be processed for remote completion.
- IMAP and SMTP protocols should be encrypted with SSL/TLS to prevent any data leakage.
- Set up SMTP authentication to control user access.
- To prevent spamming, use content filters, enable DNS-based black lists (DNSBL) and SURBL (Spam URI Real-time Block Lists). Also maintain local blacklists of IP addresses of spam senders. Properly configure mail relay parameter of the email server to prevent open relay.
- Activate Reverse DNS to block bogus senders.
- Activate Sender Policy Framework (SPF) to prevent spoofed sources.
- Implement proactive measures to defend DoS and DDoS attack.
