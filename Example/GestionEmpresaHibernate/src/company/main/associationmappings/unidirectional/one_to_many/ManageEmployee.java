package company.main.associationmappings.unidirectional.one_to_many;

import company.entity.associationsmappings.unidirectional.one_to_many.Certificate;
import company.entity.associationsmappings.unidirectional.one_to_many.Employee;
import java.util.List;
import java.util.Iterator;
import java.util.Set; //<-------------Set
import java.util.HashSet; //<-------------HashSet
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import company.util.HibernateUtil;

/**
 * <h2 align="center">one-to-many unidirectional</h2> 
 * <pre>
         Employee                                      Certificate
         ----------------                               ---------
        |id              |                /----------->|id       |  
        |firstName       |           1:n               |name     |
        |lastName        |       (one-to-many           ---------
        |salary          |       unidirectional)                  
        |address         |            /                           
        |SET certificates|-----------/                                        
         ----------------                                                                          

        |id   \                                  |id
        |      \                                 |
        |       \                                |
        |........\...............................|...........................
        |         \--------------------------\   |
        |id (pk,auto)                         \  |id (pk,auto)
                                               \ 
                                                \| employe_id(fk)
                                                   fk on employee's pk table
                                       

        EMPLOYEE_MANY_TO_ONE                          CERTIFICATE
         ------------------     1                ------------------- 
        │PK id (pk,auto)  │----<-- \           │PK id(pk,auto)     │
        │   first_name    │         \      n   │   certificate_name│
        │   last_name     │          \ ----<<--│FK employee_id     │
        │   salary        │                     --------------------          
         ------------------                       
           
<hibernate-mapping> 
    <class name="company.entity.associationsmappings.unidirectional.one_to_many.Employee" 
           table="EMPLOYEE"> 
        <meta attribute="class-description"> This class contains the employee detail. </meta> 
        <id name="id" type="int" column="id"> 
            <generator class="native"/> 
        </id> 
        
        <!--The <set> element is new here and has been introduced to set
            the relationship between Certificate and Employee classes-->
            <!--The name attribute is set to the defined Set variable in the 
                parent class, in our case it is certificates-->
            <!--We used the cascade attribute in the <set> element to tell Hibernate
                to persist the Certificate objects at the same time as the Employee objects.-->
        <set name="certificates" cascade="all"> 
            <!--The <key> element is the column in the CERTIFICATE table that
                holds the foreign key to the parent object ie. table EMPLOYEE.-->
            <key column="employee_id"/> 
            <!--The <one-to-many> element indicates that one Employee object 
                relates to many Certificate objects and, as such, 
                the Certificate object must have a Employee parent associated 
                with it. You can use either <one-to-one>, <many-to-one> or 
                <many-to-many> elements based on your requirement.-->
            <one-to-many class="company.entity.associationsmappings.unidirectional.one_to_many.Certificate"/> 
        </set>
        
        <property name="firstName" column="first_name" type="string"/> 
        <property name="lastName" column="last_name" type="string"/> 
        <property name="salary" column="salary" type="int"/> 
    </class> 
    * 
    <class name="company.entity.associationsmappings.unidirectional.one_to_many.Certificate" 
           table="CERTIFICATE">
        <meta attribute="class-description"> This class contains the certificate records. </meta> 
        <id name="id" type="int" column="id"> 
            <generator class="native"/> 
        </id> 
        <property name="name" column="certificate_name" type="string"/> 
    </class>
</hibernate-mapping>
      
 
 * </pre> 
 */

public class ManageEmployee {

  public static void main(String[] args) {

    ManageEmployee ME = new ManageEmployee(); /* Add few employee records in database */

    /* Let us have a set of certificates for the first employee */
    HashSet set1 = new HashSet();
    set1.add(new Certificate("MCA"));
    set1.add(new Certificate("MBA"));
    set1.add(new Certificate("PMP"));
    /* Add employee records in the database */
    Integer empID1 = ME.addEmployee("Manoj", "Kumar", 4000, set1);

    /* Another set of certificates for the second employee */
    HashSet set2 = new HashSet();
    set2.add(new Certificate("BCA"));
    set2.add(new Certificate("BA"));

    /* Add another employee record in the database */
    Integer empID2 = ME.addEmployee("Dilip", "Kumar", 3000, set2);

    /* List down all the employees */
    ME.listEmployees();

    /* Update employee's records */
    ME.updateEmployee(empID1, 5000);

    /* Delete an employee from the database */
    ME.deleteEmployee(empID2);

    /* List down new list of the employees */
    ME.listEmployees();
  }

  /* Method to add an employee record in the database */ //Set
  public Integer addEmployee(String fname, String lname, int salary, Set cert) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    Integer employeeID = null;

    try {
      tx = session.beginTransaction();
      Employee employee = new Employee(fname, lname, salary);
      employee.setCertificates(cert);//<----------------------Set
      employeeID = (Integer) session.save(employee);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
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

        //-----Iterate over the Set of Certificates of Employe i--------
        Set certificates = employee.getCertificates();
        for (Iterator iterator2 = certificates.iterator(); iterator2.hasNext();) {
          Certificate certName = (Certificate) iterator2.next();
          System.out.println("Certificate: " + certName.getName());
        }
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
