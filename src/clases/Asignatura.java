package clases;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import util.Conecta;

/**
 * @author Pablo Hurtado
 * @version 1.0
 * @created 27-mar-2013 10:44:48 PM
 */
public class Asignatura {

    private int idasignatura;
    private int anio;
    private String grupo;
    private String nombreA;
    private String periodo;
    private int idcarrera;
    Carrera ca = new Carrera();
    Conecta cnx = new Conecta();
    PreparedStatement ps;
    ResultSet rs;

    public Asignatura(){

    }

    public Asignatura(int idasignatura, int anio, String grupo, String nombreA, String periodo, int idcarrera) {
        this.idasignatura = idasignatura;
        this.anio = anio;
        this.grupo = grupo;
        this.nombreA = nombreA;
        this.periodo = periodo;
        this.idcarrera = idcarrera;
    }                

    public int getanio(){
            return anio;
    }

    public String getgrupo(){
            return grupo;
    }

    public String getnombreA(){
            return nombreA;
    }

    public String getperiodo(){
            return periodo;
    }

    public void setanio(int ani){
            anio = ani;
    }

    public void setgrupo(String grup){
            grupo = grup;
    }

    public void setnombreA(String nombrA){
            nombreA = nombrA;
    }

    public void setperiodo(String period){
            periodo = period;
    }

    public int getIdasignatura() {
        return idasignatura;
    }

    public void setIdasignatura(int idasignatura) {
        this.idasignatura = idasignatura;
    }

    public int getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(int idcarrera) {
        this.idcarrera = idcarrera;
    }
    
    public void actualizarAsignatura(){
        cnx.Conecta();
            try{
                String SQL ="update asignatura set nombreA=?,"
                        + "grupo=?,anio=?,periodo=?,"
                        + "idcarrera=? where idasignatura=?";
                
                ps = cnx.conn.prepareStatement(SQL);
                ps.setString(1, nombreA);
                ps.setString(2, grupo);
                ps.setInt(3, anio);
                ps.setString(4, periodo);
                ps.setInt(5, idcarrera);
                ps.setInt(6, idasignatura);
                int n = ps.executeUpdate();
                if(n>0){
                    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");                
                }                
                ps.close();
            }catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null, "Error Actualizar Asignatura: " + e.getMessage());
            } finally {
                cnx.Desconecta();
            }
    }
    
    public void eliminarAsignatura(){
        cnx.Conecta();
                try {
                    String SQL = "delete from asignatura where idasignatura= ?";
                    
                    ps = cnx.conn.prepareStatement(SQL);
                    ps.setInt(1, idasignatura);
                    int n = ps.executeUpdate();
                    if(n>0){                
                        JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
                    }
                    
                    ps.close();
                } catch(SQLException | HeadlessException e){
                    JOptionPane.showMessageDialog(null, "Error Eliminar Asignatura: " + e.getMessage());            
                } finally {
                    cnx.Desconecta();
                }
    }
    
    public void guardaAsignatura(){
        cnx.Conecta();
            try {
                String SQL = "insert into asignatura(nombreA,"
                        + "grupo,anio,periodo,idcarrera) "
                + "values(?,?,?,?,?)";
                
                ps = cnx.conn.prepareStatement(SQL);
                ps.setString(1, nombreA);
                ps.setString(2, grupo);
                ps.setInt(3, anio);
                ps.setString(4, periodo);
                ps.setInt(5, idcarrera);
                
                int n = ps.executeUpdate();
                if (n>0){
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");                
                }
                
                ps.close();
            } catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null, "Error Guardar Asignatura: " + e.getMessage());
            } finally {
                cnx.Desconecta();
            }
    }
    
    public int consultaIdA(String Asig){
        int id = 0;
        cnx.Conecta();
        try{
            String SQL = "Select idasignatura from asignatura where nombreA = "+"\""+Asig+"\"";
            
            ps = cnx.conn.prepareStatement(SQL);
            rs = ps.executeQuery();            
            while(rs.next()){
                id = rs.getInt("idasignatura");
            }
            
            ps.close();
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "Error consulta ID Asignatura: " + e.getMessage());
        }
        cnx.Desconecta();       
        return id;
    }
    
    public String consultaAsignatura(int id){
        String fila= "";
        cnx.Conecta();
        try{
            String SQL = "Select nombreA from asignatura where idasignatura="+id;
            
            ps = cnx.conn.prepareStatement(SQL);
            rs = ps.executeQuery();
            while(rs.next()){
                fila = rs.getString("nombreA");
            }
            
            ps.close();
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "Error consulta Nombre Asignatura: " + e.getMessage());
        }
        cnx.Desconecta();       
        return fila;
    }
    
    public ArrayList<String> consultaAsignatura(){
        cnx.Conecta();
        ArrayList<String> ls = new ArrayList<>();
        try{
            String SQL = "Select nombreA from asignatura";
            
            ps = cnx.conn.prepareStatement(SQL);
            rs = ps.executeQuery();            
            while(rs.next()){
                ls.add(rs.getString("nombreA"));
            }
            
            ps.close();
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "Error consultaAsignatura: " + e.getMessage());
        }
        cnx.Desconecta();
        return ls;                                  
    }
}