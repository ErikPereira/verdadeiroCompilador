import Sintatico.Sintatico;
import compilerException.CompilerException;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Compilador extends javax.swing.JFrame {
    private List<String> arquivo = new ArrayList<>();
    private String programa = "";
    private Sintatico sintatico;
    private String nomeArquivo;
    private String caminho;
    
    public Compilador() {
        initComponents();
        textResultado.setEditable(false);
        tabelaCodigo.setSelectionBackground(Color.WHITE);
        tabelaCodigo.setSelectionForeground(Color.black);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaCodigo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jNomeArquivo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textResultado = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        buttonExecutar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArquivos = new javax.swing.JMenu();
        menuAbrirArquivo = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuExecutarCodigo = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        menuDesenvolvedores = new javax.swing.JMenuItem();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("File");
        jMenuBar3.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar3.add(jMenu6);

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tabelaCodigo.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tabelaCodigo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Linha", "Código"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaCodigo.setRowHeight(25);
        tabelaCodigo.setShowHorizontalLines(false);
        jScrollPane2.setViewportView(tabelaCodigo);
        if (tabelaCodigo.getColumnModel().getColumnCount() > 0) {
            tabelaCodigo.getColumnModel().getColumn(0).setResizable(false);
            tabelaCodigo.getColumnModel().getColumn(0).setPreferredWidth(10);
            tabelaCodigo.getColumnModel().getColumn(1).setResizable(false);
            tabelaCodigo.getColumnModel().getColumn(1).setPreferredWidth(470);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Compilador: ");

        jNomeArquivo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jNomeArquivo.setText("Sem arquivo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jNomeArquivo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jNomeArquivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        textResultado.setEditable(false);
        textResultado.setColumns(20);
        textResultado.setLineWrap(true);
        textResultado.setRows(5);
        textResultado.setWrapStyleWord(true);
        textResultado.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(textResultado);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Resultado");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(jLabel2)
                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );

        buttonExecutar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buttonExecutar.setText("Executar");
        buttonExecutar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExecutarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonExecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonExecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))))
        );

        menuArquivos.setText("Arquivos");

        menuAbrirArquivo.setText("Abrir Arquivo");
        menuAbrirArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirArquivoActionPerformed(evt);
            }
        });
        menuArquivos.add(menuAbrirArquivo);

        jMenuBar1.add(menuArquivos);

        jMenu2.setText("Executar");

        menuExecutarCodigo.setText("Executar Código");
        menuExecutarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExecutarCodigoActionPerformed(evt);
            }
        });
        jMenu2.add(menuExecutarCodigo);

        jMenuBar1.add(jMenu2);

        jMenu7.setText("Sobre");

        menuDesenvolvedores.setText("Desenvolvedores");
        menuDesenvolvedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDesenvolvedoresActionPerformed(evt);
            }
        });
        jMenu7.add(menuDesenvolvedores);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExecutarActionPerformed
       if(tabelaCodigo.getRowCount() == 0){
             JOptionPane.showMessageDialog(null, "Antes de Executar, por favor, selecione um arquivo!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        executar();
    }//GEN-LAST:event_buttonExecutarActionPerformed

    private void menuExecutarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExecutarCodigoActionPerformed
        if(tabelaCodigo.getRowCount() == 0){
             JOptionPane.showMessageDialog(null, "Antes de Executar, por favor, selecione um arquivo!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        executar();
    }//GEN-LAST:event_menuExecutarCodigoActionPerformed
    
    private void executar(){
        DefaultTableModel model = (DefaultTableModel) tabelaCodigo.getModel();
    
        int rows = tabelaCodigo.getRowCount();
        textResultado.setText("");
        programa = "";
        
        for(int i = 0; i < rows; i++){
            programa += model.getValueAt(i,1).toString() + "\n";
        }
        
        sintatico = new Sintatico(programa);
        try{
            String conteudoArquivo = sintatico.analisadorSintatico();
            
            this.CorNaLinha("", "sucesso");
            try{
                String[] caminhoCompilado = caminho.split(nomeArquivo);
                String[] nome = nomeArquivo.split(".txt");
                String salvarArquivo = caminhoCompilado[0] + nome[0] + " - compilado.txt";
                
                FileWriter arq = new FileWriter(salvarArquivo);
                
                PrintWriter gravarArq = new PrintWriter(arq);

                gravarArq.printf(conteudoArquivo);
                arq.close();
                
            }catch(IOException err){  
                System.out.println("oi");
            }
            
            // informa na text que houve sucesso ao executar
            textResultado.setText("\n\n   Execução realizada com Sucesso!");
        }catch(CompilerException err){
            // informa na text o erro obtido
            textResultado.setText(err.getMessage());
            
            // modifica a cor da linha errada para vermelho
            int linhaErrada = err.getLinhaErro();
            tabelaCodigo.setRowSelectionInterval(linhaErrada - 1, linhaErrada - 1);
            this.CorNaLinha(Integer.toString(linhaErrada), "erro");
            
            // muda o foco da tabela para a linha com erro
            setViewPortPosition((JViewport) tabelaCodigo.getParent(), tabelaCodigo.getCellRect(
                (linhaErrada-1), 0, true));
        }
    }
    
    public void CorNaLinha(String linhaErrada, String modo){
        tabelaCodigo.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                Color c = Color.WHITE;
                String linhaProcura = table.getValueAt(row,0).toString();
                
                if(linhaErrada.equals(linhaProcura)){
                    switch(modo){
                        case "erro":
                            c = Color.RED;
                            break;
                        default:
                            c = Color.WHITE;
                            break;
                    }       
                }
                
                label.setBackground(c);
                table.setSelectionForeground(Color.black);
                return label; 
            }   
        });
    }
   
    private static void setViewPortPosition(JViewport viewport, Rectangle position)
    {
        // The location of the viewport relative to the object
        Point pt = viewport.getViewPosition();

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        position.setLocation(position.x - pt.x, position.y - pt.y);

        // Scroll the area into view
        viewport.scrollRectToVisible(position);
    }
    
    private void menuDesenvolvedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDesenvolvedoresActionPerformed
       JOptionPane.showMessageDialog(null, "Desenvolvedores:\n\n" 
                                         + "Erik Bezerra           RA: 17776543\n"
                                         + "Caue de Abreu     RA: 17139585");
    }//GEN-LAST:event_menuDesenvolvedoresActionPerformed

    private void menuAbrirArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirArquivoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Procurar Arquivo");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("arq","txt");
        fileChooser.setFileFilter(filter);
       
        int retorno = fileChooser.showOpenDialog(this);
        String nLinha;

        if(retorno == JFileChooser.APPROVE_OPTION){
            textResultado.setText("");
            this.programa = "";
            this.CorNaLinha("", "sucesso");
            File file = fileChooser.getSelectedFile();
            nomeArquivo = file.getName();
            jNomeArquivo.setText(file.getName());
            caminho = file.getPath();
            Stream<String> stream = null;          
           
            try {
                stream = Files.lines(Paths.get(caminho));
                arquivo = stream.collect(Collectors.toList());
                DefaultTableModel model = (DefaultTableModel) tabelaCodigo.getModel();
                model.setRowCount(0);
                
                for(int i=0; i<arquivo.size(); i++){
                    String row = " " + arquivo.get(i);
                    nLinha = Integer.toString(i+1);
                    
                    //programa += row + '\n';
                    
                    Object[] linha = {nLinha, row};
                    model.addRow(linha);
                }     
            } catch (IOException ex) {
                Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
            }         
        }
        // this.lexico = new Lexico(this.programa);
    }//GEN-LAST:event_menuAbrirArquivoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Compilador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonExecutar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JLabel jNomeArquivo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem menuAbrirArquivo;
    private javax.swing.JMenu menuArquivos;
    private javax.swing.JMenuItem menuDesenvolvedores;
    private javax.swing.JMenuItem menuExecutarCodigo;
    private javax.swing.JTable tabelaCodigo;
    private javax.swing.JTextArea textResultado;
    // End of variables declaration//GEN-END:variables
}
