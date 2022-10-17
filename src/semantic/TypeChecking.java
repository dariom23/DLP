/**
 * Tutorial de Dise帽o de Lenguajes de Programaci贸n
 * @author Ra煤l Izquierdo
 */

package semantic;

import java.util.List;

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
import ast.IdentType;
import ast.If;
import ast.IntConstant;
import ast.IntType;
import ast.LogicalExpression;
import ast.NotExpression;
import ast.ParenthesisExpression;
import ast.Position;
import ast.Print;
import ast.PrintLn;
import ast.PrintSp;
import ast.Read;
import ast.Return;
import ast.Sentence;
import ast.StructAccess;
import ast.Type;
import ast.Variable;
import ast.While;
import main.ErrorManager;
import visitor.DefaultVisitor;

public class TypeChecking extends DefaultVisitor {

	public TypeChecking(ErrorManager errorManager) {
		this.errorManager = errorManager;
	}

//	class Function { String name;  List<DefVariable> parameters;  Type tipo;  List<Sentence> body; }
	public Object visit(Function node, Object param) {
		for (Sentence sentence : node.getBody()) {
			sentence.setFunctionSelf(node);
		}
		super.visit(node, param);
		if (node.getTipo() != null)
			predicado(esTipoSimple(node.getTipo()), "El tipo de retorno debe ser simple", node);
		predicado(esTipoSimple(node.getParameters()), "Los tipos de los par醡etros deben ser simples", node);

		return null;
	}

//	class Assignment { Expression left;  Expression right; }
	public Object visit(Assignment node, Object param) {
		super.visit(node, param);
		predicado(node.getLeft().getType().getClass() == node.getRight().getType().getClass(),
				"Las expresiones deben ser del mismo tipo", node);
		predicado(esTipoSimple(node.getLeft().getType()), "Valor de la izquierda debe ser tipo simple", node);
		predicado(node.getLeft().isModificable(), "Valor de la izquierda debe ser modificable", node);
		return null;
	}

//	class Return { Expression expression; }
	public Object visit(Return node, Object param) {
		super.visit(node, param);
		if(node.getFunctionSelf().getTipo() == null) {
			predicado(node.getExpression() == null, "El return deber韆 estar vac韔",node);
		}else {
			Expression returnExpr = node.getExpression();
			predicado(returnExpr != null, "El return no deber韆 estar vac韔",node);
			if(returnExpr != null)
				predicado(node.getFunctionSelf().getTipo().getClass() == node.getExpression().getType().getClass(),
					"El tipo de retorno no coincide con el de la funci髇",node);
		}

		return null;
	}

//	class Print { Expression expression; }
	public Object visit(Print node, Object param) {
		super.visit(node, param);
		if (node.getExpression() != null && node.getExpression().getType() != null)
			predicado(esTipoSimple(node.getExpression().getType()), "Valor debe ser un tipo simple ", node);
		return null;
	}

//	class PrintSp { Expression expression; }
	public Object visit(PrintSp node, Object param) {
		super.visit(node, param);
		if (node.getExpression() != null)
			predicado(esTipoSimple(node.getExpression().getType()), "Valor debe ser un tipo simple ", node);

		return null;
	}

	// class PrintLn { Expression expression; }
	public Object visit(PrintLn node, Object param) {
		super.visit(node, param);
		if (node.getExpression() != null)
			predicado(esTipoSimple(node.getExpression().getType()), "Valor debe ser un tipo simple ", node);

		return null;
	}

	// class Read { Expression expression; }
	public Object visit(Read node, Object param) {
		super.visit(node, param);
		if (node.getExpression() != null) {
			predicado(esTipoSimple(node.getExpression().getType()), "Valor debe ser un tipo simple ", node);
			predicado(node.getExpression().isModificable(), "Valor debe ser modificable", node);
		}

		return null;
	}

//	class If { Expression condition;  List<Sentence> trueBody;  List<Sentence> falseBody; }
	public Object visit(If node, Object param) {
		for (Sentence trueSentence : node.getTrueBody()) {
			trueSentence.setFunctionSelf(node.getFunctionSelf());
		}
		for (Sentence falseSentence : node.getFalseBody()) {
			falseSentence.setFunctionSelf(node.getFunctionSelf());
		}
		super.visit(node, param);
		predicado(node.getCondition().getType().getClass() == IntType.class, "La condici髇 debe ser de tipo entero",
				node);
		return null;
	}

