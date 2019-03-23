package cemisV1_2.view;

import cemisV1_2.controller.ExameController;
import cemisV1_2.controller.PedidoExameController;
import cemisV1_2.model.Exame;
import cemisV1_2.model.Paciente;
import cemisV1_2.model.PedidoExameItens;
import cemisV1_2.model.Pedidoexame;
import cemisV1_2.model.ExameTipo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPedidoExame extends javax.swing.JInternalFrame {

    private TelaAtendimento tela;
    private Paciente paciente = new Paciente();
    private Pedidoexame pedidoExame = new Pedidoexame();
    private PedidoExameController pedidoExameController = new PedidoExameController();
    private ExameController exameController = new ExameController();
    private List<Exame> listExame = new ArrayList<>();
    private List<Exame> listExameSelecionado = new ArrayList<>();
    private List<ExameTipo> listTpExame = new ArrayList<>();
    private DefaultListModel dlmListaExame = new DefaultListModel();
    private DefaultListModel dlmListaExameSelecionado = new DefaultListModel();
    private DefaultListModel dlmListaTipoExame = new DefaultListModel();
    
    public TelaPedidoExame(TelaAtendimento ta, Paciente pac) {
        initComponents();
        this.tela = ta;
        this.paciente = pac;
        jdtcData.setDate(new Date());
        carregarTela();
    }

    public void carregarTela() {
        pedidoExame.setPaciente(paciente.getIdPaciente());
        jlblPaciente.setText(paciente.getNomePaciente());
        habilitarBotoes(false);
        try {
            listTpExame = exameController.listarTipoExame();
            carregarListTipoExame(listTpExame);
            listExame = exameController.listarExames();
        } catch (Exception ex) {
            Logger.getLogger(TelaPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void carregarListExame(List<Exame> le){
        if(!dlmListaExame.isEmpty()){
            dlmListaExame.removeAllElements();
        }
        for(int i = 0; i < le.size(); i++){
            dlmListaExame.addElement(le.get(i).getExame());
        }
        jListExame.setModel(dlmListaExame);
    }
    
    private void selecionarExame(){
        if(jListExame.getSelectedIndex() > -1){
            Exame exameAtual = new Exame();
            if(!dlmListaExameSelecionado.contains(jListExame.getSelectedValue())){
                for(int i = 0; i < listExame.size(); i++){
                    if(listExame.get(i).getExame().equals(jListExame.getSelectedValue())){
                        exameAtual = listExame.get(i);
                        listExameSelecionado.add(exameAtual);
                    }
                }
                dlmListaExameSelecionado.addElement(exameAtual.getExame());
                jListExameSelecionado.setModel(dlmListaExameSelecionado);
                if(!jbtnSalvar.isEnabled() || !jbtnCancelar.isEnabled()){
                    habilitarBotoes(true);
                }
            }
        }
    }
        
    private void carregarListTipoExame(List<ExameTipo> lte){
        if(!dlmListaTipoExame.isEmpty()){
            dlmListaTipoExame.removeAllElements();
        }
        for(int i = 0; i < lte.size(); i++){
            dlmListaTipoExame.addElement(lte.get(i).getTipoExame());
        }
        jListTipoExame.setModel(dlmListaTipoExame);
    }
    
    private void selecionarTipoExame(){
        if(jListTipoExame.getSelectedIndex()> -1){
            int indice = jListTipoExame.getSelectedIndex();
            int idtpexame = listTpExame.get(indice).getIdTipoExame();
            List<Exame> le = new ArrayList<>();
            for(int i = 0; i < listExame.size(); i++){
                if(listExame.get(i).getTipoexame() == idtpexame){
                    le.add(listExame.get(i));
                }
            }
            carregarListExame(le);
        }
    }

    private void removerDaLista(){
        if(jListExameSelecionado.getSelectedIndex() > -1){
            listExameSelecionado.remove(jListExameSelecionado.getSelectedIndex());
            dlmListaExameSelecionado.removeElementAt(jListExameSelecionado.getSelectedIndex());

        }
        if(dlmListaExameSelecionado.isEmpty()){
            habilitarBotoes(false);
        }
    }
    
    private void limparListasLancamento(){
        dlmListaExame.removeAllElements();
        dlmListaExameSelecionado.removeAllElements();
        listExameSelecionado.clear();
    }

    public void habilitarBotoes(boolean b) {
        jbtnSalvar.setEnabled(b);
        jbtnCancelar.setEnabled(b);
        jbtnRemoverDaLista.setEnabled(b);
    }
    
    private void salvarPedExame(){
        this.pedidoExame.setDataPedidoExame(jdtcData.getDate());
        int opcao = 0;
        int idPedExame = 0;
        if (pedidoExameController.salvarPedExame(this.pedidoExame)){
            List<PedidoExameItens> lpei = new ArrayList<>();
            int numLinhas = listExameSelecionado.size();
            idPedExame = pedidoExameController.ultimoPedExame().getIdPedidoExame();
            for(int i = 0; i < numLinhas; i++){
                PedidoExameItens peiAtual = new PedidoExameItens();
                peiAtual.setPedidoExame(idPedExame);
                peiAtual.setExame(listExameSelecionado.get(i).getIdExame());
                lpei.add(peiAtual);
            }
            if(pedidoExameController.salvarPedExameItens(lpei)){
                opcao = 1;
            }
        }else{
            opcao = 2;
        }
        confirmarSalvamento(opcao, idPedExame);
        habilitarBotoes(false);
    }
    
    private void confirmarSalvamento(int opcao, int idPedExame) {
        switch (opcao) {
            case 1:
                abrirTelaImpressao(idPedExame);
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "Pedido de Exame não cadastrado. Entre em contato com a"
                                + " equipe de desenvolvimento.", "Cadastro de Pacientes", 0);
                break;
            case 4:
                JOptionPane.showMessageDialog(this, "Receituario não foi alterado. Entre em contato com a "
                                + "equipe de desenvolvimento.", "Cadastro de Pacientes", 0);
                break;
        }
        if(opcao == 1){
            limparListasLancamento();
        }
    }

    private void abrirTelaImpressao(int idPedExame){
        if(idPedExame < 1){
            JOptionPane.showMessageDialog(this, "O campo idPedExame não pode ser vazio! ");
        }else{
            try {
                JRResultSetDataSource resultado = new JRResultSetDataSource(pedidoExameController.relatorioPedExame(idPedExame));
                JasperReport jRep = (JasperReport) JRLoader.loadObject(getClass().getResource("/cemisV1_2/reporter/PedidoExame.jasper"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(jRep, new HashMap(), resultado);
                JasperViewer tela = new JasperViewer(jasperPrint, false);
                tela.setVisible(true);
                tela.toFront();
            
            } catch (JRException e) {
                JOptionPane.showMessageDialog(this, "Erro no Relatório. \nErro: " + e);
            }
        }
    }
    
    private void abrirCadExame(){
        this.setVisible(false);
 
        TelaExame tela = new TelaExame(this);
        TelaPrincipal.jdpPrincipal.add(tela);
        TelaPrincipal.centralizaTela(tela);
        tela.setVisible(true);
    }
    
    private void fecharTela(){
        this.dispose();
        this.tela.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel4 = new javax.swing.JPanel();
        jbtnSair = new javax.swing.JButton();
        jbtnSalvar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jlblPaciente = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jdtcData = new com.toedter.calendar.JDateChooser();
        jPanel7 = new javax.swing.JPanel();
        jbtnAdicionarALista = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListExameSelecionado = new javax.swing.JList<>();
        jbtnRemoverDaLista = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListTipoExame = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListExame = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jbtnCadExame = new javax.swing.JButton();

        setTitle("Pedido de Exame");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/cemisV1_2/view/Icone.png"))); // NOI18N

        jPanel4.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnSair, gridBagConstraints);

        jbtnSalvar.setText("Salvar");
        jbtnSalvar.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalvarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnSalvar, gridBagConstraints);

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.setPreferredSize(new java.awt.Dimension(100, 30));
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jbtnCancelar, gridBagConstraints);

        jlblPaciente.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jlblPaciente.setForeground(new java.awt.Color(0, 51, 255));
        jlblPaciente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblPaciente.setText("Paciente");

        jdtcData.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jlblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdtcData, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addGap(22, 22, 22))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdtcData, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jbtnAdicionarALista.setText(">>");
        jbtnAdicionarALista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAdicionarAListaActionPerformed(evt);
            }
        });

        jListExameSelecionado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jListExameSelecionado.setToolTipText("");
        jScrollPane3.setViewportView(jListExameSelecionado);

        jbtnRemoverDaLista.setText("<<");
        jbtnRemoverDaLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRemoverDaListaActionPerformed(evt);
            }
        });

        jListTipoExame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListTipoExameMouseClicked(evt);
            }
        });
        jListTipoExame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jListTipoExameKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jListTipoExame);

        jScrollPane4.setViewportView(jListExame);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tipos de Exame");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Exames");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Exames Selecionados");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnAdicionarALista, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(jbtnRemoverDaLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap())
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jbtnAdicionarALista)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtnRemoverDaLista))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pedido de Exame");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbtnCadExame.setText("Abrir Cadastro de Exame");
        jbtnCadExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCadExameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnCadExame, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnCadExame, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSairActionPerformed
        fecharTela();
    }//GEN-LAST:event_jbtnSairActionPerformed

    private void jbtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalvarActionPerformed
        salvarPedExame();
    }//GEN-LAST:event_jbtnSalvarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        limparListasLancamento();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jbtnAdicionarAListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAdicionarAListaActionPerformed
        selecionarExame();
    }//GEN-LAST:event_jbtnAdicionarAListaActionPerformed

    private void jbtnRemoverDaListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRemoverDaListaActionPerformed
        removerDaLista();
    }//GEN-LAST:event_jbtnRemoverDaListaActionPerformed

    private void jListTipoExameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jListTipoExameKeyReleased
        selecionarTipoExame();
    }//GEN-LAST:event_jListTipoExameKeyReleased

    private void jListTipoExameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListTipoExameMouseClicked
        selecionarTipoExame();
    }//GEN-LAST:event_jListTipoExameMouseClicked

    private void jbtnCadExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCadExameActionPerformed
        abrirCadExame();
    }//GEN-LAST:event_jbtnCadExameActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jListExame;
    private javax.swing.JList<String> jListExameSelecionado;
    private javax.swing.JList<String> jListTipoExame;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton jbtnAdicionarALista;
    private javax.swing.JButton jbtnCadExame;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnRemoverDaLista;
    private javax.swing.JButton jbtnSair;
    private javax.swing.JButton jbtnSalvar;
    private com.toedter.calendar.JDateChooser jdtcData;
    private javax.swing.JLabel jlblPaciente;
    // End of variables declaration//GEN-END:variables
}
