package app.db;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author sqlitetutorial.net
 */
public class JDBCDriverConnection {
    private static String PKG_NAME = "JDBCDriverConnection";
    private static String DBDriver = "jdbc:postgresql";
    private static String Host = "ec2-79-125-117-53.eu-west-1.compute.amazonaws.com";
    private static String Port = "5432";
    private static String DBName = "d8hhfcadaek34f";
    private static String SSLMode = "sslmode=require";
    private static String User = "tisxxhgikougzx";
    private static String Pass = "2ff4346212f4b9c2a67ed039eecf1d838ecfb01570e08c44ff53063275d43f6b";
    private static String url = DBDriver + "://" + Host + ":" + Port + "/" + DBName + "?" + SSLMode;
                                //"jdbc:postgresql://localhost:5432/postgres";
    private static Connection conn = null;

    public static class DysgraphiaType {
        public Integer id;
        public String name;
        public String description;
    }

    public static class DefectType {
        public Integer id;
        public String name;
        public Integer dysgraphia_id;
        public String description;
    }

    public static class ExercisesType {
        public Integer id;
        public String name;
        public Integer dysgraphia_id;
        public String description;
        public String dysgraphia;
    }

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

            System.out.println(PKG_NAME + "." + Modul + "Connection to DB has been established.");

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

