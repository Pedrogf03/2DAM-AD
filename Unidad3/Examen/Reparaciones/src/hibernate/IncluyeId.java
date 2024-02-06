package hibernate;

public class IncluyeId  implements java.io.Serializable {


     private String codigoConcepto;
     private String codigoReparacion;

    public IncluyeId() {
    }

    public IncluyeId(String codigoConcepto, String codigoReparacion) {
       this.codigoConcepto = codigoConcepto;
       this.codigoReparacion = codigoReparacion;
    }
   
    public String getCodigoConcepto() {
        return this.codigoConcepto;
    }
    
    public void setCodigoConcepto(String codigoConcepto) {
        this.codigoConcepto = codigoConcepto;
    }
    public String getCodigoReparacion() {
        return this.codigoReparacion;
    }
    
    public void setCodigoReparacion(String codigoReparacion) {
        this.codigoReparacion = codigoReparacion;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IncluyeId) ) return false;
		 IncluyeId castOther = ( IncluyeId ) other; 
         
		 return ( (this.getCodigoConcepto()==castOther.getCodigoConcepto()) || ( this.getCodigoConcepto()!=null && castOther.getCodigoConcepto()!=null && this.getCodigoConcepto().equals(castOther.getCodigoConcepto()) ) )
 && ( (this.getCodigoReparacion()==castOther.getCodigoReparacion()) || ( this.getCodigoReparacion()!=null && castOther.getCodigoReparacion()!=null && this.getCodigoReparacion().equals(castOther.getCodigoReparacion()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCodigoConcepto() == null ? 0 : this.getCodigoConcepto().hashCode() );
         result = 37 * result + ( getCodigoReparacion() == null ? 0 : this.getCodigoReparacion().hashCode() );
         return result;
   }   


}


