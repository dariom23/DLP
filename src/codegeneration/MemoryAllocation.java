/**
 * Tutorial de Diseño de Lenguajes de Programación
 * @author Raúl Izquierdo
 */

package codegeneration;

import ast.CampoStruct;
import ast.Function;
import ast.GlobalVariable;
import ast.Instruction;
import ast.LocalVariable;
import ast.Program;
import ast.Sentence;
import ast.Struct;
import visitor.DefaultVisitor;

/**
 * Clase encargada de asignar direcciones a las variables.
 */
public class MemoryAllocation extends DefaultVisitor {

//	class Program { List<Instruction> body; }
	public Object visit(Program node, Object param) {
		int currentAddress = 0;
		if (node.getBody() != null)
			for (Instruction child : node.getBody()) {
				if(child instanceof GlobalVariable) {
					GlobalVariable global = (GlobalVariable) child;
					global.setAddress(currentAddress);
					currentAddress += global.getDefvariable().getTipo().getSize();
				}
				child.accept(this, param);
			}

		return null;
	}

//	class Struct { String name;  List<CampoStruct> fields; }
	public Object visit(Struct node, Object param) {
		int currentAddress = 0;
		if (node.getFields() != null)
			for (CampoStruct child : node.getFields()) {
				child.setAddress(currentAddress);
				currentAddress += child.getTipo().getSize();
			}
		node.setSize(currentAddress);
		return null;
	}

//	class Function { String name;  List<DefVariable> parameters;  Type tipo;  List<Sentence> body; }
	public Object visit(Function node, Object param) {
		int currentAddressParameters = 4;
		int currentAddressLocals = 0;
		if (node.getParameters() != null)
			for (int i = node.getParameters().size() - 1; i >= 0; i--) {
				node.getParameters().get(i).setAddress(currentAddressParameters);
				currentAddressParameters += node.getParameters().get(i).getTipo().getSize();
			}

		if (node.getBody() != null)
			for (Sentence child : node.getBody()) {
				if (child instanceof LocalVariable) {
					LocalVariable local = (LocalVariable) child;
					currentAddressLocals -= local.getDefvariable().getTipo().getSize();
					local.setAddress(currentAddressLocals);
				}
			}

		return null;
	}
}
