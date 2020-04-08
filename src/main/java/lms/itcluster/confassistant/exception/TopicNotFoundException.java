package lms.itcluster.confassistant.exception;

import javassist.NotFoundException;

public class TopicNotFoundException extends NotFoundException {

    public TopicNotFoundException(String msg) {
        super(msg);
    }
}
