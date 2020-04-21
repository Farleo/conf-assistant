package lms.itcluster.confassistant.exception;

import javassist.NotFoundException;

import java.util.NoSuchElementException;

public class NoSuchConferenceException extends NoSuchElementException {

    public NoSuchConferenceException(String msg) {
        super(msg);
    }
}
