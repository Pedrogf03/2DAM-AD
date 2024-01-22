import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyInMayus {

  public static void main(String[] args) {

    try {
      if (args.length != 2) {
        throw new IndexOutOfBoundsException("Se esperaban dos argumentos en la invocación");
      } else {

        File origen = new File(args[0]);

        if (!origen.exists()) {
          throw new FileNotFoundException("No se ha encontrado el archivo origen: " + origen.getAbsolutePath());
        }

        File destino = new File(args[1]);

        if (!destino.exists()) {
          destino.createNewFile();
        }

        try (FileReader entrada = new FileReader(origen);
            FileWriter salida = new FileWriter(destino)) {

          int c;

          while ((c = entrada.read()) != -1) {
            salida.write(Character.toUpperCase((char) c));
          }

        }

      }
    } catch (IndexOutOfBoundsException e) {
      System.err.println(e.getMessage());
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println("No se ha podido crear el archivo destino especificado");
    }

  }

}
