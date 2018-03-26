---
title: Administrating the remote completion task
categories:
- xl-release
subject:
- Tasks
tags:
- plugin
- remote
- task
since:
- XL Release 8.0.0
---

## Server configuration

### SMTP server

XL Release sends remote completion requests to users of the system by email. To configure the email server that is used to send these requests, select **Settings** > **SMTP server** from the top menu.
The SMTP server page is only available to users with the *Admin* [global permission](https://docs.xebialabs.com/xl-release/how-to/configure-permissions.html). For more details on how to set up an SMTP server, see [Configure SMTP server](https://docs.xebialabs.com/xl-release/how-to/configure-smtp-server.html).

### IMAP server
XL Release receives the remote completion emails sent by users that want to complete or fail a remote completion task. To configure the email server that is used to receive the remote completion emails, select **Settings** > **Shared configuration** > **Add IMAP server**. The IMAP server settings are only available to users with the *Admin* [global permission](https://docs.xebialabs.com/xl-release/how-to/configure-permissions.html).

* IMAP server host: the internet address of the mail server
* IMAP server port: port where the server is listening on
* Use TLS: used to secure the connection
* IMAP from address: the email address of the IMAP server account; requests to remotely complete or fail a task are received from this email account
* IMAP server login ID
* IMAP server login password
* Enable whitelisting: when enable whitelisting is checked, only emails to and from whitelisted domains are processed for remote completion
* Domain whitelist: used for adding whitelisted domains
* Secret for generating email signatures: generate an email signature that verifies the integrity of a received remote completion email. Notice that changing the secret will invalidate all previously send completion request emails.

Make sure to set up a new email account specifically for receiving remote completion emails. All emails are deleted from INBOX after they are processed by XL Release, including unrecognized and existing emails.

### Settings in `xl-release.conf`

Advanced configuration settings can be specified inside the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file. The advanced configuration is used by the email fetcher which processes incoming remote completion emails.

        xl {
          remote-completion {
            sync-interval = 30 seconds
            startup-delay = 30 seconds
          }
        }

* `sync-interval`: specifies the interval time for the email fetcher, default value is 30 seconds
* `startup-delay`: specifies the initial startup delay of the mail fetcher, default value is 30 seconds

## Mailbox auditing

Mailbox auditing can be turned on to log mailbox access by mailbox owners, delegates, and administrators. Contact your mailbox provider to set up mailbox auditing.

## Troubleshooting

### XL Release server

The XL Release server provides a mechanism for logging the application. By default, only the basic remote completion process is logged.
To enable detailed logging, you can add the following line into the `XL_RELEASE_SERVER_HOME/conf/logback.xml` file:

        <logger name="com.xebialabs.xlrelease.plugins.remotecompletion" level="debug" />

Use the log level *trace* for more detailed logging.

### JavaMail debugging

To turn on session debugging, add the following system property to the `$JAVACMD` inside the shell script that runs the XL Release server, located in `bin\run.sh`:

        -Dmail.debug=true

This property enables printing of debugging information to the console, including a protocol trace.

## Security recommendations

The XL Release remote completion feature uses emails sent by users to complete or fail any task. These are the risks associated with this feature:

* Spamming and flooding attacks

 XL Release processes each incoming email for the configured mailbox. To avoid receiving thousands of emails that can flood your mailbox, you can enable whitelisting. Only emails sent to and received from whitelisted domains are processed for remote completion. Use content filters, enable DNS-based blacklists (DNSBL) and Spam URI Real-time Block Lists (SURBL) and maintain the local blacklists of IP addresses of spam senders. Configure the email relay parameter on the email server to prevent open relay.

* Data leakage  

XL Release sends and receives email from a task owner to take action on any task. To prevent any data leakage during this process, you must encrypt IMAP and SMTP protocols with SSL/TLS and set up SMTP authentication to control user access.

* DoS and DDoS attack

To avoid DoS and DDoS attacks, limit the number of connection and authentication errors with your SMTP server.

* Email abuse attack

The majority of the abusive email messages carry fake sender addresses. Activate Sender Policy Framework (SPF) to prevent spoofed sources. The SPF check ensures that the sending Message Transfer Agent (MTA) is allowed to send emails on behalf of the senders domain name. You must also activate Reverse DNS to block fake senders. After the Reverse DNS Lookup is activated, your SMTP verifies that the senders IP address matches both the host and domain names that were submitted by the SMTP client in the EHLO/HELO command.
