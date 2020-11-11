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

/**
 * Defines how the system prints out to the shell.
 */
public class StandardOutput {


  /**
   * Prints message out to the shell on it's own line.
   * 
   * @param message, to be printed out to the shell
   */
  public static void println(String message) {
    System.out.println(message);
  }

  /**
   * Prints message out to the shell without adding a line return.
   * 
   * @param message, to be printed out to the shell
   */
  public static void print(String message) {
    System.out.print(message);
  }
}
