package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BuscarAlumno {
    

    public void buscar(JTextField NOMBRECFRM1, JTextField SEMESTREFRM1, JTextField TURNOFRM1,
                       JTextField GRUPOFRM1, JTextField FOTOFRM1, JTextField ESPECIALIDADFRM1, 
                       JTextField NoControlFRM, JLabel jLabel16) {

        String sql = "SELECT * FROM alumnos WHERE nombre = ?";

        CConexion cbd = new CConexion();
        try (Connection conn = cbd.estableceConexion()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, NOMBRECFRM1.getText().trim().toUpperCase());
                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        // Rellenar campos
                        NOMBRECFRM1.setText(rs.getString("nombre"));
                        SEMESTREFRM1.setText(rs.getString("semestre"));
                        TURNOFRM1.setText(rs.getString("turno"));
                        GRUPOFRM1.setText(rs.getString("grupo"));
                        ESPECIALIDADFRM1.setText(rs.getString("especialidad"));
                        NoControlFRM.setText(rs.getString("nocontrol"));

                        // Obtener ruta de foto y mostrar en el campo
                        String rutaFoto = rs.getString("foto");
                        if (rutaFoto == null) rutaFoto = "";
                        FOTOFRM1.setText(rutaFoto);

                        // Si hay ruta, intentar cargar la imagen con ImageIO (más fiable)
                        if (!rutaFoto.isEmpty()) {
                            File f = new File(rutaFoto);
                            if (f.exists() && f.isFile()) {
                                try {
                                    BufferedImage img = ImageIO.read(f);
                                    if (img != null) {
                                        int w = jLabel16.getWidth();
                                        int h = jLabel16.getHeight();
                                        if (w <= 0 || h <= 0) { // fallback si aún no fue mostrado
                                            Dimension d = jLabel16.getPreferredSize();
                                            w = d.width > 0 ? d.width : 120;
                                            h = d.height > 0 ? d.height : 120;
                                        }
                                        Image imgEscalada = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                                        ImageIcon icon = new ImageIcon(imgEscalada);
                                        SwingUtilities.invokeLater(() -> {
                                            jLabel16.setIcon(icon);
                                            jLabel16.revalidate();
                                            jLabel16.repaint();
                                        });
                                    } else {
                                        SwingUtilities.invokeLater(() -> jLabel16.setIcon(null));
                                    }
                                } catch (IOException ex) {
                                    SwingUtilities.invokeLater(() -> jLabel16.setIcon(null));
                                }
                            } else {
                                // archivo no existe: limpiar icono
                                SwingUtilities.invokeLater(() -> jLabel16.setIcon(null));
                                // (opcional) mostrar notificación:
                                // JOptionPane.showMessageDialog(null, "Archivo de imagen no encontrado: " + rutaFoto);
                            }
                        } else {
                            // sin ruta: limpiar icono
                            SwingUtilities.invokeLater(() -> jLabel16.setIcon(null));
                        }

                        JOptionPane.showMessageDialog(null, "Alumno encontrado.");
                    } else {
                        // no hay resultado: limpiar campos y foto
                        SEMESTREFRM1.setText("");
                        TURNOFRM1.setText("");
                        GRUPOFRM1.setText("");
                        ESPECIALIDADFRM1.setText("");
                        NoControlFRM.setText("");
                        FOTOFRM1.setText("");
                        SwingUtilities.invokeLater(() -> jLabel16.setIcon(null));

                        JOptionPane.showMessageDialog(null, "No se encontró un alumno con ese nombre.");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar alumno: " + e.getMessage());
        }
        
    }
}
