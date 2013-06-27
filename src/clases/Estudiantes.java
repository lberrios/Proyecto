package clases;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import util.Conecta;

/**
 * @author Pablo Hurtado
 * @version 1.0
 * @created 27-mar-2013 10:45:00 PM
 */
public class Estudiantes {

    private int idestudiante;
    private String apellidos;
    private int carne;
    private int celular;
    private String email;
    private String nombre;
    private int idasignatura;
    Conecta cnx = new Conecta();
    Statement stm;

	public Estudiantes(){

	}

    public Estudiantes(String apellidos, int carne, int celular, String email, String nombre, int idestudiante, int idasignatura) {
        this.apellidos = apellidos;
        this.carne = carne;
        this.celular = celular;
        this.email = email;
        this.nombre = nombre;
        this.idestudiante = idestudiante;
        this.idasignatura = idasignatura;
    }

    public int getIdEstudiante() {
        return idestudiante;
    }

    public void setIdEstudiante(int idestudiante) {
        this.idestudiante = idestudiante;
    }
        
    public String getApellidos(){
            return apellidos;
    }

    public int getCarnet(){
            return carne;
    }

    public int getCelular(){
            return celular;
    }

    public String getEmail(){
            return email;
    }

    public String getNombre(){
            return nombre;
    }

    public void setApellidos(String apellid){
            apellidos = apellid;
    }

    public void setCarnet(int carn){
            carne = carn;
    }

    public void setCelular(int celula){
            celular = celula;
    }

    public void setEmail(String correo){
            email = correo;
    }

    public void setNombre(String nombr){
            nombre = nombr;
    }

    public int getIdAsignatura() {
        return idasignatura;
    }

    public void setIdAsignatura(int idasignatura) {
        this.idasignatura = idasignatura;
    }

    @Override
    public String toString(){
        return this.nombre+" "+this.apellidos;
    }

    public void ActualizarEstudiante(){
        cnx.Conecta();
            try{
                String SQL ="update estudiante set nombreE=?,apellidoE=?,carnet=?"
                        + ",celular=?, email=? where idestudiante=?";

                stm = cnx.conn.createStatement();
                int n = stm.executeUpdate(SQL);
                if(n>0){
                    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");                
                }
            }catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null, "Error Actualizar: " + e.getMessage());
            } finally {                
                cnx.Desconecta();
            }
    }
    
    public void EliminarEstudiante(){
        cnx.Conecta();
            try {
                String SQL = "delete from estudiante where idestudiante= " + getIdEstudiante();
                stm = cnx.conn.createStatement();            
                int n = stm.executeUpdate(SQL);
                if(n>0){                
                    JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
                }
            } catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null, "Error Eliminar: " + e.getMessage());
            } finally {                
                cnx.Desconecta();
            }
    }
    
    public void GuardarEstudiante(){
        cnx.Conecta();
            try {
                String SQL = "insert into estudiante(nombreE,apellidoE,carnet,celular,email,idasignatura) "
                + "values('"+getNombre()+"','"+getApellidos()+"','"+getCarnet()+"','"+getCelular()+"','"+getEmail()+"','"+getIdAsignatura()+"')";
                stm = cnx.conn.createStatement();            
                int n = stm.executeUpdate(SQL);
                if (n>0){
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");                
                }
            } catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null, "Error Guardar: " + e.getMessage());
            } finally {
                cnx.Desconecta();
            }
    }
    
    public int consultaId(String Nombr, String Apell){
        int id = 0;
        cnx.Conecta();
        try{
            String SQL = "Select idestudiante from estudiante where nombreE = "+"\""+Nombr+"\""
                    + "and apellidoE="+"\""+Apell+"\"";
            stm = cnx.conn.createStatement();
            ResultSet rs = stm.executeQuery(SQL);            
            while(rs.next()){
                id = rs.getInt("idestudiante");
            }
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "Error consulta ID Estudiante: " + e.getMessage());
        } finally {
        cnx.Desconecta();       
        }
        return id;
    }

    public String[] consultaEstudiante(int id){
        String [] fila = new String[2];
        cnx.Conecta();
        try{
            String SQL = "Select nombreE, apellidoE from estudiante where idestudiante="+id;
            stm = cnx.conn.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while(rs.next()){
                fila[0] = rs.getString("nombreE");
                fila[1] = rs.getString("apellidoE");
            }
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "Error consulta Nombre Estidiante: " + e.getMessage());
        } finally {
        cnx.Desconecta();
        }
        return fila;
    }

    public ArrayList<String> listaEstudiante(){
        cnx.Conecta();
        ArrayList<String> ls = new ArrayList<>();
        try{
            String SQL = "Select nombreE, apellidoE from estudiante";
            stm = cnx.conn.createStatement();
            ResultSet rs = stm.executeQuery(SQL);            
            while(rs.next()){
                ls.add(rs.getString("nombreE"));
                ls.add(rs.getString("apellidoE"));
            }
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "Error consultaUniversidad: " + e.getMessage());
        } finally {
        cnx.Desconecta();
        }
        return ls;                                  
    } 
}