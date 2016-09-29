---
title: Set up a mail server in the XL Deploy Generic plugin
categories:
- xl-deploy
subject:
- Bundled plugins
tags:
- generic
- plugin
- email
- smtp
weight: 358
---

The XL Deploy Generic plugin adds support for mail servers to XL Deploy. A mail server is a `mail.SmtpServer` configuration item (CI) defined under the **Configuration** root node.

A `udm.Environment` environment configuration item can have a reference to a mail server. If it does not have a reference, a default mail server named *defaultSmtpServer* will be used to send configured mails.

Using the mail server, configuration items such as the `generic.ManualProcess` can send mails notifying you of manual actions that need to be taken.

Here's a CLI snippet showing how to create a mail server CI:

	mailServer = factory.configurationItem("Configuration/MailServer","mail.SmtpServer")
	mailServer.host = "smtp.mycompany.com"
	mailServer.username = "mymailuser"
	mailServer.password = "secret"
	mailServer.fromAddress = "noreply@mycompany.com"
	repository.create(mailServer)

The `mail.SmtpServer` uses Java Mail to send email. You can specify additional Java Mail properties in the `smtpProperties` attribute. Refer to the [Javadoc for JavaMail](http://javamail.kenai.com/nonav/javadocs/com/sun/mail/smtp/package-summary.html) for a list of all properties.

## Configuring Transport Layer Security (TLS)

To configure the mail server to send emails using TLS, set the following property in the SMTP properties:

	mailServer.smtpProperties = {}
	mailServer.smtpProperties["mail.smtp.starttls.enable"] = "true"
	repository.update(mailServer)
