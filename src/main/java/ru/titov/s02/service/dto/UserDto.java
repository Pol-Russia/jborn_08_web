package ru.titov.s02.service.dto;

import org.apache.commons.codec.digest.DigestUtils;

public class UserDto {
    private int id = -11;
    private String mail;
    private String password;
    private String nick;
    private String fullName;

    private String toMD5(String start) {
        return DigestUtils.md5Hex(start);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = toMD5(password);
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "mail = " + getMail() +"; " + "Full name: " +getFullName() + "; " + "nick - " + getNick() + "; " ;
    }
}
