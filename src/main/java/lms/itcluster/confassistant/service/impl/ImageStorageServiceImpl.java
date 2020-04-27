package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.exception.CantCompleteClientRequestException;
import lms.itcluster.confassistant.service.ImageStorageService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.result.Output;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private String uploadPath = System.getProperty("user.dir");

    @Override
    public Optional<String> saveAndReturnImageLink(byte[] bytes, String originalFileName) throws IOException {
        if (bytes == null || bytes.length <= 0) {
            return Optional.empty();
        }

        String uuIdFile = UUID.randomUUID().toString();
        String resultFileName = uuIdFile + originalFileName;
        String mediaDir = File.separator + "media" + File.separator;
        File uploadFile = new File(uploadPath + mediaDir);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();
        }

        File newPhoto = new File(uploadFile.toString() + File.separator + resultFileName);

        try (FileOutputStream fos = new FileOutputStream(newPhoto)) {
            fos.write(bytes);
        }

        return Optional.of(mediaDir + resultFileName);
    }

    @Override
    public void removeOldImage(String imageLink) {
        Path path = Paths.get(uploadPath + imageLink);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            log.error(String.format("Can't delete old photo: %s", imageLink), e);
        }
    }
}
