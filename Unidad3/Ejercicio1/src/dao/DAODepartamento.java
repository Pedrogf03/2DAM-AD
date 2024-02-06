package dao;

import static dao.DAODireccion.addDireccion;
import static dao.DAODireccion.existeDireccion;
import static dao.DAOEmpleado.addEmpleado;
import static dao.DAOEmpleado.existeEmpleado;
import static dao.DAOTarea.listTareasFromEmpleado;
import hibernate.Departamento;
import hibernate.Direccion;
import hibernate.Empleado;
import hibernate.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAODepartamento {
    
    public static int addDepartamento(Departamento d) {
        
        Empleado e = existeEmpleado(d.getJefe());

        if(e == null) {
            addEmpleado(d.getJefe());
        } else {
            d.setJefe(e);
        }
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer idDepartamento = null;
        
        try {
            tx = session.beginTransaction();
            
            idDepartamento = (Integer) session.save(d);
            
            tx.commit();
        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            
            ex.printStackTrace();
        }
        
        return idDepartamento;
        
    }
    
    public static void listDepartamentos() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            List<Departamento> departamentos = session.createQuery("FROM Departamentos").list();
            
            System.out.println("----- DEPARTAMENTOS -----");
            for(Departamento d : departamentos) {
                System.out.println("Nombre: " + d.getDenominacion());
                
                System.out.println();
                
            }
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
    }
    
    public static List<Departamento> getDepartamentos() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Departamento> departamentos = null;
        
        try {
            tx = session.beginTransaction();
            
            departamentos = session.createQuery("FROM Departamento").list();
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
        return departamentos;
        
    }
    
    public static void deleteEmpleado(int idEmpleado) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Empleado e = (Empleado) session.get(Empleado.class, idEmpleado);
            session.delete(e);
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
    }
    
}
