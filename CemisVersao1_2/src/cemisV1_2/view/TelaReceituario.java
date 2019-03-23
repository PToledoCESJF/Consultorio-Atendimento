package cemisV1_2.view;

import cemisV1_2.controller.MedicamentoController;
import cemisV1_2.controller.ReceituarioController;
import cemisV1_2.model.ReceituarioItens;
import cemisV1_2.model.Medicamento;
import cemisV1_2.model.Receituario;
import cemisV1_2.model.Paciente;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaReceituario extends javax.swing.JInternalFrame {

    private TelaAtendimento tela;
    private Paciente paciente = new Paciente();
    private ReceituarioItens itensReceituario = new ReceituarioItens();
    private ReceituarioController receituarioController = new ReceituarioController();
    private MedicamentoController medicamentoController = new MedicamentoController();
    private DefaultTableModel dtmMedicamento = new DefaultTableModel();
    private DefaultTableModel dtmItensReceituario = new DefaultTableModel();
    private String msg = "";
    private int tipoMsg;
    private final int ERRO = 0;
    private final int CONF = 1;
    private int ordemI = 1;
    private int ordemE = 1;

    public TelaReceituario(TelaAtendimento ta, Paciente pac) {
        initComponents();
        this.tela = ta;
        this.paciente = pac;
        jdtcData.setDate(new Date());
        carregarTela();
    }
    
    public void carregarTela() {
        jlblPaciente.setText(paciente.getNomePaciente());
        jtxfMedicamento.setText("Medicamento");
        jtxfMedicamento.setBackground(Color.white);
        jtxfIdMedicamento.setVisible(false);
        habilitarBotoes(false);
        habilitaCampos(false);
        try {
            carregarJTableMedicamento(medicamentoController.listarMedicamentos());
        } catch (Exception ex) {
            System.out.println("Erro ao carregar tela: "+ex);
        }
    }

    private void carregarJTableMedicamento(List<Medicamento> listMedicamentos) throws Exception {
        dtmMedicamento = (DefaultTableModel) jTableMedicamento.getModel();
        int numLinhas = dtmMedicamento.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            dtmMedicamento.removeRow(0);
        }
        listMedicamentos.forEach((med) -> {
            dtmMedicamento.addRow(new Object[]{
                med.getPrincipioAtivo(),
                med.getMedicamento()
            });
        });
    }

    private void carregarJTableItemReceituario(int ordem, String med, String poss, String quant, String dose, String uso) {
        dtmItensReceituario = (DefaultTableModel) jTableItensReceituario.getModel();
        dtmItensReceituario.addRow(new Object[]{ordem, med, poss, dose, quant, uso});
    }

    private void selecionarTabelaMedicamento() {
        if (jTableMedicamento.getSelectedRow() != -1) {
            int indice = jTableMedicamento.getSelectedRow();
            Medicamento medicamentoAtual;
            try {
                medicamentoAtual = medicamentoController.getListMedicamentos().get(indice);
                jtxfMedicamento.setText(medicamentoAtual.getMedicamento());
                jtxfMedicamento.setBackground(Color.cyan);
                jtxfIdMedicamento.setText(medicamentoAtual.getIdMedicamento().toString());
                habilitaCampos(true);
                jtxfQuantidade.requestFocus();
            } catch (Exception ex) {
                mensagem("Não foi possível selecionar o medicamento desejado.", ERRO);
            }
        }
    }
    
    private void cadastrarMedicamento(){
        this.setVisible(false);
        
        TelaMedicamentoSimples tela = new TelaMedicamentoSimples(this);
        TelaPrincipal.jdpPrincipal.add(tela);
        TelaPrincipal.centralizaTela(tela);
        tela.setVisible(true);
        tela.toFront();
        
    }

    private void limparJTableItemReceituario(boolean limparTudo) {
        dtmItensReceituario = (DefaultTableModel) jTableItensReceituario.getModel();
        int numLinhas = dtmItensReceituario.getRowCount();
        if (limparTudo) {
            for (int i = 0; i < numLinhas; i++) {
                dtmItensReceituario.removeRow(0);
            }
        } else {
            if (jTableItensReceituario.getSelectedRow() != -1) {
                int indice = jTableItensReceituario.getSelectedRow();
                dtmItensReceituario.removeRow(indice);
                numLinhas -= 1;
            }
            if (numLinhas == 0) {
                limpaCampos();
                carregarTela();
            }
        }
    }
    
    private void inserirItemReceituario(){
        String med = jtxfMedicamento.getText();
        String poss = jtxaPossologia.getText();
        String quant = jtxfQuantidade.getText();
        String dose = jtxfDose.getText();
        String uso;
        int ordem;

        if (buttonGroupUso.isSelected(rbtnUsoInterno.getModel())) {
            uso = "Interno";
            ordem = ordemI;
            ordemI++;
        } else {
            uso = "Externo";
            ordem = ordemE;
            ordemE++;
        }
        if (!med.equals("Medicamento") && !poss.equals("") && !quant.equals("") && !uso.equals("")) {
            carregarJTableItemReceituario(ordem, med, poss, quant, dose, uso);
            habilitarBotoes(true);
            limpaCampos();
        } else {
            mensagem("Os campos: Quantidade, Possologia e o Tipo de uso, devem ser preenchidos corretamente.", ERRO);
            jtxfQuantidade.requestFocus();
        }
    }
    
    private void salvarReceituario(){
        Receituario r = new Receituario();
        r.setPaciente(this.paciente.getIdPaciente());
        r.setDataReceituario(jdtcData.getDate());
        if (receituarioController.salvarReceituario(r)) {
            confirmarSalvamento(salvarItensReceituario());
            habilitaCampos(false);
            habilitarBotoes(false);
            ordemI = ordemE = 1;
        }
    }

    private Receituario salvarItensReceituario() {
        dtmItensReceituario = (DefaultTableModel) jTableItensReceituario.getModel();
        if (dtmItensReceituario.getRowCount() > 0) {
            int numLinhas = dtmItensReceituario.getRowCount();
            Receituario receituarioSalvo = receituarioController.receituarioAtual();
            for (int i = 0; i < numLinhas; i++) {
                itensReceituario.setReceituario(receituarioSalvo.getIdReceituario());
                itensReceituario.setOrdem(Integer.parseInt(jTableItensReceituario.getValueAt(i, 0).toString()));
                itensReceituario.setMedicamento(medicamentoController.getIdMedicamentoByDesc(jTableItensReceituario.getValueAt(i, 1).toString()));
                itensReceituario.setPosologia(jTableItensReceituario.getValueAt(i, 2).toString());
                itensReceituario.setDose(jTableItensReceituario.getValueAt(i, 3).toString());
                itensReceituario.setQuantidade(jTableItensReceituario.getValueAt(i, 4).toString());
                if ((jTableItensReceituario.getValueAt(i, 5).toString()).equals("Interno")) {
                    itensReceituario.setTipoUso("Uso Interno");
                } else {
                    itensReceituario.setTipoUso("Uso Externo");
                }
                receituarioController.salvarItemReceituario(itensReceituario);
            }
            limparJTableItemReceituario(true);
            return receituarioSalvo;
        } else {
            return null;
        }
    }
    
    private void confirmarSalvamento(Receituario r) {
        int opcao = 2;
        if(r != null){
            opcao = 1;
        }
        switch (opcao) {
            case 1:
                this.setVisible(false);
//                TelaReceituarioRelatorio tela = new TelaReceituarioRelatorio(this, r);
                TelaRelatorios tela = new TelaRelatorios(this, r);
                TelaPrincipal.jdpPrincipal.add(tela);
                TelaPrincipal.centralizaTela(tela);
                tela.setVisible(true);
                break;
            case 2:
                mensagem("Receituario não cadastrado. Entre em contato com a "
                        + "equipe de desenvolvimento.", ERRO);
                break;
            case 3:
                mensagem("Receituario alterado com sucesso!", CONF);
                break;
            case 4:
                mensagem("Receituario não foi alterado. Entre em contato com a "
                        + "equipe de desenvolvimento.", ERRO);
                break;
        }
        if (tipoMsg == 1 && opcao == 1) {
            carregarTela();
            limpaCampos();
        }
        if (tipoMsg == 1 && opcao == 3) {
            carregarTela();
        }
    }

    public void mensagem(String msg, int tipoMsg) {
        this.msg = msg;
        this.tipoMsg = tipoMsg;
        JOptionPane.showMessageDialog(this, msg, "Cadastro de Pacientes", tipoMsg);
    }

    public void habilitarBotoes(boolean b) {
        jbtnSalvar.setEnabled(b);
        jbtnCancelar.setEnabled(b);
        jbtnRemover.setEnabled(b);
    }

    private void habilitaCampos(boolean b) {
        jbtnInserir.setEnabled(b);
        jtxfQuantidade.setEnabled(b);
        jtxaPossologia.setEnabled(b);
        jtxfDose.setEnabled(b);
        rbtnUsoInterno.setEnabled(b);
        rbtnUsoExterno.setEnabled(b);
        if (!b) {
            jtxfPesquisa.requestFocus();
        }
    }

    private void limpaCampos() {
        jtxfIdMedicamento.setText("0");
        jtxfMedicamento.setText("Medicamento");
        jtxfMedicamento.setBackground(Color.white);
        jtxfPesquisa.setText("");
        jtxaPossologia.setText("");
        jtxfDose.setText("");
        jtxfQuantidade.setText("");
        jtxfPesquisa.requestFocus();
    }
    
    private void cancelarLancamento(){
        limparJTableItemReceituario(true);
        limpaCampos();
        carregarTela();
    }
    
    private void fecharTela(){
        this.dispose();
        this.tela.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupUso = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxfPesquisa = new javax.swing.JTextField();
        jbtnPesquisa = new javax.swing.JButton();
        jbtnLimparPesquisa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMedicamento = new javax.swing.JTable();
        jbtnNovoMedicamento = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxfQuantidade = new javax.swing.JTextField();
        jtxfIdMedicamento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtxaPossologia = new javax.swing.JTextArea();
        rbtnUsoExterno = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jbtnInserir = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jtxfDose = new javax.swing.JTextField();
        rbtnUsoInterno = new javax.swing.JRadioButton();
        jtxfMedicamento = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jlblPaciente = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jdtcData = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableItensReceituario = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jbtnSalvar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jbtnSair = new javax.swing.JButton();
        jbtnRemover = new javax.swing.JButton();

        setTitle("Receituário");
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxfPesquisa))
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

        jbtnNovoMedicamento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnNovoMedicamento.setText("Novo Medicamento");
        jbtnNovoMedicamento.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnNovoMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNovoMedicamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnNovoMedicamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(14, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnNovoMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 46, Short.MAX_VALUE)))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Quantidade:");

        jtxfQuantidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfQuantidadeKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Posologia:");

        jtxaPossologia.setColumns(20);
        jtxaPossologia.setRows(5);
        jScrollPane2.setViewportView(jtxaPossologia);

        buttonGroupUso.add(rbtnUsoExterno);
        rbtnUsoExterno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtnUsoExterno.setText("Uso Externo");
        rbtnUsoExterno.setFocusTraversalPolicyProvider(true);

        jbtnInserir.setText("Inserir");
        jbtnInserir.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnInserirActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Dose:");

        jtxfDose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        buttonGroupUso.add(rbtnUsoInterno);
        rbtnUsoInterno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtnUsoInterno.setText("Uso Interno");
        rbtnUsoInterno.setContentAreaFilled(false);

        jtxfMedicamento.setEditable(false);
        jtxfMedicamento.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxfMedicamento)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxfDose))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtnUsoExterno)
                            .addComponent(rbtnUsoInterno))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnInserir, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxfIdMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addGap(9, 9, 9)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jtxfMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxfIdMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxfDose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(rbtnUsoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtnUsoExterno))
                    .addComponent(jbtnInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbtnInserir.getAccessibleContext().setAccessibleName("jbtnInserir");

        jlblPaciente.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jlblPaciente.setForeground(new java.awt.Color(0, 51, 255));
        jlblPaciente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblPaciente.setText("Paciente");

        jdtcData.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jlblPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdtcData, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdtcData, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTableItensReceituario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ordem", "Medicamento", "Posologia", "Dose", "Quantidade", "Uso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableItensReceituario.setShowVerticalLines(false);
        jScrollPane3.setViewportView(jTableItensReceituario);
        if (jTableItensReceituario.getColumnModel().getColumnCount() > 0) {
            jTableItensReceituario.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTableItensReceituario.getColumnModel().getColumn(4).setPreferredWidth(1);
            jTableItensReceituario.getColumnModel().getColumn(5).setPreferredWidth(1);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jbtnSalvar.setText("Salvar");
        jbtnSalvar.setPreferredSize(new java.awt.Dimension(110, 33));
        jbtnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalvarActionPerformed(evt);
            }
        });

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.setPreferredSize(new java.awt.Dimension(110, 33));
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });

        jbtnSair.setText("Sair");
        jbtnSair.setMaximumSize(new java.awt.Dimension(80, 25));
        jbtnSair.setMinimumSize(new java.awt.Dimension(80, 25));
        jbtnSair.setPreferredSize(new java.awt.Dimension(110, 33));
        jbtnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSairActionPerformed(evt);
            }
        });

        jbtnRemover.setText("Remover Item");
        jbtnRemover.setPreferredSize(new java.awt.Dimension(110, 33));
        jbtnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxfPesquisaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxfPesquisaFocusGained
        habilitaCampos(false);
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
        limpaCampos();
        carregarTela();
    }//GEN-LAST:event_jbtnLimparPesquisaActionPerformed

    private void jTableMedicamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMedicamentoMouseClicked
        selecionarTabelaMedicamento();
    }//GEN-LAST:event_jTableMedicamentoMouseClicked

    private void jTableMedicamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMedicamentoKeyReleased
        selecionarTabelaMedicamento();
    }//GEN-LAST:event_jTableMedicamentoKeyReleased

    private void jbtnNovoMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNovoMedicamentoActionPerformed
        cadastrarMedicamento();
    }//GEN-LAST:event_jbtnNovoMedicamentoActionPerformed

    private void jbtnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSairActionPerformed
        fecharTela();
    }//GEN-LAST:event_jbtnSairActionPerformed

    private void jbtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalvarActionPerformed
        salvarReceituario();
    }//GEN-LAST:event_jbtnSalvarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        cancelarLancamento();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jtxfQuantidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfQuantidadeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxaPossologia.requestFocus();
        }
    }//GEN-LAST:event_jtxfQuantidadeKeyPressed

    private void jbtnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnInserirActionPerformed
        inserirItemReceituario();
    }//GEN-LAST:event_jbtnInserirActionPerformed

    private void jbtnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRemoverActionPerformed
        limparJTableItemReceituario(false);
    }//GEN-LAST:event_jbtnRemoverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupUso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTableItensReceituario;
    private javax.swing.JTable jTableMedicamento;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnInserir;
    private javax.swing.JButton jbtnLimparPesquisa;
    private javax.swing.JButton jbtnNovoMedicamento;
    private javax.swing.JButton jbtnPesquisa;
    private javax.swing.JButton jbtnRemover;
    private javax.swing.JButton jbtnSair;
    private javax.swing.JButton jbtnSalvar;
    private com.toedter.calendar.JDateChooser jdtcData;
    private javax.swing.JLabel jlblPaciente;
    private javax.swing.JTextArea jtxaPossologia;
    private javax.swing.JTextField jtxfDose;
    private javax.swing.JTextField jtxfIdMedicamento;
    private javax.swing.JTextField jtxfMedicamento;
    private javax.swing.JTextField jtxfPesquisa;
    private javax.swing.JTextField jtxfQuantidade;
    private javax.swing.JRadioButton rbtnUsoExterno;
    private javax.swing.JRadioButton rbtnUsoInterno;
    // End of variables declaration//GEN-END:variables
}
