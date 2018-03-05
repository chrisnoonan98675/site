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

As of XL Release version 8.0.0, your custom code can react on events that are happening inside XL Release. These events are published in a lot of different moments during the release execution.

## Example

In the following example, you can see how to listen to one of the XL Release events and react to it. This requires you to create a Java class `PhaseNotifiyListener` inside the `com.example.plugin` package.

XL Release uses [java ServiceLoader](https://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html) to discover your custom listeners. You must create a file with the name `com.xebialabs.xlrelease.events.XLReleaseEventListener` inside your `.jar` file under the `META-INF/services` directory.

    META-INF/services/com.xebialabs.xlrelease.events.XLReleaseEventListener

The content of the file is a line with the implementation of the `XLReleaseEventListener`, in this case:

    com.example.plugin.PhaseNotifiyListener

The content of your class:

{% highlight java %}

package com.example.plugin;

import com.xebialabs.xlrelease.domain.events.PhaseStartedEvent;
import com.xebialabs.xlrelease.events.AsyncSubscribe;
import com.xebialabs.xlrelease.events.XLReleaseEventListener;

public class PhaseNotifiyListener implements XLReleaseEventListener {

  private NotificationApi notificationApi = new ....

  @AsyncSubscribe
  public void notifyCriticalPhaseStarted(PhaseStartedEvent event) {
    if (event.phase().getTitle().equals("Critical phase")) {
      notificationApi.sendAlert("Critical phase started!", "all@xebialabs.com");
    }
  }

}

{% endhighlight %}

Your java class must implement the interface `XLReleaseEventListener` (same as the file created under `META-INF/services`). The method with the event logic must be annotated with `@AsyncSubscribe`. This `TestListener` is listening to all `PhaseStartedEvent` (event thrown when a phase has started), and sends an alert to `all@xebialabs.com` that a critical phase has started.

**Important:** As this class is instantiated by XL Release, the constructor cannot have any argument.

## List of events

The following table shows the events that are published by XL Release:

{:.table .table-bordered}
| Event type | Fields | Description |
| --------- | -------- | ----------- |
| `XLReleaseEvent` | username, timeMs | Event thrown when in any `XXXEvent`. |
| `AttachmentEvent` | Same as `XLReleaseEvent` | Event thrown in any `AttachmentXXXEvent`. |
| `AttachmentCreatedEvent` | containerId, attachment | Event thrown when attachment is created. |
| `AttachmentDeletedEvent` | containerId, attachment | Event thrown when attachment is deleted. |
| `CalendarEvent` |  Same as `XLReleaseEvent` | Event thrown when in any `BlackoutXXXEvents`. |
| `BlackoutCreatedEvent` | blackout | Event thrown when a blackout is created. |
| `BlackoutUpdatedEvent` | original, updated | Event thrown when a blackout is updated. |
| `BlackoutDeletedEvent` | blackout | Event thrown when a blackout is deleted. |
| `CommentEvent` | Same as `XLReleaseEvent` | Event thrown in any `CommentXXXEvent`. |
| `CommentCreatedEvent` | task, comment, author, createdByUser | Event thrown when a comment is created. |
| `CommentUpdatedEvent` | task, original, updated | Event thrown when a comment is updated. |
| `DependencyEvent` | Same as `XLReleaseEvent`| Event thrown when in any `DependencyXXXEvent` |
| `DependencyCreatedEvent` | dependency | Event thrown when a dependency is created |
| `DependencyUpdatedEvent` | original, updated | Event thrown when a dependency is updated |
| `DependencyDeletedEvent` | dependency | Event thrown when a dependency is deleted |
| `GateConditionEvent` | Same as `XLReleaseEvent` | Event thrown when in any `GateConditionXXXEvent` |
| `GateConditionCreatedEvent` | | Event thrown when a |
| `GateConditionDeletedEvent` | | Event thrown when a |
| `GateConditionUpdatedEvent` | | Event thrown when a |
| `GlobalVariableEvent` | | Event thrown when a |
| `GlobalVariableCreatedEvent` | | Event thrown when a |
| `GlobalVariableDeletedEvent` | | Event thrown when a |
| `GlobalVariableUpdatedEvent` | | Event thrown when a |
| `LinkEvent` | | Event thrown when a |
| `LinkCreatedEvent` | | Event thrown when a |
| `LinkDeletedEvent` | | Event thrown when a |
| `PermissionsEvent` | | Event thrown when a |
| `PermissionsUpdatedEvent` | | Event thrown when a |
| `PhaseEvent` | | Event thrown when a |
| `PhaseClosedEvent` | | Event thrown when a |
| `PhaseClosingAction` | | Event thrown when a |
| `PhaseCompletedEvent` | | Event thrown when a |
| `PhaseCompletingAction` | | Event thrown when a |
| `PhaseCopiedEvent` | | Event thrown when a |
| `PhaseCreatedEvent` | | Event thrown when a |
| `PhaseDeletedEvent` | | Event thrown when a |
| `PhaseDuplicatedEvent` | | Event thrown when a |
| `PhaseExecutionEvent` | | Event thrown when a |
| `PhaseFailedEvent` | | Event thrown when a |
| `PhaseMovedEvent` | | Event thrown when a |
| `PhaseRetriedEvent` | | Event thrown when a |
| `PhaseRetryingAction` | | Event thrown when a |
| `PhaseStartedEvent` | | Event thrown when a |
| `PhaseStartedFailingEvent` | | Event thrown when a |
| `PhaseStartingAction` | | Event thrown when a |
| `PhaseUpdatedEvent` | | Event thrown when a |
| `ReleaseEvent` | | Event thrown when a |
| `ReleaseAbortedEvent` | | Event thrown when a |
| `ReleaseAbortingAction` | | Event thrown when a |
| `ReleaseBulkAbortedEvent` | | Event thrown when a |
| `ReleaseBulkEvent` | | Event thrown when a |
| `ReleaseBulkStartedEvent` | | Event thrown when a |
| `ReleaseCompletedEvent` | | Event thrown when a |
| `ReleaseCompletingAction` | | Event thrown when a |
| `ReleaseCreatedEvent` | | Event thrown when a |
| `ReleaseDeletedEvent` | | Event thrown when a |
| `ReleaseDuplicatedEvent` | | Event thrown when a |
| `ReleaseExecutedEvent` | | Event thrown when a |
| `ReleaseExecutionEvent` | | Event thrown when a |
| `ReleaseFailedEvent` | | Event thrown when a |
| `ReleaseMovedEvent` | | Event thrown when a |
| `ReleaseOverdueEvent` | | Event thrown when a |
| `ReleasePausedEvent` | | Event thrown when a |
| `ReleasePausingAction` | | Event thrown when a |
| `ReleaseRestoredEvent` | | Event thrown when a |
| `ReleaseResumedEvent` | | Event thrown when a |
| `ReleaseResumingAction` | | Event thrown when a |
| `ReleaseRetriedEvent` | | Event thrown when a |
| `ReleaseRetryingAction` | | Event thrown when a |
| `ReleaseStartedEvent` | | Event thrown when a |
| `ReleaseStartedFailingEvent` | | Event thrown when a |
| `ReleaseStartedFromCreateReleaseTaskEvent` | | Event thrown when a |
| `ReleaseStartingAction` | | Event thrown when a |
| `ReleaseUpdatedEvent` | | Event thrown when a |
| `ReleaseVariableCreatedEvent` | | Event thrown when a |
| `ReleaseVariableDeletedEvent` | | Event thrown when a |
| `ReleaseVariableEvent` | | Event thrown when a |
| `ReleaseVariableReplacedEvent` | | Event thrown when a |
| `ReleaseVariableUpdatedEvent` | | Event thrown when a |
| `ReleaseVariablesUpdatedEvent` | | Event thrown when a |
| `RiskEvent` | | Event thrown when a |
| `RiskProfileUpdated` | | Event thrown when a |
| `TaskEvent` | | Event thrown when a |
| `TaskAbortedEvent` | | Event thrown when a |
| `TaskAbortingAction` | | Event thrown when a |
| `TaskCompletedEvent` | | Event thrown when a |
| `TaskCompletingAction` | | Event thrown when a |
| `TaskCopiedEvent` | | Event thrown when a |
| `TaskCreatedEvent` | | Event thrown when a |
| `TaskCreatedOrTypeChangedEvent` | | Event thrown when a |
| `TaskDelayedEvent` | | Event thrown when a |
| `TaskDelayingAction` | | Event thrown when a |
| `TaskDeletedEvent` | | Event thrown when a |
| `TaskDueSoonEvent` | | Event thrown when a |
| `TaskExecutionEvent` | | Event thrown when a |
| `TaskFailedEvent` | | Event thrown when a |
| `TaskFailingAction` | | Event thrown when a |
| `TaskGroupFailingEvent` | | Event thrown when a |
| `TaskMovedEvent` | | Event thrown when a |
| `TaskOverdueEvent` | | Event thrown when a |
| `TaskReopenedEvent` | | Event thrown when a |
| `TaskReopeningAction` | | Event thrown when a |
| `TaskRetriedEvent` | | Event thrown when a |
| `TaskRetryingAction` | | Event thrown when a |
| `TaskSkippedEvent` | | Event thrown when a |
| `TaskSkippingAction` | | Event thrown when a |
| `TaskStartedEvent` | | Event thrown when a |
| `TaskStartingAction` | | Event thrown when a |
| `TaskUpdatedEvent` | | Event thrown when a |
| `TaskWaitingForInputEvent` | | Event thrown when a |
| `TeamEvent` | | Event thrown when a |
| `TeamCreatedEvent` | | Event thrown when a |
| `TeamDeletedEvent` | | Event thrown when a |
| `TeamUpdatedEvent` | | Event thrown when a |
| `TeamsMergedEvent` | | Event thrown when a |
| `TeamsRemovedInTemplateEvent` | | Event thrown when a |
| `TriggerEvent` | | Event thrown when a |
| `TriggerCreatedEvent` | | Event thrown when a |
| `TriggerCreatedNewReleaseEvent` | | Event thrown when a |
| `TriggerDeletedEvent` | | Event thrown when a |
| `TriggerUpdatedEvent` | | Event thrown when a |
| `VariableEvent` | | Event thrown when a |
