---
title: Using the XL Release Time trigger plugin
categories:
- xl-release
subject:
- Triggers
tags:
- plugin
- schedule
- time
- trigger
---

The XL Release Time trigger plugin allows XL Release to trigger releases based on a user-defined time schedule. 

## Features

* Trigger releases on fixed rate interval
* Trigger releases on a cron expression

## Add a time trigger to a template

To create a time trigger:

1. Add a trigger to the template, as described in [Create a release trigger](/xl-release/how-to/create-a-release-trigger.html).
2. Select the type of schedule from the **Schedule type** list:
    * **REPEAT**: A repeatable interval in seconds
    * **CRON**: A cron expression
3. In the **Schedule** box, enter the interval length in seconds or the cron expression.
5. Finish saving the trigger, as described in [Create a release trigger](/xl-release/how-to/create-a-release-trigger.html).

## Output properties

The output of the SVN trigger is a **Trigger Time**, which is the timestamp of the last trigger activation in ISO-8601 format.

## Triggering on a cron expression

The cron pattern is a list of six fields representing second, minute, hour, day, month, weekday, each separated by a single space. Month and weekday names can be given as the first three letters of the English names.

{:.table .table-striped}
| Field | Allowed values | Special characters |
| ----- | -------------- | ------------------ |
| Second | 0-59 | `, - * /` |
| Minute | 0-59 | `, - * /` |
| Hours | 0-23 | `, - * /` |
| Day | 1-31 | `, - * ?` |
| Month | 1-12 or JAN-DEC | `, - * /` |
| Day of the week | 1-7 or SUN-SAT | `, - * ?` |

### Time zone for cron jobs

In XL Release 4.7.0 and earlier, the time zone of the XL Release server is used for all cron jobs. In XL Release 4.8.0 and later, the default time zone for cron jobs is Coordinated Universal Time (UTC). To set a different time zone:

1. Open `<XLRELEASE_SERVER_HOME>/conf/reference.conf`, or create the file if it does not exist.
1. Set the `akka.quartz.defaultTimezone` property to the desired time zone. For example, to use Eastern Standard Time, set:

        akka.quartz {
            defaultTimezone = EST
        }

1. Save the file and restart XL Release.

### Special characters

{:.table .table-striped}
| Character | Meaning |
| --------- | ------- |
| `*` | Select all values within a field. For example, `*` in the minute field means "every minute". |
| `?` | Allowed for the day and day of week fields. It is used to specify "no specific value". This is useful when you need to specify something in one of the two fields, but not the other. |
| `-` | Used to specify ranges. For example, `9-12` in the hour field means "the hours 9, 10, 11 and 12". |
| `,` | Used to specify additional values. For example, `MON,WED,FRI` in the Day of week field means "the days Monday, Wednesday, and Friday". |
| `/` | Used to specify increments. For example, `0/15` in the seconds field means "the seconds 0, 15, 30, and 45", and `10/15` in the seconds field means "the seconds 10, 25, 40, and 55". |
| '#' | Used to specify “the nth” weekday of the month. For example, `MON#1` means "the first Monday of the month" (supported in XL Release 5.0.1 and later) |

### Sample patterns

* `0 0 * * * *` = the top of every hour of every day.
* `*/10 * * * * *` = every ten seconds.
* `0 0 8-10 * * *` = 8, 9 and 10 o'clock of every day.
* `0 0/30 8-10 * * *` = 8:00, 8:30, 9:00, 9:30, and 10:00 every day.
* `0 0 9-17 * * MON-FRI` = on the hour nine-to-five weekdays
* `0 0 0 25 12 ?` = every Christmas Day at midnight
* `0 0 0 ? * MON#1` = every first Monday of the month (supported in XL Release 5.0.1 and later)
