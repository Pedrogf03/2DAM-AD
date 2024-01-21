import java.io.File;

public class Listar {

  public static void main(String[] args) throws Exception {

    // _ == ? | + == *

    try {

      if (args.length == 1) {
        mostrarAyuda();
      } else if (args.length == 2) {

        File dir = new File(args[0]);
        String patron = args[1];

        File[] files = dir.listFiles();

        System.out.println("Contenido del directorio " + dir + ":");
        for (File f : files) {
          if (comparar(patron, f.getName())) {

            if (f.isDirectory()) {
              System.out.print("d");
            } else {
              System.out.print("-");
            }

            if (f.canRead()) {
              System.out.print("r");
            } else {
              System.out.print("-");
            }

            if (f.canWrite()) {
              System.out.print("2");
            } else {
              System.out.print("-");
            }

            System.out.print("- " + f.getName());

            if (f.isFile()) {
              System.out.print(" " + f.length() + " bytes");
            }

            System.out.println();

          }
        }

      } else {
        throw new InvocationException("Error: Se esperaba un único argumento.");
      }

    } catch (Exception e) {

    }

  }

  public static void mostrarAyuda() {
    System.out.println("Uso correcto: java Listar <directorio> <patron>");
  }

  public static boolean comparar(String patron, String fileName) {

    int indexPlus = patron.indexOf("+");

    if (indexPlus != -1) {

      char nextChar = patron.charAt(indexPlus + 1);

      int nextIndex = fileName.indexOf(nextChar, indexPlus + 1);

      if (nextIndex != -1) {
        String subcadena = fileName.substring(indexPlus, nextIndex);
        patron = patron.replace("+", subcadena);
      }

    }

    for (int i = 0; i < fileName.length(); i++) {
      if (patron.charAt(i) != fileName.charAt(i) && patron.charAt(i) != '_') {
        return false;
      }
    }

    return true;

  }

}
