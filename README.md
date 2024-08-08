# dyn
A DYNamically typed general purpose language

## Context-free grammar

The following context-free grammar describes the Dyn language and is used as a guide to build
the parser. It will be updated and improved as more features are added

```
program = declaration* EOF ;
declaration = statement | varDecl | funcDecl ;
funcDecl = "fun" function ;
function = IDENTIFIER "(" parameters? ")" block ;
parameters = IDENTIFIER ( "," IDENTIFIER )* ;
statement = exprStmt | printStmt | block | ifStmt | whileStmt | forStmt | returnStmt ;
returnStmt = "return" expression? ";" ;
forStmt = "for" "(" ( varDecl | exprStmt | ";" ) ";" expression? ";" expression? ";" ")" statment ;
ifStmt = "if" "(" expression ")" statement ( "else" statement )? ;
whileStmt = "while" "(" expression ")" statement ;
block = "{" declaration* "}" ;
varDecl = "var" IDENTIFIER ("=" expression)? ";" ;
exprStmt = expression ";" ;
printStmt = "print" expression "';" ;

expression = assignment ;
assignment = IDENTIFIER "=" assignment | logicOr ;
logicOr = logicAnd ( "or" loginAnd )* ;
logicAnd = equality ( "and" equaity )* ;
equality = comparison (("==" | "!=") comparison)* ;
comparison = term (("<" | "<=" | ">" | ">=") term)* ;
term = factor (("+" | "-") factor)* ;
factor = unary (("*" | "/") unary)* ;
unary = ("!" | "-") unary | call ;
call = primary ( "(" arguments? ")" )* ;
arguments = expression ( "," expression )* ;
primary = STRING | NUMBER | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;
```

## Running dyn

Dyn can be run from file or through REPL/prompt

File:

```bash
java ./src/Dyn.java examples/<file>.dyn
```

REPL:

```bash
java ./src/Dyn.java
```

Generate Expr class which creates ./src/Expr.java file
```bash
java ./tools/GenerateAst.java
```

## Scanning/Lexical analysis

Scanning is the first major process in building the Dyn interpreter. It transforms either a
source file or System.in, depending on how the user chooses to run Dyn, to a list of tokens.
A token provides more information for a scanned chunk of text take the following example:

```
var name = "dyn";
```

The scanner will produce the following tokens from the line above:

```
Token [type=VAR, lexeme=var, literal=null, line=1]
Token [type=IDENTIFIER, lexeme=name, literal=null, line=1]
Token [type=EQUAL, lexeme==, literal=null, line=1]
Token [type=STRING, lexeme="dyn", literal=dyn, line=1]
Token [type=SEMICOLON, lexeme=;, literal=null, line=1]
```

Each token stores the type, lexeme, literal and line number for each chunk of text which
is vital information for processes such as parsing and interpreting. Scanning can also throw
errors if a character is not part of the language or if a string has not been closed, see the
following examples:

```
@

[line 1] Error : Unexpected character
```

```
var name = "dyn

[line 2] Error : Unterminated string
```
