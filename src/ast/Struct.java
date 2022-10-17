/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	struct:instruction -> name:String  fields:campoStruct*

public class Struct extends AbstractInstruction {

	public Struct(String name, List<CampoStruct> fields) {
		this.name = name;
		this.fields = fields;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(fields);
	}

	public Struct(Object name, Object fields) {
		this.name = (name instanceof Token) ? ((Token)name).getText() : (String) name;
		this.fields = this.<CampoStruct>getAstFromContexts(fields);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(name, fields);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<CampoStruct> getFields() {
		return fields;
	}
	public void setFields(List<CampoStruct> fields) {
		this.fields = fields;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String name;
	private List<CampoStruct> fields;

	public String toString() {
       return "{name:" + getName() + ", fields:" + getFields() + "}";
   }
	private int size;
	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
		
	}
}
