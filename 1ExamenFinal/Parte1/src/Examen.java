import java.util.Objects;

public class Examen implements Comparable<Examen> {

  String modulo;
  String denominacion;
  String fecha;
  int nota;

  public Examen(String modulo, String denominacion, String fecha, int nota) {
    this.denominacion = denominacion;
    this.modulo = modulo;
    this.fecha = fecha;
    this.nota = nota;
  }

  @Override
  public int compareTo(Examen otroExamen) {

    int resultado = this.modulo.compareTo(otroExamen.modulo);

    if (resultado == 0) {
      resultado = this.denominacion.compareTo(otroExamen.denominacion);
      if (resultado == 0) {
        resultado = this.fecha.compareTo(otroExamen.fecha);
      }
    }

    return resultado;

  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Examen examen = (Examen) obj;
    return Objects.equals(modulo, examen.modulo) && Objects.equals(denominacion, examen.denominacion) && Objects.equals(fecha, examen.fecha);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modulo, denominacion, fecha);
  }

}
