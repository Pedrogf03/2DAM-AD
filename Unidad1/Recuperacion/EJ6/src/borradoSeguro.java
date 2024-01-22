import java.io.File;

public class borradoSeguro {

  public static void main(String[] args) throws Exception {

    try {
      if (args.length == 0) {
        throw new CommandLineException("Error: Se esperaba una ruta especifica a un archivo.");
      } else {

        File papelera = new File("C:/Users/bleik/Desktop/Papelera");

        if (!papelera.exists()) {
          papelera.mkdir();
        }

        for (String string : args) {

          File f = new File(string);

          try {
            if (f.exists()) {

              File destino = new File(papelera, f.getName());

              int cont = 1;
              while (destino.exists()) {

                String extensionArchivo = destino.getName().substring(destino.getName().lastIndexOf(".") + 1);

                File temp = new File(papelera,
                    destino.getName().substring(0, destino.getName().lastIndexOf(".")) + "(" + cont + ")." + extensionArchivo);

                if (temp.exists()) {
                  cont++;
                } else {
                  destino = temp;
                }

              }

              f.renameTo(destino);

            } else {
              throw new CommandLineException("Warning: El archivo " + f.getName() + " no existe.");
            }
          } catch (CommandLineException e) {
            System.out.println(e.getMessage());
            continue;
          }
        }
      }
    } catch (CommandLineException e) {
      System.out.println(e.getMessage());
    }

  }

}
