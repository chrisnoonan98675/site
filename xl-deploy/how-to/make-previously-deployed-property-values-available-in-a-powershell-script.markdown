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

You can use the XL Deploy rules system (introduced in XL Deploy 4.5.0) to find and update 

Use Case: You want to find and update the previously deployed property value with the new deployed property value (example: as part of your deployment you copy a property value that changes with each deployment eg BUILD_VERSION into a file, the next time you run the deployment you need to search the file for the previous value and replace with the current value)

If you want to retrieve the previously deployed property value from your current deployment using xl-rules and powershell script please follow these instructions.

1. Create a xl-rule with condition MODIFY
2. Within <powershell-context>, create <previousDeployed expression="true">delta.previous</previousDeployed>
3. Within powershell script refer to previously deployed properties value using $previousDeployed and suffix .propertyname example: $previousDeployed.processModelIdleTimeout

xl-rule example:

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

*NOTE: For the initial deployment ie CREATE, the previousDeployed property will be null

powershell example:

    # Update file
    # Replace previous processModelIdleTimeout with new value in file
    $rFile = “C:\MyApp\myFile”

    if ($previousdeployed.processModelIdleTimeout) {
        (Get-Content $rFile) -replace $previousdeployed.processModelIdleTimeout, $deployed.processModelIdleTimeout| Set-Content $rFile
        Write-Host "previousDeployed.processModelIdleTimeout = " $previousDeployed.processModelIdleTimeout
    }
