import java.util.List;

public class Alumno {
  String nombre;
  int media;
  List<Examen> examenes;

  public Alumno(String nombre, int media, List<Examen> examenes) {
    this.nombre = nombre;
    this.media = media;
    this.examenes = examenes;
  }
}
