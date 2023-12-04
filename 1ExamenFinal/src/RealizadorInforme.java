import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.sql.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.parsers.SAXParser;
import org.xml.sax.Attributes;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RealizadorInforme {

  String currentElement;
  int media = 0;
  int countExamenes = 0;

  public RealizadorInforme(File f) {
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
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

      currentElement = qName;

      if (currentElement.equals("alumno")) {
        Alumno a = new Alumno(attributes.getValue("nombre"), 0, null);
      }

    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      currentElement = "";
    }

  }

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

  }

  public static void generarInforme(String modulos, String denom, String rangoFechas, int formato) {

    String[] mods = modulos.split(", ");
    String[] denoms = denom.split(", ");
    String[] fechas = rangoFechas.split(", ");

    for (int i = 0; i < mods.length; i++) {

      File f = new File("./" + mods[i]);
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

              if (formato == 1) {
                importarXML(file);
              } else {
                importarBin(file);
              }

            }
          }

        }

      }

    }

  }

  public static void importarXML(File f) {

  }

  public static void importarBin(File f) {

    try (RandomAccessFile readerFile = new RandomAccessFile(f, "r"); RandomAccessFile readerInforme = new RandomAccessFile("informe.dat", "r");) {

      Map<String, Integer> medias = new TreeMap<>();
      if (new File("informe.dat").exists()) {
        while (readerFile.getFilePointer() < readerFile.length()) {

          String line = readerInforme.readUTF();
          medias.put(line.split(";")[0], Integer.parseInt(line.split(";")[1]));

        }
      }

      while (readerFile.getFilePointer() < readerFile.length()) {
        String line = readerFile.readUTF();

        String[] partes = line.split(";");

        //String write = partes[0] + 0 + 

      }

    } catch (Exception e) {
    }

  }

}
