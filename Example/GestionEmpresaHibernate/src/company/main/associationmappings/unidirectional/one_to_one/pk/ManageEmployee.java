package company.main.associationmappings.unidirectional.one_to_one.pk;

import company.entity.associationsmappings.unidirectional.one_to_one.pk.Employee;
import company.entity.associationsmappings.unidirectional.one_to_one.pk.Address;
import java.util.List;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import company.util.HibernateUtil;

/**
/**
 * <h2 align="center">many-to-one unidirectional, foreign key and primary key</h2> 
 * <pre>
        Employee                                   Address
         ----------                               ---------
        |id        |                       /---->|id       |  
        |firstName |               1:1    /      |street   |
        |lastName  |         (  one-to-one       |city     |
        |salary    |          unidirectional)    |state    |
        |address --|-------------/               |zipcode  |
         ----------                               ---------
        
        |id                                      |id
        |   \                                    |
        |    \                                   |
        |.....\..................................|...................
        |      \                                 |
        |       \----------------------------\   | 
        |                                     \  |
        |                                      \ |
        |id (pk,auto)                           \|id (pk,fk) 
                                                  fk on Employee's pk table
        
                EMPLOYEE                                        ADDRESS
         ------------------------   1               1  ------------------------ 
        │PK id (pk,auto)        │---<--------------<---│PK,FK idEmployee(pk,fk)│
        │   first_name          │                    │      street_name      │
        │   last_name           │                    │      city_name        │
        │   salary              │                    │      state_name       │
        │                       │                    │      zipcode          │
         ------------------------                       -----------------------
  <hibernate-mapping>    
    <class name="company.entity.associationsmappings.unidirectional.one_to_one.pk.Employee" 
           table="EMPLOYEE"> 
        <meta attribute="class-description"> This class contains the employee detail. </meta> 
        <id name="id" type="int" column="id"> 
            <generator class="native"/> 
        </id>          
        <property name="firstName" column="first_name" type="string"/> 
        <property name="lastName" column="last_name" type="string"/> 
        <property name="salary" column="salary" type="int"/> 
          <!--the property "address" doesn't exist
           (in Address there exists a reference to a (unique) Employee-->    
    </class> 
    
    * <class name="company.entity.associationsmappings.unidirectional.one_to_one.pk.Address" table="ADDRESS_ONE_TO_ONE_PK">
    <meta attribute="class-description"> This class contains the address detail. </meta>
    <!--name:  "id" attribute from Address objects is the key
        colum: "idEmployee on ADDRESS_ONE-TO_ONE_PK table is the primary key
        type: "int"-->
    <id column="idEmployee" name="id" type="int">
      <!--A unidirectional one-to-one association on a primary key usually uses
        a special id generator: "foreign"
        class: "foreign" means that Address id's value is obtained from ...
        param name="property": ... the id of the object refered by the
                              Address property "employe" 
        (in other words: The primary key "idEmployee" of ADDRESS_ONE_TO_ONE_PK
         is, at the same time, a foreign key of the table which Employee objects
         are mapped to -that is, in fact, "EMPLOYEES"-)
        -->
      <generator class="foreign">
        <param name="property">employee</param>
      </generator>
    </id>
    <!--name: the property "employee" ...
        class: is a object of class Employee.class
        constrained:-->
    <one-to-one class="company.entity.associationsmappings.unidirectional.one_to_one.pk.Employee" constrained="true" name="employee"/>
    <property column="street_name" name="street" type="string"/>
    <property column="city_name" name="city" type="string"/>
    <property column="state_name" name="state" type="string"/>
    <property column="zipcode" name="zipcode" type="string"/>
  </class>
</hibernate-mapping>
 * </pre>
 */
public class ManageEmployee {

    public static void main(String[] args) {
   
        ManageEmployee ME = new ManageEmployee(); /* Add few employee records in database */
        
        
         /* Add employee records in the database */ 
        Integer empID1 = ME.addEmployee("Manoj", "Kumar", 4000);
        
        /* Let us have one address object */
        Address address1 = ME.addAddress(empID1,"Kondapur","Hyderabad","AP","532");
       
        Address address2;
        
        try{
        /* Add another address record in the database but trying it with the same employee */
            address2 = ME.addAddress(empID1,"Saharanpur","Ambehta","UP","111");
        }catch(org.hibernate.exception.ConstraintViolationException e){
            System.out.println("¡¡¡¡¡¡¡¡¡¡¡Dirección Postal ya asignada!!!!!!!!!!");
            //e.printStackTrace();
        }
        
      
         /* Let us have another employee to this address object */
        Integer empID2=ME.addEmployee("Dilip", "Kumar", 3000);
        address2 = ME.addAddress(empID2,"Saharanpur","Ambehta","UP","111"); 
       
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
    public Address addAddress(Integer employeeID, String street, String city, String state, String zipcode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer addressID = null;
        Address address = null;
        
        try{
            tx = session.beginTransaction();
            Employee employee=(Employee)session.get(Employee.class, employeeID);
            address = new Address(employee,street, city, state, zipcode);
            addressID = (Integer) session.save(address);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)){
                System.out.println("¡¡¡¡¡¡¡¡"+e.getMessage());
                throw e;
            }
            else 
                e.printStackTrace();
        }finally {
            session.close();
        } return address;
    }
     
    /* Method to add an employee record in the database */            
    public Integer addEmployee(String fname, String lname, int salary){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try{
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary);
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
                
               /*For each employee, a query on Address table must be run*/
                List addresses=session.createQuery("FROM Address "
                                                 + "WHERE idEmployee = :empl").
                                                 setParameter("empl", employee.getId()).
                                                 list();
               /*We get, if exists, the only address for this employee*/
                if (addresses.iterator().hasNext()){
                    Address add=(Address)addresses.iterator().next();
               
                    System.out.println("Address ");
                    System.out.println("\tStreet: " + add.getStreet());
                    System.out.println("\tCity: " + add.getCity());
                    System.out.println("\tState: " + add.getState());
                    System.out.println("\tZipcode: " + add.getZipcode());
                }
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
            
            /*MySQL doesn't implement foreign key, so this employee's address
              is not deleted in cascade, thereafter, we delete this address here*/
            Integer idAddress=EmployeeID;
            Address address= (Address)session.get(Address.class,idAddress);
            if (address!=null)
                session.delete(address);
            
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close(); 
        }
    }
}



