import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class App {

  public static void main(String[] args) throws Exception {
    File f = new File("empleados.xml");

    System.out.println("Elige una opción:");
    System.out.println("1.- Realizar alta de un empleado.");
    System.out.println("2.- Realizar baja de un empleado.");
    System.out.println("3.- Modificar datos de un empleado.");
    System.out.println("4.- Realizar una consulta.");

    Scanner sc = new Scanner(System.in);
    int option = Integer.parseInt(sc.nextLine());

    int id = 0;
    String nombre = "";
    String departamento = "";
    String ciudad = "";
    int salario = 0;

    switch (option) {
      case 1:
        System.out.print("Nombre del empleado: ");
        nombre = sc.nextLine();

        System.out.print("Departamento del empleado: ");
        departamento = sc.nextLine();

        System.out.print("Ciudad del empleado: ");
        ciudad = sc.nextLine();

        System.out.print("Salario del empleado: ");
        salario = sc.nextInt();

        agregarEmpleado(f, nombre, departamento, ciudad, salario);

        break;
      case 2:
        System.out.print("ID del empleado: ");
        id = sc.nextInt();

        eliminarEmpleado(f, id);

        break;
      case 3:
        System.out.print("ID del empleado: ");
        id = Integer.parseInt(sc.nextLine());

        System.out.print("Nombre del empleado: ");
        nombre = sc.nextLine();

        System.out.print("Departamento del empleado: ");
        departamento = sc.nextLine();

        System.out.print("Ciudad del empleado: ");
        ciudad = sc.nextLine();

        System.out.print("Salario del empleado: ");
        salario = sc.nextInt();

        modificarEmpleado(f, id, nombre, departamento, ciudad, salario);

        break;
      case 4:
        System.out.print("ID del empleado: ");
        id = Integer.parseInt(sc.nextLine());

        consultarEmpleado(f, id);

        break;
      default:
        System.out.println("Debe elegir una de las opciones anteriores.");
        break;
    }

    sc.close();
  }

  private static int obtenerNuevoId(Document doc) {
    Element root = doc.getDocumentElement();
    int maxId = 0;

    NodeList empleadoNodes = root.getElementsByTagName("empleado");

    for (int i = 0; i < empleadoNodes.getLength(); i++) {
      Element empleado = (Element) empleadoNodes.item(i);
      int id = Integer.parseInt(
        empleado.getElementsByTagName("id").item(0).getTextContent()
      );
      if (id > maxId) {
        maxId = id;
      }
    }

    // El nuevo ID será el máximo encontrado + 1
    return maxId + 1;
  }

  public static int agregarEmpleado( File f, String nombre, String departamento, String ciudad, int salario) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc;

      if (f.exists()) {
        // Si el archivo existe, carga el documento XML existente
        doc = docBuilder.parse(f);
      } else {
        // Si el archivo no existe, crea un nuevo documento
        doc = docBuilder.newDocument();
        Element root = doc.createElement("empleados");
        doc.appendChild(root);
      }

      // Crea un elemento "empleado"
      Element empleado = doc.createElement("empleado");

      int id = obtenerNuevoId(doc);

      // Crea elementos hijos para id, nombre, departamento, ciudad y salario
      Element idElement = doc.createElement("id");
      idElement.appendChild(doc.createTextNode(String.valueOf(id)));
      empleado.appendChild(idElement);

      Element nombreElement = doc.createElement("nombre");
      nombreElement.appendChild(doc.createTextNode(nombre));
      empleado.appendChild(nombreElement);

      Element departamentoElement = doc.createElement("departamento");
      departamentoElement.appendChild(doc.createTextNode(departamento));
      empleado.appendChild(departamentoElement);

      Element ciudadElement = doc.createElement("ciudad");
      ciudadElement.appendChild(doc.createTextNode(ciudad));
      empleado.appendChild(ciudadElement);

      Element salarioElement = doc.createElement("salario");
      salarioElement.appendChild(doc.createTextNode(String.valueOf(salario) + "€"));
      empleado.appendChild(salarioElement);

      // Agrega el elemento "empleado" al elemento raíz
      Element root = doc.getDocumentElement();
      root.appendChild(empleado);

      // Crea una instancia de TransformerFactory para transformar el documento XML
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "no");

      // Crea una fuente de origen para el documento XML
      DOMSource source = new DOMSource(doc);

      // Crea un resultado de transmisión para la transformación
      try (FileOutputStream out = new FileOutputStream(f)) {
        StreamResult result = new StreamResult(out);

        // Realiza la transformación y escribe el XML en el archivo
        transformer.transform(source, result);
      }

      // Devuelve 0 para indicar éxito
      return 0;
    } catch (Exception e) {
      // Devuelve 1 en caso de error
      e.printStackTrace();
      return 1;
    }
  }

  public static int eliminarEmpleado(File f, int id) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(f);

      Element root = doc.getDocumentElement();
      NodeList empleadoNodes = root.getElementsByTagName("empleado");

      for (int i = 0; i < empleadoNodes.getLength(); i++) {
        Element empleado = (Element) empleadoNodes.item(i);
        int empleadoId = Integer.parseInt(
          empleado.getElementsByTagName("id").item(0).getTextContent()
        );

        if (empleadoId == id) {
          // Si se encuentra el empleado con el ID proporcionado, se elimina
          root.removeChild(empleado);

          // Crea una instancia de TransformerFactory para transformar el documento XML
          TransformerFactory transformerFactory = TransformerFactory.newInstance();
          Transformer transformer = transformerFactory.newTransformer();
          transformer.setOutputProperty(OutputKeys.INDENT, "no");

          // Crea una fuente de origen para el documento XML
          DOMSource source = new DOMSource(doc);

          // Crea un resultado de transmisión para la transformación
          try (FileOutputStream out = new FileOutputStream(f)) {
            StreamResult result = new StreamResult(out);

            // Realiza la transformación y escribe el XML en el archivo
            transformer.transform(source, result);
          }

          // Devuelve 0 para indicar éxito
          return 0;
        }
      }

      // Si no se encontró el empleado con el ID, se Devuelve -1 para indicar que no se eliminó nada
      return -1;
    } catch (Exception e) {
      // Devuelve 1 en caso de error
      e.printStackTrace();
      return 1;
    }
  }

  public static int modificarEmpleado(File f, int id, String nombre, String departamento, String ciudad, int salario) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(f);

      Element root = doc.getDocumentElement();
      NodeList empleadoNodes = root.getElementsByTagName("empleado");

      for (int i = 0; i < empleadoNodes.getLength(); i++) {
        Element empleado = (Element) empleadoNodes.item(i);
        int empleadoId = Integer.parseInt(
          empleado.getElementsByTagName("id").item(0).getTextContent()
        );

        if (empleadoId == id) {
          // Si se encuentra el empleado con el ID proporcionado, se modifican sus datos
          empleado.getElementsByTagName("nombre").item(0).setTextContent(nombre);
          empleado.getElementsByTagName("departamento").item(0).setTextContent(departamento);
          empleado.getElementsByTagName("ciudad").item(0).setTextContent(ciudad);
          empleado.getElementsByTagName("salario").item(0).setTextContent(String.valueOf(salario) + "€");

          // Crea una instancia de TransformerFactory para transformar el documento XML
          TransformerFactory transformerFactory = TransformerFactory.newInstance();
          Transformer transformer = transformerFactory.newTransformer();
          transformer.setOutputProperty(OutputKeys.INDENT, "no");

          // Crea una fuente de origen para el documento XML
          DOMSource source = new DOMSource(doc);

          // Crea un resultado de transmisión para la transformación
          try (FileOutputStream out = new FileOutputStream(f)) {
            StreamResult result = new StreamResult(out);

            // Realiza la transformación y escribe el XML en el archivo
            transformer.transform(source, result);
          }

          // Devuelve 0 para indicar éxito
          return 0;
        }
      }

      // Si no se encontró el empleado con el ID, se Devuelve -1 para indicar que no se eliminó nada
      return -1;
    } catch (Exception e) {
      // Devuelve 1 en caso de error
      e.printStackTrace();
      return 1;
    }
  }

  public static int consultarEmpleado(File f, int id) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(f);

      Element root = doc.getDocumentElement();
      NodeList empleadoNodes = root.getElementsByTagName("empleado");

      for (int i = 0; i < empleadoNodes.getLength(); i++) {
        Element empleado = (Element) empleadoNodes.item(i);
        int empleadoId = Integer.parseInt(
          empleado.getElementsByTagName("id").item(0).getTextContent()
        );

        if (empleadoId == id) {
          // Si se encuentra el empleado con el ID proporcionado, se muestran sus datos por pantalla
          System.out.println(
            "Nombre: " + empleado.getElementsByTagName("nombre").item(0).getTextContent() +
            "\nDepartamento: " + empleado.getElementsByTagName("departamento").item(0).getTextContent() +
            "\nCiudad: " + empleado.getElementsByTagName("ciudad").item(0).getTextContent() + 
            "\nSalario: " + empleado.getElementsByTagName("salario").item(0).getTextContent()
          );

          // Devuelve 0 para indicar éxito
          return 0;
        }
      }

      // Si no se encontró el empleado con el ID, se Devuelve -1 para indicar que no se eliminó nada
      return -1;
    } catch (Exception e) {
      // Devuelve 1 en caso de error
      e.printStackTrace();
      return 1;
    }
  }
}
