package com.mycompany.biblioteca;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class EditarAl {

    // Variables para guardar datos del alumno
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

    // Getters y setters
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
    public void setESTATUSPRG(String ESTATUSPRG) { this.ESTATUSPRG = ESTATUSPRG; }

    public String getTIPOPRG() { return TIPOPRG; }
    public void setTIPOPRG(String TIPOPRG) { this.TIPOPRG = TIPOPRG; }

    // Método para guardar la foto en carpeta local "fotos"
    public boolean guardarFotoEnCarpeta(String rutaOriginal) {
        if (rutaOriginal == null || rutaOriginal.isEmpty()) {
            // No se cambia la foto, asumimos que no hay que copiar nada
            return true;
        }
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

            setFOTOPRG(destino.getName()); // Guardamos solo el nombre del archivo
            return true;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la imagen:\n" + e.getMessage());
            return false;
        }
    }

    // Método para editar alumno usando sólo JTextField
    public void editarAlumno(
        JTextField FOTOFRM2, JTextField NOMBRECFRM2, JTextField NoControlFRM1,
        JTextField SEMESTREFRM2, JTextField GRUPOFRM2, JTextField TURNOFRM2,
        JTextField ESPECIALIDADFRM2
    ) {
        String rutaFotoOriginal = FOTOFRM2.getText();

        if (!guardarFotoEnCarpeta(rutaFotoOriginal)) {
            return;
        }

        setNOMBREPRG(NOMBRECFRM2.getText().toUpperCase());
        setNoControlPRG(NoControlFRM1.getText().toUpperCase());
        setSEMESTREPRG(SEMESTREFRM2.getText().toUpperCase());
        setGRUPOPRG(GRUPOFRM2.getText().toUpperCase());
        setESPECIALIDADPRG(ESPECIALIDADFRM2.getText().toUpperCase());
        setTURNOPRG(TURNOFRM2.getText().toUpperCase());
        setESTATUSPRG("A");
        setTIPOPRG("ALUMNO");

        String sqlUpdateAlumno = "UPDATE alumnos SET foto=?, nombre=?, semestre=?, grupo=?, especialidad=?, turno=?, estatus=? WHERE nocontrol=?";
        String sqlUpdateUsuario = "UPDATE usuarios SET user=?, password=?, tipo=?, estatus=? WHERE nocontrol=?";

        try {
            CConexion cbd = new CConexion();
            Connection conn = cbd.estableceConexion();

            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                return;
            }

            PreparedStatement psAlumno = conn.prepareStatement(sqlUpdateAlumno);
            psAlumno.setString(1, getFOTOPRG());
            psAlumno.setString(2, getNOMBREPRG());
            psAlumno.setString(3, getSEMESTREPRG());
            psAlumno.setString(4, getGRUPOPRG());
            psAlumno.setString(5, getESPECIALIDADPRG());
            psAlumno.setString(6, getTURNOPRG());
            psAlumno.setString(7, getESTATUSPRG());
            psAlumno.setString(8, getNoControlPRG());
            int rowsAlumno = psAlumno.executeUpdate();

            PreparedStatement psUsuario = conn.prepareStatement(sqlUpdateUsuario);
            psUsuario.setString(1, getNOMBREPRG());
            psUsuario.setString(2, "CBTis198**"); // Puedes modificar la lógica de contraseña si quieres
            psUsuario.setString(3, getTIPOPRG());
            psUsuario.setString(4, "A");
            psUsuario.setString(5, getNoControlPRG());
            int rowsUsuario = psUsuario.executeUpdate();

            if (rowsAlumno > 0 && rowsUsuario > 0) {
                JOptionPane.showMessageDialog(null, "Alumno y usuario actualizados correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el alumno para actualizar.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar alumno/usuario:\n" + e.getMessage());
        }
    }

    // Método para buscar alumno y llenar los JTextFields y JLabel con imagen
    public void buscar(
        JTextField NOMBRECFRM2, JTextField SEMESTREFRM2, JTextField TURNOFRM2,
        JTextField GRUPOFRM2, JTextField FOTOFRM2, JTextField ESPECIALIDADFRM2, 
        JTextField NoControlFRM1, JLabel jLabel18
    ) {

        String sql = "SELECT * FROM alumnos WHERE nombre = ?";

        CConexion cbd = new CConexion();
        try (Connection conn = cbd.estableceConexion()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, NOMBRECFRM2.getText().trim().toUpperCase());
                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        // Rellenar campos
                        NOMBRECFRM2.setText(rs.getString("nombre"));
                        SEMESTREFRM2.setText(rs.getString("semestre"));
                        TURNOFRM2.setText(rs.getString("turno"));
                        GRUPOFRM2.setText(rs.getString("grupo"));
                        ESPECIALIDADFRM2.setText(rs.getString("especialidad"));
                        NoControlFRM1.setText(rs.getString("nocontrol"));

                        // Obtener ruta de foto y mostrar en el campo
                        String rutaFoto = rs.getString("foto");
                        if (rutaFoto == null) rutaFoto = "";
                        FOTOFRM2.setText(rutaFoto);

                        // Cargar imagen en JLabel
                        if (!rutaFoto.isEmpty()) {
                            File f = new File("fotos/" + rutaFoto); // Asumiendo que la foto está en carpeta "fotos"
                            if (f.exists() && f.isFile()) {
                                try {
                                    BufferedImage img = ImageIO.read(f);
                                    if (img != null) {
                                        int w = jLabel18.getWidth();
                                        int h = jLabel18.getHeight();
                                        if (w <= 0 || h <= 0) {
                                            Dimension d = jLabel18.getPreferredSize();
                                            w = d.width > 0 ? d.width : 120;
                                            h = d.height > 0 ? d.height : 120;
                                        }
                                        Image imgEscalada = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                                        ImageIcon icon = new ImageIcon(imgEscalada);
                                        SwingUtilities.invokeLater(() -> {
                                            jLabel18.setIcon(icon);
                                            jLabel18.revalidate();
                                            jLabel18.repaint();
                                        });
                                    } else {
                                        SwingUtilities.invokeLater(() -> jLabel18.setIcon(null));
                                    }
                                } catch (IOException ex) {
                                    SwingUtilities.invokeLater(() -> jLabel18.setIcon(null));
                                }
                            } else {
                                SwingUtilities.invokeLater(() -> jLabel18.setIcon(null));
                            }
                        } else {
                            SwingUtilities.invokeLater(() -> jLabel18.setIcon(null));
                        }

                        JOptionPane.showMessageDialog(null, "Alumno encontrado.");
                    } else {
                        // Si no se encuentra alumno limpiar campos
                        SEMESTREFRM2.setText("");
                        TURNOFRM2.setText("");
                        GRUPOFRM2.setText("");
                        ESPECIALIDADFRM2.setText("");
                        NoControlFRM1.setText("");
                        FOTOFRM2.setText("");
                        SwingUtilities.invokeLater(() -> jLabel18.setIcon(null));

                        JOptionPane.showMessageDialog(null, "No se encontró un alumno con ese nombre.");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar alumno: " + e.getMessage());
        }
    }

}
