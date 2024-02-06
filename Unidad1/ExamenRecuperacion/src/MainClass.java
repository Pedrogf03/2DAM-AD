import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

// Clase main, donde se ejecuta el código

public class MainClass {

  public static void main(String[] args) {

    // Obtengo las listas de vehiculos y conceptos de los dos parsers
    ArrayList<Vehiculo> vehiculos = new SAXParserVehiculos().vehiculos;
    ArrayList<Concepto> conceptos = new SAXParserConceptos().conceptos;

    ArrayList<Presupuesto> presupuestos = new ArrayList<>(); // Lista de presupuestos que se va a serializar a presupuestos.xml

    // Ficheros de entrada de datos
    File f1 = new File("12345678A.txt");
    File f2 = new File("11223344Z.txt");

    // Se obtienen presupuestos que se añaden a la lista
    presupuestos.add(crearPresupuesto(f1, vehiculos, conceptos));
    presupuestos.add(crearPresupuesto(f2, vehiculos, conceptos));

    // Serializar la lista de presupuestos
    serializarAXml(presupuestos);

  }

  /**
   * @param f fichero de entrada de datos
   * @param vehiculos lista de vehiculos
   * @param conceptos lista de conceptos
   * @return un presupuesto
   * 
   * 
   * Funcion que dado un fichero de entrada, lee los datos y crea un presupuesto
   * 
   */
  public static Presupuesto crearPresupuesto(File f, ArrayList<Vehiculo> vehiculos, ArrayList<Concepto> conceptos) {

    Presupuesto p = new Presupuesto();

    String dni = f.getName().substring(0, f.getName().lastIndexOf("."));

    for (Vehiculo v : vehiculos) {
      if (v.propietario.dni.equals(dni)) {
        p.matricula = v.matricula;
      }
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(f))) {

      String line;
      while ((line = reader.readLine()) != null) {

        String[] partes = line.split(", ");

        for (Concepto c : conceptos) {

          int precio = 0;

          if (partes[0].equals(c.nombre)) {

            switch (partes[1]) {
              case "leve":
                precio = c.leve;
                break;
              case "medio":
                precio = c.medio;
                break;
              case "grave":
                precio = c.grave;
                break;

              default:
                break;
            }

            if (partes[0].equals("motor")) {
              p.motor = precio;
            } else if (partes[0].equals("frenos")) {
              p.frenos = precio;
            }

          }

        }

      }

      p.setTotal();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return p;

  }

  /**
   * @param presupuestos lista de presupuestos
   * 
   *  Funcion para serializar una lista de presupuestos a un fichero xml
   * 
   */
  public static void serializarAXml(ArrayList<Presupuesto> presupuestos) {

    XStream xstream = new XStream();

    xstream.alias("presupuestos", List.class);
    xstream.alias("presupuesto", Presupuesto.class);

    xstream.useAttributeFor(Presupuesto.class, "matricula");

    try {
      FileOutputStream ficheroXML = new FileOutputStream("presupuestos.xml");
      xstream.toXML(presupuestos, ficheroXML);
    } catch (Exception e) {
      System.out.println(e.toString());
    }

  }

}
