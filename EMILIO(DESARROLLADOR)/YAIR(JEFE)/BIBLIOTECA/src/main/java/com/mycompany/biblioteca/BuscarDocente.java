package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BuscarDocente {

    public void buscar(JTextField nombreField3, JTextField numControlField3, JTextField fotoField3,
                       JTextField especialidadField3, JTextField turnoField3, JTextField correoField3) {
        
        String sql = "SELECT * FROM docentes WHERE nombre = ?";
        
        try {
            CConexion cbd = new CConexion();
            Connection conn = cbd.estableceConexion();
            
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                return;
            }
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombreField3.getText().trim().toUpperCase());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                numControlField3.setText(rs.getString("num_control"));
                fotoField3.setText(rs.getString("foto"));
                nombreField3.setText(rs.getString("nombre"));
                especialidadField3.setText(rs.getString("especialidad"));
                turnoField3.setText(rs.getString("turno"));
                correoField3.setText(rs.getString("correo"));
                
                JOptionPane.showMessageDialog(null, "Docente encontrado.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un docente con ese nombre.");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar docente: " + e.getMessage());
        }
    }
}
