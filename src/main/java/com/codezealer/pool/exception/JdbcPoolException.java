package com.codezealer.pool.exception;

public class JdbcPoolException extends RuntimeException {
    public JdbcPoolException() {
        super();
    }

    public JdbcPoolException(String message) {
        super(message);
    }

    public JdbcPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcPoolException(Throwable cause) {
        super(cause);
    }

    protected JdbcPoolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
