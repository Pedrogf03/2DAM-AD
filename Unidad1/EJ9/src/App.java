import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        
        File alumnos = new File("ListaDeClase.txt");
        
        FileReader reader = new FileReader(alumnos);;
        FileWriter writer = null;

        String nuevo = args[0];

        try {

            List<String> nombres = new ArrayList<>();
            Scanner sc = new Scanner(reader);

            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                nombres.add(linea);
            }

            sc.close();

            // Agregar el nuevo nombre a la lista
            nombres.add(nuevo);

            // Ordenar la lista alfabéticamente
            Collections.sort(nombres);


            writer = new FileWriter(alumnos);
            for (String nombre : nombres) {
                writer.write(nombre + "\n");
            }

        } catch (Exception e){
            System.out.println("Error");
        } finally {
            if(writer != null) {
                writer.close();
            }

            if(reader != null) {
                reader.close();
            }
        }

    }
}
