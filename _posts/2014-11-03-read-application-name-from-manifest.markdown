---
title: Read the application name from the deployment package manifest
categories:
- xl-deploy
tags:
- application
- package
---

This XL Deploy command-line interface (CLI) script will read the application name from the `deployit-manifest.xml` file in the [application deployment package](http://docs.xebialabs.com/releases/latest/xl-deploy/packagingmanual.html#xml-manifest-format) (DAR file).

	from java.util.jar import JarFile

	from org.dom4j.io import SAXReader

	def searchIfApplicationExist(darFile):
		jf = JarFile(darFile)
		appName=getApplicationName(jf.getInputStream(jf.getJarEntry("deployit-manifest.xml")))
		appIds =filter(lambda id: id.endswith('/%s'%appName), repository.search('udm.Application'))
		if len(appIds) == 0:
			print "Application %s not found" % (appName)

	def getApplicationName(deployitManifest):
		reader = SAXReader()
		doc = reader.read(deployitManifest)
		node = doc.selectSingleNode("//udm.DeploymentPackage")
		appName = node.valueOf("@application")
		print "appName: %s " % (appName)
		return appName

For more information about the command-line interface, see the [CLI Manual](http://docs.xebialabs.com/releases/latest/xl-deploy/climanual.html).
