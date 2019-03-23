package cemisV1_2.view;

import cemisV1_2.controller.MedicamentoController;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class TelaMedicamentoSimples extends javax.swing.JInternalFrame {
    
    private TelaReceituario tela;
    private MedicamentoController medController = new MedicamentoController();

    public TelaMedicamentoSimples(TelaReceituario tr) {
        initComponents();
        this.tela = tr;
        carregarTela();
    }
    
    private void carregarTela(){
        jtxfMedicamento.requestFocus();
    }
    
    private void salvarMedicamento(){
        int opcao = medController.salvarMedicamento(jtxfMedicamento.getText(), jtxfPrincipioAtivo.getText());
        confirmarSalvamento(opcao);
    }
    
    private void confirmarSalvamento(int opcao) {

        switch (opcao) {
            case 1:
                JOptionPane.showMessageDialog(this, "Medicamento cadastrado com sucesso!",
                        "Cadastro de Medicamentos", 1);
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "Medicamento não cadastrado. "
                        + "Entre em contato com a equipe de desenvolvimento.",
                        "Cadastro de Medicamentos", 0);
                break;
            case 5:
                JOptionPane.showMessageDialog(this, "Os campos Princípio Ativo e "
                        + "Medicamento não podem ser vazios",
                        "Cadastro de Medicamentos", 0);
                break;
        }
        if (opcao == 1) {
            voltarTelaReceituario();
        }
    }
    
    
    private void cancelarESair(){
        jtxfMedicamento.setText("");
        jtxfPrincipioAtivo.setText("");
        
        voltarTelaReceituario();
    }
    
    private void voltarTelaReceituario(){
        
        this.dispose();
        
        this.tela.setVisible(true);
        this.tela.carregarTela();
        this.tela.habilitarBotoes(true);
                
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jtxfMedicamento = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxfPrincipioAtivo = new javax.swing.JTextField();
        jbtnCancelar = new javax.swing.JButton();
        jbtnSalvar = new javax.swing.JButton();

        setTitle("Cadastro de Medicamentos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/cemisV1_2/view/Icone.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cadastro de Medicamentos");

        jtxfMedicamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfMedicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfMedicamentoKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Principio Ativo");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Medicamento");

        jtxfPrincipioAtivo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfPrincipioAtivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxfPrincipioAtivoKeyPressed(evt);
            }
        });

        jbtnCancelar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnCancelar.setText("Cancelar e Sair");
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });

        jbtnSalvar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnSalvar.setText("Salvar");
        jbtnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jtxfMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jbtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jbtnCancelar)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxfPrincipioAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxfMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxfPrincipioAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxfMedicamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfMedicamentoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfPrincipioAtivo.setText(jtxfMedicamento.getText());
            jtxfPrincipioAtivo.requestFocus();
        }
    }//GEN-LAST:event_jtxfMedicamentoKeyPressed

    private void jtxfPrincipioAtivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxfPrincipioAtivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jtxfMedicamento.requestFocus();
        }
    }//GEN-LAST:event_jtxfPrincipioAtivoKeyPressed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        cancelarESair();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jbtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalvarActionPerformed
        salvarMedicamento();
    }//GEN-LAST:event_jbtnSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnSalvar;
    private javax.swing.JTextField jtxfMedicamento;
    private javax.swing.JTextField jtxfPrincipioAtivo;
    // End of variables declaration//GEN-END:variables
}
