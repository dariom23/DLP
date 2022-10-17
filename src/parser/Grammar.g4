grammar Grammar
	;
import Lexicon
	;

@parser::header {
    import ast.*;
}

start returns[Program ast]
	: instrucciones EOF {$ast = new Program($instrucciones.list); }
	;

instrucciones returns[List<Instruction> list = new ArrayList<Instruction>()]
	: (instruccion { $list.add($instruccion.ast);})*
	;
 	
instruccion returns[Instruction ast]
		   :'var' defVariable';' {$ast = new GlobalVariable($defVariable.ast);}
		   |'struct' IDENT '{' camposStruct  '}' ';' {$ast = new Struct($IDENT,$camposStruct.list);}
		   | funcion {$ast = $funcion.ast;} 
		   ;

campoStruct returns[CampoStruct ast]
		   : IDENT ':' tipo ';'{$ast = new CampoStruct($IDENT, $tipo.ast);}
		   ;

camposStruct returns[List<CampoStruct> list = new ArrayList<CampoStruct>()]
    : (campoStruct { $list.add($campoStruct.ast); })+
	;
		   		   
	
funcion returns [Function ast]
		   :IDENT '('argsFunc')' typeOp '{' sentencias '}' {$ast = new Function($IDENT,$argsFunc.list,$typeOp.ast,$sentencias.list);}
		   ;

typeOp returns [Type ast]
		   : (':' tipo {$ast = $tipo.ast;})?
		   ; 
 
 
argsFunc returns[List<DefVariable> list = new ArrayList<DefVariable>()]
    : (defVariable {$list.add($defVariable.ast);} (',' defVariable {$list.add($defVariable.ast);} )*)?
    ;

		   
defVariable returns [DefVariable ast]
		   : IDENT ':' tipo {$ast = new DefVariable($IDENT, $tipo.ast);}
		   ;

sentencias returns[List<Sentence> list = new ArrayList<Sentence>()]
	: (sentencia { $list.add($sentencia.ast);})*
	;

sentencia returns [Sentence ast]
		   : 'var' defVariable ';' {$ast = new LocalVariable($defVariable.ast) ;}
		   | l=expr '=' r=expr ';' {$ast = new Assignment($l.ast,$r.ast);}
		   | 'return' exprOp ';' {$ast = new Return($exprOp.ast);}
		   | 'print' exprOp ';' {$ast = new Print($exprOp.ast);}
		   | 'printsp' exprOp ';'{$ast = new PrintSp($exprOp.ast);}
		   | 'println' exprOp ';' {$ast = new PrintLn($exprOp.ast);}
		   | 'read' expr ';' {$ast = new Read($expr.ast);}
		   | 'if' '(' expr ')' '{' sentencias '}' elseOp {$ast = new If($expr.ast,$sentencias.list,$elseOp.list);}
		   | 'while' '(' expr ')' '{' sentencias '}' {$ast = new While($expr.ast,$sentencias.list);}
		   | IDENT '(' args ')' ';' {$ast = new CallFunctionSentence($IDENT,$args.list);}
		   ;
		
exprOp returns [Expression ast]
		   :(expr {$ast = $expr.ast;})?
		   ;		
		 
elseOp returns[List<Sentence> list = new ArrayList<Sentence>()]
		   : (('else' '{' sentencias '}' { $list = $sentencias.list;}))?
		   ;
		   

args returns[List<Expression> list = new ArrayList<Expression>()]
    : (expr {$list.add($expr.ast);} (',' expr {$list.add($expr.ast);} )*)?
    ;



expr returns [Expression ast]
	  : INT_CONSTANT {$ast = new IntConstant($INT_CONSTANT);}
	  | FLOAT_CONSTANT {$ast = new FloatConstant($FLOAT_CONSTANT);}
	  | CHARACTER {$ast = new CharConstant($CHARACTER);}
	  | IDENT {$ast = new Variable($IDENT);}
	  | '(' expr ')' {$ast = new ParenthesisExpression($expr.ast);}
	  | l=expr '.' IDENT { $ast = new StructAccess($l.ast, $IDENT); }
	  | l=expr '[' r=expr ']' { $ast = new ArrayAccess($l.ast, $r.ast); }
	  | l=expr op=('*' | '/' | '%') r=expr { $ast = new ArithmeticExpression($l.ast, $op.text, $r.ast); }
	  | l=expr op=('+' | '-') r=expr { $ast = new ArithmeticExpression($l.ast, $op.text, $r.ast); }
	  | l=expr op=('==' | '!=' | '>' | '>=' | '<' | '<=') r=expr { $ast = new LogicalExpression($l.ast, $op.text, $r.ast); }
	  | l=expr op='&&' r=expr { $ast = new LogicalExpression($l.ast, $op.text, $r.ast); }
	  | l=expr op='||' r=expr { $ast = new LogicalExpression($l.ast, $op.text, $r.ast); }
	  | op='!' ex=expr {$ast = new NotExpression($op.text,$ex.ast);}
	  | IDENT '(' args ')'  { $ast = new CallFunction( $IDENT, $args.list); }
	  | '<' tipo '>' '(' expr')' { $ast = new Cast( $tipo.ast, $expr.ast); }
	  ;

tipo returns [Type ast]
	: 'int' {$ast = new IntType();}
	| 'float' {$ast = new FloatType();}
	| 'char' {$ast = new CharType();}
	| IDENT  {$ast = new IdentType($IDENT);}
	| '[' INT_CONSTANT ']' tipo {$ast = new ArrayType(new IntConstant($INT_CONSTANT),$tipo.ast);}
	;
	
