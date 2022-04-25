/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingeolineas.controlador;

import ingeolineas.modelo.Contratista;
import ingeolineas.modelo.ContratistaDAO;
import ingeolineas.modelo.Contrato;
import ingeolineas.modelo.ContratoDAO;
import ingeolineas.vista.FrmContrato;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ControladorContrato implements ActionListener {

    private FrmContrato fContrato;
    private Contrato contra;
    private ContratoDAO contratoD;

    public ControladorContrato(FrmContrato fContrato, Contrato contra, ContratoDAO contratoD) {
        this.fContrato = fContrato;
        this.contra = contra;
        this.contratoD = contratoD;

        this.fContrato.jTxIdContrato.addActionListener(this);
        this.fContrato.jCbIdCedula.addActionListener(this);
        this.fContrato.jBtNuevo.addActionListener(this);
        this.fContrato.jBtGuardar.addActionListener(this);
        this.fContrato.jBtModificar.addActionListener(this);
        this.fContrato.jBtEliminar.addActionListener(this);
        this.fContrato.jBtConsultar.addActionListener(this);
        this.fContrato.jBtSalir.addActionListener(this);

    }

    /*public void mostrarContratista() throws SQLException {
        Contratista contra = new Contratista();
        ContratistaDAO contraD = new ContratistaDAO();
        ArrayList contraA = (ArrayList) ContratistaDAO.consultarContratista();
        for (int i = 0; i < contraA.size(); i++) {
            contra = (Contratista) contraA.get(i);
            fContrato.jCbIdCedula.addItem(String.valueOf(contra.getIdCedula()));
        }
    
        }*/
    @Override
    public void actionPerformed(ActionEvent e) {

        //Método que permite INSERTAR nuevo datos en la clase contrato
        if (e.getSource() == fContrato.jBtNuevo) {
            limpiarControles();
        }
        //Método que permite GUARDAR datos en la clase contrato
        if (e.getSource() == fContrato.jBtGuardar) {
            int idCodProyecto = Integer.parseInt(fContrato.jTxIdContrato.getSelectedText().toString());
            String valorMensual = fContrato.jTxValorMensual.getText();
            contra = new Contrato(idCodProyecto, valorMensual);
            if (contratoD.adicionar(contra)) {
                JOptionPane.showMessageDialog(fContrato, "Datos registrados correctamente");
                limpiarControles();
            } else {
                JOptionPane.showMessageDialog(fContrato, "EL dato NO se puedo registrar");
            }
        }
        //Método que permite MODIFICAR datos de la clase contrato
        if (e.getSource() == fContrato.jBtModificar) {
            String idContrato = fContrato.jTxIdContrato.getText();
            int codProyecto = Integer.parseInt(fContrato.jCbIdCedula.getSelectedItem().toString());
            String valorMensual = fContrato.jTxValorMensual.getText();
            contra = new Contrato(codProyecto, valorMensual);
            try {
                if (contratoD.actualizar(contra)) {
                    JOptionPane.showMessageDialog(fContrato, "El contratista se actualizó adecuadamente");
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(fContrato, "El Contratista NO se puedo actualizar");
                }

            } catch (SQLException ex) {
                Logger.getLogger(ControladorContratista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Método que permite ELIMINAR datos de la clase contrato
        System.out.println("Estoy en seccion ants del boton eliminar");
        if (e.getSource() == fContrato.jBtEliminar) {
            int cont = Integer.parseInt(fContrato.jTxIdContrato.getText());//NO PUESO ELIMINAR CON VALOR MENSUAL
            int respuesta = JOptionPane.showConfirmDialog(fContrato, "¿Esta seguro de eliminar el registro", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                if (contratoD.eliminar(cont)) {
                    JOptionPane.showMessageDialog(fContrato, "Se eliminó con éxito");
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(fContrato, "No se eliminó - Verificar datos");
                }
            }
        }
        //Método que permite CONSULTAR datos en la clase contrato

        //Método que permite SALIR de la clase contrato
        System.out.println("Estoy en seccion ants del boton salir");
        if (e.getSource() == fContrato.jBtSalir) {

            int respuesta = JOptionPane.showConfirmDialog(fContrato, "¿Está seguro que desea salir?", "Fin productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (respuesta == JOptionPane.YES_OPTION) {
                fContrato.dispose();
            }
        }
    }

    private void limpiarControles() {
        java.util.Date date = new java.sql.Date(new java.util.Date().getTime());
        fContrato.jTxValorMensual.setText("");

    }
}
