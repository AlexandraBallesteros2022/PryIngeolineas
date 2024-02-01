/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingeolineas.controlador;

import ingeolineas.modelo.Contratista;
import ingeolineas.modelo.ContratistaDAO;
import ingeolineas.modelo.DocumentoSgsst;
import ingeolineas.modelo.DocumentoSgsstDAO;
import ingeolineas.modelo.Proyecto;
import ingeolineas.modelo.ProyectoDAO;
import ingeolineas.vista.FrmContratista;
import ingeolineas.vista.FrmDocumentoSgsst;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Usuario
 */
//Declarar el objeto de las clases FrmContratista, Contratista, ContratistaDAO.
public class ControladorDocumentoSgsst implements ActionListener{
    private FrmDocumentoSgsst fdocument;
    private DocumentoSgsst document;
    private DocumentoSgsstDAO documentD;
        String ruta_archivo = "";

    public ControladorDocumentoSgsst(FrmDocumentoSgsst fdocument, DocumentoSgsst document, DocumentoSgsstDAO documentD) throws SQLException {
        this.fdocument = fdocument;
        this.document = document;
        this.documentD = documentD;
    
        this.fdocument.jCbCodigoProyecto.addActionListener(this);
        this.fdocument.jBtConsultar.addActionListener(this);
        this.fdocument.jBtEliminar.addActionListener(this);
        this.fdocument.jBtGuardar.addActionListener(this);
        this.fdocument.jBtModificar.addActionListener(this);
        this.fdocument.jBtNuevo.addActionListener(this);
        this.fdocument.jBtSalir.addActionListener(this);
        this.fdocument.jBtSeleccionar.addActionListener(this);
        mostrarProyecto();
               
    }
           
        public void mostrarProyecto() throws SQLException {
        Proyecto proyec = new Proyecto();
        ProyectoDAO proyecDao = new ProyectoDAO();
        ArrayList proyecA = (ArrayList) proyecDao.consultarProyectos();
        for (int i = 0; i < proyecA.size(); i++) {
            proyec = (Proyecto) proyecA.get(i);
            fdocument.jCbCodigoProyecto.addItem(String.valueOf(proyec.getIdCodigoProyecto()));
        }
    
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == fdocument.jCbCodigoProyecto) {
            int codProyec = Integer.parseInt(fdocument.jCbCodigoProyecto.getSelectedItem().toString());
            Proyecto proyec = new Proyecto();
            ProyectoDAO proyecD = new ProyectoDAO();
           try {
               proyec = proyecD.consultarProyecto(codProyec);
           } catch (SQLException ex) {
               Logger.getLogger(ControladorDocumentoSgsst.class.getName()).log(Level.SEVERE, null, ex);
           }
            
            fdocument.jTxCodigoProyecto.setText(proyec.getNombre());      
        }
        
        //idCodigoSG, idCodigoProyecto, nombreDocuemto
        
        //Nuevo, guardar, modificar, eliminar, consultar y salir
       
        //Método que permite insertar nuevos datos
        if (e.getSource() == fdocument.jBtNuevo) {
            limpiarControles();
        }
        
        //Método que permite GUARDAR datos en la tabla DocumentoSG-SST
         if (e.getSource() == fdocument.jBtGuardar) {
           int idCodProyecto = Integer.parseInt(fdocument.jCbCodigoProyecto.getSelectedItem().toString());
           String nombreDocumento = fdocument.jTxNombreDocumento.getText();
           File ruta = new File(ruta_archivo);
           byte[] pdf = new byte[(int) ruta.length()];
           try {
               InputStream input = new FileInputStream(ruta);
               input.read(pdf);
           } catch (IOException ex) {
               Logger.getLogger(ControladorDocumentoSgsst.class.getName()).log(Level.SEVERE, null, ex);
           }
            //po.setArchivopdf(pdf);
           document = new DocumentoSgsst(idCodProyecto, nombreDocumento, pdf);
        if (documentD.adicionar(document)) {
                JOptionPane.showMessageDialog(fdocument, "El documento se resgistro correctamente");
                limpiarControles();
            } else {
                JOptionPane.showMessageDialog(fdocument, "El documento NO se puedo registrar");
            }
        }
         
