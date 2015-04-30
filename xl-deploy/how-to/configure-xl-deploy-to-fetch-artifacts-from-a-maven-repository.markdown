---
title: Configure XL Deploy to fetch artifacts from a Maven repository
subject:
- Repository
categories:
- xl-deploy
tags:
- maven
- artifacts
- package
- application
since:
- 5.0.0
---

If you store artifacts in a Maven repository and use XL Deploy to deploy them, you may want to avoid storing them twice. As of XL Deploy 5.0.0, you can access artifacts stored in a Maven repository using the [`fileUri` property](/xl-deploy/how-to/add-an-externally-stored-artifact-to-a-package.html) of XL Deploy artifacts. To use this feature, you must configure the Maven repositories where XL Deploy will search for artifacts.

## Step 1 Get your Maven repository details

First, you must collect information about the configuration of your environment. The list of repositories that a Maven project uses is normally listed in its `pom.xml` file, while the authentication and proxy configuration is specified in the `settings.xml` file of your development or Jenkins environment. Refer to the [Maven Settings Reference](https://maven.apache.org/settings.html) for more information.
 
For example, the `pom.xml` file might contain:
 
    <repositories>
        <repository>
            <id>xebialabs-releases</id>
            <url>https://nexus.xebialabs.com/nexus/content/repositories/releases/</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

And `settings.xml` file could contain following configuration:

    <servers>
        <server>
            <id>xebialabs-releases</id>
            <username>deployer</username>
            <password>secret</password>
        </server>
    </servers>

## Step 2 Configure XL Deploy Maven repositories

Maven repositories are configured in `SERVER_HOME/conf/maven.conf` file of the XL Deploy distribution in [HOCON format](https://github.com/typesafehub/config/blob/master/HOCON.md). The example Maven configuration above translates to following HOCON configuration:

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

The structure of `maven.conf` is slightly different from `settings.xml` and `pom.xml`. There is a list of repositories (`maven.repositories: [...]`), and each repository contains the configuration related to it. This configuration includes:
 
* Basic information: `id` and `url`.
* `authentication` configuration with the same elements as `servers` have in `settings.xml`: `username`, `password`, `privateKey` and `passphrase`.
* `proxy` configuration to use when connecting to this repository. For example:

		maven {
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

* Repository policies for `releases` and `snapshots` configure whether this repository will be used to search for `SNAPSHOT` and non-`SNAPSHOT` versions of artifacts. The `checksumPolicy` property configures how strictly XL Deploy will react to unmatched checksums when downloading artifacts from this Maven repository. Allowed values are: `ignore`, `fail` or `warn`. XL Deploy does not cache remote artifacts locally, so `updatePolicy` configuration does not apply.

    This is an example configuration of repository policies:

		maven {
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

The remaining Maven configuration in `settings.xml` does not apply to XL Deploy. For example, you do not need to specify `mirrors`, because you can just use a mirror URL directly in your repository definition. And `profiles` are used to configure the Maven build, which does not happen in XL Deploy.  

## Step 3 Restart XL Deploy

You must restart XL Deploy server for changes in `maven.conf` to be applied.
