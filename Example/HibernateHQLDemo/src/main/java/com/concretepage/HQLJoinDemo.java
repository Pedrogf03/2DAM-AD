package com.concretepage;

import java.util.List;
import org.hibernate.Session;

public class HQLJoinDemo {
  public static void main(String[] args) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    String hql = "from Company as comp inner join comp.employees as emp";
    List<?> list = session.createQuery(hql).list();
    for (int i = 0; i < list.size(); i++) {
      Object[] row = (Object[]) list.get(i);
      Company company = (Company) row[0];
      Employee employee = (Employee) row[1];
      System.out.println("CompId:" + company.getCompId() + ", CompName:" + company.getCompName() +
          ", EmpId:" + employee.getEmpId() + ", EmpName:" + employee.getEmpName());
    }
    session.close();
  }
}
