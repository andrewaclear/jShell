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

import data.Directory;
import data.File;
import data.FileSystem;
import data.FileSystemNode;
import driver.JShell;
import io.StandardOutput;
import runtime.ErrorHandler;

public class Copy extends Command {

  /**
   * Constructor for Move class. It initializes identifier, maxNumOfArguments,
   * minNumOfArguments errorTooManyArguments, missingOperand, and description
   * from its super class Commands.
   */
  public Copy() {
    this.setIdentifier("cp");

    // MakeDirectory must have three tokens
    this.setMaxNumOfArguments(3);
    this.setMinNumOfArguments(3);

    // Error Handling
    this.setErrorTooManyArguments("at most four parameters are accepted");
    this.setMissingOperand("there must be at least two parameters");

    // Description
    this.setDescription("This command copies a directory or file into the"
        + " desired directory etiher new or already existing one");
  }

  /**
   * The run method of Copy copies a FileSystemNode into another FileSystemNode
   * or copies a File into another FileSystemNode or overwrites a file into
   * another File path that already exists or copies the content of a file into
   * another File path
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param JShell contains the FileSystem and cache
   * @return returns a boolean true to mark successful execution
   */
  @Override
  public Command run(String[] tokens, JShell shell) {
    FileSystem fSystem = shell.getfSystem();
    FileSystemNode givenNode = fSystem.getSemiFileSystemNode(tokens[1]);

    if (tokens[2].startsWith(tokens[1]))
      this.setErrors(ErrorHandler.subFileSystemNodeError(
          this, tokens[1], tokens[2]));
    else if (givenNode != null) {
      if (givenNode.isChildInside(fSystem.getPathLastEntry(tokens[1]))) {
        copyFileSystemNode(tokens[1], tokens[2], shell);
      } else if (givenNode.getDirectory()
          .isFileInsideByFileName(fSystem.getPathLastEntry(tokens[1]))) {
        copyFile(tokens[1], tokens[2], shell);
      } else
        this.setErrors(ErrorHandler.invalidPath(this, tokens[1]));
    } else
      this.setErrors(ErrorHandler.invalidPath(this, tokens[1]));

    return this;
  }

  /**
   * copyFileSystemNode copies a FileSystemNode that the givenPath refers to to
   * another FileSystemNode that targetPath refers
   * 
   * @param givenPath, a path to a FileSystemNode
   * @param targetPath, a path to a FileSystemNode
   */
  private void copyFileSystemNode(String givenPath, String targetPath,
      JShell shell) {

    FileSystem fSystem = shell.getfSystem();

    FileSystemNode clonedFileSystemNode =
        new FileSystemNode(new Directory("Dummy"));

    clonedFileSystemNode = fSystem.getFileSystemNode(givenPath)
        .cloneFileSystemNode(clonedFileSystemNode);

    FileSystemNode targetNode = fSystem.getFileSystemNode(targetPath);

    if (targetNode != null) {
      clonedFileSystemNode.setParent(targetNode);
      targetNode.addChild(clonedFileSystemNode);
    } else if (fSystem.getSemiFileSystemNode(targetPath) != null && 
        fSystem.getSemiFileSystemNode(targetPath).getDirectory().
        isFileInsideByFileName(fSystem.getPathLastEntry(targetPath))) {
      this.setErrors(ErrorHandler.moveDirectoryIntoFileError(this, givenPath, 
          targetPath));
    } else if (!fSystem.inappropriatePath(targetPath)) {
      this.setErrors(ErrorHandler.invalidPath(this, targetPath));
    } else
      this.setErrors(ErrorHandler.inappropriatePath(this, targetPath));

  }

  /**
   * copyFile copies a File that the givenPath refers to to another
   * FileSystemNode or File that targetPath refers, if the File already exists,
   * overwrite it's content instead.
   * 
   * @param givenPath, a path to a FileSystemNode
   * @param targetPath, a path to a FileSystemNode
   */
  private void copyFile(String givenPath, String targetPath, JShell shell) {

    FileSystem fSystem = shell.getfSystem();
    FileSystemNode targetNode = fSystem.getSemiFileSystemNode(targetPath);
    FileSystemNode possibleNode = fSystem.getFileSystemNode(targetPath);

    if (targetNode != null) {
      if (possibleNode != null) {
        Redirection redirectionCommand = new Redirection();
        File file = fSystem.getSemiFileSystemNode(givenPath)
            .getFile(fSystem.getPathLastEntry(givenPath));
        String[] redirectionTokens =
            {"redirect", "\"" + file.getContent() + "\"", ">",
                targetPath + "/" + fSystem.getPathLastEntry(givenPath)};
        redirectionCommand.run(redirectionTokens, shell);
      } else {
        Redirection redirectionCommand = new Redirection();
        File file = fSystem.getSemiFileSystemNode(givenPath)
            .getFile(fSystem.getPathLastEntry(givenPath));
        String[] redirectionTokens =
            {"redirect", "\"" + file.getContent() + "\"", ">", targetPath};
        redirectionCommand.run(redirectionTokens, shell);
      }

    } else if (!fSystem.inappropriatePath(targetPath)) {
      this.setErrors(ErrorHandler.invalidPath(this, targetPath));
    } else
      this.setErrors(ErrorHandler.inappropriatePath(this, targetPath));
  }

}
