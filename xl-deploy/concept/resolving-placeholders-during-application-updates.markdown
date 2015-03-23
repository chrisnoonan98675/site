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

In XL Deploy 4.5.x and earlier, XL Deploy uses a fallback mechanism when it cannot resolve a property on a deployed. This mechanism means that XL Deploy uses the value from the previous deployment if:

* The corresponding deployable does not have a value for the property, or
* The value was explicitly set on the deployed in the previous deployment

## Resolving properties in XL Deploy 5.0.0 and later

In XL Deploy 5.0.0 and later, when XL Deploy cannot resolve a property on a deployed, it uses the value from the previous deployment only if the value was explicitly set on the deployed.

If the property cannot be resolved and it was not explicitly set, XL Deploy shows an error instead of falling back to the previously-used value. You can correct the error by providing a value for the property on the deployed.

### Detailed logic

In detail, this is the logic that XL Deploy uses to determine the value to use for a deployed property:

1. If the property exists in the deployable:
    1. If the deployable's property does not have a value:
        1. XL Deploy tries to resolve the property from the dictionary (using the `<deployedtype>.<propertyname>` key).
    1. If the deployable's property has a value:
        1. Resolve the value from the deployable property using the dictionary.
            1. If placeholder resolution fails, set the property to *empty*.
            1. If placeholder resolution succeeds, set the property to the resolved value.
1. If the property *does not exist* in the deployable *and* the deployed does not have a default value for the property:
    1. Try to resolve the value from the dictionary (using the `<deployedtype>.<propertyname>` key).
    1. If the previous step does not succeed (so the property is still not set):
        1. Set the property to the value from the previous deployed.
1. Otherwise, use the default value for the property of the deployed.
