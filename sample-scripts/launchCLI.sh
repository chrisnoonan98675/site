#!/bin/sh
# Script used to automatically create composite packages from the last (sorted) available version
# Nicolas Bouvrette, nbouvrette@expedia.com, December 2013

# Defining variables
deployitPath="/opt/xebialabs/deployit"
deployitVersion="3.9.3"
automationPath="${deployitPath}/automation"
deployitCli="${deployitPath}/deployit-${deployitVersion}-cli/bin/cli.sh"
cliHost="localhost"
cliPort="4516"
cliSecure=""
cliExec="${deployitCli} `cat ${automationPath}/cli.properties` -host ${cliHost} -port ${cliPort} ${cliSecure} -q -f"
echo $cliExec
logfile="${automationPath}/logs/composite.log"

# Start script
printf "$(date +%F\ %T) - Executing $0\n" >> $logfile 2>> $logfile
printf "\n" >> $logfile

# Create Composite Package
$cliExec ${automationPath}/createcomposite.py Application-Composite Application1 Application2 Application3 >> $logfile 2>> $logfile

# End script
printf "$(date +%F\ %T) - Completed $0\n" >> $logfile 2>> $logfile
printf "\n" >> $logfile

logrotate ${automationPath}/conf/logrotate.conf