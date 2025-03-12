# dyn
A DYNamically typed general purpose language

## Context-free grammar

The following context-free grammar describes the Dyn language and is used as a guide to build
the parser. It will be updated and improved as more features are added

```
program = declaration* EOF ;
declaration = statement | varDecl ;
statement = exprStmt | printStmt | block | ifStmt | whileStmt | forStmt ;
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
unary = ("!" | "-") unary | primary ;
primary = STRING | NUMBER | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;
```

## Supported features

```text
// Types
var string = "Hello, World!";
var float = 1.2;
var number = 1;
var nullable = nil;
var bool = true;

// Printing
print "Printing...";

// Assignment, equality and comparison
var x = 10;
var y = 10;

print x == y;
print x != y;
print x <= y;
print x >= y;
print x < 10;
print x > 10;

// Unary
var u = 20;

print !u;
print -u;

// Arithmetic
var q = 1;
var w = 2;

print q+w;
print q*w;
print q/w;

// Blocks/scope
{
	var x = "x is removed after block ends";
}

// If statements
var a = 1;
var b = 2;

if (a < b) {
	print "This is true";
}

// Loops
var i = 0;

while (i < 10) {
	print i;

	i = i + 1;
}

for (var j = 0; j < 2; j = j + 1) {
	print j;
}
```

## Running dyn

Generate ./src/Expr.java and ./src/Stmt.java files
```bash
java ./tools/GenerateAst.java
```

Dyn can be run from file or through REPL/prompt

File:

```bash
java ./src/Dyn.java examples/<file>.dyn
```

REPL:

```bash
java ./src/Dyn.java
```
