package entite;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ModeReglements implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
    private int code;
	
	@Basic
    private String type;
    
    @Transient
    private transient boolean ignoré;
    
    /*
     * Constructeur 1.
     * Utilisé par JPA.
     */
    public ModeReglements(){
    	super();
    }
    
    /*
     * Constructeur 2
     * Pour la gestion des commandes
     */
    public ModeReglements(String type){
    	this();
    	this.type = type;
    }

    /*
     * Accesseurs
     */
    public int getCode() {
        return code;
    }
    public String getType() {
        return type;
    }
    
    /*
     * Mutateur
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Décrit le mode de règlement
     * de manière textuelle.
     */
    @Override
    public String toString() {
    	return type;
    }
}
