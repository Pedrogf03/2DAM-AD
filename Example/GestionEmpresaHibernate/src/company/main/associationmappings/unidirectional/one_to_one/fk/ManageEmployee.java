package company.main.associationmappings.unidirectional.one_to_one.fk;

import company.entity.associationsmappings.unidirectional.one_to_one.fk.Employee;
import company.entity.associationsmappings.unidirectional.one_to_one.fk.Address;
import java.util.List;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import company.util.HibernateUtil;

/**
/**
*  <h2 align="center">Unidirectional. one-to-one , foreign key unique</h2>
* <pre>
     
         Employee                                         Address
        ----------                                       ---------
       |id        |                       /------------>|id       |  
       |firstName |               1:1    /              |street   |
       |lastName  |         (  one-to-one               |city     |
       |salary    |          unidirectional)            |state    |
       |address --|-------------/                       |zipcode  |
        ----------                                       ---------
       
       |id                                      |id
       | |address                               |
       | |                                      |
       |.|......................................|...................
       | |                                      |
       | |address(fk,nn,UNIQUE)                 |
       |  fk on                                 |
       |  Address's pk table                    |
       |id (pk,auto)                            |id (pk,auto) 
       
          EMPLOYEE_ONE_TO_ONE                                 ADDRESS
        ------------------------                   1     ----------------- 
       │PK id (pk,auto)        │              /------>│PK id(pk,auto)   │
       │   first_name          │             /        │   street_name   │
       │   last_name           │            /         │   city_name     │
       │   salary              │    1      /          │   state_name    │
       │FK address(fk,nn,uniq.)│----->----/           │   zipcode       │
        ------------------------                         -----------------
     
     <hibernate-mapping>      <!--EMPLOYEE_ONE_TO_ONE: address UNIQUE-->
   <class name="company.entity.associationsmappings.unidirectional.one_to_one.fk.Employee" 
          table="EMPLOYEE_ONE_TO_ONE"> 
       <meta attribute="class-description"> This class contains the employee detail. </meta> 
       <id name="id" type="int" column="id"> 
           <generator class="native"/> 
       </id>        
          
      <!--The <many-to-one> element indicates that many Employee objects 
          can be related to one (the same) Address object and, as such, 
          the Employee object must have an Address object associated 
          with it.
          The name attribute is set to the defined variable in the parent class,
          in our case it is address, that must be mapped.
          The column attribute is used to set the column name in the parent table
          EMPLOYEE_ONE_TO_ONE that is the target of the mapping.
          The class attribute is used to describe the class of the mapped value
          The not-null attribute sets if null values are allowed
          
          The unique attribute is set to true so that only one Employee object
          can be associated with an address object.
          This case is a unidirectional one-to-one on a FOREIGN KEY
          (EMPLOYEE_ONE_TO_ONE.address) and this column should be UNIQUE
          
       -->
       <many-to-one name="address" column="address" unique="true"
                    class="company.entity.associationsmappings.unidirectional.one_to_one.fk.Address"
                    not-null="true"/>
       
       <property name="firstName" column="first_name" type="string"/> 
       <property name="lastName" column="last_name" type="string"/> 
       <property name="salary" column="salary" type="int"/> 
   </class> 
   
     <class name="company.entity.associationsmappings.unidirectional.one_to_one.fk.Address" 
        table="ADDRESS">
   <meta attribute="class-description"> This class contains the address detail. </meta>
   <id column="id" name="id" type="int">
     <generator class="native"/>
   </id>
   <property column="street_name" name="street" type="string"/>
   <property column="city_name" name="city" type="string"/>
   <property column="state_name" name="state" type="string"/>
   <property column="zipcode" name="zipcode" type="string"/>
 </class>

* </pre>
 */
public class ManageEmployee {

  public static void main(String[] args) {

    ManageEmployee ME = new ManageEmployee(); /* Add few employee records in database */

    /* Let us have one address object */
    Address address1 = ME.addAddress("Kondapur", "Hyderabad", "AP", "532");

    /* Add employee records in the database */
    Integer empID1 = ME.addEmployee("Manoj", "Kumar", 4000, address1);

    Integer empID2 = 0;
    try {
      /* Add another employee record in the database but trying it with the same address */

      empID2 = ME.addEmployee("Dilip", "Kumar", 3000, address1);
    } catch (org.hibernate.exception.ConstraintViolationException e) {
      System.out.println("¡¡¡¡¡¡¡¡¡¡¡Dirección Postal duplicada!!!!!!!!!!");
      //e.printStackTrace();
    }

    /* Let us have another address object */
    Address address2 = ME.addAddress("Saharanpur", "Ambehta", "UP", "111");

    empID2 = ME.addEmployee("Dilip", "Kumar", 3000, address2);

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

    try {
      tx = session.beginTransaction();
      address = new Address(street, city, state, zipcode);
      addressID = (Integer) session.save(address);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return address;
  }

  /* Method to add an employee record in the database */ //Address
  public Integer addEmployee(String fname, String lname, int salary, Address address) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    Integer employeeID = null;

    try {
      tx = session.beginTransaction();
      Employee employee = new Employee(fname, lname, salary, address);
      employeeID = (Integer) session.save(employee);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();

      if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
        System.out.println("¡¡¡¡¡¡¡¡" + e.getMessage());
        throw e;
      } else
        e.printStackTrace();
    } finally {
      session.close();
    }

    return employeeID;
  }

  /* Method to READ all the employees */
  public void listEmployees() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      List employees = session.createQuery("FROM Employee").list();

      //-------Iterate over the List obtanited from the Query
      for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
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
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  /* Method to UPDATE salary for an employee */
  public void updateEmployee(Integer EmployeeID, int salary) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Employee employee = (Employee) session.get(Employee.class, EmployeeID);
      employee.setSalary(salary);
      session.update(employee);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  /* Method to DELETE an employee from the records */
  public void deleteEmployee(Integer EmployeeID) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Employee employee = (Employee) session.get(Employee.class, EmployeeID);
      session.delete(employee);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }
}
