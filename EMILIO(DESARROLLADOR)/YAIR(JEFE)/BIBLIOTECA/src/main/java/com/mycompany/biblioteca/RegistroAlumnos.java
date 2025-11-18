package com.mycompany.biblioteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegistroAlumnos {

    String FOTOPRG;
    String NOMBREPRG;
    String NoControlPRG;
    String SEMESTREPRG;
    String GRUPOPRG;
    String ESPECIALIDADPRG;
    String TURNOPRG;
    String NOMBREPMPRG;
    String ESTATUSPRG;
    String TIPOPRG;

    public String getFOTOPRG() { return FOTOPRG; }
    public void setFOTOPRG(String FOTOPRG) { this.FOTOPRG = FOTOPRG; }

    public String getNOMBREPRG() { return NOMBREPRG; }
    public void setNOMBREPRG(String NOMBREPRG) { this.NOMBREPRG = NOMBREPRG; }

    public String getNoControlPRG() { return NoControlPRG; }
    public void setNoControlPRG(String NoControlPRG) { this.NoControlPRG = NoControlPRG; }

    public String getSEMESTREPRG() { return SEMESTREPRG; }
    public void setSEMESTREPRG(String SEMESTREPRG) { this.SEMESTREPRG = SEMESTREPRG; }

    public String getGRUPOPRG() { return GRUPOPRG; }
    public void setGRUPOPRG(String GRUPOPRG) { this.GRUPOPRG = GRUPOPRG; }

    public String getESPECIALIDADPRG() { return ESPECIALIDADPRG; }
    public void setESPECIALIDADPRG(String ESPECIALIDADPRG) { this.ESPECIALIDADPRG = ESPECIALIDADPRG; }

    public String getTURNOPRG() { return TURNOPRG; }
    public void setTURNOPRG(String TURNOPRG) { this.TURNOPRG = TURNOPRG; }

    public String getNOMBREPMPRG() { return NOMBREPMPRG; }
    public void setNOMBREPMPRG(String NOMBREPMPRG) { this.NOMBREPMPRG = NOMBREPMPRG; }

    public String getESTATUSPRG() { return ESTATUSPRG; }
    public void setESTATUSPRG(String ESTATUSPRG) { this.ESTATUSPRG = "A"; }

    public String getTIPOPRG() { return TIPOPRG; }
    public void setTIPOPRG(String TIPOPRG) { this.TIPOPRG = "ALUMNO"; }

    public boolean guardarFotoEnCarpeta(String rutaOriginal) {
        File origen = new File(rutaOriginal);

        if (!origen.exists()) {
            JOptionPane.showMessageDialog(null, "La imagen seleccionada no existe.");
            return false;
        }

        File carpetaFotos = new File("fotos");
        if (!carpetaFotos.exists()) {
            carpetaFotos.mkdirs();
        }

        File destino = new File(carpetaFotos, origen.getName());

        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            setFOTOPRG(destino.getName()); // Solo el nombre del archivo
            return true;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la imagen:\n" + e.getMessage());
            return false;
        }
    }

    public void CHECAREG(
        JTextField FOTOFRM, JTextField NOMBRECFRM, JTextField NoControlFRM,
        JComboBox SEMESTREFRM, JComboBox GRUPOFRM, JComboBox TURNOFRM,
        JComboBox ESPECIALIDADFRM
    ) {
        String rutaFotoOriginal = FOTOFRM.getText();

        if (!guardarFotoEnCarpeta(rutaFotoOriginal)) {
            return;
        }

        setNOMBREPRG(NOMBRECFRM.getText().toUpperCase());
        setNoControlPRG(NoControlFRM.getText().toUpperCase());
        setSEMESTREPRG(SEMESTREFRM.getSelectedItem().toString().toUpperCase());
        setGRUPOPRG(GRUPOFRM.getSelectedItem().toString().toUpperCase());
        setESPECIALIDADPRG(ESPECIALIDADFRM.getSelectedItem().toString().toUpperCase());
        setTURNOPRG(TURNOFRM.getSelectedItem().toString().toUpperCase());
        setESTATUSPRG("A");
        setTIPOPRG("ALUMNO");

        String sqlAlumno = "INSERT INTO alumnos(foto, nombre, nocontrol, semestre, grupo, especialidad, turno, estatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuarios(nocontrol, user, password, tipo, estatus) VALUES (?, ?, ?, ?, ?)";

        if (!getESTATUSPRG().equals("X") && getTIPOPRG().equals("ALUMNO")) {
            try {
                CConexion cbd = new CConexion();
                Connection conn = cbd.estableceConexion();

                if (conn == null) {
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                    return;
                }

                PreparedStatement psAlumno = conn.prepareStatement(sqlAlumno);
                psAlumno.setString(1, getFOTOPRG());
                psAlumno.setString(2, getNOMBREPRG());
                psAlumno.setString(3, getNoControlPRG());
                psAlumno.setString(4, getSEMESTREPRG());
                psAlumno.setString(5, getGRUPOPRG());
                psAlumno.setString(6, getESPECIALIDADPRG());
                psAlumno.setString(7, getTURNOPRG());
                psAlumno.setString(8, getESTATUSPRG());
                psAlumno.executeUpdate();

                PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
                psUsuario.setString(1, getNoControlPRG());
                psUsuario.setString(2, getNOMBREPRG());
                psUsuario.setString(3, "CBTis198**");
                psUsuario.setString(4, getTIPOPRG());
                psUsuario.setString(5, "A");
                psUsuario.executeUpdate();

                JOptionPane.showMessageDialog(null, "Alumno y usuario registrados exitosamente.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al registrar alumno/usuario:\n" + e.getMessage());
            }
        }
    }
}
