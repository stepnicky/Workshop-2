package pl.coderslab.dao;

import pl.coderslab.model.User;

import java.util.Scanner;

public class MainDao {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz nazwę akcji, którą chcesz wykonać\n list - lista użytkowników \n list_id - konkretny użytkownik po nr id\n create - dodanie nowego użytkownika\n update - aktualizacja użytkownika\n delete - usunięcie użytkownika\n exit - koniec programu");
        boolean isRunning = true;
        while(isRunning) {
            System.out.println("Podaj nazwę akcji");
            String input = scanner.nextLine();
            switch(input.trim().toLowerCase()) {
                case "list": {
                    listOfAllUsers(userDao);
                    break;
                }
                case "list_id": {
                    getUserById(userDao, scanner);
                    break;
                }
                case "create": {
                    createUser(userDao, scanner);
                    break;
                }
                case "update": {
                    updateUser(userDao, scanner);
                    break;
                }
                case "delete": {
                    deleteUser(userDao, scanner);
                    break;
                }
                case "exit": {
                    isRunning = false;
                    System.out.println("Do widzenia!");
                    break;
                }
                default: {
                    System.out.println("Podałeś niewłaściwe dane");
                }
            }
        }
    }
    public static void listOfAllUsers(UserDao dao) {
        User[] users = dao.readAll();
        for(User u: users) {
            System.out.println(String.format("id: %s, email: %s, username: %s", u.getId(), u.getEmail(), u.getUserName()));
        }
    }
    public static void getUserById(UserDao dao, Scanner scanner) {
        System.out.println("Wpisz id użytkownika, którego chcesz wyświetlić");
        while(!scanner.hasNextInt()) {
            System.out.println("Nie podałeś liczby. Podaj liczbę");
            scanner.nextLine();
        }
        String id = scanner.nextLine();
        User user = dao.readById(Long.parseLong(id));
        if(user == null) {
            System.err.println(String.format("Użytkownik o id=%s nie istnieje", id));
        } else {
            System.out.println(String.format("id: %s, email: %s, username: %s", user.getId(), user.getEmail(), user.getUserName()));
        }
//        try {
//            System.out.println(String.format("id: %s, email: %s, username: %s", user.getId(), user.getEmail(), user.getUsername()));
//        } catch (NullPointerException e) {
//            System.err.println(String.format("Użytkownik o id=%s nie istnieje", id));
//        }
    }
    public static void createUser(UserDao dao, Scanner scanner) {
        System.out.println("Podaj adres email nowego użytkownika");
        String email = setEmail(dao, scanner);
        System.out.println("Podaj nazwę nowego użytkownika");
        String userName = scanner.nextLine();
        System.out.println("Podaj hasło nowego użytkownika");
        String password = scanner.nextLine();
        if (!email.isBlank() && !userName.isBlank() && !password.isBlank()) {
            User user = dao.create(new User(email, userName, password));
            System.out.println("Użytkownik został pomyślnie dodany");
            System.out.println(String.format("id: %s, email: %s, username: %s", user.getId(), user.getEmail(), user.getUserName()));
        } else {
            System.out.println("Podałeś niewystarczającą ilośc danych");
        }
    }
    public static void updateUser(UserDao dao, Scanner scanner) {
        System.out.println("Podaj id użytkownika, którego chcesz zmodyfikować");
        while(!scanner.hasNextInt()) {
            System.err.println("Nie podałeś liczby. Podaj liczbę");
            scanner.nextLine();
        }
        String id = scanner.nextLine();
        User user = dao.readById(Long.parseLong(id));
        if(user == null) {
            System.err.println("Brak użytkownika o id="+id);
            return;
        }
        System.out.println(String.format("id: %s, email: %s, username: %s", user.getId(), user.getEmail(), user.getUserName()));
        System.out.println("Podaj nowy adres email lub kliknij enter by pominąć");
        String email = setEmail(dao, scanner);
        if(!email.isBlank()) {
            user.setEmail(email);
        }
        System.out.println("Podaj nową nazwę użytkownika lub kliknij enter by pominąć");
        String userName = scanner.nextLine();
        if(!userName.isBlank()) {
            user.setUserName(userName);
        }
        System.out.println("Podaj nowe hasło użytkownika lub kliknij enter by pominąć");
        String password = scanner.nextLine();
        if(!password.isBlank()) {
            user.setPassword(password);
        }
        dao.update(user);
        User updatedUser = dao.readById(Long.parseLong(id));
        System.out.println("Użytkownik został pomyślnie zaktualizowany\n--------------------------");
        System.out.println(String.format("id: %s, email: %s, username: %s", updatedUser.getId(), updatedUser.getEmail(), updatedUser.getUserName()));
    }
    public static void deleteUser(UserDao dao, Scanner scanner) {
        System.out.println("Podaj id użytkownika, którego chcesz usunąć");
        while(!scanner.hasNextInt()) {
            System.err.println("Nie podałeś liczby. Podaj liczbę");
            scanner.nextLine();
        }
        String id = scanner.nextLine();
        User user = dao.readById(Long.parseLong(id));
        if(user == null) {
            System.err.println("Nie ma takiego użytkownika w bazie danych");
            return;
        }
        dao.delete(user.getId());
        System.out.println("Użytkownik został pomyślnie usunięty");
    }

    public static String setEmail(UserDao dao, Scanner scanner) {
        User[] users = dao.readAll();
        String email;
        boolean exists = false;
        do {
            exists = false;
            email = scanner.nextLine();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    System.err.println("Użytkownik o podanym adresie email już istnieje. Podaj inny adres");
                    exists = true;
                    break;
                }
            }
        } while(exists);
        return email;
    }


}
