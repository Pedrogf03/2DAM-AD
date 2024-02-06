
// Clase para serializar los presupuestos a escribir en el fichero xml

public class Presupuesto {

  String matricula;
  int motor;
  int frenos;

  int total;

  public Presupuesto() {
  }

  public void setTotal() {
    this.total = motor + frenos;
  }

}
