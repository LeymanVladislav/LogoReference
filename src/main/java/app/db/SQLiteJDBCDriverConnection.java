package app.db;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author sqlitetutorial.net
 */
public class SQLiteJDBCDriverConnection {
    private static String PKG_NAME = "SQLiteJDBCDriverConnection";
    private static String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static String USER = "sys";
    private static String PASS = "19871214";
    private static Connection conn = null;
    /**
     * Connect to a sample database
     */
    // Соединение с базой
    public static void connect() {
        String Modul = "connect ";

        try {
            // Register JDBC Driver
            DriverManager.registerDriver(new org.postgresql.Driver());
            // create a connection to the database
            conn = DriverManager.getConnection(url, USER, PASS);

            System.out.println(PKG_NAME + "." + Modul + "Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Завершение соединения с базой
    public static void close() {
        String Modul = "close ";
        try {
                if (conn != null) {
                    conn.close();
                    System.out.println(PKG_NAME + "." + Modul + "DB has been closed");
                }
            } catch (SQLException ex) {
                System.out.println(PKG_NAME + "." + Modul + ex.getMessage());
            }
    }

    // Получение списка пользователей
    public static ArrayList<String> getUserList() {
        String Modul = "getUserList ";

        // SQL statement for get user list
        String sql = "select * \n"
                + "from users;";

        ArrayList<String> Names = new ArrayList<>();
        ArrayList<String> Pass = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs    = stmt.executeQuery(sql);

            System.out.println(PKG_NAME + "." + Modul + " SQL RESULT:");
            // loop through the result set
            while (rs.next()) {
                Names.add(rs.getString("NAME"));
                Pass.add(rs.getString("PASS"));

                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("NAME") + "\t" +
                        rs.getString("PASS"));
                        //rs.getDouble("capacity"));
            }

        } catch (SQLException e) {
            System.out.println(PKG_NAME + "." + Modul + e.getMessage());
        }
        return Names;
    }

    // Добавление пользователя
    public static void AddUser(String Name, String Pass) {
        String Modul = "AddUser ";

        // SQL statement for get user list
        String sql = "insert into users (NAME, PASS)  VALUES ('" + Name + "', '" + Pass + "');\n"
                + "commit;";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println(PKG_NAME + "." + Modul + "USER " + Name + " has been added");

        } catch (SQLException e) {
            System.out.println(PKG_NAME + "." + Modul + e.getMessage());
        }
    }


    public static void main(String[] args) {
        connect();
        getUserList();
        close();
    }
}