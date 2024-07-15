# dyn
A DYNamically typed general purpose language

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
