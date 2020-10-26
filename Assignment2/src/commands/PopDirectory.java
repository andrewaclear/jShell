package commands;

public class PopDirectory extends Command {
  
  public PopDirectory() {
    String desc = "Remove the top entry from the directory stack, and cd"
        + " into it. The removal must be consistent as per the LIFO"
        + " behavior of  a stack.  The popd command removes the top"
        + " most directory from the directory stack and makes it the"
        + " current working directory.  If there is no directory onto"
        + " the stack, then give appropriate error message. ";
    
    String iden = "popd";
    this.setDescription(desc);
    this.setIdentifier(iden);
  }
}
