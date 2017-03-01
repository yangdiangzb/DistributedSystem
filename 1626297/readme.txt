1.three log files:
	https://dumps.wikimedia.org/other/pagecounts-raw/2016/2016-08/pagecounts-20160801-000000.gz
	https://dumps.wikimedia.org/other/pagecounts-raw/2016/2016-08/pagecounts-20160801-010000.gz
	https://dumps.wikimedia.org/other/pagecounts-raw/2016/2016-08/pagecounts-20160801-020000.gz

	I use pagecounts-20160801-000000 for part A and use all for partB and partC.
	And I'm not sure whether I decompress it corretly cause there are some lines which only contain three attributes. Therefore, I ignore those three-attribute lines(eg. aa 2 1234).

2.About run.sh
	Please export -DIGNITE_HOME before taking any actions.
	If ./run.sh compile failed, it should be the problems of -DIGNITE_LIBS. Please help re-export -DIGNITE_LIBS.
	The configuration file I use is "examples/config/example-ignite-no-discovery.xml". Hence, it may cause some problems if examples/config/example-ignite-no-discovery.xml doesn not exist in test computer.
	Please use absolute file paths.

3.PartA
	java file and class directory: source/wordcount/src/main/java/partA
	Firstly, I open another terminal to run server node. After 7 seconds, I call StreamWords class (open another terminal) and QueryWords class simultaneously. 	

4.Part B
	java file and class directory: source/wordcount/src/main/java/partB
	Firstly, I open another terminal to run server node. After 7 seconds, I call StreamWords class three times (open three terminals) and QueryWords class simultaneously. Streamers stream three differnet log files simultaneously into same cache partB. Query will query from cache partB.
	
5.Part C
	java file and class directory: source/wordcount/src/main/java/partB
	Firstly, I open another terminal to run server node. After 7 seconds, I call StreamWords class three times to stream three differnet log files simultaneously (once) into same cache partC. Three Streamer nodes will close after streaming data into cache. Then QueryWords class will start to query from cache.

6.Query result set is empty
	For PartA and PartB, because I run streamer node and query node simultaneously, query may run before the streamer streames data to the cache. Hence, query node may output "Query result set is empty" at first. And it will output correct answers after streamer streames data to the cache.
	

	
	
	


