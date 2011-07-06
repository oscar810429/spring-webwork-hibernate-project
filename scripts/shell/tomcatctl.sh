#!/usr/bin/env bash
# stop tomcat first, then relink the symobel link 'yupoo' to lastest release, 
# and start tomcat.
#
# @author Zhang Songfu 

TOMCAT_DIR="/home2/cluster_tomcat/Tomcat6A"
STOP_SCRIPT="$TOMCAT_DIR"/bin/shutdown.sh
START_SCRIPT="$TOMCAT_DIR"/bin/startup.sh

DEPLOY_DIR="/home2/workspace/mingda"
RELEASES_DIR="$DEPLOY_DIR"/web
SYMLINK="mingda"

running=false

check_running() {
	local pid=`ps -C java -o pid=`
	if [ -z "$pid" ]; then
		running=false
	else
		running=true
	fi
}

check_running

if $running; then
	echo "Tomcat currently is running."
else
	echo "Tomcat currently is not running."
fi

start() {
	if $running; then
		echo "Tomcat is already running!"
		exit 1
	fi
	
	echo "Start tomcat ...."
	
	$START_SCRIPT
}

stop() {
	if !$running; then
		echo "Tomcat is not running!"
		exit 1
	fi

	echo "Stoping tomcat ...."
	
	$STOP_SCRIPT
	
	while $running; do
		check_running
		sleep 1
	done
	
	echo "Tomcat is successfully stopped."
}

update_link() {
	local last=""
	# find the newest release
	for release in "$RELEASES_DIR"/*; do
		if [ -z "$last" -o "$release" -nt "$last" ]; then
			last="$release"
		fi
	done
	
	if [ -z "$last" ]; then
		echo "No release to update!"
		exit 1
	fi
	
	echo "Update symbel link ${SYMLINK} -> ${last}"
	
	# relink the symbel link
	cd $DEPLOY_DIR
	rm -f $SYMLINK
	ln -nfs "$last" $SYMLINK
}

restart() {
	stop
	update_link
	start
}

if [ $# -ne 1 ]; then
	restart
else
	case "${1}" in
	    start)
	        start
			;;
	    stop)
	        stop
	        ;;
	    update)
	        update_link
	        ;;
	    restart)
	        restart
	        ;;
		*)
			echo "Please specify start, stop, update or restart as the arg"
			exit 1
			;;
	esac
fi
