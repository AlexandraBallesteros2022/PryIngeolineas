/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingeolineas.controlador;

import ingeolineas.modelo.Usuario;
import ingeolineas.modelo.UsuarioDAO;
import ingeolineas.vista.FrmUsuarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author braya
 */
public class ControladorUsuario implements ActionListener {

    private FrmUsuarios fuser;
    private UsuarioDAO userD;
    private Usuario user;

    public ControladorUsuario(FrmUsuarios fuser, UsuarioDAO userD, Usuario user) {
        this.fuser = fuser;
        this.userD = userD;
        this.user = user;
        this.fuser.jBtRegistrarse.addActionListener(this);
        this.fuser.jBtCancelar.addActionListener(this);

    }

    public void validar() {
        if (fuser.jTxIdUsuario.getText().isEmpty()) {
            fuser.jLbIdUsuario.setText("*campo usuario requerido");
        } else {
            fuser.jLbIdUsuario.setText(" ");
        }
        if (fuser.jTxNombreUsuario.getText().isEmpty()) {
            fuser.jLbNomUsuario.setText("*campo usuario requerido");
        } else {
            fuser.jLbNomUsuario.setText(" ");
        }
        if (fuser.jPsContrasenia.getText().isEmpty()) {
            fuser.jPsContrasenia.setText("*campo contraseña requerido");
        } else {
            fuser.jPsContrasenia.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fuser.jBtRegistrarse) {
            String Idusuario = fuser.jTxIdUsuario.getText();
            String nom = fuser.jTxNombreUsuario.getText();
            String usuario = fuser.jTxUsuario.getText();
            String contra = new String(fuser.jPsContrasenia.getPassword());
            String correo = fuser.jTxCorreo.getText();
            user = new Usuario(Idusuario, nom, usuario, contra, correo);
            if (userD.adicionar(user)) {
                JOptionPane.showMessageDialog(fuser, "El Usuario se registro adecuadamente");
                //limpiarcontroles();
            } else {
                JOptionPane.showMessageDialog(fuser, "El Usuario NO se pudo registrar");
            }
        }

    }

}
