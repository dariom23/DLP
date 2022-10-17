/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	cast:expression -> tipo:type  value:expression

public class Cast extends AbstractExpression {

	public Cast(Type tipo, Expression value) {
		this.tipo = tipo;
		this.value = value;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo, value);
	}

	public Cast(Object tipo, Object value) {
		this.tipo = (Type) getAST(tipo);
		this.value = (Expression) getAST(value);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo, value);
	}

	public Type getTipo() {
		return tipo;
	}
	public void setTipo(Type tipo) {
		this.tipo = tipo;
	}

	public Expression getValue() {
		return value;
	}
	public void setValue(Expression value) {
		this.value = value;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Type tipo;
	private Expression value;

	public String toString() {
       return "{tipo:" + getTipo() + ", value:" + getValue() + "}";
   }
}
