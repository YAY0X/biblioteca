package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class LIBROSBD {
    public void cargarLibros(JComboBox<String> LIBROSFRM) {
        try {
            // Usamos tu clase de conexión
            CConexion cbd = new CConexion();
            Connection con = cbd.estableceConexion();
            
            String sql = "SELECT NOMBRE FROM libro";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            LIBROSFRM.removeAllItems(); // limpia el combo
            
            while (rs.next()) {
                LIBROSFRM.addItem(rs.getString("NOMBRE"));
            }
            
            rs.close();
            st.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al cargar libros: " + e.toString());
        }
    }
    
}