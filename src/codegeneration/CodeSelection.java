/**
 * Tutorial de Diseño de Lenguajes de Programación
 * @author Raúl Izquierdo
 */

package codegeneration;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.AST;
import ast.ArithmeticExpression;
import ast.ArrayAccess;
import ast.ArrayType;
import ast.Assignment;
import ast.CallFunction;
import ast.CallFunctionSentence;
import ast.CampoStruct;
import ast.Cast;
import ast.CharConstant;
import ast.CharType;
import ast.DefVariable;
import ast.Expression;
import ast.FloatConstant;
import ast.FloatType;
import ast.Function;
import ast.GlobalVariable;
import ast.IdentType;
import ast.If;
import ast.IntConstant;
import ast.IntType;
import ast.LocalVariable;
import ast.LogicalExpression;
import ast.NotExpression;
import ast.ParenthesisExpression;
import ast.Position;
import ast.Print;
import ast.PrintLn;
import ast.PrintSp;
import ast.Program;
import ast.Read;
import ast.Return;
import ast.Sentence;
import ast.Struct;
import ast.StructAccess;
import ast.Type;
import ast.Variable;
import ast.While;
import visitor.DefaultVisitor;

enum CodeFunction {
	ADDRESS, VALUE
}

public class CodeSelection extends DefaultVisitor {
	private Map<String, String> instruccion = new HashMap<String, String>();
	private int contIf=0,contWhile=0;

	public CodeSelection(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;

		instruccion.put("+", "ADD");
		instruccion.put("-", "SUB");
		instruccion.put("*", "MUL");
		instruccion.put("/", "DIV");
		instruccion.put("%", "MOD");
		instruccion.put("==", "EQ");
		instruccion.put("!=", "NE");
		instruccion.put(">", "GT");
		instruccion.put(">=", "GE");
		instruccion.put("<", "LT");
		instruccion.put("<=", "LE");
		instruccion.put("&&", "AND");
		instruccion.put("||", "OR");
	}

	public Object visit(Program node, Object param) {
		out("#source \"" + sourceFile + "\"");
		out("CALL main");
		out("HALT");
		super.visit(node, param);
		return null;
	}

//	class Struct { String name;  List<CampoStruct> fields; }
	public Object visit(Struct node, Object param) {
		String tipo = node.getName() + ": {\n\t";
		if (node.getFields() != null)
			for (CampoStruct child : node.getFields()) {
				tipo += child.getName() + ":" + child.getTipo().getMAPLName() + "\n\t";
			}
		tipo += "}";
		out("#type " + tipo);
		return null;
	}

//	class GlobalVariable { DefVariable defvariable; }
	public Object visit(GlobalVariable node, Object param) {
		out("#GLOBAL " + node.getDefvariable().getName() + ":" + node.getDefvariable().getTipo().getMAPLName());
		return null;
	}

//	class Function { String name;  List<DefVariable> parameters;  Type tipo;  List<Sentence> body; }
	public Object visit(Function node, Object param) {
		int sizeLocals = getSizeLocals(node);
		out("#func "  + node.getName());
		for (DefVariable paramet : node.getParameters()) {
			out("#param " + paramet.getName() + ":" + paramet.getTipo().getMAPLName());
		}
		for (LocalVariable local : node.getLocals()) {
			out("#local " + local.getDefvariable().getName() + ":" + local.getDefvariable().getTipo().getMAPLName());
		}
		if(node.getTipo() != null)
			out("#ret " + node.getTipo().getMAPLName());
		out(node.getName() + ":");
		out("ENTER " + sizeLocals);
		super.visit(node, param);
		if(node.getTipo() == null)
			out("RET 0," + sizeLocals + "," + getSizeParameteres(node));
		return null;
	}

//	class Assignment { Expression left;  Expression right; }
	public Object visit(Assignment node, Object param) {
		line(node);
		node.getLeft().accept(this, CodeFunction.ADDRESS);
		node.getRight().accept(this, CodeFunction.VALUE);
		out("STORE", node.getLeft().getType());

		return null;
	}

//	class Return { Expression expression; }
	public Object visit(Return node, Object param) {
		line(node);
		if (node.getExpression() != null)
			node.getExpression().accept(this, CodeFunction.VALUE);
		else
			out("RET 0, " + getSizeLocals(node.getFunctionSelf()) + "," + 
					getSizeParameteres(node.getFunctionSelf()));
		if(node.getFunctionSelf().getTipo()!= null)
			out("RET " + node.getFunctionSelf().getTipo().getSize() + "," + 
			getSizeLocals(node.getFunctionSelf()) + "," + getSizeParameteres(node.getFunctionSelf()));

		return null;
	}

//	class Print { Expression expression; }
	public Object visit(Print node, Object param) {
		line(node);
		node.getExpression().accept(this, CodeFunction.VALUE);
		out("OUT", node.getExpression().getType());
		return null;
	}

//	class PrintSp { Expression expression; }
	public Object visit(PrintSp node, Object param) {
		line(node);
		node.getExpression().accept(this, CodeFunction.VALUE);
		out("OUT", node.getExpression().getType());
		out("PUSHB 32");
		out("OUTB");
		return null;
	}

