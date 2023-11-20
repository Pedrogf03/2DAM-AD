package company.main.associationmappings.unidirectional.many_to_one.jointable;


import company.entity.associationsmappings.unidirectional.many_to_one.jointable.Address;
import company.entity.associationsmappings.unidirectional.many_to_one.jointable.Employee;
import company.util.HibernateUtil;
import java.util.List;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 <h2 align="center">Unidirectional. many-to-one with join table </h2>
   <p>A unidirectional many-to-one association on a join table is common when
      the association is optional</p>
    <pre>
             Employee                                    Address
         ----------------                               ---------
        |id              |                /----------->|id       |  
        |firstName       |           n:1               |street   |
        |lastName        |      ( many-to-one          |city     |
        |salary          |       unidirectional)       |state    |      
        |address         |-----------/                 |zipcode  |        
         ----------------                               ---------                      
                                                                                                   

        |id                                                id
        |\                                                 /|
        | \                                               / |
        ...\............................................./..|...................
        |   \------------------|employee_id(pk,fk)      /   |
        |id (pk,auto)           fk on employee's       /    |id (pk,auto)
                                pk table              /
                                                     /
                                      address(fk,nn)|             
                                    fk on address's
                                    pk table
                                      

        EMPLOYEE_MANY_TO_ONE     EMP_CERT_MANY_TO_ONE              ADDRESS
         ------------------     -------------------       ------------------- 
        │PK id (pk,auto)  │<--|FK,PK employee_id   |  /->│PK id(pk,auto)   │
        │   first_name    │   |FK,NN address_id    │/    |   street_name   │
        │   last_name     │    -------------------       │   city_name     │ 
        │   salary        │                              │   state_name    │                      
         ------------------                               │   zipcode       │
                                                           ------------------ 
                         
     <hibernate-mapping>
    <class name="company.entity.associationsmappings.unidirectional.many_to_one.jointable.Employee" 
           table="EMPLOYEE_MANY_TO_ONE"> 
        <meta attribute="class-description"> This class contains the employee detail. </meta> 
        <id name="id" type="int" column="id"> 
            <generator class="native"/> 
        </id>   
        <!--The <join> elements allows map some object properties to different
            tables. In this example is used to 'export' primary keys from
            employee and address to a intermediate table EMP_ADDR_MANY_TO_ONE
            -name: The table where the properties between '<join></join>' tags
                   will be mapped
            -optional (optional - defaults to false): if enabled, Hibernate will 
                      insert a row only if the properties defined by this join
                      are non-null.
                      It will always use an outer join to retrieve the properties.
                      In this examples, 'optional=true' means that only in the
                      case of having employee_id different to null and address different
                      to null, a row can be inserted in EMP_ADDR_MANY_TO_ONE
                      Since a outer joint (between EMPLOYEE and  EMP_CERT_MANY_TO_ONE
                      is always use to retrive these properties, it follows that
              ********HAVING AN ADDRESS IS OPTIONAL TO EMPLOYEEs.-->     
        <join table="EMP_ADDR_MANY_TO_ONE"  optional="true">
            
            <!--The key element defines the foreign-key column name in the 
                EMP_ADDR_MANY_TO_ONE
                -The column attribute in the element defines the column name
                 where the id from Employee will be stored.
                -The unique column sets that the employee_id can't be repeated
                 in this table, where it follows that 
         ********AN EMPLOYEE HAS AT MOST ONE ADDRESS-->
         
            <key column="employee_id" unique="true"/>
            <!--The <many-to-one> element indicates that many Employee objects
                can be related to one (the same) Address object
                -The 'name' attribute is set to the defined variable in the
                 parent class, in our case it is address, that must be mapped.
                -The 'column' attribute is used to set the column name in the 
                 table EMP_ADDR_MANY_TO_ONE that is the target of the mapping
                 and is too the name of the foreign key column
                -The 'not-null' attribute sets if null values are allowed-->
            <many-to-one name="address" column="addressId" not-null="true"/>
        </join>
        <property name="firstName" column="first_name" type="string"/> 
        <property name="lastName" column="last_name" type="string"/> 
        <property name="salary" column="salary" type="int"/> 
    </class> 
    
    <class name="company.entity.associationsmappings.unidirectional.many_to_one.jointable.Address"
        table="ADDRESS">
      <meta attribute="class-description"> This class contains the address detail. </meta>
    
      <id name="id" column="id" type="int">
        <generator class="native"/>
      </id>
      <property column="street_name" name="street" type="string"/>
      <property column="city_name" name="city" type="string"/>
      <property column="state_name" name="state" type="string"/>
      <property column="zipcode" name="zipcode" type="string"/>
    </class> 
</hibernate-mapping>   
  
   </pre>
 */
public class ManageEmployee {

    public static void main(String[] args) {
   
        ManageEmployee ME = new ManageEmployee(); /* Add few employee records in database */
       
        /* Let us have one address object */
        Address address = ME.addAddress("Kondapur","Hyderabad","AP","532");
       
        /* Add employee records in the database */ 
        Integer empID1 = ME.addEmployee("Manoj", "Kumar", 4000, address);
       
        /* Add another employee record in the database */
        Integer empID2 = ME.addEmployee("Dilip", "Kumar", 3000, address);
        
        /* List down all the employees */
        ME.listEmployees();
        
       /* Update employee's salary records */ 
        ME.updateEmployee(empID1, 5000);
        
        /* Delete an employee from the database */
        ME.deleteEmployee(empID2);
        
        /* List down all the employees */
        ME.listEmployees();
    }
      
    
    /* Method to add an address record in the database */
    public Address addAddress(String street, String city, String state, String zipcode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer addressID = null;
        Address address = null;
        
        try{
            tx = session.beginTransaction();
            address = new Address(street, city, state, zipcode);
            addressID = (Integer) session.save(address);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        } return address;
    }
     
    /* Method to add an employee record in the database */            //Address
    public Integer addEmployee(String fname, String lname, int salary, Address address){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try{
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary,address);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        } 

        return employeeID; 
    }

    /* Method to READ all the employees */ 
    public void listEmployees( ){ 
        Session session = HibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;
       
        try{
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            
            //-------Iterate over the List obtanited from the Query
            for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                Employee employee = (Employee) iterator.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print(" Last Name: " + employee.getLastName());
                System.out.println(" Salary: " + employee.getSalary());
                
                Address add = employee.getAddress();
                System.out.println("Address ");
                System.out.println("\tStreet: " + add.getStreet());
                System.out.println("\tCity: " + add.getCity());
                System.out.println("\tState: " + add.getState());
                System.out.println("\tZipcode: " + add.getZipcode());
            } 
            tx.commit(); 
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        } 
    }

    /* Method to UPDATE salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary ){ 

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null; 
        try{ 
            tx = session.beginTransaction(); 
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            employee.setSalary( salary );
            session.update(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
        }finally { 
            session.close();
        } 
    }

    /* Method to DELETE an employee from the records */ 
    public void deleteEmployee(Integer EmployeeID){

        Session session = HibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;
        try{
            tx = session.beginTransaction(); 
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close(); 
        }
    }
}



