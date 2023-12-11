import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Alumno implements Comparable<Alumno> {
  String nombre;
  int media;
  List<Examen> examenes;

  public Alumno(String nombre) {
    this.nombre = nombre;
    this.examenes = new ArrayList<>();
  }

  @Override
  public int compareTo(Alumno otroAlumno) {
    return this.nombre.compareTo(otroAlumno.nombre);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Alumno alumno = (Alumno) obj;
    return Objects.equals(nombre, alumno.nombre);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombre);
  }

}
