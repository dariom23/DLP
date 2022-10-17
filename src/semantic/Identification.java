/**
 * Tutorial de DiseÃ±o de Lenguajes de ProgramaciÃ³n
 * @author RaÃºl Izquierdo
 */

package semantic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ast.AST;
import ast.CallFunction;
import ast.CallFunctionSentence;
import ast.CampoStruct;
import ast.DefVariable;
import ast.Function;
import ast.GlobalVariable;
import ast.IdentType;
import ast.LocalVariable;
import ast.Position;
import ast.Sentence;
import ast.Struct;
import ast.Variable;
import main.ErrorManager;
import visitor.DefaultVisitor;

public class Identification extends DefaultVisitor {

	public Identification(ErrorManager errorManager) {
		this.errorManager = errorManager;
	}

//	class Function { String name;  List<DefVariable> parameters;  Type tipo;  List<Sentence> body; }
	public Object visit(Function node, Object param) {
		variables.set();
		if (node.getParameters() != null) {
			for (DefVariable child : node.getParameters()) {
				DefVariable definition = variables.getFromTop(child.getName());
				predicado(definition == null, "Parámetro ya definido: " + child.getName(), node);
				variables.put(child.getName(), child);
				child.accept(this, param);
			}
		}
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		Function definition = funciones.get(node.getName());
		predicado(definition == null, "Función ya definida: " + node.getName(), node);
		funciones.put(node.getName(), node);
		if (node.getBody() != null)
			for (Sentence child : node.getBody())
				child.accept(this, param);
		variables.reset();
		return null;
	}

//	class CallFunctionSentence { String name;  List<Expression> args; }
	public Object visit(CallFunctionSentence node, Object param) {
		super.visit(node, param);
		Function callFunction = funciones.get(node.getName());
		predicado(callFunction != null, "Función no definida: " + node.getName(), node);
		node.setDefinition(callFunction);
		return null;
	}

//	class CallFunction { String name;  List<Expression> args; }
	public Object visit(CallFunction node, Object param) {
		super.visit(node, param);
		Function callFunction = funciones.get(node.getName());
		predicado(callFunction != null, "Función no definida: " + node.getName(), node);
		node.setDefinition(callFunction);
		return null;
	}

//	class Struct { String name;  List<CampoStruct> fields; }
	public Object visit(Struct node, Object param) {
		Set<String> campos = new HashSet<>();
		if (node.getFields() != null) {
			for (CampoStruct child : node.getFields()) {
				if (campos.contains(child.getName())) {
					error("Campo de struct duplicado: " + child.getName());
				} else {
					campos.add(child.getName());
					child.accept(this, param);
				}
			}
		}
		Struct definition = estructuras.get(node.getName());
		predicado(definition == null, "Estructura ya definida: " + node.getName(), node);
		estructuras.put(node.getName(), node);
		return null;
	}

//	class IdentType { String value; }
	public Object visit(IdentType node, Object param) {
		Struct definition = estructuras.get(node.getValue());
		predicado(definition != null, "Estructura no definida: " + node.getValue(), node);
		node.setDefinition(definition);
		return null;
	}

//	class GlobalVariable { DefVariable variable; }
	public Object visit(GlobalVariable node, Object param) {
		super.visit(node, param);
		DefVariable definition = variables.getFromAny(node.getDefvariable().getName());
		predicado(definition == null, "Variable global ya definida: " + node.getDefvariable().getName(), node);
		variables.put(node.getDefvariable().getName(), node.getDefvariable());
		return null;
	}

//	class LocalVariable { Variable variable; }
	public Object visit(LocalVariable node, Object param) {
		super.visit(node, param);
		DefVariable definition = variables.getFromTop(node.getDefvariable().getName());
		predicado(definition == null, "Variable local ya definida: " + node.getDefvariable().getName(), node);
		variables.put(node.getDefvariable().getName(), node.getDefvariable());
		return null;
	}
	
//	class Variable { String name; }
	public Object visit(Variable node, Object param) {
		DefVariable definition = variables.getFromAny(node.getName());
		predicado(definition != null , "Variable no definida: " + node.getName(), node);
		node.setDefinition(definition);
		return null;
	}
	
//	class CampoStruct { DefVariable defvariable; }
	public Object visit(CampoStruct node, Object param) {
		 super.visit(node, param);

		return null;
	}

	// # --------------------------------------------------------
	// MÃ©todos auxiliares recomendados (opcionales) -------------

	private void error(String msg) {
		error(msg, null);
	}

	private void error(String msg, Position position) {
		errorManager.notify("Identification", msg, position);
	}

	private void predicado(boolean condition, String errorMessage, AST node) {
		if (!condition)
			error(errorMessage, node.getStart());
	}

	private ErrorManager errorManager;
	private Map<String, Function> funciones = new HashMap<String, Function>();
	private Map<String, Struct> estructuras = new HashMap<String, Struct>();
	private ContextMap<String, DefVariable> variables = new ContextMap<>();
}
