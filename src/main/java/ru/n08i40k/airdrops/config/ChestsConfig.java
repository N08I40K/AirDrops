package ru.n08i40k.airdrops.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.n08i40k.totp.TOTP;

public class UserEntry {
    private List<String> loginCommands;
    private List<String> logoutCommands;

    private String nickname;
    private String secretCode;

    private String uuid;

    public UserEntry() {
        secretCode = TOTP.getInstance().generateKey();

        loginCommands = new ArrayList<>();
        logoutCommands = new ArrayList<>();
    }



    public List<String> getLoginCommands() {
        return loginCommands;
    }

    public void setLoginCommands(List<String> loginCommands) {
        this.loginCommands = loginCommands;
    }

    public List<String> getLogoutCommands() {
        return logoutCommands;
    }

    public void setLogoutCommands(List<String> logoutCommands) {
        this.logoutCommands = logoutCommands;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
