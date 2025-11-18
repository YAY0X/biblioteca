
package com.mycompany.biblioteca;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EntradaLogin {
    
    String USERPRG;
    String PASSWORDPRG;
    String TIPOPRG;
    String ESTATUSPRG;
    
    public String getTIPOPRG(){
        return TIPOPRG;
    }
    
    public void setTIPOPRG(String TIPOPRG){
       this.TIPOPRG = TIPOPRG;
    }
    
    public String getUSERPRG(){
        return USERPRG;
    }
    
    public void setUSERPRG(String USERPRG){
        this.USERPRG = USERPRG;
    }
    
    public String getPASSWORDPRG(){
        return PASSWORDPRG;
    }
    
    public void setPASSWORDPRG(String PASSWORDPRG){
        this.PASSWORDPRG = PASSWORDPRG;
    }
    
    public String getESTATUSPRG(){
        return ESTATUSPRG;
    }
    
    public void setESTATUSPRG(String ESTATUSPRG){
        this.ESTATUSPRG = "A";
    }
    
    public void CHECAREG(JTextField USUARIOFRM, JTextField PASSWORDFRM){
        setUSERPRG(USUARIOFRM.getText().toUpperCase());
        setPASSWORDPRG(PASSWORDFRM.getText().toUpperCase());
        
        String instmysql="Select * from usuarios where (user='"+USERPRG+"') AND (password='"+PASSWORDPRG+"');";
        
        try{
            CConexion cbd = new CConexion();
            CallableStatement cs=cbd.estableceConexion().prepareCall(instmysql);
            ResultSet rs=cs.executeQuery(instmysql);
            while(rs.next()){
                USERPRG=rs.getString("USER");
                PASSWORDPRG=rs.getString("PASSWORD");
                TIPOPRG=rs.getString("TIPO");
                ESTATUSPRG = rs.getString("ESTATUS");
            }
            if ("A".equals(ESTATUSPRG)){
               
                if (TIPOPRG.equals("ALUMNO")){
                     MENUALUMNO alumno = new MENUALUMNO();
                     alumno.setVisible(true);
                }
                if (TIPOPRG.equals("SOPORTE")){
                     MENUSOPORTE soporte = new MENUSOPORTE();
                     soporte.setVisible(true);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"USUARIO SIN ACCESO");
                 USUARIOFRM.setText("");
                 PASSWORDFRM.setText("");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"USUARIO NO ENCONTRADO"+e.toString());
            USUARIOFRM.setText("");
            PASSWORDFRM.setText("");
        }
    }
}
