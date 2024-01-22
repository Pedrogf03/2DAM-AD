package hibernate;
// Generated 18-ene-2024 11:41:09 by Hibernate Tools 4.3.1



/**
 * Infofinanciera generated by hbm2java
 */
public class Infofinanciera  implements java.io.Serializable {


     private int idDepartamento;
     private Departamento departamento;
     private int presupuesto;
     private int ingresos;
     private int gastos;

    public Infofinanciera() {
    }

    public Infofinanciera(Departamento departamento, int presupuesto, int ingresos, int gastos) {
       this.departamento = departamento;
       this.presupuesto = presupuesto;
       this.ingresos = ingresos;
       this.gastos = gastos;
    }
   
    public int getIdDepartamento() {
        return this.idDepartamento;
    }
    
    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
    public Departamento getDepartamento() {
        return this.departamento;
    }
    
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    public int getPresupuesto() {
        return this.presupuesto;
    }
    
    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }
    public int getIngresos() {
        return this.ingresos;
    }
    
    public void setIngresos(int ingresos) {
        this.ingresos = ingresos;
    }
    public int getGastos() {
        return this.gastos;
    }
    
    public void setGastos(int gastos) {
        this.gastos = gastos;
    }




}

