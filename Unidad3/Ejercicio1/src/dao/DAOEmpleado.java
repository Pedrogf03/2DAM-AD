package dao;

import static dao.DAODireccion.addDireccion;
import static dao.DAODireccion.existeDireccion;
import static dao.DAODireccion.getDirecciones;
import static dao.DAOTarea.getTareasFromEmpleado;
import static dao.DAOTarea.listTareasFromEmpleado;
import hibernate.Direccion;
import hibernate.Empleado;
import hibernate.Tarea;
import hibernate.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAOEmpleado {
    
    public static int addEmpleado(Empleado e) {
        
        Direccion d = existeDireccion(e.getDireccion());

        if(d == null) {
            addDireccion(e.getDireccion());
        } else {
            e.setDireccion(d);
        }
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer idEmpleado = null;
        
        try {
            tx = session.beginTransaction();
            
            idEmpleado = (Integer) session.save(e);
            
            tx.commit();
        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            
            ex.printStackTrace();
        }
        
        return idEmpleado;
        
    }
    
    public static void listEmpleados() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            List<Empleado> empleados = session.createQuery("FROM Empleado").list();
            
            System.out.println("----- EMPLEADOS -----");
            for(Empleado e : empleados) {
                System.out.println("Nombre: " + e.getNombre());
                System.out.println("Apellidos: " + e.getApellidos());
                System.out.println("Salario: " + e.getSalario());
                System.out.println("Direccion: ");
                System.out.println("  Calle: " + e.getDireccion().getCalle());
                System.out.println("  Numero: " + e.getDireccion().getNumero());
                System.out.println("  CP: " + e.getDireccion().getCp());
                System.out.println("  Provincia: " + e.getDireccion().getProvincia());
                
                if(e.getDepartamento() != null) {
                    // TODO: sout departamento
                }
                
                if(e.getVehiculo() != null) {
                    // TODO: sout vehiculo
                }
                
                if(!e.getTareas().isEmpty()) {
                    listTareasFromEmpleado(e);
                }
                
                System.out.println();
                
            }
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
    }
    
    public static List<Empleado> getEmpleados() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Empleado> empleados = null;
        
        try {
            tx = session.beginTransaction();
            
            empleados = session.createQuery("FROM Empleado").list();
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
        return empleados;
        
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
    
    public static Empleado existeEmpleado(Empleado e) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Empleado> empleados = null;
        
        try {
            tx = session.beginTransaction();
            
            String hql = "FROM Empleado WHERE nombre = :nombre AND apellidos = :apellidos AND salario = :salario";
            
            Query query = session.createQuery(hql);
            query.setParameter("nombre", e.getNombre());
            query.setParameter("apellidos", e.getApellidos());
            query.setParameter("salario", e.getSalario());
            
            empleados = query.list();
            
            tx.commit();
        } catch (Exception ex) {
            if(tx != null)
                tx.rollback();
            
            ex.printStackTrace();
        }
        
        if(empleados.size() == 1) {
            return empleados.get(0);
        } else {
            return null;
        }
        
    }
    
}
