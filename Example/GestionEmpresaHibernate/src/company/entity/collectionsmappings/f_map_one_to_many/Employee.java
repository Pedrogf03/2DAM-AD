package company.entity.collectionsmappings.f_map_one_to_many;
 
import java.util.Map;
/**
 *<h3>Map and one-to-many</h3>
 *<p>POJO class Employee which will be used to persist the objects related to
 *   EMPLOYEE table and having a collection of certificates in List variable.
 *<p>Class Employee has a collection of certificates in <b>Map</b> variable. 
 *<p>There will be <b>one-to-many</b> relationship between EMPLOYEE and CERTIFICATE objects
 *<h3>java.util.Ma and java.util.HashMap</h3>
 *<ul>
 *<li><p> You can use Map collection in your class when a collection of key-value pairs
 *        are needed (a sort of 'dictionary'), not being allowed duplicate in
 *        the list of keys
 *<li><p> A Map is a java collection that stores elements in key-value pairs 
 *        and does not allow duplicate elements in the list. 
 *        The Map interface provides three collection views, which allow a map's
 *        contents to be viewed as a set of keys, collection of values, 
 *        or set of key-value mappings.
 *<li><p>A Map is mapped with a &lt;map&gt; element in the mapping table and an unordered
 *       map can be initialized with java.util.HashMap.
 *</ul>
 */
public class Employee { 
    private int id; 
    private String firstName; 
    private String lastName; 
    private int salary; 
    private Map certificates;//<---one-to-many: one Employee many Certificates
    
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
    
    public Map getCertificates() {
        return certificates;
    }
    
    public void setCertificates( Map certificates ) {
        this.certificates = certificates;
    }
    
}
