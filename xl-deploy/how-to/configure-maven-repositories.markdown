---
title: Configure XL Deploy to fetch artifacts from Maven repository
subject:
- System administration
categories:
- xl-deploy
tags:
- maven
---

If you have a Maven repository where you store your artifacts and also use XL Deploy to deploy them, you may want to avoid storing them twice. As of XL Deploy 5.0.0 you can do that using the [`artifactUri`](/xl-deploy/latest/udmcireference.html#udmbasedeployableartifact) property of an XL Deploy artifact. To use this feature you have to configure Maven repositories where the artifact will be searched at.

## Step 1 Get your Maven repositories details

Usually the list of repositories used by a Maven project is listed in its `pom.xml` file, while all the authentication and proxy configuration is specified in a `settings.xml` file of your development or Jenkins environment, see [Maven Settings Reference](https://maven.apache.org/settings.html) for more details. You need to find these configuration details for your environment to proceed.
 
For example your `pom.xml` file could have something like this:
 
    <repositories>
        <repository>
            <id>xebialabs-releases</id>
            <url>https://nexus.xebialabs.com/nexus/content/repositories/releases/</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

And your `settings.xml` file could contain following configuration:

    <servers>
        <server>
            <id>xebialabs-releases</id>
            <username>deployer</username>
            <password>secret</password>
        </server>
    </servers>

## Step 2 Configure XL Deploy Maven repositories

Maven repositories are configured in `SERVER_HOME/conf/maven.conf` file of the XL Deploy distribution in [HOCON format](https://github.com/typesafehub/config/blob/master/HOCON.md). The example Maven configuration mentioned above translates easily into following HOCON configuration:

    maven {
      repositories: [
        {
          id: xebialabs-releases
          url: "https://nexus.xebialabs.com/nexus/content/repositories/releases/"
          authentication: {
            username: "deployer"
            password: "secret"
          }
          snapshots: {
            enabled: false
          }
        }
      ]
    }

As you may noticed the structure of `maven.conf` is slightly different comparing to `settings.xml` and `pom.xml`. There is a list of repositories (`maven.repositories: [...]`) and each repository contains all configuration related to it. This configuration includes:
 
* Basic information: `id` and `url`.
* `authentication` configuration with the same elements as `servers` have in `settings.xml`: `username`, `password`, `privateKey` and `passphrase`.
* `proxy` configuration to use when connecting to this repository. For example:

<pre><code>maven {
  repositories: [
    {
      ...
      proxy: {
        host: "proxy.host.net"
        port: 80
        username: proxyuser
        password: proxypass
      }
    }
  ]
}
</code></pre>

* Repository policies for `releases` and `snapshots` configure whether this repository will be used to search for `SNAPSHOT` and non-`SNAPSHOT` versions of artifacts. The `checksumPolicy` property configures how strictly XL Deploy will react to unmatched checksums when downloading artifacts from this Maven repository. Allowed values are: `ignore`, `fail` or `warn`. XL Deploy does not cache remote artifacts locally, so `updatePolicy` configuration is not applicable. Here is an example configuration of repository policies:

<pre><code>maven {
  repositories: [
    {
      ...
      releases: {
        enabled: true
        checksumPolicy: fail
      }
      snapshots: {
        enabled: false
      }
    }
  ]
}
</code></pre>

The rest of Maven configuration from `settings.xml` is not applicable in XL Deploy. For example you do not have to specify `mirrors` because instead you can just use mirror URL directly in your repository definition. And `profiles` are used to configure the Maven build, which does not happen in XL Deploy.  

## Step 3 Restart XL Deploy

You need to restart XL Deploy server for changes in `maven.conf` to be applied.
