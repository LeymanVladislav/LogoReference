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
    private static String Host = "ec2-54-217-233-71.eu-west-1.compute.amazonaws.com";
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
                "    OWNER to " + User + ";\n";

        // create table defect
        sql += "CREATE TABLE defect\n" +
                "(\n" +
                "    id integer NOT NULL,\n" +
                "    name character(4000) NOT NULL,\n" +
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
                "    OWNER to " + User + ";";
        // create trigger defect
        sql += "CREATE SEQUENCE \"defect_id_seq\";\n" +
                "\n" +
                "ALTER SEQUENCE \"defect_id_seq\"\n" +
                "    OWNER TO " + User + ";\n" +
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
                "    OWNER TO " + User + ";\t\n" +
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
                "    dysgraphia_id  integer NOT NULL,\n" +
                "    period  integer,\n" +
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
                "    OWNER to " + User + ";\n" +
                "CREATE INDEX exercises_dys_id_idx\n" +
                "    ON exercises USING btree\n" +
                "    (dysgraphia_id ASC NULLS LAST)\n" +
                "    TABLESPACE pg_default;\n";

        // insert
        sql +=  // insert into dysgraphia
                "insert into dysgraphia(id,name,description) values(1,'Ошибки артикуляционно-акустической дисграфии','замена и пропуски букв по принципу сходства соответствующих им звуков');\n" +
                "insert into dysgraphia(id,name,description) values(2,'Ошибки оптической дисграфии','замена букв по принципу оптического сходства и искаженное их написание');\n" +
                "insert into dysgraphia(id,name,description) values(3,'Моторная дисграфия','нарушение связи моторных образов слов (букв) с их звуковыми и зрительными образами');\n" +
                "insert into dysgraphia(id,name,description) values(4,'Дисграфия на почве языкового анализа и синтеза','искажение звуко - слоговой структуры слов и нарушение границ между словами предложениями');\n" +
                "insert into dysgraphia(id,name,description) values(5,'Аграмматическая дисграфия','аграмматизмы на письме');\n" +
                "insert into dysgraphia(id,name,description) values(6,'Дисорфография','орфографические ошибки');\n" +
                // insert into defect
                "insert into defect(id,name,dysgraphia_id,description) values(1,'Замены звонких согласных парными глухими и наоборот (б-п, д-т, г-к, в-ф, з-с, ж-ш)',1,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(2,'Замены мягких согласных соответствующими твёрдыми (и наоборот)',1,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(3,'Разнообразные замены в группах свистящих (с, з, ц) и шипящих (ш, ж, ч, щ, з) звуков',1,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(4,'Разнообразные буквенные замены в группе сонорных согласных (р, рь, п, пь), й',1,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(5,'Замены гласных звуков',1,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(6,'Пропуски букв соответствующих пропускам звуков в устной речи а)функция зрительного анализа и синтеза; б)функция пространственных представлений',1,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(7,'Замена букв по принципу оптического сходства и искаженное их написание, состоящих из одинаковых элементов, но различно расположенных в пространстве (в-д, т-ш)',2,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(8,'Замена букв по принципу оптического сходства и искаженное их написание, включающих одинаковые элементы, но отличающихся дополнительными элементами (и-ш, п-т, х-ж, л-м)',2,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(9,'Зеркальное написание букв (с- ,э- )',2,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(10,'Пропуски элементов, особенно при соединении букв, включающих одинаковый элемент (ау-а )',2,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(11,'Добавление лишних элементов (ш- )',2,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(12,'Несоблюдение строки. Двигательная функция',2,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(13,'Нарушение качества почерка: сильный нажим, неровные буквы, много дополнительных штрихов, съезжание со строки',3,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(14,'Пропуск букв, элементов букв, слов',3,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(15,'Стереотипные добавления одной и той же буквы, элемента буквы, слога, слова. Нарушение функции языкового анализа и синтеза',3,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(16,'Пропуски гласных и согласных букв в словах',4,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(17,'Добавление лишних букв',4,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(18,'Перестановка букв',4,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(19,'Пропуск слогов, добавление, перестановка',4,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(20,'Нарушение деления на слова',4,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(21,'Слияние нескольких слов в одно',4,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(22,'Разделение слова на части. Несформированность  лексико - грамматической функции речи',4,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(23,'В связной речи',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(24,'Трудности в установлении логических связей между предложениями',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(25,'Последовательность предложений не всегда соответствует последовательности событий',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(26,'Нарушение смысловых и грамматических связей между предложениями',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(27,'Искажение морфологической структуры слова, замена префиксов,  суффиксов',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(28,'Изменение падежных окончаний',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(29,'Нарушений предложных конструкций',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(30,'Изменение падежа местоимений, числа существительных',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(31,'Нарушение согласования',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(32,'Нарушение синтаксического оформления речи: пропуски членов предложения, наруше-ние последовательности, трудности конструирования сложных предложений',5,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(33,'Сочетание ЖИ – ШИ, ЧА – ЩА, ЧУ – ЩУ',6,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(34,'Заглавная буква в начале предложения, в именах собственных',6,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(35,'Обозначение мягкости согласных на письме',6,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(36,'Безударные гласные',6,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(37,'Парные согласные',6,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(38,'Непроизносимая согласная',6,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(39,'Разделительные Ъ и Ь знаки',6,'');\n" +
                "insert into defect(id,name,dysgraphia_id,description) values(40,'Словарные слова',6,'');\n" +

                // insert into dysgraphia
                "insert into exercises(id,name,dysgraphia_id,period,description) values(1,'Упражнение 1',2,null,'Внимательно рассмотри буквы и назови их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(2,'Упражнение 2',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(3,'Упражнение 3',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(4,'Упражнение 4',2,null,'Добавь недостающий элемент буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(5,'Упражнение 5',2,null,'Обведи только букву А');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(6,'Упражнение 6',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(7,'Упражнение 7',2,null,'Обведи контуры букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(8,'Упражнение 8',2,null,'Внимательно рассмотри буквы, найди сходство и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(9,'Упражнение 9',2,null,'Найди спрятанные буквы «Б». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(10,'Упражнение 10',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(11,'Упражнение 11',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(12,'Упражнение 12',2,null,'Обведи и раскрась только те шарики, на которых буква Б написана правильно. Соедини их ниточками с Буратино');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(13,'Упражнение 13',2,null,'Добавь недостающий элемент буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(14,'Упражнение 14',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(15,'Упражнение 15',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(16,'Упражнение 16',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(17,'Упражнение 17',2,null,'Найди спрятанные буквы «В». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(18,'Упражнение 18',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(19,'Упражнение 19',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(20,'Упражнение 20',2,null,'Раскрась только те варежки, на которых буква В написана правильно');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(21,'Упражнение 21',2,null,'');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(22,'Упражнение 22',2,null,'Добавь недостающий элемент буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(23,'Упражнение 23',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(24,'Упражнение 24',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(25,'Упражнение 25',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(26,'Упражнение 26',2,null,'Найди спрятанные буквы «Г». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(27,'Упражнение 27',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(28,'Упражнение 28',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(29,'Упражнение 29',2,null,'Раскрась только те груши, на которых буква Г написана правильно');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(30,'Упражнение 30',2,null,'Добавь недостающий элемент буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(31,'Упражнение 31',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(32,'Упражнение 32',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(33,'Упражнение 33',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(34,'Упражнение 34',2,null,'Найди спрятанные буквы «Д». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(35,'Упражнение 35',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(36,'Упражнение 36',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(37,'Упражнение 37',2,null,'Добавь недостающий элемент буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(38,'Упражнение 38',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(39,'Упражнение 39',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(40,'Упражнение 40',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(41,'Упражнение 41',2,null,'Найди спрятанные буквы «Е» и «Ё».Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(42,'Упражнение 42',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(43,'Упражнение 43',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(44,'Упражнение 44',2,null,'Добавь недостающий элемент буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(45,'Упражнение 45',2,null,'Раскрась только те мыльные пузыри, на которых буква Е написана правильно');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(46,'Упражнение 46',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(47,'Упражнение 47',2,null,'Обведи и раскрась только те шарики, на которых написана буква Ё. Соедини их ниточками с ёжиком');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(48,'Упражнение 48',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(49,'Упражнение 49',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(50,'Упражнение 50',2,null,'Найди спрятанные буквы «Ж». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(51,'Упражнение 51',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(52,'Упражнение 52',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(53,'Упражнение 53',2,null,'Раскрась шарики, на которых написана буква Ж. Соедини ниточками с жирафом');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(54,'Упражнение 54',2,null,'');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(55,'Упражнение 55',2,null,'Добавь недостающюю часть, чтоб получилась буква Ж');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(56,'Упражнение 56',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(57,'Упражнение 57',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(58,'Упражнение 58',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(59,'Упражнение 59',2,null,'Найди спрятанные буквы «З». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(60,'Упражнение 60',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(61,'Упражнение 61',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(62,'Упражнение 62',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(63,'Упражнение 63',2,null,'Раскрась воздушных змеев, на которых буква З написано правильно. Соедини их ниточками с зайкой');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(64,'Упражнение 64',2,null,'Добавь недостающий элемент буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(65,'Упражнение 65',2,null,'Какой ключ подойдет к замку? Соедини ключ с замком');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(66,'Упражнение 66',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(67,'Упражнение 67',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(68,'Упражнение 68',2,null,'Найди спрятанные буквы «И» и «Й». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(69,'Упражнение 69',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(70,'Упражнение 70',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(71,'Упражнение 71',2,null,'Отремонтируй буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(72,'Упражнение 72',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(73,'Упражнение 73',2,null,'Раскрась только те кубики, на которых написана буква Й');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(74,'Упражнение 74',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(75,'Упражнение 75',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(76,'Упражнение 76',2,null,'Найди спрятанные буквы «К». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(77,'Упражнение 77',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(78,'Упражнение 78',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(79,'Упражнение 79',2,null,'Обведи и раскрась только те шарики, на которых буква К написана правильно. Соедини их ниточками с Карлсоном');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(80,'Упражнение 80',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(81,'Упражнение 81',2,null,'Добавь недостающий элемент буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(82,'Упражнение 82',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(83,'Упражнение 83',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(84,'Упражнение 84',2,null,'Найди спрятанные буквы «Л». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(85,'Упражнение 85',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(86,'Упражнение 86',2,null,'Раскрась только те флажки, на которых буква Л написана правильно');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(87,'Упражнение 87',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(88,'Упражнение 88',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(89,'Упражнение 89',2,null,'Раскрась только те лампочки, на которых написана буква Л');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(90,'Упражнение 90',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(91,'Упражнение 91',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(92,'Упражнение 92',2,null,'Найди спрятанные буквы «М». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(93,'Упражнение 93',2,null,'Назови перечеркнутые буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(94,'Упражнение 94',2,null,'Определи букву в неправильном положении');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(95,'Упражнение 95',2,null,'Выдели знакомую букву');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(96,'Упражнение 96',2,null,'Добавь недостающий элемент буквы');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(97,'Упражнение 97',2,null,'Обведи контур букв');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(98,'Упражнение 98',2,null,'Внимательно рассмотри буквы, найди сходства и различия');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(99,'Упражнение 99',2,null,'Найди спрятанные буквы «Н». Обведи их');\n" +
                "insert into exercises(id,name,dysgraphia_id,period,description) values(100,'Упражнение 100',2,null,'Назови перечеркнутые буквы');\n" +

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
    public static ArrayList<ExercisesType> getExercisesList(ArrayList<String> DefectIDList, String Period) {
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
        return getExercisesList(null,null);
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

        dropDbObjects();
        createDbObjects();
        close();
    }
}
