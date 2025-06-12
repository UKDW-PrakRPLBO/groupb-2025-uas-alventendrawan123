package org.uas;

import org.uas.data.User;
import org.uas.repository.UserRepository;
import org.uas.util.DBConnectionManager;
import org.uas.util.SessionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class UASApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = DBConnectionManager.getConnection();
        UserRepository userRepo = new UserRepository(conn);
        SessionManager session = SessionManager.getInstance();

        if (!session.isLoggedIn()) {
            System.out.print("Username: ");
            String uname = scanner.nextLine();
            System.out.print("Password: ");
            String pwd = scanner.nextLine();
            try {
                if (userRepo.authenticateUser(uname, pwd)) {
                    System.out.println("Login berhasil");
                    session.login();
                } else {
                    System.out.println("Login gagal");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n1. Lihat Semua");
            System.out.println("2. Tambah");
            System.out.println("3. Ubah");
            System.out.println("4. Hapus");
            System.out.println("5. Logout");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            String pilih = scanner.nextLine();

            try {
                switch (pilih) {
                    case "1" -> {
                        List<User> users = userRepo.findAll();
                        for (User u : users) {
                            System.out.println(u.getEmail() + " - " + u.getUsername());
                        }
                    }
                    case "2" -> {
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Username: ");
                        String username = scanner.nextLine();
                        System.out.print("Password: ");
                        String pass = scanner.nextLine();
                        userRepo.insertUser(email, username, pass);
                    }
                    case "3" -> {
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Username Baru: ");
                        String username = scanner.nextLine();
                        System.out.print("Password Baru: ");
                        String pass = scanner.nextLine();
                        userRepo.updateUser(email, username, pass);
                    }
                    case "4" -> {
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        userRepo.deleteUser(email);
                    }
                    case "5" -> {
                        session.logout();
                        System.out.println("Logout berhasil");
                        running = false;
                    }
                    case "0" -> running = false;
                    default -> System.out.println("Pilihan tidak valid");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DBConnectionManager.closeConnection();
    }
}
