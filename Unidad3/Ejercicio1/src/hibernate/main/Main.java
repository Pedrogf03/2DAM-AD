/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.main;

import hibernate.Direccion;
import hibernate.Empleado;
import hibernate.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Pedro
 */
public class Main {
    
    public static void main(String[] args) {
        
        Main m = new Main();
        
        m.addDireccion("Calle de ejemplo", "02", "51002", "Cadiz");
        
        m.listDireccion();
        
        //m.updateDireccion(1);
        
        //m.deleteDireccion(1);
        
        //m.listDireccion();
        
        System.exit(0);
        
    }
    
    
    // DIRECCION ----------------------------------------------------------------------------------
    
    public int addDireccion(String calle, String numero, String cp, String provincia) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer idDireccion = null;
        
        try {
            
            tx = session.beginTransaction();
            
            Direccion d = new Direccion(calle, numero, cp, provincia);
            idDireccion = (Integer) session.save(d);
            
            tx.commit();
            
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        
        return idDireccion;
        
    }
    
    public void listDireccion() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            List<Direccion> direcciones = session.createQuery("FROM Direccion").list();
            
            for(Direccion d : direcciones) {
                System.out.println("Calle: " + d.getCalle());
                System.out.println("Numero: " + d.getNumero());
                System.out.println("CP: " + d.getCp());
                System.out.println("Provincia: " + d.getProvincia());
            }
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        
    }
    
    public void updateDireccion(int idDireccion) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Direccion d = (Direccion) session.get(Direccion.class, idDireccion);
            d.setCalle("Juan Carlos 1º, Pton. 25");
            d.setNumero("12");
            d.setProvincia("Ceuta");
            
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        
    }
    
    public void deleteDireccion(int idDireccion) {
        
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
    
        // EMPLEADO -------------------------------------------------------------------------------
    
        public int addEmpleado(String nombre, String apellidos, int salario, Direccion direccion) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer idEmpleado = null;
        
        try {
            
            tx = session.beginTransaction();
            
            Empleado e = new Empleado(nombre, apellidos, salario, direccion);
            idEmpleado = (Integer) session.save(e);
            
            tx.commit();
            
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        
        return idEmpleado;
        
    }
    
}
