---
layout: satellite
title: Configure secure communication with a satellite server
categories:
- xl-deploy
subject:
- Satellite
tags:
- security
- satellite
since:
- 5.0.0
deprecated:
- XL Deploy 5.1.0
---

XL Deploy can communicate with satellite servers over a secure communication channel using [TLS/SSL technology](http://en.wikipedia.org/wiki/Transport_Layer_Security) to encrypt data. This algorithm relies on certificate checking and data encryption using asymmetric keys.

## TLS in a nutshell

TLS is based on the notion of public and private keys. The server contains a private key and a public certificate. In the Java world, they are stored in a *key store*. The private key must be hidden and can be protected with a passphrase. This key must not be given out or communicated. 

When a client tries to reach a server, it authenticates the destination. The server must prove its identity. To achieve this, the client gets a list of trusted certificates. This is the *trust store*. It contains public certificates that are verified by a trusted authority.

When a client tries to reach a server, there is a negotiation phase. During this phase, the client challenge the server to authenticate it. Once identified, every bit of data transferred between each side of the communication is encrypted.

With this technology, an external process that you do not manage cannot pretend to be a satellite of yours, and external processes cannot listen to the secure communication.

## How to create self-signed certificates
  
Depending on your security policy, you can create self-signed certificates using [the `keytool` utility](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html). For example:
   
    # Generate a keystore and a self-signed certificate for xl-satellite
    # storepass is the password for the keystore
    # keypass is the private password for the key
    # you can use different algorithm and key strength. Please take a look a keytool reference document from [Oracle](https://docs.oracle.com/cd/E19509-01/820-3503/ggfgo/index.html).
    keytool -genkey -alias satellite -keyalg RSA -keypass [XXX] -storepass [XXX] -keystore satellite.jks -validity 360 -keysize 1024
   
    # Extracting the cer of xld
    keytool -export -keystore satellite.jks -alias satellite -file satellite.cer
    
    # Importing into the truststore for xld
    keytool -import -alias satellite -file satellite.cer -keystore xld-truststore.jks

After you have a keystore for the satellite and a shared truststore with XL Deploy, you can enable secure communication by modifying `conf/satellite.conf` as follows:
 
    satellite {
      ssl {
        enabled = yes
      }
    }

## Security configuration

The satellite security configuration is located in `conf/satellite.conf`:

    satellite {
      ssl {
        enabled = yes

        # Absolute path to the Java Key Store used by the server connection
        key-store = "keystore"
        # This password is used for decrypting the key store
        key-store-password = "changeme"
        # This password is used for decrypting the key
        key-password = "changeme"
        
        # Absolute path to the TrustStore used by the client and server connection
        trust-store = "truststore"
        # This password is used for decrypting the trust store
        trust-store-password = "changeme"
      }
    }
    
The default configuration uses `TLSv1.2`, `TLS_RSA_WITH_AES_128_CBC_SHA`, and `AES128CounterSecureRNG`. You can change these values depending on your security policy and requirements. The change is needed to be done in `conf/satellite.conf`.

    akka.remote.netty.ssl.security {

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

**Important:** These configuration values must match the values that XL Deploy uses.

## Logging

To enabled logging of secure communications, set the `SATELLITE_OPTS` environment variable before starting the satellite:

    export SATELLITE_OPTS="$SATELLITE_OPTS -Djavax.net.debug=all"

To enable logging on XL Deploy, set the `DEPLOYIT_SERVER_OPTS` variable:

    export DEPLOYIT_SERVER_OPTS="$DEPLOYIT_SERVER_OPTS -Djavax.net.debug=all"

