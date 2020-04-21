package lms.itcluster.confassistant.exception;

import java.util.NoSuchElementException;

public class NoSuchUserException extends NoSuchElementException {

    public NoSuchUserException(String msg) {
        super(msg);
    }
}
