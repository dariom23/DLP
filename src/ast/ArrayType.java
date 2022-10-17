/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	arrayType:type -> dimension:intConstant  tipo:type

public class ArrayType extends AbstractType {

	public ArrayType(IntConstant dimension, Type tipo) {
		this.dimension = dimension;
		this.tipo = tipo;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(dimension, tipo);
	}

	public ArrayType(Object dimension, Object tipo) {
		this.dimension = (IntConstant) getAST(dimension);
		this.tipo = (Type) getAST(tipo);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(dimension, tipo);
	}

	public IntConstant getDimension() {
		return dimension;
	}
	public void setDimension(IntConstant dimension) {
		this.dimension = dimension;
	}

	public Type getTipo() {
		return tipo;
	}
	public void setTipo(Type tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private IntConstant dimension;
	private Type tipo;

	public String toString() {
       return "{dimension:" + getDimension() + ", tipo:" + getTipo() + "}";
   }

	@Override
	public int getSize() {
		return Integer.valueOf(getDimension().getValue()) * getTipo().getSize();
	}

	@Override
	public String getSuffix() {
		return tipo.getSuffix();
	}

	@Override
	public String getMAPLName() {
		return getDimension().getValue() +"*" + tipo.getMAPLName();
	}
}
