package cemisV1_2.view;

import cemisV1_2.controller.MedicamentoController;
import cemisV1_2.model.Medicamento;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaMedicamento extends javax.swing.JInternalFrame {

    private Medicamento medicamento = new Medicamento();
    private MedicamentoController medicamentoController = new MedicamentoController();

    private DefaultTableModel dtmMedicamento = new DefaultTableModel();
    private boolean botaoClicado;
    private int opcao = 0;
    private String msg = "";
    private int tipoMsg;
    private final int ERRO = 0;
    private final int CONF = 1;

    public TelaMedicamento() {
        initComponents();
        carregarTela();
        jtxfIdMedicamento.setVisible(false);
    }

    private void carregarJTableMedicamento(List<Medicamento> listMedicamentos) throws Exception {

        dtmMedicamento = (DefaultTableModel) jTableMedicamento.getModel();

        int numLinhas = dtmMedicamento.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            dtmMedicamento.removeRow(0);
        }
        for (Medicamento med : listMedicamentos) {
            dtmMedicamento.addRow(new Object[]{
                med.getPrincipioAtivo(),
                med.getMedicamento()
            });
        }
    }

    private void carregarTela() {

        try {
            carregarJTableMedicamento(medicamentoController.listarMedicamentos());
        } catch (Exception ex) {
            Logger.getLogger(TelaPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void botaoNovoOuAlterar(boolean botaoClicado) {
        this.botaoClicado = botaoClicado;
        if (botaoClicado) {
            limpaCampos();
        } else if (jtxfIdMedicamento.getText().equals("0")) {
            mensagem("Selecione o medicamento a ser alterado.", ERRO);
            jtxfPesquisa.requestFocus();
            return;
        }

        habilitaCampos();

    }

    private void habilitaCampos() {
        jtxfPrincipioAtivo.setEnabled(true);
        jtxfMedicamento.setEnabled(true);
        jtxfLaboratorio.setEnabled(true);
        jtxfApresentacao.setEnabled(true);
        jbtnNovo.setEnabled(false);
        jbtnAlterar.setEnabled(false);
        jbtnExcluir.setEnabled(false);
        jbtnSalvar.setEnabled(true);
        jbtnCancelar.setEnabled(true);
        jbtnSair.setEnabled(false);
        jtxfPrincipioAtivo.requestFocus();
    }

    private void desabilitaCampos() {
        jtxfPrincipioAtivo.setEnabled(false);
        jtxfMedicamento.setEnabled(false);
        jtxfLaboratorio.setEnabled(false);
        jtxfApresentacao.setEnabled(false);
        jbtnNovo.setEnabled(true);
        jbtnAlterar.setEnabled(true);
        jbtnExcluir.setEnabled(true);
        jbtnSalvar.setEnabled(false);
        jbtnCancelar.setEnabled(false);
        jbtnSair.setEnabled(true);
    }

    private void limpaCampos() {
        jtxfIdMedicamento.setText("0");
        jtxfPrincipioAtivo.setText("");
        jtxfMedicamento.setText("");
        jtxfLaboratorio.setText("");
        jtxfApresentacao.setText("");
        jtxfPesquisa.setText("");
    }
    
    private void salvarMedicamento(){
        opcao = (medicamentoController.salvarMedicamento(jtxfIdMedicamento.getText().trim(),
                    jtxfLaboratorio.getText().trim(), jtxfApresentacao.getText().trim(),
                    jtxfPrincipioAtivo.getText().trim(), jtxfMedicamento.getText().trim(),
                    botaoClicado));
        confirmarSalvamento(opcao);
    }

    private void confirmarSalvamento(int opcao) {

        switch (opcao) {
            case 1:
                mensagem("Medicamento cadastrado com sucesso!", CONF);
                break;
            case 2:
                mensagem("Medicamento não cadastrado. Entre em contato com a equipe de desenvolvimento.", ERRO);
                break;
            case 3:
                mensagem("Medicamento alterado com sucesso!", CONF);
                break;
            case 4:
                mensagem("Medicamento não foi alterado. Entre em contato com a equipe de desenvolvimento.", ERRO);
                break;
            case 5:
                mensagem("Os campos Princípio Ativo e Medicamento não podem ser vazios", ERRO);
                break;
        }
        if (tipoMsg == 1 && opcao == 1) {
            carregarTela();
            limpaCampos();
        }

        if (tipoMsg == 1 && opcao == 3) {
            carregarTela();
            desabilitaCampos();
        }
        jtxfPrincipioAtivo.requestFocus();
    }

    private void selecionarTabelaMedicamento() {
        if (jTableMedicamento.getSelectedRow() != -1) {
            desabilitaCampos();
            int indice = jTableMedicamento.getSelectedRow();
            Medicamento medicamentoAtual = new Medicamento();
            try {
                medicamentoAtual = medicamentoController.getListMedicamentos().get(indice);
                jtxfPrincipioAtivo.setText(medicamentoAtual.getPrincipioAtivo());
                jtxfMedicamento.setText(medicamentoAtual.getMedicamento());
                jtxfLaboratorio.setText(medicamentoAtual.getLaboratorio());
                jtxfApresentacao.setText(medicamentoAtual.getApresentacao());
                jtxfIdMedicamento.setText(medicamentoAtual.getIdMedicamento().toString());
            } catch (Exception ex) {
                mensagem("Não foi possível selecionar o medicamento desejado.", ERRO);
            }
        }
    }

    public void mensagem(String msg, int tipoMsg) {
        this.msg = msg;
        this.tipoMsg = tipoMsg;
        JOptionPane.showMessageDialog(this, msg, "Cadastro de Pacientes", tipoMsg);
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMedicamento = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxfPrincipioAtivo = new javax.swing.JTextField();
        jtxfMedicamento = new javax.swing.JTextField();
        jtxfLaboratorio = new javax.swing.JTextField();
        jtxfApresentacao = new javax.swing.JTextField();
        jtxfIdMedicamento = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jbtnNovo = new javax.swing.JButton();
        jbtnAlterar = new javax.swing.JButton();
        jbtnExcluir = new javax.swing.JButton();
        jbtnSair = new javax.swing.JButton();
        jbtnSalvar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();

        setTitle("Cadastro de Medicamentos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/cemisV1_2/view/Icone.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Principio Ativo ou Medicamento:");

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

        jbtnPesquisa.setText("Pesquisar");
        jbtnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPesquisaActionPerformed(evt);
            }
        });

        jbtnLimparPesquisa.setText("Limpar Pesquisa");
        jbtnLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLimparPesquisaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtxfPesquisa, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jbtnPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jbtnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                    .addComponent(jbtnPesquisa)
                    .addComponent(jbtnLimparPesquisa))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTableMedicamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Princípio Ativo", "Medicamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMedicamento.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableMedicamento.setShowVerticalLines(false);
        jTableMedicamento.getTableHeader().setReorderingAllowed(false);
        jTableMedicamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMedicamentoMouseClicked(evt);
            }
        });
        jTableMedicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableMedicamentoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMedicamento);
        jTableMedicamento.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 523, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Princípio Ativo:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Medicamento:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Laboratório:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Apresentação:");

        jtxfPrincipioAtivo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfPrincipioAtivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfPrincipioAtivoKeyPressed(evt);
            }
        });

        jtxfMedicamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfMedicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfMedicamentoKeyPressed(evt);
            }
        });

        jtxfLaboratorio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfLaboratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfLaboratorioKeyPressed(evt);
            }
        });

        jtxfApresentacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfApresentacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfApresentacaoKeyPressed(evt);
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
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtxfLaboratorio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                    .addComponent(jtxfMedicamento, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxfIdMedicamento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxfPrincipioAtivo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxfApresentacao))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxfMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxfPrincipioAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxfLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxfApresentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxfIdMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxfPesquisaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxfPesquisaFocusGained
        desabilitaCampos();
        limpaCampos();
    }//GEN-LAST:event_jtxfPesquisaFocusGained

    private void jtxfPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                carregarJTableMedicamento(medicamentoController.pesquisarMedicamento(jtxfPesquisa.getText()));
            } catch (Exception ex) {
                mensagem("Não foi possível acessar a base de dados!", ERRO);
            }
        }
    }//GEN-LAST:event_jtxfPesquisaKeyPressed

    private void jbtnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPesquisaActionPerformed
        try {
            carregarJTableMedicamento(medicamentoController.pesquisarMedicamento(jtxfPesquisa.getText()));
        } catch (Exception ex) {
            mensagem("Não foi possível acessar a base de dados!", ERRO);
        }
    }//GEN-LAST:event_jbtnPesquisaActionPerformed

    private void jbtnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLimparPesquisaActionPerformed
        desabilitaCampos();
        limpaCampos();
        carregarTela();
    }//GEN-LAST:event_jbtnLimparPesquisaActionPerformed

    private void jTableMedicamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMedicamentoMouseClicked
        selecionarTabelaMedicamento();
    }//GEN-LAST:event_jTableMedicamentoMouseClicked

    private void jTableMedicamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMedicamentoKeyReleased
        selecionarTabelaMedicamento();
    }//GEN-LAST:event_jTableMedicamentoKeyReleased

    private void jbtnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNovoActionPerformed
        botaoNovoOuAlterar(true);
    }//GEN-LAST:event_jbtnNovoActionPerformed

    private void jbtnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAlterarActionPerformed
        botaoNovoOuAlterar(false);
    }//GEN-LAST:event_jbtnAlterarActionPerformed

    private void jbtnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExcluirActionPerformed
        try {
            if (medicamentoController.excluirMedicamento(Integer.parseInt(jtxfIdMedicamento.getText()))) {
                mensagem("Medicamento excluido com sucessso!", CONF);
                limpaCampos();
                carregarTela();
            } else {
                mensagem("Não foi possível excluir este medicamento.", ERRO);
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jbtnExcluirActionPerformed

    private void jbtnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtnSairActionPerformed

    private void jbtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalvarActionPerformed
        salvarMedicamento();
    }//GEN-LAST:event_jbtnSalvarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        desabilitaCampos();
        limpaCampos();
        carregarTela();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jtxfPrincipioAtivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfPrincipioAtivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfLaboratorio.requestFocus();
        }
    }//GEN-LAST:event_jtxfPrincipioAtivoKeyPressed

    private void jtxfMedicamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfMedicamentoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfPrincipioAtivo.requestFocus();
        }
    }//GEN-LAST:event_jtxfMedicamentoKeyPressed

    private void jtxfLaboratorioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfLaboratorioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfApresentacao.requestFocus();
        }
    }//GEN-LAST:event_jtxfLaboratorioKeyPressed

    private void jtxfApresentacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfApresentacaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfMedicamento.requestFocus();
        }
    }//GEN-LAST:event_jtxfApresentacaoKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMedicamento;
    private javax.swing.JButton jbtnAlterar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnExcluir;
    private javax.swing.JButton jbtnLimparPesquisa;
    private javax.swing.JButton jbtnNovo;
    private javax.swing.JButton jbtnPesquisa;
    private javax.swing.JButton jbtnSair;
    private javax.swing.JButton jbtnSalvar;
    private javax.swing.JTextField jtxfApresentacao;
    private javax.swing.JTextField jtxfIdMedicamento;
    private javax.swing.JTextField jtxfLaboratorio;
    private javax.swing.JTextField jtxfMedicamento;
    private javax.swing.JTextField jtxfPesquisa;
    private javax.swing.JTextField jtxfPrincipioAtivo;
    // End of variables declaration//GEN-END:variables
}
