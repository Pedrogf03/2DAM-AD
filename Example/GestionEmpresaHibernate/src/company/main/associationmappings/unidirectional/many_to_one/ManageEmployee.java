package company.main.associationmappings.unidirectional.many_to_one;


import company.entity.associationsmappings.unidirectional.many_to_one.Address;
import company.entity.associationsmappings.unidirectional.many_to_one.Employee;
import company.util.HibernateUtil;
import java.util.List;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 <h2 align="center">Unidirectional. many-to-one </h2>
 *<p>A unidirectional many-to-one association is the most common kind of 
 *   unidirectional association.</p>
    <pre>
     
          Employee                                 Address
         ----------                               ---------
        |id        |                /----------->|id       |  
        |firstName |           n:1               |street   |
        |lastName  |      ( many-to-one          |city     |
        |salary    |       unidirectional)       |state    |
        |address --|------------/                |zipcode  |
         ----------                               ---------                                  

        |id                                      |id
        | |address                               |
        | |                                      |
        |.|......................................|...................
        | |                                      |
        | |address(fk,nn)                        |
        |  fk on                                 |
        |  Address's pk table                    |
        |id (pk,auto)                            |id (pk,auto)                  

        EMPLOYEE_MANY_TO_ONE                           ADDRESS
         ------------------                   1   ----------------- 
        │PK id (pk,auto)  │             /----->│PK id(pk,auto)   │
        │   first_name    │            /       │   street_name   │
        │   last_name     │           /        │   city_name     │
        │   salary        │    n     /         │   state_name    │
        │FK address(fk,nn)│--->>---/           │   zipcode       │
         ------------------                       -----------------
           
    <hibernate-mapping>
    <class name="company.entity.associationsmappings.unidirectional.many_to_one.Employee" 
           table="EMPLOYEE_MANY_TO_ONE"> 
        <meta attribute="class-description"> This class contains the employee detail. </meta> 
        <id name="id" type="int" column="id"> 
            <generator class="native"/> 
        </id>        
           
       <!--The <many-to-one> element indicates that many Employee objects 
           can be related to one (the same) Address object and, as such, 
           the Employee object must have an Address object associated 
           with it.
           -The 'name' attribute is set to the defined variable in the parent class,
            in our case it is address, that must be mapped.
           -The 'column' attribute is used to set the column name in the parent table
            EMPLOYEE_MANY_TO_ONE that is the target of the mapping.
           -The 'class' attribute is used to describe the class of the mapped value
           -The 'not-null' attribute sets if null values are allowed
        -->
        <many-to-one name="address" column="address" 
                     class="company.entity.associationsmappings.unidirectional.many_to_one.Address"
                     not-null="true"/>
        
        <property name="firstName" column="first_name" type="string"/> 
        <property name="lastName" column="last_name" type="string"/> 
        <property name="salary" column="salary" type="int"/> 
    </class> 
          
    <class name="company.entity.associationsmappings.h_many_to_one.unidirectional.Address"
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



