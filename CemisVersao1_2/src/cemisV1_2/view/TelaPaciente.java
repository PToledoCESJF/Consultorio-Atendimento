package cemisV1_2.view;

import cemisV1_2.controller.PacienteController;
import cemisV1_2.model.Paciente;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaPaciente extends javax.swing.JInternalFrame {

    private Paciente paciente = new Paciente();
    private PacienteController pacienteController = new PacienteController();

    private DefaultTableModel dtmPacientes = new DefaultTableModel();
    private boolean botaoClicado;
    private int opcao = 0;
    private String msg = "";
    private int tipoMsg;
    private final int ERRO = 0;
    private final int CONF = 1;

    public TelaPaciente() {
        initComponents();
        carregarTela();
        jtxfIdPaciente.setVisible(false);
    }

    private void carregarJTablePaciente(List<Paciente> listPacientes) throws Exception {

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

    private void carregarTela() {

        try {
            carregarJTablePaciente(pacienteController.listarPacientes());
        } catch (Exception ex) {
            Logger.getLogger(TelaPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void botaoNovoOuAlterar(boolean botaoClicado) {
        this.botaoClicado = botaoClicado;
        if (botaoClicado) {
            limpaCampos();
        } else if (jtxfIdPaciente.getText().equals("0")) {
            mensagem("Selecione o paciente a ser alterado.", ERRO);
            jtxfPesquisa.requestFocus();
            return;
        }

        habilitaCampos();
    }

    private void habilitaCampos() {
        jtxfNome.setEnabled(true);
        jtxfProntuario.setEnabled(true);
        jtxfEndereco.setEnabled(true);
        jtxfCidade.setEnabled(true);
        jtxfTelefone.setEnabled(true);
        jbtnNovo.setEnabled(false);
        jbtnAlterar.setEnabled(false);
        jbtnExcluir.setEnabled(false);
        jbtnSalvar.setEnabled(true);
        jbtnCancelar.setEnabled(true);
        jbtnSair.setEnabled(false);
        jtxfNome.requestFocus();
    }

    private void desabilitaCampos() {
        jtxfNome.setEnabled(false);
        jtxfProntuario.setEnabled(false);
        jtxfEndereco.setEnabled(false);
        jtxfCidade.setEnabled(false);
        jtxfTelefone.setEnabled(false);
        jbtnNovo.setEnabled(true);
        jbtnAlterar.setEnabled(true);
        jbtnExcluir.setEnabled(true);
        jbtnSalvar.setEnabled(false);
        jbtnCancelar.setEnabled(false);
        jbtnSair.setEnabled(true);
    }

    private void limpaCampos() {
        jtxfIdPaciente.setText("0");
        jtxfNome.setText("");
        jtxfProntuario.setText("");
        jtxfEndereco.setText("");
        jtxfCidade.setText("");
        jtxfTelefone.setText("");
        jtxfPesquisa.setText("");
    }

    private void confirmarSalvamento(int opcao) {

        switch (opcao) {
            case 1:
                mensagem("Paciente cadastrado com sucesso!", CONF);
                break;
            case 2:
                mensagem("Paciente não cadastrado. Entre em contato com a equipe de desenvolvimento.", ERRO);
                break;
            case 3:
                mensagem("Paciente alterado com sucesso!", CONF);
                break;
            case 4:
                mensagem("Paciente não foi alterado. Entre em contato com a equipe de desenvolvimento.", ERRO);
                break;
            case 5:
                mensagem("Os campos Nome do paciente e prontuário não podem ser vazios", ERRO);
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
        jtxfNome.requestFocus();
    }

    private void selecionarTabelaPaciente() {
        if (jTablePaciente.getSelectedRow() != -1) {
            desabilitaCampos();
            int indice = jTablePaciente.getSelectedRow();
            Paciente pacienteAtual = new Paciente();
            try {
                pacienteAtual = pacienteController.getListPacientes().get(indice);
                jtxfNome.setText(pacienteAtual.getNomePaciente());
                jtxfProntuario.setText(pacienteAtual.getProntuario());
                jtxfEndereco.setText(pacienteAtual.getEndereco());
                jtxfCidade.setText(pacienteAtual.getCidade());
                jtxfTelefone.setText(pacienteAtual.getTelefone());
                jtxfIdPaciente.setText(pacienteAtual.getIdPaciente().toString());
            } catch (Exception ex) {
                mensagem("Não foi possível selecionar o paciente desejado.", ERRO);
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

        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jbtnNovo = new javax.swing.JButton();
        jbtnAlterar = new javax.swing.JButton();
        jbtnExcluir = new javax.swing.JButton();
        jbtnSair = new javax.swing.JButton();
        jbtnSalvar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtxfNome = new javax.swing.JTextField();
        jtxfProntuario = new javax.swing.JTextField();
        jtxfEndereco = new javax.swing.JTextField();
        jtxfCidade = new javax.swing.JTextField();
        jtxfTelefone = new javax.swing.JTextField();
        jPanelPesquisa = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxfPesquisa = new javax.swing.JTextField();
        jbtnPesquisa = new javax.swing.JButton();
        jbtnLimparPesquisa = new javax.swing.JButton();
        jPanelTabela = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePaciente = new javax.swing.JTable();
        jtxfIdPaciente = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Cadastro de Pacientes");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/cemisV1_2/view/Icone.png"))); // NOI18N

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

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Nome:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel3.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Prontuário:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel3.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Endereço:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel3.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Cidade:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel3.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Telefone:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel3.add(jLabel7, gridBagConstraints);

        jtxfNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfNome.setEnabled(false);
        jtxfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfNomeKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 430;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 2.7;
        jPanel3.add(jtxfNome, gridBagConstraints);

        jtxfProntuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfProntuario.setEnabled(false);
        jtxfProntuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfProntuarioKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 430;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(jtxfProntuario, gridBagConstraints);

        jtxfEndereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfEndereco.setEnabled(false);
        jtxfEndereco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfEnderecoKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 430;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(jtxfEndereco, gridBagConstraints);

        jtxfCidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfCidade.setEnabled(false);
        jtxfCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfCidadeKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 430;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(jtxfCidade, gridBagConstraints);

        jtxfTelefone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfTelefone.setEnabled(false);
        jtxfTelefone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfTelefoneKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 430;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(jtxfTelefone, gridBagConstraints);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Nome ou Prontuário: ");

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

        javax.swing.GroupLayout jPanelPesquisaLayout = new javax.swing.GroupLayout(jPanelPesquisa);
        jPanelPesquisa.setLayout(jPanelPesquisaLayout);
        jPanelPesquisaLayout.setHorizontalGroup(
            jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPesquisaLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxfPesquisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelPesquisaLayout.setVerticalGroup(
            jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPesquisaLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnPesquisa)
                    .addComponent(jbtnLimparPesquisa))
                .addContainerGap())
        );

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

        javax.swing.GroupLayout jPanelTabelaLayout = new javax.swing.GroupLayout(jPanelTabela);
        jPanelTabela.setLayout(jPanelTabelaLayout);
        jPanelTabelaLayout.setHorizontalGroup(
            jPanelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTabelaLayout.setVerticalGroup(
            jPanelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtxfIdPaciente.setEnabled(false);
        jtxfIdPaciente.setRequestFocusEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtxfIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtxfIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBounds(0, 0, 981, 404);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPesquisaActionPerformed
        try {
            carregarJTablePaciente(pacienteController.pesquisarPacientes(jtxfPesquisa.getText()));
        } catch (Exception ex) {
            mensagem("Não foi possível acessar a base de dados!", ERRO);
        }
    }//GEN-LAST:event_jbtnPesquisaActionPerformed

    private void jbtnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAlterarActionPerformed
        botaoNovoOuAlterar(false);
    }//GEN-LAST:event_jbtnAlterarActionPerformed

    private void jbtnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNovoActionPerformed
        botaoNovoOuAlterar(true);
    }//GEN-LAST:event_jbtnNovoActionPerformed

    private void jbtnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLimparPesquisaActionPerformed
        desabilitaCampos();
        limpaCampos();
        carregarTela();
    }//GEN-LAST:event_jbtnLimparPesquisaActionPerformed

    private void jbtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalvarActionPerformed
        opcao = (pacienteController.salvarPaciente(jtxfIdPaciente.getText().trim(),
                jtxfNome.getText().trim(), jtxfProntuario.getText().trim(),
                jtxfEndereco.getText().trim(), jtxfCidade.getText().trim(),
                jtxfTelefone.getText().trim(), botaoClicado));
        confirmarSalvamento(opcao);
    }//GEN-LAST:event_jbtnSalvarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        desabilitaCampos();
        limpaCampos();
        carregarTela();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jtxfPesquisaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxfPesquisaFocusGained
        desabilitaCampos();
        limpaCampos();
    }//GEN-LAST:event_jtxfPesquisaFocusGained

    private void jTablePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePacienteMouseClicked
        selecionarTabelaPaciente();
    }//GEN-LAST:event_jTablePacienteMouseClicked

    private void jTablePacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablePacienteKeyReleased
        selecionarTabelaPaciente();
    }//GEN-LAST:event_jTablePacienteKeyReleased

    private void jtxfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfProntuario.requestFocus();
        }
    }//GEN-LAST:event_jtxfNomeKeyPressed

    private void jtxfProntuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfProntuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfEndereco.requestFocus();
        }
    }//GEN-LAST:event_jtxfProntuarioKeyPressed

    private void jtxfEnderecoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfEnderecoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfCidade.requestFocus();
        }
    }//GEN-LAST:event_jtxfEnderecoKeyPressed

    private void jtxfCidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfCidadeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfTelefone.requestFocus();
        }
    }//GEN-LAST:event_jtxfCidadeKeyPressed

    private void jtxfTelefoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfTelefoneKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfNome.requestFocus();
        }
    }//GEN-LAST:event_jtxfTelefoneKeyPressed

    private void jbtnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExcluirActionPerformed
        try {
            if (pacienteController.excluirPaciente(Integer.parseInt(jtxfIdPaciente.getText()))) {
                mensagem("Paciente excluido com sucessso!", CONF);
                limpaCampos();
                carregarTela();
            } else {
                mensagem("Não foi possível excluir este paciente.", ERRO);
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jbtnExcluirActionPerformed

    private void jbtnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtnSairActionPerformed

    private void jtxfPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                carregarJTablePaciente(pacienteController.pesquisarPacientes(jtxfPesquisa.getText()));
            } catch (Exception ex) {
                mensagem("Não foi possível acessar a base de dados!", 0);
            }
        }
    }//GEN-LAST:event_jtxfPesquisaKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelPesquisa;
    private javax.swing.JPanel jPanelTabela;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePaciente;
    private javax.swing.JButton jbtnAlterar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnExcluir;
    private javax.swing.JButton jbtnLimparPesquisa;
    private javax.swing.JButton jbtnNovo;
    private javax.swing.JButton jbtnPesquisa;
    private javax.swing.JButton jbtnSair;
    private javax.swing.JButton jbtnSalvar;
    private javax.swing.JTextField jtxfCidade;
    private javax.swing.JTextField jtxfEndereco;
    private javax.swing.JTextField jtxfIdPaciente;
    private javax.swing.JTextField jtxfNome;
    private javax.swing.JTextField jtxfPesquisa;
    private javax.swing.JTextField jtxfProntuario;
    private javax.swing.JTextField jtxfTelefone;
    // End of variables declaration//GEN-END:variables

}
