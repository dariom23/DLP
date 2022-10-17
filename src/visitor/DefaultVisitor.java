/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Program { List<Instruction> body; }
	public Object visit(Program node, Object param) {
		visitChildren(node.getBody(), param);
		return null;
	}

	//	class Struct { String name;  List<CampoStruct> fields; }
	public Object visit(Struct node, Object param) {
		visitChildren(node.getFields(), param);
		return null;
	}

	//	class GlobalVariable { DefVariable defvariable; }
	public Object visit(GlobalVariable node, Object param) {
		if (node.getDefvariable() != null)
			node.getDefvariable().accept(this, param);
		return null;
	}

	//	class Function { String name;  List<DefVariable> parameters;  Type tipo;  List<Sentence> body; }
	public Object visit(Function node, Object param) {
		visitChildren(node.getParameters(), param);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		visitChildren(node.getBody(), param);
		return null;
	}

	//	class DefVariable { String name;  Type tipo; }
	public Object visit(DefVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class CampoStruct { String name;  Type tipo; }
	public Object visit(CampoStruct node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class LocalVariable { DefVariable defvariable; }
	public Object visit(LocalVariable node, Object param) {
		if (node.getDefvariable() != null)
			node.getDefvariable().accept(this, param);
		return null;
	}

	//	class Assignment { Expression left;  Expression right; }
	public Object visit(Assignment node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Return { Expression expression; }
	public Object visit(Return node, Object param) {
		if (node.getExpression() != null)
			node.getExpression().accept(this, param);
		return null;
	}

	//	class Print { Expression expression; }
	public Object visit(Print node, Object param) {
		if (node.getExpression() != null)
			node.getExpression().accept(this, param);
		return null;
	}

	//	class PrintSp { Expression expression; }
	public Object visit(PrintSp node, Object param) {
		if (node.getExpression() != null)
			node.getExpression().accept(this, param);
		return null;
	}

	//	class PrintLn { Expression expression; }
	public Object visit(PrintLn node, Object param) {
		if (node.getExpression() != null)
			node.getExpression().accept(this, param);
		return null;
	}

	//	class Read { Expression expression; }
	public Object visit(Read node, Object param) {
		if (node.getExpression() != null)
			node.getExpression().accept(this, param);
		return null;
	}

	//	class If { Expression condition;  List<Sentence> trueBody;  List<Sentence> falseBody; }
	public Object visit(If node, Object param) {
		if (node.getCondition() != null)
			node.getCondition().accept(this, param);
		visitChildren(node.getTrueBody(), param);
		visitChildren(node.getFalseBody(), param);
		return null;
	}

	//	class While { Expression condition;  List<Sentence> body; }
	public Object visit(While node, Object param) {
		if (node.getCondition() != null)
			node.getCondition().accept(this, param);
		visitChildren(node.getBody(), param);
		return null;
	}

	//	class CallFunctionSentence { String name;  List<Expression> args; }
	public Object visit(CallFunctionSentence node, Object param) {
		visitChildren(node.getArgs(), param);
		return null;
	}

	//	class IntConstant { String value; }
	public Object visit(IntConstant node, Object param) {
		return null;
	}

	//	class FloatConstant { String value; }
	public Object visit(FloatConstant node, Object param) {
		return null;
	}

	//	class CharConstant { String value; }
	public Object visit(CharConstant node, Object param) {
		return null;
	}

	//	class Variable { String name; }
	public Object visit(Variable node, Object param) {
		return null;
	}

	//	class ArithmeticExpression { Expression left;  String operator;  Expression right; }
	public Object visit(ArithmeticExpression node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class LogicalExpression { Expression left;  String operator;  Expression right; }
	public Object visit(LogicalExpression node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class NotExpression { String operator;  Expression expr; }
	public Object visit(NotExpression node, Object param) {
		if (node.getExpr() != null)
			node.getExpr().accept(this, param);
		return null;
	}

	//	class ParenthesisExpression { Expression expr; }
	public Object visit(ParenthesisExpression node, Object param) {
		if (node.getExpr() != null)
			node.getExpr().accept(this, param);
		return null;
	}

	//	class ArrayAccess { Expression left;  Expression right; }
	public Object visit(ArrayAccess node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class StructAccess { Expression left;  String right; }
	public Object visit(StructAccess node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		return null;
	}

	//	class CallFunction { String name;  List<Expression> args; }
	public Object visit(CallFunction node, Object param) {
		visitChildren(node.getArgs(), param);
		return null;
	}

	//	class Cast { Type tipo;  Expression value; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getValue() != null)
			node.getValue().accept(this, param);
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType node, Object param) {
		return null;
	}

	//	class FloatType {  }
	public Object visit(FloatType node, Object param) {
		return null;
	}

	//	class CharType {  }
	public Object visit(CharType node, Object param) {
		return null;
	}

	//	class IdentType { String value; }
	public Object visit(IdentType node, Object param) {
		return null;
	}

	//	class ArrayType { IntConstant dimension;  Type tipo; }
	public Object visit(ArrayType node, Object param) {
		if (node.getDimension() != null)
			node.getDimension().accept(this, param);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

    // Método auxiliar -----------------------------
    protected void visitChildren(List<? extends AST> children, Object param) {
        if (children != null)
            for (AST child : children)
                child.accept(this, param);
    }
}
