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

As of XL Release version 8.0.0, your custom code can react on activity log events that are happening inside XL Release. These events are published in a lot of different moments during the release execution.

## Example

In the following example, you can see how to listen to one of the XL Release events and react to it. First of all, if you are using `maven` or `gradle` to build your plugin you have to add XebiaLabs maven repository: `https://dist.xebialabs.com/public/maven2` and the following dependency: `com.xebialabs.xlrelease:xlr-api:8.0.0`

Now let's create a Java class `PhaseNotifiyListener` inside the `com.example.plugin` package.

XL Release uses [java ServiceLoader](https://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html) to discover your custom listeners. You must create a file with the name `com.xebialabs.xlrelease.events.XLReleaseEventListener` inside your `.jar` file under the `META-INF/services` directory.

    META-INF/services/com.xebialabs.xlrelease.events.XLReleaseEventListener

The content of the file is a line with the implementation of the `XLReleaseEventListener`, in this case:

    com.example.plugin.PhaseNotifiyListener

The content of your class:

{% highlight java %}

package com.example.plugin;

import com.xebialabs.xlrelease.domain.events.ActivityLogEvent;
import com.xebialabs.xlrelease.events.AsyncSubscribe;
import com.xebialabs.xlrelease.events.XLReleaseEventListener;

public class PhaseNotifiyListener implements XLReleaseEventListener {

  private NotificationApi notificationApi = new ....

  @AsyncSubscribe
  public void notifyCriticalPhaseStarted(ActivityLogEvent event) {
    if (e.getActivityType().equals(PHASE_STARTED) && e.getMessage().contains(Critical phase)) {
      notificationApi.sendAlert(Critical phase started!, all@xebialabs.com);
    }
  }

}

{% endhighlight %}

Your java class must implement the interface `XLReleaseEventListener` (same as the file created under `META-INF/services`). The method with the event logic must be annotated with `@AsyncSubscribe`. This `TestListener` is listening to all `ActivityLogEvent`, if the type of event is a `PHASE_STARTED` and contains our critical phase name, sends an alert to `all@xebialabs.com` that a critical phase has started.

**Important:** As this class is instantiated by XL Release, the constructor cannot have any argument.

## List of activity types

The following table shows the different activity log types that you can receive in different moments:

{:.table .table-bordered}
| Activity log type | Description |
| --------- | ----------- |
| TEMPLATE_CREATED | Created template |
| TEMPLATE_DUPLICATED | Duplicated template |
| TEMPLATE_MOVED | Template moved from folder |
| RELEASE_TITLE_UPDATED | Changed release title |
| RELEASE_DESCRIPTION_UPDATED | Changed release description |
| RELEASE_DUE_DATE_UPDATED | Changed release due date |
| RELEASE_SCHEDULED_START_DATE_UPDATED | Changed release scheduled start date |
| RELEASE_OWNER_UPDATED | Changed release owner |

TODO from here! :-)

