Counts the number of nodes in a parse tree created for a Java file using antlr4.

# Example run:

```
make run2
```

The output is:

```
rm -f *.class
javac *.java
mkdir -p logs
cat input/String.java | java -Djava.util.logging.config.file=logging.properties AntlrNodeCount
Node count 33205
```

# Details
The logging location is set up in logging.properties file.

You can examine the log lines by executing:

```
tail -f logs/debug.log
```
