import java.util.Map;
import java.util.TreeMap;

public class Calificaciones {
  
  public String nombreAsignatura;
  public Map<String, String> alumnos;
  public String tipo;
  public String fecha;

  public Calificaciones(String nombreAsignatura, String tipo, String fecha){

    this.nombreAsignatura = nombreAsignatura;
    this.tipo = tipo;
    this.fecha = fecha;

    this.alumnos = new TreeMap<>();

  }

}
