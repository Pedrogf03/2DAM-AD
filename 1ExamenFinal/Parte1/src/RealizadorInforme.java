import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.sql.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.thoughtworks.xstream.XStream;

public class RealizadorInforme {

  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);

    System.out.print("Modulos: ");
    String modulos = sc.nextLine();
    System.out.print("Denominación: ");
    String denom = sc.nextLine();
    System.out.print("Rango de fechas: ");
    String fechas = sc.nextLine();
    System.out.println("Formato: ");
    // Escribe 1 para formato xml, 2 para formato binario.
    System.out.println("\t1.-XML");
    System.out.println("\t2.-Binario");
    int formato = Integer.parseInt(sc.nextLine());

    sc.close();

    generarInforme(modulos, denom, fechas, formato);
    //generarInforme("AD, PSP", "1 evaluacion, tema 2", "04-12-2023, 05-12-2023", 2);

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

                for (Examen e : a.examenes) {
                  for (Alumno alu : alumnos) {
                    if (alu.equals(a) && !alu.examenes.contains(e)) {
                      alu.examenes.add(e);
                    }
                  }
                }
              }

            }
          }

        }

      }

    }

    for (Alumno a : alumnos) {
      int media = 0;
      for (Examen e : a.examenes) {
        media += e.nota;
      }
      media /= a.examenes.size();
      a.media = media;
    }

    if (formato == 1) {
      exportarXML(alumnos);
    } else {
      exportarBin(alumnos);
    }

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

        String nombreAlu = datos[0].split("  ")[1] + " " + datos[0].split("  ")[0];

        Alumno a = new Alumno(nombreAlu);
        alumnos.add(a);

        a.examenes.add(new Examen(modulo, denom, fecha, Integer.parseInt(datos[1])));

      }

      return alumnos;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  public static void exportarXML(Set<Alumno> alus) {

    XStream xs = new XStream();

    xs.setMode(XStream.NO_REFERENCES);

    xs.alias("Alumnos", SortedSet.class);

    xs.useAttributeFor(Alumno.class, "nombre");
    xs.useAttributeFor(Alumno.class, "media");

    xs.addImplicitCollection(Alumno.class, "examenes");

    xs.useAttributeFor(Examen.class, "modulo");
    xs.useAttributeFor(Examen.class, "denominacion");
    xs.useAttributeFor(Examen.class, "fecha");

    try {
      FileOutputStream ficheroXML = new FileOutputStream("informe.xml");
      xs.toXML(alus, ficheroXML);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void exportarBin(Set<Alumno> alus) {

    try (FileWriter writer = new FileWriter("informe.dat")) {

      for (Alumno a : alus) {

        String line = a.nombre + ";;" + a.media + ";;";

        for (Examen e : a.examenes) {
          line = line + e.modulo + ";;" + e.denominacion + ";;" + e.fecha + ";;" + e.nota + ";;";
        }

        line = line + "\n";

        writer.write(line);

      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
