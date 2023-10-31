import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class App {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Por favor, introduzca dos argumentos.");
        } else {

            File f = new File(args[0]);
            File f2 = new File(args[1]);

            try (
                BufferedReader reader = new BufferedReader(new FileReader(f));
                BufferedWriter  writer = new BufferedWriter(new FileWriter(f2));
            ) {

                String entrada = reader.readLine();
                System.out.println(entrada);
                String salida = formatearCadena(entrada);
                System.out.println(salida);

                if(!f2.exists()){
                    f2.createNewFile();
                }

                writer.write(salida);
                
            } catch (Exception e) {
            
            }

        }


    }

    public static String formatearCadena(String cadena) {
        StringBuilder resultado = new StringBuilder();
        boolean despuesDePunto = true; // Comenzamos con true para que el primer carácter sea mayúscula

        for (char caracter : cadena.toCharArray()) {
            if (Character.isWhitespace(caracter)) {
                if (despuesDePunto) {
                    continue; // Saltar espacios adicionales después de punto
                } else {
                    if (resultado.length() == 0 || !Character.isWhitespace(resultado.charAt(resultado.length() - 1))) {
                        resultado.append(' '); // Agregar un espacio solo si no hay un espacio ya presente
                    }
                }
            } else if (caracter == '.') {
                resultado.append(". "); // Añadir punto y espacio
                despuesDePunto = true;
                continue; // Saltar el procesamiento adicional de punto
            } else {
                if (despuesDePunto) {
                    resultado.append(Character.toUpperCase(caracter));
                } else {
                    resultado.append(Character.toLowerCase(caracter));
                }
                despuesDePunto = false;
            }
        }

        // Agregar un punto al final de la cadena
        if (resultado.length() > 0 && resultado.charAt(resultado.length() - 1) != '.') {
            resultado.append('.');
        }

        return resultado.toString();
    }

}