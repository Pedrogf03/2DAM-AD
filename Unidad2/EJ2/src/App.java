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

    int option1 = 0;
    int option2 = 0;
    String tabla = "";
    Scanner sc = new Scanner(System.in);

    do {

      System.out.println("Selecciona la tabla: ");
      System.out.println("1.- Departamentos.");
      System.out.println("2.- Empleados.");
      System.out.println("0.- Cerrar.");

      option1 = Integer.parseInt(sc.nextLine());

      if (option1 == 1) {
        tabla = "DEPARTAMENTOS";
      } else if (option1 == 2) {
        tabla = "EMPLEADOS";
      }

      while (!tabla.equals("")) {

        System.out.println("Selecciona la operación que quiere hacer con la tabla " + tabla);
        System.out.println("1.- Realizar alta.");
        System.out.println("2.- Realizar baja.");
        System.out.println("3.- Actualizar registros.");
        System.out.println("4.- Consultas.");

        switch (option2) {
          case 1:

            break;
          case 2:

            break;
          case 3:

            break;
          case 4:

            break;
          default:
            break;
        }

      }

    } while (option1 != 0);

    sc.close();

  }
}
