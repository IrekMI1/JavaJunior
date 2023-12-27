package Sem4;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseClass {

    public String user = "root";
    public String password = "2022";
    public String url = "jdbc:mysql://localhost:3306";

    public Connection getConnection(String url, String user, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public ArrayList<String> getDataWithAuthor(Statement statement, String author) throws SQLException {
        ArrayList<String> result = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `test`.`books` WHERE " +
                "`author`= " + author + ";");
        while (resultSet.next()) {
            result.add("id = " + resultSet.getInt(1) +
                    "; name = " + resultSet.getString(2) +
                    "; author = " + resultSet.getString(3));
        }
        return result;
    }

    public static void main(String[] args) {
        DatabaseClass db = new DatabaseClass();

        try (Connection connection = db.getConnection(db.url, db.user, db.password);) {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE `test`.`books`;");
            statement.execute("CREATE TABLE `test`.`books` (" +
                    "`id` BIGINT NOT NULL, " +
                    "`name` VARCHAR(60), " +
                    "`author` VARCHAR(60)," +
                    "PRIMARY KEY(`id`));");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (1,'Hopes', 'Greek Mitanse')");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (2,'Live is Long', 'Greek Mitanse')");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (3,'Kids in adventure', 'Alan Bury')");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (4,'Lean On Me', 'Sophia Nellis')");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (5,'Day in Miami', 'Alan Bury');");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (6,'Summer Rain', 'Henry Jason');");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (7,'Closeup', 'Mike Petersen');");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (8,'Rides on bike', 'Henry Jason');");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (9,'Flowers in the river', 'Kelly Monsoon');");
            statement.execute("INSERT INTO `test`.`books` (`id`,`name`,`author`) " +
                    "VALUES (10,'Life on Earth', 'Lisa Mann');");

            ArrayList<String> data = db.getDataWithAuthor(statement, "'Alan Bury'");
            System.out.println(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
