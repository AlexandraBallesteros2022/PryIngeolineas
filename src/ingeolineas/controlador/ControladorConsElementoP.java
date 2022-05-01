/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingeolineas.controlador;

import ingeolineas.modelo.ElementoPp;
import ingeolineas.modelo.ElementoPpDAO;
import ingeolineas.vista.FrmConsultaElemento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class ControladorConsElementoP implements ActionListener {

    private ElementoPp elemento;
    private ElementoPpDAO elementoD;
    private FrmConsultaElemento felemento;
    DefaultTableModel modelo = new DefaultTableModel();

    public ControladorConsElementoP(ElementoPp elemento, ElementoPpDAO elementoD, FrmConsultaElemento felemento) {
        this.elemento = elemento;
        this.elementoD = elementoD;
        this.felemento = felemento;
        this.felemento.jCbNombreEpp.addActionListener(this);
        this.felemento.jBtSalir.addActionListener(this);
        modelo.addColumn("idCodigoEpp");
        modelo.addColumn("idCedula");
        modelo.addColumn("nombre");
        felemento.jTbConsultaEpp.setModel(modelo);
        mostrarElemntoPp();
    }

    private void mostrarElemntoPp() {
        ElementoPp elemento = new ElementoPp();
        ElementoPpDAO elementoD = new ElementoPpDAO();
        String sql = "Select * from epp group by nombre";
        ArrayList elementoA = null;
        try {
            elementoA = (ArrayList) elementoD.consultarElementoPp(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorConsElementoP.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < elementoA.size(); i++) {
            elemento = (ElementoPp) elementoA.get(i);
            felemento.jCbNombreEpp.addItem(String.valueOf(elemento.getNombreEpp()));
        }

    }

    public void limpiarTable() {
        while (felemento.jTbConsultaEpp.getRowCount() != 0) {
            ((DefaultTableModel) felemento.jTbConsultaEpp.getModel()).removeRow(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == felemento.jCbNombreEpp) {
            limpiarTable();
            String sql = "Select * from epp where nombre ='"
                    + felemento.jCbNombreEpp.getSelectedItem().toString() + "'";
            ArrayList elementoNombre = null;
            try {
                elementoNombre = (ArrayList) elementoD.consultarElementoPp(sql); //Llamar el método unSoloP
            } catch (SQLException ex) {
                Logger.getLogger(ControladorConsElementoP.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Ciclo que llena la tabla despues de la consulta
            for (int i = 0; i < elementoNombre.size(); i++) {
                String[] datos = new String[3];
                elemento = (ElementoPp) elementoNombre.get(i);
                datos[0] = String.valueOf(elemento.getIdCodigoEpp());
                datos[1] = elemento.getIdCedula();
                datos[2] = elemento.getNombreEpp();
                modelo.addRow(datos);

            }

        }

        //Método que permite salir del formulario
        if (e.getSource() == felemento.jBtSalir) {
            int respuesta = JOptionPane.showConfirmDialog(felemento, "¿Está seguro que desea salir?", "Fin productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                felemento.dispose();
            }

        }
    }
}