| RELEASE_TAGS_UPDAsTED | Updated release tags |
| RELEASE_FLAG_STATUS_UPDATED | Changed flag status for release |
| RELEASE_FLAG_COMMENT_UPDATED | Changed flag comment on release |
| RELEASE_ABORT_RELEASE_ON_FAILURE_UPDATED | Changed 'Abort release on failure' status on status |
| COMMENT_ADDED| Added a comment on task |
| COMMENT_UPDATED| Updated comment on task |
| PHASE_CREATED| Created a phase in a release|
| PHASE_RENAMED| Renamed a phase in a release|
| PHASE_DESCRIPTION_UPDATED| Changed the description of a phase |
| PHASE_DURATION_UPDATED| Changed the duration of a phase |
| PHASE_DUE_DATE_UPDATED| Changed the due date of a phase |
| PHASE_SCHEDULED_START_DATE_UPDATED| Changed scheduled start date of Phase '%s' from '%s' to '%s', RELEASE_EDIT) |
| PHASE_COLOR_CHANGED| Changed color of Phase '%s' from '%s' to '%s', RELEASE_EDIT) |
| PHASE_MOVED| Moved Phase '%s', RELEASE_EDIT) |
| PHASE_DELETED| Deleted Phase '%s', RELEASE_EDIT) |
| PHASE_DUPLICATED| Duplicated Phase to '%s', RELEASE_EDIT) |
| PHASE_STARTED| Started Phase '%s', LIFECYCLE, IMPORTANT) |
| PHASE_FAILED| Failed Phase '%s' , LIFECYCLE) |
| PHASE_FAILING|  Phase '%s' started failing, LIFECYCLE) |
| PHASE_RESTARTED| Restarted Phase '%s', LIFECYCLE) |
| PHASE_COMPLETED| Completed Phase '%s', LIFECYCLE) |
| PHASE_CLOSED| Closed Phase '%s' and skipped all its tasks, LIFECYCLE) |
| TASK_CREATED| Created Task '%s' of type '%s', RELEASE_EDIT, TASK_EDIT) |
| TASK_MOVED_BETWEEN_CONTAINERS| Moved Task '%s' from '%s' to '%s', RELEASE_EDIT) |
| TASK_MOVED_WITHIN_CONTAINER| Moved Task '%s' within '%s', RELEASE_EDIT) |
| TASK_DELETED| Deleted Task '%s', RELEASE_EDIT, IMPORTANT) |
| TASK_TITLE_UPDATED| Changed title of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_DESCRIPTION_UPDATED| Changed description of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_DURATION_UPDATED| Changed duration of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_OWNER_UPDATED| Changed owner of Task '%s' from %s to %s, TASK_EDIT, REASSIGN) |
| TASK_TASK_TEAM_UPDATED| Changed team of Task '%s' from %s to %s, TASK_EDIT, REASSIGN) |
| TASK_DUE_DATE_UPDATED| Changed due date of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_SCHEDULED_START_DATE_UPDATED| Changed scheduled start date of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_WAIT_FOR_SCHEDULED_START_DATE_UPDATED| Changed wait for scheduled start date of Task '%s' to '%s', TASK_EDIT) |
| TASK_FLAG_STATUS_UPDATED| Flagged Task '%s' from '%s' to '%s', TASK_EDIT, IMPORTANT) |
| TASK_FLAG_COMMENT_UPDATED| Changed flag status of Task '%s' from '%s' to '%s', TASK_EDIT, IMPORTANT, COMMENTS) |
| TASK_COPIED| Copied Task to '%s', RELEASE_EDIT) |
| TASK_NOTIFICATION_ADDRESSES_UPDATED| Changed Notification addresses of recipients of the Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_NOTIFICATION_CC_UPDATED| Changed Notification list of email addresses that receive the message as CC of Task '%s' from '%s' to '%s' | TASK_EDIT),
| TASK_NOTIFICATION_BCC_UPDATED| Changed Notification list of email addresses that receive the message as BCC of Task '%s' from '%s' to '%s' | TASK_EDIT),
| TASK_NOTIFICATION_REPLY_TO_UPDATED| Changed Notification email address of the reply message recipient of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_NOTIFICATION_PRIORITY_UPDATED| Changed Notification email priority of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_NOTIFICATION_SUBJECT_UPDATED| Changed Notification subject of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_NOTIFICATION_BODY_UPDATED| Changed Notification body of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_SCRIPT_UPDATED| Changed Script of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_INPUT_PROPERTY_UPDATED| Changed input property '%s' of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_INPUT_PROPERTY_PASSWORD_UPDATED| Changed input property '%s' of Task '%s', TASK_EDIT) |
| TASK_OUTPUT_PROPERTIES_UPDATED| Changed output property '%s' of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_PRECONDITION_UPDATED| Changed precondition of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_TYPE_CHANGED| Changed type of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_RELEASE_TITLE_UPDATED| Changed release title of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_RELEASE_TEMPLATE_UPDATED| Changed release template of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_START_RELEASE_FLAG_UPDATED| Changed start release flag of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_RELEASE_VARIABLE_UPDATED| Changed variable '%s' of Task '%s' from '%s' to '%s', TASK_EDIT) |
| TASK_RELEASE_TAGS_UPDATED| Changed release tags of Task '%s' from '%s' to '%s', TASK_EDIT) |
|
|  TEAM_CREATED| Created Team '%s', SECURITY, RELEASE_EDIT) |
| TEAM_UPDATED| Updated Team '%s' with members '%s' and roles '%s', REASSIGN,SECURITY) |
| TEAM_DELETED| Removed Team '%s', SECURITY, RELEASE_EDIT) |
| FOLDER_TEAM_MERGED| Merged teams and permissions from template %s into folder %s, SECURITY, RELEASE_EDIT) |
| REMOVE_TEMPLATE_TEAMS| Removed teams from template %s. It will inherit teams and permissions from folder %s, SECURITY, RELEASE_EDIT) |
|
|  GATE_CONDITION_CREATED| Created Condition '%s' on gate '%s', TASK_EDIT) |
| GATE_CONDITION_TITLE_UPDATED| Updated title on Condition '%s' of gate '%s' from '%s' to '%s', TASK_EDIT) |
| GATE_CONDITION_FULFILLED| Fulfilled Condition '%s' on gate '%s', TASK_EDIT, LIFECYCLE) |
| GATE_CONDITION_UNFULFILLED| Unfulfilled Condition '%s' on gate '%s', TASK_EDIT, LIFECYCLE) |
| GATE_CONDITION_DELETED| Deleted Condition '%s' from gate '%s', TASK_EDIT) |
|
|  LINK_ADDED| Created Link on Group '%s' from Task '%s' to Task '%s', TASK_EDIT) |
| LINK_REMOVED| Removed Link on Group '%s' from Task '%s' to Task '%s', TASK_EDIT) |
|
|  DEPENDENCY_CREATED| Added Dependency in gate '%s' on '%s', TASK_EDIT) |
| DEPENDENCY_UPDATED| Changed Dependency in gate '%s' on '%s' to '%s', TASK_EDIT) |
| DEPENDENCY_DELETED| Deleted Dependency in gate '%s' on '%s', TASK_EDIT) |
|
|  RELEASE_CREATED_FROM_TEMPLATE| Created Release '%s' from template '%s', LIFECYCLE) |
| RELEASE_CREATED_FROM_CREATE_RELEASE_TASK| Created Release '%s' from create release task '%s' and template '%s', LIFECYCLE) |
| RELEASE_CREATED_FROM_DSL| Created Release '%s' from DSL, LIFECYCLE) |
| RELEASE_RESTORED_FROM_REVISION| Template '%s' restored from revision '%s', LIFECYCLE) |
| RELEASE_CREATED| Created Empty Release '%s', LIFECYCLE, RELEASE_EDIT) |
| RELEASE_STARTED| Started Release, LIFECYCLE, IMPORTANT) |
| RELEASE_STARTED_FROM_CREATE_RELEASE_TASK| Started Release '%s' from create release task '%s', LIFECYCLE) |
| RELEASE_FAILED| Failed Release, LIFECYCLE) |
| RELEASE_FAILING| Release started failing, LIFECYCLE) |
| RELEASE_RESTARTED| Restarted Release, LIFECYCLE) |
| RELEASE_COMPLETED| Completed Release, LIFECYCLE, IMPORTANT) |
| RELEASE_ABORTED| Aborted Release, LIFECYCLE, IMPORTANT) |
|
|  TASK_STARTED| Started Task '%s', LIFECYCLE) |
| TASK_DELAYED| Activated Task '%s' with a scheduled start date of '%s', LIFECYCLE) |
| TASK_DELAYED_DUE_TO_BLACKOUT| Activated Task '%s' with a scheduled start date of '%s' due to a blackout, LIFECYCLE) |
| TASK_COMPLETED| Completed Task '%s', LIFECYCLE) |
| TASK_COMPLETED_IN_ADVANCE| Completed in advance Task '%s', LIFECYCLE) |
| TASK_SKIPPED| Skipped Task '%s', LIFECYCLE, IMPORTANT) |
| TASK_SKIPPED_IN_ADVANCE| Skipped in advance Task '%s', LIFECYCLE, IMPORTANT) |
| TASK_FAILED| Failed Task '%s': %s, LIFECYCLE, IMPORTANT) |
| TASK_RESTARTED| Restarted Task '%s', LIFECYCLE) |
| TASK_FAILING| Task '%s' started failing, LIFECYCLE) |
| TASK_REOPENED| Task '%s' reopened, LIFECYCLE) |
| TASK_WAITING_FOR_INPUT| Task '%s' requires input for variables: %s, LIFECYCLE) |
|
|  TEMPLATE_IMPORTED| Imported template, RELEASE_EDIT) |
|
|  PERMISSIONS_UPDATED| Updated permissions to:\n%s, SECURITY) |
|
|  ATTACHMENT_ADDED| Added attachment: '%s' (%s), RELEASE_EDIT) |
| ATTACHMENT_ADDED_ON_TASK| Added attachment: '%s' (%s) on task: '%s', RELEASE_EDIT) |
| ATTACHMENT_DELETED| Deleted attachment: '%s', RELEASE_EDIT) |
| ATTACHMENT_DELETED_FROM_TASK| Deleted attachment: '%s' from task : '%s', RELEASE_EDIT) |
| TRIGGER_ADDED| Added release trigger '%s', RELEASE_EDIT) |
| TRIGGER_EDITED| Edited release trigger '%s', RELEASE_EDIT) |
| TRIGGER_DELETED| Deleted release trigger '%s', RELEASE_EDIT) |
| TRIGGER_CREATE_NEW_RELEASE| Trigger %s created new release '%s' from template '%s', RELEASE_EDIT) |
| TEMPLATE_ALLOW_CONCURRENT_RELEASES_FROM_TRIGGER_UPDATED| Changed 'Allow concurrent triggered releases' from '%s' to '%s', RELEASE_EDIT) |
| RELEASE_VARIABLE_CREATED| Created variable %s, RELEASE_EDIT) |
| RELEASE_VARIABLE_DELETED| Deleted variable %s, RELEASE_EDIT) |
| RELEASE_VARIABLE_REPLACED| Replaced variable %s with %s, RELEASE_EDIT) |
| RELEASE_VARIABLE_RENAMED| Renamed variable '%s' to '%s', RELEASE_EDIT) |
| RELEASE_VARIABLE_VALUE_UPDATED| Changed value of variable %s from '%s' to '%s', RELEASE_EDIT) |
| RELEASE_VARIABLE_PASSWORD_VALUE_UPDATED| Changed value of password variable %s, RELEASE_EDIT) |
| RELEASE_VARIABLE_NAME_UPDATED| Changed name of variable %s from '%s' to '%s', RELEASE_EDIT) |
| RELEASE_VARIABLE_LABEL_UPDATED| Changed label of variable %s from '%s' to '%s', RELEASE_EDIT) |
| RELEASE_VARIABLE_DESCRIPTION_UPDATED| Changed description of variable %s from '%s' to '%s', RELEASE_EDIT) |
| RELEASE_VARIABLE_REQUIRED_UPDATED| Changed required flag of variable %s from %s to %s, RELEASE_EDIT) |
| RELEASE_VARIABLE_SHOW_ON_CREATE_UPDATED| Changed 'show on create release form' flag of variable %s from %s to %s, RELEASE_EDIT) |
| GLOBAL_VARIABLE_CREATED| Created global variable %s, RELEASE_EDIT) |
| GLOBAL_VARIABLE_VALUE_UPDATED| Changed value of global variable %s from '%s' to '%s', RELEASE_EDIT) |
| GLOBAL_VARIABLE_DELETED| Deleted global variable %s, RELEASE_EDIT) |
| RELEASE_RISK_PROFILE_UPDATED| Changed risk profile from '%s' to '%s', RELEASE_EDIT) |
