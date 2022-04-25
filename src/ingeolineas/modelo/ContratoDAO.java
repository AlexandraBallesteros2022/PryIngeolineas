/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingeolineas.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ContratoDAO {

    //Definir un objeto de la clase conexión
    Conexion cn = new Conexion();
    //Definir un objeto para validar conexción
    Connection con;
    //Verificar las instrucciones de SQL y ejecutarlas
    PreparedStatement ps;
    //Carga el resultado de la consulta con un objeto de la clase
    ResultSet rs;
    //Instanciar la clase contrato, es decir, crear un objeto de la clase contrato
    Contrato contra = new Contrato();

//Método para insertar datos a contrato en la BD (base de datos)
    public boolean adicionar(Contrato contra) {
        String sql = "insert into contrato(idCedula, valorMensual, Ciudad) values (?,?,?)";
        con = cn.getConnection(); //
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, contra.getIdCedula());
            ps.setInt(2, contra.getValorMensual());
            ps.setString(3, contra.getCiudad());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            return false;
        }
        return true;
    }

    //Método que permite consultar un solo Contrato
    public Contrato consultarContrato(int idContrato) throws SQLException {
        String sql = "Select * from contrato where  idContrato='" + idContrato + "'";
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            contra.setIdContrato(rs.getInt("idContrato"));
            contra.setIdCedula(rs.getString("idCedula"));
            contra.setValorMensual(rs.getInt("valorMensual"));
            contra.setCiudad(rs.getString("Ciudad"));

            return contra;
        } else {
            return null;
        }
    }
    
    //Método que permite consultar todos Contrato
    public ArrayList consultarContrato() throws SQLException {
        String sql = "Select * from contrato";
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        ArrayList listContra = new ArrayList();
        while (rs.next()) {
            contra.setIdContrato(rs.getInt("idContrato"));
            contra.setIdCedula(rs.getString("idCedula"));
            contra.setValorMensual(rs.getInt("valorMensual"));
            contra.setCiudad(rs.getString("Ciudad"));
            listContra.add(new Contrato(contra.getIdContrato(), contra.getIdCedula(), contra.getValorMensual(),
            contra.getCiudad()));
                    
        }
        return listContra;
    }
    
    //Método para actualizar datos a contratista en BD
    public boolean actualizar(Contrato contra) throws SQLException {
        String sql = "update contrato set idContrato=?, "
                + "idCedula=?, valorContrato=?, Ciudad=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, contra.getIdContrato());
            ps.setString(2, contra.getIdCedula());
            ps.setInt(3, contra.getValorMensual()); 
             ps.setString(4, contra.getIdCedula());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            return false;
        }
        return true;
        
    }
    
    //Método que permite eliminar datos
    public boolean eliminar(int idContrato) {
        String sql = "Delete from contrato where idContrato=" + idContrato;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            return false;
        }
        return true;
    }
}


