package pl.coderslab.dao;

import pl.coderslab.utils.DbUtil;
import pl.coderslab.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {

    private static final String READ_ALL = "SELECT * FROM users";

    private static final String READ_BY_ID = "SELECT * FROM users WHERE id=?";

    private static final String CREATE_NEW_USER = "INSERT INTO users (username, email, password) VALUES (?,?,?)";

    private static final String UPDATE_USER = "UPDATE users SET username=?, email=?, password=? WHERE id=?";

    private static final String REMOVE_USER = "DELETE FROM users WHERE id=?";

    public User create(User user) {
        try (Connection conn = DbUtil.connection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_NEW_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User readById(Long id) {
        try(Connection conn = DbUtil.connection()) {
            PreparedStatement ps = conn.prepareStatement(READ_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            User user = null;
            while(rs.next()) {
                user = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
            return user;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
//    public List<User> readAll() {
//        try(Connection conn = DbUtil.connection()) {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(READ_ALL);
//            List<User> list = new ArrayList<>();
//            while(rs.next()) {
//                list.add(new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
//            }
//            return list;
//        } catch(SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public User[] readAll() {
        try(Connection conn = DbUtil.connection()) {
            PreparedStatement ps = conn.prepareStatement(READ_ALL);
            ResultSet rs = ps.executeQuery();
            User[] users = new User[0];
            while(rs.next()) {
                users = addToArr(new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)), users);
            }
            return users;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public User update(User user) {
        try(Connection conn = DbUtil.connection()) {
            PreparedStatement ps = conn.prepareStatement(UPDATE_USER);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setLong(4, user.getId());
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean delete(Long id) {
        try(Connection conn = DbUtil.connection()) {
            PreparedStatement ps = conn.prepareStatement(REMOVE_USER);
            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                return true;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public User[] addToArr(User u, User[] users){
        User[] copyArr = Arrays.copyOf(users, users.length + 1);
        copyArr[users.length] = u;
        return copyArr;
    }

}
