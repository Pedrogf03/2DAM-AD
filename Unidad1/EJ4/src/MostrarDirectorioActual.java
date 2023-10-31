import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

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
                    System.out.println(f.getAbsolutePath());
                } else if (f.isDirectory()) {

                    File[] files = f.listFiles();

                    Arrays.sort(files);

                    for(File file : files) {
                        if(file.isDirectory()) {
                            System.out.println(file.getAbsolutePath() + "\\");
                        } else {
                            System.out.println(file.getAbsolutePath());
                        }
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
