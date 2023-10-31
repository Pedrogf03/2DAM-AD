import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class App {
    public static void main(String[] args) throws Exception {
        
        if(args.length == 2) {
            
            File f1 = new File(args[0]);
            File f2 = new File(args[1]);
            
            FileReader reader = null;
            FileWriter writer = null;

            try {
                reader = new FileReader(f1);
                writer = new FileWriter(f2);

                int c;

                while ((c = reader.read()) != -1) {
                    writer.write(Character.toUpperCase((char) c));
                }

            } catch (Exception e){

            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            }

        } else {
            System.out.println("Error! Introduzca el nombre de nos archivos.");
        }

    }
}
