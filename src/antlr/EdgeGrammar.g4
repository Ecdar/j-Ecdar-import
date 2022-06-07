grammar EdgeGrammar;

@header {
package EdgeGrammar;
}

options { caseInsensitive = true; }

/*
 * Parser Rules
 */


edge                : (guard | update) EOF ;

guard               : expression
                    | or ';'?
                    | and
                    ;

update              : assignments? ;

or                  : (orExpression '||')+ orExpression;
orExpression        : expression | and ;

and                 : (expression '&&')+ expression ;
expression          : BOOLEAN | clockExpr | boolExpr | '(' guard ')';
clockExpr           : VARIABLE OPERATOR INT ;
boolExpr            : VARIABLE OPERATOR BOOLEAN ;

assignments         : (assignment ',' assignments) | (assignment ','?) ;
assignment          : clockAssignment | boolAssignment ;
clockAssignment     : VARIABLE '=' INT ;
boolAssignment      : VARIABLE '=' BOOLEAN ;


/*
 * Lexer Rules
 */

BOOLEAN     : 'true' | 'false' ;

fragment DIGIT :   [0-9] ;
INT            :   DIGIT+ ;

fragment LOWERCASE : [a-z] ;
fragment UPPERCASE : [A-Z] ;
fragment SYMBOL    : ('.' | 'þ' | '€' | '_' | 'ð' | 'đ' | 'œ' | '-') ;
fragment LETTER        : (LOWERCASE | UPPERCASE) ;

VARIABLE    : LETTER (LETTER | DIGIT | SYMBOL)* ;

OPERATOR    : ('>=' | '<=' | '==' | '<' | '>') ;

WS : [ \t\r\n]+ -> skip ;