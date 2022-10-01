# Instructions 

Consider the grammar below specified using EBNF notation.  

```
expression = expression ( ´+´  | ´-´ ) term | term  
term       = term ( ´*´ | ´/´ ) factor | factor 
factor     = identifier | literal 
identifier = ´a´ | ´b´ | ´c´ | ´d´ | ´e´ | ´f´ | ´g´ | ´h´ | ´i´ | ´j´ | ´k´ | ´l´ | ´m´  
          | ´n´ | ´o´ | ´p´ | ´q´ | ´r´ | ´s´ | ´t´ | ´u´ | ´v´ | ´w´ | ´x´ | ´y´ | ´z´   
literal    = digit { digit } 
digit      = ´0´ | ´1´ | ´2´ | ´3´ | ´4´ | ´5´ | ´6´ | ´7´ | ´8´ | ´9´
```

Write a lexical analyzer for the language described by the grammar. The output of your lexical analyzer should be a list of pairs containing a lexical unit followed by its token number, in the order of their appearance.  Below are some source codes (with expected outputs) for you to try.  

## Source 1 

```
52 + x * 231 / y - 8 
```

output: 

```
src.Lexeme(52, LITERAL)
src.Lexeme(+, ADDITION)
src.Lexeme(x, IDENTIFIER)
src.Lexeme(*, MULTIPLICATION)
src.Lexeme(231, LITERAL)
src.Lexeme(/, DIVISION)
src.Lexeme(y, IDENTIFIER)
src.Lexeme(-, SUBTRACTION)
src.Lexeme(8, LITERAL)
```
 
## Source 2 

```
z + 3 - x * 2931 
```
 
output: 

```
src.Lexeme(z, IDENTIFIER)
src.Lexeme(+, ADDITION)
src.Lexeme(3, LITERAL)
src.Lexeme(-, SUBTRACTION)
src.Lexeme(x, IDENTIFIER)
src.Lexeme(*, MULTIPLICATION)
src.Lexeme(2931, LITERAL)
``` 

## Source 3 

Note that this source has two tabs after “+”, new lines, and multiple letters together. 

```
x +   4 

 

- 2 / abc + 2 
```

output: 

```
src.Lexeme(x, IDENTIFIER)
src.Lexeme(+, ADDITION)
src.Lexeme(4, LITERAL)
src.Lexeme(-, SUBTRACTION)
src.Lexeme(2, LITERAL)
src.Lexeme(/, DIVISION)
src.Lexeme(a, IDENTIFIER)
src.Lexeme(b, IDENTIFIER)
src.Lexeme(c, IDENTIFIER)
src.Lexeme(+, ADDITION)
src.Lexeme(2, LITERAL)
```
 
## Source 4

```
x > 4 * c 
```

output: 

```
Exception: Lexical Analyzer Error: unrecognizable symbol found! 
```

# Update

Update your lexical analyzer by changing the way identifiers are defined using the following: 

```
identifier = letter { ( letter | digit ) } 
```