	// class While { Expression condition; List<Sentence> body; }
	public Object visit(While node, Object param) {
		for (Sentence sentence : node.getBody()) {
			sentence.setFunctionSelf(node.getFunctionSelf());
		}
		super.visit(node, param);
		predicado(node.getCondition().getType().getClass() == IntType.class, "La condici髇 debe ser de tipo entero",
				node);

		return null;
	}

//	class CallFunctionSentence { String name;  List<Expression> args; }
	public Object visit(CallFunctionSentence node, Object param) {
		super.visit(node, param);
		boolean isSize = node.getArgs().size() == node.getDefinition().getParameters().size();
		predicado(isSize, "El n鷐ero de par醡etros no coincide", node);
		if (isSize) {
			predicado(sameTypeParameters(node.getArgs(), node.getDefinition().getParameters()),
					"Los par醡etros no son del mismo tipo", node);
		}
		return null;
	}

//	class IntConstant { String value; }
	public Object visit(IntConstant node, Object param) {
		node.setType(new IntType());
		node.setModificable(false);
		return null;
	}

//	class FloatConstant { String value; }
	public Object visit(FloatConstant node, Object param) {
		node.setType(new FloatType());
		node.setModificable(false);
		return null;
	}

	// class CharConstant { String value; }
	public Object visit(CharConstant node, Object param) {
		node.setType(new CharType());
		node.setModificable(false);
		return null;
	}

//	class Variable { String name; }
	public Object visit(Variable node, Object param) {
		node.setType(node.getDefinition().getTipo());
		node.setModificable(true);
		return null;
	}

//	class ArithmeticExpression { Expression left;  String operator;  Expression right; }
	public Object visit(ArithmeticExpression node, Object param) {
		super.visit(node, param);
		predicado(node.getLeft().getType().getClass() == node.getRight().getType().getClass(),
				"Las dos expresiones deben ser del mismo tipo", node);

		predicado(esTipoSimple(node.getLeft().getType()), "La parte izquierda de la expresi髇 no es un tipo simple",
				node);
		predicado(esTipoSimple(node.getRight().getType()), "La parte derecha de la expresi髇 no es un tipo simple",
				node);
		node.setType(node.getLeft().getType());
		node.setModificable(false);
		return null;
	}

//	class LogicalExpression { Expression left;  String operator;  Expression right; }
	public Object visit(LogicalExpression node, Object param) {
		super.visit(node, param);
		predicado(node.getLeft().getType().getClass() == IntType.class ||
				node.getLeft().getType().getClass() == FloatType.class,
				"La parte izquierda debe ser tipo int o float", node);
		predicado(node.getRight().getType().getClass() == IntType.class||
				node.getRight().getType().getClass() == FloatType.class,
				"La parte derecha debe ser tipo int o float", node);
		predicado(node.getRight().getType().getClass() == node.getRight().getType().getClass(),
				"Las dos partes deben ser del mismo tipo", node);
		if(node.getLeft().getType().getClass() == FloatType.class && 
				node.getRight().getType().getClass() == FloatType.class) {
			node.setType(new IntType());
		}else {
			node.setType(node.getLeft().getType());
		}
		node.setModificable(false);
		return null;
	}

//	class NotExpression { String operator;  Expression expr; }
	public Object visit(NotExpression node, Object param) {
		super.visit(node, param);
		predicado(node.getExpr().getType().getClass() == IntType.class, "La expresi髇 debe ser de tipo entero", node);
		node.setType(node.getExpr().getType());
		node.setModificable(false);
		return null;
	}

//	class ParenthesisExpression { Expression expr; }
	public Object visit(ParenthesisExpression node, Object param) {
		super.visit(node, param);
		predicado(node.getExpr().getClass() == ArithmeticExpression.class || 
				node.getExpr().getClass() == LogicalExpression.class, 
				"La expresi髇 debe ser de tipo aritm閠ico",node);
		node.setType(node.getExpr().getType());
		node.setModificable(false);
		return null;
	}

//	class ArrayAccess { Expression left;  Expression right; }
	public Object visit(ArrayAccess node, Object param) {
		super.visit(node, param);
		boolean isType = node.getLeft().getType().getClass() == ArrayType.class;
		predicado(isType, "La expresi髇 debe ser de tipo array", node);
		predicado(node.getRight().getType().getClass() == IntType.class,
				"El 韓dice debe ser entero",node);
		if (isType) {
			ArrayType tipo = (ArrayType) (node.getLeft().getType());
			node.setType(tipo.getTipo());
			node.setModificable(true);
		}
		return null;
	}

//	class StructAccess { Expression left;  String right; }
	public Object visit(StructAccess node, Object param) {
		super.visit(node, param);
		boolean isType = node.getLeft().getType().getClass() == IdentType.class;
		predicado(isType, "La expresi髇 debe de ser de tipo Struct", node);
		if (isType) {
			IdentType type = (IdentType) (node.getLeft().getType());
			CampoStruct campo = existeCampoStruct(type.getDefinition().getFields(), node.getRight());
			predicado(campo != null, "El campo de struct no existe", node);
			if (campo != null) {
				node.setType(campo.getTipo());
				node.setModificable(true);
			}
		}
		return null;
	}

//	class CallFunction { String name;  List<Expression> args; }
	public Object visit(CallFunction node, Object param) {
		super.visit(node, param);
		boolean isSize = node.getArgs().size() == node.getDefinition().getParameters().size();
		predicado(isSize, "El n鷐ero de par醡etros no coincide", node);
		if (isSize) {
			predicado(sameTypeParameters(node.getArgs(), node.getDefinition().getParameters()),
					"Los par醡etros no son del mismo tipo", node);
			predicado(node.getDefinition().getTipo() != null,
					"La funci髇 no tiene tipo de retorno",node);
			node.setType(node.getDefinition().getTipo());
			node.setModificable(false);
		}
		return null;
	}

//	class Cast { Type tipo;  Expression value; }
	public Object visit(Cast node, Object param) {
		super.visit(node, param);
		predicado(node.getValue().getType().getClass() != node.getTipo().getClass(),
				"El tipo de destino es igual al de origen", node);
		predicado(esTipoSimple(node.getValue().getType()), "La expresi髇 debe ser de tipo simple",node);
		node.setType(node.getTipo());
		node.setModificable(false);
		return null;
	}

