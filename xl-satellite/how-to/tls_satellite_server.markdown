---
title: Configuring security to an XL Satellite server
subject:
- Satellite
categories:
- xl-satellite
tags:
- security
---

## Using secured communication
  
XL-Satellite can communicate over a secured communication channel. XL-Satellite can use TLS/SSL technology to encrypt data. This algorithm relies on certificate checking and data encryption using asymmetric keys.
   
Depending on your security policy, you can use self-signed certificates using the standard tool from Java, `keytool`. 
   
    # Generate a keystore and a self-signed certificate for xl-satellite
    # storepass is the password for the keystore
    # keypass is the private password for the key
    # you can use different algorithm and key strength. Please take a look a keytool reference document from [Oracle](https://docs.oracle.com/cd/E19509-01/820-3503/ggfgo/index.html).
    keytool -genkey -alias satellite -keyalg RSA -keypass [XXX] -storepass [XXX] -keystore satellite.jks -validity 360 -keysize 1024
   
    # Extracting the cer of xld
    keytool -export -keystore satellite.jks -alias satellite -file satellite.cer
    
    # Importing into the truststore for xld
    keytool -import -alias satellite -file satellite.cer -keystore xld-truststore.jks


Once in possession of a keystore for XL-Satellite and a shared truststore with XL-Deploy, you can enable this feature by modifying file `conf/application.conf`
 
    satellite {
        tls = on
    }
    
This add the security configuration to the current satellite configuration. This configuration is located in another file, `conf/security.conf`:
    
    akka.remote.netty.ssl.security{
        # Protocol to use for SSL encryption, choose from:
        # Java 7:
        #   'TLSv1.1', 'TLSv1.2'
        protocol = "TLSv1.2"
        
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

Default configuration is set to `TLSv1.2`, `TLS_RSA_WITH_AES_128_CBC_SHA` and `AES128CounterSecureRNG`. Feel free to change those values depending on your security policy and requirements.

NOTE: Those configuration value must be symetric with XL-Deploy values to enable handshake. You can enable logging secured communication with environment variable before starting XL-Satellite:

    export SATELLITE_OPTS="$SATELLITE_OPTS -Djavax.net.debug=all"