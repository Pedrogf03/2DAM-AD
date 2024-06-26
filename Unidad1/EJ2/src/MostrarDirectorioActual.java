import java.io.File;
import java.io.FileNotFoundException;

public class MostrarDirectorioActual {

    public static void main(String[] args) {
        
        try {
            if (args.length != 1) {
                throw new LineaComandosException();
            } else if (args[0].equals("-h")) {
                mostrarAyuda();
            } else {
                File f = new File(args[0]);

                if (!f.exists()) {
                    throw new FileNotFoundException(null);
                } else if(f.isFile()) {
                    System.out.println(f.getName());
                } else if (f.isDirectory()) {

                    File[] files = f.listFiles();
                    for(File file : files) {
                        System.out.println(file.getName());
                    }

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("¡Error! No se ha encontrado ese archivo o directorio.");
        } catch (LineaComandosException e) {
            mostrarAyuda();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado.");
        }

    }

    public static void mostrarAyuda() {

        System.out.println("Pase como parámetro la ruta de un archivo o directorio.");
        System.out.println("- Si es un directorio, se mostrará todo su contenido.");
        System.out.println("- Si es un archivo, se mostrará su nombre.");

    }

}
