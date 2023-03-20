package ru.tinkoff.edu.java.parser.result;

public record StackOverflowResultRecord(String userId) implements ParseResult {
    @Override
    public String getResult() {
        return userId;
    }
}
