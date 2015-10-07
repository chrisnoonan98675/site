---
title: Rescan the rules file
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- step
since:
- XL Deploy 4.5.0
---

XL Deploy [rules](/xl-deploy/concept/understanding-xl-deploy-rules.html) allow you to use XML or Jython to specify the steps that belong in a deployment plan and how the steps are configured. You define and disable rules in `xl-rules.xml`, which is stored in the `<XLDEPLOY_HOME>/ext` directory.

You can configure XL Deploy to rescan all rules on the server whenever you change the `<XLDEPLOY_HOME>/ext/xl-rules.xml` file. To do so, edit the `file-watch` setting in the `<XLDEPLOY_HOME>/conf/planner.conf` file. For example, to check every 5 seconds if `xl-rules.xml` has been modified:
    
    xl {
        file-watch {
            interval = 5 seconds
        }
    }

By default, the interval is set to 0 seconds. This means that XL Deploy will not automatically rescan the rules when `<XLDEPLOY_HOME>/ext/xl-rules.xml` changes.

If XL Deploy is configured to automatically rescan the rules and it finds that `xl-rules.xml` has been modified, it will rescan all rules in the system. By auto-reloading the rules, it is easy to do some experimenting until you are satisfied with your set of rules.

**Note:** If you modify `planner.conf`, you must restart the XL Deploy server.
