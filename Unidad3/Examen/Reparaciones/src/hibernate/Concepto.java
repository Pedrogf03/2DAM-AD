package hibernate;
// Generated 05-feb-2024 13:22:21 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Concepto generated by hbm2java
 */
public class Concepto  implements java.io.Serializable {


     private String codigo;
     private String denominacion;
     private double precioReferencia;

    public Concepto() {
    }

	
    public Concepto(String codigo, String denominacion, double precioReferencia) {
        this.codigo = codigo;
        this.denominacion = denominacion;
        this.precioReferencia = precioReferencia;
    }
   
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getDenominacion() {
        return this.denominacion;
    }
    
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    public double getPrecioReferencia() {
        return this.precioReferencia;
    }
    
    public void setPrecioReferencia(double precioReferencia) {
        this.precioReferencia = precioReferencia;
    }




}


