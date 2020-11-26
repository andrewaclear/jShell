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
package commands;

import java.util.Arrays;
import data.Cache;
import data.Directory;
import data.FileSystem;
import data.FileSystemNode;
import driver.JShell;
import runtime.ErrorHandler;

/**
 * This command takes in two arguments only. Create directories, each of which
 * may be relative to the current directory or may be a full path. If creating
 * DIR1 results in any kind of error, do not proceed with creating DIR 2.
 * However, if DIR1 was created successfully, and DIR2 creation results in an
 * error, then give back an error specific to DIR2.
 */
public class MakeDirectory extends Command {

  /**
   * Constructor for MakeDirectory class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, missingOperand,
   * and description from its super class Commands.
   */
  public MakeDirectory() {
    this.setIdentifier("mkdir");

    // MakeDirectory must have three tokens
    this.setMaxNumOfArguments(-1);
    this.setMinNumOfArguments(2);

    // Error Handling
    this.setErrorTooManyArguments("Only two parameters are accepted");
    this.setMissingOperand("Only two parameters are accepted");

    // Description
    this.setDescription("This command takes in two arguments only."
        + " Create directories, \neach of which may be relative"
        + " to the current directory or may \nbe a full path."
        + " If creating DIR1 results in any kind of error,"
        + " \ndo not proceed with creating DIR 2. However, if DIR1"
        + " was created \nsuccessfully, and DIR2 creation results in an error,"
        + " then give \nback an error specific to DIR2. ");
  }

  /**
   * The run method of MakeDirectory makes two directories in the given path
   * tokens[1] and tokens[2] if both paths are valid/appropriate in fileSystem,
   * or makes a directory in the path tokens[1] if tokens[1] is a
   * valid/appropriate path in fileSystem but tokens[2] is not, or makes no
   * directories at all if tokens[1] is not a valid/appropriate path in fSystem.
   * In any case, returns true after being done.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param JShell contains the FileSystem and cache
   * @return returns a boolean true to mark successful execution
   */
  @Override
  public boolean run(String[] tokens, JShell shell) {
    FileSystem fSystem = shell.getfSystem();
    
    FileSystemNode targetNode = null;
    
    String[] pathTokens = Arrays.copyOfRange(tokens, 1, tokens.length) ;
    
    for (String token : pathTokens) {
        
        targetNode = fSystem.getSemiFileSystemNode(token);
        
        if (targetNode != null) {
           if (!targetNode.isChildInside(fSystem.getPathLastEntry(token))) {
             targetNode.addChild(new FileSystemNode(
                  new Directory(fSystem.getPathLastEntry(token))));
          } else {
            ErrorHandler.childAlreadyExistant(fSystem.getPathLastEntry(token), 
                targetNode);
            break;
          }
        } else {
          ErrorHandler.invalidPath(this, token);
          break;
        }
    }

    return true;

  }
}
