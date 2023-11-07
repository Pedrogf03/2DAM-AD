import java.sql.*;
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

    Scanner sc = new Scanner(System.in);
    int option = 0;

    int dept_no = 0;

    while (true) {
      System.out.println("Elige una opción: ");
      System.out.println("1.- Eliminar departamento y todos sus empleados.");
      System.out.println("2.- Obtener suma de los salarios de un departamento.");
      System.out.println("3.- Cambiar localización de departamento.");
      System.out.println("0.- Salir.");

      option = Integer.parseInt(sc.nextLine());

      if (option == 0) {
        break;
      }

      switch (option) {
        case 1:
          System.out.print("Código del departamento a eliminar: ");
          dept_no = Integer.parseInt(sc.nextLine());
          deleteEmpDpto(dept_no);
          break;
        case 2:
          System.out.print("Código del departamento: ");
          dept_no = Integer.parseInt(sc.nextLine());
          deptSalario(dept_no);
          break;
        case 3:
          System.out.print("Código del departamento: ");
          dept_no = Integer.parseInt(sc.nextLine());
          System.out.print("Nueva localización: ");
          String new_loc = sc.nextLine();
          updateLoc(dept_no, new_loc);
          break;
        default:
          break;
      }

    }

    sc.close();

  }

  public static void deleteEmpDpto(int dept_no) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);
      CallableStatement call = conn.prepareCall("{ call deleteEmpDpto(?) }");
      call.setInt(1, dept_no);

      call.executeUpdate();

      System.out.println("Departamento y empleados eliminados con éxito.");

      call.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.printf("Not found the jdbc driver %s\n", db_drivers);
    } catch (SQLException e) {
      System.out.println("SQL Exception.");
      e.printStackTrace();
    }

  }

  public static void deptSalario(int dept_no) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);
      CallableStatement call = conn.prepareCall("{ call deptSalario(?) }");
      call.setInt(1, dept_no);

      if (call.execute()) {
        ResultSet rs = call.getResultSet();
        if (rs.next()) {
          System.out.println("Salario: " + rs.getDouble(1));
        }
        rs.close();
      }

      call.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.printf("No se encontró el controlador jdbc %s\n", db_drivers);
    } catch (SQLException e) {
      System.out.println("Excepción SQL.");
      e.printStackTrace();
    }

  }

  public static void updateLoc(int dept_no, String new_loc) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);
      CallableStatement call = conn.prepareCall("{ call updateLoc(?, ?) }");
      call.setInt(1, dept_no);
      call.setString(2, new_loc);

      if (call.execute()) {
        ResultSet rs = call.getResultSet();
        if (rs.next()) {
          System.out.println("Antigua localización: " + rs.getString(1));
          System.out.println("Localización cambiada: " + rs.getBoolean(2));
        }
        rs.close();
      }

      call.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.printf("No se encontró el controlador jdbc %s\n", db_drivers);
    } catch (SQLException e) {
      System.out.println("Excepción SQL.");
      e.printStackTrace();
    }

  }

}
