---
title: Extend the XL Deploy Database plugin
categories:
- xl-deploy
subject:
- Bundled plugins
tags:
- plugin
- database
- sql
- rules
since:
- XL Deploy 5.0.0
weight: 354
---

In XL Deploy 5.0.0 and later, the Database plugin uses the XL Deploy [rules system](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html) to provide improved rollback support for SQL scripts.

For backward compatibility reasons, improved rollback support is not automatically available for custom CI types that were created in earlier versions of the plugin, and that are based on the `sql.SqlScripts` CI type. However, you can implement this support for custom types by adding rules to the `XL_DEPLOY_SERVER_HOME/ext/xl-rules.xml` file.

**Note:** If you have not created custom CI types in the Database plugin, you do not need to add these rules.

Add the following rules for each custom CI type that is based on `sql.SqlScripts`, replacing `custom.SqlScripts` with the name of your custom type:

    <rules>
      <disable-rule name="custom.SqlScripts.executeCreate_CREATE" />
      <disable-rule name="custom.SqlScripts.executeDestroy_DESTROY" />
      <disable-rule name="custom.SqlScripts.executeModify_MODIFY" />

      <rule name="rules_custom.SqlScripts.CREATE">
        <conditions>
            <type>custom.SqlScripts</type>
            <operation>CREATE</operation>
        </conditions>
        <planning-script-path>rules/sql_create.py</planning-script-path>
      </rule>
      <rule name="rules_custom.SqlScripts.MODIFY">
        <conditions>
            <type>custom.SqlScripts</type>
            <operation>MODIFY</operation>
        </conditions>
        <planning-script-path>rules/sql_modify.py</planning-script-path>
      </rule>
      <rule name="rules_custom.SqlScripts.DESTROY">
        <conditions>
            <type>custom.SqlScripts</type>
            <operation>DESTROY</operation>
        </conditions>
        <planning-script-path>rules/sql_destroy.py</planning-script-path>
      </rule>
    </rules>
