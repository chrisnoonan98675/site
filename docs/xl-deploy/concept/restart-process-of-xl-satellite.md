---
title: Satellite restart process
subject:
Satellite
categories:
xl-deploy
tags:
satellite
remoting
plugin
extension
since:
XL Deploy 5.0.0
weight: 312
---

After you [synchronize plugins](/xl-deploy/how-to/synchronize-plugins-with-a-satellite-server.html) on an XL Deploy satellite, the satellite must be restarted.

This is required because:

* The Java classes, scripts, rules, and other resources of the synchronized plugins need to be loaded into the Java virtual machine (JVM)
* The internal state of the satellite must be reset

## How the restart process works

The satellite Java process uses a special exit code, `242`, to indicate it requires a restart. The startup script or service that bootstraps the satellite process is responsible for checking for this exit code and restarting the satellite when requested.

The satellite module includes a startup script that does this. The following code fragment is an example based on the Linux startup script that can be found in `<SATELLITE_HOME>/bin/run.sh`:

    RESTART_EXIT_CODE=242

    start_xl_satellite ()
    {
     $JAVACMD $SATELLITE_OPTS $SATELLITE_LOG_OPTS -classpath "${SATELLITE_SERVER_CLASSPATH}" com.xebialabs.satellite.SatelliteBootstrapper "$@"
     rc=$?
    }

    # Run XL Satellite server first time
    start_xl_satellite

    # Reboot loop
    while [ $rc = $RESTART_EXIT_CODE ]
    do
        echo "Restarting XL-Satellite";
        start_xl_satellite
    done

In this example:

1. The `start_xl_satellite` method starts the satellite Java process.
1. The method will be called initially to start the satellite the first time.
1. The `while` reboot loop will inspect the exit code; if the exit code is `242`, it means that the satellite requests a restart. The script will start the satellite.
1. Otherwise, the loop will end and the satellite is terminated.

If you want to use an alternative startup script, service wrapper, or daemon process instead of the startup scripts that are provided with the satellite module, be sure to take the restart process into account.
