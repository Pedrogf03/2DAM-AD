import java.io.File;

public class MostrarDirectorioActual {

    public static void main(String[] args) {
    
        File f = new File(".");

        File[] files = f.listFiles();

        for(File file : files) {

            System.out.println(file.getName());

        }

    }

}