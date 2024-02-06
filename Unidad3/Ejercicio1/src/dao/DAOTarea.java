package dao;

import static dao.DAODireccion.addDireccion;
import static dao.DAODireccion.getDirecciones;
import hibernate.Direccion;
import hibernate.Empleado;
import hibernate.Tarea;
import hibernate.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAOTarea {
    
    public static void addTarea(int idEmpleado, Tarea t) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Empleado e = (Empleado) session.get(Empleado.class, idEmpleado);
            e.getTareas().add(t);
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
    }
    
    public static void listTareas() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            List<Tarea> tareas = session.createQuery("FROM Tarea").list();
            
            System.out.println("----- TAREAS -----");
            for(Tarea t : tareas) {
                System.out.println("Denominacion: " + t.getDenominacion());
                System.out.println("Descripcion: " + t.getDescripcion());
                System.out.println("Prioridad: " + t.getPrioridad());
                
                System.out.println();
                
            }
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
    }
    
    public static void listTareasFromEmpleado(Empleado e) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            String hql = "FROM Tarea WHERE idEmpleado = :idEmpleado";
            List<Tarea> tareas = session.createQuery(hql)
                            .setParameter("idEmpleado", e)
                            .list();
            
            System.out.println("Tareas: ");
            for(Tarea t : tareas) {
                System.out.println("  Denominacion: " + t.getDenominacion());
                System.out.println("  Descripcion: " + t.getDescripcion());
                System.out.println("  Prioridad: " + t.getPrioridad());
                
                System.out.println();
                
            }
            
            tx.commit();
        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            
            ex.printStackTrace();
        }
        
    }
    
    public static List<Tarea> getTareas() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Tarea> tareas = null;
        
        try {
            tx = session.beginTransaction();
            
            tareas = session.createQuery("FROM Tarea").list();
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
        return tareas;
        
    }
    
    public static List<Tarea> getTareasFromEmpleado(Empleado e) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Tarea> tareas = null;
        
        try {
            tx = session.beginTransaction();
            
            String hql = "FROM Tarea WHERE idEmpleado = :idEmpleado";
            tareas = session.createQuery(hql)
                            .setParameter("idEmpleado", e)
                            .list();
            
            tx.commit();
        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            
            ex.printStackTrace();
        }
        
        return tareas;
        
    }
        
    public static void updateTarea(int idTarea) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Tarea t = (Tarea) session.get(Tarea.class, idTarea);
            t.setPrioridad("BAJA");
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
    }
    
    public static void deleteTarea(int idTarea) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Tarea t = (Tarea) session.get(Tarea.class, idTarea);
            session.delete(t);
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
    }
    
}
