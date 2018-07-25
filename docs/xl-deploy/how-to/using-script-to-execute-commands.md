---
title: Using a script to execute commands as part of a deployment
categories:
xl-deploy
subject:
Rules
tags:
rules
script
commands
---

## Execute commands as part of a deployment

1. Create a new deployable type that uses a script that containing the commands you want to run. Add this definition to your `<XL_DEPLOY>/ext/synthetic.xml` file to create the new deployable and include it in your DAR file:

        <type type="demoscript.deployed" deployable-type="demoscript.deployable" extends="udm.BaseDeployed" container-type="overthere.Host">
               <generate-deployable type="demoscript.deployable" extends="udm.BaseDeployable" />
               <property name="userDirectory" />
               <property name="runCommandOrNot" kind="boolean" />
        </type>

1. Define the behaviors for the new deployable such as: the order, the script to run, the expression to check Boolean, etc. Add these definitions to the `<XL_DEPLOY>/ext/xl-rules.xml` file:

        <rule name="demoscript.rules_CREATEMODIFY" scope="deployed">
             <conditions>
              <type>demoscript.deployed</type>
              <operation>CREATE</operation>
              <operation>MODIFY</operation>
              <expression> deployed.runCommandOrNot == True</expression>
             </conditions>
             <steps>
                <os-script>
                    <description expression="true">"user said " + str(deployed.runCommandOrNot)</description>
                    <order>70</order>
                    <script>acme/demoscript</script>
                </os-script>
             </steps>
        </rule>
        <rule name="demoscript.rules_DESTROY" scope="deployed">
             <conditions>
              <type>demoscript.deployed</type>
              <operation>DESTROY</operation>
             </conditions>
             <steps>
                <os-script>
                    <description>Demoscript Rolling back</description>
                    <order>70</order>
                    <script>acme/demoscript-rollback</script>
                </os-script>
             </steps>
        </rule>   

1. Create the script containing the commands you want to run. Sample of a deployment script `<XL_DEPLOY>/ext/scripts/demoscript.sh.ftl`:

        cd ${deployed.userDirectory}

        dir            

2. XL Deploy has rollback options, so consider what you want to run during a rollback. Sample of a rollback script `<XL_DEPLOY>/ext/scripts/demoscript-rollback.sh.ftl`:

        cd ${deployed.userDirectory}

        echo `ls -altr`        

**Note:**  If you want to use this functionality for both Windows and Unix/Linux operating systems, you must add the  demoscript.bat.ftl and demoscript.bat.ftl scripts to your `<XL_DEPLOY>/ext/scripts` folder.        
