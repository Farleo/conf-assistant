package lms.itcluster.confassistant.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface ImageStorageService {

    Optional<String> saveAndReturnImageLink(byte[] bytes, String originalFileName) throws IOException;

    void removeOldImage(Path imageLink);
}
