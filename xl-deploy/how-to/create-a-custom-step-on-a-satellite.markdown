---
layout: satellite
title: Create a custom step on a satellite
categories:
- xl-deploy
subject:
- Satellite
tags:
- remoting
- satellite
- step
- rules
since:
- XL Deploy 5.0.0
---

Normally, when you want to create a custom deployment step, you use Java to [extend the `Step` interface](/xl-deploy/5.0.x/xldeployjavaapimanual.html#define-a-custom-step-for-rules). To create a custom step that is run by a satellite, you extend the `com.xebialabs.xlplatform.satellite.SatelliteAware` interface instead.

`com.xebialabs.xlplatform.satellite.SatelliteAware` has one method:

    /**
     * @return the satellite where this step can be executed
     */
    Satellite getSatellite();

You can get a reference to a satellite from a host configuration item (CI) instance through the `getSatellite()` method. Null values are accepted.
