package com.mycompany.biblioteca;

import java.sql.CallableStatement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ModificarDocente {
    
    String numControl;
    String foto;
    String nombre;
    String especialidad;
    String turno;
    String correo;
    String estatus;
    String tipo;
    
    public String getNumControl(){ return numControl; }
    public void setNumControl(String numControl){ this.numControl = numControl; }
    
    public String getFoto(){ return foto; }
    public void setFoto(String foto){ this.foto = foto; }
    
    public String getNombre(){ return nombre; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    
    public String getEspecialidad(){ return especialidad; }
    public void setEspecialidad(String especialidad){ this.especialidad = especialidad; }
    
    public String getTurno(){ return turno; }
    public void setTurno(String turno){ this.turno = turno; }
    
    public String getCorreo(){ return correo; }
    public void setCorreo(String correo){ this.correo = correo; }
    
    public String getEstatus(){ return estatus; }
    public void setEstatus(String estatus){ this.estatus = "A"; }
    
    public String getTipo(){ return tipo; }
    public void setTipo(String tipo){ this.tipo = "DOCENTE"; }
    
    public void CHECAREG(JTextField numControlField2, JTextField fotoField2, JTextField nombreField2,
                         JTextField especialidadField2, JTextField turnoField2, JTextField correoField2) {
        
        setNumControl(numControlField2.getText().toUpperCase());
        setFoto(fotoField2.getText().toUpperCase());
        setNombre(nombreField2.getText().toUpperCase());
        setEspecialidad(especialidadField2.getText().toUpperCase());
        setTurno(turnoField2.getText().toUpperCase());
        setCorreo(correoField2.getText().toUpperCase());
        setEstatus("A");
        setTipo("DOCENTE");
        
        String instmysql = "UPDATE docentes SET "
                         + "foto = '"+foto+"', "
                         + "nombre = '"+nombre+"', "
                         + "especialidad = '"+especialidad+"', "
                         + "turno = '"+turno+"', "
                         + "correo = '"+correo+"', "
                         + "estatus = '"+estatus+"', "
                         + "tipo = '"+tipo+"' "
                         + "WHERE num_control = '"+numControl+"';";
        
        try {
            CConexion cbd = new CConexion();
            CallableStatement cs = cbd.estableceConexion().prepareCall(instmysql);
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Docente actualizado correctamente.");
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.toString());
        }
    }
}
