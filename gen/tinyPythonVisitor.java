// Generated from C:/Users/user/IdeaProjects/week04/tinyPython.g4 by ANTLR 4.13.1

	package generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link tinyPythonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface tinyPythonVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(tinyPythonParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#file_input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile_input(tinyPythonParser.File_inputContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#defs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefs(tinyPythonParser.DefsContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(tinyPythonParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_stmt(tinyPythonParser.Simple_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSmall_stmt(tinyPythonParser.Small_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#assignment_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_stmt(tinyPythonParser.Assignment_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#flow_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlow_stmt(tinyPythonParser.Flow_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#break_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_stmt(tinyPythonParser.Break_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#continue_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_stmt(tinyPythonParser.Continue_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_stmt(tinyPythonParser.Compound_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#if_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(tinyPythonParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#while_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stmt(tinyPythonParser.While_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#def_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDef_stmt(tinyPythonParser.Def_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(tinyPythonParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(tinyPythonParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#return_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(tinyPythonParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest(tinyPythonParser.TestContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#print_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_stmt(tinyPythonParser.Print_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#print_arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_arg(tinyPythonParser.Print_argContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#comp_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_op(tinyPythonParser.Comp_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(tinyPythonParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link tinyPythonParser#opt_paren}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpt_paren(tinyPythonParser.Opt_parenContext ctx);
}