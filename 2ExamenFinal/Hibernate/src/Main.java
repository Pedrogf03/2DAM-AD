
import hibernate.Lider;
import hibernate.Seguidor;
import hibernate.Seguidoreslider;
import hibernate.SeguidoresliderId;
import hibernate.util.HibernateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bleik
 */
public class Main {
    
    public static void main(String[] args) {
        
        int option = 0;
        Scanner sc = new Scanner(System.in);

        do {
          System.out.println("-----------------------------------");
          System.out.println("Elige una opción:");
          System.out.println("1.- Realizar alta de un líder.");
          System.out.println("2.- Realizar alta de un seguidor.");
          System.out.println("3.- Seguir a un líder.");
          System.out.println("4.- Dejar de seguir a un líder.");
          System.out.println("5.- Dar 'me gusta' a un lider.");
          System.out.println("6.- Ver 'me gusta' de un lider.");
          System.out.println("7.- Ver seguidores de un lider.");
          System.out.println("-----------------------------------");

          option = Integer.parseInt(sc.nextLine());
          String nombreLider;
          String nickSeguidor;
          String correoSeguidor;
          String paisSeguidor;

          switch (option) {
              case 1:
                  System.out.println("Nombre: ");
                  nombreLider = sc.nextLine();
                  Lider l1 = new Lider(nombreLider, LocalDate.now());
                  altaLider(l1);
                  break;
              case 2:
                  System.out.println("Nick: ");
                  nickSeguidor = sc.nextLine();
                  System.out.println("Correo: ");
                  correoSeguidor = sc.nextLine();
                  System.out.println("Pais: ");
                  paisSeguidor = sc.nextLine();
                  Seguidor s1 = new Seguidor(nickSeguidor, correoSeguidor, paisSeguidor);
                  altaSeguidor(s1);
                  break;
              case 3:
                  System.out.println("Nick del seguidor: ");
                  nickSeguidor = sc.nextLine();
                  System.out.println("Nombre del lider: ");
                  nombreLider = sc.nextLine();
                  seguirLider(getSeguidor(nickSeguidor), getLider(nombreLider));
                  break;
              case 4:
                  System.out.println("Nick del seguidor: ");
                  nickSeguidor = sc.nextLine();
                  System.out.println("Nombre del lider: ");
                  nombreLider = sc.nextLine();
                  dejarSeguir(getSeguidor(nickSeguidor), getLider(nombreLider));
                  break;
              case 5:
                  System.out.println("Nick del seguidor: ");
                  nickSeguidor = sc.nextLine();
                  System.out.println("Nombre del lider: ");
                  nombreLider = sc.nextLine();
                  meGusta(getSeguidor(nickSeguidor), getLider(nombreLider));
                  break;
              case 6:
                  verMegustaLider();
                  break;
              case 7:
                  System.out.println("Nombre del lider: ");
                  nombreLider = sc.nextLine();
                  Lider l2 = getLider(nombreLider);
                  System.out.println("País de búsqueda: ");
                  paisSeguidor = sc.nextLine();
                  System.out.println("Fecha de inicio: ");
                  String fecha_inicio = sc.nextLine();
                  System.out.println("Fecha de fin: ");
                  String fecha_fin = sc.nextLine();
                  listarSeguidoresFromLider(l2, paisSeguidor, fecha_inicio, fecha_inicio);
                  break;
              default:
                break;
          }

        } while (option != 0);
        
    }
    
    public static Seguidor getSeguidor(String nick) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        Seguidor s = null;
        
        try {
            
          tx = session.beginTransaction();
          
          String hql = "FROM Seguidor WHERE nick = :n ";

          Query query = session.createQuery(hql);
          query.setParameter("n", nick);

          s = (Seguidor) query.list().get(0);
          
          tx.commit();
        } catch (Exception e) {
          if (tx != null)
            tx.rollback();

          e.printStackTrace();
          
        }
        
