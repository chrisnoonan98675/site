---
title: Troubleshoot XL Scale
subject:
- XL Scale
category:
- xl-deploy
tags:
- troubleshooting
- xl scale
- virtualization
---

{:.table .table-striped}
| Problem | Solution |
| ------- | -------- |
| Error during descriptor validation: `Can not process descriptor template. Expression x is undefined on line 1, column XXX in template`. | You are using an expression or variable, which is undefined in the FreeMarker scope. If your template is surrounded by ```#escape``` tags, remove these and try again to to pinpoint the error. |
| Error during descriptor validation: `Can not process descriptor template. Expression XXX is undefined on line YYY, column ZZZ in template.` | You are using an expression or variable, which is undefined in the FreeMarker scope. Please check the manual of the appropriate plugin for the list of available variables and their types. |
| Error during descriptor validation: `Can not parse generated XML. Encountered unknown ConfigurationItem property [XXX] for ci [YYY]` | One of CIs that is defined in your XML descriptor has an unknown field. Please check the CI reference for the plugin defining the CI type you are using. |
| Error during Create Environment: `You do not have the required permission to perform this task. Permission CONTROLTASK_EXECUTE on [Configuration/XXX] is not granted to you.` | You do not have the permission `controltask#execute` set on the environment template CI |
| Error during Destroy Environment: `You do not have the required permission to perform this task. Permission CONTROLTASK_EXECUTE on [Environments/XXX] is not granted to you.` | You do not have the permission `controltask#execute` set on the environment CI |
