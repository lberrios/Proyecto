package GUI;

import clases.Asignatura;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import util.Conecta;
import java.awt.event.ItemEvent;
import clases.Calendario;
import clases.Asistencia;
import clases.Estudiantes;
import java.awt.HeadlessException;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import util.Globales;
import util.Valida;
/**
 *
 * @author Pablo
 */
public class AsistenciaIF extends javax.swing.JInternalFrame {
    
    DefaultTableModel model;
    DefaultComboBoxModel modeloCombo;
    Asignatura a = new Asignatura();
    Calendario Ca = new Calendario();
    Asistencia asis = new Asistencia();
    Estudiantes E = new Estudiantes();
    Valida va=new Valida();
    Conecta cnx = new Conecta();
    ResultSet rs;
    Statement stm;
//    int id = 1;
    
 
    //private Boolean[] editables= {false,false,true};
    /**
     * Creates new form Asistencia
     */
    public AsistenciaIF() {
        initComponents();
        cnx.Conecta();
        limpiar();
        BotonesInicio();
        LlenarTablaIngreso();        
        llenarTXT();      
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
     
    public void Asistencia(JTable Tabla, TableColumn columna ){
        String Asis[] = {"Presente","Ausente"};
        JComboBox Combo = new JComboBox(Asis);
        columna.setCellEditor(new DefaultCellEditor(Combo));        
    }

    private void limpiar(){
        cbxFecha.removeAllItems();
        txtBuscar.setText("");
    }
  
    private void Habilitar(){
        cbxFecha.setEnabled(true);
        llenarCB();
        //txtBuscar.setEnabled(true);  
        va.SeleccionarTodo(txtBuscar);                
    }
    
    private void Deshabilitar() {        
        cbxFecha.setEnabled(false);
        txtBuscar.setEnabled(false);       
        LlenarTablaIngreso();
        tblAsistencia.setEnabled(false);
    }
    
    private void BotonesInicio(){
        jcbIngresar.setEnabled(true);
        jcbIngresar.setSelected(false);
        btnGuardar.setEnabled(false);
        jcbModificar.setEnabled(true);
        jcbModificar.setSelected(false);
        btnModificar.setEnabled(false);
        btnCancelar.setEnabled(false);        
    }
    
    private void BotonesIngresar(){
        btnGuardar.setEnabled(true);
        jcbModificar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnCancelar.setEnabled(true);        
    }
    
    private void BotonesModificar(){
        jcbIngresar.setEnabled(false);    
        btnModificar.setEnabled(true);
//        btnIntroducir.setEnabled(false);
        txtBuscar.setEnabled(true);  
        btnCancelar.setEnabled(true);
    }
//    
//    private void BotonesClick(){
//        btnGuardar.setEnabled(false);
//        btnCancelar.setEnabled(true);
//    }

    private void LlenarTablaIngreso() {
       int[] anchos = {30, 100, 100};
       
       cnx.Conecta();
        try{           
            String [] titulos ={"ID","Nombre","Asistencia"};
            
            String SQL = "Select * from estudiantea_view";
            model = new DefaultTableModel(null, titulos);
            stm = cnx.conn.createStatement();
            rs = stm.executeQuery(SQL);
            String [] fila = new String[2];
            while(rs.next()){
                fila[0] = rs.getString("idestudiante");
                fila[1] = rs.getString("nombre");             
                model.addRow(fila);
            }
            tblAsistencia.setModel(model);
            
            for(int i = 0; i < tblAsistencia.getColumnCount(); i++) {
                tblAsistencia.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            
            //Centra los datos en las celdas
//            DefaultTableCellRenderer centraCelda = new DefaultTableCellRenderer();
//            centraCelda.setHorizontalAlignment(SwingConstants.CENTER);
//            tblAsistencia.getColumnModel().getColumn(0).setCellRenderer(centraCelda);
//            tblAsistencia.getColumnModel().getColumn(2).setCellRenderer(centraCelda);
//            tblAsistencia.getColumnModel().getColumn(0).setHeaderRenderer(centraCelda);
//            tblAsistencia.getColumnModel().getColumn(2).setHeaderRenderer(centraCelda);
             Asistencia(tblAsistencia, tblAsistencia.getColumnModel().getColumn(2));   
             
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error LlenarTabla Asistencia: " + e.getMessage());
        } finally {
            cnx.Desconecta();
        }
    }
    
    private void LlenarTablaModificar() {
       int[] anchos = {30, 100, 100};       
       cnx.Conecta();
        try{                         
              String [] titulos ={"ID","Nombre","Asistencia"};              
              String SQL = "Select * from asistencia_view where fecha = '" + cbxFecha.getSelectedItem().toString() + "'";

              model = new DefaultTableModel(null, titulos);
              stm = cnx.conn.createStatement();
              rs = stm.executeQuery(SQL);
              String [] fila = new String[3];
              while(rs.next())
              {
                  fila[0] = rs.getString("idasistencia");
                  fila[1] = rs.getString("nombre");
                  fila[2] = rs.getString("asistencia");            
                  model.addRow(fila);
              }
              tblAsistencia.setModel(model);

              for(int i = 0; i < tblAsistencia.getColumnCount(); i++) {
                    tblAsistencia.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }

              //Centra los datos en las celdas
//              DefaultTableCellRenderer centraCelda = new DefaultTableCellRenderer();
//              centraCelda.setHorizontalAlignment(SwingConstants.CENTER);
//              tblAsistencia.getColumnModel().getColumn(0).setCellRenderer(centraCelda);            
//              tblAsistencia.getColumnModel().getColumn(2).setCellRenderer(centraCelda);
//              tblAsistencia.getColumnModel().getColumn(0).setHeaderRenderer(centraCelda);
//              tblAsistencia.getColumnModel().getColumn(1).setHeaderRenderer(centraCelda);
//              tblAsistencia.getColumnModel().getColumn(2).setHeaderRenderer(centraCelda);
              Asistencia(tblAsistencia, tblAsistencia.getColumnModel().getColumn(2));            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error LlenarTabla Asistencia para edición: " + e.getMessage());
        } finally {
            cnx.Desconecta();
        }
    }
               
    private void busquedaModificar(String nomb) {
       int[] anchos = {30, 100, 100};       
       cnx.Conecta();
        try{           
              String [] titulos ={"ID","Nombre","Asistencia"};            
              String SQL = "Select * from asistencia_view where nombre like '%" + nomb +
                        "%' and fecha = '" + cbxFecha.getSelectedItem().toString() + "'";

              model = new DefaultTableModel(null, titulos);
              stm = cnx.conn.createStatement();
              rs = stm.executeQuery(SQL);
              String [] fila = new String[3];
              while(rs.next())
              {
                  fila[0] = rs.getString("idasistencia");
                  fila[1] = rs.getString("nombre");
                  fila[2] = rs.getString("asistencia");            
                  model.addRow(fila);
              }
              tblAsistencia.setModel(model);

              for(int i = 0; i < tblAsistencia.getColumnCount(); i++) {
                    tblAsistencia.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }

              //Centra los datos en las celdas
//              DefaultTableCellRenderer centraCelda = new DefaultTableCellRenderer();
//              centraCelda.setHorizontalAlignment(SwingConstants.CENTER);
//              tblAsistencia.getColumnModel().getColumn(0).setCellRenderer(centraCelda);            
//              tblAsistencia.getColumnModel().getColumn(2).setCellRenderer(centraCelda);
//              tblAsistencia.getColumnModel().getColumn(0).setHeaderRenderer(centraCelda);
//              tblAsistencia.getColumnModel().getColumn(1).setHeaderRenderer(centraCelda);
//              tblAsistencia.getColumnModel().getColumn(2).setHeaderRenderer(centraCelda);
              Asistencia(tblAsistencia, tblAsistencia.getColumnModel().getColumn(2));            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error LlenarTabla Asistencia para edición: " + e.getMessage());
        } finally {
            cnx.Desconecta();
        }
    }
        
    private DefaultComboBoxModel llenarCB() {        
        cnx.Conecta();
        try {            
            modeloCombo = new DefaultComboBoxModel();
            String SQL = "select fecha from calendario";
            stm = cnx.conn.createStatement();            
            rs = stm.executeQuery(SQL);
            while (rs.next()) {
                modeloCombo.addElement(rs.getString("fecha"));
            }
            cbxFecha.setModel(modeloCombo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error LlenarCB: " + ex.getMessage());
        } finally {
            cnx.Desconecta();
        }
        //LlenarTabla();
        cbxFecha.setSelectedIndex(-1);
        return modeloCombo;        
    }
       
//    private void setJTexFieldChanged(JTextField txt)
//    {
//        DocumentListener documentListener = new DocumentListener() {
//
//        @Override
//        public void changedUpdate(DocumentEvent documentEvent) {
//            printIt(documentEvent);
//        }
//
//        @Override
//        public void insertUpdate(DocumentEvent documentEvent) {
//            printIt(documentEvent);
//        }
//
//        @Override
//        public void removeUpdate(DocumentEvent documentEvent) {
//            printIt(documentEvent);
//        }
//        };
//        txt.getDocument().addDocumentListener(documentListener);
//    }
//
//    private void printIt(DocumentEvent documentEvent) {
//        DocumentEvent.EventType type = documentEvent.getType();
//
//        if (type.equals(DocumentEvent.EventType.CHANGE))
//        {
//            txtbuscador();
//        }
//        else if (type.equals(DocumentEvent.EventType.INSERT))
//        {
//            txtbuscador();
//        }
//        else if (type.equals(DocumentEvent.EventType.REMOVE))
//        {
//            txtbuscador();
//        }
//    }
    
//    private void txtbuscador()
//    {        
//        LlenarTablaSegunTxt();
//    }
    
    private void llenarTXT() {
        cnx.Conecta();
         try {             
            String SQL = "select nombreA from asignatura where idasignatura = " + Globales.id;
            stm = cnx.conn.createStatement();            
            rs = stm.executeQuery(SQL);
            while (rs.next()) {
                txtAsignatura.setText(rs.getString("nombreA"));
            }
            rs.close();            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error LlenarTXT: " + ex.getMessage());
        } finally {
            cnx.Desconecta();
         }
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxFecha = new javax.swing.JComboBox();
        txtBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAsignatura = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAsistencia = new javax.swing.JTable();
        jcbModificar = new javax.swing.JCheckBox();
        jcbIngresar = new javax.swing.JCheckBox();
        btnModificar = new javax.swing.JButton();

        setTitle("Lista de Asistencia");
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha de Asistencia"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Fecha");

        cbxFecha.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFecha.setEnabled(false);
        cbxFecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxFechaItemStateChanged(evt);
            }
        });

