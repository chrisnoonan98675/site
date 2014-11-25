#!/usr/bin/env sh

# rsync legacy docs required by jekyll
#
# NOTE: rsync will use ssh to authenticate - if you don't want to enter
# password each time rsync is executed set up key based authentication
# 
# In order to copy your public key to the tech server, on your local computer execute:
# ssh-copy-id -i ~/.ssh/id_rsa.pub tech@tech.xebialabs.com
# 
# If you have too many keys (>2 sometimes) ssh-agent will offer them to the
# target server which may reject your attemp to authenticate. To circumvent it
# you may have to add following to your .ssh/config:
# 
# Host tech.xebialabs.com
# 	User tech
#	IdentityFile ~/.ssh/id_rsa
# 	IdentitiesOnly yes	
#
# Alternatively we might select key via:
#
# rsync -razv -e "ssh -i /where/is/my/id_rsa" ...

XLDOC_HOME=$1
if [ -z $XLDOC_HOME ]; then
	XLDOC_HOME=_site
fi

# XLD
rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/3.9/deployit/ $XLDOC_HOME/xl-deploy/3.9.x/
rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/4.0/deployit/ $XLDOC_HOME/xl-deploy/4.0.x/
rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/4.5/xl-deploy/ $XLDOC_HOME/xl-deploy/4.5.x/

# XLD plugins
for i in bamboo-xl-deploy-plugin bigip-plugin biztalk-plugin glassfish-plugin iis-plugin jbossas-plugin jbossdm-plugin netscaler-plugin osb-plugin tfs-plugin tomcat-plugin was-plugin was-plugin-extensions windows-plugin wls-plugin wmq-plugin wp-plugin wps-plugin; do 
	rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/4.0/${i} $XLDOC_HOME/xl-deploy/4.0.x/plugins
done

for i in bamboo-xl-deploy-plugin bigip-plugin biztalk-plugin cloud-plugin ec2-plugin glassfish-plugin iis-plugin jbossas-plugin jbossdm-plugin netscaler-plugin osb-plugin tfs-plugin tomcat-plugini vsphere-plugin was-plugin was-plugin-extensions windows-plugin wls-plugin wmq-plugin wp-plugin wps-plugin; do 
	rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/3.9/${i} $XLDOC_HOME/xl-deploy/3.9.x/plugins
done

for i in bamboo-xl-deploy-plugin bigip-plugin jbossdm-plugin netscaler-plugin osb-plugin tfs-plugin tomcat-plugin was-plugin was-plugin-extensions wls-plugin wmq-plugin wps-plugin; do 
	rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/4.5/${i} $XLDOC_HOME/xl-deploy/4.5.x/plugins
done

# XLR
rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/4.0/xl-release/ $XLDOC_HOME/xl-release/4.0.x/
rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/4.0/xl-release-server/rest-api $XLDOC_HOME/xl-release/4.0.x/

# XLR plugins
rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/4.0/xlr-*-plugin $XLDOC_HOME/xl-release/4.0.x/plugins/

# XL scale
mkdir -p "$XLDOC_HOME/xl-scale/4.0.x/plugins"
rsync -razv --chmod=u=rwX tech@tech.xebialabs.com:/home/tech/www/xl-online-docs/releases/4.0/xl-scale-*plugin $XLDOC_HOME/xl-scale/4.0.x/plugins/

# XL test


