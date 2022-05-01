/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingeolineas.modelo;

/**
 *
 * @author Usuario
 */
public class ElementoPp {
    private int idCodigoEpp;
    private String idCedula;
    private String nombreEpp;

    public ElementoPp() {
    }

    public ElementoPp(int idCodigoEpp, String idCedula, String nombreEpp) {
        this.idCodigoEpp = idCodigoEpp;
        this.idCedula = idCedula;
        this.nombreEpp = nombreEpp;
    }
    
     public ElementoPp(String idCedula, String nombreEpp) { //Cuando vamos a guardar y el id es autoincrementable
        this.idCedula = idCedula;
        this.nombreEpp = nombreEpp;
    }

    public int getIdCodigoEpp() {
        return idCodigoEpp;
    }

    public void setIdCodigoEpp(int idCodigoEpp) {
        this.idCodigoEpp = idCodigoEpp;
    }

    public String getIdCedula() {
        return idCedula;
    }

    public void setIdCedula(String idCedula) {
        this.idCedula = idCedula;
    }

    public String getNombreEpp() {
        return nombreEpp;
    }

    public void setNombreEpp(String nombreEpp) {
        this.nombreEpp = nombreEpp;
    }
    
     
     

    
}
