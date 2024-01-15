
package company.entity.collectionsmappings.g_sortedmap_one_to_many;


/**
 * <p>POJO class corresponding to CERTIFICATEMAP table so that certificate objects 
 *    can be stored and retrieved into the CERTIFICATEMAP table.
 */
public class Certificate implements Comparable <String>{ //implements Comparable
    private int id;
    private String name;
    
    public Certificate() {} 
    public Certificate(String name) {
        this.name = name;
    } 
    public int getId() {
        return id;
    }
    public void setId( int id ) {
        this.id = id;
    }
    public String getName() { 
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    } 
    
    /**
     * 
     * @param that other Certificate to compare with
     * @return -1 (BEFORE) if that is null, or its name is null,
     *                     or this.name comes before that.name
     *         +1 (AFTER)  this.name is null or this.name comes after that.name
     */
    @Override
    public int compareTo(String that){
        final int BEFORE = -1;
        final int AFTER = 1;
        if (that == null) {
            return BEFORE;
        }
        Comparable thisCertificate = this;
        Comparable thatCertificate = that;
        
        if(thisCertificate == null) {
            return AFTER;
        } else if(thatCertificate == null) {
                   return BEFORE;
        } else {
            return thisCertificate.compareTo(thatCertificate);
        } 
    }  
}
