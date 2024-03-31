import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) throws Exception {
        tinyPythonLexer lexer = new tinyPythonLexer(CharStreams.fromFileName("Test.tpy"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tinyPythonParser parser = new tinyPythonParser(tokens);
        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new TP(), tree);
    }
}