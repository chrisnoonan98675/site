---
title: Import infrastructure items using the XL Deploy CLI
categories:
- xl-deploy
tags:
- repository
- infrastructure
---

If you define the machines in your infrastructure in a comma-separated values (CSV) file, you can easily import them into XL Deploy's repository using the [command-line interface](http://docs.xebialabs.com/releases/latest/xl-deploy/climanual.html) (CLI).

First, format your CSV file with a name property and add a type column. For example:

	name,address,username,password,temporaryDirectoryPath,type,os
	machine1,192.168.0.31,deploy1,azerty,/tmp,overthere.SshHost,UNIX
	machine2,192.168.0.32,deploy2,azerty,/tmp,overthere.SshHost,UNIX
	machine3,192.168.0.33,deploy3,azerty,/tmp,overthere.SshHost,UNIX
	machine4,192.168.0.34,deploy4,azerty,/tmp,overthere.SshHost,UNIX
	machine5,192.168.0.35,deploy5,azerty,/tmp,overthere.SshHost,UNIX
	machine6,192.168.0.36,deploy6,azerty,/tmp,overthere.SshHost,UNIX
	machine7,192.168.0.37,deploy7,azerty,/tmp,overthere.SshHost,UNIX
	machine8,192.168.0.38,deploy8,azerty,/tmp,overthere.SshHost,UNIX
	machine9,192.168.0.39,deploy9,azerty,/tmp,overthere.SshHost,UNIX

In the XL Deploy CLI, execute:

	import csv;csvfile="<path-to-the-csv-file"; map( lambda r: repository.create(factory.configurationItem("Infrastructure/%s" % r['name'],r['type'],r)), csv.DictReader(open(csvfile)))
