package company.entity.collectionsmappings.e_bag_one_to_many;
 
import company.entity.collectionsmappings.e_bag_one_to_many.Certificate;
import java.util.Collection;
/**
 *<h3>Bag (Collection) and one-to-many</h3>
 *<p>POJO class Employee which will be used to persist the objects related to
 *   EMPLOYEE table and having a collection of certificates in Collection variable.
 *<p>Class Employee has a collection of certificates in <b>Collection</b> variable. 
 *<p>There will be <b>one-to-many</b> relationship between EMPLOYEE and CERTIFICATE objects
 *<h3>java.util.Collection and java.util.ArrayList</h3>
 *<ul>
 *<li><p> You can use Collection collection in your class when duplicate element 
 *        is allow in the collection and the exact sequence of objects is unimportant.
 *<li><p> A Bag is a java collection that stores elements without caring about 
 *        the sequencing but allow duplicate elements in the collection.
 *        A bag is a random grouping of the objects.
 *<li><p>A Collection is mapped with a &lt;bag&gt; element in the mapping table and
 *       initialized with java.util.ArrayList.
 *</ul>
 */
public class Employee { 
    private int id; 
    private String firstName; 
    private String lastName; 
    private int salary; 
    private Collection certificates;//<---one-to-many: one Employee many Certificates
    
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
    
    public Collection getCertificates() {
        return certificates;
    }
    
    public void setCertificates( Collection certificates ) {
        this.certificates = certificates;
    }
    
}
