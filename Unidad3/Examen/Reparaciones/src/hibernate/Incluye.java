package hibernate;

public class Incluye  implements java.io.Serializable {


     private IncluyeId id;
     private Concepto concepto;
     private Reparacion reparacion;
     private double precio;

    public Incluye() {
    }

    public Incluye(IncluyeId id, Concepto concepto, Reparacion reparacion, double precio) {
       this.id = id;
       this.concepto = concepto;
       this.reparacion = reparacion;
       this.precio = precio;
    }
   
    public IncluyeId getId() {
        return this.id;
    }
    
    public void setId(IncluyeId id) {
        this.id = id;
    }
    public Concepto getConcepto() {
        return this.concepto;
    }
    
    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }
    public Reparacion getReparacion() {
        return this.reparacion;
    }
    
    public void setReparacion(Reparacion reparacion) {
        this.reparacion = reparacion;
    }
    public double getPrecio() {
        return this.precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }




}


