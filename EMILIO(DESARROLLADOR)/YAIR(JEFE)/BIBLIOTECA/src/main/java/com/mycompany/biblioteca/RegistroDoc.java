package com.mycompany.biblioteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegistroDoc {
    
    String numControl;
    String foto;
    String nombre;
    String especialidad;
    String turno;
    String correo;
    String estatus;
    String tipo;

    // Getters y setters
    public String getNumControl() { return numControl; }
    public void setNumControl(String numControl) { this.numControl = numControl; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = "A"; }  // Siempre "A"

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = "DOCENTE"; }   // Siempre "DOCENTE"

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

            setFoto(destino.getAbsolutePath()); // Solo nombre del archivo guardado
            return true;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la imagen:\n" + e.getMessage());
            return false;
        }
    }

    public void registrarDocente(
        JTextField fotoField, JTextField nombreField, JTextField numControlField,
        JComboBox SEMESTREFRM3, JComboBox TURNOFRM3, JTextField correoField
    ) {
        String rutaFotoOriginal = fotoField.getText();

        if (!guardarFotoEnCarpeta(rutaFotoOriginal)) {
            return;
        }

        setNombre(nombreField.getText().trim().toUpperCase());
        setNumControl(numControlField.getText().trim().toUpperCase());
        setEspecialidad(SEMESTREFRM3.getSelectedItem().toString().toUpperCase());
        setTurno(TURNOFRM3.getSelectedItem().toString().toUpperCase());
        setCorreo(correoField.getText().trim());
        setEstatus("A");
        setTipo("DOCENTE");

        String sqlDocente = "INSERT INTO docentes(num_control, foto, nombre, especialidad, turno, correo, estatus, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuarios(nocontrol,user, password, tipo, estatus) VALUES (?, ?, ?, ?,?)";

        if (!getEstatus().equals("X") && getTipo().equals("DOCENTE")) {
            try {
                CConexion cbd = new CConexion();
                Connection conn = cbd.estableceConexion();

                if (conn == null) {
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                    return;
                }

                PreparedStatement psDocente = conn.prepareStatement(sqlDocente);
                psDocente.setString(1, getNumControl());
                psDocente.setString(2, getFoto());
                psDocente.setString(3, getNombre());
                psDocente.setString(4, getEspecialidad());
                psDocente.setString(5, getTurno());
                psDocente.setString(6, getCorreo());
                psDocente.setString(7, getEstatus());
                psDocente.setString(8, getTipo());
                psDocente.executeUpdate();

                PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
                psUsuario.setString(1, getNumControl());
                psUsuario.setString(2, getNombre());            // usuario = nombre
                psUsuario.setString(3, "Biblioteca123");         // contraseña por defecto
                psUsuario.setString(4, getTipo());
                psUsuario.setString(5, getEstatus());
                psUsuario.executeUpdate();

                JOptionPane.showMessageDialog(null, "Docente y usuario registrados exitosamente.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al registrar docente/usuario:\n" + e.getMessage());
            }
        }
    }
}

