package lms.itcluster.confassistant.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadPhotoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null) {
            return;
        }

        MultipartFile multipartFile = (MultipartFile) target;

        if (multipartFile.isEmpty()) {
            return;
        }

        if (multipartFile.getSize() > 2097152) {
            errors.rejectValue("coverPhoto", "file.oversize", "file is too large");
            return;
        }
        String contentType = multipartFile.getContentType();

        if (contentType == null) {
            errors.rejectValue("coverPhoto", "wrong.file.type", "Wrong file type");
            return;
        }

        if (!(contentType.contains("image/png") || contentType.equals("image/jpeg"))) {
            errors.rejectValue("coverPhoto", "wrong.file.type", "Use file with jpg, jpeg or png from valid");
        }
    }
}
