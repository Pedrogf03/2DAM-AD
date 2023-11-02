import java.io.File;
import java.io.FileNotFoundException;

public class borradoSeguro {
  public static void main(String[] args) {

    try {

      if (args.length == 0) {
        throw new LineaComandosException();
      } else {

        File papelera = new File(".\\papelera");

        if (!papelera.exists()) {
          papelera.mkdir();
        }

        for (int i = 0; i < args.length; i++) {

          File f = new File(args[i]);

          try {
            if (!f.exists()) {
              throw new FileNotFoundException();
            }
          } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo o directorio " + f.getName());
          }

          File destino = new File(papelera, f.getName());

          int cont = 1;
          while (destino.exists()) {

            String extensionArchivo = destino.getName().substring(destino.getName().lastIndexOf(".") + 1);

            File temp = new File(papelera, destino.getName().substring(0, destino.getName().lastIndexOf(".")) + "("
                + cont + ")." + extensionArchivo);

            if (temp.exists()) {
              cont++;
            } else {
              destino = temp;
            }

          }

          f.renameTo(destino);

        }

      }

    } catch (LineaComandosException e) {
      System.out.println("Indique los archivos o directorios que desea eliminar.");
    }

  }
}
