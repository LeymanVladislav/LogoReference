package app.db;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author sqlitetutorial.net
 */
public class SQLiteJDBCDriverConnection {
    private static String PKG_NAME = "SQLiteJDBCDriverConnection";
    private static String DBDriver = "jdbc:postgresql";
    private static String Host = "ec2-79-125-117-53.eu-west-1.compute.amazonaws.com";
    private static String Port = "5432";
    private static String DBName = "d40963l0827185";
    private static String SSLMode = "sslmode=require";
    private static String User = "jrcyfbsexdmqnt";
    private static String Pass = "2f4a45cef0abbfe16df17d93565ab53d16e2b4994845a77fae3f01c0c7635f07";
    private static String url = DBDriver + "://" + Host + ":" + Port + "/" + DBName + "?" + SSLMode;
                                //"jdbc:postgresql://localhost:5432/postgres";
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
            //Class.forName("org.postgresql.Driver");
            // create a connection to the database
            conn = DriverManager.getConnection(url, User, Pass);

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
        //getUserList();
        close();
    }
}