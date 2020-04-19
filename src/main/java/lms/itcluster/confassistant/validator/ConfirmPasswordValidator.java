package lms.itcluster.confassistant.validator;

import lms.itcluster.confassistant.annotation.ConfirmPassword;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.repository.UserRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, Object> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String password;
    private String userId;
    private String message;

    @Override
    public void initialize(final ConfirmPassword constraintAnnotation) {
        password = constraintAnnotation.password();
        userId = constraintAnnotation.userId();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final String oldPassword = BeanUtils.getProperty(value, password);
            final Long id = Long.valueOf(BeanUtils.getProperty(value, userId));

            User user = userRepository.findById(id).get();
            valid = passwordEncoder.matches(oldPassword, user.getPassword());
        }
        catch (final Exception ignore)
        {
            // ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(password)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
