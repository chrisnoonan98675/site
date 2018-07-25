---
title: Locate vulnerable deployed artifacts
categories:
xl-deploy
subject:
Audit
tags:
audit
cli
---

Sometimes, it is necessary to determine all instances of an artifact that has been deployed; for example, if a particular open source library that your application uses has been found to be vulnerable. This topic describes a method for locating artifacts using the [XL Deploy command-line interface (CLI)](/xl-deploy/concept/getting-started-with-the-xl-deploy-cli.html).

[This CLI script](https://gist.github.com/xlcommunity/fb2f63dcf4f118dd07725d8b761c55b8) will search for all deployed packages that contain a vulnerable file that you specify.

To use the script, save it as a `.py` file in the `XL_DEPLOY_CLI_HOME/bin` directory. Execute the following command, supplying any log-in information:

    ./cli.sh -q -f $(pwd)/<script>.py <artifact>

For example, if you named the script `find-vulnerable-deployed-component.py` and you want to search for a file called PetClinic-1.0.ear, execute:

    ./cli.sh -q -f $(pwd)/find-vulnerable-deployed-component.py PetClinic-1.0.ear

This is an example of the report that will be produced:

    Searching for uses of vulnerable file [PetClinic-1.0.ear]


	Vulnerability found in application [Applications/PetClinic-ear/1.0] deployed to [Environments/Ops/Acc/ACC] because of  [jcr:PetClinic-1.0.ear]
	Vulnerability found in application [Applications/PetPortal/2.1-2] deployed to [Environments/Dev/TEST] because of  [jcr:PetClinic-1.0.ear]
	Vulnerability found in application [Applications/PetPortal/2.1-2] deployed to [Environments/Ops/Acc/ACC] because of  [jcr:PetClinic-1.0.ear]
	Vulnerability found in application [Applications/PetPortal/2.1-2] deployed to [Environments/Ops/Prod/PROD] because of  [jcr:PetClinic-1.0.ear]


	The following infrastructure is affected by this vulnerability


	HOST ID                                        |  ADDRESS   
	=============================================  |  ==========
	Infrastructure/Dev/Appserver-1                 |  jboss1    
	Infrastructure/Dev/DevServer-1                 |  LOCALHOST
	Infrastructure/Ops/North/Acc/Appserver-1       |  LOCALHOST
	Infrastructure/Ops/North/Prod/Appserver-1      |  LOCALHOST
	Infrastructure/Ops/North/Prod/Appserver-3      |  LOCALHOST
	Infrastructure/Ops/South/Acc/Appserver-2       |  LOCALHOST
	Infrastructure/Ops/South/Prod/Appserver-2      |  LOCALHOST
	Infrastructure/Ops/South/Prod/Appserver-4      |  LOCALHOST
