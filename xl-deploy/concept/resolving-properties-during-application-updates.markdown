---
title: Resolving properties during application updates
categories:
- xl-deploy
subject:
- Dictionaries
tags:
- environment
- package
- placeholder
- update
since:
- 5.0.0
---

When you [update a deployed application](/xl-deploy/how-to/update-a-deployed-application.html), XL Deploy resolves the properties for the deployeds in the application.

## Resolving properties in XL Deploy 4.5.x and earlier

In XL Deploy 4.5.x and earlier, XL Deploy uses a fallback mechanism when it cannot resolve a property on a deployed. This mechanism means that XL Deploy uses the property value from the previous deployment if:

* The corresponding deployable does not have a value for the property, or
* The value was explicitly set on the deployed in the previous deployment

## Resolving properties in XL Deploy 5.0.0 and later

In XL Deploy 5.0.0 and later, XL Deploy does not use the fallback mechanism when you update a deployed application. Instead, it resolves properties in the same way that it does for the initial deployment of the application. 

In detail, this is the logic XL Deploy uses:

1. If the deployable has the property
    1. If the value of the deployable's property is not set (that is, it is null or empty collection)
        1. Try to resolve the property from the dictionary, using the `<deployed type>.<property name>` key
    1. Else if the property is set on the deployable
        1. Resolve the value from the deployable property using the dictionary
            1. If the placeholder resolution fails, set the _empty_ value on the property
            1. Else if the placeholders were resolved, set the resolved value on the property
2. Else if the deployable does not have the property and the deployed does not have a default value for the property
    2. Try to resolve the property from the dictionary using the `<deployed type>.<property name>` key
    2. If the previous step does not set a value on the deployed (that is, it is null or empty collection)
        2. Set the value from the previous deployed
3. Else use the default value for the deployed's property

**Tip:** For an in-depth look at the relationship between properties of deployables and deployeds, refer to [Understanding deployables and deployeds](/xl-deploy/concept/understanding-deployables-and-deployeds.html#what-are-the-differences-between-deployables-and-deployeds).
