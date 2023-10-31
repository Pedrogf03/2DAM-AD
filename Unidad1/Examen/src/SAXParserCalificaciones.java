import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Scanner;

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
    } catch (Exception e){
      e.printStackTrace();
    }

  }

  class MyHandler extends DefaultHandler {

    @Override
    public void startElement(
      String uri,
      String localName,
      String qName,
      Attributes attributes
    ) throws SAXException {

      currentElement = qName;

      if(currentElement.equals("calificaciones")) {
        
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
    int length
    ) throws SAXException {
      
      if(currentElement.equals("alumno")) {

        calificaciones.alumnos.put(currentAlumno, new String(chars, start, length));

      }

    }

    @Override
    public void endElement(
      String uri, 
      String localName, 
      String qName
      ) throws SAXException {
      currentElement = "";
    }

  }

  public static void main(String[] args) {

    File curso = new File("curso.dat");

    try {
      if(!curso.exists()) {
        curso.createNewFile();
      }
    } catch (IOException e){
      e.printStackTrace();
    }
    
    int option;
    Scanner sc = new Scanner(System.in);

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
          } catch (Exception e){
            e.printStackTrace();
          }

          break;
        case 2:
          System.out.println("Indique el nombre del alumno:");
          alu = sc.nextLine();

          try{
            consultaNotas(alu, curso);
          } catch(Exception e) {
            e.printStackTrace();
          }

          break;
        case 3:
          System.out.println("Indique el nombre del alumno:");
          alu = sc.nextLine();

          try{
            eliminarAlumno(alu, curso);
          } catch(Exception e) {
            e.printStackTrace();
          }

          break;
      
        default:
          break;
      }

    } while(option != 0);

    sc.close();

  }

  // Función que, dado un xml con el formato especificado en la hoja del examen, importa los datos al fichero curso.
  public static void importarXML(File xml, File curso) throws IOException{

    // El SAXParser devuelve objeto calificaciones.
    Calificaciones c = new SAXParserCalificaciones(xml).calificaciones;

    try(
      RandomAccessFile puntero = new RandomAccessFile(curso, "rw");
    ) {

      long pos = puntero.length(); //Tamaño del archivo.
      puntero.seek(pos); // Se coloca el puntero en la posicion especifica.

      // Se recorre el mapa de alumnos donde se almacena el nombre como clave y la nota como valor.
      for (Map.Entry<String, String> entry : c.alumnos.entrySet()) {

        // Se escribe en el archivo curso los datos.
        puntero.writeBytes(entry.getKey() + ";;" + c.nombreAsignatura + ";;" + c.tipo + ";;" + c.fecha + ";;" + entry.getValue() + ";;0\n");

      }

      System.out.println("-----Calificaciones importadas con éxito-----");

    }

  }

  // Funcion que devuelve todas las calificaciones del alumno.
  public static void consultaNotas(String alu, File curso) throws IOException{

    try(
      RandomAccessFile puntero = new RandomAccessFile(curso, "rw");
    ) {

      // Se coloca el puntero al inicio del archivo.
      puntero.seek(0);

      System.out.println("-----" + alu + "-----");

      // Por cada coincidencia con el nombre, se escriben sus datos.
      String line;
      while((line = puntero.readLine()) != null) {

        String[] partes = line.split(";;");

        if(partes[0].equals(alu) && partes[5].equals("0")) {

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

  // Funcion que elimina todos los datos de un alumno,
  public static void eliminarAlumno(String alu, File curso) throws IOException{

    try(
      RandomAccessFile puntero = new RandomAccessFile(curso, "rw");
    ) {

      // Se coloca el puntero al inicio.
      long posicionActual = 0;
      puntero.seek(posicionActual);

      String line;
      while((line = puntero.readLine()) != null) {

        String[] partes = line.split(";;");

        // Por cada coincidencia con el nombre.
        if(partes[0].equals(alu) && partes[5].equals("0")) {
          
          // Se borra lógicamente.
          partes[5] = "1";
          line = String.join(";;", partes);
          puntero.seek(posicionActual);

          puntero.writeBytes(line);

        }

        posicionActual = puntero.getFilePointer(); // Se recoge la posicion del puntero.

      }

    }  

  }

}
