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
            insertInto(tabla);
            break;
          case 2:
            System.out.println("Bajas con la tabla " + tabla);
            break;
          case 3:
            System.out.println("Actualizar la tabla " + tabla);
            break;
          case 4:
            System.out.println("Consultar la tabla " + tabla);
            break;
          default:
            break;
        }

      }

    }

    sc.close();

  }

  public static int insertInto(String tabla) {

    switch (tabla) {
      case "DEPARTAMENTOS":

        //TODO: Insertar en tabla departamentos.

      case "EMPLEADOS":

        //TODO: Insertar en tabla empleados.

      default:
        return -1;
    }

  }

}
