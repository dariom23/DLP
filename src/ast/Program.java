/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import java.util.*;
import visitor.*;

//	program -> body:instruction*

public class Program extends AbstractAST  {

	public Program(List<Instruction> body) {
		this.body = body;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(body);
	}

	public Program(Object body) {
		this.body = this.<Instruction>getAstFromContexts(body);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(body);
	}

	public List<Instruction> getBody() {
		return body;
	}
	public void setBody(List<Instruction> body) {
		this.body = body;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private List<Instruction> body;

	public String toString() {
       return "{body:" + getBody() + "}";
   }
}
