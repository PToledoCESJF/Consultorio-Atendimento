package cemisV1_2.model.dao;

import cemisV1_2.model.Atestado;
import cemisV1_2.model.AtestadoModelo;
import cemisV1_2.model.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtestadoDAO {
    private static PreparedStatement opNovoAtestado;
    private static PreparedStatement opListarAtestado;
    private static PreparedStatement opAtualizarAtestado;
    private static PreparedStatement opExcluirAtestado;
    
    private static PreparedStatement opNovoModelo;
    private static PreparedStatement opListarModelo;
    private static PreparedStatement opAtualizarModelo;
    private static PreparedStatement opExcluirModelo;

    private static PreparedStatement opUltimoAtestado;
    private static PreparedStatement opRelatorio;
    
    public AtestadoDAO() {
        try {
            Connection conexao = ConnectionFactory.createConnection();
            opNovoAtestado = conexao.prepareStatement("INSERT INTO atestado(ModeloAtestado, "
                    + "Paciente, dataAtestado, texto) VALUES(?, ?, ?, ?)");
            opListarAtestado = conexao.prepareStatement("SELECT * FROM atestado ORDER BY idAtestado");
            opAtualizarAtestado = conexao.prepareStatement("UPDATE atestado SET ModeloAtestado = ?, "
                    + "Paciente = ?, dataAtestado = ?, texto = ? WHERE idAtestado = ?");
            opExcluirAtestado = conexao.prepareStatement("DELETE FROM atestado WHERE idAtestado = ?");
        
            opNovoModelo = conexao.prepareStatement("INSERT INTO atestadomodelo(titulo, "
                    + "texto) VALUES(?, ?)");
            opListarModelo = conexao.prepareStatement("SELECT * FROM atestadomodelo ORDER BY titulo");
            opAtualizarModelo = conexao.prepareStatement("UPDATE atestadomodelo SET titulo = ?, "
                    + "texto = ? WHERE idModAtestado = ?");
            opExcluirModelo = conexao.prepareStatement("DELETE FROM atestadomodelo WHERE idModAtestado = ?");
                    
            opUltimoAtestado = conexao.prepareStatement("SELECT * FROM atestado ORDER BY idAtestado");
            opRelatorio = conexao.prepareStatement("SELECT * FROM atestado WHERE idAtestado = ?");
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar no banco: "+ex);
        }
    }
    
    public void salvarAtestado(Atestado at) throws SQLException{
        opNovoAtestado.clearParameters();
        opNovoAtestado.setInt(1, at.getModeloAtestado());
        opNovoAtestado.setInt(2, at.getPaciente());
        opNovoAtestado.setDate(3, new java.sql.Date(at.getDataAtestado().getTime()));
        opNovoAtestado.setString(4, at.getTexto());

        opNovoAtestado.executeUpdate();
    }
    
    public List<Atestado> listarAtestados() throws SQLException{
        List<Atestado> lat = new ArrayList<>();
        ResultSet resultado = opListarAtestado.executeQuery();
        while(resultado.next()){
            Atestado atAtual = new Atestado();
            atAtual.setIdAtestado(resultado.getInt("idAtestado"));
            atAtual.setModeloAtestado(resultado.getInt("ModeloAtestado"));
            atAtual.setPaciente(resultado.getInt("Paciente"));
            atAtual.setDataAtestado(new java.util.Date(resultado.getDate("dataAtestado").getTime()));

            lat.add(atAtual);
        }
        return lat;
    }
    
    public void atualizarAtestado(Atestado at) throws SQLException{
        opAtualizarAtestado.clearParameters();
        opAtualizarAtestado.setInt(1, at.getModeloAtestado());
        opAtualizarAtestado.setInt(2, at.getPaciente());
        opAtualizarAtestado.setDate(3, new java.sql.Date(at.getDataAtestado().getTime()));
        opAtualizarAtestado.setString(4, at.getTexto());
        opAtualizarAtestado.setInt(5, at.getIdAtestado());
        opAtualizarAtestado.executeUpdate();
    }
    
    public void excluirAtestado(int idAtestado) throws SQLException{
        opExcluirAtestado.clearParameters();
        opExcluirAtestado.setInt(1, idAtestado);
        opExcluirAtestado.executeUpdate();
    }
    
        public void salvarModelo(AtestadoModelo am) throws SQLException{
        opNovoModelo.clearParameters();
        opNovoModelo.setString(1, am.getTitulo());
        opNovoModelo.setString(2, am.getTexto());

        opNovoModelo.executeUpdate();
    }
    
    public List<AtestadoModelo> listarModelos() throws SQLException{
        List<AtestadoModelo> lma = new ArrayList<>();
        ResultSet resultado = opListarModelo.executeQuery();
        while(resultado.next()){
            AtestadoModelo amAtual = new AtestadoModelo();
            amAtual.setIdModAtestado(resultado.getInt("idModAtestado"));
            amAtual.setTitulo(resultado.getString("titulo"));
            amAtual.setTexto(resultado.getString("texto"));

            lma.add(amAtual);
        }
        return lma;
    }
    
    public void atualizarModelo(AtestadoModelo am) throws SQLException{
        opAtualizarModelo.clearParameters();
        opAtualizarModelo.setString(1, am.getTitulo());
        opAtualizarModelo.setString(2, am.getTexto());
        opAtualizarModelo.setInt(3, am.getIdModAtestado());
        opAtualizarModelo.executeUpdate();
    }
    
    public void excluirModelo(int idModelo) throws SQLException{
        opExcluirModelo.clearParameters();
        opExcluirModelo.setInt(1, idModelo);
        opExcluirModelo.executeUpdate();
    }
    
    public int idAt() throws SQLException{
        opUltimoAtestado.clearParameters();
        ResultSet resultado = opUltimoAtestado.executeQuery();
        if(resultado.last()){
            return resultado.getInt("idAtestado");
        }else{
            return -1;
        }
    }
    
    public ResultSet relatorioAtestado(int atestado) throws SQLException{
        opRelatorio.clearParameters();
        opRelatorio.setInt(1, atestado);
        return opRelatorio.executeQuery();
    }
    
}
