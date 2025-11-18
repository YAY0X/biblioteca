package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class EspecialidadesDAO {
    
    public void cargarEspecialidades(JComboBox<String> ESPECIALIDADFRM) {
        try {
            // Usamos tu clase de conexión
            CConexion cbd = new CConexion();
            Connection con = cbd.estableceConexion();
            
            String sql = "SELECT ESP FROM gg";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            ESPECIALIDADFRM.removeAllItems(); // limpia el combo
            
            while (rs.next()) {
                ESPECIALIDADFRM.addItem(rs.getString("ESP"));
            }
            
            rs.close();
            st.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al cargar especialidades: " + e.toString());
        }
    }
}
