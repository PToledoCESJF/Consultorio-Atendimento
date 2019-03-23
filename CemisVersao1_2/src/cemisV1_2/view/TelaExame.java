package cemisV1_2.view;

import cemisV1_2.controller.ExameController;
import cemisV1_2.model.Exame;
import cemisV1_2.model.Paciente;
import cemisV1_2.model.ExameTipo;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JInternalFrame;

public class TelaExame extends JInternalFrame {

    private Exame exame = new Exame();
    private ExameController exameController = new ExameController();
    private DefaultTableModel dtmExame = new DefaultTableModel();
    private boolean botaoClicado;
    private TelaPedidoExame tela = null;
    private int opcao = 0;
    private String msg = "";
    private int tipoMsg;
    private final int ERRO = 0;
    private final int CONF = 1;

    public TelaExame() {
        initComponents();
        carregarTela();
    }
    
    public TelaExame(TelaPedidoExame tpe) {
        initComponents();
        this.tela = tpe;
        carregarTela();
    }
    
    private void carregarTela() {
        habilitaCampos(false);
        jtxfidExame.setText("0");
        jtxfidExame.setVisible(false);
        try {
            carregarJTableExame(exameController.listarExames());
            carregarComboTipoExame(exameController.listarTipoExame());
        } catch (Exception ex) {
            Logger.getLogger(TelaPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    private void carregarJTableExame(List<Exame> listExames) {

        dtmExame = (DefaultTableModel) jTableExame.getModel();
        String tpExame = "";

        int numLinhas = dtmExame.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            dtmExame.removeRow(0);
        }
        for (Exame e : listExames) {
            dtmExame.addRow(new Object[]{
                exameController.getTipoExameById(e.getTipoexame()),
                e.getExame()
            });
        }
    }

    private void carregarComboTipoExame(List<ExameTipo> listTipoExames) {
        int tpExame;
        jcbxTipoExame.removeAllItems();
        for (ExameTipo tp : listTipoExames) {
            tpExame = tp.getIdTipoExame();
            jcbxTipoExame.addItem(exameController.getTipoExameById(tpExame));
        }
        limpaCampos();
    }

    private void botaoNovoOuAlterar(boolean botaoClicado) {
        this.botaoClicado = botaoClicado;
        if (botaoClicado) {
            limpaCampos();
            jcbxTipoExame.requestFocus();
        } else if (jtxfidExame.getText().equals("0")) {
            mensagem("Selecione o Exame a ser alterado.", ERRO);
            jtxfExame.requestFocus();
            return;
        }
        habilitaCampos(true);
    }

    private void habilitaCampos(boolean b) {
        jcbxTipoExame.setEnabled(b);
        jtxfExame.setEnabled(b);
        jbtnSalvar.setEnabled(b);
        jbtnCancelar.setEnabled(b);
        jbtnNovoTipo.setEnabled(b);
        jbtnNovo.setEnabled(!b);
        jbtnAlterar.setEnabled(!b);
        jbtnExcluir.setEnabled(!b);
        jbtnSair.setEnabled(!b);
        
        if(jbtnNovoTipo.isEnabled())
            jcbxTipoExame.requestFocus();
    }

    private void limpaCampos() {
        jtxfidExame.setText("0");
        jcbxTipoExame.setSelectedIndex(-1);
        jtxfExame.setText("");
    }

    private void confirmarSalvamento(int opcao) {

        switch (opcao) {
            case 1:
                mensagem("Exame cadastrado com sucesso!", CONF);
                break;
            case 2:
                mensagem("Exame não cadastrado. Entre em contato com a equipe de desenvolvimento.", ERRO);
                break;
            case 3:
                mensagem("Exame alterado com sucesso!", CONF);
                break;
            case 4:
                mensagem("Exame não foi alterado. Entre em contato com a equipe de desenvolvimento.", ERRO);
                break;
            case 5:
                mensagem("Os campos Tipo de Exame e Exame não podem ser vazios", ERRO);
                break;
        }
        if (tipoMsg == 1 && opcao == 1) {
            carregarTela();
            limpaCampos();
        }

        if (tipoMsg == 1 && opcao == 3) {
            carregarTela();
            habilitaCampos(false);
        }
        jcbxTipoExame.requestFocus();
    }

    private void selecionarTabelaExame() {
        if (jTableExame.getSelectedRow() != -1) {
            habilitaCampos(false);
            int indice = jTableExame.getSelectedRow();
            Exame exameAtual = new Exame();
            try {
                exameAtual = exameController.getListExames().get(indice);
                String tpExame = exameController.getTipoExameById(exameAtual.getTipoexame());
                jcbxTipoExame.setSelectedItem(tpExame);
                jtxfExame.setText(exameAtual.getExame());
                jtxfidExame.setText(exameAtual.getIdExame().toString());
            } catch (Exception ex) {
                mensagem("Não foi possível selecionar o exame desejado.", ERRO);
            }
        }
    }

    public void mensagem(String msg, int tipoMsg) {
        this.msg = msg;
        this.tipoMsg = tipoMsg;
        JOptionPane.showMessageDialog(this, msg, "Cadastro de Pacientes", tipoMsg);
    }
    
    private void salvarExame(){
        if (jcbxTipoExame.getSelectedIndex() != -1) {
            exame.setIdExame(Integer.parseInt(jtxfidExame.getText()));
            exame.setExame(jtxfExame.getText());
            exame.setTipoexame(exameController.getIdTipoExameByDesc(jcbxTipoExame.getSelectedItem().toString()));
            opcao = (exameController.salvarExame(exame, botaoClicado));
        } else {
            opcao = 5;
        }
        confirmarSalvamento(opcao);
        if(this.tela != null){
            fecharTela();
        }else{
            habilitaCampos(false);
        }
    }
    
    private void fecharTela(){
        this.dispose();
        
        this.tela.setVisible(true);
        this.tela.carregarTela();
        this.tela.habilitarBotoes(true);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableExame = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxfExame = new javax.swing.JTextField();
        jtxfidExame = new javax.swing.JTextField();
        jcbxTipoExame = new javax.swing.JComboBox<>();
        jbtnNovoTipo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jbtnNovo = new javax.swing.JButton();
        jbtnAlterar = new javax.swing.JButton();
        jbtnExcluir = new javax.swing.JButton();
        jbtnSair = new javax.swing.JButton();
        jbtnSalvar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Cadastro de Exames");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/cemisV1_2/view/Icone.png"))); // NOI18N

