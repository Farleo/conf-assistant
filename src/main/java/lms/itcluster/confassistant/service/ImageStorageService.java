package lms.itcluster.confassistant.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStorageService {

    String saveAndReturnImageLink(MultipartFile file) throws IOException;

    void removeOldImage(String imageLink);
}
