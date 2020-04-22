package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.exception.CantCompleteClientRequestException;
import lms.itcluster.confassistant.service.ImageStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public Optional<String> saveAndReturnImageLink(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Optional.empty();
        }

        checkContentType(file);

        String uuIdFile = UUID.randomUUID().toString();
        String resultFileName = uuIdFile + file.getOriginalFilename();
        String mediaDir = File.separator + "media" + File.separator;
        File uploadFile = new File(uploadPath + mediaDir);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();
        }

        File newPhoto = new File(uploadFile.toString() + File.separator + resultFileName);

        file.transferTo(newPhoto);
        return Optional.of(mediaDir + resultFileName);
    }

    private void checkContentType(MultipartFile file) {
        String contentType = Optional.ofNullable(file.getContentType()).orElseThrow(
                () -> {
                    log.debug("Wrong content type");
                    return new CantCompleteClientRequestException("Wrong content type");
                });

        log.debug(String.format("Content type for upload %s", contentType));

        if (!(contentType.contains("png") || contentType.contains("jpg") || contentType.contains("jpeg"))) {
            throw new CantCompleteClientRequestException("Only png and jpg image formats are supported: Current content type=" + contentType);
        }
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
