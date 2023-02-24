package pl.coderslab.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";
    private static final String DELETE_QUERY = "delete from users where id = ?";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    public static void executeUpdate(Connection conn, String query, String... params) {
        try(PreparedStatement statement = conn.prepareStatement(query)) {
            for(int i = 0; i < params.length; i++) {
                statement.setString(i+1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<List<String>> executeQuery(Connection conn, String query, String... params) {
        List<List<String>> rows = new ArrayList<>();
        try(PreparedStatement statement = conn.prepareStatement(query)) {
            if(params.length > 0){
                for(int i = 0; i < params.length; i++) {
                    statement.setString(i+1, params[i]);
                }
            }
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int numberOfColumns = resultSetMetaData.getColumnCount();
            while(rs.next()) {
                List<String> row = new ArrayList<>();
                for(int i = 0; i < numberOfColumns; i++) {
                    row.add(rs.getString(i+1));
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }
    public static void remove(Connection conn, Long id) {
        try(PreparedStatement statement = conn.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
