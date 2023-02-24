package pl.coderslab.dao;

import pl.coderslab.model.User;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public User create(User user) {
        try (Connection conn = DbUtil.connect()) {
            String query = "insert into users(email, username, password) values(?,?,?)";
            String[] values = {user.getEmail(), user.getUsername(), user.getPassword()};
            DbUtil.executeUpdate(DbUtil.connect(), query, values);
            List<List<String>> idInList = new ArrayList<>();
            idInList = DbUtil.executeQuery(DbUtil.connect(), "select max(id) from users");
            String id = idInList.get(0).get(0);
            user.setId(Long.parseLong(id));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
