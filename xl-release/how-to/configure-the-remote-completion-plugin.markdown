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
XL Release fetches the remote completion emails send by users that want to complete or fail a remote completion task. To configure the email server that is used to receive the remote completion emails, 
select Settings > Shared configuration > Add IMAP server. The IMAP server settings is only available to users who have the _Admin_ [global permission](https://docs.xebialabs.com/xl-release/how-to/configure-permissions.html).

- IMAP server host (used to specify the internet address of the mail server)
- IMAP server port (port where the server is listening on)
- Use TLS (used to secure the connection)
- IMAP from address (used as the email address that users reply to in order to remotely complete or fail a task)
- IMAP server login ID
- IMAP server login password
- Enable whitelisting (can be checked to enable whitelisting, then only emails to and from whitelisted domains will be processed for remote completion)
- Domain whitelist (used for adding whitelisted domains)
- Secret for generating email signatures (used to generate an email signature that is used to verify that a received remote completion email hasn’t been modified)

Make sure to setup a new email account especially for receiving remote completion emails. All existing emails can be deleted when you use an existing email account.

### xl-release.conf
Advanced configuration can be specified inside the **xl-release.conf** file located inside the conf folder from the XL Release server. The advanced configuration is used by the mail fetcher which processes incoming remote completion emails. 

```
xl {
  remote-approval {
    fetching-enabled = true
    sync-interval = 10 seconds
    startup-delay = 10 seconds
  }
}
```

- fetching-enabled (used to turn the fetching of remote completion emails on or off)
- sync-interval (specifies the interval time for the email fetcher)
- startup-delay (specifies the initial startup delay of the mail fetcher)

Note that when the fetching of the emails is disabled, you are still able to add remote completion tasks inside releases. 
It will also still send emails to all assignees. Disabling means that the email fetcher will not fetch for remote completion emails. That means
that all remote completion requests will not be processed. 

## Auditing

Mailbox auditing can be turned on to log mailbox access by mailbox owners, delegates, and administrators. Inquire your mailbox provider how to setup mailbox auditing.

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
- To prevent any data leakage, ongoing and incoming emails should be encrypted. IMAP and SMTP protocols should be encrypted with SSL/TLS.
- Set up SMTP authentication to control user access.
- To prevent spamming, use content filters, enable DNS-based black lists (DNSBL) and SURBL (Spam URI Real-time Block Lists). Also maintain local blacklists of IP addresses of spam senders. Properly configure mail relay parameter of the email server to prevent open relay.
- Activate Reverse DNS to block bogus senders.
- Activate Sender Policy Framework (SPF) to prevent spoofed sources.
- Implement proactive measures to defend DoS and DDoS attack.
