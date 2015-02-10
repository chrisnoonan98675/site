---
layout: pre-rules
title: Base plugins and the deployed object
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

You can write plugins for XL Deploy that customize deployment plans and behavior. Plugins are usually created by copying the built-in [Generic](/xl-deploy/latest/genericPluginManual.html), [PowerShell](/xl-deploy/latest/powershellPluginManual.html), or [Python](/xl-deploy/latest/pythonPluginManual.html) plugin. This table provides information about what each plugin does and how it works.

<table class="table table-striped">
  <thead>
    <tr>
      <th>&nbsp;</th>
      <th>Python</th>
      <th>PowerShell</th>
      <th>Generic</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>What does the plugin work with?</td>
      <td>Valid Python files</td>
      <td>Valid PowerShell files</td>
      <td><a href="http://freemarker.org/docs/">FreeMarker</a> templates</td>
    </tr>
    <tr>
      <td>How does the plugin use them?</td>
      <td>
        <ol>
          <li>Prefixes a generated header</li>
          <li>Copies to target (possibly along with artifacts)</li>
          <li>Invokes the file</li>
        </ol>
      </td>
      <td>
        <ol>
          <li>Prefixes a generated header</li>
          <li>Copies to target (possibly along with artifacts)</li>
          <li>Invokes the file</li>
        </ol>
      </td>
      <td>
        <ol>
          <li>Resolves the template on the XL Deploy server using FreeMarker</li>
          <li>Copies to target (possibly along with artifacts)</li>
          <li>If necessary, makes the file executable</li>
          <li>Invokes the file</li>
        </ol>
      </td>
    </tr>
    <tr>
      <td>Special variables</td>
      <td>
        <ul>
          <li><code>Deployed</code></li>
          <li>(Optionally) <code>deployedApplication</code></li>
        </ul>
      </td>
      <td>
        <ul>
          <li><code>deployed</code></li>
          <li>(Optionally) <code>previousDeployed</code></li>
          <li>(Optionally) <code>deployedApplication</code></li>
        </ul>
      </td>
      <td>
        <ul>
          <li><code>deployed</code></li>
          <li>(Optionally) <code>previousDeployed</code></li>
          <li><code>step</code></li>
          <li><code>statics</code> (<a href="http://freemarker.org/docs/pgui_misc_beanwrapper.html#autoid_55">FreeMarker static models</a>)</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>Which properties can be accessed on these variables?</td>
      <td>
        <ul>
          <li>Any that are defined on the type of <code>deployed</code>/<code>deployedApplication</code></li>
          <li>A <code>deployed.file</code> property if the deployed has an artifact</li>
        </ul>
      </td>
      <td>
        <ul>
          <li>Any that are defined on the type of <code>deployed</code>/<code>previousDeployed</code>/<code>deployedApplication</code></li>
          <li>Plus a <code>deployed.file</code>/<code>previousDeployed.file</code> property if the <code>deployed</code>/<code>previousDeployed</code> has an artifact</li>
        </ul>
      </td>
      <td>
        <ul>
          <li>Any that are defined on the type of <code>deployed</code></li>
          <li>Plus a <code>deployed.file</code> property if the deployed has an artifact</li>
          <li>Plus <code>deployed.deployedApplication</code> (of type <code><a href="http://docs.xebialabs.com/releases/4.5/xl-deploy/udmcireference.html#udmdeployedapplication">udm.DeployedApplication</a></code>)</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>Expression language to work with variables</td>
      <td>Python - variables are regular Python vars</td>
      <td>PowerShell - variables are regular PowerShell vars</td>
      <td>FreeMarker - supports the usual <code>.</code> syntax for property access (as in <code>${deployed.name}</code>) as well as other expressions such as <code>[n]</code> to access the n-th element of a list or set property and <code>["foo"]</code> to access the value for key "foo" of a map property. More <a href="http://freemarker.org/docs/dgui_template_exp.html#dgui_template_exp_var">here</a>.</td>
    </tr>
  </tbody>
</table>
