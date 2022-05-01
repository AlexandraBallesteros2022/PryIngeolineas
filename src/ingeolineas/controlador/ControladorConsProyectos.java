/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingeolineas.controlador;

import ingeolineas.modelo.Proyecto;
import ingeolineas.modelo.ProyectoDAO;
import ingeolineas.vista.FrmConsultaProyectos;
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
public class ControladorConsProyectos implements ActionListener {

    private Proyecto proyecto;
    private ProyectoDAO proyecD;
    private FrmConsultaProyectos fproyec;
    DefaultTableModel modelo = new DefaultTableModel();

    public ControladorConsProyectos(Proyecto proyecto, ProyectoDAO proyecD, FrmConsultaProyectos fproyec) throws SQLException {
        this.proyecto = proyecto;
        this.proyecD = proyecD;
        this.fproyec = fproyec;
        this.fproyec.jCbNombreP.addActionListener(this);
        this.fproyec.jBtSalir.addActionListener(this);
        modelo.addColumn("idCodigoProyecto");
        modelo.addColumn("Nombre");
        modelo.addColumn("Telefono");
        modelo.addColumn("fecha");
        modelo.addColumn("Ciudad");
        modelo.addColumn("Responsable");
        fproyec.jTbConsultaP.setModel(modelo);
        mostrarNombreProyecto();
    }

    //Método que permite mostrar el nombre del proyecto
    public void mostrarNombreProyecto() throws SQLException {
        Proyecto proyec = new Proyecto();
        ProyectoDAO proyecDao = new ProyectoDAO();
        String sql = "Select * from proyecto group by Nombre";
        ArrayList proyecA = (ArrayList) proyecDao.consultarProyectos();
        for (int i = 0; i < proyecA.size(); i++) {
            proyec = (Proyecto) proyecA.get(i);
            fproyec.jCbNombreP.addItem(String.valueOf(proyec.getNombre()));
        }
    }

    public void limpiarTable() {
        while (fproyec.jTbConsultaP.getRowCount() != 0) {
            ((DefaultTableModel) fproyec.jTbConsultaP.getModel()).removeRow(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fproyec.jCbNombreP) {
            limpiarTable();
            String sql = "Select * from proyecto where Nombre ='"
                    + fproyec.jCbNombreP.getSelectedItem().toString() + "'";
            ArrayList proyecNombre = null;
            try {
                proyecNombre = (ArrayList) proyecD.consultarUnSoloP(sql); //Llamar el método unSoloP
            } catch (SQLException ex) {
                Logger.getLogger(ControladorConsProyectos.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Ciclo que llena la tabla despues de la consulta
            for (int i = 0; i < proyecNombre.size(); i++) {
                String[] datos = new String[6];
                proyecto = (Proyecto) proyecNombre.get(i);
                datos[0] = String.valueOf(proyecto.getIdCodigoProyecto());
                datos[1] = proyecto.getNombre();
                datos[2] = proyecto.getTelefono();
                datos[3] = String.valueOf(proyecto.getFecha());
                datos[4] = proyecto.getCiudad();
                datos[5] = proyecto.getResponsable();
                modelo.addRow(datos);

            }
        }

        //Método que permite salir del formulario
        if (e.getSource() == fproyec.jBtSalir) {
            int respuesta = JOptionPane.showConfirmDialog(fproyec, "¿Está seguro que desea salir?", "Fin productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                fproyec.dispose();
            }
        }

    }
}
