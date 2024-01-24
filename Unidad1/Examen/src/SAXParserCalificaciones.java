import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserCalificaciones {

  String currentElement;
  public Calificaciones calificaciones;
  public String currentAlumno;

  public SAXParserCalificaciones(File f) {

    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      saxParser.parse(f, new MyHandler());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  class MyHandler extends DefaultHandler {

    @Override
    public void startElement(
        String uri,
        String localName,
        String qName,
        Attributes attributes) throws SAXException {

      currentElement = qName;

      if (currentElement.equals("calificaciones")) {

        String nombreAsignatura = attributes.getValue("asignatura");
        String tipo = attributes.getValue("tipo");
        String fecha = attributes.getValue("fecha");

        calificaciones = new Calificaciones(nombreAsignatura, tipo, fecha);

      } else if (currentElement.equals("alumno")) {

        currentAlumno = attributes.getValue("nombre");

      }

    }

    @Override
    public void characters(char[] chars,
        int start,
        int length) throws SAXException {

      if (currentElement.equals("alumno")) {

        calificaciones.alumnos.put(currentAlumno, new String(chars, start, length));

      }

    }

    @Override
    public void endElement(
        String uri,
        String localName,
        String qName) throws SAXException {
      currentElement = "";
    }

  }

  public static void main(String[] args) {

    File curso = new File("curso.dat");

    try {
      if (!curso.exists()) {
        curso.createNewFile();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    int option;
    Scanner sc = new Scanner(System.in);

    Map<String, ArrayList<Long>> indice = importarIndice("curso.idx");

    do {

      System.out.println("-----Selecciona una opción-----");
      System.out.println("1.- Importar calificaciones.");
      System.out.println("2.- Consultar calificaciones alumno.");
      System.out.println("3.- Eliminar datos alumno.");
      System.out.println("0.- Cerrar.");

      option = Integer.parseInt(sc.nextLine());
      String alu = "";

      switch (option) {
        case 1:
          System.out.println("Indique el archivo xml desde el cual importar los datos.");
          File calificaciones = new File(sc.nextLine());

          try {
            importarXML(calificaciones, curso);
          } catch (Exception e) {
            e.printStackTrace();
          }

          break;
        case 2:
          System.out.println("Indique el nombre del alumno:");
          alu = sc.nextLine();

          try {
            consultaNotas(alu, curso, indice);
          } catch (Exception e) {
            e.printStackTrace();
          }

          break;
        case 3:
          System.out.println("Indique el nombre del alumno:");
          alu = sc.nextLine();

          try {
            eliminarAlumno(alu, curso, indice);
          } catch (Exception e) {
            e.printStackTrace();
          }

          break;

        default:
          break;
      }

      indice = generarIndice(curso);

    } while (option != 0);

    sc.close();

  }

  // Función que, dado un xml con el formato especificado en la hoja del examen, importa los datos al fichero curso.
  /**
   * @param xml : fichero xml con los datos a importar
   * @param curso : fichero de datos del curso
   * @throws IOException
   */
  public static void importarXML(File xml, File curso) throws IOException {

    // El SAXParser devuelve objeto calificaciones.
    Calificaciones c = new SAXParserCalificaciones(xml).calificaciones;

    try (
        RandomAccessFile puntero = new RandomAccessFile(curso, "rw");) {

      long pos = puntero.length(); //Tamaño del archivo.
      puntero.seek(pos); // Se coloca el puntero en la posicion especifica.

      // Se recorre el mapa de alumnos donde se almacena el nombre como clave y la nota como valor.
      for (Map.Entry<String, String> entry : c.alumnos.entrySet()) {

        // Se escribe en el archivo curso los datos.
        puntero.writeUTF(entry.getKey() + ";;" + c.nombreAsignatura + ";;" + c.tipo + ";;" + c.fecha + ";;"
            + entry.getValue() + ";;0");

      }

      System.out.println("-----Calificaciones importadas con éxito-----");

    }

  }

  // Función que devuelve todas las calificaciones del alumno.
  /**
   * @param alu : nombre completo del alumno
   * @param curso : fichero de datos del curso
   * @throws IOException
   */
  public static void consultaNotas(String alu, File curso, Map<String, ArrayList<Long>> indice) throws IOException {

    try (
        RandomAccessFile puntero = new RandomAccessFile(curso, "rw");) {

      for (Map.Entry<String, ArrayList<Long>> entry : indice.entrySet()) {

        if (entry.getKey().equalsIgnoreCase(alu)) {
          System.out.println("----- " + entry.getKey() + " -----");
          for (Long l : entry.getValue()) {
            puntero.seek(l);

            String line = puntero.readUTF();
            String[] partes = line.split(";;");

            System.out.println();
            System.out.println("Asignatura: " + partes[1]);
            System.out.println("Tipo: " + partes[2]);
            System.out.println("Fecha: " + partes[3]);
            System.out.println("Nota: " + partes[4]);
            System.out.println();

          }
        }

      }

    }

  }

  // Función que elimina todos los datos de un alumno.
  /**
   * @param alu : nombre completo del alumno
   * @param curso : fichero de datos del curso
   * @throws IOException
   */
  public static void eliminarAlumno(String alu, File curso, Map<String, ArrayList<Long>> indice) throws IOException {

    try (
        RandomAccessFile puntero = new RandomAccessFile(curso, "rw");) {

      for (Map.Entry<String, ArrayList<Long>> entry : indice.entrySet()) {

        if (entry.getKey().equalsIgnoreCase(alu)) {
          for (Long l : entry.getValue()) {
            puntero.seek(l);

            String line = puntero.readUTF();
            String[] partes = line.split(";;");

            partes[5] = "1";

            line = String.join(";;", partes);
            puntero.seek(l);
            puntero.writeUTF(line);

          }
        }

      }

    }

  }

  /**
   * @param cursof : fichero de datos del curso.
   * @return : el indice guardado en un mapa.
   */
  public static Map<String, ArrayList<Long>> generarIndice(File cursof) {

    Map<String, ArrayList<Long>> indice = new TreeMap<>();

    File indexf = new File("curso.idx"); // Fichero indice
    File indextmp = new File("curso.idx.tmp"); // Fichero indice temporal

    try (
        RandomAccessFile curso = new RandomAccessFile(cursof, "r");
        RandomAccessFile index = new RandomAccessFile(indextmp, "rw");) {

      curso.seek(0);
      long posicionActual = 0;

      String line;
      while (curso.getFilePointer() < curso.length()) {

        posicionActual = curso.getFilePointer();
        line = curso.readUTF();

        String[] partes = line.split(";;");

        if (partes[5].equals("0")) {

          if (indice.get(partes[0]) == null) {
            indice.put(partes[0], new ArrayList<>());
          }

          indice.get(partes[0]).add(posicionActual);

        }

      }

      String write;
      for (Map.Entry<String, ArrayList<Long>> entry : indice.entrySet()) {

        write = entry.getKey();

        for (Long l : entry.getValue()) {
          write = write + ";;" + l;
        }
        index.writeUTF(write);

      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    indexf.delete();
    indextmp.renameTo(indexf);

    return indice;

  }

  public static Map<String, ArrayList<Long>> importarIndice(String file) {

    Map<String, ArrayList<Long>> indice = new TreeMap<>();

    File f = new File(file);

    if (f.exists()) {
      try (RandomAccessFile idx = new RandomAccessFile(f, "r")) {

        idx.seek(0);

        String line;
        while (idx.getFilePointer() < idx.length()) {

          line = idx.readUTF();

          String[] partes = line.split(";;");

          indice.put(partes[0], new ArrayList<Long>());

          for (int i = 1; i < partes.length; i++) {
            indice.get(partes[0]).add(Long.parseLong(partes[i]));
          }

        }

        return indice;

      } catch (IOException e) {
        e.printStackTrace();
        return indice;
      }

    } else {

      try {
        f.createNewFile();
        return indice;
      } catch (IOException e) {
        e.printStackTrace();
        return indice;
      }

    }

  }

}
