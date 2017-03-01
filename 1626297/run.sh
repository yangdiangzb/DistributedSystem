#!/bin/bash
#IGNITE_HOME=/afs/inf.ed.ac.uk/user/s16/s1626297/Desktop/ds/apache-ignite-fabric-1.7.0-bin
IGNITE_LIBS=$IGNITE_HOME/libs/*:$IGNITE_HOME/libs/ignite-spring/*:$IGNITE_HOME/libs/ignite-indexing/*
#compile
if [ "$1" = "compile" ]
then
	javac -cp .:$IGNITE_LIBS -source 1.8 source/wordcount/src/main/java/partA/*.java source/wordcount/src/main/java/partB/*.java source/wordcount/src/main/java/partC/*.java
#partA
elif [ "$1" = "partA" ]
then
	if [ "$2" = "" ] || [ "$3" != "" ]
	then
		echo "./run.sh partA <log_filepath> : runs part A with absolute log file path as an input"
	else
		cd source/wordcount/src/main/java
		gnome-terminal -x $IGNITE_HOME/bin/ignite.sh $IGNITE_HOME/examples/config/example-ignite-no-discovery.xml
		sleep 7
		gnome-terminal -x java -cp .:$IGNITE_LIBS -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partA.StreamWords "$2" &
		java -cp .:$IGNITE_LIBS  -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partA.QueryWords
	fi
#partB
elif [ "$1" = "partB" ]
then
	if [ "$2" = "" ] || [ "$3" = "" ] || [ "$4" = "" ] || [ "$5" != "" ]
	then
		echo "./run.sh partB <log_filepath1> <log_filepath2> <log_filepath3> : runs part B with 3 log files"
	else
		cd source/wordcount/src/main/java
		gnome-terminal -x $IGNITE_HOME/bin/ignite.sh $IGNITE_HOME/examples/config/example-ignite-no-discovery.xml
		sleep 7
		gnome-terminal -x java -cp .:$IGNITE_LIBS  -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partB.StreamWords "$2" &
		gnome-terminal -x java -cp .:$IGNITE_LIBS  -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partB.StreamWords "$3" &
		gnome-terminal -x java -cp .:$IGNITE_LIBS  -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partB.StreamWords "$4" &
		java -cp .:$IGNITE_LIBS -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partB.QueryWords
	fi
#partC
elif [ "$1" = "partC" ]
then
	if [ "$2" = "" ] || [ "$3" = "" ] || [ "$4" = "" ] || [ "$5" != "" ]
	then
		echo "./run.sh partB <log_filepath1> <log_filepath2> <log_filepath3> : runs part B with 3 log files"
	else
		cd source/wordcount/src/main/java
		gnome-terminal -x $IGNITE_HOME/bin/ignite.sh examples/config/example-ignite-no-discovery.xml
		sleep 7
	  java -cp .:$IGNITE_LIBS -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partC.StreamWords "$2" &
		java -cp .:$IGNITE_LIBS -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partC.StreamWords "$3" &
		java -cp .:$IGNITE_LIBS -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partC.StreamWords "$4";
		java -cp .:$IGNITE_LIBS -Xms512m -Xmx512m -DIGNITE_PERFORMANCE_SUGGESTIONS_DISABLED=true partC.QueryWords
	fi
#print some help messages
else
	echo "./run.sh compile : compiles the source into corresponding directory"
	echo "./run.sh partA <log_filepath> : runs part A with absolute log file path as an input"
	echo "./run.sh partB <log_filepath1> <log_filepath2> <log_filepath3> : runs part B with 3 log files"
	echo "./run.sh partC <log_filepath1> <log_filepath2> <log_filepath3> : runs part C with 3 log files"
fi

#test command
#./run.sh partA /afs/inf.ed.ac.uk/user/s16/s1626297/Desktop/resources/pagecounts-20160801-000000
#./run.sh partB /afs/inf.ed.ac.uk/user/s16/s1626297/Desktop/resources/pagecounts-20160801-000000 /afs/inf.ed.ac.uk/user/s16/s1626297/Desktop/resources/pagecounts-20160801-010000 /afs/inf.ed.ac.uk/user/s16/s1626297/Desktop/resources/pagecounts-20160801-020000
#./run.sh partC /afs/inf.ed.ac.uk/user/s16/s1626297/Desktop/resources/pagecounts-20160801-000000 /afs/inf.ed.ac.uk/user/s16/s1626297/Desktop/resources/pagecounts-20160801-010000 /afs/inf.ed.ac.uk/user/s16/s1626297/Desktop/resources/pagecounts-20160801-020000
