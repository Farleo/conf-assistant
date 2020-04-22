package lms.itcluster.confassistant.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageStorageService {

    Optional<String> saveAndReturnImageLink(MultipartFile file) throws IOException;

    void removeOldImage(String imageLink);
}
