public class Author {
  private String lastName;
  private String name;

  public Author(String name, String lastName) {
    this.name = name;
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getName() {
    return name;
  }

  public String getInitial() {
    return name.substring(0, 1).toUpperCase();
  }
}
