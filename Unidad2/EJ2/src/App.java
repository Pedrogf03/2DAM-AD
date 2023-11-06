import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class App {

  // ---------------------- SQLite ----------------------
  // static String driver = "org.sqlite.JDBC";
  // static String url = "jdbc:sqlite:C:/Users/bleik/Desktop/sqlite/ejemplo.db";

  // ---------------------- MySQL -----------------------
  static String driver = "com.mysql.cj.jdbc.Driver";
  static String url = "jdbc:mysql://localhost/empresa";

  // ---------------- Parámetros de BBDD ----------------
  static String db_drivers = driver;
  static String db_user = "root";
  static String db_passwd = "123456";

  public static void main(String[] args) throws Exception {

    int optionTable = 0;
    int optionOp = 0;
    String tabla = "";
    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.println("----------------------------------------------------------------------");
      System.out.println("Selecciona la tabla: ");
      System.out.println("1.- Departamentos.");
      System.out.println("2.- Empleados.");
      System.out.println("0.- Cerrar.");

      optionTable = Integer.parseInt(sc.nextLine());

      if (optionTable == 0) {
        break;
      }

      if (optionTable == 1) {
        tabla = "DEPARTAMENTOS";
      } else if (optionTable == 2) {
        tabla = "EMPLEADOS";
      }

      while (true) {

        System.out.println("----------------------------------------------------------------------");
        System.out.println("Selecciona la operación que quiere hacer con la tabla " + tabla);
        System.out.println("1.- Realizar alta.");
        System.out.println("2.- Realizar baja.");
        System.out.println("3.- Actualizar registros.");
        System.out.println("4.- Consultas.");
        System.out.println("0.- Salir.");

        optionOp = Integer.parseInt(sc.nextLine());

        if (optionOp == 0) {
          tabla = "";
          break;
        }

        switch (optionOp) {
        case 1:
          insertInto(tabla, sc);
          break;
        case 2:
          System.out.println("Bajas con la tabla " + tabla); // TODO
          break;
        case 3:
          System.out.println("Actualizar la tabla " + tabla); // TODO
          break;
        case 4:
          System.out.println("Consultar la tabla " + tabla); // TODO
          break;
        default:
          break;
        }

      }

    }

  }

  public static int insertInto(String tabla, Scanner sc) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);
      PreparedStatement ps;

      int rows;

      switch (tabla) {
      case "DEPARTAMENTOS":

        ResultSet rsD = conn.createStatement().executeQuery("SELECT MAX(dept_no) FROM DEPARTAMENTOS");

        int dept_no = 0;

        if (rsD.next()) {
          dept_no = rsD.getInt(1) + 10;
        }

        rsD.close();

        ps = conn.prepareStatement("INSERT INTO DEPARTAMENTOS (dept_no, dnombre, loc) VALUES (?, ?, ?)");

        System.out.print("Nombre del departamento: ");
        String dnombre = sc.nextLine();

        System.out.print("Localizacion del departamento: ");
        String loc = sc.nextLine();

        ps.setInt(1, dept_no);
        ps.setString(2, dnombre);
        ps.setString(3, loc);

        rows = ps.executeUpdate();

        if (rows > 0) {
          System.out.println("Registro insertado con éxito.");
          return 0;
        } else {
          return 1;
        }

      case "EMPLEADOS":

        ResultSet rsE = conn.createStatement().executeQuery("SELECT MAX(emp_no) FROM EMPLEADOS");

        int emp_no = 0;

        if (rsE.next()) {
          emp_no = rsE.getInt(1) + 1;
        }

        rsE.close();

        ps = conn.prepareStatement("INSERT INTO EMPLEADOS (emp_no, apellido, oficio, dir, fecha_alt, salario, comision, dept_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        System.out.println("Apellido del empleado: ");
        String apellido = sc.nextLine();

        System.out.println("Oficio del empleado: ");
        String oficio = sc.nextLine();

        System.out.println("Dir del empleado: ");
        int dir = Integer.parseInt(sc.nextLine());

        System.out.println("Fecha de alta del empleado: ");
        String fecha_alt = sc.nextLine();

        System.out.println("Salario del empleado: ");
        double salario = Double.parseDouble(sc.nextLine());

        System.out.println("Comision del empleado: ");
        int comision = Integer.parseInt(sc.nextLine());

        System.out.println("Numero de departamento del empleado: ");
        int dept_noD = Integer.parseInt(sc.nextLine());

        ps.setInt(1, emp_no);
        ps.setString(2, apellido);
        ps.setString(3, oficio);
        ps.setInt(4, dir);
        ps.setString(5, fecha_alt);
        ps.setDouble(6, salario);
        ps.setInt(7, comision);
        ps.setInt(8, dept_noD);

        rows = ps.executeUpdate();

        if (rows > 0) {
          return 0;
        } else {
          return 1;
        }

      default:
        return -1;
      }

    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }

  }

}
