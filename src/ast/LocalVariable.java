/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import codegeneration.Ambito;
import visitor.Visitor;

//	localVariable:sentence -> defvariable:defVariable

public class LocalVariable extends AbstractSentence {

	public LocalVariable(DefVariable defvariable) {
		this.defvariable = defvariable;
		this.defvariable.setAmbito(Ambito.LOCAL);

		// Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
		// Obtiene la linea/columna a partir de las de los hijos.
		setPositions(defvariable);
	}

	public LocalVariable(Object defvariable) {
		this.defvariable = (DefVariable) getAST(defvariable);
		this.defvariable.setAmbito(Ambito.LOCAL);
		// Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
		// Obtiene la linea/columna a partir de las de los hijos.
		setPositions(defvariable);
	}

	public DefVariable getDefvariable() {
		return defvariable;
	}

	public void setDefvariable(DefVariable defvariable) {
		this.defvariable = defvariable;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	private DefVariable defvariable;

	public String toString() {
		return "{defvariable:" + getDefvariable() + "}";
	}

	public int getAddress() {
		return this.defvariable.getAddress();
	}

	public void setAddress(int address) {
		this.defvariable.setAddress(address);
	}
}
