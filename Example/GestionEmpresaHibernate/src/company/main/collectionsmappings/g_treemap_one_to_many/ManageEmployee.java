package company.main.collectionsmappings.g_treemap_one_to_many;

import java.util.List;
import java.util.Iterator;
import java.util.SortedMap; //<-------------SortedMap
import java.util.Map;
import java.util.TreeMap; //<-------------TreeMap
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import company.util.HibernateUtil;
import company.entity.collectionsmappings.g_sortedmap_one_to_many.*;

public class ManageEmployee {

  public static void main(String[] args) {

    ManageEmployee ME = new ManageEmployee(); /* Add few employee records in database */

    /* Let us have a set of certificates for the first employee */
    TreeMap<String, Certificate> set1 = new TreeMap<>();
    set1.put("ComputerScience", new Certificate("MCA"));
    set1.put("BusinessManagement", new Certificate("MBA"));
    set1.put("ProjectManagement", new Certificate("PMP"));
    /* Add employee records in the database */
    Integer empID1 = ME.addEmployee("Manoj", "Kumar", 4000, set1);

    /* List down all the employees */
    ME.listEmployees();

    /* Update employee's records */
    ME.updateEmployee(empID1, 5000);

    /* List down new list of the employees */
    ME.listEmployees();
  }

  /* Method to add an employee record in the database */ //Collection
  public Integer addEmployee(String fname, String lname, int salary, SortedMap<String, Certificate> cert) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    Integer employeeID = null;

    try {
      tx = session.beginTransaction();
      Employee employee = new Employee(fname, lname, salary);
      employee.setCertificates(cert);//<----------------------SortedMap
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

        //-----Get the value per each key in the Map of Certificates of Employe i--------
        SortedMap<String, Certificate> map = employee.getCertificates();
        for (Map.Entry<String, Certificate> entry : map.entrySet()) {
          System.out.print("\tCertificate Type: " + entry.getKey());
          System.out.println(", Name: " + (entry.getValue()).getName());
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
