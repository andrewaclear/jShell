package data;

import java.util.*; 

public class FileSystemNode {
  
  // TODO: set the name of the directory when constructing a directory
  private Directory directory;
  private List<FileSystemNode> children = new ArrayList<FileSystemNode>();
  private FileSystemNode parent = null;

  public FileSystemNode(Directory directory) {
      this.directory = directory;
  }

  public void addChild(FileSystemNode child) {
      child.setParent(this);
      this.children.add(child);
  }

  public List<FileSystemNode> getChildren() {
      return this.children;
  }

  private void setParent(FileSystemNode parent) {
      this.parent = parent;
  }

  public FileSystemNode getParent() {
      return this.parent;
  }
  
  public void addFile(File file) {
    this.directory.addFile(file);
  }
  
  public Directory getDirectory() {
    return this.directory;
  }
  
  public void setDirectory(Directory directory) {
    this.directory = directory;
  }
  
  public String getPath() {
    String path = "";
    FileSystemNode current_node = this;
    
    while (current_node != null) {
      if (current_node.getParent() != null) {
        path = current_node.directory.getDirectoryName() + "/" + path;
      }
      else {
        path = current_node.directory.getDirectoryName() + path;
      }
        
      current_node = current_node.getParent();
    }
    
    return path;
  }
}
