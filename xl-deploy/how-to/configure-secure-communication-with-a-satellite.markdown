---
title: Configure secure communication between XL Deploy and satellites
categories:
- xl-deploy
subject:
- Satellite
tags:
- security
- satellite
since:
- XL Deploy 5.1.0
weight: 302
---

XL Deploy communicates with satellite servers over a secure communication channel using [TLS/SSL technology](http://en.wikipedia.org/wiki/Transport_Layer_Security) to encrypt data. This algorithm relies on certificate checking and data encryption using asymmetric keys.

**Note:** For information about secure communication in XL Deploy 5.0.x, refer to [Configure secure communication with a satellite](/xl-deploy/5.0.x/configure-secure-communication-with-a-satellite-5.0.html).

## TLS in a nutshell

TLS is based on the notion of public and private keys. The server contains a private key and a public certificate. In the Java world, they are stored in a *keystore*. The private key must be hidden and can be protected with a passphrase. This key must not be given out or communicated.

When a client tries to reach a server, it authenticates the destination. The server must prove its identity. To achieve this, the client gets a list of trusted certificates. This is the *truststore*. It contains public certificates that are verified by a trusted authority.

When a client tries to reach a server, there is a negotiation phase. During this phase, the client challenge the server to authenticate it. Once identified, every bit of data transferred between each side of the communication is encrypted.

With this technology, an external process that you do not manage cannot pretend to be a satellite of yours, and external processes cannot listen to the secure communication.

## Configure secure communication

To configure secure communication between XL Deploy and satellites:

1. Generate a key and certificate for each satellite server.
1. Add each satellite certificate to the truststore on the XL Deploy server.

If you are using XL Deploy 6.1.0 or later, you must also configure mutual authentication:

1. Generate a key and a public certificate for the XL Deploy server.
1. Add the certificate to the truststore on each satellite server.

To ensure that communication is fully secure, each key should be unique.

### Sample keystore and truststore creation

This example shows how to create keys and self-signed certificates using [the `keytool` utility](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html).

First, configure XL Deploy to trust the satellite:

1. Generate a key for a satellite:

        keytool -genkey -alias satellite -keyalg RSA -keypass k3yp@ss -storepass st0r3p@ss -keystore satellite.jks -validity 360 -keysize 1024

1. Export the public certificate:

        keytool -export -keystore satellite.jks -alias satellite -file satellite.cer

1. Import the certificate into the truststore on the XL Deploy server:

        keytool -import -alias satellite -file satellite.cer -storepass st0r3p@ss -keystore xld-truststore.jks

Repeat this procedure for each satellite.

Then, if you are using XL Deploy 6.1.0 or later, you must configure each satellite to trust XL Deploy:

1. Generate a key for the XL Deploy server:

        keytool -genkey -alias xld -keyalg RSA -keypass xldkeypass -storepass xldstorepass -keystore xld.jks -validity 360 -keysize 1024

1. Export the public certificate:

        keytool -export -keystore xld.jks -alias xld -file xld.cer

1. Import the certificate into the truststore on the satellite server:

        keytool -import -alias xld -file xld.cer -storepass xldstorepass -keystore satellite-truststore.jks

## Configure satellites

After you have configured the truststore on a satellite, update the `SATELLITE_HOME/conf/satellite.conf` configuration file to enable SSL. For example:

    satellite {
      port = 8380
      hostname = "win-s2008r2" #Host name or ip address to bind to

      streaming {
        port = 8480
        chunk-size = 100000
        compression = off
        throttle = off
        throttle-speed = 10000 #IN kBytes/sec, should be at least 100 kB/sec
      }

      ssl {
        enabled = yes

        key-store = "C:\\XLDeploy\\conf\\satellite.jks"
        key-store-password = "st0r3p@ss"
        key-password = "k3yp@ss"

        trust-store = "C:\\XLDeploy\\conf\\satellite-truststore.jks"
        trust-store-password = "xldstorepass"

        # Protocol to use for SSL encryption, choose from:
        # Java 7:
        #   'TLSv1.1', 'TLSv1.2'
        protocol = "TLSv1.2"

        # Example: ["TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA"]
        # You need to install the JCE Unlimited Strength Jurisdiction Policy
        # Files to use AES 256.
        # More info here:
        # http://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html#SunJCEP
        enabled-algorithms = ["TLS_RSA_WITH_AES_128_CBC_SHA"]

        # There are three options, in increasing order of security:
        # "" or SecureRandom => (default)
        # "SHA1PRNG" => Can be slow because of blocking issues on Linux
        # "AES128CounterSecureRNG" => fastest startup and based on AES encryption
        # "AES256CounterSecureRNG"
        # The following use one of 3 possible seed sources, depending on
        # availability: /dev/random, random.org and SecureRandom (provided by Java)
        # "AES128CounterInetRNG"
        # "AES256CounterInetRNG" (Install JCE Unlimited Strength Jurisdiction
        # Policy Files first)
        # Setting a value here may require you to supply the appropriate cipher
        # suite (see enabled-algorithms section above)
        random-number-generator = "AES128CounterSecureRNG"
      }

      timeout {
        upload.idle = "30 seconds"
      }

      directory {
        work = "workdir"
        recovery = "recovery"
      }

      metrics {
        hostname = ${satellite.hostname}
        port = 8080
      }

      maintenance {
        check-for-running-tasks-delay = 10 seconds
      }
    }

**Important:** The values for `protocol` and `enabled-algorithms` must match the values that XL Deploy uses in the  `XL_DEPLOY_SERVER_HOME/conf/system.conf` file.

## Configure XL Deploy

This is a sample `satellite` section of a corresponding `XL_DEPLOY_SERVER_HOME/conf/system.conf` file:

    satellite {

        enabled = true
        port = 8180
        # hostname = "token" #Host name or ip address to bind to

        ssl {
            enabled = yes
            port = 8280

            key-store = "/Users/sampleuser/xl-software/conf/xld.jks"
            key-store-password = "xldstorepass"
            key-password = "xldkeypass"

            trust-store = "/Users/sampleuser/xl-software/conf/xld-truststore.jks"
            trust-store-password = "st0r3p@ss"
        }

        timeout {
            ping = "5 seconds"
            upload.idle = "30 seconds"
        }
    }

If you are using XL Deploy 6.1.0 or later, the `key-store`, `key-store-password`, `key-password`, `trust-store`, and `trust-store-password` parameters are required in the `system.conf` file. In earlier versions, they are not supported.

## Enable logging

To enable logging of secure communications on a satellite, set the `SATELLITE_OPTS` environment variable before starting the satellite:

    export SATELLITE_OPTS="$SATELLITE_OPTS -Djavax.net.debug=all"

To enable logging on XL Deploy, set the `DEPLOYIT_SERVER_OPTS` variable:

    export DEPLOYIT_SERVER_OPTS="$DEPLOYIT_SERVER_OPTS -Djavax.net.debug=all"