    // Создание окружения
    public static void createDbObjects() {
        String Modul = "createDbObjects ";

        // SQL statement for get user list
        String sql;

        // create table dysgraphia
        sql = "CREATE TABLE dysgraphia\n" +
                "(\n" +
                "    id integer NOT NULL,\n" +
                "    name character(100) NOT NULL,\n" +
                "    description character(4000) NOT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE dysgraphia\n" +
                "    OWNER to jrcyfbsexdmqnt;\n";

        // create table defect
        sql += "CREATE TABLE defect\n" +
                "(\n" +
                "    id integer NOT NULL,\n" +
                "    name character(100) NOT NULL,\n" +
                "\tdysgraphia_id  integer NOT NULL,\n" +
                "    description character(4000) NOT NULL,\n" +
                "    PRIMARY KEY (id),\n" +
                "    CONSTRAINT defect_dysgraphia_id_fk FOREIGN KEY (dysgraphia_id)\n" +
                "        REFERENCES dysgraphia (id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE defect\n" +
                "    OWNER to jrcyfbsexdmqnt;";
        // create trigger defect
        sql += "CREATE SEQUENCE \"defect_id_seq\";\n" +
                "\n" +
                "ALTER SEQUENCE \"defect_id_seq\"\n" +
                "    OWNER TO jrcyfbsexdmqnt;\n" +
                "\t\n" +
                "CREATE FUNCTION defect_id_trg_fnk()\n" +
                "    RETURNS trigger\n" +
                "    LANGUAGE 'plpgsql'\n" +
                "    NOT LEAKPROOF \n" +
                "AS $BODY$\n" +
                " BEGIN\n" +
                "   New.id:=nextval('defect_id_seq');\n" +
                "   Return NEW;\n" +
                " END;\n" +
                "$BODY$;\n" +
                "\n" +
                "ALTER FUNCTION defect_id_trg_fnk()\n" +
                "    OWNER TO jrcyfbsexdmqnt;\t\n" +
                "\t\n" +
                "CREATE TRIGGER defect_id_trg\n" +
                "    BEFORE INSERT\n" +
                "    ON defect\n" +
                "    FOR EACH ROW\n" +
                "    EXECUTE PROCEDURE defect_id_trg_fnk();\n";

        // create table exercises
        sql += "CREATE TABLE exercises\n" +
                "(\n" +
                "    id integer NOT NULL,\n" +
                "    name character(100) NOT NULL,\n" +
                "\tdysgraphia_id  integer NOT NULL,\n" +
                "    description character(4000) NOT NULL,\n" +
                "    PRIMARY KEY (id),\n" +
                "    CONSTRAINT exercises_dysgraphia_id_fk FOREIGN KEY (dysgraphia_id)\n" +
                "        REFERENCES dysgraphia (id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE exercises\n" +
                "    OWNER to jrcyfbsexdmqnt;\n" +
                "CREATE INDEX exercises_dys_id_idx\n" +
                "    ON exercises USING btree\n" +
                "    (dysgraphia_id ASC NULLS LAST)\n" +
                "    TABLESPACE pg_default;\n";

        // insert
        sql +=  // insert into dysgraphia
                "insert into dysgraphia(id,name,description) values(1,'Дисграфия 1','Описание Дисграфия 1');\n" +
                "insert into dysgraphia(id,name,description) values(2,'Дисграфия 2','Описание Дисграфия 2');\n" +
                // insert into defect
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 1',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 2',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 3',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 4',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 5',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 6',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 7',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 8',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 9',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 10',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 11',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 12',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 13',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 14',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 15',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 16',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 17',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 18',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 19',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 20',1,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 21',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 22',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 23',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 24',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 25',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 26',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 27',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 28',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 29',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 30',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 31',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 32',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 33',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 34',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 35',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 36',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 37',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 38',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 39',2,'Описание Ошибка ');\n" +
                "insert into defect(name,dysgraphia_id,description) values('Ошибка 40',2,'Описание Ошибка ');\n" +
                // insert into dysgraphia
                "insert into exercises(id,name,dysgraphia_id,description) values(1,'Упражнение 1',1,'Описание Упражнение 1');\n" +
                "insert into exercises(id,name,dysgraphia_id,description) values(2,'Упражнение 2',2,'Описание Упражнение 2');\n" +
                "commit;";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println(PKG_NAME + "." + Modul + "All objects has been created.");
        } catch (SQLException e) {
            System.out.println(PKG_NAME + "." + Modul + e.getMessage());
        }
    }

    // Удаление окружения
    public static void dropDbObjects() {
        String Modul = "dropDbObjects ";

        // SQL statement for get user list
        String sql;

        // DROP ALL OBJ
        sql = "DROP FUNCTION defect_id_trg_fnk CASCADE;\n" +
                "DROP SEQUENCE defect_id_seq;\n" +
                "DROP TABLE defect CASCADE;\n" +
                "\n" +
                "DROP TABLE dysgraphia CASCADE;\n" +
                "\n" +
                "DROP TABLE exercises CASCADE;\n";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println(PKG_NAME + "." + Modul + "All objects has been droped.");
        } catch (SQLException e) {
            System.out.println(PKG_NAME + "." + Modul + e.getMessage());
        }
    }

    // Получение списка ошибок
    public static ArrayList<DefectType> getDefectList(ArrayList<String> IDList) {
        String Modul = "getDefectList ";
        ArrayList<DefectType> DefectList = new ArrayList<>();
        // SQL statement for get user list
        String sql = "select * \n"
                + "from defect";
        String id = null;
        if(IDList != null && !IDList.isEmpty()){
            System.out.println("IDList is not null");
            for (int i = 0; i < IDList.size(); i = i + 1)
                if (i == 0) {
                    id = IDList.get(i);
                } else {
                    id += ", " + IDList.get(i);
                }
            sql = sql + " where id in (" + id + ")";
        }

        sql = sql + ";";

        System.out.println(PKG_NAME + "." + Modul + " sql:" + sql);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs    = stmt.executeQuery(sql);

            System.out.println(PKG_NAME + "." + Modul + " SQL RESULT:");
            // loop through the result set
            while (rs.next()) {
                DefectType DefectStr = new DefectType();
                DefectStr.id = rs.getInt("id");
                DefectStr.name = rs.getString("name");
                DefectStr.dysgraphia_id = rs.getInt("dysgraphia_id");
                DefectStr.description = rs.getString("description");
                DefectList.add(DefectStr);

                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getInt("dysgraphia_id"));
                        //rs.getDouble("capacity"));
            }

        } catch (SQLException e) {
            System.out.println(PKG_NAME + "." + Modul + e.getMessage());
        }
        return DefectList;
    }
    // Получение списка ошибок
    public static ArrayList<DefectType> getDefectList() {
        return getDefectList(null);
    }

    // Получение списка упражнений
    public static ArrayList<ExercisesType> getExercisesList(ArrayList<String> DefectIDList) {
        String Modul = "getExercisesList ";
        ArrayList<ExercisesType> ExercisesList = new ArrayList<>();
        // SQL statement for get user list
        String sql = "select e.*, dg.name dysgraphia\n" +
                "from exercises e\n" +
                "join dysgraphia dg on dg.id = e.dysgraphia_id\n";
        String id = null;
        if(DefectIDList != null && !DefectIDList.isEmpty()){
            System.out.println("IDList is not null");
            for (int i = 0; i < DefectIDList.size(); i = i + 1)
                if (i == 0) {
                    id = DefectIDList.get(i);
                } else {
                    id += ", " + DefectIDList.get(i);
                }
            sql = sql + "where dg.id in (select d.dysgraphia_id from defect d where d.id in (" + id + "))";
        }

        sql = sql + ";";

        System.out.println(PKG_NAME + "." + Modul + " sql:" + sql);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs    = stmt.executeQuery(sql);

            System.out.println(PKG_NAME + "." + Modul + " SQL RESULT:");
            // loop through the result set
            while (rs.next()) {
                ExercisesType ExercisesStr = new ExercisesType();
                ExercisesStr.id = rs.getInt("id");
                ExercisesStr.name = rs.getString("name");
                ExercisesStr.dysgraphia_id = rs.getInt("dysgraphia_id");
                ExercisesStr.description = rs.getString("description");
                ExercisesStr.dysgraphia = rs.getString("dysgraphia");
                ExercisesList.add(ExercisesStr);

                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getInt("dysgraphia_id"));
                //rs.getDouble("capacity"));
            }

        } catch (SQLException e) {
            System.out.println(PKG_NAME + "." + Modul + e.getMessage());
        }
        return ExercisesList;
    }
    // Получение списка ошибок
    public static ArrayList<ExercisesType> getExercisesList() {
        return getExercisesList(null);
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
        ArrayList<String> Idlst = new ArrayList<>();
        Idlst.add("4");
        Idlst.add("1");
        //System.out.println("test: " + Idlst.get(2));
        //getDefectList(Idlst);
        //getExercisesList(Idlst);

        //dropDbObjects();
        createDbObjects();
        close();
    }
}
