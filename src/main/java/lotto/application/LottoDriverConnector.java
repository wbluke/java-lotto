package lotto.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LottoDriverConnector {
    private static String server = "localhost";
    private static String database = "lotto";
    private static String userName = "root";
    private static String password = "1234";

    private LottoDriverConnector() {
    }

    public static Connection getConnection() {
        loadJdbcDriver();
        return connectJdbcDriver();
    }

    private static void loadJdbcDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(" !! JDBC Driver load 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Connection connectJdbcDriver() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?useSSL=false&serverTimezone=UTC", userName, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.err.println("connection 오류:" + e.getMessage());
        }
    }
}
