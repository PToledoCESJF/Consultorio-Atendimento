package cemisV1_2.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection conexao = null;

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        if (conexao == null) {
            final String DRIVER = "com.mysql.jdbc.Driver";
            final String URL = "jdbc:mysql://localhost:3306/cemisbd_v1_1";
            final String USER = "root";
            final String PASSWORD = "root";
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        }

        return conexao;
    }

    public void desconectar() throws Exception {

        conexao.close();

    }

}
