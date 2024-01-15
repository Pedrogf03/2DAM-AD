package company.entity.collectionsmappings.c_sortedset_one_to_many;
 
import java.util.SortedSet;
/**
 * <h3>SortedSet and one-to-many</h3>
 *<p>POJO class Employee which will be used to persist the objects related to
 *   EMPLOYEE table and having a collection of certificates in SortedSet variable.
 *<p>Class Employee has a collection of certificates in <b>SortedSet</b> variable. 
 *<p>There will be <b>one-to-many</b> relationship between EMPLOYEE and CERTIFICATE objects
 *<h3>java.util.Set and java.util.TreeSet</h3>
 *<ul>
 *<li><p> You can use SortedSet collection in your class when there is no duplicate
 *        element required in the collection and must be ordered.
 *<li><p> A SortedSet is a java collection that does not contain any duplicate 
 *        element and elements are ordered using their natural ordering or by
 *        a comparator provided. If we use natural ordering then its iterator 
 *        will traverse the set in ascending element order.
 *        So objects to be added to a set will be ordered by 'natural' ordering
 *        unless implement the comparateTo() method so that Java can determine
 *        when an element comes before/after another element.
 *         
 *<li><p>A SortedSet is mapped with a &lt;set&gt; element in the mapping table and
 *        initialized with <code>java.util.TreeSet</code>.
 *        The sort attribute can be set to either a comparator or natural ordering.
 *        If we use natural ordering then its iterator will traverse the set
 *        in ascending element order
 *</ul>
 */
public class Employee { 
    private int id; 
    private String firstName; 
    private String lastName; 
    private int salary; 
    private SortedSet certificates;//<---one-to-many: one Employee many Certificates
    
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
    
    public SortedSet getCertificates() {
        return certificates;
    }
    
    public void setCertificates( SortedSet certificates ) {
        this.certificates = certificates;
    }
    
}
