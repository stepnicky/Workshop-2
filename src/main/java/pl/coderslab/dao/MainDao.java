package pl.coderslab.dao;

import pl.coderslab.model.User;

public class MainDao {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
//        User user1 = new User("andrzejnowak@gmail.com", "andrzejnowak", "alamakota");
//
//        userDao.create(user1);
//        System.out.println(user1.getId());
//        User user = userDao.read(11);
//        try {
//            System.out.println(String.format("%s, %s, %s, %s", user.getId(), user.getEmail(), user.getUsername(), user.getPassword()));
//        } catch (NullPointerException e) {
//            System.err.println("user value is " + user);
//        }
        User user = userDao.read(8);
        user.setEmail("a.nowak@gmail.com");
        userDao.update(user);

    }

}
