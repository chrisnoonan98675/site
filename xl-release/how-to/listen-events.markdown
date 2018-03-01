---
title: Listen to XL Release events
categories:
- xl-release
subject:
- Events
tags:
- events
since:
- XL Release 8.0.0
---

From XL Release 8.0.0 your custom code can react on events that are happening inside XL Release. These events are published in a lot of different moments during the release execution.

## Example

In the following example we will show how to listen to different events and react on them.

In your plugin project add the following class under the package `com.xebialabs.xlrelease`:

{% highlight java %}
package com.xebialabs.xlrelease;

import org.springframework.stereotype.Component;
import com.xebialabs.xlrelease.events.EventListener;

@Component
@EventListener
public class TestListener {

  private NotifcationApi notificationApi = new ....

  @AsyncSubscribe
  public void notifyCriticalPhaseStarted(PhaseStartedEvent event) {
    if (event.phase().getName().equals("Important phase")) {
      notificationApi.sendAlert("Critical phase started!", "all@xebialabs.com");
    }
  }

}

{% endhighlight %}

**Important:** Your listener class *must* live under `com.xebialabs.xlrelease`.

In order to listen to events your class must be annotated as `@Component` along with `@EventListener`. The method with the event logic must have `@AsyncSubscribe`.

This `TestListener` is listening to all `PhaseStartedEvent` (event thrown when a phase has started), and sends an alert to `all@xebialabs.com` that a critical phase has started


## List of events

The following table shows the events that are published by XL Release:

{:.table .table-bordered}
| Event type | Fields | Description |
| --------- | -------- | ----------- |
| `XLReleaseEvent`      | username, timeMs | General event thrown in any other `XXXXXEvent`. |
| `AttachmentEvent`      | Same as `XLReleaseEvent` | General event thrown in any `AttachmentXXXXXEvent`. |
| `AttachmentCreatedEvent`      | containerId, attachment | Event thrown when attachment is created. |
| `AttachmentDeletedEvent`      | containerId, attachment | Event thrown when attachment is deleted. |
.... more to follow....
