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
 * @created 27-mar-2013 10:45:07 PM
 */
public class Actividad {
    private String actividad;
    private int idactividad;
    Conecta cnx = new Conecta();
    PreparedStatement ps;
    ResultSet rs;    

    public Actividad(){

    }

    public Actividad(String Activida, int idtipoactividad) {
        this.actividad = Activida;
        this.idactividad = idtipoactividad;            
    }

    public String getActividad(){
            return actividad;
    }

    public void setActividad(String activid){
            actividad = activid;
    }

    public int getIdactividad() {
        return idactividad;
    }

    public void setIdactividad(int idtipoactividad) {
        this.idactividad = idtipoactividad;
    }        
    
    public void ActualizarActividad(){
        cnx.Conecta();
            try{
                String SQL ="update actividad set actividad=?"
                + "where idactividad=?";
                
                ps = cnx.conn.prepareStatement(SQL);               
                ps.setString(1, actividad);
                ps.setInt(2, idactividad);
                int n = ps.executeUpdate();
                if(n>0){
                    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");                
                }
                ps.close();
            }catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null, "Error Actualizar Actividad: " + e.getMessage());
            } finally {
                cnx.Desconecta();
            }
    }
    
    public void EliminarActividad(){
        cnx.Conecta();
        try {
            String SQL = "delete from actividad where idactividad=?";

            ps = cnx.conn.prepareStatement(SQL);     
            ps.setInt(1, idactividad);                    
            int n = ps.executeUpdate();
            if(n>0){                
                JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
            }
            ps.close();
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "Error Eliminar actividad: " + e.getMessage());            
        } finally {
        cnx.Desconecta();
        }
    }
    
    public void GuardarActividad(){
        cnx.Conecta();
            try {
                String SQL = "insert into actividad(actividad) values(?)";
                
                ps = cnx.conn.prepareStatement(SQL);
                ps.setString(1, actividad);                
                int n = ps.executeUpdate();
                if (n>0){
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");                
                }
                ps.close();
            } catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null, "Error Guardar actividad: " + e.getMessage());
            } finally {
                cnx.Desconecta();
            }
    }
    
    public int consultaIdAct(String Activ){
        int id = 0;
        cnx.Conecta();
            try{
                String SQL = "Select idactividad from actividad where actividad = "+"\""+Activ+"\"";

                ps = cnx.conn.prepareStatement(SQL);
                rs = ps.executeQuery();            
                while(rs.next()){
                    id = rs.getInt("idactividad");
                }            
                ps.close();
            } catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null, "Error consulta Id actividad: " + e.getMessage());
            }
        cnx.Desconecta();       
        return id;
    }
    
    public String consultaActividad(int id){
        String eva= "";
        cnx.Conecta();
        try{
            String SQL = "Select actividad from actividad where idactividad= " + id;
            
            ps = cnx.conn.prepareStatement(SQL);
            rs = ps.executeQuery();
            while(rs.next()){
                eva = rs.getString("actividad");
            }            
            ps.close();
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "Error consultaDescrip actividad: " + e.getMessage());
        }
        cnx.Desconecta();       
        return eva;
    }
    
    public ArrayList<String> consultaActividad(){
        cnx.Conecta();
        ArrayList<String> ls = new ArrayList<>();
        try{
            String SQL = "Select actividad from actividad";
            
            ps = cnx.conn.prepareStatement(SQL);            
            rs = ps.executeQuery();
            while(rs.next()){
                ls.add(rs.getString("actividad"));
            }            
            ps.close();
        } catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "Error consultaActividad: " + e.getMessage());
        }
        cnx.Desconecta();
        return ls;                                  
    }
}