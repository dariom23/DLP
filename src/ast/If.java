/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	if:sentence -> condition:expression  trueBody:sentence*  falseBody:sentence*

public class If extends AbstractSentence {

	public If(Expression condition, List<Sentence> trueBody, List<Sentence> falseBody) {
		this.condition = condition;
		this.trueBody = trueBody;
		this.falseBody = falseBody;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condition, trueBody, falseBody);
	}

	public If(Object condition, Object trueBody, Object falseBody) {
		this.condition = (Expression) getAST(condition);
		this.trueBody = this.<Sentence>getAstFromContexts(trueBody);
		this.falseBody = this.<Sentence>getAstFromContexts(falseBody);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condition, trueBody, falseBody);
	}

	public Expression getCondition() {
		return condition;
	}
	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public List<Sentence> getTrueBody() {
		return trueBody;
	}
	public void setTrueBody(List<Sentence> trueBody) {
		this.trueBody = trueBody;
	}

	public List<Sentence> getFalseBody() {
		return falseBody;
	}
	public void setFalseBody(List<Sentence> falseBody) {
		this.falseBody = falseBody;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expression condition;
	private List<Sentence> trueBody;
	private List<Sentence> falseBody;

	public String toString() {
       return "{condition:" + getCondition() + ", trueBody:" + getTrueBody() + ", falseBody:" + getFalseBody() + "}";
   }
}
