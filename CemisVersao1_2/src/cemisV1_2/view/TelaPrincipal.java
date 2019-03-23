package cemisV1_2.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

/**
 *
 * @author ptol1
 */
public class TelaPrincipal extends javax.swing.JFrame {
    
    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Icone.jpg")));
       
        this.setSize(800, 600);
        jdpPrincipal.setSize(this.getWidth(), (this.getHeight() -  70));
        this.add(jdpPrincipal);
        this.setLocationRelativeTo(null);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ImageIcon icon = new ImageIcon(getClass().getResource("/cemisV1_2/util/imagens/LogotipoTopo.png"));
        Image image = icon.getImage();
        jdpPrincipal = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g){
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }

        };
        jMenu = new javax.swing.JMenuBar();
        mnAtendimento = new javax.swing.JMenu();
        jmnItemAtendimento = new javax.swing.JMenuItem();
        mnCadastro = new javax.swing.JMenu();
        jmnItemPaciente = new javax.swing.JMenuItem();
        jmnItemMedicamento = new javax.swing.JMenuItem();
        jmnItemExame = new javax.swing.JMenuItem();
        mnRelatorios = new javax.swing.JMenu();
        jmnItemReceituarios = new javax.swing.JMenuItem();
        jmnItemAtestado = new javax.swing.JMenuItem();
        jmnItemPedExame = new javax.swing.JMenuItem();
        mnSair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CEMIS  -  Sistema de Controle de Atendimento");
        setExtendedState(6);

        javax.swing.GroupLayout jdpPrincipalLayout = new javax.swing.GroupLayout(jdpPrincipal);
        jdpPrincipal.setLayout(jdpPrincipalLayout);
        jdpPrincipalLayout.setHorizontalGroup(
            jdpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        jdpPrincipalLayout.setVerticalGroup(
            jdpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 241, Short.MAX_VALUE)
        );

        mnAtendimento.setText("Atendimento");
        mnAtendimento.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmnItemAtendimento.setText("Atendimento");
        jmnItemAtendimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnItemAtendimentoActionPerformed(evt);
            }
        });
        mnAtendimento.add(jmnItemAtendimento);

        jMenu.add(mnAtendimento);

        mnCadastro.setText("Cadastro");
        mnCadastro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmnItemPaciente.setText("Paciente");
        jmnItemPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnItemPacienteActionPerformed(evt);
            }
        });
        mnCadastro.add(jmnItemPaciente);

        jmnItemMedicamento.setText("Medicamento");
        jmnItemMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnItemMedicamentoActionPerformed(evt);
            }
        });
        mnCadastro.add(jmnItemMedicamento);

        jmnItemExame.setText("Exame");
        jmnItemExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnItemExameActionPerformed(evt);
            }
        });
        mnCadastro.add(jmnItemExame);

        jMenu.add(mnCadastro);

        mnRelatorios.setText("Relatórios");
        mnRelatorios.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmnItemReceituarios.setText("Receituarios");
        jmnItemReceituarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnItemReceituariosActionPerformed(evt);
            }
        });
        mnRelatorios.add(jmnItemReceituarios);

        jmnItemAtestado.setText("Atestado");
        jmnItemAtestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnItemAtestadoActionPerformed(evt);
            }
        });
        mnRelatorios.add(jmnItemAtestado);

        jmnItemPedExame.setText("Pedido de Exame");
        jmnItemPedExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnItemPedExameActionPerformed(evt);
            }
        });
        mnRelatorios.add(jmnItemPedExame);

        jMenu.add(mnRelatorios);

        mnSair.setText("Sair");
        mnSair.setFocusTraversalPolicyProvider(true);
        mnSair.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mnSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnSairMouseClicked(evt);
            }
        });
        jMenu.add(mnSair);

        setJMenuBar(jMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdpPrincipal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jdpPrincipal)
                .addGap(0, 0, 0))
        );

        setLocation(new java.awt.Point(0, 0));
    }// </editor-fold>//GEN-END:initComponents

    private void jmnItemPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnItemPacienteActionPerformed
        if (jdpPrincipal.getComponentCount() == 0) {  // Não permite carregar outra tela enquanto a tela atual não for fechada
            TelaPaciente tela = new TelaPaciente();
            jdpPrincipal.add(tela);
            centralizaTela(tela);
            tela.setVisible(true);
            //tela.toFront(); // Mantem a tela na frente das outras
        }
    }//GEN-LAST:event_jmnItemPacienteActionPerformed

    private void mnSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnSairMouseClicked
        System.exit(0);
    }//GEN-LAST:event_mnSairMouseClicked

    private void jmnItemMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnItemMedicamentoActionPerformed
        if (jdpPrincipal.getComponentCount() == 0) {  // Não permite carregar outra tela enquanto a tela atual não for fechada
            TelaMedicamento tela = new TelaMedicamento();
            jdpPrincipal.add(tela);
            centralizaTela(tela);
            tela.setVisible(true);
            //super.setFocusable(false);
            //tela.toFront(); // Mantem a tela na frente das outras
        }
    }//GEN-LAST:event_jmnItemMedicamentoActionPerformed

    private void jmnItemExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnItemExameActionPerformed
        if (jdpPrincipal.getComponentCount() == 0) {  // Não permite carregar outra tela enquanto a tela atual não for fechada
            TelaExame tela = new TelaExame();
            jdpPrincipal.add(tela);
            centralizaTela(tela);
            tela.setVisible(true);
        }
    }//GEN-LAST:event_jmnItemExameActionPerformed

    private void jmnItemAtendimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnItemAtendimentoActionPerformed
        if (jdpPrincipal.getComponentCount() == 0) {  // Não permite carregar outra tela enquanto a tela atual não for fechada
            TelaAtendimento tela = new TelaAtendimento();
            jdpPrincipal.add(tela);
            centralizaTela(tela);
            tela.setVisible(true);
        }
    }//GEN-LAST:event_jmnItemAtendimentoActionPerformed

    private void jmnItemReceituariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnItemReceituariosActionPerformed
        if (jdpPrincipal.getComponentCount() == 0) {  // Não permite carregar outra tela enquanto a tela atual não for fechada
//            TelaReceituarioRelatorio tela = new TelaReceituarioRelatorio();
            TelaRelatorios tela = new TelaRelatorios(1);
            jdpPrincipal.add(tela);
            centralizaTela(tela);
            tela.setVisible(true);
        }
    }//GEN-LAST:event_jmnItemReceituariosActionPerformed

    private void jmnItemAtestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnItemAtestadoActionPerformed
        if (jdpPrincipal.getComponentCount() == 0) {  // Não permite carregar outra tela enquanto a tela atual não for fechada
            TelaRelatorios tela = new TelaRelatorios(2);
            jdpPrincipal.add(tela);
            centralizaTela(tela);
            tela.setVisible(true);
        }
    }//GEN-LAST:event_jmnItemAtestadoActionPerformed

    private void jmnItemPedExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnItemPedExameActionPerformed
        if (jdpPrincipal.getComponentCount() == 0) {  // Não permite carregar outra tela enquanto a tela atual não for fechada
            TelaRelatorios tela = new TelaRelatorios(3);
            jdpPrincipal.add(tela);
            centralizaTela(tela);
            tela.setVisible(true);
        }

    }//GEN-LAST:event_jmnItemPedExameActionPerformed

    public static void centralizaTela(JInternalFrame tela) {
        tela.setLocation((jdpPrincipal.getWidth() / 2) - (tela.getWidth() / 2),
                (jdpPrincipal.getHeight() / 2) - (tela.getHeight() / 2)
        );
    }

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JMenuBar jMenu;
    public static javax.swing.JDesktopPane jdpPrincipal;
    private javax.swing.JMenuItem jmnItemAtendimento;
    private javax.swing.JMenuItem jmnItemAtestado;
    private javax.swing.JMenuItem jmnItemExame;
    private javax.swing.JMenuItem jmnItemMedicamento;
    private javax.swing.JMenuItem jmnItemPaciente;
    private javax.swing.JMenuItem jmnItemPedExame;
    private javax.swing.JMenuItem jmnItemReceituarios;
    private javax.swing.JMenu mnAtendimento;
    private javax.swing.JMenu mnCadastro;
    private javax.swing.JMenu mnRelatorios;
    private javax.swing.JMenu mnSair;
    // End of variables declaration//GEN-END:variables

}
