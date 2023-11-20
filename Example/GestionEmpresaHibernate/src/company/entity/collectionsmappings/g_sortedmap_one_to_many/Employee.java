package company.entity.collectionsmappings.g_sortedmap_one_to_many;
 
import java.util.SortedMap;
/**
 *<h3>SortedMap and one-to-many</h3>
 *<p>POJO class Employee which will be used to persist the objects related to
 *   EMPLOYEE table and having a collection of certificates in List variable.
 *<p>Class Employee has a collection of certificates in <b>SortedMap</b> variable. 
 *<p>There will be <b>one-to-many</b> relationship between EMPLOYEE and CERTIFICATE objects
 *<h3>java.util.SortedMap and java.util.TreeMap</h3>
 *<ul>
 *<li><p> You can use SortedMap collection in your class when a collection of key-value pairs
 *        are needed (a sort of 'dictionary'), not being allowed duplicate in
 *        the list of keys and the set of keys is totaly ordered.
 *<li><p> A SortedMap is a similar java collection as Map that stores elements in 
 *        key-value pairs and provides a total ordering on its keys. 
 *        Duplicate elements are not allowed in the map. The map is ordered according
 *        to the natural ordering of its keys, or by a Comparator typically
 *        provided at sorted map creation time.
 *<li><p>A SortedMap is mapped with a &lt;map&gt; element in the mapping table and an 
 *       ordered map can be initialized with java.util.TreeMap.
 *</ul>
 */
public class Employee { 
    private int id; 
    private String firstName; 
    private String lastName; 
    private int salary; 
    private SortedMap certificates;//<---one-to-many: one Employee many Certificates
    
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
    
    public SortedMap getCertificates() {
        return certificates;
    }
    
    public void setCertificates( SortedMap certificates ) {
        this.certificates = certificates;
    }
    
}
