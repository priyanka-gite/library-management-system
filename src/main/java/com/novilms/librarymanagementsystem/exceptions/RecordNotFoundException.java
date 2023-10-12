package com.novilms.librarymanagementsystem.exceptions;

public class RecordNotFoundException extends RuntimeException {
    private String message;

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

}
