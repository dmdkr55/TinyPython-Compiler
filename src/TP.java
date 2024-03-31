import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileWriter;
import java.io.IOException;

public class TP extends tinyPythonBaseListener {

    int addr = 0;  // 메모리의 위치
    String text;
    SymbolTable st;

    @Override
    public void enterProgram(tinyPythonParser.ProgramContext ctx) {
        // 클래스의 default 생성자 추가
        st = new SymbolTable();
        text = ".class public Test\n" +
                ".super java/lang/Object\n" +
                "; standard initializer\n" +
                ".method public <init>()V\n" +
                "aload_0\n" +
                "invokenonvirtual java/lang/Object/<init>()V\n" +
                "return\n" +
                ".end method\n";
    }

    @Override
    public void exitProgram(tinyPythonParser.ProgramContext ctx) {
        text += "return\n" +
                ".end method\n"; //main함수 끝
        try {
            FileWriter writer = new FileWriter("./Test.j");
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterDef_stmt(tinyPythonParser.Def_stmtContext ctx) {
        st.enterScope(); // def_stmt진입시 새로운 scope를 추가함.
        addr = 0;
        // 함수 정의 부분 추가.
        String str = ".method public static ";
        String funcName = ctx.getChild(1).getText();
        int numArgs = (ctx.getChild(3).getChildCount() + 1) / 2;
        String argsI = "";
        for (int i = 0; i < numArgs; i++) {
            argsI += "I";
        }
        str += funcName + "(" + argsI + ")I\n" +
                ".limit stack 32\n" +
                ".limit locals 32\n";
        ParseTree args = ctx.getChild(3);
        // 인자들을 Symbol Table에 추가.
        for (int i = 0; i < args.getChildCount(); i += 2) {
            String argName = args.getChild(i).getText();
            st.addVariable(argName, addr);
            addr++;
        }

        text += str;
    }

    @Override
    public void exitDef_stmt(tinyPythonParser.Def_stmtContext ctx) {
        // Def_stmt를 나갈 때, 현재 Scope를 제거.
        st.exitScope();
        text += ".end method\n";
    }

    @Override
    public void exitReturn_stmt(tinyPythonParser.Return_stmtContext ctx) {
        if (ctx.getChildCount() == 1) {
            text += "return\n";
        } else {
            text += "ireturn\n";
        }
    }

    @Override
    public void exitDefs(tinyPythonParser.DefsContext ctx) {
        // main함수 시작
        text += ".method public static main([Ljava/lang/String;)V\n" +
                ".limit stack 32\n" +
                ".limit locals 32\n";
        addr = 1;
    }

    // while문과 if문에 대해서도 scope를 추가및 삭제를 진행하였다.
    @Override
    public void enterCompound_stmt(tinyPythonParser.Compound_stmtContext ctx) {
        st.enterScope();
    }

    @Override
    public void exitCompound_stmt(tinyPythonParser.Compound_stmtContext ctx) {
        st.exitScope();
    }

    @Override
    public void exitAssignment_stmt(tinyPythonParser.Assignment_stmtContext ctx) {
        String name = ctx.getChild(0).getText();
        int existAddr = st.getVariable(name);
        if (existAddr != -1) {
            // 해당 이름의 변수가 scope 내에 선언되어있는 경우, 해당 변수의 메모리로.
            st.addVariable(name, existAddr);
            text += "istore_" + existAddr + "\n";
        } else {
            // 해당 이름의 변수가 scope 내에 존재하지 않는 경우, 새로운 메모리 할당.
            st.addVariable(name, addr);
            text += "istore_" + addr + "\n";
            addr++;
        }
    }

    // if의 형태들
    boolean if1 = false; // if만 존재할 경우
    boolean if2 = false; // if와 else만 존재
    boolean if3 = false; // if와 elif가 1개 이상 존재하고, else도 존재.
    boolean if4 = false; // if와 elif가 1개 이상 존재하고, else도 존재하지 않음.
    // 아래의 변수는 각 stmt마다 고유한 Label을 부여해주기 위함.
    int numIf_stmt = 0;
    int numWhile_stmt = 0;

    @Override
    public void enterWhile_stmt(tinyPythonParser.While_stmtContext ctx) {
        numWhile_stmt++;
        text += "loop_start" + numWhile_stmt + ":\n";
    }

    @Override
    public void exitWhile_stmt(tinyPythonParser.While_stmtContext ctx) {
        text += "loop_end" + numWhile_stmt + ":\n";
    }

    @Override
    public void exitBreak_stmt(tinyPythonParser.Break_stmtContext ctx) {
        // 해당 loop의 end로 goto.
        text += "goto loop_end" + numWhile_stmt + "\n";
    }

    @Override
    public void exitContinue_stmt(tinyPythonParser.Continue_stmtContext ctx) {
        // 해당 loop의 start로 goto.
        text += "goto loop_start" + numWhile_stmt + "\n";
    }

    @Override
    public void enterIf_stmt(tinyPythonParser.If_stmtContext ctx) {
        numIf_stmt++;
        // 각 if의 형태들을 초기화.
        if (ctx.getChildCount() == 4) {
            if1 = true;
        } else if (ctx.getChildCount() == 7) {
            if2 = true;
        } else if (ctx.getChild(ctx.getChildCount() - 3).getText().equals("else")) {
            if3 = true;
        } else {
            if4 = true;
        }
    }

    @Override
    public void exitIf_stmt(tinyPythonParser.If_stmtContext ctx) {
        if1 = false;
        if2 = false;
        if3 = false;
        if4 = false;
    }

    public static String setOperation(String op) {
        // 비교연산자에 따른 java byte code로 변환.
        // conditional branch에서 주로 not을 많이 사용하기 때문에,
        // 기존 비교연산자에 not을 적용한 형태를 반환한다.
        switch (op) {
            case "<":
                return "icmpge";
            case ">":
                return "icmple";
            case "==":
                return "icmpne";
            case ">=":
                return "icmplt";
            case "<=":
                return "icmpgt";
            case "!=":
                return "icmpeq";
        }
        return null;
    }

    public int currentIndex(ParserRuleContext ctx) {
        // 현재 ctx가 부모의 몇번째 child인지. index를 반환
        for (int i = 0; i < ctx.getParent().getChildCount(); i++) {
            if (ctx.equals(ctx.getParent().getChild(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void enterTest(tinyPythonParser.TestContext ctx) {
        // if에 형태에 맞도록 Lable를 작성
        if (if3) {
            int index = (currentIndex(ctx) - 1) / 4;
            if (ctx.getParent().getChild(currentIndex(ctx) - 1).getText().equals("elif")) {
                text += "elif" + index + "Label" + numIf_stmt + ":\n";
            } else if (ctx.getParent().getChild(currentIndex(ctx) - 1).getText().equals("else")) {
                text += "elif" + index + "Label" + numIf_stmt + ":\n";
            }
        } else if (if4) {
            int index = (currentIndex(ctx) - 1) / 4;
            if (ctx.getParent().getChild(currentIndex(ctx) - 1).getText().equals("elif")) {
                text += "elif" + index + "Label" + numIf_stmt + ":\n";
            }
        }
    }

    @Override
    public void exitTest(tinyPythonParser.TestContext ctx) {
        String op = setOperation(ctx.getChild(1).getText());
        if (ctx.getParent().getChild(0).getText().equals("while")) {
            // while문의 conditional branch를 작성
            text += "if_" + op + " loop_end" + numWhile_stmt + "\n";
        } else {
            // if에 형태에 맞도록 conditional branch를 작성
            if (if1) {
                text += "if_" + op + " endLabel" + numIf_stmt + "\n";
            } else if (if2) {
                text += "if_" + op + " elseLabel" + numIf_stmt + "\n";
            } else if (if3) {
                int index = (currentIndex(ctx) + 3) / 4;
                text += "if_" + op + " elif" + index + "Label" + numIf_stmt + "\n";
            } else if (if4) {
                if (currentIndex(ctx) == ctx.getParent().getChildCount() - 3) {
                    text += "if_" + op + " endLabel" + numIf_stmt + "\n";
                } else {
                    int index = (currentIndex(ctx) + 3) / 4;
                    text += "if_" + op + " elif" + index + "Label" + numIf_stmt + "\n";
                }
            }
        }
    }

    @Override
    public void enterSuite(tinyPythonParser.SuiteContext ctx) {
        // if의 형태에 맞도록 label설정.
        if (if2 && currentIndex(ctx) == ctx.getParent().getChildCount() - 1) {
            text += "elseLabel" + numIf_stmt + ":\n";
        } else if (if3 && currentIndex(ctx) == ctx.getParent().getChildCount() - 1) {
            int index = (currentIndex(ctx) - 2) / 4;
            text += "elif" + index + "Label" + numIf_stmt + ":\n";
        }
    }

    @Override
    public void exitSuite(tinyPythonParser.SuiteContext ctx) {
        if (ctx.getParent().getChild(0).getText().equals("while")) {
            // while문의 conditional branch를 작성
            text += "goto loop_start" + numWhile_stmt + "\n";
        } else {
            // if에 형태에 맞도록 label과 conditional branch를 작성
            if (if1) {
                text += "endLabel" + numIf_stmt + ":\n";
            } else if (if2) {
                if (ctx.getParent().getChild(ctx.getParent().getChildCount() - 1).equals(ctx)) {
                    text += "endLabel" + numIf_stmt + ":\n";
                } else {
                    text += "goto endLabel" + numIf_stmt + "\n";
                }
            } else if (if3) {
                if (currentIndex(ctx) == ctx.getParent().getChildCount() - 1) {
                    text += "endLabel" + numIf_stmt + ":\n";
                } else {
                    text += "goto endLabel" + numIf_stmt + "\n";
                }
            } else if (if4) {
                if (currentIndex(ctx) == ctx.getParent().getChildCount() - 1) {
                    text += "endLabel" + numIf_stmt + ":\n";
                } else {
                    text += "goto endLabel" + numIf_stmt + "\n";
                }
            }
        }
    }

    @Override
    public void exitExpr(tinyPythonParser.ExprContext ctx) {
        if (ctx.getChildCount() == 2) {
            String name = ctx.getChild(0).getText();
            if (ctx.getChild(1).getChildCount() == 0) {
                // 변수일 때
                int addr = st.getVariable(name);
                text += "iload_" + addr + "\n";
            } else {
                // function call일 때
                String funcName = ctx.getChild(0).getText();
                if (ctx.getChild(1).getChildCount() == 2) {
                    // 함수의 인자가 존재하지 않음.
                    text += "invokestatic Test/" + funcName + "()I\n";
                } else {
                    // 함수의 인자를 고려.
                    int numArgs = (ctx.getChild(1).getChildCount() - 1) / 2;
                    String argsI = "";
                    for (int i = 0; i < numArgs; i++) {
                        argsI += "I";
                    }
                    text += "invokestatic Test/" + funcName + "(" + argsI + ")I\n";
                }
            }
        } else if (ctx.getChildCount() == 1) {
            // number일 때
            String number = ctx.getChild(0).getText();
            text += "ldc " + number + "\n";
        } else if (ctx.getChildCount() == 3) {
            // '+'와 '-'연산자
            if (ctx.getChild(1).getText().equals("+")) {
                text += "iadd\n";
            } else if (ctx.getChild(1).getText().equals("-")) {
                text += "isub\n";
            }
        }
    }

    // 출력관련 코드들.
    @Override
    public void enterPrint_stmt(tinyPythonParser.Print_stmtContext ctx) {
        text += "getstatic java/lang/System/out Ljava/io/PrintStream;\n";
    }

    @Override
    public void exitPrint_stmt(tinyPythonParser.Print_stmtContext ctx) {
        if (ctx.getChild(1).getChild(0).getText().contains("\"")) {
            // print할 대상이 문자열일 때.
            text += "ldc " + ctx.getChild(1).getChild(0).getText() + "\n";
            text += "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n";
        } else {
            // print할 대상이 int일 때.
            text += "invokevirtual java/io/PrintStream/println(I)V\n";
        }
    }
}