package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
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
            String[] values = {user.getEmail(), user.getUsername(), hashPassword(user.getPassword())};
            DbUtil.executeUpdate(conn, query, values);
            List<List<String>> idInList;
            idInList = DbUtil.executeQuery(conn, "select max(id) from users");
            String id = idInList.get(0).get(0);
            user.setId(Long.parseLong(id));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.connect()) {
            String query = "select * from users where id=?";
            List<List<String>> userInMultiList = DbUtil.executeQuery(conn, query, String.valueOf(userId));
            if(userInMultiList.size() == 0) {
                return null;
            }
            List<String> userInList = userInMultiList.get(0);
            User user = new User();
            user.setId(Long.parseLong(userInList.get(0)));
            user.setEmail(userInList.get(1));
            user.setUsername(userInList.get(2));
            user.setPassword(userInList.get(3));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String hashPassword(String password){
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashed;
    }
}
