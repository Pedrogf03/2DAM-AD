import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// Clase para serializar vehiculos a la clase vehiculo

public class SAXParserVehiculos {

  private String currentElement;
  ArrayList<Vehiculo> vehiculos;

  private Vehiculo v;
  private Propietario p;

  public SAXParserVehiculos() {
    try {
      vehiculos = new ArrayList<>();
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();
      parser.parse(
          new File("vehiculos.xml"),
          new MyHandler());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  class MyHandler extends DefaultHandler {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      currentElement = qName;
      if (currentElement.equals("vehiculo")) {
        int idV = Integer.parseInt(attributes.getValue("id").trim());
        v = new Vehiculo(idV);
      } else if (currentElement.equals("propietario")) {
        p = new Propietario();
      }
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
      if (currentElement.equals("matricula")) {
        v.matricula = new String(chars, start, length).trim();
      } else if (currentElement.equals("dni")) {
        p.dni = new String(chars, start, length).trim();
      } else if (currentElement.equals("nombre")) {
        p.nombre = new String(chars, start, length).trim();
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      if (qName.equals("propietario")) {
        v.propietario = p;
      } else if (qName.equals("vehiculo")) {
        vehiculos.add(v);
      }
      currentElement = "";
    }
  }

}
