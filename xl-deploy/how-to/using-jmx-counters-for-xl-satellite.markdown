---
title: Using JMX counters for XL Satellite
categories:
- xl-deploy
subject:
- Satellite
tags:
- satellite
- JMX
- counters
since:
- XL Deploy 7.2.0
---

You can use the Java Management Extensions (JMX) technology to manage and monitor your XL Satellite servers. With JMX counters you can keep track of the resources the XL Satellite server is consuming.

A JMX agent can be started on XL Deploy or XL Satellite by configuring JMX settings in `system.conf` or `satellite.conf` files respectively. Using a client like JConsole or VisualVM, you can connect to the running JMX agent through a port to view the information tracked by the JMX counters.

## Supported JMX counters

* `task.done` - Number of tasks completed
* `task.active` - Number of active tasks
* `remote.<host_ip>-<host_port>` - Where `host_ip` is the IP address of XL Deploy connected to XL Satellite and `host_port`is the port on which XL Deploy is connected to XL Satellite. The value for this counter can be either `ASSOCIATED` or `DISASSOCIATED`, depending if XL Deploy and XL Satellite are connected or not.

## Configure secure communications

A client like `JConsole` can connect to the JMX agent over a secure communication channel using the TLS/SSL technology.
To configure a secure communication:

1. Generate a key and a certificate for XL Deploy or XL Satellite servers.
1. Add a certificate to the `truststore` on the client machine.

### Sample `keystore` and `truststore` creation

This example describes how to create keys and self-signed certificate using the `keytool` utility.

1. Generate a key for an XL Deploy or XL Satellite instance:

        keytool -genkey -alias jmx -keyalg RSA -keypass k3yp@ss -storepass st0r3p@ss -keystore keystore-jmx.jks -validity 360 -keysize 1024

2. Export the public certificate:

        keytool -export -keystore keystore-jmx.jks -alias jmx -file jmx-cert.cer

3. Import the certificate into the truststore on the client machine:    

        keytool -import -alias jmx -file jmx-cert.cer -storepass st0r3p@ss -keystore truststore-jmx.jks

## Configure XL Deploy or XL Satellite to start the JMX agent

Modify the `system.conf` file for XL Deploy or `satellite.conf` file for XL Satellite:

        xl {
          jmx {
            enabled = yes
            port = 1099

            ssl {
              enabled = yes
              key-store = "keystore-jmx.jks"
              key-password = "k3yp@ss"
              key-store-password = "st0r3p@ss"
              protocol = "TLSv1.2"
              enabled-algorithms = ["TLS_RSA_WITH_AES_128_CBC_SHA"]
            }
          }
        }    

Start the XL Deploy or XL Satellite servers with above configuration. This will start the JMX agent with SSL enabled.

To connect the client (for example `JConsole`) to the JMX agent:  

        jconsole -J-Djavax.net.ssl.trustStore=truststore-jmx.jks -J-Djavax.net.ssl.trustStorePassword=st0r3p@ss

Use the following URL to connect to the JMX agent:      

        service:jmx:rmi:///jndi/rmi://<server_host>:1099/jmxrmi
