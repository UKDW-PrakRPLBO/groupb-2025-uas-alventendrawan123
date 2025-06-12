package org.uas.util;

import java.io.*;

public class SessionManager implements Serializable {
    private static SessionManager instance;
    private boolean isLoggedIn = false;
    private static final String FILE_NAME = "session.ser";

    private SessionManager() {
        loadSession();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login() {
        isLoggedIn = true;
        saveSession();
    }

    public void logout() {
        isLoggedIn = false;
        saveSession();
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    private void saveSession() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSession() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SessionManager saved = (SessionManager) in.readObject();
            this.isLoggedIn = saved.isLoggedIn;
        } catch (IOException | ClassNotFoundException e) {
            this.isLoggedIn = false;
        }
    }
}
