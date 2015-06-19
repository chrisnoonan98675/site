---
title: Define a rule
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- step
since:
- 4.5.0
---

XL Deploy [rules](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html) allow you to use XML or Jython to specify the steps that belong in a deployment plan and how the steps are configured.

You define and disable rules in `xl-rules.xml`, which is stored in the `<XLDEPLOY_HOME>/ext` directory. XL Deploy plugin JARs can also contain `xl-rules.xml`. 

Each rule:
 
* Must have a name that is unique across the whole system
* Must have a [scope](/xl-deploy/concept/understanding-xl-deploy-rule-scope.html)
* Must define the conditions under which it will run
* Can use the planning context to influence the resulting plan
 
The `xl-rules.xml` file has the default namespace `xmlns="http://www.xebialabs.com/deployit/xl-rules"`. The root element must be `rules`, under which `rule` and `disable-rule` elements are located.

When the XL Deploy server starts, it scans all `xl-rules.xml` files and registers their rules. Refer to [Rescan the rules file](/xl-deploy/how-to/rescan-the-rules-file.html) for information about rescanning `xl-rules.xml` after you make changes.