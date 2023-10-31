import java.io.File;

public class Listar {
    public static void main(String[] args) {

        // _ = ? | + = *

        try {
            if(args.length != 1) {
                throw new NumberOfArgumentsException();
            } else {

                String patron = args[0];

                File dir = new File(System.getProperty("user.dir"));

                File[] archivos = dir.listFiles();

                System.out.println("El contenido del directorio " + dir + ":");
                for (File archivo : archivos){
                    if(comparar(patron, archivo.getName())){
                        if(archivo.isDirectory()) {
                            System.out.print("d");
                        } else {
                            System.out.print("-");
                        }

                        if(archivo.canRead()) {
                            System.out.print("r");
                        } else {
                            System.out.print("-");
                        }

                        if(archivo.canWrite()) {
                            System.out.print("w");
                        } else {
                            System.out.print("-");
                        }

                        System.out.print("- " + archivo.getName());

                        if(archivo.isFile()) {
                            System.out.print(" " + archivo.length() + " bytes");
                        }

                        System.out.println();

                    }
                }

            }
        } catch (NumberOfArgumentsException e){
            System.out.println("Introduzca un solo argumento.");
        }

    }

    private static boolean comparar(String patron, String file){

        int indexPlus = patron.indexOf("+");

        if(indexPlus != -1){

            char nextChar = patron.charAt(indexPlus + 1);

            if(file.indexOf(nextChar) != -1) {
                String subcadena = file.substring(indexPlus, file.indexOf(nextChar));
                patron = patron.replace("+", subcadena);
            }

        }



        for (int i = 0; i < patron.length(); i++) {

            if (patron.charAt(i) != file.charAt(i) && patron.charAt(i) != '_') {
                return false;
            }

        }

        return true;
    }

}


