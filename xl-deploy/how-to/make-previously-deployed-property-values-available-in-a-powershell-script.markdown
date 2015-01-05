---
title: Make previously deployed property values available in a PowerShell script
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- rules
- powershell
- ci
- deployed
---

You can use the XL Deploy rules system (introduced in XL Deploy 4.5.0) and a PowerShell script to find and update the value of a previously deployed property with a new deployed property value.

For example, as a part of your deployment, you might copy a property value that changes with each deployment (such as a build version) into a file. The next time that you run the deployment, you would need to search the file for the previous value and replace it with the new value.

This example shows how you can retrieve the previously deployed property value from the current deployment.

1. Create a rule in `xl-rules.xml` with the condition `MODIFY`.
1. In the `powershell-context` tag, add:

        <previousDeployed expression="true">delta.previous</previousDeployed>

1. In the PowerShell script, refer to the previously deployed properties value using `$previousDeployed` and the suffix `.propertyname`. For example: 

        $previousDeployed.processModelIdleTimeout

The complete entry in `xl-rules.xml` would look like:

    <rule name="AppPoolSpec.CREATE.MODIFY" scope="deployed">
        <conditions>
            <type>iis.ApplicationPool</type>
            <operation>CREATE</operation>
            <operation>MODIFY</operation>
        </conditions>
        <steps>
            <powershell>
                <order>60</order>
                <description>Modify the hosts file</description>
                <script>previous.ps1</script>
                <powershell-context>
                    <previousDeployed expression="true">delta.previous</previousDeployed>
                    <Deployed>Deployed</Deployed>           
                </powershell-context> 
            </powershell>
        </steps>    
    </rule>

**Note:** For the initial deployment (that is, the `CREATE` operation), the `previousDeployed` property will be null.

The PowerShell script would look like:

    # Update file
    # Replace previous processModelIdleTimeout with new value in file
    $rFile = “C:\MyApp\myFile”

    if ($previousdeployed.processModelIdleTimeout) {
        (Get-Content $rFile) -replace $previousdeployed.processModelIdleTimeout, $deployed.processModelIdleTimeout| Set-Content $rFile
        Write-Host "previousDeployed.processModelIdleTimeout = " $previousDeployed.processModelIdleTimeout
    }
