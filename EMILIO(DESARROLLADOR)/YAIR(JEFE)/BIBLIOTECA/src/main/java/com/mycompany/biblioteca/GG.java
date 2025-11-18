package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GG {
    private String SEMESTREPRG;
    private String GRUPOPRG;
    private String ESPPRG;
        
    public String getSEMESTREPRG() { return SEMESTREPRG; }
    public void setSEMESTREPRG(String SEMESTREPRG) { this.SEMESTREPRG = SEMESTREPRG; } 
    
    public String getGRUPOPRG() { return GRUPOPRG; }
    public void setGRUPOPRG(String GRUPOPRG) { this.GRUPOPRG = GRUPOPRG; }
    
    public String getESPPRG() { return ESPPRG; }
    public void setESPPRG(String ESPPRG) { this.ESPPRG = ESPPRG; }
    
    public void CHECAREG(JTextField agsemesfrm, JTextField aggrupofrm, JComboBox asespfrm) {
        setSEMESTREPRG(agsemesfrm.getText().toUpperCase());
        setGRUPOPRG(aggrupofrm.getText().toUpperCase());
        setESPPRG(asespfrm.getSelectedItem().toString().toUpperCase());        
        
        String sqlgg = "INSERT INTO gg (SEMESTRE, GRUPO, ESP) VALUES (?, ?, ?)";
        
        try {
            CConexion cbd = new CConexion();
            Connection conn = cbd.estableceConexion();

            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                return;
            }

            PreparedStatement psSem = conn.prepareStatement(sqlgg);
            psSem.setString(1, getSEMESTREPRG());
            psSem.setString(2, getGRUPOPRG());
            psSem.setString(3, getESPPRG());
            
            int filas = psSem.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Grado y grupo registrados correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el grado y grupo.");
            }

            psSem.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el grado y grupo:\n" + e.getMessage());
        }
    }
    
    public void AGESP(JTextField agespfrm) {
        
        setESPPRG(agespfrm.getText().toUpperCase());

        String sqlgg = "INSERT INTO gg (ESP) VALUES (?)";

        try {
            CConexion cbd = new CConexion();
            Connection conn = cbd.estableceConexion();

            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                return;
            }

            PreparedStatement psEsp = conn.prepareStatement(sqlgg);
            psEsp.setString(1, getESPPRG());

            int filas = psEsp.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Especialidad registrada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar la especialidad.");
            }

            psEsp.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la especialidad:\n" + e.getMessage());
        }
    }
}

