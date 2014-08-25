---
title: Building community plugins
categories: knowledge-base xl-platform
tags: xl-community github
---

Here's a quick list of steps you need to follow in order to build the community plugins repository.

1. Checkout the community repo :<a href="https://github.com/xebialabs/community-plugins">https://github.com/xebialabs/community-plugins</a>
1. Make sure you have java 1.7 and maven&nbsp;
1. Make sure maven is also using java 1.7 ( since otherwise it would have issues while building)&nbsp;
1. Export an environment variable called DEPLOYIT_HOME and point it the deployit home directory on your machine
1. Export an environment variable called DEPLOYIT_CLI_HOME and point it the CLI home directory on your machine
1. You would also need to ensure that you have weblogic plugin in your DEPLOYIT_HOME/plugins since one of the liferay plugin requires that. ( Otherwise you may just skip that plugin from build)
1. Now once you do all the above, you can run&nbsp;<em>mvn package&nbsp;</em>or&nbsp;<em>mvn clean install&nbsp;</em>to build all the plugins.&nbsp;
1. You will find all the built plugins under each one’s target folder.

If you using mac , for step 3 you may need to ensure the version for java and java version used by maven using these commands

Change java version on mac : export JAVA_HOME=`/usr/libexec/java_home -v 1.7`

Change maven’s java version on mac : echo JAVA_HOME=\`/usr/libexec/java_home -v 1.7\` | sudo tee -a /etc/mavenrc