	// class PrintLn { Expression expression; }
	public Object visit(PrintLn node, Object param) {
		line(node);
		node.getExpression().accept(this, CodeFunction.VALUE);
		out("OUT", node.getExpression().getType());
		out("PUSHB 10");
		out("OUTB");
		return null;
	}

//	class Read { Expression expression; }
	public Object visit(Read node, Object param) {
		line(node);
		if (node.getExpression() != null) {
			node.getExpression().accept(this, CodeFunction.ADDRESS);
			out("IN", node.getExpression().getType());
			out("STORE" + node.getExpression().getType().getSuffix());
		}
		return null;
	}
	
//	class If { Expression condition;  List<Sentence> trueBody;  List<Sentence> falseBody; }
	public Object visit(If node, Object param) {
		line(node);
		int n = contIf++;
		if (node.getCondition() != null)
			node.getCondition().accept(this, CodeFunction.VALUE);
		out("JZ else" + n);
		if (node.getTrueBody() != null)
			for (Sentence child : node.getTrueBody())
				child.accept(this, param);
		out("JMP finElse" + n);
		out("else" + n + ":");
		if (node.getFalseBody() != null)
			for (Sentence child : node.getFalseBody())
				child.accept(this, param);
		out("finElse" + n + ":");
		return null;
	}
	
//	class While { Expression condition;  List<Sentence> body; }
	public Object visit(While node, Object param) {
		line(node);
		int n=contWhile++;
		out("condition" + n + ":");
		if (node.getCondition() != null)
			node.getCondition().accept(this, CodeFunction.VALUE);
		out("JZ finWhile" + n);
		if (node.getBody() != null)
			for (Sentence child : node.getBody())
				child.accept(this, param);
		out("JMP condition" + n);
		out("finWhile" + n + ":");
		return null;
	}
	
//	class CallFunctionSentence { String name;  List<Expression> args; }
	public Object visit(CallFunctionSentence node, Object param) {
		line(node);
		if (node.getArgs() != null)
			for (Expression child : node.getArgs())
				child.accept(this, CodeFunction.VALUE);
		out("CALL " + node.getName());
		if(node.getDefinition().getTipo() != null)
			out("POP",node.getDefinition().getTipo());
		return null;
	}

//	class IntConstant { String value; }
	public Object visit(IntConstant node, Object param) {
		out("PUSH " + node.getValue());
		return null;
	}

//	class FloatConstant { String value; }
	public Object visit(FloatConstant node, Object param) {
		out("PUSHF " + Float.parseFloat(node.getValue()));
		return null;
	}

//	class CharConstant { String value; }
	public Object visit(CharConstant node, Object param) {
		if(node.getValue().equals("'\\n'")) {
			out("PUSHB " + 10 );
		}else {
			int value = node.getValue().charAt(1);
			out("PUSHB " + value );
		}
		return null;
	}

//	class Variable { String name; }
	public Object visit(Variable node, Object param) {
		if (((CodeFunction) param) == CodeFunction.VALUE) {
			visit(node, CodeFunction.ADDRESS);
			out("LOAD", node.getType());
		} else { // Funcion.DIRECCION
			assert (param == CodeFunction.ADDRESS);
			if(node.getDefinition().getAmbito() == Ambito.GLOBAL)
				out("PUSHA " + node.getDefinition().getAddress());
			else {
				out("PUSHA BP");
				out("PUSH " + node.getDefinition().getAddress());
				out("ADD");
			}
		}
		return null;
	}

//	class ArithmeticExpression { Expression left;  String operator;  Expression right; }
	public Object visit(ArithmeticExpression node, Object param) {
		node.getLeft().accept(this, CodeFunction.VALUE);
		node.getRight().accept(this, CodeFunction.VALUE);
		out(instruccion.get(node.getOperator()), node.getType());
		return null;
	}

//	class LogicalExpression { Expression left;  String operator;  Expression right; }
	public Object visit(LogicalExpression node, Object param) {
		node.getLeft().accept(this, CodeFunction.VALUE);
		node.getRight().accept(this, CodeFunction.VALUE);
		if (node.getOperator().equals("&&") || node.getOperator().equals("||")) {
			out(instruccion.get(node.getOperator()));
		} else {
			out(instruccion.get(node.getOperator()), node.getLeft().getType());
		}
		return null;
	}

//	class NotExpression { String operator;  Expression expr; }
	public Object visit(NotExpression node, Object param) {
		node.getExpr().accept(this, CodeFunction.VALUE);
		out("NOT");
		return null;
	}

//	class ParenthesisExpression { Expression expr; }
	public Object visit(ParenthesisExpression node, Object param) {
		node.getExpr().accept(this, CodeFunction.VALUE);
		return null;
	}

//	class ArrayAccess { Expression left;  Expression right; }
	public Object visit(ArrayAccess node, Object param) {
		node.getLeft().accept(this, CodeFunction.ADDRESS);
		node.getRight().accept(this, CodeFunction.VALUE);
		out("PUSH " + ((ArrayType) node.getLeft().getType()).getTipo().getSize());
		out("MUL");
		out("ADD");
		if (((CodeFunction) param) == CodeFunction.VALUE)
			out("LOAD", node.getType());
		return null;
	}

//	class StructAccess { Expression left;  String right; }
	public Object visit(StructAccess node, Object param) {
		node.getLeft().accept(this, CodeFunction.ADDRESS);
		IdentType type = (IdentType) (node.getLeft().getType());
		CampoStruct campo = getCampoStruct(type.getDefinition().getFields(), node.getRight());
		out("PUSH " + campo.getAddress());
		out("ADD");
		if (((CodeFunction) param) == CodeFunction.VALUE)
			out("LOAD", node.getType());
		return null;
	}

//	class CallFunction { String name;  List<Expression> args; }
	public Object visit(CallFunction node, Object param) {
		if (node.getArgs() != null)
			for (Expression child : node.getArgs())
				child.accept(this, CodeFunction.VALUE);
		out("CALL " + node.getName());
		return null;
	}

//	class Cast { Type tipo;  Expression value; }
	public Object visit(Cast node, Object param) {
		node.getValue().accept(this, param);
		if (node.getValue().getType().getClass() == CharType.class) {
			out("B2I");
		} else if (node.getValue().getType().getClass() == FloatType.class) {
			out("F2I");
		} else if (node.getValue().getType().getClass() == IntType.class
				&& node.getTipo().getClass() == FloatType.class) {
			out("I2F");
		} else {
			out("I2B");
		}
		return null;
	}

