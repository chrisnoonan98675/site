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

### Gradle

    repositories {
      ...
      maven {
        url 'https://dist.xebialabs.com/public/maven2'
      }
    }
    dependencies {
      ...
      compile: "com.xebialabs.xlrelease:xlr-api:8.0.0"
    }


### Maven

    <project>
      ...
      <repositories>
        <repository>
          <id>xebialabs-dist-repo</id>
          <name>Xebialabs maven repostiroy</name>
          <url>https://dist.xebialabs.com/public/maven2</url>
        </repository>
      </repositories>
      ...
      <dependencies>
        ...
        <dependency>
          <groupId>com.xebialabs.xlrelease</groupId>
          <artifactId>xlr-api</artifactId>
          <version>8.0.0</version>
        </dependency>
      </dependencies>
      ...
    </project>

Here is a sample of the directories inside your plugin structure:

    phase-notify-plugin
    ├── META-INF/services
    │   └── com.xebialabs.xlrelease.events.XLReleaseEventListener
    └── com/example/plugin
        └── PhaseNotifiyListener.java

XL Release uses [java ServiceLoader](https://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html) to discover your custom listeners.

You must create a file with the name `com.xebialabs.xlrelease.events.XLReleaseEventListener` inside your `.jar` file under the `META-INF/services` directory.

    META-INF/services/com.xebialabs.xlrelease.events.XLReleaseEventListener

The content of the file is a line with the implementation of the `XLReleaseEventListener`, in this case:

    com.example.plugin.PhaseNotifiyListener

Now let's create a Java class `PhaseNotifiyListener` inside the `com.example.plugin` package:

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

## Using the Configuration API

It is also possible to use the [Configuration API](https://docs.xebialabs.com/jython-docs/#!/xl-release/8.0.x//service/com.xebialabs.xlrelease.api.v1.ConfigurationApi) from your java class. You just need to create a private field with the `@Resource` annotation and XL Release will inject the interface for you.

{% highlight java %}

package com.example.plugin;

import javax.annotation.Resource;

import com.xebialabs.xlrelease.api.v1.ConfigurationApi;
import com.xebialabs.xlrelease.domain.events.ActivityLogEvent;
import com.xebialabs.xlrelease.events.AsyncSubscribe;
import com.xebialabs.xlrelease.events.XLReleaseEventListener;

public class PhaseNotifiyListener implements XLReleaseEventListener {

  @Resource
  private ConfigurationApi configurationApi;

  @AsyncSubscribe
  public void notifyCriticalPhaseStarted(ActivityLogEvent event) {
    configurationApi.searchByTypeAndTitle(.......)
    ...
  }

}

{% endhighlight %}



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
| RELEASE_TAGS_UPDAsTED | Updated release tags |
| RELEASE_FLAG_STATUS_UPDATED | Changed flag status for release |
| RELEASE_FLAG_COMMENT_UPDATED | Changed flag comment on release |
| RELEASE_ABORT_RELEASE_ON_FAILURE_UPDATED | Changed 'Abort release on failure' status on status |
| COMMENT_ADDED | Added a comment on task |
| COMMENT_UPDATED | Updated comment on task |
| PHASE_CREATED | Created a phase in a release|
| PHASE_RENAMED | Renamed a phase in a release|
| PHASE_DESCRIPTION_UPDATED | Changed the description of a phase |
| PHASE_DURATION_UPDATED | Changed the duration of a phase |
| PHASE_DUE_DATE_UPDATED | Changed the due date of a phase |
| PHASE_SCHEDULED_START_DATE_UPDATED | Changed the scheduled start date of a phase |
| PHASE_COLOR_CHANGED | Changed the phase color |
| PHASE_MOVED | Moved phase |
| PHASE_DELETED | Deleted phase |
| PHASE_DUPLICATED | Duplicated phase |
| PHASE_STARTED | Started phase |
| PHASE_FAILED | Failed phase |
| PHASE_FAILING |  Phase started failing |
| PHASE_RESTARTED | Restarted phase |
| PHASE_COMPLETED | Completed phase |
| PHASE_CLOSED | Closed phase and skipped all its tasks |
| TASK_CREATED | Created task |
| TASK_MOVED_BETWEEN_CONTAINERS | Moved a task between containers |
| TASK_MOVED_WITHIN_CONTAINER | Moved a task within a container |
| TASK_DELETED | Deleted task |
| TASK_TITLE_UPDATED | Changed the title of a task |
| TASK_DESCRIPTION_UPDATED | Changed the description of a task |
| TASK_DURATION_UPDATED | Changed the duration of a task |
| TASK_OWNER_UPDATED | Changed the owner of a task |
| TASK_TASK_TEAM_UPDATED | Changed the team of a task |
| TASK_DUE_DATE_UPDATED | Changed the due date of a task |
| TASK_SCHEDULED_START_DATE_UPDATED | Changed the scheduled start date of a task |
| TASK_WAIT_FOR_SCHEDULED_START_DATE_UPDATED | Changed the wait for scheduled start date property of a task |
| TASK_FLAG_STATUS_UPDATED | Changed the flag on a task |
| TASK_FLAG_COMMENT_UPDATED | Changed the flag comment on a task |
| TASK_COPIED | Copied task |
| TASK_NOTIFICATION_ADDRESSES_UPDATED | Changed the notification addresses of recipients of the task |
| TASK_NOTIFICATION_CC_UPDATED | Changed the notification list of email addresses that receive the message as CC of a task |
| TASK_NOTIFICATION_BCC_UPDATED | Changed the notification list of email addresses that receive the message as BCC of a task |
| TASK_NOTIFICATION_REPLY_TO_UPDATED | Changed the notification email address of the reply message recipient of a task |
| TASK_NOTIFICATION_PRIORITY_UPDATED | Changed the notification email priority of a task |
| TASK_NOTIFICATION_SUBJECT_UPDATED | Changed the notification subject of a task |
| TASK_NOTIFICATION_BODY_UPDATED | Changed the notification body of a task  |
| TASK_SCRIPT_UPDATED | Changed the script of a task |
| TASK_INPUT_PROPERTY_UPDATED | Changed the input property of a task |
| TASK_INPUT_PROPERTY_PASSWORD_UPDATED | Changed the password on an input property of a task |
| TASK_OUTPUT_PROPERTIES_UPDATED | Changed the output property of a task |
| TASK_PRECONDITION_UPDATED | Changed the precondition of a task |
| TASK_TYPE_CHANGED | Changed the type of a task |
| TASK_RELEASE_TITLE_UPDATED | Changed the release title of a task |
| TASK_RELEASE_TEMPLATE_UPDATED | Changed the release template of a task |
| TASK_START_RELEASE_FLAG_UPDATED | Changed the start release flag of a task |
| TASK_RELEASE_VARIABLE_UPDATED | Changed a variable of a task |
| TASK_RELEASE_TAGS_UPDATED | Changed release tags of a task |
| TEAM_CREATED | Created team |
| TEAM_UPDATED | Updated team |
| TEAM_DELETED | Removed team |
| FOLDER_TEAM_MERGED | Merged teams and permissions from a template into a folder |
| REMOVE_TEMPLATE_TEAMS | Removed teams from template. It will inherit teams and permissions from a folder |
| GATE_CONDITION_CREATED | Created condition on a gate |
| GATE_CONDITION_TITLE_UPDATED | Updated the condition gate title |
| GATE_CONDITION_FULFILLED | Fulfilled condition on gate |
| GATE_CONDITION_UNFULFILLED | Unfulfilled condition on gate |
| GATE_CONDITION_DELETED | Deleted condition from gate |
| LINK_ADDED | Created a link between tasks on a group |
| LINK_REMOVED | Removed a link between tasks on a group |
| DEPENDENCY_CREATED | Added a dependency in a gate |
| DEPENDENCY_UPDATED | Changed a dependency in a gate |
| DEPENDENCY_DELETED | Deleted a dependency in a gate |
| RELEASE_CREATED_FROM_TEMPLATE | Created a release from a template |
| RELEASE_CREATED_FROM_CREATE_RELEASE_TASK | Created a release from a create release task and a template |
| RELEASE_CREATED_FROM_DSL | Created a release from DSL |
| RELEASE_RESTORED_FROM_REVISION | Restored a template from a revision |
| RELEASE_CREATED | Created an empty release |
| RELEASE_STARTED | Started a release |
| RELEASE_STARTED_FROM_CREATE_RELEASE_TASK | Started a release from a create release task |
| RELEASE_FAILED | Failed release |
| RELEASE_FAILING | Release started failing |
| RELEASE_RESTARTED | Restarted release |
| RELEASE_COMPLETED | Completed release |
| RELEASE_ABORTED | Aborted release |
| TASK_STARTED | Started task |
| TASK_DELAYED | Activated a task with a scheduled start date |
| TASK_DELAYED_DUE_TO_BLACKOUT | Activated a task with a scheduled start date due to a blackout |
| TASK_COMPLETED | Completed a task |
| TASK_COMPLETED_IN_ADVANCE | Completed a task in advance |
| TASK_SKIPPED | Skipped task |
| TASK_SKIPPED_IN_ADVANCE | Skipped a task in advance |
| TASK_FAILED | Failed task |
| TASK_RESTARTED | Restarted task |
| TASK_FAILING | Task started failing |
| TASK_REOPENED | Reopened task |
| TASK_WAITING_FOR_INPUT | Task requires input for variables |
| TEMPLATE_IMPORTED | Imported template |
| PERMISSIONS_UPDATED | Updated permissions |
| ATTACHMENT_ADDED | Added attachment |
| ATTACHMENT_ADDED_ON_TASK | Added attachment on a task |
| ATTACHMENT_DELETED | Deleted attachment |
| ATTACHMENT_DELETED_FROM_TASK | Deleted attachment from task |
| TRIGGER_ADDED | Added release trigger |
| TRIGGER_EDITED | Edited release trigger |
| TRIGGER_DELETED | Deleted release trigger |
| TRIGGER_CREATE_NEW_RELEASE | Trigger created a new release from a template |
| TEMPLATE_ALLOW_CONCURRENT_RELEASES_FROM_TRIGGER_UPDATED | Changed 'Allow concurrent triggered releases' template |
| RELEASE_VARIABLE_CREATED | Created release variable |
| RELEASE_VARIABLE_DELETED | Deleted release variable |
| RELEASE_VARIABLE_REPLACED | Replaced release variable |
| RELEASE_VARIABLE_RENAMED | Renamed release variable |
| RELEASE_VARIABLE_VALUE_UPDATED | Changed the value of a release variable |
| RELEASE_VARIABLE_PASSWORD_VALUE_UPDATED | Changed the value of a password variable |
| RELEASE_VARIABLE_NAME_UPDATED | Changed the name of a release variable |
| RELEASE_VARIABLE_LABEL_UPDATED | Changed the label of a release variable |
| RELEASE_VARIABLE_DESCRIPTION_UPDATED | Changed the description of a release variable |
| RELEASE_VARIABLE_REQUIRED_UPDATED | Changed the required flag of a release variable |
| RELEASE_VARIABLE_SHOW_ON_CREATE_UPDATED | Changed the 'show on create release form' flag of a release variable |
| GLOBAL_VARIABLE_CREATED | Created global variable |
| GLOBAL_VARIABLE_VALUE_UPDATED | Changed the value of a global variable |
| GLOBAL_VARIABLE_DELETED | Deleted global variable |
| RELEASE_RISK_PROFILE_UPDATED | Changed the release risk profile |
