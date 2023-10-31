import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        
        if(args.length < 2 || args.length > 3) {
            System.out.println("Error! No se han introducido los parámetros esperados.");
        } else {
            
            File f1 = new File(args[0]);
            try {
                if(!f1.exists()){
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException ex) {
                System.out.println("No se ha encontrado el archivo origen.");
            }

            File f2 = new File(args[1]);
            if(!f2.exists()){
                f2.createNewFile();
            }

            String option = "";
            if(args.length > 2) {
                option = args[2];
            }

            FileInputStream in = null;
            FileOutputStream out = null;

            try {
                
                in = new FileInputStream(f1);
                if(option.equals("-a")){
                    out = new FileOutputStream(f2, true);
                } else {
                    out = new FileOutputStream(f2);
                }

                int c;

                while((c = in.read()) != -1) {
                    out.write(c);
                }

            } catch (IOException e){
                System.out.println("Ha ocurrido un error.");
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }

        }

    }
}
