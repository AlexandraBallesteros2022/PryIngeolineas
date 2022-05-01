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

    public ControladorContrato(FrmContrato fContrato, Contrato contra, ContratoDAO contratoD) throws SQLException {
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
            mostrarContratista();    
    }

    public void mostrarContratista() throws SQLException {
        Contratista contra = new Contratista();
        ContratistaDAO contraD = new ContratistaDAO();
        ArrayList contraA = (ArrayList)contraD.consultarContratistas();
        for (int i = 0; i < contraA.size(); i++) {
            contra = (Contratista) contraA.get(i);
            fContrato.jCbIdCedula.addItem(String.valueOf(contra.getIdCedula()));
        }
   }
    
    @Override
    public void actionPerformed(ActionEvent e) {
   if (e.getSource() == fContrato.jCbIdCedula) {
            String idCedula = fContrato.jCbIdCedula.getSelectedItem().toString();
            Contratista contra = new Contratista();
            ContratistaDAO contraD = new ContratistaDAO();
            try {
                contra = contraD.consultarContratista(idCedula);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorContratista.class.getName()).log(Level.SEVERE, null, ex);
            }
            fContrato.jCbIdCedula.setSelectedItem(String.valueOf(contra.getIdCedula()));      
        }
              
        //Método que permite INSERTAR nuevo datos en la clase contrato
        if (e.getSource() == fContrato.jBtNuevo) {
            limpiarControles();
        }
        
        //Método que permite GUARDAR datos en la clase contrato
        if (e.getSource() == fContrato.jBtGuardar) {
            String idCedula = fContrato.jCbIdCedula.getSelectedItem().toString();
            int valorMensual = Integer.parseInt(fContrato.jTxValorMensual.getText());
            String ciudad = fContrato.jCbCiudad.getSelectedItem().toString();
            contra = new Contrato(idCedula, valorMensual, ciudad);
            if (contratoD.adicionar(contra)) {
                JOptionPane.showMessageDialog(fContrato, "Datos registrados correctamente");
                limpiarControles();
            } else {
                JOptionPane.showMessageDialog(fContrato, "EL dato NO se pudo registrar");
            }
        }

                //Método que permite MODIFICAR datos de la clase contrato
        if (e.getSource() == fContrato.jBtModificar) {
            int idContrato = Integer.parseInt(fContrato.jTxIdContrato.getText());
            String idCedula = fContrato.jCbIdCedula.getSelectedItem().toString();
            int valorMensual = Integer.parseInt(fContrato.jTxValorMensual.getText());
            String ciudad = fContrato.jCbCiudad.getSelectedItem().toString();
            contra = new Contrato(idContrato, idCedula, valorMensual, ciudad);
            try {
                if (contratoD.actualizar(contra)) {
                    JOptionPane.showMessageDialog(fContrato, "El contratista se actualizó adecuadamente");
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(fContrato, "El Contrato NO se pudo actualizar");
                }

            } catch (SQLException ex) {
                Logger.getLogger(ControladorContratista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Método que permite ELIMINAR datos de la clase contrato
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
        if (e.getSource() == fContrato.jBtConsultar) {
            int idContrato = Integer.parseInt(JOptionPane.showInputDialog(fContrato, "Ingrese el ID del contrato a consultar"));
            try {
                contra = contratoD.consultarContrato(idContrato);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorContratista.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }
            if (contra != null) {
                fContrato.jTxIdContrato.setText(String.valueOf(contra.getIdContrato()));
                fContrato.jCbIdCedula.setSelectedItem(String.valueOf(contra.getIdCedula()));
                fContrato.jTxValorMensual.setText(String.valueOf(contra.getValorMensual()));
                fContrato.jCbCiudad.setSelectedItem(String.valueOf(contra.getCiudad()));
            } else {
                JOptionPane.showMessageDialog(fContrato, "El contrato consultado no se encuentra en la BD");
            }
        }

           //Método que permite SALIR de la clase contrato
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


