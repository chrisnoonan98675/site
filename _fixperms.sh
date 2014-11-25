#!/usr/bin/env sh

XLDOC_HOME=$1
if [ -z $XLDOC_HOME ]; then
	XLDOC_HOME=_site
fi

find $XLDOC_HOME -type d | xargs -i chmod go=rx '{}'
find $XLDOC_HOME -type f | xargs -i chmod go=r  '{}'

