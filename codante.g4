grammar codante;

program: statement+;

statement: variableDeclaration
         | assignment
         | ifStatement
         | inputStatement
         | outputStatement
         | mathExpression
         ;

variableDeclaration: type Identifier '=' expression ';';

type: 'int' | 'float' | 'string';

assignment: Identifier '=' expression ';';

ifStatement: 'if' '(' condition ')' statement ('else' statement)?;

condition: expression relationalOperator expression;

relationalOperator: '==' | '!=' | '<' | '>' | '<=' | '>=';

inputStatement: 'input' Identifier ';';

outputStatement: 'output' expression ';';

mathExpression: expression;

expression: additionExpression;

additionExpression: multiplicationExpression ( ('+' | '-') multiplicationExpression )*;

multiplicationExpression: atom ( ('*' | '/') atom )*;

atom: Identifier
    | Number
    | StringLiteral
    | '(' expression ')'
    ;

Number: ('0'..'9')+ ('.' ('0'..'9')+)?;

StringLiteral: '"' (~["\r\n])* '"';

Identifier: [a-zA-Z_] [a-zA-Z_0-9]*;

Whitespace: [ \t\r\n]+ -> skip;
