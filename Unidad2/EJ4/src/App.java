import java.sql.*;
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

    Scanner sc = new Scanner(System.in);
    int option = 0;

    String dbName = "";

    while (true) {
      System.out.println("---------------------------------------------");
      System.out.println("Elige una opción: ");
      System.out.println("1.- Comprobar Base de Datos.");
      System.out.println("2.- Mostrar info de conexión con una Base de Datos.");
      System.out.println("3.- Consultar metadatos de una Base de Datos.");
      System.out.println("4.- Obtener tablas de una Base de Datos.");
      System.out.println("0.- Salir.");
      System.out.println("---------------------------------------------");

      option = Integer.parseInt(sc.nextLine());

      if (option == 0) {
        break;
      }

      switch (option) {
        case 1:
          System.out.print("Nombre de la Base de Datos: ");
          dbName = sc.nextLine();
          checkDB(dbName);
          break;
        case 2:
          infoDB();
          break;
        case 3:
          System.out.print("Nombre de la Base de Datos: ");
          dbName = sc.nextLine();
          System.out.println("1.- Consultar tablas.");
          System.out.println("2.- Consultar vistas.");
          System.out.println("3.- Consultar ambas.");
          int tbOrVw = Integer.parseInt(sc.nextLine());
          checkTablesOrViews(dbName, tbOrVw);
          break;
        case 4:
          System.out.print("Nombre de la Base de Datos: ");
          dbName = sc.nextLine();
          System.out.print("Patrón de búsqueda: ");
          String pattern = sc.nextLine();
          selectTablesDB(dbName, pattern);
          break;
        default:
          break;
      }

    }

  }

  public static void checkDB(String dbName) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      DatabaseMetaData dbmd = conn.getMetaData();

      // Obtener todas las bases de datos.
      ResultSet rs = dbmd.getCatalogs();

      boolean dbExists = false;

      while (rs.next()) {
        String databaseName = rs.getString(1);
        if (databaseName.equals(dbName)) {
          dbExists = true;
          break;
        }
      }

      if (dbExists) {
        System.out.println("La base de datos " + dbName + " existe.");
      } else {
        System.out.println("La base de datos " + dbName + " no existe.");
      }

      rs.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.printf("No se encontró el controlador jdbc %s\n", db_drivers);
    } catch (SQLException e) {
      System.out.println("Excepción SQL.");
      e.printStackTrace();
    }

  }

  public static void infoDB() {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      DatabaseMetaData dbmd = conn.getMetaData();

      System.out.println("Información de la conexión a la base de datos:");
      System.out.println("Motor: " + dbmd.getDatabaseProductName());
      System.out.println("Driver: " + dbmd.getDriverName());
      System.out.println("URL: " + dbmd.getURL());

      String[] userMachine = dbmd.getUserName().split("@");

      for (int i = 0; i < 2; i++) {
        if (i == 0) {
          System.out.print("Usuario: ");
        } else {
          System.out.print("Máquina: ");
        }
        System.out.print(userMachine[i]);
        System.out.println();
      }

      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.printf("No se encontró el controlador jdbc %s\n", db_drivers);
    } catch (SQLException e) {
      System.out.println("Excepción SQL.");
      e.printStackTrace();
    }

  }

  public static void checkTablesOrViews(String dbName, int tbOrVw) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      DatabaseMetaData dbmd = conn.getMetaData();

      String[] types = null;

      if (tbOrVw == 1) {
        types = new String[] { "TABLE" };
      } else if (tbOrVw == 2) {
        types = new String[] { "VIEW" };
      } else if (tbOrVw == 3) {
        types = new String[] { "TABLE", "VIEW" };
      }

      ResultSet rs = dbmd.getTables(dbName, null, "%", types);

      while (rs.next()) {
        System.out.println();
        System.out.println("Nombre: " + rs.getString("TABLE_NAME"));
        System.out.println("Tipo: " + rs.getString("TABLE_TYPE"));
        System.out.println("Esquema: " + rs.getString("TABLE_SCHEM"));
      }

      rs.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.printf("No se encontró el controlador jdbc %s\n", db_drivers);
    } catch (SQLException e) {
      System.out.println("Excepción SQL.");
      e.printStackTrace();
    }

  }

  public static void selectTablesDB(String dbName, String pattern) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      DatabaseMetaData dbmd = conn.getMetaData();

      ResultSet rs = dbmd.getTables(dbName, null, pattern, new String[] { "TABLE", "VIEW" });

      while (rs.next()) {
        System.out.println(rs.getString("TABLE_TYPE") + ": " + rs.getString("TABLE_NAME"));
      }

      rs.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.printf("No se encontró el controlador jdbc %s\n", db_drivers);
    } catch (SQLException e) {
      System.out.println("Excepción SQL.");
      e.printStackTrace();
    }

  }

}
