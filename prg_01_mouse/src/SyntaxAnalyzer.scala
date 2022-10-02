/*
 * CS3210 - Principles of Programming Languages - Fall 2022
 * Instructor: Thyago Mota
 * Description: Prg 01 - SyntaxAnalyzer (an iterable syntax analyzer)
 * Student(s) Name(s): Samuel Vang
 */

class SyntaxAnalyzer(private var source: String) {

  private val it = new LexicalAnalyzer(source).iterator
  private var current: Lexeme = null

  // returns the current lexeme
  private def getLexeme: Lexeme = {
    if (current == null)
      nextLexeme
    current
  }

  // advances the input one lexeme
  private def nextLexeme = {
    current = it.next
  }

  // returns true if the given token identifies a statement (or the beginning of a statement)
  private def isStatement(token: Token.Value): Boolean = {
    token == Token.IDENTIFIER     ||
    token == Token.LITERAL        ||
    token == Token.STRING         ||
    token == Token.INPUT          ||
    token == Token.OUTPUT         ||
    token == Token.ASSIGNMENT     ||
    token == Token.ADDITION       ||
    token == Token.SUBTRACTION    ||
    token == Token.MULTIPLICATION ||
    token == Token.DIVISION       ||
    token == Token.MODULUS        ||
    token == Token.LESS           ||
    token == Token.LESS_EQUAL     ||
    token == Token.GREATER        ||
    token == Token.GREATER_EQUAL  ||
    token == Token.EQUAL          ||
    token == Token.DIFFERENT      ||
    token == Token.BREAK          ||
    token == Token.DOT            ||
    token == Token.OPEN_BRACKET   ||
    token == Token.OPEN_PAR
  }

  // returns true if the given token identifies a line (or the beginning of a line)
  // a line can be a statement or a comma
  private def isLine(token: Token.Value): Boolean = {
    isStatement(token) || token == Token.COMMENT
  }

  // parses the program, returning its corresponding parse tree
  def parse: Node = {
    parseMouse
  }

  // mouse = { line } ´$$´
  private def parseMouse: Node = {
    val node = new Node(new Lexeme("mouse"))

    // parse a line until 'end of program' is read
    while (getLexeme.token != Token.EO_PRG){
      node.add(parseLine)
    }

    // add end of program node
    node.add(new Node(getLexeme))

    node
  }

  // line = statement | comment
  private def parseLine: Node = {
    val node = new Node(new Lexeme("line"))

    // decide to add a comment or a statement
    if (getLexeme.token == Token.COMMENT) {
      node.add(new Node(getLexeme))
      nextLexeme
    }
    else{
      node.add(parseStatement)
    }
    node
  }

  // statement = ´?´ | ´!´ | string | identifier | ´=´ | literal | ´+´ | ´-´ | ´*´ | ´/´ | ´%´ | ´<´ | ´<=´ | ´>´ | ´>=´ | ´==´ | ´!=´ | ´^´ | ´.´ |  | if | while
  private def parseStatement: Node = {
    val node = new Node(new Lexeme("statement"))

    // decide if token is an open or close bracket
    if (getLexeme.token == Token.OPEN_BRACKET){
      node.add(parseIf)
    }
    else if (getLexeme.token == Token.OPEN_PAR){
      node.add(parseWhile)
    }
    else if (getLexeme.token == Token.CLOSE_BRACKET) { // throws exception if close bracket is read
      throw new Error("Opening '[' expected")
    }
    else if (getLexeme.token == Token.CLOSE_PAR) { // throws exception if close par is read
      throw new Error("Opening '(' expected")
    }
    else {
      node.add(new Node(getLexeme)) // add statement
      nextLexeme
    }

    node
  }

  // if = ´[´ { line } ´]´
  def parseIf: Node = {
    val node = new Node(new Lexeme("if"))

    // check if open bracket is read
    if (getLexeme.token == Token.OPEN_BRACKET){
      node.add(new Node(getLexeme))
      nextLexeme // consume open bracket
      while(getLexeme.token != Token.CLOSE_BRACKET){ // iterate through if statement until close bracket is read
        if (getLexeme.token == Token.EOF || getLexeme.token == Token.CLOSE_PAR){
          throw new Error("Closing ']' expected") // throws exception if no close bracket is read
        }
        node.add(parseLine)
      }
      node.add(new Node(getLexeme))
      nextLexeme // consume close bracket
    }
    else
      throw new Error("Opening '[' expected")
    node
  }

  // while = ´(´ { line } ´)´
  def parseWhile: Node = {
    val node = new Node(new Lexeme("while"))

    // check if open par is read
    if (getLexeme.token == Token.OPEN_PAR){
      node.add(new Node(getLexeme))
      nextLexeme // consume open par
      while(getLexeme.token != Token.CLOSE_PAR){ // iterate through if statement until close par is read
        if (getLexeme.token == Token.EOF || getLexeme.token == Token.CLOSE_BRACKET){
          throw new Error("Closing ')' expected") // throws exception if no close par is read
        }
        node.add(parseLine)
      }
      node.add(new Node(getLexeme))
      nextLexeme // consume close par
    }
    else
      throw new Error("Opening '(' expected")
    node
  }
}

object SyntaxAnalyzer {
  def main(args: Array[String]): Unit = {

    // check if source file was passed through the command-line
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    val syntaxAnalyzer = new SyntaxAnalyzer(args(0))
    val parseTree = syntaxAnalyzer.parse
    print(parseTree)
  }
}
