import java.io.File;
import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    
    int option;
    Scanner sc = new Scanner(System.in);

    do {

      System.out.println("-----Selecciona una opción-----");
      System.out.println("1.- Importar calificaciones.");
      System.out.println("2.- Consultar calificaciones alumno.");
      System.out.println("3.- Eliminar datos alumno.");
      System.out.println("0.- Cerrar.");

      option = Integer.parseInt(sc.nextLine());

      switch (option) {
        case 1:
          System.out.println("Indique el archivo xml desde el cual importar los datos.");
          File calificaciones = new File(sc.nextLine());

          importarXML(calificaciones);

          break;
        case 2:
          
          break;
        case 3:
          
          break;
      
        default:
          break;
      }




    } while(option != 0);

    sc.close();

  }

  public static void importarXML(File f){

    

  }

}
