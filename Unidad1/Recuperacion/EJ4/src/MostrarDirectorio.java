import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.TreeSet;

public class MostrarDirectorio {

  public static void main(String[] args) throws Exception {

    try {
      if (args.length == 0 || args.length > 1) {
        throw new LineaComandosException("Error: Se esperaba un único argumento.");
      } else if (args[0].equals("-h")) {
        mostrarAyuda();
      } else {

        File f = new File(args[0]);

        if (f.isFile()) {

          throw new LineaComandosException("Error: Se esperaba un directorio, no un fichero.");

        } else if (f.isDirectory()) {

          Set<String> files = new TreeSet<>();
          Set<String> directories = new TreeSet<>();

          for (File file : f.listFiles()) {
            if (file.isFile()) {
              files.add(file.getName());
            } else if (file.isDirectory()) {
              directories.add(file.getName());
            }
          }

          System.out.println("Ficheros encontrados: ");
          for (String string : files) {
            System.out.println(string);
          }

          System.out.println();

          System.out.println("Directorios encontrados: ");
          for (String string : directories) {
            System.out.println(string);
          }

        } else if (!f.exists()) {

          throw new FileNotFoundException("El archivo o directorio especificado no existe en la ruta proporcionada.");

        }

      }
    } catch (LineaComandosException e) {
      System.out.println(e.getMessage());
      mostrarAyuda();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

  }

  public static void mostrarAyuda() {
    System.out.println("Uso correcto: java MostrarDirectorio <nombre/ruta de directorio>");
  }

}
