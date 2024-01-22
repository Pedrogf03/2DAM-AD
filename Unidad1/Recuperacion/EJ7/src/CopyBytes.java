import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;

public class CopyBytes {

  public static void main(String[] args) {

    File origen = null;
    File destino = null;

    try {
      if (args.length < 2 || args.length > 3) {
        throw new InputMismatchException("Error: Uso correcto -> java CopyBytes </ruta/origen> </ruta/destino> [-a]");
      } else {

        origen = new File(args[0]);
        destino = new File(args[1]);

        if (!origen.exists()) {
          throw new FileNotFoundException();
        }

        if (!destino.exists()) {
          destino.createNewFile();
        }

        FileOutputStream salida = null;

        try (FileInputStream entrada = new FileInputStream(origen);) {

          if (args.length == 3 && args[2].equals("-a")) {
            salida = new FileOutputStream(destino, true);
          } else {
            salida = new FileOutputStream(destino);
          }

          int c;

          while ((c = entrada.read()) != -1) {
            salida.write(c);
          }

        } finally {
          try {
            if (salida != null) {
              salida.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      }
    } catch (FileNotFoundException e) {
      System.out.println("No se ha encontrado el archivo origen en la ruta especificada: " + origen.getAbsolutePath());
    } catch (IOException e) {
      System.out.println("No se ha podido crear el archivo destino: " + destino.getAbsolutePath());
    } catch (InputMismatchException e) {
      System.out.println(e.getMessage());
    }

  }

}
