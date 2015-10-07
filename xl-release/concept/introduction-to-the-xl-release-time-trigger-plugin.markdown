---
title: Introduction to the XL Release Time trigger plugin
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

Time plugin is an XL Release plugin that allows XL Release to trigger releases based on a user defined time schedule. 

## Features

* Trigger on fixed rate interval
* Trigger on cron expression

## Usage

Input properties:

* **Schedule type**: Type of schedule as repeatable interval in seconds (i.e. each 10 seconds) or cron expression (required)
* **Schedule**: Interval in seconds (REPEAT) or as a cron expression (CRON) (required)

Output properties:

* **Trigger Time**: Last trigger activation timestamp (ISO8601)

### Trigger on fixed rate interval

To specify a trigger based on a fixed rate interval, select REPEAT as the **Schedule type** and specify the interval in seconds in the **Schedule** property.

### Trigger on cron expression

To specify a cron trigger, select CRON from the **Schedule type** dropdown and enter a valid cron pattern into the **Schedule** property. 
The cron pattern is a list of six single space-separated fields: representing second, minute, hour, day, month, weekday. Month and weekday names can be given as the first three letters of the English names.

{:.table .table-striped}
| Field | Allowed values | Special characters |
| ----- | -------------- | ------------------ |
| Second | 0-59 | `, - * /` |
| Minute | 0-59 | `, - * /` |
| Hours | 0-23 | `, - * /` |
| Day | 1-31 | `, - * ?` |
| Month | 1-12 or JAN-DEC | `, - * /` |
| Day of the week | 1-7 or SUN-SAT | `, - * ?` |

#### Special characters

{:.table .table-striped}
| Character | Meaning |
| --------- | ------- |
| `*` | Select all values within a field. For example, `*` in the minute field means 'every minute' |
| `?` | Allowed for the day and day of week fields. It is used to specify "no specific value". This is useful when you need to specify something in one of the two fields, but not the other |
| `-` | Used to specify ranges. For example, "9-12" in the hour field means "the hours 9, 10, 11 and 12" |
| `,` | Used to specify additional values. For example, "MON,WED,FRI" in the Day of week field means "the days Monday, Wednesday, and Friday" |
| `/` | Used to specify increments. For example, "0/15" in the seconds field means "the seconds 0, 15, 30, and 45". And "10/15" in the seconds field means "the seconds 10, 25, 40, and 55" |

#### Sample patterns

* `0 0 * * * *` = the top of every hour of every day.
* `*/10 * * * * *` = every ten seconds.
* `0 0 8-10 * * *` = 8, 9 and 10 o'clock of every day.
* `0 0/30 8-10 * * *` = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
* `0 0 9-17 * * MON-FRI` = on the hour nine-to-five weekdays
* `0 0 0 25 12 ?` = every Christmas Day at midnight
