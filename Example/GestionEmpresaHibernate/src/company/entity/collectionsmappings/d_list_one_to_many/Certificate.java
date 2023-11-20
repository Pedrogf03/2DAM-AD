
package company.entity.collectionsmappings.d_list_one_to_many;


/**
 * <p>POJO class corresponding to CERTIFICATE2 table so that certificate objects 
 *    can be stored and retrieved into the CERTIFICATE2 table.
 */
public class Certificate {
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
}
