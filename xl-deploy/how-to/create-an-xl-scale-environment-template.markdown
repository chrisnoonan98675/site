---
title: Create an XL Scale environment template
categories:
- xl-deploy
subject:
- XL Scale
tags:
- xl scale
- environment
- template
- virtualization
---

XL Scale can combine several hosts into a ```cloud.Environment``` CI. This type of CI behaves similar to ```udm.Environment``` and can immediately be used for application deployment. Analogous to the single-host scenario, a special CI type ```cloud.EnvironmentTemplate``` is used to define which host to create and allows the user to fine-tune the set of members which will end up in the new environment.

Environment templates are created in the Repository under the **Configuration** root node. The following configuration options are available for environment templates:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| Environment descriptor | String | XML descriptor of the middleware that is present once the host template is instantiated |
| Host templates | List of CI | The list of host templates that together make up this environment template; host templates will be instantiated in order when the environment template is instantiated |
| Environment descriptor | String | FreeMarker and XML descriptor of the middleware that is present once the host template is instantiated |

## The environment template descriptor

Analogous to the host template, the environment template also specifies a special property ```xmlDescriptor``` which describes the contents of the new environment.

The environment descriptor is a FreeMarker template which is transformed into an XML-based definition of the environment that is registered in XL Deploy after the instances are launched on the virtualization platform. <!--For more details regarding the XML format please check the [XL Deploy REST API documentation](http://docs.xebialabs.com/releases/4.0/xl-deploy/rest-api/com.xebialabs.deployit.plugin.api.udm.ConfigurationItem.html).-->

The FreeMarker template can make use of the variables described in the following table:

{:.table .table-striped}
| Variable | Type | Meaning |
| -------- | ---- | ------- |
| `environmentId` | String | ID of the future environment; this parameter is entered during control task invocation |
| `environmentTemplate` | `cloud.EnvironmentTemplate` | Reference to this template; allows reuse of some of its properties |
| `hosts` | `Set<overthere.Host>` | Set of hosts that were created as a result of instantiating the environment template |

### Sample environment descriptor

Here is an example of an environment descriptor:

    <#escape x as x?xml>
      <list>
        <cloud.Environment id="${environmentId}">
          <members>
            <#list hosts as h>
              <ci ref="${h.id}" />
              <#if h.name?starts_with("Apache")>
                <ci ref="${h.id}/httpd" />
              </#if>
            </#list>
          </members>
        </cloud.Environment>
      </list>
    </#escape>

Please note that:

* You should always use ```<list>``` as a parent XML element even if you define only one CI.
* ```validateDescriptor``` task processes the template with an empty set as a value of the ```hosts``` variable.
* It is possible to include other CIs in addition to the environment, such as dictionaries.
* You must properly quote substituted values for XML output.

## Validating the environment template descriptor

Every ```cloud.EnvironmentTemplate``` CI defines the ```validateDescriptor``` control task which processes the FreeMarker template, parses the resulting XML and reports errors if something is wrong. No actual changes are made to the repository during this control task execution.
