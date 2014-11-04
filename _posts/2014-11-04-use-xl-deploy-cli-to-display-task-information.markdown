---
title: Use the XL Deploy CLI to display task information
categories:
- xl-deploy
tags:
- cli
- deployment
- task
---

[This XL Deploy command-line interface (CLI) script](/sample-scripts/taskinfo.cli.py) will display deployment task information. To install it:

1. Copy [the file](taskinfo.cli.py) into the `DEPLOYIT_CLI/ext` folder.
2. Run `cli.sh` with the `-expose-proxies option`.

Sample output:

    deployit > dumpTask('a11ec319-1a34-48a2-b2b9-16ea78af14b4')
    Task -- Upgrade of package:Applications/deployit-petclinic/1.0 from version:2.0 to version:1.0 to env:Environments/Demo-TomcatApache --
    State:  DONE 10 / 10
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 1  DONE  Stop Tomcat server Infrastructure/tomcat6.vm/tomcat6-1
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 2  DONE  Stop Tomcat server Infrastructure/tomcat6.vm/tomcat6-2
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 3  DONE  Wait 3000ms for Tomcat server to complete the step
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 4  DONE  Undeploy WAR file Applications/deployit-petclinic/2.0/PetClinic.war from Tomcat server Infrastructure/tomcat6.vm/tomcat6-2
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 5  DONE  Undeploy WAR file Applications/deployit-petclinic/2.0/PetClinic.war from Tomcat server Infrastructure/tomcat6.vm/tomcat6-1
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 6  DONE  Deploy WAR file Applications/deployit-petclinic/1.0/PetClinic.war to Tomcat server Infrastructure/tomcat6.vm/tomcat6-2
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 7  DONE  Deploy WAR file Applications/deployit-petclinic/1.0/PetClinic.war to Tomcat server Infrastructure/tomcat6.vm/tomcat6-1
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 8  DONE  Starting Tomcat server Infrastructure/tomcat6.vm/tomcat6-1
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 9  DONE  Starting Tomcat server Infrastructure/tomcat6.vm/tomcat6-2
    a11ec319-1a34-48a2-b2b9-16ea78af14b4 # 10  DONE  Wait 3000ms for Tomcat server to complete the step
