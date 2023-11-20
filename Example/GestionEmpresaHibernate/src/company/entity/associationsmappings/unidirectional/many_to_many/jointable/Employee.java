package company.entity.associationsmappings.unidirectional.many_to_many.jointable;
 
import java.util.Set;
/**
 * <h3>Set and many-to-many</h3>
 *<p>POJO class Employee which will be used to persist the objects related
 *   to EMPLOYEE table.
 *<p>Class Employee has a collection of certificates in <b>Set</b> variable. 
 *<p>There will be <b>many-to-many</b> relationship between 
 *   EMPLOYEE and CERTIFICATE objects, using EMP_CERT table
 *<h3>java.util.Set and java.util.HashSet</h3>
 *<ul>
 *<li><p> You can use Set collection in your class when there is no duplicate
 *        element required in the collection.
 *<li><p> A Set is a java collection that does not contain any duplicate element.
 *        More formally, sets contain no pair of elements e1 and e2 such that
 *        e1.equals(e2), and at most one null element.
 *        So objects to be added to a set must implement both the
 *        equals() and hashCode() methods so that Java can determine 
 *        whether any two elements/objects are identical.
 *<li><p>A Set is mapped with a&lt;set&gt; element in the mapping table and
 *        initialized with <code>java.util.HashSet</code>.
 *</ul>
 */
public class Employee { 
    private int id; 
    private String firstName; 
    private String lastName; 
    private int salary; 
    private Set certificates;//<---many-to-many(unidirectional): one Employee many Certificates
    
    public Employee() {}
    
    public Employee(String fname, String lname, int salary) {
        this.firstName = fname; 
        this.lastName = lname; 
        this.salary = salary;
        
    } public int getId() {
        return id; 
    } 
    
    public void setId( int id ){ 
            this.id = id;
    } 
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName( String first_name ) {
        this.firstName = first_name;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName( String last_name ) {
        this.lastName = last_name;
    }
    public int getSalary() {
        return salary;
    }
    
    public void setSalary( int salary ) {
        this.salary = salary;
    } 
    
    public Set getCertificates() {
        return certificates;
    }
    
    public void setCertificates( Set certificates ) {
        this.certificates = certificates;
    }
    
}
