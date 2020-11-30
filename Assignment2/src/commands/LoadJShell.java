package commands;

import data.Cache;
import data.FileSystem;
import driver.JShell;
import io.StandardOutput;
import runtime.ErrorHandler;
import java.io.*;

public class LoadJShell extends Command {

  /**
   * Constructor for loadJShell class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, missingOperand,
   * and description from its super class Commands.
   */
  public LoadJShell() {
    this.setIdentifier("loadJShell");
    this.setDescription(
        "The file FileName is some file that is stored on the actual filesystem"
            + " of your computer. The purpose of this\r\n"
            + "command is to load the saved session of the JShell before the user"
            + " closed it down. ");
    this.setMaxNumOfArguments(2);
    this.setMinNumOfArguments(2);
    this.setErrorTooManyArguments("Too many arguments, please enter fileName");
    this.setMissingOperand("What file name, do wish to call the save?");
  }

  @Override
  public Command run(String[] tokens, JShell shell) {
    if (shell.getCache().getHistorySize() <= 1) {
      try {
        FileInputStream file = new FileInputStream(tokens[1] + ".ser");
        ObjectInputStream inStream = new ObjectInputStream(file);
        JShell newShell = (JShell) inStream.readObject();
        shell.setfSystem(newShell.getfSystem());
        shell.setCache(newShell.getCache());

        inStream.close();
        file.close();

      } catch (IOException | ClassNotFoundException e) {
        this.setErrors(ErrorHandler.invalidPath(this, tokens[1]));
      }
    } else {
      this.setErrors(ErrorHandler.badInput(this, "Non-empty JShell"));
    }

    return this;
  }
}
