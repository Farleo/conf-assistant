package lms.itcluster.confassistant.exception;

import javassist.NotFoundException;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class NoSuchEntityException extends NoSuchElementException {

    public NoSuchEntityException(String msg) {
        super(msg);
    }
}