	// --------------------------------------------------------
	// Funciones auxiliares

	private boolean sameTypeParameters(List<Expression> args, List<DefVariable> parameters) {
		for (int i = 0; i < parameters.size(); i++) {
			if (args.get(i).getType().getClass() != parameters.get(i).getTipo().getClass())
				return false;
		}
		return true;
	}

	private boolean esTipoSimple(List<DefVariable> parameters) {
		for (DefVariable defVariable : parameters) {
			if (!esTipoSimple(defVariable.getTipo()))
				return false;
		}
		return true;
	}

	private boolean esTipoSimple(Type type) {
		return (type.getClass() == IntType.class || type.getClass() == CharType.class
				|| type.getClass() == FloatType.class);
	}

	private CampoStruct existeCampoStruct(List<CampoStruct> fields, String name) {
		for (CampoStruct cStruct : fields) {
			if (cStruct.getName().equals(name))
				return cStruct;
		}
		return null;
	}

	/**
	 * predicado. M茅todo auxiliar para implementar los predicados. Borrar si no se
	 * quiere usar.
	 *
	 * Ejemplos de uso (suponiendo que existe un m茅todo "esPrimitivo(expr)"):
	 *
	 * 1. predicado(esPrimitivo(expr.tipo), "La expresi贸n debe ser de un tipo
	 * primitivo", expr.getStart()); 2. predicado(esPrimitivo(expr.tipo), "La
	 * expresi贸n debe ser de un tipo primitivo", expr); // Se asume getStart() 3.
	 * predicado(esPrimitivo(expr.tipo), "La expresi贸n debe ser de un tipo
	 * primitivo");
	 *
	 * NOTA: El m茅todo getStart() (ejemplo 1) indica la linea/columna del fichero
	 * fuente donde estaba el nodo (y as铆 poder dar informaci贸n m谩s detallada de
	 * la posici贸n del error). Si se usa VGen, dicho m茅todo habr谩 sido generado
	 * en todos los nodos del AST. No es obligatorio llamar a getStart() (ejemplo
	 * 2), ya que si se pasa el nodo, se usar谩 por defecto dicha posici贸n. Si no
	 * se quiere imprimir la posici贸n del fichero, se puede omitir el tercer
	 * argumento (ejemplo 3).
	 *
	 * @param condition     Debe cumplirse para que no se produzca un error
	 * @param errorMessage  Se imprime si no se cumple la condici贸n
	 * @param posicionError Fila y columna del fichero donde se ha producido el
	 *                      error.
	 */

	private void predicado(boolean condition, String errorMessage, AST node) {
		predicado(condition, errorMessage, node.getStart());
	}

	private void predicado(boolean condition, String errorMessage, Position position) {
		if (!condition)
			errorManager.notify("Type Checking", errorMessage, position);
	}

	private void predicado(boolean condition, String errorMessage) {
		predicado(condition, errorMessage, (Position) null);
	}

	private ErrorManager errorManager;
}
