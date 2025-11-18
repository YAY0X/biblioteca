package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class SEMESTREBD {
    public void cargarSemestre(JComboBox<String> SEMESTREFRM) {
        try {
            // Usamos tu clase de conexión
            CConexion cbd = new CConexion();
            Connection con = cbd.estableceConexion();
            
            String sql = "SELECT SEMESTRE FROM gg";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            SEMESTREFRM.removeAllItems(); // limpia el combo
            
            while (rs.next()) {
                SEMESTREFRM.addItem(rs.getString("SEMESTRE"));
            }
            
            rs.close();
            st.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al cargar especialidades: " + e.toString());
        }
    }

}
