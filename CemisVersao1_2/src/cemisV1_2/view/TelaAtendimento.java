package cemisV1_2.view;

import cemisV1_2.controller.PacienteController;
import cemisV1_2.model.Paciente;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class TelaAtendimento extends javax.swing.JInternalFrame {
    
    private String nomePesquisa;
    private Paciente paciente = new Paciente();
    private List<Paciente> listPacientes = new ArrayList<>();
    private PacienteController pacienteController = new PacienteController();
    private DefaultTableModel dtmPacientes = new DefaultTableModel();

    public TelaAtendimento() {
        initComponents();
        
        this.nomePesquisa = "";
        carregarTela();
    }
    
    public TelaAtendimento(String paciente) {
        initComponents();
        this.nomePesquisa = paciente;
        carregarTela();
    }
   
    private void carregarTela() {
        habilitarBotoes(false);
        jtxfPaciente.setText("Paciente");
        jtxfPaciente.setBackground(Color.white);
        try {
            if(this.nomePesquisa.isEmpty() || this.nomePesquisa.length() == 0){
                listPacientes = pacienteController.listarPacientes();                
            }else{
                listPacientes = pacienteController.pesquisarPacientes(nomePesquisa);
            }
            carregarJTablePaciente(listPacientes);
        } catch (Exception ex) {
            Logger.getLogger(TelaAtendimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void carregarJTablePaciente(List<Paciente> listPacientes) throws Exception {

        dtmPacientes = (DefaultTableModel) jTablePaciente.getModel();

        int numLinhas = dtmPacientes.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            dtmPacientes.removeRow(0);
        }
        for (Paciente pac : listPacientes) {
            dtmPacientes.addRow(new Object[]{
                pac.getNomePaciente(),
                pac.getProntuario()
            });
        }
    }

    private void habilitarBotoes(boolean b) {
        jbtnReceituario.setEnabled(b);
        jbtnExame.setEnabled(b);
        jbtnAtestado.setEnabled(b);
    }

    private void selecionarTabelaPaciente() {
        if (jTablePaciente.getSelectedRow() != -1) {
            habilitarBotoes(true);
            int indice = jTablePaciente.getSelectedRow();
            Paciente pacienteAtual = new Paciente();
            try {
                pacienteAtual = pacienteController.getListPacientes().get(indice);
                setPaciente(pacienteAtual);
                jtxfPaciente.setText(pacienteAtual.getNomePaciente());
                jtxfPaciente.setBackground(Color.cyan);

            } catch (Exception ex) {

            }
        }
    }

    private void abrirTelaReceituario() {
        this.setVisible(false);

        TelaReceituario tela = new TelaReceituario(this, getPaciente());
        TelaPrincipal.jdpPrincipal.add(tela);
        TelaPrincipal.centralizaTela(tela);
        tela.setVisible(true);
    }
    
    private void abrirTelaPedidoExame() {
        this.setVisible(false);

        TelaPedidoExame tela = new TelaPedidoExame(this, getPaciente());
        TelaPrincipal.jdpPrincipal.add(tela);
        TelaPrincipal.centralizaTela(tela);
        tela.setVisible(true);
    }

    private void abrirTelaAtestado() {
        this.setVisible(false);

        TelaAtestado tela = new TelaAtestado(this, getPaciente());
        TelaPrincipal.jdpPrincipal.add(tela);
        TelaPrincipal.centralizaTela(tela);
        tela.setVisible(true);
    }

    private void abrirTelaPacienteSimples() {
        this.dispose();

        TelaPacienteSimples tela = new TelaPacienteSimples();
        TelaPrincipal.jdpPrincipal.add(tela);
        TelaPrincipal.centralizaTela(tela);
        tela.setVisible(true);
        tela.toFront();
        
    }

    public void atualizaTabela(String nomePesquisa) {
        try {
            this.requestFocusInWindow();
            String nome = nomePesquisa;

//            carregarJTablePaciente(pacienteController.pesquisarPacientes(nomePesquisa));
//            carregarTela();
        } catch (Exception ex) {
            Logger.getLogger(TelaAtendimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxfPesquisa = new javax.swing.JTextField();
        jbtnPesquisa = new javax.swing.JButton();
        jbtnLimparPesquisa = new javax.swing.JButton();
        jtxfPaciente = new javax.swing.JTextField();
        jbtnNovo = new javax.swing.JButton();
        jbtnExibirTodos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePaciente = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jbtnReceituario = new javax.swing.JButton();
        jbtnExame = new javax.swing.JButton();
        jbtnAtestado = new javax.swing.JButton();
        jbtnSair = new javax.swing.JButton();

        setClosable(true);
        setTitle("Atendimento");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/cemisV1_2/view/Icone.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Nome do Paciente ou Prontuário: ");

        jtxfPesquisa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxfPesquisaFocusGained(evt);
            }
        });
        jtxfPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfPesquisaKeyPressed(evt);
            }
        });

        jbtnPesquisa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnPesquisa.setText("Pesquisar");
        jbtnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPesquisaActionPerformed(evt);
            }
        });

        jbtnLimparPesquisa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnLimparPesquisa.setText("Limpar Pesquisa");
        jbtnLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLimparPesquisaActionPerformed(evt);
            }
        });

        jtxfPaciente.setEditable(false);
        jtxfPaciente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jtxfPaciente.setText("Paciente");

        jbtnNovo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnNovo.setText("Novo Paciente");
        jbtnNovo.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNovoActionPerformed(evt);
            }
        });

        jbtnExibirTodos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnExibirTodos.setText("Exibir Todos");
        jbtnExibirTodos.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnExibirTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExibirTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtxfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnExibirTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnExibirTodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(328, 328, 328))
        );

        jTablePaciente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTablePaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Prontuário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePaciente.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTablePaciente.setShowVerticalLines(false);
        jTablePaciente.getTableHeader().setReorderingAllowed(false);
        jTablePaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePacienteMouseClicked(evt);
            }
        });
        jTablePaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablePacienteKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePaciente);
        jTablePaciente.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTablePaciente.getColumnModel().getColumnCount() > 0) {
            jTablePaciente.getColumnModel().getColumn(1).setMinWidth(80);
            jTablePaciente.getColumnModel().getColumn(1).setMaxWidth(80);
        }

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jbtnReceituario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnReceituario.setText("Receituário");
        jbtnReceituario.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnReceituario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReceituarioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnReceituario, gridBagConstraints);

        jbtnExame.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnExame.setText("Exame");
        jbtnExame.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnExame, gridBagConstraints);

        jbtnAtestado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnAtestado.setText("Atestado");
        jbtnAtestado.setMaximumSize(new java.awt.Dimension(80, 25));
        jbtnAtestado.setMinimumSize(new java.awt.Dimension(80, 25));
        jbtnAtestado.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnAtestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAtestadoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnAtestado, gridBagConstraints);

        jbtnSair.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnSair.setText("Sair");
        jbtnSair.setMaximumSize(new java.awt.Dimension(80, 25));
        jbtnSair.setMinimumSize(new java.awt.Dimension(80, 25));
        jbtnSair.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSairActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnSair, gridBagConstraints);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBounds(0, 0, 531, 519);
    }// </editor-fold>//GEN-END:initComponents


    private void jbtnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPesquisaActionPerformed
        try {
            carregarJTablePaciente(pacienteController.pesquisarPacientes(jtxfPesquisa.getText()));
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_jbtnPesquisaActionPerformed

    private void jbtnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLimparPesquisaActionPerformed
        jtxfPesquisa.setText("");
        carregarTela();
        habilitarBotoes(false);
    }//GEN-LAST:event_jbtnLimparPesquisaActionPerformed

    private void jtxfPesquisaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxfPesquisaFocusGained
        jtxfPesquisa.setText("");
    }//GEN-LAST:event_jtxfPesquisaFocusGained

    private void jtxfPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                carregarJTablePaciente(pacienteController.pesquisarPacientes(jtxfPesquisa.getText()));
            } catch (Exception ex) {

            }
        }
    }//GEN-LAST:event_jtxfPesquisaKeyPressed

    private void jbtnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtnSairActionPerformed

    private void jbtnAtestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAtestadoActionPerformed
        abrirTelaAtestado();
    }//GEN-LAST:event_jbtnAtestadoActionPerformed
    private void jbtnExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExameActionPerformed
        abrirTelaPedidoExame();
    }//GEN-LAST:event_jbtnExameActionPerformed

    private void jbtnReceituarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReceituarioActionPerformed
        abrirTelaReceituario();
    }//GEN-LAST:event_jbtnReceituarioActionPerformed

    private void jTablePacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablePacienteKeyReleased
        selecionarTabelaPaciente();
    }//GEN-LAST:event_jTablePacienteKeyReleased

    private void jTablePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePacienteMouseClicked
        selecionarTabelaPaciente();
    }//GEN-LAST:event_jTablePacienteMouseClicked

    private void jbtnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNovoActionPerformed
        abrirTelaPacienteSimples();
    }//GEN-LAST:event_jbtnNovoActionPerformed

    private void jbtnExibirTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExibirTodosActionPerformed
        this.nomePesquisa = "";
        carregarTela();
    }//GEN-LAST:event_jbtnExibirTodosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePaciente;
    private javax.swing.JButton jbtnAtestado;
    private javax.swing.JButton jbtnExame;
    private javax.swing.JButton jbtnExibirTodos;
    private javax.swing.JButton jbtnLimparPesquisa;
    private javax.swing.JButton jbtnNovo;
    private javax.swing.JButton jbtnPesquisa;
    private javax.swing.JButton jbtnReceituario;
    private javax.swing.JButton jbtnSair;
    private javax.swing.JTextField jtxfPaciente;
    private javax.swing.JTextField jtxfPesquisa;
    // End of variables declaration//GEN-END:variables

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Paciente> getListPacientes() {
        return listPacientes;
    }

    public void setListPacientes(List<Paciente> listPacientes) {
        this.listPacientes = listPacientes;
    }

}
