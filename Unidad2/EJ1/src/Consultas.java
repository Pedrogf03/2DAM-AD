import java.sql.*;

public class Consultas {

  // ---------------------- SQLite ----------------------
  static String driver = "org.sqlite.JDBC";
  static String url = "jdbc:sqlite:C:/Users/bleik/Desktop/sqlite/ejemplo.db";

  // ---------------------- MySQL -----------------------
  // static String driver = "com.mysql.cj.jdbc.Driver";
  // static String url = "jdbc:mysql://localhost/empresa";

  // ---------------- Parámetros de BBDD ----------------
  static String db_drivers = driver;
  static String db_user = "root";
  static String db_passwd = "123456";

  public static void main(String[] args) {

    System.out.println("1-. APELLIDO, OFICIO y SALARIO de los empleados del departamento 10: ");
    consulta1();
    System.out.println();

    System.out.println("2-. APELLIDO y SALARIO del empleado con mayor salario: ");
    consulta2();
    System.out.println();

    System.out.println("3-. Nombres y Localización de los departamentos (ordenados alfabéticamente) y, para cada departamento,\r\n" + //
        "    APELLIDO y OFICIO de los empleados que trabajan en cada uno de ellos (ordenados también alfabéticamente): ");
    consulta3();
    System.out.println();

    System.out.println("4-. Para cada NOMBRE de departamento el salario medio de sus empleados\r\n" + //
        "    y el SALARIO del empleado con mayor salario: ");
    consulta4();
    System.out.println();

    System.out.println("5-. Para cada NOMBRE de departamento el salario medio de sus empleados\r\n" + //
        "    y el APELLIDO y SALARIO del empleado con mayor salario");
    consulta5();

  }

  // ---------- Ejercicio 1 ----------
  public static void consulta1() {

    // Consulta que se quiere hacer.
    String consulta = "SELECT apellido, oficio, salario FROM empleados WHERE dept_no = 10";

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      // Se ejecuta la consulta.
      ResultSet result = conn.createStatement().executeQuery(consulta);

      System.out.printf("%-10s %-10s %-5s\n", "APELLIDO", "OFICIO", "SALARIO");
      System.out.println("-----------------------------");
      while (result.next()) {
        System.out.printf("%-10s %-10s %-5d\n", result.getString(1), result.getString(2), result.getInt(3));
      }

      result.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  // ---------- Ejercicio 2 ----------
  public static void consulta2() {

    // Consulta que se quiere hacer.
    String consulta = "SELECT apellido, salario FROM empleados WHERE salario = (SELECT MAX(salario) from empleados)";

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      // Se ejecuta la consulta.
      ResultSet result = conn.createStatement().executeQuery(consulta);

      System.out.printf("%-10s %-5s\n", "APELLIDO", "SALARIO");
      System.out.println("------------------");
      while (result.next()) {
        System.out.printf("%-10s %-5d\n", result.getString(1), result.getInt(2));
      }

      result.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  // ---------- Ejercicio 3 ----------
  public static void consulta3() {

    // Consulta que se quiere hacer.
    String consulta = "SELECT dnombre, loc, dept_no FROM departamentos ORDER BY dnombre ASC";

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      // Se ejecuta la consulta.
      ResultSet result = conn.createStatement().executeQuery(consulta);

      System.out.printf("%-15s %-10s\n", "NOMBRE", "LOCALIZACION");
      System.out.println("----------------------------");
      while (result.next()) {
        System.out.printf("%-15s %-10s:\n", result.getString(1), result.getString(2));

        consulta = "SELECT apellido, oficio FROM empleados WHERE dept_no = " + result.getInt(3) + " ORDER BY apellido ASC";
        ResultSet result2 = conn.createStatement().executeQuery(consulta);

        while (result2.next()) {
          System.out.printf("-%-10s %-10s\n", result2.getString(1), result2.getString(2));
        }

      }

      result.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  // ---------- Ejercicio 4 ----------
  public static void consulta4() {

    // Consulta que se quiere hacer.
    String consulta = "SELECT dnombre, dept_no FROM departamentos";

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      // Se ejecuta la consulta.
      ResultSet result = conn.createStatement().executeQuery(consulta);

      System.out.printf("%-15s\n", "DEPARTAMENTO");
      System.out.println("----------------------------");
      while (result.next()) {
        System.out.println(result.getString(1) + ":");

        consulta = "SELECT AVG(salario) FROM empleados WHERE dept_no = " + result.getInt(2);
        ResultSet result2 = conn.createStatement().executeQuery(consulta);
        System.out.println("-Salario medio:  " + result2.getInt(1));

        consulta = "SELECT MAX(salario) FROM empleados WHERE dept_no = " + result.getInt(2);
        result2 = conn.createStatement().executeQuery(consulta);
        System.out.println("-Salario máximo: " + result2.getInt(1));

      }

      result.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  // ---------- Ejercicio 5 ----------
  public static void consulta5() {

    // Consulta que se quiere hacer.
    String consulta = "SELECT dnombre, dept_no FROM departamentos";

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      // Se ejecuta la consulta.
      ResultSet result = conn.createStatement().executeQuery(consulta);

      System.out.printf("%-15s\n", "DEPARTAMENTO");
      System.out.println("----------------------------");
      while (result.next()) {
        System.out.println(result.getString(1) + ":");

        consulta = "SELECT AVG(salario) FROM empleados WHERE dept_no = " + result.getInt(2);
        ResultSet result2 = conn.createStatement().executeQuery(consulta);
        System.out.println("-Salario medio:  " + result2.getInt(1));

        consulta = "SELECT apellido, MAX(salario) FROM empleados WHERE dept_no = " + result.getInt(2);
        result2 = conn.createStatement().executeQuery(consulta);
        System.out.println("-Salario máximo: " + result2.getString(1) + " " + result2.getInt(2));

      }

      result.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
