/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package visitor;

import java.io.*;

import ast.*;
import java.util.*;

/**
 * ASTPrinter. Utilidad que ayuda a validar un arbol AST:
 * - Muestra la estructura del árbol en HTML.
 * - Destaca los hijos/propiedades a null.
 * - Muestra a qué texto apuntan las posiciones de cada nodo (linea/columna)
 *      ayudando a decidir cual de ellas usar en los errores y generación de código.
 *
 * Esta clase se genera con VGen. El uso de esta clase es opcional (puede eliminarse del proyecto).
 *
 */
public class ASTPrinter extends DefaultVisitor {

    /**
     * toHtml. Muestra la estructura del AST indicando qué hay en las posiciones
     * (línea y columna) de cada nodo.
     *
     * @param sourceFile El fichero del cual se ha obtenido el AST
     * @param raiz       El AST creado a partir de sourceFile
     * @param filename   Nombre del fichero HMTL a crear con la traza del AST
     */

    public static void toHtml(String sourceFile, AST raiz, String filename) {
        toHtml(sourceFile, raiz, filename, 4);
    }

    public static void toHtml(AST raiz, String filename) {
        toHtml(null, raiz, filename);
    }

    // tabWidth deberían ser los espacios correspondientes a un tabulador en eclipse.
    // Normalmente no sería necesario especificarlo. Usar mejor los dos métodos anteriores.

    public static void toHtml(String sourceFile, AST raiz, String filename, int tabWidth) {
        try {
            PrintWriter writer = new PrintWriter(
                    new FileWriter(filename.endsWith(".html") ? filename : filename + ".html"));
            generateHeader(writer);
            writer.println("[ASTPrinter] -------------------------------- line:col  line:col");
            if (raiz != null) {
                ASTPrinter tracer = new ASTPrinter(writer, loadLines(sourceFile, tabWidth));
                raiz.accept(tracer, Integer.valueOf(0));
            } else
                writer.println("raiz == null");
            writer.println(ls + ls + "[ASTPrinter] --------------------------------");
            generateFooter(writer);
            writer.close();
            System.out.println(ls + "ASTPrinter: Fichero '" + filename
                    + ".html' generado. Abra dicho fichero para validar el AST generado.");
        } catch (IOException e) {
            System.out.println(ls + "ASTPrinter: No se ha podido crear el fichero " + filename);
            e.printStackTrace();
        }
    }

    private static void generateHeader(PrintWriter writer) {
        writer.println("<html>\r\n"
            + "<head>\r\n"
            + "<meta charset=\"utf-8\" />\r\n"
            + "<style type=\"text/css\">\r\n"
            + ".value { font-weight: bold; }\r\n"
            + ".dots { color: #bebdbd; }\r\n"
            + ".type { color: #BBBBBB; }\r\n"
            + ".pos { color: #CCCCCC; }\r\n"
            + ".sourceText { color: #BBBBBB; }\r\n"
            + ".posText {\r\n" + "	color: #BBBBBB;\r\n"
            + "	text-decoration: underline; font-weight: bold;\r\n"
            + "}\r\n"
            + ".null {\r\n"
            + "	color: #FF0000;\r\n"
            + "	font-weight: bold;\r\n"
            + "	font-style: italic;\r\n" + "}\r\n"
            + "</style>\r\n" + "</head>\r\n" + "\r\n"
            + "<body><pre>");
    }

    private static void generateFooter(PrintWriter writer) {
        writer.println("</pre>\r\n" + "</body>\r\n" + "</html>");
    }

    private ASTPrinter(PrintWriter writer, List<String> sourceLines) {
        this.writer = writer;
        this.sourceLines = sourceLines;
    }

    // ----------------------------------------------
	//	class Program { List<Instruction> body; }
	public Object visit(Program node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Program", node, false);

		visit(indent + 1, "body", "List<Instruction>",node.getBody());
		return null;
	}

	//	class Struct { String name;  List<CampoStruct> fields; }
	public Object visit(Struct node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Struct", node, false);

		print(indent + 1, "name", "String", node.getName());
		visit(indent + 1, "fields", "List<CampoStruct>",node.getFields());
		return null;
	}

