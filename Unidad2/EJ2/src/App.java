import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class App {

  // ---------------------- SQLite ----------------------
  // static String driver = "org.sqlite.JDBC";
  // static String url = "jdbc:sqlite:C:/Users/bleik/Desktop/sqlite/ejemplo.db";

  // // ---------------------- MySQL -----------------------
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
      // Selección de tabla.
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

        // Selección de operación sobre la tabla elegida.
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Selecciona la operación que quiere hacer con la tabla " + tabla);
        System.out.println("1.- Realizar alta.");
        System.out.println("2.- Realizar baja.");
        System.out.println("3.- Actualizar registros.");
        System.out.println("4.- Consultas.");
        System.out.println("0.- Volver.");

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
          deleteFrom(tabla, sc);
          break;
        case 3:
          update(tabla, sc);
          break;
        case 4:
          selectFrom(tabla, sc);
          break;
        default:
          break;
        }

      }

    }

  }

  // Función para insertar datos en una tabla.
  public static int insertInto(String tabla, Scanner sc) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);
      PreparedStatement ps;

      int rows;

      switch (tabla) {
      case "DEPARTAMENTOS":

        ResultSet rsD = conn.createStatement().executeQuery("SELECT MAX(dept_no) FROM departamentos");

        int dept_no = 0;

        if (rsD.next()) {
          dept_no = rsD.getInt(1) + 10;
        }

        rsD.close();

        ps = conn.prepareStatement("INSERT INTO departamentos (dept_no, dnombre, loc) VALUES (?, ?, ?)");

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

        ResultSet rsE = conn.createStatement().executeQuery("SELECT MAX(emp_no) FROM empleados");

        int emp_no = 0;

        if (rsE.next()) {
          emp_no = rsE.getInt(1) + 1;
        }

        rsE.close();

        ps = conn.prepareStatement("INSERT INTO empleados (emp_no, apellido, oficio, dir, fecha_alt, salario, comision, dept_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        System.out.println("Apellido del empleado: ");
        String apellido = sc.nextLine();

        System.out.println("Oficio del empleado: ");
        String oficio = sc.nextLine();

        System.out.println("Dir del empleado: ");
        int dir = Integer.parseInt(sc.nextLine());

        System.out.println("Fecha de alta del empleado (yyyy/mm/dd): ");
        String fecha_alt = sc.nextLine();

        System.out.println("Salario del empleado: ");
        double salario = Double.parseDouble(sc.nextLine());

        System.out.println("Comision del empleado: ");
        int comision = Integer.parseInt(sc.nextLine());

        System.out.println("Numero de departamento del empleado: ");
        int dept_noE = Integer.parseInt(sc.nextLine());

        ps.setInt(1, emp_no);
        ps.setString(2, apellido);
        ps.setString(3, oficio);
        ps.setInt(4, dir);
        ps.setString(5, fecha_alt);
        ps.setDouble(6, salario);
        ps.setInt(7, comision);
        ps.setInt(8, dept_noE);

        rows = ps.executeUpdate();

        if (rows > 0) {
          System.out.println("Registro insertado con éxito.");
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

  // Función para borrar datos de una tabla.
  public static int deleteFrom(String tabla, Scanner sc) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);
      PreparedStatement ps;

      int rows;

      switch (tabla) {
      case "DEPARTAMENTOS":

        ps = conn.prepareStatement("DELETE FROM departamentos WHERE dnombre = ? AND loc = ?;");

        System.out.print("Nombre del departamento: ");
        String dnombre = sc.nextLine();

        System.out.print("Localizacion del departamento: ");
        String loc = sc.nextLine();

        ps.setString(1, dnombre);
        ps.setString(2, loc);

        rows = ps.executeUpdate();

        if (rows > 0) {
          System.out.println("Registro eliminado con éxito.");
          return 0;
        } else {
          return 1;
        }

      case "EMPLEADOS":

        ps = conn.prepareStatement("DELETE FROM empleados WHERE emp_no = ?");

        System.out.println("ID del empleado: ");
        int emp_no = Integer.parseInt(sc.nextLine());

        ps.setInt(1, emp_no);

        rows = ps.executeUpdate();

        if (rows > 0) {
          System.out.println("Registro eliminado con éxito.");
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

  public static int update(String tabla, Scanner sc) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);
      PreparedStatement ps;

      int rows;

      switch (tabla) {
      case "DEPARTAMENTOS":

        ps = conn.prepareStatement("UPDATE departamentos SET dnombre = ?, loc = ? WHERE dnombre = ? AND loc = ?;");

        System.out.print("Nombre actual del departamento: ");
        String dnombre = sc.nextLine();
        System.out.print("Nuevo nombre del departamento: ");
        String dnombre_nuevo = sc.nextLine();

        System.out.print("Localizacion actual del departamento: ");
        String loc = sc.nextLine();
        System.out.print("Nueva localizacion del departamento: ");
        String loc_nuevo = sc.nextLine();

        ps.setString(1, dnombre_nuevo);
        ps.setString(2, loc_nuevo);
        ps.setString(3, dnombre);
        ps.setString(4, loc);

        rows = ps.executeUpdate();

        if (rows > 0) {
          System.out.println("Registro actualizado con éxito.");
          return 0;
        } else {
          return 1;
        }

      case "EMPLEADOS":

        ps = conn.prepareStatement("UPDATE empleados SET apellido = ?, oficio = ?, dir = ?, fecha_alt = ?, salario = ?, comision = ?, dept_no = ? WHERE emp_no = ?");

        System.out.println("ID del empleado: ");
        int emp_no = Integer.parseInt(sc.nextLine());

        System.out.println("Apellido del empleado: ");
        String apellido = sc.nextLine();

        System.out.println("Oficio del empleado: ");
        String oficio = sc.nextLine();

        System.out.println("Dir del empleado: ");
        int dir = Integer.parseInt(sc.nextLine());

        System.out.println("Fecha de alta del empleado (yyyy/mm/dd): ");
        String fecha_alt = sc.nextLine();

        System.out.println("Salario del empleado: ");
        double salario = Double.parseDouble(sc.nextLine());

        System.out.println("Comision del empleado: ");
        int comision = Integer.parseInt(sc.nextLine());

        System.out.println("Numero de departamento del empleado: ");
        int dept_noE = Integer.parseInt(sc.nextLine());

        ps.setString(1, apellido);
        ps.setString(2, oficio);
        ps.setInt(3, dir);
        ps.setString(4, fecha_alt);
        ps.setDouble(5, salario);
        ps.setInt(6, comision);
        ps.setInt(7, dept_noE);
        ps.setInt(8, emp_no);

        rows = ps.executeUpdate();

        if (rows > 0) {
          System.out.println("Registro actualizado con éxito.");
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

  public static void selectFrom(String tabla, Scanner sc) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      switch (tabla) {
      case "DEPARTAMENTOS":

        while (true) {

          System.out.println("----------------------------------------------------------------------");
          System.out.println("1.- Mostrar todos los departamentos.");
          System.out.println("2.- Mostrar todos los departamentos con un nombre dado.");
          System.out.println("3.- Mostrar todos los departamentos según la localización.");
          System.out.println("0.- Salir.");

          int option = Integer.parseInt(sc.nextLine());

          if (option == 0) {
            break;
          }

          PreparedStatement ps = null;

          ResultSet rs = null;

          switch (option) {
          case 1:
            rs = conn.createStatement().executeQuery("SELECT * FROM departamentos;");
            break;
          case 2:
            System.out.println("Nombre del departamento: ");
            String dnombre = sc.nextLine();
            ps = conn.prepareStatement("SELECT * FROM departamentos WHERE dnombre = ?;");
            ps.setString(1, dnombre);
            rs = ps.executeQuery();
            break;
          case 3:
            System.out.println("Localización del departamento: ");
            String loc = sc.nextLine();
            ps = conn.prepareStatement("SELECT * FROM departamentos WHERE loc = ?;");
            ps.setString(1, loc);
            rs = ps.executeQuery();
            break;
          default:
            break;
          }

          System.out.printf("%-2s %-10s %-15s\n", "ID", "NOMBRE", "LOCALIZACION");
          while (rs.next()) {
            System.out.printf("%-2d %-10s %-15s\n", rs.getInt(1), rs.getString(2), rs.getString(3));
          }

        }

      case "EMPLEADOS":

      default:
        break;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
