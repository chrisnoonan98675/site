---
title: Define a rule
categories:
xl-deploy
subject:
Rules
tags:
rules
step
since:
XL Deploy 4.5.0
weight: 126
---

XL Deploy [rules](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html) allow you to use XML or Jython to specify the steps that belong in a deployment plan and how the steps are configured. You define and disable rules in `XL_DEPLOY_SERVER_HOME/ext/xl-rules.xml`. XL Deploy plugin JAR files can also contain `xl-rules.xml` files.

The `xl-rules.xml` file has the default namespace `xmlns="http://www.xebialabs.com/deployit/xl-rules"`. The root element must be `rules`, under which `rule` and `disable-rule` elements are located.

Each rule:

* Must have a name that is unique across the whole system
* Must have a [scope](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html#how-rules-affect-one-another)
* Must define the conditions under which it will run
* Can use the planning context to influence the resulting plan

## Scanning for rules

When the XL Deploy server starts, it scans all `xl-rules.xml` files and registers their rules.

You can configure XL Deploy to rescan all rules on the server whenever you change the `XL_DEPLOY_SERVER_HOME/ext/xl-rules.xml` file. To do so, edit the `file-watch` setting in the `XL_DEPLOY_SERVER_HOME/conf/planner.conf` file. For example, to check every 5 seconds if `xl-rules.xml` has been modified:

    xl {
        file-watch {
            interval = 5 seconds
        }
    }

By default, the interval is set to 0 seconds. This means that XL Deploy will not automatically rescan the rules when `XL_DEPLOY_SERVER_HOME/ext/xl-rules.xml` changes.

If XL Deploy is configured to automatically rescan the rules and it finds that `xl-rules.xml` has been modified, it will rescan all rules in the system. By auto-reloading the rules, it is easy to do some experimenting until you are satisfied with your set of rules.

**Note:** If you modify `planner.conf`, you must restart the XL Deploy server.
