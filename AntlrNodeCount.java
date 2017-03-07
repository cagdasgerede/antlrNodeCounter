//import org.antlr.v4.runtime.ParserRuleContext;
//import org.antlr.v4.runtime.tree.ErrorNode;
//import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.*;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
//import java.util.*;
//import java.io.*;

public class AntlrNodeCount extends Java8BaseListener {
	private final static Logger LOGGER = Logger.getLogger(AntlrNodeCount.class.getName());

	public static void main(String[] args) throws IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Info Log");
		AntlrNodeCount counter = new AntlrNodeCount();
		InputStream code = System.in;
		counter.count(code);
	}


	public void count(InputStream code)
	throws IOException {
		ANTLRInputStream input = new ANTLRInputStream(System.in);
		Java8Lexer lexer = new Java8Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Java8Parser parser = new Java8Parser(tokens);
		LOGGER.info("Building the parse tree...");
		long start = System.nanoTime();
		ParseTree tree = parser.compilationUnit();
		long elapsedNano = System.nanoTime() - start;
		long elapsedSec =
			TimeUnit.SECONDS.convert(elapsedNano, TimeUnit.NANOSECONDS);
		LOGGER.info(String.format(
			"Built the parse tree...(took %d seconds)", elapsedSec));

		ParseTreeWalker walker2 = new ParseTreeWalker();
		NodeCounter counter = new NodeCounter();
		walker2.walk(counter, tree);
		System.out.printf("Node count %d\n", counter.count());
	}
}

class NodeCounter extends Java8BaseListener {
	int count = 0;

	public int count() {
		return count;
	}

	@Override public void enterEveryRule(ParserRuleContext ctx) {
		count++;
	}
	@Override public void visitTerminal(TerminalNode node) {
		count++;
	}
}
