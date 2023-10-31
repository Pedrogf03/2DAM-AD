import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class App {

  public static void main(String[] args) throws Exception {
    Map<String, String> index = importarIndex();

    System.out.println("Elige una opción:");
    System.out.println("1.- Realizar alta de un empleado.");
    System.out.println("2.- Realizar baja de un empleado.");
    System.out.println("3.- Modificar datos de un empleado.");
    System.out.println("4.- Realizar una consulta.");

    Scanner sc = new Scanner(System.in);
    int option = Integer.parseInt(sc.nextLine());

    File f = new File("empleados.dat");

    switch (option) {
      case 1:
        System.out.print("Nombre del empleado: ");
        String nombre = sc.nextLine();

        System.out.print("Departamento del empleado: ");
        String departamento = sc.nextLine();

        System.out.print("Ciudad del empleado: ");
        String ciudad = sc.nextLine();

        System.out.print("Salario del empleado: ");
        int salario = sc.nextInt();

        realizarAlta(nombre, departamento, ciudad, salario, f);

        break;
      case 2:
        System.out.print("ID del empleado: ");
        int delete = sc.nextInt();

        realizarBaja(delete, f, index);

        break;
      case 3:
        System.out.print("ID del empleado: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nombre del empleado: ");
        String nombre2 = sc.nextLine();

        System.out.print("Departamento del empleado: ");
        String departamento2 = sc.nextLine();

        System.out.print("Ciudad del empleado: ");
        String ciudad2 = sc.nextLine();

        System.out.print("Salario del empleado: ");
        int salario2 = sc.nextInt();

        modificarEmpleado(
          id,
          nombre2,
          departamento2,
          ciudad2,
          salario2,
          f,
          index
        );

        break;
      case 4:
        System.out.print("ID del empleado: ");
        int idConsultar = Integer.parseInt(sc.nextLine());

        consultarEmpleado(idConsultar, f, index);

        break;
      default:
        System.out.println("Debe elegir una de las opciones anteriores.");
        break;
    }

    sc.close();

    generarIndice(f);
  }

  public static void generarIndice(File archivoEmpleados) {
    File indextmp = new File("empleados.tmp");
    File indexf = new File("empleados.index");

    try (
      RandomAccessFile file = new RandomAccessFile(archivoEmpleados, "r");
      RandomAccessFile index = new RandomAccessFile(indextmp, "rw");
    ) {
      file.seek(0); // Mover el puntero al principio del archivo de empleados
      long posicionActual = 0;

      String linea;
      while ((linea = file.readLine()) != null) {
        String[] partes = linea.split(";;");
        if (partes.length > 0 && partes[5].equals("0")) {
          String idEmpleado = partes[0];
          index.writeBytes(idEmpleado + ";;" + posicionActual + "\n");
        }
        posicionActual = file.getFilePointer(); // Obtener la posición actual en el archivo de empleados
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    indexf.delete();
    indextmp.renameTo(indexf);
  }

  public static Map<String, String> importarIndex() {
    Map<String, String> indice = new TreeMap<>();

    try (
      RandomAccessFile index = new RandomAccessFile("empleados.index", "r");
    ) {
      index.seek(0); // Mover el puntero al principio del archivo

      String linea;
      while ((linea = index.readLine()) != null) {
        String[] partes = linea.split(";;");
        if (partes.length > 0) {
          indice.put(partes[0], partes[1]);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return indice;
  }

  // Obtener el próximo ID único en el archivo
  public static int obtenerProximoId(RandomAccessFile file) throws IOException {
    int proximoId = 0;
    file.seek(0); // Ir al principio del archivo

    String linea;
    while ((linea = file.readLine()) != null) {
      String[] partes = linea.split(";;");
      if (partes.length > 0) {
        int idExistente = Integer.parseInt(partes[0]);
        if (idExistente >= proximoId) {
          proximoId = idExistente + 1; // El próximo ID es uno más que el ID existente más alto
        }
      }
    }

    return proximoId;
  }

  public static void realizarAlta(
    String nombre,
    String departamento,
    String ciudad,
    int salario,
    File f
  ) {
    try (
      RandomAccessFile file = new RandomAccessFile(f, "rw"); // Abrir el archivo en modo lectura/escritura
    ) {
      long pos = file.length(); // Tamaño del archivo.
      file.seek(pos); // Colocar el puntero al final del archivo.
      int id = obtenerProximoId(file);
      file.writeBytes(
        id +
        ";;" +
        nombre +
        ";;" +
        departamento +
        ";;" +
        ciudad +
        ";;" +
        salario +
        ";;0" +
        "\n"
      ); // Escribir en el lugar donde apunta el puntero.

      System.out.println("Empleado " + nombre + " almecenado con el ID " + id);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void realizarBaja(int id, File f, Map<String, String> index) { // Borrado lógico
    try (
      RandomAccessFile file = new RandomAccessFile(f, "rw"); // Abrir el archivo en modo lectura/escritura
    ) {
      for (Map.Entry<String, String> entry : index.entrySet()) {
        if (Integer.parseInt(entry.getKey()) == id) { // Si la clave coincide con el id que se quiere borrar.
          file.seek(Long.parseLong(entry.getValue())); // Se coloca el puntero en la posicion donde se encuentra ese id segun el indice
          String line = file.readLine(); // Se guarda la linea
          String[] partes = line.split(";;");
          partes[5] = "1";
          line = String.join(";;", partes); // Se crea una nueva linea
          file.seek(Long.parseLong(entry.getValue())); // Se coloca el puntero en la posicion donde se encuentra ese id segun el indice
          file.writeBytes(line); // Se guarda en el archivo
          break;
        }
      }

      System.out.println("Empleado borrado con éxito.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void modificarEmpleado(
    int id,
    String nombre,
    String departamento,
    String ciudad,
    int salario,
    File f,
    Map<String, String> index
  ) {
    boolean found = false; // Variable para comprobar si se ha encontrado o no al empleado.

    try (
      RandomAccessFile file = new RandomAccessFile(f, "rw"); // Abrir el archivo en modo lectura/escritura
    ) {
      for (Map.Entry<String, String> entry : index.entrySet()) {
        if (Integer.parseInt(entry.getKey()) == id) { // Si la clave coincide con el id que se quiere modificar.
          file.seek(Long.parseLong(entry.getValue())); // Se coloca el puntero en la posicion donde se encuentra ese id segun el indice
          String line = file.readLine(); // Se guarda la linea
          String[] partes = line.split(";;");
          partes[1] = nombre;
          partes[2] = departamento;
          partes[3] = ciudad;
          partes[4] = String.valueOf(salario);
          line = String.join(";;", partes); // Se crea una nueva linea
          file.seek(Long.parseLong(entry.getValue())); // Se coloca el puntero en la posicion donde se encuentra ese id segun el indice
          file.writeBytes(line); // Se guarda en el archivo
          break;
        }
      }

      if (!found) {
        System.out.println("No se ha encontrado al empleado solicitado.");
      } else {
        System.out.println("Empleado modificado con éxito.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void consultarEmpleado(
    int id,
    File f,
    Map<String, String> index
  ) {
    boolean found = false; // Variable para comprobar si se ha encontrado o no al empleado.

    try (
      RandomAccessFile file = new RandomAccessFile(f, "rw"); // Abrir el archivo en modo lectura/escritura
    ) {
      for (Map.Entry<String, String> entry : index.entrySet()) {
        if (Integer.parseInt(entry.getKey()) == id) { // Si la clave coincide con el id que se quiere consultar.
          file.seek(Long.parseLong(entry.getValue())); // Se coloca el puntero en la posicion donde se encuentra ese id segun el indice
          String line = file.readLine(); // Se guarda la linea
          String[] partes = line.split(";;");
          System.out.println("ID: " + partes[0]);
          System.out.println("Nombre: " + partes[1]);
          System.out.println("Departamento: " + partes[2]);
          System.out.println("Ciudad: " + partes[3]);
          System.out.println("Salario: " + partes[4]);
          found = true;
        }
      }

      if (!found) {
        System.out.println("No se ha encontrado al empleado solicitado.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
