package ru.n08i40k.totp.config;

import java.util.ArrayList;
import java.util.List;

public class MainConfig {
    private String lang;

    private List<String> loginCommands;
    private List<String> logoutCommands;

    private List<UserEntry> users;

    public MainConfig() {
        lang = "ru-RU";

        loginCommands = new ArrayList<>();
        logoutCommands = new ArrayList<>();

        users = new ArrayList<>();
    }


    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    // Getters

    public List<String> getLoginCommands() {
        return loginCommands;
    }

    public List<String> getLogoutCommands() {
        return logoutCommands;
    }

    public List<UserEntry> getUsers() {
        return users;
    }



    // Setters

    public void setLoginCommands(List<String> loginCommands) {
        this.loginCommands = loginCommands;
    }

    public void setLogoutCommands(List<String> logoutCommands) {
        this.logoutCommands = logoutCommands;
    }

    public void setUsers(List<UserEntry> users) {
        this.users = users;
    }

}
