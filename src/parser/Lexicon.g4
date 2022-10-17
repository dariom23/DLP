lexer grammar Lexicon
	;
	
IDENT
	: [a-zA-Z][a-zA-Z0-9_]*
	;
	
INT_CONSTANT
	: [0-9]+
	;

FLOAT_CONSTANT
	: [0-9]+ '.' [0-9]+
	;
	
CHARACTER
	:'\'' ~[\n\r\t] '\''
	| '\'''\\n''\''
	;

LINE_COMMENT
	: '//' .*? ('\n' | EOF) -> skip
	;

MULTILINE_COMMENT
	: '/*' .*? '*/' -> skip
	;

WHITESPACE
	: [ \t\r\n]+ -> skip
	;
