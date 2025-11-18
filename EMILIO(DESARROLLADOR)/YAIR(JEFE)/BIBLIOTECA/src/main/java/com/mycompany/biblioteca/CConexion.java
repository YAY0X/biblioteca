/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca;
import java.awt.HeadlessException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CConexion {
    Connection conectar=null;
    
    String USER = "root";
    String CONTRA = "2136";
    String bd = "biblioteca";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena ="jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar=DriverManager.getConnection(cadena,USER,CONTRA);
            //JOptionPane.showMessageDialog(null,"Se conecto correctamente a la B.D");
        } catch(HeadlessException | ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,"problemas en la conexion"+ e.toString());
        }
        return conectar;
    }
}