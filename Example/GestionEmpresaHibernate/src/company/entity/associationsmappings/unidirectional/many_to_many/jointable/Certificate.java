
package company.entity.associationsmappings.unidirectional.many_to_many.jointable;

/**
 * <p>POJO class corresponding to CERTIFICATE_WITH_JOIN_TABLE table so that certificate
 * objects can be stored and retrieved into the CERTIFICATE_WITH_JOIN_TABLE table.
 * <p>This class should also implement both the equals() and hashCode() methods
 * so that Java can determine whether any two elements/objects are identical.
 */
public class Certificate {
  private int id;
  private String name;

  public Certificate() {
  }

  public Certificate(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * 
   * @param obj an Object
   * @return false if obj is null or obj is not an instance of Certificate,
   *                  or have diferent 'id' or have diferent 'name'
   *         true otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!this.getClass().equals(obj.getClass()))
      return false;
    Certificate obj2 = (Certificate) obj;
    if ((this.id == obj2.getId()) && (this.name.equals(obj2.getName()))) {
      return true;
    }
    return false;
  }

  /**
   * 
   * @return a hashCode obtanined from the concated string of all properties
   */
  @Override
  public int hashCode() {
    int tmp;
    tmp = (id + name).hashCode();
    return tmp;
  }
}
