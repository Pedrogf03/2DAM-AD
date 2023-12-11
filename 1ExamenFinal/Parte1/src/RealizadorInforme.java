import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class RealizadorInforme {

  public static void main(String[] args) throws Exception {
    // Scanner sc = new Scanner(System.in);

    // System.out.print("Modulos: ");
    // String modulos = sc.nextLine();
    // System.out.print("Denominación: ");
    // String denom = sc.nextLine();
    // System.out.print("Rango de fechas: ");
    // String fechas = sc.nextLine();
    // System.out.println("Formato: ");
    // // Escribe 1 para formato xml, 2 para formato binario.
    // System.out.println("\t1.-XML");
    // System.out.println("\t2.-Binario");
    // int formato = Integer.parseInt(sc.nextLine());

    // sc.close();

    //generarInforme(modulos, denom, fechas, formato);
    generarInforme("AD", "1 evaluacion", "04-12-2023, 05-12-2023", 1);

  }

  public static void generarInforme(String modulos, String denom, String rangoFechas, int formato) {

    TreeSet<Alumno> alumnos = new TreeSet<>();

    String[] mods = modulos.split(", ");
    String[] denoms = denom.split(", ");
    String[] fechas = rangoFechas.split(", ");

    for (int i = 0; i < mods.length; i++) {

      File f = new File(mods[i]);
      File[] files = f.listFiles();

      for (File file : files) {
        String[] fileName = file.getName().split("-");

        String denomExamen = fileName[0];

        Date fechaExamen = Date.valueOf((fileName[3].split(".csv")[0] + "-" + fileName[2] + "-" + fileName[1]));

        String[] fecha1 = fechas[0].split("-");
        Date fechaInicio = Date.valueOf(fecha1[2] + "-" + fecha1[1] + "-" + fecha1[0]);

        String[] fecha2 = fechas[1].split("-");
        Date fechaFin = Date.valueOf(fecha2[2] + "-" + fecha2[1] + "-" + fecha2[0]);

        for (int j = 0; j < denoms.length; j++) {

          if (denomExamen.equalsIgnoreCase("examen " + denoms[j])) {
            if ((fechaExamen.after(fechaInicio) && fechaExamen.before(fechaFin) || fechaExamen.equals(fechaInicio) || fechaExamen.equals(fechaFin))) {

              for (Alumno a : importarDatos(file)) {
                if (!alumnos.contains(a)) {
                  alumnos.add(a);
                }
              }

            }
          }

        }

      }

    }

    // if (formato == 1) {
    //   exportarXML();
    // } else {
    //   exportarBin();
    // }

  }

  public static TreeSet<Alumno> importarDatos(File f) {

    TreeSet<Alumno> alumnos = new TreeSet<>();

    try (RandomAccessFile reader = new RandomAccessFile(f, "r")) {

      String nombreArchivo = f.getPath();
      String modulo = nombreArchivo.split("\\\\")[0];

      String examen = nombreArchivo.split("\\\\")[1];
      String denom = examen.split("-")[0].split(" ")[1] + " " + examen.split("-")[0].split(" ")[2];
      String fecha = examen.split("-")[1] + "-" + examen.split("-")[2] + "-" + examen.split("-")[3].split(".csv")[0];

      String line;
      while ((line = reader.readLine()) != null) {
        String[] datos = line.split(";");

        Alumno a = new Alumno(datos[0]);
        alumnos.add(a);

        a.examenes.add(new Examen(modulo, denom, fecha, Integer.parseInt(datos[1])));

      }

      return alumnos;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  public static void exportarXML() {

  }

  public static void exportarBin() {

  }

}
