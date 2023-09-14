package com.novilms.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;


    public class RecordNotFound {
        private final String message;
        private final HttpStatus httpStatus;

        public RecordNotFound(String message, HttpStatus httpStatus) {
            this.message = message;
            this.httpStatus = httpStatus;
        }

        public String getMessage() {
            return message;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }
}
