package dao;

import hibernate.Direccion;
import hibernate.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class DAODireccion {
    
    public static int addDireccion(Direccion d) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer idDireccion = null;
        
        try {
            
            tx = session.beginTransaction();
            
            idDireccion = (Integer) session.save(d);
            
            tx.commit();
            
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
        return idDireccion;
        
    }
    
    public static void listDirecciones() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            List<Direccion> direcciones = session.createQuery("FROM Direccion").list();
            
            System.out.println("----- DIRECCIONES -----");
            for(Direccion d : direcciones) {
                System.out.println("Calle: " + d.getCalle());
                System.out.println("Numero: " + d.getNumero());
                System.out.println("CP: " + d.getCp());
                System.out.println("Provincia: " + d.getProvincia());
                System.out.println();
            }
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
    }
    
        public static List<Direccion> getDirecciones() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Direccion> direcciones = null;
        
        try {
            tx = session.beginTransaction();
            
            direcciones = session.createQuery("FROM Direccion").list();
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
        return direcciones;
        
    }
    
    public static void deleteDireccion(int idDireccion) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Direccion d = (Direccion) session.get(Direccion.class, idDireccion);
            session.delete(d);
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
    }
    
    public static Direccion existeDireccion(Direccion d) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Direccion> direcciones = null;
        
        try {
            tx = session.beginTransaction();
            
            String hql = "FROM Direccion WHERE calle = :calle AND numero = :numero AND cp = :cp AND provincia = :provincia";
            
            Query query = session.createQuery(hql);
            query.setParameter("calle", d.getCalle());
            query.setParameter("numero", d.getNumero());
            query.setParameter("cp", d.getCp());
            query.setParameter("provincia", d.getProvincia());
            
            direcciones = query.list();
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            
            e.printStackTrace();
        }
        
        if(direcciones.size() == 1) {
            return direcciones.get(0);
        } else {
            return null;
        }
        
    }
    
}