        return s;
        
    }
    
    public static Lider getLider(String nombre) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        Lider l= null;
        
        try {
            
          tx = session.beginTransaction();
          
          String hql = "FROM Lider WHERE nombre = :n ";

          Query query = session.createQuery(hql);
          query.setParameter("n", nombre);

          l = (Lider) query.list().get(0);
          
          tx.commit();
        } catch (Exception e) {
          if (tx != null)
            tx.rollback();

          e.printStackTrace();
          
        }
        
        return l;
        
    }
    
    
    public static int altaLider(Lider l) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer idLider = null;

        try {

          tx = session.beginTransaction();

          idLider = (int) session.save(l);

          tx.commit();

        } catch (Exception e) {
          if (tx != null)
            tx.rollback();

          e.printStackTrace();
        }

        return idLider;
        
    }
    
    public static int altaSeguidor(Seguidor s) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer idSeguidor = null;

        try {

          tx = session.beginTransaction();

          idSeguidor = (int) session.save(s);

          tx.commit();

        } catch (Exception e) {
          if (tx != null)
            tx.rollback();

          e.printStackTrace();
        }

        return idSeguidor;
        
    }
    
    public static int seguirLider(Seguidor s, Lider l) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer idSeguido = null;
        
        try {
            
          tx = session.beginTransaction();
          
          Seguidoreslider sl = new Seguidoreslider(new SeguidoresliderId(l.getIdLider(), s.getIdSeguidor()), l, s, LocalDate.now(), 0);
          
          idSeguido = (int) session.save(sl);
          
          tx.commit();
      
        } catch (Exception e) {
          if (tx != null)
            tx.rollback();

          e.printStackTrace();
          
        }
        
        return idSeguido;
        
    }
    
    public static boolean dejarSeguir(Seguidor s, Lider l) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Seguidoreslider> lideresSeguidos = null;
        
        try {
            
          tx = session.beginTransaction();
          
          String hql = "FROM Seguidoreslider WHERE idLider = :idL AND idSeguidor = :idS";

          Query query = session.createQuery(hql);
          query.setParameter("idL", l.getIdLider());
          query.setParameter("idS", s.getIdSeguidor());

          lideresSeguidos = query.list();
          
          if (lideresSeguidos.size() == 1) {
            Seguidoreslider sl = lideresSeguidos.get(0);
            session.delete(sl);
          }
          
          tx.commit();
          
          return true;
        } catch (Exception e) {
          if (tx != null)
            tx.rollback();

          e.printStackTrace();
          
          return false;
        }
        
    }
    
    public static boolean meGusta(Seguidor s, Lider l) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Seguidoreslider> lideresSeguidos = null;
        
        try {
            
          tx = session.beginTransaction();
          
          String hql = "FROM Seguidoreslider WHERE idLider = :idL AND idSeguidor = :idS";

          Query query = session.createQuery(hql);
          query.setParameter("idL", l.getIdLider());
          query.setParameter("idS", s.getIdSeguidor());

          lideresSeguidos = query.list();
          
          if (lideresSeguidos.size() == 1) {
            Seguidoreslider sl = lideresSeguidos.get(0);
            
            sl.setMegusta(sl.getMegusta() + 1); 
            
            session.update(sl);
            
          }
          
          tx.commit();
          
          return true;
        } catch (Exception e) {
          if (tx != null)
            tx.rollback();

          e.printStackTrace();
          
          return false;
        }
        
    }
    
    public static void verMegustaLider() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
          tx = session.beginTransaction();
          
          List<Lider> lideres = session.createQuery("FROM Lider").list();
          
          for(Lider l : lideres) {
              
              System.out.println("Lider " + l.getNombre() + ":");
              System.out.println("  Seguidores:");
              
              String hql = "FROM Seguidoreslider WHERE idLider = :idL";
              Query query = session.createQuery(hql);
              query.setParameter("idL", l.getIdLider());
              
              List<Seguidoreslider> seguidoreslider = query.list();
              
              Seguidoreslider maxMG = seguidoreslider.get(0);
              Seguidoreslider minMG = seguidoreslider.get(0);
              
              List<Seguidoreslider> maxMegusta = new ArrayList<Seguidoreslider>();
              List<Seguidoreslider> minMegusta = new ArrayList<Seguidoreslider>();
              
              // Ordenación y guardado de seguidores
              for(Seguidoreslider sl : seguidoreslider) {
                  if(sl.getMegusta() >= maxMG.getMegusta()) {
                      if(sl.getMegusta() > maxMG.getMegusta()) {
                          maxMG = sl;
                          maxMegusta.clear();
                          maxMegusta.add(sl);
                      } else if (sl.getMegusta() == maxMG.getMegusta()) {
                          maxMegusta.add(sl);
                      }
                  } else if (sl.getMegusta() <= maxMG.getMegusta()) {
                      if(sl.getMegusta() < maxMG.getMegusta()) {
                          minMG = sl;
                          minMegusta.clear();
                          minMegusta.add(sl);
                      } else if (sl.getMegusta() == maxMG.getMegusta()) {
                          minMegusta.add(sl);
                      }
                  }
              }
              
              System.out.println("Seguidores con más me gusta: ");
              for(Seguidoreslider sl : maxMegusta) {
                String hql2 = "FROM Seguidor WHERE idSeguidor = :idS AND idLider = :idL";
                Query query2 = session.createQuery(hql2);
                query2.setParameter("idS", sl.getSeguidor().getIdSeguidor());
                query2.setParameter("idL", sl.getLider().getIdLider());
                
                Seguidor s = (Seguidor) query2.list().get(0);
                
                System.out.println("    Nick: " + s.getNick());
                System.out.println("    Me gusta: " + sl.getMegusta());
                
              }
              
              System.out.println("Seguidores con menos me gusta: ");
              for(Seguidoreslider sl : minMegusta) {
                String hql2 = "FROM Seguidor WHERE idSeguidor = :idS AND idLider = :idL";
                Query query2 = session.createQuery(hql2);
                query2.setParameter("idS", sl.getSeguidor().getIdSeguidor());
                query2.setParameter("idL", sl.getLider().getIdLider());
                
                Seguidor s = (Seguidor) query2.list().get(0);
                
                System.out.println("    Nick: " + s.getNick());
                System.out.println("    Me gusta: " + sl.getMegusta());
                
              }
              
          }
          
          tx.commit();
        } catch (Exception e) {
          if (tx != null)
            tx.rollback();

          e.printStackTrace();
        }
        
    }
    
    public static void listarSeguidoresFromLider(Lider l, String pais, String fecha_inicio, String fecha_fin) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        List<Seguidor> seguidores = null;

        try {
          tx = session.beginTransaction();
          
          String hql = "FROM Seguidor s JOIN Seguidoreslider sl ON s.idSeguidor = sl.idSeguidor WHERE sl.idLider = :idL AND s.pais = :pais AND sl.fechaSeguido BETWEEN :fechaInit AND :fechaFin";
          Query query = session.createQuery(hql);
          
          query.setParameter("idL", l.getIdLider());          
          query.setParameter("pais", pais);          
          query.setParameter("fechaInit", fecha_inicio);          
          query.setParameter("fechaFin", fecha_fin);
          
          seguidores = query.list();
          
          System.out.println("Seguidores que cumplen con el criterio: ");
          for(Seguidor s : seguidores) {
            System.out.println(s.getNick());
          }
          
          tx.commit();
        } catch (Exception e) {
          if (tx != null)
            tx.rollback();

          e.printStackTrace();
        }
        
    }
    
    
}
