package cemisV1_2.view;

import cemisV1_2.controller.AtestadoController;
import cemisV1_2.controller.PacienteController;
import cemisV1_2.controller.PedidoExameController;
import cemisV1_2.controller.ReceituarioController;
import cemisV1_2.model.Atestado;
import cemisV1_2.model.Pedidoexame;
import cemisV1_2.model.Receituario;
import cemisV1_2.model.TmpTabelaRelatorios;
import cemisV1_2.util.ordena_listas.OrdemListaRecPorData;
import cemisV1_2.util.ordena_listas.OrdemListaRecPorNome;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class TelaRelatorios extends javax.swing.JInternalFrame {

    private TelaReceituario telaReceituario = null;
    private ReceituarioController receituarioController = new ReceituarioController();
    private PedidoExameController pedidoExameController = new PedidoExameController();
    private PacienteController pacienteController = new PacienteController();
    private DefaultTableModel dtmAtendimento = new DefaultTableModel();
    private List<TmpTabelaRelatorios> tabelaAtendimentos = new ArrayList<>();
    private SimpleDateFormat formataDataExtenso = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy");
    private int opcao;

    public TelaRelatorios() {
        initComponents();
    }
        
    public TelaRelatorios(TelaReceituario tr,  Receituario r) {
        initComponents();
        this.telaReceituario = tr;
        if (r != null) {
            jPanelPesquisa.setVisible(false);
            Integer id = r.getIdReceituario();
            jtxfPesquisaOculta.setText(id.toString());
            List<Receituario> listReceituario = new ArrayList<>();
            listReceituario.add(r);
            jPanelTabela.setVisible(false);
            jtxfPaciente.setText(pacienteController.converteIdPacienteEmNomePaciente(r.getPaciente()));
            this.setSize(710, 220);
        }
        carregarTelaReceituario(receituarioController.listarReceituario());
    }

    public TelaRelatorios(int opcao) {
        initComponents();
        this.opcao = opcao;
        switch(opcao){
            case 1 : 
                carregarTelaReceituario(receituarioController.listarReceituario());
                break;
            case 2 :
                carregarTelaAtestado(AtestadoController.listarAtestado());
                break;
            case 3 :
                carregarTelaPedExame(pedidoExameController.listarPedExame());
                break;
        }
    }

    private void exibirItensDaTela(String titulo, boolean btCo, boolean btEs, 
            boolean btAz, boolean btAt, boolean btPe){
        jlblTitulo.setText(titulo);
        jbtnComum.setVisible(btCo);
        jbtnEspecial.setVisible(btEs);
        jbtnAzul.setVisible(btAz);
        jbtnAtestado.setVisible(btAt);
        jbtnPedExame.setVisible(btPe);
    }
        
    
//    Carrega a tela como os dados de Receituario
    private void carregarTelaReceituario(List<Receituario> lr){
        exibirItensDaTela("Receituário", true, true, true, false, false);
        dtmAtendimento = (DefaultTableModel) jtblAtendimento.getModel();
        for(Receituario rec : lr){
            String pac = pacienteController.converteIdPacienteEmNomePaciente(rec.getPaciente());
            tabelaAtendimentos.add(new TmpTabelaRelatorios(rec.getIdReceituario(), pac, rec.getDataReceituario()));
        }
        carregarTabela(tabelaAtendimentos);
        jtxfPesquisaOculta.setVisible(false);
        
    }
    
//    Carrega a tela como os dados de Atestado
    private void carregarTelaAtestado(List<Atestado> lat){
        exibirItensDaTela("Atestado", false, false, false, true, false);
        dtmAtendimento = (DefaultTableModel) jtblAtendimento.getModel();
        for(Atestado at : lat){
            String pac = pacienteController.converteIdPacienteEmNomePaciente(at.getPaciente());
            tabelaAtendimentos.add(new TmpTabelaRelatorios(at.getIdAtestado(), pac, at.getDataAtestado()));
        }
        carregarTabela(tabelaAtendimentos);
        jtxfPesquisaOculta.setVisible(false);
        
    }
        
//    Carrega a tela como os dados de Pedido de Exame
    private void carregarTelaPedExame(List<Pedidoexame> lpe){
        exibirItensDaTela("Pedido de Exame", false, false, false, false, true);
        dtmAtendimento = (DefaultTableModel) jtblAtendimento.getModel();
        for(Pedidoexame ped : lpe){
            String pac = pacienteController.converteIdPacienteEmNomePaciente(ped.getPaciente());
            tabelaAtendimentos.add(new TmpTabelaRelatorios(ped.getIdPedidoExame(), pac, ped.getDataPedidoExame()));
        }
        carregarTabela(tabelaAtendimentos);
        jtxfPesquisaOculta.setVisible(false);
    }
        
//    Método que carrega dados na tabela 
    
    private void carregarTabela(List<TmpTabelaRelatorios> tabela) {
        if (TelaPrincipal.jdpPrincipal.getComponentCount() < 2) {

            if(gbtnOrdem.isSelected(jrdbPaciente.getModel())){
                Comparator recNome = new OrdemListaRecPorNome();
                Collections.sort(tabela, recNome);
            }else{
                Comparator recData = new OrdemListaRecPorData();
                Collections.sort(tabela, recData);
            }
            
            while(dtmAtendimento.getRowCount() > 0){
                dtmAtendimento.removeRow(0);
            }
            
            for (TmpTabelaRelatorios tb : tabela) {
                dtmAtendimento.addRow(new Object[]{
                    tb.getNomePaciente(),
                    formataDataExtenso.format(tb.getData())
                });
            }
        }
    }
    
//    Método de pesquisa na tabela
    
    private void pesquisarPaciente(){
        String str = jtxfPesquisa.getText();
        List<TmpTabelaRelatorios> tabela = new ArrayList<>();
        for(int i = 0; i < tabelaAtendimentos.size(); i++){
            if(tabelaAtendimentos.get(i).getNomePaciente().toLowerCase().contains(str)){
                tabela.add(tabelaAtendimentos.get(i));
            }
            
        }
        if(tabela.isEmpty()){
            JOptionPane.showMessageDialog(this, "Nenhum Paciente com este nome",
                    "Seleção de Receituário", JOptionPane.ERROR_MESSAGE);
        }else{
            tabelaAtendimentos = new ArrayList<>(tabela);
            carregarTabela(tabelaAtendimentos);
        }
    }
    
//    Método que limpa a pesquisa e mostra os valores originais da tabela
    
    private void limparPesquisa(){
        jtxfPesquisa.setText("");
        jtxfPaciente.setText("");
        jtxfPesquisaOculta.setText("");
        while(tabelaAtendimentos.size() > 0){
            tabelaAtendimentos.remove(0);
        }
        switch(opcao){
            case 1 : 
                carregarTelaReceituario(receituarioController.listarReceituario());
                break;
            case 2 :
                carregarTelaAtestado(AtestadoController.listarAtestado());
                break;
            case 3 :
                carregarTelaPedExame(pedidoExameController.listarPedExame());
                break;
        }
    }

//    Método executado quando o usuário seleciona um item na tabela
    
    private void selecionarNaTabela() {
        if (jtblAtendimento.getSelectedRow() != -1) {
            Integer indice = jtblAtendimento.getSelectedRow();

            TmpTabelaRelatorios tbr = new TmpTabelaRelatorios(
                    tabelaAtendimentos.get(indice).getId(),
                    tabelaAtendimentos.get(indice).getNomePaciente(),
                    tabelaAtendimentos.get(indice).getData());
            
            jtxfPesquisaOculta.setText(tbr.getId().toString());
            jtxfPaciente.setText(tbr.getNomePaciente());
        }
    }
    
    private void fecharTela(){
        this.dispose();
        if(telaReceituario != null){
            this.telaReceituario.setVisible(true);
        }
    }
    
    private void imprimirRecComum(){
        if (jtxfPesquisaOculta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Pesquisa não pode ser vazio! ");
            jtxfPesquisaOculta.requestFocus();
        } else {
            try {
                int pesquisa = (Integer.parseInt(jtxfPesquisaOculta.getText().trim()));
                JRResultSetDataSource resultado = new JRResultSetDataSource(receituarioController.relatorioReceituario(pesquisa));
                JasperReport jRep = (JasperReport) JRLoader.loadObject(getClass().getResource("/cemisV1_2/reporter/ReceituarioComum.jasper"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(jRep, new HashMap(), resultado);
                JasperViewer tela = new JasperViewer(jasperPrint, false);
                tela.setVisible(true);
                tela.toFront();
            } catch (JRException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro no Relatório. \nErro: " + ex);
            }
        }        
    }
    
    private void imprimirRecEspecial(){
        if (jtxfPesquisaOculta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Pesquisa não pode ser vazio! ");
            jtxfPesquisaOculta.requestFocus();
        } else {
            try {
                int pesquisa = (Integer.parseInt(jtxfPesquisaOculta.getText().trim()));
                JRResultSetDataSource resultado = new JRResultSetDataSource(receituarioController.relatorioReceituario(pesquisa));
                JasperReport jRep = (JasperReport) JRLoader.loadObject(getClass().getResource("/cemisV1_2/reporter/ReceituarioEspecial.jasper"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(jRep, new HashMap(), resultado);
                JasperViewer tela = new JasperViewer(jasperPrint, false);
                tela.setVisible(true);
                tela.toFront();
            } catch (JRException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro no Relatório. \nErro: " + ex);
            }
        }        
    }
    
    private void imprimirRecAzul(){
        if (jtxfPesquisaOculta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Pesquisa não pode ser vazio! ");
            jtxfPesquisaOculta.requestFocus();
        } else {
            try {
                int pesquisa = (Integer.parseInt(jtxfPesquisaOculta.getText().trim()));
                JRResultSetDataSource resultado = new JRResultSetDataSource(receituarioController.relatorioReceituario(pesquisa));
                JasperReport jRep = (JasperReport) JRLoader.loadObject(getClass().getResource("/cemisV1_2/reporter/ReceituarioAzul.jasper"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(jRep, new HashMap(), resultado);
                JasperViewer tela = new JasperViewer(jasperPrint, false);
                tela.setVisible(true);
                tela.toFront();
            } catch (JRException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro no Relatório. \nErro: " + ex);
            }
        }        
    }
    private void imprimirAtestado(){
        if (jtxfPesquisaOculta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Pesquisa não pode ser vazio! ");
            jtxfPesquisaOculta.requestFocus();
        } else {
            
            try {
                int pesquisa = (Integer.parseInt(jtxfPesquisaOculta.getText().trim()));
                JRResultSetDataSource resultado = new JRResultSetDataSource(AtestadoController.relatorioAtestado(pesquisa));
                JasperReport jRep = (JasperReport) JRLoader.loadObject(getClass().getResource("/cemisV1_2/reporter/Atestado.jasper"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(jRep, new HashMap(), resultado);
                JasperViewer tela = new JasperViewer(jasperPrint, false);
                tela.setVisible(true);
                tela.toFront();
            } catch (JRException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro no Relatório. \nErro: " + ex);
            }
        }
    }
   
    private void imprimirPedExame(){
        if (jtxfPesquisaOculta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Pesquisa não pode ser vazio! ");
            jtxfPesquisaOculta.requestFocus();
        } else {
            try {
                int pesquisa = (Integer.parseInt(jtxfPesquisaOculta.getText().trim()));
                JRResultSetDataSource resultado = new JRResultSetDataSource(pedidoExameController.relatorioPedExame(pesquisa));
                JasperReport jRep = (JasperReport) JRLoader.loadObject(getClass().getResource("/cemisV1_2/reporter/PedidoExame.jasper"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(jRep, new HashMap(), resultado);
                JasperViewer tela = new JasperViewer(jasperPrint, false);
                tela.setVisible(true);
                tela.toFront();
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(this, "Erro no Relatório. \nErro: " + ex);
            }
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gbtnOrdem = new javax.swing.ButtonGroup();
        jPanelTabela = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblAtendimento = new javax.swing.JTable();
        jPanelPesquisa = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtxfPesquisa = new javax.swing.JTextField();
        jbtnPesquisa = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jrdbPaciente = new javax.swing.JRadioButton();
        jrdbData = new javax.swing.JRadioButton();
        jbtnLimarPesquisa = new javax.swing.JButton();
        jtxfPesquisaOculta = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jbtnComum = new javax.swing.JButton();
        jbtnEspecial = new javax.swing.JButton();
        jbtnAzul = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jtxfPaciente = new javax.swing.JTextField();
        jlblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jbtnPedExame = new javax.swing.JButton();
        jbtnAtestado = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jbtnFechar = new javax.swing.JButton();

        setClosable(true);
        setResizable(true);
        setTitle("Relatórios");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/cemisV1_2/view/Icone.png"))); // NOI18N

        jtblAtendimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtblAtendimento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Paciente", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblAtendimento.setShowVerticalLines(false);
        jtblAtendimento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblAtendimentoMouseClicked(evt);
            }
        });
        jtblAtendimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtblAtendimentoKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtblAtendimento);

        javax.swing.GroupLayout jPanelTabelaLayout = new javax.swing.GroupLayout(jPanelTabela);
        jPanelTabela.setLayout(jPanelTabelaLayout);
        jPanelTabelaLayout.setHorizontalGroup(
            jPanelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelTabelaLayout.setVerticalGroup(
            jPanelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Pesquisar Paciente:");

        jtxfPesquisa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jbtnPesquisa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnPesquisa.setText("Pesquisar");
        jbtnPesquisa.setPreferredSize(new java.awt.Dimension(182, 25));
        jbtnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPesquisaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Ordenar a lista por:");

        gbtnOrdem.add(jrdbPaciente);
        jrdbPaciente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrdbPaciente.setSelected(true);
        jrdbPaciente.setText("Nome do Paciente");
        jrdbPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrdbPacienteActionPerformed(evt);
            }
        });

        gbtnOrdem.add(jrdbData);
        jrdbData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrdbData.setText("Data do Atendimento");
        jrdbData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrdbDataActionPerformed(evt);
            }
        });

        jbtnLimarPesquisa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnLimarPesquisa.setText("Limpar Pesquisa");
        jbtnLimarPesquisa.setPreferredSize(new java.awt.Dimension(182, 25));
        jbtnLimarPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLimarPesquisaActionPerformed(evt);
            }
        });

        jtxfPesquisaOculta.setEditable(false);
        jtxfPesquisaOculta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxfPesquisaOculta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxfPesquisaOcultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelPesquisaLayout = new javax.swing.GroupLayout(jPanelPesquisa);
        jPanelPesquisa.setLayout(jPanelPesquisaLayout);
        jPanelPesquisaLayout.setHorizontalGroup(
            jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPesquisaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelPesquisaLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelPesquisaLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jrdbPaciente)
                            .addComponent(jrdbData))
                        .addGap(56, 56, 56)
                        .addComponent(jtxfPesquisaOculta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnLimarPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelPesquisaLayout.setVerticalGroup(
            jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPesquisaLayout.createSequentialGroup()
                        .addGroup(jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jrdbPaciente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jrdbData))
                    .addGroup(jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnLimarPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxfPesquisaOculta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        jbtnComum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnComum.setText("Receituario Comum");
        jbtnComum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnComumActionPerformed(evt);
            }
        });

        jbtnEspecial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnEspecial.setText("Receituario Especial");
        jbtnEspecial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEspecialActionPerformed(evt);
            }
        });

        jbtnAzul.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnAzul.setText("Receituario Azul");
        jbtnAzul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAzulActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addComponent(jbtnComum, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnAzul, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnComum, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnAzul, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jtxfPaciente.setEditable(false);
        jtxfPaciente.setBackground(new java.awt.Color(255, 255, 0));
        jtxfPaciente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtxfPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtxfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlblTitulo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jlblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTitulo.setText("Relatórios");

        jbtnPedExame.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnPedExame.setText("Imprimir Exame");
        jbtnPedExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPedExameActionPerformed(evt);
            }
        });

        jbtnAtestado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnAtestado.setText("Imprimir Atestado");
        jbtnAtestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAtestadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnAtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnPedExame, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnPedExame, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnAtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbtnFechar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtnFechar.setText("Fechar");
        jbtnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jbtnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jlblTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jlblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtblAtendimentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblAtendimentoMouseClicked
        selecionarNaTabela();
    }//GEN-LAST:event_jtblAtendimentoMouseClicked

    private void jtblAtendimentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtblAtendimentoKeyReleased
        selecionarNaTabela();
    }//GEN-LAST:event_jtblAtendimentoKeyReleased

    private void jtxfPesquisaOcultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxfPesquisaOcultaActionPerformed
        JOptionPane.showMessageDialog(this, "Clique no botão com o tipo de Receituario desejado! ");
    }//GEN-LAST:event_jtxfPesquisaOcultaActionPerformed

    private void jbtnComumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnComumActionPerformed
        imprimirRecComum();
    }//GEN-LAST:event_jbtnComumActionPerformed

    private void jbtnEspecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEspecialActionPerformed
        imprimirRecEspecial();
    }//GEN-LAST:event_jbtnEspecialActionPerformed

    private void jbtnAzulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAzulActionPerformed
        imprimirRecAzul();
    }//GEN-LAST:event_jbtnAzulActionPerformed

    private void jbtnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFecharActionPerformed
        fecharTela();        
    }//GEN-LAST:event_jbtnFecharActionPerformed

    private void jbtnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPesquisaActionPerformed
        pesquisarPaciente();
    }//GEN-LAST:event_jbtnPesquisaActionPerformed

    private void jrdbPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrdbPacienteActionPerformed
        carregarTabela(this.tabelaAtendimentos);
    }//GEN-LAST:event_jrdbPacienteActionPerformed

    private void jbtnLimarPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLimarPesquisaActionPerformed
        limparPesquisa();
    }//GEN-LAST:event_jbtnLimarPesquisaActionPerformed

    private void jrdbDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrdbDataActionPerformed
        carregarTabela(this.tabelaAtendimentos);
    }//GEN-LAST:event_jrdbDataActionPerformed

    private void jbtnPedExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPedExameActionPerformed
        imprimirPedExame();
    }//GEN-LAST:event_jbtnPedExameActionPerformed

    private void jbtnAtestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAtestadoActionPerformed
        imprimirAtestado();
    }//GEN-LAST:event_jbtnAtestadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup gbtnOrdem;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelPesquisa;
    private javax.swing.JPanel jPanelTabela;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnAtestado;
    private javax.swing.JButton jbtnAzul;
    private javax.swing.JButton jbtnComum;
    private javax.swing.JButton jbtnEspecial;
    private javax.swing.JButton jbtnFechar;
    private javax.swing.JButton jbtnLimarPesquisa;
    private javax.swing.JButton jbtnPedExame;
    private javax.swing.JButton jbtnPesquisa;
    private javax.swing.JLabel jlblTitulo;
    private javax.swing.JRadioButton jrdbData;
    private javax.swing.JRadioButton jrdbPaciente;
    private javax.swing.JTable jtblAtendimento;
    private javax.swing.JTextField jtxfPaciente;
    private javax.swing.JTextField jtxfPesquisa;
    public javax.swing.JTextField jtxfPesquisaOculta;
    // End of variables declaration//GEN-END:variables

}
