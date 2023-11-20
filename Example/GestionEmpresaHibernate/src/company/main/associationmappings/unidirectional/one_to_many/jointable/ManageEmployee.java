package company.main.associationmappings.unidirectional.one_to_many.jointable;

import company.entity.associationsmappings.unidirectional.one_to_many.jointable.Certificate;
import company.entity.associationsmappings.unidirectional.one_to_many.jointable.Employee;
import java.util.List;
import java.util.Iterator;
import java.util.Set;     //<-------------Set
import java.util.HashSet; //<-------------HashSet
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import company.util.HibernateUtil;

/**
 * <h2 align="center">one-to-many unidirectional with join table</h2> 
 * <p>An unidirectional one-to-many association on a join table is the preferred 
 *    option. Specifiying 'unique="true"' on <many-to-many> element, changes the
 *    multiciplity from many-to-many to one-to-many.</p>
 * <pre>
         Employee                                      Certificate
         ----------------                               ---------
        |id              |                /----------->|id       |  
        |firstName       |           1:n               |name     |
        |lastName        |      ( one -to-many          ---------
        |salary          |       unidirectional)                  
        |address         |            /                           
        |SET certificates|-----------/                                        
         ----------------                                                                          

        |id                                                id
        |\                                                 /|
        | \                                               / |
        ...\............................................./..|...................
        |   \------------------|employee_id(fk)         /   |
        |id (pk,auto)           fk on employee's       /    |id (pk,auto)
                                pk table              /
                                                     /
                               certificate_id(fk,pk)|             
                               fk on certificates's
                               pk table
                                      

        EMPLOYEE_MANY_TO_ONE        EMP_CERT           CERTIFICATE_WITH_JOIN_TABLE
         ------------------     -------------------       ------------------- 
        │PK id (pk,auto)  │<--|FK   employee_id   |  /->│PK id(pk,auto)     │
        │   first_name    │   |PK,FK certificate_id|/   │   certificate_name│
        │   last_name     │    -------------------        -------------------
        │   salary        │                                                           
         ------------------                       
           
<hibernate-mapping> 
    <class name="company.entity.associationsmappings.unidirectional.many_to_many.jointable.Employee" 
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
                to persist the Certificate objects for SAVE i.e. CREATE and UPDATE
                operations at the same time as the Employee objects-->
            <!--table attribute is set to the intermediate table EMP_CERT.-->
        <set name="certificates" cascade="save-update" table="EMP_CERT">
            <!--The <key> element is the column in the EMP_CERT table that
                holds the foreign key to the parent object ie. table EMPLOYEE.-->
            <key column="employee_id"/> 
            <!--The <many-to-many> element indicates that one Employee object 
                relates to many Certificate objects and column attribute in
                EMP_CERT_ONE_TO_MANY table holds the foreing key to the Certificate 
                object,ie. table CERTIFICATE_WITH_JOIN_TABLE-->
            <many-to-many column="certificate_id" 
                          class="company.entity.associationsmappings.unidirectional.many_to_many.jointable.Certificate"
                          unique="true"/> 
        </set>
        <property name="firstName" column="first_name" type="string"/> 
        <property name="lastName" column="last_name" type="string"/> 
        <property name="salary" column="salary" type="int"/> 
    </class> 
    
    <class name="company.entity.associationsmappings.unidirectional.many_to_many.jointable.Certificate" 
           table="CERTIFICATE_MANY_TO_MANY">
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
        
        Certificate cert=new Certificate("ABC");
        
        /* Let us have a set of certificates for the first employee */
        HashSet set1 = new HashSet();
        
        set1.add(new Certificate("MCA"));
        set1.add(new Certificate("MBA"));
        set1.add(new Certificate("PMP")); 
        set1.add(cert);
       /* Add employee records in the database */ 
        Integer empID1 = ME.addEmployee("Manoj", "Kumar", 4000, set1);
          
        HashSet set2= new HashSet();
        set2.add(new Certificate("DEF"));
        set2.add(cert); 
        Integer empID2=null;
        try{
        /* One of the certificates is the same for the second employee: error */
        /* Add another employee record in the database */
           empID2 = ME.addEmployee("Dilip", "Kumar", 3000, set2);     
        }catch(org.hibernate.exception.ConstraintViolationException e){
            System.out.println("¡¡¡¡El certificado \"ABC\" no puede ser compartido!!!!");
        }
        
        //Once this last certificate is remove from the set, the new employee
        //can be added
        set2.remove(cert);
        
        empID2 = ME.addEmployee("Dilip", "Kumar", 3000, set2);
     /*Se genera un error: parece que se trata de hacer un update sobre la tabla 
        CERTIFICATE_WITH_JOIN_TABLE cuando se debería hacer un insert
        ¿La 'Transactión' no funcionó como debería?*/
        
        /* List down all the employees */
        ME.listEmployees();
        
        /* Update employee's records */
        ME.updateEmployee(empID1, 5000);
        
        /* Delete an employee from the database */ 
        ME.deleteEmployee(empID2); //Deleting empID2, all entries in EMP_CERT 
                                   //are deleted, but the entry for
                                   //"ABC" certificate stay in
                                   //CERTIFICATES_MANY_TO_MANY
        
        /* List down new list of the employees */
        ME.listEmployees(); 
    }
        
     
    /* Method to add an employee record in the database */            //Set
    public Integer addEmployee(String fname, String lname, int salary, Set cert){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try{
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary);
            employee.setCertificates(cert);//<----------------------Set
            employeeID = (Integer) session.save(employee);//<set cascade="save-update"...>
            tx.commit();
        }catch(org.hibernate.exception.ConstraintViolationException e){//<--Certificado
            if (tx!=null) tx.rollback();                               //compartido por
                System.out.println(e.getMessage());                    //dos empleados
                throw e;
        }
        catch (HibernateException e) { 
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
                
                //-----Iterate over the Set of Certificates of Employe i--------
                Set certificates = employee.getCertificates();
                for (Iterator iterator2 = certificates.iterator();iterator2.hasNext();){
                    Certificate certName = (Certificate) iterator2.next();
                    System.out.println("Certificate: " + certName.getName());
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
            session.update(employee); //<set cascade="save-update"...>
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



