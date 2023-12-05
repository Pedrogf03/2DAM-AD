import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

  // ---------------------- SQLite ----------------------
  // static String driver = "org.sqlite.JDBC";
  // static String url = "jdbc:sqlite:C:/Users/bleik/Desktop/GonzalezFernandez/calificaciones.db";

  // ---------------------- MySQL -----------------------
  static String driver = "com.mysql.cj.jdbc.Driver";
  static String url = "jdbc:mysql://localhost/rutas";

  // ---------------- Parámetros de BBDD ----------------
  static String db_drivers = driver;
  static String db_user = "root";
  static String db_passwd = "123456";

  public static void main(String[] args) throws Exception {

    Scanner sc = new Scanner(System.in);
    int option = 0;

    while (true) {

      System.out.println("Elige una opcion: ");
      System.out.println("1.- Alta alumno.");
      System.out.println("2.- Alta examen.");
      System.out.println("3.- Alta programa.");
      System.out.println("4.- Baja programa.");
      System.out.println("5.- Actualizar puntuacion examen.");
      System.out.println("6.- Consulta notas.");

      try {
        option = Integer.parseInt(sc.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Error: Opción no válida.");
        break;
      }

      if (option == 0) {
        break;
      }

      int codigo = 0;
      switch (option) {
        case 1:
          System.out.print("Nombre: ");
          String nombre = sc.nextLine();
          System.out.print("Expediente: ");
          int exp = 0;
          try {
            exp = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          altaAlumno(nombre, exp);
          break;
        case 2:
          System.out.print("Codigo del examen: ");
          int codigoExamen = 0;
          try {
            codigoExamen = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          System.out.print("Titulo: ");
          String titulo = sc.nextLine();
          System.out.print("Modulo: ");
          String modulo = sc.nextLine();
          System.out.println("Nº Preguntas: ");
          int preguntas = 0;
          try {
            preguntas = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          System.out.print("Fecha del examen: ");
          String fecha = sc.nextLine();
          altaExamen(codigoExamen, titulo, modulo, preguntas, fecha);
          break;
        case 3:
          System.out.println("Codigo:");

          try {
            codigo = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          System.out.println("Ruta:");
          String ruta = sc.nextLine();

          altaPrograma(codigo, ruta);
          break;
        case 4:
          System.out.println("Codigo:");

          try {
            codigo = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          bajaPrograma(codigo);
          break;
        case 5:
          System.out.println("Codigo del examen: ");
          try {
            codigo = Integer.parseInt(sc.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Error: Opción no válida.");
            break;
          }
          actualizarPuntuacionExamen(codigo);
          break;
        case 6:
          System.out.println("Nombre del modulo: ");
          String nombreMod = sc.nextLine();
          System.out.println("Rango de fechas: ");
          String fechas = sc.nextLine();
          consulta(nombreMod, fechas.split(", "));
          break;

        default:
          break;
      }
    }

    sc.close();

  }

  public static void altaAlumno(String nombreAlu, int expAlu) {

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement("INSERT INTO Alumno (expediente, nombre) VALUES (?,?)");

      ps.setInt(1, expAlu);
      ps.setString(2, nombreAlu);

      int rows = ps.executeUpdate();

      if (rows > 0) {
        System.out.println("Registro insertado con éxito.");
      } else {
        System.out.println("No se ha podido insertar el registro.");
      }

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static void altaExamen(int codigo, String titulo, String modulo, int preguntas, String fecha) {

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement(
          "INSERT INTO Examen (numeroExamen, denominacion, enunciado, fecha, trimestral, codigoModulo) VALUES (?,?,?,?,?,?)");

      ps.setInt(1, codigo);
      ps.setString(2, titulo);
      ps.setString(3, modulo);
      ps.setInt(4, preguntas);
      ps.setString(5, fecha);

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

  public static void altaPrograma(int codigo, String ruta) {

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement("INSERT INTO Programa (codigo, ruta) VALUES (?,?)");

      ps.setInt(1, codigo);
      ps.setString(2, ruta);

      int rows = ps.executeUpdate();

      if (rows > 0) {
        System.out.println("Registro insertado con éxito.");
      } else {
        System.out.println("No se ha podido insertar el registro.");
      }

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static void bajaPrograma(int codigo) {
    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement("DELETE FROM Programa WHERE codigo = ?");

      ps.setInt(1, codigo);

      int rows = ps.executeUpdate();

      if (rows > 0) {
        System.out.println("Registro borrado con éxito.");
      } else {
        System.out.println("No se ha podido insertar el registro.");
      }

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void actualizarPuntuacionExamen(int codigoExamen) {
    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement(
          "SELECT a.expediente, SUM(r.puntuacion) FROM alumno a JOIN redacta r ON a.expediente = r.expediente JOIN Programa p ON r.codigo = p.codigo JOIN Corresponde c ON c.codigoPrograma = p.codigo WHERE c.codigoExamen = ? GROUP BY a.expediente");

      ps.setInt(1, codigoExamen);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        int exp = rs.getInt(1);
        int suma = rs.getInt(2);

        DatabaseMetaData dbms = conn.getMetaData();

        ResultSet rs2 = dbms.getProcedures(null, null, "actualizarExamenAlumno");

        if (rs2.next()) {

          CallableStatement call = conn.prepareCall("{call actualizarExamenAlumno(?,?,?)}");

          call.setInt(1, exp);
          call.setInt(2, codigoExamen);
          call.setInt(3, suma);

          int rows = call.executeUpdate();

          if (rows > 0) {
            System.out.println("Registro insertado con éxito.");
          } else {
            System.out.println("No se ha podido insertar el registro.");
          }

        } else {

          ps = conn.prepareStatement("INSERT INTO Realiza VALUES (?, ?, ?)");

          ps.setInt(1, exp);
          ps.setInt(2, codigoExamen);
          ps.setInt(3, suma);

          int rows = ps.executeUpdate();

          if (rows > 0) {
            System.out.println("Registro insertado con éxito.");
          } else {
            System.out.println("No se ha podido insertar el registro.");
          }

        }

      }

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void consulta(String nombreMod, String[] fechas) {

    try {

      // Cargar los drives en RAM.
      Class.forName(db_drivers);

      // Conexión a la BBDD
      Connection conn = DriverManager.getConnection(url, db_user, db_passwd);

      PreparedStatement ps = conn.prepareStatement(
          "SELECT a.expediente, a.nombre, AVG(r.puntuacion) FROM alumno a JOIN realiza r ON a.expediente = r.expediente JOIN examen e ON e.codigo = r.codigo WHERE e.fecha BETWEEN 1 AND 2 AND e.modulo = 1 GROUP BY a.nombre, a.expediente ORDER BY a.nombre ASC");

      ps.setString(1, fechas[0]);
      ps.setString(2, fechas[1]);
      ps.setString(3, nombreMod);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        int exp = rs.getInt(1);
        String nombre = rs.getString(2);
        int media = rs.getInt(3);

        System.out.println("Media de " + nombre + " en " + nombreMod + ": " + media);

        ps = conn.prepareStatement(
            "SELECT e.titulo, r.puntuacion FROM examen e JOIN realiza r ON e.codigo = r.codigo WHERE r.expediente = ? ORDER BY e.fecha ASC");

        ps.setInt(1, exp);

        ResultSet rs2 = ps.executeQuery();

        while (rs2.next()) {

          String titulo = rs.getString(1);
          int nota = rs.getInt(2);

          System.out.println(titulo + ": " + nota);
        }

      }

    } catch (ClassNotFoundException e) {
      System.out.println("No se han encontrado los drivers " + db_drivers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
