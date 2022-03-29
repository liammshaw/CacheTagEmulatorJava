Lab4:
	javac lab6.java

testing:
	javac lab6.java
	java lab6.java mem_stream.1 > test1.output
	java lab6.java mem_stream.2 > test2.output
	diff -w -B test1.out test1.output
	diff -w -B test2.out test2.output

