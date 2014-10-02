---
title: Search applications, environments, and hosts using the XL Deploy CLI
categories:
- xl-deploy
tags:
- CLI
- application
- environment
- host
---

This script enables you to use the XL Deploy [command-line interface](http://docs.xebialabs.com/releases/latest/xl-deploy/climanual.html) (CLI) to search for applications, environments, and hosts that are stored in your XL Deploy repository.

To use it:

1. Copy the script below and save it as `searchRepo.py` in the `ext` directory in your XL Deploy home directory.
2. Start the XL Deploy CLI with the `-expose-proxies` option.

To search for applications with the command `findApp("testApp")`. Replace `testApp` with the ID of the application that you want to find.

To search for environments with the command `findEnvironment("testEnv")`. Replace `testEnv` with the ID of the environment that you want to find.

To search for hosts with the command `findHost("testCifsHost", "overthere.CifsHost")`. Replace `testCifsHost` with the name of the host that you want to find, and `overthere.CifsHost` with its type.

## Script

	from com.xebialabs.deployit.plugin.api.reflect import Type 
	def findApp(appNameCriteria):
  
	  apps = proxies.getRepository().query(Type.valueOf("udm.Application"), None, appNameCriteria, None, None, 0, -1)
	  apps = [str(appName.id) for appName in apps if appName.id.startswith("Application")]
	  if apps:
		print "Found the following applications matching criteria %s" % (appNameCriteria)
		print apps
	  else:
		print "No applications found for search criteria %s " % appNameCriteria


	def findHost(hostNameCriteria,hostType):
  
	  hosts = proxies.getRepository().query(Type.valueOf(hostType), None, hostNameCriteria, None, None, 0, -1)
	  host = [str(hostName.id) for hostName in hosts if hostName.id.startswith("Infrastructure")]
	  if host:
		print "Found the following hosts matching criteria %s" % (hostNameCriteria)
		print host
	  else:
		print "No hosts found for search criteria %s " % hostNameCriteria

	def findEnvironment(envNameCriteria):
  
	  envs = proxies.getRepository().query(Type.valueOf("udm.Environment"), None, envNameCriteria, None, None, 0, -1)
	  env = [str(env.id) for env in envs if env.id.startswith("Environments")]
	  if env:
		print "Found the following Environments matching criteria %s" % (envNameCriteria)
		print env
	  else:
		print "No Environments found for search criteria %s " % envNameCriteria
