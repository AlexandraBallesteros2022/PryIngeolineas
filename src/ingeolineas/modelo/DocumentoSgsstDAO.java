/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingeolineas.modelo;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class DocumentoSgsstDAO {
     Conexion cn = new Conexion();
    //Definir un objeto para validar conexción
    Connection con;
    //Verificar las instrucciones de SQL y ejecutarlas
    PreparedStatement ps;
    //Carga el resultado de la consulta con un objeto de la clase
    ResultSet rs;
    //Instanciar la clase inquilinos, es decir, crear un objeto de la clase inquilinos
    DocumentoSgsst documento = new  DocumentoSgsst();
    //Método para insertar datos a inquilinos en la BD (base de datos)
public boolean adicionar(DocumentoSgsst documento) {
        String sql = "insert into documentosgsst(idCodigoProyecto, nombreDocumento, ArchivoPDF)values(?,?,?)";
         con = cn.getConnection();
try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, documento.getIdCodigoProyecto());
            ps.setString(2, documento.getNombre());   
            ps.setBytes(3, documento.getArchivoPDF());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            return false;
        }
        return true;
    
}
public DocumentoSgsst consultarDocumento(int idCodigoSg) throws SQLException {
        String sql = "select * from documentosgsst where idCodigoSG=" + idCodigoSg+ " ";
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            documento.setIdCodigoSg(rs.getInt("idCodigoSG"));
            documento.setIdCodigoProyecto(rs.getInt("idCodigoProyecto"));
            documento.setNombre(rs.getString("nombreDocumento")); 
            documento.setArchivoPDF(rs.getBytes("ArchivoPDF"));
             
            return documento;
        } else {

            return null;
        }
    }
public boolean actualizar(DocumentoSgsst documento) throws SQLException {
        String sql = "update documentosgsst set idCodigoSG=?, idCodigoProyecto=?, nombreDocumento=?, ArchivoPDF=? where idCodigoSG=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,documento.getIdCodigoProyecto());
            ps.setString(2,documento.getNombre());
            ps.setInt(3,documento.getIdCodigoSg());
            ps.setBytes(4,documento.getArchivoPDF());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            return false;
        }
        return true;
    
}
public boolean eliminar(int idCodigoSG) {
        String sql = "Delete from documentosgsst where idCodigoSG=" + idCodigoSG;
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

//Método que permite consultar todos los Documentos
    public ArrayList consultarDocumentos() throws SQLException {
        String sql = "Select * from documentosgsst";
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        ArrayList listDocument = new ArrayList();
        while (rs.next()) {
            documento.setIdCodigoSg(rs.getInt("codigo"));
            documento.setNombre(rs.getString("nombreDocumento"));
            documento.setIdCodigoProyecto(rs.getInt("idCodigoProyecto"));
            listDocument.add(new DocumentoSgsst(documento.getIdCodigoSg(), documento.getIdCodigoProyecto(), documento.getNombre(), documento.getArchivoPDF()));
        }
        return listDocument;
    }
    
    //Método que permite mostrar el PDF contenido en la BD
    public void ejecutar_archivoPDF(int id) throws SQLException{
        Conexion cn = new Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        byte[] b = null;
        
        try{
            ps = cn.getConnection().prepareStatement("SELECT ArchivoPDF from documentosgsst WHERE idCodigoSG=?");//FALTA INGRESAR EL WHERE
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                b = rs.getBytes(1);
            }
            
            InputStream bos = new ByteArrayInputStream(b);
            
            int tamanoInput = bos.available();
            byte[] datosPDF = new byte[tamanoInput];
            bos.read(datosPDF, 0, tamanoInput);

            OutputStream out = new FileOutputStream("new.pdf");
            out.write(datosPDF);

            //abrir archivo
            out.close();
            bos.close();
            ps.close();
            rs.close();
            cn.desconectar();

        } catch (IOException | NumberFormatException | SQLException ex) {
            System.out.println("Error al abrir archivo PDF " + ex.getMessage());
        }
    }
}

