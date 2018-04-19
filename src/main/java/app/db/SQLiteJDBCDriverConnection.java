package app.db;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author sqlitetutorial.net
 */
public class SQLiteJDBCDriverConnection {
    private static String PKG_NAME = "SQLiteJDBCDriverConnection";
    private static String url = "jdbc:sqlite:E:/GITHUB/IDEA/LogoReference/src/main/db/SQLite.s3db";
    private static Connection conn = null;
    /**
     * Connect to a sample database
     */
    public static void connect() {
        String Modul = "connect ";

        try {
            // Register JDBC Driver
            DriverManager.registerDriver(new org.sqlite.JDBC());
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println(PKG_NAME + "." + Modul + "Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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

    public static ArrayList<String> getUserList() {
        String Modul = "getUserList ";

        // SQL statement for get user list
        String sql = "select * \n"
                + "from users;";

        ArrayList<String> Names = new ArrayList<String>();
        ArrayList<String> Pass = new ArrayList<String>();

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
        }finally {
            return Names;
        }
    }


    public static void main(String[] args) {
        connect();
        getUserList();
        close();
    }
}