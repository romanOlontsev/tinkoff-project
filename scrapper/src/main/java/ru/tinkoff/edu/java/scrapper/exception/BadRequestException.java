package ru.tinkoff.edu.java.scrapper.exception;

public class BadRequestException extends Exception{
    public BadRequestException(String message) {
        super(message);
    }
}
