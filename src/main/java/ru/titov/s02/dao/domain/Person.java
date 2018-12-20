package ru.titov.s02.dao.domain;

import java.util.List;

public class Person {
    private int id;
    private String mail;
    private String password;
    private String nick;
    private String fullName;
    private List<Account> listAccount; //Список счетов!

    public List<Account> getListAccount(int numberAccount) {//Метод, возвращающий список счетов
        //данного клиента
        AccountDao accountDao = new AccountDao();
        return accountDao.findByNumberAccount(numberAccount);
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
}
