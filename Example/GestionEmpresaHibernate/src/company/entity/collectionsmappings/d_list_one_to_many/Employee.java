package company.entity.collectionsmappings.d_list_one_to_many;
 
import company.entity.collectionsmappings.d_list_one_to_many.Certificate;
import java.util.List;
/**
 *<h3>List and one-to-many</h3>
 *<p>POJO class Employee which will be used to persist the objects related to
 *   EMPLOYEE table and having a collection of certificates in List variable.
 *<p>Class Employee has a collection of certificates in <b>List</b> variable. 
 *<p>There will be <b>one-to-many</b> relationship between EMPLOYEE and CERTIFICATE objects
 *<h3>java.util.List and java.util.ArrayList</h3>
 *<ul>
 *<li><p> You can use List collection in your class when duplicate element 
 *        is allow in the collection and index of its position in the list is needed.
 *<li><p> A List is a java collection that stores elements in sequence and allow 
 *        duplicate elements. The user of this interface has precise control over
 *        where in the list each element is inserted. The user can access elements
 *        by their integer index, and search for elements in the list.
 *        More formally, lists typically allow pairs of elements e1 and e2 such 
 *        that e1.equals(e2), and they typically allow multiple null elements
 *        if they allow null elements at all.
 *<li><p>A List is mapped with a &lt;list&gt; element in the mapping table and
 *       initialized with java.util.ArrayList. The index of list is always of 
 *       type integer and is mapped into the table (see tag 'index' in the 
 *       employee mapping file.
 *</ul>
 */
public class Employee { 
    private int id; 
    private String firstName; 
    private String lastName; 
    private int salary; 
    private List certificates;//<---one-to-many: one Employee many Certificates
    
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
    
    public List getCertificates() {
        return certificates;
    }
    
    public void setCertificates( List certificates ) {
        this.certificates = certificates;
    }
    
}
