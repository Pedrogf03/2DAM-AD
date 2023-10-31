public class Empleado {

  private int id;
  private String nombre;
  private String departamento;
  private String ciudad;
  private int salario;

  public Empleado() {
  }

  public Empleado(int id, String nombre, String departamento, String ciudad, int salario) {

    this.id = id;
    this.nombre = nombre;
    this.departamento = departamento;
    this.ciudad = ciudad;
    this.salario = salario;

  }

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDepartamento() {
    return departamento;
  }
  public void setDepartamento(String departamento) {
    this.departamento = departamento;
  }

  public String getCiudad() {
    return ciudad;
  }
  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public int getSalario() {
    return salario;
  }
  public void setSalario(int salario) {
    this.salario = salario;
  }

  @Override
  public String toString() {
      return "Empleado -> Id: " + id + ", Nombre: " + nombre + ", Departamento: " + departamento + ", Ciudad: " + ciudad + ", Salario: " + salario;
  }

}
