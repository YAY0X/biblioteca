package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class LIBROSBD2 {
    public String obtenerRutaPorNombre(String nombreLibro) {
        
        String ruta = null;
        try {
            CConexion cbd = new CConexion();
            Connection con = cbd.estableceConexion();
            String sql = "SELECT ruta_documento FROM libro WHERE NOMBRE = '" + nombreLibro + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                ruta = rs.getString("ruta_documento");
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ruta: " + e.toString());
        }
        return ruta;
    }
}