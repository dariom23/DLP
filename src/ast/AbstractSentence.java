/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

public abstract class AbstractSentence extends AbstractAST implements Sentence {

	public void setFunctionSelf(Function functionSelf) {
        this.functionSelf = functionSelf;
    }

    public Function getFunctionSelf() {
        return functionSelf;
    }
    
    private Function functionSelf;
	
}
