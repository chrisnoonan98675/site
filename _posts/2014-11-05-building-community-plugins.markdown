---
title: Building community plugins
categories:
- xl-deploy
- xl-release
tags:
- plugin
- community
---

Here's a quick list of steps you need to follow in order to build the community plugins repository.

1. Check out the community repository: [https://github.com/xebialabs/community-plugins](https://github.com/xebialabs/community-plugins)
1. Ensure that you have Java 1.7 and Maven installed. 
1. Ensure that Maven also uses Java 1.7, to prevent issues while building. 
1. Export an environment variable called `DEPLOYIT_HOME` and point it the XL Deploy home directory on your computer.
1. Export an environment variable called `DEPLOYIT_CLI_HOME` and point it the XL Deploy command-line interface (CLI) home directory on your computer.
1. Ensure that you have the XL Deploy WebLogic plugin installed in `DEPLOYIT_HOME/plugins`. One of the Liferay plugin requires it. (Otherwise, you can just skip that plugin from the build).
1. Run `mvn package` or `mvn clean install` to build all plugins. 

You will find all the built plugins under each one's target folder.

If you are using OS X, you may need to ensure that the Java version is correct. To change the Java version:

    export JAVA_HOME=`/usr/libexec/java_home -v 1.7`

To change the Maven Java version:

    echo JAVA_HOME=\`/usr/libexec/java_home -v 1.7\` | sudo tee -a /etc/mavenrc