	//	class GlobalVariable { DefVariable defvariable; }
	public Object visit(GlobalVariable node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "GlobalVariable", node, false);
		print(indent + 1, "address", "int", node.getAddress());
		return null;
	}

	//	class Function { String name;  List<DefVariable> parameters;  Type tipo;  List<Sentence> body; }
	public Object visit(Function node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Function", node, false);

		print(indent + 1, "name", "String", node.getName());
		visit(indent + 1, "parameters", "List<DefVariable>",node.getParameters());
		visit(indent + 1, "tipo", "Type",node.getTipo());
		visit(indent + 1, "body", "List<Sentence>",node.getBody());
		return null;
	}

	//	class DefVariable { String name;  Type tipo; }
	public Object visit(DefVariable node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefVariable", node, false);

		print(indent + 1, "name", "String", node.getName());
		print(indent + 1, "address", "int", node.getAddress());
		visit(indent + 1, "tipo", "Type",node.getTipo());
		return null;
	}

	//	class CampoStruct { String name;  Type tipo; }
	public Object visit(CampoStruct node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "CampoStruct", node, false);

		print(indent + 1, "name", "String", node.getName());
		print(indent + 1, "address", "int", node.getAddress());
		visit(indent + 1, "tipo", "Type",node.getTipo());
		return null;
	}

	//	class LocalVariable { DefVariable defvariable; }
	public Object visit(LocalVariable node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "LocalVariable", node, false);

		visit(indent + 1, "defvariable", "DefVariable",node.getDefvariable());
		return null;
	}

	//	class Assignment { Expression left;  Expression right; }
	public Object visit(Assignment node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Assignment", node, false);

		visit(indent + 1, "left", "Expression",node.getLeft());
		visit(indent + 1, "right", "Expression",node.getRight());
		return null;
	}

	//	class Return { Expression expression; }
	public Object visit(Return node, Object param) {
		int indent = ((Integer)param).intValue();
		printName(indent, "Return", node, false);
		visit(indent + 1, "expression", "Expression",node.getExpression());
		return null;
	}

	//	class Print { Expression expression; }
	public Object visit(Print node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Print", node, false);

		visit(indent + 1, "expression", "Expression",node.getExpression());
		return null;
	}

	//	class PrintSp { Expression expression; }
	public Object visit(PrintSp node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "PrintSp", node, false);

		visit(indent + 1, "expression", "Expression",node.getExpression());
		return null;
	}

	//	class PrintLn { Expression expression; }
	public Object visit(PrintLn node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "PrintLn", node, false);

		visit(indent + 1, "expression", "Expression",node.getExpression());
		return null;
	}

	//	class Read { Expression expression; }
	public Object visit(Read node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Read", node, false);

		visit(indent + 1, "expression", "Expression",node.getExpression());
		return null;
	}

	//	class If { Expression condition;  List<Sentence> trueBody;  List<Sentence> falseBody; }
	public Object visit(If node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "If", node, false);

		visit(indent + 1, "condition", "Expression",node.getCondition());
		visit(indent + 1, "trueBody", "List<Sentence>",node.getTrueBody());
		visit(indent + 1, "falseBody", "List<Sentence>",node.getFalseBody());
		return null;
	}

	//	class While { Expression condition;  List<Sentence> body; }
	public Object visit(While node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "While", node, false);

		visit(indent + 1, "condition", "Expression",node.getCondition());
		visit(indent + 1, "body", "List<Sentence>",node.getBody());
		return null;
	}

	//	class CallFunctionSentence { String name;  List<Expression> args; }
	public Object visit(CallFunctionSentence node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "CallFunctionSentence", node, false);

		print(indent + 1, "name", "String", node.getName());
		visit(indent + 1, "args", "List<Expression>",node.getArgs());
		return null;
	}

	//	class IntConstant { String value; }
	public Object visit(IntConstant node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "IntConstant", node, "value", node.getValue());
		return null;
	}

	//	class FloatConstant { String value; }
	public Object visit(FloatConstant node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "FloatConstant", node, "value", node.getValue());
		return null;
	}

	//	class CharConstant { String value; }
	public Object visit(CharConstant node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "CharConstant", node, "value", node.getValue());
		return null;
	}

	//	class Variable { String name; }
	public Object visit(Variable node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Variable", node, "name", node.getName());
		return null;
	}

	//	class ArithmeticExpression { Expression left;  String operator;  Expression right; }
	public Object visit(ArithmeticExpression node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ArithmeticExpression", node, false);

		visit(indent + 1, "left", "Expression",node.getLeft());
		print(indent + 1, "operator", "String", node.getOperator());
		visit(indent + 1, "right", "Expression",node.getRight());
		return null;
	}

	//	class LogicalExpression { Expression left;  String operator;  Expression right; }
	public Object visit(LogicalExpression node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "LogicalExpression", node, false);

		visit(indent + 1, "left", "Expression",node.getLeft());
		print(indent + 1, "operator", "String", node.getOperator());
		visit(indent + 1, "right", "Expression",node.getRight());
		return null;
	}

	//	class NotExpression { String operator;  Expression expr; }
	public Object visit(NotExpression node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "NotExpression", node, false);

		print(indent + 1, "operator", "String", node.getOperator());
		visit(indent + 1, "expr", "Expression",node.getExpr());
		return null;
	}

	//	class ParenthesisExpression { Expression expr; }
	public Object visit(ParenthesisExpression node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ParenthesisExpression", node, false);

		visit(indent + 1, "expr", "Expression",node.getExpr());
		return null;
	}

	//	class ArrayAccess { Expression left;  Expression right; }
	public Object visit(ArrayAccess node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ArrayAccess", node, false);

		visit(indent + 1, "left", "Expression",node.getLeft());
		visit(indent + 1, "right", "Expression",node.getRight());
		return null;
	}

	//	class StructAccess { Expression left;  String right; }
	public Object visit(StructAccess node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "StructAccess", node, false);

		visit(indent + 1, "left", "Expression",node.getLeft());
		print(indent + 1, "right", "String", node.getRight());
		return null;
	}

	//	class CallFunction { String name;  List<Expression> args; }
	public Object visit(CallFunction node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "CallFunction", node, false);

		print(indent + 1, "name", "String", node.getName());
		visit(indent + 1, "args", "List<Expression>",node.getArgs());
		return null;
	}

	//	class Cast { Type tipo;  Expression value; }
	public Object visit(Cast node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Cast", node, false);

		visit(indent + 1, "tipo", "Type",node.getTipo());
		visit(indent + 1, "value", "Expression",node.getValue());
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "IntType", node, true);

		return null;
	}

	//	class FloatType {  }
	public Object visit(FloatType node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "FloatType", node, true);

		return null;
	}

	//	class CharType {  }
	public Object visit(CharType node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "CharType", node, true);

		return null;
	}

	//	class IdentType { String value; }
	public Object visit(IdentType node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "IdentType", node, "value", node.getValue());
		return null;
	}

	//	class ArrayType { IntConstant dimension;  Type tipo; }
	public Object visit(ArrayType node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ArrayType", node, false);

		visit(indent + 1, "dimension", "IntConstant",node.getDimension());
		visit(indent + 1, "tipo", "Type",node.getTipo());
		return null;
	}


	// -----------------------------------------------------------------
	// Métodos invocados desde los métodos visit -----------------------

	private void printName(int indent, String name, AST node, boolean empty) {
		String text = ls + tabula(indent) + name + " &rarr;  ";
		text = String.format("%1$-" + 93 + "s", text);
		if (empty)
			text = text.replace(name, valueTag(name));
		writer.print(text + getPosition(node));
	}

	private void print(int indent, String name, String type, Object value) {
		write(indent, formatValue(value) + "  " + typeTag(type));
	}

	private void print(int indent, String attName, String type, List<? extends Object> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (Object child : children)
				write(indent + 1, formatValue(child));
		else
			writer.print(" " + valueTag(null));
	}

	// Versión compacta de una linea para nodos que solo tienen un atributo String
	private void printCompact(int indent, String nodeName, AST node, String attName, Object value) {
		String fullName = nodeName + '.' + attName;
		String text = ls + tabula(indent) + '\"' + value + "\"  " + fullName;
		text = String.format("%1$-" + 88 + "s", text);
		// text = text.replace(value.toString(), valueTag(value));
		text = text.replace(fullName, typeTag(fullName));
		writer.print(text + getPosition(node));
	}

	private void visit(int indent, String attName, String type, List<? extends AST> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (AST child : children)
				child.accept(this, indent + 1);
		else
			writer.print(" " + valueTag(null));
	}

	private void visit(int indent, String attName, String type, AST child) {
		if (child != null)
			child.accept(this, Integer.valueOf(indent));
		else
			write(indent, valueTag(null) + "  " + attName + ':' + typeTag(type));
	}

	// -----------------------------------------------------------------
	// Métodos auxiliares privados -------------------------------------

	private void write(int indent, String text) {
		writer.print(ls + tabula(indent) + text);
	}

	private static String tabula(int count) {
		StringBuffer cadena = new StringBuffer("<span class=\"dots\">");
		for (int i = 0; i < count; i++)
			cadena.append(i % 2 == 0 && i > 0 ? "|  " : ".  ");
		return cadena.toString() + "</span>";
	}

	private String typeTag(String type) {
		if (type.equals("String"))
			return "";
		return "<span class=\"type\">" + type.replace("<", "&lt;").replace(">", "&gt;") + "</span>";
	}

	private String valueTag(Object value) {
		if (value == null)
			return "<span class=\"null\">null</span>";
		return "<span class=\"value\">" + value + "</span>";
	}

	private String formatValue(Object value) {
		String text = valueTag(value);
		if (value instanceof String)
			text = "\"" + text + '"';
		return text;
	}


	// -----------------------------------------------------------------
	// Métodos para mostrar las Posiciones -----------------------------

	private String getPosition(AST node) {
		String text = node.getStart() + "  " + node.getEnd();
		text = "<span class=\"pos\">" + String.format("%1$-" + 13 + "s", text) + "</span>";
		text = text.replace("null", "<span class=\"null\">null</span>");
		String sourceText = findSourceText(node);
		if (sourceText != null)
			text += sourceText;
		return text;
	}

	private String findSourceText(AST node) {
		if (sourceLines == null)
			return null;

		Position start = node.getStart();
		Position end = node.getEnd();
		if (start == null || end == null)
			return null;

		String afterText, text, beforeText;
		if (start.getLine() == end.getLine()) {
			String line = sourceLines.get(start.getLine() - 1);
			afterText = line.substring(0, start.getColumn() - 1);
			text = line.substring(start.getColumn() - 1, end.getColumn());
			beforeText = line.substring(end.getColumn());
		} else {
			String firstLine = sourceLines.get(start.getLine() - 1);
			String lastLine = sourceLines.get(end.getLine() - 1);

			afterText = firstLine.substring(0, start.getColumn() - 1);

			text = firstLine.substring(start.getColumn() - 1);
			text += "</span><span class=\"sourceText\">" + " ... " + "</span><span class=\"posText\">";
			text += lastLine.substring(0, end.getColumn()).replaceAll("^\\s+", "");

			beforeText = lastLine.substring(end.getColumn());
		}
		return "<span class=\"sourceText\">" + afterText.replaceAll("^\\s+", "")
				+ "</span><span class=\"posText\">" + text
				+ "</span><span class=\"sourceText\">" + beforeText + "</span>";
	}

	private static List<String> loadLines(String sourceFile, int tabWidth) {
		if (sourceFile == null)
			return null;
		try {
			String spaces = new String(new char[tabWidth]).replace("\0", " ");

			List<String> lines = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			String line;
			while ((line = br.readLine()) != null) {
			//	lines.add(line.replace("\t", spaces)); // Si tab = 4 espaces (Eclipse)
				lines.add(line);
            }
			br.close();
			return lines;
		} catch (FileNotFoundException e) {
			System.out.println("Warning. No se pudo encontrar el fichero fuente '" + sourceFile + "'. No se mostrará informaicón de posición.");
			return null;
		} catch (IOException e) {
			System.out.println("Warning. Error al leer del fichero fuente '" + sourceFile + "'. No se mostrará informaicón de posición.");
			return null;
		}
	}


	private List<String> sourceLines;
	private static String ls = System.getProperty("line.separator");
	private PrintWriter writer;
}
