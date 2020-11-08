package commands;

import data.Cache;
import data.File;
import data.FileSystem;
import data.FileSystemNode;
import runtime.ErrorHandler;

/**
 * Takes in an array of tokens from execution and executes the Echo command.
 * echo "STRING > OUTFILE does the following: 
 * Puts STRING into file OUTFILE. STRING is a string of characters surrounded 
 * by double  quotation marks. This creates a new file if OUTFILE does not
 * exists and erases the old contents if OUTFILE already exists. In either
 * case, the only thing in OUTFILE should be STRING. 
 * echo "STRING" >> OUTFILE:
 * Like the previous command, but appends instead of overwrites. 
 */
public class EchoToFile extends Command{
  /**
   * Constructor for EchoToFile class. It initializes Description, Identifier,
   * MaxNumOfArguments, ErrorTooManyArguments, MissingOperand from its
   * super class Commands.
   */
  public EchoToFile() {
    this.setDescription("If OUTFILE is not provided, print STRING"
      + " on the shell. Otherwise, put \nSTRING into file OUTFILE."
      + " STRING is a string of"
      + " characters surrounded \nby double  quotation marks. This"
      + " creates a new file if OUTFILE does \nnot exists and erases"
      + " the old contents if OUTFILE already exists. \nIn either case,"
      + " the only thing in OUTFILE should be STRING.");
    
    this.setIdentifier("echo");
    this.setMaxNumOfArguments(4);
    this.setMinNumOfArguments(2);
    this.setErrorTooManyArguments("too many arguments");
    this.setMissingOperand("please specify a string to print");
  }
  
  /**
   * Tries to get a FileSystemNode from path, return a FileSystemNode if 
   * successful, else return null.
   * 
   * @param path, a path to a file in the file system
   * @param fSystem, an instance of FileSystem class to read and write
   * to the file structure.
   * @return returns a FileSystemNode at the specified path
   */
  private FileSystemNode tryGetFileSystemNode(String path, FileSystem fSystem) {
    try {
      FileSystemNode node = fSystem.getFileSystemNode(path);
      return node;
    } catch (Exception e) {
      return null;
    }
  }
  
  
  /**
   * Updates a file's contents specified from tokens or creates a new file
   * if file doesn't exist.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param node, an instance of FileSystemNode class that holds the position
   * of the file in the path directory tree
   * @param name, name of the file
   * @return returns void
   */
  private void toFile(String[] tokens, FileSystemNode node, String name) {
    File curFile = node.getDirectory().getFile(name);
    String desc = tokens[1].replaceAll("\"", "");
    //Check if file exists
    if (curFile != null) {
      if (tokens[2].equals(">>")) { //With >> then appends to file
        curFile.setContent(curFile.getContent() + desc);
      } else { //With > then override the file
        curFile.setContent(desc);
      }
    //If file doesn't exist then create new file with specified contents
    } else {
      File newFile = new File(name);
      newFile.setContent(desc);
      node.getDirectory().addFile(newFile);
    }
  }
 
  /**
   * Writes STRING to a new file, overrides contents
   * of a file if file already exists and/or appends STRING to a file.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param fSystem, an instance of FileSystem class to read and write
   * to the file structure.
   * @param cache, store the current directory stack
   * @return returns a boolean true to mark successful execution
   * @Override overrides run method for super class Command
   */
  public boolean run(String[] tokens, FileSystem fSystem, Cache cache) {
    String path = tokens[3];
    String name;
    FileSystemNode node;
    
    try {
      //Only file name and no path (Located in currentDirectory)
      if(!path.contains("/")) {
        node = fSystem.getCurrentDirectory();
        name = path;
      } else { // Must follow path to find location of file
        //remove fileName from path
        name = path.substring(path.lastIndexOf('/')+1);
        path = path.substring(0, path.lastIndexOf('/') + 1);
        //True then path is relative, else path is absolute 
        if (!(path.charAt(0) == '/')) {
          path = fSystem.getCurrentDirectory().getPath() + path;
        }  
           
        node = tryGetFileSystemNode(path, fSystem);
      }
      
      if (name.matches("(.+)?[ /.!@#$%^&*(){}~|<>?](.+)?")) {
        ErrorHandler.invalidName(this, tokens);
        return true;
      }
      
      toFile(tokens, node, name);
      } catch (Exception e) {
        ErrorHandler.invalidPath(this);
      }
    return true;
  }
}