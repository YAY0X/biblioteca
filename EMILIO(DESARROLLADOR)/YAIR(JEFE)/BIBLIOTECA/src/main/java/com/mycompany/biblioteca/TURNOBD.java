package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class TURNOBD {
    public void cargarTurnos(JComboBox<String> TURNOFRM) {
        try {
            // Usamos tu clase de conexión
            CConexion cbd = new CConexion();
            Connection con = cbd.estableceConexion();
            
            String sql = "SELECT TURNOS FROM turno";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            TURNOFRM.removeAllItems(); // limpia el combo
            
            while (rs.next()) {
                TURNOFRM.addItem(rs.getString("TURNOS"));
            }
            
            rs.close();
            st.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al cargar especialidades: " + e.toString());
        }
    }

}