	// # ----------------------------------------------------------
	// Métodos auxiliares recomendados (opcionales) -------------
	private CampoStruct getCampoStruct(List<CampoStruct> fields, String name) {
		for (CampoStruct cStruct : fields) {
			if (cStruct.getName().equals(name))
				return cStruct;
		}
		return null;
	}
	
	private int getSizeLocals(Function node) {
		List<LocalVariable> locals = node.getLocals();
		int sizeLocals = 0;
		for (LocalVariable localVariable : locals) {
			sizeLocals+=localVariable.getDefvariable().getTipo().getSize();
		}
		return sizeLocals;
	}
	private int getSizeParameteres(Function node) {
		List<DefVariable> variables = node.getParameters();
		int size = 0;
		for (DefVariable variable : variables) {
			size+=variable.getTipo().getSize();
		}
		return size;
	}

	// Imprime una línea en el fichero de salida
	private void out(String instruction) {
		writer.println(instruction);
	}

	private void out(String instruccion, Type tipo) {
		out(instruccion + tipo.getSuffix());
	}

	private void line(AST node) {
		line(node.getEnd());
	}

	private void line(Position pos) {
		if (pos != null)
			out("\n#line " + pos.getLine());
		else
			System.out.println("#line no generado. Se ha pasado una Position null. Falta información en el AST");
	}

	private PrintWriter writer;
	private String sourceFile;
}
