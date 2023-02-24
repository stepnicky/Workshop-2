package pl.coderslab.dao;

import pl.coderslab.model.User;

public class MainDao {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user1 = new User("andrzejnowak@gmail.com", "andrzejnowak", "alamakota");

        userDao.create(user1);
        System.out.println(user1.getId());
    }

}
