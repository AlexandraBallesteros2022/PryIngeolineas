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
public class Contrato {

    private int idContrato;
    private String idCedula;
    private int valorMensual;
    private String ciudad;

    public Contrato() {
    }

    public Contrato(String idCedula, int valorMensual, String ciudad) {
        this.idCedula = idCedula;
        this.valorMensual = valorMensual;
        this.ciudad = ciudad;
    }

    public Contrato(int idContrato, String idCedula, int valorMensual, String ciudad) {
        this.idContrato = idContrato;
        this.idCedula = idCedula;
        this.valorMensual = valorMensual;
        this.ciudad = ciudad;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public String getIdCedula() {
        return idCedula;
    }

    public void setIdCedula(String idCedula) {
        this.idCedula = idCedula;
    }

    public int getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(int valorMensual) {
        this.valorMensual = valorMensual;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

}
