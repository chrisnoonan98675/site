---
title: Using risk awareness in XL Release  
categories:
- xl-release
subject:
- Risk Awareness
tags:
- release
- risk aware
- flag
since:
- XL Release 6.2.0
---

XL Release calculates a risk level for each release based on different factors such as flags, failed or failing states, or due dates. This allows you to see which releases have a high risk level so you can take the appropriate actions.

You can see if a release has a high risk level in the [release overview](/xl-release/how-to/using-the-release-overview.html). An icon next to the release indicates the risk level. Click the icon to see the detailed risk information.

![image](/images/at-risk.png) - Shows **At risk** state, the reason (Example: `Release has tasks flagged as red.`), and a message (Example: `Task name: Flag comment`)

![image](/images/attention-needed.png) - Shows **Attention needed** state, the reason (Example: `Release has tasks flagged as amber.`), and a message (Example: `Task name: Flag comment`)

![image](/images/on-track.png) - Shows **On track** state - The release is on track.

## Risk assessors

The risk assessors used to calculate the risk level of a release are the following:

* Release flags: Red or Amber
* Release state: Failed or Failing
* Task flags: Red or Amber
* Release past due date
* Current task past due date
* Retries for a failed task

## The risk threshold:

{:.table table-striped}
| Flag | Score |
| ----- | ----------- |
| Red | 50 - 100 |
| Amber | 10 -49 |
| Green | < 10 |

## The risk score:

{:.table table-striped}
| Risk assessor | Score |
| ----- | ----------- |
| Release flags | Red - 80 |
| | Amber - 30 |
| Release state | Failed - 90 |
| | Failing - 70 |
| Task red flag | Red |
| | * 1 red flag: 65 |
| | * 2-3 red flags: 70 |
| | * 4-6 red flags: 75 |
| | * >6 red flags: 80 |
| Task amber flag | amber |
| | * 1 amber flag: 10 |
| | * 2-3 amber flags: 20 |
| | * 4-6 amber flags: 30 |
| | * >6 amber flags: 40 |
| Release past due date | 30 |
| Task past due date | 1 task - 25 |
| | >1 task - 35 |
| Retries for failed task | Task failed once - 50 |
| | Risk score increases by 10 for each subsequent task failure |
| | When task failed more than 6 times, the score is 100 |

**Important** To order the release overview by risk level, click **Order by** and select **Risk**. The sorting is done based on risk calculation, displaying the release with the maximum risk score at the top. If multiple releases have the same maximum risk score, a second sorting rule is applied calculating the total risk sum for each release.
