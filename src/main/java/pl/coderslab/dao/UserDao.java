package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.model.User;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void update(User user) {
        try (Connection conn = DbUtil.connect()) {
            String updateEmail = "update users set email=? where id=?";
            String updateUsername = "update users set username=? where id=?";
            String updatePassword = "update users set password=? where id=?";
            DbUtil.executeUpdate(conn, updateEmail, user.getEmail(), String.valueOf(user.getId()));
            DbUtil.executeUpdate(conn, updateUsername, user.getUsername(), String.valueOf(user.getId()));
            DbUtil.executeUpdate(conn, updatePassword, hashPassword(user.getPassword()), String.valueOf(user.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(Long userId) {
        try(Connection conn = DbUtil.connect()) {
            DbUtil.remove(conn, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User[] findAll() {
        String query = "select * from users";
        try (Connection conn = DbUtil.connect()){
            List<List<String>> multiListOfUsers = DbUtil.executeQuery(conn, query);
            if(multiListOfUsers.size() < 1) {
                return new User[0];
            }
            User[] users = new User[0];
            for(int i = 0; i < multiListOfUsers.size(); i++) {
                List<String> row = multiListOfUsers.get(i);
                User user = new User();
                user.setId(Long.parseLong(row.get(0)));
                user.setEmail(row.get(1));
                user.setUsername(row.get(2));
                user.setPassword(row.get(3));
                users = addToArray(user, users);
            }
            return users;
        } catch(SQLException e) {
            e.printStackTrace();
            return new User[0];
        }
    }
    public User[] addToArray(User u, User[] users) {
        User[] copyUsers = Arrays.copyOf(users, users.length + 1);
        copyUsers[users.length] = u;
        return copyUsers;
    }
    public String hashPassword(String password){
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashed;
    }
}
