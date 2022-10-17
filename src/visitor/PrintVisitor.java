package visitor;

import java.io.PrintWriter;

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
import ast.NotExpression;
import ast.ParenthesisExpression;
import ast.Print;
import ast.PrintLn;
import ast.PrintSp;
import ast.Program;
import ast.Read;
import ast.Return;
import ast.Sentence;
import ast.Struct;
import ast.StructAccess;
import ast.Variable;
import ast.While;
import parser.GrammarParser.CamposStructContext;

public class PrintVisitor extends DefaultVisitor {

	public PrintVisitor(PrintWriter writer) {
		this.writer = writer;
	}

	public Object visit(Program node, Object param) {
		super.visit(node, param);

		return null;
	}

	public Object visit(Struct node, Object param) {
		out("struct " + node.getName() + "{\n");
		if (node.getFields() != null) {
			for (CampoStruct child : node.getFields()) {
				out("\t");
				child.accept(this, param);
				out(";\n");
			}
		}
		out("};\n");

		return null;
	}

	public Object visit(GlobalVariable node, Object param) {
		out("var ");
		super.visit(node, param);
		out(";\n");

		return null;
	}

	public Object visit(Function node, Object param) {
		out(node.getName() + "(");
		if (node.getParameters() != null) {
			int parameters = 0;
			for (DefVariable child : node.getParameters()) {
				parameters += 1;
				child.accept(this, param);
				if(parameters < node.getParameters().size())
					out(",");
			}
		}
		out(")");

		if (node.getTipo() != null) {
			out(": ");
			node.getTipo().accept(this, param);
		}

		if (node.getBody() != null) {
			out("{\n");
			for (Sentence child : node.getBody()) {
				out("\t");
				child.accept(this, param);
			}
			out("}\n");
		}

		return null;
	}

	public Object visit(DefVariable node, Object param) {
		out(node.getName() + ":");
		super.visit(node, param);

		return null;
	}

	public Object visit(LocalVariable node, Object param) {
		out("var ");
		super.visit(node, param);
		out(";\n");

		return null;
	}

	public Object visit(Assignment node, Object param) {

		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		out(" = ");
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		out(";\n");
		return null;
	}

	public Object visit(Return node, Object param) {
		out("return ");
		super.visit(node, param);
		out(";\n");
		return null;
	}

	public Object visit(Print node, Object param) {
		out("print");
		super.visit(node, param);
		out(";\n");
		return null;
	}

	public Object visit(PrintSp node, Object param) {
		out("printsp ");
		super.visit(node, param);
		out(";\n");
		return null;
	}

	public Object visit(PrintLn node, Object param) {
		out("println ");
		super.visit(node, param);
		out(";\n");
		return null;
	}

	public Object visit(Read node, Object param) {
		out("read ");
		super.visit(node, param);
		out(";\n");
		return null;
	}

	public Object visit(If node, Object param) {
		out("if (");
		if (node.getCondition() != null)
			node.getCondition().accept(this, param);
		out(") {\n");

		if (node.getTrueBody() != null)
			for (Sentence child : node.getTrueBody()) {
				out("\t\t");
				child.accept(this, param);
			}
		out("\t}");

		if (node.getFalseBody() != null && !node.getFalseBody().isEmpty()) {
			out("else {\n");
			for (Sentence child : node.getFalseBody()) {
				out("\t\t");
				child.accept(this, param);
			}
			out("\t}\n");
		} else {
			out("\n");
		}

		return null;
	}

	public Object visit(While node, Object param) {
		out("while (");
		if (node.getCondition() != null) {
			node.getCondition().accept(this, param);
			out("){\n");
		}

		if (node.getBody() != null) {
			for (Sentence child : node.getBody()) {
				out("\t\t");
				child.accept(this, param);
			}
			out("\t}\n");
		}

		return null;
	}

	public Object visit(CallFunctionSentence node, Object param) {
		out(node.getName() + "(");
		if (node.getArgs() != null) {
			int parameters = 0;
			for (Expression child : node.getArgs()) {
				parameters+=1;
				child.accept(this, param);
				if(parameters < node.getArgs().size())
					out(",");
			}
		}
		out(");\n");

		return null;
	}

	public Object visit(IntConstant node, Object param) {
		out(node.getValue());
		return null;
	}

	public Object visit(FloatConstant node, Object param) {
		out(node.getValue());
		return null;
	}

	public Object visit(CharConstant node, Object param) {
		out(node.getValue());
		return null;
	}

	public Object visit(Variable node, Object param) {
		out(node.getName());
		return null;
	}

	public Object visit(ArithmeticExpression node, Object param) {

		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		out(" " + node.getOperator() + " ");
		if (node.getRight() != null)
			node.getRight().accept(this, param);

		return null;
	}

	public Object visit(NotExpression node, Object param) {
		out(node.getOperator());
		super.visit(node, param);

		return null;
	}

	public Object visit(ParenthesisExpression node, Object param) {
		out("(");
		if (node.getExpr() != null)
			node.getExpr().accept(this, param);
		out(")");
		return null;
	}

	public Object visit(ArrayAccess node, Object param) {

		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		out("[");
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		out("]");

		return null;
	}

	public Object visit(StructAccess node, Object param) {

		super.visit(node, param);
		out("." + node.getRight());

		return null;
	}

	public Object visit(CallFunction node, Object param) {
		out(node.getName() + "(");
		if (node.getArgs() != null) {
			int parameters = 0;
			for (Expression child : node.getArgs()) {
				parameters+=1;
				child.accept(this, param);
				if(parameters < node.getArgs().size())
					out(",");
			}
			out(")");
		}
		return null;
	}

	public Object visit(Cast node, Object param) {
		out("<");
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		out(">");

		if (node.getValue() != null)
			out("(");
		node.getValue().accept(this, param);
		out(")");

		return null;
	}

	public Object visit(IntType node, Object param) {
		out("int");
		return null;
	}

	public Object visit(FloatType node, Object param) {
		out("float");
		return null;
	}

	public Object visit(CharType node, Object param) {
		out("char");
		return null;
	}

	public Object visit(IdentType node, Object param) {
		out(node.getValue());
		return null;
	}

	public Object visit(ArrayType node, Object param) {
		out("[");
		if (node.getDimension() != null)
			node.getDimension().accept(this, param);
		out("] ");

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}
	// # ----------------------------------------------------------
	// Métodos auxiliares recomendados (opcionales) -------------

	// Imprime una línea en el fichero de salida
	private void out(String instruction) {
		writer.print(instruction);
	}

	private PrintWriter writer;
}
