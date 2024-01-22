import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class ListaDeClase {

  public static void main(String[] args) throws Exception {

    File f = new File("ListaDeClase.txt");

    Set<String> alumnos = new TreeSet<>();

    try (FileReader entrada = new FileReader(f)) {

      int c;
      String nombre = "";

      while ((c = entrada.read()) != -1) {

        if (((char) c) == '\n') {

          alumnos.add(nombre);
          nombre = "";

        } else {

          nombre += (char) c;

        }

      }

      // Comprueba si 'nombre' contiene algún carácter después de salir del bucle
      if (!nombre.isEmpty()) {
        alumnos.add(nombre);
      }

    }

    Scanner sc = new Scanner(System.in);

    System.out.print("Introduzca el nombre de un alumno: ");
    String name = sc.nextLine();
    sc.close();

    alumnos.add(name);

    try (FileWriter salida = new FileWriter(f)) {
      for (String nombre : alumnos) {
        salida.write(nombre + '\n');
      }
    }

  }

}
