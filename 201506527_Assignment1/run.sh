#!/bin/bash
if [ $# -eq 1 ]
then
	if [ $1 == '-c' ]	
	then
		javac -cp ":./src/java-json.jar" -d bin src/com/google/protobuf/*.java src/*.java 
		echo "Compilation success"
	else
		echo "Invalid input"
	fi
elif [ $# -eq 3 ] 	
then
	java -cp :./src/java-json.jar:bin: MStudent $1 $2 $3
else
	echo "Invalid input"
fi




