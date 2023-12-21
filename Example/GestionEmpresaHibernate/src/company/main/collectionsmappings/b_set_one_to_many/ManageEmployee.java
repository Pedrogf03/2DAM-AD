package company.main.collectionsmappings.b_set_one_to_many;

import java.util.List;
import java.util.Iterator;
import java.util.Set; //<-------------Set
import java.util.HashSet; //<-------------HashSet
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import company.util.HibernateUtil;
import company.entity.collectionsmappings.b_set_one_to_many.*;

public class ManageEmployee {

  public static void main(String[] args) {

    ManageEmployee ME = new ManageEmployee(); /* Add few employee records in database */

    /* Let us have a set of certificates for the first employee */
    HashSet<Certificate> set1 = new HashSet<>();
    set1.add(new Certificate("MCA"));
    set1.add(new Certificate("MBA"));
    set1.add(new Certificate("PMP"));
    /* Add employee records in the database */
    Integer empID1 = ME.addEmployee("Manoj", "Kumar", 4000, set1);

    // /* Another set of certificates for the second employee */
    // HashSet<Certificate> set2 = new HashSet<>();
    // set2.add(new Certificate("BCA"));
    // set2.add(new Certificate("BA"));

    // /* Add another employee record in the database */
    // Integer empID2 = ME.addEmployee("Dilip", "Kumar", 3000, set2);

    // /* List down all the employees */
    // ME.listEmployees();

    // /* Update employee's records */
    // ME.updateEmployee(empID1, 5000);

    // /* Delete an employee from the database */
    // ME.deleteEmployee(empID2);

    // /* List down new list of the employees */
    // ME.listEmployees();
  }

  /* Method to add an employee record in the database */ //Set
  public Integer addEmployee(String fname, String lname, int salary, Set<Certificate> cert) {

    Session session = null;
    Transaction tx = null;
    Integer employeeID = null;

    try {

      session = HibernateUtil.getSessionFactory().openSession();

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
      @SuppressWarnings("unchecked")
      List<Employee> employees = session.createQuery("FROM Employee").list();

      //-------Iterate over the List obtanited from the Query
      for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
        Employee employee = (Employee) iterator.next();
        System.out.print("First Name: " + employee.getFirstName());
        System.out.print(" Last Name: " + employee.getLastName());
        System.out.println(" Salary: " + employee.getSalary());

        //-----Iterate over the Set of Certificates of Employe i--------
        Set<Certificate> certificates = employee.getCertificates();
        for (Iterator<Certificate> iterator2 = certificates.iterator(); iterator2.hasNext();) {
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
