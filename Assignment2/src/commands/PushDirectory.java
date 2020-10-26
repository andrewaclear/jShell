package commands;

public class PushDirectory extends Command {

  public PushDirectory() {
    String desc = "Saves the current working directory by pushing onto"
        + " directory stack and then changes the new current working"
        + " directory to DIR. The push must be consistent as per the"
        + " LIFO behavior of a stack. The pushd command saves the old"
        + " current working directory in directory stack so that it can"
        + " be returned to at any time (via the popd command).  The size"
        + " of the directory stack is dynamic and dependent on the pushd"
        + " and the popd commands.  ";
    
    String iden = "pushd";
    this.setDescription(desc);
    this.setIdentifier(iden);
  }
}
