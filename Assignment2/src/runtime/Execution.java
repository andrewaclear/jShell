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
package runtime;

import data.*;
import commands.*;
import java.util.HashMap;

/**
 * Takes in an array of tokens from Parser and executes the
 * the specified command from tokens. 
 */
public class Execution {
  /**
   * HashMap that stores entries <String commandId, String commandClass>
   */
  private HashMap<String, String> 
    commandHashMap = new HashMap<String, String>();
  
  /**
   * Constructor for Execution class. It initializes the commandHashMap
   * HashMap.
   */
  public Execution () {
    initHashMap(commandHashMap);
  }
  
  /**
   * Takes in an array of tokens and attempts to execute a command. 
   * Returns true when exit command is executed. Verifies that  the number of 
   * command arguments are within designated max and min lengths. 
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param fSystem, an instance of FileSystem class to read and write
   * to the file structure.
   * @param cache, store the current directory stack
   * @return returns a boolean true when exit is entered into terminal
   */
  public boolean executeCommand(String[] tokens, FileSystem fSystem, 
                                Cache cache) {
    boolean run = true;
    
    try {
      if (commandHashMap.containsKey(tokens[0])) {
        String commandName = commandHashMap.get(tokens[0]);
        Command command = (Command) Class.forName(commandName).
                          getDeclaredConstructor().newInstance();
      
        if ((command.getMaxNumOfArguments() < 0 ||
             tokens.length <= command.getMaxNumOfArguments()) &&
            tokens.length >= command.getMinNumOfArguments()) {
          run = command.run(tokens, fSystem, cache);
        } else if (tokens.length > command.getMaxNumOfArguments()) {
          ErrorHandler.tooManyArguments(command);
        } else if (tokens.length < command.getMinNumOfArguments()) {
          ErrorHandler.missingOperand(command);
        }
      // } else if (tokens[1].equals("Failed Parsing")) {
      //   ErrorHandler.commandNotFound(tokens);
      } else {
        ErrorHandler.commandNotFound(tokens);
      }
    } catch (Exception e) {
     // StandardOutput.println(e.getMessage());
    }
    
    return run;
  }
  
  /**
   * Initializes the commandHashMap with command ids and command classes
   * 
   * @param commandHashMap, a hashmap mapping commandId to command class
   * @return returns void
   */
  private static void initHashMap(HashMap<String, String> commandHashMap)  {
    commandHashMap.put("man", "commands.Manual");
    commandHashMap.put("cd", "commands.ChangeDirectory");
    commandHashMap.put("cat", "commands.Concatenate");
    commandHashMap.put("echo", "commands.Echo");
    commandHashMap.put("exit", "commands.Exit");
    commandHashMap.put("history", "commands.History");
    commandHashMap.put("ls", "commands.ListContents");
    commandHashMap.put("mkdir", "commands.MakeDirectory");
    commandHashMap.put("popd", "commands.PopDirectory");
    commandHashMap.put("pwd", "commands.PrintWorkingDirectory");
    commandHashMap.put("pushd", "commands.PushDirectory");
  }
}