        jTableExame.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de Exame", "Exame"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableExame.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableExame.setShowVerticalLines(false);
        jTableExame.getTableHeader().setReorderingAllowed(false);
        jTableExame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableExameMouseClicked(evt);
            }
        });
        jTableExame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableExameKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableExame);
        jTableExame.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Tipo de Exame:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Exame:");

        jtxfExame.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfExame.setEnabled(false);
        jtxfExame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfExameKeyPressed(evt);
            }
        });

        jcbxTipoExame.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbxTipoExame.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione um tipo de exame..." }));
        jcbxTipoExame.setEnabled(false);
        jcbxTipoExame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcbxTipoExameKeyPressed(evt);
            }
        });

        jbtnNovoTipo.setText("Novo Tipo");
        jbtnNovoTipo.setEnabled(false);
        jbtnNovoTipo.setMaximumSize(new java.awt.Dimension(80, 25));
        jbtnNovoTipo.setMinimumSize(new java.awt.Dimension(80, 25));
        jbtnNovoTipo.setPreferredSize(new java.awt.Dimension(80, 25));
        jbtnNovoTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNovoTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxfidExame, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jcbxTipoExame, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jbtnNovoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jtxfExame, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbxTipoExame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnNovoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxfExame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxfidExame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jcbxTipoExame.getAccessibleContext().setAccessibleDescription("");

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jbtnNovo.setText("Novo");
        jbtnNovo.setPreferredSize(new java.awt.Dimension(80, 25));
        jbtnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNovoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnNovo, gridBagConstraints);

        jbtnAlterar.setText("Alterar");
        jbtnAlterar.setPreferredSize(new java.awt.Dimension(80, 25));
        jbtnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAlterarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnAlterar, gridBagConstraints);

        jbtnExcluir.setText("Excluir");
        jbtnExcluir.setMaximumSize(new java.awt.Dimension(80, 25));
        jbtnExcluir.setMinimumSize(new java.awt.Dimension(80, 25));
        jbtnExcluir.setPreferredSize(new java.awt.Dimension(80, 25));
        jbtnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExcluirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnExcluir, gridBagConstraints);

        jbtnSair.setText("Sair");
        jbtnSair.setMaximumSize(new java.awt.Dimension(80, 25));
        jbtnSair.setMinimumSize(new java.awt.Dimension(80, 25));
        jbtnSair.setPreferredSize(new java.awt.Dimension(80, 25));
        jbtnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSairActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnSair, gridBagConstraints);

        jbtnSalvar.setText("Salvar");
        jbtnSalvar.setPreferredSize(new java.awt.Dimension(80, 25));
        jbtnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalvarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnSalvar, gridBagConstraints);

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.setPreferredSize(new java.awt.Dimension(80, 25));
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnCancelar, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableExameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableExameMouseClicked
        selecionarTabelaExame();
    }//GEN-LAST:event_jTableExameMouseClicked

    private void jTableExameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableExameKeyReleased
        selecionarTabelaExame();
    }//GEN-LAST:event_jTableExameKeyReleased

    private void jtxfExameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfExameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jcbxTipoExame.requestFocus();
        }
    }//GEN-LAST:event_jtxfExameKeyPressed

    private void jbtnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNovoActionPerformed
        botaoNovoOuAlterar(true);
    }//GEN-LAST:event_jbtnNovoActionPerformed

    private void jbtnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAlterarActionPerformed
        botaoNovoOuAlterar(false);
    }//GEN-LAST:event_jbtnAlterarActionPerformed

    private void jbtnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExcluirActionPerformed
        try {
            if (exameController.excluirExame(Integer.parseInt(jtxfidExame.getText()))) {
                mensagem("Exame excluido com sucessso!", CONF);
                limpaCampos();
                carregarTela();
            } else {
                mensagem("Não foi possível excluir este exame.", ERRO);
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jbtnExcluirActionPerformed

    private void jbtnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSairActionPerformed
        fecharTela();
    }//GEN-LAST:event_jbtnSairActionPerformed

    private void jbtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalvarActionPerformed
        salvarExame();
    }//GEN-LAST:event_jbtnSalvarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        habilitaCampos(false);
        limpaCampos();
        carregarTela();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jbtnNovoTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNovoTipoActionPerformed
        String tExame = JOptionPane.showInputDialog("Tipo de Exame:  ", null);
        exameController.salvarTipoExame(tExame);
        carregarTela();
    }//GEN-LAST:event_jbtnNovoTipoActionPerformed

    private void jcbxTipoExameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcbxTipoExameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfExame.requestFocus();
        }
    }//GEN-LAST:event_jcbxTipoExameKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableExame;
    private javax.swing.JButton jbtnAlterar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnExcluir;
    private javax.swing.JButton jbtnNovo;
    private javax.swing.JButton jbtnNovoTipo;
    private javax.swing.JButton jbtnSair;
    private javax.swing.JButton jbtnSalvar;
    private javax.swing.JComboBox<Object> jcbxTipoExame;
    private javax.swing.JTextField jtxfExame;
    private javax.swing.JTextField jtxfidExame;
    // End of variables declaration//GEN-END:variables
}
