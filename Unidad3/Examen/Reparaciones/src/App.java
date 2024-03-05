import hibernate.Cliente;
import hibernate.Concepto;
import hibernate.Incluye;
import hibernate.IncluyeId;
import hibernate.Reparacion;
import hibernate.Vehiculo;
import hibernate.util.HibernateUtil;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class App {

  public static void main(String[] args) {

    int option = 0;
    Scanner sc = new Scanner(System.in);

    do {
      System.out.println("-----------------------------------");
      System.out.println("Elige una opción:");
      System.out.println("1.- Realizar alta de un concepto.");
      System.out.println("2.- Realizar alta de un vehiculo.");
      System.out.println("3.- Realizar alta de una reaparacion.");
      System.out.println("4.- Reporte de reparaciones.");
      System.out.println("5.- Reporte de conceptos.");
      System.out.println("-----------------------------------");

      option = Integer.parseInt(sc.nextLine());

      switch (option) {
        case 1:
          System.out.print("Codigo: ");
          String cod = sc.nextLine();
          System.out.print("Denominacion: ");
          String denom = sc.nextLine();
          System.out.print("Precio: ");
          Double precio = Double.parseDouble(sc.nextLine());
          altaConcepto(new Concepto(cod, denom, precio));
          break;
        case 2:
          System.out.print("Matricula: ");
          String mat = sc.nextLine();
          System.out.print("Marca: ");
          String marca = sc.nextLine();
          System.out.print("Modelo: ");
          String model = sc.nextLine();
          System.out.print("Nombre del propietario: ");
          String name = sc.nextLine();
          System.out.print("Numero de cliente del propietario: ");
          int numCliente = Integer.parseInt(sc.nextLine());
          altaVehiculo(new Vehiculo(mat, new Cliente(numCliente, name), marca, model));
          break;
        case 3:
          System.out.print("Codigo ");
          String codRep = sc.nextLine();
          System.out.println("Fecha: ");
          String date = sc.nextLine();
          altaReparacion(new Reparacion(codRep, Date.valueOf(date)), getConceptos());
          break;
        case 4:
          System.out.print("Matricula: ");
          consultaPropietarioyReparaciones(sc.nextLine());
          break;
        case 5:
          reporteConceptos();
          break;
        default:
          break;
      }

    } while (option != 0);

  }

  public static String altaConcepto(Concepto c) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    String codigo = null;

    try {

      tx = session.beginTransaction();

      codigo = (String) session.save(c);

      tx.commit();

    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

    return codigo;

  }

  public static String altaVehiculo(Vehiculo v) {

    Cliente c = existeCliente(v.getPropietario());

    if (c == null) {
      altaCliente(v.getPropietario());
    } else {
      v.setPropietario(c);
    }

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    String matricula = null;

    try {

      tx = session.beginTransaction();

      matricula = (String) session.save(v);

      tx.commit();

    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

    return matricula;

  }

  public static Cliente existeCliente(Cliente c) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;

    List<Cliente> clientes = null;

    try {
      tx = session.beginTransaction();

      String hql = "FROM Cliente WHERE numCliente = :num";

      Query query = session.createQuery(hql);
      query.setParameter("num", c.getNumCliente());

      clientes = query.list();

      tx.commit();
    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

    if (clientes.size() == 1) {
      return clientes.get(0);
    } else {
      return null;
    }

  }

  public static int altaCliente(Cliente c) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    Integer numCliente = null;

    try {

      tx = session.beginTransaction();

      numCliente = (int) session.save(c);

      tx.commit();

    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

    return numCliente;

  }

  public static String altaReparacion(Reparacion r, List<Concepto> conceptos) {

    for (Concepto c : conceptos) {

      c = existeConcepto(c);
      if (c == null) {
        altaConcepto(c);
      }

    }

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    String codigo = null;

    try {

      tx = session.beginTransaction();

      codigo = (String) session.save(r);

      tx.commit();

      double precioTotal = 0.00;

      Scanner sc = new Scanner(System.in);
      for (Concepto c : conceptos) {
        System.out.println("Precio de la reparación del " + c.getDenominacion());
        int precio = sc.nextInt();

        while (precioValido(precio, c.getPrecioReferencia())) {
          precio = sc.nextInt();
        }

        session.save(new Incluye(new IncluyeId(c.getCodigo(), r.getCodigo()), c, r, precio));
        precioTotal += precio;

      }

      updateReparacion(r.getCodigo(), precioTotal);

      tx.commit();

    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

    return codigo;

  }

  public static boolean precioValido(double precio, double ref) {

    double cantidad = ref * 0.25;

    if (precio > (ref + cantidad) || precio < (ref - cantidad))
      return false;

    return true;

  }

  public static void updateReparacion(String codigo, double precio) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();

      Reparacion r = (Reparacion) session.get(Reparacion.class, codigo);
      r.setPrecio(precio);

      tx.commit();
    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

  }

  public static Concepto existeConcepto(Concepto c) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;

    List<Concepto> conceptos = null;

    try {
      tx = session.beginTransaction();

      String hql = "FROM Concepto WHERE codigo = :cod";

      Query query = session.createQuery(hql);
      query.setParameter("cod", c.getCodigo());

      conceptos = query.list();

      tx.commit();
    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

    if (conceptos.size() == 1) {
      return conceptos.get(0);
    } else {
      return null;
    }

  }

  public static void consultaPropietarioyReparaciones(String matricula) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();

      String hql = "FROM Vehiculo WHERE matricula = :mat";

      Query query = session.createQuery(hql);
      query.setParameter("mat", matricula);

      Vehiculo v = (Vehiculo) query.list().get(0);

      System.out.println("Propietario: ");
      System.out.println("  Numero de cliente: " + v.getPropietario().getNumCliente());
      System.out.println("  Nombre: " + v.getPropietario().getNombre());
      System.out.println("Reparaciones: ");
      for (Reparacion r : v.getReparaciones()) {
        System.out.println("  Coste: " + r.getPrecio());
        System.out.println("  Coste: " + r.getPrecio());
        System.out.println("  Conceptos: ");
        for (Concepto c : getConceptosFromReparacion(r)) {
          System.out.println("  Denominacion: " + c.getDenominacion());
          System.out.println("  Coste: " + c.getPrecioReferencia());
        }
      }

      tx.commit();
    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

  }

  public static List<Concepto> getConceptosFromReparacion(Reparacion r) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;

    List<Concepto> con = null;

    try {
      tx = session.beginTransaction();

      String hql = "FROM Concepto c JOIN Incluye i ON c.codigo = i.id.codigoConcepto WHERE i.id.codigoReparacion = :rep";

      Query query = session.createQuery(hql);
      query.setParameter("red", r.getCodigo());

      con = query.list();

      tx.commit();
    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

    return con;

  }

  public static List<Concepto> getConceptos() {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;

    List<Concepto> con = null;

    try {
      tx = session.beginTransaction();

      String hql = "FROM Concepto c";

      Query query = session.createQuery(hql);

      con = query.list();

      tx.commit();
    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

    return con;

  }

  public static void reporteConceptos() {

    List<Concepto> con = getConceptos();

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();

      for (Concepto c : con) {

        String hql = "Select count(i.id.codigoReparacion), sum(i.precio) as cuantia FROM Incluye i WHERE i.id.codigoConcepto = :cod ORDER BY cuantia";

        Query query = session.createQuery(hql);
        query.setParameter("cod", c.getCodigo());
        List<Object[]> listResult = query.list();

        System.out.println("Concepto: " + c.getDenominacion());
        for (Object[] aRow : listResult) {

          System.out.println(" Objeto de reparacion en " + aRow[0] + " veces");
          System.out.println(" Cuantía ingresada: " + aRow[1] + "€");

        }

      }

      tx.commit();
    } catch (Exception e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    }

  }

}