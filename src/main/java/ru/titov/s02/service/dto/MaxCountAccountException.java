package ru.titov.s02.service.dto;

public class MaxCountAccountException extends Exception{
        private int number;
        public int getNumber(){return number;}
        public MaxCountAccountException(String message, int num){

            super(message);
            number=num;
        }
    }

