package lms.itcluster.confassistant.exception;

import javassist.NotFoundException;

public class EntityNotFoundException extends NotFoundException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
