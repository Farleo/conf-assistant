package lms.itcluster.confassistant.validator;

import lms.itcluster.confassistant.annotation.UniqueEmail;
import lms.itcluster.confassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean valid = userRepository.findByEmail(value) == null;

        return valid;
    }
}
