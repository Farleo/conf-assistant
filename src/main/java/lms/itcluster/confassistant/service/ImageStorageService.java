package lms.itcluster.confassistant.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface ImageStorageService {

    Optional<String> saveAndReturnImageLink(byte[] bytes, String originalFileName) throws IOException;

    void removeOldImage(String imageLink);
}
