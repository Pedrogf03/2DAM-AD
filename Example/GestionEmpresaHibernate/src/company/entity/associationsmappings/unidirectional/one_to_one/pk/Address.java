
package company.entity.associationsmappings.unidirectional.one_to_one.pk;


public class Address{
    private int id;
    Employee employee;//<------one-to-one: References the (only) Employe that has this Addres
    private String street;
    private String city;
    private String state;
    private String zipcode;
    
    public Address() {}
    
    public Address(Employee employee, String street, String city, String state, String zipcode) {
        this.employee=employee;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }
    
    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }
    
    public String getStreet() { 
        return street;
    }
    
    public void setStreet( String street ) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity( String city ) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    } 
    
    public void setState( String state ) {
        this.state = state;
    }
    
    public String getZipcode() { 
        return zipcode;
    }
    
    public void setZipcode( String zipcode ) { 
        this.zipcode = zipcode;
    }
    
    public Employee getEmployee() { 
        return employee;
    }
    
    public void setEmployee( Employee employee ) { 
        this.employee = employee;
    }
}
