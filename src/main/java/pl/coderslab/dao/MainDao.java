package pl.coderslab.dao;

import pl.coderslab.model.User;

public class MainDao {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
//        User user1 = new User("andrzejnowak@gmail.com", "andrzejnowak", "alamakota");
//
//        userDao.create(user1);
//        User user2 = new User("jankowalski@yahoo.com", "jankowal", "blablaal");
//        userDao.create(user2);
//        System.out.println(user1.getId());
//        User user = userDao.read(11);
//        try {
//            System.out.println(String.format("%s, %s, %s, %s", user.getId(), user.getEmail(), user.getUsername(), user.getPassword()));
//        } catch (NullPointerException e) {
//            System.err.println("user value is " + user);
//        }
//        User user = userDao.read(8);
//        user.setEmail("a.nowak@gmail.com");
//        userDao.update(user);

//        userDao.delete(8L);

        User andrzej = userDao.read(9L);
        andrzej.setUsername("andrewnowak");
        andrzej.setPassword("alamakotaaa");
        userDao.update(andrzej);

        User[] users = userDao.findAll();
        for(User user : users) {
            System.out.println(String.format("%s, %s, %s, %s", user.getId(), user.getEmail(), user.getUsername(), user.getPassword()));
        }
    }

}
