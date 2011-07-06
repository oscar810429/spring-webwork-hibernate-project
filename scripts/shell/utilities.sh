#!/usr/bin/env bash
# Shell Utilities
#
# @author Zhang Songfu 

safe_mkdir() {
	if [ -z "$1" ]; then
		echo "can not create empty dir"
		return
	fi
	if [ -d "$1" ]; then
		echo "${1} exists"
	else
		mkdir "$1"
		echo "${1} created"
	fi
}

dir_exists() {
	if [ -z "$2" -a -d "$2" ]; then
		echo "true"
	else
		echo "false"
	fi
}

file_exists() {
	if [ -z "$2" -a -f "$2" ]; then
		echo "true"
	else
		echo "false"
	fi
}

case "${1}" in
	dir_exists)
		dir_exists
		;;
	file_exists)
		file_exists
		;;
	safe_mkdir)
		safe_mkdir $2
		;;	
	*)
		echo "Please specify dir_exists, file_exists or safe_mkdir as the arg"
		exit 1
		;;
esac