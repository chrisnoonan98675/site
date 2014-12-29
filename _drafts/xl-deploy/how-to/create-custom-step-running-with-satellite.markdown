---
title: Create a step running on a satellite
categories:
- xl-deploy
subject:
- Satellite
tags:
- remoting
- satellite
- steps
---

Making your own steps to be running with satellite is simple. Instead of extending **Step** class to create a step, you need to extends **com.xebialabs.xlplatform.satellite.SatelliteAware**.

This interface ask you to implement only one method:

    /**
     * @return the satellite where this step can be executed
     */
    Satellite getSatellite();


You can get a reference to a satellite from a Host CI instance throught the method *getSatellite()*. Null values are accepted.