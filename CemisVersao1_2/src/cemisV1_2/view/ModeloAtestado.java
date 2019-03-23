package cemisV1_2.view;

import cemisV1_2.controller.AtestadoController;
import cemisV1_2.controller.PacienteController;
import cemisV1_2.model.Atestado;
import cemisV1_2.model.AtestadoModelo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ModeloAtestado extends javax.swing.JPanel {

    private List<String> listObject = new ArrayList<>();
    private MaskFormatter maskData;
    private MaskFormatter maskHoras;
    private SimpleDateFormat fdtExtenso;
    private AtestadoModelo atestadoModelo = new AtestadoModelo();
    private String paciente = " **** PACIENTE ****";

    public ModeloAtestado() {
        initComponents();
        carregarTela();
    }

    public ModeloAtestado(AtestadoModelo atMod) {
        initComponents();
        atestadoModelo = atMod;
        carregarTela();
        selecaoDeCampos();
    }

    public ModeloAtestado(String pac, AtestadoModelo atMod) {
        initComponents();
        atestadoModelo = atMod;
        this.paciente = pac;
        carregarTela();
        selecaoDeCampos();
    }

    private void carregarTela() {
        try {
            maskData = new MaskFormatter("##/##/####");
            jtxfDataInicio.setFormatterFactory(new DefaultFormatterFactory(maskData));
            jtxfDataFim.setFormatterFactory(new DefaultFormatterFactory(maskData));
            maskHoras = new MaskFormatter("##:##");
            jtxfHoras.setFormatterFactory(new DefaultFormatterFactory(maskHoras));
            fdtExtenso = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy");

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro nas MASCARAS", "Erro", 0);
        }
        
        jdcData.setDate(new Date());
        
        jlblDocumento.setVisible(false);
        jtxfDocumento.setVisible(false);
        jlblLocal.setVisible(false);
        jcmbLocal.setVisible(false);
        jlblDias.setVisible(false);
        jcmbDias.setVisible(false);
        jlblHoras.setVisible(false);
        jtxfHoras.setVisible(false);
        jlblAtividades.setVisible(false);
        jtxfAtividades.setVisible(false);
        jlblPatologia.setVisible(false);
        jtxfPatologia.setVisible(false);
        jlblMedicamento.setVisible(false);
        jtxfMedicamento.setVisible(false);
        jlblDataInicio.setVisible(false);
        jtxfDataInicio.setVisible(false);
        jlblDataFim.setVisible(false);
        jtxfDataFim.setVisible(false);

    }

    public void selecaoDeCampos() {
        String [] txtMesclado = atestadoModelo.getTexto().split("§");
        listObject = Arrays.asList(txtMesclado);
        for (int i = 0; i < listObject.size(); i++) {
            switch (listObject.get(i).toString()
                    ) {
                case "[DOCUMENTO]":
                    jlblDocumento.setVisible(true);
                    jtxfDocumento.setVisible(true);
                    break;
                case "[LOCAL]":
                    jlblLocal.setVisible(true);
                    jcmbLocal.setVisible(true);
                    break;
                case "[DIAS]":
                    jlblDias.setVisible(true);
                    jcmbDias.setVisible(true);
                    break;
                case "[HORAS]":
                    jlblHoras.setVisible(true);
                    jtxfHoras.setVisible(true);
                    break;
                case "[ATIVIDADES]":
                    jlblAtividades.setVisible(true);
                    jtxfAtividades.setVisible(true);
                    break;
                case "[PATOLOGIA]":
                    jlblPatologia.setVisible(true);
                    jtxfPatologia.setVisible(true);
                    break;
                case "[MEDICAMENTO]":
                    jlblMedicamento.setVisible(true);
                    jtxfMedicamento.setVisible(true);
                    break;
                case "[DATA_INICIO]":
                    jlblDataInicio.setVisible(true);
                    jtxfDataInicio.setVisible(true);
                    break;
                case "[DATA_FIM]":
                    jlblDataFim.setVisible(true);
                    jtxfDataFim.setVisible(true);
                    break;
                default:
                    break;
            }
        }
        carregarDados();
    }

    private void carregarDados() {
        String texto = "      ";
        for (int i = 0; i < listObject.size(); i++) {
            texto += " " + listObject.get(i);
        }
        jEdTexto.setText(texto);
    }

    private void visualizarDados() {
//         Recebe um List e buscar a existencia do campo nele 
        List<Object> lst = new ArrayList<>(listObject);
        String texto = "      ";

        for (int i = 0; i < lst.size(); i++) {
            switch(lst.get(i).toString()){
                case "[PACIENTE]" : 
                    lst.set(i, this.paciente);
                    break;
                case "[DOCUMENTO]" :
                    lst.set(i, jtxfDocumento.getText());
                    break;
                case "[LOCAL]" :
                    lst.set(i, jcmbLocal.getSelectedItem());
                    break;
                case "[DIAS]" :
                    lst.set(i, jcmbDias.getSelectedItem());
                    break;
                case "[HORAS]"  :
                    lst.set(i, jtxfHoras.getText());
                    break;
                case "[ATIVIDADES]" :
                    lst.set(i, jtxfAtividades.getText());
                    break;
                case "[PATOLOGIA]" :
                    lst.set(i, jtxfPatologia.getText());
                    break;
                case "[MEDICAMENTO]" :
                    lst.set(i, jtxfMedicamento.getText());
                    break;
                case "[DATA_INICIO]" :
                    lst.set(i, jtxfDataInicio.getText());
                    break;
                case "[DATA_FIM]" :
                    lst.set(i, jtxfDataFim.getText());
                    break;
                default:
                    break;
            }
            texto += " " + lst.get(i);
        }
        jEdTexto.setText(texto);
        jlblData.setText(fdtExtenso.format(jdcData.getDate()));
    }
    
    private void salvarEImprimir(){
        visualizarDados();
        PacienteController pc = new PacienteController();
        
        Atestado atAtual = new Atestado(pc.converteNomePacienteEmIdPaciente(this.paciente),
                atestadoModelo.getIdModAtestado(), jdcData.getDate(), jEdTexto.getText());
        
        if(!AtestadoController.salvarAtestado(atAtual)){
            System.out.println("Erro ao salvar no Banco!"); 
        }else{
            imprimirAtestado();
        }
    }
    
    private void imprimirAtestado(){
        try {
            JRResultSetDataSource resultado = new JRResultSetDataSource(
                    AtestadoController.relatorioAtestado(AtestadoController.utimoSalvo()));
            JasperReport jRep = (JasperReport) JRLoader.loadObject(getClass().getResource("/cemisV1_2/reporter/Atestado.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jRep, new HashMap(), resultado);
            JasperViewer tela = new JasperViewer(jasperPrint, false);
            tela.setVisible(true);
            tela.toFront();
        } catch (JRException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro no Relatório. \nErro: " + ex);
        }
    }
   
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlblDocumento = new javax.swing.JLabel();
        jcmbLocal = new javax.swing.JComboBox<>();
        jtxfDocumento = new javax.swing.JTextField();
        jlblLocal = new javax.swing.JLabel();
        jlblDias = new javax.swing.JLabel();
        jlblAtividades = new javax.swing.JLabel();
        jtxfAtividades = new javax.swing.JTextField();
        jlblPatologia = new javax.swing.JLabel();
        jtxfPatologia = new javax.swing.JTextField();
        jlblMedicamento = new javax.swing.JLabel();
        jtxfMedicamento = new javax.swing.JTextField();
        jlblDataInicio = new javax.swing.JLabel();
        jlblDataFim = new javax.swing.JLabel();
        jlblHoras = new javax.swing.JLabel();
        jcmbDias = new javax.swing.JComboBox<>();
        jtxfHoras = new javax.swing.JFormattedTextField();
        jtxfDataInicio = new javax.swing.JFormattedTextField();
        jtxfDataFim = new javax.swing.JFormattedTextField();
        jbtnVisualizar = new javax.swing.JButton();
        jbtnSalvar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlblData = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEdTexto = new javax.swing.JEditorPane();
        jPanel5 = new javax.swing.JPanel();
        jdcData = new com.toedter.calendar.JDateChooser();

        jlblDocumento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblDocumento.setText("Documento:");

        jcmbLocal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jcmbLocal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "das atividades laborativas", "das atividades escolares", "das atividades acadêmicas" }));

        jtxfDocumento.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jlblLocal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblLocal.setText("Local:");

        jlblDias.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblDias.setText("Dias:");

        jlblAtividades.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblAtividades.setText("Atividades:");

        jtxfAtividades.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jlblPatologia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblPatologia.setText("Patologia:");

        jtxfPatologia.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jlblMedicamento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblMedicamento.setText("Medicamento:");

        jtxfMedicamento.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jlblDataInicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblDataInicio.setText("Data Inicio:");

        jlblDataFim.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblDataFim.setText("Data Fim:");

        jlblHoras.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblHoras.setText("Horas:");

        jcmbDias.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jcmbDias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "02 (dois)", "03 (três)", "04 (quatro)", "05 (cinco)", "06 (seis)", "07 (sete)", "08 (oito)", "09 (nove)", "10 (dez)", "11 (onze)", "12 (doze)", "13 (treze)", "14 (quatorze)", "15 (quinze)", "16 (dezesseis)", "17 (dezessete)", "18 (dezoito)", "19 (dezenove)", "20 (vinte)", "21 (vinte e um)", "22 (vinte e dois)", "23 (vinte e três)", "24 (vinte e quatro)", "25 (vinte e cinco)", "26 (vinte e seis)", "27 (vinte e sete)", "28 (vinte e oito)", "29 (vinte e nove)", "30 (trinta)", "60 (sessenta)", "90 (noventa)", "120 (cento e vinte)" }));

        jtxfHoras.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jtxfDataInicio.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jtxfDataFim.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jbtnVisualizar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbtnVisualizar.setText("Visualizar");
        jbtnVisualizar.setPreferredSize(new java.awt.Dimension(140, 34));
        jbtnVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnVisualizarActionPerformed(evt);
            }
        });

        jbtnSalvar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbtnSalvar.setText("Salvar e Imprimir");
        jbtnSalvar.setPreferredSize(new java.awt.Dimension(140, 34));
        jbtnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlblDataFim, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblDataInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblMedicamento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(jlblLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblDias, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblHoras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblAtividades, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblPatologia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcmbLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcmbDias, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxfHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxfDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jtxfDataFim, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtxfAtividades, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(jtxfPatologia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(jtxfMedicamento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDocumento)
                    .addComponent(jtxfDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblLocal)
                    .addComponent(jcmbLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDias)
                    .addComponent(jcmbDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxfHoras)
                    .addComponent(jlblHoras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblAtividades)
                    .addComponent(jtxfAtividades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblPatologia)
                    .addComponent(jtxfPatologia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblMedicamento)
                    .addComponent(jtxfMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDataInicio)
                    .addComponent(jtxfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDataFim)
                    .addComponent(jtxfDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Atestado Médico");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Atesto para os devidos fins que:");

        jlblData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlblData.setText("?!DATA");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Lima Duarte, ");

        jEdTexto.setEditable(false);
        jEdTexto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jEdTexto.setAutoscrolls(false);
        jEdTexto.setFocusable(false);
        jScrollPane2.setViewportView(jEdTexto);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblData, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlblData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jdcData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(jdcData, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jdcData, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnVisualizarActionPerformed
        visualizarDados();
    }//GEN-LAST:event_jbtnVisualizarActionPerformed

    private void jbtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalvarActionPerformed
        salvarEImprimir();
    }//GEN-LAST:event_jbtnSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEdTexto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnSalvar;
    private javax.swing.JButton jbtnVisualizar;
    private javax.swing.JComboBox<String> jcmbDias;
    private javax.swing.JComboBox<String> jcmbLocal;
    private com.toedter.calendar.JDateChooser jdcData;
    private javax.swing.JLabel jlblAtividades;
    private javax.swing.JLabel jlblData;
    private javax.swing.JLabel jlblDataFim;
    private javax.swing.JLabel jlblDataInicio;
    private javax.swing.JLabel jlblDias;
    private javax.swing.JLabel jlblDocumento;
    private javax.swing.JLabel jlblHoras;
    private javax.swing.JLabel jlblLocal;
    private javax.swing.JLabel jlblMedicamento;
    private javax.swing.JLabel jlblPatologia;
    private javax.swing.JTextField jtxfAtividades;
    private javax.swing.JFormattedTextField jtxfDataFim;
    private javax.swing.JFormattedTextField jtxfDataInicio;
    private javax.swing.JTextField jtxfDocumento;
    private javax.swing.JFormattedTextField jtxfHoras;
    private javax.swing.JTextField jtxfMedicamento;
    private javax.swing.JTextField jtxfPatologia;
    // End of variables declaration//GEN-END:variables
}
