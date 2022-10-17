/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	function:instruction -> name:String  parameters:defVariable*  tipo:type  body:sentence*

public class Function extends AbstractInstruction {

	public Function(String name, List<DefVariable> parameters, Type tipo, List<Sentence> body) {
		this.name = name;
		this.parameters = parameters;
		this.tipo = tipo;
		this.body = body;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(parameters, tipo, body);
	}

	public Function(Object name, Object parameters, Object tipo, Object body) {
		this.name = (name instanceof Token) ? ((Token)name).getText() : (String) name;
		this.parameters = this.<DefVariable>getAstFromContexts(parameters);
		this.tipo = (Type) getAST(tipo);
		this.body = this.<Sentence>getAstFromContexts(body);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(name, parameters, tipo, body);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<DefVariable> getParameters() {
		return parameters;
	}
	public void setParameters(List<DefVariable> parameters) {
		this.parameters = parameters;
	}

	public Type getTipo() {
		return tipo;
	}
	public void setTipo(Type tipo) {
		this.tipo = tipo;
	}

	public List<Sentence> getBody() {
		return body;
	}
	public void setBody(List<Sentence> body) {
		this.body = body;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String name;
	private List<DefVariable> parameters;
	private Type tipo;
	private List<Sentence> body;

	public String toString() {
       return "{name:" + getName() + ", parameters:" + getParameters() + ", tipo:" + getTipo() + ", body:" + getBody() + "}";
   }
	
	public List<LocalVariable> getLocals(){
		List<LocalVariable> locals = new ArrayList<>();
		for (Sentence sentence : getBody()) {
			if(sentence.getClass() == LocalVariable.class)
				locals.add((LocalVariable) sentence);
		}
		return locals;
	}
}
