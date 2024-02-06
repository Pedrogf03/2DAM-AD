import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// Clase para serializar conceptos a la clase concepto

public class SAXParserConceptos {

  private String currentElement;
  ArrayList<Concepto> conceptos;

  private Concepto c;

  public SAXParserConceptos() {
    try {
      conceptos = new ArrayList<>();
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();
      parser.parse(
          new File("conceptos.xml"),
          new MyHandler());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  class MyHandler extends DefaultHandler {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      currentElement = qName;
      if (currentElement.equals("concepto")) {
        String nombre = attributes.getValue("nombre").trim();
        c = new Concepto(nombre);
      }
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
      if (currentElement.equals("leve")) {
        c.leve = Integer.parseInt(new String(chars, start, length).trim());
      } else if (currentElement.equals("medio")) {
        c.medio = Integer.parseInt(new String(chars, start, length).trim());
      } else if (currentElement.equals("grave")) {
        c.grave = Integer.parseInt(new String(chars, start, length).trim());
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      if (qName.equals("concepto")) {
        conceptos.add(c);
      }
      currentElement = "";
    }
  }

}
