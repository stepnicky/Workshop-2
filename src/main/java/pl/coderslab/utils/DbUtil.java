package pl.coderslab.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

    private static final String CREATE_TABLE = "create table users (\n" +
            "  id int auto_increment primary key,\n" +
            "  username varchar(100) not null ,\n" +
            "  email varchar(200) not null unique,\n" +
            "  password varchar(200) not null\n" +
            ");";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
