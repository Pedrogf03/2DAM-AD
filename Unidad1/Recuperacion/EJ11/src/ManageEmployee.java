import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ManageEmployee {

  // CAMPO DE BORRADO LÓGICO
  // 0 -> NO BORRADO
  // 1 -> BORRADO

  public static void main(String[] args) throws Exception {

    File f = new File("Empleados.txt");

    int option = 0;

    Scanner sc = new Scanner(System.in);

    do {

      System.out.println("Elija una opción:");
      System.out.println("1-. Realizar alta empleado");
      System.out.println("2-. Realizar baja empleado");
      System.out.println("3-. Modificar empleado");
      System.out.println("4-. Consultar empleados");
      System.out.println("0-. Salir");

      System.out.println("-----------------------------------------------------");

      try {
        option = Integer.parseInt(sc.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Opcion inválida");
        continue;
      }

      switch (option) {
        case 1:
          System.out.print("Introduzca el nombre del nuevo empleado: ");
          String newEmployeeName = sc.nextLine();
          System.out.print("Introduzca el departamento del nuevo empleado: ");
          String newDepartment = sc.nextLine();
          System.out.print("Introduzca el ciudad del nuevo empleado: ");
          String newCity = sc.nextLine();
          System.out.print("Introduzca el salario del nuevo empleado: ");
          double newSalary = Double.parseDouble(sc.nextLine());
          addEmployee(newEmployeeName, newDepartment, newCity, newSalary, f);
          break;
        case 2:
          System.out.print("Introduzca el del nuevo empleado: ");
          String deleteEmpName = sc.nextLine();
          deleteEmployee(deleteEmpName, f);
          break;
        case 3:
          //TODO: Realizar modificacion
          break;
        case 4:
          //TODO: Realizar consulta
          break;

        default:
          break;
      }

      System.out.println("-----------------------------------------------------");

    } while (option != 0);

    sc.close();

  }

  public static void addEmployee(String name, String department, String city, double salary, File employees) {

    try (FileWriter writer = new FileWriter(employees, true)) {

      writer.write(name + ";" + department + ";" + city + ";" + salary + ";0\n");

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void deleteEmployee(String name, File employees) throws IOException {
    File tempFile = new File(employees.getAbsolutePath() + ".tmp");

    try (FileReader reader = new FileReader(employees);
        FileWriter writer = new FileWriter(tempFile)) {

      int c;
      StringBuilder line = new StringBuilder();
      while ((c = reader.read()) != -1) {
        char caracter = (char) c;
        if (c == '\n') {
          if (!line.toString().contains(name)) {
            writer.write(line.toString());
            writer.write("\n");
          }
          line = new StringBuilder();
        } else {
          line.append(caracter);
        }
      }
      if (!line.toString().contains(name)) {
        writer.write(line.toString());
      }
    }

    employees.delete();
    tempFile.renameTo(employees);
  }

}
