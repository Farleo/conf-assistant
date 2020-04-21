package lms.itcluster.confassistant.exception;

import javassist.NotFoundException;

import java.util.NoSuchElementException;

public class NoSuchTopicException extends NoSuchElementException {

    public NoSuchTopicException(String msg) {
        super(msg);
    }
}