        //Método que permite MODIFICAR datos de la tabla DocumentoSG-SST
         if (e.getSource() == fdocument.jBtModificar) {
            int idCodDocumento = Integer.parseInt(fdocument.jTxCodigosg.getText());
            int codProyecto = Integer.parseInt(fdocument.jCbCodigoProyecto.getSelectedItem().toString());
            String nombreDocumento = fdocument.jTxNombreDocumento.getText();
            File ruta = new File(ruta_archivo);
            byte[] pdf = new byte[(int) ruta.length()];
            
           try {
               InputStream input = new FileInputStream(ruta);
               input.read(pdf);
           } catch (IOException ex) {
               Logger.getLogger(ControladorDocumentoSgsst.class.getName()).log(Level.SEVERE, null, ex);
           }
            document = new DocumentoSgsst(idCodDocumento, codProyecto, nombreDocumento, pdf);
            try {
                if (documentD.actualizar(document)){
                JOptionPane.showMessageDialog(fdocument, "El documento se actualizó adecuadamente");
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(fdocument, "El documento NO se puedo actualizar");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ControladorDocumentoSgsst.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Método que permite ELIMINAR datos en la tabla DocumentoSG-SST
        if (e.getSource() == fdocument.jBtEliminar) {
            int idCodDocumento = Integer.parseInt(fdocument.jTxCodigosg.getText());
            int respuesta = JOptionPane.showConfirmDialog(fdocument, "¿Esta seguro de eliminar el registro", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                File ruta = new File(ruta_archivo);
                byte[] pdf = new byte[(int) ruta.length()];
                
                try {
                    InputStream input = new FileInputStream(ruta);
                    input.read(pdf);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorDocumentoSgsst.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (documentD.eliminar(idCodDocumento)){
                    JOptionPane.showMessageDialog(fdocument, "Se eliminó con éxito");
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(fdocument, "No se eliminó - Verificar datos");
                }
            }
        }
        
       //Método que permite CONSULTAR datos de la tabla DocumentoSG-SST
        if (e.getSource() == fdocument.jBtConsultar) {
            int codDocumento = Integer.parseInt(JOptionPane.showInputDialog(fdocument, "Código del documento a consultar"));
            try {
                document = documentD.consultarDocumento(codDocumento);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorDocumentoSgsst.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }
            if (document != null) {
                fdocument.jTxCodigosg.setText(String.valueOf(document.getIdCodigoSg()));
                fdocument.jCbCodigoProyecto.setSelectedItem(document.getIdCodigoProyecto());
                fdocument.jTxNombreDocumento.setText(document.getNombre());
                  
            } else {
                JOptionPane.showMessageDialog(fdocument, "El documento consultado no se encuentra en la BD");
            }
        }
        
        
        
        //Método que permite salir del formulario
        if (e.getSource() == fdocument.jBtSalir) {
            int respuesta = JOptionPane.showConfirmDialog(fdocument, "¿Está seguro que desea salir?", "Fin productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                fdocument.dispose();
            }
        }
        
        if (e.getSource() == fdocument.jBtSeleccionar){
            seleccionar_pdf();
        }
        
    
}
    //Método que permite seleccionar el PDF
           
    public void seleccionar_pdf() {
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fi = new FileNameExtensionFilter("pdf", "pdf");
        j.setFileFilter(fi);
        int se = j.showOpenDialog(fdocument);
        if (se == 0) {
            fdocument.jBtSeleccionar.setText("" + j.getSelectedFile().getName());
            ruta_archivo = j.getSelectedFile().getAbsolutePath();

        } else {
        }
          }

        public void limpiarControles() {
        java.util.Date date = new java.sql.Date(new java.util.Date().getTime());
        fdocument.jTxCodigosg.setText("");
        fdocument.jTxCodigoProyecto.setText("");
        fdocument.jTxNombreDocumento.setText("");
           }
    }

    

