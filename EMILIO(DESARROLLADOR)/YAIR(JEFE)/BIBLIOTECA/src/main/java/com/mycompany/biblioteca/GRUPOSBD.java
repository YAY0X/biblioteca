package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class GRUPOSBD {
    
    public void cargarGrupos(JComboBox<String> GRUPOFRM) {
        try {
            // Usamos tu clase de conexión
            CConexion cbd = new CConexion();
            Connection con = cbd.estableceConexion();
            
            String sql = "SELECT descripcion FROM gg";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            GRUPOFRM.removeAllItems(); // limpia el combo
            
            while (rs.next()) {
                GRUPOFRM.addItem(rs.getString("GRUPO"));
            }
            
            rs.close();
            st.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al cargar grupos: " + e.toString());
        }
    }
    
}
