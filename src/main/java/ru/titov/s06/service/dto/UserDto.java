package ru.titov.s06.service.dto;

public class UserDto {
    private int id = -11;
    private String mail;
    private String password;
    private String nick;
    private String fullName;


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
        this.password = password;
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
        return "id = " + this.getId() + " mail = " + this.getMail() + " password = " + this.getPassword();
    }
}
