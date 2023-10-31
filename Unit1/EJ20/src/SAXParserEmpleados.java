import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.thoughtworks.xstream.XStream;

public class SAXParserEmpleados {

  private String currentElement;
  private Empleado e;
  private List<Empleado> empleados;

  public SAXParserEmpleados() {
    try {
      empleados = new ArrayList<>();
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();
      parser.parse(
          new File(
              "empleados.xml"),
          new MyHandler());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Empleado> getEmpleados() {
    return empleados;
  }

  class MyHandler extends DefaultHandler {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      currentElement = qName;
      if (currentElement.equals("Empleado")) {
        e = new Empleado();
      }
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
      if (currentElement.equals("id")) {
        e.setId(Integer.parseInt(new String(chars, start, length)));
      } else if (currentElement.equals("nombre")) {
        e.setNombre((new String(chars, start, length)));
      } else if (currentElement.equals("departamento")) {
        e.setDepartamento((new String(chars, start, length)));
      } else if (currentElement.equals("ciudad")) {
        e.setCiudad((new String(chars, start, length)));
      } else if (currentElement.equals("salario")) {
        e.setSalario(Integer.parseInt(new String(chars, start, length)));
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      if (qName.equals("Empleado")) {
        empleados.add(e);
      }
      currentElement = "";
    }
  }

  public static void main(String[] args) {

    List<Empleado> empleados = new SAXParserEmpleados().getEmpleados();

    int option;
    Scanner sc = new Scanner(System.in);

    do {
      System.out.println("-----------------------------------");
      System.out.println("Elige una opción:");
      System.out.println("1.- Realizar alta de un empleado.");
      System.out.println("2.- Realizar baja de un empleado.");
      System.out.println("3.- Modificar datos de un empleado.");
      System.out.println("4.- Mostrar empleados.");
      System.out.println("5.- Cerrar.");
      System.out.println("-----------------------------------");

      option = Integer.parseInt(sc.nextLine());

      int id = 0;
      String nombre = "";
      String departamento = "";
      String ciudad = "";
      int salario = 0;

      switch (option) {

        case 1:

          System.out.println("-----------------------------------");
          System.out.print("Nombre del empleado: ");
          nombre = sc.nextLine();

          System.out.print("Departamento del empleado: ");
          departamento = sc.nextLine();

          System.out.print("Ciudad del empleado: ");
          ciudad = sc.nextLine();

          System.out.print("Salario del empleado: ");
          salario = Integer.parseInt(sc.nextLine());

          empleados.add(new Empleado(generarId(empleados), nombre, departamento, ciudad, salario));

          break;
        case 2:

          System.out.println("-----------------------------------");
          System.out.println("ID del empleado: ");
          id = Integer.parseInt(sc.nextLine());

          borrarEmpleado(id, empleados);

          break;
        case 3:

          System.out.println("-----------------------------------");
          System.out.println("ID del empleado: ");
          id = Integer.parseInt(sc.nextLine());

          System.out.print("Nombre del empleado: ");
          nombre = sc.nextLine();

          System.out.print("Departamento del empleado: ");
          departamento = sc.nextLine();

          System.out.print("Ciudad del empleado: ");
          ciudad = sc.nextLine();

          System.out.print("Salario del empleado: ");
          salario = Integer.parseInt(sc.nextLine());

          modificarEmpleado(id, nombre, departamento, ciudad, salario, empleados);

          break;
        case 4:

          System.out.println("-----------------------------------");
          System.out.println("Empleados: ");
          for (Empleado e : empleados) {
            System.out.println("\t" + e);
          }

          break;

      }

    } while (option != 5);

    sc.close();

    serializarAXml(empleados);

  }

  public static int generarId(List<Empleado> empleados) {

    int maxId = 0;

    for (Empleado e : empleados) {
      if (e.getId() > maxId) {
        maxId = e.getId();
      }
    }

    return maxId + 1;

  }

  public static void borrarEmpleado(int id, List<Empleado> empleados) {

    int index = -1;
    
    for (Empleado e : empleados) {
      if (e.getId() == id) {
        index = empleados.indexOf(e);
      }
    }

    if(index != -1) {
      empleados.remove(index);
    }

  }

  public static void modificarEmpleado(int id, String nombre, String departamento, String ciudad, int salario, List<Empleado> empleados ) {
    
    for (Empleado e : empleados) {
      if (e.getId() == id) {
        e.setNombre(nombre);
        e.setDepartamento(departamento);
        e.setCiudad(ciudad);
        e.setSalario(salario);
      }
    }

  }

  public static void serializarAXml(List<Empleado> empleados) {

    XStream xstream = new XStream();

    xstream.alias("Empleado", Empleado.class);
    xstream.alias("Empleados", List.class);

    try {
      FileOutputStream ficheroXML = new FileOutputStream("empleados.xml");
      xstream.toXML(empleados, ficheroXML);
    } catch (Exception e) {
      System.out.println(e.toString());
    }

  }

}
