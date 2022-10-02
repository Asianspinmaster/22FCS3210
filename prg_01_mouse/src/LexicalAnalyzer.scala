/*
 * CS3210 - Principles of Programming Languages - Fall 2022
 * Instructor: Thyago Mota
 * Description: Prg 01 - LexicalAnalyzer (an iterable lexical analyzer)
 * Student(s) Name(s): Samuel Vang
 */

import LexicalAnalyzer.{BLANKS, DIGITS, LETTERS, NEW_LINE, PUNCTUATIONS, SPECIALS}
import scala.io.Source

class LexicalAnalyzer(private var source: String) extends Iterable[Lexeme]{

  var input = ""
  for (line <- Source.fromFile(source).getLines)
    input += line + LexicalAnalyzer.NEW_LINE
  input = input.trim

  // checks if reached eof
  private def eof: Boolean = input.length == 0

  var currentChar: Char = 0

  // returns the current char
  private def getChar = {
    if (!eof)
      currentChar = input(0)
    currentChar
  }

  // advances the input one character
  private def nextChar: Unit = {
    if (!eof)
      input = input.substring(1)
  }

  // checks if input has a blank character ahead
  private def hasBlank: Boolean = {
    LexicalAnalyzer.BLANKS.contains(getChar)
  }

  // reads the input until a non-blank character is found, updating the input
  def readBlanks: Unit = {
    while (!eof && hasBlank)
      nextChar
  }

  // checks if input has a letter ahead
  private def hasLetter: Boolean = {
    LexicalAnalyzer.LETTERS.contains(getChar)
  }

  // checks if input has a digit ahead
  private def hasDigit: Boolean = {
    LexicalAnalyzer.DIGITS.contains(getChar)
  }

  // checks if input has a special character ahead
  private def hasSpecial: Boolean = {
    LexicalAnalyzer.SPECIALS.contains(getChar)
  }

  // checks if input has a punctuation character ahead
  private def hasPunctuation: Boolean = {
    LexicalAnalyzer.PUNCTUATIONS.contains(getChar)
  }

  // returns an iterator for the lexical analyzer
  override def iterator: Iterator[Lexeme] = {

    new Iterator[Lexeme] {

      // returns true/false depending whether there is a lexeme to be read from the input
      override def hasNext: Boolean = {
        readBlanks
        !eof
      }

      // return the next lexeme (or Token.EOF if there isn't any lexeme left to be read)
      override def next(): Lexeme = {

        if (!hasNext)
          return new Lexeme("eof", Token.EOF)

        // identifies comment
        if (getChar == ';'){
          var str = ""
          nextChar
          nextChar
          while (getChar != NEW_LINE){
            str += getChar
            nextChar
          }
          return new Lexeme(str, Token.COMMENT)
        }

        // identifies end of program
        else if (getChar == '$'){
          var str = getChar + ""
          nextChar
          if (getChar == '$'){
            str += getChar
            nextChar
            return new Lexeme(str, Token.EO_PRG)
          }
        }

        // identifies input
        else if (getChar == '?'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.INPUT)
        }

        // identifies different or output
        else if (getChar == '!'){
          var str = getChar + ""
          nextChar
          if (getChar == '='){
            str += getChar
            nextChar
            return new Lexeme(str, Token.DIFFERENT)
          }
          else {
            return new Lexeme(str, Token.OUTPUT)
          }
        }

        // identifies string
        else if (getChar == '"'){
          var str = ""
          nextChar
          while (getChar != '"'){
            str += getChar
            nextChar
          }
          nextChar
          return new Lexeme(str, Token.STRING)
        }

        // identifies identifier
        else if (hasLetter){
          var str = getChar + ""
          nextChar
          while(hasLetter || hasDigit || getChar == '_'){
            str += getChar
            nextChar
          }
          return new Lexeme(str, Token.IDENTIFIER)
        }

        // identifies equal or assignment
        else if (getChar == '='){
          var str = getChar + ""
          nextChar
          if (getChar == '='){
            str += getChar
            nextChar
            return new Lexeme(str, Token.EQUAL)
          }
          else {
            return new Lexeme(str, Token.ASSIGNMENT)
          }
        }

        // identifies literal
        else if (hasDigit){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.LITERAL)
        }

        // identifies addition
        else if (getChar == '+'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.ADDITION)
        }

        // identifies subtraction
        else if (getChar == '-'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.SUBTRACTION)
        }

        // identifies multiplication
        else if (getChar == '*'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.MULTIPLICATION)
        }

        // identifies division
        else if (getChar == '/'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.DIVISION)
        }

        // identifies module
        else if (getChar == '%'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.MODULUS)
        }

        // identifies less or less/equal
        else if (getChar == '<'){
          var str = getChar + ""
          nextChar
          if (getChar == '='){
            str += getChar
            nextChar
            return new Lexeme(str, Token.LESS_EQUAL)
          }
          else {
            return new Lexeme(str, Token.LESS)
          }
        }

        // identifies greater or greater/equal
        else if (getChar == '>'){
          var str = getChar + ""
          nextChar
          if (getChar == '='){
            str += getChar
            nextChar
            return new Lexeme(str, Token.GREATER_EQUAL)
          }
          else {
            return new Lexeme(str, Token.GREATER)
          }
        }

        // identifies break
        else if (getChar == '^'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.BREAK)
        }

        // identifies dot
        else if (getChar == '.'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.DOT)
        }

        // identifies open parentheses
        else if (getChar == '('){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.OPEN_PAR)
        }

        // identifies close parentheses
        else if (getChar == ')'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.CLOSE_PAR)
        }

        // identifies open bracket
        else if (getChar == '['){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.OPEN_BRACKET)
        }

        // identifies close bracket
        else if (getChar == ']'){
          val str = getChar + ""
          nextChar
          return new Lexeme(str, Token.CLOSE_BRACKET)
        }

        // throw an exception if an unrecognizable symbol is found
        throw new Exception("Lexical Analyzer Error: unrecognizable symbol \"" + getChar + "\" found!")
      }
    }
  }
}

object LexicalAnalyzer {
  val BLANKS       = " \n\t"
  val NEW_LINE     = '\n'
  val LETTERS      = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val DIGITS       = "0123456789"
  val PUNCTUATIONS = ".,;:?!"
  val SPECIALS     = "<_@#$%^&()-+='/\\[]{}|"

  def main(args: Array[String]): Unit = {

    // checks the command-line for source file
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    // iterates over the lexical analyzer, printing the lexemes found
    val lex = new LexicalAnalyzer(args(0))
    val it = lex.iterator
    while (it.hasNext)
      println(it.next())

  } // end main method
}
