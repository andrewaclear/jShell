// **********************************************************
// Assignment2:
// Student1: Christian Chen Liu
// UTORID user_name: Chenl147
// UT Student #: 1006171009
// Author: Christian Chen Liu
//
// Student2: Christopher Suh
// UTORID user_name: suhchris
// UT Student #: 1006003664
// Author: Christopher Suh
//
// Student3: Andrew D'Amario
// UTORID user_name: damario4
// UT Student #: 1006618947
// Author: Andrew D'Amario
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package io;

import java.util.ArrayList;
import runtime.ErrorHandler;

/**
 * Takes in a line of input from JShell and parses ( by spaces and
 * quotation marks " ") it in into an array of tokens.
 */
public class Parser {
  /**
   * Takes in a line as a String, seperates it ( by spaces and
   * quotation marks " ") and then returns an array of tokens of type String
   * 
   * @param line, a string representing a line of entered text from 
   * the terminal
   * @return returns an array of string tokens which are command args
   */
  public String[] parse(String line) {
    String temp; // input string parsed to remove extra spaces
    String tempString = ""; //temp string used to build each argument
    int i = 0, j = 0; // counters
    //Stores tokens dynamically, later converted to an array
    ArrayList<String> tokens = new ArrayList<String>(); 
    
    temp = line.replaceAll(" +", " ");
    temp = temp.trim();
    
    mainLoop:
    while (i < temp.length()) {
      if (temp.charAt(i) == ' ') { //' ' marks the end of an argument
        tokens.add(tempString);
        tempString = "";
        i++;
      } else if (temp.charAt(i) == '\"') { //Start of a string (ignore spaces)
          j = i + 1;
          if (j < temp.length()) { // In case input is just "
            while (temp.charAt(j) != '\"') { //get whole string as single arg 
              tempString += temp.charAt(j);
              j++;
              if (j >= temp.length()) { // No closing " for the string
                ErrorHandler.illegalString();
                //tokens = failedParsing(tokens);
                break mainLoop;
              }
            }
            tokens.add("\"" + tempString + "\""); // Add string to tokens
            i = j + 2; //Update counter to new position in input string; temp
            tempString = "";
          } else {
            ErrorHandler.illegalString();
            //tokens = failedParsing(tokens);
            break mainLoop;
          }
      //Continue adding characters to build string argument
      } else {
        tempString += temp.charAt(i);
      //Last character before end of input
        if (i + 1 == temp.length()) tokens.add(tempString); 
        i++;
      }
    }
    
    String[] tokensArray = new String[tokens.size()];
 
    return tokens.toArray(tokensArray);
  }
}
