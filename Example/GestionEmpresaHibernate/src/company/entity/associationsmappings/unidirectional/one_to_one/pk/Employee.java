package company.entity.associationsmappings.unidirectional.one_to_one.pk;


public class Employee { 
    private int id; 
    private String firstName; 
    private String lastName; 
    private int salary; 
    //The property "address" doesn't exist (in "Address" objects there exists
    //a reference to the (unique) "Employee" object that has this "address" 
    
    public Employee() {}
    
    public Employee(String fname, String lname, int salary){ //, Address address) {
        this.firstName = fname; 
        this.lastName = lname; 
        this.salary = salary;
       // this.address = address;
        
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
      
}
