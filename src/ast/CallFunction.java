/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	callFunction:expression -> name:String  args:expression*

public class CallFunction extends AbstractExpression {

	public CallFunction(String name, List<Expression> args) {
		this.name = name;
		this.args = args;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(args);
	}

	public CallFunction(Object name, Object args) {
		this.name = (name instanceof Token) ? ((Token)name).getText() : (String) name;
		this.args = this.<Expression>getAstFromContexts(args);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(name, args);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Expression> getArgs() {
		return args;
	}
	public void setArgs(List<Expression> args) {
		this.args = args;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	public Function getDefinition() {
		return definition;
	}

	public void setDefinition(Function definition) {
		this.definition = definition;
	}


	private String name;
	private List<Expression> args;
	private Function definition;
	public String toString() {
       return "{name:" + getName() + ", args:" + getArgs() + "}";
   }
}
