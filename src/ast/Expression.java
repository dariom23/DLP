/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

public interface Expression extends AST {
	public void setType(Type type);

    public Type getType();

    public void setModificable(boolean modificable);

    public boolean isModificable();
}
