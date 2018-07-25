---
title: Start XL Deploy
categories:
xl-deploy
subject:
Installation
tags:
system administration
cli
installation
weight: 108
---

To start the XL Deploy server, open a command prompt or terminal, go to the `XL_DEPLOY_SERVER_HOME/bin` directory, and execute the appropriate command:

{:.table .table-striped}
| Operating system | XL Deploy version | Command |
| ---------------| ----------------| ------|
| Microsoft Windows | XL Deploy 4.5.x or earlier | `server.cmd` |
| Microsoft Windows | XL Deploy 5.0.0 or later | `run.cmd` |
| Unix-based systems | XL Deploy 4.5.x or earlier | `server.sh` |
| Unix-based systems | XL Deploy 5.0.0 or later | `run.sh` |

## Start XL Deploy in the background

To run the XL Deploy server as a background process:

* In XL Deploy 4.5.0 and earlier:
    * On Unix, use `nohup bin/server.sh &`
    * On Windows, run XL Deploy [as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html)
* In XL Deploy 5.0.0 and later:
    * On Unix, use `nohup bin/run.sh &` or run XL Deploy as a service
    * On Windows, run XL Deploy as a service

**Important:** If you have installed XL Deploy as a service, you must ensure that the XL Deploy server is configured so that it can start without user interaction. For example, the server should not [require a password](/xl-deploy/how-to/install-xl-deploy.html#step-10-provide-a-password-for-the-encryption-key) for the encryption key that protects passwords in the repository. Alternatively, you can store the password in the `XL_DEPLOY_SERVER_HOME/conf/deployit.conf file` as follows:

    repository.keystore.password=MY_PASSWORD

XL Deploy will encrypt the password when you start the server.

## Server options

Start the server with the `-help` flag to see the options it supports. They are:

{:.table .table-striped}
| Option | Description |
| -----| ----------|
| `-force-upgrades` | Forces the execution of upgrades at XL Deploy startup. This option is supported in XL Deploy 4.5.x and later. |
| `-recovery` | Attempts to recover a corrupted repository.
| `-repository-keystore-password VAL` | Specifies the password that XL Deploy should use to access the repository keystore. Alternatively, you can specify the password in the `deployit.conf` file with the `repository.keystore.password` key. If you do not specify the password and the keystore requires one, XL Deploy will prompt you for it. |
| `-reinitialize` | Reinitialize the repository. This option is only available for use with the `-setup` option, and it is only supported when XL Deploy is using a filesystem repository. It cannot be used when you have configured XL Deploy to run against a database. |
| `-setup` | Runs the XL Deploy setup wizard. |
| `-setup-defaults VAL` | Specifies a file that contains default values for configuration properties in the setup wizard. |

Any options you want to give the XL Deploy server when it starts can be specified in the `XL_DEPLOY_SERVER_OPTS` environment variable.

**Tip:** For information about the `-setup-defaults` option, refer to [Install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html#automatically-install-xl-deploy-with-default-values).

## Troubleshoot slow start up time for XL Deploy version 7.5.0

In version 7.5.0 new configuration items were added to the `conf/xld-wrapper-linux.conf` file:

        wrapper.java.additional.4=-Djava.security.manager=java.lang.SecurityManager
        wrapper.java.additional.5=-Djava.security.policy=conf/xl-deploy.policy
        wrapper.java.additional.6=-javaagent:lib/aspectjweaver-1.8.10.jar

These parameters in the wrapper configuration file (`xld-wrapper-(linux|win).conf`) can be used to change the behavior of the application.        

### SecurityManager configuration

The [Deployfile functionality](/xl-deploy/concept/environment-as-code.html) allows users to execute scripts on the XL Deploy server. The execution environment for these scripts is sandboxed by the SecurityManager of the JVM. This is configured in the wrapper configuration file with these lines:

        wrapper.java.additional.4=-Djava.security.manager=java.lang.SecurityManager
        wrapper.java.additional.5=-Djava.security.policy=conf/xl-deploy.policy

When these lines are removed or commented, the XLD server will start faster, but the sandbox will not be secured and will allow commands such as the one below to execute through the CLI:

        user > repository.applyDeployfile("println(new File('/etc/passwd').text)")

This command would print the content of the `/etc/passwd` file on the console in the XL Deploy server.
With the sandbox properly configured, executing this command would result in an exception:

        com.xebialabs.deployit.deployfile.execute.DeployfileExecutionException: Error while executing script on line 1.
        ...
        Caused by: java.security.AccessControlException: access denied ("java.io.FilePermission" "/etc/passwd" "read")

It is strongly recommended to have the SecurityManager configuration enabled on any installation that is accessible by multiple users. Only for demonstration or temporary purposes should it be considered to comment/disable it in favor of faster startup times.

### AspectJWeaver for JMX monitoring

When the JMX monitoring is switched on (`xl.jmx.enabled = true`), parts of the task engine can be instrumented to provide more detailed information. To enable this, the following setting must be added/uncommented in the wrapper configuration file:

        wrapper.java.additional.6=-javaagent:lib/aspectjweaver-1.8.10.jar

This will slow down the startup of the XL Deploy server considerably. If do not add this line, the following warning will show up in the log:

        ERROR kamon.ModuleLoaderExtension -

          ___                           _      ___   _    _                                 ___  ___ _            _
         / _ \                         | |    |_  | | |  | |                                |  \/  |(_)          (_)
        / /_\ \ ___  _ __    ___   ___ | |_     | | | |  | |  ___   __ _ __   __ ___  _ __  | .  . | _  ___  ___  _  _ __    __ _
        |  _  |/ __|| '_ \  / _ \ / __|| __|    | | | |/\| | / _ \ / _` |\ \ / // _ \| '__| | |\/| || |/ __|/ __|| || '_ \  / _` |
        | | | |\__ \| |_) ||  __/| (__ | |_ /\__/ / \  /\  /|  __/| (_| | \ V /|  __/| |    | |  | || |\__ \\__ \| || | | || (_| |
        \_| |_/|___/| .__/  \___| \___| \__|\____/   \/  \/  \___| \__,_|  \_/  \___||_|    \_|  |_/|_||___/|___/|_||_| |_| \__, |
                    | |                                                                                                      __/ |
                    |_|                                                                                                     |___/

         It seems like your application was not started with the -javaagent:/path-to-aspectj-weaver.jar option but Kamon detected
         the following modules which require AspectJ to work properly:

              kamon-akka, kamon-scala

         If you need help on setting up the aspectj weaver go to http://kamon.io/introduction/get-started/ for more info. On the
         other hand, if you are sure that you do not need or do not want to use the weaver then you can disable this error message
         by changing the kamon.show-aspectj-missing-warning setting in your configuration file.

The task engine metrics will not be available, but other metrics will be accessible through JMX.
