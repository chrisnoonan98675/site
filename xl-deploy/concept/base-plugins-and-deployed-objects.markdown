---
title: Base Plugins and The Deployed Object
categories: 
- xl-deploy
subject:
- Plugins
tags:
- plugin
- powershell
- generic
- python
---

If you create or are working on a plugin based on the [Generic](/xl-deploy/latest/genericPluginManual.html), [PowerShell](/xl-deploy/latest/powershellPluginManual.html), or [Python](/xl-deploy/latest/pythonPluginManual.html) plugin the following table should give you a useful insight into what the plugins actually do.


## Python, PowerShell, and Generic Plugin Cheat Sheet

<table border="1" class="table table-striped table-bordered table-hover table-condensed">
  <thead>
    <tr class="sortableHeader">
      <th data-column="0">
        <div class="tablesorter-header-inner">
          &nbsp;
        </div>
      </th>

      <th data-column="1">
        <div class="tablesorter-header-inner">
          Python
        </div>
      </th>

      <th data-column="2">
        <div class="tablesorter-header-inner">
          PowerShell
        </div>
      </th>

      <th data-column="3">
        <div class="tablesorter-header-inner">
          Generic
        </div>
      </th>
    </tr>
  </thead>

  <tbody>
    <tr>
      <td ><em>What does the plugin work with?</em></td>

      <td >valid Python files</td>

      <td >valid PowerShell files</td>

      <td ><a href="http://freemarker.org/docs/" class=
      "external-link" rel="nofollow">FreeMarker</a> templates</td>
    </tr>

    <tr>
      <td colspan="1" ><em>How does the plugin use them?</em></td>

      <td colspan="1" >
        <ol>
          <li>prefixes a generated header</li>

          <li>copies to target (possibly along with artifacts)</li>

          <li>invokes the file</li>
        </ol>
      </td>

      <td colspan="1" >
        <ol>
          <li>prefixes a generated header</li>

          <li>copies to target (possibly along with artifacts)</li>

          <li>invokes the file</li>
        </ol>
      </td>

      <td colspan="1" >
        <ol>
          <li>resolves the template on the XLD server using FreeMarker</li>

          <li>copies to target (possibly along with artifacts)</li>

          <li>if necessary, makes the file executable</li>

          <li>invokes the file</li>
        </ol>
      </td>
    </tr>

    <tr>
      <td colspan="1" ><em>"Special" variables</em></td>

      <td colspan="1" >
        <ul>
          <li>
            <p>deployed</p>
          </li>

          <li>
            <p>(optionally) deployedApplication</p>
          </li>
        </ul>
      </td>

      <td colspan="1" >
        <ul>
          <li>
            <p>deployed</p>
          </li>

          <li>
            <p>(optionally) previousDeployed</p>
          </li>

          <li>
            <p>(optionally) deployedApplication</p>
          </li>
        </ul>
      </td>

      <td colspan="1" >
        <ul>
          <li>
            <p>deployed</p>
          </li>

          <li>(optionally) previousDeployed</li>

          <li>step</li>

          <li>statics (<a href=
          "http://freemarker.org/docs/pgui_misc_beanwrapper.html#autoid_55" class=
          "external-link" rel="nofollow">FreeMarker static models</a>)</li>
        </ul>
      </td>
    </tr>

    <tr>
      <td colspan="1" ><em>Which properties can be accessed on
      these variables?</em></td>

      <td colspan="1" >
        <ul>
          <li>any which are defined on the type of deployed/deployedApplication</li>

          <li>a  "deployed.file" property if the deployed has an artifact</li>
        </ul>
      </td>

      <td colspan="1" >
        <ul>
          <li>any which are defined on the type of
          deployed/previousDeployed/deployedApplication</li>

          <li>plus a  "deployed.file"/"previousDeployed.file" property if the
          deployed/previousDeployed has an artifact</li>
        </ul>
      </td>

      <td colspan="1" >
        <ul>
          <li>any which are defined on the type of deployed</li>

          <li>plus a  "deployed.file" property if the deployed has an
          artifact</li>

          <li>plus "deployed.deployedApplication" (of type <a href=
          "http://docs.xebialabs.com/releases/4.5/xl-deploy/udmcireference.html#udmdeployedapplication"
          class="external-link" rel="nofollow">udm.DeployedApplication</a>)</li>

        </ul>
      </td>
    </tr>

    <tr>
      <td colspan="1" ><em>Expression language to work with
      variables</em></td>

      <td colspan="1" >Python - variables are regular Python
      vars</td>

      <td colspan="1" >PowerShell - variables are regular
      PowerShell vars</td>

      <td colspan="1" >FreeMarker - supports the usual "." syntax
      for property access (as in ${deployed.name}) as well as other expressions such as
      "[n]" to access the n-th element of a list or set property and "["foo"]" to
      access the value for key "foo" of a map property. More <a href=
      "http://freemarker.org/docs/dgui_template_exp.html#dgui_template_exp_var" class=
      "external-link" rel="nofollow">here</a>.</td>
    </tr>
  </tbody>
</table>