        txtBuscar.setEnabled(false);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Buscar");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Asignatura");

        txtAsignatura.setEditable(false);
        txtAsignatura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtAsignatura.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        txtAsignatura.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        tblAsistencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAsistencia.setColumnSelectionAllowed(true);
        tblAsistencia.setEnabled(false);
        tblAsistencia.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblAsistencia);
        tblAsistencia.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAsistencia.getColumnModel().getColumn(0).setResizable(false);
        tblAsistencia.getColumnModel().getColumn(1).setResizable(false);

        jcbModificar.setText("Modificar");
        jcbModificar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbModificarItemStateChanged(evt);
            }
        });

        jcbIngresar.setText("Ingresar");
        jcbIngresar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbIngresarItemStateChanged(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbIngresar)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(jcbModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 24, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalir)
                    .addComponent(btnGuardar)
                    .addComponent(jcbIngresar)
                    .addComponent(jcbModificar)
                    .addComponent(btnModificar))
                .addGap(44, 44, 44))
        );

        jcbModificar.getAccessibleContext().setAccessibleDescription("");

        getAccessibleContext().setAccessibleParent(jScrollPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try
        {
            if (cbxFecha.getSelectedIndex()==-1)
            {
                JOptionPane.showMessageDialog(null,"El campo fecha esta vacío");
            }
            else if(asis.validarFecha(Ca.ConsultarIDCal(cbxFecha.getSelectedItem().toString()))>0)
            {
                JOptionPane.showMessageDialog(null, "Ya existe un registro de asistencia con esa Fecha \n "+
                        "Si desea cambiar el valor de las asistencias utilice la función Modificar.");
            }
            else
            {
                DefaultTableModel modelo = (DefaultTableModel)tblAsistencia.getModel();
                int filas = modelo.getRowCount();      
                for (int i = 0; i < filas; i++) 
                {
                    //int ax = a.consultaIdA(txtAsignatura.getText());               
                    if (tblAsistencia.getValueAt(i, 2)==null)
                    {
                        asis.setAsistencia("Ausente");
                    }
                    else
                    {
                        asis.setAsistencia(modelo.getValueAt(i, 2).toString());                     
                    }
                    asis.setIdcalendario(Ca.ConsultarIDCal(cbxFecha.getSelectedItem().toString()));                               
                    asis.setIdestudiante(Integer.parseInt(modelo.getValueAt(i, 0).toString()));
                    asis.setIdasignatura(a.consultaIdA(txtAsignatura.getText()));                
                    asis.GuardarAsistencia();                           
                }
                JOptionPane.showMessageDialog(null, "Datos Guardados Exitosamente");
                limpiar();
                Deshabilitar();
                BotonesInicio();
            }
        }            
        catch(HeadlessException | NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Error guardar Asistencia: " + e.getMessage());
        }       
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
                limpiar();
                Deshabilitar();
                LlenarTablaIngreso();
                BotonesInicio();                                
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Desea Salir?","Confirmar",
            JOptionPane.OK_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE);
        if(i==JOptionPane.OK_OPTION){
            this.doDefaultCloseAction();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void cbxFechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxFechaItemStateChanged
        if ((cbxFecha.getSelectedIndex()==-1) || jcbIngresar.isSelected()==true)
        {  }
        else
        {
            LlenarTablaModificar();            
        }
//       
//        if(evt.getStateChange() == ItemEvent.SELECTED)
//        {
//            LlenarTablaSegunTxt();
//            tblAsistencia.setEnabled(false);
//        }
    }//GEN-LAST:event_cbxFechaItemStateChanged

    private void jcbModificarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbModificarItemStateChanged
        if (jcbModificar.isSelected())
        {
            Habilitar();
            BotonesModificar();
            tblAsistencia.setEnabled(true);
        }
        else
        {
            limpiar();
            Deshabilitar();
            BotonesInicio();            
        }
    }//GEN-LAST:event_jcbModificarItemStateChanged

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        String nombre=txtBuscar.getText();        
        if(nombre.equals("") && cbxFecha.getSelectedIndex()==-1)
        {  }
        else                     
        {           
            busquedaModificar(nombre);
        }
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void jcbIngresarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbIngresarItemStateChanged
        if (jcbIngresar.isSelected())
        {
            Habilitar();
            BotonesIngresar();
            tblAsistencia.setEnabled(true);
        }
        else
        {
            limpiar();
            Deshabilitar();
            BotonesInicio();            
        }
    }//GEN-LAST:event_jcbIngresarItemStateChanged

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        try
        {
            if (cbxFecha.getSelectedIndex()==-1)
            {
                JOptionPane.showMessageDialog(null,"El campo fecha esta vacío");
            }
            else 
            {
                DefaultTableModel modelo = (DefaultTableModel)tblAsistencia.getModel();
                int filas = modelo.getRowCount();      
                for (int i = 0; i < filas; i++) 
                {
                    asis.setIdasistencia(Integer.parseInt(modelo.getValueAt(i,0).toString()));
                    asis.setAsistencia(modelo.getValueAt(i, 2).toString());              
                    asis.ActualizarAsistencia();                                               
                }          
            JOptionPane.showMessageDialog(null, "Datos Actualizados Exitosamente");
            limpiar();
            Deshabilitar();
            BotonesInicio();
            }

        }            
        catch(HeadlessException | NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Error actualizar Asistencia: " + e.getMessage());
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cbxFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox jcbIngresar;
    private javax.swing.JCheckBox jcbModificar;
    private javax.swing.JTable tblAsistencia;
    private javax.swing.JTextField txtAsignatura;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}