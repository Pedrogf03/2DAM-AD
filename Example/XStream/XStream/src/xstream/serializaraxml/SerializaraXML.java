/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xstream.serializaraxml;

import com.thoughtworks.xstream.XStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miguelb
 */

public class SerializaraXML {

  //Variables de instancia de la clase SerializaraXML

  private class ColeccionPersonas {

    private List<Persona> listaPersonas;

    public ColeccionPersonas() {
      listaPersonas = new ArrayList<Persona>();
    }

    public void add(Persona persona) {
      listaPersonas.add(persona);
    }
  }

  ColeccionPersonas personas = new ColeccionPersonas();

  //-----------Ejemplos---------------------

  private void ejemploSerializarUnObjetoSimpleaXML() {
    Persona persona = new Persona("Juanito", "Perez", "Sanchez", 5);

    XStream xstream = new XStream();

    try {
      FileOutputStream ficheroXML = new FileOutputStream("XStream\\xml\\Persona.xml");
      xstream.toXML(persona, ficheroXML);

      /*Si tratamos de serializar a mismo archivo XML un segundo objeto
      Se construirá mal el archivo (el segundo objeto se añade después
      de la marca de fin de fichero)*/

      // xstream.toXML(new Persona("Maria","Lopez","Ruiz", 8),ficheroXML);

      System.out.println("Serializando objeto Persona a fichero XML");
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  private void ejemploSerializarUnObjetoSimpleConAliasaXML() {
    Persona persona1 = new Persona("Juanito", "Perez", "Sanchez", 5);

    XStream xstream = new XStream();

    //Alias de clase
    xstream.alias("Persona", Persona.class); //<---Esto es conveniente

    //Omisión de un campo en la serialización
    // xstream.omitField(NombreYApellidos.class, "nombre");

    //Ejemplo de creación de un alias para un campo
    xstream.aliasField("primerapellido", NombreYApellidos.class, "apellido1");
    xstream.aliasField("segundoapellido", NombreYApellidos.class, "apellido2");

    try {
      FileOutputStream ficheroXML = new FileOutputStream("XStream/xml/PersonaConAlias.xml");
      xstream.toXML(persona1, ficheroXML);

      System.out.println("Serializando objeto Persona con alias a fichero XML");
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  private void ejemploSerializarUnaColeccionDePersonasaXML() {

    personas.add(new Persona("Juanito", "Perez", "Sanchez", 5));
    personas.add(new Persona("Maria", "Lopez", "Ruiz", 8));

    XStream xstream = new XStream();

    xstream.alias("Persona", Persona.class);
    xstream.alias("Personas", ColeccionPersonas.class);

    try {
      FileOutputStream ficheroXML = new FileOutputStream("XStream/xml/ListaPersonas.xml");
      xstream.toXML(personas, ficheroXML);

      System.out.println("Serializando la coleccion de personas a fichero XML");
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    
  }

  private void ejemploSerializarUnaColeccionImplicitaDePersonasaXML() {
    personas.add(new Persona("Juanito", "Perez", "Sanchez", 5));
    personas.add(new Persona("Maria", "Lopez", "Ruiz", 8));

    XStream xstream = new XStream();

    xstream.alias("Persona", Persona.class);
    xstream.alias("Personas", ColeccionPersonas.class);

    xstream.addImplicitCollection(ColeccionPersonas.class, "listaPersonas");

    try {
      FileOutputStream ficheroXML = new FileOutputStream("XStream/xml/ListaImplícitaPersonas.xml");
      xstream.toXML(personas, ficheroXML);

      System.out.println("Serializando la coleccion de personas a fichero XML");
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void main(String[] args) {
    SerializaraXML programa = new SerializaraXML();

    programa.ejemploSerializarUnObjetoSimpleaXML();
    programa.ejemploSerializarUnObjetoSimpleConAliasaXML();
    programa.ejemploSerializarUnaColeccionDePersonasaXML();
    programa.ejemploSerializarUnaColeccionImplicitaDePersonasaXML();
  }
}

class Persona {

  //Variables de instancia de la clase Persona
  private NombreYApellidos nombreCompleto;
  private int edad;

  public Persona(String nombre, String apellido1, String apellido2, int edad) {
    this.nombreCompleto = new NombreYApellidos(nombre, apellido1, apellido2);
    this.edad = edad;
  }

  public Persona() {
    this.nombreCompleto = null;
    this.edad = 0;
  }

  public void setNombre(String nombre, String apellido1, String apellido2) {
    this.nombreCompleto = new NombreYApellidos(nombre, apellido1, apellido2);
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public NombreYApellidos getNombre() {
    return nombreCompleto;
  }

  public int getEdad() {
    return edad;
  }

  public void mostrarPersona() {
    System.out.println(getNombre() + "," + getEdad());
  }
}

class NombreYApellidos {

  String nombre;
  String apellido1;
  String apellido2;

  public NombreYApellidos(String nombre, String apellido1, String apellido2) {
    this.nombre = nombre;
    this.apellido1 = apellido1;
    this.apellido2 = apellido2;
  }

  public NombreYApellidos() {
    this.nombre = null;
    this.apellido1 = null;
    this.apellido2 = null;
  }

  @Override
  public String toString() {
    return (nombre + " " + apellido1 + " " + apellido2);
  }
}
