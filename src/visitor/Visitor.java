/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Program node, Object param);
	public Object visit(Struct node, Object param);
	public Object visit(GlobalVariable node, Object param);
	public Object visit(Function node, Object param);
	public Object visit(DefVariable node, Object param);
	public Object visit(CampoStruct node, Object param);
	public Object visit(LocalVariable node, Object param);
	public Object visit(Assignment node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(Print node, Object param);
	public Object visit(PrintSp node, Object param);
	public Object visit(PrintLn node, Object param);
	public Object visit(Read node, Object param);
	public Object visit(If node, Object param);
	public Object visit(While node, Object param);
	public Object visit(CallFunctionSentence node, Object param);
	public Object visit(IntConstant node, Object param);
	public Object visit(FloatConstant node, Object param);
	public Object visit(CharConstant node, Object param);
	public Object visit(Variable node, Object param);
	public Object visit(ArithmeticExpression node, Object param);
	public Object visit(LogicalExpression node, Object param);
	public Object visit(NotExpression node, Object param);
	public Object visit(ParenthesisExpression node, Object param);
	public Object visit(ArrayAccess node, Object param);
	public Object visit(StructAccess node, Object param);
	public Object visit(CallFunction node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(IntType node, Object param);
	public Object visit(FloatType node, Object param);
	public Object visit(CharType node, Object param);
	public Object visit(IdentType node, Object param);
	public Object visit(ArrayType node, Object param);
}
