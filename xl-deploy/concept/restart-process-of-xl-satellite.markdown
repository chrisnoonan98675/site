---
title: The restart process of XL Satellite
subject:
- Installation
categories:
- xl-deploy
tags:
- system administration
- installation
- service
- daemon
- setup
- satellite
- restart
---

The satellite will require a restart after [Synchronising plugins](/xl-deploy/how-to/synchronize-plugins-with-a-satellite-server.html), or when the connection drops. 

This is necessary because:

* The Java classes, scripts, rules and other resources of the synchronised plugins need to be loaded into JVM.
* The internal state of the satellite needs to be reset.

## How the restart process works

The java process of the satellite uses a special exit code `242` to indicate it requires a restart. The startup script or service that bootstraps the satellite process is responsible for checking for this exit code and restarting the satellite when requested.

In XL Satellite we provide a startup script that does this. The following fragment is an example and based on the linux startup script that can be found here: `bin/run.sh`

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

Explanation of the shell script fragment: 
    
1. The `start_xl_satellite` method will start the satellite java process
1. The method will be called initially to start the satellite the first time
1. The reboot loop `while` will inspect the exit code, if the exit code is `242` it means that the satellite requests a restart. The script will start the satellite
1. Otherwise, the loop will end and the satellite is terminated.

If you do not use the provided shell scripts but you are using an alternative startup script, service wrapper or daemon process to run XL Satellite, this is something to keep in mind.    
