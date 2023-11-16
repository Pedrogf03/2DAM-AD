import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;

public class App {

  // ---------------------- SQLite ----------------------
  // static String driver = "org.sqlite.JDBC";
  // static String url = "jdbc:sqlite:C:/Users/bleik/Desktop/GonzalezFernandez/calificaciones.db";

  // ---------------------- MySQL -----------------------
  static String driver = "com.mysql.cj.jdbc.Driver";
  static String url = "jdbc:mysql://localhost/calificaciones";

  // ---------------- Parámetros de BBDD ----------------
  static String db_drivers = driver;
  static String db_user = "root";
  static String db_passwd = "123456";

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int option = 0;

    while (true) {

      System.out.println("Elige una opcion: ");
      System.out.println("1.- Alta módulo.");
      System.out.println("2.- Alta examen.");
      System.out.println("3.- Alta alumno y matricular.");
      System.out.println("4.- Baja alumno.");
      System.out.println("5.- Calificar examen alumno.");
      System.out.println("6.- Consulta notas."); // TODO

      try {
        option = Integer.parseInt(sc.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Error: Opción no válida.");
        break;
      }

      switch (option) {
        case 1:
          System.out.print("Ciclo: ");
          String ciclo = sc.nextLine();
          System.out.print("Nombre: ");
          String nombre = sc.nextLine();
          System.out.print("Codigo: ");
          int codigoModulo = 0;
          try {
            codigoModulo = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          altaModulo(ciclo, nombre, codigoModulo);
          break;
        case 2:
          System.out.print("Numero del examen: ");
          int numExamen = 0;
          try {
            numExamen = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          System.out.print("Denominacion: ");
          String denominacion = sc.nextLine();
          System.out.print("Enunciado: ");
          String enunciado = sc.nextLine();
          System.out.print("Fecha (YYYY-MM-DD): ");
          String fecha = sc.nextLine();
          System.out.println("Trimestral (Y/N): ");
          String trimestral = sc.nextLine();
          System.out.print("Codigo del modulo: ");
          int codigoMod = 0;
          try {
            codigoModulo = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          altaExamen(numExamen, denominacion, enunciado, fecha, trimestral, codigoMod);
          break;
        case 3:
          System.out.println("Nombre del alumno:");
          String nombreAlumno = sc.nextLine();
          System.out.println("Expediente del alumno:");
          int expAlu = 0;
          try {
            expAlu = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          System.out.println("Modulo en el que se quiere matricular: ");
          String modulo = sc.nextLine();
          altaAlumno(nombreAlumno, expAlu, modulo);
          break;
        case 4:
          System.out.println("Expediente del alumno: ");
          int expAluDel = 0;
          try {
            expAluDel = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          deleteAlumno(expAluDel);
          break;
        case 5:
          System.out.println("Numero del examen: ");
          int numExam = 0;
          try {
            numExam = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          calificarAlumnos(numExam, sc);
          break;
        case 6:

          break;

        default:
          break;
      }

    }

    sc.close();

  }

  public static void altaModulo(String ciclo, String nombre, int codigoModulo) {

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement("INSERT INTO Modulo (ciclo, nombre, codigoModulo) VALUES (?,?,?)");

      ps.setString(1, ciclo);
      ps.setString(2, nombre);
      ps.setInt(3, codigoModulo);

      int rows = ps.executeUpdate();

      if (rows > 0) {
        System.out.println("Registro insertado con éxito.");
      } else {
        System.out.println("No se ha podido insertar el registro.");
      }

      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static void altaExamen(int numExamen, String denominacion, String enunciado, String fecha, String trimestral,
      int codigoMod) {

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement(
          "INSERT INTO Examen (numeroExamen, denominacion, enunciado, fecha, trimestral, codigoModulo) VALUES (?,?,?,?,?,?)");

      ps.setInt(1, numExamen);
      ps.setString(2, denominacion);
      ps.setString(3, enunciado);
      ps.setString(4, fecha);
      if (trimestral.equals("Y")) {
        ps.setBoolean(5, true);
      } else {
        ps.setBoolean(5, false);
      }
      ps.setInt(6, codigoMod);

      int rows = ps.executeUpdate();

      if (rows > 0) {
        System.out.println("Registro insertado con éxito.");
      } else {
        System.out.println("No se ha podido insertar el registro.");
      }

      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static void altaAlumno(String nombreAlu, int expAlu, String nombreMod) {

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement("INSERT INTO Alumno (expedienteAlu, nombre) VALUES (?,?)");

      ps.setInt(1, expAlu);
      ps.setString(2, nombreAlu);

      int rows = ps.executeUpdate();

      if (rows > 0) {
        System.out.println("Registro insertado con éxito.");
      } else {
        System.out.println("No se ha podido insertar el registro.");
      }

      DatabaseMetaData dbms = conn.getMetaData();

      ResultSet rs = dbms.getProcedures(null, null, "matricular");

      if (rs.next()) {

        PreparedStatement ps2 = conn.prepareStatement("SELECT codigoModulo FROM Modulo WHERE nombre = ?");

        ps2.setString(1, nombreMod);

        ResultSet rs2 = ps2.executeQuery();

        int codigoModulo = 0;

        if (rs2.next()) {
          codigoModulo = rs2.getInt(1);
        }

        CallableStatement call = conn.prepareCall("{call matricular(?,?)}");

        call.setInt(1, expAlu);
        call.setInt(2, codigoModulo);

        call.executeUpdate();

        System.out.println("Alumno matriculado con éxito.");

        call.close();
        rs2.close();

      }

      rs.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static void deleteAlumno(int expAlu) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement("DELETE FROM matriculado WHERE expedienteAlu = ?");
      PreparedStatement ps2 = conn.prepareStatement("DELETE FROM Alumno WHERE expedienteAlu = ?");

      ps.setInt(1, expAlu);
      ps2.setInt(1, expAlu);

      int rows = ps.executeUpdate();

      if (rows != 0) {
        System.out.println("No se ha podido eliminar el registro.");
        return;
      }

      rows = ps2.executeUpdate();

      if (rows > 0) {
        System.out.println("Registro eliminado con éxito.");
      } else {
        System.out.println("No se ha podido eliminar el registro.");
      }

      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static void calificarAlumnos(int numExam, Scanner sc) {

    try {

      Class.forName(db_drivers);

      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement(
          "SELECT m.nombre FROM Modulo m JOIN examen e ON m.codigoModulo = e.codigoModulo WHERE e.numeroExamen = ?");

      ps.setInt(1, numExam);

      ResultSet rs = ps.executeQuery();

      String nombreModulo = "";

      if (rs.next()) {
        nombreModulo = rs.getString(1);
      }

      PreparedStatement ps2 = conn.prepareStatement(
          "SELECT a.expedienteAlu FROM Alumno a JOIN matriculado m ON a.expedienteAlu = m.expedienteAlu JOIN modulo mm ON m.codigoModulo = mm.codigoModulo WHERE mm.nombre = ?");

      ps2.setString(1, nombreModulo);

      ResultSet rs2 = ps2.executeQuery();

      if (rs2.next()) {

        PreparedStatement ps3 = conn
            .prepareStatement("INSERT INTO realiza (expedienteAlu, calificacion, numeroExamen) VALUES (?, ?, ?)");

        System.out.println("Calificacion del alumno " + rs2.getInt(1));
        int nota = Integer.parseInt(sc.nextLine());

        while (nota > 10 || nota < 0) {
          System.out.println("La nota ha de estar comprendida entre 0 y 10");
          nota = Integer.parseInt(sc.nextLine());
        }

        ps3.setInt(1, rs2.getInt(1));
        ps3.setInt(2, nota);
        ps3.setInt(3, numExam);

        ps3.executeUpdate();

      }

      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
