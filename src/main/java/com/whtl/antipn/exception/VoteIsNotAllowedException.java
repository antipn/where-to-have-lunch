package com.whtl.antipn.exception;

public class VoteIsNotAllowedException extends RuntimeException {

    private String message;
    private int userId;

    public VoteIsNotAllowedException(int userId, String message) {
        super(message);
        this.userId = userId;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }
}
