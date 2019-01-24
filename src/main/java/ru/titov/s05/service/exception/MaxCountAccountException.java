package ru.titov.s05.service.exception;

public class MaxCountAccountException extends Exception{
    private int number;
    public int getNumber(){return number;}
    public MaxCountAccountException(String message, int num){

        super(message);
        number=num;
    }
}

