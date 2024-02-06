import static dao.DAODireccion.*;
import static dao.DAOEmpleado.addEmpleado;
import static dao.DAOEmpleado.deleteEmpleado;
import static dao.DAOEmpleado.listEmpleados;
import static dao.DAOTarea.addTarea;
import hibernate.Direccion;
import hibernate.Empleado;
import hibernate.Tarea;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

  public static void main(String[] args) {

    Direccion d1 = new Direccion("Calle de ejemplo", "02", "51002", "Ceuta");
    
    Empleado e1 = new Empleado("Francisco", "Rodriguez Belmonte", 2300, d1);
    
    addEmpleado(e1);

    System.exit(0);

  }

}
