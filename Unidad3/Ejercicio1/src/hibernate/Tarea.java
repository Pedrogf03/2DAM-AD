package hibernate;
// Generated 18-ene-2024 11:41:09 by Hibernate Tools 4.3.1



/**
 * Tarea generated by hbm2java
 */
public class Tarea  implements java.io.Serializable {


     private int idTarea;
     private String denominacion;
     private String descripcion;
     private String prioridad;

    public Tarea() {
    }

    public Tarea(int idTarea, String denominacion, String descripcion, String prioridad) {
       this.idTarea = idTarea;
       this.denominacion = denominacion;
       this.descripcion = descripcion;
       this.prioridad = prioridad;
    }
   
    public int getIdTarea() {
        return this.idTarea;
    }
    
    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }
    
    public String getDenominacion() {
        return this.denominacion;
    }
    
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getPrioridad() {
        return this.prioridad;
    }
    
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }




}

